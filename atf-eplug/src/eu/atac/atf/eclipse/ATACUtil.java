package eu.atac.atf.eclipse;

import org.eclipse.jdt.core.JavaModelException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.ITypeParameter;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;

public class ATACUtil {
	public static String getUnresolvedFullyQualifiedMethodSignature(IMethod method, String typeSignature) throws JavaModelException {
		IType type = method.getDeclaringType();

		int arrayCount = Signature.getArrayCount(typeSignature);
		String typeSignatureWithoutArray = typeSignature.substring(arrayCount);

		String typeName = Signature.toString(typeSignatureWithoutArray);
		if ((method.getTypeParameter(typeName).exists()) || (type.getTypeParameter(typeName).exists())) {
			String typeSig = 'T' + typeName + ';';
			return Signature.createArraySignature(typeSig, arrayCount);
		}
		String[][] types = type.resolveType(typeName);

		StringBuilder fqname = new StringBuilder();
		if (types != null) {
			fqname.append(types[0][0]);
			fqname.append('.');
			fqname.append(types[0][1]);

			String typeSig = Signature.createTypeSignature(fqname.toString(), false);
			return Signature.createArraySignature(typeSig, arrayCount);
		}

		return typeSignature;
	}
	
	public static String getUnresolvedFullyQualifiedFieldSignature(IType classType, String typeSignature) throws JavaModelException {

		int arrayCount = Signature.getArrayCount(typeSignature);
		
		String typeSignatureWithoutArray = typeSignature.substring(arrayCount);

		String typeName = Signature.toString(typeSignatureWithoutArray);
		String[][] types = classType.resolveType(typeName);
		
		StringBuilder fqname = new StringBuilder();
		if (types != null) {

			fqname.append(types[0][0]);
			fqname.append('.');
			fqname.append(types[0][1]);

			String typeSig = Signature.createTypeSignature(fqname.toString(), false);
			return Signature.createArraySignature(typeSig, arrayCount);
		}
		return typeSignature;
	}
	
	public static String getUnresolvedFullyQualifiedFieldGenericSignature(IType classType, String typeSignature) throws JavaModelException {
	
		int indexGeneric = typeSignature.indexOf(Signature.C_GENERIC_START);
		if (indexGeneric > 0) {
			String genericSignature = typeSignature.substring(++indexGeneric, typeSignature.indexOf(Signature.C_GENERIC_END));
			genericSignature = Signature.toString(genericSignature);

			String[][] typesGeneric = classType.resolveType(genericSignature);

			StringBuilder fqname = new StringBuilder();

			if (typesGeneric != null) {
				// String typeSig =
				// Signature.createTypeSignature(fqname.toString(), false);
				// return Signature.createArraySignature(typeSig, arrayCount);
				fqname.append(typesGeneric[0][0]);
				fqname.append('.');
				fqname.append(typesGeneric[0][1]);
				return fqname.toString();
			}

		}
		return null;
	}
	

	public static String getPackageName(String fullyQualifiedName) {
		int lastDelimiter = fullyQualifiedName.lastIndexOf('.');

		if (lastDelimiter == -1) {
			return "";
		}
		return fullyQualifiedName.substring(0, lastDelimiter);
	}

	public static String getClassName(String fullyQualifiedName) {
		int lastDelimiter = fullyQualifiedName.lastIndexOf('.');

		if (lastDelimiter == -1) {
			return fullyQualifiedName;
		}
		return fullyQualifiedName.substring(lastDelimiter + 1);
	}

	public static String getFullyQualifiedName(String packageName, String className) {
		if (packageName.length() == 0) {
			return className;
		}
		return packageName + '.' + className;
	}

	public static IPackageFragmentRoot getPackageFragmentRoot(IJavaProject javaProject, String folder) {
		if ((javaProject != null) && (javaProject.exists()) && (javaProject.isOpen())) {
			try {
				IPath path = javaProject.getPath().append(folder).makeAbsolute();
				IPackageFragmentRoot pfr = javaProject.findPackageFragmentRoot(path);
				if ((pfr != null) && (pfr.exists()))
					return pfr;
			} catch (JavaModelException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static IProject getProjectFromName(String projectName) {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IStatus status = workspace.validateName(projectName, 4);

		if (status.isOK()) {
			IProject project = workspace.getRoot().getProject(projectName);

			if (!project.exists()) {
				return null;
			}
			return project;
		}
		return null;
	}

	public static List<IType> findTestableTypes(IJavaElement element, boolean ignoreJUnitTestCases, IProgressMonitor monitor) {
		switch (element.getElementType()) {
		case 3:
			IPackageFragmentRoot pfr = (IPackageFragmentRoot) element;
			return findTestableTypes(pfr, ignoreJUnitTestCases, monitor);
		case 4:
			IPackageFragment pf = (IPackageFragment) element;
			return findTestableTypes(pf, ignoreJUnitTestCases, monitor);
		case 5:
			ICompilationUnit cu = (ICompilationUnit) element;
			return findTestableTypes(cu, ignoreJUnitTestCases, monitor);
		case 6:
			IClassFile cf = (IClassFile) element;
			return findTestableTypes(cf, ignoreJUnitTestCases, monitor);
		}
		return null;
	}

	public static List<IType> findTestableTypes(IPackageFragmentRoot pfr, boolean ignoreJUnitTestCases, IProgressMonitor monitor) {
		SubMonitor sm = SubMonitor.convert(monitor);

		List types = new ArrayList();
		try {
			IJavaElement[] children = pfr.getChildren();
			String taskName = MessageFormat.format("Searching for Java types in {0}", new Object[] { pfr.getElementName() });
			sm.beginTask("", children.length);
			sm.subTask(taskName);
			for (IJavaElement e : children) {
				Assert.isTrue(e instanceof IPackageFragment);
				IPackageFragment pf = (IPackageFragment) e;
				types.addAll(findTestableTypes(pf, ignoreJUnitTestCases, sm.newChild(1)));
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		} finally {
			sm.done();
		}

		return types;
	}

	public static List<IType> findTestableTypes(IPackageFragment pf, boolean ignoreJUnitTestCases, IProgressMonitor monitor) {
		SubMonitor sm = SubMonitor.convert(monitor);

		List types = new ArrayList();
		try {
			String taskName = MessageFormat.format("Searching for Java types in {0}", new Object[] { pf.getElementName() });
			List localList1;
//			String taskName;
			switch (pf.getKind()) {
			case 2:
				IClassFile[] classFiles = pf.getClassFiles();
				sm.beginTask("", classFiles.length);
				sm.subTask(taskName);
//				IClassFile[] classFiles;
				for (IClassFile cf : classFiles) {
					types.addAll(findTestableTypes(cf, ignoreJUnitTestCases, sm.newChild(1)));

					if (sm.isCanceled()) {
						localList1 = types;
						return localList1;
					}
				}
				break;
			case 1:
				ICompilationUnit[] compilationUnits = pf.getCompilationUnits();
				sm.beginTask("", compilationUnits.length);
				sm.subTask(taskName);
//				ICompilationUnit[] compilationUnits;
//				String taskName;
				for (ICompilationUnit cu : compilationUnits) {
					types.addAll(findTestableTypes(cu, ignoreJUnitTestCases, sm.newChild(1)));

					if (sm.isCanceled()) {
						localList1 = types;
						return localList1;
					}
				}
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		} finally {
			sm.done();
		}

		return types;
	}

	public static List<IType> findTestableTypes(ICompilationUnit cu, boolean ignoreJUnitTestCases, IProgressMonitor pm) {
		SubMonitor sm = SubMonitor.convert(pm);

		List validTypes = new ArrayList();
		if ((cu != null) && (cu.exists())) {
			try {
				IType[] allTypes = cu.getAllTypes();
				sm.beginTask("", allTypes.length);
				sm.subTask(MessageFormat.format("Searching for valid Java types in {0}", new Object[] { cu.getElementName() }));
				for (IType t : allTypes) {
					if (isValidTestInput(t, ignoreJUnitTestCases)) {
						validTypes.add(t);

						if (sm.isCanceled()) {
							return validTypes;
						}
					}
					sm.worked(1);
				}
			} catch (JavaModelException e) {
				e.printStackTrace();
			}
		}

		sm.done();
		return validTypes;
	}

	public static List<IType> findTestableTypes(IClassFile cf, boolean ignoreJUnitTestCases, IProgressMonitor monitor) {
		SubMonitor sm = SubMonitor.convert(monitor);

		List types = new ArrayList();

		sm.beginTask("", 1);
		sm.subTask(MessageFormat.format("Checking if Java type for {0} is valid", new Object[] { cf.getElementName() }));
		if ((cf != null) && (cf.exists())) {
			IType t = cf.getType();
			if (isValidTestInput(t, ignoreJUnitTestCases)) {
				types.add(t);
			}

			sm.worked(1);
		}

		sm.done();
		return types;
	}

	public static boolean isValidTestInput(IType type, boolean ignoreJUnitTestCases) {
		try {
			int flags = type.getFlags();
			if ((type.isInterface()) || (Flags.isAbstract(flags)) || (!Flags.isPublic(flags))) {
				return false;
			}
			if (ignoreJUnitTestCases) {
				String siName = type.getSuperclassName();
				if ((siName != null) && (siName.equals("TestCase")))
					return false;
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}

		return true;
	}

	public static IPackageFragmentRoot[] findPackageFragmentRoots(IJavaProject javaProject, IClasspathEntry classpathEntry) throws JavaModelException {
		if (classpathEntry == null) {
			return null;
		}

		if (classpathEntry.getEntryKind() == 2) {
			IWorkspace workspace = javaProject.getProject().getWorkspace();
			IProject project = workspace.getRoot().getProject(classpathEntry.getPath().toString());

			if (project.exists()) {
				IJavaProject referencedJavaProject = JavaCore.create(project);
				List roots = new ArrayList();
				for (IClasspathEntry cpe : referencedJavaProject.getRawClasspath()) {
					if ((cpe.getEntryKind() == 3) || (cpe.isExported())) {
						roots.addAll(Arrays.asList(findPackageFragmentRoots(referencedJavaProject, cpe)));
					}
				}
				return (IPackageFragmentRoot[]) roots.toArray(new IPackageFragmentRoot[roots.size()]);
			}
			return null;
		}
		return javaProject.findPackageFragmentRoots(classpathEntry);
	}
}
