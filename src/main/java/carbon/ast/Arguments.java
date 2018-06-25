package carbon.ast;

import carbon.ast.model.Argument;
import groovy.lang.GroovyObject;
import org.codehaus.groovy.ast.expr.*;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.codehaus.groovy.runtime.StringGroovyMethods;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static asteroid.Expressions.constX;
import static carbon.ast.Constants.*;

class Arguments {

    public static Argument DEFAULT_ARGUMENT_HELP = new Argument(
            "help",
            "command help",
            false,
            null,
            null);

    interface OptionalArgument extends
            Function<MapEntryExpression, Optional<Argument>> {

    }

    static Optional<Argument> extractArgument(MapEntryExpression entryExpression) {
        List<OptionalArgument> strategies = Arrays.asList(
                Arguments::extractArgumentFromMap,
                Arguments::extractArgumentFromList);

        return strategies
            .stream()
            .map(strategy -> strategy.apply(entryExpression))
            .flatMap(Functions::flatten)
            .findFirst();
    }

    private static Optional<Argument> extractArgumentFromMap(MapEntryExpression entryExpr) {
        Expression keyExpression = entryExpr.getKeyExpression();
        String name = keyExpression.getText();

        return Optional
            .of(entryExpr.getValueExpression())
            .filter(MapExpression.class::isInstance)
            .map(MapExpression.class::cast)
            .map(mapExpr -> createTaskArgument(name, mapExpr));
    }

    private static Argument createTaskArgument(String name, MapExpression valExpression) {
        String description = findMapEntry(valExpression, DEFAULT_ARGS_DESCRIPTION)
                .map(MapEntryExpression::getValueExpression)
                .map(Expression::getText)
                .orElse(EMPTY);

        Boolean mandatory = findMapEntry(valExpression, DEFAULT_ARGS_PARAM_MANDATORY)
                .map(MapEntryExpression::getValueExpression)
                .flatMap(Arguments::extractMandatoryValue)
                .orElse(false);

        Class type = findMapEntry(valExpression, "type")
                .map(MapEntryExpression::getValueExpression)
                .flatMap(Arguments::extractTypeValue)
                .orElse(String.class);

        String defaultValue = findMapEntry(valExpression, "value")
                .map(MapEntryExpression::getValueExpression)
                .map(Expression::getText)
                .orElse(null);

        return new Argument(name, description, mandatory, type, defaultValue);
    }

    private static Optional<Boolean> extractMandatoryValue(Expression expr) {
        return Optional
            .ofNullable(expr)
            .filter(BooleanExpression.class::isInstance)
            .map(Expression::getText)
            .map(Boolean::valueOf);
    }

    private static Optional<Class> extractTypeValue(Expression expression) {
        return Optional.ofNullable(expression)
                .filter(ClassExpression.class::isInstance)
                .map(ClassExpression.class::cast)
                .map(classExpr -> classExpr.getType().getTypeClass());
    }

    private static Optional<Argument> extractArgumentFromList(MapEntryExpression entryExpr) {
        Expression keyExpression = entryExpr.getKeyExpression();
        String name = keyExpression.getText();

        return Optional
            .of(entryExpr.getValueExpression())
            .filter(ListExpression.class::isInstance)
            .map(ListExpression.class::cast)
            .map(listExpr -> createTaskArgument(name, listExpr));
    }

    private static Argument createTaskArgument(String name, ListExpression valExpression) {
        List<Expression> argumentList = valExpression.getExpressions();

        String description = Optional
                .of(argumentList)
                .flatMap(getArgNo(0))
                .map(Expression::getText)
                .orElse(name);

        Boolean mandatory = Optional
                .of(argumentList)
                .flatMap(getArgNo(1))
                .flatMap(Arguments::extractMandatoryValue)
                .orElse(false);

        Class type = Optional
                .of(argumentList)
                .flatMap(getArgNo(2))
                .flatMap(Arguments::extractTypeValue)
                .orElse(String.class);

        String defaultValue = Optional.of(argumentList)
                .flatMap(getArgNo(3))
                .map(Expression::getText)
                .orElse(EMPTY);

        return new Argument(name, description, mandatory, type, defaultValue);
    }

    private static Function<List<Expression>, Optional<Expression>> getArgNo(Integer no) {
        return (List<Expression> listExpr) -> {
            return Optional
                .ofNullable(listExpr)
                .filter(list -> list.size() > no)
                .map(list -> list.get(no));
        };
    }

    private static Optional<MapEntryExpression> findMapEntry(MapExpression expr, String key) {
        return expr
                .getMapEntryExpressions()
                .stream()
                .filter(entry -> entry.getKeyExpression().getText().equals(key))
                .findAny();
    }
}
