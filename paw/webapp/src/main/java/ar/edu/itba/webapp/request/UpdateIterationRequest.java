package ar.edu.itba.webapp.request;

import org.springframework.cglib.core.Local;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;

@XmlRootElement
public class UpdateIterationRequest {

    private String begindate;
    private String enddate;

    @XmlElement
    public LocalDate getBegindate() {
        return LocalDate.parse(begindate);
    }

    public void setBegindate(String begindate) {
        this.begindate = begindate;
    }

    @XmlElement
    public LocalDate getEnddate() {
        return LocalDate.parse(enddate);
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }
}
