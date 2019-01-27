package carbon.ast.transformer

import asteroid.Criterias
import asteroid.transformer.AbstractMethodNodeTransformer
import groovy.transform.CompileStatic
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.expr.Expression
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

    /**
     * Default constructor
     *
     * @param sourceUnit in case we need to interact with compiler
     * @since 0.1.0
     */
    MethodTransformer(final SourceUnit sourceUnit) {
        super(sourceUnit, Criterias.byMethodNodeName('run'))
    }

    @Override
    void transformMethod(final MethodNode methodNode) {
        Expression carbonExpression = new CarbonExpressionSeeker(this.sourceUnit)
            .find(methodNode)
        Map<String,?> carbonConfiguration = new ConfigurationBuilder()
            .build(carbonExpression)

        println "===>${carbonConfiguration}"
        // BlockStatement newScriptCode = buildCarbonCode(methodNode, carbonConfiguration)
        // methodNode.setCode(newScriptCode)
        // visitAndResetVariableScopes(methodNode)
    }

    /**
     * @param methodNode
     * @since 0.2.0
     */
    void visitAndResetVariableScopes(MethodNode methodNode) {
        VariableScopeVisitor scopeVisitor = new VariableScopeVisitor(this.sourceUnit)
        scopeVisitor.visitClass(methodNode.declaringClass)
    }

    /**
     * With the information gathered in the configuration we'll be
     * creating the cli api
     *
     * @param methodNode current script run method
     * @param carbonConfiguration configuration gathered from carbon variable
     * @return a {@link BlockStatement} with final script code
     * @since 0.2.0
     */
    BlockStatement buildCarbonCode(MethodNode methodNode, Map<String,?> carbonConfiguration) {
        return null
    }
}
