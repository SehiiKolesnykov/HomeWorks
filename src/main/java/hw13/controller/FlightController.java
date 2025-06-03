package hw13.controller;

import hw13.exception.BookingSystemException;
import hw13.model.Flight;
import hw13.service.FlightService;

import java.time.LocalDateTime;
import java.util.List;

import static hw13.util.Texts.*;

public class FlightController {

    private final FlightService flightService;

    public FlightController() {
        this.flightService = new FlightService();
    }

    public List<Flight> getFlightsInTimeRange(LocalDateTime start, LocalDateTime end) {
        return flightService.getFlightsInTimeRange(start, end);
    }

    public Flight getFlightById(String flightId) {
        return flightService.getFlightById(flightId)
                .orElseThrow(() -> new BookingSystemException(FLIGHT_NOT_FOUND));
    }

    public List<List<Flight>> searchFlightsWithConnections(List<String> params) {
        return flightService.searchFlightsWithConnections(params);
    }

    public void updateFlight(Flight flight) {
        flightService.updateFlight(flight);
    }
}
