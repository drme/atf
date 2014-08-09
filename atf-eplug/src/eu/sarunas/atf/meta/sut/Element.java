package eu.sarunas.atf.meta.sut;

import java.util.ArrayList;
import java.util.List;
import eu.sarunas.atf.meta.sut.body.ICodeElement;

public abstract class Element implements ICodeElement
{
	public Element(String name, Object sourceElement)
	{
		this.sourceElement = sourceElement;
		this.name = name;
	};

	public String getName()
	{
		return this.name;
	};

	public List<String> getAnnotations()
	{
		return this.annotations;
	};
	
	public Object getSourceElement()
	{
		return this.sourceElement;
	};

	public String toString()
	{
		return "Element [name=" + name + "]";
	};
	
	/**
	 * Searches for elements that are build from passed source element.
	 * 
	 * @param sourceElement the source element to check if this element of its' children are build from.
	 * 
	 * @return this or one of its children if one of them matches sourceElement.
	 */
	protected Element findElement(Object sourceElement)
	{
		if (true == sourceElement.equals(this.sourceElement))
		{
			return this;
		}
		else
		{
			return null;
		}
	};

	private Object sourceElement = null;
	private String name = null;
	private List<String> annotations = new ArrayList<String>();
};
