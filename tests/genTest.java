package tests;

import java.util.*;

public class genTest {
    public static List<List<Integer>> generatePermutations(int n) {
        List<List<Integer>> permutations = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < n; i++) {
            List<Integer> permutation = new ArrayList<>();
            for (int j = 1; j <= n; j++) {
                permutation.add(j);
            }
            Collections.shuffle(permutation, random);
            permutations.add(permutation);
        }
        return permutations;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of lines to generate: ");
        int n = scanner.nextInt();

        List<List<Integer>> permutations = generatePermutations(n);

        // Print the generated permutations
        for (List<Integer> permutation : permutations) {
            for (int i = 0; i < permutation.size(); i++) {
                System.out.print(permutation.get(i));
                if (i < permutation.size() - 1) {
                    System.out.print(",");
                }
            }
            System.out.println();
        }
        scanner.close();
    }
}
