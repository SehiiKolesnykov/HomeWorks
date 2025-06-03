package hw13.dao;

import hw13.model.Flight;
import hw13.util.DataStorage;
import hw13.util.LoggerUtil;

import java.util.*;

import static hw13.util.Texts.*;

public class FlightDaoImpl implements FlightDao{

    @Override
    public List<Flight> getAllFlights() {
        List<Flight> flights = DataStorage.loadFlights();
        LoggerUtil.log(String.format(LOAD_FLIGHTS, flights.size()));
        return flights.isEmpty() ? new ArrayList<>() : flights;
    }

    @Override
    public Optional<Flight> getFlightById(String flightId) {
        return getAllFlights().stream()
                .filter(f -> f.getId().equals(flightId))
                .findFirst();
    }

    @Override
    public void updateFlight(String id, Flight flight) {
        List<Flight> flights = getAllFlights();
        flights.removeIf(f -> f.getId().equals(id));
        flights.add(flight);
        DataStorage.saveFlights(flights);
        LoggerUtil.log(String.format(UPDATE_FLIGHT, id));
    }

    @Override
    public void saveFlights(List<Flight> flights) {
        Set<String> ids = new HashSet<>();
        for (Flight flight : flights) {
            if (!ids.add(flight.getId())) {
                throw new IllegalStateException(String.format(DUPLICATE_FLIGHT, flight.getId()));
            }
        }
        DataStorage.saveFlights(flights);
        LoggerUtil.log(String.format(SAVE_FLIGHTS, flights.size()));
    }
}
