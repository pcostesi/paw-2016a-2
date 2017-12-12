package ar.edu.itba.models.event;

import ar.edu.itba.models.Iteration;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class IterationCreatedEvent extends LogEvent {

    private static final long serialVersionUID = -2688266605827834699L;

    @ManyToOne(optional = false)
    private Iteration iteration;
}
