package lt.atgplugin.utils;

public class Constants {
	/**
	 * Name used for created class objects.
	 */
	public static String classObjectName = "test";
	/**
	 * Default depth how deeply to search for parameters.
	 */
	public static int depth = 3;
	/**
	 * Postfix for generated test classes.
	 */
	public static String testClassPostfix = "Test";

	/**
	 * Prefix for generated method name.
	 */
	public static String testMethodPrefix = "test";

	/**
	 * Flag if save results to different root folder.
	 */
	public static boolean differentFolder = Boolean.TRUE;

	/**
	 * Folder name where to save if differentFolder flag is set.
	 */
	public static String folderName = "tests";

	/**
	 * Error message when no constructors can be found.
	 */
	public static String errorNoConstructor = "No proper construcor can be found";

	/**
	 * Holds flags if to check classes hierarchy.
	 */
	public static boolean searchParents = false;

	/**
	 * How deep to search for hierarchies.
	 */
	public static int parentSearchLevel = 3;

	/**
	 * Flag if show performance results.
	 */
	public static boolean showTimes = false;
	
	public static boolean useCache = true;
}
