package ar.edu.itba.models.event;

import ar.edu.itba.models.Project;
import ar.edu.itba.models.User;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.immutables.builder.Builder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;
import java.util.Optional;


@XmlRootElement
@Entity
@DiscriminatorValue("ProjectCreated")
public class ProjectCreatedEvent extends LogEvent {

    private static final long serialVersionUID = 7843078553409559449L;

    public ProjectCreatedEvent() {
    }

    @Builder.Constructor
    public ProjectCreatedEvent(Optional<User> actor, Optional<Project> project, LocalDateTime time) {
        super(actor, project, time);
    }
}
