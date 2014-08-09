package eu.sarunas.atf.meta.testdata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import eu.sarunas.projects.atf.metadata.generic.Type;

/**
 * Test object for complex types. Aggregates other test objects using a fields member.
 */
public class TestObjectComplex extends TestObject
{
	/**
	 * Constructs test data object for complex types.
	 * 
	 * @param name object name.
	 * @param type object type.
	 */
	public TestObjectComplex(String name, Type type)
	{
		super(name, type);
	};

	/**
	 * Adds a field to the test object.
	 * 
	 * @param field field to add.
	 */
	public void addField(TestObjectField field)
	{
		this.fields.add(field);
	};

	/**
	 * A read-only list of this object fields with their values.
	 * 
	 * @return objects fields.
	 */
	public List<TestObjectField> getFields()
	{
		return Collections.unmodifiableList(this.fields);
	};
	
	public String toString()
	{
		String result = this.getName() + ":";
		
		for (TestObjectField field : this.fields)
		{
			result += field.toString();
		}
		
		return result;
	};

	private List<TestObjectField> fields = new ArrayList<TestObjectField>();
};
