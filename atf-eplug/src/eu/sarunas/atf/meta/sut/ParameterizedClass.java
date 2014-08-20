package eu.sarunas.atf.meta.sut;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import eu.sarunas.projects.atf.metadata.generic.Type;

/**
 * Represents a generic class that is parameterized with some types.
 */
public class ParameterizedClass extends Class
{
	public ParameterizedClass(Class genericClass, String name, EnumSet<Modifier> modifiers, Package pckg, Object sourceElement)
	{
		super(name, modifiers, pckg, sourceElement);

		this.genericClass = genericClass;
	};

	public List<Type> getParameters()
	{
		return Collections.unmodifiableList(this.parameters);
	};

	public void addParameter(Type parameter)
	{
		this.parameters.add(parameter);
	};

	@Override
	public String getName()
	{
		return this.genericClass.getName();
	};

	private Class genericClass = null;
	private List<Type> parameters = new ArrayList<Type>();
};
