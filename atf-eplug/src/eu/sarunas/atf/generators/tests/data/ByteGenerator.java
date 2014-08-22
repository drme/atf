package eu.sarunas.atf.generators.tests.data;

import eu.sarunas.atf.model.checker.ITestDataValidator;
import eu.sarunas.projects.atf.metadata.generic.Type;

public class ByteGenerator extends NumberGenerator
{
	public ByteGenerator(Randomizer randomizer)
	{
		super(randomizer, 0, 255);
	};

	public Object generate(Type type, ITestDataValidator validator)
	{
		return (byte) generate();
	};
};
