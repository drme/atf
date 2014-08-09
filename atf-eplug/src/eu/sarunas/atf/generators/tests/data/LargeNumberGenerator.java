package eu.sarunas.atf.generators.tests.data;

import java.math.BigDecimal;
import java.math.BigInteger;
import eu.sarunas.atf.meta.sut.basictypes.LargeNumberType;
import eu.sarunas.projects.atf.metadata.generic.Type;

public class LargeNumberGenerator extends NumberGenerator
{
	public LargeNumberGenerator(Randomizer randomizer)
	{
		super(randomizer, -100, 100);
	};

	public Object generate(Type type)
	{
		if ("BigInteger".equalsIgnoreCase(((LargeNumberType)type).getNativeType()))
		{
			return new BigInteger("" + ((int)this.generate()));
		}
		else
		{
			return new BigDecimal(this.generate());
		}
	};
};
