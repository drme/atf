public class Cosine
{
	public long fact(long n)
	{
		if (n > 1)
		{
			return n * fact(n - 1);
		}
		else
		{
			return n;
		}
	};

	public float getValue(float x)
	{
		while (x < -Math.PI)
		{
			x += Math.PI * 2;
		}

		while (x > Math.PI)
		{
			x -= Math.PI * 2;
		}

		int n = 10;
		double result = 1;

		for (int i = 1; i <= n; i++)
		{
			double a = Math.pow(x, 2 * i);
			double b = fact(2 * i);
			double e = a / b;

			if (i % 2 == 0)
			{
				result = result + e;
			}
			else
			{
				result = result - e;
			}
		}

		return (float)result;
	};

	public static void main(String[] args)
	{
		Cosine s = new Cosine();

		System.out.println("" + Math.cos(Math.PI/2.0f));
		System.out.println("" + s.getValue((float)Math.PI/2.0f));



		System.out.println("" + Math.cos(0));
		System.out.println("" + s.getValue(0.0f));

		System.out.println("" + Math.cos(Math.PI));
		System.out.println("" + s.getValue((float)Math.PI));

	};
};
