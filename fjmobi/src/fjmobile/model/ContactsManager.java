package fjmobile.model;

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
public class ContactsManager extends AbstractManager implements
		IResourceChangeListener {

	private static ContactsManager manager;
	private List contacts;
	private List listeners = new ArrayList();

	private ContactsManager() {
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this,
				IResourceChangeEvent.POST_CHANGE);
	}

	// / IContact Accessors ///

	public static ContactsManager getManager() {
		if (manager == null)
			manager = new ContactsManager();
		return manager;
	}

	public IContact[] getContacts() {
		loadContacts();
		return (IContact[]) contacts.toArray(new IContact[contacts.size()]);
	}

	public void addContacts(IContact[] newContacts) {
		for (IContact contact : newContacts)
			DBFactory.inertMobileno(contact.getMobileno(), contact
					.getPassword());
	}

	public void newContact(IContact newContact) {
		DBFactory.inertMobileno(newContact.getMobileno(), newContact
				.getPassword());
	}

	public void changeContact(String mobileno, String filed) {
		ResultSet rs = DBFactory.queryAllMobile(mobileno);
		IContact contact = null;
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
			ResultSet rs = DBFactory.queryAllMobile(null);
			while (rs.next()) {
				Contact c = setContact(rs);
				contacts.add(c);
			}
			rs.close();
		} catch (Exception e) {
		} finally {
		}
	}

	private Contact setContact(ResultSet rs) throws SQLException {
		Contact c = new Contact();
		if (rs != null && !rs.isClosed()) {
			c.setID(rs.getString("ID"));
			c.setMobileno(rs.getString("mobileno"));
			c.setPassword(rs.getString("password"));
			c.setTingjifuji(rs.getString("tingjifuji"));
			c.setYuejiezhangda(rs.getString("yuejiezhangdan"));
			c.setShishihuafei(rs.getString("shishihuafei"));
			c.setHujiaozhuanyi(rs.getString("hujiaozhuanyi"));
			c.setJiaofeilishi(rs.getString("jiaofeilishi"));
			c.setDuanxin(rs.getString("duanxin"));
			c.setYueqianfei(rs.getString("yueqianfei"));
			c.setXiugaimima(rs.getString("xiugaimima"));
			c.setShoujishangwang(rs.getString("shoujishangwang"));
			c.setMoshangwang(rs.getString("moshangwang"));
			c.setWlan(rs.getString("wlan"));
			c.setWlan(rs.getString("ihome"));
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

	public void fireContactsChanged(IContact contactChanged, String filed) {
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
