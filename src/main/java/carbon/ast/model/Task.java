package carbon.ast.model;

import org.codehaus.groovy.ast.expr.ConstantExpression;
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

    public Task(ConstantExpression name, ConstantExpression description, List<TaskArgument> arguments, List<Statement> statements) {
        this.name = name;
        this.description = description;
        this.arguments = arguments;
        this.statements = statements;
    }

    public ConstantExpression getName() {
        return name;
    }

    public ConstantExpression getDescription() {
        return description;
    }

    public List<TaskArgument> getArguments() {
        return arguments;
    }

    public List<Statement> getStatements() {
        return statements;
    }
}
