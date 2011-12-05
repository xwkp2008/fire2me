package fjmobile.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

import cmobile.common.OpFile;
import cmobile.db.DBFactory;
import fjmobile.ContactsViewContentProvider;
import fjmobile.model.ContactsManager;
import fjmobile.model.IContact;
import fjmobile.wizards.NewContactWizard;

@SuppressWarnings("unchecked")
public class MobileNoEditor extends EditorPart {

	private Table table;
	private TableViewer viewer;
	public static final String ID = "fjmobile.editor.MobileNoEditor";
	private boolean checked = false;

	/**
	 * Create contents of the editor part
	 * 
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		
		Composite container = new Composite(parent, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		container.setLayout(gridLayout);
		viewer = new TableViewer(container, SWT.MULTI | SWT.BORDER| SWT.CHECK);
		viewer.setContentProvider(new ContactsViewContentProvider());
		viewer.setInput(ContactsManager.getManager());

		table = viewer.getTable();
		final GridData gd_table = new GridData(GridData.FILL, SWT.FILL, true,
				true);
		table.setLayoutData(gd_table);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		final TableColumn newColumnTableColumn_1 = new TableColumn(table,
				SWT.NONE);
		table.setSortColumn(newColumnTableColumn_1);
		newColumnTableColumn_1.setWidth(150);
		newColumnTableColumn_1.setText("手机号码");

		final TableColumn newColumnTableColumn = new TableColumn(table,
				SWT.NONE);
		newColumnTableColumn.setWidth(100);
		newColumnTableColumn.setText("手机密码");

		final Group group = new Group(container, SWT.NONE);
		final GridData gd_group = new GridData(SWT.FILL, SWT.FILL, false, true);
		group.setLayoutData(gd_group);
		group.setLayout(new GridLayout());

		final Button button = new Button(group, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				NewContactWizard wizard = new NewContactWizard();
				WizardDialog dialog = new WizardDialog(e.display
						.getActiveShell(), wizard);
				dialog.open();
				showMessageOnStatusbar("增加1个手机号码");
				setTable();
			}
		});
		button.setText("添加");

		final Button button_2 = new Button(group, SWT.NONE);

		button_2.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				FileDialog fileDialog = new FileDialog(e.display
						.getActiveShell(), SWT.OPEN);
				String filepath = fileDialog.open();
				if (filepath != null)
					try {
						IContact[] mobile = OpFile.imp(filepath);
						for (IContact c : mobile) {
							DBFactory.inertMobileno(c.getMobileno(), c
									.getPassword());
						}
						showMessageOnStatusbar("导入" + mobile.length + "手机号码");
						setTable();
					} catch (Exception e1) {
						MessageDialog.openInformation(e.display
								.getActiveShell(), "错误", e1.getMessage());
					}
			}
		});
		button_2.setText("导入");

		final Button button_3 = new Button(group, SWT.NONE);
		button_3.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				OpFile.ExportFile(e, table);
			}
		});
		button_3.setText("导出");

		final Button button_1 = new Button(group, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				try {
					TableItem[] items = table.getItems();
					List list = new ArrayList();
					for (TableItem item : items) {
						String mobileno = "'" + item.getText(0) + "'";
						list.add(mobileno);
					}
					DBFactory.delMobile(list);
					showMessageOnStatusbar("删除" + list.size() + "手机号码");
					setTable();
				} catch (Exception e1) {
					MessageDialog.openInformation(e.display.getActiveShell(),
							"错误", e1.getMessage());
				}
			}
		});
		button_1.setText("删除");

		final Button button_4 = new Button(group, SWT.NONE);
		button_4.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				showMessageOnStatusbar("总共" + setTable() + "个手机号码");
			}
		});
		button_4.setText("刷新");

		setTable();
	}

	private int setTable() {
		table.removeAll();
		IContact[] col = ContactsManager.getManager().getContacts();
		for (int i = 0; i < col.length; i++) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(new String[] { col[i].getMobileno(),
					col[i].getPassword() });
		}
		return col.length;
	}

	public void showMessageOnStatusbar(String message) {
		IStatusLineManager statusline = this.getEditorSite().getActionBars()
				.getStatusLineManager();
		statusline.setErrorMessage(this.getSite().getShell().getImage(),
				message);
	}

	@Override
	public void setFocus() {
		// Set the focus
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		// Do the Save operation
	}

	@Override
	public void doSaveAs() {
		// Do the Save As operation
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		setSite(site);
		setInput(input);
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

}
