package hw01;

import javax.swing.*;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.Random;

public class Numbers {
    public static void main(String[] args) {
        PrintStream out = System.out;
        InputStream in = System.in;
        Scanner scanner = new Scanner(in);

        String askName = "What is your name?";
        String emptyName = "Name cannot be empty. Please try again.";
        String startText = "Let the game begin!";
        String taskText = "Your number is:";
        String invalidInput = "Invalid input. Please enter a valid number.";
        String lowNumberText = "Your number is too small. Please, try again.";
        String bigNumberText = "Your number is too big. Please, try again.";
        String endText = "Congratulations,";

        int secretNumber = new Random().nextInt(101);

        String name = "";

        while (name.isBlank()) {
            out.println(askName);
            name = scanner.nextLine();

            if (name.isBlank()) {
                out.printf("%s\n", emptyName);
            }
        }

        out.printf("%s\n", startText);

        while (true){

            out.printf("%s\n", taskText);

            if (!scanner.hasNextInt()) {
                out.printf("%s\n", invalidInput);
                scanner.next();
                continue;
            }

            int number = scanner.nextInt();

            if (number < secretNumber) {
                out.printf("%s\n", lowNumberText);
            } else if (number > secretNumber) {
                out.printf("%s\n", bigNumberText);
            } else {
                out.printf("%s %s!\n", endText, name);
                break;
            }
        }

    }
}
