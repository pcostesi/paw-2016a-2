package ar.edu.itba.models.event;

import ar.edu.itba.models.Project;
import ar.edu.itba.models.Story;
import ar.edu.itba.models.User;
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
@DiscriminatorValue("StoryCreated")
public class StoryCreatedEvent extends LogEvent {

    private static final long serialVersionUID = 6319711219335349022L;
    @XmlElement

    @ManyToOne(optional = false)
    private Story story;

    public StoryCreatedEvent() {
    }

    @Builder.Constructor
    public StoryCreatedEvent(Optional<User> actor, Optional<Project> project, LocalDateTime time, Story story) {
        super(actor, project, time);
        this.story = story;
    }
}
