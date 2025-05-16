package hw08;

import hw08.Human.*;
import hw08.Pets.*;
import static hw08.Texts.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Main {

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

        LocalDate currentDate = LocalDate.now();


        out.printf("\n\nDemo Long birthDay timestamp: %s", father.getBirthDay());
        out.printf("\n\nDemo describeAge: %s, \n birthday : %s, \n current data : %s",
                son.describeAge(),
                son.getBirthdayToString(),
                currentDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        demonstratingAdoptChild();
        out.printf("\n\nDemo refactor toString: \n%s", father);


//        out.printf(familyInfoText, family.toString());

//        demonstrateHumanMethods(mother, pet);
//        demonstrateHumanMethods(father, pet);
//        demonstratePetMethods(pets);
//        demonstrateBornChild(family);
//        out.printf(pet.toString());
//
//        out.printf(familyInfoText, family.toString());
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
            case "father" -> new Man("James", "Potter", "27/03/1960", 70, defaultSchedule, null);
            case "mother" -> new Woman("Lily", "Potter", "30/01/1960", 90, defaultSchedule, null);
            case "child" -> new Man("Harry", "Potter", "31/07/1980", 60, defaultSchedule, null);
            default -> {
                out.printf(errorHumanTypeText, type);
                yield null;
            }
        };

        return human;
    }

    private static void printHumanInfo(Human human) {
        out.printf(humanInfoText, human);
    }

    private static void demonstrateHumanMethods (Human human, Pet pet) {
        out.printf(textBorder);
        out.printf(demoHumanMethodText, human.getName(), human.getSurname());
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
            out.printf(textBorder);
            out.printf(demoPetMethodsText, pet.getNickname());
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

        out.printf(textBorder);
        out.printf(demoBornChildText);
        Human newChild = ((Woman) family.getMother()).bornChild("01/01/2000");
        if (newChild != null) {
            out.printf(demoBornEndText, newChild.toString());
            return;
        }
        out.printf(errorBornChildText);
    }

    private static void demonstratingAdoptChild () {

        Human adoptedChild =  AdoptChild
                .createAdoptChild("Alex", "Smith", "10/10/1999", 50, "male");

        out.printf("\n\nDemo adoptChild : \n%s", adoptedChild.toString());
    }
}
