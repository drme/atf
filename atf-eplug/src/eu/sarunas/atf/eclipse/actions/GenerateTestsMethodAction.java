package eu.sarunas.atf.eclipse.actions;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jface.dialogs.MessageDialog;
import org.junit.Assert;
import eu.atac.atf.main.Files;
import eu.sarunas.atf.eclipse.generators.EclipseTestsWriter;
import eu.sarunas.atf.eclipse.parsers.JavaProjectParser;
import eu.sarunas.atf.eclipse.utils.ProjectManager;
import eu.sarunas.atf.generators.code.java.CodeGeneratorJava;
import eu.sarunas.atf.generators.code.junit.TestTransformerJUnit;

import eu.sarunas.atf.meta.sut.Field;
import eu.sarunas.atf.meta.sut.Method;
import eu.sarunas.atf.meta.sut.Modifier;
import eu.sarunas.atf.meta.sut.Project;
import eu.sarunas.atf.meta.sut.basictypes.IntegerType;
import eu.sarunas.atf.meta.testdata.TestObjectComplex;
import eu.sarunas.atf.meta.testdata.TestObjectField;
import eu.sarunas.atf.meta.testdata.TestObjectSimple;
import eu.sarunas.atf.meta.tests.TestCase;
import eu.sarunas.atf.meta.tests.TestProject;
import eu.sarunas.atf.meta.tests.TestSuite;
import eu.sarunas.atf.model.checker.TestDataValidator;
import eu.sarunas.projects.atf.db.DataBase;

public class GenerateTestsMethodAction extends BaseAction
{
	public GenerateTestsMethodAction()
	{
		super("Generating tests for a method");
	};
	
	protected void executeAction(IProgressMonitor monitor) throws Throwable
	{
		if (this.selectedItem instanceof IMethod)
		{
			IMethod method = (IMethod)this.selectedItem;
			
			IJavaProject project = method.getParent().getJavaProject();
			
			JavaProjectParser parser = new JavaProjectParser();
			
			Project sutModel = parser.parseProject(project);

			System.out.println(sutModel.toString());
			
			eu.sarunas.atf.generators.tests.TestsGenerator testsGenerator = new eu.sarunas.atf.generators.tests.TestsGenerator();
			
			Method methodToTest = sutModel.findMethod(method);

			TestProject testProject = new TestProject();
			testProject.setName(project.getElementName());
			
			
			try
			{
				String constriants = ProjectManager.getConstraints(project);
				
				eu.sarunas.atf.meta.tests.TestSuite testSuite = testsGenerator.generate(methodToTest, testProject, constriants);
			
			testProject.getTestSuites().add(testSuite);
			
			System.out.println(testSuite.toString());
			
			
			// do validation.. 
			
			
			
			
			
			EclipseTestsWriter writer = new EclipseTestsWriter();
			
			IPath testsFolder = writer.createTestsFolder("tests", project, monitor);
			
			for (TestSuite ts : testProject.getTestSuites())
			{
				TestTransformerJUnit trasnformer = new TestTransformerJUnit();
				
				eu.sarunas.atf.meta.sut.Class cl = trasnformer.transformTest(ts);
				
						CodeGeneratorJava cdgj = new CodeGeneratorJava();
						
						String code=		cdgj.generateClass(cl);
						
				 
				
				writer.createTestsFile(cl.getFullName(), code, project, testsFolder, monitor);
			}
			
			
			}


			catch (Exception ex)
			{
				ex.printStackTrace();
				
		//		MessageDialog.openInformation(null, this.taskName, "Invalid test data\n" + ex.getMessage());
			}
			
			
			
			/*
			

			this.methods = 1;
				
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
