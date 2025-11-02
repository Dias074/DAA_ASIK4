
package graph.topo;

import java.util.*;

public class KahnTopo {
    public static List<Integer> kahnTopo(Map<Integer, List<Integer>> adj) {
        Map<Integer, Integer> indeg = new HashMap<>();
        for (int u : adj.keySet()) indeg.put(u, 0);
        for (int u : adj.keySet()) {
            for (int v : adj.get(u)) indeg.put(v, indeg.getOrDefault(v,0)+1);
        }
        Deque<Integer> q = new ArrayDeque<>();
        for (var e : indeg.entrySet()) if (e.getValue()==0) q.add(e.getKey());
        List<Integer> order = new ArrayList<>();
        while (!q.isEmpty()) {
            int u = q.removeFirst();
            order.add(u);
            for (int v : adj.getOrDefault(u, Collections.emptyList())) {
                indeg.put(v, indeg.get(v)-1);
                if (indeg.get(v)==0) q.add(v);
            }
        }
        if (order.size() != adj.size()) throw new RuntimeException("Graph not DAG (cycle found) in topo.");
        return order;
    }
}
