package hw13.controller;

import hw13.exception.BookingSystemException;
import hw13.model.Booking;
import hw13.service.BookingService;
import hw13.util.LoggerUtil;

import java.util.List;

public class BookingController {

    private final BookingService bookingService;
    private final UserController userController;

    public BookingController() {
        this.bookingService = new BookingService();
        this.userController = new UserController();
    }

    public Booking createBooking (String userEmail, List<String> flightIds, String[] passengers) {
        Booking booking = bookingService.createBooking(userEmail, flightIds, passengers);
        userController.addBooking(userEmail, booking.getId());
        return booking;
    }

    public void cancelBooking(String bookingId, String userEmail) {
        try {
            bookingService.cancelBooking(bookingId, userEmail);
            userController.removeBooking(userEmail, bookingId);
        } catch (BookingSystemException e) {
            throw new BookingSystemException("Error cancelling booking: " + e.getMessage());
        }
    }

    public List<Booking> getUserBookings (String userEmail) {
        return bookingService.getUserBookings(userEmail);
    }

    public String bookingToString(Booking booking) {
      return   bookingService.formatUserDetails(booking);
    }
}
