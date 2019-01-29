package carbon.ast.transformer

import groovy.transform.TupleConstructor
import asteroid.A
import asteroid.Criterias
import asteroid.nodes.AnnotationNodeBuilder
import picocli.CommandLine
import picocli.groovy.PicocliScript
import picocli.groovy.PicocliBaseScript
import asteroid.transformer.AbstractMethodNodeTransformer
import groovy.transform.Field
import groovy.transform.Generated
import groovy.transform.CompileStatic
import org.codehaus.groovy.ast.Parameter
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.FieldNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.ast.ConstructorNode
import org.codehaus.groovy.ast.ClassHelper
import org.codehaus.groovy.ast.expr.Expression
import org.codehaus.groovy.ast.expr.ListExpression
import org.codehaus.groovy.ast.expr.DeclarationExpression
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.classgen.VariableScopeVisitor

/**
 * @since 0.2.0
 */
@TupleConstructor
class PicocliOptsBuilder {

    /**
     * @since 0.2.0
     */
    static final Map<String, ?> OPT_MAPPERS = [
        required: PicocliOptsBuilder.&required,
    ]

    /**
     * @since 0.2.0
     */
    static final List<Closure<Void>> DEFAULTS = [
        PicocliOptsBuilder.&defaultNames,
    ]

    /**
     * @since 0.2.0
     */
    MethodNode methodNode

    /**
     * @since 0.2.0
     */
    Map<String,?> carbonConfig

    /**
     * @since 0.2.0
     */
    void build() {
        Map<String, ?> options = carbonConfig.options as Map<String,?>

        options
            .entrySet()
            .stream()
            .map(this.&toOptField)
            .forEach({ FieldNode field ->
                methodNode.declaringClass.addField(field)
            })
    }

    private FieldNode toOptField(Map.Entry<String,?> entry) {
        FieldNode newField = createFieldNode(entry)
        AnnotationNodeBuilder builder = A.NODES.annotation(CommandLine.Option)

        DEFAULTS.each { d ->
            d.call(builder, entry)
        }

        entry.value.entrySet().each { e ->
            OPT_MAPPERS[e.key]?.call(builder, entry)
        }

        newField.addAnnotation(builder.build())
        newField.addAnnotation(generatedAnnotation)
        newField.synthetic = true

        return newField
    }

    private static void defaultNames(AnnotationNodeBuilder builder, Map.Entry<String,?> entry) {
        String optionName = entry.key.toString()
        ListExpression optionNamesX = A.EXPR.listX(
            A.EXPR.constX("--$optionName".toString()),
            A.EXPR.constX("-${optionName[0]}".toString())
        )

        builder.member('names', optionNamesX)
    }

    private static void required(AnnotationNodeBuilder builder, Map.Entry<String,?> entry) {
        Map<String,?> val = entry.value as Map<String,?>

        if (val.required) {
            builder.member('required', A.EXPR.constX(val.required))
        }
    }

    private FieldNode createFieldNode(Map.Entry<String,?> entry) {
        Map<String,?> val = entry.value as Map<String,?>
        String optionName = entry.key.toString()
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

    private AnnotationNode getGeneratedAnnotation() {
        return A.NODES.annotation(Generated).build()
    }
}
