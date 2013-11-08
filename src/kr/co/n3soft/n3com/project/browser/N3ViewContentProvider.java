package kr.co.n3soft.n3com.project.browser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import kr.co.n3soft.n3com.model.comm.FinalPackageModel;
import kr.co.n3soft.n3com.model.component.ComponentEditModel;
import kr.co.n3soft.n3com.model.node.NodeRootModel;
import kr.co.n3soft.n3com.projectmanager.CompAssemManager;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewSite;

public class N3ViewContentProvider implements IStructuredContentProvider, ITreeContentProvider {
	private UMLTreeParentModel invisibleRoot;
	IViewSite viewSite = null;
	RootTreeModel root = null;
	RootLibTreeModel rootLib = null;
	NodeRootTreeModel nodeRootTreeModel = null;
	RootServerRepositoryTreeMode rs = null;
	
	RootCmpComposingTreeModel rcctm = null;
	RootTemplateTreeModel rtt = null;
	
	public RootCmpComposingTreeModel getRcctm() {
		return rcctm;
	}

	public void setRcctm(RootCmpComposingTreeModel rcctm) {
		this.rcctm = rcctm;
	}

	RootCmpEdtTreeModel rcetm = null;

	public RootCmpEdtTreeModel getRcetm() {
		return rcetm;
	}

	public void setRcetm(RootCmpEdtTreeModel rcetm) {
		this.rcetm = rcetm;
	}
	
	RootCmpFnshTreeModel rcftm = null;

	public RootCmpFnshTreeModel getRcftm() {
		return rcftm;
	}

	public void setRcftm(RootCmpFnshTreeModel rcftm) {
		this.rcftm = rcftm;
	}

	MonitorRootTreeModel mrt = null;

	public RootServerRepositoryTreeMode getRs() {
		return rs;
	}

	public void setRs(RootServerRepositoryTreeMode rs) {
		this.rs = rs;
	}

	public NodeRootTreeModel getNodeRootTreeModel() {
		return nodeRootTreeModel;
	}

	public void setNodeRootTreeModel(NodeRootTreeModel nodeRootTreeModel) {
		this.nodeRootTreeModel = nodeRootTreeModel;
	}

	public void inputChanged(Viewer v, Object oldInput, Object newInput) {
	}

	public void dispose() {
	}

	public Object[] getElements(Object parent) {
		if (parent.equals(viewSite)) {
			if (invisibleRoot == null) initialize();
			return getChildren(invisibleRoot);
		}
		return getChildren(parent);
	}

	public Object getParent(Object child) {
		if (child instanceof UMLTreeModel) {
			return ((UMLTreeModel)child).getParent();
		}
		return null;
	}

	public Object[] getChildren(Object parent) {
		if (parent instanceof UMLTreeParentModel) {
			return ((UMLTreeParentModel)parent).getChildren();
		}
		return new Object[0];
	}

	public boolean hasChildren(Object parent) {
		if (parent instanceof UMLTreeParentModel)
			return ((UMLTreeParentModel)parent).hasChildren();
		return false;
	}

	/*
	 * We will set up a dummy model to initialize tree heararchy.
	 * In a real code, you will connect to a real model and
	 * expose its hierarchy.
	 */

	 private void initialize() {
		 root = new RootTreeModel("Solution");

		 invisibleRoot = new RootTreeModel("");
		 invisibleRoot.addChild(root);
		 
		 rcctm = new RootCmpComposingTreeModel("Component Composing"); 
		 rcetm = new RootCmpEdtTreeModel("Component Editing");
		 rcftm = new RootCmpFnshTreeModel("Component Editor Project");
		 
		 rtt = new RootTemplateTreeModel("Template");
		 if(!ProjectManager.getInstance().isLoad()){
			 rcetm = new RootCmpEdtTreeModel("Component Editing");
			 ComponentEditModel npm = new ComponentEditModel();
//			 npm.set

			 npm.setName("Component Editing");

			 rcetm.setRefModel(npm);

			 npm.setTreeModel(rcetm);
//			 nodeRootTreeModel.setParent(root);
//			 root.addChild(nodeRootTreeModel);
			 root.addChild(rcetm);
			 
			 
			 
		 }
//		 root.addChild(rcftm);
		 root.addChild(rcctm);
		 root.addChild(rtt);
		
		 if(!ProjectManager.getInstance().isLoad()){
			 FinalPackageModel finalPackageModel = new FinalPackageModel();
			 finalPackageModel.getUMLDataModel().setProperty("ID_TYPE", "Application");
			 ProjectManager.getInstance().addUMLModel("Application", rcctm, finalPackageModel, 0, 5);
		 }
		 //        ProjectManager.getInstance().setAppDiagram(finalPackageModel.getN3EditorDiagramModel());
		 rootLib = new RootLibTreeModel("Local Repository");
		 root.addChild(rootLib);
//		 invisibleRoot.addChild(rootLib);
		 rs = new RootServerRepositoryTreeMode("Remote Repository");
		 root.addChild(rs);
		 //        ProjectManager.getInstance().at
		 //		ProgressMonitorDialog progress =  new ProgressMonitorDialog(ProjectManager.getInstance().window.getShell());
		 ProjectManager.getInstance().window =  ProjectManager.getInstance().at.getWorkbench().getActiveWorkbenchWindow();
		 ProgressMonitorDialog progress =  new ProgressMonitorDialog(ProjectManager.getInstance().at.getWorkbench().getActiveWorkbenchWindow().getShell());
		 //		LoadLibProgress lp = new LoadLibProgress(true);
		 Display dis = Display.getDefault();		
		 Shell newShell = new Shell(dis, SWT.DIALOG_TRIM);
		 //111206 SDM S - Library를 전체 로드  하시겠습니까? Dialog 삭제
//		 MessageBox dialog = new MessageBox(newShell,SWT.ICON_QUESTION|SWT.YES|SWT.NO);
//		 dialog.setText("Message");
//		 dialog.setMessage("Library를 전체 로드  하시겠습니까?");
//		 int i=dialog.open();
//		
//		 switch(i) {
//		 case 64:
//			 CompAssemManager.getInstance().setTotal(true);
//			 break;
//		 case SWT.NO:
//			 CompAssemManager.getInstance().setTotal(false);
//			 break;
//		 case SWT.CANCEL:
//			 break;
//		 }
		 CompAssemManager.getInstance().setTotal(true);
		//111206 SDM E - Library를 전체 로드  하시겠습니까? Dialog 삭제
		 CompAssemManager.getInstance().setRunType(1);
		 CompAssemManager.getInstance().setRtm(rootLib);
		 try{
			 progress.run(true, true, CompAssemManager.getInstance());
		 }
		 catch(Exception e){
			 e.printStackTrace();
		 }
		 if(!ProjectManager.getInstance().isLoad()){
			 nodeRootTreeModel = new NodeRootTreeModel("Robots");
			 NodeRootModel npm = new NodeRootModel();

			 npm.setName("Node");

			 nodeRootTreeModel.setRefModel(npm);

			 npm.setTreeModel(nodeRootTreeModel);
			 nodeRootTreeModel.setParent(root);
			 root.addChild(nodeRootTreeModel);
			 
			 
			 
		 }


		 //		 mrt = new MonitorRootTreeModel("monitor");
		 //		 invisibleRoot.addChild(mrt);

		 try{
			 File ipFile = new File("c:\\ip.txt");
			 FileReader fr = new FileReader(ipFile);
			 BufferedReader bf = new BufferedReader(fr);
			 if(ipFile.exists()){
				 if(ipFile.canRead()){

					 String line = null;
					 while((line=bf.readLine())!=null){
						 ServerRepositoryTreeModel srt= new ServerRepositoryTreeModel(line);
						 srt.setParent(rs);
						 rs.addChild(srt);
					 }
					 ProjectManager.getInstance().getModelBrowser().refresh(rs);
					 ProjectManager.getInstance().getModelBrowser().expend(rs);
				 }
			 }

		 }
		 catch(Exception e){

		 }


		 //        CompAssemManager.getInstance().makeLib(rootLib);
		 
		 //110810서동민 컴포넌트에디터 프로젝트 불러오기
//		 ProjectManager.getInstance().getModelBrowser().CmpEdtPjtLoad(rcftm);
//		 System.out.println("에디터 프로젝트 불러오기 완료");
		 
//		 CompAssemManager.getInstance().setTotal(true);
//		 CompAssemManager.getInstance().setRunType(7);
//		 CompAssemManager.getInstance().setRcefm(rcftm);
		 
	 }

	 public IViewSite getViewSite() {
		 return viewSite;
	 }

	 public void setViewSite(IViewSite viewSite) {
		 this.viewSite = viewSite;
	 }

	 public RootTreeModel getRoot() {
		 return root;
	 }

	 public void setRoot(RootTreeModel root) {
		 this.root = root;
	 }

	 public RootLibTreeModel getRootLib() {
		 return rootLib;
	 }

	 public void setRootLib(RootLibTreeModel rootLib) {
		 this.rootLib = rootLib;
	 }
}
