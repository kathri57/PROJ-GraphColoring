import si.fri.algator.entities.Variables;
import static si.fri.algator.execute.AbstractTestCase.PROPS;
import si.fri.algator.execute.AbstractTestCaseGenerator;
import si.fri.algator.global.ErrorStatus;

/**
 * Generator for classic, named graphs with well-known structure and
 * chromatic number: Petersen graph, cycles, complete graphs, star
 * graphs and paths.
 *
 * GeneratingParameters: GraphType, GraphParam
 *   GraphType in {PETERSEN, CYCLE, COMPLETE, STAR, PATH}
 *   GraphParam: number of vertices (ignored for PETERSEN, which is fixed at 10)
 *
 * @author btrivunovic
 */
public class TestCaseGenerator_Type0 extends AbstractTestCaseGenerator {

  @Override
  public TestCase generateTestCase(Variables generatingParameters) {
    String type = generatingParameters.getVariable("GraphType", "PETERSEN").getStringValue();
    int param = generatingParameters.getVariable("GraphParam", 10).getIntValue();
    int n;
    int[][] edges;
    String name;

    switch (type) {
      case "PETERSEN": {
        n = 10;
        edges = new int[][]{
            {0,1},{1,2},{2,3},{3,4},{4,0},
            {5,7},{7,9},{9,6},{6,8},{8,5},
            {0,5},{1,6},{2,7},{3,8},{4,9}
        };
        name = "Petersen";
        break;
      }
      case "CYCLE": {
        n = param;
        edges = new int[n][2];
        for (int i = 0; i < n; i++) edges[i] = new int[]{i, (i + 1) % n};
        name = "Cycle C" + n;
        break;
      }
      case "COMPLETE": {
        n = param;
        edges = new int[n * (n - 1) / 2][2];
        int k = 0;
        for (int i = 0; i < n; i++)
          for (int j = i + 1; j < n; j++)
            edges[k++] = new int[]{i, j};
        name = "Complete K" + n;
        break;
      }
      case "STAR": {
        n = param + 1;
        edges = new int[param][2];
        for (int i = 1; i <= param; i++) edges[i - 1] = new int[]{0, i};
        name = "Star S" + param;
        break;
      }
      case "PATH": {
        n = param;
        edges = new int[n - 1][2];
        for (int i = 0; i < n - 1; i++) edges[i] = new int[]{i, i + 1};
        name = "Path P" + n;
        break;
      }
      default:
        ErrorStatus.setLastErrorMessage(ErrorStatus.ERROR, "Unknown GraphType: " + type);
        return null;
    }

    TestCase tc = new TestCase();
    tc.setInput(new Input(n, edges, name));

    Variables testcaseParameters = new Variables(generatingParameters);
    testcaseParameters.addProperty(PROPS, "Type", "Type0");
    tc.getInput().setParameters(testcaseParameters);

    // No single correct coloring exists; expected output left empty.
    // Correctness is re-validated against the input graph (see IndicatorTest_Check).
    tc.setExpectedOutput(new Output(null));

    return tc;
  }
}
