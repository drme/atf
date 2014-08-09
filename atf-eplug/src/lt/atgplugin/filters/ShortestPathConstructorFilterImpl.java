package lt.atgplugin.filters;

import java.util.List;

import lt.atgplugin.wizards.helpers.ATGDialog;

import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.swt.widgets.Shell;

public class ShortestPathConstructorFilterImpl extends ConstructorFilter {

	@Override
	protected IMethod getChosenConstructor(IType type,
			List<IMethod> constructors) {

		int count = 9999;
		IMethod res = null;
		for (IMethod m : constructors) {
			try {
				// check if constructor is not private
				if (!Flags.isPrivate(m.getFlags())
						&& !Flags.isPackageDefault(m.getFlags())) {
					if (count > m.getParameterTypes().length) {
						count = m.getParameterTypes().length;
						res = m;
					}
				}
			} catch (JavaModelException e) {
				e.printStackTrace();
			}
		}

		return res;
	}

	@Override
	public String getName() {
		return "Default constructor filter";
	}

	@Override
	public String getDescription() {
		return "This filter takes first constructor with least number of parameters";
	}

	@Override
	public String getShortName() {
		return "DCF";
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
