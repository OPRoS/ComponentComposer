package kr.co.n3soft.n3com.model.comm.descriptor;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.PropertyDescriptor;
//PKY 08072201 S 시퀀스라인 오른쪽 메뉴에 메뉴가 아닌 왼쪽 프로퍼티에서 다이얼로그 오픈할 수 있도록 수정
public class SequenceMessageDescriptor extends PropertyDescriptor {
	 /* Creates an property descriptor with the given id and display name.
    *
    * @param id the id of the property
    * @param displayName the name to display for the property
    */

   public SequenceMessageDescriptor(Object id, String displayName) {
       super(id, displayName);
   }

   /**
    * The <code>ColorPropertyDescriptor</code> implementation of this <code>IPropertyDescriptor</code> method creates and
    * returns a new <code>ColorCellEditor</code>. <p>
    * The editor is configured with the current validator if there is one. </p>
    */
   public CellEditor createPropertyEditor(Composite parent) {
       CellEditor editor = new SequenceMessageCellEditor(parent);
       if (getValidator() != null) {
           editor.setValidator(getValidator());
       }
       return editor;
   }
}
