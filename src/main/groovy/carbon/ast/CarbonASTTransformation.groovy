package carbon.ast

import groovy.transform.CompileStatic

import asteroid.AbstractGlobalTransformation
import asteroid.Phase
import asteroid.transformer.Transformer
import org.codehaus.groovy.control.CompilePhase

@CompileStatic
@Phase(CompilePhase.SEMANTIC_ANALYSIS)
class CarbonASTTransformation extends AbstractGlobalTransformation {
    @Override
    List<Class<Transformer>> getTransformers() {
        return [CliBuilderTransformer] as List<Class<Transformer>>
    }
}
