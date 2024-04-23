public class Demo {
    public static void main(String[] args) {
        FileParser fileParser = new FileParser("test/trueTest");
        SolveAssignment solution = new KuhnMunkres(fileParser.getProficiencies(), fileParser.getCandidates(), fileParser.getPositions());
        solution.printResult();
    }
}
