package lt.atgplugin.filters;

import org.eclipse.jdt.core.IType;

/**
 * Class used for enumeration checking.
 * 
 * @author greta
 * 
 */
public abstract class EnumFilter implements ATGOption {
	/**
	 * Return enum constant value.
	 * 
	 * @param type
	 *            - for which to check
	 * @return constant
	 */
	public abstract String getValue(IType type);

}
