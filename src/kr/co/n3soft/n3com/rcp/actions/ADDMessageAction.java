package kr.co.n3soft.n3com.rcp.actions;

import java.util.List;

import kr.co.n3soft.n3com.UMLEditor;
import kr.co.n3soft.n3com.edit.MessageAssoicateLineEditPart;
import kr.co.n3soft.n3com.edit.UMLEditPart;
import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.comm.LineTextModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.communication.MessageAssoicateLineModel;
import kr.co.n3soft.n3com.model.communication.MessageCommunicationModel;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IEditorPart;

public class ADDMessageAction extends SelectionAction {
    public ADDMessageAction(IEditorPart editor) {
        super(editor);
        this.setId("ADDMessageAction");
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
            	this.setText(N3Messages.POPUP_ADD_MESSAGE+" from "+us.getName()+" to "+ut.getName());
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
            	m.addRightArrowModel();
    			//PKY 08070701 S 커뮤니케이션 저장 불러오기 하면 넘버링 초기화 되는문제 수정
    			if(ue.getSeqNumberSize()<=0)
    			if(um.getMiddleLineTextModel()!=null&&um.getMiddleLineTextModel().getAcceptParentModel()!=null){
    				for(int i = 0; i < ((N3EditorDiagramModel)um.getMiddleLineTextModel().getAcceptParentModel()).getChildren().size(); i++){
    						if(((N3EditorDiagramModel)um.getMiddleLineTextModel().getAcceptParentModel()).getChildren().get(i)  instanceof LineTextModel){
    							LineTextModel lineModels =(LineTextModel)((N3EditorDiagramModel)um.getMiddleLineTextModel().getAcceptParentModel()).getChildren().get(i);
    							for(int k = 0; k < lineModels.getLineTextm().getLineTextModels().size(); k++){
    								Object objx =lineModels.getLineTextm().getLineTextModels().get(k);
    								MessageCommunicationModel msg=(MessageCommunicationModel)lineModels.getLineTextm().getLineTextModels().get(k);
    		    					ue.addSeqNumber(msg);
    							}
    				
    						}
    					
    				}
    			}
    			//PKY 08070701 E 커뮤니케이션 저장 불러오기 하면 넘버링 초기화 되는문제 수정
            	m.setNumber(ue.getNum());
            	m.setName("message");
            	m.setMessageIntType(Integer.valueOf(MessageCommunicationModel.TYPE_COMMUNICATION_MESSAGE));//PKY 08072401 S 라인 메세지들 다이어그램에서 내용을 없애면 사라지도록 수정
            	um.addMiddleLineTextModel(m);
            	ue.addSeqNumber(m);
            	
            	um.getMiddleLineTextModel().setLocation(new Point(um.getMiddleLineTextModel().getLocation())); //PKY 08070701 S 리플래쉬 안되는문제 수정
    			//PKY 08070701 S 커뮤니케이션 메시지 추가하다보면 텍스트사이즈가 늘어나지않아서 안보이는문제 수정
    			int width=0;
    			for(int i =0; i < um.getMiddleLineTextModel().getMessageSize(); i++){
    				if(width<ProjectManager.getInstance().widthAutoSize(um.getMiddleLineTextModel().getMessageCommunicationModel(i).getName()))
    					width=ProjectManager.getInstance().widthAutoSize(um.getMiddleLineTextModel().getMessageCommunicationModel(i).getName());
    			}
    			if(width>0)
    				um.getMiddleLineTextModel().setSize(new Dimension(width+35,um.getMiddleLineTextModel().getMessageSize()*22));//PKY 08070701 S 커뮤니케이션 메시지 추가하다보면 텍스트사이즈가 늘어나지않아서 안보이는문제 수정
    			else
    				um.getMiddleLineTextModel().setSize(new Dimension(um.getMiddleLineTextModel().getSize().width,um.getMiddleLineTextModel().getMessageSize()*22));//PKY 08070701 S 커뮤니케이션 메시지 추가하다보면 텍스트사이즈가 늘어나지않아서 안보이는문제 수정	
    			//PKY 08070701 E 커뮤니케이션 메시지 추가하다보면 텍스트사이즈가 늘어나지않아서 안보이는문제 수정
            }
        }
    }
}
