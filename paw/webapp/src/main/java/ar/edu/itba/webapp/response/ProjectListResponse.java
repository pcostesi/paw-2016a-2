package ar.edu.itba.webapp.response;
import ar.edu.itba.models.Project;
import ar.edu.itba.models.ProjectUser;
import ar.edu.itba.models.User;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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