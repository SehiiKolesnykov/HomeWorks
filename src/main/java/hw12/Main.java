package hw12;

import hw12.Human.*;
import hw12.Pets.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static hw12.Texts.*;

public class Main {

    public static void main(String[] args) {

        FamilyDao familyDao = new CollectionFamilyDao();
        FamilyService familyService = new FamilyService(familyDao);
        FamilyController controller = new FamilyController(familyService);

        Man father1 = (Man) createHuman("father1");
        Woman mother1 = (Woman) createHuman("mother1");
        Man son1 = (Man) createHuman("child1");

        Man father2 = (Man) createHuman("father2");
        Woman mother2 = (Woman) createHuman("mother2");

        Dog dog = (Dog) createPet("dog");
        DomesticCat domesticCat = (DomesticCat) createPet("cat");
        RoboCat roboCat = (RoboCat) createPet("robocat");

        // Demo Family Controller //

        out.printf(demoCreateFamilyText);
        Family family1 = controller.createNewFamily(mother1, father1);
        Family family2 = controller.createNewFamily(mother2, father2);

        out.printf(demoAddChildText);
        controller.bornChild(family1, "James Jr.", "Lili Jr.", "10/10/2018");
        controller.bornChild(family2, "Ron", "Amely", "01/02/2020");
        controller.adoptChild(family1, son1);

        out.printf(demoAddPetsText);
        controller.addPet(0, dog);
        controller.addPet(0, domesticCat);
        controller.addPet(1, roboCat);

        out.printf(demoDisplayAllFamiliesText);
        out.printf(textBorder);
        controller.displayAllFamilies();

        controller.getFamiliesBiggerThan(3);

        controller.getFamiliesLessThan(4);

        controller.countFamilyWithMemberNumber(4);

        controller.getFamilyById(0);

        out.printf(demoGetPetsText, 0);
        controller.getPets(0).forEach(out::println);

        controller.deleteAllChildrenOlderThan(5);
        controller.displayAllFamilies();

        controller.count();

        controller.deleteFamilyByIndex(1);
        controller.displayAllFamilies();


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
            case "father1" -> new Man("James", "Potter", "27/03/1960", 70, defaultSchedule, null);
            case "mother1" -> new Woman("Lily", "Potter", "30/01/1960", 90, defaultSchedule, null);
            case "child1" -> new Man("Harry", "Potter", "31/07/1980", 60, defaultSchedule, null);
            case "father2" -> new Man("Newton", "Scamander", "24/02/1897", 80, defaultSchedule, null);
            case "mother2" -> new Woman("Porpentina", "Goldstein", "19/08/1901", 90, defaultSchedule, null);
            default -> {
                out.printf(errorHumanTypeText, type);
                yield null;
            }
        };

        return human;
    }
}
