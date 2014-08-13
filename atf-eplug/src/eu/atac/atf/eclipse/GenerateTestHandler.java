package eu.atac.atf.eclipse;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.core.CompilationUnit;
import org.eclipse.jdt.internal.core.PackageFragment;
import org.eclipse.jdt.internal.core.PackageFragmentRoot;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.osgi.framework.internal.core.Constants;
import org.eclipse.osgi.util.ManifestElement;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.IConsoleView;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.handlers.HandlerUtil;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;

import eu.atac.atf.main.ATF;
import eu.atac.atf.main.ATFTestModel;
import eu.atac.atf.main.Generate;
import eu.atac.atf.main.ModelUtil;
import eu.atac.atf.test.metadata.TestCase;
import eu.atac.atf.test.metadata.TestSuite;
import eu.sarunas.atf.eclipse.generators.EclipseTestsWriter;
import eu.sarunas.atf.meta.sut.Class;

import eu.sarunas.atf.meta.sut.Package;
import eu.sarunas.atf.meta.sut.Project;
import eu.sarunas.atf.utils.Logger;


@SuppressWarnings("restriction")
public class GenerateTestHandler extends AbstractHandler {
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Shell shell = HandlerUtil.getActiveShell(event);
		ISelection sel = HandlerUtil.getActiveMenuSelection(event);
		IStructuredSelection selection = (IStructuredSelection) sel;

		Object firstElement = selection.getFirstElement();
		try {
			
			//
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			IConsoleView consoleView = (IConsoleView) page.showView(IConsoleConstants.ID_CONSOLE_VIEW);

			MessageConsole messageConsole = null;
			for (IConsole console : ConsolePlugin.getDefault().getConsoleManager().getConsoles()) {
				if(console.getName().equals(ATACPlug.ATF_CONSOLE)){
					messageConsole = (MessageConsole) console;
					break;
				}
			}
//			if(messageConsole == null){
//				messageConsole = new MessageConsole(ATACPlug.ATF_CONSOLE, null);
//				ConsolePlugin.getDefault().getConsoleManager().addConsoles(new IConsole[] { messageConsole });
//				consoleView.display(messageConsole);
//				MessageConsoleStream stream = messageConsole.newMessageStream();
//				System.setOut(new PrintStream(stream));
//				System.setErr(new PrintStream(stream));
//			}
			//

				;		
			if (firstElement instanceof CompilationUnit) {
				CompilationUnit unit = (CompilationUnit) firstElement;

				if(!validateCompiliationUnit(unit)){
					Logger.logger.severe("Can not generate test for test.");
					return null;
				}


				
				IProgressMonitor monitor = new NullProgressMonitor();

				IJavaProject javaProject = unit.getJavaProject();
				
				EclipseTestsWriter writer = new EclipseTestsWriter();
				
				writer.createTestsFolder("tests", javaProject, null);

				
				
				Project project = new Transformer().transformEclipseModelToATF(unit);
				Class clzz = ModelUtil.findClass(project, getPackageName(unit), getClassName(unit));
				
				Package pckg = ModelUtil.findPackage(project, getPackageName(unit));
				ATFTestModel atfTestModel = new ATFTestModel(project,(Package) clzz.getPackage(),clzz, null);
				//----------------------------------------------------------------------------------
//				for (IClasspathEntry iClasspathEntry : javaProject.getRawClasspath()) {
//					System.out.println(iClasspathEntry.getPath().toFile());
//				}

				//----------------------------------------------------------------------------------
				String[] pathsModel = { ATACPlug.ATF_TEST_DIRECTORY + "/" + ATACPlug.ATF_MODEL_DIRECTORY};
			   writer. addToProjectStructure(javaProject.getProject(), pathsModel, null);
			    StringBuilder modelSource = new StringBuilder(200);
			    modelSource.append("package ").append(ATACPlug.ATF_MODEL_DIRECTORY).append(";").append(ATF.NEW_LINE);
			    modelSource.append(ATF.NEW_LINE).append("public class ").append(ATACPlug.ATF_MODEL_NAME).append('{');
			    
			    for (Class cls : pckg.getClasses()) {
			    	modelSource.append(ATF.NEW_LINE).append(ATF.TAB).
			    	    append("public ").append(((Class)cls).getFullName()).append(" ").
			    		append(cls.getName().toLowerCase()).append(";");
				}
			    
			    
			    modelSource.append(ATF.NEW_LINE).append('}');
			    
			    createClass(ATACPlug.ATF_MODEL_NAME, ATACPlug.ATF_MODEL_DIRECTORY, modelSource.toString(), javaProject, monitor);
				
			    javaProject.getProject().build(IncrementalProjectBuilder.INCREMENTAL_BUILD , monitor);
			    
		        IPath projectPath = unit.getJavaProject().getResource().getLocation(); 
		        IPath modelPath = projectPath.
		        		append(ATACPlug.ATF_DEFAULT_CLASS_FILE_DIR).addTrailingSeparator().
		        		append(ATACPlug.ATF_MODEL_DIRECTORY).addTrailingSeparator().
		        		append(ATACPlug.ATF_MODEL_NAME).addFileExtension("class"); 
		        
		         
		        File modelfile =  modelPath.toFile(); 
				//----------------------------------------------------------------------------------
		        
				File oclFIle   = findOclFile(unit,getOCLFileName(clzz));
				
				TestSuite testSuite = Generate.generate(atfTestModel,modelfile,oclFIle);
				
				if(testSuite != null){
					if(testSuite.getDirectory() != null){
						String[] paths = { ATACPlug.ATF_TEST_DIRECTORY + "/" + testSuite.getDirectory()};
					writer.	addToProjectStructure(javaProject.getProject(), paths, monitor);
					}
					for (TestCase testCase : testSuite.getTestCases()) {
						if(testCase.getName()!=null){
							createClass(testCase.getName(),testSuite.getDirectory(), testCase.getContent(),javaProject, monitor);
							javaProject.getProject().build(IncrementalProjectBuilder.INCREMENTAL_BUILD, monitor);
							java.lang.Class<?> clz = loadClass(ATF.ATF_TEST_PACKAGE + "." + testCase.getName(), unit);
//							System.out.println(Arrays.toString(clz.getMethods()));
//							org.junit.runner.Result result = JUnitCore.runClasses(clz);
//							
//							for (Failure fail : result.getFailures()) {
//								System.out.println(fail.getMessage() + fail.getException());
//							}
//							System.out.println(result.getRunCount());
//							System.out.println(result.getFailureCount());
//							System.out.println(result.getIgnoreCount());
//							System.out.println(result.wasSuccessful());
						}
					}
				}
			}
		} catch (Exception e) {
			ATF.log(e);
		}

		return null;
	}

	private String getPersistentProperty(IResource res, QualifiedName qn) {
		try {
			return res.getPersistentProperty(qn);
		} catch (CoreException e) {
			ATF.log(e);
			return "";
		}
	}

	private void setPersistentProperty(IResource res, QualifiedName qn, String value) {
		try {
			res.setPersistentProperty(qn, value);
		} catch (CoreException e) {
			ATACPlug.log(e);
		}
	}

	



	public IPackageFragmentRoot getPackageFragmentRoot(IJavaProject javaProject, String folder) {
		if ((javaProject != null) && (javaProject.exists()) && (javaProject.isOpen())) {
			try {
				IPath path = javaProject.getPath().append(folder).makeAbsolute();
				IPackageFragmentRoot pfr = javaProject.findPackageFragmentRoot(path);
				if ((pfr != null) && (pfr.exists()))
					return pfr;
			} catch (JavaModelException e) {
				ATF.log(e);
			}
		}
		return null;
	}

	
	private void deleteFromTestDirectory(IJavaProject javaProject, IProgressMonitor monitor){
		try {
			for (IJavaElement element : javaProject.getChildren()) {
				if(element.getElementName().equals(ATACPlug.ATF_TEST_DIRECTORY)){
					PackageFragmentRoot pfr = (PackageFragmentRoot) element;
					for (IJavaElement elemenetLevel2 : pfr.getChildren()) {
						PackageFragment pf = (PackageFragment) elemenetLevel2;
						pf.delete(true, monitor);
					}
				}
			}
		} catch (Exception e) {
			ATF.log(e);
		}
	}
	
	private void createClass(String name, String _package,String content,IJavaProject javaProject, IProgressMonitor monitor){
		//Now supports up to 1 level package
		try {
			for (IJavaElement element : javaProject.getChildren()) {
				if(element.getElementName().equals(ATACPlug.ATF_TEST_DIRECTORY)){
					PackageFragmentRoot pfr = (PackageFragmentRoot) element;
					for (IJavaElement elemenetLevel2 : pfr.getChildren()) {
						PackageFragment pf = (PackageFragment) elemenetLevel2;
						if(_package == null || _package.length() == 0){
							if(pf.getElementName() == null || pf.getElementName().length() == 0){
								pf.createCompilationUnit(name, content, true, monitor);
								break;
							}
						}else{
							if(pf.getElementName().equals(_package)){
								pf.createCompilationUnit(name + ".java", content, true, monitor);
								break;
							}
						}
					}
					break;
				}
			}
		} catch (Exception e) {
			ATF.log(e);
		}
	}
	
	private boolean validateCompiliationUnit(IJavaElement element){
		//return true if valid
		if(element == null){
			return false;
		}
		if(element instanceof PackageFragmentRoot){
			PackageFragmentRoot pfr = (PackageFragmentRoot)element;
			if(pfr.getElementName().equals(ATACPlug.ATF_TEST_DIRECTORY)){
				return false;
			}
			return true;
		}
		
		return validateCompiliationUnit(element.getParent());
	}
		
    private File findOclFile(IJavaElement element, String oclFileName){ 
        if(element == null){ 
            return null; 
        } 
        if(element instanceof PackageFragmentRoot){ 
            PackageFragmentRoot pfr = (PackageFragmentRoot)element; 
             
            try { 
                IPath projectPath = pfr.getJavaProject().getResource().getLocation(); 
                IPath oclPath = projectPath.addTrailingSeparator().append(pfr.getElementName()).addTrailingSeparator();
                for (Object res : pfr.getNonJavaResources()) { 
                    if(res instanceof org.eclipse.core.internal.resources.File ){ 
                        org.eclipse.core.internal.resources.File file = (org.eclipse.core.internal.resources.File)res; 
                        
                        if(file.getName().equals(oclFileName + "." + ATF.ATF_OCL_FILE_EXTENSION)){
                        	return oclPath.append(file.getName()).toFile();
                        }
                    } 
                } 
                 
                return null;
            } catch (Exception e) { 
                ATF.log(e); 
            } 
            return null; 
        } 
         
        return findOclFile(element.getParent(),oclFileName); 
    } 
    
    @Deprecated
    private File findClassFile(IJavaElement element, String className){ 
        if(element == null || className == null){ 
            return null; 
        } 
        IPath projectPath = element.getJavaProject().getResource().getLocation(); 
        IPath path = projectPath.append(ATACPlug.ATF_DEFAULT_CLASS_FILE_DIR).addTrailingSeparator(); 
        if(className.indexOf(".") < 0 ){ 
            path = path.append(className + ".class"); 
        }else{ 
            String[] pathArray = className.split("\\."); 
            for (int i = 0; i < pathArray.length - 1; i++) { 
                path = path.append(pathArray[i]).addTrailingSeparator(); 
            } 
            path = path.append(pathArray[pathArray.length - 1] + ".class"); 
        } 
         
        return path.toFile(); 
    }
    
    private String getOCLFileName(Class clzz ){
    	if(clzz.getPackage() == null){
    		return ATF.ATF_DEFAULT_OCL_FILE;
    	}
    	String name = clzz.getPackage().getName();
    	if(name == null){
    		return ATF.ATF_DEFAULT_OCL_FILE;
    	}
    	return name.replace('.', '_');
    }
    
    private String getClassName(IJavaElement element){
    	String name = element.getElementName();
    	return name.substring(0, name.indexOf("."));
    }
    
    private String getPackageName(IJavaElement element){
    	PackageFragment packageFragment = (PackageFragment) element.getParent();
    	return packageFragment.getElementName();
    }
    
    @SuppressWarnings("unchecked")
    private java.lang.Class<?> loadClass(String className, CompilationUnit unit){
		try {
			IJavaProject javaProject = unit.getJavaProject();
			
			String[] classPathEntries = org.eclipse.jdt.launching.JavaRuntime.computeDefaultRuntimeClassPath(javaProject);
			List<URL> urlList = new ArrayList<URL>();
			for (int i = 0; i < classPathEntries.length; i++) {
				String entry = classPathEntries[i];
				IPath path = new Path(entry);
				URL url = path.toFile().toURI().toURL();
				urlList.add(url);
			}
			
			ClassLoader parentClassLoader = unit.getJavaProject().getProject().getClass().getClassLoader();
			URL[] urls = (URL[]) urlList.toArray(new URL[urlList.size()]);
			URLClassLoader classLoader = new URLClassLoader(urls, parentClassLoader);
			
			java.lang.Class<?> clazz = classLoader.loadClass(className);
			
			
			java.lang.Class<JUnitCore> jUnitCoreClass = (java.lang.Class<JUnitCore>) classLoader.loadClass("org.junit.runner.JUnitCore");
			java.lang.Class<Result>    resultClass    = (java.lang.Class<Result>) classLoader.loadClass("org.junit.runner.Result");
			try {
				Method runClassesMethod = jUnitCoreClass.getMethod("runClasses", java.lang.Class[].class);
				Object resultObject = runClassesMethod.invoke(null, new Object[] {new java.lang.Class[]{clazz}});
				
				Method getRunCountMethod   = resultClass.getMethod("getRunCount");
				Method wasSuccessfulMethod = resultClass.getMethod("wasSuccessful");
				Method getFailureCountMethod = resultClass.getMethod("getFailureCount");
				
//				System.out.println("getRunCountMethod:" + getRunCountMethod.invoke(resultObject));
//				System.out.println("wasSuccessfulMethod" + getFailureCountMethod.invoke(resultObject));
//				System.out.println("getFailureCountMethod" + wasSuccessfulMethod.invoke(resultObject));

				
				//org.junit.runner.Result result = (Result) method.invoke(null, new Object[] {param});
				
			} catch (Exception e) {
				Logger.logger.severe(e.getMessage());
				Logger.logger.severe(e.getMessage());
			}

			//org.junit.runner.Result result = JUnitCore.runClasses(clz);
			
			
			return clazz;
		} catch (IllegalArgumentException e) {
			Logger.logger.severe(e.getMessage());
			Logger.logger.severe(e.getMessage());
		} catch (Exception e) {
			Logger.log(e);
		}
		return null;
    }
    
    //----------------------------------------------------------------
    
    @Deprecated
	protected java.lang.Class<?> loadSuiteClass(String suiteClassName) throws ClassNotFoundException {
//		if (fTestPluginName == null)
//			return Class.forName(suiteClassName);
		String fTestPluginName = "AtfEclipsePlugin";
		Bundle bundle = Platform.getBundle(fTestPluginName);
		if (bundle == null)
			throw new ClassNotFoundException(suiteClassName, new Exception("Could not find plugin \"" + fTestPluginName + "\"")); //$NON-NLS-1$ //$NON-NLS-2$

		// is the plugin a fragment?
		Dictionary headers = bundle.getHeaders();
		String hostHeader = (String) headers.get(Constants.FRAGMENT_HOST);
		if (hostHeader != null) {
			// we are a fragment for sure
			// we need to find which is our host
			ManifestElement[] hostElement = null;
			try {
				hostElement = ManifestElement.parseHeader(Constants.FRAGMENT_HOST, hostHeader);
			} catch (BundleException e) {
				throw new RuntimeException("Could not find host for fragment:" + fTestPluginName, e); //$NON-NLS-1$
			}
			Bundle host = Platform.getBundle(hostElement[0].getValue());
			// we really want to get the host not the fragment
			bundle = host;
		}

		return bundle.loadClass(suiteClassName);
	}
}