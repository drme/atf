package lt.atgplugin.generator.rules;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lt.atgplugin.cache.RulesCache;
import lt.atgplugin.results.Param;
import lt.atgplugin.wizards.helpers.ATGDialog;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.swt.widgets.Shell;

public class NullTestingRule extends Rule {

	@Override
	public List<String> getFilteredTestMethodBodies(IJavaProject project,
			Param analyzed, Map<String, List<Param>> elements, Param main) {
		List<String> sar = new ArrayList<String>();

		StringBuilder invocationCode = new StringBuilder();
		generateInvocationValue(analyzed, invocationCode);
		sar.add("try {\n " + invocationCode.toString() + ";"
				+ "\n }\n catch (Exception e) {\n fail(e.getMessage());\n}");

		return sar;

	}

	static SecureRandom random = new SecureRandom();

	@Override
	protected String getValue(Param p) {
		String name = p.getQualifiedName();

		if (name.contains("double")) {

			return new Double(random.nextDouble() * 100).toString();
		}
		if (name.contains("boolean")) {
			return new Boolean(random.nextBoolean()).toString();

		}
		if (name.contains("byte")) {

			return new Integer(random.nextInt(128)).toString();

		}
		if (name.contains("char")) {
			return "'4'";

		}
		if (name.contains("short")) {
			return new Integer(random.nextInt(500)).toString();
		}
		if (name.contains("int")) {
			return new Integer(random.nextInt()).toString();
		}
		if (name.contains("long")) {
			return new Long(random.nextLong()).toString() + "l";
		}
		if (name.contains("float")) {
			return new Float(random.nextFloat()).toString() + "f";

		}

		return "\"null\"";
	}

	@Override
	public String getDescription() {
		return "This rule is used for testing methods when using null values. ";

	}

	@Override
	public boolean isFiltered(Param p) {
		return true;
	}

	@Override
	public String getName() {
		return "Null testing rule";
	}

	@Override
	public String getShortName() {
		return "NT";
	}

	@Override
	public boolean isOverriden(Param p) {
		return (!p.isArray() && RulesCache.contains(p.getQualifiedName()));
	}

	@Override
	public boolean isConfigurable() {
		return false;
	}

	@Override
	public ATGDialog getDialog(Shell shell) {
		return null;
	}
}
