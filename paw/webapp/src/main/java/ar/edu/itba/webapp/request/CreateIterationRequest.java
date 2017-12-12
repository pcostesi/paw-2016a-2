package ar.edu.itba.webapp.request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CreateIterationRequest {

    private String start;
    private String end;

    @XmlElement
    public String getStart() {
        return start;
    }

    public void setStart(final String start) {
        this.start = start;
    }

    @XmlElement
    public String getEnd() {
        return end;
    }

    public void setEnd(final String end) {
        this.end = end;
    }
}
