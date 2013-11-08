package kr.co.n3soft.n3com.rcp.actions;

import kr.co.n3soft.n3com.lang.N3Messages;

import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IEditorPart;

public class ChangeDeepHistoryAction extends SelectionAction {
    public ChangeDeepHistoryAction(IEditorPart editor) {
        super(editor);
        this.setId("ChangeDeepHistoryAction");
        this.setText(N3Messages.POPUP_SET_DEEP_HISTORY);
    }

    protected boolean calculateEnabled() {
      
        return false;
    }

    public void run() {
       
    }
}
