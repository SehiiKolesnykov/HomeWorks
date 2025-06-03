package hw13.console;

import hw13.controller.BookingController;
import hw13.controller.FlightController;
import hw13.controller.UserController;
import hw13.exception.InvalidInputException;
import hw13.model.Booking;
import hw13.model.Flight;
import hw13.util.LoggerUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.function.Supplier;

import static hw13.util.Texts.*;

public class ConsoleHandler implements AutoCloseable {

    private final Scanner scanner = new Scanner(System.in);
    private final Menu menu;
    private final FlightController flightController;
    private final BookingController bookingController;
    private final UserController userController;
    private String currentUserEmail;

    public ConsoleHandler() {
        this.menu = new Menu();
        this.flightController = new FlightController();
        this.bookingController = new BookingController();
        this.userController = new UserController();
    }

    public void start() {
        if (handleAuthMenu(false)) {
            boolean continueLoop = true;
            while (continueLoop) {
                continueLoop = handleMenuLoop();
            }
        }
    }

    private boolean handleMenuLoop() {
        try {
            menu.displayMainMenu();
            String choice = readInput();
            LoggerUtil.log(String.format(SELECTED_OPTION, currentUserEmail, choice));
            switch (choice) {
                case "1" -> {
                    showFlightBoard();
                    return true;
                }
                case "2" -> {
                    showFlightInfo();
                    return true;
                }
                case "3" -> {
                    searchAndBookFlight();
                    return true;
                }
                case "4" -> {
                    canselBooking();
                    return true;
                }
                case "5" -> {
                    showMyBookings();
                    return true;
                }
                case "6" -> {
                    logOut();
                    return true;
                }
                case "7" -> {
                    LoggerUtil.log(EXIT);
                    return false; }
                default -> throw new InvalidInputException(INVALID_MENU_OPTION);
            }
        } catch (InvalidInputException e) {
            menu.displayError(e.getMessage());
            LoggerUtil.log(String.format(ERROR_INPUT, e.getMessage()));
            return true;
        }
    }

    private boolean login() {
        return menu.promptLogin(
                this::readInput,
                userController::authenticate,
                userEmail -> this.currentUserEmail = userEmail
        );
    }

    private boolean signIn() {
        return menu.promptSignIn(
                this::readInput,
                (name, surname, email, password) -> {
                   try {
                       userController.registerUser(name, surname, email, password);
                       this.currentUserEmail = email;
                       return true;
                   } catch (InvalidInputException e) {
                       menu.displayError(e.getMessage());
                       return false;
                   }
                }
        );
    }

    private void logOut() {
        currentUserEmail = null;
        LoggerUtil.log(LOGOUT);
        handleAuthMenu(true);
    }

    private boolean handleAuthMenu(boolean isLogout) {
        if (isLogout) {
            currentUserEmail = null;
            LoggerUtil.log(LOGOUT);
        }
        while (true) {
            menu.displayAuthMenu();
            String choice = readInput();
            LoggerUtil.log(String.format(SELECTED_AUTH_OPTION, choice));
            switch (choice) {
                case "1" -> {
                    if (login()) {
                        return true;
                    }
                    menu.displayError(INVALID_LOGIN);
                }
                case "2" -> {
                    if (signIn()) {
                        return true;
                    }
                }
                case "3" -> {
                    LoggerUtil.log(EXIT);
                    return false;
                }
                default -> menu.displayError(INVALID_AUTH_OPTION);
            }
        }
    }

    private void showFlightBoard() {
        LocalDateTime now = LocalDateTime.now();
        flightController.getFlightsInTimeRange(now, now.plusHours(24))
                .forEach(menu::displayFlight);
        LoggerUtil.log(DISPLAY_FLIGHT_BOARD);
    }

    private void showFlightInfo() {
        menu.promptFlightId(this::readInput, flightController::getFlightById)
                .ifPresentOrElse(
                        menu::displayFlight,
                        () -> menu.displayError(FLIGHT_NOT_FOUND)
                );
        LoggerUtil.log(DISPLAY_FLIGHT_INFO);
    }

    private void searchAndBookFlight() {
        Supplier<String> inputSupplier = this::readInput;
        List<List<Flight>> flights = menu.promptFlightSearch(inputSupplier, flightController::searchFlightsWithConnections);
        if (flights.isEmpty()) {
            menu.displayError(FLIGHT_NOT_FOUND);
            return;
        }
        List<Flight> selectedRoute = menu.promptFlightSelection(inputSupplier, flights);
        if (selectedRoute == null) return;

        int passengerCount = menu.getLastPassengerCount();
        if (passengerCount <= 0) {
            menu.displayError(INVALID_PASSENGERS_COUNT);
            return;
        }
        for (Flight flight : selectedRoute) {
            if (passengerCount > flight.getAvailableSeats()) {
                menu.displayError(String.format(NOT_ENOUGH_SEATS, flight.getId()));
                return;
            }
        }
        String[] passengers = menu.promptPassengers(inputSupplier, passengerCount);
        if (passengers.length == 0) return;

        List<String> flightIds = selectedRoute.stream().map(Flight::getId).toList();
        Booking booking = bookingController.createBooking(currentUserEmail, flightIds, passengers);
        menu.displaySuccess(BOOKING_SUCCESS + booking.getId());
        LoggerUtil.log(CREATE_BOOKING + booking.getId());
    }

    private void canselBooking() {
        menu.promptBookingId(this::readInput, id -> bookingController.cancelBooking(id, currentUserEmail));
        menu.displaySuccess(BOOKING_CANCELLED);
        LoggerUtil.log(CANCEL_BOOKING);
    }

    private void showMyBookings() {
        List<Booking> bookings = bookingController.getUserBookings(currentUserEmail);
        if (bookings.isEmpty()) {
            menu.displayError(NO_BOOKINGS_FOUND);
        } else {
            bookings.forEach(menu::displayBooking);
        }
        LoggerUtil.log(String.format(DISPLAY_BOOKING, currentUserEmail));
    }

    private String readInput() {
        return scanner.nextLine().trim();
    }

    @Override
    public void close() {
        scanner.close();
        LoggerUtil.log(CONSOLE_CLOSED);
    }
}
