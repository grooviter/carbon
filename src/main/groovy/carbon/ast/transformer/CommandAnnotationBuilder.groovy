package carbon.ast.transformer

import static picocli.CommandLine.Command

import asteroid.A
import asteroid.nodes.AnnotationNodeBuilder
import org.codehaus.groovy.ast.AnnotationNode
import groovy.transform.TupleConstructor

/**
 * @since 0.2.0
 */
@TupleConstructor
class CommandAnnotationBuilder {

    /**
     * @since 0.2.0
     */
    static final List<String> VALID_PROPERTIES = [
        'version',
        'description',
    ]

    /**
     * @since 0.2.0
     */
    Map<String,?> config

    /**
     * @return
     * @since 0.2.0
     */
    AnnotationNode build() {
        Map<String, ?> propsToApply = config.subMap(VALID_PROPERTIES)
        AnnotationNodeBuilder annotationBuilder = propsToApply.inject(
            A.NODES.annotation(Command),
            this.&configureNextProperty
        )

        return annotationBuilder.build()
    }

    private AnnotationNodeBuilder configureNextProperty(AnnotationNodeBuilder acc, Map.Entry<String, ?> entry) {
        return acc.member(entry.key, A.EXPR.constX(entry.value))
    }
}
