package holidaysAndDates.client;

import holidaysAndDates.service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import java.util.Date;

@Component
@Scope("session")
@ManagedBean
public class MainPage {

    private Date dateFrom;
    private Date dateTo;
    private int result;

    @Autowired
    private ServiceImpl service;

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public void setService(ServiceImpl service) {
        this.service = service;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public void getDaysOffBetweenTwoDates() {
        result = service.getDaysOffBetweenTwoDates(dateFrom, dateTo);
    }

    public void clearNewOrderDialog() {
        setResult(0);
    }

}
