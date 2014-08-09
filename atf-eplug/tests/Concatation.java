
public class Concatation
{
	// post: rez.length = a.length + b.length
	public static String concat(String a, String b )
	{
		int l1 = a.length();
		int l2 = b.length();
		
		char rez[] = new char[l1+l2];
		
		for(int i = 0; i < l1; i++)
			rez[i] = a.charAt(i);
		
		for(int i = 0; i < l2; i++)
			rez[l1 + i] = b.charAt(i);
		
		
		return new String(rez);
		
	}

	public static String concat1(String a, String b )
	{
		int l1 = 10;
		int l2 = b.length();
		
		char rez[] = new char[l1+l2];
		
		for(int i = 0; i < l1; i++)
			rez[i] = a.charAt(i);
		
		for(int i = 0; i < l2; i++)
			rez[l1 + i] = b.charAt(i);
		
		
		return new String(rez);
		
	}

	public static String concat2(String a, String b )
	{
		int l1 = a.length();
 		int l2 = 1;
		
		char rez[] = new char[l1+l2];
		
		for(int i = 0; i < l1; i++)
			rez[i] = a.charAt(i);
		
		for(int i = 0; i < l2; i++)
			rez[l1 + i] = b.charAt(i);
		
		
		return new String(rez);
		
	}

	public static String concat3(String a, String b )
	{
		int l1 = 0;
		int l2 = 0;
		
		char rez[] = new char[l1+l2];
		
		for(int i = 0; i < l1; i++)
			rez[i] = a.charAt(i);
		
		for(int i = 0; i < l2; i++)
			rez[l1 + i] = b.charAt(i);
		
		
		return new String(rez);
		
	}

	public static String concat4(String a, String b )
	{
		int l1 = a.length();
		int l2 = b.length();
		
		char rez[] = new char[l1-l2];
		
		for(int i = 0; i < l1; i++)
			rez[i] = a.charAt(i);
		
		for(int i = 0; i < l2; i++)
			rez[l1 + i] = b.charAt(i);
		
		
		return new String(rez);
		
	}

	public static String concat5(String a, String b )
	{
		int l1 = a.length();
		int l2 = b.length();
		
		char rez[] = new char[l1+l2];
		
		for(int i = 0; i < l2; i++)
			rez[i] = a.charAt(i);
		
		for(int i = 0; i < l2; i++)
			rez[l1 + i] = b.charAt(i);
		
		
		return new String(rez);
		
	}


	public static String concat6(String a, String b )
	{
		int l1 = a.length();
		int l2 = b.length();
		
		char rez[] = new char[l1+l2];
		
		for(int i = 0; i < l1; i++)
			rez[i] = a.charAt(i);
		
		for(int i = 0; i < l2; i++)
			rez[i] = b.charAt(i);
		
		
		return new String(rez);
		
	}


	public static String concat7(String a, String b )
	{
		int l1 = a.length();
		int l2 = b.length();
		
		char rez[] = new char[l1+l2];
		
		for(int i = 0; i < l1; i++)
			rez[i] = a.charAt(i);
		
		for(int i = 0; i < l2; i++)
			rez[l1 + i] = b.charAt(i);
		
		
		return a;
		
	}

	public static String concat8(String a, String b )
	{
		int l1 = a.length();
		int l2 = b.length();
		
		char rez[] = new char[l1+l2];
		
		for(int i = 0; i < l1; i++)
			rez[i] = a.charAt(i);
		
		for(int i = 0; i < l2; i++)
			rez[l1 + i] = b.charAt(i);
		
		
		return b;
		
	}

	public static String concat9(String a, String b )
	{
		int l1 = a.length();
		int l2 = b.length();
		
		char rez[] = new char[l1+l2];
		
		for(int i = 0; i < l2; i++)
			rez[l1 + i] = b.charAt(i);
		
		
		return new String(rez);
		
	}

	public static String concat10(String a, String b )
	{
		int l1 = a.length();
		int l2 = b.length();
		
		char rez[] = new char[l1+l2];
		
		for(int i = 0; i < l1; i++)
			rez[i] = a.charAt(i);
		
		
		
		return new String(rez);
		
	}


	public static String concat11(String a, String b )
	{
		int l1 = a.length();
		int l2 = b.length();
		
		char rez[] = null;//new char[l1+l2];
		
		for(int i = 0; i < l1; i++)
			rez[i] = a.charAt(i);
		
		for(int i = 0; i < l2; i++)
			rez[l1 + i] = b.charAt(i);
		
		
		return new String(rez);
		
	}
















	private static String randomString()
	{
		int len = (int)(Math.random() * 1000);

		String rez = "";

		for (int i = 0; i < len; i++)
		{
			rez += (char)(Math.random() * 256);
		}

		return rez;
	};

	private static void test(String a, String b)
	{
		try { kill(0, concat1(a, b), a, b); } catch (Exception ex) { killed[0] = true; };
		try { kill(1, concat2(a, b), a, b); } catch (Exception ex) { killed[1] = true; };
		try { kill(2, concat3(a, b), a, b); } catch (Exception ex) { killed[2] = true; };
		try { kill(3, concat4(a, b), a, b); } catch (Exception ex) { killed[3] = true; };
		try { kill(4, concat5(a, b), a, b); } catch (Exception ex) { killed[4] = true; };
		try { kill(5, concat6(a, b), a, b); } catch (Exception ex) { killed[5] = true; };
		try { kill(6, concat7(a, b), a, b); } catch (Exception ex) { killed[6] = true; };
		try { kill(7, concat8(a, b), a, b); } catch (Exception ex) { killed[7] = true; };
		try { kill(8, concat9(a, b), a, b); } catch (Exception ex) { killed[8] = true; };
		try { kill(9, concat10(a, b), a, b); } catch (Exception ex) { killed[9] = true; };
		try { kill(10, concat11(a, b), a, b); } catch (Exception ex) { killed[10] = true; };
	};

	public static void kill(int mutant, String result, String a, String b)
	{
		if (!(a.length() + b.length() == result.length()))
		{
			killed[mutant] = true;
		}
	};

	public static void main(String[] args)
	{
		int maxTc = 1000;

		for (int tc = 0; tc < maxTc; tc++)
		{
			for (int i = 0; i < mutants; i++)
			{
				killed[i] = false;
			}

			for (int i = 0; i < tc; i++)
			{
				String a = randomString();
				String b = randomString();

				test(a, b);
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
