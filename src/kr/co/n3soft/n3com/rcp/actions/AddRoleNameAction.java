package kr.co.n3soft.n3com.rcp.actions;

import java.util.HashMap;

import kr.co.n3soft.n3com.edit.AssociateLineEditPart;
import kr.co.n3soft.n3com.edit.LineEditPart;
import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.comm.LineModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorPart;
//PKY 08090804 S 

public class AddRoleNameAction extends SelectionAction {
	boolean isSource = true;
	boolean isAggregate = false;
	LineModel lm = null;
	String value = "";
	public AddRoleNameAction(IEditorPart editor) {
		super(editor);
		this.setId("AddRoleNameAction");
		this.setText(N3Messages.POPUP_ROLE);
//		setChecked(isChecked());
	}
//	public boolean isChecked() {

//	return true;

//	}
	protected boolean calculateEnabled() {
		// TODO Workaround for Bug 82622/39369.  Should be removed when 39369 is fixed.
		Object obj1 = this.getSelection();
		StructuredSelection list = (StructuredSelection)this.getSelection();
		if (list != null && list.size() == 1) {
			Object obj = list.getFirstElement();
			if (obj instanceof AssociateLineEditPart) {
				LineEditPart u = (LineEditPart)obj;
				lm = (LineModel)u.getModel();

				PolylineConnection n3line = (PolylineConnection)u.getFigure();

				Point pt1  = n3line.getStart();
				Point pt2  =  n3line.getEnd();
				Point pt = ProjectManager.getInstance().getMouseSelectPoint();

				double d = pt.getDistance(pt1);
				double d1 = pt.getDistance(pt2);
				if(d>d1){
					isSource = false;  
					HashMap hm = lm.getDetailProp();
					value = (String)hm.get(LineModel.ID_TARGET_ROLE);

					if(value==null || value.equals("")){
						this.setText(N3Messages.POPUP_ROLE);
						value = "target Role";

					}
					else{
						this.setText(N3Messages.POPUP_REMOVE_ROLE);
					}

				}
				else{

					isSource = true;  
					HashMap hm = lm.getDetailProp();
					value = (String)hm.get(LineModel.ID_SOURCE_ROLE);

					if(value==null || value.equals("")){
						this.setText(N3Messages.POPUP_ROLE);
						value = "source Role";

					}
					else{
						this.setText(N3Messages.POPUP_REMOVE_ROLE);
					}

				}

				return true;


			}
		}
		return false;
	}

	public void run() {
		//		this.getCommandStack().execute(command)
		if(lm!=null){
			if(this.isSource){
				if(lm.getDetailProp().get(LineModel.ID_SOURCE_ROLE)==null){
					lm.getDetailProp().put(LineModel.ID_SOURCE_ROLE, value);
				}else{
					lm.getDetailProp().remove(LineModel.ID_SOURCE_ROLE);

				}

			}
			else{
				if(lm.getDetailProp().get(LineModel.ID_TARGET_ROLE)==null){
					lm.getDetailProp().put(LineModel.ID_TARGET_ROLE, value);
				}else{
					lm.getDetailProp().remove(LineModel.ID_TARGET_ROLE);

				}

			}
			lm.setDetailProp(lm.getDetailProp());
			lm.isChange = true;
		}
	}
}
