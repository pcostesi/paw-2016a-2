package ar.edu.itba.persistence;

import java.util.List;

import ar.edu.itba.interfaces.BacklogDao;
import ar.edu.itba.models.BacklogItem;

public class BacklogHibernateDao implements BacklogDao{

	@Override
	public BacklogItem createBacklogItem(String title, String description, int projectId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean backlogItemExists(String title, int projectId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean backlogItemExists(int itemId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void deleteItem(int itemId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTitle(int itemId, String title) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateDescription(int itemId, String description) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<BacklogItem> getBacklogForProject(int projectId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getParent(int backlogItemId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BacklogItem getBacklogItemById(int itemId) {
		// TODO Auto-generated method stub
		return null;
	}

}
