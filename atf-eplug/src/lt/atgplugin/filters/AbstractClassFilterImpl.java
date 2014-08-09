package lt.atgplugin.filters;

import java.util.List;

import lt.atgplugin.utils.Constants;
import lt.atgplugin.utils.Utils;
import lt.atgplugin.wizards.helpers.ATGDialog;

import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.swt.widgets.Shell;

/**
 * Default implementation for abstract classes filter.
 * 
 * @author greta
 * 
 */
public class AbstractClassFilterImpl extends AbstractClassFilter {

	@Override
	protected IType getRealizedClass(IJavaProject project, IType main,
			List<IType> list) {
		String name = main.getFullyQualifiedName();
		if (Constants.useCache && cache.containsKey(name)) {
			try {
				String tmp = cache.get(name);
				Utils.log("[SEARCH] Found class in cache " + tmp);
				return project.findType(tmp);
			} catch (JavaModelException e) {

				e.printStackTrace();
				return null;
			}
		}

		for (int i = 0; i < list.size(); i++) {
			try {
				if (!Flags.isAbstract(list.get(i).getFlags())
						&& Flags.isPublic(list.get(i).getFlags())) {
					Utils.log("[SEARCH] Found first not abstract class in "
							+ list.get(i).getFullyQualifiedName());
					if (Constants.useCache) {
						cache.put(name, list.get(i).getFullyQualifiedName());
					}
					return list.get(i);
				}
			} catch (JavaModelException e) {
				e.printStackTrace();
			}

		}
		return null;
	}

	@Override
	public String getName() {
		return "Default abstract class filter";
	}

	@Override
	public String getDescription() {
		return "This filter takes first found realizing class, if none is found - null is used as parameter";
	}

	@Override
	public String getShortName() {
		return "DACF";
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
