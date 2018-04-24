package eu.sarunas.atf.eclipse.generators;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import eu.sarunas.atf.eclipse.utils.ProjectManager;

/**
 * Creates tests package and stores all JUnit test code.
 */
public class EclipseTestsWriter
{
	/**
	 * Adds tests folder to Java project, adds it to the build path. If folder already exists and is included in the build path, no actions are taken.
	 * 
	 * @param name tests folder name, usually "tests".
	 * @param javaProject java project to add tests folder to.
	 * @param monitor reports task progress.
	 * @return tests path object.
	 * @throws CoreException
	 */
	public IPath createTestsFolder(String name, IJavaProject javaProject, IProgressMonitor monitor) throws CoreException
	{
		IClasspathEntry[] entries = javaProject.getRawClasspath();

		IPath testsDirectory = javaProject.getPath().append(name);

		for (IClasspathEntry entry : entries)
		{
			if (true == entry.getPath().equals(testsDirectory))
			{
				return entry.getPath();
			}
		}

		IClasspathEntry[] newEntries = new IClasspathEntry[entries.length + 1];
		System.arraycopy(entries, 0, newEntries, 0, entries.length);

		IClasspathEntry srcEntry = JavaCore.newSourceEntry(testsDirectory, null);

		newEntries[entries.length] = JavaCore.newSourceEntry(srcEntry.getPath());
		javaProject.setRawClasspath(newEntries, null);

		String[] paths = { name };

		addToProjectStructure(javaProject.getProject(), paths, monitor);

		return testsDirectory;
	};

	/**
	 * Adds tests file with generated JUnit tests code, includes it into tests folder. If file already exists, its contents are overwritten.
	 * 
	 * @param fileName tests file name.
	 * @param code file contents as a source code string.
	 */
	public void createTestsFile(String className, String code, IJavaProject javaProject, IPath testsFolder, IProgressMonitor monitor)
	{
		String[] packages = className.split("[.]");
		
		String name = packages[packages.length - 1];
		String _package = "";
		
		if (packages.length > 1)
		{
			_package = packages[0];
			
		for (int i = 1; i < packages.length - 1; i++)
		{
			_package += "." + packages[i];
		}
		}
		
		//Now supports up to 1 level package
		try {
			for (IJavaElement element : javaProject.getChildren()) {
				if(element.getElementName().equals(testsFolder.lastSegment())){
					IPackageFragmentRoot pfr = (IPackageFragmentRoot) element;
					
					
					
					
					IPackageFragment packageFragment = pfr.getPackageFragment(_package);
					
		//			if (null == packageFragment)
	//				{
//						packageFragment = pfr.createPackageFragment(_package, true, monitor);
					//}
					
		
					addToProjectStructure(javaProject.getProject(), new String[] { testsFolder.lastSegment() + "/" + _package.replaceAll("[.]", "/")  }, monitor);
					
					
					
					//packageFragment.c
					
					packageFragment.createCompilationUnit(name + ".java", code, true, monitor);
					
					/*pfr.createPackageFragment(arg0, arg1, arg2)
					
					for (IJavaElement elemenetLevel2 : pfr.getChildren()) {
						IPackageFragment pf = (IPackageFragment) elemenetLevel2;
						if(_package == null || _package.length() == 0){
							if(pf.getElementName() == null || pf.getElementName().length() == 0){
								pf.createCompilationUnit(name, code, true, monitor);
								break;
							}
						}else{
							if(pf.getElementName().equals(_package)){
								pf.createCompilationUnit(name + ".java", code, true, monitor);
								break;
							}
						}
					}
					break;*/
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
	}		
		
		
	};

	// TODO: make private
	public void addToProjectStructure(IProject project, String[] paths, IProgressMonitor monitor) throws CoreException
	{
		for (String path : paths)
		{
			IFolder folders = project.getFolder(path);

			//createFolder(folders, project, 1, monitor);
			
			ProjectManager.createFolderHelper(folders, monitor);
		}
	};

	private void createFolder(IFolder folder, IProject project, int depth, IProgressMonitor monitor) throws CoreException
	{
		IContainer parent = folder.getParent();

		if (parent instanceof IFolder)
		{
			createFolder((IFolder) parent, project, ++depth, monitor);
		}

		if (false == folder.exists())
		{
			folder.create(false, true, monitor);

			project.refreshLocal(depth, monitor);
		}
	};
};
