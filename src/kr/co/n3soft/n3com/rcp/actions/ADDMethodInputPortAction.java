package kr.co.n3soft.n3com.rcp.actions;

import kr.co.n3soft.n3com.edit.UMLEditPart;
import kr.co.n3soft.n3com.model.activity.FinalObjectNodeModel;
import kr.co.n3soft.n3com.model.comm.MethodInputPortModel;
import kr.co.n3soft.n3com.model.comm.PortContainerModel;
import kr.co.n3soft.n3com.model.comm.PortModel;
import kr.co.n3soft.n3com.model.comm.UMLContainerModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.component.ComponentModel;
import kr.co.n3soft.n3com.model.umlclass.FinalClassModel;
import kr.co.n3soft.n3com.model.usecase.UseCaseModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeParentModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorPart;

public class ADDMethodInputPortAction extends SelectionAction {
    public ADDMethodInputPortAction(IEditorPart editor) {
        super(editor);
        this.setId("ADDMethodInputPortAction");
        this.setText("Service Required");
        setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(10007))); // WJH 090810
    }

    protected boolean calculateEnabled() {
        // TODO Workaround for Bug 82622/39369.  Should be removed when 39369 is fixed.
        Object obj1 = this.getSelection();
        StructuredSelection list = (StructuredSelection)this.getSelection();
        if (list != null && list.size() == 1) {
            Object obj = list.getFirstElement();
            if (obj instanceof UMLEditPart) {
                UMLEditPart u = (UMLEditPart)obj;
                UMLModel um = (UMLModel)u.getModel();
                if (um instanceof PortContainerModel
                		&& ((um instanceof UseCaseModel)
                		|| (um instanceof FinalClassModel)
                		|| (um instanceof FinalObjectNodeModel)
                		|| (um instanceof ComponentModel)
                	)) {
//                	if(um instanceof ComponentLibModel){
//                		return false;
//                	}
                    return true;
                }
                else {
                    return false;
                }
            }
        }
        return false;
    }

    public void run() {
        //		this.getCommandStack().execute(command)
        Object obj1 = this.getSelection();
        StructuredSelection list = (StructuredSelection)this.getSelection();
        if (list != null && list.size() == 1) {
            Object obj = list.getFirstElement();
            if (obj instanceof UMLEditPart) {
                UMLEditPart u = (UMLEditPart)obj;
                
                PortContainerModel um = (PortContainerModel)u.getModel();
//                PortModel pm = new MethodInputPortModel(um);
                PortModel pm = new MethodInputPortModel(um);
                um.createPort( pm, (UMLContainerModel)um.getAcceptParentModel());
                UMLTreeParentModel to1 = (UMLTreeParentModel)um.getUMLTreeModel();
				UMLTreeModel port = new UMLTreeModel("");
				to1.addChild(port);
				port.setRefModel(pm);
				pm.getElementLabelModel().setTreeModel(port);
                ProjectManager.getInstance().getModelBrowser().refresh(to1);
//                PortModel pm = new MethodInputPortModel(um);
                ProjectManager.getInstance().getModelBrowser().searchComponentDiagramIdModel(um.getUMLDataModel().getId());
        		java.util.ArrayList al = ProjectManager.getInstance().getSearchModel();
        		System.out.println("dddd");
        		for(int i = 0; i<al.size();i++){
        			UMLModel um1 = (UMLModel)al.get(i);
        			if(um1!=um){
            				if(um1 instanceof ComponentModel){
            					ComponentModel cm = (ComponentModel)um1;
            					PortModel pm2 = new MethodInputPortModel(cm);
//            					if(pm instanceof MethodOutputPortModel)
                					cm.createPort(pm2, (UMLContainerModel)cm.getAcceptParentModel());
            					pm2.getUMLDataModel().setId(pm.getID());
            					pm2.getElementLabelModel().setPortId(pm.getID());
            				}
            					
            				

        				
        			}
        		}
            }
        }
    }
}
