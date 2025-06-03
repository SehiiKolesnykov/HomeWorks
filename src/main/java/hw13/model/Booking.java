package hw13.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class Booking implements Serializable {

    private final String id;
    private final String userEmail;
    private final List<String> flightIds;
    private final String[] passengers;

    public Booking(String id, String userEmail, List<String> flightIds, String[] passengers) {
        this.id = id;
        this.userEmail = userEmail;
        this.flightIds = flightIds;
        this.passengers = passengers;
    }

    public String getId() { return this.id; }
    public String getUserEmail() { return this.userEmail; }
    public List<String> getFlightIds() { return this.flightIds; }
    public String[] getPassengers() { return this.passengers; }

    @Override
    public String toString() {
        return String.format("\n\nBooking [id=%s, flights=%s, passengers=%s]", id, flightIds, Arrays.toString(passengers));
    }
}
