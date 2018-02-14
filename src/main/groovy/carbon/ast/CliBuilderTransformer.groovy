package carbon.ast

import groovy.util.CliBuilder
import groovy.transform.CompileStatic
import org.codehaus.groovy.ast.expr.Expression
import org.codehaus.groovy.ast.expr.MapExpression
import org.codehaus.groovy.ast.expr.PropertyExpression
import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codehaus.groovy.ast.expr.DeclarationExpression
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.ast.stmt.Statement
import org.codehaus.groovy.ast.stmt.BlockStatement

import asteroid.A
import asteroid.utils.StatementUtils
import asteroid.transformer.AbstractMethodNodeTransformer

/**
 * @since 0.1.0
 */
@CompileStatic
class CliBuilderTransformer extends AbstractMethodNodeTransformer {


    /**
     * @param sourceUnit
     * @since 0.1.0
     */
    CliBuilderTransformer(final SourceUnit sourceUnit) {
        super(sourceUnit, A.CRITERIA.byMethodNodeName('run'))
    }

    @Override
    void transformMethod(final MethodNode methodNode) {
        final BlockStatement blockS = A.UTIL.NODE.getCodeBlock(methodNode)
        final List<StatementUtils.Group> groups = A.UTIL
            .STMT
            .groupStatementsByLabel(blockS)

        final List<Statement> cases = groups.collect(this.&createCaseStmt)
        final Statement cliWithS = cliWithS(groups)

        methodNode.code = A.STMT.blockS(
            [newBuilderS()] + [cliWithS] + [parseArgsStmt()] + cases
        )
    }

    Statement cliWithS(final List<StatementUtils.Group> groups) {
        return A.STMT.blockS(groups.collect(this.&cliWithCaseS) as List<Statement>)
    }

    Statement cliWithCaseS(StatementUtils.Group group) {
        String groupName = group.label.name
        MethodCallExpression methodCallX =
            A.EXPR.callThisX("${groupName.find()}",
                             A.EXPR.mapX(A.EXPR.mapEntryX(A.EXPR.constX('longOpt'),
                                                          A.EXPR.constX(groupName)),
                                         A.EXPR.mapEntryX(A.EXPR.constX('required'),
                                                          A.EXPR.constX(false))))

        return A.STMT.stmt(methodCallX)
    }

    Statement parseArgsStmt() {
        DeclarationExpression declarationX =
            A.EXPR.varDeclarationX('options',
                                   Object,
                                   A.EXPR.callX(A.EXPR.varX('cli'), 'parse', A.EXPR.varX('args')))

        return A.STMT.stmt(declarationX)
    }

    Statement createCaseStmt(final StatementUtils.Group group) {
        PropertyExpression propX = A.EXPR.propX(A.EXPR.varX('options'), A.EXPR.constX(group.label.name.find()))

        return A.STMT.ifS(A.EXPR.boolX(propX), A.STMT.blockS(group.statements))
    }

    Statement newBuilderS() {
        MapExpression usageX =
            A.EXPR.mapX(A.EXPR.mapEntryX(A.EXPR.constX('usage'), A.EXPR.constX('bla')))
        DeclarationExpression declarationX =
            A.EXPR.varDeclarationX('cli',
                                   CliBuilder,
                                   A.EXPR.newX(CliBuilder, usageX))

        return A.STMT.stmt(declarationX)
    }
}
