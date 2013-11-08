package kr.co.n3soft.n3com.rcp.actions;

import kr.co.n3soft.n3com.edit.UMLEditPart;
import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.activity.FinalActiionModel;
import kr.co.n3soft.n3com.model.comm.PortContainerModel;
import kr.co.n3soft.n3com.model.comm.PortModel;
import kr.co.n3soft.n3com.model.comm.UMLContainerModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorPart;

public class ADDActionPinAction extends SelectionAction {
    public ADDActionPinAction(IEditorPart editor) {
        super(editor);
        this.setId("ADDActionPinAction");
        this.setText(N3Messages.POPUP_ADD_ACTION_PIN);
    }

    protected boolean calculateEnabled() {
        // TODO Workaround for Bug 82622/39369.  Should be removed when 39369 is fixed.
    	 java.util.List list = ProjectManager.getInstance().getSelectNodes();
        if (list != null && list.size() == 1) {
            Object obj = list.get(0);
            if (obj instanceof UMLEditPart) {
                UMLEditPart u = (UMLEditPart)obj;
                UMLModel um = (UMLModel)u.getModel();
                if (um instanceof PortContainerModel
                		       		&& (um instanceof FinalActiionModel)) {
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
    	 java.util.List list = ProjectManager.getInstance().getSelectNodes();
        if (list != null && list.size() == 1) {
            Object obj = list.get(0);
            if (obj instanceof UMLEditPart) {
                UMLEditPart u = (UMLEditPart)obj;
                PortContainerModel um = (PortContainerModel)u.getModel();
                um.createPort(new PortModel(um), (UMLContainerModel)um.getAcceptParentModel());
            }
        }
    }
}
