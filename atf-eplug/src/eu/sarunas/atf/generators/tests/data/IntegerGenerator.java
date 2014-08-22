package eu.sarunas.atf.generators.tests.data;

import eu.sarunas.atf.model.checker.ITestDataValidator;
import eu.sarunas.projects.atf.metadata.generic.Type;

public class IntegerGenerator extends ITypeGenerator
{
	public IntegerGenerator(Randomizer randomizer)
	{
		super(randomizer);
	};

	public Object generate(Type type, ITestDataValidator validator)
	{
		//int value = (int)(Integer.MIN_VALUE + (Integer.MAX_VALUE - Integer.MIN_VALUE) * this.randomizer.getDistribution().getRandomValue());
		int value = (int) (10.0 * this.randomizer.getDistribution().getRandomValue());

		return new Integer(value);
	};
};
