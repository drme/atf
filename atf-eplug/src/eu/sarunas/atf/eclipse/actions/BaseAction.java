package eu.sarunas.atf.eclipse.actions;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.progress.IProgressConstants;
import eu.sarunas.projects.atf.eclipse.actions.TypeNameExtractor;

/**
 * Base class for eclipse plug-in actions. The action is run as a job with a progress indicator.
 */
public abstract class BaseAction implements IObjectActionDelegate
{
	/**
	 * Constructs base action object.
	 * 
	 * @param taskName task name.
	 */
	protected BaseAction(String taskName)
	{
		this.taskName = taskName;
	};

	public void setActivePart(IAction action, IWorkbenchPart targetPart)
	{
		this.targetPart = targetPart;
	};

	public void selectionChanged(IAction action, ISelection selection)
	{
		this.fqn = null;

		if (selection instanceof StructuredSelection)
		{
			Object selectedObject = ((StructuredSelection) selection).getFirstElement();

			if (null != selectedObject)
			{
				this.selectedItem = selectedObject;

				this.fqn = this.extractor.getFqn(selectedObject); // May be null, but that's fine.
			}
		}
	};

	public void run(IAction action)
	{
		Job job = new Job(this.taskName)
		{
			protected IStatus run(IProgressMonitor monitor)
			{
				try
				{
					monitor.beginTask(BaseAction.this.taskName, IProgressMonitor.UNKNOWN);

					executeAction(monitor);

					monitor.done();

					Boolean isModal = (Boolean) this.getProperty(IProgressConstants.PROPERTY_IN_DIALOG);

					if (isModal != null && isModal.booleanValue())
					{
						setProperty(IProgressConstants.KEEP_PROPERTY, Boolean.TRUE);

						Display.getDefault().asyncExec(new Runnable()
						{
							public void run()
							{
								onDone();
							}
						});
					}
					else
					{
						setProperty(IProgressConstants.KEEP_PROPERTY, Boolean.TRUE);
						setProperty(IProgressConstants.ACTION_PROPERTY, new Action()
						{
							public void run()
							{
								onDone();
							};
						});
					}

					return Status.OK_STATUS;
				}
				catch (Throwable ex)
				{
					ex.printStackTrace();

					monitor.done();

					return new Status(Status.ERROR, "", ex.getMessage());
				}
			};
		};

		job.setUser(true);
		job.schedule();
	};

	protected void onDone()
	{
	};

	protected void executeAction(IProgressMonitor monitor) throws Throwable
	{
	};

	protected String taskName = "";
	protected String fqn = null;
	protected Object selectedItem = null;
	protected IWorkbenchPart targetPart = null;
	private TypeNameExtractor extractor = new TypeNameExtractor();
};
