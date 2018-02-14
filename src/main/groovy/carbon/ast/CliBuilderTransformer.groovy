package carbon.ast

import groovy.util.CliBuilder
import groovy.transform.CompileStatic
import org.codehaus.groovy.ast.expr.Expression
import org.codehaus.groovy.ast.expr.MapExpression
import org.codehaus.groovy.ast.expr.MapEntryExpression
import org.codehaus.groovy.ast.expr.PropertyExpression
import org.codehaus.groovy.ast.expr.ClosureExpression
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
 * Transforms the main method in a script, which is the "run" method
 * to convert labelled statements into a CliBuilder aware script.
 *
 * @since 0.1.0
 */
@CompileStatic
class CliBuilderTransformer extends AbstractMethodNodeTransformer {

    /**
     * Name of the method to be transformed
     *
     * @since 0.1.0
     */
    static final String SCRIPT_METHOD_NAME = 'run'

    /**
     * Name of the {@link CliBuilder} instance variable
     *
     * @since 0.1.0
     */
    static final String CLI_BUILDER_NAME = 'cli'

    /**
     * Default script's usage label
     *
     * @since 0.1.0
     */
    static final String DEFAULT_USAGE_LABEL = 'usage'

    /**
     * Default script's usage name
     *
     * @since 0.1.0
     */
    static final String DEFAULT_USAGE_NAME = 'usage'

    /**
     * Default script's usage description
     *
     * @since 0.1.0
     */
    static final String DEFAULT_USAGE_DESC = 'It would be nice to know how to use this script right ? Shame on you!'

    /**
     * Default constructor
     *
     * @param sourceUnit in case we need to interact with compiler
     * @since 0.1.0
     */
    CliBuilderTransformer(final SourceUnit sourceUnit) {
        super(sourceUnit, A.CRITERIA.byMethodNodeName(SCRIPT_METHOD_NAME))
    }

    @Override
    void transformMethod(final MethodNode methodNode) {
        final BlockStatement blockS = A.UTIL.NODE.getCodeBlock(methodNode)
        final List<StatementUtils.Group> groups = A.UTIL.STMT.groupStatementsByLabel(blockS)

        final Statement newBuilderS = newBuilderS(groups)
        final Statement cliWithS = cliWithS(groups)
        final Statement parseArgsS = parseArgsStmt()

        final List<Statement> cliConfiguration = [newBuilderS, cliWithS, parseArgsS]
        final List<Statement> cliBuilderCases = createCases(groups)

        methodNode.code = A.STMT.blockS(cliConfiguration + cliBuilderCases)
    }

    /**
     * Creates the {@link Statement} where the CliBuilder instance declaration
     * is created
     *
     * <code>CliBuilder cli = new CliBuilder(usage: '')</code>
     *
     * @param groups all the labelled groups
     * @return an {@link Statement} with the CliBuilder instance creation expression
     * @since 0.1.0
     */
    Statement newBuilderS(List<StatementUtils.Group> groups) {
        StatementUtils.Group usageGroup = groups.find { StatementUtils.Group group ->
            !notUsage(group)
        }

        String name = usageGroup?.label?.name ?: DEFAULT_USAGE_NAME
        String desc = usageGroup?.label?.desc ?: DEFAULT_USAGE_DESC

        MapExpression usageX =
            A.EXPR.mapX(A.EXPR.mapEntryX(A.EXPR.constX(name),
                                         A.EXPR.constX(desc)))

        DeclarationExpression declarationX =
            A.EXPR.varDeclarationX(CLI_BUILDER_NAME,
                                   CliBuilder,
                                   A.EXPR.newX(CliBuilder, usageX))

        return A.STMT.stmt(declarationX)
    }

    List<Statement> createCases(List<StatementUtils.Group> groups) {
        return groups
            .findAll(this.&notUsage)
            .collect(this.&createCaseStmt)
    }

    Statement createCaseStmt(final StatementUtils.Group group) {
        PropertyExpression propX = A.EXPR.propX(A.EXPR.varX('options'),
                                                A.EXPR.constX(group.label.name.find()))

        return A.STMT.ifS(A.EXPR.boolX(propX),
                          A.STMT.blockS(group.statements))
    }

    Statement cliWithS(final List<StatementUtils.Group> groups) {
        List<MethodCallExpression> optionsX = groups
            .findAll(this.&notUsage)
            .collect(this.&createOptionExpression)

        MethodCallExpression cliWithX = cliWithX(optionsX)

        return A.STMT.stmt(cliWithX)
    }

    /**
     * Predicate to filter all groups that are not the usage group
     *
     * @param group the group to be evaluated
     * @return true if it's not the usage group false otherwise
     */
    Boolean notUsage(StatementUtils.Group group) {
        return group.label.name != DEFAULT_USAGE_LABEL
    }

    MethodCallExpression cliWithX(List<MethodCallExpression> optionsXs) {
        List<Statement> statements = optionsXs.collect(A.STMT.&stmt)
        ClosureExpression closureX = A.EXPR.closureX(A.STMT.blockS(statements))

        return A.EXPR.callX(A.EXPR.varX(CLI_BUILDER_NAME), 'with', closureX)
    }

    MethodCallExpression createOptionExpression(StatementUtils.Group group) {
        String groupName = group.label.name

        List<MapEntryExpression> configEntries =
            [A.EXPR.mapEntryX(A.EXPR.constX('longOpt'),
                              A.EXPR.constX(groupName)),
             A.EXPR.mapEntryX(A.EXPR.constX('required'),
                              A.EXPR.constX(false))]

        MethodCallExpression methodCallX =
            A.EXPR.callThisX("${groupName.find()}",
                             A.EXPR.mapX(configEntries))

        return methodCallX
    }

    Statement parseArgsStmt() {
        DeclarationExpression declarationX =
            A.EXPR.varDeclarationX('options',
                                   Object,
                                   A.EXPR.callX(A.EXPR.varX(CLI_BUILDER_NAME), 'parse', A.EXPR.varX('args')))

        return A.STMT.stmt(declarationX)
    }
}
