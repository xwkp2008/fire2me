package fjmobile.model;

import java.util.EventObject;

import org.eclipse.core.resources.IResourceChangeListener;

public class ContactsManagerEvent extends EventObject {
	private static final long serialVersionUID = 5516075349620653480L;
	private final IContact changed;
	private final String filed;

	public ContactsManagerEvent(IResourceChangeListener source,
			IContact contactChanged, String filed) {
		super(source);
		this.filed = filed;
		changed = contactChanged;
	}

	public IContact getChanged() {
		return changed;
	}

	public String getFiled() {
		return filed;
	}
}
