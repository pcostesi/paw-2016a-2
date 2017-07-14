package ar.edu.itba.webapp.request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CreateIterationRequest {

    @XmlElement
    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    private String start;

    @XmlElement
    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    private String end;
}
