package ar.edu.itba.webapp.response;

import ar.edu.itba.models.event.LogEvent;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


@XmlRootElement
public class LogEventListResponse {
    @XmlElement
    private List<? extends LogEvent> events;

    public List<? extends LogEvent> getEvents() {
        return events;
    }

    public void setEvents(List<? extends LogEvent> events) {
        this.events = events;
    }
}
