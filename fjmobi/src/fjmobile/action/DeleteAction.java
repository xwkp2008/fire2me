package fjmobile.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;

public class DeleteAction extends Action
{
	private final IWorkbenchWindow window;
	
	
	public DeleteAction(IWorkbenchWindow window, String label) {
		this.window = window;
        setText(label);
        // The id is used to refer to the action in a menu or toolbar
		setId("rcpContactsMngr.delete");
        // Associate the action with a pre-defined command, to allow key bindings.
		setActionDefinitionId("rcpContactsMngr.DeleteAction");
	}
	
	public void run() {
		if(window != null) {	
			try {
				MessageDialog.openInformation(window.getShell(), "Action", "Executing Delete Contact Action");
			} catch (Exception e) {
				MessageDialog.openError(window.getShell(), "Error", "Error opening dialog" + e.getMessage());
			}
		}
	}
}
