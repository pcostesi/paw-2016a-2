package ar.edu.itba.webapp.response;

import ar.edu.itba.models.Story;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class StoryListResponse {

    @XmlElement
    private Story[] stories;

    public Story[] getStories() {
        return stories;
    }

    public void setStories(final Story[] stories) {
        this.stories = stories;
    }
}
