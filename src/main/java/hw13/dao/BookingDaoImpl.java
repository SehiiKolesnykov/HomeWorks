package hw13.dao;

import hw13.model.Booking;
import hw13.util.DataStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookingDaoImpl  implements BookingDao{
    @Override
    public void addBooking(Booking booking) {
        List<Booking> bookings = getAllBookings();
        bookings.add(booking);
        DataStorage.saveBookings(bookings);
    }

    @Override
    public Optional<Booking> getBookingById(String bookingId) {
        return getAllBookings().stream()
                .filter(b -> b.getId().equals(bookingId))
                .findFirst();
    }

    @Override
    public void removeBooking(String bookingId) {
        List<Booking> bookings = getAllBookings();
        bookings.removeIf(b -> b.getId().equals(bookingId));
        DataStorage.saveBookings(bookings);
    }

    @Override
    public List<Booking> getUserBookings(String userEmail) {
        return getAllBookings().stream()
                .filter(b -> b.getUserEmail().equals(userEmail))
                .collect(Collectors.toList());
    }

    @Override
    public List<Booking> getAllBookings() {
        List<Booking> bookings = DataStorage.loadBookings();
        return bookings.isEmpty() ? new ArrayList<>() : bookings;
    }

    @Override
    public int getBookingCounter() {
        int counter = DataStorage.loadBookingCounter();
        return counter;
    }

    @Override
    public void setBookingCounter(int counter) {
        DataStorage.saveBookingCounter(counter);
    }
}
