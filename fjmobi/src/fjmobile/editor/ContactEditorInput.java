package fjmobile.editor;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import fjmobile.model.Contact;


public class ContactEditorInput
	implements IEditorInput
{
	private Contact contact;
	
	public ContactEditorInput(Contact c){
		contact = c;
	}
	public boolean exists() {
		// TODO Auto-generated method stub
		return false;
	}

	public ImageDescriptor getImageDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		return("Contacts");
	}

	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getToolTipText() {
		return contact.toString();
	}

	public Object getAdapter(Class adapter) {
		if (adapter == Contact.class) 
			return this.contact;	
		else return null;
	}

}
