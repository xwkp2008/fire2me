package fjmobile.wizards;

import org.eclipse.jface.wizard.WizardPage;
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
import org.eclipse.swt.widgets.Text;

import fjmobile.model.Contact;
import fjmobile.model.ContactsManager;

public class ImpIhomeMainWizardPage extends WizardPage {
	private Text subFile;
	private Text mainFile;

	public Text getMainFile() {
		return mainFile;
	}

	public Text getSubFile() {
		return subFile;
	}

	public ImpIhomeMainWizardPage() {
		super("wizardPage");
		setTitle("导入主副号码");
	}

	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
		container.setLayout(gridLayout);

		final Composite composite = new Composite(container, SWT.NONE);
		composite.setRedraw(true);
		final GridData gridData_9 = new GridData(GridData.FILL,
				GridData.CENTER, true, false);
		gridData_9.widthHint = 360;
		composite.setLayoutData(gridData_9);
		composite.setLayout(new GridLayout());

		createNameGroup(composite);
		createAddressGroup(composite);
		createPhoneGroup(composite);
		createEmailGroup(composite);
		setControl(container);

	}

	private void createNameGroup(Composite composite) {
		final Group nameGroup = new Group(composite, SWT.NONE);
		nameGroup.setRedraw(true);
		nameGroup.setLayoutData(new GridData(GridData.FILL, GridData.CENTER,
				true, false));
		nameGroup.setText("信息");
		final GridLayout gridLayout_3 = new GridLayout();
		gridLayout_3.numColumns = 3;
		nameGroup.setLayout(gridLayout_3);

		final Label firstNameLabel = new Label(nameGroup, SWT.NONE);
		firstNameLabel.setText("主号码");

		Button button = new Button(nameGroup, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fileDialog = new FileDialog(e.display
						.getActiveShell(), SWT.OPEN);
				fileDialog.setText("主号码文件");
				String[] filterExt = { "*.txt" };
				fileDialog.setFilterExtensions(filterExt);
				String selected = fileDialog.open();
				if (selected == null)
					return;
				mainFile.setText(selected);
			}
		});
		button.setText("导入");

		mainFile = new Text(nameGroup, SWT.BORDER);
		mainFile.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		final Label lastNameLabel = new Label(nameGroup, SWT.NONE);
		lastNameLabel.setText("副号码");

		Button button_1 = new Button(nameGroup, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fileDialog = new FileDialog(e.display
						.getActiveShell(), SWT.OPEN);
				fileDialog.setText("副号码文件");
				String[] filterExt = { "*.txt" };
				fileDialog.setFilterExtensions(filterExt);
				String selected = fileDialog.open();
				if (selected == null)
					return;
				subFile.setText(selected);
			}
		});
		button_1.setText("导入");

		subFile = new Text(nameGroup, SWT.BORDER);
		subFile.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
	}

	private void createAddressGroup(Composite composite) {
	}

	private void createPhoneGroup(Composite composite) {

	}

	private void createEmailGroup(Composite composite) {
	}

	public void createContact() {
		Contact contact = new Contact(subFile.getText(), mainFile.getText());
		ContactsManager mgr = ContactsManager.getManager();
		mgr.newContact(contact);
	}

}
