import si.fri.algator.execute.AbstractInput;

/**
 * A graphcoloring-project-specific input.
 *
 * The graph is represented as a number of vertices (n) and a list of
 * edges (pairs of vertex indices). This representation is simple to
 * generate, serialize and log.
 *
 * @author btrivunovic
 */
public class Input extends AbstractInput {

  /** Number of vertices in the graph. */
  public int n;

  /** Edges of the graph; edges[i] = {u, v} means vertex u is adjacent to vertex v. */
  public int[][] edges;

  /** Human-readable name of the graph (e.g. "Petersen", "Zachary Karate", "G(100,0.4)"). */
  public String graphName;

  public Input() {}

  public Input(int n, int[][] edges, String graphName) {
    this.n = n;
    this.edges = edges;
    this.graphName = graphName;
  }

  @Override
  public String toString() {
    return super.toString() + ", Graph: " + graphName
        + " (n=" + n + ", m=" + (edges == null ? 0 : edges.length) + ")";
  }
}
