package eu.sarunas.atf.generators.tests.data;

import eu.sarunas.atf.model.checker.ITestDataValidator;
import eu.sarunas.projects.atf.metadata.generic.Type;

public class StringGenerator extends ITypeGenerator
{
	public StringGenerator(Randomizer randomizer)
	{
		super(randomizer);
	};

	public Object generate(Type type, ITestDataValidator validator)
	{
		int length = (int) ((float) this.maxLength * this.randomizer.getDistribution().getRandomValue());

		StringBuilder s = new StringBuilder();

		for (int i = 0; i < length; i++)
		{
			char c = 'a';//(char) ('a' + ('z' - 'a') * this.randomizer.getDistribution().getRandomValue());

			s.append(c);
		}

		return s.toString();
	};

	private int maxLength = 10;
};
