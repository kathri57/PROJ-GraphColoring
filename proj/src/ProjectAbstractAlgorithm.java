import si.fri.algator.execute.AbstractAlgorithm;

/**
 * @author btrivunovic
 */
public abstract class ProjectAbstractAlgorithm extends AbstractAlgorithm {

  protected abstract Output execute(Input graphInput);

  @Override
  public TestCase getCurrentTestCase() {
    return (TestCase) super.getCurrentTestCase();
  }

  @Override
  public void run() {
    algorithmOutput = execute(getCurrentTestCase().getInput());
  }
}
