package carbon.ast.transformer

import carbon.ast.transformer.PicocliVisitorUtils as U
import asteroid.A
import asteroid.nodes.AnnotationNodeBuilder
import org.codehaus.groovy.ast.expr.ConstantExpression
import picocli.CommandLine
import groovy.transform.CompileStatic
import groovy.transform.TupleConstructor
import org.codehaus.groovy.ast.FieldNode
import org.codehaus.groovy.ast.ClassNode
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
class PicocliOptsVisitor {

    /**
     * Even before processing any option property found, Carbon could
     * provide sane defaults for these properties.
     *
     * @since 0.2.0
     */
    static final Map<String, Closure> DEFAULTS = [
        names:PicocliOptsVisitor.&defaultNames,
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
        usageHelp:U.extractValue('usageHelp'),
        versionHelp:U.extractValue('versionHelp'),
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
    @SuppressWarnings('Indentation')
    void visit() {
        Map<String, ?> options = carbonConfig.options as Map<String,?>

        if (!options) {
            return
        }

        ClassNode classNode = methodNode.declaringClass

        options
            .entrySet()
            .stream()
            .map(PicocliOptsVisitor.&toField)
            .forEach { FieldNode field ->
                A.UTIL.NODE.addGeneratedField(classNode, field)
            }
    }

    private static FieldNode toField(Map.Entry<String,Map<String,?>> entry) {
        FieldNode newField = makeFieldNode(entry)
        AnnotationNodeBuilder builder = A.NODES.annotation(CommandLine.Option)

        U.applyX(U.diffByKeys(DEFAULTS, entry.value), builder, entry)
        U.applyX(U.intersectByKeys(OPT_MAPPERS, entry.value), builder, entry)

        newField.addAnnotation(builder.build())

        return newField
    }

    private static FieldNode makeFieldNode(Map.Entry<String,?> entry) {
        Map<String,?> val = entry.value as Map<String,?>
        String optionName = entry.key
        Class clazz = val.type as Class
        Expression initialX = A.EXPR.constX(val.defaultValue)

        return A.NODES
            .field(optionName)
            .modifiers(FieldNode.ACC_PUBLIC)
            .type(clazz)
            .expression(initialX)
            .build()
    }

    private static void defaultNames(AnnotationNodeBuilder builder, Map.Entry<String,?> entry) {
        String optionName = entry.key
        ConstantExpression longOpt = A.EXPR.constX("--$optionName".toString())
        ConstantExpression shortOpt = A.EXPR.constX("-${optionName[0]}".toString())
        ListExpression optionNamesX = A.EXPR.listX(longOpt, shortOpt)

        builder.member('names', optionNamesX)
    }
}
