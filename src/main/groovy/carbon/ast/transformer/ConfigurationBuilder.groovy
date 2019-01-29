package carbon.ast.transformer

import org.codehaus.groovy.ast.expr.Expression
import org.codehaus.groovy.ast.expr.ClassExpression
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codehaus.groovy.ast.expr.MapExpression
import org.codehaus.groovy.ast.expr.MapEntryExpression
import groovy.transform.TailRecursive
import groovy.transform.TupleConstructor
import org.yaml.snakeyaml.Yaml

/**
 * Builds Carbon's configuration from the Carbon's variable found in
 * the Groovy script
 *
 * @since 0.2.0
 */
@TupleConstructor
class ConfigurationBuilder {

    static final Map<String,?> DEFAULT = [
        params: [:],
        options: [:]
    ]

    Expression expr

    /**
     * From the expression value found, a configuration map will be
     * built
     *
     * @return a map containing the configuration values
     * @since 0.2.0
     */
    Map<String,?> build() {
        switch (expr) {
            case MapExpression:
                List<MapEntryExpression> mapEntryXList = ((MapExpression)expr).mapEntryExpressions
                return buildFromMap(mapEntryXList, DEFAULT)
            case ConstantExpression:
                ConstantExpression constX = expr as ConstantExpression
                return buildFromString(constX)

            default:
                return [:]// throw new IllegalStateException('Carbon configuration can be only either a map or a string')
        }
    }

    @TailRecursive
    private Map<String,?> buildFromMap(List<MapEntryExpression> entries, Map<String,?> acc = [:]) {
        if (!entries) {
            return acc
        }

        return buildFromMap(entries.tail(), acc + resolveEntry(entries.head()))
    }

    private Map<String, ?> resolveEntry(MapEntryExpression entry) {
        Expression valueX = entry.valueExpression
        ConstantExpression keyX = entry.keyExpression

        return [(keyX.text):resolveEntryValue(valueX)]
    }

    private Object resolveEntryValue(ConstantExpression entry) {
        return entry.value
    }

    private Object resolveEntryValue(ClassExpression entry) {
        return entry.type.typeClass
    }

    private Object resolveEntryValue(MapExpression entry) {
        return buildFromMap(entry.mapEntryExpressions)
    }

    @SuppressWarnings('UnusedPrivateMethodParameter')
    private Object resolveEntryValue(Expression entry) {
        println("WARNING: Carbon entry of type ${entry.class} can't be processed")
        return null
    }

    private Map<String,?> buildFromString(ConstantExpression constX) {
        File configFile = new File(constX.text)

        if (!configFile.exists()) {
            throw new IllegalStateException('Carbon configuration path doesn\'t exist')
        }

        return new Yaml().load(new FileReader(configFile)) as Map
    }
}
