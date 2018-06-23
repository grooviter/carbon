package carbon.ast;

import asteroid.Criterias;
import asteroid.Expressions;
import asteroid.transformer.AbstractMethodNodeTransformer;
import asteroid.utils.StatementUtils;
import carbon.ast.model.Argument;
import carbon.ast.model.Task;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.control.SourceUnit;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static asteroid.Statements.blockS;
import static carbon.ast.Constants.SCRIPT_METHOD_NAME;

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
        List<StatementUtils.Group> groups = Sections.getGroupsFromMethod(methodNode);
        Task task = Sections.createTaskFrom(groups);

        Statement newCliStmt = Ast.newBuilderS(task.getMetaInfo().getName(), task.getUsage());
        Statement parseArgsStmt = Ast.parseArgsStmt(methodNode.getDeclaringClass());
        Statement usageMessageStmt = Ast.usageMessageConfigurationStmt(task.getUsage());
        Statement ifHelpStmt = Ast.usageStmt();
        List<Statement> allCases = createScriptOptions(task.getArguments());
        List<Statement> statements = new ArrayList<>();

        statements.add(newCliStmt);
        statements.addAll(allCases);
        statements.add(usageMessageStmt);
        statements.add(parseArgsStmt);
        statements.add(ifHelpStmt);
        statements.addAll(task.getStatements());

        methodNode.setCode(blockS(statements));
    }

    private static MethodCallExpression printlnX(String message) {
        return Expressions.callThisX("println", Expressions.constX(message));
    }

    private List<Statement> createScriptOptions(final List<Argument> arguments) {
        return arguments
            .stream()
            .map(Ast::createFieldStmt)
            .collect(Collectors.toList());
    }
}
