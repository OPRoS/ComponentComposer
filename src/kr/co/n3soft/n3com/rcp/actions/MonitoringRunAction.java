package kr.co.n3soft.n3com.rcp.actions;

import java.util.List;

import kr.co.n3soft.n3com.edit.UMLEditPart;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.component.ComponentModel;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;
import kr.co.n3soft.n3com.model.node.NodeItemModel;
import kr.co.n3soft.n3com.project.browser.RootCmpComposingTreeModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.project.dialog.MonitoringThread;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorPart;

public class MonitoringRunAction extends SelectionAction {
	N3EditorDiagramModel n3EditorDiagramModel;
	N3EditorDiagramModel n3EditorPackageDiagramModel;//2008040401PKY S "패키지 오른쪽 클릭 시 OpenDiagram 메뉴 추가  여러 다이어그램이있을경우 리스트 나오도록"
	private UMLDiagramModel parent;
	UMLTreeModel treeModel = null;
	  private UMLModel child;
    public MonitoringRunAction(IEditorPart editor) {
        super(editor);
        this.setId("MonitoringRunAction");
        this.setText("Run Monitoring");
        this.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(352)));
    }
    public MonitoringRunAction(){
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
//            	if(child instanceof )
            }
        }
        return false;
        
          
    }

    public void run() {
//    	Thread th = ProjectManager.getInstance().getThread();
    	if(child!=null){
    		if(child instanceof ComponentModel){
				ComponentModel cm = (ComponentModel)child;
				cm.isMonitor = true;
				NodeItemModel nodeItemModel = cm.getNodeItemModel();
				if((nodeItemModel!=null && nodeItemModel.getThd()==null) || (nodeItemModel.getMt()!=null && !nodeItemModel.getMt().isStart)){
//					OpenMonitoringDialog od = new OpenMonitoringDialog();
					MonitoringThread t1 = new MonitoringThread();
//					t1.set
					
					t1.nodeItemModel = nodeItemModel;
					Thread t = new Thread(t1);
				
					nodeItemModel.setThd(t);
					nodeItemModel.setMt(t1);
//					ProjectManager.getInstance().setThread(null);
					t1.isConnectm = true;
					t1.initData = false;
					cm.setLocation(cm.getLocation());
					nodeItemModel.addTempCmp(cm);
//					od.
					nodeItemModel.getThd().start();
				}
				else{
					cm.setLocation(cm.getLocation());
					nodeItemModel.addTempCmp(cm);
					cm.refresh();	//111206 SDM
				}
//				setApp(null);
			}
//	    	if(th==null){
//	    		OpenMonitoringDialog od = new OpenMonitoringDialog();
//	    		
//	    	}
    	}

    	
    }
//    public String setApp(UMLTreeModel ut){
//		if(ut==null){
//			 ut = um.getUMLTreeModel();
//			
//		}
//		
//		if(ut.getParent() instanceof RootCmpComposingTreeModel){
//			this.appName = ut.getName();
//			return;
//		}
//		else{
//			this.setApp(ut.getParent());
//		}
//		
//	}
    //2008040401PKY S "패키지 오른쪽 클릭 시 OpenDiagram 메뉴 추가  여러 다이어그램이있을경우 리스트 나오도록"
	public N3EditorDiagramModel getN3EditorPackageDiagramModel() {
		return n3EditorPackageDiagramModel;
	}
	public void setN3EditorPackageDiagramModel(	N3EditorDiagramModel editorPackageDiagramModel) {
		n3EditorPackageDiagramModel = editorPackageDiagramModel;
	}
    //2008040401PKY E "패키지 오른쪽 클릭 시 OpenDiagram 메뉴 추가  여러 다이어그램이있을경우 리스트 나오도록"
}