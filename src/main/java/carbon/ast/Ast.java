package carbon.ast;

import asteroid.A;
import carbon.ast.model.Argument;
import carbon.ast.model.Usage;
import groovy.cli.picocli.CliBuilder;
import groovy.cli.picocli.OptionAccessor;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.expr.*;
import org.codehaus.groovy.ast.stmt.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import static asteroid.Expressions.*;
import static asteroid.Statements.*;
import static carbon.ast.Constants.CLI_BUILDER_NAME;
import static carbon.ast.Constants.DEFAULT_USAGE_NAME;

public class Ast {

    public static final String PARAMS = "params";

    /**
     * Creates the {@link Statement} where the CliBuilder instance declaration
     * is created
     *
     * <code>CliBuilder cli = new CliBuilder(name: '', usage: "")</code>
     *
     * @return an {@link Statement} with the CliBuilder instance creation expression
     * @since 0.1.0
     */
    static Statement newBuilderS(String name, Usage usage) {
        MapExpression usageMapX = mapX(mapEntryX(constX("name"), constX(name)));
        DeclarationExpression declarationX = varDeclarationX(
                CLI_BUILDER_NAME,
                CliBuilder.class,
                newX(CliBuilder.class, usageMapX));

        return stmt(declarationX);
    }

    /**
     * Creates the statement where the CliBuilder parses the script arguments
     *
     * <code>options = cli.parse(strategies)</code>
     *
     * @return a {@link Statement} with an expression parsing the script strategies
     * @since 0.1.0
     */
    static Statement parseArgsStmt() {
        VariableExpression cliVarX = varX(
                CLI_BUILDER_NAME,
                A.NODES.clazz(CliBuilder.class.getName()).build());

        MethodCallExpression parseFromInstanceX = callX(cliVarX,
                "parse",
                varX("args"));

        DeclarationExpression declarationExpression = varDeclarationX(
                PARAMS, OptionAccessor.class, parseFromInstanceX);

        return stmt(declarationExpression);
    }

    /**
     * <code>
     *   cli.usageMessage.with {
     *     headerHeading("Header heading:%n")
     *     header("header 1", "header 2")
     *     synopsisHeading("%nUSAGE: ")
     *     descriptionHeading("%nDescription heading:%n")
     *     description("description 1", "description 2")
     *     optionListHeading("%nOPTIONS:%n")
     *     footerHeading("%nFooter heading:%n")
     *     footer("footer 1", "footer 2")
     *   }
     * </code>
     */
    static Statement usageMessageConfigurationStmt(Usage usage) {
        return blockS(
            getUsageMessageX("headerHeading", usage.getHeaderHeading()),
            getUsageMessageX("headerHeading", usage.getHeaderHeading()),
            getUsageMessageX("header", usage.getHeader()),
            getUsageMessageX("synopsisHeading", usage.getSynopsisHeading()),
            getUsageMessageX("descriptionHeading", usage.getDescriptionHeading()),
            getUsageMessageX("description", usage.getDescription()),
            getUsageMessageX("optionListHeading", usage.getOptionListHeading()),
            getUsageMessageX("footerHeading", usage.getFooterHeading()),
            getUsageMessageX("footer", usage.getFooter())
        );
    }

    private static Statement getUsageMessageX(String method, String value) {
        MethodCallExpression usageMessageX = callX(varX(Constants.CLI_BUILDER_NAME), "getUsageMessage");

        return stmt(callX(usageMessageX, method, constX(value)));
    }

    /**
     * if (!params) {
     *   return
     * }
     *
     * if (params.help) {
     *   cli.usage()
     *   return
     * }
     *
     *
     */
    static Statement usageStmt() {
        IfStatement ifNotParams =  ifS(boolX(notX(varX("params"))), emptyReturnStmt());

        MethodCallExpression cliUsage = callX(varX(Constants.CLI_BUILDER_NAME), "usage");
        List<Statement> stmtList = Arrays.asList(stmt(cliUsage), emptyReturnStmt());
        IfStatement ifParamsHelp = ifS(boolX(propX(varX("params"), constX("help"))), blockS(stmtList));

        return blockS(Arrays.asList(ifNotParams, ifParamsHelp));
    }

    static Statement emptyReturnStmt() {
        return returnS(constX(""));
    }

    /**
     * @Option(description = "table to truncate",
     *         shortName = "t",
     *         longName = "table",
     *         valueSeparator = "",
     *         optionalArg = false,
     *         numberOfArguments = 1,
     *         defaultValue = "",
     *         convert = String) table
     */
    static Statement createFieldStmt(Argument arg) {
        MapExpression mapExpression = Optional.of(mapX(new ArrayList<>()))
                .map(addArgumentAttr("type", arg.getType(), () -> classX(arg.getType())))
                .map(addArgumentAttr("longOpt", arg.getName(), () -> constX(arg.getName())))
                .map(addArgumentAttr("defaultValue", arg.getDefaultValue(), () -> constX(arg.getDefaultValue())))
                .map(addArgumentAttr("description", arg.getDescription(), () -> constX(arg.getDescription())))
                .map(addArgumentAttr("required", arg.getMandatory(), () -> constX(arg.getMandatory())))
                .map(addArgumentAttr("usageHelp", arg.isUsageHelp(), () -> constX(arg.isUsageHelp())))
                .orElse(mapX());

        MethodCallExpression cliOptionX =
                callX(varX(CLI_BUILDER_NAME),
                        "" + arg.getName().charAt(0),
                        mapExpression,
                        constX(arg.getName()));

        return stmt(cliOptionX);
    }

    private static Function<MapExpression, MapExpression> addArgumentAttr(
            final String argumentAttrName,
            final Object value,
            final Supplier<Expression> expression) {
        final Optional<?> optionalValue = Optional
            .ofNullable(value)
            .map(Object::toString)
            .map(String::isEmpty);

        return (MapExpression mapX) -> {
            if (optionalValue.isPresent()) {
                mapX.addMapEntryExpression(mapEntryX(constX(argumentAttrName), expression.get()));
            }
            return mapX;
        };
    }
}
