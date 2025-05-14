package hw07;

import hw07.Human.*;
import hw07.Pets.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class HappyFamilyTest {

    private Family family;
    private Woman mother;
    private Man father;
    private Man child;
    private Pet pet;
    private Man newChild;
    private Pet pet2;

    @BeforeEach
    void setUp() {
        mother = new Woman("Jane", "Corleone", 1890, 60,
                Map.of(DayOfWeek.MONDAY.name(), "walk the dog"), null);

        father = new Man("Vito", "Corleone", 1887, 70,
                Map.of(DayOfWeek.THURSDAY.name(), "Go to the gym"), null);

        child = new Man("Michael", "Corleone", 1920, 80,
                Map.of(DayOfWeek.FRIDAY.name(), "Sleep all day"), null);

        pet = new Dog( "Rock", 5, 75, Set.of("eat", "drink", "sleep"));

        newChild = new Man("Human", "Child", 2000, 60,
                Map.of(DayOfWeek.MONDAY.name(), "Walk the dog"), null);

        pet2 = new DomesticCat( "Rocky", 2, 50, Set.of("eat", "drink", "sleep"));

        family = new Family(mother, father);
        family.addFamilyMember(child);
        family.addFamilyMember(pet);
    }

    @Test
    public void petToStringTest() {

        String expected = "\n{species: 'DOG'," +
                "\nnickname: 'Rock',\n" +
                "age: 5,\n" +
                "trickLevel: 75,\n" +
                "habits: [sleep, eat, drink],\n" +
                "canFly: false,\n" +
                "numberOfLegs: 4,\n" +
                "hasFur: true}";

        assertEquals(expected, pet.toString());
    }

    @Test
    public void humanToStringTest() {
        String expected = "\n{" +
                "name: 'Jane',\n" +
                "surname: 'Corleone',\n" +
                "year: 1890,\n" +
                "iq: 60,\n" +
                "schedule: [\n" +
                "[MONDAY, walk the dog]]}";

        assertEquals(expected, mother.toString());
    }

    @Test
    public void familyToStringTest() {
        String expected = "\nFamily\n{ mother = Woman" +
                "\n{name: 'Jane',\n" +
                "surname: 'Corleone',\n" +
                "year: 1890,\n" +
                "iq: 60,\n" +
                "schedule: [\n" +
                "[MONDAY, walk the dog]]}, \n\n" +
                "father = Man" +
                "\n{name: 'Vito',\n" +
                "surname: 'Corleone',\n" +
                "year: 1887,\n" +
                "iq: 70,\n" +
                "schedule: [\n" +
                "[THURSDAY, Go to the gym]]}, \n\n" +
                "children = [\nMan" +
                "\n{name: 'Michael',\n" +
                "surname: 'Corleone',\n" +
                "year: 1920,\n" +
                "iq: 80,\n" +
                "schedule: [\n" +
                "[FRIDAY, Sleep all day]]}], \n\n" +
                "pets = [" +
                "\n{species: 'DOG'," +
                "\nnickname: 'Rock',\n" +
                "age: 5,\n" +
                "trickLevel: 75,\n" +
                "habits: [sleep, eat, drink],\n" +
                "canFly: false,\n" +
                "numberOfLegs: 4,\n" +
                "hasFur: true}]}" +
                "\n--------------------------------------\n";

        assertEquals(expected, family.toString());
    }

    @Test
    public void addChildTest() {
        int initialLength = family.countChildren();
        family.addFamilyMember(newChild);

        assertEquals(initialLength+1, family.countChildren());
        assertEquals(newChild, family.getChildren().get(initialLength));
        assertEquals(family, newChild.getFamily());
    }

    @Test
    public void deleteChildTestByEqualObject() {
        int initialLength = family.countChildren();

        assertTrue(family.deleteChildByObject(child));
        assertEquals(initialLength-1, family.countChildren());
        assertNull(child.getFamily());
    }

    @Test
    public void deleteChildTestByNoEqualObject() {
        int initialLength = family.countChildren();

        assertFalse(family.deleteChildByObject(newChild));
        assertEquals(initialLength, family.countChildren());
    }

    @Test
    public void deleteChildTestByIndex() {
        int initialLength = family.countChildren();

        assertTrue(family.deleteChildByIndex(0));
        assertEquals(initialLength-1, family.countChildren());
        assertNull(child.getFamily());
    }

    @Test
    public void deleteChildTestByNonExistentIndex() {
        int initialLength = family.countChildren();

        assertFalse(family.deleteChildByIndex(99));
        assertEquals(initialLength, family.countChildren());
    }

    @Test
    public void addPetTest() {
        int initialLength = family.countPets();
        family.addFamilyMember(pet2);

        assertEquals(initialLength+1, family.countPets());
        assertTrue(family.getPet().contains(pet2));
    }

    @Test
    public void petEqualTest() {
        Pet pet3 = new Dog( "Rock", 5, 75, Set.of("eat", "drink", "sleep"));
        assertEquals(pet, pet3);
        assertEquals(pet.hashCode(), pet3.hashCode());
    }

    @Test
    public void petNotEqualTest() {
        assertNotEquals(pet, pet2);
        assertNotEquals(pet.hashCode(), pet2.hashCode());
    }

    @Test
    public void petSpeciesNotEqualTest() {
        Pet pet3 = new DomesticCat( "Rock", 5, 75, Set.of("eat", "drink", "sleep"));
        assertNotEquals(pet, pet3);
        assertNotEquals(pet.hashCode(), pet3.hashCode());
    }

    @Test
    public void petNicknameNoEqualTest() {
        Pet pet3 = new Dog( "Rocky", 5, 75, Set.of("eat", "drink", "sleep"));
        assertNotEquals(pet, pet3);
        assertNotEquals(pet.hashCode(), pet3.hashCode());
    }

    @Test
    public void petTrickLevelNoEqualTest() {
        Pet pet3 = new Dog( "Rock", 5, 50, Set.of("eat", "drink", "sleep"));
        assertNotEquals(pet, pet3);
        assertNotEquals(pet.hashCode(), pet3.hashCode());
    }

    @Test
    public void petAgeNoEqualTest() {
        Pet pet3 = new Dog( "Rock", 1, 75, Set.of("eat", "drink", "sleep"));
        assertNotEquals(pet, pet3);
        assertNotEquals(pet.hashCode(), pet3.hashCode());
    }

    @Test
    public void petHabitsNoEqualTest() {
        Pet pet3 = new Dog( "Rock", 5, 75, Set.of("sleep"));
        assertNotEquals(pet, pet3);
        assertNotEquals(pet.hashCode(), pet3.hashCode());
    }

    @Test
    public void humanEqualTest() {
        Man father2 = new Man("Vito", "Corleone", 1887, 70,
                Map.of(DayOfWeek.THURSDAY.name(), "Go to the gym"), null);

        assertTrue(father.equals(father2));
        assertEquals(father.hashCode(), father2.hashCode());
    }

    @Test
    public void humanNoEqualTest() {
        assertFalse(father.equals(mother));
        assertNotEquals(father.hashCode(), mother.hashCode());
    }

    @Test
    public void humanNameNoEqualTest() {
        Man father2 = new Man("Viton", "Corleone", 1887, 70,
                Map.of(DayOfWeek.THURSDAY.name(), "Go to the gym"), null);

        assertFalse(father.equals(father2));
        assertNotEquals(father.hashCode(), father2.hashCode());
    }

    @Test
    public void humanSurnameNoEqualTest() {
        Man father2 = new Man("Vito", "Corl", 1887, 70,
                Map.of(DayOfWeek.THURSDAY.name(), "Go to the gym"), null);

        assertFalse(father.equals(father2));
        assertNotEquals(father.hashCode(), father2.hashCode());
    }

    @Test
    public void humanYearNoEqualTest() {
        Man father2 = new Man("Vito", "Corleone", 2000, 70,
                Map.of(DayOfWeek.THURSDAY.name(), "Go to the gym"), null);

        assertFalse(father.equals(father2));
        assertNotEquals(father.hashCode(), father2.hashCode());
    }

    @Test
    public void humanIqNoEqualTest() {
        Man father2 = new Man("Vito", "Corleone", 1887, 1,
                Map.of(DayOfWeek.THURSDAY.name(), "Go to the gym"), null);

        assertFalse(father.equals(father2));
        assertNotEquals(father.hashCode(), father2.hashCode());
    }

    @Test
    public void humanScheduleNoEqualTest() {
        Man father2 = new Man("Vito", "Corleone", 1887, 70,
                Map.of(DayOfWeek.MONDAY.name(), "Go to the gym"), null);

        assertFalse(father.equals(father2));
        assertNotEquals(father.hashCode(), father2.hashCode());
    }

    @Test
    public void familyEqualTest() {
        Family family2 = new Family(mother, father);
        family.addFamilyMember(child);
        family.addFamilyMember(pet);

        assertTrue(family.equals(family2));
        assertEquals(family.hashCode(), family2.hashCode());
    }

    @Test
    public void familyFatherNoEqualTest() {
        Man father2 = new Man("Watcher", "Corleone", 1887, 70,
                Map.of(DayOfWeek.MONDAY.name(), "Go to the gym"), null);

        Family family2 = new Family(mother, father2);

        assertFalse(family.equals(family2));
        assertNotEquals(family.hashCode(), family2.hashCode());
    }

    @Test
    public void familyMotherNoEqualTest() {
        Woman mother2 = new Woman("Some", "Corleone", 1887, 70,
                Map.of(DayOfWeek.MONDAY.name(), "Go to the gym"), null);

        Family family2 = new Family(mother2, father);

        assertFalse(family.equals(family2));
        assertNotEquals(family.hashCode(), family2.hashCode());
    }

    @Test
    public void feedPetTest() {
        boolean isTimeToFeed = true;
        assertTrue(father.feedPet(pet, isTimeToFeed));
    }

    @Test
    public void feedPetByTrickTest() {
        boolean isTimeToFeed = false;
        pet2.setTrickLevel(100);
        family.addFamilyMember(pet2);

        assertTrue(father.feedPet(pet2, isTimeToFeed));
    }

    @Test
    public void feedPetNoTimeTest() {
        boolean isTimeToFeed = false;
        pet2.setTrickLevel(0);
        family.addFamilyMember(pet2);
        assertFalse(father.feedPet(pet2, isTimeToFeed));
    }

    @Test
    public void countChildrenTest() {
        assertEquals(family.countChildren(), family.getChildren().size());
    }

    @Test
    public void countFamilyTest() {
        assertEquals(3, family.countFamily());
        family.addFamilyMember(newChild);
        assertEquals(4, family.countFamily());
    }

    @Test
    public void bornChildTest() {
        int childCount = family.countChildren();

        Human newChild = ((Woman) family.getMother()).bornChild(2000);

        assertEquals(childCount +1, family.countChildren());
    }

    @Test
    public void bornChildTestWithoutFamily() {
        Woman mather2 = new Woman("Jane", "Corleone", 1890, 60,
                Map.of(DayOfWeek.MONDAY.name(), "walk the dog"), null);

        Human newChild = mather2.bornChild(2000);

        assertNull(newChild);
    }
}
