package fjmobile.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;

import fjmobile.View;



public class CopyContactAction extends Action
{
	private View view;

	public CopyContactAction(
		View view,String text){
		
		super(text);
		this.view = view;
	}
	
	public void run(){
		if(view != null) {	
			try {
				MessageDialog.openInformation(view.getSite().getShell(), "Action", "Executing copy Action");
			} catch (Exception e) {
				MessageDialog.openError(view.getSite().getShell(), "Error", "Error opening dialog" + e.getMessage());
			}
		}
		
	}
	
}
