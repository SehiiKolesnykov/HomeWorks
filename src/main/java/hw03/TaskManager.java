package hw03;

import java.io.PrintStream;
import java.util.Scanner;

public class TaskManager {

    private static final String askDay = "Please, input the day of the week: ";
    private static final String answer = "Your tasks for ";
    private static final String wrongInput = "Sorry, I don't understand you, please try again.";
    private static final String exitWord = "exit";
    private static final String exitMessage = "Exit the program...";
    private static final String changeTasksMessage = "Please, input new tasks for ";
    private static final String emptyTasksMessage = "Error: New tasks cannot be empty. Please try again.";

    public static void main(String[] args) {

        PrintStream out = System.out;
        Scanner scanner = new Scanner(System.in);

        String[][] schedule = initializeSchedule();

        processUserInput(schedule, scanner, out);

        scanner.close();
    }

    private static String[][] initializeSchedule() {
        String[][] schedule = new String[7][2];
        schedule[0][0] = "Sunday";
        schedule[0][1] = "do home work";
        schedule[1][0] = "Monday";
        schedule[1][1] = "go to courses; watch a film";
        schedule[2][0] = "Tuesday";
        schedule[2][1] = "go to playground";
        schedule[3][0] = "Wednesday";
        schedule[3][1] = "walk the dog";
        schedule[4][0] = "Thursday";
        schedule[4][1] = "meet my friends near cinema";
        schedule[5][0] = "Friday";
        schedule[5][1] = "talk with parents";
        schedule[6][0] = "Saturday";
        schedule[6][1] = "sleep all day";
        return schedule;
    }

    private static void processUserInput(String[][] schedule, Scanner scanner, PrintStream out) {

        while (true) {
            out.println(askDay);
            String input = scanner.nextLine().trim();
            String inputLowerCase = input.toLowerCase();

            if (input.equalsIgnoreCase(exitWord)) {
                out.println(exitMessage);
                break;
            }

            if (inputLowerCase.startsWith("change") || inputLowerCase.startsWith("reschedule")) {
                handleChangeCommand(schedule, scanner, out, inputLowerCase);
                continue;
            }

            boolean found = isDayInput(schedule, out, inputLowerCase);

            if (!found) {
                out.println(wrongInput);
            }
        }
    }

    private static boolean isDayInput(String[][] schedule, PrintStream out, String inputLowerCase) {

        return switch (inputLowerCase) {
            case "sunday" -> isFound(schedule, out, "Sunday");
            case "monday" -> isFound(schedule, out, "Monday");
            case "tuesday" -> isFound(schedule, out, "Tuesday");
            case "wednesday" -> isFound(schedule, out, "Wednesday");
            case "thursday" -> isFound(schedule, out, "Thursday");
            case "friday" -> isFound(schedule, out, "Friday");
            case "saturday" -> isFound(schedule, out, "Saturday");
            default -> false;
        };
    }

    private static boolean isFound(String[][] schedule, PrintStream out, String day) {
        for (String[] strings : schedule) {
            if (strings[0].equalsIgnoreCase(day)) {
                handlePrintTasks(strings[0], strings[1], out);
                return true;
            }
        }
        return false;
    }

    private static void handlePrintTasks(String day, String task, PrintStream out) {

        out.printf("%s%s: %s.\n", answer, day, task);
    }

    private static void handleChangeCommand(String[][] schedule, Scanner scanner, PrintStream out, String inputLowerCase) {
        String[] parts = inputLowerCase.split(" ", 2);
        if (parts.length == 2) {
            String day = parts[1].trim();
            for (int i = 0; i < schedule.length; i++) {
                if (schedule[i][0].equalsIgnoreCase(day)) {
                    out.printf("%s%s :\n", changeTasksMessage, schedule[i][0]);
                    String newTasks = "";

                    while (newTasks.isBlank()) {

                        newTasks = scanner.nextLine().trim();

                        if (newTasks.isBlank()) {
                            out.println(emptyTasksMessage);
                        }
                    }
                    schedule[i][1] = newTasks;
                    out.printf("%s%s %s\n", answer, schedule[i][0], "are updated!");
                    return;
                }
            }
        }
        out.println(wrongInput);
    }

}
