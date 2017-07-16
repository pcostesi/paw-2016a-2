package ar.edu.itba.webapp.response;

import ar.edu.itba.models.BacklogItem;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class BacklogListResponse {
    public BacklogItem[] getBacklog() {
        return backlog;
    }

    public void setBacklog(BacklogItem[] backlog) {
        this.backlog = backlog;
    }

    @XmlElement
    private BacklogItem[] backlog;
}
