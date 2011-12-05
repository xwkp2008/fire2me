package fjmobile.editor;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

public class FunctionEditorInput implements IEditorInput {
	public String c;
	public FunctionEditorInput(Object c2) {
		c=c2.toString();
	}

	@Override
	public boolean exists() {
		return false;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	@Override
	public String getName() {
		return c;
	}

	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	@Override
	public String getToolTipText() {
		return c;
	}

	@Override
	public Object getAdapter(Class adapter) {
		return null;
	}

}
