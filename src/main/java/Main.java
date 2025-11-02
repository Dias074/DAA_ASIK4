
import java.util.*;
import java.nio.file.*;
import graph.scc.TarjanSCC;
import graph.scc.Condensation;
import graph.topo.KahnTopo;
import graph.dagsp.DAGSP;
import util.GraphLoader;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length==0) {
            System.out.println("Usage: java Main <datafile.json> [sourceNode]");
            return;
        }
        String path = args[0];
        var parsed = GraphLoader.loadFromFile(path);
        var nodes = (List<Integer>) parsed.get("nodes");
        var edges = (List<int[]>) parsed.get("edges");
        var durations = (Map<Integer,Integer>) parsed.get("durations");
        Map<Integer,List<Integer>> adj = GraphLoader.buildAdj(nodes, edges);
        System.out.println("Nodes: " + nodes.size());
        TarjanSCC tarjan = new TarjanSCC(adj);
        var comps = tarjan.run();
        System.out.println("SCCs (count="+comps.size()+"):");
        for (var c : comps) System.out.println(c);
        var cadj = Condensation.buildCondensation(adj, comps);
        System.out.println("Condensation adjacency:");
        for (var e : cadj.entrySet()) System.out.println(e.getKey()+" -> " + e.getValue());
        var topo = KahnTopo.kahnTopo(cadj);
        System.out.println("Topo order of components: " + topo);
        List<Integer> derived = new ArrayList<>();
        for (int compId : topo) {
            derived.addAll(comps.get(compId));
        }
        System.out.println("Derived order of original tasks (after SCC compression): " + derived);
        Map<Integer,Integer> nodeDur = new HashMap<>();
        for (int v : nodes) nodeDur.put(v, durations.getOrDefault(v,1));
        DAGSP dagsp = new DAGSP(cadj, new HashMap<>());
        var pair = dagsp.longestPath();
        System.out.println("Critical (longest) path in condensation DAG length="+pair.length+" path="+pair.path);
        if (args.length>1) {
            int src = Integer.parseInt(args[1]);
            var dists = dagsp.shortestFrom(src, topo);
            System.out.println("Shortest distances from " + src + ": " + dists);
        }
    }
}
