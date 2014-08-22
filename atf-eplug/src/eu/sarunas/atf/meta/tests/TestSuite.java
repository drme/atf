package eu.sarunas.atf.meta.tests;

import java.util.ArrayList;
import java.util.List;

public class TestSuite
{
	public TestSuite(TestProject project, String name)
	{
		this.testProject = project;
		this.name = name;
	};

	public void addTestCase(TestCase testCase)
	{
		this.testCases.add(testCase);
	};

	public List<TestCase> getTestCases()
	{
		return testCases;
	};

	public String getName()
	{
		return name;
	};

	public void setName(String name)
	{
		this.name = name;
	};

	public TestProject getTestProject()
	{
		return this.testProject;
	};

	public void setTestProject(TestProject testProject)
	{
		this.testProject = testProject;
	};

	public String toString()
	{
		String result = "TestSuite: " + this.name + "\n";

		for (TestCase testCase : this.testCases)
		{
			result += testCase.toString();
		}

		return result;
	};

	private String name = null;
	private List<TestCase> testCases = new ArrayList<TestCase>();
	private TestProject testProject = null;
};
