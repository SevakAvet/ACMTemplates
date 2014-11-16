package flows.minCostMaxFlow;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static java.lang.Math.min;

public class MinCostMaxFlow {
    int INF = 1_000_000_000;
    int n;
    List<Edge> g[];
    int[][] f;
    int sumf = 0;
    int sump = 0;

    class Edge {
        int to, c, f, p, rev;

        private Edge(int to, int c, int f, int p, int rev) {
            this.to = to;
            this.c = c;
            this.f = f;
            this.rev = rev;
        }
    }

    void addEdge(int u, int v, int c, int p) {
        Edge fw = new Edge(v, c, 0, p, g[v].size());
        Edge bw = new Edge(u, 0, 0, -p, g[u].size());

        g[u].add(fw);
        g[v].add(bw);
    }

    boolean enlarge(int s, int t) {
        Queue<Integer> q = new LinkedList<>();
        boolean[] used = new boolean[n];
        int[] mf = new int[n];
        int[] d = new int[n];
        int[][] p = new int[n][2];

        Arrays.fill(d, INF);
        d[s] = 0;
        mf[s] = INF;
        q.add(s);
        used[s] = true;

        for (int[] curP : p) {
            Arrays.fill(curP, -1);
        }
        p[s] = new int[]{s, -1};

        while (!q.isEmpty()) {
            int from = q.poll();
            used[from] = false;

            for (int to = 0; to < g[from].size(); to++) {
                Edge e = g[from].get(to);

                if (e.c - e.f > 0 && d[from] + e.p < d[e.to]) {
                    d[e.to] = d[from] + e.p;
                    p[e.to] = new int[]{from, to};
                    mf[e.to] = min(mf[from], e.c - e.f);

                    if (!used[e.to]) {
                        used[e.to] = true;
                        q.add(e.to);
                    }
                }
            }
        }

        if (mf[t] <= 0) {
            return false;
        }

        sumf += mf[t];
        sumf += mf[t] * d[t];

        int v = t;
        while (v != s) {
            Edge fw = g[p[v][0]].get(p[v][1]);
            Edge bw = g[v].get(fw.rev);

            fw.f += mf[t];
            bw.f -= mf[t];
            v = p[v][0];
        }

        return true;
    }
}