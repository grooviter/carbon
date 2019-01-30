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
 * @since 0.2.0
 */
@TupleConstructor
class PicocliScriptVisitor {
    /**
     * @since 0.2.0
     */
    static final Parameter[] PARAMS = [
        A.NODES
            .param('context')
            .type(ClassHelper.BINDING_TYPE)
            .build(),
    ] as Parameter[]

    MethodNode methodNode

    Map<String,?> carbonConfig

    /**
     * @since 0.2.0
     */
    void visit() {
        ClassNode declaringClass = methodNode.declaringClass

        addBaseClass(declaringClass)
        moveRunMethod(methodNode)
        removeBindingConstructorIfNeccessary(declaringClass)
        addCommandAnnotation(declaringClass, carbonConfig)
    }

    private void addBaseClass(ClassNode classNode) {
        classNode.superClass = A.NODES.clazz(PicocliBaseScript).build()
    }

    private void moveRunMethod(MethodNode methodNode) {
        ClassNode classNode = methodNode
            .declaringClass
        MethodNode runScriptBody = A.NODES
            .method('runScriptBody')
            .code(methodNode.code)
            .returnType(Object)
            .build()

        classNode.addMethod(runScriptBody)
        classNode.removeMethod(methodNode)
    }

    private void removeBindingConstructorIfNeccessary(ClassNode classNode) {
        Boolean hasBindingCtor = classNode
            .superClass
            .getDeclaredConstructor(PARAMS)

        if (!hasBindingCtor) {
            ConstructorNode orphanedConstructor = classNode.getDeclaredConstructor(PARAMS)
            classNode.removeConstructor(orphanedConstructor)
        }
    }

    private void addCommandAnnotation(ClassNode classNode, Map<String,?> config) {
        AnnotationNode commandAnn = new CommandAnnotationBuilder(config).build()

        classNode.addAnnotation(commandAnn)
    }
}
