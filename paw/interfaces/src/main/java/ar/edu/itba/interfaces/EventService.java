package ar.edu.itba.interfaces;

import ar.edu.itba.models.event.LogEvent;

public interface EventService {

    public void emit(LogEvent e);
}
