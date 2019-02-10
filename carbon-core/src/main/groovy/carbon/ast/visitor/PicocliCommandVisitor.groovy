package carbon.ast.visitor

import static picocli.CommandLine.Command

import asteroid.A
import asteroid.nodes.AnnotationNodeBuilder
import groovy.transform.CompileStatic
import groovy.transform.TupleConstructor
import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.ast.ClassNode

/**
 * Adds Command annotation to Script {@link ClassNode}
 *
 * @since 0.2.0
 */
@CompileStatic
@TupleConstructor
class PicocliCommandVisitor {

    /**
     * Allowed properties to be used when building the annotation
     *
     * @since 0.2.0
     */
    static final List<String> VALID_PROPERTIES = [
        'header',
        'version',
        'description',
        'footerHeading',
        'footer',
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

        AnnotationNode commandAnnotation = annotationBuilder
                .member('mixinStandardHelpOptions', A.EXPR.constX(true))
                .build()

        classNode.addAnnotation(commandAnnotation)
    }

    private AnnotationNodeBuilder configureNextProperty(AnnotationNodeBuilder acc, Map.Entry<String, ?> entry) {
        return acc.member(entry.key, A.EXPR.constX(entry.value))
    }
}
