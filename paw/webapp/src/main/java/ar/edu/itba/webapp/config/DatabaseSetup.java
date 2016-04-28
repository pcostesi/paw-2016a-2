package ar.edu.itba.webapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import ar.edu.itba.interfaces.IterationService;
import ar.edu.itba.interfaces.ProjectService;
import ar.edu.itba.interfaces.StoryService;
import ar.edu.itba.interfaces.TaskService;


@Component
public class DatabaseSetup {
	@Autowired
	private ProjectService ps;

	@Autowired
	private IterationService is;

	@Autowired
	private TaskService ts;
	
	@Autowired
	private StoryService ss;

	private boolean initialized = false;
	
	@EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
		if (initialized) {
			return;
		}
//        Project project = ps.createProject("Scrumlr", "This is a project", "scrumlr");
//        Iteration iteration = is.createIteration(project, new Date(), new Date());
//        Story story = ss.create(iteration, "This is a story title");
//        Task task = ts.createTask(story, "This is a task title", "This is a task description");
        initialized = true;
    }
}
