package eu.sarunas.atf.generators.tests.data;

import eu.sarunas.projects.atf.metadata.generic.Type;

public class FloatGenerator extends NumberGenerator
{
	public FloatGenerator(Randomizer randomizer)
	{
		super(randomizer, -10.0, 10.0);
	};

	public Object generate(Type type)
	{
		return (float)generate();
	};
};
