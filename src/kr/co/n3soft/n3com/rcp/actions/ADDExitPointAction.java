package kr.co.n3soft.n3com.rcp.actions;

import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorPart;

public class ADDExitPointAction extends SelectionAction {
    public ADDExitPointAction(IEditorPart editor) {
        super(editor);
        this.setId("ADDExitPointAction");
        this.setText(N3Messages.POPUP_ADD_EXIT_POINT);
        setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(321)));//PKY 08070301 S 다이얼로그 이름 및 아이콘 넣기
    }

    protected boolean calculateEnabled() {
      
        return false;
    }

    public void run() {
      
    }
}
