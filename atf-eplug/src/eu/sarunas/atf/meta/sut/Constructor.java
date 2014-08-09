package eu.sarunas.atf.meta.sut;

import eu.sarunas.projects.atf.metadata.generic.Type;

public class Constructor extends Method
{
	public Constructor(Class parent, String name, Modifier modifier, Type returnType, Object sourceElement)
	{
		super(parent, "<init>", modifier, returnType, sourceElement);
	};

	public boolean isDefaultConstructor()
	{
		return getParameters().isEmpty();
	};
};
