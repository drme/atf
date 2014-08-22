package eu.sarunas.atf.meta.sut.body;

import eu.sarunas.projects.atf.metadata.generic.Type;

public class ArrayConstruct extends MethodCall
{
	public ArrayConstruct(Type typeToConstruct, String objectName, int size)
	{
		super(null, objectName, null);

		this.size = size;
		this.typeToConstruct = typeToConstruct;
	};

	public int getSize()
	{
		return this.size;
	};

	public Type getTypeToConstruct()
	{
		return this.typeToConstruct;
	};

	private Type typeToConstruct = null;
	private int size = 0;
};
