package carbon.ast.model;

import org.codehaus.groovy.ast.stmt.Statement;

import java.util.List;

/**
 * @since 0.1.0
 */
public class Task {
    private final List<Argument> arguments;
    private final MetaInfo metaInfo;
    private final Usage usage;
    private final List<Statement> statements;

    public Task(List<Argument> arguments, MetaInfo metaInfo, Usage usage, List<Statement> statements) {
        this.arguments = arguments;
        this.metaInfo = metaInfo;
        this.usage = usage;
        this.statements = statements;
    }

    public List<Argument> getArguments() {
        return arguments;
    }

    public MetaInfo getMetaInfo() {
        return metaInfo;
    }

    public Usage getUsage() {
        return usage;
    }

    public List<Statement> getStatements() {
        return statements;
    }
}
