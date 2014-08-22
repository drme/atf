package eu.sarunas.atf.generators.tests.data;

import java.util.Set;
import eu.sarunas.atf.meta.sut.Enum;
import eu.sarunas.atf.meta.testdata.TestObjectEnumValue;
import eu.sarunas.atf.model.checker.ITestDataValidator;
import eu.sarunas.projects.atf.metadata.generic.Type;

public class EnumGenerator extends ITypeGenerator
{
	public EnumGenerator(Randomizer randomizer)
	{
		super(randomizer);
	};

	public Object generate(Type type, ITestDataValidator validator)
	{
		Set<String> values = ((Enum) type).getValues();

		if (values.size() > 0)
		{
			int index = (int) (values.size() * Math.abs(this.randomizer.getDistribution().getRandomValue()));
			
			if (index >= values.size())
			{
				index = 0;
			}
			
			String value = (String) values.toArray()[index];

			return new TestObjectEnumValue(null, type, value);
		}
		else
		{
			return null;
		}
	};
};
