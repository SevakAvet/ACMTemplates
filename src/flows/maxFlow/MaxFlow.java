package flows.maxFlow;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import static java.lang.Math.min;

public class MaxFlow {
    int INF = 1_000_000_000;
    int n;
    int[][] c;
    int[][] f;
    int sum = 0;

    boolean enlarge(int s, int t) {
        Queue<Integer> q = new LinkedList<>();
        int[] mf = new int[n];
        int[] p = new int[n];

        q.add(s);
        mf[s] = INF;
        Arrays.fill(p, -1);
        p[s] = s;

        while (!q.isEmpty() && mf[t] == 0) {
            int from = q.poll();

            for (int to = 0; to < n; to++) {
                if (c[from][to] - f[from][to] > 0 && mf[to] == 0) {
                    mf[to] = min(mf[from], c[from][to] - f[from][to]);
                    p[to] = from;
                    q.add(to);
                }
            }
        }

        if (mf[t] == 0) {
            return false;
        }

        sum += mf[t];

        int v = t;
        while (p[v] != v) {
            f[p[v]][v] += mf[t];
            f[v][p[v]] -= mf[t];
            v = p[v];
        }

        return true;
    }
}