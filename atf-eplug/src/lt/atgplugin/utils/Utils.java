package lt.atgplugin.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;
import org.eclipse.jdt.core.ToolFactory;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.formatter.CodeFormatter;
import org.eclipse.jdt.core.formatter.DefaultCodeFormatterConstants;
//import org.eclipse.jface.text.Document;
//import org.eclipse.jface.text.IDocument;
//import org.eclipse.text.edits.TextEdit;

public class Utils {
	/**
	 * This parameter will hold plugin messages.
	 */
	private static final StringBuilder log = new StringBuilder();
	/**
	 * This parameter will hold plugin errors.
	 */
	private static final StringBuilder errorLog = new StringBuilder();

	/**
	 * Method used for parsing ICompilationUnit.
	 * 
	 * @param unit
	 *            - unit which will be parsed
	 * @return parsed CompilationUnit
	 */
	public static CompilationUnit parse(ICompilationUnit unit) {
		if (unit == null) {
			throw new IllegalArgumentException("Unit can not be null");
		}
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(unit); // set source
		parser.setResolveBindings(true); // we need bindings later on
		return (CompilationUnit) parser.createAST(null); // parse
	}

	/**
	 * Method for logging plugin messages.
	 * 
	 * @param message
	 *            which will be logged
	 */
	public static void log(String message) {
		log.append(message + "\n");
		System.out.println(message);
	}

	/**
	 * Method for logging error messages.
	 * 
	 * @param message
	 *            which will be logged
	 */
	public static void error(String message) {
		System.out.println(message);
		errorLog.append(message + "\n");
	}

	/**
	 * Returns type declaration from compilation unit for type.
	 * 
	 * @param type
	 *            - for which declaration will be found
	 * @return type declaration
	 */
	public static TypeDeclaration getTypeDeclarationForType(IType type) {
		try {
			CompilationUnit u = Utils.parse(type.getCompilationUnit());
			TypeDeclaration tmp = (TypeDeclaration) u.types().get(0);
			for (Object o : (u.types())) {
				if (o instanceof TypeDeclaration) {
					TypeDeclaration tmpType = (TypeDeclaration) o;
					if ((u.getPackage().getName().getFullyQualifiedName() + "." + tmpType
							.getName().getFullyQualifiedName()).equals(type
							.getFullyQualifiedName())) {
						tmp = tmpType;
					}

				}
			}
			return tmp;
		} catch (Exception e) {

			return null;

		}
	}

	/**
	 * Method for finding all public, protected and private constructors.
	 * 
	 * @param element
	 * @param typeDeclaration
	 * @return list of constructors
	 */
	public static List<MethodDeclaration> getAllDeclaredConstructors(
			IType element, TypeDeclaration typeDeclaration) {
		if (typeDeclaration == null) {
			typeDeclaration = getTypeDeclarationForType(element);
		}
		List<MethodDeclaration> sar = new ArrayList<MethodDeclaration>(0);

		if (typeDeclaration != null) {
			for (MethodDeclaration m : typeDeclaration.getMethods()) {
				System.out.println("TIKRINAM METODa " + m);
				if (m.isConstructor()) {
					System.out.println("RADOM " + m);
					sar.add(m);
				}
			}
		}
		return sar;
	}

	/**
	 * Method for checking if class has private constructor.
	 * 
	 * @param element
	 * @param typeDeclaration
	 * @return list of constructors
	 */
	public static boolean hasPrivateConstructor(IType element,
			TypeDeclaration typeDeclaration) {
		try {
			for (IMethod m : element.getMethods()) {
				Utils.log("[WORK] Checking method " + m.getElementName());
				if (m.isConstructor() && Flags.isPrivate(m.getFlags())) {
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static List<MethodDeclaration> getAllDeclaredConstructorsNonPrivate(
			IType element, TypeDeclaration typeDeclaration) {
		if (typeDeclaration == null) {
			typeDeclaration = getTypeDeclarationForType(element);
		}
		List<MethodDeclaration> sar = new ArrayList<MethodDeclaration>(0);

		if (typeDeclaration != null) {
			for (MethodDeclaration m : typeDeclaration.getMethods()) {
				if (m.isConstructor() && !Flags.isPrivate(m.getFlags())) {
					sar.add(m);
				}
			}
		}
		return sar;
	}

	/**
	 * Method which finds constructors.
	 * 
	 * @param type
	 *            - class for which constructors will be found
	 * @param packageName
	 *            - contains package name where generated unit will be put, if
	 *            null - no checking for package name will be performed
	 * @param allPublic
	 *            - flag if search for public constructors
	 * @param allProtected
	 *            - flag if search for protected constructors
	 * @param allStatic
	 *            - flag if search for static methods which return
	 *            <code>type</code> IType
	 * @return list containing constructor methods
	 */
	public static List<IMethod> getAllConstructors(IType type,
			String packageName, boolean allPublic, boolean allProtected,
			boolean allStatic) {
		List<IMethod> constructors = new ArrayList<IMethod>();
		if (type == null) {
			throw new IllegalArgumentException("Type can not be null");
		}
		try {
			for (IMethod m : type.getMethods()) {

				if (allPublic && m.isConstructor()
						&& Flags.isPublic(m.getFlags())) {
					constructors.add(m);
				} else {
					if (allProtected
							&& (packageName != null ? type.getPackageFragment()
									.getElementName().equals(packageName)
									: false)
							&& m.isConstructor()
							&& (Flags.isProtected(m.getFlags()) || Flags
									.isPackageDefault(m.getFlags()))) {
						constructors.add(m);
					} else {
						String returnType = Signature.toString(
								m.getReturnType()).split("<")[0];
						if (allStatic
								&& allPublic
								&& returnType.equals(type
										.getFullyQualifiedName())
								&& Flags.isStatic(m.getFlags())
								&& Flags.isPublic(m.getFlags()) || allStatic
								&& allPublic
								&& returnType.equals(type.getElementName())
								&& Flags.isStatic(m.getFlags())
								&& Flags.isPublic(m.getFlags())) {
							constructors.add(m);
						} else {
							if ((allStatic
									&& allProtected
									&& (packageName != null ? type
											.getPackageFragment()
											.getElementName()
											.equals(packageName) : false)
									&& returnType.equals(type
											.getFullyQualifiedName())
									&& Flags.isStatic(m.getFlags()) && (Flags
									.isProtected(m.getFlags()) || Flags
									.isPackageDefault(m.getFlags())))
									|| (allStatic
											&& allProtected
											&& (packageName != null ? type
													.getPackageFragment()
													.getElementName()
													.equals(packageName)
													: false)
											&& returnType.equals(type
													.getElementName())
											&& Flags.isStatic(m.getFlags()) && (Flags
											.isProtected(m.getFlags()) || Flags
											.isPackageDefault(m.getFlags())))) {
								constructors.add(m);
							}
						}
					}
				}
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
			error("Can not get methods for " + type.getFullyQualifiedName());
		}
		Utils.log("[START] All found constructors:");
		for (IMethod x : constructors) {
			Utils.log("[WORK] Found " + x.getElementName() + " with "
					+ x.getParameterTypes().length + " parameters");
		}
		Utils.log("[END] Searching for all constructors.");
		return constructors;
	}

	/**
	 * Checks if parameter is array.
	 * 
	 * @param parameter
	 *            - holds parameter which will be checked
	 * @return true if array
	 */
	public static boolean isArray(String parameter) {
		System.out.println("CHECKING " + parameter);
		boolean poz = parameter.lastIndexOf("[") > -1;
		return poz;
	}
	static List<String> sar = Arrays.asList(new String[] { "boolean", "byte",
			"char", "short", "int", "long", "float", "double" });
	
	/**
	 * Checks if parameter is primitive.
	 * 
	 * @param name
	 *            - parameter name
	 * @return true if primitive type
	 */
	public static boolean isPrimitive(String parameter) {
		
		return sar.contains(parameter.toLowerCase());
	}

	/**
	 * Formats source.
	 * 
	 * @param source
	 *            - unformatted source, which will be used for formatting
	 * @return formatted source
	 */
	@SuppressWarnings("unchecked")
	public static String formatCode(String source) {
		if (source == null) {
			error("Can not format document, contents as source is null");
			throw new IllegalArgumentException(
					"Source for formatting can not be null");
		} else {
			// take default Eclipse formatting options
			@SuppressWarnings("rawtypes")
			Map options = DefaultCodeFormatterConstants
					.getEclipseDefaultSettings();

			// initialize the compiler settings to be able to format 1.5 code
			options.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_5);
			options.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM,
					JavaCore.VERSION_1_5);
			options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_5);

			// change the option to wrap each enum constant on a new line
			options.put(
					DefaultCodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_ENUM_CONSTANTS,
					DefaultCodeFormatterConstants.createAlignmentValue(true,
							DefaultCodeFormatterConstants.WRAP_ONE_PER_LINE,
							DefaultCodeFormatterConstants.INDENT_ON_COLUMN));

			// instantiate the default code formatter with the given options
			final CodeFormatter codeFormatter = ToolFactory
					.createCodeFormatter(options);
/*
			final TextEdit edit = codeFormatter.format(
					CodeFormatter.K_COMPILATION_UNIT, // format a compilation
														// unit
					source, // source to format
					0, // starting position
					source.length(), // length
					0, // initial indentation
					System.getProperty("line.separator") // line separator
					);

			IDocument document = new Document(source);
			try {
				edit.apply(document);
				return document.get();
			} catch (Exception e) {
				error("Can not format document, contents:\n" + source);
				return null;
			} */
		}
		return source;
	}

	public static boolean isPrimitive2(String typeName) {
		if (typeName.toLowerCase().startsWith("float")) {
			return true;
		}
		if (typeName.toLowerCase().startsWith("char")) {
			return true;
		}
		if (typeName.toLowerCase().startsWith("double")) {
			return true;
		}
		if (typeName.toLowerCase().startsWith("byte")) {
			return true;
		}
		if (typeName.toLowerCase().startsWith("int")) {
			return true;
		}
		if (typeName.toLowerCase().startsWith("long")) {
			return true;
		}
		if (typeName.toLowerCase().startsWith("boolean")) {
			return true;
		}
		if (typeName.toLowerCase().startsWith("short")) {
			return true;
		}

		return false;
	}
}
