package eu.sarunas.atf.generators.tests.data;

import eu.sarunas.projects.atf.metadata.generic.Type;

public abstract class ITypeGenerator
{
	public ITypeGenerator(Randomizer randomizer)
	{
		this.randomizer = randomizer;
	};

	public abstract Object generate(Type type);

	protected Randomizer randomizer = null;
};
