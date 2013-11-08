package kr.co.n3soft.n3com.rcp.actions;

import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorPart;

public class ADDEntryPointAction extends SelectionAction {
    public ADDEntryPointAction(IEditorPart editor) {
        super(editor);
        this.setId("ADDEntryPointAction");
        this.setText(N3Messages.POPUP_ADD_ENTRY_POINT);
        setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(323)));//PKY 08070301 S ���̾�α� �̸� �� ������ �ֱ�
    }

    protected boolean calculateEnabled() {
    	return false;
      
    }

    public void run() {
        //		this.getCommandStack().execute(command)
        
    }
}
