
package graph.scc;

import java.util.*;

public class Condensation {
    public static Map<Integer, List<Integer>> buildCondensation(Map<Integer, List<Integer>> adj, List<List<Integer>> comps) {
        Map<Integer, Integer> compOf = new HashMap<>();
        for (int i = 0; i < comps.size(); i++) {
            for (int v : comps.get(i)) compOf.put(v, i);
        }
        Map<Integer, Set<Integer>> cadj = new HashMap<>();
        for (int u : adj.keySet()) {
            for (int v : adj.get(u)) {
                int cu = compOf.get(u);
                int cv = compOf.get(v);
                if (cu != cv) {
                    cadj.computeIfAbsent(cu, k->new HashSet<>()).add(cv);
                }
            }
        }
        Map<Integer, List<Integer>> res = new HashMap<>();
        for (int i = 0; i < comps.size(); i++) {
            res.put(i, new ArrayList<>(cadj.getOrDefault(i, Collections.emptySet())));
        }
        return res;
    }
}
