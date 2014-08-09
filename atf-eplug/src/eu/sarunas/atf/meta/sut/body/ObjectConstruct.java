package eu.sarunas.atf.meta.sut.body;

import eu.sarunas.atf.meta.sut.Class;

public class ObjectConstruct extends MethodCall
{
	public ObjectConstruct(Class classToConstruct, String objectName)
	{
		super(null, objectName, null);

		this.classToConstruct = classToConstruct;
	};

	public Class getClassToConstruct()
	{
		return this.classToConstruct;
	};

	private Class classToConstruct = null;
};
