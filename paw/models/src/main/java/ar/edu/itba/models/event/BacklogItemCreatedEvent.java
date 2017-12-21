package ar.edu.itba.models.event;

import ar.edu.itba.models.BacklogItem;
import ar.edu.itba.models.Project;
import ar.edu.itba.models.User;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.immutables.builder.Builder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;
import java.util.Optional;

@XmlRootElement
@Entity
@DiscriminatorValue("BacklogItemCreated")
public class BacklogItemCreatedEvent extends LogEvent {

    private static final long serialVersionUID = -2512725377464637574L;
    @XmlElement
    @ManyToOne()
    @OnDelete(action = OnDeleteAction.CASCADE)
    private BacklogItem item;

    public BacklogItemCreatedEvent() {
    }

    @Builder.Constructor
    public BacklogItemCreatedEvent(Optional<User> actor, Optional<Project> project, LocalDateTime time, BacklogItem item) {
        super(actor, project, time);
        this.item = item;
    }
}
