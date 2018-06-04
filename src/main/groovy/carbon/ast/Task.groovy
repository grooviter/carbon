package carbon.ast

import groovy.transform.Immutable

import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codehaus.groovy.ast.expr.MapExpression
import org.codehaus.groovy.ast.stmt.Statement

@Immutable(copyWith = true)
class Task {
    ConstantExpression name
    ConstantExpression description
    MapExpression arguments
    List<Statement> statements
}
