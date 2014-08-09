
public class Average 
{
	/*	Average	*/
	// rez >= min, rez <= max //
	public static double average(int []array)
	{
		double av = 0;
		int n = array.length;
		
		int sum = 0;
		int i = 0;
		
		for(; i < n; i++)
			sum += array[i];
				
		if(i > 0) av = (double)sum / i;
		
		return av;									
	}

	public static double average1(int []array)
	{
		double av = 134;
		int n = array.length;
		
		int sum = 0;
		int i = 0;
		
		for(; i < n; i++)
			sum += array[i];
				
		if(i > 0) av = (double)sum / i;
		
		return av;									
	}

	public static double average2(int []array)
	{
		double av = 0;
		int n = 4;
		
		int sum = 0;
		int i = 0;
		
		for(; i < n; i++)
			sum += array[i];
				
		if(i > 0) av = (double)sum / i;
		
		return av;									
	}

	public static double average3(int []array)
	{
		double av = 0;
		int n = array.length;
		
		int sum = 10000;
		int i = 0;
		
		for(; i < n; i++)
			sum += array[i];
				
		if(i > 0) av = (double)sum / i;
		
		return av;									
	}


	public static double average4(int []array)
	{
		double av = 0;
		int n = array.length;
		
		int sum = 0;
		int i = 1230;
		
		for(; i < n; i++)
			sum += array[i];
				
		if(i > 0) av = (double)sum / i;
		
		return av;									
	}
	                            
	public static double average5(int []array)
	{
		double av = 0;
		int n = array.length;
		
		int sum = 0;
		int i = 0;
		
		for(; i < n; i++)
			sum -= array[i];
				
		if(i > 0) av = (double)sum / i;
		
		return av;									
	}



	public static double average6(int []array)
	{
		double av = 0;
		int n = array.length;
		
		int sum = 0;
		int i = 0;
		
		for(; i < n; i++)
			sum += array[i];
				
		if(i > 0) av = (double)sum;
		
		return av;									
	}

	public static double average7(int []array)
	{
		double av = 0;
		int n = array.length;
		
		int sum = 0;
		int i = 0;
		
		for(; i < n; i++)
			sum += array[i];
				
		
		return av;									
	}

	public static double average8(int []array)
	{
		double av = 0;
		int n = array.length;
		
		int sum = 0;
		int i = 0;
		
		for(; i < n; i++)
			sum += array[i];
				
		if(i < 0) av = (double)sum / i;
		
		return av;									
	}

	public static double average9(int []array)
	{
		double av = 0;
		int n = array.length;
		
		int sum = 0;
		int i = 0;
		
		for(; i < n; i++)
			sum += array[i];
				
		if(i > 0) av = (double)sum / i;
		
		return sum;									
	}







	private static int[] randomArray()
	{
		int[] lst = new int[(int)(Math.random() * 100)];

		for (int j = 0; j < lst.length; j++)
		{
			lst[j] = (int)(-100 + Math.random() * 200);
		}

		return lst;
	};


	private static void test(int[] array)
	{
		try { kill(0, average1(array), array); } catch (Exception ex) { killed[0] = true; };
		try { kill(1, average2(array), array); } catch (Exception ex) { killed[1] = true; };
		try { kill(2, average3(array), array); } catch (Exception ex) { killed[2] = true; };
		try { kill(3, average4(array), array); } catch (Exception ex) { killed[3] = true; };
		try { kill(4, average5(array), array); } catch (Exception ex) { killed[4] = true; };
		try { kill(5, average6(array), array); } catch (Exception ex) { killed[5] = true; };
		try { kill(6, average7(array), array); } catch (Exception ex) { killed[6] = true; };
		try { kill(7, average8(array), array); } catch (Exception ex) { killed[7] = true; };
		try { kill(8, average9(array), array); } catch (Exception ex) { killed[8] = true; };
	};

	public static void kill(int mutant, double result, int[] array)
	{
	    rezType mm = MinMax.minMax(array);

		if (!( (result >= mm.min) && (result <= mm.max) ))
		{
			killed[mutant] = true;
		}
	};

	public static void main(String[] args)
	{
		int maxTc = 10;

		for (int tc = 0; tc < maxTc; tc++)
		{
			for (int i = 0; i < mutants; i++)
			{
				killed[i] = false;
			}

			for (int i = 0; i < tc; i++)
			{
				test(randomArray());
			}

			int dead = 0;

			for (int i = 0; i < mutants; i++)
			{
				if (true == killed[i])
				{
					dead++;
				}
			}

			System.out.println("tc = " + tc + " ==> " + dead + "/" + mutants);
		}
	};

	private static int mutants = 9;
	private static boolean[] killed = new boolean[9];

}
