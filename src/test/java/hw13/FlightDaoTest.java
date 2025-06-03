package hw13;

import hw13.dao.FlightDaoImpl;
import hw13.model.Flight;
import hw13.model.User;
import hw13.util.DataStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mockStatic;

public class FlightDaoTest {

    private FlightDaoImpl flightDao;
    private List<Flight> mockFlight;

    @BeforeEach
    void setUp() {
        flightDao = new FlightDaoImpl();
        mockFlight = new ArrayList<>();
    }

    @Test
    void getAllFlightsTest() {
        try (MockedStatic<DataStorage> mockedDataStorage = mockStatic(DataStorage.class)){
            Flight flight = new Flight("0001", "Kyiv", "Lviv", LocalDateTime.now(), 100);
            mockFlight.add(flight);
            mockedDataStorage.when(DataStorage::loadFlights).thenReturn(mockFlight);

            List<Flight> result = flightDao.getAllFlights();

            assertEquals(1, result.size());
            assertEquals("0001", result.getFirst().getId());
        }
    }

    @Test
    void getAllFlightsEmptyTest() {
        try (MockedStatic<DataStorage> mockedDataStorage = mockStatic(DataStorage.class)){
            mockedDataStorage.when(DataStorage::loadFlights).thenReturn(mockFlight);

            List<Flight> result = flightDao.getAllFlights();

            assertTrue(result.isEmpty());
        }
    }

    @Test
    void getFlightByIdTest() {
        try (MockedStatic<DataStorage> mockedDataStorage = mockStatic(DataStorage.class)){
            Flight flight = new Flight("0001", "Kyiv", "Lviv", LocalDateTime.now(), 100);
            mockFlight.add(flight);
            mockedDataStorage.when(DataStorage::loadFlights).thenReturn(mockFlight);

            Optional<Flight> result = flightDao.getFlightById("0001");

            assertTrue(result.isPresent());
            assertEquals("0001", result.get().getId());
        }
    }

    @Test
    void getFlightByIdEmptyTest() {
        try (MockedStatic<DataStorage> mockedDataStorage = mockStatic(DataStorage.class)){
            mockedDataStorage.when(DataStorage::loadFlights).thenReturn(mockFlight);

            Optional<Flight> result = flightDao.getFlightById("0001");

            assertTrue(result.isEmpty());
        }
    }

    @Test
    void updateFlightTest() {
        try (MockedStatic<DataStorage> mockedDataStorage = mockStatic(DataStorage.class)){
            Flight flight = new Flight("0001", "Kyiv", "Lviv", LocalDateTime.now(), 100);
            Flight newflight = new Flight("0001", "Kyiv", "Odessa", LocalDateTime.now(), 150);
            mockFlight.add(flight);
            mockedDataStorage.when(DataStorage::loadFlights).thenReturn(mockFlight);

            flightDao.updateFlight("0001", newflight);


            mockedDataStorage.verify(() -> DataStorage.saveFlights(anyList()));
            assertTrue(mockFlight.contains(newflight));
        }
    }

    @Test
    void saveFlightsTest() {
        try (MockedStatic<DataStorage> mockedDataStorage = mockStatic(DataStorage.class)){
            List<Flight> flights = List.of(new Flight("0001", "Kyiv", "Lviv", LocalDateTime.now(), 100));

            flightDao.saveFlights(flights);

            mockedDataStorage.verify(() -> DataStorage.saveFlights(eq(flights)));
        }
    }

    @Test
    void saveFlightsExceptionTest() {
        try (MockedStatic<DataStorage> mockedDataStorage = mockStatic(DataStorage.class)){
            Flight flight = new Flight("0001", "Kyiv", "Lviv", LocalDateTime.now(), 100);
            Flight newflight = new Flight("0001", "Kyiv", "Odessa", LocalDateTime.now(), 150);
            List<Flight> flights = List.of(flight, newflight);

            Exception exception = assertThrows(IllegalStateException.class, () -> flightDao.saveFlights(flights));

            assertTrue(exception.getMessage().contains("0001"));
        }
    }
}
