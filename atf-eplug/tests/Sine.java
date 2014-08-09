public class Sine
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
		if (x < -Math.PI)
		{
			while (x < -Math.PI)
			{
				x += Math.PI * 2;
			}
		}

		if (x > Math.PI)
		{
			while (x > Math.PI)
			{
				x -= Math.PI * 2;
			}
		}

		int n = 7;
		double result = x;

		for (int i = 1; i <= n; i++)
		{
			if (i % 2 == 0)
			{
				double a = Math.pow(x, 2 * i + 1);
				double b = fact(2 * i + 1);
				double e = a / b;
				result = result + e;
			}
			else
			{
				double a = Math.pow(x, 2 * i + 1);
				double b = fact(2 * i + 1);
				double e = a / b;
				result = result - e;
			}
		}

		return (float)result;
	};

	public static void main(String[] args)
	{
		Sine s = new Sine();

		System.out.println("" + Math.sin(100.0f/*Math.PI/2.0f*/));
		System.out.println("" + s.getValue(100.0f/*(float)Math.PI/2.0f*/));
	};
};
