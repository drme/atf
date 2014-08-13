package eu.sarunas.atf.generators.tests;

import eu.sarunas.atf.meta.sut.Method;
import eu.sarunas.atf.meta.tests.TestProject;
import eu.sarunas.atf.meta.tests.TestSuite;

public interface ITestGenerator
{
	/**
	 * Generates tests for a method
	 */
	public TestSuite generate(Method method, ITestsGeneratorManager manager, TestProject project) throws Exception;
};
