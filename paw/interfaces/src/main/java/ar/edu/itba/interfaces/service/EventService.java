package ar.edu.itba.interfaces.service;

import ar.edu.itba.models.Project;
import ar.edu.itba.models.User;
import ar.edu.itba.models.event.LogEvent;

import java.util.List;

public interface EventService {

    void emit(LogEvent e);

    List<? extends LogEvent> getEventsForUser(User user);

    List<? extends LogEvent> getEventsForActor(User user);

    List<? extends LogEvent> getEventsForProject(Project project);
}
