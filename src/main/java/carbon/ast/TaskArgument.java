package carbon.ast;

public class TaskArgument {
    private final String name;
    private final String description;
    private final Boolean mandatory;

    TaskArgument(String name, String description, Boolean mandatory) {
        this.name = name;
        this.description = description;
        this.mandatory = mandatory;
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
}
