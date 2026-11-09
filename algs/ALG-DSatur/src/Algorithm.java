import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * DSatur (Degree of Saturation) graph coloring algorithm.
 *
 * The saturation degree of a vertex is the number of distinct colors
 * used among its already-colored neighbours. At each step, the vertex
 * with the highest saturation degree is colored next (ties broken by
 * the vertex's original degree, then by index).
 *
 * Naive time complexity: O(|V|^2); can be improved to
 * O((|V|+|E|) log|V|) using a priority queue.
 *
 * Reference: Brelaz, D. (1979). New methods to color the vertices of a
 * graph. Communications of the ACM, 22(4), 251-256.
 *
 * @author btrivunovic
 */
public class Algorithm extends ProjectAbstractAlgorithm {

  @Override
  protected Output execute(Input input) {
    int n = input.n;
    List<List<Integer>> adj = buildAdjacency(n, input.edges);

    int[] color = new int[n];
    int[] satDeg = new int[n];
    boolean[] done = new boolean[n];

    @SuppressWarnings("unchecked")
    Set<Integer>[] neighborColors = new Set[n];
    for (int i = 0; i < n; i++) neighborColors[i] = new HashSet<>();

    for (int step = 0; step < n; step++) {
      int sel = -1;
      for (int v = 0; v < n; v++) {
        if (done[v]) continue;
        //@COUNT{CMP, 1}
        if (sel == -1
            || satDeg[v] > satDeg[sel]
            || (satDeg[v] == satDeg[sel] && adj.get(v).size() > adj.get(sel).size())
            || (satDeg[v] == satDeg[sel] && adj.get(v).size() == adj.get(sel).size() && v < sel)) {
          sel = v;
        }
      }

      //@COUNT{CALL, 1}
      color[sel] = smallestFree(adj, sel, color);
      done[sel] = true;

      for (int u : adj.get(sel)) {
        if (!done[u]) {
          neighborColors[u].add(color[sel]);
          satDeg[u] = neighborColors[u].size();
        }
      }
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
