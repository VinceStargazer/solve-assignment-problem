import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileParser {
    private String[] candidates;
    private String[] positions;
    private int[][] proficiencies;

    public FileParser(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            line = reader.readLine();
            this.candidates = line.split(",");
            line = reader.readLine();
            this.positions = line.split(",");
            if (candidates.length != positions.length) {
                System.out.println("The given input is not a balanced assignment");
                return;
            }
            this.proficiencies = new int[candidates.length][positions.length];
            int r = 0;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int c = 0;
                for (String part : parts) {
                    proficiencies[r][c++] = Integer.parseInt(part);
                }
                r++;
            }

            reader.close();
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public String[] getCandidates() {
        return candidates;
    }

    public String[] getPositions() {
        return positions;
    }

    public int[][] getProficiencies() {
        return proficiencies;
    }
}
