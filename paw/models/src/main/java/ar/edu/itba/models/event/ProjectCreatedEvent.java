package ar.edu.itba.models.event;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import ar.edu.itba.models.Project;

@Entity
public class ProjectCreatedEvent extends LogEvent {

    private static final long serialVersionUID = 7843078553409559449L;

    @ManyToOne(optional = false)
    private Project project;
}
