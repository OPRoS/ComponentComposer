package kr.co.n3soft.n3com.rcp.actions;

import java.io.File;

import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.component.ComponentModel;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.model.node.NodeItemModel;
import kr.co.n3soft.n3com.net.NetManager;
import kr.co.n3soft.n3com.project.browser.UMLTreeParentModel;
import kr.co.n3soft.n3com.projectmanager.CompAssemManager;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchWindow;

public class DeployAction extends Action {

	IWorkbenchWindow workbenchWindow;
	public static   int ID=0;
	IEditorInput input = null;
	NodeItemModel nodeItemModel = null;
	public boolean isTar = false;
	public N3EditorDiagramModel appDiagram = null;
	public N3EditorDiagramModel getAppDiagram() {
		return appDiagram;
	}

	public void setAppDiagram(N3EditorDiagramModel appDiagram) {
		this.appDiagram = appDiagram;
	}

	public NodeItemModel getNodeItemModel() {
		return nodeItemModel;
	}

	public void setNodeItemModel(NodeItemModel nodeItemModel) {
		this.nodeItemModel = nodeItemModel;
	}

	public DeployAction(IWorkbenchWindow window) {
		setId("DeployAction");
		this.setText("");
		workbenchWindow = window;
		//		setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(301)));//PKY 08070101 S 팝업 메뉴 이미지 삽입
		this.setAccelerator(SWT.CTRL |'d'); //PKY 08061801 S 단축키 정의
	}

	public DeployAction() {
		setId("DeployAction");
		this.setText("");
		//		workbenchWindow = window;
		//		setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(301)));//PKY 08070101 S 팝업 메뉴 이미지 삽입
	}
	public DeployAction(String text) {
		setId("DeployAction");
		this.setText(text);
		//		workbenchWindow = window;
		//		setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(301)));//PKY 08070101 S 팝업 메뉴 이미지 삽입
	}

	public void run() {
		
		ProgressMonitorDialog progress =  new ProgressMonitorDialog(ProjectManager.getInstance().window.getShell());
		try{
			if(this.isTar){
				NetManager.getInstance().isJar = true;
			}
			else{
				NetManager.getInstance().isJar = false;
			}
			
			NetManager.getInstance().setAppDiagram(appDiagram);
			NetManager.getInstance().setText(this.getText());
			NetManager.getInstance().setNodeItemModel(nodeItemModel);
			NetManager.getInstance().setRunLoop(0);	//111108 SDM S - 실행루프.. (임시)
			progress.run(true, true, NetManager.getInstance());
			NetManager.getInstance().printMsg();
			NetManager.getInstance().isJar = false;
		}
		catch(Exception e){
			e.printStackTrace();
		}
//		boolean isChk = true; 
//		if(this.appDiagram!=null){
//			java.util.ArrayList temp = new java.util.ArrayList();
//			if("All".equals(this.getText())){
//				for(int i=0;i<appDiagram.getChildren().size();i++){
//
//					Object obj = appDiagram.getChildren().get(i);
//					if(obj instanceof ComponentModel){
//						ComponentModel cm = (ComponentModel)obj;
//						if(cm.getNodeItemModel()==null){
//							isChk = false;
//							ProjectManager.getInstance().getConsole().appendMessage2(cm.getName()+": node is not exist");
//							//							return;
//						}
//						else{
//							temp.add(cm);
//						}
//
//					}
//				}
//				if(!isChk){
//					ProjectManager.getInstance().getConsole().appendMessage2("deploy fail");
//					return;
//				}
//				for(int j=0;j<temp.size();j++){
//					ComponentModel cm = 	(ComponentModel)temp.get(j);
//					UMLModel addModel = cm;
//					N3EditorDiagramModel nd = ProjectManager.getInstance().getUMLEditor().getDiagram();
//
//
//					UMLTreeParentModel up = nd.getUMLTreeModel().getParent();
//					String str = CompAssemManager.getInstance().getNetFoler();
//					File target = new File(str);
//					if(addModel.getUMLTreeModel() instanceof UMLTreeParentModel){
//						CompAssemManager.getInstance().makeNetCopyMain(up, target,(UMLTreeParentModel)addModel.getUMLTreeModel());
//
//					}
//					NetManager.getInstance().setDirectory(new File(str));
//					NetManager.getInstance().setIp(cm.getNodeItemModel().getIP());
//					NetManager.getInstance().setPort(cm.getNodeItemModel().getPort1());
//					ProjectManager.getInstance().getConsole().addMessage(addModel.getName()+" Component 전송시작");
//					NetManager.getInstance().executeCommand(0);
//				}
//
//			}
//			else{
//
//				for(int i=0;i<appDiagram.getChildren().size();i++){
//
//					Object obj = appDiagram.getChildren().get(i);
//					if(obj instanceof ComponentModel){
//						ComponentModel cm = (ComponentModel)obj;
//						if(cm.getNodeItemModel()==null){
//							isChk = false;
//							ProjectManager.getInstance().getConsole().appendMessage2(cm.getName()+": node is not exist");
//							//							return;
//						}
//						else{
//							if(this.nodeItemModel == cm.getNodeItemModel())
//							temp.add(cm);
//						}
//
//					}
//				}
//				if(!isChk){
//					ProjectManager.getInstance().getConsole().appendMessage2("deploy fail");
//					return;
//				}
//				for(int j=0;j<temp.size();j++){
//					ComponentModel cm = 	(ComponentModel)temp.get(j);
//					UMLModel addModel = cm;
//					N3EditorDiagramModel nd = ProjectManager.getInstance().getUMLEditor().getDiagram();
//
//
//					UMLTreeParentModel up = nd.getUMLTreeModel().getParent();
//					String str = CompAssemManager.getInstance().getNetFoler();
//					File target = new File(str);
//					if(addModel.getUMLTreeModel() instanceof UMLTreeParentModel){
//						CompAssemManager.getInstance().makeNetCopyMain(up, target,(UMLTreeParentModel)addModel.getUMLTreeModel());
//
//					}
//					NetManager.getInstance().setDirectory(new File(str));
//					NetManager.getInstance().setIp(cm.getNodeItemModel().getIP());
//					NetManager.getInstance().setPort(cm.getNodeItemModel().getPort1());
//					ProjectManager.getInstance().getConsole().addMessage(addModel.getName()+" Component 전송시작");
//					NetManager.getInstance().executeCommand(0);
//				}
//
//			
//			}
//			
//		}
//
//
//	}
	}

	//	private String openFileDialog() {
	//	FileDialog dialog = new FileDialog(workbenchWindow.getShell(), SWT.OPEN);
	//	dialog.setText("shapes");
	//	//dialog.setFilterExtensions(new String[] { ".shapes" });
	//	return dialog.open();
	//	}
}
