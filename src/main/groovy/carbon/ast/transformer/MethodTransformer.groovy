package carbon.ast

import asteroid.A
import asteroid.Criterias
import asteroid.Expressions
import asteroid.transformer.AbstractMethodNodeTransformer
import asteroid.utils.StatementUtils
import carbon.ast.model.Argument
import carbon.ast.model.Task
import groovy.transform.CompileStatic
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.Variable
import org.codehaus.groovy.ast.VariableScope
import org.codehaus.groovy.ast.ClassCodeExpressionTransformer
import org.codehaus.groovy.ast.expr.*
import org.codehaus.groovy.ast.stmt.Statement
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.classgen.VariableScopeVisitor
import org.codehaus.groovy.syntax.Types
import org.codehaus.groovy.macro.matcher.ASTMatcher
import static asteroid.Statements.blockS

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
        Expression carbonExpression = findCarbonExpressionValue(methodNode)
        Map<String,?> carbonConfiguration = buildCarbonConfiguration(carbonExpression)
        BlockStatement newScriptCode = buildCarbonCode(methodNode, carbonConfiguration)

        // methodNode.setCode(newScriptCode)
        // visitAndResetVariableScopes(methodNode)
    }

    /**
     * @param methodNode
     * @since 0.2.0
     */
    void visitAndResetVariableScopes(MethodNode methodNode) {
        VariableScopeVisitor scopeVisitor = new VariableScopeVisitor(this.sourceUnit)
		scopeVisitor.visitClass(methodNode.getDeclaringClass())
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
        println "create new code"
        return null
    }

    /**
     * Finds the carbon variable declaration in the Groovy script and
     * returns its value's expression
     *
     * @param methodNode script's run method node
     * @return an {@link Expression} with the value of the carbon expression
     * @since 0.2.0
     */
    Expression findCarbonExpressionValue(MethodNode methodNode) {
        println "finding carbon expression"
        CarbonExpressionSeeker visitor = new CarbonExpressionSeeker(this.sourceUnit)
        methodNode.code.visit(visitor)

        return visitor?.found?.rightExpression
    }

    /**
     * From the expression value found, a configuration map will be
     * built
     *
     * @param expr {@link Expression} containing the carbon expression value
     * @return a map containing the configuration values
     * @since 0.2.0
     */
    Map<String,?> buildCarbonConfiguration(Expression expr) {
        println "build carbon configuration"

        return [:]
    }
}
