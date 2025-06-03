package hw13.service;

import hw13.controller.FlightController;
import hw13.dao.UserDao;
import hw13.dao.UserDaoImpl;
import hw13.exception.InvalidInputException;
import hw13.model.Flight;
import hw13.model.User;
import hw13.util.LoggerUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static hw13.util.Texts.*;

public class UserService{
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private final UserDao userDao;
    private final FlightController flightController;

    public UserService() {
        this.userDao = new UserDaoImpl();
        this.flightController = new FlightController();
        initializeUsers();
    }

    public boolean authenticate(String email, String password) {
        User user = userDao.getUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            updateTicketsStatus(user);
            userDao.updateUser(email, user);
            return true;
        }
        LoggerUtil.log(String.format(ERROR_AUTH, email));
        return false;
    }

    public void registerUser(String name, String surname, String email, String password) {
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new InvalidInputException(INVALID_EMAIL);
        }
        if (password.length() < 6 ) {
            throw new InvalidInputException(INVALID_PASSWORD_TO_SHORT);
        }
        if (userDao.getUserByEmail(email) != null) {
            throw new InvalidInputException(EMAIL_ALREADY_EXIST);
        }
        User user = new User(name, surname, email, password);
        LoggerUtil.log(String.format(REGISTER_USER, email));
        userDao.updateUser(email, user);
        LoggerUtil.log(String.format(REGISTER_SUCCESS, email));
    }

    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    public void updateUser(String email, User user) {
        userDao.updateUser(email, user);
    }

    private void updateTicketsStatus(User user) {
        List<String> toMove = user.getBookings().stream()
                .filter(bookingIDd -> {
                    try {
                        Flight flight = flightController.getFlightById(bookingIDd);
                        return flight.getDepartureTime().isBefore(LocalDateTime.now());
                    } catch (Exception e) {
                        return false;
                    }
                })
                .collect(Collectors.toList());

        user.getBookings().removeAll(toMove);
    }

    private void initializeUsers() {
        List<User> users = userDao.getAllUsers();
        LoggerUtil.log(String.format(LOAD_USERS_AFTER_INI, users.size()));
        if (users.isEmpty()) {
            users.add(new User("Test", "Test", "test@test.com", "test123"));
            users.add(new User("John", "Smith", "john@gmail.com", "password"));
            LoggerUtil.log(DEFAULT_USERS);
            userDao.saveUsers(users);
        }
    }
}
