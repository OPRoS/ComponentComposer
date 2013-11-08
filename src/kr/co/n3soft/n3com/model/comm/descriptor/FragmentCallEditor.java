//2008041501PKY S ÀüÃ¼ 

package kr.co.n3soft.n3com.model.comm.descriptor;

import kr.co.n3soft.n3com.edit.FragmentEditPart;
import kr.co.n3soft.n3com.project.dialog.FragmentDialog;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class FragmentCallEditor extends DialogCellEditor {


	protected Object openDialogBox(Control cellEditorWindow) {
		try{
		    Object value = getValue();
			FragmentDialog fragmentDialog = new FragmentDialog(ProjectManager.getInstance().window.getShell());
			 java.util.List l = ProjectManager.getInstance().getSelectNodes();
			 Object object=l.get(0);
			 if(object instanceof FragmentEditPart){
				 FragmentEditPart fragmentEditPart=(FragmentEditPart)object;
//				 if(fragmentEditPart.getModel()instanceof FragmentModel){
//					 FragmentModel model=(FragmentModel) fragmentEditPart.getModel();
//					 fragmentDialog.setFragmentModel((FragmentModel)model);
//				 }
			 }
			 
//        	;
			 fragmentDialog.open();
	        //        value = dialog.attributes;
		}catch(Exception e){
			e.printStackTrace();
		}
	        return null;
	}
    public FragmentCallEditor(Composite parent) {
        super(parent, SWT.NONE);
    }

	
}
