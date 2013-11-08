package kr.co.n3soft.n3com.rcp.actions;

import java.util.List;

import kr.co.n3soft.n3com.edit.ComponentEditPart;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.component.AtomicComponentModel;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IEditorPart;

public class OutputComponentIdTest extends SelectionAction {
	N3EditorDiagramModel n3EditorDiagramModel;
	N3EditorDiagramModel n3EditorPackageDiagramModel;//2008040401PKY S "��Ű�� ������ Ŭ�� �� OpenDiagram �޴� �߰�  ���� ���̾�׷���������� ����Ʈ ��������"
	private UMLDiagramModel parent;
	UMLTreeModel treeModel = null;
	  private UMLModel child;
	  AtomicComponentModel am;
	  
	  public OutputComponentIdTest(IEditorPart editor) {
        super(editor);
        this.setId("OutputComponentIdTest");
        this.setText("Get ID SystemOutPrint");
       
    }
    public OutputComponentIdTest(){
   	 super(null);
   }

    protected boolean calculateEnabled() {
        // TODO Workaround for Bug 82622/39369.  Should be removed when 39369 is fixed.
    	List list = ProjectManager.getInstance().getSelectNodes();
        if (list != null && list.size() == 1) {
            Object obj = list.get(0);
            if(obj instanceof ComponentEditPart){
            	ComponentEditPart ce = (ComponentEditPart)obj;
            	Object obj2 = ce.getModel();
            	if(obj2 instanceof AtomicComponentModel){
            		am = (AtomicComponentModel)obj2;
            		return true;
            	}
            }
            
        }
        return false;
        
          
    }

    //20110707������>> ������/������������ ���� �� �ʿ� �߰� �� �����ͷ� ����
    public void run() {
    	
    }

}
