package eu.sarunas.atf.meta.sut;

import java.util.ArrayList;
import java.util.List;
import eu.sarunas.atf.meta.sut.basictypes.VoidType;
import eu.sarunas.atf.meta.sut.body.ICodeBodyElement;
import eu.sarunas.projects.atf.metadata.generic.Type;

public class Method extends Element
{
	public Method(Class parent, String name, Modifier modifier, Type returnType, Object sourceElement)
	{
		super(name, sourceElement);
		this.parent = parent;
		this.modifer = modifier;
		this.returnType = returnType;
	};

	public void addParameter(Parameter parameter)
	{
		this.parameters.add(parameter);
	};

	public List<Parameter> getParameters()
	{
		return this.parameters;
	};

	public Type getReturnType()
	{
		return this.returnType;
	};

	public Class getParent()
	{
		return this.parent;
	};

	public List<Exception> getExceptions()
	{
		return this.exceptions;
	};

	public Modifier getModifier()
	{
		return this.modifer;
	};

	public List<ICodeBodyElement> getImplementation()
	{
		return this.elements;
	};

	protected Element findElement(Object sourceElement)
	{
		Element result = super.findElement(sourceElement);

		if (null == result)
		{
			for (Parameter parameter : this.parameters)
			{
				result = parameter.findElement(sourceElement);

				if (null != result)
				{
					break;
				}
			}
		}

		return result;
	};

	public String toString()
	{
		String result = "Method: " + getName() + "\n";

		result += "Return type: " + this.returnType.getName() + "\n";

		for (Parameter parameter : this.parameters)
		{
			result += parameter.toString();
		}

		return result;
	};

	protected List<Exception> exceptions = new ArrayList<Exception>();
	protected Type returnType = new VoidType();
	protected Class parent = null;
	protected Modifier modifer = Modifier.None;
	protected List<ICodeBodyElement> elements = new ArrayList<ICodeBodyElement>();
	protected List<Parameter> parameters = new ArrayList<Parameter>();
};
