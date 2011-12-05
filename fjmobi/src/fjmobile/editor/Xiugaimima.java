package fjmobile.editor;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.ContributionManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
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
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
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

public class Xiugaimima extends EditorPart {

	private Table table;
	List mobileList = new ArrayList();
	private boolean checked = false;
	Label label_1 = null;
	private String newpassword = "";
	private TableViewer viewer;

	public static final String ID = "fjmobile.editor.Xiugaimima"; //$NON-NLS-1$

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
		
		viewer = new TableViewer(container, SWT.BORDER | SWT.CHECK | SWT.MULTI
				| SWT.FULL_SELECTION);
		viewer.setContentProvider(new ContactsViewContentProvider());

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
		newColumnTableColumn_1.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				showMessageOnStatusbar(e.item.getData().toString());
			}
		});
		newColumnTableColumn_1.setText("号码");

		final TableColumn newColumnTableColumn_2 = new TableColumn(table, SWT.NONE);
		newColumnTableColumn_2.setWidth(100);
		newColumnTableColumn_2.setText("修改密码");

		final TableColumn newColumnTableColumn = new TableColumn(table,
				SWT.NONE);
		newColumnTableColumn.setWidth(100);
		newColumnTableColumn.setText("原密码");
		viewer.setInput(ContactsManager.getManager());

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

		final Button button = new Button(group, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				getMobiles();
				InputDialog input = new InputDialog(e.display.getActiveShell(),
						"输入密码", "", null, new IInputValidator() {
							@Override
							public String isValid(String newText) {
								if (newText.length() == 6)
									return null;
								return "输入六位新密码";
							}
						});
				input.open();
				newpassword = input.getValue();
				getMobiles();
				if (newpassword.length() == 6) {
					e.display.syncExec(new Runnable() {
						public void run() {
							refresh("xiugaimima");
						}
					});
					setTime(label_1);
				} else {
					showMessageOnStatusbar("密码设置错误！");
				}
			}
		});
		button.setLayoutData(new GridData());
		button.setText("修改密码");

		final Button button_2 = new Button(group, SWT.NONE);
		button_2.setLayoutData(new GridData());
		button_2.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				setTable();
			}
		});
		button_2.setText("刷新");

		final Button button_5 = new Button(group, SWT.NONE);
		button_5.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				OpFile.ExportFile(e, table);
			}
		});
		button_5.setText("导出");

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
		String time = DBFactory.getProp("xiugaimima_time");
		label_1.setText(time == null ? "" : time);
		new Label(container, SWT.NONE);
		//
		setTable();
		
		List actionList = new ArrayList();
		actionList.add("xiugaimima_retain");
		OpFile.retain(actionList, getEditorSite());
	}

	private void setTime(Label l) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = df.format(new Date());
		DBFactory.Sysprop("xiugaimima_time", time);
		l.setText(time);
	}

	private int setTable() {
		table.removeAll();
		IContact[] col = ContactsManager.getManager().getContacts();
		for (int i = 0; i < col.length; i++) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(new String[] { col[i].getMobileno(),"",col[i].getPassword() });
		}
		return col.length;
	}

	private void getMobiles() {
		TableItem[] items = table.getItems();
		mobileList.clear();
		for (TableItem item : items) {
			if (item.getChecked() && item.getGrayed() == false)
				mobileList.add(item.getText(0));
		}
	}

	private void refresh(String type) {
		Map map = new HashMap();
		map.put("password", newpassword);
		map.put("pwd", newpassword);
		map.put("again", "0");
		OpFile.refresh(map, mobileList, type, getEditorSite(), new Function() {
			@Override
			public void exec() {
				//setTable();
			}
		});
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
