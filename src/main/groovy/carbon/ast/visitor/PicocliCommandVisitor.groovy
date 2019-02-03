package carbon.ast.visitor

import asteroid.A
import asteroid.nodes.AnnotationNodeBuilder
import groovy.transform.TupleConstructor
import org.codehaus.groovy.ast.ClassNode

import static picocli.CommandLine.Command

/**
 * Builds a {@link Command} annotation
 *
 * @since 0.2.0
 */
@TupleConstructor
class PicocliCommandVisitor {

    /**
     * Allowed properties to be used when building the annotation
     *
     * @since 0.2.0
     */
    static final List<String> VALID_PROPERTIES = [
        'version',
        'description',
    ]

    /**
     * @since 0.2.0
     */
    ClassNode classNode

    /**
     * Carbon configuration
     *
     * @since 0.2.0
     */
    Map<String,?> config

    /**
     *
     * @since 0.2.0
     */
    void visit() {
        Map<String, ?> propsToApply = config.subMap(VALID_PROPERTIES)
        AnnotationNodeBuilder annotationBuilder = propsToApply.inject(
            A.NODES.annotation(Command),
            this.&configureNextProperty
        )

        classNode.addAnnotation(annotationBuilder.build())
    }

    private AnnotationNodeBuilder configureNextProperty(AnnotationNodeBuilder acc, Map.Entry<String, ?> entry) {
        return acc.member(entry.key, A.EXPR.constX(entry.value))
    }
}
