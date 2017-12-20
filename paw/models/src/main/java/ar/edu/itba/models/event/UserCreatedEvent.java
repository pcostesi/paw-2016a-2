package ar.edu.itba.models.event;

import ar.edu.itba.models.Project;
import ar.edu.itba.models.User;
import org.immutables.builder.Builder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;
import java.util.Optional;


@XmlRootElement
@Entity
@DiscriminatorValue("UserCreated")
public class UserCreatedEvent extends LogEvent {

    private static final long serialVersionUID = 7752221278042305087L;

    @XmlElement
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User created;

    public UserCreatedEvent() {
    }

    @Builder.Constructor
    public UserCreatedEvent(Optional<User> actor, Optional<Project> project, LocalDateTime time, User created) {
        super(actor, project, time);
        this.created = created;
    }

    public User getCreated() {
        return created;
    }

    public void setCreated(final User created) {
        this.created = created;
    }
}
