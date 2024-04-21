import java.util.Arrays;

public class Backtrack {
    private static int max_match;
    private static int[] assignment;

    public static void solve(int[][] proficiencies, String[] candidates, String[] positions) {
        int m = proficiencies.length, n = proficiencies[0].length;
        max_match = 0;
        assignment = new int[m];
        Arrays.fill(assignment, -1);
        int[] occupied = new int[n];
        Arrays.fill(occupied, -1);

        backtrack(proficiencies, occupied, 0, 0);

        for (int i = 0; i < m; i++) {
            if (assignment[i] != -1) {
                System.out.println(candidates[i] + " is assigned to " + positions[assignment[i]]);
            }
        }
    }

    private static void backtrack(int[][] proficiencies, int[] occupied, int i, int score) {
        int m = proficiencies.length, n = proficiencies[0].length;
        if (i == m) {
            if (score > max_match) {
                max_match = score;
                for (int j = 0; j < n; j++) {
                    assignment[occupied[j]] = j;
                }
            }
            return;
        }
        for (int j = 0; j < n; j++) {
            if (occupied[j] >= 0) continue;
            occupied[j] = i;
            backtrack(proficiencies, occupied, i + 1, score + proficiencies[i][j]);
            occupied[j] = -1;
        }

    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Backtrack <file_name>");
            return;
        }

        String fileName = args[0];
        FileParser fileParser = new FileParser(fileName);
        System.out.println("The assignment solution to " + fileName + " is:");
        solve(fileParser.getProficiencies(), fileParser.getCandidates(), fileParser.getPositions());
    }
}
