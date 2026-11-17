package dicegame;

import java.util.Scanner;

// Reads input from the keyboard safely, so wrong typing never crashes the game
public class InputReader {

    private final Scanner scanner;

    // one Scanner is used for all keyboard input in the game
    public InputReader() {
        scanner = new Scanner(System.in);
    }

    // keeps asking until the user types a whole number between min and max
    public int readNumber(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                int number = Integer.parseInt(line);
                if (number >= min && number <= max) {
                    return number;
                }
                System.out.println("Please enter a number from " + min
                        + " to " + max + ".");
            } catch (NumberFormatException e) {
                // parseInt fails if the text is not a whole number, e.g. "abc"
                System.out.println("That is not a number. Please try again.");
            }
        }
    }

    // pauses the game until the user presses Enter
    public void waitForEnter(String prompt) {
        System.out.print(prompt);
        scanner.nextLine();
    }

    // keeps asking until the user types a name that is not empty and not too long
    public String readName(String prompt, int maxLength) {
        while (true) {
            System.out.print(prompt);
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("The name cannot be empty.");
            } else if (name.length() > maxLength) {
                System.out.println("Please use " + maxLength
                        + " characters or fewer.");
            } else {
                return name;
            }
        }
    }
}
