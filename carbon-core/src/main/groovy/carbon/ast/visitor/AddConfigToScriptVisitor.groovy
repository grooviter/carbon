package carbon.ast.visitor

import asteroid.A
import carbon.ast.CarbonASTTransformation
import carbon.ast.CarbonScript
import groovy.transform.TupleConstructor
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.FieldNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codehaus.groovy.ast.expr.Expression
import org.codehaus.groovy.ast.expr.VariableExpression
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

    private static final String FIELD_NAME = 'configuration'

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
        String configuration = carbonConfig.configuration as String

        // Don't add in case method has been implemented already
        if (hasMethodAlready) {
            return
        }

        FieldNode configurationField = getFieldNode(classNode, configuration)

        A.UTIL.NODE.addGeneratedField(classNode, configurationField)
        A.UTIL.NODE.addGeneratedMethod(classNode, configurationMethod)
    }

    private static MethodNode getConfigurationMethod() {
        return A.NODES
            .method(METHOD_NAME)
            .modifiers(CarbonASTTransformation.ACC_PUBLIC)
            .returnType(Map)
            .code(methodCode)
            .build()
    }

    private static FieldNode getFieldNode(ClassNode classNode, String path) {
        Expression initialExpression = getInitialExpression(path)

        return A.NODES
            .field(FIELD_NAME)
            .type(Map)
            .owner(classNode)
            .expression(initialExpression)
            .build()
    }

    @SuppressWarnings('UnnecessaryPackageReference')
    private static Expression getInitialExpression(String path) {
        if (!path) {
            return A.EXPR.mapX()
        }

        ConstantExpression pathExpression = A.EXPR.constX(path)
        Expression expression = macro(CompilePhase.CONVERSION) {
            new org.yaml.snakeyaml.Yaml().load(
                    new java.io.FileInputStream(
                            new java.io.File($v { pathExpression })
                    )
            )
        }

        return expression
    }

    private static Statement getMethodCode() {
        VariableExpression configurationVarX = A.EXPR.varX(FIELD_NAME)

        return macro(CompilePhase.CONVERSION) {
            return $v { configurationVarX }
        }
    }
}
