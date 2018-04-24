package eu.sarunas.atf.meta.sut;

import java.util.EnumSet;
import eu.sarunas.projects.atf.metadata.generic.Type;

public class Field extends Element
{
	public Field(String name, EnumSet<Modifier> modifers, Type type, Class parent, Object sourceElement)
	{
		super(name, sourceElement);
		this.type = type;
		this.modifiers = modifers;
		this.parent = parent;
	};

	public EnumSet<Modifier> getModifiers()
	{
		return this.modifiers;
	};

	public Type getType()
	{
		return this.type;
	};

	public Class getParent()
	{
		return this.parent;
	};

	public Type getGenericType()
	{
		return this.genericType;
	};

	public void setGenericType(Type genericType)
	{
		this.genericType = genericType;
	};

	@Override
	public String toString()
	{
		return "Field: " + getName() + "\n";
	};

	private Type genericType = null;
	private Type type;
	private EnumSet<Modifier> modifiers;
	private Class parent;
};
