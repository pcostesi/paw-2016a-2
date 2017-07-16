package ar.edu.itba.webapp.response;

import ar.edu.itba.models.BacklogItem;
import ar.edu.itba.models.Story;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class StoryListResponse {

    public Story[] getStories() {
        return stories;
    }

    public void setStories(Story[] stories) {
        this.stories = stories;
    }

    @XmlElement
    private Story[] stories;
}
