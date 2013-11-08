package kr.co.n3soft.n3com.rcp.actions;

import java.util.List;

import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.comm.OperationElementModel;
import kr.co.n3soft.n3com.model.comm.TypeRefModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IEditorPart;

public class NewOperationAction extends SelectionAction {
	
//	MessageModel mm;
	OperationElementModel oem;
	TypeRefModel trm;
    public NewOperationAction(IEditorPart editor) {
        super(editor);
        this.setId("NewOperationAction");
        this.setText(N3Messages.POPUP_NEW_OPERATION);
    }
    public NewOperationAction(){
    	 super(null);
    }

    protected boolean calculateEnabled() {
    	  List list = ProjectManager.getInstance().getSelectNodes();
    	  if (list != null && list.size() == 1) {
    	  Object obj = list.get(0);
    	
    	}
    	return false;

    }

    public void run() {
    	
    }
//	public OperationEditableTableItem getOti() {
//		return oti;
//	}
//	public void setOti(OperationEditableTableItem oti) {
//		this.oti = oti;
//	}
	
	public OperationElementModel getOem() {
		return oem;
	}
	public void setOem(OperationElementModel oem) {
		this.oem = oem;
	}
	public TypeRefModel getTrm() {
		return trm;
	}
	public void setTrm(TypeRefModel trm) {
		this.trm = trm;
	}
}
