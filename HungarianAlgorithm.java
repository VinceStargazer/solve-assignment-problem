import java.util.Arrays;

public class HungarianAlgorithm {
    private static final int MAXN = 40; // 假设最多有 40 个职位或候选人
    private int[][] costMatrix = new int[MAXN][MAXN]; // 成本矩阵
    private final int[] matchL = new int[MAXN]; // 左部匹配结果
    private final int[] matchR = new int[MAXN]; // 右部匹配结果
    private final int[] labelL = new int[MAXN]; // 左部顶标
    private final int[] labelR = new int[MAXN]; // 右部顶标
    private final boolean[] seen = new boolean[MAXN];
    private final int[] slack = new int[MAXN];
    private final int N; // 实际使用的节点数量

    public static void solve(int[][] proficiencies, String[] candidates, String[] positions) {
        HungarianAlgorithm km = new HungarianAlgorithm(4);
        km.costMatrix = proficiencies;
        km.run();

        for (int i = 0; i < km.N; i++) {
            if (km.matchL[i] != -1) {
                System.out.println(candidates[i] + " is assigned to " + positions[km.matchL[i]]);
            }
        }
    }

    public HungarianAlgorithm(int n) {
        this.N = n;
    }

    private void initLabels() {
        Arrays.fill(labelL, Integer.MIN_VALUE);
        Arrays.fill(labelR, 0);
        Arrays.fill(matchL, -1);
        Arrays.fill(matchR, -1);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                labelL[i] = Math.max(labelL[i], costMatrix[i][j]);
            }
        }
    }

    private boolean tryKuhn(int v) {
        seen[v] = true;
        for (int u = 0; u < N; u++) {
            if (labelL[v] + labelR[u] - costMatrix[v][u] == 0) {
                if (!seen[u]) {
                    seen[u] = true;
                    if (matchR[u] == -1 || tryKuhn(matchR[u])) {
                        matchR[u] = v;
                        matchL[v] = u;
                        return true;
                    }
                }
            } else {
                slack[u] = Math.min(slack[u], labelL[v] + labelR[u] - costMatrix[v][u]);
            }
        }
        return false;
    }

    private void run() {
        initLabels();
        for (int i = 0; i < N; i++) {
            Arrays.fill(slack, Integer.MAX_VALUE);
            while (true) {
                Arrays.fill(seen, false);
                if (tryKuhn(i)) break;
                int delta = Integer.MAX_VALUE;
                for (int j = 0; j < N; j++) {
                    if (!seen[j]) {
                        delta = Math.min(delta, slack[j]);
                    }
                }
                for (int j = 0; j < N; j++) {
                    if (seen[j]) labelL[j] -= delta;
                    if (seen[j]) labelR[j] += delta;
                    else slack[j] -= delta;
                }
            }
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java HungarianAlgorithm <file_name>");
            return;
        }

        String fileName = args[0];
        FileParser fileParser = new FileParser(fileName);
        System.out.println("The assignment solution to " + fileName + " is:");
        solve(fileParser.getProficiencies(), fileParser.getCandidates(), fileParser.getPositions());
    }
}
