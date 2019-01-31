package carbon.ast.transformer

import org.codehaus.groovy.ast.expr.Expression
import org.codehaus.groovy.ast.expr.ClassExpression
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codehaus.groovy.ast.expr.MapExpression
import org.codehaus.groovy.ast.expr.MapEntryExpression
import groovy.transform.TailRecursive
import groovy.transform.TupleConstructor
import groovy.util.logging.Log

/**
 * Builds Carbon's configuration from the Carbon's variable found in
 * the Groovy script
 *
 * @since 0.2.0
 */
@Log
@TupleConstructor
@SuppressWarnings('UnusedPrivateMethodParameter')
class ConfigurationBuilder {

    static final String KEY_CONFIGURATION = 'configuration'

    /**
     * {@link Expression} containing the Carbon configuration
     *
     * @since 0.2.0
     */
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
                return buildFromMap(expr)
            case ConstantExpression:
                return buildFromString(expr)

            default:
                return [:] // config should be only either a map or a string
        }
    }

    private Map<String, ?> buildFromMap(MapExpression mapExpression) {
        List<MapEntryExpression> mapEntryXList = mapExpression.mapEntryExpressions
        Map<String,?> config = buildFromMap(mapEntryXList)

        if (config.configuration) {
            Map<String, ?> merged = config.configuration + config
            merged.remove(KEY_CONFIGURATION)

            return merged
        }

        return config
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
        String key = keyX.text

        return [(key):resolveEntryValue(key, valueX)]
    }

    private Object resolveEntryValue(String key, ConstantExpression entry) {
        if (key == KEY_CONFIGURATION) {
            return buildFromString(entry)
        }

        return entry.value
    }

    private Object resolveEntryValue(String key, ClassExpression entry) {
        return entry.type.typeClass
    }

    private Object resolveEntryValue(String key, MapExpression entry) {
        return buildFromMap(entry.mapEntryExpressions)
    }

    private Object resolveEntryValue(String key, Expression entry) {
        log.info "WARNING: Carbon entry of type ${entry.class} can't be processed"
        return null
    }

    private Map<String,?> buildFromString(ConstantExpression constX) {
        File configFile = new File(constX.text)

        if (!configFile.exists()) {
            throw new IllegalStateException('Carbon configuration path doesn\'t exist')
        }

        return new ConfigSlurper().parse(configFile.toURL())
    }
}
