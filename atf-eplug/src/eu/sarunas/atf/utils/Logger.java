package eu.sarunas.atf.utils;

public class Logger
{
	public static void log(Exception ex)
	{
		System.err.println(ex.getMessage());
		ex.printStackTrace(System.err);
	};
	
	public static final java.util.logging.Logger logger = java.util.logging.Logger.getGlobal(); 
};
