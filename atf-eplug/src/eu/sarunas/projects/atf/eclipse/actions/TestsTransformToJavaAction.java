package eu.sarunas.projects.atf.eclipse.actions;

import java.io.StringBufferInputStream;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IJavaProject;
import eu.sarunas.atf.eclipse.actions.BaseAction;
import eu.sarunas.atf.eclipse.utils.ProjectManager;
import eu.sarunas.atf.meta.tests.TestCase;
import eu.sarunas.atf.meta.tests.TestProject;
import eu.sarunas.atf.meta.tests.TestSuite;

import eu.sarunas.projects.atf.db.DataBase;

@SuppressWarnings("deprecation")
public class TestsTransformToJavaAction extends BaseAction
{
	public TestsTransformToJavaAction()
	{
		super("Tranforming tests to JUnit");
	};
	
	protected void executeAction(IProgressMonitor monitor) throws Throwable
	{
		try
		{
			if (this.selectedItem instanceof IJavaProject)
			{
				IJavaProject project = (IJavaProject)this.selectedItem;
			
				TestProject tests = DataBase.getInstance().loadTests(project.getElementName());

				createTestFiles(project, tests, monitor);
			}
		}
		catch (Throwable ex)
		{
			ex.printStackTrace();
		}
	};
	
	private void createTestFiles(IJavaProject project, TestProject tests, IProgressMonitor monitor) throws CoreException
	{
/*		IPath projectPath = project.getProject().getFullPath();
		IPath srcPath = projectPath.append("tests");
	    IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
	    IFolder srcFolder = root.getFolder(srcPath);
	    ProjectManager.createFolderHelper(srcFolder,null);

	    int worked = 0;
	    
	    for (TestSuite ts : tests.getTestSuites())
	    {
	    	for (TestCase tc : ts.getTestCases())
	    	{
	    		monitor.worked(worked++);
	    		
	    		if ((tc.getPackageName() != null) && (tc.getPackageName().length() > 0))
	    		{
					IPath packagePath = srcPath.append(tc.getPackageName().replaceAll("[.]", "/"));
			    	IFolder packageFolder = root.getFolder(packagePath);
			    	ProjectManager.createFolderHelper(packageFolder, null);	    			
	    		}
	    		
	    		IPath indexPath = srcPath.append(tc.getPackageName().replaceAll("[.]", "/") + "/" + tc.getClassName() + "Test.xml");
				
				 IFile indexFile = root.getFile(indexPath);
				 
				 if (indexFile.exists())
				 {
					 indexFile.delete(true, true, null);
				 }
				 
				 eu.sarunas.atf.utils.Serializer s = new eu.sarunas.atf.utils.Serializer();
				 
				 StringBufferInputStream sb = new StringBufferInputStream(s.toString(ts));
				 
				 indexFile.create(sb, true, null);
				 
				 eu.sarunas.atf.generators.code.junit.TestTransformerJUnit ut = new eu.sarunas.atf.generators.code.junit.TestTransformerJUnit();
				 
				 eu.sarunas.atf.meta.sut.Class c = ut.transformTest(ts);
					 
				 eu.sarunas.atf.generators.code.java.CodeGeneratorJava cj = new eu.sarunas.atf.generators.code.java.CodeGeneratorJava();
					 
				 String code = cj.generateClass(c);
					 
				 IPath codeFilePath = srcPath.append(tc.getPackageName().replaceAll("[.]", "/") + "/" + tc.getClassName() + ".java");
						
				 IFile codeFileFile = root.getFile(codeFilePath);
					 
				 if (codeFileFile.exists())
				 {
					 codeFileFile.delete(true, true, null);
				 }
					 
				 StringBufferInputStream sbCode = new StringBufferInputStream(code);
					 
				codeFileFile.create(sbCode, true, null);
	    	}
	    }*/
	};
};
