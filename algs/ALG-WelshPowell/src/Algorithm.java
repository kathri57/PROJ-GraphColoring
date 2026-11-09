import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Welsh-Powell graph coloring algorithm.
 *
 * Improvement over Greedy: vertices are first sorted in decreasing
 * order of degree. Vertices with high degree (most constraints) are
 * colored first, which typically reduces the total number of colors.
 *
 * Time complexity: O(|V| log|V| + |E|)
 *
 * Reference: Welsh, D.J.A., Powell, M.B. (1967). An upper bound for the
 * chromatic number of a graph and its application to timetabling
 * problems. The Computer Journal, 10(1), 85-86.
 *
 * @author btrivunovic
 */
public class Algorithm extends ProjectAbstractAlgorithm {

  @Override
  protected Output execute(Input input) {
    int n = input.n;
    List<List<Integer>> adj = buildAdjacency(n, input.edges);

    Integer[] order = new Integer[n];
    for (int i = 0; i < n; i++) order[i] = i;
    Arrays.sort(order, (a, b) -> {
      //@COUNT{CMP, 1}
      int d = Integer.compare(adj.get(b).size(), adj.get(a).size());
      return d != 0 ? d : Integer.compare(a, b);
    });

    int[] color = new int[n];
    for (int v : order) {
      //@COUNT{CALL, 1}
      color[v] = smallestFree(adj, v, color);
    }

    return new Output(color);
  }

  private List<List<Integer>> buildAdjacency(int n, int[][] edges) {
    List<List<Integer>> adj = new ArrayList<>();
    for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
    for (int[] e : edges) {
      adj.get(e[0]).add(e[1]);
      adj.get(e[1]).add(e[0]);
    }
    return adj;
  }

  private int smallestFree(List<List<Integer>> adj, int v, int[] color) {
    int d=adj.get(v).size();
    boolean[] used=new boolean[d+2];
    for (int u : adj.get(v)) {
      //@COUNT{CMP, 1}
      int c=color[u];
      if (c>0 && c<=d+1) used[c] = true;
    }
    for (int c = 1; ; c++) {
      //@COUNT{CMP, 1}
      if (!used[c]) return c;
    }
  }
}
