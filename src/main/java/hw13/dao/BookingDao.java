package hw13.dao;

import hw13.model.Booking;

import java.util.List;
import java.util.Optional;

public interface BookingDao {
    void addBooking(Booking booking);
    Optional<Booking> getBookingById(String bookingId);
    void removeBooking(String bookingId);
    List<Booking> getUserBookings(String userEmail);
    List<Booking> getAllBookings();
    int getBookingCounter();
    void setBookingCounter(int counter);
}
