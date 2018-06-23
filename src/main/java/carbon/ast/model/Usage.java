package carbon.ast.model;

import carbon.ast.Constants;

import static carbon.ast.Constants.EMPTY;

public class Usage {

    private final String headerHeading;
    private final String header;
    private final String synopsisHeading;
    private final String descriptionHeading;
    private final String description;
    private final String optionListHeading;
    private final String footerHeading;
    private final String footer;

    private Usage(
            String headerHeading,
            String header,
            String synopsisHeading,
            String descriptionHeading,
            String description,
            String optionListHeading,
            String footerHeading,
            String footer) {

        this.headerHeading = headerHeading;
        this.header = header;
        this.synopsisHeading = synopsisHeading;
        this.descriptionHeading = descriptionHeading;
        this.description = description;
        this.optionListHeading = optionListHeading;
        this.footerHeading = footerHeading;
        this.footer = footer;
    }

    public String getHeaderHeading() {
        return headerHeading;
    }

    public String getHeader() {
        return header;
    }

    public String getSynopsisHeading() {
        return synopsisHeading;
    }

    public String getDescriptionHeading() {
        return descriptionHeading;
    }

    public String getDescription() {
        return description;
    }

    public String getOptionListHeading() {
        return optionListHeading;
    }

    public String getFooterHeading() {
        return footerHeading;
    }

    public String getFooter() {
        return footer;
    }

    public static Usage newUsage() {
        return new Usage(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY);
    }

    public Usage withHeaderHeading(String headerHeading){
        return new Usage(
                headerHeading,
                this.header,
                this.synopsisHeading,
                this.descriptionHeading,
                this.description,
                this.optionListHeading,
                this.footerHeading,
                this.footer);
    }

    public Usage withHeader(String header) {
        return new Usage(this.headerHeading,
                header,
                this.synopsisHeading,
                this.descriptionHeading,
                this.description,
                this.optionListHeading,
                this.footerHeading,
                this.footer);
    }

    public Usage withSynopsisHeading(String synopsisHeading) {
        return new Usage(this.headerHeading,
                this.header,
                synopsisHeading,
                this.descriptionHeading,
                this.description,
                this.optionListHeading,
                this.footerHeading,
                this.footer);
    }

    public Usage withDescriptionHeading(String descriptionHeading) {
        return new Usage(this.headerHeading,
                this.header,
                this.synopsisHeading,
                descriptionHeading,
                this.description,
                this.optionListHeading,
                this.footerHeading,
                this.footer);
    }

    public Usage withDescription(String description) {
        return new Usage(this.headerHeading,
                this.header,
                this.synopsisHeading,
                this.descriptionHeading,
                description,
                this.optionListHeading,
                this.footerHeading,
                this.footer);
    }

    public Usage withOptionListHeading(String optionListHeading) {
        return new Usage(this.headerHeading,
                this.header,
                this.synopsisHeading,
                this.descriptionHeading,
                this.description,
                optionListHeading,
                this.footerHeading,
                this.footer);
    }

    public Usage withFooterHeading(String footerHeading) {
        return new Usage(this.headerHeading,
                this.header,
                this.synopsisHeading,
                this.descriptionHeading,
                this.description,
                this.optionListHeading,
                footerHeading,
                this.footer);
    }

    public Usage withFooter(String footer) {
        return new Usage(this.headerHeading,
                this.header,
                this.synopsisHeading,
                this.descriptionHeading,
                this.description,
                this.optionListHeading,
                this.footerHeading,
                footer);
    }
}
