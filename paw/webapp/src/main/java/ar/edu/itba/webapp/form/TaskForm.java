package ar.edu.itba.webapp.form;

import ar.edu.itba.models.Priority;
import ar.edu.itba.models.Score;
import ar.edu.itba.models.Status;
import ar.edu.itba.webapp.form.constraint.TaskNameFree;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@TaskNameFree(markedField = "title")
public class TaskForm {

    @Size(min = 1, max = 100)
    private String title;

    @Size(max = 500)
    private String description;

    @NotNull
    private Status status;

    @NotNull
    private Score score;

    @NotNull
    private String owner;

    @NotNull
    private Priority priority;

    private String oldTitle;

    private int storyId;

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(final Priority priority) {
        this.priority = priority;
    }

    public int getStoryId() {
        return storyId;
    }

    public void setStoryId(final int storyId) {
        this.storyId = storyId;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(final Score score) {
        this.score = score;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(final String owner) {
        this.owner = owner;
    }

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

    public Status getStatus() {
        return status;
    }

    public void setStatus(final Status status) {
        this.status = status;
    }

    public String getOldTitle() {
        return oldTitle;
    }

    public void setOldTitle(final String oldTitle) {
        this.oldTitle = oldTitle;
    }

}
