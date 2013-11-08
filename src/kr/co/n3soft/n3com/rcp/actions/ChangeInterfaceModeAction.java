package kr.co.n3soft.n3com.rcp.actions;

import kr.co.n3soft.n3com.edit.UMLEditPart;
import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.umlclass.InterfaceModel;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorPart;

public class ChangeInterfaceModeAction extends SelectionAction {
    public ChangeInterfaceModeAction(IEditorPart editor) {
        super(editor);
        this.setId("ChangeInterfaceModeAction");
        this.setText(N3Messages.POPUP_SET_INTERFACE_MODE);
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
                if (um instanceof InterfaceModel) {
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
                InterfaceModel um = (InterfaceModel)u.getModel();
                if (um.getMode()) {
                	//PKY 08061101 S �������̽� ����� ���� �� �ٽ� �����·� ������ ��� �ʺ� ����� �ʱ�ȭ�Ǵ� ����
                	um.getUMLDataModel().setElementProperty("oldSize", um.getSize());
                	//PKY 08061101 E �������̽� ����� ���� �� �ٽ� �����·� ������ ��� �ʺ� ����� �ʱ�ȭ�Ǵ� ����
                    um.setMode(false);
                    
                }
                else {
                	
                    um.setMode(true);
                  //PKY 08061101 S �������̽� ����� ���� �� �ٽ� �����·� ������ ��� �ʺ� ����� �ʱ�ȭ�Ǵ� ����
                    if(um.getUMLDataModel().getElementProperty("oldSize")!=null){
                    	um.setSize(new Dimension(((Dimension)um.getUMLDataModel().getElementProperty("oldSize")).width,((Dimension)um.getUMLDataModel().getElementProperty("oldSize")).height));//PKY 08081801 S �������̽� ����� ������ ��� ���������� �ٽ� ������ �������� ����
                    }
                  //PKY 08061101 E �������̽� ����� ���� �� �ٽ� �����·� ������ ��� �ʺ� ����� �ʱ�ȭ�Ǵ� ����
                }
            }
        }
    }
}
