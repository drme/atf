package lt.atgplugin.wizards.pages;

import java.util.ArrayList;
import java.util.List;

import lt.atgplugin.filters.ConstructorFilter;
import lt.atgplugin.filters.ATGOption;
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

public class ConstructorsPage extends WizardPage {
	public ConstructorFilter getDefault() {
		DefaultOptions.setDefaultConstructor(sar.getSelectedItem());
		return (ConstructorFilter) sar.getSelectedItem();
	}

	public List<ConstructorFilter> getSelectedOptions() {
		DefaultOptions.setDefaultConstructors(table.getSelectedItems());
		List<ConstructorFilter> tmp = new ArrayList<ConstructorFilter>(0);
		for (ATGOption o : table.getSelectedItems()) {
			tmp.add((ConstructorFilter) o);
		}
		return tmp;
	}

	protected ATGTable table = null;
	protected ATGList sar = null;
	protected Shell shell;

	/**
	 * Constructor for SampleNewWizardPage.
	 * 
	 * @param pageName
	 */
	public ConstructorsPage(ISelection selection, Shell shell) {

		super("wizardPage");
		setTitle("Constructors options");
		setDescription("Please select constructor filters and default contructor for object instantiation.");
		this.shell = shell;
	}

	public ConstructorsPage() {
		super("wizardPage");
		setTitle("Constructors options");
		setDescription("Please select constructor filters and default contructor for object instantiation.");
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
		createElement(gridData, container, "&Select Constructor filters:");
		dialogChanged();
		setControl(container);
	}

	private void createElement(GridData gridData, Composite container,
			String name) {
		Label label = new Label(container, SWT.NULL);
		label.setText(name);
		table = new ATGTable(this.getShell(), container, SWT.CHECK | SWT.BORDER
				| SWT.V_SCROLL | SWT.H_SCROLL | SWT.FULL_SELECTION,
				DefaultOptions.getConstructors(), DefaultOptions.getDefaultConstructors());
		table.getTable().addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (table.getSelectedItems().size() == 0) {
					updateStatus("At least one table element should be selected.");
				}else {
					updateStatus(null);
				}
			}
		});
		sar = new ATGList(this.getShell(), container, SWT.CHECK | SWT.BORDER
				| SWT.V_SCROLL | SWT.H_SCROLL | SWT.FULL_SELECTION,
				DefaultOptions.getConstructors(),
				"Select default constructor filter, which will be used for for tested class invocation object generation, for instance test = new String(\"test\"); and so on:",
				DefaultOptions.getDefaultConstructor());

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