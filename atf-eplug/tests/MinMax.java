
public class MinMax
{
	// min <= a[0];
	// max >= a[0];
	public static rezType minMax(int []array)
	{
		final rezType rez = new rezType();
		int n = array.length;
		
		rez.max = array[0];
		rez.min = array[0];
		
		int i = 0;
		
		while (i < n)
		{
			if(array[i] > rez.max) rez.max = array[i];
			if(array[i] < rez.min) rez.min = array[i];
			i++;			
		}		
		
		
		return rez;
	}

	public static rezType minMax1(int []array)
	{
		final rezType rez = new rezType();
		int n = array.length;
		
		rez.max = array[0];
		rez.min = array[0];
		
		int i = 1;
		
		while (i < n)
		{
			if(array[i] > rez.max) rez.max = array[i];
			if(array[i] < rez.min) rez.min = array[i];
			i++;			
		}		
		
		
		return rez;
	}

	public static rezType minMax2(int []array)
	{
		final rezType rez = new rezType();
		int n = array.length;
		
		rez.max = array[0];
		rez.min = array[0];
		
		int i = 0;
		
		while (i < n)
		{
			if(array[i] == rez.max) rez.max = array[i];
			if(array[i] == rez.min) rez.min = array[i];
			i++;			
		}		
		
		
		return rez;
	}

	public static rezType minMax3(int []array)
	{
		final rezType rez = new rezType();
		int n = 0;
		
		rez.max = array[0];
		rez.min = array[0];
		
		int i = 0;
		
		while (i < n)
		{
			if(array[i] > rez.max) rez.max = array[i];
			if(array[i] < rez.min) rez.min = array[i];
			i++;			
		}		
		
		
		return rez;
	}

	public static rezType minMax4(int []array)
	{
		final rezType rez = new rezType();
		int n = array.length;
		
		int i = 0;
		
		while (i < n)
		{
			if(array[i] > rez.max) rez.max = array[i];
			if(array[i] < rez.min) rez.min = array[i];
			i++;			
		}		
		
		
		return rez;
	}

	public static rezType minMax5(int []array)
	{
		final rezType rez = new rezType();
		int n = array.length;
		
		rez.max = array[0];
		rez.min = array[0];
		
		int i = 0;
		
		while (i < n)
		{
			if(array[i] < rez.max) rez.max = array[i];
			if(array[i] > rez.min) rez.min = array[i];
			i++;			
		}		
		
		
		return rez;
	}

	public static rezType minMax6(int []array)
	{
		final rezType rez = new rezType();
		int n = array.length;
		
		rez.max = array[0];
		rez.min = array[0];
		
		int i = 0;
		
		while (i < n)
		{
			if(array[i] < rez.max) rez.max = array[i];
			if(array[i] < rez.min) rez.min = array[i];
			i++;			
		}		
		
		
		return rez;
	}

	public static rezType minMax7(int []array)
	{
		final rezType rez = new rezType();
		int n = array.length;
		
		rez.max = array[0];
		rez.min = array[0];
		
		int i = 0;
		
		while (i < n)
		{
			if(array[i] > rez.max) rez.max = array[i];
			i++;			
		}		
		
		
		return rez;
	}

	public static rezType minMax8(int []array)
	{
		final rezType rez = new rezType();
		int n = array.length;
		
		rez.max = array[0];
		rez.min = array[0];
		
		int i = 0;
		
		while (i < n)
		{
			if(array[i] > rez.max) rez.max = array[i];
			i++;			
		}		
		
		
		return rez;
	}

	public static rezType minMax9(int []array)
	{
		final rezType rez = new rezType();
		int n = array.length;
		
		rez.max = array[0];
		rez.min = array[0];
		
		int i = 0;
		
		while (i > n)
		{
			if(array[i] > rez.max) rez.max = array[i];
			if(array[i] < rez.min) rez.min = array[i];
			i++;			
		}		
		
		
		return rez;
	}

	public static void test(int[] array, int exmin, int exmax, boolean testex)
	{
		if (array.length <= 0)
		{
			return;
		}
		
		kill(minMax1(array), array, 0, exmin, exmax, testex);
		kill(minMax2(array), array, 1, exmin, exmax, testex);
		kill(minMax3(array), array, 2, exmin, exmax, testex);
		kill(minMax4(array), array, 3, exmin, exmax, testex);
		kill(minMax5(array), array, 4, exmin, exmax, testex);
		kill(minMax6(array), array, 5, exmin, exmax, testex);
		kill(minMax7(array), array, 6, exmin, exmax, testex);
		kill(minMax8(array), array, 7, exmin, exmax, testex);
		kill(minMax9(array), array, 8, exmin, exmax, testex);
	};

	private static void kill(rezType r, int[] a, int n, int exmin, int exmax, boolean testex)
	{
		int a0 = a[(int)(Math.random() * (a.length - 1))];

		if (!(r.max >= a0))
		{
			killed[n] = true;
		}

		if (!(r.min <= a0))
		{
			killed[n] = true;
		}

		if (true == testex)
		{
			if (!(exmin == r.min))
			{
				killed[n] = true;
			}

			if (!(exmax == r.max))
			{
				killed[n] = true;
			}
		}
	};

	public static void main(String[] a)
	{
		for (int tc = 0; tc < 10; tc++)
	{

		for (int i = 0; i < 9; i++)
		{
			killed[i] = false;
		}

		for (int i = 1; i < tc; i++)
		{
			int[] lst = new int[(int)(Math.random() * 100)];

			for (int j = 0; j < lst.length; j++)
			{
				lst[j] = (int)(-100 + Math.random() * 200);
			}

			test(lst, 0, 0, false);
		}

	//	System.out.println("tc = " + tc);

			test(new int[] { 2, 10, 3, 4, -2, 0, 1 }, -2, 10, true);
			test(new int[] { -4, -3, -1 }, -4, -1, true);


int dead = 0;
		for (int i = 0; i < 9; i++)
		{
//			results[tc][i] = killed[i];
//			System.out.println("" + (i+1) + " " + killed[i]);
		if (killed[i])
		dead++;
		}

	System.out.println("tc = " + tc + " ==> " + dead);

		
	}
	};

	static boolean[] killed = new boolean[9];

//	static boolean[][] results = new boolean[8][100];
}


/*
 *  new data type for MinMax 
 */
class rezType 
{
		public int max = 0;
		public int min = 0;
		
		public rezType(){min = 0; max = 0;}			
}

