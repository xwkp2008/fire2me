package fjmobile.wizards;

import org.eclipse.jface.wizard.Wizard;

public class NewContactWizard extends Wizard
{
	private NewContactWizardPage contactPage;
	
	
	public NewContactWizard(){
		super();
	}
	
	public void addPages(){
		contactPage = new NewContactWizardPage();
		addPage(contactPage);
	}
	
	 public boolean canFinish() {
		 return (contactPage.isPageComplete());
	  }
	 
	public boolean performFinish() {
		contactPage.createContact();
		return true;
	}

}
