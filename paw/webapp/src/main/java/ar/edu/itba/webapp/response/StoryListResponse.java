package ar.edu.itba.webapp.response;

import ar.edu.itba.models.Story;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StoryListResponse {

    @XmlElement
    public Story[] stories;
}
