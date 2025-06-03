package hw13.dao;

import hw13.model.User;
import hw13.util.DataStorage;
import hw13.util.LoggerUtil;

import java.util.ArrayList;
import java.util.List;

import static hw13.util.Texts.*;

public class UserDaoImpl implements UserDao{

    @Override
    public User getUserByEmail(String email) {
        User user =  getAllUsers().stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst()
                .orElse(null);
        LoggerUtil.log(String.format(FETCH_USER, email, (user != null)));
        return user;
    }

    @Override
    public void updateUser(String email, User user) {
        List<User> users = getAllUsers();
        LoggerUtil.log(String.format(UPDATE_USER, email, users.size()));
        users.removeIf(u -> u.getEmail().equals(email));
        users.add(user);
        DataStorage.saveUsers(users);
        LoggerUtil.log(String.format(UPDATE_USER, email, users.size()));
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = DataStorage.loadUsers();
        LoggerUtil.log(String.format(LOAD_USERS, users.size()));
        return users.isEmpty() ? new ArrayList<>() : users;
    }

    @Override
    public void saveUsers(List<User> users) {
        LoggerUtil.log(String.format(SAVING_USERS, users.size()));
        DataStorage.saveUsers(users);
    }
}
