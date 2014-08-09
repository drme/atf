package eu.sarunas.atf.generators.tests.data;

import eu.sarunas.projects.atf.metadata.generic.Type;

public class LongGenerator extends NumberGenerator
{
	public LongGenerator(Randomizer randomizer)
	{
		super(randomizer, -10.0, 10.0);
	};

	public Object generate(Type type)
	{
		return (long) generate();
	};
};
