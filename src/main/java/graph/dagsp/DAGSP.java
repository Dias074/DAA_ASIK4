
package graph.dagsp;

import java.util.*;

public class DAGSP {
    private Map<Integer, List<Integer>> adj;
    private Map<Integer, Integer> duration;

    public DAGSP(Map<Integer, List<Integer>> adj, Map<Integer, Integer> duration) {
        this.adj = adj;
        this.duration = duration;
    }

    public Map<Integer, Integer> shortestFrom(int src, List<Integer> topo) {
        final int INF = Integer.MAX_VALUE / 4;
        Map<Integer, Integer> dist = new HashMap<>();
        for (int v : adj.keySet()) dist.put(v, INF);
        dist.put(src, duration.getOrDefault(src, 0));
        for (int u : topo) {
            if (dist.get(u) == INF) continue;
            for (int v : adj.getOrDefault(u, Collections.emptyList())) {
                int nd = dist.get(u) + duration.getOrDefault(v, 0);
                if (nd < dist.get(v)) dist.put(v, nd);
            }
        }
        return dist;
    }

    public Pair longestPath() {
        List<Integer> topo = new ArrayList<>(adj.keySet());

        try {
            topo = graph.topo.KahnTopo.kahnTopo(adj);
        } catch (Exception ex) {

            topo = new ArrayList<>(adj.keySet());
        }
        Map<Integer, Integer> dp = new HashMap<>();
        Map<Integer, Integer> parent = new HashMap<>();
        for (int v : adj.keySet()) { dp.put(v, Integer.MIN_VALUE/4); parent.put(v, -1); }
        Set<Integer> hasIncoming = new HashSet<>();
        for (int u : adj.keySet()) for (int v : adj.get(u)) hasIncoming.add(v);
        for (int v : adj.keySet()) if (!hasIncoming.contains(v)) dp.put(v, duration.getOrDefault(v,0));
        for (int u : topo) {
            if (dp.get(u) <= Integer.MIN_VALUE/8) continue;
            for (int v : adj.getOrDefault(u, Collections.emptyList())) {
                int cand = dp.get(u) + duration.getOrDefault(v,0);
                if (cand > dp.get(v)) { dp.put(v, cand); parent.put(v, u); }
            }
        }
        int bestV = -1, bestVal = Integer.MIN_VALUE;
        for (var e : dp.entrySet()) if (e.getValue() > bestVal) { bestVal = e.getValue(); bestV = e.getKey(); }
        List<Integer> path = new ArrayList<>();
        int cur = bestV;
        while (cur!=-1 && cur!=-0) {
            path.add(cur);
            cur = parent.getOrDefault(cur, -1);
            if (cur==-1) break;
        }
        Collections.reverse(path);
        return new Pair(bestVal, path);
    }

    public static class Pair {
        public int length;
        public List<Integer> path;
        public Pair(int length, List<Integer> path) { this.length = length; this.path = path; }
    }
}
