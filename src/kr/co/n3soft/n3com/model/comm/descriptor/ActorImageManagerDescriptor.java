package kr.co.n3soft.n3com.model.comm.descriptor;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.PropertyDescriptor;
//PKY 08080501 S Image Dialog 이미지 리스트 및 불러오기 편하도록 개선

public class ActorImageManagerDescriptor extends PropertyDescriptor {
	 /* Creates an property descriptor with the given id and display name.
    *
    * @param id the id of the property
    * @param displayName the name to display for the property
    */

   public ActorImageManagerDescriptor(Object id, String displayName) {
       super(id, displayName);
   }

   /**
    * The <code>ColorPropertyDescriptor</code> implementation of this <code>IPropertyDescriptor</code> method creates and
    * returns a new <code>ColorCellEditor</code>. <p>
    * The editor is configured with the current validator if there is one. </p>
    */
   public CellEditor createPropertyEditor(Composite parent) {
       CellEditor editor = new ActorImageManagerCellEditor(parent);
       if (getValidator() != null) {
           editor.setValidator(getValidator());
       }
       return editor;
   }
}
