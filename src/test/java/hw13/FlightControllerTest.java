package hw13;

import hw13.controller.FlightController;
import hw13.dao.FlightDao;
import hw13.exception.BookingSystemException;
import hw13.exception.InvalidInputException;
import hw13.model.Flight;
import hw13.service.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FlightControllerTest {

    private FlightDao flightDao;
    private FlightController flightController;
    private FlightService flightService;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @BeforeEach
    void setUp() {
        flightDao = Mockito.mock(FlightDao.class);
        flightService = new FlightService();
        flightController = new FlightController();

        when(flightDao.getAllFlights()).thenReturn(Collections.emptyList());
        doNothing().when(flightDao).saveFlights(anyList());

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
    }

    @Test
    void getFlightsInTimeRangeTest() {
        LocalDateTime start = LocalDateTime.of(2025, 6, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2025, 6, 2, 0, 0);
        String flightId = "0001";
        String origin = "Kyiv";
        String destination = "Lviv";
        int availableSeats = 100;
        Flight flight = new Flight(flightId, origin, destination,start.plusHours(1), availableSeats );
        when(flightDao.getAllFlights()).thenReturn(List.of(flight));

        List<Flight> result = flightController.getFlightsInTimeRange(start, end);

        assertEquals(List.of(flight), result);
    }

    @Test
    void getFlightsInTimeRangeNoFlightsTest() {
        LocalDateTime start = LocalDateTime.of(2025, 6, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2025, 6, 2, 0, 0);

        when(flightDao.getAllFlights()).thenReturn(Collections.emptyList());

        List<Flight> result = flightController.getFlightsInTimeRange(start, end);

        assertTrue(result.isEmpty());
        verify(flightDao, atLeastOnce()).getAllFlights();
    }

    @Test
    void getFlightByIdTest() {
        String flightId = "0001";
        String origin = "Kyiv";
        String destination = "Lviv";
        int availableSeats = 100;
        Flight flight = new Flight(flightId, origin, destination, LocalDateTime.now(), availableSeats );
        when(flightDao.getFlightById(flightId)).thenReturn(Optional.of(flight));

        Flight result = flightController.getFlightById(flightId);

        assertEquals(flight, result);
        verify(flightDao).getFlightById(flightId);
    }

    @Test
    void getFlightByIdNoExistFlightTest() {
        String flightId = "0001";
        when(flightDao.getFlightById(flightId)).thenReturn(Optional.empty());

        BookingSystemException exception = assertThrows(BookingSystemException.class,
                () -> flightController.getFlightById(flightId));

        assertEquals("Flight not found!", exception.getMessage());
        verify(flightDao).getFlightById(flightId);
    }

    @Test
    void searchFlightWithConnectionTest() {
        String flightId = "0001";
        String origin = "Kyiv";
        String destination = "Lviv";
        LocalDateTime date = LocalDateTime.of(2025, 6, 1, 0, 0);
        String dateStr = date.format(DATE_FORMATTER);
        int availableSeats = 100;
        List<String> params = List.of(origin, destination, dateStr, "2");
        Flight flight = new Flight(flightId, origin, destination, date, availableSeats);
        when(flightDao.getAllFlights()).thenReturn(List.of(flight));

        List<List<Flight>> result = flightController.searchFlightsWithConnections(params);

        assertEquals(List.of(List.of(flight)), result);
        verify(flightDao, atLeastOnce()).getAllFlights();
    }

    @Test
    void searchFlightWithConnectionInvalidParamsTest() {
        List<String> params = List.of("Kyiv", "Lviv", "01.02.2025");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> flightController.searchFlightsWithConnections(params));

        assertEquals("Expected 4 parameters: origin, destination, date, passengerCount", exception.getMessage());
        verify(flightDao, never()).getAllFlights();
    }

    @Test
    void searchFlightWithConnectionInvalidPassengerCountTest() {
        List<String> params = List.of("Kyiv", "Lviv", "01.02.2025", "0");

        InvalidInputException exception = assertThrows(InvalidInputException.class,
                () -> flightController.searchFlightsWithConnections(params));

        assertEquals("Invalid passengers count!", exception.getMessage());
        verify(flightDao, never()).getAllFlights();
    }

    @Test
    void searchFlightWithConnectionInvalidDateTest() {
        List<String> params = List.of("Kyiv", "Lviv", "01-02-2025", "3");

        InvalidInputException exception = assertThrows(InvalidInputException.class,
                () -> flightController.searchFlightsWithConnections(params));

        assertEquals("Invalid date format!", exception.getMessage());
        verify(flightDao, never()).getAllFlights();
    }

    @Test
    void updateFlightTest() {
        Flight flight = new Flight("0001", "Kyiv", "Lviv", LocalDateTime.now(), 100);
        doNothing().when(flightDao).updateFlight(eq(flight.getId()), any(Flight.class));

        flightController.updateFlight(flight);

        verify(flightDao).updateFlight(flight.getId(), flight);
    }
}
