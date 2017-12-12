package ar.edu.itba.webapp.request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserUpdateDetailsRequest {
    private String username;
    private String password;
    private String mail;

    @XmlElement
    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    @XmlElement
    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    @XmlElement
    public String getMail() {
        return mail;
    }

    public void setMail(final String mail) {
        this.mail = mail;
    }
}
