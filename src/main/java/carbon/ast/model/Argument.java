package carbon.ast.model;

public class Argument {
    private final String name;
    private final String description;
    private final Boolean mandatory;
    private final Class type;
    private final String defaultValue;

    public Argument(String name, String description, Boolean mandatory, Class type, String defaultValue) {
        this.name = name;
        this.description = description;
        this.mandatory = mandatory;
        this.type = type;
        this.defaultValue = defaultValue;
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

    public String getDefaultValue() {
        return defaultValue;
    }
}
