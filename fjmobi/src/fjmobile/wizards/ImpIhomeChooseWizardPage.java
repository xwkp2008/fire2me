package fjmobile.wizards;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.grouplayout.GroupLayout;
import org.eclipse.swt.layout.grouplayout.LayoutStyle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Label;

public class ImpIhomeChooseWizardPage extends WizardPage {

	private String OPTION_DEAL_ID="37002001";
	private String INURE_TYPE="1";

	public String getOPTION_DEAL_ID() {
		return OPTION_DEAL_ID;
	}

	public void setOPTION_DEAL_ID(String oPTIONDEALID) {
		OPTION_DEAL_ID = oPTIONDEALID;
	}

	public String getINURE_TYPE() {
		return INURE_TYPE;
	}

	public void setINURE_TYPE(String iNURETYPE) {
		INURE_TYPE = iNURETYPE;
	}

	protected ImpIhomeChooseWizardPage() {
		super("choosewizard");
		setTitle("建家选项");
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		setControl(container);
		container.setLayout(new GridLayout(1, false));

		Composite composite = new Composite(container, SWT.NONE);

		Button btn0 = new Button(composite, SWT.RADIO);
		btn0.setBounds(10, 10, 65, 24);
		btn0.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				OPTION_DEAL_ID = "37002001";
			}
		});
		btn0.setSelection(true);
		btn0.setText("I号套餐");

		Button btn1 = new Button(composite, SWT.RADIO);
		btn1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				OPTION_DEAL_ID = "37004001";
			}
		});
		btn1.setBounds(93, 10, 69, 24);
		btn1.setText("II号套餐");

		Composite composite_1 = new Composite(container, SWT.NONE);

		Button button_0 = new Button(composite_1, SWT.RADIO);
		button_0.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				INURE_TYPE = "1";
			}
		});
		button_0.setBounds(10, 30, 73, 24);
		button_0.setSelection(true);
		button_0.setText("立即生效");

		Button button_1 = new Button(composite_1, SWT.RADIO);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				INURE_TYPE = "3";
			}
		});
		button_1.setBounds(89, 30, 73, 24);
		button_1.setText("次月生效");
		setPageComplete(true);
	}
}
