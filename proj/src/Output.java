import si.fri.algator.execute.AbstractOutput;

/**
 * A graphcoloring-project-specific output.
 *
 * @author btrivunovic
 */
public class Output extends AbstractOutput {

  /** color[v] is the color (a positive integer) assigned to vertex v. */
  public int[] color;

  public Output() {}

  public Output(int[] color) {
    this.color = color;
  }

  /** Number of distinct colors used (assumes colors are 1..k). */
  public int numColors() {
    int max = 0;
    if (color != null) {
      for (int c : color) max = Math.max(max, c);
    }
    return max;
  }

  @Override
  public String toString() {
    return super.toString() + ", Colors used: " + numColors()
        + ", Coloring: " + graphcoloring.Tools.intArrayToString(color);
  }
}
