package kr.co.n3soft.n3com.model.comm.descriptor;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.PropertyDescriptor;
//PKY 08072401 S 액션 추가 시 타입묻는 다이어그램 오픈되지 않도록 기본타입 노멀

public class ActionTypePropertyDescriptor  extends PropertyDescriptor {
	 /* Creates an property descriptor with the given id and display name.
    *
    * @param id the id of the property
    * @param displayName the name to display for the property
    */

   public ActionTypePropertyDescriptor(Object id, String displayName) {
       super(id, displayName);
   }

   /**
    * The <code>ColorPropertyDescriptor</code> implementation of this <code>IPropertyDescriptor</code> method creates and
    * returns a new <code>ColorCellEditor</code>. <p>
    * The editor is configured with the current validator if there is one. </p>
    */
   public CellEditor createPropertyEditor(Composite parent) {
       CellEditor editor = new ActionTypeCellEditor(parent);
       if (getValidator() != null) {
           editor.setValidator(getValidator());
       }
       return editor;
   }
}
