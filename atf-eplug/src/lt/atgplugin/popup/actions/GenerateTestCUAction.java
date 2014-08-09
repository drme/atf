package lt.atgplugin.popup.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import lt.atgplugin.utils.Constants;
import lt.atgplugin.wizards.GenerateTestWizard;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.core.PackageFragment;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

@SuppressWarnings("restriction")
public class GenerateTestCUAction implements IObjectActionDelegate {

	private Shell shell;
	List<ICompilationUnit> list = new ArrayList<ICompilationUnit>();

	/**
	 * Constructor for Action1.
	 */
	public GenerateTestCUAction() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();

	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
		GenerateTestWizard wizard = new GenerateTestWizard(shell);
		wizard.init(null, null);
		WizardDialog dialog = new WizardDialog(shell, wizard) {
			@Override
			protected void configureShell(Shell newShell) {
				super.configureShell(newShell);
				newShell.setSize(newShell.getSize().x, 600);
			}
		};
		dialog.create();
		dialog.open();
		if (dialog.getReturnCode() == 0) {
			MainGeneratorAction m = new MainGeneratorAction(list, shell);

			if (Constants.showTimes) {
				MessageDialog.openWarning(shell, "Performance",
						m.getPerformanceTimes());
				if (m.isAnyError()) {
					MessageDialog.openWarning(shell,
							"Not all tests were generated", m.getFailedClasses());
				} else {
					MessageDialog.openInformation(shell, "Tests were generated",
							"Tests were generated successfully!");

				}
			}
		
			list.clear();

		}
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof TreeSelection) {
			org.eclipse.jface.viewers.TreeSelection t = (TreeSelection) selection;
			Iterator<?> it = t.iterator();
			while (it.hasNext()) {
				Object o = it.next();
				if (o instanceof ICompilationUnit) {
					ICompilationUnit c = (ICompilationUnit) o;
					list.add(c);
				} else {
					if (o instanceof IPackageFragmentRoot) {
						IPackageFragmentRoot pack = (IPackageFragmentRoot) o;
						try {
							for (IJavaElement e : pack.getChildren()) {
								System.out.println(e.getClass());
								if (e instanceof PackageFragment) {
									PackageFragment p = (PackageFragment) e;
									list.addAll(Arrays.asList(p
											.getCompilationUnits()));
								}

							}
						} catch (JavaModelException e) {
							e.printStackTrace();
						}
					} else {
						if (o instanceof IPackageFragment) {
							IPackageFragment fragment = (IPackageFragment) o;
							try {
								list.addAll(Arrays.asList(fragment
										.getCompilationUnits()));
							} catch (JavaModelException e) {
								e.printStackTrace();
							}

						}
					}
				}
			}
		}
	}
}
