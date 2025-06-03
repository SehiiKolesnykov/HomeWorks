package hw13.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Flight implements Serializable {

    private final String id;
    private final String origin;
    private final String destination;
    private final LocalDateTime departureTime;
    private final int totalSeats;
    private int availableSeats;

    public Flight(String id, String origin, String destination, LocalDateTime departureTime, int totalSeats) {
        this.id = id;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
    }

    public String getId() { return this.id; }
    public String getOrigin() { return this.origin; }
    public String getDestination() { return this.destination; }
    public LocalDateTime getDepartureTime() { return this.departureTime; }
    public int getTotalSeats() { return this.totalSeats; }
    public int getAvailableSeats() { return this.availableSeats; }

    public void setAvailableSeats (int availableSeats) { this.availableSeats = availableSeats; }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return String.format(
                "\nFlight %s: %s -> %s, %s, Available seats: %d",
                id, origin, destination, departureTime.format(formatter), availableSeats);
    }
}
