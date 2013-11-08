package kr.co.n3soft.n3com.rcp.actions;

import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.comm.OperationElementModel;
import kr.co.n3soft.n3com.model.comm.TypeRefModel;
import kr.co.n3soft.n3com.model.umlclass.OperationEditableTableItem;

import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IEditorPart;

public class ADDSEQMessageAction extends SelectionAction {
	OperationEditableTableItem oti;

	OperationElementModel oem;
	TypeRefModel trm;
    public ADDSEQMessageAction(IEditorPart editor) {
        super(editor);
        this.setId("ADDSEQMessageAction");
        this.setText(N3Messages.POPUP_ADD_SEQ_MESSAGE);
    }
    public ADDSEQMessageAction(){
    	 super(null);
    }

    protected boolean calculateEnabled() {
    	return true;
        // TODO Workaround for Bug 82622/39369.  Should be removed when 39369 is fixed.

    }

    public void run() {
    	
    }
	public OperationEditableTableItem getOti() {
		return oti;
	}
	public void setOti(OperationEditableTableItem oti) {
		this.oti = oti;
	}
	
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