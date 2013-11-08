package kr.co.n3soft.n3com.rcp.actions;

import java.util.List;

import kr.co.n3soft.n3com.edit.UMLEditPart;
import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IEditorPart;

public class DeleteNumberBarAction extends SelectionAction {
    public DeleteNumberBarAction(IEditorPart editor) {
        super(editor);
        this.setId("DeleteNumberBar");
        this.setText(N3Messages.POPUP_SET_RULER_MODE);
    }

    protected boolean calculateEnabled() {
        // TODO Workaround for Bug 82622/39369.  Should be removed when 39369 is fixed.
    	List list = ProjectManager.getInstance().getSelectNodes();
        if (list != null && list.size() == 1) {
            Object obj = list.get(0);
            if (obj instanceof UMLEditPart) {
                UMLEditPart u = (UMLEditPart)obj;
                UMLModel um = (UMLModel)u.getModel();
                
                    return false;
                
            }
        }
        //		return false;
        return false;
    }

    public void run() {}
}
