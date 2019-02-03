package carbon.ast

import carbon.ast.config.ConfigurationBuilder
import carbon.ast.visitor.PicocliCommandVisitor
import carbon.ast.visitor.PicocliOptsVisitor
import carbon.ast.visitor.PicocliParamsVisitor
import carbon.ast.visitor.PicocliScriptVisitor
import carbon.ast.visitor.PicocliVisitorUtils
import groovy.transform.CompileStatic
import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.ModuleNode
import org.codehaus.groovy.ast.expr.BinaryExpression
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.macro.matcher.ASTMatcher
import org.codehaus.groovy.macro.matcher.TreeContext
import org.codehaus.groovy.transform.AbstractASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation

/**
 * Applies a series of transformations to the script class and the
 * script's run method in order to add Picocli behavior to our script
 * depending on the Carbon's configuration found in the script.
 *
 * @since 0.2.0
 */
@CompileStatic
@GroovyASTTransformation(phase = CompilePhase.SEMANTIC_ANALYSIS)
class CarbonASTTransformation extends AbstractASTTransformation {

    private static final ASTNode PATTERN = macro { carbon = _ } as ASTNode
    private static final String METHOD_RUN = 'run'

    @Override
    void visit(ASTNode[] nodes, SourceUnit sourceUnit) {
        ASTNode node = nodes.find() as ASTNode

        switch (node) {
            case ModuleNode:
                ModuleNode moduleNode = node as ModuleNode
                moduleNode
                    .classes
                    .find(this.&isScript)
                    .each(this.&applyToClassNode)

            default: return
        }
    }

    private Boolean isScript(ClassNode node) {
        return node.superClass.name == Script.name
    }

    @SuppressWarnings('Indentation')
    private void applyToClassNode(ClassNode classNode) {
        MethodNode methodNode = classNode.getMethod(METHOD_RUN)
        TreeContext context = ASTMatcher
            .find(methodNode.code, PATTERN)
            .find()

        Optional
            .ofNullable(context)
            .map { (BinaryExpression) it.node }
            .map { new ConfigurationBuilder(it.rightExpression).build() }
            .ifPresent {
                new PicocliOptsVisitor(methodNode, it).visit()
                new PicocliParamsVisitor(methodNode, it).visit()
                new PicocliScriptVisitor(classNode, it).visit()
                new PicocliCommandVisitor(classNode, it).visit()

                PicocliVisitorUtils.visitAndResetVariableScopes(classNode, sourceUnit)
            }
    }
}
