package eu.sarunas.atf.meta.testdata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import eu.sarunas.atf.meta.sut.basictypes.CollectionStyle;
import eu.sarunas.projects.atf.metadata.generic.Type;

/**
 * Test object for array type. Contains a list of aggregated test objects.
 */
public class TestObjectCollection extends TestObjectComplex
{
	/**
	 * Constructs test object of array type.
	 * 
	 * @param name object name.
	 * @param type object type.
	 * @param size array size.
	 * @param style collection style;
	 */
	public TestObjectCollection(String name, Type type, int size, CollectionStyle style)
	{
		super(name, type);

		this.size = size;
		this.style = style;
	};

	/**
	 * Adds element to the array end.
	 * 
	 * @param element element to add.
	 */
	public void addElement(Object element)
	{
		this.elements.add(element);
	};

	/**
	 * Array elements.
	 * 
	 * @return array elements.
	 */
	public List<Object> getElements()
	{
		return Collections.unmodifiableList(this.elements);
	};

	/**
	 * Array size.
	 * 
	 * @return array size.
	 */
	public int getSize()
	{
		return this.size;
	};

	/**
	 * Collection style.
	 * 
	 * @return collection style.
	 */
	public CollectionStyle getStyle()
	{
		return this.style;
	};

	private List<Object> elements = new ArrayList<Object>();
	private int size = 0;
	private CollectionStyle style = CollectionStyle.Array;
};
