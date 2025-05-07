package hw05;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HappyFamilyTest {

    private Family family;
    private Human father;
    private Human mother;
    private Human child;
    private Pet pet;

    @BeforeEach
    void setUp() {
        mother = new Human("Jane", "Corleone", 1890);
        mother.setIq(60);
        mother.setSchedule(new String[][]{{DayOfWeek.MONDAY.name(), "Walk the dog"}});

        father = new Human("Vito", "Corleone", 1887);
        father.setIq(70);
        father.setSchedule(new String[][]{{DayOfWeek.THURSDAY.name(), "Go to the gym"}});

        child = new Human("Michael", "Corleone", 1920);
        child.setIq(80);
        child.setSchedule(new String[][]{{DayOfWeek.FRIDAY.name(), "Sleep all day"}});

        pet = new Pet(Species.DOG, "Rock", 5, 75, new String[]{"eat", "drink", "sleep"});

        family = new Family(mother, father);
        family.addChild(child);
        family.setPet(pet);
    }

    @Test
    public void petToStringTest() {
        String expected = "\n{DOG ,\nnickname:'Rock', \n" +
                "age:5, \n" +
                "trickLevel=75, \n" +
                "habits=[eat, drink, sleep], \n" +
                "canFly=false, \n" +
                "numberOfLegs=4, \n" +
                "hasFur=true}";

        assertEquals(expected, pet.toString());
    }

    @Test
    public void humanToStringTest() {
        String expected = "Human\n" +
                "{name='Jane', \n" +
                "surname='Corleone', \n" +
                "year=1890, \n" +
                "iq=60, \n" +
                "schedule=[\n" +
                "[MONDAY, Walk the dog]]}";

        assertEquals(expected, mother.toString());
    }

    @Test
    public void familyToStringTest() {
        String expected = "\n" +
                "Family\n" +
                "{ mother = Human\n" +
                "{name='Jane', \n" +
                "surname='Corleone', \n" +
                "year=1890, \n" +
                "iq=60, \n" +
                "schedule=[\n" +
                "[MONDAY, Walk the dog]]}, \n" +
                "father = Human\n" +
                "{name='Vito', \n" +
                "surname='Corleone', \n" +
                "year=1887, \n" +
                "iq=70, \n" +
                "schedule=[\n" +
                "[THURSDAY, Go to the gym]]}, \n" +
                "children = [Human\n" +
                "{name='Michael', \n" +
                "surname='Corleone', \n" +
                "year=1920, \n" +
                "iq=80, \n" +
                "schedule=[\n" +
                "[FRIDAY, Sleep all day]]}], \n" +
                "pet = \n" +
                "{DOG ,\n" +
                "nickname:'Rock', \n" +
                "age:5, \n" +
                "trickLevel=75, \n" +
                "habits=[eat, drink, sleep], \n" +
                "canFly=false, \n" +
                "numberOfLegs=4, \n" +
                "hasFur=true}}";

        assertEquals(expected, family.toString());
    }

    @Test
    public void addChildTest() {
        Human newChild = new Human("Human", "Child", 2000);
        newChild.setIq(60);
        newChild.setSchedule(new String[][]{{DayOfWeek.MONDAY.name(), "Walk the dog"}});

        int initialLength = family.countChildren();

        family.addChild(newChild);

        assertEquals(initialLength+1, family.countChildren());

        assertEquals(newChild, family.getChildren()[initialLength]);

        assertEquals(family, newChild.getFamily());
    }

    @Test
    public void deleteChildTestByEqualObject() {
        int initialLength = family.countChildren();

        assertTrue(family.deleteChild(child));

        assertEquals(initialLength-1, family.countChildren());

        assertNull(child.getFamily());
    }

    @Test
    public void deleteChildTestByNoEqualObject() {
        int initialLength = family.countChildren();

        Human newChild = new Human("Human", "Child", 2000);

        assertFalse(family.deleteChild(newChild));

        assertEquals(initialLength, family.countChildren());
    }

    @Test
    public void deleteChildTestByIndex() {
        int initialLength = family.countChildren();

        assertTrue(family.deleteChild(0));

        assertEquals(initialLength-1, family.countChildren());

        assertNull(child.getFamily());
    }

    @Test
    public void deleteChildTestByNonExistentIndex() {
        int initialLength = family.countChildren();

        assertFalse(family.deleteChild(99));

        assertEquals(initialLength, family.countChildren());
    }

    @Test
    public void petEqualTest() {
        Pet pet2 = new Pet(Species.DOG, "Rock", 5, 75, new String[]{"eat", "drink", "sleep"});
        assertTrue(pet.equals(pet2));
        assertEquals(pet.hashCode(), pet2.hashCode());
    }

    @Test
    public void petSpeciesNotEqualTest() {
        Pet pet2 = new Pet(Species.CAT, "Rock", 5, 75, new String[]{"eat", "drink", "sleep"});
        assertFalse(pet.equals(pet2));
        assertNotEquals(pet.hashCode(), pet2.hashCode());
    }

    @Test
    public void petNicknameNoEqualTest() {
        Pet pet2 = new Pet(Species.CAT, "Rocky", 5, 75, new String[]{"eat", "drink", "sleep"});
        assertFalse(pet.equals(pet2));
        assertNotEquals(pet.hashCode(), pet2.hashCode());
    }

    @Test
    public void petTrickLevelNoEqualTest() {
        Pet pet2 = new Pet(Species.DOG, "Rock", 5, 50, new String[]{"eat", "drink", "sleep"});
        assertFalse(pet.equals(pet2));
        assertNotEquals(pet.hashCode(), pet2.hashCode());
    }

    @Test
    public void petAgeNoEqualTest() {
        Pet pet2 = new Pet(Species.DOG, "Rock", 6, 75, new String[]{"eat", "drink", "sleep"});
        assertFalse(pet.equals(pet2));
        assertNotEquals(pet.hashCode(), pet2.hashCode());
    }

    @Test
    public void humanEqualTest() {
        Human father2 = new Human("Vito", "Corleone", 1887);
        father2.setIq(70);

        assertTrue(father.equals(father2));

        assertEquals(father.hashCode(), father.hashCode());
    }

    @Test
    public void humanNameNoEqualTest() {
        Human father2 = new Human("Viton", "Corleone", 1887);
        father2.setIq(60);

        assertFalse(father.equals(father2));
        assertNotEquals(father.hashCode(), father2.hashCode());
    }

    @Test
    public void humanSurnameNoEqualTest() {
        Human father2 = new Human("Vito", "Corl", 1887);
        father2.setIq(60);

        assertFalse(father.equals(father2));
        assertNotEquals(father.hashCode(), father2.hashCode());
    }

    @Test
    public void humanYearNoEqualTest() {
        Human father2 = new Human("Vito", "Corleone", 188);
        father2.setIq(60);

        assertFalse(father.equals(father2));
        assertNotEquals(father.hashCode(), father2.hashCode());
    }

    @Test
    public void humanIqNoEqualTest() {
        Human father2 = new Human("Vito", "Corleone", 1887);
        father2.setIq(10);

        assertFalse(father.equals(father2));
        assertNotEquals(father.hashCode(), father2.hashCode());
    }

    @Test
    public void familyEqualTest() {
        Family family2 = new Family(mother, father);

        assertTrue(family.equals(family2));

        assertEquals(family.hashCode(), family2.hashCode());
    }

    @Test
    public void familyFatherNoEqualTest() {
        Human father2 = new Human("Viton", "Corleones", 188);

        Family family2 = new Family(mother, father2);

        assertFalse(family.equals(family2));
        assertNotEquals(family.hashCode(), family2.hashCode());
    }

    @Test
    public void familyMotherNoEqualTest() {
        Human mother2 = new Human("Viton", "Corleones", 188);

        Family family2 = new Family(mother2, father);

        assertFalse(family.equals(family2));
        assertNotEquals(family.hashCode(), family2.hashCode());
    }

    @Test
    public void feedPetTest() {
        boolean isTimeToFeed = true;

        assertTrue(father.feedPet(isTimeToFeed));
    }

    @Test
    public void feedPetByTrickTest() {
        boolean isTimeToFeed = false;

        pet.setTrickLevel(100);

        assertTrue(father.feedPet(isTimeToFeed));
    }

    @Test
    public void feedPetNoTimeTest() {
        boolean isTimeToFeed = false;
        pet.setTrickLevel(0);
        assertFalse(father.feedPet(isTimeToFeed));
    }

    @Test
    public void countChildrenTest() {
        assertEquals(family.countChildren(), family.getChildren().length);
    }

    @Test
    public void countFamilyTest() {
        assertEquals(3, family.countFamily());
        family.addChild(new Human("Viton", "Corleones", 1887));
        assertEquals(4, family.countFamily());
    }

}
