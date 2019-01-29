package carbon.ast.transformer

import asteroid.A
import picocli.CommandLine
import groovy.transform.Generated
import groovy.transform.TupleConstructor
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.FieldNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.AnnotationNode

/**
 * @since 0.2.0
 */
@TupleConstructor
class PicocliParamsBuilder {

    /**
     * @since 0.2.0
     */
    static final String ARRAY_CLASS_PREFIX = '[L'

    /**
     * @since 0.2.0
     */
    MethodNode methodNode

    /**
     * @since 0.2.0
     */
    Map<String,?> carbonConfig

    @SuppressWarnings('BuilderMethodWithSideEffects')
    void build() {
        Map<String, ?> parameters = carbonConfig.params as Map<String,?>

        parameters
            .entrySet()
            .stream()
            .map(this.&toParamField)
            .forEach { FieldNode field -> methodNode.declaringClass.addField(field) }
    }

    private FieldNode toParamField(Map.Entry<String,?> entry) {
        Map<String,?> val = entry.value as Map<String,?>
        String optionName = entry.key
        ClassNode optionClass = A.NODES.clazz(val.type).build()

        String simple = optionClass.name
        ClassNode clazzNode = simple.contains(ARRAY_CLASS_PREFIX) ?
            A.NODES.clazz((simple - ARRAY_CLASS_PREFIX) - ';').build().makeArray() :
            A.NODES.clazz(simple).build()

        FieldNode newField = new FieldNode(
            optionName,
            FieldNode.ACC_PUBLIC,
            clazzNode,
            null,
            null
        )

        AnnotationNode optionAnnotation = A.NODES
            .annotation(CommandLine.Parameters)
            .member('paramLabel', A.EXPR.constX(optionName))
            .build()
        AnnotationNode generatedAnnotation = A.NODES.annotation(Generated).build()

        newField.addAnnotation(optionAnnotation)
        newField.addAnnotation(generatedAnnotation)
        newField.synthetic = true

        return newField
    }
}
