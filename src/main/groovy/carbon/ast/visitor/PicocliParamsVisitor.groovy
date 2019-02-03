package carbon.ast.visitor

import carbon.ast.visitor.PicocliVisitorUtils as U
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
@SuppressWarnings('DuplicateStringLiteral')
class PicocliParamsVisitor {

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
    static final Map<String, Closure<Void>> DEFAULTS = [
        paramLabel:PicocliParamsVisitor.&defaultParamLabel,
    ]

    /**
     * For every Picocli param found there's a function to process the
     * param's value
     *
     * @since 0.2.0
     */
    static final Map<String, Closure<Void>> OPT_MAPPERS = [
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

    @SuppressWarnings('Indentation')
    void visit() {
        Map<String, ?> parameters = carbonConfig.params as Map<String,?>

        if (!parameters) {
            return
        }

        ClassNode classNode = methodNode.declaringClass

        parameters
            .entrySet()
            .stream()
            .map(PicocliParamsVisitor.&toField)
            .forEach { FieldNode field ->
                A.UTIL.NODE.addGeneratedField(classNode, field)
            }
    }

    private static FieldNode toField(Map.Entry<String,Map<String,?>> entry) {
        FieldNode newField = makeFieldNode(entry)
        AnnotationNodeBuilder builder = A.NODES.annotation(CommandLine.Parameters)

        U.applyX(U.diffByKeys(DEFAULTS, entry.value), builder, entry)
        U.applyX(U.intersectByKeys(OPT_MAPPERS, entry.value), builder, entry)

        newField.addAnnotation(builder.build())

        return newField
    }

    private static FieldNode makeFieldNode(Map.Entry<String,?> entry) {
        Map<String,?> val = entry.value as Map<String,?>
        String optionName = entry.key
        ClassNode optionClass = A.NODES.clazz(val.type as Class).build()

        String simple = optionClass.name
        ClassNode clazzNode = simple.contains(ARRAY_CLASS_PREFIX) ?
            A.NODES.clazz((simple - ARRAY_CLASS_PREFIX) - ';').build().makeArray() :
            A.NODES.clazz(simple).build()

        return A.NODES.field(optionName)
            .modifiers(FieldNode.ACC_PUBLIC)
            .type(clazzNode)
            .build()
    }

    private static void defaultParamLabel(AnnotationNodeBuilder builder, Map.Entry<String,?> entry) {
        String optionName = entry.key

        builder.member('paramLabel', A.EXPR.constX(optionName))
    }
}
