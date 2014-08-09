package lt.atgplugin.filters;

import lt.atgplugin.wizards.helpers.ATGDialog;

import org.eclipse.swt.widgets.Shell;

/**
 * Wrapper class between ATGPlugin and SWT interface.
 * 
 * @author greta
 * 
 */
public interface ATGOption {

	/**
	 * Methid to get full name of this option.
	 * 
	 * @return name
	 */
	public String getName();

	/**
	 * Method to get full description of this option.
	 * 
	 * @return description
	 */
	public String getDescription();

	/**
	 * Method to get short name of this option.
	 * 
	 * @return short name of option
	 */
	public String getShortName();

	/**
	 * Checks if this option need some additional dynamic configuration.
	 * 
	 * @return true if this options needs to be changed
	 */
	public boolean isConfigurable();

	/**
	 * This method might be used if there is need for special filters/rules
	 * dynamic changing, for instance setting of various parameters and so on.
	 * 
	 * @param shell
	 *            - parent
	 * @return - created dialog, if null - no dialog is displayed
	 */
	public ATGDialog getDialog(Shell shell);

}
