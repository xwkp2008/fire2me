package fjmobile.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IWorkbenchWindow;

import fjmobile.wizards.NewContactWizard;


public class NewContactAction extends Action
{
	private final IWorkbenchWindow window;
	private String action;
	
	public NewContactAction(IWorkbenchWindow window, String label,String action) {
		this.window = window;
        setText(label);
		setId("rcpContactsMngr.NewContact");
		setImageDescriptor(fjmobile.Activator.getImageDescriptor("/icons/sample.gif"));
		this.action=action;
	}
	
	public void run() {
		NewContactWizard wizard = new NewContactWizard();
		WizardDialog dialog =
			new WizardDialog(window.getShell(),wizard);
		dialog.open();
	}
}
