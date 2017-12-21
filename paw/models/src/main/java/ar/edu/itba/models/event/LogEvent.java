package ar.edu.itba.models.event;

import ar.edu.itba.models.Project;
import ar.edu.itba.models.User;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;


/*
* BEWARE CODER: Add your subclass here or it won't get serialized.
* */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "event")
@DiscriminatorColumn(name = "type")
@DiscriminatorValue("BaseEvent")
@XmlSeeAlso( {
 BacklogItemCreatedEvent.class,
 IterationCreatedEvent.class,
 ProjectCreatedEvent.class,
 StoryCreatedEvent.class,
 TaskCreatedEvent.class,
 TaskEditedEvent.class,
 UserCreatedEvent.class
})
public class LogEvent implements Serializable {

    private static final long serialVersionUID = -1185577548660797492L;

    @XmlElement
    @Column(updatable = false, insertable = false)
    private String type;

    @XmlElement
    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User actor;

    @XmlElement
    @Id
    @GeneratedValue
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Column(name = "event_id", nullable = false, unique = true)
    private int eventId;

    @XmlElement
    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Project project;

    @XmlElement
    @Column(name = "time", nullable = false)
    private LocalDateTime time;

    public LogEvent() {
        //Just because we need it for Hibernate
    }

    public LogEvent(Optional<User> actor, Optional<Project> project, LocalDateTime time) {
        this.actor = actor.orElse(null);
        this.project = project.orElse(null);
        this.time = time;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((actor == null) ? 0 : actor.hashCode());
        result = prime * result + eventId;
        result = prime * result + ((project == null) ? 0 : project.hashCode());
        result = prime * result + ((time == null) ? 0 : time.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LogEvent other = (LogEvent) obj;
        if (actor == null) {
            if (other.actor != null) {
                return false;
            }
        } else if (!actor.equals(other.actor)) {
            return false;
        }
        if (eventId != other.eventId) {
            return false;
        }
        if (project == null) {
            if (other.project != null) {
                return false;
            }
        } else if (!project.equals(other.project)) {
            return false;
        }
        if (time == null) {
            if (other.time != null) {
                return false;
            }
        } else if (!time.equals(other.time)) {
            return false;
        }
        return true;
    }

    public User getActor() {
        return actor;
    }

    public void setActor(final User actor) {
        this.actor = actor;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(final int eventId) {
        this.eventId = eventId;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(final Project project) {
        this.project = project;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(final LocalDateTime time) {
        this.time = time;
    }

}
