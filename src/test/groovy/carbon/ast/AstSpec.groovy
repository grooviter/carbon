package carbon.ast

import spock.lang.Specification
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.MethodNode
import asteroid.utils.StatementUtils
import carbon.ast.model.Task

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

    void 'Create a task from the groups found'() {
        when: 'creating a task'
        MethodNode runMethodNode = scriptNode.methods.first()
        List<StatementUtils.Group> groups = Sections.getGroupsFromMethod(runMethodNode)
        Task task = Sections.createTaskFrom(groups)

        then: 'we should get the expected meta info'
        verifyAll(task.metaInfo) {
            name == 'sample-script'
            version == '0.0.1'
            author == 'john'
        }

        and: 'we should get a minimal usage information'
        task.usage.description == 'description'
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
