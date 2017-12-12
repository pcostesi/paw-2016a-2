package ar.edu.itba.interfaces.service;

import ar.edu.itba.models.Project;
import ar.edu.itba.models.User;
import ar.edu.itba.models.event.LogEvent;

public interface EventService {

    void emit(LogEvent e);

    LogEvent getEventsForUser(User user);

    LogEvent getEventsForActor(User user);

    LogEvent getEventsForProject(Project project);
}
