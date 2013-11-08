//2008041501PKY S ÀüÃ¼ 

package kr.co.n3soft.n3com.model.comm.descriptor;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.PropertyDescriptor;

public class FragmentPropertyDescriptor extends PropertyDescriptor {

	public FragmentPropertyDescriptor(Object id, String displayName) {
		super(id, displayName);
		// TODO Auto-generated constructor stub
	}


    /**
     * The <code>ColorPropertyDescriptor</code> implementation of this <code>IPropertyDescriptor</code> method creates and
     * returns a new <code>ColorCellEditor</code>. <p>
     * The editor is configured with the current validator if there is one. </p>
     */
    public CellEditor createPropertyEditor(Composite parent) {
        CellEditor editor = new FragmentCallEditor(parent);
        if (getValidator() != null) {
            editor.setValidator(getValidator());
        }
        return editor;
    }
    

}
