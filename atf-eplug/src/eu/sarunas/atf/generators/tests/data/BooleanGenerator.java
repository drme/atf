package eu.sarunas.atf.generators.tests.data;

import eu.sarunas.atf.model.checker.ITestDataValidator;
import eu.sarunas.projects.atf.metadata.generic.Type;

public class BooleanGenerator extends NumberGenerator
{
	public BooleanGenerator(Randomizer randomizer)
	{
		super(randomizer, 0, 1);
	};

	public Object generate(Type type, ITestDataValidator validator)
	{
		if (generate() > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
};
