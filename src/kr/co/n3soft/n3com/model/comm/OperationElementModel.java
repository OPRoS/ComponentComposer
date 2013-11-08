package kr.co.n3soft.n3com.model.comm;

import kr.co.n3soft.n3com.edit.StateEditPart;
import kr.co.n3soft.n3com.model.umlclass.OperationEditableTableItem;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

public class OperationElementModel extends ElementLabelModel {
    private OperationEditableTableItem att = null;

    public OperationElementModel(OperationEditableTableItem p) {
    	LOGIC_LABEL_ICON	= ProjectManager.getInstance().getImage(ProjectManager.getInstance().getElementType(this, -1));
    	this.att = p;
    	//PKY 08071601 S State  Operation 표시 다른것 수정
    	//PKY 08071601 S 오퍼레이션 저장 불러오기할경우 바로 안보이는문제
    	if(ProjectManager.getInstance()!=null&&ProjectManager.getInstance().getSelectNodes()!=null)
        if(ProjectManager.getInstance().getSelectNodes().size()>0){
        	if(ProjectManager.getInstance().getSelectNodes().get(0) instanceof StateEditPart){
        		if(this.att!=null)
        			this.att.setIsState(true);
        	}
        }
    	//PKY 08071601 E 오퍼레이션 저장 불러오기할경우 바로 안보이는문제

        this.setLabelContents(att.toString());
        
        if (p.id != null) {
            this.setID(p.id);
        }
        else {
            p.id = this.getID();
        }

    }

    public void setOperationEditableTableItem(OperationEditableTableItem p) {
        this.att = p;
    }

    public OperationEditableTableItem getAttributeEditableTableItem() {
        return this.att;
    }

    public String toString() {
        if (att != null)
            return this.att.toString();
        else
            return "";
    }
}
