package lt.atgplugin.filters;

import lt.atgplugin.wizards.helpers.ATGDialog;

import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.swt.widgets.Shell;

public class EnumFilterImpl extends EnumFilter {

	@Override
	public String getValue(IType type) {
		// returns first found enum constant
		try {
			for (IField f : type.getFields()) {
				if (f.isEnumConstant()) {
					
					return type.getFullyQualifiedName().replace("$", ".") + "."
							+ f.getElementName();
				}
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		return "null";
	}

	@Override
	public String getName() {
		return "Default enum filter";
	}

	@Override
	public String getDescription() {
		return "This filter takes first found enum constant, if none is found - null is used as parameter";
	}

	@Override
	public String getShortName() {
		return "DEF";
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
