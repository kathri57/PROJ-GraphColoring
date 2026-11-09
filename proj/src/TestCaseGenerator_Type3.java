import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import si.fri.algator.entities.Variables;
import static si.fri.algator.execute.AbstractTestCase.PROPS;
import si.fri.algator.execute.AbstractTestCaseGenerator;
import si.fri.algator.global.ErrorStatus;

/**
 * Generator for real-world social network graphs (Zachary Karate Club,
 * Zebra, Dolphin, Florentine Families, Davis Southern Women).
 *
 * Since these graphs have a fixed, specific edge structure (not
 * generated from a formula), their edge lists are stored as plain text
 * resource files under the test set's resources directory
 * (tests/Testset__files/<Filename>), one edge "u v" per line. The
 * first line contains the number of vertices n.
 *
 * GeneratingParameters: Filename, GraphName
 *
 * @author btrivunovic
 */
public class TestCaseGenerator_Type3 extends AbstractTestCaseGenerator {

  @Override
  public TestCase generateTestCase(Variables generatingParameters) {
    String commonResourcesPath = getCommonResourcesPath(generatingParameters);
    String filename = generatingParameters.getVariable("Filename", "").getStringValue();
    String graphName = generatingParameters.getVariable("GraphName", filename).getStringValue();

    int n;
    List<int[]> edgeList = new ArrayList<>();

    String testFile = commonResourcesPath + File.separator + filename;
    try (BufferedReader br = new BufferedReader(new FileReader(new File(testFile)))) {
      n = Integer.parseInt(br.readLine().trim());
      String line;
      while ((line = br.readLine()) != null) {
        line = line.trim();
        if (line.isEmpty()) continue;
        String[] parts = line.split("\\s+");
        edgeList.add(new int[]{Integer.parseInt(parts[0]), Integer.parseInt(parts[1])});
      }
    } catch (Exception e) {
      ErrorStatus.setLastErrorMessage(ErrorStatus.ERROR, "Error reading file " + testFile + ": " + e.getMessage());
      return null;
    }

    int[][] edges = edgeList.toArray(new int[0][]);

    TestCase tc = new TestCase();
    tc.setInput(new Input(n, edges, graphName));

    Variables testcaseParameters = new Variables(generatingParameters);
    testcaseParameters.addProperty(PROPS, "Type", "Type3");
    testcaseParameters.addProperty(PROPS, "Filename", filename);
    tc.getInput().setParameters(testcaseParameters);

    tc.setExpectedOutput(new Output(null));

    return tc;
  }
}
