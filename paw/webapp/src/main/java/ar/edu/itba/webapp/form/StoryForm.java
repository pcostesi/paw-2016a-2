package ar.edu.itba.webapp.form;

import ar.edu.itba.webapp.form.constraint.StoryNameFree;

import javax.validation.constraints.Size;

@StoryNameFree(markedField = "title")
public class StoryForm {

    @Size(min = 1, max = 100)
    private String title;

    private int iterationId;

    private String oldTitle;

    public int getIterationId() {
        return iterationId;
    }

    public void setIterationId(final int iterationId) {
        this.iterationId = iterationId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getOldTitle() {
        return oldTitle;
    }

    public void setOldTitle(final String oldTitle) {
        this.oldTitle = oldTitle;
    }

}
