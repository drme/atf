package lt.atgplugin.wizards.helpers;

import java.util.ArrayList;
import java.util.List;

import lt.atgplugin.filters.ATGOption;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class ATGTable {

	List<ATGOption> items = null;

	List<ATGOption> selectedItems = new ArrayList<ATGOption>(0);

	public List<ATGOption> getSelectedItems() {
		return selectedItems;
	}

	public void setSelectedItems(List<ATGOption> selectedItems) {
		this.selectedItems = selectedItems;
	}

	final Table table;

	public Table getTable() {
		return table;
	}

	public ATGTable(final Shell shell, Composite parent, int style,
			final List<ATGOption> items, List<ATGOption> defaults) {

		Color CONFIGURABLE = new Color(shell.getDisplay(), 255, 51, 0);
		final Color CONFIGURATED = new Color(shell.getDisplay(), 51, 153, 51);
		table = new Table(parent, style);
		this.items = items;
		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		table.setLayoutData(data);
		String[] titles = { "#", "Title", "Description" };
		for (int i = 0; i < titles.length; i++) {
			TableColumn column = new TableColumn(table, SWT.NONE);
			column.setText(titles[i]);
		}
		int j = 0;
		for (ATGOption element : items) {
			j++;
			TableItem rulesItem = new TableItem(table, SWT.NONE);
			rulesItem.setText(0, (j) + "");
			rulesItem.setText(1, element.getName());
			if (element.isConfigurable()) {
				rulesItem.setGrayed(true);
				rulesItem.setForeground(CONFIGURABLE);
			}
			if (defaults.contains(element)) {
				rulesItem.setChecked(true);
				selectedItems.add(element);
			}

			rulesItem.setText(2, element.getDescription());

		}
		table.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {

				if (event.detail == SWT.CHECK) {

					TableItem w = (TableItem) event.item;
					table.select(Integer.parseInt(w.getText()) - 1);
					if (!w.getGrayed()) {
						if (w.getChecked()) {
							selectedItems.add(items.get(table
									.getSelectionIndex()));

						}
						if (!w.getChecked()) {
							selectedItems.remove(items.get(table
									.getSelectionIndex()));

						}
					}

				}

			}
		});
		table.addListener(SWT.MouseDoubleClick, new Listener() {
			public void handleEvent(Event event) {
				int e = table.getSelectionIndex();
				ATGOption element = items.get(e);
				if (element.isConfigurable()) {
					ATGDialog d = element.getDialog(shell);
					if (d != null) {
						d.open();
						if (d.isSucccess()) {
							table.getItem(e).setForeground(CONFIGURATED);
							table.getItem(e).setGrayed(false);

						}
					} else {
						table.getItem(e).setForeground(CONFIGURATED);
						table.getItem(e).setGrayed(false);

					}
				}

			}
		});
		for (int i = 0; i < titles.length; i++) {
			table.getColumn(i).pack();
		}
	}

}
