package hw02;

import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;

public class TargetShooting {

    static PrintStream out = System.out;
    static Scanner scanner = new Scanner(System.in);

    static int fieldSize = 5;
    static int targetSize = 5;
    static int hits;

    static int target = 1;
    static int hit = 2;
    static int miss = 3;

    static Boolean isCanCreate() {
        Boolean result = targetSize <= fieldSize;
        return result;
    }
    static int[][] createField(int size) {

        Random random = new Random();

        int[][] field = new int[size + 1][size + 1];

        boolean isTargetRow = random.nextBoolean();

        int targetIndexX = isTargetRow
                ? random.nextInt(size - 1) + 1
                : Math.max(1, random.nextInt(size - targetSize + 1));

        int targetIndexY = isTargetRow
                ? Math.max(1, random.nextInt(size - targetSize + 1))
                : random.nextInt(size - 1) + 1;

        for (int i = 0; i < targetSize; i++) {
            int x = isTargetRow ? targetIndexX : targetIndexX + i;
            int y = isTargetRow ? targetIndexY + i : targetIndexY;
            field[x][y] = target;
        }

        return field;
    }

    static int[][] shot(int[][] field, int[][] shots, int shotsCount) {

        int row = shots[shotsCount - 1][0];
        int column = shots[shotsCount - 1][1];

        Boolean isHit = field[row][column] == target;
        Boolean isReHit = field[row][column] == hit || field[row][column] == miss;

        if (!isReHit) {
            field[row][column] = isHit ? hit : miss;
            hits = isHit ? hits + 1 : hits;
        }

        return field;
    }

    static String battleField(int[][] field) {

        StringBuilder printField = new StringBuilder();

        int size = field.length;
        String cell;


        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                Boolean firstRow = x == 0;
                Boolean firstColumn = y == 0;
                Boolean isHit = field[x][y] == hit;
                Boolean isMiss = field[x][y] == miss;

                if (firstRow) {
                    cell = String.format(" %d |", y);
                } else if (firstColumn) {
                    cell = String.format(" %d |", x);
                } else if (isHit) {
                    cell = " X |";
                } else if (isMiss) {
                    cell = " * |";
                } else {
                    cell = " - |";
                }

                printField.append(cell);

            }

            printField.append("\n");
        }

        return printField.toString();
    }

    public static void main(String[] args) {

        String incorrectData = "The field length is less than the target length. Change the initial data!";
        String startText = " All Set. Get ready to rumble!.";
        String enterRow = "Enter the shooting row";
        String enterColumn = "Enter the shooting column";
        String invalidInput = "Invalid input. Please enter a valid number from 1 to";
        String hitText = "Hit!";
        String missText = "Miss!";
        String winText = "You have won!";
        String loseText = "You have lost!";

        if (!isCanCreate()) {
            out.println(incorrectData);
            return;
        }

        int[][] field = createField(fieldSize);

        int[][] shots = new int[100][2];
        int shotsCount = 0;
        int hitsCount = 0;

        String game;
        game = battleField(field);

        out.println(startText);
        out.println(game);

        while (true) {

            int row;
            int column;

            while (true) {

                out.println(enterRow);

                if (!scanner.hasNextInt()) {
                    out.printf("%s %s\n", invalidInput, fieldSize);
                    scanner.next();
                    continue;
                }

                row = scanner.nextInt();

                Boolean isInRange = row < 1 || row > fieldSize;

                if (isInRange) {
                    out.printf("%s %s\n", invalidInput, fieldSize);
                    continue;
                }

                break;
            }

            while (true) {

                out.println(enterColumn);

                if (!scanner.hasNextInt()) {
                    out.printf("%s %s\n", invalidInput, fieldSize);
                    scanner.next();
                    continue;
                }

                column = scanner.nextInt();

                Boolean isInRange = column < 1 || column > fieldSize;

                if (isInRange) {
                    out.printf("%s %s\n", invalidInput, fieldSize);
                    continue;
                }

                break;
            }

            shots[shotsCount][0] = row;
            shots[shotsCount][1] = column;

            shotsCount++;

            field = shot(field, shots, shotsCount);

            if (hits != targetSize) {
                if (hits > hitsCount) {
                    out.println(hitText);
                    hitsCount++;
                } else {
                    out.println(missText);
                }
                game = battleField(field);
                out.println(game);

            } else if (shotsCount > 100){

                out.println(loseText);

                game = battleField(field);
                out.println(game);
                break;

            } else {

                out.println(winText);

                game = battleField(field);
                out.println(game);
                break;

            }
        }

    }
}
