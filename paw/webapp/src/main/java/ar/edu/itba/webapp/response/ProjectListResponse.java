package ar.edu.itba.webapp.response;
import ar.edu.itba.models.Project;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class ProjectListResponse {

    @XmlElement
    public Project.ProjectDTO[] projects;

    public ProjectListResponse(List<Project> projectList){
        List<Project.ProjectDTO> projects = new ArrayList<>();
        projectList.forEach(p -> projects.add(p.toDTO()));
        this.projects = projects.toArray(new Project.ProjectDTO[]{});
    }
}