package lt.atgplugin.analyzer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lt.atgplugin.filters.AbstractClassFilter;
import lt.atgplugin.filters.ConstructorFilter;
import lt.atgplugin.filters.EnumFilter;
import lt.atgplugin.filters.InterfaceFilter;
import lt.atgplugin.results.AnalyzedClass;
import lt.atgplugin.results.Param;
import lt.atgplugin.utils.Constants;
import lt.atgplugin.utils.LoggerComponent;
import lt.atgplugin.utils.ParamCache;
import lt.atgplugin.utils.Utils;

import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.ITypeHierarchy;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;

/**
 * Abstract class used for ICompilationUnit analysis.
 * 
 * @author greta
 * 
 */
public abstract class Analyzer extends LoggerComponent {

	protected ConstructorFilter defaultClassConstructor = null;

	protected InterfaceFilter defaultInterfaceFilter;

	protected AbstractClassFilter defaultAbstractClassFilter;

	protected EnumFilter defaultEnumFilter;

	protected IJavaProject project;

	protected List<ConstructorFilter> contructorFilters = new ArrayList<ConstructorFilter>(
			0);
	/**
	 * List holding interfaces filters
	 */
	protected List<InterfaceFilter> interfaceFilters = new ArrayList<InterfaceFilter>(
			0);
	/**
	 * List holding abstract class filters
	 */
	protected List<AbstractClassFilter> abstractClassFilters = new ArrayList<AbstractClassFilter>(
			0);

	/**
	 * List holding enumeration filters
	 */
	protected List<EnumFilter> enumFilters = new ArrayList<EnumFilter>(0);

	String packageName;
	/**
	 * Object containing analyzed results.
	 */
	protected AnalyzedClass result = new AnalyzedClass();

	/**
	 * Getter for results.
	 * 
	 * @return results
	 */
	public AnalyzedClass getGeneratedResult() {
		return result;
	}

	public Analyzer(List<ConstructorFilter> c, List<AbstractClassFilter> a,
			List<InterfaceFilter> i, List<EnumFilter> e,
			ConstructorFilter defaultConstructor,
			AbstractClassFilter defaultAbstractClassFilter,
			InterfaceFilter defaultInterfaceFilter, EnumFilter defaultEnumFilter) {
		abstractClassFilters = a;
		interfaceFilters = i;
		enumFilters = e;
		contructorFilters = c;
		this.defaultClassConstructor = defaultConstructor;
		this.defaultAbstractClassFilter = defaultAbstractClassFilter;
		this.defaultInterfaceFilter = defaultInterfaceFilter;
		this.defaultEnumFilter = defaultEnumFilter;
	}

	/**
	 * Setter for analyzed results.
	 * 
	 * @param - generatedResult analyzed results
	 */
	public void setGeneratedResult(AnalyzedClass gResult) {
		this.result = gResult;
	}

	protected IMethod[] searchForMethods(IType type) {
		if (Constants.searchParents) {
			int level = Constants.parentSearchLevel;
			final List<IMethod> allMethods = new ArrayList<IMethod>();
			try {
				for (IMethod d : type.getMethods()) {
					if (!Flags.isPrivate(d.getFlags())) {
						allMethods.add(d);
					}
				}
			} catch (JavaModelException e) {
				setLog(TYPES.ANALYZER, "Methods are null");
				e.printStackTrace();
			}

			{

				try {
					ITypeHierarchy h = type.newSupertypeHierarchy(null);
					IType[] foundTypes = h.getAllSupertypes(type);
					int tmpLevel = (level >= foundTypes.length ? foundTypes.length
							: level);
					for (int i = 0; i < tmpLevel; i++) {
						IType tmp = foundTypes[i];
						for (IMethod m : tmp.getMethods()) {
							if (needsChecking(tmp, m)) {
								boolean found = false;
								if (allMethods == null) {
									setLog(TYPES.ANALYZER, "Methods are null");
								} else {
									for (IMethod x : allMethods) {
										if (x == null) {
											setLog(TYPES.ANALYZER,
													"Method is null");
										} else {
											{

												if (needsAdding(m, x)) {
													found = true;
												}
											}

										}

									}
									if (!found) {
										if ((!Flags.isPrivate(m.getFlags()))

										) {
											if (tmp.getPackageFragment()
													.getElementName()
													.equals(type
															.getPackageFragment()
															.getElementName())
													&& (Flags.isProtected(m
															.getFlags()) || Flags
															.isPackageDefault(m
																	.getFlags()))) {
												allMethods.add(m);
											} else {
												if (Flags.isPublic((m
														.getFlags()))) {
													allMethods.add(m);
												}
											}

										}
									}
								}
							}
						}

					}

				} catch (JavaModelException e1) {
					e1.printStackTrace();
					errors = true;
				}
			}
			return allMethods.toArray(new IMethod[0]);
		} else {
			final List<IMethod> allMethods = new ArrayList<IMethod>();
			try {
				for (IMethod d : type.getMethods()) {
					if (!Flags.isPrivate(d.getFlags())) {
						allMethods.add(d);
					}
				}
				return allMethods.toArray(new IMethod[0]);

			} catch (JavaModelException e) {
				setLog(TYPES.ANALYZER, "Methods are null");
				e.printStackTrace();
			}

		}
		return null;
	}

	private boolean needsAdding(IMethod m, IMethod x) throws JavaModelException {
		if (x.getElementName().equals(m.getElementName())
				&& x.getSignature().equals(m.getSignature())
				&& x.getFlags() == m.getFlags()
				&& m.getParameterTypes().length == x.getParameterTypes().length) {
			for (int i = 0; i < m.getParameterTypes().length; i++) {
				if (!m.getParameterTypes()[i].equals(x.getParameterTypes()[i])) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	private boolean needsChecking(IType tmp, IMethod m) {

		try {
			if (m.isConstructor()
					|| (m.getElementName().equals("<clinit>")
							|| m.getElementName().equals("<init>") || (m
							.getElementName().equals("Object") && tmp
							.getPackageFragment().getElementName()
							.equals("java.lang")))) {

				return false;
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		return true;
	}

	public boolean analyze(ICompilationUnit cUnit) {
		boolean poz = true;
		try {
			if (cUnit == null) {
				setLog(TYPES.ANALYZER, "Unit is null!");
				poz = false;
			} else {
				project = cUnit.getJavaProject();
				if (cUnit.getAllTypes().length > 0) {
					IType parentType = cUnit.getAllTypes()[0];

					packageName = parentType.getPackageFragment()
							.getElementName();
					// if no testable methods are found, nothing is generated
					if (searchForMethods(parentType).length > 0) {
						AnalyzedClass result = checkMainType(project,
								parentType, packageName);

						if (result != null && !isErrors()) {
							this.result = result;
						} else {
							poz = false;
							setLog(TYPES.ANALYZER,
									"No result was generated for unit!");
						}
					} else {
						errors = true;
						poz = false;
						setLog(TYPES.ANALYZER, "No testable methods found!");

					}

				} else {
					poz = false;
					setLog(TYPES.ANALYZER, "No testable classes found!");
				}
			}
		} catch (Exception e) {
			poz = false;
			setLog(TYPES.ANALYZER, "Unknown error!");
			e.printStackTrace();
		}
		return poz;

	}

	protected AnalyzedClass checkMainType(IJavaProject project,
			IType parentType, String packageName) throws JavaModelException {

		AnalyzedClass aResult = new AnalyzedClass();
		aResult.setTestType(parentType);
		aResult.setPackageName(packageName);
		aResult.setQualifiedClassName(parentType.getFullyQualifiedName());
		aResult.setClassName(parentType.getElementName());
		Param code = generateClassObjectConstructorForTests(project,
				parentType, packageName);
		aResult.setTestClassInvocationObject(code);
		if (!isErrors()) {
			Map<String, List<Param>> result = new HashMap<String, List<Param>>(
					0);

			for (IMethod m : searchForMethods(parentType)) {

				List<Param> sar = new ArrayList<Param>(0);
				String methodName = getMethodName(m);
				for (ConstructorFilter c : contructorFilters) {
					for (AbstractClassFilter a : abstractClassFilters) {
						for (InterfaceFilter i : interfaceFilters) {
							for (EnumFilter e : enumFilters) {
								Param root = generateRootParam(parentType, m);
								boolean t = checkMethod(project, parentType, m,
										root, c, a, i, e, packageName,
										Constants.depth);
								if (t) {
									sar.add(root);
								}
							}

						}
					}
				}

				result.put(methodName, sar);

			}

			aResult.setMethods(result);
		}
		return aResult;

	}

	protected Param generateClassObjectConstructorForTests(
			IJavaProject project, IType parentType, String packageName)
			throws JavaModelException {
		Param start = null;
		Utils.log("[START] Searching for constructor for object class invocation object");
		if (parentType.isEnum()) {
			String value = defaultEnumFilter.getValue(parentType);
			Utils.log("[WORK] Found enum type will use " + value + " value");
			start = new Param(parentType.getFullyQualifiedName(), null, false);
			start.setOtherValue(value);
		} else {
			IMethod m = defaultClassConstructor.getConstructor(parentType,
					packageName);

			if (m != null) {
				if (Flags.isStatic(m.getFlags())) {
					Utils.log("[WORK] Found static constructor!");
					start = new Param(parentType.getFullyQualifiedName() + "."
							+ m.getElementName(), null, false);
				} else {
					Utils.log("[WORK] Found constructor!");
					start = new Param("new "
							+ parentType.getFullyQualifiedName(), null, false);

				}
				checkMethod(project, parentType, m, start,
						defaultClassConstructor, defaultAbstractClassFilter,
						defaultInterfaceFilter, defaultEnumFilter, packageName,
						Constants.depth);
			} else {

				// if none found - use default

				if (!Utils.hasPrivateConstructor(parentType, null)) {
					start = new Param(parentType.getFullyQualifiedName(), null,
							false);
					start.setUseDefaultConstructor();
					Utils.log("[WORK] Found no constructor! Using default.");
				} else {

					start = new Param(parentType.getFullyQualifiedName(), null,
							true);
					Utils.log("[WORK] Found private constructor! Using null.");

				}
			}
		}
		Utils.log("[END] Searching for constructor for object class invocation object");

		return start;
	}

	/**
	 * Method which constructs method name, used in Generator.
	 * 
	 * @param m
	 *            - method for which to construct name
	 * @return name of test method
	 * @throws JavaModelException
	 * @throws IllegalArgumentException
	 */
	protected String getMethodName(IMethod m) throws IllegalArgumentException,
			JavaModelException {

		String sig = Signature.toString(m.getSignature()).replace(",", "")
				.replace("(", "").replace(")", "").replace("<", "")
				.replace(">", "").replace("[", "0").replace("]", "")
				.replace(" ", "").replaceAll("/", "");
		String methodName = Constants.testMethodPrefix
				+ m.getElementName().substring(0, 1).toUpperCase()
				+ m.getElementName().substring(1) + "_"
				+ sig.substring(0, 1).toUpperCase() + sig.substring(1);
		return methodName;

	}

	protected Param generateRootParam(IType parentType, IMethod m)
			throws JavaModelException {
		Param result = null;
		if (Flags.isStatic(m.getFlags())) {
			result = new Param(parentType.getFullyQualifiedName() + "."
					+ m.getElementName(), null, false);

		} else {
			if (m.isConstructor()) {
				result = new Param("new " + parentType.getFullyQualifiedName(),
						null, false);
			} else {
				result = new Param(Constants.classObjectName + "."
						+ m.getElementName(), null, false);
			}
		}

		{
			String tmpName = Signature.toString(m.getReturnType());

			if (tmpName != null && !tmpName.equals("void")
					&& !tmpName.equals("null")) {

				String fullName = tmpName;
				/*
				 * if (!tmpName.contains(".")) { String tmp =
				 * Signature.toString(m.getReturnType().replace( "[", "")); if
				 * (!Utils.isPrimitive(tmp)) { String res[][] =
				 * parentType.resolveType(tmp); fullName = res[0][0] + "." +
				 * tmpName; } else { fullName = tmpName; }
				 * 
				 * }
				 */
				result.setReturnValue(fullName);
			}
		}
		return result;
	}

	protected boolean checkMethod(IJavaProject project, IType parentType,
			IMethod m, Param root, ConstructorFilter contructorFilter,
			AbstractClassFilter abstractClassFilter,
			InterfaceFilter interfaceFilter, EnumFilter enumFilter,
			String packageName, int depth) throws JavaModelException {

		boolean poz = true;

		if (m != null) {
			Utils.log("[CHECKING] " + m.getElementName() + " "
					+ m.getParameterTypes().length);
			if (!Flags.isPrivate(m.getFlags())) {
				Utils.log("\t[WORKING] " + m.getElementName() + " "
						+ m.getParameterTypes().length);
				for (int i = 0; i < m.getParameterTypes().length; i++) {
					checkParam(project, parentType, root, depth,
							m.getParameterTypes()[i], m.getParameterNames()[i],
							contructorFilter, abstractClassFilter,
							interfaceFilter, enumFilter, packageName);
				}
			} else {
				poz = false;

			}
		}
		return poz;
	}

	protected void checkParam(IJavaProject project, IType parentType,
			Param root, int depth, String parameterType,
			String parameterNameInMethod, ConstructorFilter c,
			AbstractClassFilter a, InterfaceFilter i, EnumFilter e,
			String packageName) throws JavaModelException {

		Param p = null;
		String typeName = null;

		String fullyQualifiedName = null;
		depth--;

		// if array
		int dim = Signature.getArrayCount(parameterType);
		if (dim > 0) {
			String tmpType = Signature.getElementType(parameterType);
			typeName = Signature.toString(tmpType);
			fullyQualifiedName = typeName;
			p = new Param(typeName, root);
			p.setArray(dim);
			if (Utils.isPrimitive2(typeName)) {
				p.setQualifiedName(typeName);
				p.setPrimitive(true);
			} else {
				String[][] res = parentType.resolveType(typeName);
				if (res.length > 0) {
					fullyQualifiedName = Signature.toQualifiedName(res[0]);
					p.setQualifiedName(fullyQualifiedName);
				}

				checkParam(project, parentType, p, depth, tmpType,
						parameterNameInMethod, c, a, i, e, packageName);
			}
		} else {

			typeName = Signature.toString(parameterType);

			if (Utils.isPrimitive2(typeName)) {
				fullyQualifiedName = typeName;
				p = new Param(typeName, root);
				p.setPrimitivie();

			} else {

				String[][] res = parentType.resolveType(typeName);
				if (res != null) {
					if (res.length > 0) {
						fullyQualifiedName = Signature.toQualifiedName(res[0]);

						if (depth >= 0) {

							if (!ParamCache.contains(fullyQualifiedName, depth)) {
								// class
								IType type = project
										.findType(fullyQualifiedName);
								if (depth > 0) {
									if (type.isEnum()) {
										p = new Param(
												type.getFullyQualifiedName(),
												root);
										p.setOtherValue(e.getValue(type));
									} else {
										IType foundType = null;
										if (Flags.isAbstract(type.getFlags())) {
											foundType = a.getClass(project,
													type);
										} else {
											if (Flags.isInterface(type
													.getFlags())) {
												foundType = i.getInterface(
														project, type);
											} else {
												foundType = type;
											}
										}

										p = createClassParam(root, foundType,
												packageName, c, a, i, e, depth);
									}
								} else {
									p = new Param(fullyQualifiedName, root,
											true);
								}
								ParamCache.addParam(fullyQualifiedName, depth,
										p);
							} else {
								p = ParamCache.getLast();

							}

						} else {
							p = new Param(fullyQualifiedName, root, true);

						}
					} else {
						p = new Param(fullyQualifiedName, root, true);
					}

				} else {
					p = new Param(typeName, root, true);
				}
			}

		}
		root.addParam(p);
		// System.out.println("TURIM "+p.getQualifiedName()+" "+p.getDim());
		/*
		 * if (Utils.isPrimitive2(tmpType)) { typeName = tmpType;
		 * fullyQualifiedName = tmpType;
		 * System.out.println("PRIMITIVE "+tmpType); }else {
		 * 
		 * System.out.println("OTHER "+tmpType); }
		 */
		/*
		 * if (parameterType.endsWith(";")) { //
		 * System.out.println("PARAMETRSS " + parameterType);
		 * 
		 * typeName = Signature.toString(Signature
		 * .getElementType(parameterType));
		 * 
		 * if (typeName != null) {
		 * 
		 * if (!Utils.isPrimitive(typeName)) { String[][] res =
		 * parentType.resolveType(typeName); fullyQualifiedName = res[0][0] +
		 * "." + res[0][1]; } else { fullyQualifiedName =
		 * Signature.toString(parameterType); typeName = fullyQualifiedName; } }
		 * } else { if (parameterType.contains(".")) { typeName = parameterType;
		 * fullyQualifiedName = parameterType; } else {
		 * 
		 * typeName = Signature.toString(Signature
		 * .getElementType(parameterType));
		 * 
		 * if (typeName != null) {
		 * 
		 * if (!Utils.isPrimitive(typeName)) { String[][] res =
		 * parentType.resolveType(typeName); fullyQualifiedName = res[0][0] +
		 * "." + res[0][1]; } else { fullyQualifiedName =
		 * Signature.toString(parameterType); typeName = fullyQualifiedName; } }
		 * } }
		 */
		// System.out.println("Checkinam "+typeName+" "+fullyQualifiedName);

		/*
		 * // if primitive type if (Utils.isPrimitive(typeName)) { //
		 * Utils.isArray(parameter) p = new Param(typeName, root);
		 * p.setPrimitivie(); } else { // if array type if
		 * (Utils.isArray(parameterType)) { int dim =
		 * parameterType.lastIndexOf("[") + 1; p = new Param(fullyQualifiedName,
		 * root); p.setArray(dim); // System.out.println("TURIM " + typeName);
		 * if (!Utils.isPrimitive2(typeName)) { // check again
		 * checkParam(project, parentType, p, depth, fullyQualifiedName, null,
		 * c, a, i, e, packageName); } else { // System.out.println("PRIMI " +
		 * dim); p.setPrimitivie();
		 * p.setQualifiedName(Signature.toString(Signature
		 * .getElementType(parameterType))); Param tmp = new
		 * Param(typeName.replace("[", "").replace( "]", ""), p);
		 * tmp.setPrimitivie(); p.addParam(tmp);
		 * 
		 * }
		 * 
		 * } else {
		 * 
		 * IType type = project.findType(fullyQualifiedName); if (type != null)
		 * { // depth--; if (depth >= 0) { // if abstract if
		 * (Flags.isAbstract(type.getFlags())) { IType foundType =
		 * a.getClass(project, type.getFullyQualifiedName()); p =
		 * createClassParam(root, foundType, packageName, c, a, i, e);
		 * 
		 * } else { // if interface if (Flags.isInterface(type.getFlags())) {
		 * IType foundType = i.getInterface(project,
		 * type.getFullyQualifiedName()); p = createClassParam(root, foundType,
		 * packageName, c, a, i, e); } else { // if enum if
		 * (Flags.isEnum(type.getFlags())) { p = new
		 * Param(type.getFullyQualifiedName(), root);
		 * p.setOtherValue(e.getValue(type));
		 * 
		 * } else { // if class p = createClassParam(root, type, packageName, c,
		 * a, i, e); } } } } else { p = new Param(fullyQualifiedName, root);
		 * p.setNull(); } } else { p = new Param(fullyQualifiedName, root);
		 * p.setNull(); } } } p.setParamName(parameterNameInMethod);
		 * root.addParam(p);
		 */

	}

	protected Param createClassParam(Param root, IType type,
			String packageName, ConstructorFilter c, AbstractClassFilter a,
			InterfaceFilter i, EnumFilter e, int depth)
			throws JavaModelException {

		Param p = null;
		if (type != null) {
			p = new Param(type.getFullyQualifiedName(), root);
			IMethod m = c.getConstructor(type, packageName);
			if (m != null) {
				if (Flags.isStatic(m.getFlags())) {
					p.setInvokeOtherConstructor(type.getFullyQualifiedName()
							+ "." + m.getElementName());
				}
				checkMethod(project, type, m, p, c, a, i, e, packageName, depth);

			} else {
				if (Utils.hasPrivateConstructor(type, null)) {
					p.setNull();
				} else {
					p.setUseDefaultConstructor();
				}

			}

		} else {
			p = new Param("-", root, true);
			p.setNull();

		}
		return p;
	}
}
