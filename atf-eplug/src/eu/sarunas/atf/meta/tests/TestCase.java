package eu.sarunas.atf.meta.tests;

import java.util.ArrayList;
import java.util.List;
import eu.sarunas.atf.meta.sut.Method;
import eu.sarunas.atf.meta.sut.Package;

public class TestCase
{
	public TestCase(Method method, TestSuite testSuite)
	{
		this.method = method;
		this.className = method.getParent().getName();
		this.packge = method.getParent().getPackage();
		this.testSuite = testSuite;
	};

	public void addInput(TestInput testInput)
	{
		this.inputs.add(testInput);
	};

	public List<TestInput> getInputs()
	{
		return inputs;
	};

	public Method getMethod()
	{
		return this.method;
	};

	public String getClassName()
	{
		return this.className;
	};

	public Package getPackage()
	{
		return this.packge;
	};

	public TestSuite getTestSuite()
	{
		return this.testSuite;
	};

	public void setTestSuite(TestSuite testSuite)
	{
		this.testSuite = testSuite;
	};

	public String toString()
	{
		String result = "\tTestCase: " + this.packge.getName() + "." + this.className + "." + this.method + "\n";

		for (TestInput testInput : this.inputs)
		{
			result += testInput.toString();
		}

		return result;
	};

	private TestSuite testSuite = null;
	private List<TestInput> inputs = new ArrayList<TestInput>();
	private String className = null;
	private Method method = null;
	private Package packge = null;
};
