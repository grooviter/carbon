package carbon.ast.model;

public class Argument {
    private final String name;
    private final String description;
    private final Boolean mandatory;
    private final Class type;
    private final String defaultValue;
    private final Boolean isUsageHelp;

    public Argument(String name, String description, Boolean mandatory, Class type, String defaultValue, Boolean isUsageHelp) {
        this.name = name;
        this.description = description;
        this.mandatory = mandatory;
        this.type = type;
        this.defaultValue = defaultValue;
        this.isUsageHelp = isUsageHelp;
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

    public Boolean isUsageHelp() {
        return this.isUsageHelp;
    }
}
