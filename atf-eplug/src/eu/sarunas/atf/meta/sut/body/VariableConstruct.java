package eu.sarunas.atf.meta.sut.body;

import eu.sarunas.projects.atf.metadata.generic.Type;

public class VariableConstruct extends Variable
{
	protected VariableConstruct(String name, Type type, ObjectConstruct consturct)
	{
		super(name, type);

		this.construct = consturct;
	};

	public ObjectConstruct getConstruct()
	{
		return this.construct;
	};

	private ObjectConstruct construct = null;
};
