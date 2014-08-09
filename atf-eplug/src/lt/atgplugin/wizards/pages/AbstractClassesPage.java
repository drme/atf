package lt.atgplugin.wizards.pages;

import java.util.ArrayList;
import java.util.List;

import lt.atgplugin.filters.ATGOption;
import lt.atgplugin.filters.AbstractClassFilter;
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

public class AbstractClassesPage extends WizardPage {

	public AbstractClassFilter getDefault() {
		DefaultOptions.setDefaultAbtractClassFilter(sar.getSelectedItem());
		return (AbstractClassFilter) sar.getSelectedItem();
	}

	public List<AbstractClassFilter> getSelectedOptions() {
		DefaultOptions.setDefaultAclasses(table.getSelectedItems());
		List<AbstractClassFilter> tmp = new ArrayList<AbstractClassFilter>(0);
		for (ATGOption o : table.getSelectedItems()) {
			tmp.add((AbstractClassFilter) o);
		}
		return tmp;
	}

	protected ATGTable table = null;
	protected ATGList sar = null;

	/**
	 * Constructor for SampleNewWizardPage.
	 * 
	 * @param pageName
	 */
	public AbstractClassesPage(ISelection selection, Shell shell) {

		super("wizardPage");
		setTitle("Abstract clasess options");
		setDescription("Please select abstract classes filters.");
	}

	public AbstractClassesPage() {
		super("wizardPage");
		setTitle("Abstract clasess options");
		setDescription("Please select abstract classes filters.");
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

		createElement(gridData, container,
				"&Select Abstract classes filters:\n");

		dialogChanged();
		setControl(container);
	}

	private void createElement(GridData gridData, Composite container,
			String name) {
		Label label = new Label(container, SWT.NULL);
		label.setText(name);
		table = new ATGTable(this.getShell(), container, SWT.CHECK | SWT.BORDER
				| SWT.V_SCROLL | SWT.H_SCROLL | SWT.FULL_SELECTION,
				DefaultOptions.getAbstractFilters(),
				DefaultOptions.getDefaultAclasses());

		table.getTable().addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (table.getSelectedItems().size() == 0) {
					updateStatus("At least one table element should be selected.");
				}else {
					updateStatus(null);
				}
			}
		});
		sar = new ATGList(
				this.getShell(),
				container,
				SWT.CHECK | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL
						| SWT.FULL_SELECTION,
				DefaultOptions.getAbstractFilters(),
				"Select default abstract class filter, which will be used for tested class invocation object generation:",
				DefaultOptions.getDefaultAbtractClassFilter());
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