package fjmobile.preferences;

import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * A field editor for adding space to a preference page.
 */
public class SpacerFieldEditor extends FieldEditor {
	// Implemented as an empty label field editor.
	
	private Label label;
	
	public SpacerFieldEditor(Composite parent) {
		super("spacer","", parent);
	}

	protected void adjustForNumColumns(int numColumns) {
		((GridData) label.getLayoutData()).horizontalSpan = numColumns;
		
	}

	protected void doFillIntoGrid(Composite parent, int numColumns) {
		label = getLabelControl(parent);
		
		GridData gridData = new GridData();
		gridData.horizontalSpan = numColumns;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = false;
		gridData.verticalAlignment = GridData.CENTER;
		gridData.grabExcessVerticalSpace = false;
		
		label.setLayoutData(gridData);
	}

	protected void doLoad() {
	}

	protected void doLoadDefault() {		
	}

	protected void doStore() {		
	}

	public int getNumberOfControls() {
		return 1;
	}
}
