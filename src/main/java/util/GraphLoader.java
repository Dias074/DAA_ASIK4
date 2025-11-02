package util;

import java.util.*;
import java.io.*;
import graph.scc.TarjanSCC;
import graph.scc.Condensation;
import graph.topo.KahnTopo;
import graph.dagsp.DAGSP;


public class GraphLoader {

    public static Map<Integer, List<Integer>> buildAdj(List<Integer> nodes, List<int[]> edges) {
        Map<Integer, List<Integer>> adj = new HashMap<>();
        for (int n : nodes) adj.put(n, new ArrayList<>());
        for (int[] e : edges) {
            adj.get(e[0]).add(e[1]);
        }
        return adj;
    }

    public static Map<String, Object> loadFromFile(String path) throws IOException {
        String txt = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(path)));

        Map<String, Object> res = new HashMap<>();
        List<Integer> nodes = new ArrayList<>();
        List<int[]> edges = new ArrayList<>();
        Map<Integer, Integer> durations = new HashMap<>();

        int ni = txt.indexOf("\"nodes\"");
        if (ni != -1) {
            int b = txt.indexOf('[', ni);
            int e = txt.indexOf(']', b);
            String body = txt.substring(b + 1, e).trim();
            if (!body.isEmpty()) {
                for (String s : body.split(",")) {
                    if (!s.trim().isEmpty())
                        nodes.add(Integer.parseInt(s.trim()));
                }
            }
        }

        int ei = txt.indexOf("\"edges\"");
        if (ei != -1) {
            int b = txt.indexOf('[', ei);
            int e = txt.indexOf(']', b);
            String body = txt.substring(b + 1, e).trim();

            if (!body.isEmpty()) {
                String[] parts = body.split("\\},");
                for (String p : parts) {
                    p = p.replace("{", "").replace("}", "").replace("\"", "").trim();
                    String[] kvs = p.split(",");
                    int u = -1, v = -1;
                    for (String kv : kvs) {
                        kv = kv.trim();
                        if (kv.startsWith("u:"))
                            u = Integer.parseInt(kv.split(":")[1]);
                        if (kv.startsWith("v:"))
                            v = Integer.parseInt(kv.split(":")[1]);
                    }
                    if (u != -1 && v != -1)
                        edges.add(new int[]{u, v});
                }
            }
        }


        int di = txt.indexOf("\"durations\"");
        if (di != -1) {
            int b = txt.indexOf('{', di);
            int e = txt.indexOf('}', b);
            String body = txt.substring(b + 1, e).trim();

            if (!body.isEmpty()) {
                for (String kv : body.split(",")) {
                    String[] parts = kv.split(":");
                    if (parts.length == 2) {
                        String keyStr = parts[0].replace("\"", "").trim();
                        int k = Integer.parseInt(keyStr);
                        int v = Integer.parseInt(parts[1].trim());
                        durations.put(k, v);
                    }
                }
            }
        }

        res.put("nodes", nodes);
        res.put("edges", edges);
        res.put("durations", durations);
        return res;
    }
}
