package eu.sarunas.projects.atf.tests.runners;

import eu.sarunas.atf.meta.tests.TestCase;

public interface ITestsRunner
{
	public void runTest(TestCase testCase);
	
	public void setUp();
	public void run();
	public void cleanUp();
};
