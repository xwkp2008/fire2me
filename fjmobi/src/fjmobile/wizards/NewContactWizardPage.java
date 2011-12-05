package fjmobile.wizards;

import org.eclipse.core.runtime.Preferences;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import fjmobile.Activator;
import fjmobile.model.Contact;
import fjmobile.model.ContactsManager;
import fjmobile.preferences.PreferenceConstants;

public class NewContactWizardPage extends WizardPage
{
	private Text text_1;
	private Text text;
	private boolean fnameEntered = false;
	private boolean lnameEntered = false;
	
	public NewContactWizardPage() {
		super("wizardPage");
		setTitle("新增手机号码");
		//setPageComplete(false);
	}

	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
		container.setLayout(gridLayout);

		final Composite composite = new Composite(container, SWT.NONE);
		composite.setRedraw(true);
		final GridData gridData_9 = new GridData(GridData.FILL, GridData.CENTER, true, false);
		gridData_9.widthHint = 360;
		composite.setLayoutData(gridData_9);
		composite.setLayout(new GridLayout());
		
		createNameGroup(composite);
		createAddressGroup(composite);
		createPhoneGroup(composite);
		createEmailGroup(composite);
		initContents();
		setControl(container);
		
	}

	private void createNameGroup(Composite composite){
		final Group nameGroup = new Group(composite, SWT.NONE);
		nameGroup.setRedraw(true);
		nameGroup.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false));
		nameGroup.setText("信息");
		final GridLayout gridLayout_3 = new GridLayout();
		gridLayout_3.numColumns = 2;
		nameGroup.setLayout(gridLayout_3);

		final Label firstNameLabel = new Label(nameGroup, SWT.NONE);
		firstNameLabel.setText("手机号码");

		text = new Text(nameGroup, SWT.BORDER);
		text.setTextLimit(11);
		final GridData gd_text = new GridData(SWT.FILL, SWT.CENTER, true, false);
		text.setLayoutData(gd_text);

		final Label lastNameLabel = new Label(nameGroup, SWT.NONE);
		lastNameLabel.setLayoutData(new GridData());
		lastNameLabel.setText("密码");

		text_1 = new Text(nameGroup, SWT.BORDER);
		text_1.setTextLimit(6);
		text_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
	}
	
	private void createAddressGroup(Composite composite){
	}
	
	private void createPhoneGroup(Composite composite){
		
	}

	private void createEmailGroup(Composite composite){
	}
	
	private void initContents(){
		Preferences prefs = Activator
							.getDefault().getPluginPreferences();
		String areaCode = "(" +
			prefs.getString(PreferenceConstants.CONTACTS_DEFAULT_PHONE_AREA_CODE)
			+ ")";
	}
	
	public void createContact(){
		Contact contact=new Contact(text.getText(),text_1.getText());
		ContactsManager mgr = ContactsManager.getManager();
		mgr.newContact(contact);
	}
	
}
