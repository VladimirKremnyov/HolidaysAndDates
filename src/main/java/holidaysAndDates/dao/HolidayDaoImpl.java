package holidaysAndDates.dao;

import holidaysAndDates.entity.Holiday;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


@Repository
@Transactional
public class HolidayDaoImpl extends AbstractDao implements HolidayDao{

    @Override
    @SuppressWarnings("unchecked")
    public List<Holiday> getAllHolidays() {
        return (List<Holiday>)entityManager.createQuery("from Holiday").getResultList();
    }

}
