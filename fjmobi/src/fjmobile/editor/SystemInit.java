package fjmobile.editor;

import key.Crypt;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.ViewPart;

import cmobile.common.Constant;
import cmobile.db.DBFactory;

import com.swtdesigner.SWTResourceManager;

public class SystemInit extends EditorPart {

	private Text text_1;
	private Text text;
	public static final String ID = "fjmobile.editor.SystemInit";

	public static String system_proc = "1";

	public static String system_timeout = "60";

	/**
	 * Create contents of the view part
	 * 
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		final GridLayout gridLayout = new GridLayout();
		parent.setLayout(gridLayout);

		final Group group = new Group(parent, SWT.NONE);
		group.setText("注册信息");
		group.setLayoutData(new GridData(461, SWT.DEFAULT));
		final GridLayout gridLayout_1 = new GridLayout();
		gridLayout_1.numColumns = 3;
		group.setLayout(gridLayout_1);

		final Label label_1 = new Label(group, SWT.NONE);
		label_1.setText("机器码：");

		text_1 = new Text(group, SWT.BORDER);
		text_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		text_1.setText(Crypt.getMac());
		new Label(group, SWT.NONE);

		final Label label = new Label(group, SWT.NONE);
		label.setText("填写注册码：");

		text = new Text(group, SWT.BORDER);
		text.setLayoutData(new GridData(302, SWT.DEFAULT));

		final Button button = new Button(group, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				if (Crypt.getMD5(Crypt.getMac() + 0).equals(text.getText())
						|| Crypt.getMD5(Crypt.getMac() + 1).equals(
								text.getText())) {
					DBFactory.Sysprop("system_regcode", text.getText());
					MessageDialog.openInformation(e.display.getActiveShell(),
							"成功", "系统已注册成功，重新开启后使用！");
				} else {
					MessageDialog.openInformation(e.display.getActiveShell(),
							"成功", "注册码不正确！");
				}
			}
		});
		button.setText("注册");

		final Label label_3 = new Label(parent, SWT.NONE);
		if (DBFactory.getConn() == null) {
			label_3.setText("已经有其他自动机在运行！不能重复运行.......");
			group.setVisible(false);
		} else {

		}
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
