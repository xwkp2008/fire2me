package fjmobile.wizards;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.grouplayout.GroupLayout;
import org.eclipse.swt.layout.grouplayout.LayoutStyle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public class IhomeDialog extends Dialog {

	private Table table;

	/**
	 * Create the dialog
	 * 
	 * @param parentShell
	 */
	public IhomeDialog(Shell parentShell) {
		super(parentShell);
	}

	/**
	 * Create contents of the dialog
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);

		Label label;
		label = new Label(container, SWT.NONE);
		label.setText("号码");

		Label mobileno;
		mobileno = new Label(container, SWT.NONE);
		mobileno.setForeground(Display.getCurrent().getSystemColor(
				SWT.COLOR_RED));
		mobileno.setText("00000000000");

		Label label_1;
		label_1 = new Label(container, SWT.NONE);
		label_1.setText("是否建家");

		Label issetup;
		issetup = new Label(container, SWT.NONE);
		issetup.setForeground(Display.getCurrent()
				.getSystemColor(SWT.COLOR_RED));
		issetup.setText("否");

		Label setuptime;
		setuptime = new Label(container, SWT.NONE);
		setuptime.setText("建家时间");

		Label label_3;
		label_3 = new Label(container, SWT.NONE);
		label_3.setForeground(Display.getCurrent()
				.getSystemColor(SWT.COLOR_RED));
		label_3.setText("0000年00月00日");

		table = new Table(container, SWT.BORDER);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		Group group;
		group = new Group(container, SWT.NONE);
		group.setLayout(new GridLayout());

		final Button button = new Button(group, SWT.NONE);
		button.setText("刷新");

		final Button button_1 = new Button(group, SWT.NONE);
		button_1.setText("添加");

		final Button button_2 = new Button(group, SWT.NONE);
		button_2.setText("删除");

		Label label_2;
		label_2 = new Label(container, SWT.NONE);
		label_2.setText("成员列表：");

		final Button button_3 = new Button(group, SWT.NONE);
		button_3.setText("缴费");

		final TableColumn newColumnTableColumn_1 = new TableColumn(table, SWT.NONE);
		newColumnTableColumn_1.setWidth(100);
		newColumnTableColumn_1.setText("New column");

		final TableColumn newColumnTableColumn = new TableColumn(table, SWT.NONE);
		newColumnTableColumn.setWidth(100);
		newColumnTableColumn.setText("New column");

		Label label_4;
		label_4 = new Label(container, SWT.NONE);
		label_4.setText("家庭");

		Label homename;
		homename = new Label(container, SWT.NONE);
		homename.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
		homename.setText("00000000");
		final GroupLayout groupLayout = new GroupLayout(container);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(GroupLayout.LEADING)
				.add(groupLayout.createSequentialGroup()
					.addContainerGap()
					.add(groupLayout.createParallelGroup(GroupLayout.LEADING)
						.add(groupLayout.createSequentialGroup()
							.add(table, GroupLayout.PREFERRED_SIZE, 381, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.RELATED)
							.add(group, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE))
						.add(groupLayout.createSequentialGroup()
							.add(groupLayout.createParallelGroup(GroupLayout.TRAILING, false)
								.add(groupLayout.createSequentialGroup()
									.add(label)
									.addPreferredGap(LayoutStyle.RELATED)
									.add(mobileno)
									.add(50, 50, 50)
									.add(label_1))
								.add(groupLayout.createSequentialGroup()
									.add(label_4)
									.addPreferredGap(LayoutStyle.RELATED)
									.add(homename)
									.addPreferredGap(LayoutStyle.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.add(setuptime)))
							.addPreferredGap(LayoutStyle.RELATED)
							.add(groupLayout.createParallelGroup(GroupLayout.LEADING)
								.add(label_3)
								.add(issetup)))
						.add(label_2))
					.addContainerGap(34, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(GroupLayout.TRAILING)
				.add(groupLayout.createSequentialGroup()
					.add(21, 21, 21)
					.add(groupLayout.createParallelGroup(GroupLayout.BASELINE)
						.add(label)
						.add(mobileno)
						.add(label_1)
						.add(issetup))
					.addPreferredGap(LayoutStyle.RELATED)
					.add(groupLayout.createParallelGroup(GroupLayout.BASELINE)
						.add(label_3)
						.add(setuptime)
						.add(label_4)
						.add(homename))
					.add(9, 9, 9)
					.add(label_2)
					.addPreferredGap(LayoutStyle.RELATED)
					.add(groupLayout.createParallelGroup(GroupLayout.TRAILING)
						.add(GroupLayout.LEADING, group, GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
						.add(GroupLayout.LEADING, table, GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE))
					.addContainerGap())
		);
		container.setLayout(groupLayout);
		//
		return container;
	}

	/**
	 * Create contents of the button bar
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(500, 375);
	}

}
