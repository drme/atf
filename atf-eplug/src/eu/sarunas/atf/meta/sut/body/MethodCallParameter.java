package eu.sarunas.atf.meta.sut.body;

public class MethodCallParameter
{
	public MethodCallParameter(String name, Object value)
	{
		this.parameterName = name;
		this.parameterValue = value;
	};

	public String getParameterName()
	{
		return this.parameterName;
	};

	public Object getParameterValue()
	{
		return this.parameterValue;
	};

	private String parameterName = null;
	private Object parameterValue = null;
};
