package carbon.ast.transformer

import asteroid.A
import asteroid.nodes.AnnotationNodeBuilder
import picocli.CommandLine
import groovy.transform.Generated
import groovy.transform.TupleConstructor
import org.codehaus.groovy.ast.FieldNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.ast.expr.Expression
import org.codehaus.groovy.ast.expr.ListExpression

/**
 * Builds Picocli options from the Carbon's configuration found in the
 * script.
 *
 * @since 0.2.0
 */
@TupleConstructor
@SuppressWarnings('FactoryMethodName')
class PicocliOptsBuilder {

    /**
     * Even before processing any option property found, Carbon could
     * provide sane defaults for these properties.
     *
     * @since 0.2.0
     */
    static final Map<String, Closure> DEFAULTS = [
        names:PicocliOptsBuilder.&defaultNames,
    ]

    /**
     * For every Picocli option property found there's a function to
     * process the property's value
     *
     * @since 0.2.0
     */
    static final Map<String, Closure> OPT_MAPPERS = [
        required:PicocliOptsBuilder.extractValue('required'),
        description:PicocliOptsBuilder.extractValue('description'),
    ]

    /**
     * Method containing script code
     *
     * @since 0.2.0
     */
    MethodNode methodNode

    /**
     * Carbon configuration
     *
     * @since 0.2.0
     */
    Map<String,?> carbonConfig

    /**
     * @since 0.2.0
     */
    @SuppressWarnings(['BuilderMethodWithSideEffects', 'Indentation'])
    void build() {
        Map<String, ?> options = carbonConfig.options as Map<String,?>

        options
            .entrySet()
            .stream()
            .map(PicocliOptsBuilder.&toOptField)
            .forEach { FieldNode field ->
                methodNode.declaringClass.addField(field)
            }
    }

    private static void defaultNames(AnnotationNodeBuilder builder, Map.Entry<String,?> entry) {
        String optionName = entry.key
        ListExpression optionNamesX = A.EXPR.listX(
            A.EXPR.constX("--$optionName".toString()),
            A.EXPR.constX("-${optionName[0]}".toString())
        )

        builder.member('names', optionNamesX)
    }

    private static Closure extractValue(String property) {
        return { AnnotationNodeBuilder builder, Map.Entry<String,?> entry ->
            Map<String,?> val = entry.value as Map<String,?>
            Object propertyValue = val[property]

            if (propertyValue) {
                builder.member(property, A.EXPR.constX(propertyValue))
            }
        }
    }

    private static FieldNode toOptField(Map.Entry<String,?> entry) {
        FieldNode newField = createFieldNode(entry)
        AnnotationNodeBuilder builder = A.NODES.annotation(CommandLine.Option)

        Map<String,?> values = entry.value
        Set<String> defaults = diffKeys(DEFAULTS, values)
        Set<String> processors = intersectKeys(OPT_MAPPERS, values)

        defaults.each { String key ->
            Closure func = DEFAULTS[key]

            func.call(builder, entry)
        }

        processors.each { String key ->
            Closure func = OPT_MAPPERS[key]

            func.call(builder, entry)
        }

        newField.addAnnotation(builder.build())
        newField.addAnnotation(generatedAnnotation)
        newField.synthetic = true

        return newField
    }

    private static Set<String> diffKeys(Map<String,?> left, Map<String,?> right) {
        return left.keySet() - right.keySet()
    }

    private static Set<String> intersectKeys(Map<String,?> left, Map<String,?> right) {
        return left.keySet().intersect(right.keySet())
    }

    private static FieldNode createFieldNode(Map.Entry<String,?> entry) {
        Map<String,?> val = entry.value as Map<String,?>
        String optionName = entry.key
        Class clazz = val.type as Class
        Expression initialX = A.EXPR.constX(val.defaultValue) ?: null

        return new FieldNode(
            optionName,
            FieldNode.ACC_PUBLIC,
            A.NODES.clazz(clazz).build(),
            null,
            initialX
        )
    }

    private static AnnotationNode getGeneratedAnnotation() {
        return A.NODES.annotation(Generated).build()
    }
}
