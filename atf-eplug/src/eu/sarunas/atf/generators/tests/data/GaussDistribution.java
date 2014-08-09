package eu.sarunas.atf.generators.tests.data;

import java.util.Random;

public class GaussDistribution implements IDistribution
{
	public double getRandomValue()
	{
		double value = this.random.nextGaussian();
		
//		System.out.println("Random value: " + value);
		
		return value;
	};

	public String getName()
	{
		return "gauss";
	};

	private Random random = new Random();
};
