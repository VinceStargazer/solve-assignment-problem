import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AsgmtTest {
    @Test
    public void testAccuracy() {
        String prefix = "test/test";
        for (int i = 4; i <= 10; i++) {
            String fileName = prefix + i;
            FileParser fileParser = new FileParser(fileName);
            assertNotNull(fileParser);
            String[] candidates = fileParser.getCandidates();
            String[] positions = fileParser.getPositions();
            int[][] proficiencies = fileParser.getProficiencies();
            SolveAssignment solution1 = new Backtrack(proficiencies, candidates, positions);
            SolveAssignment solution2 = new KuhnMunkres(proficiencies, candidates, positions);
            assertEquals(solution1.getScore(), solution2.getScore());
        }
    }

    @Test
    public void testSpeed() {String prefix = "test/test";
        for (int i = 6; i <= 12; i++) {
            String fileName = prefix + i;
            FileParser fileParser = new FileParser(fileName);
            assertNotNull(fileParser);
            String[] candidates = fileParser.getCandidates();
            String[] positions = fileParser.getPositions();
            int[][] proficiencies = fileParser.getProficiencies();

            long startTime1 = System.currentTimeMillis();
            new Backtrack(proficiencies, candidates, positions);
            long elapsedTime1 = System.currentTimeMillis() - startTime1;

            long startTime2 = System.currentTimeMillis();
            new KuhnMunkres(proficiencies, candidates, positions);
            long elapsedTime2 = System.currentTimeMillis() - startTime2;

            System.out.println("Runtime of data size " + i + " in milliseconds");
            System.out.println("Backtrack: " + elapsedTime1);
            System.out.println("Kuhn Munkres: " + elapsedTime2);
        }
    }
}
