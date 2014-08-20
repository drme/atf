package eu.sarunas.atf.meta.sut;

import java.util.EnumSet;

public class Exception extends Class
{
	public Exception(String name, EnumSet<Modifier> modifiers, Package pckg, Object sourceElement)
	{
		super(name, modifiers, pckg, sourceElement);
	};
};
