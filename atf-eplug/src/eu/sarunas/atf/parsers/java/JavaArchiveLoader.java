package eu.sarunas.atf.parsers.java;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JavaArchiveLoader extends ClassLoader
{
	public JavaArchiveLoader(String fileName) throws IOException
	{
		this.jarFile = new JarFile(fileName);
	};

	public List<String> getClasses()
	{
		List<String> classes = new ArrayList<String>();

		Enumeration<JarEntry> list = this.jarFile.entries();

		while (list.hasMoreElements())
		{
			JarEntry e = list.nextElement();

			String name = e.getName();

			if (name.endsWith(classExtension))
			{
				String className = name.substring(0, name.length() - classExtension.length()).replace("/", ".");

				classes.add(className);
			}
		}

		return classes;
	};

	private byte[] getClassBytes(String className)
	{
		try
		{
			String name = className.replace(".", "/") + ".class";

			JarEntry e = this.jarFile.getJarEntry(name);

			InputStream s = this.jarFile.getInputStream(e);

			byte[] data = new byte[(int) e.getSize()];

			s.read(data);

			s.close();

			return data;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();

			return null;
		}
	};

	public Class<?> loadClass(String className) throws ClassNotFoundException, ClassFormatError
	{
		try
		{
			Class<?> sysClass = findSystemClass(className);

			if (null != sysClass)
			{
				return sysClass;
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

		Class<?> cc = loadedClasses.get(className);

		if (null != cc)
		{
			return cc;
		}

		byte[] bytes = getClassBytes(className);

		if (null == bytes)
		{
			throw new ClassNotFoundException();
		}

		Class<?> c = defineClass(className, bytes, 0, bytes.length);

		if (null == c)
		{
			throw new ClassFormatError();
		}

		loadedClasses.put(className, c);

		return c;
	};

	private JarFile jarFile = null;
	private static final String classExtension = ".class";
	private static HashMap<String, Class<?>> loadedClasses = new HashMap<String, Class<?>>();
};
