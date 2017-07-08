package ar.edu.itba.webapp.response;

import ar.edu.itba.models.User;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserListResponse {
    @XmlElement
    public User[] users;
}
