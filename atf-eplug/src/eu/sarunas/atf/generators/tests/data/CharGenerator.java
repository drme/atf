package eu.sarunas.atf.generators.tests.data;

import eu.sarunas.atf.model.checker.ITestDataValidator;
import eu.sarunas.projects.atf.metadata.generic.Type;

public class CharGenerator extends NumberGenerator
{
	public CharGenerator(Randomizer randomizer)
	{
		super(randomizer, 0, 0xff);
	};

	public Object generate(Type type, ITestDataValidator validator)
	{
		return (char) generate();
	};
};
