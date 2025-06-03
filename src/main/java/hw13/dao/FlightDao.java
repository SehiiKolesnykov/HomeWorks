package hw13.dao;

import hw13.model.Flight;

import java.util.List;
import java.util.Optional;

public interface FlightDao {
    List<Flight> getAllFlights();
    Optional<Flight> getFlightById(String flightId);
    void updateFlight(String id, Flight flight);
    void saveFlights (List<Flight> flights);
}
