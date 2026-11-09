import java.util.ArrayList;
import java.util.List;

/**
 * RLF (Recursive Largest First) graph coloring algorithm.
 *
 * Builds one independent set (color class) at a time:
 *   - Starts with the vertex having the most uncolored neighbours.
 *   - Iteratively adds the candidate with the most neighbours in X (uncolored vertices already excluded from the current class);
 *     ties are broken by the fewest neighbours among the remaining candidates C, then by the smaller index.
 *   - When the class can no longer grow, it is colored and the process repeats on the remaining vertices.
 *
 * Time complexity: O(|V| * (|V| + |E|)) in the worst case.
 *
 * Reference: Leighton, F.T. (1979). A graph coloring algorithm for large scheduling problems. Journal of Research of the National Bureau of Standards, 84(6), 489-506.
 *
 * @author btrivunovic
 */
public class Algorithm extends ProjectAbstractAlgorithm {

  @Override
  protected Output execute(Input input) {
    int n = input.n;
    List<List<Integer>> adj = buildAdjacency(n, input.edges);

    int[] color = new int[n];
    boolean[] colored = new boolean[n];
    int col = 1;
    int remaining = n;

    while (remaining > 0) {
      //@COUNT{CALL, 1}

      boolean[] inC = new boolean[n];
      for (int v = 0; v < n; v++) {
        if (!colored[v]) inC[v] = true;
      }
      boolean[] inX = new boolean[n];

      int start = -1, startDeg = -1;
      for (int v = 0; v < n; v++) {
        if (!inC[v]) continue;
        int d = countInSet(adj, v, inC);
        //@COUNT{CMP, 1}
        if (d > startDeg) {
          start = v;
          startDeg = d;
        }
      }

      List<Integer> colorClass = new ArrayList<>();
      addToColorClass(adj, start, colorClass, inC, inX);

      while (true) {
        int best = -1, bestX = -1, bestC = -1;
        for (int v = 0; v < n; v++) {
          if (!inC[v]) continue;
          int x = countInSet(adj, v, inX);
          int c = countInSet(adj, v, inC);
          //@COUNT{CMP, 1}
          if (best == -1 || x > bestX || (x == bestX && c < bestC)) {
            best = v;
            bestX = x;
            bestC = c;
          }
        }
        if (best == -1) break;
        addToColorClass(adj, best, colorClass, inC, inX);
      }

      for (int v : colorClass) {
        color[v] = col;
        colored[v] = true;
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

  /** Counts neighbours of v that are in the given set. */
  private int countInSet(List<List<Integer>> adj, int v, boolean[] inSet) {
    int cnt = 0;
    for (int u : adj.get(v)) {
      //@COUNT{CMP, 1}
      if (inSet[u]) cnt++;
    }
    return cnt;
  }

  /** Adds a vertex to the current color class and updates C and X. */
  private void addToColorClass(List<List<Integer>> adj, int v,
                                List<Integer> cls, boolean[] inC, boolean[] inX) {
    cls.add(v);
    inC[v] = false;
    for (int u : adj.get(v)) {
      if (inC[u]) {
        inC[u] = false;
        inX[u] = true;
      }
    }
  }
}
