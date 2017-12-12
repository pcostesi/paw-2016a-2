package ar.edu.itba.models.event;

import ar.edu.itba.models.Task;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class TaskCreatedEvent extends LogEvent {

    private static final long serialVersionUID = -2512725377464637574L;

    @ManyToOne(optional = false)
    private Task task;
}
