package lt.atgplugin.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lt.atgplugin.generator.rules.Rule;
import lt.atgplugin.results.AnalyzedClass;
import lt.atgplugin.results.Param;
import lt.atgplugin.utils.Constants;
import lt.atgplugin.utils.LoggerComponent;
import lt.atgplugin.utils.Utils;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

/**
 * Class used for results generation.
 * 
 * @author greta
 * 
 */
public abstract class Generator extends LoggerComponent {

	protected ICompilationUnit generatedUnit;

	protected Rule defaultValueGenerator;

	protected List<Rule> valueGenerators = new ArrayList<Rule>(0);
	protected boolean poz = false;

	protected StringBuilder getUnitContents(ICompilationUnit c,
			AnalyzedClass generatorResult) {
		StringBuilder b = new StringBuilder();
		try {
			b.append(createClassHeader(generatorResult));
			Map<String, List<Param>> elements = generatorResult.getMethods();
			Param main = generatorResult
					.getTestedClassObjectForMethodsCalling();
			for (String methodName : elements.keySet()) {
				int prefix = 1;
				for (Rule v : valueGenerators) {
					for (Param p : elements.get(methodName)) {
						List<String> list = v.getTestMethodsBodies(
								c.getJavaProject(), p, elements, main);
						if (list != null) {
							for (String tmp : list) {
								b.append(getTestMethod(
										methodName
												+ (v.getShortName() != null ? "_"
														+ v.getShortName()
														: "") + "_" + prefix,
										tmp));
								poz = true;
								prefix++;
							}
						}
					}
				}

			}
			createClassFooter(b);

		} catch (Exception e) {
			setLog(TYPES.GENERATOR, "Getting unit contents failed");
			e.printStackTrace();
		}
		return b;
	}

	public String getGeneratedClassName() {
		if (generatedUnit != null) {
			try {
				return generatedUnit.getAllTypes()[0].getFullyQualifiedName();
			} catch (JavaModelException e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	public ICompilationUnit getGeneratedUnit() {
		return generatedUnit;
	}

	public void setGeneratedUnit(ICompilationUnit generatedUnit) {
		this.generatedUnit = generatedUnit;
	}

	public Generator(List<Rule> valueGenerators, Rule defaultValueGenerator) {
		this.valueGenerators = valueGenerators;
		this.defaultValueGenerator = defaultValueGenerator;
		if (valueGenerators.isEmpty()) {
			setLog(TYPES.GENERATOR, "No generators are set!");
			throw new IllegalArgumentException(
					"There should be at least one generator");
		}
		if (defaultValueGenerator == null) {
			setLog(TYPES.GENERATOR, "No default value generator is set!");
		}

	}

	public void generate(ICompilationUnit c, AnalyzedClass generatorResult) {

		String className = c.getElementName().replace(".java", "")
				+ Constants.testClassPostfix + ".java";

		StringBuilder b = getUnitContents(c, generatorResult);
		if (poz) {
			if (!isErrors()) {
				try {
					if (Constants.differentFolder) {
						IJavaProject project = c.getJavaProject();
						IClasspathEntry entry = JavaCore.newSourceEntry(project
								.getPath().append(
										new Path(Constants.folderName)));
						int LENGTH = project.getRawClasspath().length + 1;
						IClasspathEntry[] list = new IClasspathEntry[LENGTH];
						boolean poz = false;
						for (int i = 0; i < project.getRawClasspath().length; i++) {
							list[i] = project.getRawClasspath()[i];
							if (project.getRawClasspath()[i]
									.getPath()
									.toFile()
									.getAbsoluteFile()
									.toString()
									.equals(entry.getPath().toFile()
											.getAbsoluteFile().toString())) {
								poz = true;
							}
						}
						list[LENGTH - 1] = entry;
						if (!poz) {
							project.setRawClasspath(list, null);
						}

						IFolder f = project.getProject().getFolder(
								Constants.folderName);
						if (!f.exists()) {
							f.create(true, true, null);
						}
						IPackageFragmentRoot rootFolder = project
								.getPackageFragmentRoot(f);
						IPackageFragment fragment = rootFolder
								.createPackageFragment(c.getTypes()[0]
										.getPackageFragment().getElementName(),
										true, null);
						save(fragment, className, b.toString());

					} else {
						save(c.getTypes()[0].getPackageFragment(), className,
								b.toString());
					}
				} catch (Exception e) {
					setLog(TYPES.GENERATOR, "Generating code failed!");
					e.printStackTrace();
				}
			}
		} else {
			
			setLog(TYPES.GENERATOR, "No methods found!");
		}
	}

	protected void save(IPackageFragment pack, String name, String contents) {

		contents = Utils.formatCode(contents);
		if (contents != null) {
			ICompilationUnit cTest;
			try {
				cTest = pack.createCompilationUnit(name, contents, true, null);
				generatedUnit = cTest;
			} catch (JavaModelException e) {
				setLog(TYPES.GENERATOR, "Creating compilation unit failed!");
				e.printStackTrace();
			}

		} else {
			setLog(TYPES.GENERATOR, "Formatting code failed!");
		}

	}

	protected void createClassFooter(StringBuilder b) {
		b.append("}");
	}

	protected String createClassHeader(AnalyzedClass res) {
		StringBuilder b = new StringBuilder();
		if (res.getPackageName() != null) {
			if (!res.getPackageName().isEmpty()) {
				b.append("package " + res.getPackageName() + ";\n\n");
			}

		}
		b.append("import static org.junit.Assert.*;\n\n");
		b.append("import org.junit.*;\n\n");

		b.append("public class " + res.getClassName()
				+ Constants.testClassPostfix + " {\n\n");
		b.append("\n protected transient " + res.getQualifiedClassName() + " "
				+ Constants.classObjectName + " = null;\n");
		b.append(createSetUpBeforeClassMethod(null));
		b.append(createTearDownBeforeClassMethod(null));

		StringBuilder tmp = new StringBuilder();
		defaultValueGenerator.generateInvocationValue(
				res.getTestedClassObjectForMethodsCalling(), tmp);
		String invocationCode = (!tmp.toString().endsWith(";") ? tmp.toString()
				+ ";" : tmp.toString());
		b.append(createSetUpMethod((res.getTestedClassObjectForMethodsCalling()
				.isFailed() ? "fail(\""
				+ res.getTestedClassObjectForMethodsCalling().getFailMsg()
				+ "\");" : Constants.classObjectName + " = " + invocationCode)));
		b.append(createTearDownMethod(null));
		return b.toString();
	}

	protected String createSetUpMethod(String text) {
		return "@Before\n	public void setUp() throws Exception {\n"
				+ (text != null ? text : "") + "\n	}\n";
	}

	protected String createTearDownMethod(String text) {
		return "@After\n	public void tearDown() throws Exception {\n"
				+ (text != null ? text : "") + "\n	}\n\n";
	}

	protected String createTearDownBeforeClassMethod(String text) {
		return "@AfterClass\n	public static void tearDownAfterClass() throws Exception {\n"
				+ (text != null ? text : "") + "	}\n\n	";
	}

	protected String createSetUpBeforeClassMethod(String text) {
		return "@BeforeClass\n	public static void setUpBeforeClass() throws Exception {\n"
				+ (text != null ? text : "") + "	}\n\n	";

	}

	protected String getTestMethod(String name, String body) {
		StringBuilder t = new StringBuilder();
		t.append("\n@Test(timeout=60000)\npublic void " + name + "(){\n");
		t.append(body);
		t.append("\n}\n");
		return t.toString();
	}
}
