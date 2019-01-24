package carbon.ast

import groovy.transform.CompileStatic

import asteroid.AbstractGlobalTransformation
import asteroid.Phase
import asteroid.transformer.Transformer
import org.codehaus.groovy.control.CompilePhase

/**
 * @since 0.1.0
 */
@CompileStatic
@Phase(CompilePhase.CANONICALIZATION)
class CarbonASTTransformation extends AbstractGlobalTransformation {
    @Override
    List<Class<Transformer>> getTransformers() {
        return [carbon.ast.Transformer]
    }
}
