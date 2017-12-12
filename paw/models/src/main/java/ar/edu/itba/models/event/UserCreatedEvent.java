package ar.edu.itba.models.event;

import ar.edu.itba.models.User;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class UserCreatedEvent extends LogEvent {

    private static final long serialVersionUID = 7752221278042305087L;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User created;

    public UserCreatedEvent() {
    }

    public User getCreated() {
        return created;
    }

    public void setCreated(final User created) {
        this.created = created;
    }
}
