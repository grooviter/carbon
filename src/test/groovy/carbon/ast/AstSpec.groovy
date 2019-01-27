package carbon.ast

import spock.lang.Specification
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.MethodNode
import asteroid.utils.StatementUtils

/**
 * Checking how AST transformation works
 *
 * @since 0.1.0
 */
class AstSpec extends Specification {

    void 'Getting script groups'() {
        when: 'parsing the body of the method'
        MethodNode runMethodNode = scriptNode.methods.first()
        List<StatementUtils.Group> groups = Sections.getGroupsFromMethod(runMethodNode)

        then: 'we should get 4 groups'
        groups.size() == 5

        and: 'the names should match'
        groups.label.name == ['name', 'version', 'description', 'author', 'script']
    }

    @SuppressWarnings(['Indentation', 'Println', 'MethodReturnTypeRequired', 'ClassName'])
    ClassNode getScriptNode() {
        return new MacroClass() {
            class Script {
                def run() {
                    name: 'sample-script'
                    version: '0.0.1'
                    description: 'description'
                    author: 'john'
                    script:

                    println 'hello world'
                }
            }
        }
    }
}
