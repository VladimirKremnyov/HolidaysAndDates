package holidaysAndDates.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "holidays")
public class Holiday extends EntityParent {


    private Date date;

    @Column(name = "holiday_date")
    @Temporal(TemporalType.DATE)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
