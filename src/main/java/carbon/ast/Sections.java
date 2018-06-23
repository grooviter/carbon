package carbon.ast;

import asteroid.Utils;
import asteroid.utils.StatementUtils;
import carbon.ast.model.*;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.expr.*;
import org.codehaus.groovy.ast.stmt.Statement;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static asteroid.Statements.stmt;
import static carbon.ast.Arguments.DEFAULT_ARGUMENT_HELP;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.collectingAndThen;

/**
 * Utility functions to extract Sections from code labelled blocks
 *
 * @since 0.1.0
 */
class Sections {

    /**
     * Alias of Function<StatementUtils.Group, Optional<Section>>
     *
     * @since 0.1.0
     */
    interface OptionalSection extends Function<StatementUtils.Group, Optional<Section>> {
        // used as alias
    }

    /**
     *
     * @param group the code statement group
     * @return
     * @since 0.1.0
     */
    private static Optional<Section> extractSection(final StatementUtils.Group group) {
        final List<OptionalSection> strategyList = asList(
            Sections::fromEmptyStrategy,
            Sections::fromMapStrategy,
            Sections::fromTextStrategy
        );

        return strategyList
            .stream()
            .map(strategy -> strategy.apply(group))
            .flatMap(Functions::flatten)
            .findFirst();
    }

    private static Optional<Section> findSection(String name, List<StatementUtils.Group> groupList) {
        return groupList
            .stream()
            .filter(Sections.isSection(name))
            .map(Sections::extractSection)
            .flatMap(Functions::flatten)
            .findFirst();
    }

    static Task createTaskFrom(List<StatementUtils.Group> groups) {
        MetaInfo metaInfo = extractMetaInfo(groups);

        return new Task(
            extractArguments(groups),
            metaInfo,
            extractUsage(groups, metaInfo),
            extractStatements(groups));
    }

    private static Usage extractUsage(List<StatementUtils.Group> groups, MetaInfo metaInfo) {
        Optional<Section> section = findSection("usage", groups);

        List<MapEntryExpression> parts = section
            .map(Section::getMapExpression)
            .map(MapExpression::getMapEntryExpressions)
            .orElse(emptyList());

        String descriptionDefaults = section
            .map(Section::getDescription)
            .orElse(Constants.DEFAULT_USAGE_DESC);

        String headerHeading = findParam(parts,
                "headerHeading",
                "%n@|bold,underline Name|@:%n");

        String header = findParam(parts,
                "header",
                format("@|bold,white %s |@%n", metaInfo.getName()));

        String synopsisHeading = findParam(parts,
                "synopsisHeading",
                "@|bold,underline Usage|@: ");

        String descriptionHeading = findParam(parts,
                "descriptionHeading",
                "%n@|bold,underline Description|@:%n");

        String description = findParam(parts,
                "description",
                descriptionDefaults);

        String footerHeading = findParam(parts,
                "footerHeading",
                "%n@|bold,underline Version|@:%n");

        String footer = findParam(parts,
                "footer",
                metaInfo.getVersion());

        String optionListHeading = findParam(parts,
                "optionListHeading",
                "%n@|bold,underline Options|@:%n");

        return Usage
            .newUsage()
            .withDescription(description)
            .withDescriptionHeading(descriptionHeading)
            .withFooter(footer)
            .withFooterHeading(footerHeading)
            .withHeader(header)
            .withHeaderHeading(headerHeading)
            .withOptionListHeading(optionListHeading)
            .withSynopsisHeading(synopsisHeading);
    }

    private static String findParam(List<MapEntryExpression> parts, String name, String defaultValue) {
        return parts
            .stream()
            .filter(entryExpression -> entryExpression.getKeyExpression().getText().equals(name))
            .map(MapEntryExpression::getValueExpression)
            .map(Expression::getText)
            .findFirst()
            .orElse(defaultValue);
    }

    private static List<Argument> extractArguments(List<StatementUtils.Group> groups) {
        Predicate<Argument> isHelpArgument = (Argument arg) -> {
            return arg.getName().equalsIgnoreCase("help");
        };

        return findSection("params", groups)
            .map(Section::getMapExpression)
            .map(MapExpression::getMapEntryExpressions)
            .orElse(emptyList())
            .stream()
            .map(Arguments::extractArgument)
            .flatMap(Functions::flatten)
            .collect(collectingAndThen(Collectors.toList(), (list) -> {
                if (list.stream().noneMatch(isHelpArgument)) {
                    list.add(DEFAULT_ARGUMENT_HELP);
                }
                return list;
            }));
    }

    private static List<Statement> extractStatements(List<StatementUtils.Group> groups) {
        return findSection("script", groups)
            .map(Section::getStatements)
            .orElse(emptyList());
    }

    private static MetaInfo extractMetaInfo(List<StatementUtils.Group> groups) {
        String name = findSection("name", groups)
            .map(Section::getDescription)
            .orElse("unknown");

        String version = findSection("version", groups)
            .map(Section::getDescription)
            .orElse("0.1.0-SNAPSHOT");

        String author = findSection("author", groups)
            .map(Section::getDescription)
            .orElse("anonymous");

        return new MetaInfo(name,version, author);
    }

    private static Predicate<StatementUtils.Group> isSection(String sectionName) {
        return (StatementUtils.Group group) -> group.label.name.equals(sectionName);
    }

    /**
     * @param methodNode
     * @return
     * @since 0.1.0
     */
    static List<StatementUtils.Group> getGroupsFromMethod(MethodNode methodNode) {
        return Utils.STMT.groupStatementsByLabel(Utils.NODE.getCodeBlock(methodNode));
    }

    /**
     *
     * <pre>
     * <code>
     *  save: [desc: 'saves a record']
     * </code>
     * </pre>
     *
     * @param group the labelled code block
     * @return
     * @since 0.1.0
     */
    private static Optional<Section> fromMapStrategy(final StatementUtils.Group group) {
        return fromStrategy(group, Sections::isMapExpression, Sections::createSectionFromMap);
    }

    /**
     *
     * <pre>
     * <code>
     *  save: 'saves a record'
     * </code>
     * </pre>
     *
     * @param group the labelled code block
     * @return
     * @since 0.1.0
     */
    private static Optional<Section> fromTextStrategy(final StatementUtils.Group group) {
        return fromStrategy(group, Sections::isTextExpression, Sections::createSectionFromText);
    }

    private static Optional<Section> fromEmptyStrategy(final StatementUtils.Group group) {
        return fromStrategy(group, Sections::isEmptyExpression, Sections::createSectionToGetOnlyStatements);
    }

    /**
     *
     * @param group group to transform
     * @param cond condition to transform the group
     * @param transform function transforming the group
     * @return
     * @since 0.1.0
     */
    private static Optional<Section> fromStrategy(StatementUtils.Group group,
                                       Predicate<StatementUtils.Group> cond,
                                       Function<StatementUtils.Group, Section> transform) {
        return Optional
            .ofNullable(group)
            .filter(Sections::isThereLabel)
            .filter(cond)
            .map(transform);
    }

    /**
     *
     * @param group group to get the information from
     * @return
     * @since 0.1.0
     */
    private static Section createSectionFromMap(StatementUtils.Group group) {
        StatementUtils.Label label = group.label;
        ConstantExpression nameExpr = label.nameAsExpression();
        MapExpression mapExpr = (MapExpression) label.expression;

        return new Section(nameExpr.getText(), null, mapExpr, group.statements);
    }

    /**
     * @param group
     * @return
     * @since 0.1.0
     */
    private static Section createSectionFromText(StatementUtils.Group group) {
        StatementUtils.Label label = group.label;
        ConstantExpression nameExpr = label.nameAsExpression();
        ConstantExpression descExpr = (ConstantExpression) label.expression;

        return new Section(nameExpr.getText(), descExpr.getText(), null, group.statements);
    }

    private static Section createSectionToGetOnlyStatements(StatementUtils.Group group) {
        StatementUtils.Label label = group.label;

        LinkedList<Statement> statements = new LinkedList<>(group.statements);
        statements.addFirst(stmt(label.expression));

        return new Section(label.name, "", null, statements);
    }

    /**
     * Function that can be used as a predicate to decide whether a given {@link StatementUtils.Group}
     * has a label or not
     *
     * @param group checked group
     * @return true if the group has a label or false otherwise
     * @since 0.1.0
     */
    private static Boolean isThereLabel(StatementUtils.Group group) {
        return group.label != null;
    }

    /**
     * Function that can be used as a predicate to decide whether a given {@link StatementUtils.Label}
     * expression is of type text or not
     *
     * @param group checked label group
     * @return true if the label expression is of type text, false otherwise
     * @since 0.1.0
     */
    private static Boolean isTextExpression(StatementUtils.Group group) {
        Expression expression = group.label.expression;

        return expression instanceof ConstantExpression &&
                expression.getType().getTypeClass().equals(String.class);
    }

    private static Boolean isEmptyExpression(StatementUtils.Group group) {
        return !isTextExpression(group) && !isMapExpression(group);
    }

    /**
     * Function that can be used as a predicate to decide whether a given {@link StatementUtils.Label}
     * expression is of type {@link MapExpression} or not
     *
     * @param group checked label group
     * @return true if the label expression is of type {@link MapExpression}, false otherwise
     * @since 0.1.0
     */
    private static Boolean isMapExpression(StatementUtils.Group group) {
        Expression expression = group.label.expression;

        return expression instanceof MapExpression;
    }
}
