package eu.sarunas.atf.generators.tests.data;

import eu.sarunas.atf.meta.sut.basictypes.CollectionType;
import eu.sarunas.atf.meta.testdata.TestObjectCollection;
import eu.sarunas.projects.atf.metadata.generic.Type;

public class CollectionGenerator extends ITypeGenerator
{
	public CollectionGenerator(Randomizer randomizer)
	{
		super(randomizer);
	};

	public Object generate(Type type)
	{
		CollectionType collectionType = (CollectionType) type;

		int length = (int) ((float) Math.abs(this.maxLength * this.randomizer.getDistribution().getRandomValue()));

		TestObjectCollection array = new TestObjectCollection(null, type, length, collectionType.getStyle());

		for (int i = 0; i < length; i++)
		{
			array.addElement(this.randomizer.getRandomValue(collectionType.getEnclosingType()));
		}

		return array;
	};

	private int maxLength = 2;
};
