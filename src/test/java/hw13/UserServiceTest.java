package hw13;

import hw13.controller.FlightController;
import hw13.dao.UserDao;
import hw13.exception.InvalidInputException;
import hw13.model.Flight;
import hw13.model.User;
import hw13.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserService userService;

    private FlightController flightControllerStub;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        final Map<String, Flight> flightMap = new HashMap<>();
        flightControllerStub = new FlightController() {
            @Override
            public List<Flight> getFlightsInTimeRange(LocalDateTime start, LocalDateTime end) {
                return new ArrayList<>();
            }
            @Override
            public Flight getFlightById(String flightId) {
                return flightMap.get(flightId);
            }
            @Override
            public List<List<Flight>> searchFlightsWithConnections(List<String> params) {
                return new ArrayList<>();
            }
            @Override
            public void updateFlight(Flight flight) {
                flightMap.put(flight.getId(), flight);
            }
        };

        try {
            java.lang.reflect.Field controllerField = UserService.class.getDeclaredField("flightController");
            controllerField.setAccessible(true);
            controllerField.set(userService, flightControllerStub);
        } catch (Exception e) {
            throw new RuntimeException("%s", e);
        }

        try{
            java.lang.reflect.Field daoField = UserService.class.getDeclaredField("userDao");
            daoField.setAccessible(true);
            daoField.set(userService, userDao);
        } catch (Exception e) {
            throw new RuntimeException("%s", e);
        }
    }

    @Test
    void authenticateTest() {
        User user = new User("Name", "Surname", "test@test.com", "123456");
        when(userDao.getUserByEmail("test@test.com")).thenReturn(user);
        doNothing().when(userDao).updateUser(eq("test@test.com"), any(User.class));

        boolean result = userService.authenticate("test@test.com", "123456");

        assertTrue(result);
        verify(userDao).updateUser(eq("test@test.com"), any(User.class));
    }

    @Test
    void authenticateInvalidPasswordTest() {
        User user = new User("Name", "Surname", "test@test.com", "123456");
        when(userDao.getUserByEmail("test@test.com")).thenReturn(user);
        doNothing().when(userDao).updateUser(eq("test@test.com"), any(User.class));

        boolean result = userService.authenticate("test@test.com", "wrong");

        assertFalse(result);
        verify(userDao, never()).updateUser(any(String.class), any(User.class));
    }

    @Test
    void authenticateUserNotFoundTest() {
        when(userDao.getUserByEmail("none@test.com")).thenReturn(null);

        boolean result = userService.authenticate("none@test.com", "password");

        assertFalse(result);
        verify(userDao, never()).updateUser(any(String.class), any(User.class));
    }

    @Test
    void registerUserTest() {
        when(userDao.getUserByEmail("test@test.com")).thenReturn(null);
        doNothing().when(userDao).updateUser(eq("test@test.com"), any(User.class));

        userService.registerUser("Name", "Surname", "test@test.com", "password");

        verify(userDao).updateUser(eq("test@test.com"), any(User.class));
    }

    @Test
    void registerUserInvalidEmailTest() {
        InvalidInputException exception = assertThrows(InvalidInputException.class,
                () -> userService.registerUser("Name", "Surname", "invalid-email", "password"));

        assertEquals("Invalid email format!", exception.getMessage());
    }

    @Test
    void registerUserShortPasswordTest() {
        InvalidInputException exception = assertThrows(InvalidInputException.class,
                () -> userService.registerUser("Name", "Surname", "test@test.com", "pass"));

        assertEquals("Password must be at least 6 characters!", exception.getMessage());
    }

    @Test
    void registerUserEmailExistedTest() {
        User user = new User("Name", "Surname", "test@test.com", "123456");
        when(userDao.getUserByEmail("test@test.com")).thenReturn(user);

        InvalidInputException exception = assertThrows(InvalidInputException.class,
                () -> userService.registerUser("Name", "Surname", "test@test.com", "password"));

        assertEquals("Email already exist!", exception.getMessage());
    }

    @Test
    void getUserByEmailTest() {
        User user = new User("Name", "Surname", "test@test.com", "123456");
        when(userDao.getUserByEmail("test@test.com")).thenReturn(user);

        User result = userService.getUserByEmail("test@test.com");

        assertNotNull(result);
        assertEquals("test@test.com", result.getEmail());
    }

    @Test
    void updateUserTest() {
        User user = new User("Name", "Surname", "test@test.com", "123456");
        doNothing().when(userDao).updateUser(eq("test@test.com"), any(User.class));

        userService.updateUser("test@test.com", user);

        verify(userDao).updateUser("test@test.com", user);
    }

    @Test
    void updateTicketStatusTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        User user = new User("Name", "Surname", "test@test.com", "123456");
        user.addBooking("0001");

        when(userDao.getUserByEmail("test@test.com")).thenReturn(user);
        doNothing().when(userDao).updateUser(eq("test@test.com"), any(User.class));

        userService.authenticate("test@test.com", "123456");

        assertTrue(user.getBookings().contains("0001"));
        verify(userDao).updateUser(eq("test@test.com"), any(User.class));
    }
}
