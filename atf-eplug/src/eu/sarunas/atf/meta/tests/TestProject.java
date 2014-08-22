package eu.sarunas.atf.meta.tests;

import java.util.ArrayList;
import java.util.List;

public class TestProject
{
	public TestProject()
	{
	};

	public String getName()
	{
		return name;
	};

	public void setName(String name)
	{
		this.name = name;
	};

	public List<TestSuite> getTestSuites()
	{
		return this.testSuites;
	};

	private String name = null;
	private List<TestSuite> testSuites = new ArrayList<TestSuite>();
};
