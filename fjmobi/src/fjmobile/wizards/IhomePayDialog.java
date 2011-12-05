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
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import com.swtdesigner.SWTResourceManager;

public class IhomePayDialog extends Dialog {

	private Combo combo_2;
	private Combo combo_1;
	private Combo combo;
	private Table table_3;
	private Table table_2;
	private Table table;
	private Text text;
	/**
	 * Create the dialog
	 * @param parentShell
	 */
	public IhomePayDialog(Shell parentShell) {
		super(parentShell);
	}

	/**
	 * Create contents of the dialog
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);

		Label label;
		label = new Label(container, SWT.NONE);
		label.setText("号码");

		Label label_1;
		label_1 = new Label(container, SWT.NONE);
		label_1.setText("00000000000");

		Composite composite;
		composite = new Composite(container, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 6;
		composite.setLayout(gridLayout);
		composite.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));

		final Label label_2 = new Label(composite, SWT.NONE);
		label_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		label_2.setText("成员");

		final Label label_3 = new Label(composite, SWT.NONE);
		label_3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		label_3.setText("付款类型");

		final Label label_4 = new Label(composite, SWT.NONE);
		label_4.setText("付款限额");

		final Label label_5 = new Label(composite, SWT.NONE);
		label_5.setText("开始付款时间");

		final Label label_6 = new Label(composite, SWT.NONE);
		label_6.setText("截止时间");
		new Label(composite, SWT.NONE);

		combo = new Combo(composite, SWT.NONE);
		combo.select(0);
		combo.setItems(new String[] {"全部", "13763802177", "13763802177", "13763802177", "13763802177"});
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		combo_1 = new Combo(composite, SWT.NONE);
		combo_1.select(0);
		combo_1.setItems(new String[] {"全额付款", "限额付款"});
		combo_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		text = new Text(composite, SWT.BORDER);
		final GridData gd_text = new GridData(SWT.LEFT, SWT.CENTER, true, false);
		gd_text.widthHint = 59;
		text.setLayoutData(gd_text);

		combo_2 = new Combo(composite, SWT.NONE);
		combo_2.select(0);
		combo_2.setItems(new String[] {"次月生效", "次日生效"});
		combo_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		new DateTime(composite, SWT.NONE);

		final Button button = new Button(composite, SWT.NONE);
		button.setText("主号付费");

		Composite composite_1;
		composite_1 = new Composite(container, SWT.NONE);
		composite_1.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
		composite_1.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));

		Label label_7;
		label_7 = new Label(composite_1, SWT.NONE);
		label_7.setText("付款记录");

		table = new Table(composite_1, SWT.BORDER);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		final TableColumn newColumnTableColumn = new TableColumn(table, SWT.NONE);
		newColumnTableColumn.setWidth(100);
		newColumnTableColumn.setText("New column");

		Button button_1;
		button_1 = new Button(composite_1, SWT.NONE);
		button_1.setText("取消付费");
		final GroupLayout groupLayout_1 = new GroupLayout(composite_1);
		groupLayout_1.setHorizontalGroup(
			groupLayout_1.createParallelGroup(GroupLayout.LEADING)
				.add(groupLayout_1.createSequentialGroup()
					.add(groupLayout_1.createParallelGroup(GroupLayout.LEADING)
						.add(label_7)
						.add(groupLayout_1.createSequentialGroup()
							.add(6, 6, 6)
							.add(table, GroupLayout.PREFERRED_SIZE, 392, GroupLayout.PREFERRED_SIZE)
							.add(6, 6, 6)
							.add(button_1)))
					.add(13, 13, 13))
		);
		groupLayout_1.setVerticalGroup(
			groupLayout_1.createParallelGroup(GroupLayout.LEADING)
				.add(groupLayout_1.createSequentialGroup()
					.add(label_7)
					.addPreferredGap(LayoutStyle.RELATED)
					.add(groupLayout_1.createParallelGroup(GroupLayout.LEADING)
						.add(button_1)
						.add(table, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(18, Short.MAX_VALUE))
		);
		composite_1.setLayout(groupLayout_1);

		Composite composite_1_1;
		composite_1_1 = new Composite(container, SWT.NONE);
		composite_1_1.setForeground(SWTResourceManager.getColor(0, 0, 0));
		composite_1_1.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND));

		Label label_7_1;
		label_7_1 = new Label(composite_1_1, SWT.NONE);
		label_7_1.setText("Label");

		table_2 = new Table(composite_1_1, SWT.BORDER);
		table_2.setLinesVisible(true);
		table_2.setHeaderVisible(true);

		final TableColumn newColumnTableColumn_1 = new TableColumn(table_2, SWT.NONE);
		newColumnTableColumn_1.setWidth(100);
		newColumnTableColumn_1.setText("New column");

		Button button_1_1;
		button_1_1 = new Button(composite_1_1, SWT.NONE);
		button_1_1.setText("button");

		Label label_7_2;
		label_7_2 = new Label(composite_1_1, SWT.NONE);
		label_7_2.setText("付款历史");

		table_3 = new Table(composite_1_1, SWT.BORDER);
		table_3.setLinesVisible(true);
		table_3.setHeaderVisible(true);

		final TableColumn newColumnTableColumn_2 = new TableColumn(table_3, SWT.NONE);
		newColumnTableColumn_2.setWidth(100);
		newColumnTableColumn_2.setText("New column");
		final GroupLayout groupLayout = new GroupLayout(container);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(GroupLayout.TRAILING)
				.add(groupLayout.createSequentialGroup()
					.addContainerGap()
					.add(groupLayout.createParallelGroup(GroupLayout.LEADING)
						.add(composite_1, GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
						.add(composite, GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
						.add(groupLayout.createSequentialGroup()
							.add(label)
							.addPreferredGap(LayoutStyle.RELATED)
							.add(label_1))
						.add(composite_1_1, GroupLayout.PREFERRED_SIZE, 470, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(GroupLayout.LEADING)
				.add(groupLayout.createSequentialGroup()
					.addContainerGap()
					.add(groupLayout.createParallelGroup(GroupLayout.BASELINE)
						.add(label)
						.add(label_1))
					.addPreferredGap(LayoutStyle.RELATED)
					.add(composite, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(LayoutStyle.RELATED)
					.add(composite_1, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(LayoutStyle.RELATED, 19, Short.MAX_VALUE)
					.add(composite_1_1, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		final GroupLayout groupLayout_2 = new GroupLayout(composite_1_1);
		groupLayout_2.setHorizontalGroup(
			groupLayout_2.createParallelGroup(GroupLayout.LEADING)
				.add(groupLayout_2.createSequentialGroup()
					.add(groupLayout_2.createParallelGroup(GroupLayout.LEADING)
						.add(groupLayout_2.createSequentialGroup()
							.add(6, 6, 6)
							.add(table_3, GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE))
						.add(label_7_2, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		groupLayout_2.setVerticalGroup(
			groupLayout_2.createParallelGroup(GroupLayout.LEADING)
				.add(groupLayout_2.createSequentialGroup()
					.add(label_7_2, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(LayoutStyle.RELATED)
					.add(table_3, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(18, Short.MAX_VALUE))
		);
		composite_1_1.setLayout(groupLayout_2);
		container.setLayout(groupLayout);
		return container;
	}

	/**
	 * Create contents of the button bar
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
		return new Point(500, 484);
	}

}
