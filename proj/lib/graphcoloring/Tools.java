package graphcoloring;

/**
 * This class contains helper methods used to check the correctness of
 * the graph coloring algorithms' results and to present results in a "human-readable" way.
 *
 * @author btrivunovic
 */
public class Tools {

  /**
   * Checks whether a given coloring is valid for a graph defined by n
   * vertices and a list of edges: no two adjacent vertices may share
   * the same color, and every vertex must be assigned a positive color.
   */
  public static boolean isValidColoring(int n, int[][] edges, int[] color) {
    if (color == null || color.length != n) return false;
    for (int v = 0; v < n; v++) {
      if (color[v] <= 0) return false;
    }
    for (int[] e : edges) {
      int u = e[0], v = e[1];
      if (color[u] == color[v]) return false;
    }
    return true;
  }

  /** Returns the number of distinct colors used (assumes colors are 1..k). */
  public static int numColors(int[] color) {
    int max = 0;
    if (color != null) {
      for (int c : color) max = Math.max(max, c);
    }
    return max;
  }

  /** Returns a string representation of an int array (first 10 elements). */
  public static String intArrayToString(int[] array) {
    if (array == null) return "null";
    int i;
    StringBuilder sb = new StringBuilder();
    for (i = 0; i < Math.min(10, array.length); i++) {
      if (i > 0) sb.append(",");
      sb.append(array[i]);
    }
    if (i < array.length) {
      sb.append(", ... (").append(array.length).append(" elements)");
    }
    return "[" + sb + "]";
  }
}
