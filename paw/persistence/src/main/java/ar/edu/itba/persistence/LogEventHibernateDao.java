package ar.edu.itba.persistence;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.interfaces.dao.LogEventDao;
import ar.edu.itba.models.Project;
import ar.edu.itba.models.User;
import ar.edu.itba.models.event.LogEvent;

@Primary
@Repository
public class LogEventHibernateDao implements LogEventDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public LogEvent insertEvent(final LogEvent event) {
        em.persist(event);
        em.flush();
        return event;
    }

    @Override
    public void removeEvent(final LogEvent event) {
        final Query query = em.createQuery("delete from Event event where event.eventId = :eventId");
        query.setParameter("eventId", event.getEventId());
        query.executeUpdate();

    }

    @Override
    public List<? extends LogEvent> getEventsForProject(final Project project) {
        final TypedQuery<LogEvent> query = em.createQuery(
                "from LogEvent event where event.project = :project order by event.time", LogEvent.class);
        query.setParameter("project", project);
        return query.getResultList();

    }

    @Override
    public List<? extends LogEvent> getEventsForActor(final User user) {
        final TypedQuery<LogEvent> query = em.createQuery(
                "from LogEvent event where event.actor = :actor order by event.time", LogEvent.class);
        query.setParameter("actor", user);
        return query.getResultList();

    }

    @Override
    public List<? extends LogEvent> getEventsForRange(final LocalDate start, final LocalDate end) {
        final TypedQuery<LogEvent> query = em.createQuery(
                "from LogEvent event where event.time < :endDate and event.time >= startDate order by event.time",
                LogEvent.class);
        final LocalTime zero = LocalTime.of(0, 0);
        query.setParameter("startDate", LocalDateTime.of(start, zero));
        query.setParameter("endDate", LocalDateTime.of(end, zero));
        return query.getResultList();

    }

}
