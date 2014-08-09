package eu.sarunas.atf.generators.code;

import eu.sarunas.atf.meta.sut.body.ObjectConstruct;
import eu.sarunas.atf.meta.testdata.TestObject;
import eu.sarunas.projects.atf.metadata.generic.Type;

public class TestObjectVariable extends TestObject
{
	public TestObjectVariable(String name, Type type, ObjectConstruct variable)
	{
		super(name, type);
		
		assert(null != variable);
		
		this.variable = variable;
	};

	public ObjectConstruct getVariable()
	{
		return this.variable;
	};

	private ObjectConstruct variable = null;
};
