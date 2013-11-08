package kr.co.n3soft.n3com.rcp.actions;

import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IEditorPart;

public class DockingConfigureTimelineAction extends SelectionAction {
	java.util.ArrayList dockingList = new java.util.ArrayList(); 
    public DockingConfigureTimelineAction(IEditorPart editor) {
        super(editor);
        this.setId("DockingConfigureTimelineAction");
        this.setText("DockingConfigureTimeline");
    }

    protected boolean calculateEnabled() {
       
        return false;
    }

    public void run() {
    	


    }
}