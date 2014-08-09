package eu.sarunas.atf.meta.sut.body;

import eu.sarunas.projects.atf.metadata.generic.Type;

public abstract class Variable implements ICodeBodyElement
{
	protected Variable(String name, Type type)
	{
		this.name = name;
		this.type = type;
	};

	public String getName()
	{
		return this.name;
	};

	public Type getType()
	{
		return this.type;
	};

	private String name = null;
	private Type type = null;
};
