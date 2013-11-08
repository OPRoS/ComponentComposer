package kr.co.n3soft.n3com.rcp.actions;

import java.io.File;
import java.util.List;

import kr.co.n3soft.n3com.edit.ComponentEditPart;
import kr.co.n3soft.n3com.model.comm.FinalPackageModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.net.NetManager;
import kr.co.n3soft.n3com.project.browser.UMLTreeParentModel;
import kr.co.n3soft.n3com.projectmanager.CompAssemManager;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;

public class NodeDeployAction extends SelectionAction {
	IWorkbenchWindow workbenchWindow;
	public static   int ID=0;
	IEditorInput input = null;
	UMLModel selectModel;
	Color color = null;
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}

	String ip = null;
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}

	String port = null;
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}

	String node_id = null;
	  
    public String getNode_id() {
		return node_id;
	}
	public void setNode_id(String node_id) {
		this.node_id = node_id;
	}
	public NodeDeployAction(IEditorPart editor) {
        super(editor);
        this.setId("NodeDeployAction");
//        this.setText(N3Messages.POPUP_OPEN_DIAGRAM);
       
    }
    public NodeDeployAction(){
   	 super(null);
   }

    protected boolean calculateEnabled() {
    	List list = ProjectManager.getInstance().getSelectNodes();
        if (list != null && list.size() == 1) {
            Object obj = list.get(0);
            if(obj instanceof ComponentEditPart){
            	N3EditorDiagramModel nd = ProjectManager.getInstance().getUMLEditor().getDiagram();
    			UMLTreeParentModel up = nd.getUMLTreeModel().getParent();
          
            	UMLModel ur = (UMLModel)up.getRefModel();
        		if(ur instanceof FinalPackageModel){
        			ComponentEditPart cep = (ComponentEditPart)obj;
        			selectModel = (UMLModel)cep.getModel();
        			return true;
        		}
            	
            }
        }   
      
        return false;
    }

    public void run() {
		try{
			
			if(selectModel!=null){
				N3EditorDiagramModel nd = ProjectManager.getInstance().getUMLEditor().getDiagram();
				UMLTreeParentModel up = nd.getUMLTreeModel().getParent();
				String str = CompAssemManager.getInstance().getNetFoler();
				File target = new File(str);
				if(selectModel.getUMLTreeModel() instanceof UMLTreeParentModel){
					CompAssemManager.getInstance().makeNetCopyMain(up, target,(UMLTreeParentModel)selectModel.getUMLTreeModel());
					
				}
				NetManager.getInstance().setDirectory(new File(str));
				NetManager.getInstance().setIp(selectModel.getIP());
				NetManager.getInstance().setPort(selectModel.getPort1());
				ProjectManager.getInstance().getConsole().addMessage(selectModel.getName()+" Component 전송시작");
				NetManager.getInstance().executeCommand(0);
				selectModel.setBackGroundColor(ColorConstants.lightGreen);
			}
			 
		}
		catch(Exception e){
			e.printStackTrace();
		}
	    
//	    ProjectManager.getInstance().getModelBrowser().writeModel();
		
	}
    //2008040401PKY S "패키지 오른쪽 클릭 시 OpenDiagram 메뉴 추가  여러 다이어그램이있을경우 리스트 나오도록"
	
}
