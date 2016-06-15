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
    public LogEvent insertEvent(final LogEvent event) {
        try {
            em.persist(event);
            em.flush();
            return event;
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to persist event");
        }
    }

    @Override
    public void removeEvent(final LogEvent event) {
        try {
            final Query query = em.createQuery("delete from Event event where event.eventId = :eventId");
            query.setParameter("eventId", event.getEventId());
            query.executeUpdate();
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to delete event");
        }
    }

    @Override
    @Transactional
    public List<LogEvent> getEventsForProject(final Project project) {
        try {
            final TypedQuery<LogEvent> query = em.createQuery(
                    "from LogEvent event where event.project = :project order by event.time", LogEvent.class);
            query.setParameter("project", project);
            return query.getResultList();
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to get events for this project");
        }
    }

    @Override
    public List<LogEvent> getEventsForActor(final User user) {
        try {
            final TypedQuery<LogEvent> query = em.createQuery(
                    "from LogEvent event where event.actor = :actor order by event.time", LogEvent.class);
            query.setParameter("actor", user);
            return query.getResultList();
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to get events for this actor");
        }
    }

    @Override
    public List<LogEvent> getEventsForRange(final LocalDate start, final LocalDate end) {
        try {
            final TypedQuery<LogEvent> query = em.createQuery(
                    "from LogEvent event where event.time < :endDate and event.time >= startDate order by event.time",
                    LogEvent.class);
            final LocalTime zero = LocalTime.of(0, 0);
            query.setParameter("startDate", LocalDateTime.of(start, zero));
            query.setParameter("endDate", LocalDateTime.of(end, zero));
            return query.getResultList();
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to get events on this range");
        }
    }

}
