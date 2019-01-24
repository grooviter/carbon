package carbon.spec

import carbon.ast.CarbonASTTransformation
import spock.lang.Specification
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.tools.ast.TransformTestHelper

/**
 * Utility class to evaluate script transformation when applying
 * Carbon's AST transformer
 */
class CarbonSpec extends Specification {

    public <T> T evaluateScript(String script) {
        return new TransformTestHelper(
            CarbonASTTransformation.newInstance(),
            CompilePhase.CANONICALIZATION
        ).parse(script)
    }
}
