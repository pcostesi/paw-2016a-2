package ar.edu.itba.services;

import ar.edu.itba.interfaces.dao.IterationDao;
import ar.edu.itba.interfaces.dao.StoryDao;
import ar.edu.itba.interfaces.dao.TaskDao;
import ar.edu.itba.interfaces.service.StoryService;
import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.Status;
import ar.edu.itba.models.Story;
import ar.edu.itba.models.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StoryServiceImpl implements StoryService {

    @Autowired
    private final
    StoryDao storyDao;

    @Autowired
    private final
    IterationDao iterationDao;

    @Autowired
    private final
    TaskDao taskDao;

    @Autowired
    StoryServiceImpl(final StoryDao storyDao, final IterationDao iterationDao, final TaskDao taskDao) {
        this.storyDao = storyDao;
        this.iterationDao = iterationDao;
        this.taskDao = taskDao;
    }

    @Override
    public Story create(final Iteration iteration, final String title) {
        if (iteration == null) {
            throw new IllegalArgumentException("Iteration can't be null");
        }

        if (title == null) {
            throw new IllegalArgumentException("Story title can't be null");
        }

        if (title.length() == 0) {
            throw new IllegalArgumentException("Story title can't be empty");
        }

        if (title.length() > 100) {
            throw new IllegalArgumentException("Story title can't be longer than 100 characters");
        }

        if (!iterationDao.iterationExists(iteration)) {
            throw new IllegalStateException("Iteration doesn't exist");
        }

        if (storyDao.storyExists(iteration, title)) {
            throw new IllegalStateException("There is another story with this title in this iteration");
        }

        if (iteration.status() == Status.COMPLETED) {
            throw new IllegalStateException("Can't add stories to a closed iteration");
        }

        return storyDao.createStory(iteration, title);
    }

    @Override
    public Story getById(final int storyId) {
        if (storyId < 0) {
            throw new IllegalArgumentException("Invalid story id");
        }

        final Story story = storyDao.getStoryById(storyId);

        if (story == null) {
            throw new IllegalStateException("Story doesn't exist");
        } else {
            return story;
        }
    }

    @Override
    public Map<Story, List<Task>> getStoriesWithTasksForIteration(final Iteration iteration) {
        if (iteration == null) {
            throw new IllegalArgumentException("Iteration can't be null");
        }

        if (!iterationDao.iterationExists(iteration)) {
            throw new IllegalStateException("Iteration doesn't exist");
        }

        final Map<Story, List<Task>> result = new HashMap<Story, List<Task>>();
        final List<Story> stories = storyDao.getStoriesForIteration(iteration);
        for (final Story story : stories) {
            result.put(story, taskDao.getTasksForStory(story));
        }
        return result;
    }

    @Override
    public Story setName(final Story story, final String title) {
        if (story == null) {
            throw new IllegalArgumentException("Story can't be null");
        }

        if (title == null) {
            throw new IllegalArgumentException("Story title can't be null");
        }

        if (title.length() == 0) {
            throw new IllegalArgumentException("Story title can't be empty");
        }

        if (title.length() > 100) {
            throw new IllegalArgumentException("Story title can't be longer than 100 characters");
        }

        if (!storyDao.storyExists(story)) {
            throw new IllegalStateException("Story doesn't exist");
        }

        if (story.title().equals(title)) {
            return story;
        }

        if (storyDao.storyExists(storyDao.getParent(story), title)) {
            throw new IllegalStateException("There is another story with this title in this iteration");
        }

        final Iteration iteration = storyDao.getParent(story);

        if (iteration.status() == Status.COMPLETED) {
            throw new IllegalStateException("Can't edit stories from a closed iteration");
        }

        storyDao.updateTitle(story, title);

        return storyDao.getStoryById(story.storyId());
    }

    @Override
    public void deleteStory(final Story story) {
        if (story == null) {
            throw new IllegalArgumentException("Story can't be null");
        }

        if (!storyDao.storyExists(story)) {
            throw new IllegalStateException("Story doesn't exist");
        }

        final Iteration iteration = storyDao.getParent(story);

        if (iteration.status() == Status.COMPLETED) {
            throw new IllegalStateException("Can't delete stories from a closed iteration");
        }

        storyDao.deleteStory(story);
    }

    @Override
    public Iteration getParent(final Story story) {
        if (story == null) {
            throw new IllegalArgumentException("Story can't be null");
        }

        if (!storyDao.storyExists(story)) {
            throw new IllegalStateException("Story doesn't exist");
        }

        return storyDao.getParent(story);
    }

    @Override
    public boolean storyExists(final Iteration iteration, final String title) {
        if (iteration == null) {
            throw new IllegalArgumentException("Iteration cant' be null");
        }

        if (!iterationDao.iterationExists(iteration)) {
            throw new IllegalStateException("Iteration doesn't exist");
        }

        return storyDao.storyExists(iteration, title);
    }

}