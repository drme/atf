package eu.sarunas.atf.generators.tests.data;

import eu.sarunas.atf.model.checker.ITestDataValidator;
import eu.sarunas.projects.atf.metadata.generic.Type;

public class ObjectGenerator extends ITypeGenerator
{
	public ObjectGenerator(Randomizer randomizer)
	{
		super(randomizer);
	};

	public Object generate(Type type, ITestDataValidator validator)
	{
		return new Object();
	};
};
