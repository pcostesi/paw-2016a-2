package ar.edu.itba.webapp.response;

import ar.edu.itba.models.Project;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class ProjectListResponse {
    @XmlElement
    private Project[] projects;

    public Project[] getProjects() {
        return projects;
    }

    public void setProjects(final Project[] projects) {
        this.projects = projects;
    }

    public void setProjectsForResponse(final List<Project> projectList) {
        this.projects = projectList.toArray(new Project[projectList.size()]);
    }
}