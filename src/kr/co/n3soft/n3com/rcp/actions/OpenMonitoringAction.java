package kr.co.n3soft.n3com.rcp.actions;

import java.util.List;

import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.project.dialog.OpenMonitoringDialog;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorPart;

public class OpenMonitoringAction extends SelectionAction {
	N3EditorDiagramModel n3EditorDiagramModel;
	N3EditorDiagramModel n3EditorPackageDiagramModel;//2008040401PKY S "��Ű�� ������ Ŭ�� �� OpenDiagram �޴� �߰�  ���� ���̾�׷���������� ����Ʈ ��������"
	private UMLDiagramModel parent;
	UMLTreeModel treeModel = null;
	  private UMLModel child;
    public OpenMonitoringAction(IEditorPart editor) {
        super(editor);
        this.setId("OpenMonitoringAction");
        this.setText("Open Monitoring Dialog ");
        this.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(355)));//PKY 08070101 S �˾� �޴� �̹��� ����
    }
    public OpenMonitoringAction(){
   	 super(null);
   }

    protected boolean calculateEnabled() {
        // TODO Workaround for Bug 82622/39369.  Should be removed when 39369 is fixed.
    	List list = ProjectManager.getInstance().getSelectNodes();
        if (list != null && list.size() == 1) {
            Object obj = list.get(0);
        }
        return true;
          
    }

    public void run() {
    	OpenMonitoringDialog dialog = new OpenMonitoringDialog(this.getWorkbenchPart().getSite().getShell());
//        Object value = getValue();
        //        if (value != null) {
        //			dialog.attributes = (java.util.ArrayList)value;
        //		}
        dialog.open();
    	
    }
    //2008040401PKY S "��Ű�� ������ Ŭ�� �� OpenDiagram �޴� �߰�  ���� ���̾�׷���������� ����Ʈ ��������"
	
    //2008040401PKY E "��Ű�� ������ Ŭ�� �� OpenDiagram �޴� �߰�  ���� ���̾�׷���������� ����Ʈ ��������"
}
