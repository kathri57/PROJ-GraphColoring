import si.fri.algator.execute.AbstractIndicatorTest;

/**
 * IndicatorTest for the "NumColors" indicator of the GraphColoring project.
 *
 * Reports the number of distinct colors used by the algorithm's coloring.
 * This is the central quality indicator for this project: lower NumColors 
 * (closer to the graph's true chromatic number) means a better coloring.
 *
 * @author btrivunovic
 */
public class IndicatorTest_NumColors extends AbstractIndicatorTest<TestCase, Output> {

  @Override
  public Object getValue(TestCase testCase, Output algorithmOutput) {
    return graphcoloring.Tools.numColors(algorithmOutput.color);
  }
}
