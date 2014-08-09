package eu.sarunas.atf.generators.tests.data;

public abstract class NumberGenerator extends ITypeGenerator
{
	protected NumberGenerator(Randomizer randomizer, double min, double max)
	{
		super(randomizer);

		this.min = min;
		this.max = max;
	};

	protected double generate()
	{
		double value = this.min + (this.max - this.min) * this.randomizer.getDistribution().getRandomValue();

		return value;
	};

	private double min;
	private double max;
};
