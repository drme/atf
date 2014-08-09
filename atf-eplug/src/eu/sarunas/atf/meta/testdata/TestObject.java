package eu.sarunas.atf.meta.testdata;

import eu.sarunas.projects.atf.metadata.generic.Type;

/**
 * The object instance that is used in a test case.
 */
public abstract class TestObject
{
	/**
	 * Constructs test object.
	 * 
	 * @param name object name.
	 * @param type object type.
	 */
	protected TestObject(String name, Type type)
	{
		this.name = name;
		this.type = type;
	};

	/**
	 * Object type.
	 * 
	 * @return object type.
	 */
	public Type getType()
	{
		return this.type;
	};

	/**
	 * Object name.
	 * 
	 * @return object name.
	 */
	public String getName()
	{
		return this.name;
	};

	private Type type = null;
	private String name = null;
};
