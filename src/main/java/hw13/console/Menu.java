package hw13.console;

import hw13.controller.BookingController;
import hw13.exception.BookingSystemException;
import hw13.exception.InvalidInputException;
import hw13.model.Booking;
import hw13.model.Flight;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static hw13.util.Texts.*;

public class Menu {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private int lastPassengerCount;
    private final BookingController bookingController = new BookingController();

    public void displayMainMenu() {
        out.printf(MAIN_MENU);
    }

    public void displayAuthMenu() {
        out.printf(AUTH_MENU);
    }

    public void displayError(String message) {
        out.printf(message);
    }

    public void displaySuccess(String message) {
        out.printf(message);
    }

    public void displayFlight(Flight flight) {
        out.printf(flight.toString());
    }

    public void displayBooking(Booking booking) {
        out.printf(bookingController.bookingToString(booking));
    }

    public boolean promptLogin (
            Supplier<String> inputSupplier,
            BiFunction<String, String, Boolean> authenticator,
            Consumer<String> setUserEmail) {

        out.printf(ENTER_EMAIL);
        String email = inputSupplier.get();
        out.printf(ENTER_PASSWORD);
        String password = inputSupplier.get();

        boolean success = authenticator.apply(email, password);

        if (success) {
            setUserEmail.accept(email);
        }
        return success;
    }

    public boolean promptSignIn(Supplier<String> inputSupplier, RegisterFunction registerFunction) {
        out.printf(ENTER_NAME);
        String name = inputSupplier.get();

        out.printf(ENTER_SURNAME);
        String surname = inputSupplier.get();

        out.printf(ENTER_EMAIL);
        String email = inputSupplier.get();

        out.printf(ENTER_PASSWORD);
        String password = inputSupplier.get();

        return registerFunction.register(name, surname, email, password);
    }

    public List<List<Flight>> promptFlightSearch (
            Supplier<String> inputSupplier,
            Function<List<String>, List<List<Flight>>> flightSearcher){

        out.printf(ENTER_DESTINATION);
        String destination = inputSupplier.get();

        out.printf(ENTER_DATE);
        String dateStr = inputSupplier.get();

        out.printf(ENTER_PASSENGER_COUNT);
        String passengerCountStr = inputSupplier.get();

        try {
            LocalDateTime date = LocalDateTime.parse(dateStr + " 00:00", DATE_TIME_FORMATTER);
            int passengerCount = Integer.parseInt(passengerCountStr);
            if (passengerCount <= 0) {
                displayError(INVALID_PASSENGERS_COUNT);
                lastPassengerCount = 0;
                return List.of();
            }
            lastPassengerCount = passengerCount;
            return flightSearcher.apply(List.of("Kyiv", destination, dateStr, passengerCountStr));
        } catch (DateTimeParseException e) {
            throw new InvalidInputException(INVALID_DATE_FORMAT);
        } catch (NumberFormatException e) {
            throw new InvalidInputException(INVALID_PASSENGERS_COUNT);

        }
    }

    public int getLastPassengerCount() {
        return lastPassengerCount;
    }

    public List<Flight> promptFlightSelection(Supplier<String> inputSupplier, List<List<Flight>> flights) {
        for (int i = 0; i < flights.size(); i++) {
            List<Flight> route = flights.get(i);
            if (route.size() == 1) {
                out.println((i + 1) + ". Direct: " + route.getFirst());
            } else {
                out.println((i + 1) + ". Connecting: " + route.get(0) + " -> " + route.get(1));
            }
        }
        out.print("Select flight option (1-" + flights.size() + ") or 0 to cancel: ");
        String choice = inputSupplier.get();
        try {
            int index = Integer.parseInt(choice) - 1;
            if (index == -1) return null;
            if (index >= 0 && index < flights.size()) {
                return flights.get(index); // Return the selected route
            }
            displayError(INVALID_FLIGHT_OPTION);
        } catch (NumberFormatException e) {
            displayError(INVALID_FLIGHT_OPTION);
        }
        return null;
    }

    public Optional<Flight> promptFlightId(Supplier<String> inputSupplier, FlightIdFunction idFunction) {
        out.printf(ENTER_FLIGHT_ID);
        String flightId = inputSupplier.get();

        try {
            return Optional.of(idFunction.get(flightId));
        } catch (BookingSystemException e) {
            displayError(e.getMessage());
            return Optional.empty();
        }
    }

    public String[] promptPassengers (Supplier<String> inputSupplier, int passengersCount) {

        String[] passengers = new String[passengersCount];
        for (int i = 0; i < passengersCount; i++) {
            out.printf(ENTER_PASSENGER_NAME + (i + 1) + " (Name Surname): ");
            passengers[i] = inputSupplier.get();
        }
        return passengers;
    }

    public void promptBookingId (Supplier<String> inputSupplier, Consumer<String> cancelFunction) {
        out.printf(ENTER_BOOKING_ID);
        String bookingId = inputSupplier.get();
        cancelFunction.accept(bookingId);
    }

    @FunctionalInterface
    public interface FlightSearchFunction {
        List<Flight> search (String destination, LocalDateTime date, int passengerCount);
    }

    @FunctionalInterface
    public interface FlightIdFunction {
        Flight get(String flightId);
    }

    @FunctionalInterface
    public interface RegisterFunction {
        boolean register(String name, String surname, String email, String password);
    }
}
