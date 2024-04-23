package tests;

import java.util.*;

public class genTest {
    public static String generateLine(int n) {
        StringBuilder line = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < n; i++) {
            int randomNumber = random.nextInt(10) + 1; // Generate a random number between 1 and 10
            line.append(randomNumber);
            if (i < n - 1) {
                line.append(","); // Add comma if it's not the last number
            }
        }
        return line.toString();
    }

    public static void main(String[] args) {
        int n = 40; // Example: Generate a line with 5 numbers
        for (int i = 0; i < n; i++) {
            String line = generateLine(n);
            System.out.println(line);
        }
    }
}
