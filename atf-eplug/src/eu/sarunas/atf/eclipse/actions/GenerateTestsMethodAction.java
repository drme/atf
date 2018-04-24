package eu.sarunas.atf.eclipse.actions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.internal.compiler.lookup.MissingTypeBinding;
import org.eclipse.jdt.junit.JUnitCore;
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

//		Logger.logger.info(sutModel.toString());

			eu.sarunas.atf.generators.tests.TestsGenerator testsGenerator = new eu.sarunas.atf.generators.tests.TestsGenerator();

			Method methodToTest = sutModel.findMethod(method);

			TestProject testProject = new TestProject();
			testProject.setName(project.getElementName());

			try
			{
				List<String> constraintsFiles = ProjectManager.getConstraintsFiles(project);
				
				
				eu.sarunas.atf.meta.tests.TestSuite testSuite = testsGenerator.generate(methodToTest, testProject, ProjectManager.getConstraints(project));
				testProject.getTestSuites().add(testSuite);

				Logger.logger.info(testSuite.toString());

				// TODO: do validation..

				EclipseTestsWriter writer = new EclipseTestsWriter();

				IPath testsFolder = writer.createTestsFolder("tests", project, monitor);

				generateHelperClass(project, testsFolder, monitor);
				addHelperLibrary(project, monitor);
				addHelperFiles(project, monitor);
				
				addHelperLibrary(project, monitor, "org.emftext.access_1.2.0.201009131109.jar");
				addHelperLibrary(project, monitor, "org.emftext.commons.antlr3_2_0_1.0.0.jar");
				addHelperLibrary(project, monitor, "org.kiama.attribution_3.1.0.201101171054.jar");
				addHelperLibrary(project, monitor, "scala-library.jar");
				addHelperLibrary(project, monitor, "tudresden.ocl20.logging_3.1.0.201101171054.jar");
				addHelperLibrary(project, monitor, "tudresden.ocl20.pivot.essentialocl.standardlibrary_3.1.0.201101171054.jar");
				addHelperLibrary(project, monitor, "tudresden.ocl20.pivot.essentialocl_3.1.0.201101171054.jar");
				addHelperLibrary(project, monitor, "tudresden.ocl20.pivot.examples.pml_3.1.0.201101171055.jar");
				addHelperLibrary(project, monitor, "tudresden.ocl20.pivot.examples.simple_3.1.0.201101171055.jar");
				addHelperLibrary(project, monitor, "tudresden.ocl20.pivot.interpreter_3.1.0.201101171054.jar");
				addHelperLibrary(project, monitor, "tudresden.ocl20.pivot.language.ocl.resource.ocl_3.1.0.201101171054.jar");
				addHelperLibrary(project, monitor, "tudresden.ocl20.pivot.language.ocl.semantics_3.1.0.201101171054.jar");
				addHelperLibrary(project, monitor, "tudresden.ocl20.pivot.language.ocl.staticsemantics_3.1.0.201101171054.jar");
				addHelperLibrary(project, monitor, "tudresden.ocl20.pivot.language.ocl_3.1.0.201101171054.jar");
				addHelperLibrary(project, monitor, "tudresden.ocl20.pivot.metamodels.ecore_3.1.0.201101171054.jar");
				addHelperLibrary(project, monitor, "tudresden.ocl20.pivot.metamodels.java_3.1.0.201101171054.jar");
				addHelperLibrary(project, monitor, "tudresden.ocl20.pivot.metamodels.uml2_3.1.0.201101171054.jar");
				addHelperLibrary(project, monitor, "tudresden.ocl20.pivot.metamodels.xsd_3.1.0.201101171054.jar");
				addHelperLibrary(project, monitor, "tudresden.ocl20.pivot.model_3.1.0.201101171054.jar");
				addHelperLibrary(project, monitor, "tudresden.ocl20.pivot.modelinstance_3.1.0.201101171054.jar");
				addHelperLibrary(project, monitor, "tudresden.ocl20.pivot.modelinstancetype.ecore_3.1.0.201101171054.jar");
				addHelperLibrary(project, monitor, "tudresden.ocl20.pivot.modelinstancetype.java_3.1.0.201101171054.jar");
				addHelperLibrary(project, monitor, "tudresden.ocl20.pivot.modelinstancetype.xml_3.1.0.201101171054.jar");
				addHelperLibrary(project, monitor, "tudresden.ocl20.pivot.modelinstancetype_3.1.0.201101171054.jar");
				addHelperLibrary(project, monitor, "tudresden.ocl20.pivot.parser_3.1.0.201101171054.jar");
				addHelperLibrary(project, monitor, "tudresden.ocl20.pivot.pivotmodel.semantics_3.1.0.201101171054.jar");
				addHelperLibrary(project, monitor, "tudresden.ocl20.pivot.pivotmodel_3.1.0.201101171054.jar");
				addHelperLibrary(project, monitor, "tudresden.ocl20.pivot.standardlibrary.java_3.1.0.201101171054.jar");
				addHelperLibrary(project, monitor, "tudresden.ocl20.pivot.tools.codegen.declarativ.ocl2sql_3.1.0.201101171054.jar");
				addHelperLibrary(project, monitor, "tudresden.ocl20.pivot.tools.codegen.declarativ_3.0.0.201101171054.jar");
				addHelperLibrary(project, monitor, "tudresden.ocl20.pivot.tools.codegen.ocl2java.types_3.0.0.201101171054.jar");
				addHelperLibrary(project, monitor, "tudresden.ocl20.pivot.tools.codegen.ocl2java_3.1.0.201101171054.jar");
				addHelperLibrary(project, monitor, "tudresden.ocl20.pivot.tools.codegen_3.1.0.201101171054.jar");
				addHelperLibrary(project, monitor, "tudresden.ocl20.pivot.tools.CWM_3.0.0.201101171054.jar");
				addHelperLibrary(project, monitor, "tudresden.ocl20.pivot.tools.template.sql_3.1.0.201101171054.jar");
				addHelperLibrary(project, monitor, "tudresden.ocl20.pivot.tools.template_3.1.0.201101171054.jar");
				addHelperLibrary(project, monitor, "tudresden.ocl20.pivot.tools.transformation.pivot2sql_3.0.0.201101171054.jar");
				addHelperLibrary(project, monitor, "tudresden.ocl20.pivot.tools.transformation_3.1.0.201101171054.jar");
				addHelperLibrary(project, monitor, "org.eclipse.emf.ecore_2.6.0.v20100614-1136.jar");
				addHelperLibrary(project, monitor, "org.eclipse.emf.common_2.6.0.v20100614-1136.jar");
				addHelperLibrary(project, monitor, "org.eclipse.core.runtime_3.6.0.v20100505.jar");
				addHelperLibrary(project, monitor, "org.eclipse.osgi_3.6.0.v20100517.jar");
				addHelperLibrary(project, monitor, "org.eclipse.equinox.common_3.6.0.v20100503.jar");
				addHelperLibrary(project, monitor, "org.apache.log4j_1.2.13.v200903072027.jar");
				addHelperLibrary(project, monitor, "org.apache.commons.lang_2.3.0.v200803061910.jar");
				addHelperLibrary(project, monitor, "org.eclipse.emf.ecore.xmi_2.5.0.v20100521-1846.jar");
				
				for (TestSuite ts : testProject.getTestSuites())
				{
					TestTransformerJUnit trasnformer = new TestTransformerJUnit();
					eu.sarunas.atf.meta.sut.Class cl = trasnformer.transformTest(ts, constraintsFiles);

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

	private void addHelperLibrary(IJavaProject javaProject, IProgressMonitor monitor) throws UnsupportedEncodingException, IOException, URISyntaxException, CoreException
	{
		IFolder folder = javaProject.getProject().getFolder("tests_lib");

		if (false == folder.exists())
		{
			folder.create(IResource.NONE, true, monitor);
		}

		IFile file = folder.getFile("tests-runner-helper.jar");

		if (file.exists())
		{
			file.delete(true, monitor);
		}

		file.create(this.getClass().getResourceAsStream("/tests-runner-helper.jar"), IResource.NONE, monitor);

		IClasspathEntry jUnitEntry = JavaCore.newContainerEntry(JUnitCore.JUNIT4_CONTAINER_PATH);

		IClasspathEntry[] currentEntries = javaProject.getRawClasspath();

		boolean hasRunner = false;
		boolean hasJUnit = false;

		for (IClasspathEntry entry : currentEntries)
		{
			if (true == entry.getPath().equals(file.getFullPath()))
			{
				hasRunner = true;
			}
			else if (true == entry.getPath().equals(jUnitEntry.getPath()))
			{
				hasJUnit = true;
			}
		}

		if ((false == hasJUnit) || (false == hasRunner))
		{
			List<IClasspathEntry> newEntries = new ArrayList<>();
			Collections.addAll(newEntries, currentEntries);

			if (false == hasRunner)
			{
				newEntries.add(JavaCore.newLibraryEntry(file.getFullPath(), null, null));
			}

			if (false == hasJUnit)
			{
				newEntries.add(jUnitEntry);
			}

			javaProject.setRawClasspath(newEntries.toArray(new IClasspathEntry[0]), monitor);
		}
	};
	
	private void addHelperLibrary(IJavaProject javaProject, IProgressMonitor monitor, String libraryName) throws UnsupportedEncodingException, IOException, URISyntaxException, CoreException
	{
		IFolder folder = javaProject.getProject().getFolder("tests_lib");

		if (false == folder.exists())
		{
			folder.create(IResource.NONE, true, monitor);
		}

		IFile file = folder.getFile(libraryName);

		if (file.exists())
		{
			file.delete(true, monitor);
		}

		file.create(this.getClass().getResourceAsStream("/" + libraryName), IResource.NONE, monitor);

		IClasspathEntry[] currentEntries = javaProject.getRawClasspath();

		boolean hasLibrary = false;

		for (IClasspathEntry entry : currentEntries)
		{
			if (true == entry.getPath().equals(file.getFullPath()))
			{
				hasLibrary = true;
				break;
			}
		}

		if (false == hasLibrary)
		{
			List<IClasspathEntry> newEntries = new ArrayList<>();
			Collections.addAll(newEntries, currentEntries);

			newEntries.add(JavaCore.newLibraryEntry(file.getFullPath(), null, null));

			javaProject.setRawClasspath(newEntries.toArray(new IClasspathEntry[0]), monitor);
		}
	};	
	
	private void addHelperFiles(IJavaProject javaProject, IProgressMonitor monitor) throws UnsupportedEncodingException, IOException, URISyntaxException, CoreException
	{
		/*
//		IFolder folder = javaProject.getProject().getFolder(javaProject.getProject().getLocation());		

		IFolder folder = ResourcesPlugin.getWorkspace().getRoot().getFolder(javaProject.getProject().getLocation());
		
		if (false == folder.exists())
		{
	    folder.create(IResource.NONE, true, monitor);
		}		
		
		IFile file = folder.getFile("temp.types");
		
		if (file.exists())
		{
			file.delete(true, monitor);
		}
		
		file.create(this.getClass().getResourceAsStream("/temp.types"), IResource.NONE, monitor);*/
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
