package eu.sarunas.projects.atf.eclipse.actions;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IJavaProject;

import eu.sarunas.atf.eclipse.actions.BaseAction;
import eu.sarunas.projects.atf.db.DataBase;

public class TestsRemoveAction extends BaseAction
{
	public TestsRemoveAction()
	{
		super("Removing all tests");
	};

	protected void executeAction(IProgressMonitor monitor) throws Throwable
	{	
		if (this.selectedItem instanceof IJavaProject)
		{
			IJavaProject project = (IJavaProject)this.selectedItem;
			
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			IPath projectPath = project.getProject().getFullPath();
			IPath srcPath = projectPath.append("tests");
			IFolder srcFolder = root.getFolder(srcPath);
				
			if (true == srcFolder.exists())
			{
				srcFolder.delete(true, null);
			}
				
			DataBase.getInstance().removeTests(project.getElementName());
		}
	};
};
