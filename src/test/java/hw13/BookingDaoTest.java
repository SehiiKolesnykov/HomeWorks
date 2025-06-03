package hw13;

import hw13.dao.BookingDaoImpl;
import hw13.model.Booking;
import hw13.util.DataStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mockStatic;

public class BookingDaoTest {

    private BookingDaoImpl bookingDao;
    private List<Booking> mockBookings;

    @BeforeEach
    void setUp() {
        bookingDao = new BookingDaoImpl();
        mockBookings = new ArrayList<>();
    }

    @Test
    void getBookingByIdTest() {
        try (MockedStatic<DataStorage> mockedDataStorage = mockStatic(DataStorage.class)){
            Booking booking = new Booking("0001", "user@us.com", List.of("999"), new String[] {"Name Surname"});
            mockBookings.add(booking);
            mockedDataStorage.when(DataStorage::loadBookings).thenReturn(mockBookings);

            Optional<Booking> result = bookingDao.getBookingById("0001");

            assertTrue(result.isPresent());
            assertEquals("0001", result.get().getId());
        }
    }

    @Test
    void getBookingByIdEmptyTest() {
        try (MockedStatic<DataStorage> mockedDataStorage = mockStatic(DataStorage.class)){
            mockedDataStorage.when(DataStorage::loadBookings).thenReturn(mockBookings);

            Optional<Booking> result = bookingDao.getBookingById("0001");

            assertTrue(result.isEmpty());
        }
    }

    @Test
    void removeBookingTest() {
        try (MockedStatic<DataStorage> mockedDataStorage = mockStatic(DataStorage.class)){
            Booking booking = new Booking("0001", "user@us.com", List.of("999"), new String[] {"Name Surname"});
            mockBookings.add(booking);
            mockedDataStorage.when(DataStorage::loadBookings).thenReturn(mockBookings);

            bookingDao.removeBooking("0001");

            mockedDataStorage.verify(() -> DataStorage.saveBookings(anyList()));
            assertFalse(mockBookings.contains(booking));
        }
    }

    @Test
    void getUserBookingsTest() {
        try (MockedStatic<DataStorage> mockedDataStorage = mockStatic(DataStorage.class)){
            Booking booking = new Booking("0001", "user@us.com", List.of("999"), new String[] {"Name Surname"});
            Booking booking2 = new Booking("0002", "user@us.com", List.of("999"), new String[] {"Name Surname"});
            Booking booking3 = new Booking("0003", "otheruser@us.com", List.of("999"), new String[] {"Name Surname"});
            mockBookings.addAll(List.of(booking3, booking2, booking));
            mockedDataStorage.when(DataStorage::loadBookings).thenReturn(mockBookings);

            List<Booking> result = bookingDao.getUserBookings("user@us.com");

            assertEquals(2, result.size());
            assertTrue(result.stream().allMatch(b -> b.getUserEmail().equals("user@us.com")));
        }
    }

    @Test
    void getUserBookingsEmptyTest() {
        try (MockedStatic<DataStorage> mockedDataStorage = mockStatic(DataStorage.class)){
            mockedDataStorage.when(DataStorage::loadBookings).thenReturn(mockBookings);

            List<Booking> result = bookingDao.getUserBookings("user@us.com");

            assertTrue(result.isEmpty());
        }
    }

    @Test
    void getBookingCountTest() {
        try (MockedStatic<DataStorage> mockedDataStorage = mockStatic(DataStorage.class)){
            int counter = 100;
            mockedDataStorage.when(DataStorage::loadBookingCounter).thenReturn(counter);

            int result = bookingDao.getBookingCounter();

           assertEquals(100, result);
        }
    }

    @Test
    void setBookingCountTest() {
        try (MockedStatic<DataStorage> mockedDataStorage = mockStatic(DataStorage.class)){
            int counter = 100;

            bookingDao.setBookingCounter(counter);

            mockedDataStorage.verify(() -> DataStorage.saveBookingCounter(eq(counter)));
        }
    }
}
