package eu.sarunas.atf.meta.testdata;

import eu.sarunas.atf.meta.sut.Field;

/**
 * Represents the field in test data object. Specifies field name, type and values.
 */
public class TestObjectField
{
	/**
	 * Constructs test data object field value. Contains reference to the class field and its value.
	 * 
	 * @param field class field.
	 * @param value class fields value.
	 */
	public TestObjectField(Field field, TestObject value)
	{
		this.field = field;
		this.value = value;
	};

	/**
	 * Class field this value belongs to.
	 * 
	 * @return field.
	 */
	public Field getField()
	{
		return this.field;
	};

	/**
	 * This instances value for this field.
	 * 
	 * @return value.
	 */
	public TestObject getValue()
	{
		return this.value;
	};
	
	public String toString()
	{
		String result = "" + this.field.getName() + "=" + ((null != value) ? this.value.toString() : "null");
		
		return result;
	};	

	private TestObject value;
	private Field field;
};
