package carbon.spec

import carbon.ast.CarbonASTTransformation
import spock.lang.Specification
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.tools.ast.TransformTestHelper

class CarbonSpec extends Specification {

    public <T> T evaluateScript(String script) {
        return new TransformTestHelper(
            CarbonASTTransformation.newInstance(),
            CompilePhase.CANONICALIZATION
        ).parse(script)
    }
}
