package eu.sarunas.atf.eclipse.actions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jface.dialogs.MessageDialog;
import eu.sarunas.atf.eclipse.generators.EclipseTestsWriter;
import eu.sarunas.atf.eclipse.parsers.JavaProjectParser;
import eu.sarunas.atf.eclipse.utils.ProjectManager;
import eu.sarunas.atf.generators.code.java.CodeGeneratorJava;
import eu.sarunas.atf.generators.code.junit.TestTransformerJUnit;
import eu.sarunas.atf.meta.sut.Method;
import eu.sarunas.atf.meta.sut.Project;
import eu.sarunas.atf.meta.tests.TestProject;
import eu.sarunas.atf.meta.tests.TestSuite;
import eu.sarunas.atf.utils.Logger;

public class GenerateTestsMethodAction extends BaseAction
{
	public GenerateTestsMethodAction()
	{
		super("Generating tests for a method");
	};

	@Override
	protected void executeAction(IProgressMonitor monitor) throws Throwable
	{
		if (this.selectedItem instanceof IMethod)
		{
			IMethod method = (IMethod) this.selectedItem;
			IJavaProject project = method.getParent().getJavaProject();

			JavaProjectParser parser = new JavaProjectParser();
			Project sutModel = parser.parseProject(project);

			Logger.logger.info(sutModel.toString());

			eu.sarunas.atf.generators.tests.TestsGenerator testsGenerator = new eu.sarunas.atf.generators.tests.TestsGenerator();

			Method methodToTest = sutModel.findMethod(method);

			TestProject testProject = new TestProject();
			testProject.setName(project.getElementName());

			try
			{
				String constriants = ProjectManager.getConstraints(project);

				eu.sarunas.atf.meta.tests.TestSuite testSuite = testsGenerator.generate(methodToTest, testProject, constriants);
				testProject.getTestSuites().add(testSuite);

				Logger.logger.info(testSuite.toString());

				// TODO: do validation..

				EclipseTestsWriter writer = new EclipseTestsWriter();

				IPath testsFolder = writer.createTestsFolder("tests", project, monitor);

				generateHelperClass(project, testsFolder, monitor);
				
				for (TestSuite ts : testProject.getTestSuites())
				{
					TestTransformerJUnit trasnformer = new TestTransformerJUnit();
					eu.sarunas.atf.meta.sut.Class cl = trasnformer.transformTest(ts);

					CodeGeneratorJava cdgj = new CodeGeneratorJava();
					String code = cdgj.generateClass(cl);

					writer.createTestsFile(cl.getFullName(), code, project, testsFolder, monitor);
				}
			}
			catch (Exception ex)
			{
				this.exception = ex;
			}
		}
	};
	
	private void generateHelperClass(IJavaProject javaProject, IPath testsFolder, IProgressMonitor monitor) throws UnsupportedEncodingException, IOException, URISyntaxException
	{
		EclipseTestsWriter writer = new EclipseTestsWriter();

		String code = "";

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/JUnitTemplate.txt"))))
		{
			for (String line; (line = reader.readLine()) != null;)
			{
				code += line + "\n";
			}
		}

		writer.createTestsFile("eu.sarunas.junit.TestsHelper", code, javaProject, testsFolder, monitor);
	};

	@Override
	protected void onDone()
	{
		if (null != this.exception)
		{
			Logger.log(this.exception);

			MessageDialog.openInformation(null, this.taskName, "Invalid test data\n" + this.exception.getMessage());
		}
		else
		{
			String message = "Generated tests: " + this.generatedTests;
			message += "\r\nfor Classes: " + this.classes;
			message += "\r\nfor Methods: " + this.methods;

			MessageDialog.openInformation(null, this.taskName, message);
		}
	};

	private int classes = 1;
	private int methods = 0;
	private int generatedTests = 0;
	private Exception exception = null;
};
