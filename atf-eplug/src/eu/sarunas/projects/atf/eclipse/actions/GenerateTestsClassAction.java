package eu.sarunas.projects.atf.eclipse.actions;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jface.dialogs.MessageDialog;
import eu.sarunas.atf.eclipse.actions.BaseAction;
import eu.sarunas.atf.eclipse.parsers.JavaProjectParser;
import eu.sarunas.atf.meta.tests.TestProject;
import eu.sarunas.atf.meta.tests.TestSuite;

import eu.sarunas.projects.atf.db.DataBase;

public class GenerateTestsClassAction extends BaseAction
{
	public GenerateTestsClassAction()
	{
		super("Generating tests for a class");
	};
	
	protected void executeAction(IProgressMonitor monitor) throws Throwable
	{
		if (this.selectedItem instanceof IType)
		{
	/*		IType type = (IType)this.selectedItem;
			
			IJavaProject project = type.getParent().getJavaProject();
			
			TestProject testProject = new TestProject();
			testProject.setName(project.getElementName());
				
			IClass cl = JavaProjectParser.getInstance().transformClass(type);

			this.methods = cl.getMethods().size();
			
			eu.sarunas.projects.atf.generators.TestsGenerator tg = new eu.sarunas.projects.atf.generators.TestsGenerator();
								
			if (null != cl)
			{
				eu.sarunas.projects.atf.tests.TestSuite ts = tg.generate(cl, testProject);
									
				testProject.getTestSuites().add(ts);
			}
				
			DataBase.getInstance().saveTests(testProject);
			
			this.generatedTests = 0;
			
			for (TestSuite ts : testProject.getTestSuites())
			{
				this.generatedTests += ts.getTestCases().size();
			}
			
			this.generatedTests = testProject.getTestSuites().size();*/
		}
	};
	
	protected void onDone()
	{
		String message = "Generated tests: " + this.generatedTests; 
		message += "\r\nfor Classes: " + this.classes;
		message += "\r\nfor Methods: " + this.methods;
		
		MessageDialog.openInformation(null, this.taskName, message);
	};
	
	private int classes = 1;
	private int methods = 0;
	private int generatedTests = 0; 
};
