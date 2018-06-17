package carbon.ast;

import asteroid.Criterias;
import asteroid.Statements;
import asteroid.transformer.AbstractMethodNodeTransformer;
import asteroid.utils.StatementUtils;
import carbon.ast.model.Task;
import groovy.cli.picocli.CliBuilder;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.expr.*;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.control.SourceUnit;

import java.util.*;

import static asteroid.Expressions.*;
import static asteroid.Statements.*;
import static carbon.ast.Constants.*;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.*;

/**
 * Transforms the main method in a script, which is the "run" method
 * to convert labelled statements into a CliBuilder aware script.
 *
 * @since 0.1.0
 */
public class Transformer extends AbstractMethodNodeTransformer {

    /**
     * Default constructor
     *
     * @param sourceUnit in case we need to interact with compiler
     * @since 0.1.0
     */
    public Transformer(final SourceUnit sourceUnit) {
        super(sourceUnit, Criterias.byMethodNodeName(SCRIPT_METHOD_NAME));
    }

    @Override
    public void transformMethod(final MethodNode methodNode) {
        List<StatementUtils.Group> groups = Tasks.getGroupsFromMethod(methodNode);

        Task usageTask = Tasks.getUsageTask(groups).orElse(DEFAULT_USAGE_TASK);
        List<Task> nonUsageTasks = Tasks.getNonUsageTasks(groups);

        List<Statement> cliConfiguration = asList(
                newBuilderS(usageTask),
                cliWithS(nonUsageTasks),
                parseArgsStmt());

        List<Statement> cliBuilderCases = createCases(nonUsageTasks);
        List<Statement> allCases = new ArrayList<>();

        allCases.addAll(cliConfiguration);
        allCases.addAll(cliBuilderCases);

        methodNode.setCode(blockS(allCases));
    }

    /**
     * Creates the {@link Statement} where the CliBuilder instance declaration
     * is created
     *
     * <code>CliBuilder cli = new CliBuilder(usage: "")</code>
     *
     * @param usageTask task responsible to show how to use the task
     * @return an {@link Statement} with the CliBuilder instance creation expression
     * @since 0.1.0
     */
    static Statement newBuilderS(Task usageTask) {
        MapExpression usageMapX = mapX(mapEntryX(usageTask.getName(), usageTask.getDescription()));
        DeclarationExpression declarationX = varDeclarationX(
            CLI_BUILDER_NAME,
            CliBuilder.class,
            newX(CliBuilder.class, usageMapX));

        return stmt(declarationX);
    }

    /**
     * Creates the CliBuilder statement with the different usage options
     *
     * <code>
     * cli.with {
     *    h(...)
     *    u(...)
     * }
     * </code>
     *
     * @param tasks all available tasks minus the usage task
     * @return a statement with the CliBuilder options call statement
     * @since 0.1.0
     */
    static Statement cliWithS(List<Task> tasks) {
        MethodCallExpression usageHelpOptX = createOptX("help", "");
        List<MethodCallExpression> optionsX = tasks
            .stream()
            .map(Transformer::createOptX)
            .collect(collectingAndThen(toList(), (list) -> {
                list.add(usageHelpOptX);

                return list;
            }));

        return stmt(cliWithX(optionsX));
    }

    private static MethodCallExpression createOptX(Task task) {
        String groupName = task.getName().getText();
        String groupDesc = task.getDescription().getText();

        return createOptX(groupName, groupDesc);
    }

    private static MethodCallExpression createOptX(String name, String desc) {
        String optChar = Optional
            .ofNullable(name)
            .map(String::toLowerCase)
            .map(n -> EMPTY + n.charAt(0))
            .orElse(EMPTY);

        MapExpression mapExpr = mapX(
                mapEntryX(constX("longOpt"), constX(name)),
                mapEntryX(constX("required"), constX(false)));

        return callThisX(optChar, mapExpr, constX(desc));
    }

    private static MethodCallExpression cliWithX(List<MethodCallExpression> optionsXs) {
        List<Statement> statements = optionsXs
            .stream()
            .map(Statements::stmt)
            .collect(toList());

        ClosureExpression closureX = closureX(blockS(statements));

        return callX(varX(CLI_BUILDER_NAME), "with", closureX);
    }

    /**
     * Creates the statement where the CliBuilder parses the script arguments
     *
     * <code>options = cli.parse(strategies)</code>
     *
     * @return a {@link Statement} with an expression parsing the script strategies
     * @since 0.1.0
     */
    static Statement parseArgsStmt() {
        DeclarationExpression declarationX = varDeclarationX(
            "options",
            Object.class,
            callX(varX(CLI_BUILDER_NAME), "parse", varX("strategies")));

        return stmt(declarationX);
    }

    /**
     * Generates if/else statements from the different groups passed
     * as parameters
     *
     * <code>
     * if (options.h) {
     *  // code
     * }
     * </code>
     */
    static List<Statement> createCases(List<Task> taskList) {
        return taskList
            .stream()
            .map(Transformer::transformLiteralGroup)
            .map(Transformer::createNormalCaseS)
            .collect(collectingAndThen(toCollection(LinkedList::new), (list) -> {
                list.addFirst(createHelpCaseS());

                return list;
            }));
    }

    private static Statement createNormalCaseS(final Task task) {
        return createIfOptionStmt(task.getName().getText(), task.getStatements());
    }

    private static Statement createHelpCaseS() {
        MethodCallExpression callUsageX = callX(varX(CLI_BUILDER_NAME), DEFAULT_USAGE_NAME);
        Statement usageStatement = stmt(callUsageX);

        return createIfOptionStmt("h", Collections.singletonList(usageStatement));
    }

    private static Statement createIfOptionStmt(String option, List<Statement> statements) {
        PropertyExpression propX = propX(varX("options"), constX(option));

        return ifS(boolX(propX), blockS(statements));
    }

    private static Task transformLiteralGroup(Task task) {
        List<Statement> statements = task.getStatements();

        if (statements.size() == 1) {
            Statement stmt = statements.get(0);

            if (!Tasks.isExpressionStmtAndHasAConstantExpression(stmt)) {
                ExpressionStatement statement = (ExpressionStatement) stmt;
                MethodCallExpression printlnX = callThisX("println", statement.getExpression());
                Statement printlnS = stmt(printlnX);

                return new Task(
                        task.getName(),
                        task.getDescription(),
                        task.getArguments(),
                        Collections.singletonList(printlnS));
            }
        }

        return task;
    }
}
