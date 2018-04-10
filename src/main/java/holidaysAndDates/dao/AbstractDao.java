package holidaysAndDates.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class AbstractDao {

    @PersistenceContext
    EntityManager entityManager;

}