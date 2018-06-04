package carbon.ast

import java.util.function.Function
import java.util.function.Predicate

import asteroid.utils.StatementUtils
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codehaus.groovy.ast.expr.Expression
import org.codehaus.groovy.ast.expr.MapEntryExpression
import org.codehaus.groovy.ast.expr.MapExpression
import org.codehaus.groovy.ast.stmt.Statement

/**
 * Utility functions to extract Tasks from code labelled blocks
 *
 * @since 0.1.0
 */
class TaskUtils {

    /**
     * Extracts the information of a given {@link Task} from a {@link StatementUtils.Group}
     *
     * @param group the code statement group
     * @return an {@link Optional} of a possible new {@link Task}
     * @since 0.1.0
     */
    static Optional<Task> extractTask(final StatementUtils.Group group) {
        final List<Closure<Optional<Task>>> strategyList = [
            TaskUtils.&fromMapStrategy,
            TaskUtils.&fromTextStrategy
        ]

        return strategyList.findResult({ Closure<Optional<Task>> strategy ->
            strategy(group)
        }) as Optional<Task>
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
    static Optional<Task> fromMapStrategy(final StatementUtils.Group group) {
        return fromStrategy(group, TaskUtils.&isMapExpression, TaskUtils.&createTaskFromMap)
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
    static Optional<Task> fromTextStrategy(final StatementUtils.Group group) {
        return fromStrategy(group, TaskUtils.&isTextExpression, TaskUtils.&createTaskFromText)
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
    static Optional<Task> fromStrategy(StatementUtils.Group group,
                                       Predicate<StatementUtils.Group> cond,
                                       Function<StatementUtils.Group, Task> transform) {
        return Optional
            .ofNullable(group)
            .filter(TaskUtils.&isThereLabel)
            .filter(cond)
            .map(transform)
    }

    /**
     * Creates an instance of {@link Task} with the information found in a {@link StatementUtils.Group} when
     * the label expression is a map
     *
     * @param group group to get the information from
     * @return an instance of {@link Task}
     * @since 0.1.0
     */
    static Task createTaskFromMap(StatementUtils.Group group) {
        StatementUtils.Label label = group.label
        List<Statement> statements = group.statements
        ConstantExpression nameExpr = label.nameAsExpression()
        MapExpression mapExpr = label.expression as MapExpression
        ConstantExpression descExpr = findEntryByKey(mapExpr, 'desc', ConstantExpression)
        MapExpression argsExpr = findEntryByKey(mapExpr, 'args', MapExpression)

        return new Task(
            name: nameExpr,
            description: descExpr,
            statements: statements,
            arguments: argsExpr
        )
    }

    /**
     * Creates an instance of {@link Task} with the information found in a {@link StatementUtils.Group} when
     * the label expression is a constant String
     *
     * @param group group to get the information from
     * @return an instance of {@link Task}
     * @since 0.1.0
     */
    static Task createTaskFromText(StatementUtils.Group group) {
        StatementUtils.Label label = group.label
        ConstantExpression nameExpr = label.nameAsExpression()
        ConstantExpression descExpr = label.expression as ConstantExpression

        return new Task(
            name: nameExpr,
            description: descExpr,
            statements: group.statements
        )
    }

    /**
     * Function that can be used as a predicate to decide whether a given {@link StatementUtils.Group}
     * has a label or not
     *
     * @param group checked group
     * @return true if the group has a label or false otherwise
     * @since 0.1.0
     */
    static Boolean isThereLabel(StatementUtils.Group group) {
        return group.label
    }

    /**
     * Function that can be used as a predicate to decide whether a given {@link StatementUtils.Label}
     * expression is of type text or not
     *
     * @param label checked label
     * @return true if the label expression is of type text, false otherwise
     * @since 0.1.0
     */
    static Boolean isTextExpression(StatementUtils.Group group) {
        Expression expression = group.label.expression

        return expression &&
                expression instanceof ConstantExpression &&
                expression.getType().getTypeClass().equals(String)
    }

    /**
     * Function that can be used as a predicate to decide whether a given {@link StatementUtils.Label}
     * expression is of type {@link MapExpression} or not
     *
     * @param label checked label
     * @return true if the label expression is of type {@link MapExpression}, false otherwise
     * @since 0.1.0
     */
    static Boolean isMapExpression(StatementUtils.Group group) {
        Expression expression = group.label.expression

        return expression && expression instanceof MapExpression
    }

    /**
     * Finds a given {@link MapEntryExpression} from a given {@link MapExpression} by its key
     *
     * @param expr the map expression to get the entry from
     * @param key the key used to identigy the entry
     * @param clazz the type of class expected for the entry value
     * @return the expression of the entry value
     * @since 0.1.0
     */
    static <T extends Expression> T findEntryByKey(MapExpression expr, String key, Class<T> clazz) {
        return expr.mapEntryExpressions.find({ MapEntryExpression entryExpression ->
            entryExpression.keyExpression.getText() == key
        }).asType(clazz)
    }
}
