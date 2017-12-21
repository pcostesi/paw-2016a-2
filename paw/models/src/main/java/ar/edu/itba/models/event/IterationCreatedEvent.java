package ar.edu.itba.models.event;

import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.Project;
import ar.edu.itba.models.User;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.immutables.builder.Builder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;
import java.util.Optional;

@XmlRootElement
@Entity
@DiscriminatorValue("IterationCreated")
public class IterationCreatedEvent extends LogEvent {

    private static final long serialVersionUID = -2688266605827834699L;

    @ManyToOne()
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Iteration iteration;

    @Builder.Constructor
    public IterationCreatedEvent(Optional<User> actor, Optional<Project> project, LocalDateTime time, Iteration iteration) {
        super(actor, project, time);
        this.iteration = iteration;
    }

    public IterationCreatedEvent() {
    }


}
