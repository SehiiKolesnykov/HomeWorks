package hw05;

import java.io.PrintStream;

public class Main {

    static PrintStream out = System.out;

    private static final String petTypeError = "Invalid pet type: ";
    private static final String humanTypeError = "Invalid human type: ";

    public static void main(String[] args) {

        Human mother = createHuman("mother", null);
        Human father = createHuman("father", null);
        Human son1 = createHuman("son1", null);

        Pet dog = createPet("dog");
        Pet cat = createPet("cat");
        Pet bird = createPet("bird");

        Family family1 = new Family(mother, father);

        family1.getFather().setFamily(family1);
        family1.getMother().setFamily(family1);
        family1.addChild(son1);
        family1.setPet(dog);

        Human mother2 = createHuman("mother", null);
        Human father2 = createHuman("father", null);

        Family family2 = new Family(mother2, father2);

        family2.getFather().setFamily(family2);
        family2.getMother().setFamily(family2);

        Human son2 = createHuman("son2", family2);

        family2.addChild(son2);

        family2.getFather().setName("Tom");
        family2.getFather().setSurname("Cruise");
        family2.getFather().setYear(1962);
        family2.getFather().setIq(66);
        family2.getFather().setSchedule(new String[][] {
                {DayOfWeek.MONDAY.name(), "sleep"}, {DayOfWeek.WEDNESDAY.name(), "walk the dog"}});

        family2.getMother().setName("Kate");
        family2.getMother().setSurname("Holmes");
        family2.getMother().setYear(1978);
        family2.getMother().setIq(90);
        family2.getMother().setSchedule(new String[][] {
                {DayOfWeek.MONDAY.name(), "Walk"}, {DayOfWeek.WEDNESDAY.name(), "go to the forest"}});

        family2.getChildren()[0].setName("Connor");
        family2.getChildren()[0].setSurname("Cruise");
        family2.getChildren()[0].setYear(1995);
        family2.getChildren()[0].setIq(30);
        family2.getChildren()[0].setSchedule(new String[][] {
                {DayOfWeek.MONDAY.name(), "Meet my parents"},{DayOfWeek.WEDNESDAY.name(), "call my grandma"}});

        family2.setPet(cat);

        out.println("\nInformation about family :\n " + family1.toString());
        out.println("\n--------------------------------------");
        out.println("\nInformation about family2 :\n " + family2.toString());

        demonstrateHumanMethods(son1);
        demonstratePetMethods(dog);
        demonstrateDeleteChild(family1, son1);

        out.println("\n--------------------------------------");

        out.println("\nCreating many Human objects to trigger garbage collection:");
        for (int i = 0; i < 4_000_000; i++) {
            Human temp = new Human("Temp" + i, "Person", 2000);
            if (i % 1_000_000 == 0) {
                System.gc();
                out.println("Created " + i + " Human objects");
            }
        }
    }

    private static Pet createPet(String type) {
        Pet pet;
        switch (type.toLowerCase()) {

            case "dog":
                pet = new Pet();
                pet.setSpecies(Species.DOG);
                pet.setNickname("Rock");
                pet.setAge(5);
                pet.setTrickLevel(75);
                pet.setHabits(new String[]{"eat", "drink", "sleep"});
                break;

            case "cat":
                pet = new Pet();
                pet.setSpecies(Species.CAT);
                pet.setNickname("Kitty");
                pet.setAge(7);
                pet.setTrickLevel(46);
                pet.setHabits(new String[]{"eat", "drink", "sleep"});
                break;

            case "bird":
                pet = new Pet();
                pet.setSpecies(Species.BIRD);
                pet.setNickname("Seed");
                pet.setAge(1);
                pet.setTrickLevel(50);
                pet.setHabits(new String[]{"fly", "sing", "eat"});
                break;

            default:
                throw new IllegalArgumentException(petTypeError + type);
        }

        return pet;
    }

    private static Human createHuman(String type, Family family) {

        Human human;
        String[][] schedule = initializeSchedule();

        switch (type) {

            case "father":
                human = new Human();
                human.setName("Vito");
                human.setSurname("Corleone");
                human.setYear(1887);
                human.setIq(80);
                human.setSchedule(schedule);
                human.setFamily(family);
                break;

            case "mother":
                human = new Human();
                human.setName("Jane");
                human.setSurname("Corleone");
                human.setYear(1890);
                human.setIq(60);
                human.setSchedule(schedule);
                human.setFamily(family);
                break;

            case "son1":
                human = new Human();
                human.setName("Michael");
                human.setSurname("Corleone");
                human.setYear(1920);
                human.setIq(90);
                human.setSchedule(schedule);
                human.setFamily(family);
                break;

            case "son2":
                human = new Human();
                human.setName("Santino");
                human.setSurname("Corleone");
                human.setYear(1916);
                human.setIq(50);
                human.setSchedule(schedule);
                human.setFamily(family);
                break;

            default:
                throw new IllegalArgumentException(humanTypeError + type);
        }

        return human;
    }

    private static String[][] initializeSchedule() {
        String[][] schedule = new String[7][2];
        for (int i = 0; i < DayOfWeek.values().length; i++) {
            schedule[i][0] = DayOfWeek.values()[i].name();
        }
        schedule[0][1] = "do home work";
        schedule[1][1] = "go to courses; watch a film";
        schedule[2][1] = "go to playground";
        schedule[3][1] = "walk the dog";
        schedule[4][1] = "meet my friends near cinema";
        schedule[5][1] = "talk with parents";
        schedule[6][1] = "sleep all day";
        return schedule;
    }

    private static void printHumanInfo(Human human) {
        out.println("Human info: " + human);
    }

    private static void demonstrateHumanMethods (Human human) {
        out.println("\n--------------------------------------");
        out.println("\n Demonstrating methods for: " + human.getName() + " " + human.getSurname() + "\n");
        out.println(human);
        human.greetPet();
        human.describePet();
        human.feedPet(true);
        human.feedPet(false);
    }

    private static void demonstratePetMethods (Pet pet) {
        out.println("\n--------------------------------------");
        out.println("\nDemonstrating methods for a pet: " + pet.getNickname());
        out.println(pet);
        pet.eat();
        pet.respond();
        pet.foul();
    }

    private static void demonstrateDeleteChild (Family family, Human child) {
        out.println("\n--------------------------------------");
        out.println("\nDeleting child: " + child);
        family.deleteChild(child);
        out.println("\n--------------------------------------");
        out.println("\nFamily after deleting child: \n" + family);
    }

}
