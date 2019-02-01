package carbon.ast.transformer

import asteroid.A
import picocli.groovy.PicocliBaseScript
import groovy.transform.TupleConstructor
import org.codehaus.groovy.ast.ClassHelper
import org.codehaus.groovy.ast.Parameter
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.ConstructorNode
import org.codehaus.groovy.ast.AnnotationNode

/**
 * Executes a series of tasks over the script class.
 *
 * <ul>
 *   <li>Makes the Script to extend {@link PicocliBaseScript}</li>
 *   <li>Moves the Script's run method to runScriptBody method</li>
 *   <li>Removes the constructor with the binding context if super class doesn't have it</li>
 *   <li>Adds Command annotation to Script</li>
 * </ul>
 *
 * @since 0.2.0
 */
@TupleConstructor
class PicocliScriptVisitor {

    /**
     *
     * @since 0.2.0
     */
    ClassNode classNode

    /**
     * Carbon configuration
     *
     * @since 0.2.0
     */
    Map<String,?> carbonConfig

    /**
     * Applies tasks to the Script's code
     *
     * @since 0.2.0
     */
    void visit() {
        // Make script extend PicocliBaseScript
        addBaseClass()

        // Move run method to runScriptBody
        moveRunMethod()

        // Remove binding constructor if necesssary
        removeBindingConstructorIfNeccessary()

        // Add @Command annotation to script class
        addCommandAnnotation()
    }

    private void addBaseClass() {
        classNode.superClass = A.NODES.clazz(PicocliBaseScript).build()
    }

    private void moveRunMethod() {
        MethodNode methodNode = classNode.getMethod('run')
        MethodNode runScriptBody = A.NODES
            .method('runScriptBody')
            .code(methodNode.code)
            .returnType(Object)
            .build()

        classNode.addMethod(runScriptBody)
        classNode.removeMethod(methodNode)
    }

    private void removeBindingConstructorIfNeccessary() {
        Parameter[] parameter = [A.NODES.param('context').type(ClassHelper.BINDING_TYPE).build()]
        Boolean hasBindingCtor = classNode
            .superClass
            .getDeclaredConstructor(parameter)

        if (!hasBindingCtor) {
            ConstructorNode orphanedConstructor = classNode.getDeclaredConstructor(parameter)
            classNode.removeConstructor(orphanedConstructor)
        }
    }

    private void addCommandAnnotation() {
        AnnotationNode commandAnn = new CommandAnnotationBuilder(carbonConfig).build()

        classNode.addAnnotation(commandAnn)
    }
}
