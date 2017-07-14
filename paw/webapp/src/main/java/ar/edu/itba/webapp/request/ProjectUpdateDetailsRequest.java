package ar.edu.itba.webapp.request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ProjectUpdateDetailsRequest {

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String username) {
        this.name = username;
    }

    @XmlElement
    public String getDescription() {
        return description;
    }

    public void setDescription(String password) {
        this.description = password;
    }

    @XmlElement
    public String getCode() {
        return code;
    }

    public void setCode(String mail) {
        this.code = mail;
    }

    private String name;
    private String description;
    private String code;
}