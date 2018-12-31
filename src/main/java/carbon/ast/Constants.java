package carbon.ast;

import carbon.ast.model.Task;

import static asteroid.Expressions.constX;

public class Constants {
    /**
     * Name of the method to be transformed
     *
     * @since 0.1.0
     */
    static final String SCRIPT_METHOD_NAME = "run";

    /**
     * Name of the {@link groovy.cli.picocli.CliBuilder} instance variable
     *
     * @since 0.1.0
     */
    static final String CLI_BUILDER_NAME = "cli";

    /**
     * Default script"s usage name
     *
     * @since 0.1.0
     */
    public static final String DEFAULT_USAGE_NAME = "usage";

    public static final String DEFAULT_ARGS_DESCRIPTION = "description";

    public static final String DEFAULT_ARGS_PARAM_MANDATORY = "mandatory";

    public static final String EMPTY = "";

    /**
     * Default script"s usage description
     *
     * @since 0.1.0
     */
    static final String DEFAULT_USAGE_DESC = "Still no description";

    /**
     * @since 0.1.0
     */
    static final Task DEFAULT_USAGE_TASK = null;

}
