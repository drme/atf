package eu.sarunas.atf.eclipse.parsers;

import java.math.BigDecimal;
import java.util.HashMap;
import javax.security.auth.callback.LanguageCallback;
import javax.xml.datatype.XMLGregorianCalendar;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IImportDeclaration;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;
import eu.sarunas.atf.generators.tests.data.LargeNumberGenerator;
import eu.sarunas.atf.meta.sut.Class;
import eu.sarunas.atf.meta.sut.Constructor;
import eu.sarunas.atf.meta.sut.Field;
import eu.sarunas.atf.meta.sut.Method;
import eu.sarunas.atf.meta.sut.Modifier;
import eu.sarunas.atf.meta.sut.Package;
import eu.sarunas.atf.meta.sut.Parameter;
import eu.sarunas.atf.meta.sut.ParameterizedClass;
import eu.sarunas.atf.meta.sut.Project;
import eu.sarunas.atf.meta.sut.basictypes.BooleanType;
import eu.sarunas.atf.meta.sut.basictypes.ByteType;
import eu.sarunas.atf.meta.sut.basictypes.CharType;
import eu.sarunas.atf.meta.sut.basictypes.CollectionStyle;
import eu.sarunas.atf.meta.sut.basictypes.CollectionType;
import eu.sarunas.atf.meta.sut.basictypes.DateType;
import eu.sarunas.atf.meta.sut.basictypes.DoubleType;
import eu.sarunas.atf.meta.sut.basictypes.FloatType;
import eu.sarunas.atf.meta.sut.basictypes.IntegerType;
import eu.sarunas.atf.meta.sut.basictypes.LargeNumberType;
import eu.sarunas.atf.meta.sut.basictypes.LongType;
import eu.sarunas.atf.meta.sut.basictypes.ObjectType;
import eu.sarunas.atf.meta.sut.basictypes.ShortType;
import eu.sarunas.atf.meta.sut.basictypes.StringType;
import eu.sarunas.atf.meta.sut.basictypes.VoidType;
import eu.sarunas.atf.parsers.IModelParser;
import eu.sarunas.projects.atf.metadata.generic.Type;

/**
 * Parses eclipse java project into a sut meta-model.
 */
public class JavaProjectParser implements IModelParser
{
	public JavaProjectParser()
	{
	};

	public Project parseProject(Object source)
	{
		try
		{
			return transformProject((IJavaProject) source);
		}
		catch (JavaModelException e)
		{
			e.printStackTrace();

			return null;
		}
	};

	/**
	 * Parsers java project.
	 */
	public synchronized Project transformProject(IJavaProject source) throws JavaModelException
	{
		this.project = new Project(source.getElementName(), source);

		Package rootPackage = new Package(project, "", source);

		project.addPackage(rootPackage);
		
		for (IPackageFragmentRoot root : source.getPackageFragmentRoots())
		{
			if (root.getKind() == IPackageFragmentRoot.K_SOURCE)
			{
				for (IJavaElement e : root.getChildren())
				{
					if (e instanceof IPackageFragment)
					{
						IPackageFragment fragment = (IPackageFragment) e;

						if (fragment.getElementName().equals(""))
						{
							transfomPackage(rootPackage, fragment);
						}
						else
						{
							if (false == fragment.isOpen())
							{
								fragment.open(null);
							}

							Package p = new Package(project, fragment.getElementName(), fragment);

							project.addPackage(p);
							
							transfomPackage(p, fragment);
						}
					}
				}
			}
		}

		return project;
	};

	private void transfomPackage(Package p, IPackageFragment fragment) throws JavaModelException
	{
		for (IJavaElement element : fragment.getChildren())
		{
			if (element instanceof ICompilationUnit)
			{
				ICompilationUnit cu = (ICompilationUnit) element;

				IType type = cu.findPrimaryType();

				if (null != type)
				{
					p.addClass(transformClass(type));
				}
			}
		}
	};

	private Class transformClass(IType type) throws JavaModelException
	{
		Class c = getClass(getFullClassName(type), type);
		
		c.setSuperClass(transformType(type.getSuperclassTypeSignature(), type.getCompilationUnit()));

		for (String intfrce : type.getSuperInterfaceTypeSignatures())
		{
			c.addInterface(transformType(intfrce, type.getCompilationUnit()));
		}

		for (IMethod m : type.getMethods())
		{
			if (true == m.isConstructor())
			{
				c.addConstructor(transformConstructor(m, c));
			}
			else
			{
				c.addMethod(transformMethod(m, c));
			}
		}

		for (IField field : type.getFields())
		{
			c.addField(transformField(field, c));
		}

		return c;
	};
	
	private Field transformField(org.eclipse.jdt.core.IField f, Class c) throws JavaModelException
	{
		Field field = new Field(f.getElementName(), getAccessLevel(f.getFlags()), transformType(f.getTypeSignature(), f.getCompilationUnit()), c, f);

		return field;
	};
	
	private String[] getFullTypeSignature(String typeName, ICompilationUnit unit) throws JavaModelException
	{
		if ((typeName.length() > 0) && (typeName.charAt(0) == Signature.C_UNRESOLVED))
		{
			String className = typeName.substring(1, typeName.length() - 1); // remove Q and ;
			
			String genericPart = ""; 
			
			if (true == className.endsWith("" + Signature.C_GENERIC_END))
			{
				genericPart = className.substring(className.indexOf(Signature.C_GENERIC_START) + 1, className.length() - 1);
				className = className.substring(0, className.indexOf(Signature.C_GENERIC_START));
			}
			
			// search full package name in file imports list.
			for (IImportDeclaration imprt : unit.getImports())
			{
				if (imprt.getElementName().endsWith("." + className))
				{
					return getTypeInfo(genericPart, imprt.getElementName());
				}
			}
			
			// search for class with the specified name in the current package.
			if (unit.getParent() instanceof IPackageFragment)
			{
				IPackageFragment parentPackage = (IPackageFragment) unit.getParent();
				
				for (IJavaElement element : parentPackage.getChildren())
				{
					if (element instanceof ICompilationUnit)
					{
						ICompilationUnit cu = (ICompilationUnit) element;

						IType type = cu.findPrimaryType();
						
						if (null != type && type.getElementName().equals(className))
						{
							String packageName = parentPackage.getElementName();
							
							if (packageName.length() > 0)
							{
								return getTypeInfo(genericPart, packageName + "." + className);
							}
							else
							{
								return getTypeInfo(genericPart, className);
							}
						}
					}
				}
			}
			
			// TODO: handle import .*, static imports
			
			return getTypeInfo(genericPart, className);
		}
		else
		{
			return getTypeInfo("", typeName);
		}
	}

	private String[] getTypeInfo(String genericPart, String fullClassName)
	{
		if (genericPart.length() > 0)
		{
			return new String[] { fullClassName + Signature.C_GENERIC_START + genericPart + Signature.C_GENERIC_END, fullClassName, genericPart };
		}
		else
		{
			return new String[] { fullClassName, fullClassName, null };
		}		
	};
		
	private String getFullClassName(IType type)
	{
		String packageName = getPackage(type).getName();
		
		if (packageName.length() > 0)
		{
			return packageName + "." + type.getElementName();
		}
		else
		{
			return type.getElementName();
		}
	};
	
	private Modifier getAccessLevel(int flags)
	{
		Modifier result = Modifier.None;
		
		if (Flags.isPublic(flags))
		{
			result = Modifier.Public;
		}
		
		return result;
	};
	
	private Method transformMethod(IMethod m, Class c) throws JavaModelException
	{
		Method method = new Method(c, m.getElementName(), Modifier.Public, transformType(m.getReturnType(), m.getCompilationUnit()),  m);

		for (int i = 0; i < m.getParameterNames().length; i++)
		{
			method.addParameter(transformParameter(m, i));
		}

		return method;
	};

	private Constructor transformConstructor(IMethod method, Class c) throws JavaModelException
	{
		Constructor constructor = new Constructor(c, method.getElementName(), Modifier.Public, typeVoid, method);

		for (int i = 0; i < method.getParameterNames().length; i++)
		{
			constructor.addParameter(transformParameter(method, i));
		}

		return constructor;
	};
	
	private Parameter transformParameter(IMethod m, int id) throws JavaModelException
	{
		String name = m.getParameterNames()[id];
		String type = m.getParameterTypes()[id];

		for (IImportDeclaration imprt : m.getCompilationUnit().getImports())
		{
//			System.out.println(imprt.toString());
		}
		
		return new Parameter(name, transformType(type, m.getCompilationUnit()), null);
	};

	private Package getPackage(IType type)
	{
		IPackageFragment pckg = (IPackageFragment) type.getCompilationUnit().getParent();

		String name = pckg.getElementName();
		//System.out.println(type.getElementName() + " " + name);//TODO FIXME

		return getPackage(name, pckg);
	};

	//TODO: make private
	protected Type transformType(String type, ICompilationUnit unit) throws JavaModelException
	{
		if (null == type)
		{
			return null;
		}
		
		switch (type)
		{
			case Signature.SIG_BOOLEAN:
				return typeBoolean;
			case Signature.SIG_BYTE:
				return typeByte;
			case Signature.SIG_CHAR:
				return typeChar;
			case Signature.SIG_DOUBLE:
			case "QDouble;": // not nice
				return typeDouble;
			case Signature.SIG_FLOAT:
				return typeFloat;
			case Signature.SIG_INT:
				return typeInteger;
			case Signature.SIG_LONG:
				return typeLong;
			case Signature.SIG_SHORT:
				return typeShort;
			case Signature.SIG_VOID:
				return typeVoid;
			case "Ljava.lang.String;": // not nice
			case "QString;": // not nice
				return typeString;
			case "QObject;":
				return typeObject;
			case "Ljava.math.BigDecimal;": // not nice
			case "QBigDecimal;": // not nice
				return typeBigDecimal;
			case "Ljava.math.BigInteger;": // not nice
			case "QBigInteger;": // not nice
				return new LargeNumberType("BigInteger");
			case "Ljava.util.Date;": // not nice
			case "QDate;": // not nice
				return typeDate;
			case "QXMLGregorianCalendar;":
				return new DateType("XMLGregorianCalendar");
			default:
				return getComplexType(type, unit);
		}		
	};
	
	private Type getComplexType(String name, ICompilationUnit unit) throws JavaModelException
	{
		switch (name.charAt(0))
		{
			case Signature.C_ARRAY:
				return new CollectionType(CollectionStyle.Array, transformType(name.substring(1), unit));
			case Signature.C_UNRESOLVED:
				String[] fullName = getFullTypeSignature(name, unit);
				String genericType = fullName[2];
				
				if (fullName[0].startsWith("java.util.List"))
				{
					return new CollectionType(CollectionStyle.List, transformType(genericType, unit));
				}
				else
				{
					return getClass(fullName[0]);
				}
			default:
				throw new RuntimeException("Unknown type: " + name);
		}
	};
	
	/*
	private Class findClass(String className)
	{
		Class type = this.parsedComplexTypes.get(className);
		
		if ((null == type) && (true == className.contains(".")))
		{
			return this.parsedComplexTypes.get(className.substring(className.lastIndexOf('.') + 1)); 
		}
		else
		{
			return type;
		}
	}*/
	
	private Class getClass(String className) throws JavaModelException
	{
//		Class type = getClass(className, null); 
				//findClass(className);
	//	
	//	if (null == type)
//		{
			if (true == className.endsWith("" + Signature.C_GENERIC_END))
			{
				String genericClassName = className.substring(0, className.indexOf(Signature.C_GENERIC_START));
				
				Class genericClass = getClass(genericClassName, null);

				String packageName = className.lastIndexOf('.') != -1 ? className.substring(0, className.lastIndexOf('.')) : "";
				
				return new ParameterizedClass(genericClass, className, 0, getPackage(packageName, null), null);
				
				//TODO: type add parameters
			}
			else
			{
				return getClass(className, null);
				
			//	String packageName = className.lastIndexOf('.') != -1 ? className.substring(0, className.lastIndexOf('.')) : "";
				
				/*
				 * 
				 * 
				 thank you very much for this untested and non working POS
				
				
				
				int modifier = 0;
				boolean collection = false;
				boolean list = false;
				
				if(className.startsWith("java.") || className.startsWith("javax.")){
					try {
						java.lang.Class<?> javaClass = java.lang.Class.forName(className);
						modifier = javaClass.getModifiers();
						collection = collectionInterface.isAssignableFrom(javaClass);
						list = listInterface.isAssignableFrom(javaClass);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				if(collection){
					type = new eu.sarunas.atf.meta.sut.Collection(className, modifier, getPackage(packageName, null), type);
					((eu.sarunas.atf.meta.sut.Collection)type).setList(list);
				}else{
					type = new Class(className, modifier, getPackage(packageName, null), type);
				}
				if(className.toLowerCase().indexOf("bool") > 0){
					System.out.println(className);
				}
				
				*/

			//	type = new Class(className, 0, getPackage(packageName, null), type);
		//	}
			
	//		this.parsedComplexTypes.put(className, type);
		}
		
//		return type;
	};
	
	/**
	 * Searches for packages in already loaded packages list.
	 * If package was not found a new one is created.
	 * 
	 * @param packageName packages' name to find or create.
	 * 
	 * @return discovered or created package.
	 */
	private Package getPackage(String packageName, Object sourceElement)
	{
		Package p = this.project.getPackage(packageName);
		
		if (null != p)
		{
			return p;
		}
		else
		{
			return this.project.addPackage(new Package(this.project, packageName, sourceElement));
		}
	};
	
	/**
	 * Searches for a class in already loaded classes list.
	 * If class is not found a new one is created.
	 * 
	 * @param classFullName class name in format package.package.ClassName.
	 * @param sourceElement source element of this class.
	 * @return discovered or created class.
	 */
	private Class getClass(String classFullName, IType sourceElement) throws JavaModelException
	{
		Package pckge = null;
		String className = null;
		
		if (classFullName.contains("."))
		{
			String packageName = classFullName.substring(0, classFullName.lastIndexOf('.'));
			className = classFullName.substring(packageName.length() + 1);
			pckge = getPackage(packageName, null);
		}
		else
		{
			pckge = getPackage("", null);
			className = classFullName;
		}
		
		Class cls = pckge.getClass(className);
		
		if (null == cls)
		{
			if ((null != sourceElement) && (true == sourceElement.isEnum()))
			{
				cls = new eu.sarunas.atf.meta.sut.Enum(className, 0, pckge, sourceElement);
			}
			else
			{
				cls = new Class(className, 0, pckge, sourceElement);
			}
			
			pckge.addClass(cls);
		}
		
		return cls;
	};
	
	private Project project = null;
	private static Type typeInteger = new IntegerType();
	private static Type typeVoid = new VoidType();
	private static Type typeShort = new ShortType();
	private static Type typeFloat = new FloatType();
	private static Type typeDouble = new DoubleType();
	private static Type typeChar = new CharType();
	private static Type typeByte = new ByteType();
	private static Type typeBoolean = new BooleanType();
	private static Type typeLong = new LongType();
	private static Type typeString = new StringType();
	private static Type typeObject = new ObjectType();
	private static Type typeBigDecimal = new LargeNumberType();
	private static Type typeDate = new DateType();
};
