package hw13.util;

import java.io.PrintStream;
import java.time.format.DateTimeFormatter;

public class Texts {
    // CONSTANTS //
    public static final PrintStream out = System.out;
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static final String AUTH_MENU = """
            \n\nAuthentication Menu:
            1. Log in
            2. Sign in
            3. Exit
            
            Select an option (1-3):\s
            """;

    public static final String MAIN_MENU = """
            \n\n Main menu:
            1. Flight board
            2. View flight information
            3. Search and book flight
            4. Cancel bookings
            5. My bookings
            6. End Session
            7. Exit
            
            Select an option (1-7):\s""";


                            // MESSAGE //
    // USER //

    public static final String FETCH_USER = "Fetched user by email: %s, found: %s";
    public static final String UPDATE_USER = "Update user: %s, current users count %d";
    public static final String LOAD_USERS = "Loaded %d users from storage";
    public static final String SAVING_USERS = "Saving %d users to storage";
    public static final String REGISTER_USER = "Register user: %s";
    public static final String REGISTER_SUCCESS = "User registered successfully: %s";
    public static final String LOAD_USERS_AFTER_INI = "Loaded %d users during initialization";
    public static final String DEFAULT_USERS = "Initializing default users";

    // FLIGHT //

    public static final String LOAD_FLIGHTS = "Loaded %d flights from storage";
    public static final String UPDATE_FLIGHT = "Updated flight: %s";
    public static final String SAVE_FLIGHTS = "Saved %d flights to storage";
    public static final String LOAD_FLIGHTS_AFTER_INI = "Loaded %d flights during initialization";
    public static final String REMOVE_EXPIRED_FLIGHTS = "Removed expired flights, remaining %d flights.";
    public static final String INI_FLIGHTS = "Initialized %d flights";

    // BOOKING //

    // MENU //

    public static final String ENTER_EMAIL = "Enter email: ";
    public static final String ENTER_PASSWORD = "Enter password: ";
    public static final String ENTER_NAME = "Enter name: ";
    public static final String ENTER_SURNAME = "Enter surname: ";
    public static final String ENTER_DESTINATION = "Enter destination: ";
    public static final String ENTER_DATE = "Enter date (format dd.MM.yyyy): ";
    public static final String ENTER_PASSENGER_COUNT = "Enter number of passengers: ";
    public static final String ENTER_FLIGHT_ID = "Enter flight ID: ";
    public static final String ENTER_PASSENGER_NAME = "Enter name and surname of passenger: ";
    public static final String ENTER_BOOKING_ID = "Enter booking ID: ";
    public static final String BOOKING_SUCCESS = "Booking successful! Booking ID: ";
    public static final String BOOKING_CANCELLED = "Booking canceled!";
    public static final String SELECTED_OPTION = "User %s selected menu option: %s";
    public static final String EXIT = "Application exited via auth menu";
    public static final String LOGOUT = "User logged out";
    public static final String SELECTED_AUTH_OPTION = "Selected auth option: %s";
    public static final String DISPLAY_FLIGHT_BOARD = "Display flight board";
    public static final String DISPLAY_FLIGHT_INFO = "Displayed flight information";
    public static final String CREATE_BOOKING = "Created booking: ";
    public static final String CANCEL_BOOKING = "Canceled booking";
    public static final String DISPLAY_BOOKING = "Display bookings for user: %s";
    public static final String CONSOLE_CLOSED = "Console closed";

    // DATA //

    public static final String SAVE_ITEMS = "Saved %d items to %s";

                                // ERRORS //

    // USER //

    public static final String ERROR_AUTH = "Authentification failed for email: %s";
    public static final String INVALID_EMAIL = "Invalid email format!";
    public static final String EMAIL_ALREADY_EXIST = "Email already exist!";
    public static final String INVALID_PASSWORD_TO_SHORT = "Password must be at least 6 characters!";

    // FLIGHTS //

    public static final String DUPLICATE_FLIGHT = "Duplicate flight id: %s";
    public static final String INVALID_PASSENGERS_COUNT = "Invalid passengers count!";
    public static final String EXPECTED_PARAMETERS = "Expected 4 parameters: origin, destination, date, passengerCount";
    public static final String INVALID_DATE_FORMAT = "Invalid date format!";

    // BOOKING //

    public static final String BOOKING_NOT_FOUND = "Booking not found!";
    public static final String INVALID_BOOKING_PARAMETERS = "Invalid bookings parameters!";
    public static final String NOT_ENOUGH_SEATS = "Not enough available seats on flight %s";
    public static final String FLIGHT_NO_FOUND = "Flight %s not found: %s";
    public static final String FLIGHT_NOT_FOUND_IN_CANCEL = "Flight %s not found during cancellation: %s";
    public static final String BOOKING_NULL = "Booking cannot be null";

    // MENU //

    public static final String INVALID_FLIGHT_OPTION = "Invalid flight option!";
    public static final String INVALID_MENU_OPTION = "Invalid menu option!";
    public static final String INVALID_LOGIN = "Invalid email or password";
    public static final String INVALID_AUTH_OPTION = "Invalid authentication option!";
    public static final String FLIGHT_NOT_FOUND = "Flight not found!";
    public static final String NO_BOOKINGS_FOUND = "No bookings found!";
    public static final String ERROR_INPUT = "Input error: %s";

    // LOG //

    public static final String ERROR_WRITING_LOG = "Error writing to log: %s";

    // DATA //

    public static final String INVALID_DATA_FORMAT = "Invalid data format in %s";
    public static final String FILE_NOT_FOUND = "%s file not found! Return empty list";
    public static final String ERROR_LOAD_DATA = "Error loading data from %s: %s";
    public static final String ERROR_SAVE_DATA = "Error saving data to %s: %s";
    public static final String NO_BOOKING_COUNTER = "Booking counter file is empty, initializing to 0";
}
