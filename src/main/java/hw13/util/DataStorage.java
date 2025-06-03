package hw13.util;

import hw13.model.Booking;
import hw13.model.Flight;
import hw13.model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static hw13.util.Texts.*;

public class DataStorage {

    private static final String FLIGHT_FILE = "flights.dat";
    private static final String BOOKING_FILE = "bookings.dat";
    private static final String USERS_FILE = "users.dat";
    private static final String COUNTER_FILE = "booking_counter.dat";

    @SuppressWarnings("unchecked")
    public static <T> List<T> loadData(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))){
            Object obj = ois.readObject();
            if (obj instanceof List) {
                return (List<T>) obj;
            }
            LoggerUtil.log(String.format(INVALID_DATA_FORMAT, fileName));
            return new ArrayList<>();
        } catch (FileNotFoundException e) {
            LoggerUtil.log(String.format(FILE_NOT_FOUND, fileName));
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            LoggerUtil.log(String.format(ERROR_LOAD_DATA, fileName, e.getMessage()));
            return new ArrayList<>();
        }
    }

    public static <T> void saveData(String filename, List<T> data) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))){
            oos.writeObject(data);
            LoggerUtil.log(String.format(SAVE_ITEMS, data.size(), filename));
        } catch (IOException e) {
            LoggerUtil.log(String.format(ERROR_SAVE_DATA, filename, e.getMessage()));
            throw new RuntimeException(String.format(ERROR_SAVE_DATA, filename, e));
        }
    }

    public static List<Flight> loadFlights() {
        return loadData(FLIGHT_FILE);
    }

    public static void saveFlights(List<Flight> flights) {
        saveData(FLIGHT_FILE, flights);
    }

    public static List<Booking> loadBookings() {
        return loadData(BOOKING_FILE);
    }

    public static void saveBookings(List<Booking> bookings) {
        saveData(BOOKING_FILE, bookings);
    }

    public static List<User> loadUsers() {
        return loadData(USERS_FILE);
    }

    public static void saveUsers(List<User> users) {
        saveData(USERS_FILE, users);
    }

    public static int loadBookingCounter() {
        List<Integer> counterList = loadData(COUNTER_FILE);
        if (counterList.isEmpty()) {
            LoggerUtil.log(NO_BOOKING_COUNTER);
            return 0;
        }
        return counterList.getFirst();
    }

    public static void saveBookingCounter(int counter) {
        List<Integer> counterList = List.of(counter);
        saveData(COUNTER_FILE, counterList);
    }
}
