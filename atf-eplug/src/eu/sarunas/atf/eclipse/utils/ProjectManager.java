package eu.sarunas.atf.eclipse.utils;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IJavaProject;

public class ProjectManager
{
	public static void createFolderHelper(IFolder folder, IProgressMonitor monitor) throws CoreException
	{
		if (false == folder.exists())
		{
			final IContainer parent = folder.getParent();
			
			if (parent instanceof IFolder && (false == ((IFolder)parent).exists()))
			{
				createFolderHelper((IFolder) parent, monitor);
			}
	
			folder.create(false, true, monitor);
		}
	};
	
	public static String getConstraints(IJavaProject project)
	{
		BufferedReader reader = null;

		try
		{
			// TODO: search in buildpath
			IFolder sourceFolder = project.getProject().getFolder("src");
			IFile file = sourceFolder.getFile("model.ocl");

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
			close(reader);
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
