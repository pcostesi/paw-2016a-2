package ar.edu.itba.models.event;

import ar.edu.itba.models.Priority;
import ar.edu.itba.models.Score;
import ar.edu.itba.models.Status;
import ar.edu.itba.models.Task;

import javax.persistence.*;

@Entity
public class TaskEditedEvent extends LogEvent {

    private static final long serialVersionUID = 8053618207538447394L;

    @ManyToOne
    private Task task;

    @Enumerated(EnumType.ORDINAL)
    @Column()
    private Status status;

    @Enumerated(EnumType.ORDINAL)
    @Column()
    private Score score;

    @Enumerated(EnumType.ORDINAL)
    @Column()
    private Priority priority;
}
