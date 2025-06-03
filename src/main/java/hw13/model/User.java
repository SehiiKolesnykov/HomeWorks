package hw13.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User implements Serializable {

    private final String id;
    private final String name;
    private final String surname;
    private final String email;
    private final String password;
    private final List<String> bookings;

    public User(String name, String surname, String email, String password) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.bookings = new ArrayList<>();
    }

    public String getId() { return this.id; }
    public String getName() { return this.name; }
    public String getSurname() { return  this.surname; }
    public String getEmail() { return this.email; }
    public String getPassword() { return this.password; }
    public List<String> getBookings() { return new ArrayList<>(bookings); }


    public void addBooking(String bookingId) {
        bookings.add(bookingId);
    }

    public void removeBooking(String bookingId) {
        bookings.remove(bookingId);
    }
}
