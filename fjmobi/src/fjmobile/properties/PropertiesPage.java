package fjmobile.properties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.dialogs.PropertyPage;

import fjmobile.model.Contact;
import cmobile.common.IContact;

public class PropertiesPage extends PropertyPage
{

private ColorEditor colorEditor;
	
	protected Control createContents(Composite parent) {
		Composite panel = new Composite(parent,SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		panel.setLayout(layout);
		
		Label label = new Label(panel,SWT.NONE);
		label.setLayoutData(new GridData());
		label.setText("Color of contact");
		
		colorEditor = new ColorEditor(panel);
		colorEditor.setColorValue(getColorPropertyValue());
		colorEditor.getButton().setLayoutData(
			new GridData(100,SWT.DEFAULT));
		 
		Composite subpanel =  new Composite(panel,SWT.NONE);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalSpan = 2;
		subpanel.setLayoutData(gridData);

		return panel;
	}

	
	protected RGB getColorPropertyValue(){
		//IContact contact = (IContact)getElement();
		//Color color = contact.getColor();
		//return color.getRGB();
		return null;
	}
	
	protected void setColorPropertyValue(RGB rgb){
		IContact contact = (IContact) getElement();
		//Color color = Contact.getColor(rgb);
		//if (color.equals(Contact.getDefaultColor()))
		//	color = null;
		//contact.setColor(color);
	}
	
	public boolean performOk(){
		setColorPropertyValue(colorEditor.getColorValue());
		return super.performOk();
	}  
	
	
}
