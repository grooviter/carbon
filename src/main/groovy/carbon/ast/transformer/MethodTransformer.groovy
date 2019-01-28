package carbon.ast.transformer

import asteroid.A
import asteroid.Criterias
import asteroid.transformer.AbstractMethodNodeTransformer
import groovy.transform.CompileStatic
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.expr.Expression
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

        if (!carbonConfig.options) {
            return
        }

        // Picocli opts
        new PicocliOptsBuilder(methodNode, carbonConfig).build()

        // Picocli params
        new PicocliParamsBuilder(methodNode, carbonConfig).build()

        // Picocli script
        new PicocliScriptBuilder(methodNode, carbonConfig).build()

        // Cleaning up
        visitAndResetVariableScopes(methodNode, this.sourceUnit)
    }

    /**
     * Reviews and fixes variable scopes along the changed code. This
     * is specially necessary when resolving variables in new added
     * closures.
     *
     * @param methodNode method to get its {@link ClassNode} from
     * @param sourceUnit
     * @since 0.2.0
     */
    private void visitAndResetVariableScopes(MethodNode methodNode, SourceUnit sourceUnit) {
        VariableScopeVisitor scopeVisitor = new VariableScopeVisitor(sourceUnit)

        scopeVisitor.visitClass(methodNode.declaringClass)
    }
}
