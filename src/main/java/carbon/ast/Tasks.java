package carbon.ast;

import asteroid.Expressions;
import asteroid.Utils;
import asteroid.utils.StatementUtils;
import carbon.ast.model.Task;
import carbon.ast.model.TaskArgument;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.MapEntryExpression;
import org.codehaus.groovy.ast.expr.MapExpression;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.ast.stmt.Statement;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import static asteroid.Expressions.mapX;
import static carbon.ast.Constants.DEFAULT_ARGS_DESCRIPTION;
import static carbon.ast.Constants.EMPTY;
import static carbon.ast.Functions.not;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

/**
 * Utility functions to extract Tasks from code labelled blocks
 *
 * @since 0.1.0
 */
public class Tasks {

    /**
     * Alias of Function<StatementUtils.Group, Optional<Task>>
     *
     * @since 0.1.0
     */
    interface OptionalTask extends Function<StatementUtils.Group, Optional<Task>> {
        // used as alias
    }

    /**
     * Extracts the information of a given {@link Task} from a {@link StatementUtils.Group}
     *
     * @param group the code statement group
     * @return an {@link Optional} of a possible new {@link Task}
     * @since 0.1.0
     */
    private static Optional<Task> extractTask(final StatementUtils.Group group) {
        final List<OptionalTask> strategyList = asList(
            Tasks::fromMapStrategy,
            Tasks::fromTextStrategy
        );

        return strategyList
            .stream()
            .map(strategy -> strategy.apply(group))
            .flatMap(Functions::flatten)
            .findFirst();
    }

    /**
     * @param groupList
     * @return
     * @since 0.1.0
     */
    public static Optional<Task> getUsageTask(List<StatementUtils.Group> groupList) {
        return groupList
            .stream()
            .filter(Tasks::isUsageTask)
            .map(Tasks::extractTask)
            .flatMap(Functions::flatten)
            .findFirst();
    }

    /**
     * @param groupList
     * @return
     * @since 0.1.0
     */
    public static List<Task> getNonUsageTasks(List<StatementUtils.Group> groupList) {
        return groupList
            .stream()
            .filter(not(Tasks::isUsageTask))
            .map(Tasks::extractTask)
            .flatMap(Functions::flatten)
            .collect(toList());
    }

    public static Boolean isUsageTask(StatementUtils.Group group) {
        return group.label.name.equals(Constants.DEFAULT_USAGE_NAME);
    }

    /**
     * @param methodNode
     * @return
     * @since 0.1.0
     */
    static List<StatementUtils.Group> getGroupsFromMethod(MethodNode methodNode) {
        return Utils.STMT.groupStatementsByLabel(Utils.NODE.getCodeBlock(methodNode));
    }

    /**
     * This method evaluates if the {@link Statement} passed as
     * parameter has only one expression of type {@link
     * ConstantExpression}
     *
     * @param stmt the statement to evaluate
     * @return true if the statement has one and only one constant
     * expression false otherwise
     * @since 0.1.4
     */
    static Boolean isExpressionStmtAndHasAConstantExpression(final Statement stmt) {
        return stmt instanceof ExpressionStatement &&
                ((ExpressionStatement) stmt).getExpression() instanceof ConstantExpression;
    }

    /**
     * Creates a {@link Optional} task from a given group that uses a {@link MapExpression}
     * as label expression value
     * <pre>
     * <code>
     *  save: [desc: 'saves a record']
     * </code>
     * </pre>
     *
     * @param group the labelled code block
     * @return an {@link Optional} of a possible new {@link Task}
     * @since 0.1.0
     */
    private static Optional<Task> fromMapStrategy(final StatementUtils.Group group) {
        return fromStrategy(group, Tasks::isMapExpression, Tasks::createTaskFromMap);
    }

    /**
     * Creates a {@link Optional} task from a given group that uses a {@link ConstantExpression}
     * as label expression value
     * <pre>
     * <code>
     *  save: 'saves a record'
     * </code>
     * </pre>
     *
     * @param group the labelled code block
     * @return an {@link Optional} of a possible new {@link Task}
     * @since 0.1.0
     */
    private static Optional<Task> fromTextStrategy(final StatementUtils.Group group) {
        return fromStrategy(group, Tasks::isTextExpression, Tasks::createTaskFromText);
    }

    /**
     * Transforms a given {@link StatementUtils.Group} to a {@link Task} with a given function,
     * only if the group matches a specific condition
     *
     * @param group group to transform
     * @param cond condition to transform the group
     * @param transform function transforming the group
     * @return a {@link Task} if the condition is met
     * @since 0.1.0
     */
    private static Optional<Task> fromStrategy(StatementUtils.Group group,
                                       Predicate<StatementUtils.Group> cond,
                                       Function<StatementUtils.Group, Task> transform) {
        return Optional
            .ofNullable(group)
            .filter(Tasks::isThereLabel)
            .filter(cond)
            .map(transform);
    }

    /**
     * Creates an instance of {@link Task} with the information found in a {@link StatementUtils.Group} when
     * the label expression is a map
     *
     * @param group group to get the information from
     * @return an instance of {@link Task}
     * @since 0.1.0
     */
    private static Task createTaskFromMap(StatementUtils.Group group) {
        StatementUtils.Label label = group.label;
        List<Statement> statements = group.statements;
        ConstantExpression nameExpr = label.nameAsExpression();
        MapExpression mapExpr = (MapExpression) label.expression;

        ConstantExpression descExpr = findMapEntry(mapExpr, DEFAULT_ARGS_DESCRIPTION)
            .map(MapEntryExpression::getValueExpression)
            .filter(ConstantExpression.class::isInstance)
            .map(ConstantExpression.class::cast)
            .orElse(Expressions.constX(EMPTY));

        MapExpression argsExpr = findMapEntry(mapExpr, Constants.DEFAULT_ARGS_PARAM_NAME)
            .map(MapEntryExpression::getValueExpression)
            .filter(MapExpression.class::isInstance)
            .map(MapExpression.class::cast)
            .orElse(mapX());

        List<TaskArgument> argumentList = argsExpr
            .getMapEntryExpressions()
            .stream()
            .map(Arguments::extractArgument)
            .flatMap(Functions::flatten)
            .collect(toList());

        return new Task(nameExpr, descExpr, argumentList, statements);
    }

    /**
     * Creates an instance of {@link Task} with the information found in a {@link StatementUtils.Group} when
     * the label expression is a constant String
     *
     * @param group group to get the information from
     * @return an instance of {@link Task}
     * @since 0.1.0
     */
    private static Task createTaskFromText(StatementUtils.Group group) {
        StatementUtils.Label label = group.label;
        ConstantExpression nameExpr = label.nameAsExpression();
        ConstantExpression descExpr = (ConstantExpression) label.expression;

        return new Task(nameExpr, descExpr, emptyList(), group.statements);
    }

    /**
     * Function that can be used as a predicate to decide whether a given {@link StatementUtils.Group}
     * has a label or not
     *
     * @param group checked group
     * @return true if the group has a label or false otherwise
     * @since 0.1.0
     */
    private static Boolean isThereLabel(StatementUtils.Group group) {
        return group.label != null;
    }

    /**
     * Function that can be used as a predicate to decide whether a given {@link StatementUtils.Label}
     * expression is of type text or not
     *
     * @param group checked label group
     * @return true if the label expression is of type text, false otherwise
     * @since 0.1.0
     */
    private static Boolean isTextExpression(StatementUtils.Group group) {
        Expression expression = group.label.expression;

        return expression instanceof ConstantExpression &&
                expression.getType().getTypeClass().equals(String.class);
    }

    /**
     * Function that can be used as a predicate to decide whether a given {@link StatementUtils.Label}
     * expression is of type {@link MapExpression} or not
     *
     * @param group checked label group
     * @return true if the label expression is of type {@link MapExpression}, false otherwise
     * @since 0.1.0
     */
    private static Boolean isMapExpression(StatementUtils.Group group) {
        Expression expression = group.label.expression;

        return expression instanceof MapExpression;
    }

    /**
     * Finds a given {@link MapEntryExpression} from a given {@link MapExpression} by its key
     *
     * @param expr the map expression to get the entry from
     * @param key the key used to identigy the entry
     * @return the expression of the entry value
     * @since 0.1.0
     */
    private static Optional<MapEntryExpression> findMapEntry(MapExpression expr, String key) {
        return expr
            .getMapEntryExpressions()
            .stream()
            .filter(entry -> entry.getKeyExpression().getText().equals(key))
            .findAny();
    }
}
