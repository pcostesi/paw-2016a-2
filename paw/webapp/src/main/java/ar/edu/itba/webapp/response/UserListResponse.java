package ar.edu.itba.webapp.response;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserListResponse {
    @XmlElement
    public String[] users;
}
