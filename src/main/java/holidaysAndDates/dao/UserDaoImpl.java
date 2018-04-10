package holidaysAndDates.dao;

import holidaysAndDates.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.Query;

@Repository
@Transactional
public class UserDaoImpl extends AbstractDao implements UserDao {

    @Override
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public boolean validate(String userName, String password) {
        try {
            Query query = entityManager.createQuery("from User u WHERE u.userName =:userName AND u.password =:password", User.class);
            query.setParameter("userName", userName);
            query.setParameter("password", password);
            if (query.getSingleResult() != null) {
                return true;
            }
        } catch (NoResultException e) {
            System.err.println("No such user found in DB");
        }

        return false;
    }


}
