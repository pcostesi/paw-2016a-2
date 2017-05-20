package ar.edu.itba.models.event;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ar.edu.itba.models.Project;
import ar.edu.itba.models.User;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "event")
public abstract class LogEvent implements Serializable {

    private static final long serialVersionUID = -1185577548660797492L;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    private User actor;

    @Id
    @GeneratedValue
    @Column(name = "event_id", nullable = false, unique = true)
    private int eventId;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    private Project project;

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
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LogEvent other = (LogEvent) obj;
		if (actor == null) {
			if (other.actor != null)
				return false;
		} else if (!actor.equals(other.actor))
			return false;
		if (eventId != other.eventId)
			return false;
		if (project == null) {
			if (other.project != null)
				return false;
		} else if (!project.equals(other.project))
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		return true;
	}

	@Column(name = "time", nullable = false)
    private LocalDateTime time;

    public LogEvent() {
    	//Just because we need it for Hibernate
    }
    
    public User getActor() {
        return actor;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(final int eventId) {
        this.eventId = eventId;
    }

    public void setActor(final User actor) {
        this.actor = actor;
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
