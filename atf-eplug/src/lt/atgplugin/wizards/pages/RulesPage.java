package lt.atgplugin.wizards.pages;

import java.util.ArrayList;
import java.util.List;

import lt.atgplugin.filters.ATGOption;
import lt.atgplugin.generator.rules.Rule;
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

public class RulesPage extends WizardPage {

	public Rule getDefault() {
		DefaultOptions.setDefaultRule(sar.getSelectedItem());
		return (Rule) sar.getSelectedItem();
	}

	public List<Rule> getSelectedOptions() {
		DefaultOptions.setDefaultRules(table.getSelectedItems());
		List<Rule> tmp = new ArrayList<Rule>(0);
		for (ATGOption o : table.getSelectedItems()) {
			tmp.add((Rule) o);
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
	public RulesPage(ISelection selection, Shell shell) {

		super("wizardPage");

		setTitle("Rules options");
		setDescription("Please select default rules for tests generation.");
		this.shell = shell;
	}

	public RulesPage() {
		super("wizardPage");
		setTitle("Rules options");
		setDescription("Please select default rules for tests generation.");
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

		createElement(gridData, container);
		dialogChanged();
		setControl(container);
	}

	private void createElement(GridData gridData, Composite container) {
		Label label = new Label(container, SWT.NULL);
		label.setText("&Select Rules, which will be applied to tested methods, and used to call them:");
		table = new ATGTable(this.getShell(), container, SWT.CHECK | SWT.BORDER
				| SWT.V_SCROLL | SWT.H_SCROLL | SWT.FULL_SELECTION,
				DefaultOptions.getRules(), DefaultOptions.getDefaultRules());
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
				DefaultOptions.getRules(),
				"Select default rule, which will be used for tested class invocation object generation, for instance test = new String(\"test\"); and so on:",
				DefaultOptions.getDefaultRule());
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