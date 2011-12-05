package fjmobile.editor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.ViewPart;

import cmobile.common.Constant;
import cmobile.db.DBFactory;

import com.swtdesigner.SWTResourceManager;

public class CopyOfSystemConfig extends EditorPart {

	public static final String ID = "fjmobile.editor.SystemConfig";
	
	public static String system_proc="1";
	
	public static String system_timeout="60";

	/**
	 * Create contents of the view part
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 4;
		parent.setLayout(gridLayout);

		final Label label = new Label(parent, SWT.NONE);
		label.setText("系统线程");

		final Spinner spinner_1 = new Spinner(parent, SWT.BORDER);
		spinner_1.setMinimum(1);
		spinner_1.setMaximum(10);
		spinner_1.setSelection(Integer.valueOf(system_proc));
		spinner_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));

		final Label label_5 = new Label(parent, SWT.NONE);
		label_5.setText("个");

		final Label label_3 = new Label(parent, SWT.NONE);
		label_3.setFont(SWTResourceManager.getFont("Sans", 9, SWT.NONE));
		label_3.setLayoutData(new GridData());
		label_3.setText("提高线程数能加速操作，但受限于系统性能影响稳定性");

		final Label label_1 = new Label(parent, SWT.NONE);
		label_1.setText("线程超时");

		final Spinner spinner = new Spinner(parent, SWT.BORDER);
		spinner.setMinimum(30);
		spinner.setMaximum(300);
		spinner.setSelection(Integer.valueOf(system_timeout));
		spinner.setLayoutData(new GridData());

		final Label label_4 = new Label(parent, SWT.NONE);
		label_4.setLayoutData(new GridData());
		label_4.setText("秒");

		final Label label_2 = new Label(parent, SWT.NONE);
		label_2.setFont(SWTResourceManager.getFont("Sans", 9, SWT.NONE));
		label_2.setLayoutData(new GridData());
		label_2.setText("定义线程超时多久后，放弃改操作");
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);

		final Button button = new Button(parent, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				boolean succ;
				succ=DBFactory.Sysprop(Constant.SYSTEM_PROC, spinner_1.getText());
				succ=DBFactory.Sysprop(Constant.SYSTEM_TIMEOUT, spinner.getText());
				showMessageOnStatusbar("参数保存"+(succ?"成功":"失败")+"！");
			}
		});
		button.setText("保存");
	}
	
	public void showMessageOnStatusbar(String message) {
		IStatusLineManager statusline = this.getEditorSite().getActionBars()
				.getStatusLineManager();
		statusline.setErrorMessage(this.getSite().getShell().getImage(),
				message);
	}

	@Override
	public void setFocus() {
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
	}

	@Override
	public void doSaveAs() {
		
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		setSite(site);
		setInput(input);
		
		String proc=DBFactory.getProp(Constant.SYSTEM_PROC);
		if(proc!=null) system_proc=proc;
		String timout=DBFactory.getProp(Constant.SYSTEM_TIMEOUT);
		if(proc!=null) system_timeout=timout;
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
