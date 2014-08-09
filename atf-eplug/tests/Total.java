
public class Total {
	// res >= min & res >=max || res <= min && res <= max
	public static int total(int []array)
	{
		int ttl = 0;
		
		int n = array.length;
		
		for (int i=0; i < n; i++)
			ttl += array[i];
		
		return ttl;
	}
}
