package carbon.ast.transformer

import asteroid.A
import groovy.transform.CompileStatic
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.ClassCodeExpressionTransformer
import org.codehaus.groovy.ast.expr.Expression
import org.codehaus.groovy.ast.expr.BinaryExpression
import org.codehaus.groovy.ast.expr.VariableExpression
import org.codehaus.groovy.control.SourceUnit

/**
 * Looks for the expression containing the Carbon's configuration
 *
 * @since 0.2.0
 */
@CompileStatic
class ExpressionFinder extends ClassCodeExpressionTransformer {

    /**
     * Carbon variable's name
     *
     * @since 0.2.0
     */
    static final String VARIABLE_NAME = 'carbon'

    /**
     * The Carbon expression will be an assignment expression:
     *
     * <ul>
     *   <li>carbon = [:]</li>
     *   <li>carbon = 'carbon.yml'</li>
     * </ul>
     *
     * @since 0.2.0
     */
    BinaryExpression found

    /**
     * The {@link SourceUnit} instance can be used in case some
     * compilation error should be raised, or to get more information
     * from compilation.
     *
     * @since 0.2.0
     */
    SourceUnit sourceUnit

    /**
     * Receives an instance of {@link SourceUnit} to be capable of
     * controlling compilation
     *
     * @since 0.2.0
     */
    ExpressionFinder(SourceUnit sourceUnit) {
        this.sourceUnit = sourceUnit
    }

    /**
     * Returns the Carbon's configuration
     *
     * @param methodNode the method to find the expression
     * @return the {@link Expression} containing Carbon's config
     * @since 0.2.0
     */
    Expression find(MethodNode methodNode) {
        methodNode.code.visit(this)

        return this.found?.rightExpression
    }

    @Override
    @SuppressWarnings('Instanceof')
    Expression transform(Expression expr) {
        if (expr instanceof BinaryExpression && expr.leftExpression instanceof VariableExpression) {
            VariableExpression leftX = (VariableExpression) expr.leftExpression
            if (leftX && leftX.name == VARIABLE_NAME) {
                this.found = expr
                return A.EXPR.constX(null)
            }
        }

        return super.transform(expr)
    }
}
