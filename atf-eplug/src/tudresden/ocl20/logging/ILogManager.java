package tudresden.ocl20.logging;

import org.apache.log4j.Logger;

public abstract interface ILogManager
{
  public static final String DEFAULT_CONFIG_FILE_NAME = "log4j.properties";

  public abstract Logger getLogger(String paramString);

  public abstract Logger getLogger(Class<?> paramClass);

  public abstract Logger getRootLogger();

  public abstract void dispose();
}
