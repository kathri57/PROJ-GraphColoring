import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import si.fri.algator.entities.EVariable;
import si.fri.algator.entities.Variables;
import static si.fri.algator.execute.AbstractTestCase.PROPS;
import si.fri.algator.execute.AbstractTestCaseGenerator;
import si.fri.algator.global.ErrorStatus;

/**
 * Generator for Erdos-Renyi random graphs G(n, p).
 *
 * GeneratingParameters: N, P, Seed
 *   N - number of vertices
 *   P - edge probability (0..1)
 *   Seed - random seed, for reproducibility
 *
 * @author btrivunovic
 */
public class TestCaseGenerator_Type1 extends AbstractTestCaseGenerator {

  @Override
  public TestCase generateTestCase(Variables generatingParameters) {
    int n = generatingParameters.getVariable("N", 0).getIntValue();
    double p = generatingParameters.getVariable("P", 0.0).getDoubleValue();
    long seed = generatingParameters.getVariable("Seed", 42).getIntValue();

    if (n < 1) {
      ErrorStatus.setLastErrorMessage(ErrorStatus.ERROR, "N too small");
      return null;
    }
    if (p < 0 || p > 1) {
      ErrorStatus.setLastErrorMessage(ErrorStatus.ERROR, "P must be in [0, 1]");
      return null;
    }

    Random rnd = new Random(seed);
    List<int[]> edgeList = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      for (int j = i + 1; j < n; j++) {
        if (rnd.nextDouble() < p) {
          edgeList.add(new int[]{i, j});
        }
      }
    }
    int[][] edges = edgeList.toArray(new int[0][]);
    String name = "G(" + n + "," + p + ")";

    TestCase tc = new TestCase();
    tc.setInput(new Input(n, edges, name));

    Variables testcaseParameters = new Variables(generatingParameters);
    testcaseParameters.addVariable(new EVariable("N", n));
    testcaseParameters.addProperty(PROPS, "Type", "Type1");
    tc.getInput().setParameters(testcaseParameters);

    tc.setExpectedOutput(new Output(null));

    return tc;
  }
}
