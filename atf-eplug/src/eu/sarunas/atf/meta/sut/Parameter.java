package eu.sarunas.atf.meta.sut;

import eu.sarunas.projects.atf.metadata.generic.Type;

public class Parameter extends Element
{
	public Parameter(String name, Type type, Object sourceElement)
	{
		super(name, sourceElement);

		this.type = type;

		if (null == type)
		{
			throw new IllegalArgumentException();
		}
	};

	public Type getType()
	{
		return this.type;
	};

	public String toString()
	{
		return "Parameter [name=" + getName() + " type=" + type + "]";
	};

	protected Type type = null;
};
