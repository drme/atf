package lt.atgplugin.generator.rules;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lt.atgplugin.cache.RulesCache;
import lt.atgplugin.results.Param;
import lt.atgplugin.wizards.helpers.ATGDialog;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.swt.widgets.Shell;

public class DefaultInvocationRule extends Rule {

	@Override
	public List<String> getFilteredTestMethodBodies(IJavaProject project,
			Param analyzed, Map<String, List<Param>> elements, Param main) {
		return new ArrayList<String>();
	}

	@Override
	protected String getValue(Param p) {
		return RulesCache.getValue(p.getQualifiedName());
	}

	@Override
	public String getDescription() {
		return "This rule might be used for object values invocation. ";
	}

	@Override
	public boolean isFiltered(Param p) {
		return false;
	}

	@Override
	public String getName() {
		return "Default object invocation generation rule";
	}

	@Override
	public String getShortName() {
		return "OG";
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
