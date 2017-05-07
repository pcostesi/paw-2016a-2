package ar.edu.itba.interfaces.dao;

import java.time.LocalDate;
import java.util.List;

import ar.edu.itba.models.Project;
import ar.edu.itba.models.User;
import ar.edu.itba.models.event.LogEvent;

public interface LogEventDao {

    public LogEvent insertEvent(LogEvent e);

    public void removeEvent(LogEvent e);

    public List<? extends LogEvent> getEventsForProject(Project project);

    public List<? extends LogEvent> getEventsForActor(User user);

    public List<? extends LogEvent> getEventsForRange(final LocalDate start, final LocalDate end);
}
