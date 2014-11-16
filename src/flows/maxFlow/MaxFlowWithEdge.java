package flows.maxFlow;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static java.lang.Math.min;

public class MaxFlowWithEdge {
    int INF = 1_000_000_000;
    int n;
    List<Edge> g[];
    int[][] f;
    int sum = 0;

    class Edge {
        int to, c, f, rev;

        private Edge(int to, int c, int f, int rev) {
            this.to = to;
            this.c = c;
            this.f = f;
            this.rev = rev;
        }
    }

    void addEdge(int u, int v, int c) {
        Edge fw = new Edge(v, c, 0, g[v].size());
        Edge bw = new Edge(u, 0, 0, g[u].size());

        g[u].add(fw);
        g[v].add(bw);
    }

    boolean enlarge(int s, int t) {
        Queue<Integer> q = new LinkedList<>();
        int[] mf = new int[n];
        int[][] p = new int[n][2];

        q.add(s);
        mf[s] = INF;

        for (int[] curP : p) {
            Arrays.fill(curP, -1);
        }
        p[s] = new int[]{s, -1};

        while (!q.isEmpty() && mf[t] == 0) {
            int from = q.poll();

            for (int to = 0; to < g[from].size(); to++) {
                Edge e = g[from].get(to);

                if (e.c - e.f > 0 && mf[e.to] == 0) {
                    mf[e.to] = min(mf[from], e.c - e.f);
                    p[e.to] = new int[]{from, to};
                    q.add(e.to);
                }
            }
        }

        if (mf[t] == 0) {
            return false;
        }

        sum += mf[t];

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