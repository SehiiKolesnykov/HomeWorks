package hw13;

import hw13.dao.UserDaoImpl;
import hw13.model.User;
import hw13.util.DataStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mockStatic;

public class UserDaoImplTest {

    private UserDaoImpl userDao;
    private List<User> mockUsers;

    @BeforeEach
    void setUp() {
        userDao = new UserDaoImpl();
        mockUsers = new ArrayList<>();
    }

    @Test
    void getUserByEmailTest() {
        try (MockedStatic<DataStorage> mockedDataStorage = mockStatic(DataStorage.class)){

            User user = new User("Test", "Test", "test@test.com", "123456");
            mockUsers.add(user);
            mockedDataStorage.when(DataStorage::loadUsers).thenReturn(mockUsers);

            User result = userDao.getUserByEmail("test@test.com");

            assertNotNull(result);
            assertEquals("Test", result.getName());
            assertEquals("test@test.com", result.getEmail());
        }
    }

    @Test
    void getUserByEmailExistTest() {
        try (MockedStatic<DataStorage> mockedDataStorage = mockStatic(DataStorage.class)){
            mockedDataStorage.when(DataStorage::loadUsers).thenReturn(mockUsers);

            User result = userDao.getUserByEmail("exist@dd.com");

            assertNull(result);
        }
    }

    @Test
    void updateUserTest() {
        try (MockedStatic<DataStorage> mockedDataStorage = mockStatic(DataStorage.class)){
            User user = new User("Test", "Test", "test@test.com", "123456");
            User updateUser = new User("newTest", "newTest", "test@test.com", "123456");
            mockUsers.add(user);
            mockedDataStorage.when(DataStorage::loadUsers).thenReturn(mockUsers);

            userDao.updateUser("test@test.com", updateUser);
            mockedDataStorage.verify(() -> DataStorage.saveUsers(anyList()));
            assertTrue(mockUsers.contains(updateUser));
        }
    }

    @Test
    void getAllUsersTest() {
        try (MockedStatic<DataStorage> mockedDataStorage = mockStatic(DataStorage.class)){
            User user = new User("Test", "Test", "test@test.com", "123456");
            mockUsers.add(user);
            mockedDataStorage.when(DataStorage::loadUsers).thenReturn(mockUsers);

            List<User> result = userDao.getAllUsers();

            assertEquals(1, result.size());
            assertEquals("test@test.com", result.getFirst().getEmail());
        }
    }

    @Test
    void getAllUsersEmptyTest() {
        try (MockedStatic<DataStorage> mockedDataStorage = mockStatic(DataStorage.class)){

            mockedDataStorage.when(DataStorage::loadUsers).thenReturn(mockUsers);

            List<User> result = userDao.getAllUsers();

            assertTrue(result.isEmpty());
            assertNotNull(result);
        }
    }

}
