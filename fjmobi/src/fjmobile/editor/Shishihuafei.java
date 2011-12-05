package fjmobile.editor;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.ContributionManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.progress.IProgressService;

import cmobile.action.ActionFactory;
import cmobile.common.Function;
import cmobile.common.OpFile;
import cmobile.db.DBFactory;
import fjmobile.ContactsViewContentProvider;
import fjmobile.editor.job.RefreshStatusJob;
import fjmobile.model.ContactsManager;
import fjmobile.model.IContact;

public class Shishihuafei extends EditorPart {

	private Table table;
	List mobileList = new ArrayList();
	private boolean checked = false;
	Label label_1 = null;
	private TableViewer viewer;

	public static final String ID = "fjmobile.editor.Shishihuafei"; //$NON-NLS-1$

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
		
		
		viewer = new TableViewer(container, SWT.FULL_SELECTION | SWT.MULTI
				| SWT.BORDER| SWT.CHECK);
		viewer.setContentProvider(new ContactsViewContentProvider());
		viewer.setInput(ContactsManager.getManager());

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
		newColumnTableColumn.setWidth(4000);
		newColumnTableColumn.setText("实时话费");

		final Group group = new Group(container, SWT.NONE);
		final GridData gd_group = new GridData(SWT.FILL, SWT.CENTER, false,
				false);
		gd_group.heightHint = 196;
		group.setLayoutData(gd_group);
		group.setLayout(new GridLayout());

		final Button button_4 = new Button(group, SWT.NONE);
		button_4.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false));
		button_4.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				TableItem[] items = table.getItems();
				checked = !checked;
				for (TableItem item : items) {
					item.setChecked(checked);
				}
			}
		});
		button_4.setText("全选");

		final Button button_3 = new Button(group, SWT.NONE);
		button_3.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				getMobiles();
				e.display.syncExec(new Runnable() {
					public void run() {
						refresh("shishihuafei");
					}
				});
				setTime(label_1);
			}
		});
		button_3.setText("查询");

		final Button button_2 = new Button(group, SWT.NONE);
		button_2.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				if (!DBFactory.clearAll("shishihuafei")) {
					MessageDialog.openInformation(e.display.getActiveShell(),
							"错误", "清除失败！");
					setTable();
					setTime(label_1);
				} else {
					OpFile.showMessageOnStatusbar("清除" + setTable() + "个手机号码",
							getEditorSite());
				}
			}
		});
		button_2.setText("清除");

		final Button button = new Button(group, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				setTable();
			}
		});
		button.setText("刷新");

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
		String time = DBFactory.getProp("shishihuafei_time");
		label_1.setText(time == null ? "" : time);
		new Label(container, SWT.NONE);
		//
		setTable();
		
		List actionList = new ArrayList();
		actionList.add("shishihuafei_retain");
		OpFile.retain(actionList, getEditorSite());
	}

	private void setTime(Label l) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = df.format(new Date());
		DBFactory.Sysprop("shishihuafei_time", time);
		l.setText(time);
	}

	private int setTable() {
		table.removeAll();
		IContact[] col = ContactsManager.getManager().getContacts();
		for (int i = 0; i < col.length; i++) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(new String[] { col[i].getMobileno(),
					col[i].getShishihuafei() });
		}
		return col.length;
	}

	private void getMobiles() {
		TableItem[] items = table.getItems();
		mobileList.clear();
		for (TableItem item : items) {
			if (item.getChecked())
			mobileList.add(item.getText(0));
		}
	}

	private void refresh(String type) {
		OpFile.refresh(null,mobileList, type, getEditorSite(), new Function() {
			@Override
			public void exec() {
				//setTable();
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
