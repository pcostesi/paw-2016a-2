package ar.edu.itba.interfaces.service;

import ar.edu.itba.models.Project;
import ar.edu.itba.models.User;
import ar.edu.itba.models.event.LogEvent;

public interface EventService {

    public void emit(LogEvent e);

    public LogEvent getEventsForUser(User user);

    public LogEvent getEventsForActor(User user);

    public LogEvent getEventsForProject(Project project);
}
