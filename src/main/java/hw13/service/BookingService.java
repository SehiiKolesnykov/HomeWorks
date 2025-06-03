package hw13.service;

import hw13.controller.FlightController;
import hw13.dao.BookingDao;
import hw13.dao.BookingDaoImpl;
import hw13.exception.BookingSystemException;
import hw13.exception.InvalidInputException;
import hw13.model.Booking;
import hw13.model.Flight;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static hw13.util.Texts.*;

public class BookingService{

    private final BookingDao bookingDao;
    private final FlightController flightController;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public BookingService() {
        this.bookingDao = new BookingDaoImpl();
        this.flightController = new FlightController();
    }

    public Booking createBooking(String userEmail, List<String> flightIds, String[] passengers) {

        if (userEmail == null || flightIds == null || flightIds.isEmpty() || passengers == null || passengers.length == 0) {
            throw new BookingSystemException(INVALID_BOOKING_PARAMETERS);
        }
        for (String flightId : flightIds) {
            try {
                Flight flight = flightController.getFlightById(flightId);
                if (flight.getAvailableSeats() < passengers.length) {
                    throw new BookingSystemException(String.format(NOT_ENOUGH_SEATS, flightId));
                }
            } catch (InvalidInputException e) {
                throw new BookingSystemException(String.format(FLIGHT_NO_FOUND, flightId, e));
            }
        }
        String bookingId = generateUniqueBookingId();

        for (String flightId : flightIds) {
            Flight flight = flightController.getFlightById(flightId);
            flight.setAvailableSeats(flight.getAvailableSeats() - passengers.length);
            flightController.updateFlight(flight);
        }

        Booking booking = new Booking(bookingId, userEmail, flightIds, passengers);
        bookingDao.addBooking(booking);

        return booking;
    }

    public void cancelBooking(String bookingId, String userEmail) {
        Booking booking = bookingDao.getBookingById(bookingId)
                .filter(b -> b.getUserEmail().equals(userEmail))
                .orElseThrow(() -> new BookingSystemException(BOOKING_NOT_FOUND));

        for (String flightId : booking.getFlightIds()) {
            try {
                Flight flight = flightController.getFlightById(flightId);
                flight.setAvailableSeats(flight.getAvailableSeats() + booking.getPassengers().length);
                flightController.updateFlight(flight);
            } catch (InvalidInputException e) {
                throw new BookingSystemException(String.format(FLIGHT_NOT_FOUND_IN_CANCEL, flightId, e));
            }
        }

        bookingDao.removeBooking(bookingId);
    }

    public List<Booking> getUserBookings(String userEmail) {
        return bookingDao.getUserBookings(userEmail);
    }

    public String formatUserDetails(Booking booking) {
        if (booking == null) {
            throw new BookingSystemException(BOOKING_NULL);
        }
        String flightDetails = booking.getFlightIds().stream()
                .map(this::formatFlightDetails)
                .collect(Collectors.joining(" -> "));
        String passengerDetails = String.join(", ", booking.getPassengers());
        return String.format("\nBooking %s: %s, Passengers: [%s]", booking.getId(), flightDetails, passengerDetails);
    }

    private String formatFlightDetails(String flightId) {
        try {
            Flight flight = flightController.getFlightById(flightId);
            return String.format("Flight %s: %s -> %s, %s",
                    flight.getId(),
                    flight.getOrigin(),
                    flight.getDestination(),
                    flight.getDepartureTime().format(DATE_TIME_FORMATTER));
        }catch (IllegalArgumentException e) {
            return String.format("Flights %s: [Not found]", flightId);
        }
    }

    private String generateUniqueBookingId() {
        int counter = bookingDao.getBookingCounter();
        if (counter == 9999) {
            counter = 0;
        }
        int nextCount = counter + 1;
        bookingDao.setBookingCounter(nextCount);
        String id = String.format("%04d", nextCount);
        return id;
    }
}
