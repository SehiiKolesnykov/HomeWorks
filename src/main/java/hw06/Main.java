package hw06;

import hw06.Human.*;
import hw06.Pets.*;

import java.io.PrintStream;

public class Main {
    static final PrintStream out = System.out;

    @FunctionalInterface
    interface Action<T> {
        void apply(T t);
    }
    public static void main(String[] args) {

        Man father = (Man) createHuman("father");
        Woman mother = (Woman) createHuman("mother");
        Man son = (Man) createHuman("child");
        Dog pet = (Dog) createPet("dog");
        Pet[] arrOfPets = {
                createPet("dog"),
                createPet("cat"),
                createPet("robocat"),
                createPet("bird"),
                createPet("snake"),
                createPet("fish"),
                createPet("dsdsff")
        };

        Family family = new Family(mother, father);
        family.addChild(son);
        family.setPet(pet);

        out.printf(Texts.familyInfoText, family.toString());

        demonstrateHumanMethods(mother);
        demonstrateHumanMethods(father);
        demonstratePetMethods(arrOfPets);
        demonstrateBornChild(family);

        out.printf(Texts.familyInfoText, family.toString());
    }

    private static Pet createPet(String type) {
        Pet pet;

        pet = switch (type) {

            case "dog" -> new Dog("Найда", 5, 30, new String[]{"walk", "run", "eat"});
            case "cat" -> new DomesticCat("Мурчик", 2, 70, new String[]{"sleep", "play", "eat"});
            case "robocat" -> new RoboCat("Echo", 1, 100, new String[]{"sing", "speak", "charge"});
            case "bird" -> new Bird("Твіті", 3, 50, new String[]{"sing", "fly"});
            case "snake" -> new Snake("Коко", 4, 10, new String[]{"sleep", "hunt"});
            case "fish" -> new Fish("Вусач", 1, 35, new String[]{"swim", "hide"});

            default -> {
                Pet unknown = new Dog("Unknown", 1, 0, new String[]{"unknown"});
                unknown.setSpecies(Species.UNKNOWN);
                yield unknown;
            }

        };

        return pet;
    }

    private static Human createHuman(String type) {
        Human human;

        String[][] defaultSchedule = {{DayOfWeek.MONDAY.name(), "Eat outside"}, {DayOfWeek.FRIDAY.name(), "Walk in the park"}};

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

    private static <T> void processArray (T[] array, Action<T> action) {
        for (T item : array) {
            action.apply(item);
        }
    }

    private static void demonstrateHumanMethods (Human human) {
        out.printf(Texts.textBorder);
        out.printf(Texts.demoHumanMethodText, human.getName(), human.getSurname());
        out.println(human);
        human.greetPet();
        human.describePet();
        human.feedPet(true);
        human.feedPet(false);
        if (human instanceof Man) {
            ((Man) human).repairCar();
        } else if (human instanceof Woman) {
            ((Woman) human).makeup();
        }
    }

    private static void demonstratePetMethods (Pet[] pet) {

        for (Pet animal : pet) {
            out.printf(Texts.textBorder);
            out.printf(Texts.demoPetMethodsText, animal.getNickname());
            out.println(animal);
            if (animal.getSpecies() != Species.UNKNOWN) {
                animal.eat();
                animal.respond();
                if (animal instanceof CanFoul) {
                    ((CanFoul) animal).foul();
                }
            }
        }
    }

    private static void demonstrateBornChild (Family family) {

        out.printf(Texts.textBorder);
        out.printf(Texts.demoBornChildText);
        Human newChild = ((Woman) family.getMother()).bornChild(2000);
        if (newChild != null) {
            family.addChild(newChild);
            out.printf(Texts.demoBornEndText, newChild.toString());
            return;
        }
        out.printf(Texts.errorBornChildText);
    }
}
