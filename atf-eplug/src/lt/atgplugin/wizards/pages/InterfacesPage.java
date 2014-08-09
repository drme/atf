package lt.atgplugin.wizards.pages;

import java.util.ArrayList;
import java.util.List;

import lt.atgplugin.filters.ATGOption;
import lt.atgplugin.filters.InterfaceFilter;
import lt.atgplugin.wizards.helpers.ATGList;
import lt.atgplugin.wizards.helpers.ATGTable;
import lt.atgplugin.wizards.helpers.DefaultOptions;

import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class InterfacesPage extends WizardPage {

	public List<InterfaceFilter> getSelectedOptions() {
		DefaultOptions.setDefaultInterfaces(table.getSelectedItems());
		List<InterfaceFilter> tmp = new ArrayList<InterfaceFilter>(0);
		for (ATGOption o : table.getSelectedItems()) {
			tmp.add((InterfaceFilter) o);
		}
		return tmp;
	}

	public InterfaceFilter getDefault() {
		DefaultOptions.setDefaultInterface(sar.getSelectedItem());
		return (InterfaceFilter) sar.getSelectedItem();
	}

	protected ATGList sar = null;
	protected ATGTable table = null;

	/**
	 * Constructor for SampleNewWizardPage.
	 * 
	 * @param pageName
	 */
	public InterfacesPage(ISelection selection, Shell shell) {

		super("wizardPage");
		setTitle("Interfaces options");
		setDescription("Please select interfaces filters.");
	}

	public InterfacesPage() {
		super("wizardPage");
		setTitle("Interfaces options");
		setDescription("Please select interfaces filters.");
	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {

		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessVerticalSpace = true;
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 1;
		layout.verticalSpacing = 9;

		createElement(gridData, container, "&Select Interfaces filters:");
		dialogChanged();
		setControl(container);
	}

	private void createElement(GridData gridData, Composite container,
			String name) {
		Label label = new Label(container, SWT.NULL);
		label.setText(name);
		table = new ATGTable(this.getShell(), container, SWT.CHECK | SWT.BORDER
				| SWT.V_SCROLL | SWT.H_SCROLL | SWT.FULL_SELECTION,
				DefaultOptions.getInterfaces(),
				DefaultOptions.getDefaultInterfaces());
		table.getTable().addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (table.getSelectedItems().size() == 0) {
					updateStatus("At least one table element should be selected.");
				} else {
					updateStatus(null);
				}
			}
		});
		sar = new ATGList(
				this.getShell(),
				container,
				SWT.CHECK | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL
						| SWT.FULL_SELECTION,
				DefaultOptions.getInterfaces(),
				"Select default interface filter, which will be used for tested class invocation object generation:",
				DefaultOptions.getDefaultInterface());
	}

	/**
	 * Ensures that both text fields are set.
	 */

	private void dialogChanged() {
		updateStatus(null);
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

}