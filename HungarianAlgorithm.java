import java.util.Arrays;

public class HungarianAlgorithm {

    private static final int MAXN = 4;
    private static final int MAXM = 4;
    private int[][] Map = new int[MAXM][MAXN]; // 成本矩阵
    private int[] p = new int[MAXN];           // 记录匹配情况
    private boolean[] vis = new boolean[MAXN]; // 记录访问状态
    private int N = 4;                         // 四个职位

    private boolean match(int i) {
        for (int j = 0; j < N; ++j) { // 注意Java数组下标从0开始
            if (Map[i][j] > 0 && !vis[j]) {
                vis[j] = true;
                if (p[j] == -1 || match(p[j])) {
                    p[j] = i;
                    return true;
                }
            }
        }
        return false;
    }

    private int hungarian() {
        int cnt = 0;
        Arrays.fill(p, -1); // 使用-1初始化表示未匹配
        for (int i = 0; i < N; ++i) {
            Arrays.fill(vis, false);
            if (match(i)) {
                cnt++;
            }
        }
        return cnt;
    }

    public static void main(String[] args) {
        int[][] proficiencies = {
            {1, 2, 3, 4}, // A的熟练度
            {4, 1, 2, 3}, // B的熟练度
            {3, 4, 1, 2}, // C的熟练度
            {2, 3, 4, 1}  // D的熟练度
        };

        HungarianAlgorithm ha = new HungarianAlgorithm();
        
        // 找到熟练度的最大值
        int maxProficiency = 4;
        
        // 将熟练度转换为成本
        for (int i = 0; i < proficiencies.length; i++) {
            for (int j = 0; j < proficiencies[i].length; j++) {
                ha.Map[i][j] = maxProficiency - proficiencies[i][j];
            }
        }

        ha.hungarian();

        // 输出匹配结果
        String[] candidates = {"A", "B", "C", "D"};
        String[] positions = {"Java", "Python", "C++", "SQL"};
        for (int j = 0; j < ha.N; ++j) {
            if (ha.p[j] != -1) {
                System.out.println(candidates[ha.p[j]] + " is assigned to " + positions[j]);
            }
        }
    }
}
