package kr.co.n3soft.n3com.rcp.actions;

import kr.co.n3soft.n3com.edit.PortEditPart;
import kr.co.n3soft.n3com.edit.UMLEditPart;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

//PKY 08101342 S
public class ModelMoveUp extends SelectionAction {
	public ModelMoveUp(IWorkbenchPart part) {

		super(part);
//		String id =  N3Messages.POPUP_DELETE;
		this.setId("ModelMoveUp");
	}


	protected boolean calculateEnabled() {
		if(ProjectManager.getInstance()!=null){
			if(ProjectManager.getInstance().getSelectNodes().size()>0){
				for(int i = 0 ; i < ProjectManager.getInstance().getSelectNodes().size(); i++){
				
					Object obj = ProjectManager.getInstance().getSelectNodes().get(i);
					if(obj instanceof UMLEditPart){
						
						if( ((UMLEditPart)obj).getModel()!=null   &&((UMLEditPart)obj).getModel() instanceof UMLModel){
							if( ((UMLEditPart)obj) instanceof PortEditPart){
								return true;
							}
						if(ProjectManager.getInstance().getCopyModelType((UMLModel)((UMLEditPart)obj).getModel())>-1)
						return true;
					}
					}
				
				}
			}

		}
		return false;
	}
	public void run() {
		//PKY 08101368 S
		 if(ProjectManager.getInstance()!=null)
//			 ProjectManager.getInstance().setModify(true);
		if(ProjectManager.getInstance()!=null){
			if(ProjectManager.getInstance().getSelectNodes().size()>0){
				for(int i = 0 ; i < ProjectManager.getInstance().getSelectNodes().size(); i++){
				
					Object obj = ProjectManager.getInstance().getSelectNodes().get(i);
					if(obj instanceof UMLEditPart){
						
						if( ((UMLEditPart)obj).getModel()!=null   &&((UMLEditPart)obj).getModel() instanceof UMLModel){
							UMLModel umlModel = (UMLModel)((UMLEditPart)obj).getModel();
							if(ProjectManager.getInstance().getCopyModelType(umlModel)>-1){
							Point point = new Point(umlModel.getLocation().x,umlModel.getLocation().y-1);
							umlModel.setLocation(point);
							}
						}
					
					}
				
				}
			}

		}
	}
}
