package eu.sarunas.atf.meta.testdata;

import eu.sarunas.projects.atf.metadata.generic.Type;

/**
 * The object instance that is used in a test case. Used for simple types.
 */
public class TestObjectSimple<T> extends TestObject
{
	/**
	 * Constructs Test data object and assigns its' value.
	 * 
	 * @param name object name.
	 * @param type object type.
	 * @param value object value (actual value used in the test case).
	 */
	public TestObjectSimple(String name, Type type, T value)
	{
		super(name, type);

		this.value = value;
	};

	/**
	 * Objects' instance value.
	 * 
	 * @return Objects' instance value.
	 */
	public T getValue()
	{
		return this.value;
	};
	
	public String toString()
	{
		return this.value.toString();
	};	

	private T value;
};
