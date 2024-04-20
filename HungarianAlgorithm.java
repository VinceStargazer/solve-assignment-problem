import java.util.Arrays;

public class HungarianAlgorithm {
    private static final int MAXN = 40; // 假设最多有 40 个职位或候选人
    private int[][] costMatrix = new int[MAXN][MAXN]; // 成本矩阵
    private int[] matchL = new int[MAXN]; // 左部匹配结果
    private int[] matchR = new int[MAXN]; // 右部匹配结果
    private int[] labelL = new int[MAXN]; // 左部顶标
    private int[] labelR = new int[MAXN]; // 右部顶标
    private boolean[] seen = new boolean[MAXN];
    private int[] slack = new int[MAXN];
    private int N; // 实际使用的节点数量

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

    public void run() {
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
        int[][] proficiencies = {
            {1, 2, 3, 4}, 
            {2, 3, 4, 1}, 
            {3, 4, 1, 2}, 
            {4, 1, 2, 3}
        };

        HungarianAlgorithm km = new HungarianAlgorithm(4);
        km.costMatrix = proficiencies;

        km.run();

        String[] candidates = {"A", "B", "C", "D"};
        String[] positions = {"Java", "Python", "C++", "SQL"};
        for (int i = 0; i < km.N; i++) {
            if (km.matchL[i] != -1) {
                System.out.println(candidates[i] + " is assigned to " + positions[km.matchL[i]]);
            }
        }
    }
}
