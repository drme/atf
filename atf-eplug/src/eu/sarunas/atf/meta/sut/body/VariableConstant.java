package eu.sarunas.atf.meta.sut.body;

import eu.sarunas.projects.atf.metadata.generic.Type;

public class VariableConstant<T> extends Variable
{
	public VariableConstant(String name, Type type, T value)
	{
		super(name, type);

		this.value = value;
	};

	public T getValue()
	{
		return this.value;
	};

	private T value;
};
