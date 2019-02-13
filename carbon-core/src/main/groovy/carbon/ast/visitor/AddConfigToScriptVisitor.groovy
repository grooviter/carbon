package carbon.ast.visitor

import asteroid.A
import carbon.ast.CarbonASTTransformation
import carbon.ast.CarbonScript
import groovy.transform.TupleConstructor
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codehaus.groovy.ast.stmt.Statement
import org.codehaus.groovy.control.CompilePhase

/**
 * Makes available full Carbon configuration as the implementation
 * for the {@link CarbonScript#getConfiguration}
 *
 * @since 0.2.0
 */
@TupleConstructor
class AddConfigToScriptVisitor {

    private static final String METHOD_NAME = 'getConfiguration'

    /**
     * {@link ClassNode} to add the Carbon configuration as new field
     *
     * @since 0.2.0
     */
    ClassNode classNode

    /**
     * Carbon configuration to be added to the script as a field node
     *
     * @since 0.2.0
     */
    Map<String,?> carbonConfig

    /**
     * Executes the procedure to add the configuration as a field node
     *
     * @since 0.2.0
     */
    void visit() {
        Boolean hasMethodAlready = classNode.methods.find { it.name == METHOD_NAME }
        String configuration = carbonConfig.configuration

        // Don't add in case method has been implemented already
        if (hasMethodAlready) {
            return
        }

        MethodNode methodNode = getMethodNode(configuration)

        A.UTIL.NODE.addGeneratedMethod(classNode, methodNode)
    }

    private static MethodNode getMethodNode(String path) {
        Statement loadYaml = getMethodCode(path)

        return A.NODES
            .method(METHOD_NAME)
            .modifiers(CarbonASTTransformation.ACC_PUBLIC)
            .returnType(Map)
            .code(loadYaml)
            .build()
    }

    @SuppressWarnings('UnnecessaryPackageReference')
    private static Statement getMethodCode(String path) {
        ConstantExpression pathExpression = A.EXPR.constX(path)

        if (path) {
            return macro(CompilePhase.CONVERSION) {
                return new org.yaml.snakeyaml.Yaml().load(
                    new java.io.FileInputStream(
                        new java.io.File($v { pathExpression })
                    )
                )
            }
        }

        return macro(CompilePhase.CONVERSION) {
            return [:]
        }
    }
}
