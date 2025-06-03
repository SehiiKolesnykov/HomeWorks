package hw13;

import hw13.controller.FlightController;
import hw13.controller.UserController;
import hw13.dao.FlightDao;
import hw13.dao.UserDao;
import hw13.exception.InvalidInputException;
import hw13.model.Flight;
import hw13.model.User;
import hw13.service.FlightService;
import hw13.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserControllerTest {
    private UserDao userDao;
    private FlightDao flightDao;
    private FlightService flightService;
    private FlightController flightController;
    private UserService userService;
    private UserController userController;


    @BeforeEach
    void setUp() {
        userDao = Mockito.mock(UserDao.class);
        flightDao = Mockito.mock(FlightDao.class);
        flightService = new FlightService();
        flightController = new FlightController();
        userService = new UserService();
        userController = new UserController();

        try {
            java.lang.reflect.Field daoField = FlightService.class.getDeclaredField("flightDao");
            daoField.setAccessible(true);
            daoField.set(flightService, flightDao);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            java.lang.reflect.Field serviceField = FlightController.class.getDeclaredField("flightService");
            serviceField.setAccessible(true);
            serviceField.set(flightController, flightService);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            java.lang.reflect.Field userDaoField = UserService.class.getDeclaredField("userDao");
            userDaoField.setAccessible(true);
            userDaoField.set(userService, userDao);

            java.lang.reflect.Field controllerField = UserService.class.getDeclaredField("flightController");
            controllerField.setAccessible(true);
            controllerField.set(userService, flightController);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            java.lang.reflect.Field userControllerField = UserController.class.getDeclaredField("userService");
            userControllerField.setAccessible(true);
            userControllerField.set(userController, userService);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        when(userDao.getAllUsers()).thenReturn(new ArrayList<>());
        doNothing().when(userDao).saveUsers(anyList());
    }

    @Test
    void authenticateTest() {
        String email = "test@test.com";
        String password = "password";
        User user = new User("Name", "Surname", email, password);

        when(userDao.getUserByEmail(email)).thenReturn(user);

        boolean result = userController.authenticate(email, password);

        assertTrue(result);
        verify(userDao).getUserByEmail(email);
        verify(userDao).updateUser(eq(email), any(User.class));
    }

    @Test
    void authenticateInvalidTest() {
        String email = "test@test.com";
        String password = "wrong";
        User user = new User("Name", "Surname", email, "password");

        when(userDao.getUserByEmail(email)).thenReturn(user);

        boolean result = userController.authenticate(email, password);

        assertFalse(result);
        verify(userDao).getUserByEmail(email);
        verify(userDao, never()).updateUser(anyString(), any());
    }

    @Test
    void authenticateNoUserTest() {
        String email = "test@test.com";
        String password = "password";

        when(userDao.getUserByEmail(email)).thenReturn(null);

        boolean result = userController.authenticate(email, password);

        assertFalse(result);
        verify(userDao).getUserByEmail(email);
        verify(userDao, never()).updateUser(anyString(), any());
    }

    @Test
    void authenticateWithExpiredBookingTest() {
        String email = "test@test.com";
        String password = "password";
        String bookingId = "0001";
        User user = new User("Name", "Surname", email, password);
        user.addBooking(bookingId);
        Flight flight = new Flight(bookingId, "Kyiv", "Lviv", LocalDateTime.now().minusDays(2), 100);

        when(userDao.getUserByEmail(email)).thenReturn(user);
        when(flightDao.getFlightById(bookingId)).thenReturn(Optional.of(flight));

        boolean result = userController.authenticate(email, password);

        assertTrue(result);
        assertTrue(user.getBookings().contains(bookingId));
        verify(userDao).getUserByEmail(email);
        verify(userDao).updateUser(eq(email), any(User.class));
        verify(flightDao).getFlightById(bookingId);
    }

    @Test
    void registerUserTest() {
        String name = "Name";
        String surname = "Surname";
        String email = "test@test.com";
        String password = "password";
        when(userDao.getUserByEmail(email)).thenReturn(null);

        userController.registerUser(name, surname, email, password);

        verify(userDao).updateUser(eq(email), argThat(user ->
                        user.getName().equals(name) &&
                        user.getSurname().equals(surname) &&
                        user.getEmail().equals(email) &&
                        user.getPassword().equals(password)
        ));
    }

    @Test
    void registerUserInvalidEmailTest() {
        String name = "Name";
        String surname = "Surname";
        String email = "invalid";
        String password = "password";
        assertThrows(InvalidInputException.class,
                () -> userController.registerUser(name, surname, email, password));
        verify(userDao, never()).updateUser(anyString(), any());
    }

    @Test
    void registerUserShortPasswordTest() {
        String name = "Name";
        String surname = "Surname";
        String email = "test@test.com";
        String password = "pass";
        assertThrows(InvalidInputException.class,
                () -> userController.registerUser(name, surname, email, password));
        verify(userDao, never()).updateUser(anyString(), any());
    }

    @Test
    void registerUserExistedEmailTest() {
        String name = "Name";
        String surname = "Surname";
        String email = "test@test.com";
        String password = "password";
        User existedUser = new User("New", "User", email, "passwordOld");
        when(userDao.getUserByEmail(email)).thenReturn(existedUser);

        assertThrows(InvalidInputException.class,
                () -> userController.registerUser(name, surname, email, password));
        verify(userDao, never()).updateUser(anyString(), any());
        verify(userDao).getUserByEmail(email);
    }

    @Test
    void addBookingTest() {
        String name = "Name";
        String surname = "Surname";
        String email = "test@test.com";
        String password = "password";
        String bookingId = "0001";
        User user = new User(name, surname, email, password);
        when(userDao.getUserByEmail(email)).thenReturn(user);

        userController.addBooking(email, bookingId);

        assertTrue(user.getBookings().contains(bookingId));
        verify(userDao).getUserByEmail(email);
        verify(userDao).updateUser(email, user);
    }

    @Test
    void addBookingNoExistedUserTest() {
        String email = "test@test.com";
        String bookingId = "0001";
        when(userDao.getUserByEmail(email)).thenReturn(null);

        userController.addBooking(email, bookingId);

        verify(userDao).getUserByEmail(email);
        verify(userDao, never()).updateUser(anyString(), any());
    }

    @Test
    void removeBookingTest() {
        String name = "Name";
        String surname = "Surname";
        String email = "test@test.com";
        String password = "password";
        String bookingId = "0001";
        User user = new User(name, surname, email, password);
        when(userDao.getUserByEmail(email)).thenReturn(user);

        userController.removeBooking(email, bookingId);

        assertFalse(user.getBookings().contains(bookingId));
        verify(userDao).updateUser(email,user);
        verify(userDao).getUserByEmail(email);
    }

    @Test
    void removeBookingNoExistedUserTest() {
        String email = "test@test.com";
        String bookingId = "0001";
        when(userDao.getUserByEmail(email)).thenReturn(null);

        userController.removeBooking(email, bookingId);

        verify(userDao).getUserByEmail(email);
        verify(userDao, never()).updateUser(anyString(), any());
    }

    @Test
    void getUserByEmailTest() {
        String name = "Name";
        String surname = "Surname";
        String email = "test@test.com";
        String password = "password";
        User user = new User(name, surname, email, password);
        when(userDao.getUserByEmail(email)).thenReturn(user);

        User result = userController.getUserByEmail(email);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
        verify(userDao).getUserByEmail(email);
    }

    @Test
    void getUserByEmailNoExistedUserTest() {
        String email = "test@test.com";

        when(userDao.getUserByEmail(email)).thenReturn(null);

        User result = userController.getUserByEmail(email);

        assertNull(result);
        verify(userDao).getUserByEmail(email);
    }
}
