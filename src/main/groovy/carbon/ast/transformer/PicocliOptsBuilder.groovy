package carbon.ast.transformer

import carbon.ast.transformer.PicocliBuilderUtils as U
import asteroid.A
import asteroid.nodes.AnnotationNodeBuilder
import picocli.CommandLine
import groovy.transform.CompileStatic
import groovy.transform.TupleConstructor
import org.codehaus.groovy.ast.FieldNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.expr.Expression
import org.codehaus.groovy.ast.expr.ListExpression

/**
 * Builds Picocli's {@link CommandLine.Option} annotations and fields
 * from the Carbon's configuration found in the script.
 *
 * @since 0.2.0
 */
@CompileStatic
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
        required:U.extractValue('required'),
        description:U.extractValue('description'),
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
            .map(PicocliOptsBuilder.&toField)
            .forEach { FieldNode field ->
                methodNode.declaringClass.addField(field)
            }
    }

    private static FieldNode toField(Map.Entry<String,Map<String,?>> entry) {
        FieldNode newField = createFieldNode(entry)
        AnnotationNodeBuilder builder = A.NODES.annotation(CommandLine.Option)

        U.applyX(U.diffByKeys(DEFAULTS, entry.value), builder, entry)
        U.applyX(U.intersectByKeys(OPT_MAPPERS, entry.value), builder, entry)

        newField.addAnnotation(builder.build())
        newField.addAnnotation(U.generatedAnnotation)
        newField.synthetic = true

        return newField
    }

    private static FieldNode createFieldNode(Map.Entry<String,?> entry) {
        Map<String,?> val = entry.value as Map<String,?>
        String optionName = entry.key
        Class clazz = val.type as Class
        Expression initialX = A.EXPR.constX(val.defaultValue)

        return new FieldNode(
            optionName,
            FieldNode.ACC_PUBLIC,
            A.NODES.clazz(clazz).build(),
            null,
            initialX
        )
    }

    private static void defaultNames(AnnotationNodeBuilder builder, Map.Entry<String,?> entry) {
        String optionName = entry.key
        ListExpression optionNamesX = A.EXPR.listX(
            A.EXPR.constX("--$optionName".toString()),
            A.EXPR.constX("-${optionName[0]}".toString())
        )

        builder.member('names', optionNamesX)
    }
}
