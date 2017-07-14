package ar.edu.itba.webapp.response;

import ar.edu.itba.models.Task;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TaskListResponse {

    @XmlElement
    public Task[] tasks;
}
