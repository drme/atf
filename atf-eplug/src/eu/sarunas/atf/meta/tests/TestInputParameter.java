package eu.sarunas.atf.meta.tests;

import eu.sarunas.atf.meta.sut.Parameter;

public class TestInputParameter
{
	public TestInputParameter(Parameter parameter, Object value)
	{
		this.value = value;
		this.parameter = parameter;
	};

	public Object getValue()
	{
		return this.value;
	};

	public String getName()
	{
		return this.parameter.getName();
	};

	public Parameter getParameter()
	{
		return this.parameter;
	};

	public String toString()
	{
		String result = "\tTestInputParameter: " + this.parameter.getName() + ":" + this.value + "\n";

		return result;
	};

	private Parameter parameter = null;
	private Object value = null;
};
