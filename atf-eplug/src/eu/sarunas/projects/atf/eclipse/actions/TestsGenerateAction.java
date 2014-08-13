package eu.sarunas.projects.atf.eclipse.actions;

import java.io.File;
import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import eu.sarunas.atf.eclipse.actions.BaseAction;
import eu.sarunas.atf.eclipse.parsers.JavaProjectParser;
import eu.sarunas.atf.eclipse.utils.ProjectManager;
import eu.sarunas.atf.meta.sut.Class;
import eu.sarunas.atf.meta.tests.TestProject;
import eu.sarunas.atf.utils.Printer;
import eu.sarunas.projects.atf.db.DataBase;
import eu.sarunas.projects.atf.tests.*;

public class TestsGenerateAction extends BaseAction
{

	public TestsGenerateAction()
	{
		super("Generating tests");
	};
	
	protected void executeAction(IProgressMonitor monitor) throws Throwable
	{
		if (this.selectedItem instanceof IJavaProject)
		{
	/*		IJavaProject project = (IJavaProject)this.selectedItem;
			
			IPath projectPath = project.getProject().getFullPath();
			IPath srcPath = projectPath.append("src");
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			IFolder srcFolder = root.getFolder(srcPath);
			    
			String folder = srcFolder.getLocationURI().toString();
			    
			folder = folder.substring(6, folder.length() - 4);
			    
			List<String> jars = new ArrayList<String>();
				
			getJars(folder, jars); 
				
			TestProject testProject = new TestProject();
			testProject.setName(project.getElementName());
						
			for (String jarFile : jars)
			{
				eu.sarunas.projects.atf.metadata.IProject p = new eu.sarunas.projects.atf.metadata.transformers.JavaTransformer().transformProject(jarFile); 
					
				for (eu.sarunas.projects.atf.metadata.IPackage pck : p.getLibraries().get(0).getPackages())
				{
					for (eu.sarunas.atf.meta.sut.IClass cl : pck.getClasses())
					{
						eu.sarunas.projects.atf.generators.TestsGenerator tg = new eu.sarunas.projects.atf.generators.TestsGenerator();
								
						if (null != cl)
						{
							eu.sarunas.projects.atf.tests.TestSuite ts = tg.generate(cl, testProject);
									
							testProject.getTestSuites().add(ts);
						}
					}
				}
			}
				
			eu.sarunas.projects.atf.metadata.IProject p = JavaProjectParser.getInstance().transformProject(project);
				
			for (eu.sarunas.projects.atf.metadata.IPackage pck : p.getLibraries().get(0).getPackages())
			{
				for (eu.sarunas.atf.meta.sut.IClass cl : pck.getClasses())
				{
					eu.sarunas.projects.atf.generators.TestsGenerator tg = new eu.sarunas.projects.atf.generators.TestsGenerator();
							
					if (null != cl)
					{
						eu.sarunas.projects.atf.tests.TestSuite ts = tg.generate(cl, testProject);
								
						testProject.getTestSuites().add(ts);
					}
				}
			}				
				
			DataBase.getInstance().saveTests(testProject); */
		}
	};
	
	private void getJars(String folder, List<String> jars)
	{
		File f = new File(folder);
		
		if (true == f.exists())
		{
			if (true == f.isFile())
			{
				if (f.getName().contains("junit") == false)
				{
					if (true == f.getName().endsWith(".jar"))
					{
						jars.add(f.getPath());
					}
				}
			}
			else if (true == f.isDirectory())
			{
				for (String file : f.list())
				{
					getJars(folder + "/" + file, jars);
				}
			}
		}
	};

// old VVV

	public void run5(IAction action)
	{
		try
		{
			if (this.selectedItem instanceof IJavaProject)
			{
				IJavaProject project = (IJavaProject)this.selectedItem;
			
				IPath projectPath = project.getProject().getFullPath();
				IPath    srcPath = projectPath.append("src");
			    IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			    IFolder srcFolder = root.getFolder(srcPath);
			    
			    String folder = srcFolder.getLocationURI().toString();
			    
			    folder = folder.substring(6, folder.length() - 4);
			    
				
				
				
				List<String> jars = new ArrayList<String>();
				
				getJars(folder, jars); 
				
				TestProject testProject = new TestProject();
				testProject.setName(project.getElementName());
						
				for (String jarFile : jars)
				{
					eu.sarunas.atf.meta.sut.Project p = null;//new eu.sarunas.projects.atf.metadata.transformers.JavaTransformer().transformProject(jarFile); 
					
					//for (eu.sarunas.projects.atf.metadata.IPackage pck : p.getLibraries().get(0).getPackages())
					{
						//for (eu.sarunas.atf.meta.sut.IClass cl : pck.getClasses())
						{
							eu.sarunas.atf.generators.tests.TestsGenerator tg = new eu.sarunas.atf.generators.tests.TestsGenerator();
								
							//if (null != cl)
							{
								//eu.sarunas.projects.atf.tests.TestSuite ts = tg.generate(cl, testProject);
									
							//	testProject.getTestSuites().add(ts);
							}
						}
					}
				}
				
				DataBase.getInstance().saveTests(testProject);
			}
		}
		catch (Throwable ex)
		{
			ex.printStackTrace();
		}
	};
	
	
	
	
	
	private void run2(IAction action)
	{
		try
		{
			if (this.selectedItem instanceof IJavaProject)
			{
				IJavaProject project = (IJavaProject)this.selectedItem;
			
			//	eu.sarunas.atf.meta.sut.IProject p = new eu.sarunas.projects.atf.metadata.transformers.JavaTransformer().transformProject("e:/atf-lib.jar"); 
//				eu.sarunas.projects.atf.metadata.IProject p = EclipseJavaProjectTransformer.getInstance().transformProject(project);
			
				Printer printer = new Printer();
			
//				printer.print(p);

		//		createTestsFolder(project, p);

			}
		}
		catch (Throwable ex)
		{
			ex.printStackTrace();
		}
		
		
		
		
		
		
		
		
		
		
		
		
//		try
//		{
//		if (this.selectedItem instanceof IType)
//		{
//			IType type = (IType)this.selectedItem;
//			
//			IJavaProject project = type.getJavaProject();
//			
//			String name = project.getElementName();
//			
//		//	createTestProject(name, null);
//			
//			eu.sarunas.projects.atf.metadata.IProject p = EclipseJavaProjectTransformer.getInstance().transformProject(project);
//			
//			Printer printer = new Printer();
//			
//			printer.print(p);
//		}
//		}
//		catch (Throwable ex)
//		{
//			ex.printStackTrace();
//		}
	};
	
	private void createTestsFolder(IJavaProject project, eu.sarunas.atf.meta.sut.Project p) throws CoreException
	{
		
		
		
	      IPath projectPath = project.getProject().getFullPath();
	        IPath    srcPath = projectPath.append("tests");
	//            rulesPath = projectPath.append("rules"),
	  //          publishPath = projectPath.append("publish");
	    IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
	    IFolder srcFolder = root.getFolder(srcPath);
//	              rulesFolder = root.getFolder(rulesPath),
//	              publishFolder = root.getFolder(publishPath);
	      ProjectManager.createFolderHelper(srcFolder,null);
//	      createFolderHelper(rulesFolder,monitor);
//	      createFolderHelper(publishFolder,monitor);
//	      monitor.worked(10);
//	      monitor.subTask(Resources.getString("eclipse.creatingfiles"));
//	      IPath indexPath = srcPath.append("index.xml"),
//	            defaultPath = rulesPath.append("default.xsl");
//	      IFile indexFile = root.getFile(indexPath),
//	            defaultFile = root.getFile(defaultPath);
//	      Class clasz = getClass();
//	      InputStream indexIS = clasz.getResourceAsStream("/org/ananas/xm/eclipse/resources/index.xml"),
//	          defaultIS = clasz.getResourceAsStream("/org/ananas/xm/eclipse/resources/default.xsl");
//	      indexFile.create(indexIS,false,new SubProgressMonitor(monitor,10));
//	      defaultFile.create(defaultIS,false,new SubProgressMonitor(monitor,10));

		
	      //if (this.selectedItem instanceof IType)
			{
				
				TestProject testProject = new TestProject();
				testProject.setName(project.getElementName());
				
				
				for (eu.sarunas.atf.meta.sut.Package pck : p.getPackages())
				{
					if (pck.getName().length() > 0)
					{
						IPath packagePath = srcPath.append(pck.getName().replaceAll("[.]", "/"));
				    	IFolder packageFolder = root.getFolder(packagePath);
				    	ProjectManager.createFolderHelper(packageFolder,null);
					}
					
					for (Class cl : pck.getClasses())
					{
						eu.sarunas.atf.generators.tests.TestsGenerator tg = new eu.sarunas.atf.generators.tests.TestsGenerator();
						
//						
						
						if (null != cl)
						{
							eu.sarunas.atf.meta.tests.TestSuite ts = null;
              try
              {
	              ts = tg.generate(cl, testProject, "");
              }
              catch (Exception e)
              {
	              // TODO Auto-generated catch block
	              e.printStackTrace();
              }
							
							testProject.getTestSuites().add(ts);
						
							/*
						IPath indexPath = srcPath.append(cl.getPackage().getName().replaceAll("[.]", "/") + "/" + cl.getName() + "Test.xml");
						
						 IFile indexFile = root.getFile(indexPath);
						 
						 if (indexFile.exists())
						 {
							 indexFile.delete(true, true, null);
						 }
						 
						 
						 
						 eu.sarunas.projects.atf.utils.Serializer s = new eu.sarunas.projects.atf.utils.Serializer();
						 
						 StringBufferInputStream sb = new StringBufferInputStream(s.toString(ts));
						 
						 indexFile.create(sb, true, null);
						 
						 eu.sarunas.projects.atf.tests.transformers.junit.TestTransformerJUnit ut = new eu.sarunas.projects.atf.tests.transformers.junit.TestTransformerJUnit();
						 
						 for (TestCase tc : ts.getTestCases())
						 {
							 Class c = ut.transformTest(tc);
							 
							 eu.sarunas.projects.atf.code.generators.java.CodeGeneratorJava cj = new eu.sarunas.projects.atf.code.generators.java.CodeGeneratorJava();
							 
							 String code = cj.generateClass(c);
							 
							 IPath codeFilePath = srcPath.append(cl.getPackage().getName().replaceAll("[.]", "/") + "/" + c.getName() + ".java");
								
							 IFile codeFileFile = root.getFile(codeFilePath);
							 
							 if (codeFileFile.exists())
							 {
								 codeFileFile.delete(true, true, null);
							 }
							 
							 StringBufferInputStream sbCode = new StringBufferInputStream(code);
							 
							 codeFileFile.create(sbCode, true, null);
							 
							 
						 }*/
						 
						 
						}
						
					}
					
					DataBase.getInstance().saveTests(testProject);
					
				}
	    	  
	    	  
	    	  
/*				Class cls = EclipseJavaProjectTransformer.getInstance().getClass((IType) this.selectedItem);

					String xml = EclipseJavaProjectTransformer.getInstance().generateTests(cls);
					
					System.out.println(xml);
				
					IPath indexPath = srcPath.append("test_224543.xml");
					
					 IFile indexFile = root.getFile(indexPath);
					 
					 StringBufferInputStream sb = new StringBufferInputStream(xml);
					 
					 indexFile.create(sb, true, null);
						 
//			            defaultFile = root.getFile(defaultPath);
//			      Class clasz = getClass();
//			      InputStream indexIS = clasz.getResourceAsStream("/org/ananas/xm/eclipse/resources/index.xml"),
//			          defaultIS = clasz.getResourceAsStream("/org/ananas/xm/eclipse/resources/default.xsl");
//			      indexFile.create(indexIS,false,new SubProgressMonitor(monitor,10));
//			      defaultFile.create(defaultIS,false,new SubProgressMonitor(monitor,10));						
					
					
					*/
			}		
	      
	  
	};

	private void createTestProject(String projectName, IProgressMonitor monitor)
	{
		try
		{
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		      
		      //monitor.subTask(Resources.getString("eclipse.creatingdirectories"));
		      
			IProject project = root.getProject(projectName + "Tests");
			
			if (false == project.exists())
			{
				IProjectDescription description = ResourcesPlugin.getWorkspace().newProjectDescription(project.getName());

		      //if(!Platform.getLocation().equals("aaa"))
		      //   description.setLocation(namePage.getLocationPath());
		      
		   //   description.setNatureIds(new String[] { PluginConstants.NATURE_ID });
////		      ICommand command = description.newCommand();
///		      command.setBuilderName(PluginConstants.BUILDER_ID);
//		      description.setBuildSpec(new ICommand[] { command });
		      project.create(description, null);
//		      monitor.worked(10);
		      project.open(null);
//		      project.setPersistentProperty(PluginConstants.SOURCE_PROPERTY_NAME,"src");
	//	      project.setPersistentProperty(PluginConstants.RULES_PROPERTY_NAME,"rules");
		//      project.setPersistentProperty(PluginConstants.PUBLISH_PROPERTY_NAME,"publish");
		  //    project.setPersistentProperty(PluginConstants.BUILD_PROPERTY_NAME,"false");
		    //  monitor.worked(10);
		      IPath projectPath = project.getFullPath();
		        IPath    srcPath = projectPath.append("src");
		//            rulesPath = projectPath.append("rules"),
		  //          publishPath = projectPath.append("publish");
		    IFolder srcFolder = root.getFolder(srcPath);
//		              rulesFolder = root.getFolder(rulesPath),
	//	              publishFolder = root.getFolder(publishPath);
		      ProjectManager.createFolderHelper(srcFolder,null);
//		      createFolderHelper(rulesFolder,monitor);
	//	      createFolderHelper(publishFolder,monitor);
//		      monitor.worked(10);
//		      monitor.subTask(Resources.getString("eclipse.creatingfiles"));
//		      IPath indexPath = srcPath.append("index.xml"),
//		            defaultPath = rulesPath.append("default.xsl");
//		      IFile indexFile = root.getFile(indexPath),
//		            defaultFile = root.getFile(defaultPath);
//		      Class clasz = getClass();
//		      InputStream indexIS = clasz.getResourceAsStream("/org/ananas/xm/eclipse/resources/index.xml"),
//		          defaultIS = clasz.getResourceAsStream("/org/ananas/xm/eclipse/resources/default.xsl");
//		      indexFile.create(indexIS,false,new SubProgressMonitor(monitor,10));
//		      defaultFile.create(defaultIS,false,new SubProgressMonitor(monitor,10));
	
		      
			   if (this.selectedItem instanceof IType)
				{
				/*	Class cls = EclipseJavaProjectTransformer.getInstance().getClass((IType) this.selectedItem);

						String xml = EclipseJavaProjectTransformer.getInstance().generateTests(cls);
						
						System.out.println(xml);
					
						IPath indexPath = srcPath.append("test_224543.xml");
						
						 IFile indexFile = root.getFile(indexPath);
						 
						 StringBufferInputStream sb = new StringBufferInputStream(xml);
						 
						 indexFile.create(sb, true, null);
*/						 
//				            defaultFile = root.getFile(defaultPath);
//				      Class clasz = getClass();
//				      InputStream indexIS = clasz.getResourceAsStream("/org/ananas/xm/eclipse/resources/index.xml"),
//				          defaultIS = clasz.getResourceAsStream("/org/ananas/xm/eclipse/resources/default.xsl");
//				      indexFile.create(indexIS,false,new SubProgressMonitor(monitor,10));
//				      defaultFile.create(defaultIS,false,new SubProgressMonitor(monitor,10));						
						
				}			      
		      
			}
		   }
		   catch(Throwable x)
		   {
		     System.err.println(x.getLocalizedMessage());
		     x.printStackTrace();
		   }
		   finally
		   {
		    //  monitor.done();
		   }			
		
		
		   
		   
		   
	};
	
	
	
	
	
	



	/**
	 * Invoked when the user selects the action from the popup-menu. This method is responsible for putting
	 * the fully qualified classname in {@link #fqn} on the system clipboard. 
	 */
	public void run1(IAction action)
	{
		/*
		if (this.selectedItem instanceof IType)
		{
			Class cls = Transformer.getInstance().getClass((IType) this.selectedItem);

			for (Method m : cls.getMethods())
			{
				String xml = Transformer.getInstance().generateTests(m);
				
				System.out.println(xml);
			}
		}
	*/	
		
		
		
//		IProjectDescription qq = ResourcesPlugin.getWorkspace().newProjectDescription("ASDFASDFASDF");
		
	
	
		 //  monitor.beginTask(Resources.getString("eclipse.creatingproject"),50);
		   try
		   {
		      IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		      //monitor.subTask(Resources.getString("eclipse.creatingdirectories"));
		      IProject project = root.getProject("zai zai");
		      IProjectDescription description = ResourcesPlugin.getWorkspace().newProjectDescription(project.getName());

		      //if(!Platform.getLocation().equals("aaa"))
		      //   description.setLocation(namePage.getLocationPath());
		      
		   //   description.setNatureIds(new String[] { PluginConstants.NATURE_ID });
////		      ICommand command = description.newCommand();
///		      command.setBuilderName(PluginConstants.BUILDER_ID);
//		      description.setBuildSpec(new ICommand[] { command });
		      project.create(description, null);
//		      monitor.worked(10);
		      project.open(null);
//		      project.setPersistentProperty(PluginConstants.SOURCE_PROPERTY_NAME,"src");
	//	      project.setPersistentProperty(PluginConstants.RULES_PROPERTY_NAME,"rules");
		//      project.setPersistentProperty(PluginConstants.PUBLISH_PROPERTY_NAME,"publish");
		  //    project.setPersistentProperty(PluginConstants.BUILD_PROPERTY_NAME,"false");
		    //  monitor.worked(10);
		      IPath projectPath = project.getFullPath();
		        IPath    srcPath = projectPath.append("src");
		//            rulesPath = projectPath.append("rules"),
		  //          publishPath = projectPath.append("publish");
		    IFolder srcFolder = root.getFolder(srcPath);
//		              rulesFolder = root.getFolder(rulesPath),
	//	              publishFolder = root.getFolder(publishPath);
		      ProjectManager.createFolderHelper(srcFolder,null);
//		      createFolderHelper(rulesFolder,monitor);
	//	      createFolderHelper(publishFolder,monitor);
//		      monitor.worked(10);
//		      monitor.subTask(Resources.getString("eclipse.creatingfiles"));
//		      IPath indexPath = srcPath.append("index.xml"),
//		            defaultPath = rulesPath.append("default.xsl");
//		      IFile indexFile = root.getFile(indexPath),
//		            defaultFile = root.getFile(defaultPath);
//		      Class clasz = getClass();
//		      InputStream indexIS = clasz.getResourceAsStream("/org/ananas/xm/eclipse/resources/index.xml"),
//		          defaultIS = clasz.getResourceAsStream("/org/ananas/xm/eclipse/resources/default.xsl");
//		      indexFile.create(indexIS,false,new SubProgressMonitor(monitor,10));
//		      defaultFile.create(defaultIS,false,new SubProgressMonitor(monitor,10));
		   }
		   catch(Throwable x)
		   {
		     System.err.println(x.getLocalizedMessage());
		     x.printStackTrace();
		   }
		   finally
		   {
		    //  monitor.done();
		   }		
		
		
		
		
		System.out.println(fqn);
		
		/*
		Clipboard clipboard = null;
		IStatusLineManager statusLineManager = null;
		
		try {
			clipboard = new Clipboard(Display.getCurrent());
			clipboard.setContents(new Object[] { fqn }, new Transfer[] {  TextTransfer.getInstance() });
			
			if(targetPart != null) {
				IViewSite viewSite = (IViewSite)targetPart.getSite();
				statusLineManager = viewSite.getActionBars().getStatusLineManager();
				String msg = CopyFqnPlugin.getInstance().getMessage("copied.message", fqn);
				statusLineManager.setMessage(msg);
			}
		} finally {
			if(clipboard != null) {
				clipboard.dispose();
			}
		}*/
	};


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//    private List selectedObjects; // contains Objects
//
//    private List selectedClasses; // contains Class objects
//
//    /**
//     * Constructor for Action1.
//     */
//    public TestsGenerateAction() {
//        super();
//    }
//
//    /**
//     * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
//     */
//    public void setActivePart(IAction action, IWorkbenchPart targetPart) {
//    }
//
//    /**
//     * @see IActionDelegate#run(IAction)
//     */
//    public void run(IAction action) {
//    	
//    	System.out.println("acion runninh");
//    /*	
//        Iterator objectIter = selectedObjects.iterator();
//        Iterator classIter = selectedClasses.iterator();
//        StringBuffer files = new StringBuffer();
//        while (objectIter.hasNext()) {
//            Object selected = (Object) objectIter.next();
//            Class selectedClass = (Class) classIter.next();
//            if (selected == null) {
//                MessageDialog.openInformation(new Shell(), "ContextMenuPlugin", "Unable to select "
//                        + selectedClass.getName());
//                //ContextMenuPlugin.log("Unable to select " + selectedClass);
//                return;
//            }
//            java.io.File file = null;
//            if (selected instanceof IResource) {
//                file = new java.io.File(((IResource) selected).getLocation().toOSString());
//            } else if (selected instanceof java.io.File) {
//                file = (java.io.File) selected;
//            }
//            files.append("\"" + file + "\" ");
//        }*/
//        try {
////            String target = ContextMenuPlugin.getDefault().getTarget();
////            if (target.indexOf("{0}") == -1) {
//  //              target = target.trim() + " {0}";
//    //        }
//      //      Runtime.getRuntime().exec(MessageFormat.format(target, new Object[] { files.toString() }));
//        } catch (Exception e) {
//        //    ContextMenuPlugin.log(e);
//          //  MessageDialog.openInformation(new Shell(), "ContextMenuPlugin", "Unable to start:\n"
//            //        + ContextMenuPlugin.getDefault().getTarget() + "\n"
//              //      + "Please check Window->Preferences->Context Menu");
//        }
//
//    }
//
//    /**
//     * @see IActionDelegate#selectionChanged(IAction, ISelection)
//     */
//    public void selectionChanged(IAction action, ISelection selection) {
///*        IAdaptable adaptable = null;
//        selectedObjects = new ArrayList();
//        selectedClasses = new ArrayList();
//        if (selection instanceof IStructuredSelection) {
//
//            for (Iterator iter = ((IStructuredSelection) selection).iterator(); iter.hasNext();) {
//                adaptable = (IAdaptable) iter.next();
//                selectedClasses.add(adaptable.getClass());
//                if (adaptable instanceof IResource) {
//                    selectedObjects.add((IResource) adaptable);
//                } else if (adaptable instanceof PackageFragment
//                        && ((PackageFragment) adaptable).getPackageFragmentRoot() instanceof JarPackageFragmentRoot) {
//                    selectedObjects.add(getJarFile(((PackageFragment) adaptable).getPackageFragmentRoot()));
//                } else if (adaptable instanceof JarPackageFragmentRoot) {
//                    selectedObjects.add(getJarFile(adaptable));
//                } else {
//                    selectedObjects.add((IResource) adaptable.getAdapter(IResource.class));
//                }
//            }
//        } */
//    }
//
//    /*
//    protected File getJarFile(IAdaptable adaptable) {
//        JarPackageFragmentRoot jpfr = (JarPackageFragmentRoot) adaptable;
//        File selected = (File) jpfr.getPath().makeAbsolute().toFile();
//        if (!((File) selected).exists()) {
//            File projectFile = new File(jpfr.getJavaProject().getProject().getLocation().toOSString());
//            selected = new File(projectFile.getParent() + selected.toString());
//        }
//        return selected;
//    }
//*/
	
	public void getJars22(String folder, List<String> jars)
	{
		File f = new File(folder);
		
		if (true == f.exists())
		{
			if (true == f.isFile())
			{
				if (true == f.getName().endsWith(".jar"))
				{
					jars.add(f.getPath());
				}
			}
			else if (true == f.isDirectory())
			{
				for (String file : f.list())
				{
					getJars(folder + "/" + file, jars);
				}
			}
		}
	};
};






















































