package ar.edu.itba.interfaces.dao;

import ar.edu.itba.models.Project;
import ar.edu.itba.models.User;
import ar.edu.itba.models.event.LogEvent;

import java.time.LocalDate;
import java.util.List;

public interface LogEventDao {

    LogEvent insertEvent(LogEvent e);

    void removeEvent(LogEvent e);

    List<? extends LogEvent> getEventsForProject(Project project);

    List<? extends LogEvent> getEventsForActor(User user);

    List<? extends LogEvent> getEventsForRange(final LocalDate start, final LocalDate end);
}
