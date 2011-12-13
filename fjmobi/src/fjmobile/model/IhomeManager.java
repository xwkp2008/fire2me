package fjmobile.model;

import cmobile.common.IContact;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;

import cmobile.db.DBFactory;
import fjmobile.ContactsViewContentProvider;

@SuppressWarnings("unchecked")
public class IhomeManager extends AbstractManager implements
		IResourceChangeListener {

	private static IhomeManager manager;
	private List contacts;
	private List listeners = new ArrayList();

	private IhomeManager() {
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this,
				IResourceChangeEvent.POST_CHANGE);
	}

	public static IhomeManager getManager() {
		if (manager == null)
			manager = new IhomeManager();
		return manager;
	}

	public IhomeMobile[] getContacts() {
		loadContacts();
		return (IhomeMobile[]) contacts
				.toArray(new IhomeMobile[contacts.size()]);
	}

	public void addContacts(IContact[] newContacts) {
		for (IContact contact : newContacts)
			DBFactory.doInsertMobileno(contact.getMobileno(), contact
					.getPassword(), "ihome");
	}

	public void newContact(IContact newContact) {
		DBFactory.doInsertMobileno(newContact.getMobileno(), newContact
				.getPassword(), "ihome");
	}

	public void changeContact(String mobileno, String filed) {
		ResultSet rs = DBFactory.doQueryAllMobile(mobileno,"ihome");
		IhomeMobile contact = null;
		if (rs != null) {
			try {
				rs.next();
				contact = setContact(rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		fireContactsChanged(contact, filed);
	}

	public void removeContacts(IContact[] oldContacts) {
		List list = new ArrayList();
		for (IContact contact : oldContacts) {
			list.add(contact.getMobileno());
		}
		DBFactory.delMobile(list);
	}

	private void loadContacts() {
		contacts = new ArrayList();
		try {
			ResultSet rs = DBFactory.doQueryAllMobile(null, "ihome");
			while (rs.next()) {
				IhomeMobile c = setContact(rs);
				contacts.add(c);
			}
			rs.close();
		} catch (Exception e) {
		} finally {
		}
	}

	private IhomeMobile setContact(ResultSet rs) throws SQLException {
		IhomeMobile c = new IhomeMobile();
		if (rs != null && !rs.isClosed()) {
			c.setID(rs.getString("ID"));
			c.setMobileno(rs.getString("mobileno"));
			c.setPassword(rs.getString("password"));
		}
		return c;
	}

	public void addContactsManagerListener(ContactsViewContentProvider listener) {
		if (!listeners.contains(listener))
			listeners.add(listener);
	}

	public void removeContactsManagerListener(
			ContactsViewContentProvider listener) {
		listeners.remove(listener);
	}

	public void fireContactsChanged(IhomeMobile contactChanged, String filed) {
		ContactsManagerEvent event = new ContactsManagerEvent(this,
				contactChanged, filed);
		for (Iterator iter = listeners.iterator(); iter.hasNext();)
			((ContactsViewContentProvider) iter.next()).contactsChanged(event);
	}

	public void saveContacts() {
		if (contacts == null)
			return;
	}

	public static void shutdown() {
		if (manager != null) {
			ResourcesPlugin.getWorkspace()
					.removeResourceChangeListener(manager);
			manager = null;
		}
	}

	public void resourceChanged(IResourceChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
