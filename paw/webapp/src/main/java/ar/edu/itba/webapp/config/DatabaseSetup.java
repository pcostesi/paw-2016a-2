package ar.edu.itba.webapp.config;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import ar.edu.itba.interfaces.IterationService;
import ar.edu.itba.interfaces.ProjectService;
import ar.edu.itba.interfaces.TaskService;


@Component
public class DatabaseSetup {
	@Autowired
	private ProjectService ps;

	@Autowired
	private IterationService is;

	@Autowired
	private TaskService ts;

	private boolean initialized = false;
	
	@EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
		if (initialized) {
			return;
		}
        ps.createProject("scrumlr", "This is a test project");
        is.createIteration("scrumlr", new Date(), new Date());
        ts.createTask("scrumlr", 1, "My first task", "Hello task!");
        initialized = true;
    }
}
