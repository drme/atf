package eu.sarunas.atf.utils;

import java.io.File;

public class FileUtils
{
	public static void deleteFile(File file)
	{
		try
		{
			if (null != file)
			{
				file.delete();
			}
		}
		catch (Throwable ex)
		{
			Logger.log(ex);
		}
	};
};
