package ar.edu.itba.webapp.model;

import java.util.List;

import ar.edu.itba.models.User;

public class UserList {
	private List<User> elements;

	public List<User> getElements() {
		return elements;
	}

	public UserList(List<User> elements) {
		this.elements = elements;
	}
}
