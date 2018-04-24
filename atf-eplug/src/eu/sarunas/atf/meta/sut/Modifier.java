package eu.sarunas.atf.meta.sut;

public enum Modifier
{
	None(0),
	Public(1),
	Abstract(2),
	Static(4);

	Modifier(int value)
	{
		this.value = value;
	};
	
	@Deprecated
	public int value = 0;
	

	
	
	
	@Deprecated
    public static boolean hasModifier(int value, int modifier)
    {
    	if ((value & modifier) > 0)
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    };

	public static String toString(int value)
	{
		String result = "";
		/*
		if (true == hasModifier(value, Abstract))
		{
			result += " abstract";
		}
*/
		if (true == hasModifier(value, FINAL))
		{
			result += " FINAL";
		}
		if (true == hasModifier(value, INTERFACE))
		{
			result += " INTERFACE";
		}
		if (true == hasModifier(value, NATIVE))
		{
			result += " NATIVE";
		}
		if (true == hasModifier(value, PRIVATE))
		{
			result += " PRIVATE";
		}
		if (true == hasModifier(value, PROTECTED))
		{
			result += " PROTECTED";
		}
		if (true == hasModifier(value, PUBLIC))
		{
			result += " PUBLIC";
		}
		if (true == hasModifier(value, STATIC))
		{
			result += " STATIC";
		}
		if (true == hasModifier(value, STRICT))
		{
			result += " STRICT";
		}
		if (true == hasModifier(value, SYNCHRONIZED))
		{
			result += " SYNCHRONIZED";
		}
		if (true == hasModifier(value, TRANSIENT))
		{
			result += " TRANSIENT";
		}
		if (true == hasModifier(value, VOLATILE))
		{
			result += " VOLATILE";
		}
		
		return result;
	};
	
	@Deprecated
	public static int FINAL = 2;
	@Deprecated
	public static int INTERFACE = 4;
	@Deprecated
	public static int NATIVE = 8;
	@Deprecated
	public static int PRIVATE = 16;
	@Deprecated
	public static int PROTECTED = 32;
	@Deprecated
	public static int PUBLIC = 64;
	@Deprecated
	public static int STATIC = 128;
	@Deprecated
	public static int STRICT = 256;
	@Deprecated
	public static int SYNCHRONIZED = 512;
	@Deprecated
	public static int TRANSIENT = 1024;
	@Deprecated
	public static int VOLATILE = 2048;
};
