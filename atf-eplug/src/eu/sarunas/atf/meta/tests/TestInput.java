package eu.sarunas.atf.meta.tests;

import java.util.ArrayList;
import java.util.List;

public class TestInput
{
	public TestInput(TestCase testCase)
	{
		this.testCase = testCase;
	};

	public void addParameter(TestInputParameter parameter)
	{
		this.inputParameters.add(parameter);
	};

	public List<TestInputParameter> getInputParameters()
	{
		return this.inputParameters;
	};

	public TestCase getTestCase()
	{
		return this.testCase;
	};

	public String toString()
	{
		String result = "\tTestInput: " + "\n";

		for (TestInputParameter parameter : this.inputParameters)
		{
			result += parameter.toString();
		}

		return result;
	};

	private TestCase testCase = null;
	private List<TestInputParameter> inputParameters = new ArrayList<TestInputParameter>();
};
