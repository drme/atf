package eu.sarunas.atf.meta.sut.basictypes;

import eu.sarunas.projects.atf.metadata.generic.Type;

/**
 * Class for languages that use single type for all objects to inherit of.
 */
public class ObjectType extends Type
{
	public ObjectType()
	{
		super("Object", null);
		setReferenceType(true);
	};
};
