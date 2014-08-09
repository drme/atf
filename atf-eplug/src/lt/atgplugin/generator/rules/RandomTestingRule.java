package lt.atgplugin.generator.rules;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lt.atgplugin.results.Param;
import lt.atgplugin.wizards.helpers.ATGDialog;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.swt.widgets.Shell;

public class RandomTestingRule extends Rule {

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

		if (name.contains("String")) {
			return "\"" + new BigInteger(130, random).toString(32) + "\"";
		}
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
		if (name.toLowerCase().contains("string")) {
			return "\"test\"";
		}
		if (name.contains("java.io.File")) {
			return "new java.io.File(\"test.txt\")";
		}
		if (name.contains("java.net.URL")) {
			return "new java.net.URL(\"http://www.google.com\")";
		}
		if (name.contains("java.awt.Component")) {
			return "new javax.swing.JLabel(\"test\")";
		}
		if (name.contains("java.math.BigDecimal")) {
			return "new java.math.BigDecimal("
					+ (new Float(random.nextFloat()).toString() + "f") + ")";
		}
		if (name.contains("java.math.BigInteger")) {
			return "new java.math.BigInteger(\""
					+ new Integer(random.nextInt()).toString() + "\")";
		}
		return "null";
	}

	@Override
	public String getDescription() {
		return "This rule is used for testing methods when calling random values. ";

	}

	@Override
	public boolean isFiltered(Param p) {

		return true;
	}

	@Override
	public String getName() {
		return "Random testing rule";
	}

	@Override
	public String getShortName() {
		return "RT";
	}

	@Override
	public boolean isOverriden(Param p) {
		return (!p.isArray() && ((p.getQualifiedName().equals(
				"java.lang.String") && !p.isArray())
				|| (p.getQualifiedName().equals("java.io.File") && !p.isArray())
				|| (p.getQualifiedName().equals("java.awt.Component") && !p
						.isArray())
				|| (p.getQualifiedName().equals("java.net.URL") && !p.isArray())
				|| (p.getQualifiedName().equals("java.math.BigInteger") && !p
						.isArray()) || (p.getQualifiedName().equals(
				"java.math.BigDecimal") && !p.isArray())));
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
