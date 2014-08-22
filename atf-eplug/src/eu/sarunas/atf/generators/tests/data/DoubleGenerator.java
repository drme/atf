package eu.sarunas.atf.generators.tests.data;

import eu.sarunas.atf.model.checker.ITestDataValidator;
import eu.sarunas.projects.atf.metadata.generic.Type;

public class DoubleGenerator extends NumberGenerator
{
	public DoubleGenerator(Randomizer randomizer)
	{
		super(randomizer, -100, 100);
	};

	public Object generate(Type type, ITestDataValidator validator)
	{
		return this.generate();
	};
};
