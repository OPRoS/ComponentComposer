package kr.co.n3soft.n3com.rcp.actions;

import java.util.List;

import kr.co.n3soft.n3com.UMLEditor;
import kr.co.n3soft.n3com.edit.MessageAssoicateLineEditPart;
import kr.co.n3soft.n3com.edit.UMLEditPart;
import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.communication.MessageAssoicateLineModel;
import kr.co.n3soft.n3com.model.communication.MessageCommunicationModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IEditorPart;

public class ADDTargetMessageAction extends SelectionAction {
    public ADDTargetMessageAction(IEditorPart editor) {
        super(editor);
        this.setId("ADDTargetMessageAction");
        this.setText(N3Messages.POPUP_ADD_MESSAGE);
    }

    protected boolean calculateEnabled() {
        // TODO Workaround for Bug 82622/39369.  Should be removed when 39369 is fixed.
    	 List list = ProjectManager.getInstance().getSelectNodes();
//        Object obj1 = this.getSelection();
//        Object obj2 = this.getSelectedObjects();
//        if (getSelectedObjects().size() == 1
//        		  && (getSelectedObjects().get(0) instanceof EditPart)) {
//        			EditPart part = (EditPart)getSelectedObjects().get(0);
////        			return part.understandsRequest(getDirectEditRequest());
//        		}
        
//        StructuredSelection list = (StructuredSelection)this.getSelection();
        if (list != null && list.size() == 1) {
            Object obj = list.get(0);
            if (obj instanceof MessageAssoicateLineEditPart) {
            	MessageAssoicateLineEditPart mae = (MessageAssoicateLineEditPart)obj;
            	UMLEditPart uTarget = (UMLEditPart)mae.getTarget();
            	UMLEditPart uSource = (UMLEditPart)mae.getSource();
            	UMLModel ut = (UMLModel)uTarget.getModel();
            	UMLModel us = (UMLModel)uSource.getModel();
            	this.setText(N3Messages.POPUP_ADD_MESSAGE+" from "+ut.getName()+" to "+us.getName());
               return true;
            }
        }
        return false;
    }

    public void run() {
        //		this.getCommandStack().execute(command)
    	 List list = ProjectManager.getInstance().getSelectNodes();
        if (list != null && list.size() == 1) {
            Object obj = list.get(0);
            if (obj instanceof MessageAssoicateLineEditPart) {
            	UMLEditor ue = ProjectManager.getInstance().getUMLEditor();
            	MessageAssoicateLineEditPart u = (MessageAssoicateLineEditPart)obj;
            	MessageAssoicateLineModel um = (MessageAssoicateLineModel)u.getModel();
            	MessageCommunicationModel m = new MessageCommunicationModel();
            	m.addLeftArrowModel();
            	m.setNumber(ue.getNum());
            	m.setName("message");
            	um.addMiddleLineTextModel(m);
            	ue.addSeqNumber(m);
            	
                
            }
        }
    }
}
