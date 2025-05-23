package hw12;

import hw12.Human.*;
import hw12.Pets.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static hw12.Texts.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HappyFamilyTest {

    private FamilyService familyService;
    private FamilyDao familyDao;
    private Family family;
    private Man father;
    private Woman mother;
    private Dog dog;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        familyDao = new CollectionFamilyDao();
        familyService = new FamilyService(familyDao);
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        Map<String, String> schedule = new HashMap<>();
        father = new Man("Kolesnykov", "Serhii", "16/09/1991", 80, schedule, null);
        mother = new Woman("Kolesnykova", "Svytlana", "14/04/1991", 80, schedule, null);
        dog = new Dog("Rex", 3, 40, Set.of("eat", "sleep", "play"));
        family = new Family(mother, father);
        familyDao.saveFamily(family);
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        familyDao.getAllFamilies().clear();
    }

    @Test
    void getAllFamiliesTest() {
        List<Family> families = familyService.getAllFamilies();
        assertEquals(1, families.size());
        assertEquals(family, families.getFirst());

        families.add(new Family(new Woman("Test", "Woman", "01/01/200", 50, null, null),
                new Man("Test", "Man", "01/01/200", 50, null, null)));
        assertEquals(1, familyService.getAllFamilies().size());
    }

    @Test
    void getAllFamiliesEmptyTest() {
        familyDao.deleteFamily(family);
        assertEquals(0, familyService.getAllFamilies().size());
    }

    @Test
    void displayAllFamiliesTest() {
        List<Family> families = familyService.getAllFamilies();
        assertFalse(families.isEmpty());

        familyService.displayAllFamilies();
        String output = outputStream.toString().replace("\r\n", "\n");

        assertTrue(output.contains(family.toString()));
        assertTrue(output.contains("Family 1:"));
    }

    @Test
    void displayAllFamiliesEmptyTest() {
        familyDao.deleteFamily(family);
        familyService.displayAllFamilies();
        String output = outputStream.toString().replace("\r\n", "\n");
        assertTrue(output.contains(errorNoFamilies));
    }

    @Test
    void getFamiliesBiggerThanTest() {
        family.addFamilyMember(new Man("Test", "Child", "01/01/2000", 50, null, family));
        familyDao.saveFamily(family);

        List<Family> result = familyService.getFamiliesBiggerThen(2);
        assertEquals(1, result.size());
        assertEquals(family, result.getFirst());

        result = familyService.getFamiliesBiggerThen(3);
        assertTrue(result.isEmpty());
    }

    @Test
    void getFamiliesLessThenTest() {
        List<Family> result = familyService.getFamiliesLessThen(3);
        assertEquals(1, result.size());
        assertEquals(family, result.getFirst());

        family.addFamilyMember(new Man("Test", "Child", "01/01/2000", 50, null, family));
        familyDao.saveFamily(family);
        result = familyService.getFamiliesLessThen(3);
        assertTrue(result.isEmpty());
    }

    @Test
    void countFamilyWithMemberNumberTest() {
        int count = familyService.countFamiliesWithMemberNumber(2);
        assertEquals(1, count);

        count = familyService.countFamiliesWithMemberNumber(3);
        assertEquals(0, count);
    }

    @Test
    void createNewFamilyTest() {
        Man father2 = new Man("Test", "Man", "01/01/2000", 50, null, null);
        Family newFamily = familyService.createNewFamily(mother, father2);
        assertNotNull(newFamily);

        assertEquals(2, familyService.getAllFamilies().size());
        assertEquals(mother, newFamily.getMother());
        assertEquals(father2, newFamily.getFather());
    }

    @Test
    void deleteFamilyByIndexTest() {
        boolean deleted = familyService.deleteFamilyByIndex(0);
        assertTrue(deleted);
        assertEquals(0, familyService.getAllFamilies().size());

        deleted = familyService.deleteFamilyByIndex(0);
        assertFalse(deleted);
    }

    @Test
    void bornChildTest() {
        Family result = familyService.bornChild(family, "Ivan", "Zlata", "01/01/2014");
        assertNotNull(result);
        assertEquals(1, family.getChildren().size());
        assertEquals(family, result);
    }

    @Test
    void adoptChildTest() {
        Human child = new Man("Child", "Kolesnykov", "01/01/2014", 50, null, family);
        Family result = familyService.adoptChild(family, child);
        assertNotNull(result);
        assertEquals(1, family.getChildren().size());
        assertEquals(family, result);
        assertEquals(child, family.getChildren().getFirst());
        assertEquals(family, child.getFamily());
    }

    @Test
    void adoptChildNullFamilyTest() {
        Human child = new Man("Child", "Kolesnykov", "01/01/2014", 50, null, null);
        Family result = familyService.adoptChild(null, child);
        assertNull(result);
        assertEquals(0, family.getChildren().size());
        assertNull(child.getFamily());
    }

    @Test
    void deleteAllChildrenOlderThanTest() {
        Human adultChild = new Man("Test", "Kolesnykov", "01/01/2006", 50, null, family);
        family.addFamilyMember(adultChild);
        Human child = new Man("Child", "Kolesnykov", "01/01/2014", 50, null, family);
        family.addFamilyMember(child);
        familyDao.saveFamily(family);

        familyService.deleteAllChildrenOlderThan(18);
        assertEquals(1, family.getChildren().size());
        assertEquals(child, family.getChildren().getFirst());
    }

    @Test
    void deleteAllChildrenOlderThanNoChildrenTest() {
        familyService.deleteAllChildrenOlderThan(18);
        assertTrue(family.getChildren().isEmpty());
    }

    @Test
    void countTest() {
       int count = familyService.count();
       assertEquals(1, count);

       familyDao.deleteFamily(family);
       count = familyService.count();
       assertEquals(0, count);
    }

    @Test
    void getFamilyByIdTest() {
        Family result = familyService.getFamilyById(0);
        assertEquals(family, result);

        result = familyService.getFamilyById(999);
        assertNull(result);
    }

    @Test
    void getPetsTest() {
        family.addFamilyMember(dog);
        familyDao.saveFamily(family);

        Set<Pet> pets = familyService.getPets(0);
        assertEquals(1, pets.size());
        assertEquals(dog, pets.iterator().next());

        pets = familyService.getPets(999);
        assertTrue(pets.isEmpty());
    }

    @Test
    void addPetTest() {
        familyService.addPet(0, dog);
        assertEquals(1, family.getPet().size());
        assertTrue(family.getPet().contains(dog));

        familyService.addPet(999, dog);
        assertEquals(1, family.getPet().size());
        assertTrue(family.getPet().contains(dog));
    }
}
