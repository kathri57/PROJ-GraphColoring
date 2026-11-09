import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * MSISCA - Malatya Sequential Independent Set Coloring Algorithm.
 *
 * Malatya centrality of a vertex v among the active vertices:
 *
 *   psi(v) = sum over neighbours u of v of  d(v) / d(u)
 *
 * Intuition: a vertex with low psi has "weak" connections relative to
 * its (higher-degree) neighbours, making it a good candidate for
 * an independent set.
 *
 * The algorithm repeatedly builds a sequential independent set (SIS) by
 * picking the active vertex with the minimum Malatya centrality,
 * adding it to the SIS, and removing it together with its neighbours
 * from the active set. Each SIS is colored with one new color.
 *
 * Time complexity: O(|V|^2 * Delta * k), where Delta is the maximum
 * degree and k is the number of colors used.
 *
 * Reference: Yakut, S. (2025). A robust and efficient algorithm for
 * graph coloring problem based on Malatya centrality and sequent
 * independent sets. Egyptian Informatics Journal, 30, 100676.
 *
 * @author btrivunovic
 */
public class Algorithm extends ProjectAbstractAlgorithm {

  @Override
  protected Output execute(Input input) {
    int n = input.n;
    List<List<Integer>> adj = buildAdjacency(n, input.edges);

    int[] color = new int[n];
    boolean[] active = new boolean[n];
    Arrays.fill(active, true);
    int remaining = n;
    int col = 1;

    while (remaining > 0) {
      //@COUNT{CALL, 1}
      List<Integer> sis = buildSIS(adj, n, active);
      for (int v : sis) {
        color[v] = col;
        active[v] = false;
        remaining--;
      }
      col++;
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

  private double malatyaCentrality(List<List<Integer>> adj, boolean[] active, int[] deg, int v) {
    if (deg[v] == 0) return 0.0;
    double psi = 0.0;
    for (int u : adj.get(v)) {
      //@COUNT{CMP, 1}
      if (active[u] && deg[u] > 0) psi += (double) deg[v] / deg[u];
    }
    return psi;
  }

  /** Builds one sequential independent set of currently active vertices. */
  private List<Integer> buildSIS(List<List<Integer>> adj, int n, boolean[] baseActive) {
    boolean[] temp = Arrays.copyOf(baseActive, baseActive.length);

    int[] deg = new int[n];
    for (int v = 0; v < n; v++) {
      if (temp[v]) {
        for (int u : adj.get(v)) {
          if (temp[u]) deg[v]++;
        }
      }
    }

    List<Integer> sis = new ArrayList<>();

    while (true) {
      int sel = -1;
      double best = Double.POSITIVE_INFINITY;
      for (int v = 0; v < n; v++) {
        if (!temp[v]) continue;
        double mc = malatyaCentrality(adj, temp, deg, v);
        //@COUNT{CMP, 1}
        if (sel == -1 || mc < best - 1e-12 || (Math.abs(mc - best) < 1e-12 && v < sel)) {
          sel = v;
          best = mc;
        }
      }
      if (sel == -1) break;

      sis.add(sel);

      List<Integer> toRemove = new ArrayList<>();
      toRemove.add(sel);
      for (int u : adj.get(sel)) {
        if (temp[u]) toRemove.add(u);
      }

      for (int r : toRemove) {
        if (!temp[r]) continue;
        temp[r] = false;
        for (int u : adj.get(r)) {
          if (temp[u]) deg[u]--;
        }
      }
    }
    return sis;
  }
}
