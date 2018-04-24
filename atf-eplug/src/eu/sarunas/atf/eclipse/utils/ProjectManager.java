package eu.sarunas.atf.eclipse.utils;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IJavaProject;

/**
 * Utilities class for working with eclipse project.
 */
public class ProjectManager
{
	public static void createFolderHelper(IFolder folder, IProgressMonitor monitor) throws CoreException
	{
		if (false == folder.exists())
		{
			final IContainer parent = folder.getParent();

			if ((parent instanceof IFolder) && (false == ((IFolder) parent).exists()))
			{
				ProjectManager.createFolderHelper((IFolder) parent, monitor);
			}

			folder.create(false, true, monitor);
		}
	};

	public static List<String> getConstraints(IJavaProject project)
	{
		List<IFile> files = new ArrayList<IFile>();
		List<String> result = new ArrayList<String>();

		try
		{
			ProjectManager.getConstraints(project.getProject(), files);
		}
		catch (CoreException e)
		{
			e.printStackTrace();
		}

		for (IFile file : files)
		{
			String code = ProjectManager.readToString(file).trim();

			if (code.length() > 0)
			{
				result.add(code);
			}
		}

		return result;
	};

	public static List<String> getConstraintsFiles(IJavaProject project)
	{
		List<IFile> files = new ArrayList<IFile>();
		List<String> result = new ArrayList<String>();

		try
		{
			ProjectManager.getConstraints(project.getProject(), files);
		}
		catch (CoreException e)
		{
			e.printStackTrace();
		}

		for (IFile file : files)
		{
			result.add(file.getFullPath().toString().substring(project.getPath().toString().length()).substring(4));
		}

		return result;
	};	

	
	
	
	
	private static void getConstraints(IContainer container, List<IFile> result) throws CoreException
	{
		for (IResource member : container.members())
		{
			if (member instanceof IContainer)
			{
				ProjectManager.getConstraints((IContainer) member, result);
			}
			else if (member instanceof IFile)
			{
				IFile file = (IFile) member;

				if (false == file.isDerived())
				{
					if (file.getFileExtension().toLowerCase().equals("ocl"))
					{
						result.add(file);
					}
				}
			}
		}
	};

	private static String readToString(IFile file)
	{
		BufferedReader reader = null;

		try
		{
			StringBuilder result = new StringBuilder();

			reader = new BufferedReader(new InputStreamReader(file.getContents()));

			String line = reader.readLine();

			while (line != null)
			{
				result.append(line);
				result.append("\n");

				line = reader.readLine();
			}

			return result.toString();
		}
		catch (Throwable ex)
		{
			ex.printStackTrace();

			return "";
		}
		finally
		{
			ProjectManager.close(reader);
		}
	};

	private static void close(Closeable stream)
	{
		if (null != stream)
		{
			try
			{
				stream.close();
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}
	};
};
