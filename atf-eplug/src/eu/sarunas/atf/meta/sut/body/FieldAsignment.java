package eu.sarunas.atf.meta.sut.body;

import eu.sarunas.atf.meta.sut.Field;
import eu.sarunas.atf.meta.testdata.TestObject;

public class FieldAsignment implements ICodeBodyElement
{
	public FieldAsignment(ObjectConstruct object, Field field, TestObject value)
	{
		this.object = object;
		this.value = value;
		this.field = field;
	};

	public ObjectConstruct getObject()
	{
		return this.object;
	};

	public TestObject getValue()
	{
		return this.value;
	};
	
	public Field getField()
	{
		return this.field;
	};

	private ObjectConstruct object = null;
	private TestObject value = null;
	private Field field = null;
};
