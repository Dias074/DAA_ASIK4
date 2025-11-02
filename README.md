
# Assignment 4 — Graph Algorithms Project
### Smart City / Smart Campus Scheduling
**Student:** *Dias Nygman*  
**Course:** Design and Analysis of Algorithms (ASIK 4)

---

##  Project Overview

This project implements advanced graph algorithms for scheduling and dependency analysis in a **Smart City / Smart Campus** system.

The program supports **two weight models**:
1. **Node Duration Model** — each node has a duration (processing time).
2. **Edge Weight Model** — each edge has a cost/weight (`w`).

The program can:
- Find **Strongly Connected Components (SCC)** using **Tarjan’s algorithm**
- Build the **Condensation Graph (DAG)**
- Perform **Topological Sorting** using **Kahn’s algorithm**
- Compute **Shortest and Longest Paths** in the DAG
- Run **Dijkstra’s algorithm** for edge-weighted graphs
- Analyze runtime and structure of generated datasets

---

## 🗂 Project Structure

| Algorithm | Description | File |
|------------|--------------|------|
| **Tarjan's SCC** | Detects strongly connected components (cycles). | `graph/scc/TarjanSCC.java` |
| **Condensation DAG** | Builds acyclic graph of SCCs. | `graph/scc/Condensation.java` |
| **Kahn’s Topological Sort** | Produces valid execution order for DAGs. | `graph/topo/KahnTopo.java` |
| **DAG Shortest & Longest Path (DP)** | Finds minimal and maximal duration paths (node model). | `graph/dagsp/DAGSP.java` |
| **Dijkstra Algorithm** | Finds shortest paths in edge-weight graphs. | `graph/dagsp/Dijkstra.java` |
| **GraphLoader** | Reads JSON graph data (both formats). | `util/GraphLoader.java` |
| **Main.java** | Entry point — automatically detects which model to use. | `Main.java` |



---

##  How It Works

###  1. Node-Duration Model (default)
- Reads JSON like:
```json
{
  "nodes": [1,2,3],
  "edges": [{"u":1,"v":2},{"u":2,"v":3}],
  "durations": {"1":3,"2":4,"3":5}
}


{
  "directed": true,
  "n": 8,
  "edges": [
    {"u": 0, "v": 1, "w": 3},
    {"u": 1, "v": 2, "w": 2},
    {"u": 2, "v": 3, "w": 4}
  ],
  "source": 4,
  "weight_model": "edge"
}


# Compile all .java files
mkdir -p out
javac -d out $(find src -name "*.java")

# Run (node-duration model)
java -cp out Main data/small1.json

# Run (edge-weight model)
java -cp out Main data/teacher.json



./build.sh
java -cp out Main data/small1.json




Weight model: node
Nodes: 6
Edges: 9

--- Running Node-duration model (Tarjan + DAG) ---
SCCs (count=3):
[1, 2]
[3]
[4, 5]
Condensation adjacency:
0 -> [1, 2]
Topo order: [0, 1, 2]
Critical path length = 16
Critical path = [1, 2, 5]



Weight model: edge
Nodes: 8
Edges: 7

--- Running Dijkstra (edge-weight model) ---
From 4 to 4 = 0
From 4 to 5 = 2
From 4 to 6 = 7
From 4 to 7 = 8


| Type   | File Count | Node Range | Description               |
| ------ | ---------- | ---------- | ------------------------- |
| Small  | 3          | 6–8        | Simple connected DAGs     |
| Medium | 3          | 12–18      | Mixed graphs with cycles  |
| Large  | 3          | 24–36      | Dense or multi-SCC graphs |



| Algorithm                  | Time Complexity | Space    |
| -------------------------- | --------------- | -------- |
| Tarjan’s SCC               | O(V + E)        | O(V)     |
| Condensation DAG           | O(V + E)        | O(V + E) |
| Kahn Topo Sort             | O(V + E)        | O(V)     |
| DAG Shortest/Longest Paths | O(V + E)        | O(V)     |
| Dijkstra                   | O(E log V)      | O(V + E) |



Performance Notes

All algorithms run efficiently up to ~40 nodes, 100+ edges.

Tarjan and Kahn are purely linear.

Dijkstra adds a logarithmic factor (priority queue).

Works with both small academic datasets and large test graphs.



Report Summary

Implemented Tarjan SCC, Condensation, Kahn Topo, DAG Shortest/Longest Path, and Dijkstra.

Supports two data models (node and edge).

9 datasets generated for benchmarking.

Analyzed runtime and scalability up to 36 nodes.

Code structured into clear OOP packages with documentation.
