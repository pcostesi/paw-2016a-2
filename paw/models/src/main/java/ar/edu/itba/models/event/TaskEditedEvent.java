package ar.edu.itba.models.event;

import ar.edu.itba.models.Priority;
import ar.edu.itba.models.Project;
import ar.edu.itba.models.Score;
import ar.edu.itba.models.Status;
import ar.edu.itba.models.Task;
import ar.edu.itba.models.User;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.immutables.builder.Builder;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;
import java.util.Optional;


@XmlRootElement
@Entity
@DiscriminatorValue("TaskEdited")
public class TaskEditedEvent extends LogEvent {

    private static final long serialVersionUID = 8053618207538447394L;

    @XmlElement
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Task task;

    @XmlElement
    @Enumerated(EnumType.ORDINAL)
    @Column()
    private Status status;

    @XmlElement
    @Enumerated(EnumType.ORDINAL)
    @Column()
    private Score score;

    @XmlElement
    @Enumerated(EnumType.ORDINAL)
    @Column()
    private Priority priority;

    public TaskEditedEvent() {
    }

    @Builder.Constructor
    public TaskEditedEvent(Optional<User> actor, Optional<Project> project, LocalDateTime time, Task task, Status status, Score score, Priority priority) {
        super(actor, project, time);
        this.task = task;
        this.status = status;
        this.score = score;
        this.priority = priority;
    }
}
