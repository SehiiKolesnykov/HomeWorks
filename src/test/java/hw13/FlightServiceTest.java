package hw13;

import hw13.dao.FlightDao;
import hw13.exception.InvalidInputException;
import hw13.model.Flight;
import hw13.service.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FlightServiceTest {


    private FlightDao flightDao;
    private FlightService flightService;
    private List<Flight> mockFlights;

    @BeforeEach
    void setUp() {
        flightDao = Mockito.mock(FlightDao.class);
        flightService = new FlightService();
        mockFlights = new ArrayList<>();

        try {
            Field daoField = FlightService.class.getDeclaredField("flightDao");
            daoField.setAccessible(true);
            daoField.set(flightService, flightDao);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        when(flightDao.getAllFlights()).thenReturn(Collections.emptyList());
        doNothing().when(flightDao).saveFlights(anyList());
    }

    @Test
    void getFlightInTimeRangeTest() {
        LocalDateTime date  = LocalDateTime.now();
        Flight flight1 = new Flight("0001", "Kyiv", "Lviv", date.plusHours(1), 100);
        Flight flight2 = new Flight("0002", "Kyiv", "Odesa", date.plusHours(2), 100);
        Flight flight3 = new Flight("0003", "Berlin", "Rivne", date.plusHours(3), 100);

        mockFlights.addAll(List.of(flight1, flight2, flight3));
        when(flightDao.getAllFlights()).thenReturn(mockFlights);

        List<Flight> results = flightService.getFlightsInTimeRange(date, date.plusHours(4));

        assertEquals(2, results.size());
        assertTrue(results.contains(flight1));
        assertTrue(results.contains(flight2));
    }

    @Test
    void getFlightByIdTest() {
        LocalDateTime date  = LocalDateTime.now();
        Flight flight1 = new Flight("0001", "Kyiv", "Lviv", date.plusHours(1), 100);
        when(flightDao.getFlightById("0001")).thenReturn(Optional.of(flight1));

        Optional<Flight> result = flightService.getFlightById("0001");

        assertTrue(result.isPresent());
        assertEquals("0001", result.get().getId());
    }

    @Test
    void getFlightByIdEmptyTest() {
        when(flightDao.getFlightById("0001")).thenReturn(Optional.empty());

        Optional<Flight> result = flightService.getFlightById("0001");

        assertTrue(result.isEmpty());
    }

    @Test
    void updateFlightTest() {
        LocalDateTime date  = LocalDateTime.now();
        Flight flight1 = new Flight("0001", "Kyiv", "Lviv", date.plusHours(1), 100);

        flightService.updateFlight(flight1);

        verify(flightDao).updateFlight("0001", flight1);
    }

    @Test
    void searchFlightTest() {
        LocalDateTime date  = LocalDateTime.now();
        Flight flight1 = new Flight("0001", "Kyiv", "Lviv", date.plusMinutes(10), 100);
        Flight flight2 = new Flight("0002", "Kyiv", "Odesa", date.plusMinutes(11), 100);

        mockFlights.addAll(List.of(flight1, flight2));
        when(flightDao.getAllFlights()).thenReturn(mockFlights);

        List<Flight> results = flightService.searchFlights("Kyiv", "Lviv", date);

        assertEquals(1, results.size());
        assertEquals("0001", results.getFirst().getId());
        verify(flightDao, atLeastOnce()).getAllFlights();
    }

    @Test
    void searchWithFlightConnectionTest() {
        DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDateTime date  = LocalDateTime.now();
        Flight flight1 = new Flight("0001", "Kyiv", "Lviv", date.plusMinutes(10), 100);
        Flight flight2 = new Flight("0002", "Lviv", "Odesa", date.plusHours(2), 100);

        mockFlights.addAll(List.of(flight1, flight2));
        when(flightDao.getAllFlights()).thenReturn(mockFlights);

        List<List<Flight>> result = flightService.searchFlightsWithConnections(
                List.of("Kyiv", "Odesa", date.format(DATE_TIME_FORMATTER), "2"));

        assertEquals(1, result.size());
        assertEquals(2, result.getFirst().size());
        assertEquals("0001", result.getFirst().getFirst().getId());
        assertEquals("0002", result.getFirst().getLast().getId());
    }

    @Test
    void searchWithFlightConnectionInvalidParamTest() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> flightService.searchFlightsWithConnections(List.of("Kyiv", "Odesa", "01.01.2025")));

        assertEquals("Expected 4 parameters: origin, destination, date, passengerCount", exception.getMessage());
    }

    @Test
    void searchWithFlightConnectionInvalidDateTest() {
        InvalidInputException exception = assertThrows(InvalidInputException.class,
                () -> flightService.searchFlightsWithConnections(List.of("Kyiv", "Odesa", "01.01", "2")));

        assertEquals("Invalid date format!", exception.getMessage());
    }

    @Test
    void searchWithFlightConnectionInvalidPassengersTest() {
        InvalidInputException exception = assertThrows(InvalidInputException.class,
                () -> flightService.searchFlightsWithConnections(List.of("Kyiv", "Odesa", "01.01.2025", "ddd")));

        assertEquals("Invalid passengers count!", exception.getMessage());
    }

}
