package lt.atgplugin.wizards;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import lt.atgplugin.filters.AbstractClassFilter;
import lt.atgplugin.filters.ConstructorFilter;
import lt.atgplugin.filters.EnumFilter;
import lt.atgplugin.filters.InterfaceFilter;
import lt.atgplugin.generator.rules.Rule;
import lt.atgplugin.wizards.pages.AbstractClassesPage;
import lt.atgplugin.wizards.pages.ConstantsPage;
import lt.atgplugin.wizards.pages.ConstructorsPage;
import lt.atgplugin.wizards.pages.EnumsPage;
import lt.atgplugin.wizards.pages.InterfacesPage;
import lt.atgplugin.wizards.pages.RulesPage;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;

/**
 * This is a sample new wizard. Its role is to create a new file resource in the
 * provided container. If the container resource (a folder or a project) is
 * selected in the workspace when the wizard is opened, it will accept it as the
 * target container. The wizard creates one file with the extension "java". If a
 * sample multi-page editor (also available as a template) is registered for the
 * same extension, it will be able to open it.
 */

public class GenerateTestWizard extends Wizard implements INewWizard {
	private RulesPage rPage;
	ConstructorsPage cPage;
	AbstractClassesPage aPage;
	InterfacesPage iPage;
	EnumsPage ePage;
	ConstantsPage constants;

	public ISelection selection;
	Shell shell;

	/**
	 * Constructor for GenerateTestWizard.
	 */
	public GenerateTestWizard(Shell shell) {
		super();
		setWindowTitle("Generate Automatic tests");
		setNeedsProgressMonitor(true);
		this.shell = shell;

	}

	/**
	 * Adding the page to the wizard.
	 */

	public void addPages() {

		rPage = new RulesPage(selection, shell);
		cPage = new ConstructorsPage(selection, shell);
		constants = new ConstantsPage(selection);
		aPage = new AbstractClassesPage(selection, shell);
		ePage = new EnumsPage(selection, shell);
		iPage = new InterfacesPage(selection, shell);
		addPage(rPage);
		addPage(cPage);
		addPage(aPage);
		addPage(iPage);
		addPage(ePage);
		addPage(constants);

	}

	/**
	 * This method is called when 'Finish' button is pressed in the wizard. We
	 * will create an operation and run it using wizard as execution context.
	 */
	public boolean performFinish() {

		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor)
					throws InvocationTargetException {
				try {
					doFinish(monitor);
				} catch (CoreException e) {
					throw new InvocationTargetException(e);
				} finally {
					monitor.done();
				}
			}
		};
		
		try {
			getContainer().run(true, false, op);
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			Throwable realException = e.getTargetException();
			MessageDialog.openError(getShell(), "Error",
					realException.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * The worker method. It will find the container, create the file if missing
	 * or just replace its contents, and open the editor on the newly created
	 * file.
	 */
	@SuppressWarnings("unused")
	private void doFinish(IProgressMonitor monitor) throws CoreException {
		monitor.beginTask("Setting options", 1);
		List<Rule> rules = rPage.getSelectedOptions();
		Rule dRule = rPage.getDefault();
		List<ConstructorFilter> cons = cPage.getSelectedOptions();
		ConstructorFilter dConstructorFilter = cPage.getDefault();
		List<InterfaceFilter> interfaces = iPage.getSelectedOptions();
		InterfaceFilter di = iPage.getDefault();
		
		List<AbstractClassFilter> aclasses = aPage.getSelectedOptions();
		AbstractClassFilter a = aPage.getDefault();
		
		List<EnumFilter> enums = ePage.getSelectedOptions();
		monitor.done();
	}

	/**
	 * We will accept the selection in the workbench to see if we can initialize
	 * from it.
	 * 
	 * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}
}