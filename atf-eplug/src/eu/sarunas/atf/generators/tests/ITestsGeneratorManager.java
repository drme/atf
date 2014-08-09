package eu.sarunas.atf.generators.tests;

import eu.sarunas.atf.meta.tests.TestCase;

public interface ITestsGeneratorManager
{
	public boolean isDone();
	public boolean acceptTest(TestCase testCase);
};
