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
class PicocliOptsBuilder {

    MethodNode methodNode
    Map<String,?> carbonConfig

    void build() {
        Map<String, ?> opts = carbonConfig.options as Map<String,?>

        opts
            .entrySet()
            .stream()
            .map(this.&toOptField)
            .forEach({ FieldNode field ->
                methodNode.declaringClass.addField(field)
            })
    }

    FieldNode toOptField(Map.Entry<String,?> entry) {
        Map<String,?> val = entry.value as Map<String,?>
        String optionName = entry.key.toString()
        Class optionClass = val.type as Class
        Expression initialExpr = A.EXPR.constX(val.defaultValue) ?: null
        FieldNode newField = new FieldNode(
            optionName,
            FieldNode.ACC_PUBLIC,
            A.NODES.clazz(optionClass).build(),
            null,
            initialExpr
        )

        // AnnotationNode fieldAnnotation = A.NODES.annotation(Field).build()
        ListExpression namesX = A.EXPR.listX(
            A.EXPR.constX("--$optionName".toString()),
            A.EXPR.constX("-${optionName[0]}".toString())
        )
        AnnotationNode optionAnnotation = A.NODES
            .annotation(CommandLine.Option)
            .member('names', namesX)
            .build()
        AnnotationNode generatedAnnotation = A.NODES.annotation(Generated).build()

        // newField.addAnnotation(fieldAnnotation)
        newField.addAnnotation(optionAnnotation)
        newField.addAnnotation(generatedAnnotation)
        newField.synthetic = true

        return newField
    }
}
