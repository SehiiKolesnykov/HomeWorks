package hw13.controller;

import hw13.model.User;
import hw13.service.UserService;

public class UserController {

    private final UserService userService;

    public UserController() {
        this.userService = new UserService();
    }

    public boolean authenticate(String email, String password) {
        return userService.authenticate(email, password);
    }

    public void registerUser(String name, String surname, String email, String password) {
            userService.registerUser(name, surname, email, password);
    }

    public void addBooking (String email, String bookingId) {
        User user = getUserByEmail(email);
        if (user != null) {
            user.addBooking(bookingId);
            userService.updateUser(email, user);
        }
    }

    public void removeBooking (String email, String bookingId) {
        User user = getUserByEmail(email);
        if (user != null ) {
            user.removeBooking(bookingId);
            userService.updateUser(email, user);
        }
    }

    public User getUserByEmail(String email) {
        return userService.getUserByEmail(email);
    }
}
