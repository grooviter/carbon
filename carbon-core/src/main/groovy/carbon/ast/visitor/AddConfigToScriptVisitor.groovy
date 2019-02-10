package carbon.ast.visitor

import asteroid.A
import carbon.ast.CarbonScript
import carbon.ast.CarbonASTTransformation
import groovy.transform.TailRecursive
import groovy.transform.TupleConstructor
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.expr.MapEntryExpression
import org.codehaus.groovy.ast.expr.MapExpression

/**
 * Makes available full Carbon configuration as the implementation
 * for the {@link CarbonScript#getCarbonConfig}
 *
 * @since 0.2.0
 */
@TupleConstructor
class AddConfigToScriptVisitor {

    private static final String METHOD_NAME = 'getCarbonConfig'

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
        // Don't add in case method has been implemented already
        if (classNode.methods.find { it.name == METHOD_NAME }) {
            return
        }

        Set<Map.Entry<String,?>> entries = carbonConfig.entrySet()
        MapExpression carbonConfigX = buildMapX(entries)
        MethodNode methodNode = createGetCarbonConfigMethod(carbonConfigX)

        A.UTIL.NODE.addGeneratedMethod(classNode, methodNode)
    }

    @SuppressWarnings('FactoryMethodName')
    private static MethodNode createGetCarbonConfigMethod(MapExpression mapExpression) {
        return A.NODES
            .method(METHOD_NAME)
            .modifiers(CarbonASTTransformation.ACC_PUBLIC)
            .returnType(Map)
            .code(A.STMT.returnS(mapExpression))
            .build()
    }

    @TailRecursive
    @SuppressWarnings('FactoryMethodName')
    private MapExpression buildMapX(Collection<Map.Entry<String,?>> entries, MapExpression acc = new MapExpression()) {
        if (entries.isEmpty()) {
            return acc
        }

        Map.Entry<String,?> head = entries.head()
        MapEntryExpression nextExpression = resolveEntry(head.key, head.value)
        acc.addMapEntryExpression(nextExpression)

        return buildMapX(entries.tail(), acc)
    }

    private MapEntryExpression resolveEntry(String key, Class clazz) {
        return A.EXPR.mapEntryX(key, A.EXPR.classX(clazz))
    }

    private MapEntryExpression resolveEntry(String key, Map map) {
        return A.EXPR.mapEntryX(key, buildMapX(map.entrySet()))
    }

    private MapEntryExpression resolveEntry(String key, Object value) {
        return A.EXPR.mapEntryX(key, A.EXPR.constX(value))
    }
}
