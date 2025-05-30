package hw10;

import hw10.Human.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Scanner;

import static hw10.Texts.*;

public class Menu {

    private final FamilyController controller;
    private final Scanner scanner;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Menu(FamilyController familyController) {
        this.controller = familyController;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        while (true) {
            displayMainMenu();
            String input = scanner.nextLine().trim();
            if (input.equals("exit"))  {
                out.println("Exiting...");
                break;
            }
            processMainMenu(input);
        }
    }

    private void displayMainMenu() {
        out.printf("\nAvailable commands:");
        out.printf("\n1. Fill with test data");
        out.printf("\n2. Display all families");
        out.printf("\n3. Display families with member more then");
        out.printf("\n4. Display families with member less then");
        out.printf("\n5. Count families with exact number of members");
        out.printf("\n6. Create new family");
        out.printf("\n7. Delete family by index");
        out.printf("\n8. Edit family by index");
        out.printf("\n9. Remove all children older than age");
        out.printf("\nexit - Exit the program...");
        out.print("\nEnter a command: ");
    }

    private void processMainMenu(String input) {
        try {
            int command = Integer.parseInt(input);
            switch (command) {
                case 1 -> controller.fillWithTestData();
                case 2 -> controller.displayAllFamilies();
                case 3 -> handleFamilyBiggerThan();
                case 4 -> handleFamilyLessThan();
                case 5 -> handleCountFamiliesWithMembers();
                case 6 -> handleCreateNewFamily();
                case 7 -> handleDeleteFamily();
                case 8 -> handleEditFamily();
                case 9 -> handleDeleteAllChildrenOlderThan();
                default -> out.println("Invalid command. Please enter 1-9 or exit.");
            }
        } catch (NumberFormatException e) {
            out.printf("Invalid command. Please enter 1-9 or exit.");
        }
    }

    private void handleFamilyBiggerThan() {
        int count = readPositiveInt(
                "Enter a number of members: " ,
                "Numbers of members must be positive!");
        controller.getFamiliesBiggerThan(count);
    }

    private void handleFamilyLessThan() {
        int count = readPositiveInt(
                "Enter a number of members: " ,
                "Numbers of members must be positive!");
        controller.getFamiliesLessThan(count);
    }

    private void handleCountFamiliesWithMembers() {
        int count = readPositiveInt(
                "Enter a number of members: " ,
                "Numbers of members must be positive!");
        controller.countFamilyWithMemberNumber(count);
    }

    private void handleCreateNewFamily() {

        out.println("Enter mother`s details:");
        Human mother = createHuman("mother");
        if (mother == null) {
            out.println("Failed to create mother. Aborting family creation!");
            return;
        }
        out.println("Enter father`s details:");
        Human father = createHuman("father");
        if (father == null) {
            out.println("Failed to create father. Aborting family creation!");
            return;
        }
        controller.createNewFamily((Woman) mother, (Man) father);
    }

    private Human createHuman(String type) {

        String name = readNonEmptyString("Name: ", "Name cannot be empty.");
        String surname = readNonEmptyString("Surname: ", "Surname cannot be empty.");
        String birthDay = readDate();
        int iq = readPositiveInt("IQ: ", "IQ must be positive.", 0, 100);

        return switch (type) {
            case "mother" -> new Woman(name, surname, birthDay, iq, Map.of(), null);
            case "father" -> new Man(name, surname, birthDay, iq, Map.of(), null);
            case "boy" -> new Man(name, surname, birthDay, iq, null, null);
            case "girl" -> new Woman(name, surname, birthDay, iq, null, null);
            default -> throw new IllegalArgumentException("Invalid type of human.");
        };
    }

    private void handleDeleteFamily() {
        int index = readPositiveInt("Enter a family index: ", "Index must be positive.");
        controller.deleteFamilyByIndex(index);
    }

    private void handleEditFamily() {

        int index = readPositiveInt("Enter a family index: ", "Index must be positive.");
        Family family = controller.getFamilyById(index);
        if (family == null) {
            out.println("Invalid index.");
            return;
        }

        while (true) {
            displayEditMenu();
            String input = readNonEmptyString(
                    "Enter a command: ",
                    "Invalid command. Please enter 1, 2 or 3." );
            if (input.equals("3")) break;
            if (!processEditMenu(input, family)) {
                out.println("Invalid command. Please enter 1, 2 or 3.");
            }
        }
    }

    private void displayEditMenu() {
        out.println("Edit family options: ");
        out.println("1. Born child");
        out.println("2. Adopt child");
        out.println("3. Return to main menu");
    }

    private boolean processEditMenu(String input, hw10.Family family) {
        try {
            int command = Integer.parseInt(input);
            switch (command) {
                case 1 -> handleBornChild(family);
                case 2 -> handleAdoptChild(family);
                default -> {
                    return false;
                }
            }
            return true;
        } catch (NumberFormatException e) {
            out.println("Invalid number format.");
            return false;
        } catch (IllegalArgumentException e) {
            out.println(e.getMessage());
            return false;
        }
    }

    private void handleBornChild(hw10.Family family) {
        String maleName = readNonEmptyString("Enter name for boy: ", "Name cannot be empty.");
        String femaleName = readNonEmptyString("Enter name for girl: ", "Name cannot be empty.");
        String birthDay = readDate();
        controller.bornChild(family, maleName, femaleName, birthDay);
    }

    private void handleAdoptChild(Family family) {
        String gender = readGender("Enter child`s gender (male or female): ");
        Human child = createHuman(gender.equals("male") ? "boy" : "girl");
        if (child == null) {
            out.println("Failed to create child. Aborting adopting child!");
            return;
        }
        controller.adoptChild(family, child);
    }

    private void handleDeleteAllChildrenOlderThan() {
        int age = readPositiveInt("Enter an age: ", "Age must be positive.");
        controller.deleteAllChildrenOlderThan(age);
    }

    private int readPositiveInt(String prompt, String errorMessage) {
        return readPositiveInt(prompt, errorMessage, 0, Integer.MAX_VALUE);
    }

    private int readPositiveInt(String prompt, String errorMessage, int minValue, int maxValue) {
        while (true) {
            try {
                out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine().trim());
                if (value < minValue || value > maxValue) throw new IllegalArgumentException(errorMessage);
                return value;
            } catch (NumberFormatException e) {
                out.println(errorMessage);
            } catch (IllegalArgumentException e) {
                out.println(e.getMessage());
            }
        }
    }

    private String readNonEmptyString(String prompt, String errorMessage) {
        while (true) {
            out.print(prompt);
            String value = scanner.nextLine().trim();
            if (value.isEmpty()) out.println(errorMessage);
            else return value;
        }
    }

    private String readGender(String prompt) {
        while (true) {
            out.print(prompt);
            String value = scanner.nextLine().trim();
            if (value.equals("male") || value.equals("female")) return value;
            out.println("Invalid gender. Please enter either male or female.");
        }
    }

    private String readDate() {
        while (true) {
            try {
                int year = readPositiveInt("Enter year: ", "Year must be positive.", 1, 9999);
                int month = readPositiveInt("Enter month: ", "Month must be positive.", 1, 12);
                int day = readPositiveInt("Enter day: ", "Day must be positive.", 1, 31);
                return LocalDate.of(year, month, day).format(DATE_FORMATTER);
            } catch (IllegalArgumentException e) {
                out.println("Invalid date format.");
            }
        }
    }
}
