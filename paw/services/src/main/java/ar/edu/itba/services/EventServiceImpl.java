package ar.edu.itba.services;

import ar.edu.itba.interfaces.dao.LogEventDao;
import ar.edu.itba.interfaces.service.EventService;
import ar.edu.itba.models.Project;
import ar.edu.itba.models.User;
import ar.edu.itba.models.event.LogEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private LogEventDao logDao;

    @Override
    public void emit(LogEvent e) {
        logDao.insertEvent(e);
    }

    @Override
    public List<? extends LogEvent> getEventsForUser(User user) {
        return logDao.getEventsForActor(user);
    }

    @Override
    public List<? extends LogEvent> getEventsForActor(User user) {
        return logDao.getEventsForActor(user);
    }

    @Override
    public List<? extends LogEvent> getEventsForProject(Project project) {
        return logDao.getEventsForProject(project);
    }
}
