package lt.atgplugin.filters;

import java.util.List;

import lt.atgplugin.utils.Constants;
import lt.atgplugin.wizards.helpers.ATGDialog;

import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.swt.widgets.Shell;

public class InterfaceFilterImpl extends InterfaceFilter {

	@Override
	protected IType getRealizedInterface(IJavaProject project, IType main,
			List<IType> list) {
		IType res = null;
		// checks if list contains class
		if (Constants.useCache
				&& cache.containsKey(main.getFullyQualifiedName())) {
			try {
				res = project.findType(cache.get(main.getFullyQualifiedName()));
				return res;
			} catch (JavaModelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		// takes first found class from list, from found realizing classes
		for (IType t : list) {
			try {

				if (Flags.isPublic(t.getFlags())) {
					if (Constants.useCache) {
						cache.put(main.getFullyQualifiedName(),
								t.getFullyQualifiedName());
					}
					return t;

				}
			} catch (JavaModelException e) {
				e.printStackTrace();
			}
		}

		return res;
	}

	@Override
	public String getName() {
		return "Default interfaces filter";
	}

	@Override
	public String getDescription() {
		return "This filter takes first found realizing class, if none is found - null is used as parameter";
	}

	@Override
	public String getShortName() {
		return "DFF";
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
