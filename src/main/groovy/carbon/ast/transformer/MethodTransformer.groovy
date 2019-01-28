package carbon.ast.transformer

import asteroid.A
import asteroid.Criterias
import picocli.CommandLine
import picocli.groovy.PicocliScript
import picocli.groovy.PicocliBaseScript
import asteroid.transformer.AbstractMethodNodeTransformer
import groovy.transform.Field
import groovy.transform.Generated
import groovy.transform.CompileStatic
import org.codehaus.groovy.ast.Parameter
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.FieldNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.ast.ConstructorNode
import org.codehaus.groovy.ast.ClassHelper
import org.codehaus.groovy.ast.expr.Expression
import org.codehaus.groovy.ast.expr.ListExpression
import org.codehaus.groovy.ast.expr.DeclarationExpression
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.classgen.VariableScopeVisitor

/**
 * Transforms the main method in a script, which is the "run" method
 * to convert labelled statements into a CliBuilder aware script.
 *
 * @since 0.1.0
 */
@CompileStatic
class MethodTransformer extends AbstractMethodNodeTransformer {

    static final Parameter[] params = [
        A.NODES
            .param('context')
            .type(ClassHelper.BINDING_TYPE)
            .build()
    ] as Parameter[]

    /**
     * The transformation will only affect to nodes of type {@link
     * MethodNode} with name 'run'
     *
     * @param sourceUnit in case we need to interact with compiler
     * @since 0.1.0
     */
    MethodTransformer(SourceUnit sourceUnit) {
        super(sourceUnit, Criterias.byMethodNodeName('run'))
    }

    @Override
    void transformMethod(MethodNode methodNode) {
        // Carbon's configuration
        Expression carbonX = new ExpressionFinder(this.sourceUnit).find(methodNode)
        Map<String,?> carbonConfig = new ConfigurationBuilder(carbonX).build()
        ClassNode declaringClass = methodNode.declaringClass

        if (!carbonConfig.options) {
            return
        }

        // Picocli opts
        new PicocliOptsBuilder(methodNode, carbonConfig).build()

        // Picocli params
        new PicocliParamsBuilder(methodNode, carbonConfig).build()

        // Picocli script
        addBaseClass(declaringClass)
        moveRunMethod(methodNode)
        removeBindingConstructorIfNeccessary(declaringClass)

        // Picocli command annotation
        addCommandAnnotation(declaringClass, carbonConfig)

        // Cleaning up
        // visitAndResetVariableScopes(declaringClass, this.sourceUnit)
    }

    void addBaseClass(ClassNode classNode) {
        classNode.superClass = A.NODES.clazz(PicocliBaseScript).build()
    }

    void moveRunMethod(MethodNode methodNode) {
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

    void removeBindingConstructorIfNeccessary(ClassNode classNode) {
        Boolean hasBindingCtor = classNode
            .superClass
            .getDeclaredConstructor(params)

        if (!hasBindingCtor) {
            ConstructorNode orphanedConstructor = classNode.getDeclaredConstructor(params)
            classNode.removeConstructor(orphanedConstructor)
        }
    }

    /**
     * This method adds the annotation and the {@link Command}
     * annotation to the script
     *
     * @param classNode {@link ClassNode} to add the annotations to
     * @param carbonConfig Carbon's configuration
     * @since 0.2.0
     */
    void addCommandAnnotation(ClassNode classNode, Map<String,?> config) {
        AnnotationNode commandAnn = new CommandAnnotationBuilder(config).build()

        classNode.addAnnotation(commandAnn)
    }

    /**
     * Reviews and fixes variable scopes along the changed code. This
     * is specially necessary when resolving variables in new added
     * closures.
     *
     * @param scriptClass {@link ClassNode} to review and fix
     * @param sourceUnit
     * @since 0.2.0
     */
    void visitAndResetVariableScopes(ClassNode scriptClass, SourceUnit sourceUnit) {
        VariableScopeVisitor scopeVisitor = new VariableScopeVisitor(sourceUnit)
        scopeVisitor.visitClass(scriptClass)
    }
}
