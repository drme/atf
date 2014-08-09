package lt.atgplugin.wizards.helpers;

import lt.atgplugin.filters.ATGOption;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class ATGList {
	ATGOption selectedItem = null;

	public ATGOption getSelectedItem() {
		return selectedItem;
	}

	public ATGList(final Shell shell, Composite parent, int style,
			final java.util.List<ATGOption> items, String text,
			ATGOption defaultRule) {
		Label label = new Label(parent, SWT.NULL);
		label.setText(text);
		final List l = new List(parent, style | SWT.SINGLE);

		for (ATGOption s : items) {
			l.add(s.getName());

		}
		for (int i = 0; i < l.getItemCount(); i++) {
			if (l.getItem(i).equals(defaultRule.getName())) {
				l.setSelection(i);
				selectedItem = defaultRule;
			}
		}
		l.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				selectedItem = items.get(l.getSelectionIndex());
			}
		});

	}
}
