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
 * @since 0.2.0
 */
@CompileStatic
class ExpressionFinder extends ClassCodeExpressionTransformer {

    /**
     * @since 0.2.0
     */
    static final String VARIABLE_NAME = 'carbon'

    /**
     * @since 0.2.0
     */
    BinaryExpression found

    /**
     * @since 0.2.0
     */
    SourceUnit sourceUnit

    /**
     * @since 0.2.0
     */
    ExpressionFinder(SourceUnit sourceUnit) {
        this.sourceUnit = sourceUnit
    }

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
