import java.util.Arrays;

public class KuhnMunkres implements SolveAssignment {
    private static final int INF = 0x3f3f3f3f;
    private final int maxn;

    private final int[] wx; //左部每个点的顶标值（需要根据二分图处理出来）
    private final int[] wy; //右部每个点的顶标值（需要根据二分图处理出来）
    private final int[] matchx; //左部每个点所匹配的点
    private final int[] matchy; //右部每个点所匹配的点
    private final int[] visx; //左部每个点是否加入增广路
    private final int[] visy; //右部每个点是否加入增广路
    private final int[][] mp; //二分图边的权值
    private final int cntx; //左部X的点数
    private final int cnty; //右部Y的点数
    private int d; //边权和顶标最小的差值
    private int ans; // the answer
    private final String[] candidates, positions;

    public KuhnMunkres(int[][] proficiencies, String[] candidates, String[] positions) {
        maxn = proficiencies.length;
        wx = new int[maxn]; //左部每个点的顶标值（需要根据二分图处理出来）
        wy = new int[maxn]; //右部每个点的顶标值（需要根据二分图处理出来）
        matchx = new int[maxn]; //左部每个点所匹配的点
        matchy = new int[maxn]; //右部每个点所匹配的点
        visx = new int[maxn]; //左部每个点是否加入增广路
        visy = new int[maxn]; //右部每个点是否加入增广路
        cntx = maxn; //左部X的点数
        cnty = maxn; //右部Y的点数
        mp = proficiencies;
        this.candidates = candidates;
        this.positions = positions;

        KM();
    }

    private boolean dfs(int u) { //进入DFS的都是X部的点
        visx[u] = 1; //标记进入增广路
        for (int v = 0; v < cnty; v++) {
            if (visy[v] == 0 && mp[u][v] != INF) {
                int t = wx[u] + wy[v] - mp[u][v];
                if (t == 0) { // t为0说明是相等子图
                    visy[v] = 1;
                    if (matchy[v] == -1 || dfs(matchy[v])) {
                        matchx[u] = v;
                        matchy[v] = u; //进行匹配
                        return true;
                    }
                } else if (t > 0) { //此处t一定是大于0，因为顶标之和一定>=边权
                    d = Math.min(d, t); //边权和顶标最小的差值
                }
            }
        }
        return false;
    }

    private void KM() {
        Arrays.fill(matchx, -1);
        Arrays.fill(matchy, -1);
        Arrays.fill(wx, 0); // wx的顶标为该点连接的边的最大权值
        Arrays.fill(wy, 0); // wy的顶标为0

        for (int i = 0; i < cntx; i++) { //预处理出顶标值
            for (int j = 0; j < cnty; j++) {
                if (mp[i][j] == INF) {
                    continue;
                }
                wx[i] = Math.max(wx[i], mp[i][j]);
            }
        }

        for (int i = 0; i < cntx; i++) { //枚举X部的点
            while (true) {
                d = INF;
                Arrays.fill(visx, 0);
                Arrays.fill(visy, 0);
                if (dfs(i)) break; //已经匹配正确

                //还未匹配，将X部的顶标减去d，Y部的顶标加上d
                for (int j = 0; j < cntx; j++) {
                    if (visx[j] == 1) {
                        wx[j] -= d;
                    }
                }
                for (int j = 0; j < cnty; j++) {
                    if (visy[j] == 1) {
                        wy[j] += d;
                    }
                }
            }
        }

        int ans = 0; //二分图最优匹配权值
        for (int i = 0; i < cntx; i++) {
            if (matchx[i] != -1) {
                ans += mp[i][matchx[i]];
            }
        }
        this.ans = ans;
    }

    @Override
    public int getScore() {
        return ans;
    }

    @Override
    public int[] getAssignment() {
        return matchx;
    }

    @Override
    public void printResult() {
        for (int i = 0; i < maxn; i++) {
            if (matchx[i] != -1) {
                System.out.println(candidates[i] + " is assigned to " + positions[matchx[i]]);
            }
        }
    }
}