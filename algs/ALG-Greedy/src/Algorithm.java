import java.util.ArrayList;
import java.util.List;

/**
 * Greedy graph coloring algorithm.
 *
 * Vertices are processed in their natural order (0, 1, 2, ...). Each
 * vertex is assigned the smallest color not used by any already
 * colored neighbour.
 *
 * Time complexity: O(|V| + |E|)
 *
 * @author btrivunovic
 */
public class Algorithm extends ProjectAbstractAlgorithm {

  @Override
  protected Output execute(Input input) {
    int n = input.n;
    List<List<Integer>> adj = buildAdjacency(n, input.edges);

    int[] color = new int[n];
    for (int v = 0; v < n; v++) {
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
