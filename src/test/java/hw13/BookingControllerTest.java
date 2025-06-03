package hw13;

import hw13.controller.BookingController;
import hw13.controller.FlightController;
import hw13.controller.UserController;
import hw13.dao.BookingDao;
import hw13.dao.FlightDao;
import hw13.dao.UserDao;
import hw13.exception.BookingSystemException;
import hw13.model.Booking;
import hw13.model.Flight;
import hw13.model.User;
import hw13.service.BookingService;
import hw13.service.FlightService;
import hw13.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

public class BookingControllerTest {

    private BookingDao bookingDao;
    private FlightDao flightDao;
    private UserDao userDao;
    private FlightService flightService;
    private UserService userService;
    private BookingService bookingService;
    private FlightController flightController;
    private UserController userController;
    private BookingController bookingController;

    @BeforeEach
    void setUp() {
        bookingDao = Mockito.mock(BookingDao.class);
        flightDao = Mockito.mock(FlightDao.class);
        userDao = Mockito.mock(UserDao.class);
        bookingService = new BookingService();
        flightService = new FlightService();
        userService = new UserService();
        bookingController = new BookingController();
        flightController = new FlightController();
        userController = new UserController();

        when(bookingDao.getBookingCounter()).thenReturn(0);
        doNothing().when(bookingDao).setBookingCounter(anyInt());
        when(flightDao.getAllFlights()).thenReturn(Collections.emptyList());
        doNothing().when(flightDao).saveFlights(anyList());
        when(userDao.getAllUsers()).thenReturn(Collections.emptyList());
        doNothing().when(userDao).saveUsers(anyList());

        try {
            Field daoField = FlightService.class.getDeclaredField("flightDao");
            daoField.setAccessible(true);
            daoField.set(flightService, flightDao);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            Field serviceField = FlightController.class.getDeclaredField("flightService");
            serviceField.setAccessible(true);
            serviceField.set(flightController, flightService);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            Field userDaoField = UserService.class.getDeclaredField("userDao");
            userDaoField.setAccessible(true);
            userDaoField.set(userService, userDao);

            Field controllerField = UserService.class.getDeclaredField("flightController");
            controllerField.setAccessible(true);
            controllerField.set(userService, flightController);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            Field userControllerField = UserController.class.getDeclaredField("userService");
            userControllerField.setAccessible(true);
            userControllerField.set(userController, userService);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            Field bookingDaoField = BookingService.class.getDeclaredField("bookingDao");
            bookingDaoField.setAccessible(true);
            bookingDaoField.set(bookingService, bookingDao);

            Field flightControllerField = BookingService.class.getDeclaredField("flightController");
            flightControllerField.setAccessible(true);
            flightControllerField.set(bookingService, flightController);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            Field bookingServiceField = BookingController.class.getDeclaredField("bookingService");
            bookingServiceField.setAccessible(true);
            bookingServiceField.set(bookingController, bookingService);

            Field userControllerField = BookingController.class.getDeclaredField("userController");
            userControllerField.setAccessible(true);
            userControllerField.set(bookingController, userController);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void createBookingTest() {
        String userEmail = "test@test.com";
        List<String> flightIds = List.of("0001");
        String[] passengers = {"Name Surname"};

        Flight flight = new Flight("0001", "Kyiv", "Lviv", LocalDateTime.now().plusHours(1), 100);
        User user = new User("Name", "Surname", userEmail, "password");

        when(flightDao.getFlightById("0001")).thenReturn(Optional.of(flight));
        when(userDao.getUserByEmail(userEmail)).thenReturn(user);
        when(bookingDao.getBookingCounter()).thenReturn(1);
        Booking expectedBooking = new Booking("0002", userEmail, flightIds, passengers);

        Booking result = bookingController.createBooking(userEmail, flightIds, passengers);

        assertEquals(expectedBooking.getId(),result.getId());
        assertEquals(99, flight.getAvailableSeats());
        verify(bookingDao).addBooking(any(Booking.class));
        verify(userDao).updateUser(eq(userEmail), any(User.class));
    }

    @Test
    void createBookingNoSeatsTest() {
        String userEmail = "test@test.com";
        List<String> flightIds = List.of("0001");
        String[] passengers = {"Name Surname", "Test Test"};

        Flight flight = new Flight("0001", "Kyiv", "Lviv", LocalDateTime.now().plusHours(1), 1);
        when(flightDao.getFlightById("0001")).thenReturn(Optional.of(flight));

        BookingSystemException exception = assertThrows(BookingSystemException.class,
                () -> bookingController.createBooking(userEmail, flightIds, passengers));

        assertEquals("Not enough available seats on flight 0001", exception.getMessage());
        verify(userDao, never()).updateUser(anyString(), any());
        verify(bookingDao, never()).addBooking(any());
    }

    @Test
    void createBookingInvalidParametersTest() {
        String userEmail = null;
        List<String> flightIds = List.of("0001");
        String[] passengers = {"Name Surname"};

        BookingSystemException exception = assertThrows(BookingSystemException.class,
                () -> bookingController.createBooking(userEmail, flightIds, passengers));

        assertEquals("Invalid bookings parameters!", exception.getMessage());
        verify(userDao, never()).updateUser(anyString(), any());
        verify(bookingDao, never()).addBooking(any());
    }

    @Test
    void cancelBookingTestTest() {
        String bookingId = "0001";
        String userEmail = "test@test.com";
        List<String> flightIds = List.of("001");
        String[] passengers = {"Name Surname"};

        Booking booking = new Booking(bookingId, userEmail, flightIds, passengers);
        Flight flight = new Flight("0001", "Kyiv", "Lviv", LocalDateTime.now().plusHours(1), 100);
        User user = new User("Name", "Surname", userEmail, "password");
        user.addBooking(bookingId);

        when(bookingDao.getBookingById(bookingId)).thenReturn(Optional.of(booking));
        when(flightDao.getFlightById("001")).thenReturn(Optional.of(flight));
        when(userDao.getUserByEmail(userEmail)).thenReturn(user);

        bookingController.cancelBooking(bookingId, userEmail);

        assertEquals(100, flight.getTotalSeats());
        verify(bookingDao).removeBooking(bookingId);
        verify(userDao).updateUser(eq(userEmail), any(User.class));
    }

    @Test
    void cancelBookingNoBookingTest() {
        String bookingId = "0001";
        String userEmail = "test@test.com";
        when(bookingDao.getBookingById(bookingId)).thenReturn(Optional.empty());

        verify(bookingDao, never()).removeBooking(anyString());
        verify(userDao, never()).updateUser(anyString(), any());
    }

    @Test
    void getUserBookingsTest() {
        String userEmail = "test@test.com";
        Booking booking = new Booking("0001", userEmail, List.of("001"), new String[]{"Name, Surname"});
        when(bookingDao.getUserBookings(userEmail)).thenReturn(List.of(booking));

        List<Booking> bookings = bookingController.getUserBookings(userEmail);

        assertEquals(List.of(booking), bookings);
        verify(bookingDao).getUserBookings(userEmail);
    }

    @Test
    void getUserBookingsNoBookingTest() {
        String userEmail = "test@test.com";

        when(bookingDao.getUserBookings(userEmail)).thenReturn(Collections.emptyList());

        List<Booking> bookings = bookingController.getUserBookings(userEmail);

        assertTrue(bookings.isEmpty());
        verify(bookingDao).getUserBookings(userEmail);
    }

}
