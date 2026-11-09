import si.fri.algator.execute.AbstractIndicatorTest;

/**
 * IndicatorTest for the "Check" indicator of the GraphColoring project.
 *
 * A coloring is considered correct ("OK") if it is a valid coloring of
 * the input graph, i.e. no two adjacent vertices share the same color.
 * Since multiple valid colorings may exist, we do not compare against
 * testCase.getExpectedOutput(); instead, we validate the produced
 * coloring against the input graph.
 *
 * @author btrivunovic
 */
public class IndicatorTest_Check extends AbstractIndicatorTest<TestCase, Output> {

  @Override
  public Object getValue(TestCase testCase, Output algorithmOutput) {
    Input input = testCase.getInput();
    boolean valid = graphcoloring.Tools.isValidColoring(
        input.n, input.edges, algorithmOutput.color);
    return valid ? "OK" : "NOK";
  }
}
