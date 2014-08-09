package lt.atgplugin.filters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lt.atgplugin.utils.Constants;
import lt.atgplugin.utils.Utils;

import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;

/**
 * Abstract class used for constructor filtering.
 * 
 * @author greta
 * 
 */
public abstract class ConstructorFilter implements ATGOption {
	protected abstract IMethod getChosenConstructor(IType type,
			List<IMethod> constructors);

	/**
	 * Method for finding constructor method for class.
	 * 
	 * @param mSar
	 *            - method list from which to search
	 * @return - MethodDeclaration containing method which will be used as
	 *         constructor
	 */

	public IMethod getConstructor(IType tipas, String packageName) {
		IMethod res = null;
		Utils.log("[START] Starting constructing constructor for class "
				+ tipas.getFullyQualifiedName());
		if (Constants.useCache
				&& methodCache.containsKey(tipas.getFullyQualifiedName())) {
			res = methodCache.get(tipas.getFullyQualifiedName());
		} else {
			List<IMethod> constructors = Utils.getAllConstructors(tipas,
					packageName, true, true, true);
			res = getChosenConstructor(tipas, constructors);
			if (res != null) {
				methodCache.put(tipas.getFullyQualifiedName(), res);
			}
		}
		if (res != null) {
			Utils.log("[END] Finished constructing constructor for class "
					+ tipas.getFullyQualifiedName()
					+ " will use constructor with "
					+ res.getParameterTypes().length + " parameters");
		} else {
			Utils.log("[END] Finished constructing constructor for class "
					+ tipas.getFullyQualifiedName());
		}
		return res;
	}

	static Map<String, IMethod> methodCache = new HashMap<String, IMethod>(0);
}
