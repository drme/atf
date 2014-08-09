package eu.sarunas.projects.atf.runner;
import eu.sarunas.atf.meta.tests.TestCase;
import eu.sarunas.atf.meta.tests.TestSuite;

/**
 * Interface responsible for transforming tests stored in database
 * to selected unit testing framework.
 */
public interface ITestTransform
{
	public TransformedTestCase transform(TestSuite testSuite);
};
