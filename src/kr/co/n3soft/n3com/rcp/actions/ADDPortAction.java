package kr.co.n3soft.n3com.rcp.actions;

import kr.co.n3soft.n3com.edit.UMLEditPart;
import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.activity.FinalObjectNodeModel;
import kr.co.n3soft.n3com.model.comm.PortContainerModel;
import kr.co.n3soft.n3com.model.comm.PortModel;
import kr.co.n3soft.n3com.model.comm.UMLContainerModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.component.ComponentModel;
import kr.co.n3soft.n3com.model.umlclass.FinalClassModel;
import kr.co.n3soft.n3com.model.usecase.UseCaseModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorPart;

public class ADDPortAction extends SelectionAction {
    public ADDPortAction(IEditorPart editor) {
        super(editor);
        this.setId("포트추가");
        this.setText(N3Messages.POPUP_ADD_PORT);
        setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(320)));//PKY 08070301 S 다이얼로그 이름 및 아이콘 넣기
    }

    protected boolean calculateEnabled() {
        // TODO Workaround for Bug 82622/39369.  Should be removed when 39369 is fixed.
        Object obj1 = this.getSelection();
        StructuredSelection list = (StructuredSelection)this.getSelection();
        if (list != null && list.size() == 1) {
            Object obj = list.getFirstElement();
            if (obj instanceof UMLEditPart) {
                UMLEditPart u = (UMLEditPart)obj;
                UMLModel um = (UMLModel)u.getModel();
                if (um instanceof PortContainerModel
                		&& ((um instanceof UseCaseModel)
                		|| (um instanceof FinalClassModel)
                		|| (um instanceof FinalObjectNodeModel)
                		|| (um instanceof ComponentModel)
                	)) {
                    return true;
                }
                else {
                    return false;
                }
            }
        }
        return false;
    }

    public void run() {
        //		this.getCommandStack().execute(command)
        Object obj1 = this.getSelection();
        StructuredSelection list = (StructuredSelection)this.getSelection();
        if (list != null && list.size() == 1) {
            Object obj = list.getFirstElement();
            if (obj instanceof UMLEditPart) {
                UMLEditPart u = (UMLEditPart)obj;
                
                PortContainerModel um = (PortContainerModel)u.getModel();
               
                um.createPort(new PortModel(um), (UMLContainerModel)um.getAcceptParentModel());
            }
        }
    }
}
