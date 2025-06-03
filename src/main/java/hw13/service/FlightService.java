package hw13.service;

import hw13.dao.FlightDao;
import hw13.dao.FlightDaoImpl;
import hw13.exception.InvalidInputException;
import hw13.model.Flight;
import hw13.util.LoggerUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

import static hw13.util.Texts.*;

public class FlightService{

    private final FlightDao flightDao;
    private static final String[] CITIES = {
            "Kyiv", "Berlin", "Paris", "Rome", "Madrid", "Warsaw", "Vienna", "Budapest", "Prague", "Athens"
    };
    private static final int MAX_FLIGHTS = 900;
    private static final Random RANDOM = new Random();
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public FlightService() {
        this.flightDao = new FlightDaoImpl();
        initializeFlights();
    }

    public void initializeFlights() {
        LocalDateTime now = LocalDateTime.now();
        List<Flight> flights = flightDao.getAllFlights();
        LoggerUtil.log(String.format(LOAD_FLIGHTS_AFTER_INI, flights.size()));

        flights.removeIf(f -> f.getDepartureTime().isBefore(now));
        flightDao.saveFlights(flights);
        LoggerUtil.log(String.format(REMOVE_EXPIRED_FLIGHTS, flights.size()));

        Set<String> usedIds = flights.stream().map(Flight::getId).collect(Collectors.toSet());

        while (flights.size() < MAX_FLIGHTS) {
            String id = generatedUniqueId(usedIds);
            usedIds.add(id);

            String origin = CITIES[RANDOM.nextInt(CITIES.length)];
            String destination;

            do {
                destination = CITIES[RANDOM.nextInt(CITIES.length)];
            } while (destination.equals(origin));

            LocalDateTime departureTime = now.plusHours(RANDOM.nextInt(168)).plusMinutes(RANDOM.nextInt(60));
            int totalSeats = 10 + RANDOM.nextInt(191);
            flights.add(new Flight(id, origin, destination, departureTime, totalSeats));
        }

        flightDao.saveFlights(flights);
        LoggerUtil.log(String.format(INI_FLIGHTS, flights.size()));
    }

    public List<Flight> getFlightsInTimeRange(LocalDateTime start, LocalDateTime end) {
        return flightDao.getAllFlights().stream()
                .filter(f -> f.getOrigin().equalsIgnoreCase("Kyiv"))
                .filter(f -> !f.getDepartureTime().isBefore(start) && !f.getDepartureTime().isAfter(end))
                .sorted((f1, f2) -> f1.getDepartureTime().compareTo(f2.getDepartureTime()))
                .collect(Collectors.toList());
    }

    public Optional<Flight> getFlightById(String flightId) {
        return flightDao.getFlightById(flightId);
    }

    public void updateFlight(Flight flight) {
        flightDao.updateFlight(flight.getId(), flight);
    }

    public List<Flight> searchFlights(String origin, String destination, LocalDateTime date) {
        return flightDao.getAllFlights().stream()
                .filter(f -> f.getOrigin().equalsIgnoreCase(origin) &&
                        f.getDestination().equalsIgnoreCase(destination) &&
                        f.getDepartureTime().toLocalDate().isEqual(date.toLocalDate()))
                .collect(Collectors.toList());
    }

    public List<List<Flight>> searchFlightsWithConnections(List<String> params) {
        if (params.size() != 4) {
            throw new IllegalArgumentException(EXPECTED_PARAMETERS);
        }

        String origin = params.get(0);
        String destination = params.get(1);
        String dateStr = params.get(2);
        int passengersCount;
         try {
             passengersCount = Integer.parseInt(params.get(3));
             if (passengersCount <= 0) {
                 throw new InvalidInputException(INVALID_PASSENGERS_COUNT);
             }
         } catch (NumberFormatException e) {
             throw new InvalidInputException(INVALID_PASSENGERS_COUNT);
         }

         LocalDateTime date;
         try {
             date = LocalDateTime.parse(dateStr + " 00:00", DATE_TIME_FORMATTER);
         } catch (DateTimeParseException e) {
             throw new InvalidInputException(INVALID_DATE_FORMAT);
         }

         List<Flight> directFlights = flightDao.getAllFlights().stream()
                 .filter(f -> f.getOrigin().equalsIgnoreCase(origin) &&
                                    f.getDestination().equalsIgnoreCase(destination) &&
                                    f.getDepartureTime().toLocalDate().isEqual(date.toLocalDate()) &&
                                    f.getAvailableSeats() >= passengersCount)
                 .toList();

         List<List<Flight>> results = new ArrayList<>(directFlights.stream().map(List::of).toList());

         List<Flight> firstLegs = flightDao.getAllFlights().stream()
                 .filter(f -> f.getOrigin().equalsIgnoreCase(origin) &&
                         f.getDepartureTime().toLocalDate().isEqual(date.toLocalDate()) &&
                         f.getAvailableSeats() >= passengersCount)
                 .toList();

         for (Flight first : firstLegs) {
             List<Flight> secondLegs = flightDao.getAllFlights().stream()
                     .filter(f -> f.getOrigin().equalsIgnoreCase(first.getDestination()) &&
                             f.getDestination().equalsIgnoreCase(destination) &&
                             f.getDepartureTime().isAfter(first.getDepartureTime().plusHours(1)) &&
                             f.getDepartureTime().isBefore(first.getDepartureTime().plusHours(13)) &&
                             f.getAvailableSeats() >= passengersCount)
                     .toList();
             for (Flight second : secondLegs) {
                 results.add(List.of(first, second));
             }
         }

         return results;
    }

    private String generatedUniqueId(Set<String> usedIds) {
        String id;
        do {
            id = String.format("%03d", RANDOM.nextInt((999) + 1));
        } while (usedIds.contains(id));
        return id;
    }
}
