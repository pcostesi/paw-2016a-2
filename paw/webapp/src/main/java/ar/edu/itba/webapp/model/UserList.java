package ar.edu.itba.webapp.model;

import ar.edu.itba.models.User;

import java.util.List;

class UserList {
    private final List<User> elements;

    public UserList(final List<User> elements) {
        this.elements = elements;
    }

    public List<User> getElements() {
        return elements;
    }
}
