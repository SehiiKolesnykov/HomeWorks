package hw01;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;

public class Quiz {
    public static void main(String[] args) {

        PrintStream out = System.out;
        InputStream in = System.in;
        Scanner scanner = new Scanner(in);
        Random random = new Random();

        String askName = "What is your name?";
        String startText = "Let the game begin!";
        String taskText = "Guess the year:";
        String invalidInput = "Invalid input. Please enter a valid number.";
        String lowNumberText = "The year is too small. Please, try again.";
        String bigNumberText = "The year is too big. Please, try again.";
        String endText = "Congratulations,";
        String finalText = "Your numbers:";

        Object[][] events = {
                {1939, "When did the World War II begin?"},
                {1991, "When was the Act of Declaration of Independence of Ukraine adopted?"},
                {1995, "When was the Java programming language created?"},
                {1970, "When was the band Queen founded?"},
                {1837, "When was the first mechanical computer invented?"},
                {1969, "When was the first manned Moon landing?"},
                {1945, "When was the first program run on a computer?"}
        };

        int eventIndex = random.nextInt(events.length);
        int secretYear = (int) events[eventIndex][0];
        String eventQuestion = (String) events[eventIndex][1];

        out.println(askName);
        String name = scanner.nextLine();

        out.println(startText);
        out.println(eventQuestion);

        int[] enteredYears = new int[100];
        int yearsCount = 0;

        while (true){

            out.println(taskText);

            if (!scanner.hasNextInt()) {
                out.println(invalidInput);
                scanner.next();
                continue;
            }

            int year = scanner.nextInt();

            if (yearsCount < enteredYears.length) {
                enteredYears[yearsCount++] = year;
            }

            if (year < secretYear) {
                out.println(lowNumberText);
            } else if (year > secretYear) {
                out.print(bigNumberText);
            } else {
                out.printf("%s %s!\n", endText, name);
                break;
            }
        }

        int[] userYears = Arrays.copyOf(enteredYears, yearsCount);
        Arrays.sort(userYears);

        StringBuilder yearsText = new StringBuilder();

        for (int i = 0; i < userYears.length; i++) {
            if (i > 0) yearsText.append(",");
            yearsText.append(userYears[i]);
        }

        out.printf("%s %s.\n",finalText, yearsText);

    }
}
