package fjmobile.editor;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

import cmobile.common.Function;
import cmobile.common.OpFile;
import cmobile.db.DBFactory;
import fjmobile.ContactsViewContentProvider;
import fjmobile.model.Contact;
import fjmobile.model.ContactsManager;
import fjmobile.model.IContact;
import fjmobile.model.IhomeManager;
import fjmobile.wizards.ImpIhomeMainWizardPage;
import fjmobile.wizards.ImpIhomeWizard;

public class Ihome extends EditorPart {

	private Table table;
	public static final String ID = "fjmobile.editor.Ihome"; //$NON-NLS-1$
	List mobileList = new ArrayList();
	private boolean checked = false;
	Label label_1 = null;
	private TableViewer viewer;

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

		viewer = new TableViewer(container, SWT.FULL_SELECTION | SWT.BORDER);
		viewer.setContentProvider(new ContactsViewContentProvider("ihome"));
		viewer.setInput(IhomeManager.getManager());

		table = viewer.getTable();

		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		final GridData gd_table = new GridData(GridData.FILL, SWT.FILL, true,
				true);
		gd_table.widthHint = 408;
		table.setLayoutData(gd_table);

		final TableColumn newColumnTableColumn_1 = new TableColumn(table,
				SWT.NONE);
		newColumnTableColumn_1.setWidth(150);
		newColumnTableColumn_1.setText("号码");

		final TableColumn newColumnTableColumn = new TableColumn(table,
				SWT.NONE);
		newColumnTableColumn.setWidth(100);
		newColumnTableColumn.setText("是否建家");

		final Group group = new Group(container, SWT.NONE);
		final GridData gd_group = new GridData(SWT.FILL, SWT.CENTER, false,
				false);
		gd_group.heightHint = 220;
		group.setLayoutData(gd_group);
		group.setLayout(new GridLayout());

		final Button button_3 = new Button(group, SWT.NONE);
		button_3.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				getMobiles();
				e.display.syncExec(new Runnable() {
					public void run() {
						getMobiles();
						refresh("ihome", "open");
					}
				});
				setTime(label_1);
			}
		});
		button_3.setText("进入");

		final Button button = new Button(group, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				getMobiles();
				e.display.syncExec(new Runnable() {
					public void run() {
						getMobiles();
						refresh("ihome_query", "");
					}
				});
				setTime(label_1);
			}
		});
		button.setText("\u67E5\u8BE2");

		final Button button_2 = new Button(group, SWT.NONE);
		button_2.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				if (!DBFactory.clearAll("ihome")) {
					MessageDialog.openInformation(e.display.getActiveShell(),
							"错误", "清除失败！");
					setTable();
				} else {
					OpFile.showMessageOnStatusbar("清除" + setTable() + "个手机号码",
							getEditorSite());
				}
			}
		});
		button_2.setText("\u5237\u65B0");

		final Button imp = new Button(group, SWT.NONE);
		imp.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				ImpIhomeWizard wizard = new ImpIhomeWizard(getEditorSite());
				WizardDialog dialog = new WizardDialog(e.display
						.getActiveShell(), wizard);
				dialog.open();
			}
		});
		imp.setLayoutData(new GridData());
		imp.setText("导入");

		final Button button_1 = new Button(group, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				OpFile.ExportFile(e, table);
			}
		});
		button_1.setText("导出");

		final Group group_1 = new Group(container, SWT.NONE);
		final GridData gd_group_1 = new GridData(SWT.FILL, SWT.CENTER, false,
				false);
		gd_group_1.widthHint = 427;
		group_1.setLayoutData(gd_group_1);
		final GridLayout gridLayout_1 = new GridLayout();
		gridLayout_1.numColumns = 2;
		group_1.setLayout(gridLayout_1);

		final Label label = new Label(group_1, SWT.NONE);
		label.setText("更新时间：");

		label_1 = new Label(group_1, SWT.NONE);
		String time = DBFactory.getProp("ihome_time");
		label_1.setText(time == null ? "" : time);
		new Label(container, SWT.NONE);
		//
		setTable();

		List actionList = new ArrayList();
		actionList.add("ihome_retain");
		OpFile.retain(actionList, getEditorSite());
	}

	public void showMessageOnStatusbar(String message) {
		IStatusLineManager statusline = this.getEditorSite().getActionBars()
				.getStatusLineManager();
		statusline.setErrorMessage(this.getSite().getShell().getImage(),
				message);
	}

	private void setTime(Label l) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = df.format(new Date());
		DBFactory.Sysprop("ihome_time", time);
		l.setText(time);
	}

	private int setTable() {
		table.removeAll();

		try {
			ResultSet rs = DBFactory.doQueryAllMobile(null, "ihome");
			int count = 0;
			while (rs.next()) {
				TableItem item = new TableItem(table, SWT.NONE);
				item.setText(new String[] { rs.getString(0) });
				count++;
			}
			rs.close();
			return count;
		} catch (Exception e) {
		}
		return 0;
	}

	private void getMobiles() {
		TableItem[] items = table.getItems();
		mobileList.clear();
		for (TableItem item : items) {
			if (item.getChecked())
				mobileList.add(item.getText(0));
		}
	}

	private void refresh(String type, String operate) {
		Map map = new HashMap();
		map.put("sjswoperate", operate);
		OpFile.refresh(map, mobileList, type, getEditorSite(), new Function() {
			@Override
			public void exec() {
				// setTable();
			}
		});
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
