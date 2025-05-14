package hw07;

import hw07.Human.*;
import hw07.Pets.*;

import java.io.PrintStream;
import java.util.*;

public class Main {
    static final PrintStream out = System.out;

    public static void main(String[] args) {

        Man father = (Man) createHuman("father");
        Woman mother = (Woman) createHuman("mother");
        Man son = (Man) createHuman("child");
        Dog pet = (Dog) createPet("dog");
        Set<Pet> pets = new HashSet<>(Set.of(
                createPet("dog"),
                createPet("cat"),
                createPet("robocat"),
                createPet("bird"),
                createPet("snake"),
                createPet("fish"),
                createPet("dsdsff")));

        Family family = new Family(mother, father);
        family.addFamilyMember(son);
        family.addFamilyMember(pet);
        pets.forEach(family::addFamilyMember);

        out.printf(Texts.familyInfoText, family.toString());

        demonstrateHumanMethods(mother, pet);
        demonstrateHumanMethods(father, pet);
        demonstratePetMethods(pets);
        demonstrateBornChild(family);
        out.printf(pet.toString());

        out.printf(Texts.familyInfoText, family.toString());
    }

    private static Pet createPet(String type) {
        Pet pet;

        pet = switch (type) {

            case "dog" -> new Dog("Найда", 5, 30, Set.of("walk", "run", "eat"));
            case "cat" -> new DomesticCat("Мурчик", 2, 70,Set.of("sleep", "play", "eat"));
            case "robocat" -> new RoboCat("Echo", 1, 100, Set.of("sing", "speak", "charge"));
            case "bird" -> new Bird("Твіті", 3, 50, Set.of("sing", "fly"));
            case "snake" -> new Snake("Коко", 4, 10, Set.of("sleep", "hunt"));
            case "fish" -> new Fish("Вусач", 1, 35, Set.of("swim", "hide"));

            default -> {
                Pet unknown = new Dog("Unknown", 1, 0, Set.of("unknown"));
                unknown.setSpecies(Species.UNKNOWN);
                yield unknown;
            }

        };

        return pet;
    }

    private static Human createHuman(String type) {
        Human human;

        Map<String,String> defaultSchedule = new HashMap<>();
        defaultSchedule.put(DayOfWeek.MONDAY.name(), "Eat outside");
        defaultSchedule.put(DayOfWeek.FRIDAY.name(), "Walk in the park");

        human = switch (type) {
            case "father" -> new Man("James", "Potter", 1960, 70, defaultSchedule, null);
            case "mother" -> new Woman("Lily", "Potter", 1960, 90, defaultSchedule, null);
            case "child" -> new Man("Harry", "Potter", 1980, 60, defaultSchedule, null);
            default -> {
                out.printf(Texts.errorHumanTypeText, type);
                yield null;
            }
        };

        return human;
    }

    private static void printHumanInfo(Human human) {
        out.printf(Texts.humanInfoText, human);
    }

    private static void demonstrateHumanMethods (Human human, Pet pet) {
        out.printf(Texts.textBorder);
        out.printf(Texts.demoHumanMethodText, human.getName(), human.getSurname());
        out.println(human);

        human.greetPet();
        human.describePets();
        human.feedPet(pet, true);

        human.feedAllPets(true);
        human.feedAllPets(false);
        if (human instanceof Man) {
            ((Man) human).repairCar();
        } else if (human instanceof Woman) {
            ((Woman) human).makeup();
        }
    }

    private static void demonstratePetMethods (Set<Pet> pets) {

        pets.forEach(pet -> {
            out.printf(Texts.textBorder);
            out.printf(Texts.demoPetMethodsText, pet.getNickname());
            out.println(pet);
            if (pet.getSpecies() != Species.UNKNOWN) {
                pet.eat();
                pet.respond();
                if (pet instanceof CanFoul) {
                    ((CanFoul) pet).foul();
                }
            }
        });
    }

    private static void demonstrateBornChild (Family family) {

        out.printf(Texts.textBorder);
        out.printf(Texts.demoBornChildText);
        Human newChild = ((Woman) family.getMother()).bornChild(2000);
        if (newChild != null) {
            out.printf(Texts.demoBornEndText, newChild.toString());
            return;
        }
        out.printf(Texts.errorBornChildText);
    }
}
