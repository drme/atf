package eu.sarunas.atf.generators.tests;

import java.io.IOException;
import eu.atac.atf.main.Files;
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

public class TestsGenerator
{
	public TestsGenerator()
	{
	};
	
	public TestSuite generate(Method method, TestProject project, final String constraints)
	{
		return this.m.generate(method, new ITestsGeneratorManager()
		{
			public boolean acceptTest(TestCase testCase)
            {
				TestDataValidator validator = new TestDataValidator();

				for (TestInput input : testCase.getInputs())
				{
					for (TestInputParameter parameter : input.getInputParameters())
					{
						if (parameter.getValue() instanceof TestObjectComplex)
						{
							TestObjectComplex data = (TestObjectComplex)parameter.getValue();
									
							if (false == validator.validate(testCase.getMethod().getParent().getPackage().getProject(), constraints, data).isValid())			
							{
							System.out.println("Discarded: " + data.toString());
								
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
				return count > 10;
            };
			
			private int count = 0;
		}, project);
	};
	
	public TestSuite generate(Class cl, TestProject project, String constraints)
	{
		TestSuite ts = new TestSuite(project, "TestSuite" + cl.getName());
		
		ts.setName("TestSuite" + cl.getName());
		
		for (Method m : cl.getMethods())
		{
			TestSuite testSuite = generate(m, project, constraints);
			
			ts.getTestCases().addAll(testSuite.getTestCases());
		}
		
		return ts;
	};
	
	private ITestGenerator m = new RandomGenerator(new Randomizer());
};
