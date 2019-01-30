package carbon.ast.transformer

import carbon.ast.transformer.PicocliBuilderUtils as U
import asteroid.A
import asteroid.nodes.AnnotationNodeBuilder
import picocli.CommandLine
import groovy.transform.CompileStatic
import groovy.transform.TupleConstructor
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.FieldNode
import org.codehaus.groovy.ast.MethodNode

/**
 * Builds Picocli's {@link CommandLine.Parameters} annotations and
 * fields from the Carbon's configuration found in the script.
 *
 * @since 0.2.0
 */
@CompileStatic
@TupleConstructor
@SuppressWarnings(['FactoryMethodName', 'DuplicateStringLiteral'])
class PicocliParamsBuilder {

    /**
     * @since 0.2.0
     */
    static final String ARRAY_CLASS_PREFIX = '[L'

    /**
     * Even before processing any param property found, Carbon could
     * provide sane defaults for these properties.
     *
     * @since 0.2.0
     */
    static final Map<String, Closure> DEFAULTS = [
        paramLabel:PicocliParamsBuilder.&defaultParamLabel,
    ]

    /**
     * For every Picocli param found there's a function to process the
     * param's value
     *
     * @since 0.2.0
     */
    static final Map<String, Closure> OPT_MAPPERS = [
        paramLabel:U.extractValue('paramLabel'),
    ]

    /**
     * @since 0.2.0
     */
    MethodNode methodNode

    /**
     * @since 0.2.0
     */
    Map<String,?> carbonConfig

    @SuppressWarnings(['BuilderMethodWithSideEffects', 'Indentation'])
    void build() {
        Map<String, ?> parameters = carbonConfig.params as Map<String,?>

        parameters
            .entrySet()
            .stream()
            .map(PicocliParamsBuilder.&toField)
            .forEach { FieldNode field ->
                methodNode.declaringClass.addField(field)
            }
    }

    private static FieldNode toField(Map.Entry<String,Map<String,?>> entry) {
        FieldNode newField = createFieldNode(entry)
        AnnotationNodeBuilder builder = A.NODES.annotation(CommandLine.Parameters)

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
        ClassNode optionClass = A.NODES.clazz(val.type as Class).build()

        String simple = optionClass.name
        ClassNode clazzNode = simple.contains(ARRAY_CLASS_PREFIX) ?
            A.NODES.clazz((simple - ARRAY_CLASS_PREFIX) - ';').build().makeArray() :
            A.NODES.clazz(simple).build()

        return new FieldNode(
            optionName,
            FieldNode.ACC_PUBLIC,
            clazzNode,
            null,
            null
        )
    }

    private static void defaultParamLabel(AnnotationNodeBuilder builder, Map.Entry<String,?> entry) {
        String optionName = entry.key

        builder.member('paramLabel', A.EXPR.constX(optionName))
    }
}
