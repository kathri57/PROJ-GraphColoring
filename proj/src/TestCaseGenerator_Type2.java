import java.util.ArrayList;
import java.util.List;
import si.fri.algator.entities.Variables;
import static si.fri.algator.execute.AbstractTestCase.PROPS;
import si.fri.algator.execute.AbstractTestCaseGenerator;
import si.fri.algator.global.ErrorStatus;

/**
 * Generator for DIMACS-style benchmark graphs: Queen(n,n) and
 * Mycielski graphs Myciel(k).
 *
 * GeneratingParameters: GraphType, GraphParam
 *   GraphType in {QUEEN, MYCIEL}
 *   GraphParam: for QUEEN, the board size n (graph has n*n vertices);
 *               for MYCIEL, the chromatic number k
 *               (note: Myciel(k) here has chromatic number k, e.g. Myciel(3) = C5;
 *               the DIMACS file myciel{k} corresponds to Myciel(k+1))
 *
 * @author btrivunovic
 */
public class TestCaseGenerator_Type2 extends AbstractTestCaseGenerator {

  @Override
  public TestCase generateTestCase(Variables generatingParameters) {
    String type = generatingParameters.getVariable("GraphType", "QUEEN").getStringValue();
    int param = generatingParameters.getVariable("GraphParam", 5).getIntValue();

    int n;
    int[][] edges;
    String name;

    switch (type) {
      case "QUEEN": {
        n = param * param;
        List<int[]> edgeList = new ArrayList<>();
        for (int r1 = 0; r1 < param; r1++) {
          for (int c1 = 0; c1 < param; c1++) {
            for (int r2 = 0; r2 < param; r2++) {
              for (int c2 = 0; c2 < param; c2++) {
                if (r1 == r2 && c1 == c2) continue;
                int v1 = r1 * param + c1, v2 = r2 * param + c2;
                if (v1 >= v2) continue;
                if (r1 == r2 || c1 == c2 || Math.abs(r1 - r2) == Math.abs(c1 - c2)) {
                  edgeList.add(new int[]{v1, v2});
                }
              }
            }
          }
        }
        edges = edgeList.toArray(new int[0][]);
        name = "Queen " + param + "x" + param;
        break;
      }
      case "MYCIEL": {
        // Build iteratively using the Mycielski construction, starting from K2.
        int curN = 2;
        List<int[]> curEdges = new ArrayList<>();
        curEdges.add(new int[]{0, 1});

        for (int step = 2; step < param; step++) {
          int newN = 2 * curN + 1;
          List<int[]> newEdges = new ArrayList<>();
          // copy original edges
          for (int[] e : curEdges) {
            newEdges.add(new int[]{e[0], e[1]});
          }
          // shadow edges: ui connected to all neighbours of vi
          for (int[] e : curEdges) {
            newEdges.add(new int[]{curN + e[0], e[1]});
            newEdges.add(new int[]{curN + e[1], e[0]});
          }
          // apex vertex w (index 2*curN) connected to all shadows
          for (int v = 0; v < curN; v++) {
            newEdges.add(new int[]{2 * curN, curN + v});
          }
          curN = newN;
          curEdges = newEdges;
        }
        n = curN;
        edges = curEdges.toArray(new int[0][]);
        name = "Myciel" + param;
        break;
      }
      default:
        ErrorStatus.setLastErrorMessage(ErrorStatus.ERROR, "Unknown GraphType: " + type);
        return null;
    }

    TestCase tc = new TestCase();
    tc.setInput(new Input(n, edges, name));

    Variables testcaseParameters = new Variables(generatingParameters);
    testcaseParameters.addProperty(PROPS, "Type", "Type2");
    tc.getInput().setParameters(testcaseParameters);

    tc.setExpectedOutput(new Output(null));

    return tc;
  }
}
