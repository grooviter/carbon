package carbon.spec

import carbon.ast.CarbonASTTransformation
import spock.lang.Specification
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.tools.ast.TransformTestHelper

/**
 * Utility class to evaluate script transformation when applying
 * Carbon's AST transformer
 *
 * @since 0.1.0
 */
class CarbonSpec extends Specification {

    /**
     * @param script
     * @return
     * @since 0.1.0
     */
    public <T> T evaluateScript(String script) {
        return new TransformTestHelper(
            CarbonASTTransformation.newInstance(),
            CompilePhase.SEMANTIC_ANALYSIS
        ).parse(script)
    }
}
