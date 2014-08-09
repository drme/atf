package lt.atgplugin.generator.rules;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lt.atgplugin.cache.RulesCache;
import lt.atgplugin.results.Param;
import lt.atgplugin.wizards.helpers.ATGDialog;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.swt.widgets.Shell;

public class SettersTestingRule extends Rule {

	@Override
	public List<String> getFilteredTestMethodBodies(IJavaProject project,
			Param analyzed, Map<String, List<Param>> elements, Param main) {

		List<String> sar = new ArrayList<String>();
		{
			StringBuilder invocationCode = new StringBuilder();
			generateInvocationValue(analyzed, invocationCode);
			sar.add("try {\n " + invocationCode.toString() + ";"
					+ "\n }\n catch (Exception e) {\n fail(e.getMessage());\n}");

		}
		return sar;
	}

	@Override
	protected String getValue(Param p) {
		return RulesCache.getValue(p.getQualifiedName());
	}

	@Override
	public String getDescription() {
		return "This rule is used for testing setters. ";
	}

	@Override
	public boolean isFiltered(Param p) {
		if (p.getQualifiedName().contains(".set")) {
			return true;
		}
		return false;
	}

	@Override
	public String getName() {
		return "Setters testing rule";
	}

	@Override
	public String getShortName() {
		return "ST";
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
