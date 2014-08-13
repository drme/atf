package eu.sarunas.atf.generators.tests;

import eu.sarunas.atf.generators.tests.data.Randomizer;
import eu.sarunas.atf.meta.sut.Class;
import eu.sarunas.atf.meta.sut.Method;
import eu.sarunas.atf.meta.testdata.TestObjectComplex;
import eu.sarunas.atf.meta.tests.TestCase;
import eu.sarunas.atf.meta.tests.TestInput;
import eu.sarunas.atf.meta.tests.TestInputParameter;
import eu.sarunas.atf.meta.tests.TestProject;
import eu.sarunas.atf.meta.tests.TestSuite;
import eu.sarunas.atf.model.checker.TestDataValidator;
import eu.sarunas.atf.utils.Logger;

public class TestsGenerator
{
	public TestSuite generate(final Method method, TestProject project, final String constraints) throws Exception
	{
		return this.testsGenerator.generate(method, new ITestsGeneratorManager()
		{
			public boolean acceptTest(TestCase testCase) throws Exception
			{
				TestDataValidator validator = new TestDataValidator();

				for (TestInput input : testCase.getInputs())
				{
					for (TestInputParameter parameter : input.getInputParameters())
					{
						if (parameter.getValue() instanceof TestObjectComplex)
						{
							TestObjectComplex data = (TestObjectComplex) parameter.getValue();

							if (false == validator.validate(/*testCase.getMethod().getParent().getPackage().getProject()*/ method.getParent().getPackage().getProject() , constraints, data).isValid())
							{
								Logger.logger.info("Discarded: " + data.toString());

								return false;
							}
						}
					}
				}

				count++;

				return true;
			};

			public boolean isDone()
			{
				return count > maxTestsCount;
			};

			private int count = 0;
		}, project);
	};
	
	public TestSuite generate(Class cl, TestProject project, String constraints) throws Exception
	{
		TestSuite testSuites = new TestSuite(project, "TestSuite" + cl.getName());

		testSuites.setName("TestSuite" + cl.getName());

		for (Method method : cl.getMethods())
		{
			TestSuite testSuite = generate(method, project, constraints);

			testSuites.getTestCases().addAll(testSuite.getTestCases());
		}

		return testSuites;
	};
	
	private static int maxTestsCount = 10;
	private ITestGenerator testsGenerator = new RandomGenerator(new Randomizer());
};
