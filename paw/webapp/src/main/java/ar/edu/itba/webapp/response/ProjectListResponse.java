package ar.edu.itba.webapp.response;
import ar.edu.itba.models.Project;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ProjectListResponse {

    @XmlElement
    public Project[] projects;
}
