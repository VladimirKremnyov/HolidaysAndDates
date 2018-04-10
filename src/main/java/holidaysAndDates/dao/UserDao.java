package holidaysAndDates.dao;

import holidaysAndDates.entity.User;

public interface UserDao {

    void addUser(User user);

    boolean validate(String userName, String password);

}
