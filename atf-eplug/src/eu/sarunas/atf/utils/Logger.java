package eu.sarunas.atf.utils;

import java.util.logging.Level;

public class Logger
{
	public static void log(Throwable ex)
	{
		logger.log(Level.SEVERE, ex.getMessage(), ex);
	};
	
	public static final java.util.logging.Logger logger = java.util.logging.Logger.getGlobal(); 
};
