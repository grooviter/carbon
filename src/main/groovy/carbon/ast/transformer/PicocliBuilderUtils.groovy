package carbon.ast.transformer

import asteroid.A
import asteroid.nodes.AnnotationNodeBuilder
import groovy.transform.Generated
import groovy.transform.CompileStatic
import org.codehaus.groovy.ast.AnnotationNode

/**
 * Utility functions used in several builder classes
 *
 * @since 0.2.0
 */
@CompileStatic
class PicocliBuilderUtils {

    /**
     * Apply processors to the entry (option or param) properties in
     * order to populate an annotation via the {@link
     * AnnotationBuilder}.
     *
     * Processors are stored under the property's name they want to
     * process.
     *
     * @param processors map containing processors to apply to properties
     * @param builder {@link AnnotationNodeBuilder} to populate the
     * annotation members
     * @param entry {@link Map.Entry} of a given option/param. The
     * value of the entry is every potential property of the
     * annotation
     * @since 0.2.0
     */
    static void applyX(Map<String, Closure> processors,
                       AnnotationNodeBuilder builder,
                       Map.Entry<String,?> entry) {
        processors.each { Map.Entry<String,Closure> processor ->
            Closure func = processor.value

            func.call(builder, entry)
        }
    }

    /**
     * Returns a function that will apply a given option/param property
     * to the {@link AnnotationNodeBuilder} used to create the final
     * annotation
     *
     * @param property property to apply to the annotation
     * @return a {@link Closure} used to apply the property to an
     * {@link AnnotationNodeBuilder}
     * @since 0.2.0
     */
    static Closure extractValue(String property) {
        return { AnnotationNodeBuilder builder, Map.Entry<String,?> entry ->
            Map<String,?> val = entry.value as Map<String,?>
            Object propertyValue = val[property]

            if (propertyValue) {
                builder.member(property, A.EXPR.constX(propertyValue))
            }
        }
    }

    /**
     * Given two maps, it returns the entries from the left map not
     * found in the right map. It compares entries only by keys.
     *
     * @param left map to get entries from
     * @param right map to compare the left map
     * @return a submap of the left map containing entries not found in right map
     * @since 0.2.0
     */
    static <T> Map<String,T> diffByKeys(Map<String,T> left, Map<String,?> right) {
        Set<String> keys = left.keySet() - right.keySet()

        return left.subMap(keys) as Map<String,T>
    }

    /**
     * Given two maps, it returns the entries from the left map found
     * in the right map (intersection). It compares entries only by
     * keys.
     *
     * @param left map to get entries from
     * @param right map to compare the left map
     * @return a submap of the left map containing entries found in right map
     * @since 0.2.0
     */
    static <T> Map<String,T> intersectByKeys(Map<String,T> left, Map<String,?> right) {
        Set<String> keys = left.keySet().intersect(right.keySet())

        return left.subMap(keys) as Map<String,T>
    }

    /**
     * Creates an annotation of type {@link Generated}. This
     * annotation helps coverage tools to avoid assessing generated
     * code.
     *
     * @return an {@link AnnotationNode} of type {@link Generated}
     * @since 0.2.0
     */
    static AnnotationNode getGeneratedAnnotation() {
        return A.NODES.annotation(Generated).build()
    }
}
