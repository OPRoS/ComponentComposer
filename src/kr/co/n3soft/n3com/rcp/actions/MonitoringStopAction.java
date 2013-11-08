package kr.co.n3soft.n3com.rcp.actions;

import java.util.List;

import kr.co.n3soft.n3com.edit.UMLEditPart;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.component.ComponentModel;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;
import kr.co.n3soft.n3com.model.node.NodeItemModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.project.dialog.MonitoringThread;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorPart;

public class MonitoringStopAction extends SelectionAction {
	N3EditorDiagramModel n3EditorDiagramModel;
	N3EditorDiagramModel n3EditorPackageDiagramModel;//2008040401PKY S "패키지 오른쪽 클릭 시 OpenDiagram 메뉴 추가  여러 다이어그램이있을경우 리스트 나오도록"
	private UMLDiagramModel parent;
	UMLTreeModel treeModel = null;
	  private UMLModel child;
    public MonitoringStopAction(IEditorPart editor) {
        super(editor);
        this.setId("MonitoringStopAction");
        this.setText("Stop Monitoring");
        this.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(351)));
       
    }
    public MonitoringStopAction(){
   	 super(null);
   }

    protected boolean calculateEnabled() {
        // TODO Workaround for Bug 82622/39369.  Should be removed when 39369 is fixed.
    	List list = ProjectManager.getInstance().getSelectNodes();
        if (list != null && list.size() == 1) {
            Object obj = list.get(0);
            if(obj instanceof UMLEditPart){
            	UMLEditPart ud = (UMLEditPart)obj;
            	child = (UMLModel)ud.getModel();
            	return true;
            }
        }
        return false;
          
    }

    public void run() {
//    	Thread th = ProjectManager.getInstance().getThread();
    	if(child!=null){
    		if(child instanceof ComponentModel){
				ComponentModel cm = (ComponentModel)child;
				NodeItemModel nodeItemModel = cm.getNodeItemModel();
				cm.isMonitor = false;
				if(nodeItemModel!=null && nodeItemModel.getThd()!=null){
//				
					nodeItemModel.delTempCmp(cm);
					if(nodeItemModel.getTempCmp().size()==0){
						nodeItemModel.setThd(null);
						nodeItemModel.setMt(null);
						cm.setLocation(cm.getLocation());
						cm.refresh();	//111206 SDM  - STOP시 모니터링 데이터 삭제(다이어그램 새로고침)
					}
//					od.
//					nodeItemModel.getThd().start();
				}
				
//				setApp(null);
			}
//	    	if(th==null){
//	    		OpenMonitoringDialog od = new OpenMonitoringDialog();
//	    		
//	    	}
    	}

    	
    }
    //2008040401PKY S "패키지 오른쪽 클릭 시 OpenDiagram 메뉴 추가  여러 다이어그램이있을경우 리스트 나오도록"
	public N3EditorDiagramModel getN3EditorPackageDiagramModel() {
		return n3EditorPackageDiagramModel;
	}
	public void setN3EditorPackageDiagramModel(	N3EditorDiagramModel editorPackageDiagramModel) {
		n3EditorPackageDiagramModel = editorPackageDiagramModel;
	}
    //2008040401PKY E "패키지 오른쪽 클릭 시 OpenDiagram 메뉴 추가  여러 다이어그램이있을경우 리스트 나오도록"
}
