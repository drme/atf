package eu.sarunas.atf.generators.tests.data;

import eu.sarunas.projects.atf.metadata.generic.Type;

public class IntegerGenerator extends ITypeGenerator
{
	public IntegerGenerator(Randomizer randomizer)
	{
		super(randomizer);
	};

	public Object generate(Type type)
	{
		int value = (int) (10.0 * this.randomizer.getDistribution().getRandomValue());

		return new Integer(value);
	};
};
