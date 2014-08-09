
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int array[] = {1, 4, 9, 5};		// any size

		/*		MinMax of array		*/
		
		Object obj = (Object)MinMax.minMax(array);	
		rezType ans = (rezType)(obj);
		System.err.println(ans.min + " " + ans.max);
		
		/*		Average of array	*/
		System.err.println(Average.average(array));
		
		/*		Restricted average of array	*/
		System.err.println(Restricted_Average.res_average(array, 0, 2));
		
		/*     Concatation of "pele" and "laidas"	*/
		System.err.println(Concatation.concat("pele","laidas"));
		
		/*    Total sum of array elements	*/
		System.err.println(Total.total(array));
		
		
	}
}
