package lt.atgplugin.wizards.helpers;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public abstract class ATGDialog extends Dialog {
	Object result;

	public abstract void initBody(Dialog parent);

	public ATGDialog(Shell parent, int style) {
		super(parent, style);
	}

	public ATGDialog(Shell parent) {
		this(parent, 0);
	}

	private boolean p = false;

	public boolean isSucccess() {
		return p;
	}

	public Object open() {
		Shell parent = getParent();
		Shell shell = new Shell(parent);

		p = true;
		initBody(this);
		shell.open();
		Display display = parent.getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		return result;
	}
}
