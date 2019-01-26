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
 * @since 0.2.0
 */
@CompileStatic
static class CarbonExpressionSeeker extends ClassCodeExpressionTransformer {

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
    CarbonExpressionSeeker(SourceUnit sourceUnit) {
        this.sourceUnit = sourceUnit
    }

    @Override
    Expression transform(Expression expr) {
        if (expr && expr instanceof BinaryExpression && expr.leftExpression instanceof VariableExpression) {
            VariableExpression leftX = (VariableExpression) expr.leftExpression
            if (leftX && leftX.name == VARIABLE_NAME) {
                this.found = expr
            }
        }

        return super.transform(expr)
    }
}
