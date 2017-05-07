package ar.edu.itba.models.event;

import javax.persistence.Entity;

@Entity
public class ProjectCreatedEvent extends LogEvent {

    private static final long serialVersionUID = 7843078553409559449L;

    public ProjectCreatedEvent() {
    }
}
