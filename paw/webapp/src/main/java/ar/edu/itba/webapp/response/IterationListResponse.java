package ar.edu.itba.webapp.response;

import ar.edu.itba.models.Iteration;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class IterationListResponse {

    @XmlElement
    private Iteration[] iterations;

    public Iteration[] getIterations() {
        return iterations;
    }

    public void setIterations(Iteration[] iterations) {
        this.iterations = iterations;
    }
}
