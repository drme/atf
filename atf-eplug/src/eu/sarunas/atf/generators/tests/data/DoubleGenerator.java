package eu.sarunas.atf.generators.tests.data;

import eu.sarunas.projects.atf.metadata.generic.Type;

public class DoubleGenerator extends NumberGenerator
{
	public DoubleGenerator(Randomizer randomizer)
	{
		super(randomizer, -100, 100);
	};

	public Object generate(Type type)
	{
		return this.generate();
	};
};
