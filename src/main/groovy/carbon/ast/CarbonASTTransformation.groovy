package carbon.ast

import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.expr.Expression
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.transform.GroovyASTTransformation
import org.codehaus.groovy.transform.AbstractASTTransformation
import carbon.ast.transformer.PicocliOptsVisitor
import carbon.ast.transformer.PicocliParamsVisitor
import carbon.ast.transformer.PicocliScriptVisitor
import carbon.ast.transformer.ExpressionFinder
import carbon.ast.transformer.ConfigurationBuilder
import carbon.ast.transformer.PicocliVisitorUtils

/**
 * Applies a series of transformations to the script class and the
 * script's run method in order to add Picocli behavior to our script
 * depending on the Carbon's configuration found in the script.
 *
 * @since 0.2.0
 */
@GroovyASTTransformation(phase = CompilePhase.SEMANTIC_ANALYSIS)
class CarbonASTTransformation extends AbstractASTTransformation {

    @Override
    void visit(ASTNode[] nodes, SourceUnit sourceUnit) {
        sourceUnit.AST.classes
            .find(this.&isScript)
            .each(this.&applyToClassNode)
    }

    private Boolean isScript(ClassNode classNode) {
        return classNode.superClass.name == Script.name
    }

    private void applyToClassNode(ClassNode classNode) {
        MethodNode methodNode = classNode.getMethod('run')
        Expression carbonX = new ExpressionFinder(this.sourceUnit).find(methodNode)
        Map<String,?> carbonConfig = new ConfigurationBuilder(carbonX).build()

        if (!carbonConfig) {
            return
        }

        new PicocliOptsVisitor(methodNode, carbonConfig).visit()

        new PicocliParamsVisitor(methodNode, carbonConfig).visit()

        new PicocliScriptVisitor(classNode, carbonConfig).visit()

        PicocliVisitorUtils.visitAndResetVariableScopes(classNode, sourceUnit)
    }
}
