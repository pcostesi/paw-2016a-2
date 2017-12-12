package ar.edu.itba.webapp.form;

import ar.edu.itba.webapp.form.constraint.ItemTitleFree;

import javax.validation.constraints.Size;

@ItemTitleFree(markedField = "title")
public class BacklogForm {

    @Size(min = 1, max = 100)
    private String title;

    @Size(max = 500)
    private String description;

    private int projectId;

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(final int projectId) {
        this.projectId = projectId;
    }

}
