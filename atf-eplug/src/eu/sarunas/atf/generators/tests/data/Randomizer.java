package eu.sarunas.atf.generators.tests.data;

import java.util.HashMap;
import eu.sarunas.atf.meta.sut.basictypes.BooleanType;
import eu.sarunas.atf.meta.sut.basictypes.ByteType;
import eu.sarunas.atf.meta.sut.basictypes.CharType;
import eu.sarunas.atf.meta.sut.basictypes.CollectionType;
import eu.sarunas.atf.meta.sut.basictypes.DateType;
import eu.sarunas.atf.meta.sut.basictypes.DoubleType;
import eu.sarunas.atf.meta.sut.basictypes.FloatType;
import eu.sarunas.atf.meta.sut.basictypes.IntegerType;
import eu.sarunas.atf.meta.sut.basictypes.LargeNumberType;
import eu.sarunas.atf.meta.sut.basictypes.LongType;
import eu.sarunas.atf.meta.sut.basictypes.ObjectType;
import eu.sarunas.atf.meta.sut.basictypes.ShortType;
import eu.sarunas.atf.meta.sut.basictypes.StringType;
import eu.sarunas.atf.model.checker.ITestDataValidator;
import eu.sarunas.projects.atf.metadata.generic.Type;

/**
 * Builds a random value for a selected type.
 */
public class Randomizer
{
	public Randomizer()
	{
		this.generators.put(DoubleType.class, new DoubleGenerator(this));
		this.generators.put(FloatType.class, new FloatGenerator(this));
		this.generators.put(IntegerType.class, new IntegerGenerator(this));
		this.generators.put(LongType.class, new LongGenerator(this));
		this.generators.put(ShortType.class, new ShortGenerator(this));
		this.generators.put(ByteType.class, new ByteGenerator(this));
		this.generators.put(CharType.class, new CharGenerator(this));
		this.generators.put(BooleanType.class, new BooleanGenerator(this));
		this.generators.put(StringType.class, new StringGenerator(this));
		this.generators.put(ObjectType.class, new ObjectGenerator(this));
		this.generators.put(CollectionType.class, new CollectionGenerator(this));
		this.generators.put(LargeNumberType.class, new LargeNumberGenerator(this));
		this.generators.put(DateType.class, new DateGenerator(this));
	};

	public Object getRandomValue(Type type, ITestDataValidator validator)
	{
		ITypeGenerator generator = this.generators.get(type.getClass());

		if (null != generator)
		{
			return generator.generate(type, validator);
		}

		if (type instanceof eu.sarunas.atf.meta.sut.Enum)
		{
			return this.enumGenerator.generate(type, validator);
		}
		
		if (type instanceof eu.sarunas.atf.meta.sut.Class)
		{
			return this.compositeGenerator.generate(type, validator);
		}

		return null;
	};

	public IDistribution getDistribution()
	{
		return this.distribution;
	};

	private HashMap<Class<? extends Type>, ITypeGenerator> generators = new HashMap<Class<? extends Type>, ITypeGenerator>();
	private IDistribution distribution = new GaussDistribution();
	private CompositeGenerator compositeGenerator = new CompositeGenerator(this);
	private EnumGenerator enumGenerator = new EnumGenerator(this);
};
