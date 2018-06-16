package carbon.ast;

import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.MapExpression;
import org.codehaus.groovy.ast.stmt.Statement;

import java.util.List;

/**
 * @since 0.1.0
 */
public class Task {
    /**
     * @since 0.1.0
     */
    private final ConstantExpression name;

    /**
     * @since 0.1.0
     */
    private final ConstantExpression description;

    /**
     * @since 0.1.0
     */
    private final List<TaskArgument> arguments;

    /**
     * @since 0.1.0
     */
    private final List<Statement> statements;

    Task(ConstantExpression name, ConstantExpression description, List<TaskArgument> arguments, List<Statement> statements) {
        this.name = name;
        this.description = description;
        this.arguments = arguments;
        this.statements = statements;
    }

    ConstantExpression getName() {
        return name;
    }

    ConstantExpression getDescription() {
        return description;
    }

    List<TaskArgument> getArguments() {
        return arguments;
    }

    List<Statement> getStatements() {
        return statements;
    }
}
