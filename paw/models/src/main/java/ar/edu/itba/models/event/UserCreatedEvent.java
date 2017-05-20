package ar.edu.itba.models.event;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import ar.edu.itba.models.User;

@Entity
public class UserCreatedEvent extends LogEvent {

    private static final long serialVersionUID = 7752221278042305087L;

    public UserCreatedEvent() {
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User created;

    public User getCreated() {
        return created;
    }

    public void setCreated(final User created) {
        this.created = created;
    }
}
