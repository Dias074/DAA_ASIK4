
package graph.scc;

import java.util.*;
import java.io.*;


public class TarjanSCC {
    private int n;
    private Map<Integer, List<Integer>> adj;
    private Map<Integer, Integer> indexMap, lowlink;
    private Deque<Integer> stack;
    private Set<Integer> onStack;
    private int index;
    private List<List<Integer>> components;

    public TarjanSCC(Map<Integer, List<Integer>> adj) {
        this.adj = adj;
        this.indexMap = new HashMap<>();
        this.lowlink = new HashMap<>();
        this.stack = new ArrayDeque<>();
        this.onStack = new HashSet<>();
        this.index = 0;
        this.components = new ArrayList<>();
    }

    public List<List<Integer>> run() {
        for (Integer v : adj.keySet()) {
            if (!indexMap.containsKey(v)) strongconnect(v);
        }
        return components;
    }

    private void strongconnect(int v) {
        indexMap.put(v, index);
        lowlink.put(v, index);
        index++;
        stack.push(v);
        onStack.add(v);

        for (int w : adj.getOrDefault(v, Collections.emptyList())) {
            if (!indexMap.containsKey(w)) {
                strongconnect(w);
                lowlink.put(v, Math.min(lowlink.get(v), lowlink.get(w)));
            } else if (onStack.contains(w)) {
                lowlink.put(v, Math.min(lowlink.get(v), indexMap.get(w)));
            }
        }

        if (lowlink.get(v).equals(indexMap.get(v))) {
            List<Integer> comp = new ArrayList<>();
            int w;
            do {
                w = stack.pop();
                onStack.remove(w);
                comp.add(w);
            } while (w != v);
            components.add(comp);
        }
    }
}
