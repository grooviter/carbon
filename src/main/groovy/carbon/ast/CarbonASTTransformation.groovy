package carbon.ast

import static asteroid.Phase.GLOBAL

import groovy.transform.CompileStatic

import asteroid.Phase
import asteroid.AbstractGlobalTransformation
import asteroid.transformer.Transformer

@CompileStatic
@Phase(GLOBAL.SEMANTIC_ANALYSIS)
class CarbonASTTransformation extends AbstractGlobalTransformation {
    @Override
    List<Class<Transformer>> getTransformers() {
        return [CliBuilderTransformer]
    }
}
