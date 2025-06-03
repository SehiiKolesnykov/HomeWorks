package hw13;

import hw13.controller.FlightController;
import hw13.dao.BookingDao;
import hw13.exception.BookingSystemException;
import hw13.model.Booking;
import hw13.model.Flight;
import hw13.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BookingServiceTest {

    @Mock
    private BookingDao bookingDao;

    @InjectMocks
    private BookingService bookingService;

    private FlightController flightControllerStub;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        flightControllerStub = new FlightController() {
            private final Map<String, Flight> flightMap = new HashMap<>();

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
            java.lang.reflect.Field field = BookingService.class.getDeclaredField("flightController");
            field.setAccessible(true);
            field.set(bookingService, flightControllerStub);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            java.lang.reflect.Field field = BookingService.class.getDeclaredField("bookingDao");
            field.setAccessible(true);
            field.set(bookingService, bookingDao);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }



    }

    @Test
    void createBookingTest() {
        Flight flight = new Flight("0001", "Kyiv", "Lviv", LocalDateTime.now(), 100);

        try {
            java.lang.reflect.Field field = flightControllerStub.getClass().getDeclaredField("flightMap");
            field.setAccessible(true);
            Map<String, Flight> flightMap = (Map<String, Flight>) field.get(flightControllerStub);
            flightMap.put("0001", flight);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        when(bookingDao.getBookingCounter()).thenReturn(0);
        String[] passengers = {"Test Test", "Name Surname"};

        try {
            Booking result = bookingService.createBooking("test@test.com", List.of("0001"), passengers);
            assertNotNull(result);
            assertEquals("0001", result.getId());
        } catch (Exception e) {
            throw e;
        }

        verify(bookingDao).addBooking(any(Booking.class));
        verify(bookingDao).setBookingCounter(1);
        assertEquals(98, flight.getAvailableSeats());

    }

    @Test
    void createBookingNoEnoughSeatsTest() {
        Flight flight = new Flight("0001", "Kyiv", "Lviv", LocalDateTime.now(), 1);

        try {
            java.lang.reflect.Field field = flightControllerStub.getClass().getDeclaredField("flightMap");
            field.setAccessible(true);
            Map<String, Flight> flightMap = (Map<String, Flight>) field.get(flightControllerStub);
            flightMap.put("0001", flight);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        String[] passengers = {"Test Test", "Name Surname"};

        BookingSystemException exception = assertThrows(BookingSystemException.class,
                () -> bookingService.createBooking("test@test.com", List.of("0001"), passengers));
        assertEquals("Not enough available seats on flight 0001", exception.getMessage());
    }

    @Test
    void createBookingInvalidParametersTest() {
        BookingSystemException exception = assertThrows(BookingSystemException.class,
                () -> bookingService.createBooking("tesr@test.com", List.of(), new  String[]{}));
        assertEquals("Invalid bookings parameters!", exception.getMessage());
    }

    @Test
    void cancelBookingTest() {
        Flight flight = new Flight("001", "Kyiv", "Lviv", LocalDateTime.now(), 100);
        Booking booking = new Booking("0001", "test@test.com", List.of("001"), new String[]{"Name Surname"});

        try {
            java.lang.reflect.Field field = flightControllerStub.getClass().getDeclaredField("flightMap");
            field.setAccessible(true);
            Map<String, Flight> flightMap = (Map<String, Flight>) field.get(flightControllerStub);
            flightMap.put("001", flight);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        when(bookingDao.getBookingById("0001")).thenReturn(Optional.of(booking));
        bookingService.cancelBooking("0001", "test@test.com");

        verify(bookingDao).removeBooking("0001");
        assertEquals(101, flight.getAvailableSeats());
    }

    @Test
    void cancelBookingNotFoundTest() {
        when(bookingDao.getBookingById("0001")).thenReturn(Optional.empty());

        BookingSystemException exception = assertThrows(BookingSystemException.class,
                () -> bookingService.cancelBooking("0001", "test@test.com"));
        assertEquals("Booking not found!", exception.getMessage());
    }

    @Test
    void getUserBookingsTest() {
        Booking booking = new Booking("0001", "test@test.com", List.of("001"), new String[]{"Name Surname"});
        when(bookingDao.getUserBookings("test@test.com")).thenReturn(List.of(booking));

        List<Booking> result = bookingService.getUserBookings("test@test.com");

        assertEquals(1, result.size());
        assertEquals("0001", result.getFirst().getId());
    }

    @Test
    void formatUserDetailsTest() {
        Flight flight = new Flight("001", "Kyiv", "Lviv", LocalDateTime.now(), 100);
        Booking booking = new Booking("0001", "test@test.com", List.of("001"), new String[]{"Name Surname"});

        try {
            java.lang.reflect.Field field = flightControllerStub.getClass().getDeclaredField("flightMap");
            field.setAccessible(true);
            Map<String, Flight> flightMap = (Map<String, Flight>) field.get(flightControllerStub);
            flightMap.put("001", flight);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String result = bookingService.formatUserDetails(booking);

        assertTrue(result.contains("Booking 0001"));
        assertTrue(result.contains("Kyiv -> Lviv"));
        assertTrue(result.contains("Name Surname"));
    }

    @Test
    void formatUserDetailsNoBookingTest() {
        BookingSystemException exception = assertThrows(BookingSystemException.class,
                () -> bookingService.formatUserDetails(null));
        assertEquals("Booking cannot be null", exception.getMessage());
    }

}
