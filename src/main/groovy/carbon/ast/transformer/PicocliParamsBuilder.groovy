package carbon.ast.transformer

import groovy.transform.TupleConstructor
import asteroid.A
import asteroid.Criterias
import picocli.CommandLine
import picocli.groovy.PicocliScript
import picocli.groovy.PicocliBaseScript
import asteroid.transformer.AbstractMethodNodeTransformer
import groovy.transform.Field
import groovy.transform.Generated
import groovy.transform.CompileStatic
import org.codehaus.groovy.ast.Parameter
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.FieldNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.ast.ConstructorNode
import org.codehaus.groovy.ast.ClassHelper
import org.codehaus.groovy.ast.expr.Expression
import org.codehaus.groovy.ast.expr.ListExpression
import org.codehaus.groovy.ast.expr.DeclarationExpression
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.classgen.VariableScopeVisitor

@TupleConstructor
class PicocliParamsBuilder {
    MethodNode methodNode
    Map<String,?> carbonConfig

    void build() {
        Map<String, ?> params = carbonConfig.params as Map<String,?>

        params
            .entrySet()
            .stream()
            .map(this.&toParamField)
            .forEach({ FieldNode field -> methodNode.declaringClass.addField(field) })
    }

    private FieldNode toParamField(Map.Entry<String,?> entry) {
        Map<String,?> val = entry.value as Map<String,?>
        String optionName = entry.key.toString()
        ClassNode optionClass = A.NODES.clazz(val.type).build()

        String simple = optionClass.name
        ClassNode clazzNode = simple.contains('[L')
        ? A.NODES.clazz((simple - '[L') - ';').build().makeArray()
        : A.NODES.clazz(simple).build()

        FieldNode newField = new FieldNode(
            optionName,
            FieldNode.ACC_PUBLIC,
            clazzNode,
            null,
            null
        )

        AnnotationNode optionAnnotation = A.NODES
            .annotation(CommandLine.Parameters)
            .member('paramLabel', A.EXPR.constX(optionName))
            .build()
        AnnotationNode generatedAnnotation = A.NODES.annotation(Generated).build()

        newField.addAnnotation(optionAnnotation)
        newField.addAnnotation(generatedAnnotation)
        newField.synthetic = true

        return newField
    }
}
