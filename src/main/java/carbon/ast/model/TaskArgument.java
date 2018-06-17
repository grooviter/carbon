package carbon.ast.model;

public class TaskArgument {
    private final String name;
    private final String description;
    private final Boolean mandatory;
    private final Class type;

    public TaskArgument(String name, String description, Boolean mandatory, Class type) {
        this.name = name;
        this.description = description;
        this.mandatory = mandatory;
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public Boolean getMandatory() {
        return mandatory;
    }

    public Class getType() {
        return type;
    }
}
