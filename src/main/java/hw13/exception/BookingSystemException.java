package hw13.exception;

public class BookingSystemException extends RuntimeException {

    public BookingSystemException (String message) {
        super(message);
    }

    public BookingSystemException (String message, Throwable cause) {
        super(message, cause);
    }
}
