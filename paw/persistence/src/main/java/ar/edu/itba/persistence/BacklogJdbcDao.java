package ar.edu.itba.persistence;

import java.util.List;

import ar.edu.itba.interfaces.BacklogDao;
import ar.edu.itba.models.BacklogItem;

public class BacklogJdbcDao implements BacklogDao{

	@Override
	public BacklogItem createBacklogItem(String name, String description, int projectId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean backlogItemExists(int backlogITemId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean backlogItemExists(String name, String description, int ProjectID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void deleteItem(int itemId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<BacklogItem> getBacklogForProject(int projectId) {
		// TODO Auto-generated method stub
		return null;
	}

}
