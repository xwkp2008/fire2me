package fjmobile.model;

import fjmobile.ContactsViewContentProvider;

public abstract class AbstractManager {

	public AbstractManager() {
		super();
	}

	public abstract void addContactsManagerListener(ContactsViewContentProvider listener);

	public abstract void removeContactsManagerListener(ContactsViewContentProvider listener);
	
	public abstract IContact[] getContacts();
}