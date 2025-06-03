package hw13.dao;

import hw13.model.User;

import java.util.List;

public interface UserDao {
    User getUserByEmail(String email);
    void updateUser(String email, User user);
    List<User> getAllUsers();
    void saveUsers(List<User> users);
}
