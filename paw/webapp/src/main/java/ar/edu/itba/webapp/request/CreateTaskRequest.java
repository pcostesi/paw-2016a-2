package ar.edu.itba.webapp.request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CreateTaskRequest {

    private String title;
    private String description;
    private String stat;
    private String username;
    private String score;
    private String priority;

    @XmlElement
    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    @XmlElement
    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    @XmlElement
    public String getStat() {
        return stat;
    }

    public void setStat(final String stat) {
        this.stat = stat;
    }

    @XmlElement
    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    @XmlElement
    public String getScore() {
        return score;
    }

    public void setScore(final String score) {
        this.score = score;
    }

    @XmlElement
    public String getPriority() {
        return priority;
    }

    public void setPriority(final String priority) {
        this.priority = priority;
    }
}