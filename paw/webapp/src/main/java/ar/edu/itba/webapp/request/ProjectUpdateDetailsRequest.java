package ar.edu.itba.webapp.request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ProjectUpdateDetailsRequest {

    private String name;
    private String description;
    private String code;

    @XmlElement
    public String[] getMembers() {
        return members;
    }

    public void setMembers(String[] members) {
        this.members = members;
    }

    private String[] members;

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(final String username) {
        this.name = username;
    }

    @XmlElement
    public String getDescription() {
        return description;
    }

    public void setDescription(final String password) {
        this.description = password;
    }

    @XmlElement
    public String getCode() {
        return code;
    }

    public void setCode(final String mail) {
        this.code = mail;
    }
}