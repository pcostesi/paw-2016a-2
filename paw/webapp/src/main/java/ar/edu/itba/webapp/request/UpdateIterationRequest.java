package ar.edu.itba.webapp.request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;

@XmlRootElement
public class UpdateIterationRequest {

    private String beginDate;
    private String endDate;

    @XmlElement
    public LocalDate getBeginDate() {
        return LocalDate.parse(beginDate);
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    @XmlElement
    public LocalDate getEndDate() {
        return LocalDate.parse(endDate);
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
