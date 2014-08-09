package eu.sarunas.atf.meta.sut.body;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import eu.sarunas.atf.meta.sut.Method;

public class MethodCall implements ICodeBodyElement
{
	public MethodCall(Method method, String objectName, String returnObjectName)
	{
		this.method = method;
		this.objectName = objectName;
		this.returnObjectName = returnObjectName;
	};

	public Method getMethod()
	{
		return this.method;
	};

	public String getObjectName()
	{
		return this.objectName;
	};

	public List<MethodCallParameter> getPrameters()
	{
		return Collections.unmodifiableList(this.parameters);
	};

	public void addParameter(MethodCallParameter parameter)
	{
		this.parameters.add(parameter);
	};

	public String getReturnObjectName()
	{
		return this.returnObjectName;
	};

	private Method method = null;
	private String objectName = null;
	private String returnObjectName = null;
	private List<MethodCallParameter> parameters = new ArrayList<MethodCallParameter>();
};
