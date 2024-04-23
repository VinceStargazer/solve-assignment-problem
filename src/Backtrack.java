import java.util.Arrays;

public class Backtrack implements SolveAssignment {
    private int max_match;
    private final int[] assignment;
    private final String[] candidates, positions;

    public Backtrack(int[][] proficiencies, String[] candidates, String[] positions) {
        int m = proficiencies.length, n = proficiencies[0].length;
        max_match = 0;
        assignment = new int[m];
        this.candidates = candidates;
        this.positions = positions;
        Arrays.fill(assignment, -1);
        int[] occupied = new int[n];
        Arrays.fill(occupied, -1);

        backtrack(proficiencies, occupied, 0, 0);
    }

    private void backtrack(int[][] proficiencies, int[] occupied, int i, int score) {
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

    @Override
    public int getScore() {
        return max_match;
    }

    @Override
    public int[] getAssignment() {
        return assignment;
    }

    @Override
    public void printResult() {
        System.out.println("The maximum score is " + max_match + " by: ");
        for (int i = 0; i < candidates.length; i++) {
            if (assignment[i] != -1) {
                System.out.println(candidates[i] + " is assigned to " + positions[assignment[i]]);
            }
        }
    }
}
