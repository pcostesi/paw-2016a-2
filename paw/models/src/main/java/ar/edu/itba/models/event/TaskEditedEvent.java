package ar.edu.itba.models.event;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import ar.edu.itba.models.Priority;
import ar.edu.itba.models.Score;
import ar.edu.itba.models.Status;
import ar.edu.itba.models.Task;

@Entity
public class TaskEditedEvent extends LogEvent {

    private static final long serialVersionUID = 8053618207538447394L;

    @ManyToOne
    private Task task;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = true)
    private Status status;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = true)
    private Score score;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = true)
    private Priority priority;
}
