package io.keam;

/**
 * Webpage Spec
 * @author Jon Keam
 */
public class WebpageSpec {

    public WebpageSpec() {
        markdown = "";
    }

    public WebpageSpec(final String markdown) {
        this.markdown = markdown;
    }

    private String markdown;

    public String getMarkdown() {
        return markdown;
    }

    public void setMarkdown(String markdown) {
        this.markdown = markdown;
    }
}
