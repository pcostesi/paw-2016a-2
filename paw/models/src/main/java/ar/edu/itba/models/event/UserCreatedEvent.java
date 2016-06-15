package ar.edu.itba.models.event;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import ar.edu.itba.models.User;

@Entity
public class UserCreatedEvent extends LogEvent {

    private static final long serialVersionUID = 7752221278042305087L;

    @ManyToOne
    private User created;
}
