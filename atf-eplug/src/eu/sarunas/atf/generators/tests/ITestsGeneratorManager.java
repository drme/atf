package eu.sarunas.atf.generators.tests;

import eu.sarunas.atf.meta.tests.TestCase;
import eu.sarunas.atf.model.checker.ITestDataValidator;

public interface ITestsGeneratorManager
{
	public boolean isDone();
	public boolean acceptTest(TestCase testCase) throws Exception;
	public ITestDataValidator getValidator();
};
