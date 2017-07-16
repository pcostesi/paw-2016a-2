package ar.edu.itba.webapp.response;
import ar.edu.itba.models.Project;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class ProjectListResponse {
    public Project[] getProjects() {
        return projects;
    }

    public void setProjects(Project[] projects) {
        this.projects = projects;
    }

    @XmlElement
    private Project[] projects;

    public void setProjectsForResponse(List<Project> projectList){
        this.projects = projectList.toArray(new Project[projectList.size()]);
    }
}