package carbon.ast.model;

import org.codehaus.groovy.ast.expr.MapExpression;
import org.codehaus.groovy.ast.stmt.Statement;

import java.util.List;

public class Section {
    private final String name;
    private final String description;
    private final MapExpression mapExpression;
    private final List<Statement> statements;

    public Section(String name, String description, MapExpression mapExpression, List<Statement> statements) {
        this.name = name;
        this.description = description;
        this.mapExpression = mapExpression;
        this.statements = statements;
    }

    public MapExpression getMapExpression() {
        return mapExpression;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Statement> getStatements() {
        return statements;
    }
}
