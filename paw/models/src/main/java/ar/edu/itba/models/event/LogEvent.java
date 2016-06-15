package ar.edu.itba.models.event;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ar.edu.itba.models.Project;
import ar.edu.itba.models.User;

@Entity
@Inheritance
@Table(name = "event")
public abstract class LogEvent implements Serializable {

    private static final long serialVersionUID = -1185577548660797492L;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    private User actor;

    @Id
    @GeneratedValue
    @Column(name = "event_id", nullable = false, unique = true)
    private int eventId;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    private Project project;

    @Column(name = "time", nullable = false)
    private LocalDateTime time;

    public User getActor() {
        return actor;
    }

    public int getEventId() {
        return eventId;
    }

    public Project getProject() {
        return project;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setActor(final User actor) {
        this.actor = actor;
    }

    public void setProject(final Project project) {
        this.project = project;
    }

    public void setTime(final LocalDateTime time) {
        this.time = time;
    }
}
