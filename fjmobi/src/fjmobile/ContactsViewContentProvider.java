package fjmobile;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import cmobile.common.OpFile;
import cmobile.db.DBFactory;
import fjmobile.editor.MobileNoEditor;
import fjmobile.model.AbstractManager;
import fjmobile.model.ContactsManager;
import fjmobile.model.ContactsManagerEvent;
import fjmobile.model.ContactsManagerListener;
import fjmobile.model.IContact;
import fjmobile.model.IhomeManager;

/**
 * The content provider class is responsible for providing objects to the view.
 * It can wrap existing objects in adapters or simply return objects as-is.
 * These objects may be sensitive to the current input of the view, or ignore it
 * and always show the same content (like Task List, for example).
 */
public class ContactsViewContentProvider implements IStructuredContentProvider,
		ContactsManagerListener {

	private AbstractManager manager;
	private TableViewer viewer;

	public ContactsViewContentProvider() {
		manager=ContactsManager.getManager();
	}

	public ContactsViewContentProvider(String type) {
		if("ihome".equals(type)){
			manager=IhomeManager.getManager();
		}
	}

	public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		this.viewer = (TableViewer) v;
		if (manager != null)
			manager.removeContactsManagerListener(this);
		manager = (AbstractManager) newInput;
		if (manager == null)
			manager = (AbstractManager) oldInput;
		if (manager != null)
			manager.addContactsManagerListener(this);

	}

	public void dispose() {
	}

	public Object[] getElements(Object parent) {
		return manager.getContacts();
	}

	public void contactsChanged(final ContactsManagerEvent event) {

		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				if (OpFile.conCurrEditor.equals(OpFile.nowEditor)
						&& !OpFile.nowEditor.equals(MobileNoEditor.ID)) {
					Table table = viewer.getTable();
					table.setRedraw(false);
					try {
						TableItem[] items = table.getItems();
						for (int i = 0; i < items.length; i++) {
							IContact contact = event.getChanged();
							if (items[i].getText(0).equals(
									contact.getMobileno())) {
								String value = DBFactory.getFiled(contact
										.getMobileno(), event.getFiled());
								items[i].setText(1, value);
							}
						}
					} finally {
						viewer.getControl().setRedraw(true);
					}
				}
			}
		});
		// viewer.refresh();
	}
}