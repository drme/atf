package eu.sarunas.atf.meta.sut;

public enum Modifier
{
	Public(1),
	None(0);

	Modifier(int value)
	{
		this.value = value;
	};
	
	public boolean isPublic()
	{
		return (this.value & Public.value) > 0;
	};
	
	public int value = 0;
	

	
	
	
	
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
		
		if (true == hasModifier(value, ABSTRACT))
		{
			result += " abstract";
		}

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
	
	public static int ABSTRACT = 1;
	public static int FINAL = 2;
	public static int INTERFACE = 4;
	public static int NATIVE = 8;
	public static int PRIVATE = 16;
	public static int PROTECTED = 32;
	public static int PUBLIC = 64;
	public static int STATIC = 128;
	public static int STRICT = 256;
	public static int SYNCHRONIZED = 512;
	public static int TRANSIENT = 1024;
	public static int VOLATILE = 2048;
};
