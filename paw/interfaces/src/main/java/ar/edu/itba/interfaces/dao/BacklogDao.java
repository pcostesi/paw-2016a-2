package ar.edu.itba.interfaces.dao;

import ar.edu.itba.models.BacklogItem;
import ar.edu.itba.models.Project;

import java.util.List;

public interface BacklogDao {

    BacklogItem createBacklogItem(final String title, final String description, final Project project);

    boolean backlogItemExists(final Project project, final String title);

    boolean backlogItemExists(final BacklogItem backlogItem);

    void deleteItem(final BacklogItem backlogItem);

    void updateTitle(final BacklogItem backlogItem, final String title);

    void updateDescription(final BacklogItem backlogItem, final String description);

    List<BacklogItem> getBacklogForProject(final Project project);

    Project getParent(final BacklogItem backlogItem);

    BacklogItem getBacklogItemById(final int backlogItemId);

}
