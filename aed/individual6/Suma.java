package aed.individual6;

import es.upm.aedlib.graph.Edge;
import es.upm.aedlib.graph.Vertex;
import es.upm.aedlib.graph.DirectedGraph;
import es.upm.aedlib.map.Map;
import es.upm.aedlib.set.HashTableMapSet;
import es.upm.aedlib.set.Set;
import es.upm.aedlib.map.HashTableMap;

public class Suma {
  public static <E> Map<Vertex<Integer>, Integer> sumVertices(DirectedGraph<Integer, E> g) {
    Map<Vertex<Integer>, Integer> map = new HashTableMap<>();
    for (Vertex<Integer> v : g.vertices()) {
      Set<Vertex<Integer>> set = new HashTableMapSet<>();
      set.add(v);
      vertexExplorer(g, set, v);
      int sum = 0;
      for (Vertex<Integer> v2 : set)
        sum += v2.element();
      map.put(v, sum);
    }
    return map;
  }

  public static <E> void vertexExplorer(DirectedGraph<Integer, E> g, Set<Vertex<Integer>> set,
      Vertex<Integer> v) {
    for (Edge<E> e : g.outgoingEdges(v)) {
      Vertex<Integer> v2 = g.endVertex(e);
      if (set.contains(v2))
        continue;
      set.add(v2);
      vertexExplorer(g, set, v2);
    }
  }
}
