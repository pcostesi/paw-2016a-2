package ar.edu.itba.models.event;

import ar.edu.itba.models.Story;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class StoryCreatedEvent extends LogEvent {

    private static final long serialVersionUID = 6319711219335349022L;

    @ManyToOne(optional = false)
    private Story story;
}
