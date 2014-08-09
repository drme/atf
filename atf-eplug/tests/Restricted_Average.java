
public class Restricted_Average{
	
	/* Restricted Average */
	// rez >= min, rez <= max //
	public static double res_average(int []array, int min, int max)
	{
		double av = 0;
		int n = array.length;
		
		int sum = 0;
		int i = min;
		
		for(; i < max && i < n; i++)
			sum += array[i];
				
		if((max-min) > 0) av = (double)sum / (max-min);
		
		return av;									
	}

	public static double res_average1(int []array, int min, int max)
	{
		double av = 10;
		int n = array.length;
		
		int sum = 0;
		int i = min;
		
		for(; i < max && i < n; i++)
			sum += array[i];
				
		if((max-min) > 0) av = (double)sum / (max-min);
		
		return av;									
	}

	public static double res_average2(int []array, int min, int max)
	{
		double av = 0;
		int n = 3;
		
		int sum = 0;
		int i = min;
		
		for(; i < max && i < n; i++)
			sum += array[i];
				
		if((max-min) > 0) av = (double)sum / (max-min);
		
		return av;									
	}

	public static double res_average3(int []array, int min, int max)
	{
		double av = 0;
		int n = array.length;
		
		int sum = 10000;
		int i = min;
		
		for(; i < max && i < n; i++)
			sum += array[i];
				
		if((max-min) > 0) av = (double)sum / (max-min);
		
		return av;									
	}

	public static double res_average4(int []array, int min, int max)
	{
		double av = 0;
		int n = array.length;
		
		int sum = 0;
		int i = max;
		
		for(; i < max && i < n; i++)
			sum += array[i];
				
		if((max-min) > 0) av = (double)sum / (max-min);
		
		return av;									
	}

	public static double res_average5(int []array, int min, int max)
	{
		double av = 0;
		int n = array.length;
		
		int sum = 0;
		int i = 0;
		
		for(; i < max && i < n; i++)
			sum += array[i];
				
		if((max-min) > 0) av = (double)sum / (max-min);
		
		return av;									
	}


	public static double res_average6(int []array, int min, int max)
	{
		double av = 0;
		int n = array.length;
		
		int sum = 0;
		int i = min;
		
		for(; i < n; i++)
			sum += array[i];
				
		if((max-min) > 0) av = (double)sum / (max-min);
		
		return av;									
	}


	public static double res_average7(int []array, int min, int max)
	{
		double av = 0;
		int n = array.length;
		
		int sum = 0;
		int i = min;
		
		for(; i < max && i < n; i++)
			sum += array[i];
				
		
		return av;									
	}




	public static double res_average8(int []array, int min, int max)
	{
		double av = 0;
		int n = array.length;
		
		int sum = 0;
		int i = min;
		
		for(; i < max && i < n; i++)
			sum += array[i];
				
		if((max-min) > 0) av = (double)sum / (max-min);
		
		return sum;									
	}

	public static double res_average9(int []array, int min, int max)
	{
		double av = 0;
		int n = array.length;
		
		int sum = 0;
		int i = min;
		
		for(; i < max && i < n; i++)
			sum += array[i];
				
		if((max+min) > 0) av = (double)sum / (max+min);
		
		return av;									
	}

	public static double res_average10(int []array, int min, int max)
	{
		double av = 0;
		int n = array.length;
		
		int sum = 0;
		int i = min;
		
		for(; i < max && i < n; i++)
			sum += array[i];
				
		if((max-min) > 0) av = (double)sum * (max-min);
		
		return av;									
	}

	public static double res_average11(int []array, int min, int max)
	{
		double av = 0;
		int n = array.length;
		
		int sum = 0;
		int i = min;
		
		for(; i < max && i < n; i++)
			sum += array[i];
				
		if((max-min) > 0) av = (double)sum / (max-min);
		
		return max;									
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


	private static void test(int[] array, int min, int max)
	{
		try { kill(0, res_average1(array, min, max), array, min, max); } catch (Exception ex) { killed[0] = true; };
		try { kill(1, res_average2(array, min, max), array, min, max); } catch (Exception ex) { killed[2] = true; };
		try { kill(2, res_average3(array, min, max), array, min, max); } catch (Exception ex) { killed[3] = true; };
		try { kill(3, res_average4(array, min, max), array, min, max); } catch (Exception ex) { killed[3] = true; };
		try { kill(4, res_average5(array, min, max), array, min, max); } catch (Exception ex) { killed[4] = true; };
		try { kill(5, res_average6(array, min, max), array, min, max); } catch (Exception ex) { killed[5] = true; };
		try { kill(6, res_average7(array, min, max), array, min, max); } catch (Exception ex) { killed[6] = true; };
		try { kill(7, res_average8(array, min, max), array, min, max); } catch (Exception ex) { killed[7] = true; };
		try { kill(8, res_average9(array, min, max), array, min, max); } catch (Exception ex) { killed[8] = true; };
		try { kill(9, res_average10(array, min, max), array, min, max); } catch (Exception ex) { killed[9] = true; };
		try { kill(10, res_average11(array, min, max), array, min, max); } catch (Exception ex) { killed[10] = true; };
	};

	public static void kill(int mutant, double result, int[] array, int min, int max)
	{
		int[] arr = new int[max-min+1];

		for (int i = 0; i < max-min; i++)
		{
			arr[i] = array[min+i];
		}

	    rezType mm = MinMax.minMax(arr);

		if (!( (result >= mm.min) && (result <= mm.max) ))
		{
			killed[mutant] = true;
		}
	};

	public static void main(String[] args)
	{
		int maxTc = 100;

		for (int tc = 0; tc < maxTc; tc++)
		{
			for (int i = 0; i < mutants; i++)
			{
				killed[i] = false;
			}

			for (int i = 0; i < tc; i++)
			{
				int[] arr = randomArray();
				int min = (int)(Math.random() * arr.length);
				int max = 0;

				while (max < min)
				{
					max = (int)(Math.random() * arr.length);
				}

				test(arr, min, max);
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

	private static int mutants = 11;
	private static boolean[] killed = new boolean[11];

}
