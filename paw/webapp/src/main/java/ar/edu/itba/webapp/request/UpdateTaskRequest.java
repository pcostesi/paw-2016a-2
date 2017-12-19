package ar.edu.itba.webapp.request;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UpdateTaskRequest {

    private String title;
    private String description;
    private String stat;
    private String username;
    private String scoreN;
    private String priorityN;

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
    public String getScoreN() {
        return scoreN;
    }

    public void setScoreN(final String scoreN) {
        this.scoreN = scoreN;
    }

    @XmlElement
    public String getPriorityN() {
        return priorityN;
    }

    public void setPriorityN(final String priorityN) {
        this.priorityN = priorityN;
    }
}
