package lt.atgplugin.wizards.pages;

import lt.atgplugin.utils.Constants;

import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class ConstantsPage extends WizardPage {

	/**
	 * Constructor for SampleNewWizardPage.
	 * 
	 * @param pageName
	 */
	public ConstantsPage(ISelection selection) {
		super("wizardPage");
		setTitle("Options");
		setDescription("Please select default options for tests generation.");
	}

	public ConstantsPage() {
		super("wizardPage");
		setTitle("Options");
		setDescription("Please select default options for tests generation.");
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
		layout.verticalSpacing = 2;
		layout.horizontalSpacing = 2;

		{
			Label label = new Label(container, SWT.NULL);
			label.setText("&Enter Depth:");
			final Text text = new Text(container, SWT.SINGLE | SWT.BORDER);
			text.setTextLimit(30);
			text.setText(Constants.depth + "");
			text.addModifyListener(new ModifyListener() {
				public void modifyText(ModifyEvent e) {
					if (text.getText().isEmpty()) {
						updateStatus("Please enter number.");
					} else {
						updateStatus(null);
						Constants.depth = Integer.parseInt(text.getText());

					}

				}
			});
		}
		{
			Label label = new Label(container, SWT.NULL);
			label.setText("&Enter Test class postfix, for instance, if \"Test\" - it will be used as DataTest:");
			final Text text = new Text(container, SWT.SINGLE | SWT.BORDER);
			text.setTextLimit(30);
			text.setText(Constants.testClassPostfix);
			text.setLayoutData(gridData);
			text.addModifyListener(new ModifyListener() {
				public void modifyText(ModifyEvent e) {
					if (text.getText().isEmpty()
							|| Character.isDigit(text.getText().indexOf(0, 1))) {
						updateStatus("Please enter valid class postfix name, containing at least 1 character.");
					} else {
						updateStatus(null);
						Constants.testClassPostfix = text.getText();

					}

				}
			});
		}
		{
			Label label = new Label(container, SWT.NULL);
			label.setText("&Enter test methods prefix, for instance, if \"test\" - it will be used as testMethod:");
			final Text text = new Text(container, SWT.SINGLE | SWT.BORDER);
			text.setTextLimit(30);
			text.setText(Constants.testMethodPrefix);
			text.setLayoutData(gridData);
			text.addModifyListener(new ModifyListener() {
				public void modifyText(ModifyEvent e) {
					if (text.getText().isEmpty()) {
						updateStatus("Please enter valid method prefix name.");
					} else {
						if (Character.isDigit(text.getText().indexOf(0, 1))) {
							updateStatus("Please enter valid method prefix name NOT starting with number.");
						} else {
							updateStatus(null);
							Constants.testMethodPrefix = text.getText();
						}
					}

				}
			});
		}
		{
			Label label = new Label(container, SWT.NULL);
			label.setText("&Enter tested class instance name:");
			final Text text = new Text(container, SWT.SINGLE | SWT.BORDER);
			text.setTextLimit(30);
			text.setText(Constants.classObjectName);
			text.addModifyListener(new ModifyListener() {
				public void modifyText(ModifyEvent e) {

					if (text.getText().isEmpty()) {
						updateStatus("Please enter valid class instance object name, containing at least 1 character.");
					} else {
						updateStatus(null);
						Constants.classObjectName = text.getText();

					}

				}
			});
			text.setLayoutData(gridData);
		}
		{
			Label separator = new Label(container, SWT.SEPARATOR
					| SWT.HORIZONTAL);
			separator.setLayoutData(gridData);
			final Button button = new Button(container, SWT.CHECK);
			button.setText("Select flag if save result to different folder");
			button.setSelection(Constants.differentFolder);

			Label pathLabel = new Label(container, SWT.NULL);
			pathLabel.setText("&Enter path where to save tests:");
			final Text text = new Text(container, SWT.SINGLE | SWT.BORDER);
			text.setEnabled(Constants.differentFolder);
			text.setTextLimit(30);
			text.setText(Constants.folderName);
			text.addModifyListener(new ModifyListener() {
				public void modifyText(ModifyEvent e) {

					if (text.getText().isEmpty()) {
						updateStatus("Please enter valid folder.");
					} else {
						updateStatus(null);
						Constants.folderName = text.getText();

					}

				}
			});
			text.setLayoutData(gridData);
			button.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					Constants.differentFolder = button.getSelection();
					if (button.getSelection() == true) {
						text.setEnabled(true);
					} else {
						text.setEnabled(false);
					}
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {

				}
			});
		}
		{
			Label separator = new Label(container, SWT.SEPARATOR
					| SWT.HORIZONTAL);
			separator.setLayoutData(gridData);
			final Button button = new Button(container, SWT.CHECK);
			button.setText("Select flag if check type hierarchy");
			button.setSelection(Constants.searchParents);

			Label pathLabel = new Label(container, SWT.NULL);
			pathLabel
					.setText("&Enter level how deep to search, if 0 - indefinite level will be used:");
			final Text text = new Text(container, SWT.SINGLE | SWT.BORDER);
			text.setEnabled(Constants.searchParents);
			text.setTextLimit(30);
			text.setText(Constants.parentSearchLevel + "");
			text.addModifyListener(new ModifyListener() {
				public void modifyText(ModifyEvent e) {

					if (text.getText().isEmpty()
							|| (Integer.parseInt(text.getText()) < 0)) {
						updateStatus("Please enter value >= 0.");
					} else {
						updateStatus(null);
						Constants.parentSearchLevel = Integer.parseInt(text
								.getText());
					}

				}
			});
			text.setLayoutData(gridData);
			button.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					Constants.searchParents = button.getSelection();
					if (button.getSelection() == true) {
						text.setEnabled(true);
					} else {
						text.setEnabled(false);
					}
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {

				}
			});
		}

		{
			Label separator = new Label(container, SWT.SEPARATOR
					| SWT.HORIZONTAL);
			separator.setLayoutData(gridData);
			final Button button = new Button(container, SWT.CHECK);
			button.setText("Select flag if there is need for displaying performance results.");
			button.setSelection(Constants.showTimes);
			button.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					Constants.showTimes = button.getSelection();

				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {

				}
			});

		}
		dialogChanged();
		setControl(container);
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