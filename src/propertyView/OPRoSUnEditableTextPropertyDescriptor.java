package propertyView;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class OPRoSUnEditableTextPropertyDescriptor extends TextPropertyDescriptor {

	public OPRoSUnEditableTextPropertyDescriptor(Object id, String displayName) {
		super(id, displayName);
	}

	
	public CellEditor createPropertyEditor(Composite parent) {
		return null; 
	}
	
}