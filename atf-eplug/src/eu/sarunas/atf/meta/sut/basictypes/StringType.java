package eu.sarunas.atf.meta.sut.basictypes;

import eu.sarunas.projects.atf.metadata.generic.Type;

public class StringType extends Type
{
	public StringType()
	{
		super("String", null);
		setReferenceType(true);
	};
};
