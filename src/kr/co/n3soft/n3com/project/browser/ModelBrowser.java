package kr.co.n3soft.n3com.project.browser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javac.parser.ReverseFromJavaToModel;
import kr.co.n3soft.n3com.OPRoSUtil;
import kr.co.n3soft.n3com.edit.UMLEditPart;
import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.activity.FinalActivityModel;
import kr.co.n3soft.n3com.model.activity.FinalStrcuturedActivityModel;
import kr.co.n3soft.n3com.model.comm.ClassifierModel;
import kr.co.n3soft.n3com.model.comm.DocComponentModel;
import kr.co.n3soft.n3com.model.comm.FinalPackageModel;
import kr.co.n3soft.n3com.model.comm.LineModel;
import kr.co.n3soft.n3com.model.comm.PortContainerModel;
import kr.co.n3soft.n3com.model.comm.PortModel;
import kr.co.n3soft.n3com.model.comm.StrcuturedPackageTreeModel;
import kr.co.n3soft.n3com.model.comm.TypeRefModel;
import kr.co.n3soft.n3com.model.comm.UMLElementModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.component.AtomicComponentModel;
import kr.co.n3soft.n3com.model.component.ComponentLibModel;
import kr.co.n3soft.n3com.model.component.ComponentModel;
import kr.co.n3soft.n3com.model.component.TemplateComponentModel;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;
import kr.co.n3soft.n3com.model.node.NodeItemModel;
import kr.co.n3soft.n3com.model.node.NodePackageModel;
import kr.co.n3soft.n3com.model.umlclass.EnumerationModel;
import kr.co.n3soft.n3com.model.umlclass.FinalClassModel;
import kr.co.n3soft.n3com.model.umlclass.InterfaceModel;
import kr.co.n3soft.n3com.model.usecase.CollaborationModel;
import kr.co.n3soft.n3com.model.usecase.FinalActorModel;
import kr.co.n3soft.n3com.model.usecase.UseCaseModel;
import kr.co.n3soft.n3com.net.HeaderReceiveData;
import kr.co.n3soft.n3com.net.HeaderSendData;
import kr.co.n3soft.n3com.net.NetIPPort;
import kr.co.n3soft.n3com.net.NetManager;
import kr.co.n3soft.n3com.net.SocketUtil;
import kr.co.n3soft.n3com.parser.LinkModel;
import kr.co.n3soft.n3com.parser.ModelManager;
import kr.co.n3soft.n3com.parser.RefLinkModel;
import kr.co.n3soft.n3com.parser.TeamProjectManager;
import kr.co.n3soft.n3com.project.dialog.CInputDialog;
import kr.co.n3soft.n3com.project.dialog.NewDiagramDialog;
import kr.co.n3soft.n3com.project.dialog.OPRoSRegenSourceQuestionDialog;
import kr.co.n3soft.n3com.projectmanager.CompAssemManager;
import kr.co.n3soft.n3com.projectmanager.ExcelManager;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;
import kr.co.n3soft.n3com.rcp.actions.DeployAction;
import kr.co.n3soft.n3com.rcp.actions.LoadAction;
import kr.co.n3soft.n3com.rcp.actions.NewAction;
import kr.co.n3soft.n3com.rcp.actions.OpenComponentEditorAction;
import kr.co.n3soft.n3com.rcp.actions.OpenN3PropertyDialogAction;
import kr.co.n3soft.n3com.rcp.actions.RefreshAction;
import kr.co.n3soft.n3com.rcp.actions.SaveAction;
import kr.co.n3soft.n3com.rcp.actions.SaveAsAction;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.gef.dnd.TemplateTransfer;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.DrillDownAdapter;
import org.eclipse.ui.part.ViewPart;

import uuu.Activator;
import uuu.ApplicationActionBarAdvisor;
import uuu.preferences.PreferenceConstants;

public class ModelBrowser extends ViewPart{
	public  static int saveValue = 0; 
	public  boolean isChange = false;

	public boolean isChange() {
		return isChange;
	}

	public void setChange(boolean isChange) {
		this.isChange = isChange;
	}
	private TreeViewer viewer;
	private TreeItem[] items;
	private DrillDownAdapter drillDownAdapter;
	//	private Action action1;
	//	private Action action2;
	private Action saveExcel;		// V1.01 WJH E 080423 추가
	private Action loadExcel;		// V1.01 WJH E 080430 추가

	private Action requiredExcel;	// V1.02 WJH E 080904 요구사항 추적표 추가
	private Action excelReqModelSave;// V1.02 WJH E 080904 요구사항 추적표 추가

	private Action newApplication;
	private Action addPackage;
	private Action addDiagram;
	private Action addModel;
	private Action addClassDiagram;
	private Action deleteModel;
	
	private Action syncModel;
	
	private Action addRefModel;
	private Action reName;
	private Action findModelBrowser;

	private Action repoDeleteModel;

	private Action porperties;
	private Action exportComponent;
	private Action exportComponent_tar;
	
	private Action saveDiagram;

	private Action exportApplication;
	private Action exportApplication_tar;
	
	private Action importApplication;//khg 2010.05.26 임포트 어플리케이션 
	
//	private Action importModelComponent;//khg 2010.05.26 임포트 어플리케이션 
	
	private Action importModelComponent;
	
	private Action deployApplication;

	private Action mainNew;//khg 10.4.26새로운다이어그램
	private Action mainSave;
	private Action mainOpen;
	private Action mainSaveAS;
	
	private Action addTemplate;
	
	private Action addAppTemplate;
	
	private Action addMSVCTemplate;
	
	private Action addMSVCAppTemplate;


	private Action importComponent;
	//20080721IJS
	private Action findReqModelBrowser;
	//	private Action action1;
	//	private Action action2;
	private Action doubleClickAction;
	//	PKY

	TreeSelection iSelection =null;
	UMLTreeModel treeObject=null;
	Object tree=null;

	boolean isElementSubmenu=false;
	private Action addUseCaseDiagram;
	private Action addActivityDiagram;
	private Action addCompositeDiagram;
	private Action addInteractionDiagram;
	private Action addComponenteDiagram;
	private Action addStateDiagram;
	private Action addDeploymenteDiagram;
	private Action addCommunicationDiagram;
	private Action addTimingDiagram;
	private Action refresh;
	N3ViewContentProvider n3ViewContentProvider;
	//PKY
	//20080811IJS 시작
	private Action saveLink;
	private Action loadLink;
	private Action addLink;

	private Action addNodePackage;
	private Action deleteNodePackage;
	private Action addNodeItem;
	private Action deleteNodeItem;

	//모니터링
	private Action appListAction;
	private Action connectMonitor;
	private Action appCompListAction;

	private Action appRunAction;
	private Action appStopAction;
	private Action appStateAction;
	private Action compStateAction;
	private Action compListAction;
	private Action compPropAction;

	private Action newCompCreateAction;
	private Action atCompRefreshAction;	//새로고침
	private Action openCmpEdt;	//컴포넌트에디터 호출
	private Action exportComp;	//110818 SDM
	private Action reGenAction;
	private Action teGenAction;
	// IJS 2010-08-13 2:16오후 소스생성  관련
	private Action reGenMSVCAction;
	
	private Action allLoad;

	private Action openN3PropertyDialogAction = null;
	//20080811IJS 끝

	private Action reportAction; //20080508 KDI s

	private Action writeJava;
	private Action writeH;
	RootTreeModel root;
	RootLibTreeModel rootLib; 
	IViewSite ivewSite = null;

	private Action connectRepository;

	private UMLTreeModel appTreeModel = null;

	RootCmpComposingTreeModel rcctm = null;
	RootCmpEdtTreeModel rcet = null;
	// IJS 2010-08-12 4:17오후  라이브러리 이동 관련
	private Action exportAtomicComponent;
	
	private Action openTemplateSrcCPPComponent;
	private Action openTemplateSrcHComponent;
	
	private Action openMergecpp;
	private Action openMergeH;
	
	private Action loadTemplate;
	
	private boolean isMerge = false;




	/**
	 * @return
	 */
	public RootCmpEdtTreeModel getRcet() {
		//		if(rcet==null){
		for(int i = 0;i<viewer.getTree().getItems().length;i++){
			TreeItem ut = viewer.getTree().getItems()[i];
			if(ut.getData() instanceof RootTreeModel){
				for(int j=0;j<ut.getItems().length;j++){
					TreeItem child =ut.getItems()[j];
					if(child.getData() instanceof RootCmpEdtTreeModel){

						return (RootCmpEdtTreeModel)child.getData();
					}
				}
				//					return rcet;
			}
		}
		return null;
		//		}
		//		return rcet;
	}

	public void setRcet(RootCmpEdtTreeModel rcet) {
		this.rcet = rcet;
	}

	public RootCmpComposingTreeModel getRcctm() {
		return rcctm;
	}

	public void setRcctm(RootCmpComposingTreeModel rcctm) {
		this.rcctm = rcctm;
	}
	private Action connect;

	ApplicationActionBarAdvisor application = ProjectManager.getInstance().getApplication();

	//모니터링
	public static boolean  isTest = false;


	int y = 0;

	/*
	 * The content provider class is responsible for
	 * providing objects to the view. It can wrap
	 * existing objects in adapters or simply return
	 * objects as-is. These objects may be sensitive
	 * to the current input of the view, or ignore
	 * it and always show the same content
	 * (like Task List, for example).
	 */

	//	class ViewContentProvider implements IStructuredContentProvider, ITreeContentProvider {
	//	private UMLTreeParentModel invisibleRoot;

	//	public void inputChanged(Viewer v, Object oldInput, Object newInput) {
	//	}

	//	public void dispose() {
	//	}

	//	public Object[] getElements(Object parent) {
	//	if (parent.equals(getViewSite())) {
	//	if (invisibleRoot == null) initialize();
	//	return getChildren(invisibleRoot);
	//	}
	//	return getChildren(parent);
	//	}

	//	public Object getParent(Object child) {
	//	if (child instanceof UMLTreeModel) {
	//	return ((UMLTreeModel)child).getParent();
	//	}
	//	return null;
	//	}

	//	public Object[] getChildren(Object parent) {
	//	if (parent instanceof UMLTreeParentModel) {
	//	return ((UMLTreeParentModel)parent).getChildren();
	//	}
	//	return new Object[0];
	//	}

	//	public boolean hasChildren(Object parent) {
	//	if (parent instanceof UMLTreeParentModel)
	//	return ((UMLTreeParentModel)parent).hasChildren();
	//	return false;
	//	}

	//	/*
	//	* We will set up a dummy model to initialize tree heararchy.
	//	* In a real code, you will connect to a real model and
	//	* expose its hierarchy.
	//	*/

	//	private void initialize() {
	//	root = new RootTreeModel("Model");
	//	invisibleRoot = new RootTreeModel("");
	//	invisibleRoot.addChild(root);
	//	}
	//	}


	class NameSorter extends ViewerSorter {
		//PKY 08090201 S ModelBrowser Sort
		public int compare(Viewer viewer, Object obj1, Object obj2) {
			int i = 0;
			int index1 = 0;
			int index2 = 0;

			if(obj1 instanceof UMLTreeParentModel && obj2 instanceof UMLTreeParentModel){
				UMLTreeParentModel umlModel1 = (UMLTreeParentModel)obj1;
				UMLTreeParentModel umlModel2 = (UMLTreeParentModel)obj2;
				if(umlModel1.rank!=-1 && umlModel2.rank!=-1){
					if(umlModel1.rank>umlModel2.rank)
						return 1;
					else
						return -1;
				}
			}
			 if(obj1 instanceof UMLTreeModel){
				UMLTreeModel ut = (UMLTreeModel)obj1;
				Object objN = ut.getRefModel();
				if(objN instanceof N3EditorDiagramModel){
					return -1;
				}
			}
			  if(obj2 instanceof UMLTreeModel){
				UMLTreeModel ut = (UMLTreeModel)obj2;
				Object objN = ut.getRefModel();
				if(objN instanceof N3EditorDiagramModel){
					return 1;
				}
			}
			return i;

			//			if(obj1 instanceof UMLTreeModel && obj2 instanceof UMLTreeModel){
			//				UMLTreeModel umlModel1 = (UMLTreeModel)obj1;
			//				UMLTreeModel umlModel2 = (UMLTreeModel)obj2;
			//				
			//
			//				if(umlModel1.getRefModel()!=null && umlModel2.getRefModel()!=null){
			//
			//					if(umlModel1.getRefModel() instanceof FinalPackageModel ){
			//						index1 = 0;
			//					}
			//
			//					else if(umlModel1.getRefModel() instanceof N3EditorDiagramModel){
			//						index1 = 1;
			//					}
			//
			//					else if(umlModel1.getRefModel() instanceof FinalActorModel){
			//						index1 = 2;
			//					}
			//
			//					else if(umlModel1.getRefModel() instanceof FinalActiionModel){
			//						index1 = 3;
			//					}
			//
			//					else if(umlModel1.getRefModel() instanceof FinalActivityModel){
			//						index1 = 4;
			//					}
			//
			//					else if(umlModel1.getRefModel() instanceof ArtfifactModel){
			//						index1 = 5;
			//					}
			//
			//					else if(umlModel1.getRefModel() instanceof CentralBufferNodeModel){
			//						index1 = 6;
			//					}
			//
			//					else if(umlModel1.getRefModel() instanceof FinalClassModel){
			//						index1 = 7;
			//					}
			//
			//					else if(umlModel1.getRefModel() instanceof CollaborationModel){
			//						index1 = 8;
			//					}
			//
			//					else if(umlModel1.getRefModel() instanceof ComponentModel){
			//						index1 = 9;
			//					}
			//
			//					else if(umlModel1.getRefModel() instanceof DataStoreModel){
			//						index1 = 10;
			//					}
			//
			//					//					else if(umlModel1.getRefModel() instanceof DeploymentSpecificationModel){
			//					//						index1 = 11;
			//					//					}
			//
			//					//					else if(umlModel1.getRefModel() instanceof DeviceModel){
			//					//						index1 = 12;
			//					//					}
			//
			//					else if(umlModel1.getRefModel() instanceof EnumerationModel){
			//						index1 = 13;
			//					}
			//
			//					else if(umlModel1.getRefModel() instanceof ExceptionModel){
			//						index1 = 14;
			//					}
			//
			//					//					else if(umlModel1.getRefModel() instanceof ExecutionEnvironmentModel){
			//					//						index1 = 15;
			//					//					}
			//
			//					else if(umlModel1.getRefModel() instanceof InterfaceModel){
			//						index1 = 16;
			//					}
			//
			//
			//
			//
			//
			//					//					else if(umlModel1.getRefModel() instanceof NodeModel){
			//					//						index1 = 19;
			//					//					}
			//
			//					else if(umlModel1.getRefModel() instanceof FinalObjectNodeModel){
			//						index1 = 20;
			//					}
			//
			//					else if(umlModel1.getRefModel() instanceof PartModel){
			//						index1 = 21;
			//					}
			//
			//					else if(umlModel1.getRefModel() instanceof ReceiveModel){
			//						index1 = 22;
			//					}
			//
			//					
			//
			//					else if(umlModel1.getRefModel() instanceof SendModel){
			//						index1 = 24;
			//					}
			//
			//					
			//
			//
			//					else if(umlModel1.getRefModel() instanceof FinalStrcuturedActivityModel){
			//						index1 = 27;
			//					}
			//
			//
			//
			//					else if(umlModel1.getRefModel() instanceof UseCaseModel){
			//						index1 = 29;
			//					}
			//
			//
			//
			//					if(umlModel2.getRefModel() instanceof FinalPackageModel ){
			//						index2 = 0;
			//					}
			//
			//					else if(umlModel2.getRefModel() instanceof N3EditorDiagramModel){
			//						index2 = 1;
			//					}
			//					
			//				
			//
			//					else if(umlModel2.getRefModel() instanceof FinalActorModel){
			//						index2 = 2;
			//					}
			//
			//					else if(umlModel2.getRefModel() instanceof FinalActiionModel){
			//						index2 = 3;
			//					}
			//
			//					else if(umlModel2.getRefModel() instanceof FinalActivityModel){
			//						index2 = 4;
			//					}
			//
			//					else if(umlModel2.getRefModel() instanceof ArtfifactModel){
			//						index2 = 5;
			//					}
			//
			//					else if(umlModel2.getRefModel() instanceof CentralBufferNodeModel){
			//						index2 = 6;
			//					}
			//
			//					else if(umlModel2.getRefModel() instanceof FinalClassModel){
			//						index2 = 7;
			//					}
			//
			//					else if(umlModel2.getRefModel() instanceof CollaborationModel){
			//						index2 = 8;
			//					}
			//
			//					else if(umlModel2.getRefModel() instanceof ComponentModel){
			//						index2 = 9;
			//					}
			//
			//					else if(umlModel2.getRefModel() instanceof DataStoreModel){
			//						index2 = 10;
			//					}
			//
			//					//					else if(umlModel2.getRefModel() instanceof DeploymentSpecificationModel){
			//					//						index2 = 11;
			//					//					}
			//
			//					//					else if(umlModel2.getRefModel() instanceof DeviceModel){
			//					//						index2 = 12;
			//					//					}
			//
			//					else if(umlModel2.getRefModel() instanceof EnumerationModel){
			//						index2 = 13;
			//					}
			//
			//					else if(umlModel2.getRefModel() instanceof ExceptionModel){
			//						index2 = 14;
			//					}
			//
			//					//					else if(umlModel2.getRefModel() instanceof ExecutionEnvironmentModel){
			//					//						index2 = 15;
			//					//					}
			//
			//					else if(umlModel2.getRefModel() instanceof InterfaceModel){
			//						index2 = 16;
			//					}
			//
			//
			//
			//					//					else if(umlModel2.getRefModel() instanceof NodeModel){
			//					//						index2 = 19;
			//					//					}
			//
			//					else if(umlModel2.getRefModel() instanceof FinalObjectNodeModel){
			//						index2 = 20;
			//					}
			//
			//					else if(umlModel2.getRefModel() instanceof PartModel){
			//						index2 = 21;
			//					}
			//
			//					else if(umlModel2.getRefModel() instanceof ReceiveModel){
			//						index2 = 22;
			//					}
			//
			//					
			//
			//					else if(umlModel2.getRefModel() instanceof SendModel){
			//						index2 = 24;
			//					}
			//
			//					
			//
			//
			//					else if(umlModel2.getRefModel() instanceof FinalStrcuturedActivityModel){
			//						index2 = 27;
			//					}
			//
			//
			//
			//					else if(umlModel2.getRefModel() instanceof UseCaseModel){
			//						index2 = 29;
			//					}
			//
			//					if(index2 >= index1)
			//						i = -1;
			//					else
			//						i = 1;
			//
			//				}
			//
			//			}
			//			return i;
//			ViewerSorter
		}
	}

	public void dispose() {
		ProjectManager.getInstance().setRoot(root);
		// WJH 100326 S 종료 시 저장로직 추가
		if((ProjectManager.getInstance().getCommandStack()!=null && ProjectManager.getInstance().getModelBrowser()!=null) && (ProjectManager.getInstance().getCommandStack().isDirty() ||  ProjectManager.getInstance().getModelBrowser().isChange)){
			Display dis = Display.getDefault();		
			Shell newShell = new Shell(dis, SWT.DIALOG_TRIM);
			MessageBox dialog = new MessageBox(newShell,SWT.ICON_QUESTION|SWT.YES|SWT.NO);
			dialog.setText("Message");
			dialog.setMessage("OPRos Component Composer 프로젝트를 세이브 하시겠습니까?");
			int i=dialog.open();
			switch(i) {
			case 64:
				System.out.println("Scrip Wizard Finish!!");
				ProjectManager.getInstance().getUMLEditor().doSaveAs(newShell);
				break;
			case SWT.NO:
				return;
			case SWT.CANCEL:
				break;
			}
		}
		// WJH 100326 E 종료 시 저장로직 추가
		super.dispose();
	}

	/** The constructor. */
	public ModelBrowser() {
		ProjectManager.getInstance().setModelBrowser(this);


	}

	public void init(){
		isChange = false;
		n3ViewContentProvider = new N3ViewContentProvider();
		n3ViewContentProvider.setViewSite(this.getViewSite());
		n3ViewContentProvider.setRoot(root);
		viewer.setContentProvider(n3ViewContentProvider);
		root = n3ViewContentProvider.root;
		this.rootLib = n3ViewContentProvider.getRootLib();
		this.rcet = n3ViewContentProvider.getRcetm();
		System.out.println("dddddd");
		ProjectManager.getInstance().window = ProjectManager.getInstance().at.getWorkbench().getActiveWorkbenchWindow();
	}
	//ijs080429
	public void writeModel(String filePath){
		StringBuffer sb = new StringBuffer();
		for(int i = 0;i<viewer.getTree().getItems().length;i++){
			TreeItem ut = viewer.getTree().getItems()[i];
			if(ut.getData() instanceof RootTreeModel
					&& !(ut.getData() instanceof RootLibTreeModel)){
				RootTreeModel rt = (RootTreeModel)ut.getData();
				rt.writeModel(sb);
				//				rt.searchModel(type, text, isCase, isWwo, isDesc);
			}
		}
		try{
			FileWriter fw = new FileWriter(new File(filePath));
			BufferedWriter bw=new BufferedWriter(fw);

			bw.write(sb.toString());
			bw.close();
			saveValue = -1;

		}
		catch(Exception e){
			e.printStackTrace();

		}


	}


	// WJH 100326 S 종료 시 저장로직 추가
	public void writeModel2(String filePath){		
		StringBuffer sb = new StringBuffer();
		root.writeModel(sb);
		//		for(int i = 0;i<items.length;i++){
		//			TreeItem ut = items[i];
		//			if(ut.getData() instanceof RootTreeModel
		//					&& !(ut.getData() instanceof RootLibTreeModel)){
		//				RootTreeModel rt = (RootTreeModel)ut.getData();
		//				rt.writeModel(sb);
		//				//				rt.searchModel(type, text, isCase, isWwo, isDesc);
		//			}
		//		}
		try{
			FileWriter fw = new FileWriter(new File(filePath));
			BufferedWriter bw=new BufferedWriter(fw);

			bw.write(sb.toString());
			bw.close();
			saveValue = -1;

		}
		catch(Exception e){
			e.printStackTrace();

		}
	}
	// WJH 100326 E 종료 시 저장로직 추가

	public void writeComponentXMLModel(String filePath,UMLTreeParentModel up){
		StringBuffer sb = new StringBuffer(); 
		try{

			String wsb= CompAssemManager.getInstance().writeExportComponentXML(up, sb);
			FileWriter fw = new FileWriter(new File(filePath));
			BufferedWriter bw=new BufferedWriter(fw);

			bw.write(wsb);
			bw.close();

		}
		catch(Exception e){
			e.printStackTrace();

		}	
	}

	public void writeMainXMLModel(String filePath,UMLTreeParentModel up){
		StringBuffer sb = new StringBuffer(); 
		try{

			String wsb= CompAssemManager.getInstance().writeExportAppComponentXML(up, sb);
			FileWriter fw = new FileWriter(new File(filePath));
			BufferedWriter bw=new BufferedWriter(fw);

			bw.write(wsb);
			bw.close();

		}
		catch(Exception e){
			e.printStackTrace();

		}	
	}

	public void writeComponentViewXMLModel(String filePath,UMLTreeParentModel up){
		StringBuffer sb = new StringBuffer(); 
		try{

			String wsb= CompAssemManager.getInstance().writeExportComponentViewXML(up, sb);
			FileWriter fw = new FileWriter(new File(filePath));
			BufferedWriter bw=new BufferedWriter(fw);

			bw.write(wsb);
			bw.close();

		}
		catch(Exception e){
			e.printStackTrace();

		}	
	}

	public void writeComponentAppXMLModel(String filePath,UMLTreeParentModel up){
		StringBuffer sb = new StringBuffer(); 
		try{

			String wsb= CompAssemManager.getInstance().writeExportAppComponentXML(up, sb);
			File f = new File(filePath);
			FileWriter fw = new FileWriter(f);
			BufferedWriter bw=new BufferedWriter(fw);

			bw.write(wsb);
			bw.close();
			fw.close();
			
			if(NetManager.getInstance().isJar){
				up.oPRoSManifest.f = new File(f.getParent()+File.separator+"OPRoS.mf");
				up.oPRoSManifest.writeOPRoSManifest();
			}

		}
		catch(Exception e){
			e.printStackTrace();

		}	
	}

	//20080811IJS 시작
	public void writeLinkModel(String filePath,UMLTreeParentModel up){


		StringBuffer sb = new StringBuffer();
		up.setLinkPath(filePath);
		up.writeModel(sb);
		ModelManager.getInstance().writeViews(sb);
		try{

			//PKY 08082201 S 팀 프로젝트 경로를 프로젝트에서 변경가능하도록
			if(filePath.indexOf(TeamProjectManager.getInstance().ID_TEAM_PROJECT_FOLDER)>-1){
				filePath = filePath.replace(TeamProjectManager.getInstance().ID_TEAM_PROJECT_FOLDER, ProjectManager.getInstance().getTeamProjectFolder());
			}
			//PKY 08082201 E 팀 프로젝트 경로를 프로젝트에서 변경가능하도록
			FileWriter fw = new FileWriter(new File(filePath));
			BufferedWriter bw=new BufferedWriter(fw);

			bw.write(sb.toString());
			bw.close();
			saveValue = -1;
			//			TeamProjectManager.getInstance().addLink(up,filePath);
			up.setIsLinkType(1);
			this.refresh(up);
		}
		catch(Exception e){
			e.printStackTrace();

		}


	}
	//20080811IJS 끝

	public void writeJavas(String path){
		for(int i = 0;i<viewer.getTree().getItems().length;i++){
			TreeItem ut = viewer.getTree().getItems()[i];
			if(ut.getData() instanceof RootTreeModel){
				RootTreeModel rt = (RootTreeModel)ut.getData();
				rt.writeJavas();
				//				rt.searchModel(type, text, isCase, isWwo, isDesc);
			}
		}
	}

	public Object getAdapter(Class type) {
		//		if (IPropertySheetPage.class.equals(type)) {
		//            return new PropertySheetPage();
		//        }
		return super.getAdapter(type);
	}

	public TreeViewer getViewer(){
		return this.viewer;
	}





	/** This is a callback that will allow us to create the viewer and initialize it. */
	public void createPartControl(Composite parent) {
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.SELECTED);
		this.getSite().setSelectionProvider(viewer);
		Transfer[] types = new Transfer[] {
				TemplateTransfer.getInstance()
		};
		//		 viewer.addDropTargetListener((TransferDropTargetListener)
		//					new TemplateTransferDropTargetListener(getGraphicalViewer()));
		//		 viewer.addDropTargetListener((TransferDropTargetListener)
		//					new TextTransferDropTargetListener(
		////							getGraphicalViewer(), TextTransfer.getInstance()));
		ViewerDropAdapter testdropper;
		viewer.addDragSupport(DND.DROP_COPY | DND.DROP_MOVE, types, new ModelBrowserDragListener(this));
		viewer.addDropSupport(DND.DROP_COPY | DND.DROP_MOVE, types, new ModelBrowerViewerDropAdapter(viewer));
		drillDownAdapter = new DrillDownAdapter(viewer);
		N3ViewContentProvider n3ViewContentProvider = new N3ViewContentProvider();
		n3ViewContentProvider.setViewSite(this.getViewSite());
		n3ViewContentProvider.setRoot(root);
		viewer.setContentProvider(n3ViewContentProvider);
		viewer.setLabelProvider(new ViewLabelProvider());

		CellEditor editors[] = new CellEditor[1];
		//		editors[0] = null;
		editors[0] = new TextCellEditor(viewer.getTree(), SWT.NONE);
		viewer.setCellEditors(editors);

		//		viewer.setCellModifier(new CustomCellModifier());
		viewer.setCellModifier(
				new ICellModifier() {
					public boolean canModify(Object element, String property) {
						return true;
					}
					public Object getValue(Object element, String property) {
						if (element instanceof UMLTreeModel)
							return ((UMLTreeModel)element).getName();
						return null;
					}
					public void modify(Object element, String property, Object value) {
						//				Item item = (Item) ((TreeItem) element).getData();
						if (element instanceof UMLTreeModel)
							((UMLTreeModel)element).setName((String)value);
						//				else if (item instanceof Orange)
						//					((Orange) item).setVariety((OrangeVariety) value);
					}
				});
		viewer.setSorter(new NameSorter());
		viewer.setInput(getViewSite());
		this.getSite().setSelectionProvider(viewer);


		// WJH 100326 S 종료 시 저장로직 추가
		Object obj = viewer.getTree().getItems()[0].getData();
		this.setRoot((RootTreeModel)obj);
		// WJH 100326 S 종료 시 저장로직 추가


		makeActions();
		menuInit();
		//PKY 08082201 S 팀 프로젝트 읽기전용일때 수정불가능하도록 작업

		hookContextMenu();
		hookSelectAction();
		hookDoubleClickAction();
		contributeToActionBars();


	}
	private void hookContextInstanceMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(
				new IMenuListener() {
					public void menuAboutToShow(IMenuManager manager) {
						ModelBrowser.this.fillInstanceContextMenu(manager);
					}
				});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}
	
	private void hookContextTemplateComponentModelMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(
				new IMenuListener() {
					public void menuAboutToShow(IMenuManager manager) {
						ModelBrowser.this.fillTemplateComponentModelContextMenu(manager);
					}
				});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}
	
	private void hookContextAddAppTemplateModelModelMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(
				new IMenuListener() {
					public void menuAboutToShow(IMenuManager manager) {
						ModelBrowser.this.fillAddAppTemplateModelContextMenu(manager);
					}
				});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}
	
	
	private void hookContextRootTemplatePackageTreeModelMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(
				new IMenuListener() {
					public void menuAboutToShow(IMenuManager manager) {
						ModelBrowser.this.fillRootTemplatePackageTreeModelContextMenu(manager);
					}
				});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}
	
	private void hookContextRootCmpComposingTreeModelMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(
				new IMenuListener() {
					public void menuAboutToShow(IMenuManager manager) {
						ModelBrowser.this.fillRootCmpComposingTreeModelContextMenu(manager);
					}
				});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}
	
	
	private void hookContextLoadTemplateModelModelMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(
				new IMenuListener() {
					public void menuAboutToShow(IMenuManager manager) {
						ModelBrowser.this.fillLoadTemplateContextMenu(manager);
					}
				});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}


	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(
				new IMenuListener() {
					public void menuAboutToShow(IMenuManager manager) {
						ModelBrowser.this.fillContextMenu(manager);
					}
				});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void hookRootTreeModelContextMenu() {
		try{
			MenuManager menuMgr = new MenuManager("#PopupMenu");
			menuMgr.setRemoveAllWhenShown(true);
			menuMgr.addMenuListener(
					new IMenuListener() {
						public void menuAboutToShow(IMenuManager manager) {
							ModelBrowser.this.fillRootTreeModelContextMenu(manager);
						}
					});
			Menu menu = menuMgr.createContextMenu(viewer.getControl());
			viewer.getControl().setMenu(menu);
			getSite().registerContextMenu(menuMgr, viewer);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	//모니터링
	private void hookMonitorRootTreeModelContextMenu() {
		try{
			MenuManager menuMgr = new MenuManager("#PopupMenu");
			menuMgr.setRemoveAllWhenShown(true);
			menuMgr.addMenuListener(
					new IMenuListener() {
						public void menuAboutToShow(IMenuManager manager) {
							ModelBrowser.this.fillMoniitorRootTreeModelContextMenu(manager);
						}
					});
			Menu menu = menuMgr.createContextMenu(viewer.getControl());
			viewer.getControl().setMenu(menu);
			getSite().registerContextMenu(menuMgr, viewer);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	private void hookMonitorTreeModelContextMenu() {
		try{
			MenuManager menuMgr = new MenuManager("#PopupMenu");
			menuMgr.setRemoveAllWhenShown(true);
			menuMgr.addMenuListener(
					new IMenuListener() {
						public void menuAboutToShow(IMenuManager manager) {
							ModelBrowser.this.fillMoniitorTreeModelContextMenu(manager);
						}
					});
			Menu menu = menuMgr.createContextMenu(viewer.getControl());
			viewer.getControl().setMenu(menu);
			getSite().registerContextMenu(menuMgr, viewer);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	private void hookAppTreeModelModelContextMenu() {
		try{
			MenuManager menuMgr = new MenuManager("#PopupMenu");
			menuMgr.setRemoveAllWhenShown(true);
			menuMgr.addMenuListener(
					new IMenuListener() {
						public void menuAboutToShow(IMenuManager manager) {
							ModelBrowser.this.fillAppTreeModelContextMenu(manager);
						}
					});
			Menu menu = menuMgr.createContextMenu(viewer.getControl());
			viewer.getControl().setMenu(menu);
			getSite().registerContextMenu(menuMgr, viewer);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	private void hookMonitorCompTreeModelContextMenu() {
		try{
			MenuManager menuMgr = new MenuManager("#PopupMenu");
			menuMgr.setRemoveAllWhenShown(true);
			menuMgr.addMenuListener(
					new IMenuListener() {
						public void menuAboutToShow(IMenuManager manager) {
							ModelBrowser.this.fillMonitorCompTreeModelContextMenu(manager);
						}
					});
			Menu menu = menuMgr.createContextMenu(viewer.getControl());
			viewer.getControl().setMenu(menu);
			getSite().registerContextMenu(menuMgr, viewer);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	private void hookREpoCompTreeModelContextMenu() {
		try{
			MenuManager menuMgr = new MenuManager("#PopupMenu");
			menuMgr.setRemoveAllWhenShown(true);
			menuMgr.addMenuListener(
					new IMenuListener() {
						public void menuAboutToShow(IMenuManager manager) {
							ModelBrowser.this.fillRepoComponentTreeModelContextMenu(manager);
						}
					});
			Menu menu = menuMgr.createContextMenu(viewer.getControl());
			viewer.getControl().setMenu(menu);
			getSite().registerContextMenu(menuMgr, viewer);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	private void hookRootCmpEdtTreeModelContextMenu() {
		try{
			MenuManager menuMgr = new MenuManager("#PopupMenu");
			menuMgr.setRemoveAllWhenShown(true);
			menuMgr.addMenuListener(
					new IMenuListener() {
						public void menuAboutToShow(IMenuManager manager) {
							ModelBrowser.this.fillRootCmpEdtTreeModelContextMenu(manager);
						}
					});
			Menu menu = menuMgr.createContextMenu(viewer.getControl());
			viewer.getControl().setMenu(menu);
			getSite().registerContextMenu(menuMgr, viewer);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//20110729서동민 >> 새 트리 팝업 구성
	private void hookRootCmpFnshTreeModelContextMenu() {
		try{
			MenuManager menuMgr = new MenuManager("#PopupMenu");
			menuMgr.setRemoveAllWhenShown(true);
			menuMgr.addMenuListener(
					new IMenuListener() {
						public void menuAboutToShow(IMenuManager manager) {
							ModelBrowser.this.fillRootCmpFnshTreeModelContextMenu(manager);
						}
					});
			Menu menu = menuMgr.createContextMenu(viewer.getControl());
			viewer.getControl().setMenu(menu);
			getSite().registerContextMenu(menuMgr, viewer);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}





	private void hookNodeRootTreeModelContextMenu() {
		try{
			MenuManager menuMgr = new MenuManager("#PopupMenu");
			menuMgr.setRemoveAllWhenShown(true);
			menuMgr.addMenuListener(
					new IMenuListener() {
						public void menuAboutToShow(IMenuManager manager) {
							ModelBrowser.this.fillNodeRootTreeModelContextMenu(manager);
						}
					});
			Menu menu = menuMgr.createContextMenu(viewer.getControl());
			viewer.getControl().setMenu(menu);
			getSite().registerContextMenu(menuMgr, viewer);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	private void fillNodeRootTreeModelContextMenu(IMenuManager manager) {
		//		manager.add(addPackage);
		manager.add(addNodePackage);


	}

	private void hookNodePackageTreeModelContextMenu() {
		try{
			MenuManager menuMgr = new MenuManager("#PopupMenu");
			menuMgr.setRemoveAllWhenShown(true);
			menuMgr.addMenuListener(
					new IMenuListener() {
						public void menuAboutToShow(IMenuManager manager) {
							ModelBrowser.this.fillNodePackageTreeModelContextMenu(manager);
						}
					});
			Menu menu = menuMgr.createContextMenu(viewer.getControl());
			viewer.getControl().setMenu(menu);
			getSite().registerContextMenu(menuMgr, viewer);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	private void fillNodePackageTreeModelContextMenu(IMenuManager manager) {
		//		manager.add(addPackage);
		//		manager.add(addNodePackage);
		//111027 SDM - 삭제메뉴 아래로 이동
		manager.add(addNodeItem);
		manager.add(deleteModel);


	}

	private void hookNodeItemTreeModelContextMenu() {
		try{
			MenuManager menuMgr = new MenuManager("#PopupMenu");
			menuMgr.setRemoveAllWhenShown(true);
			menuMgr.addMenuListener(
					new IMenuListener() {
						public void menuAboutToShow(IMenuManager manager) {
							ModelBrowser.this.fillNodeItemTreeModelContextMenu(manager);
						}
					});
			Menu menu = menuMgr.createContextMenu(viewer.getControl());
			viewer.getControl().setMenu(menu);
			getSite().registerContextMenu(menuMgr, viewer);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	private void fillNodeItemTreeModelContextMenu(IMenuManager manager) {
		manager.add(appListAction);
		manager.add(deleteModel);



	}

	private void hookRootPackageTreeModelContextMenu() {
		try{
			MenuManager menuMgr = new MenuManager("#PopupMenu");
			menuMgr.setRemoveAllWhenShown(true);
			menuMgr.addMenuListener(
					new IMenuListener() {
						public void menuAboutToShow(IMenuManager manager) {
							ModelBrowser.this.fillRootPackageTreeModelContextMenu(manager);
						}
					});
			Menu menu = menuMgr.createContextMenu(viewer.getControl());
			viewer.getControl().setMenu(menu);
			getSite().registerContextMenu(menuMgr, viewer);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	private void hookRootPackageTreeModelContextMenu2() {
		try{
			MenuManager menuMgr = new MenuManager("#PopupMenu");
			menuMgr.setRemoveAllWhenShown(true);
			menuMgr.addMenuListener(
					new IMenuListener() {
						public void menuAboutToShow(IMenuManager manager) {
							ModelBrowser.this.fillRootPackageTreeModelContextMenu2(manager);
						}
					});
			Menu menu = menuMgr.createContextMenu(viewer.getControl());
			viewer.getControl().setMenu(menu);
			getSite().registerContextMenu(menuMgr, viewer);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	private void hookPackageTreeModelContextMenu() {
		try{
			MenuManager menuMgr = new MenuManager("#PopupMenu");
			menuMgr.setRemoveAllWhenShown(true);
			menuMgr.addMenuListener(
					new IMenuListener() {
						public void menuAboutToShow(IMenuManager manager) {
							ModelBrowser.this.fillPackageTreeModelContextMenu(manager);
						}
					});
			Menu menu = menuMgr.createContextMenu(viewer.getControl());
			viewer.getControl().setMenu(menu);
			getSite().registerContextMenu(menuMgr, viewer);
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}

	private void hookRootServerRepositoryTreeModeContextMenu() {
		try{
			MenuManager menuMgr = new MenuManager("#PopupMenu");
			menuMgr.setRemoveAllWhenShown(true);
			menuMgr.addMenuListener(
					new IMenuListener() {
						public void menuAboutToShow(IMenuManager manager) {
							ModelBrowser.this.fillRootServerRepositoryTreeModeContextMenu(manager);
						}
					});
			Menu menu = menuMgr.createContextMenu(viewer.getControl());
			viewer.getControl().setMenu(menu);
			getSite().registerContextMenu(menuMgr, viewer);
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}

	private void hookServerRepositoryTreeModelContextMenu() {
		try{
			MenuManager menuMgr = new MenuManager("#PopupMenu");
			menuMgr.setRemoveAllWhenShown(true);
			menuMgr.addMenuListener(
					new IMenuListener() {
						public void menuAboutToShow(IMenuManager manager) {
							ModelBrowser.this.fillServerRepositoryTreeModelContextMenu(manager);
						}
					});
			Menu menu = menuMgr.createContextMenu(viewer.getControl());
			viewer.getControl().setMenu(menu);
			getSite().registerContextMenu(menuMgr, viewer);
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}


	private void hookMainTreeModelContextMenu() {
		try{
			MenuManager menuMgr = new MenuManager("#PopupMenu");
			menuMgr.setRemoveAllWhenShown(true);
			menuMgr.addMenuListener(
					new IMenuListener() {
						public void menuAboutToShow(IMenuManager manager) {
							ModelBrowser.this.fillMainTreeModelContextMenu(manager);
						}
					});
			Menu menu = menuMgr.createContextMenu(viewer.getControl());
			viewer.getControl().setMenu(menu);
			getSite().registerContextMenu(menuMgr, viewer);
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}

	private void hookTreeModelContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(
				new IMenuListener() {
					public void menuAboutToShow(IMenuManager manager) {
						ModelBrowser.this.fillUMLTreeModelContextMenu(manager);
					}
				});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
		//PKY 08082201 S 팀 프로젝트 읽기전용일때 수정불가능하도록 작업			
		//PKY 08090402 S 모델브라우져에서 더블클릭시 NullPoint발생
		IStructuredSelection sel = (IStructuredSelection)viewer.getSelection();
		Object selectModel = sel.getFirstElement();

		if(selectModel instanceof UMLTreeModel){
			UMLTreeModel modelUML = (UMLTreeModel)selectModel; 
			if(modelUML.getRefModel()!=null && modelUML.getRefModel() instanceof UMLModel ){
				UMLModel umlModel = (UMLModel) modelUML.getRefModel();
				if(umlModel.isReadOnlyModel()||!umlModel.isExistModel()){
					readOnlyMenuEnable();
				}
			}
		}
		//PKY 08090402 E 모델브라우져에서 더블클릭시 NullPoint발생

		//PKY 08082201 E 팀 프로젝트 읽기전용일때 수정불가능하도록 작업
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void hookTreeModelAddActor() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(
				new IMenuListener() {
					public void menuAboutToShow(IMenuManager manager) {
						ModelBrowser.this.fillUMLTreeModelAddActor(manager);
					}
				});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
		//PKY 08090402 S 모델브라우져에서 더블클릭시 NullPoint발생
		IStructuredSelection sel = (IStructuredSelection)viewer.getSelection();
		Object selectModel = sel.getFirstElement();

		if(selectModel instanceof UMLTreeModel){
			UMLTreeModel modelUML = (UMLTreeModel)selectModel; 
			if(modelUML.getRefModel()!=null && modelUML.getRefModel() instanceof UMLModel ){
				UMLModel umlModel = (UMLModel) modelUML.getRefModel();
				if(umlModel.isReadOnlyModel()||!umlModel.isExistModel()){
					readOnlyMenuEnable();
				}
			}
		}
		//PKY 08090402 E 모델브라우져에서 더블클릭시 NullPoint발생

	}
	//PKY 08060201 S 컴포넌트 하위에 클래스 다이어그램 생성되도록 추가
	//PKY 08071101 S 모델 브라우져에서  Usecase UseCase다이어그램 추가 
	private void hookTreeModelAddUseCase() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(
				new IMenuListener() {
					public void menuAboutToShow(IMenuManager manager) {
						ModelBrowser.this.fillUMLTreeModelAddUseCase(manager);
					}
				});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
		//PKY 08090402 S 모델브라우져에서 더블클릭시 NullPoint발생
		IStructuredSelection sel = (IStructuredSelection)viewer.getSelection();
		Object selectModel = sel.getFirstElement();

		if(selectModel instanceof UMLTreeModel){
			UMLTreeModel modelUML = (UMLTreeModel)selectModel; 
			if(modelUML.getRefModel()!=null && modelUML.getRefModel() instanceof UMLModel ){
				UMLModel umlModel = (UMLModel) modelUML.getRefModel();
				if(umlModel.isReadOnlyModel()||!umlModel.isExistModel()){
					readOnlyMenuEnable();
				}
			}
		}
		//PKY 08090402 E 모델브라우져에서 더블클릭시 NullPoint발생

	}
	//PKY 08071101 E 모델 브라우져에서  Usecase UseCase다이어그램 추가 
	//20080721IJS
	private void hookTreeModelAddRequireModel() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(
				new IMenuListener() {
					public void menuAboutToShow(IMenuManager manager) {
						ModelBrowser.this.fillUMLTreeModelAddRequireModel(manager);
					}
				});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
		//PKY 08090402 S 모델브라우져에서 더블클릭시 NullPoint발생
		IStructuredSelection sel = (IStructuredSelection)viewer.getSelection();
		Object selectModel = sel.getFirstElement();

		if(selectModel instanceof UMLTreeModel){
			UMLTreeModel modelUML = (UMLTreeModel)selectModel; 
			if(modelUML.getRefModel()!=null && modelUML.getRefModel() instanceof UMLModel ){
				UMLModel umlModel = (UMLModel) modelUML.getRefModel();
				if(umlModel.isReadOnlyModel()||!umlModel.isExistModel()){
					readOnlyMenuEnable();
				}
			}
		}
		//PKY 08090402 E 모델브라우져에서 더블클릭시 NullPoint발생

	}

	private void hookTreeModelAddComponentLib() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(
				new IMenuListener() {
					public void menuAboutToShow(IMenuManager manager) {
						ModelBrowser.this.fillUMLTreeModelAddComponentLib(manager);
					}
				});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
		//PKY 08090402 S 모델브라우져에서 더블클릭시 NullPoint발생
		IStructuredSelection sel = (IStructuredSelection)viewer.getSelection();
		Object selectModel = sel.getFirstElement();

		if(selectModel instanceof UMLTreeModel){
			UMLTreeModel modelUML = (UMLTreeModel)selectModel; 
			if(modelUML.getRefModel()!=null && modelUML.getRefModel() instanceof UMLModel ){
				UMLModel umlModel = (UMLModel) modelUML.getRefModel();
				if(umlModel.isReadOnlyModel()||!umlModel.isExistModel()){
					readOnlyMenuEnable();
				}
			}
		}
		//PKY 08090402 E 모델브라우져에서 더블클릭시 NullPoint발생

	}

	private void hookTreeModelPortModel() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(
				new IMenuListener() {
					public void menuAboutToShow(IMenuManager manager) {
						ModelBrowser.this.fillUMLTreeModelPortModel(manager);
					}
				});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
		//PKY 08090402 S 모델브라우져에서 더블클릭시 NullPoint발생
		IStructuredSelection sel = (IStructuredSelection)viewer.getSelection();
		Object selectModel = sel.getFirstElement();

		if(selectModel instanceof UMLTreeModel){
			UMLTreeModel modelUML = (UMLTreeModel)selectModel; 
			if(modelUML.getRefModel()!=null && modelUML.getRefModel() instanceof UMLModel ){
				UMLModel umlModel = (UMLModel) modelUML.getRefModel();
				if(umlModel.isReadOnlyModel()||!umlModel.isExistModel()){
					readOnlyMenuEnable();
				}
			}
		}
		//PKY 08090402 E 모델브라우져에서 더블클릭시 NullPoint발생

	}


	private void hookTreeModelAtomicComponent() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(
				new IMenuListener() {
					public void menuAboutToShow(IMenuManager manager) {
						ModelBrowser.this.fillUMLTreeModelAtomicComponent(manager);
					}
				});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
		//PKY 08090402 S 모델브라우져에서 더블클릭시 NullPoint발생
		IStructuredSelection sel = (IStructuredSelection)viewer.getSelection();
		Object selectModel = sel.getFirstElement();

		if(selectModel instanceof UMLTreeModel){
			UMLTreeModel modelUML = (UMLTreeModel)selectModel; 
			if(modelUML.getRefModel()!=null && modelUML.getRefModel() instanceof UMLModel ){
				UMLModel umlModel = (UMLModel) modelUML.getRefModel();
				if(umlModel.isReadOnlyModel()||!umlModel.isExistModel()){
					readOnlyMenuEnable();
				}
			}
		}
		//PKY 08090402 E 모델브라우져에서 더블클릭시 NullPoint발생

	}

	private void hookTreeModelAddComponent() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(
				new IMenuListener() {
					public void menuAboutToShow(IMenuManager manager) {
						ModelBrowser.this.fillUMLTreeModelAddComponent(manager);
					}
				});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
		//PKY 08090402 S 모델브라우져에서 더블클릭시 NullPoint발생
		IStructuredSelection sel = (IStructuredSelection)viewer.getSelection();
		Object selectModel = sel.getFirstElement();

		if(selectModel instanceof UMLTreeModel){
			UMLTreeModel modelUML = (UMLTreeModel)selectModel; 
			if(modelUML.getRefModel()!=null && modelUML.getRefModel() instanceof UMLModel ){
				UMLModel umlModel = (UMLModel) modelUML.getRefModel();
				if(umlModel.isReadOnlyModel()||!umlModel.isExistModel()){
					readOnlyMenuEnable();
				}
			}
		}
		//PKY 08090402 E 모델브라우져에서 더블클릭시 NullPoint발생

	}
	//PKY 08060201 E 컴포넌트 하위에 클래스 다이어그램 생성되도록 추가

	private void hookTreeModelAddState() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(
				new IMenuListener() {
					public void menuAboutToShow(IMenuManager manager) {
						ModelBrowser.this.fillUMLTreeModelAddState(manager);
					}
				});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
		//PKY 08090402 S 모델브라우져에서 더블클릭시 NullPoint발생
		IStructuredSelection sel = (IStructuredSelection)viewer.getSelection();
		Object selectModel = sel.getFirstElement();

		if(selectModel instanceof UMLTreeModel){
			UMLTreeModel modelUML = (UMLTreeModel)selectModel; 
			if(modelUML.getRefModel()!=null && modelUML.getRefModel() instanceof UMLModel ){
				UMLModel umlModel = (UMLModel) modelUML.getRefModel();
				if(umlModel.isReadOnlyModel()||!umlModel.isExistModel()){
					readOnlyMenuEnable();
				}
			}
		}
		//PKY 08090402 E 모델브라우져에서 더블클릭시 NullPoint발생

	}

	private void fillLocalPullDown(IMenuManager manager) {
		//		manager.add(action1);
		//		manager.add(new Separator());
		//		manager.add(action2);
	}

	private void fillInstanceContextMenu(IMenuManager manager) {
		manager.add(deleteModel);
		//20080328 PKY S 프로그램을 구동한 뒤 프로젝트 브라우져에서 오른쪽 패키지 추가 메뉴를 누룰경우 반응을 하지 않는문제 오른쪽 메뉴 삭제
		//		MenuManager AddPackageMenu = new MenuManager("&AddPackage", "패키지추가");
		//		AddPackageMenu.add(addDiagram);
		//		manager.add(AddPackageMenu);
		//		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		//20080328 PKY E 프로그램을 구동한 뒤 프로젝트 브라우져에서 오른쪽 패키지 추가 메뉴를 누룰경우 반응을 하지 않는문제 오른쪽 메뉴 삭제
	}
	
	
	private void fillTemplateComponentModelContextMenu(IMenuManager manager) {
		manager.add(addTemplate);
		manager.add(addMSVCTemplate);
		manager.add(openTemplateSrcCPPComponent);
		manager.add(openTemplateSrcHComponent);
		//20080328 PKY S 프로그램을 구동한 뒤 프로젝트 브라우져에서 오른쪽 패키지 추가 메뉴를 누룰경우 반응을 하지 않는문제 오른쪽 메뉴 삭제
		//		MenuManager AddPackageMenu = new MenuManager("&AddPackage", "패키지추가");
		//		AddPackageMenu.add(addDiagram);
		//		manager.add(AddPackageMenu);
		//		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		//20080328 PKY E 프로그램을 구동한 뒤 프로젝트 브라우져에서 오른쪽 패키지 추가 메뉴를 누룰경우 반응을 하지 않는문제 오른쪽 메뉴 삭제
	}
	
	private void fillRootTemplatePackageTreeModelContextMenu(IMenuManager manager) {
//		manager.add(loadTemplate);
//		manager.add(newApplication);
		//20080328 PKY S 프로그램을 구동한 뒤 프로젝트 브라우져에서 오른쪽 패키지 추가 메뉴를 누룰경우 반응을 하지 않는문제 오른쪽 메뉴 삭제
		//		MenuManager AddPackageMenu = new MenuManager("&AddPackage", "패키지추가");
		//		AddPackageMenu.add(addDiagram);
		//		manager.add(AddPackageMenu);
		//		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		//20080328 PKY E 프로그램을 구동한 뒤 프로젝트 브라우져에서 오른쪽 패키지 추가 메뉴를 누룰경우 반응을 하지 않는문제 오른쪽 메뉴 삭제
	}
	
	private void fillRootCmpComposingTreeModelContextMenu(IMenuManager manager) {
//		manager.add(loadTemplate);
		manager.add(newApplication);
		//20080328 PKY S 프로그램을 구동한 뒤 프로젝트 브라우져에서 오른쪽 패키지 추가 메뉴를 누룰경우 반응을 하지 않는문제 오른쪽 메뉴 삭제
		//		MenuManager AddPackageMenu = new MenuManager("&AddPackage", "패키지추가");
		//		AddPackageMenu.add(addDiagram);
		//		manager.add(AddPackageMenu);
		//		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		//20080328 PKY E 프로그램을 구동한 뒤 프로젝트 브라우져에서 오른쪽 패키지 추가 메뉴를 누룰경우 반응을 하지 않는문제 오른쪽 메뉴 삭제
	}
	
	private void fillLoadTemplateContextMenu(IMenuManager manager) {
		manager.add(loadTemplate);
		//20080328 PKY S 프로그램을 구동한 뒤 프로젝트 브라우져에서 오른쪽 패키지 추가 메뉴를 누룰경우 반응을 하지 않는문제 오른쪽 메뉴 삭제
		//		MenuManager AddPackageMenu = new MenuManager("&AddPackage", "패키지추가");
		//		AddPackageMenu.add(addDiagram);
		//		manager.add(AddPackageMenu);
		//		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		//20080328 PKY E 프로그램을 구동한 뒤 프로젝트 브라우져에서 오른쪽 패키지 추가 메뉴를 누룰경우 반응을 하지 않는문제 오른쪽 메뉴 삭제
	}
	
	
	private void fillAddAppTemplateModelContextMenu(IMenuManager manager) {
		manager.add(addAppTemplate);
		manager.add(addMSVCAppTemplate);
		//20080328 PKY S 프로그램을 구동한 뒤 프로젝트 브라우져에서 오른쪽 패키지 추가 메뉴를 누룰경우 반응을 하지 않는문제 오른쪽 메뉴 삭제
		//		MenuManager AddPackageMenu = new MenuManager("&AddPackage", "패키지추가");
		//		AddPackageMenu.add(addDiagram);
		//		manager.add(AddPackageMenu);
		//		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		//20080328 PKY E 프로그램을 구동한 뒤 프로젝트 브라우져에서 오른쪽 패키지 추가 메뉴를 누룰경우 반응을 하지 않는문제 오른쪽 메뉴 삭제
	}

	private void fillContextMenu(IMenuManager manager) {
		//20080328 PKY S 프로그램을 구동한 뒤 프로젝트 브라우져에서 오른쪽 패키지 추가 메뉴를 누룰경우 반응을 하지 않는문제 오른쪽 메뉴 삭제
		//		MenuManager AddPackageMenu = new MenuManager("&AddPackage", "패키지추가");
		//		AddPackageMenu.add(addDiagram);
		//		manager.add(AddPackageMenu);
		//		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		//20080328 PKY E 프로그램을 구동한 뒤 프로젝트 브라우져에서 오른쪽 패키지 추가 메뉴를 누룰경우 반응을 하지 않는문제 오른쪽 메뉴 삭제
	}

	private void fillRootTreeModelContextMenu(IMenuManager manager) {
		manager.add(mainNew);//khg 10.4.26 새로운 다이어그램
		manager.add(mainSave);
		manager.add(mainOpen);
//		manager.add(newApplication);
//		manager.add(importApplication);//khg 2010.05.26임포트 어플리케이션
		//		manager.add(refresh);
		//		manager.add(openN3PropertyDialogAction);

	}



	//모니터링
	private void fillMoniitorRootTreeModelContextMenu(IMenuManager manager) {
		manager.add(connectMonitor);
		//		manager.add(mainOpen);
		//		manager.add(refresh);
		//		manager.add(openN3PropertyDialogAction);

	}
	private void fillMoniitorTreeModelContextMenu(IMenuManager manager) {
		manager.add(appListAction);
		manager.add(deleteModel);
		//		manager.add(mainOpen);
		//		manager.add(refresh);
		//		manager.add(openN3PropertyDialogAction);

	}
	private void fillAppTreeModelContextMenu(IMenuManager manager) {
		boolean isRun = false;
		if(this.appTreeModel!=null && this.appTreeModel instanceof AppTreeModel){
			AppTreeModel ap = (AppTreeModel)appTreeModel;
			isRun = ap.isRun;
		}
		if(isRun){
			//			manager.add(appRunAction);
			manager.add(appStopAction);
			manager.add(appStateAction);
			manager.add(appCompListAction);
			manager.add(deleteModel);
		}
		else{
			manager.add(appRunAction);
			//			manager.add(appStateAction);
			//			manager.add(appCompListAction);
			manager.add(deleteModel);
		}

		//		manager.add(deleteAction);
		//		manager.add(mainOpen);
		//		manager.add(refresh);
		//		manager.add(openN3PropertyDialogAction);

	}

	private void fillMonitorCompTreeModelContextMenu(IMenuManager manager) {
		boolean isA = false;
		if(this.appTreeModel!=null && this.appTreeModel instanceof MonitorCompTreeModel){
			MonitorCompTreeModel ap = (MonitorCompTreeModel)appTreeModel;
			isA = ap.isAtomic;
		}
		if(isA){
			//			manager.add(compListAction);
			manager.add(compStateAction);
			manager.add(compPropAction);
//			manager.add(deleteModel);	//111223 SDM  - 해당 메뉴 삭제
		}
		else{
			manager.add(compListAction);
			manager.add(compStateAction);
			manager.add(compPropAction);
			manager.add(deleteModel);
		}

		//		manager.add(deleteAction);
		//		manager.add(mainOpen);
		//		manager.add(refresh);
		//		manager.add(openN3PropertyDialogAction);

	}

	private void fillRepoComponentTreeModelContextMenu(IMenuManager manager) {

		manager.add(repoDeleteModel);

	}

	private void fillRootCmpEdtTreeModelContextMenu(IMenuManager manager) {

//		manager.add(atCompRefreshAction);	// 110902 SDM 동기화 메뉴 뺌
		//ijs 0811
//		manager.add(newCompCreateAction);
//		manager.add(importModelComponent);

	}
	
	//20110729서동민  Finish쪽 트리 팝업메뉴 구성
	private void fillRootCmpFnshTreeModelContextMenu(IMenuManager manager) {
		//20110811서동민 팝업메뉴 삭제
		//manager.add(allLoad);
	}

	private void fillRootPackageTreeModelContextMenu(IMenuManager manager) {
		//		manager.add(addPackage);
		manager.add(importComponent);
		//		manager.add(openN3PropertyDialogAction);
		manager.add(refresh);
//		manager.add(openN3PropertyDialogAction);	//111102 SDM - Property 메뉴 제거

	}

	private void fillRootPackageTreeModelContextMenu2(IMenuManager manager) {
		//		manager.add(addPackage);
		//		manager.add(importComponent);
		//		manager.add(openN3PropertyDialogAction);
		manager.add(refresh);
		//		manager.add(openN3PropertyDialogAction);

	}

	private void fillRootServerRepositoryTreeModeContextMenu(IMenuManager manager) {
		manager.add(connectRepository);
		//		manager.add(deleteModel);
		//		

	}

	private void fillServerRepositoryTreeModelContextMenu(IMenuManager manager) {
		manager.add(connect);
		manager.add(deleteModel);
		//		

	}

	private void fillMainTreeModelContextMenu(IMenuManager manager) {
		manager.add(addPackage);
		manager.add(addModel);
		manager.add(addLink);
		IStructuredSelection sel = (IStructuredSelection)viewer.getSelection();
		Object selectModel = sel.getFirstElement();
		if(selectModel instanceof PackageTreeModel){
			PackageTreeModel packageUML = (PackageTreeModel)selectModel; 
			if(packageUML.getRefModel()!=null){
				FinalPackageModel finalPackageModel = (FinalPackageModel) packageUML.getRefModel();
				if(finalPackageModel.isReadOnlyModel()||!finalPackageModel.isExistModel()){
					readOnlyMenuEnable();
					this.loadLink.setEnabled(true);

				}
			}
		}

		//PKY 08090101 E 모델 브라우져 패키지 Enable에러 문제
		deleteModel.setEnabled(true);





		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	public java.util.ArrayList deployActions = new java.util.ArrayList();

	public void setDeployAction(){

	}

	java.util.ArrayList deploylist = new java.util.ArrayList();

	public void setDeployList(UMLTreeParentModel upm){
		java.util.ArrayList pkg = new java.util.ArrayList();
		for(int i=0;i<upm.getChildren().length;i++){
			UMLTreeModel utm = (UMLTreeModel)upm.getChildren()[i];
			if(utm instanceof NodeItemTreeModel){
				deploylist.add(utm);
			}
			else if(utm instanceof NodePackageTreeModel){
				pkg.add(utm);
			}
		}

		for(int i=0;i<pkg.size();i++){
			UMLTreeParentModel upm1 = (UMLTreeParentModel)pkg.get(i);
			this.setDeployList(upm1);
		}



	}

	private void fillPackageTreeModelContextMenu(IMenuManager manager) {

		IStructuredSelection sel = (IStructuredSelection)viewer.getSelection();
		Object selectModel = sel.getFirstElement();
		if(selectModel instanceof PackageTreeModel){
			PackageTreeModel pt = (PackageTreeModel)selectModel;
			if(pt.getParent() instanceof RootCmpEdtTreeModel){
				return;
			}
		}
		manager.add(exportApplication);
		manager.add(exportApplication_tar);
		

		MenuManager elementSubmenu = new MenuManager("Deploy", null);
		MenuManager elementSubmenu2 = new MenuManager("Deploy(tar)", null);
		elementSubmenu.removeAll();
		elementSubmenu2.removeAll();
		N3ViewContentProvider n3ViewContentProvider = (N3ViewContentProvider)this.viewer.getContentProvider();

		NodeRootTreeModel nrt = n3ViewContentProvider.getNodeRootTreeModel();
		if(nrt==null){
			nrt = ProjectManager.getInstance().getNodeRootTreeModel();
		}
		deploylist.clear();

		this.setDeployList(nrt);




		//PKY 08090101 E 모델 브라우져 패키지 Enable에러 문제
		//PKY 08090101 S 모델 브라우져 패키지 Enable에러 문제
		 sel = (IStructuredSelection)viewer.getSelection();
		 selectModel = sel.getFirstElement();
		FinalPackageModel finalPackageModel = null;
		if(selectModel instanceof PackageTreeModel){
			PackageTreeModel packageUML = (PackageTreeModel)selectModel; 
			if(packageUML.getRefModel()!=null){
				finalPackageModel = (FinalPackageModel) packageUML.getRefModel();
				if(finalPackageModel.isReadOnlyModel()||!finalPackageModel.isExistModel()){
					readOnlyMenuEnable();
					this.loadLink.setEnabled(true);

				}
			}
		}

		//PKY 08090101 E 모델 브라우져 패키지 Enable에러 문제
		deleteModel.setEnabled(true);
		if(finalPackageModel!=null){
			DeployAction allAction = new DeployAction("All");
			allAction.setAppDiagram(finalPackageModel.getN3EditorDiagramModel());
			elementSubmenu.add(allAction);
			for(int i=0;i<this.deploylist.size();i++){
				NodeItemTreeModel nit = (NodeItemTreeModel)this.deploylist.get(i);
				NodeItemModel nim =  (NodeItemModel)nit.getRefModel();
				String pkg1 = nim.getPackage();
				int index = pkg1.indexOf(".");
				String pkg = "";
				if(pkg1!=null && !"".equals(pkg1) && index!=-1){
					pkg = pkg1.substring(index+1);
				}
				else{
					pkg = nim.getPackage().substring(7);	
				}
				//				
				DeployAction da2 = new DeployAction(pkg+"."+nit.getName());
				da2.setNodeItemModel(nim);
				elementSubmenu.add(da2);
				da2.setAppDiagram(finalPackageModel.getN3EditorDiagramModel());
			}
			
			 allAction = new DeployAction("All");
			 allAction.isTar = true;
			allAction.setAppDiagram(finalPackageModel.getN3EditorDiagramModel());
			elementSubmenu2.add(allAction);
			for(int i=0;i<this.deploylist.size();i++){
				NodeItemTreeModel nit = (NodeItemTreeModel)this.deploylist.get(i);
				NodeItemModel nim =  (NodeItemModel)nit.getRefModel();
				String pkg1 = nim.getPackage();
				int index = pkg1.indexOf(".");
				String pkg = "";
				if(pkg1!=null && !"".equals(pkg1) && index!=-1){
					pkg = pkg1.substring(index+1);
				}
				else{
					pkg = nim.getPackage().substring(7);	
				}
				//				
				DeployAction da2 = new DeployAction(pkg+"."+nit.getName());
				da2.setNodeItemModel(nim);
				elementSubmenu2.add(da2);
				da2.isTar = true;
				da2.setAppDiagram(finalPackageModel.getN3EditorDiagramModel());
			}
		}




		manager.add(elementSubmenu);
		manager.add(elementSubmenu2);



		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		
		manager.add(deleteModel);	//111027 SDM - 삭제메뉴 아래로 이동
	}


	private void fillUMLTreeModelAddComponentLib(IMenuManager manager) {
		MenuManager elementSubmenu = new MenuManager(N3Messages.POPUP_ADD); 
		MenuManager javaElementSubmenu = new MenuManager(N3Messages.POPUP_GENERATE_CODE); 
		elementSubmenu.removeAll();
		manager.add(saveDiagram);

		IStructuredSelection sel = (IStructuredSelection)viewer.getSelection();
		Object selectModel = sel.getFirstElement();

		if(selectModel instanceof UMLTreeModel){
			UMLTreeModel modelUML = (UMLTreeModel)selectModel; 
			if(modelUML.getRefModel()!=null && modelUML.getRefModel() instanceof UMLModel ){
				UMLModel umlModel = (UMLModel) modelUML.getRefModel();
				if(umlModel.isReadOnlyModel()||!umlModel.isExistModel()){
					readOnlyMenuEnable();
				}
			}
		}
		//PKY 08090402 E 모델브라우져에서 더블클릭시 NullPoint발생

		manager.add(elementSubmenu);
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));

	}

	private void fillUMLTreeModelPortModel(IMenuManager manager) {
		MenuManager elementSubmenu = new MenuManager(N3Messages.POPUP_ADD); 
		MenuManager javaElementSubmenu = new MenuManager(N3Messages.POPUP_GENERATE_CODE); 
		elementSubmenu.removeAll();

		IStructuredSelection sel = (IStructuredSelection)viewer.getSelection();
		Object selectModel = sel.getFirstElement();



		manager.add(elementSubmenu);
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		manager.add(deleteModel);	//111027 SDM - 삭제메뉴 아래로 이동

	}

	private void fillUMLTreeModelAtomicComponent(IMenuManager manager) {
		MenuManager elementSubmenu = new MenuManager(N3Messages.POPUP_ADD); 
		//		MenuManager javaElementSubmenu = new MenuManager(N3Messages.POPUP_GENERATE_CODE); 
		elementSubmenu.removeAll();
		//		manager.add(reGenAction);
//		MenuManager elementGenSubmenu = new MenuManager("Generate Source");
//		elementGenSubmenu.add(reGenAction);
//		elementGenSubmenu.add(reGenMSVCAction);
//		manager.add(reGenAction);
//		manager.add(teGenAction);
//		manager.add(deleteModel);
		//20110720 동기화 메뉴추가
//		manager.add(syncModel);	//110902 SDM  동기화 메뉴 삭제
		//20110805서동민 컴포넌트에디터 호출
		manager.add(openCmpEdt);
		manager.add(deleteModel);	//111027 SDM - 삭제메뉴 아래로 이동
		// IJS 2010-08-12 4:17오후  라이브러리 이동 관련
//		manager.add(exportAtomicComponent);
		
		if(this.isMerge){
			MenuManager elementGenSubmenu1 = new MenuManager("run Merge");
			elementGenSubmenu1.add(openMergeH);
			elementGenSubmenu1.add(openMergecpp);
			manager.add(elementGenSubmenu1);
//			manager.add(openMergecpp);
		}
		//		manager.add(exportComponent);
		//		manager.add(saveLink);

		//PKY 08070301 E 툴바 추가작업
		//		elementSubmenu.add(addActivityDiagram);
		//		elementSubmenu.add(addInteractionDiagram);
		//		elementSubmenu.add(addCommunicationDiagram);
		//		elementSubmenu.add(addStateDiagram);
		//		elementSubmenu.add(addClassDiagram);
		//PKY 08090402 S 모델브라우져에서 더블클릭시 NullPoint발생
		//		IStructuredSelection sel = (IStructuredSelection)viewer.getSelection();
		//		Object selectModel = sel.getFirstElement();

		//		if(selectModel instanceof UMLTreeModel){
		//			UMLTreeModel modelUML = (UMLTreeModel)selectModel; 
		//			if(modelUML.getRefModel()!=null && modelUML.getRefModel() instanceof UMLModel ){
		//				UMLModel umlModel = (UMLModel) modelUML.getRefModel();
		//				if(umlModel.isReadOnlyModel()||!umlModel.isExistModel()){
		//					readOnlyMenuEnable();
		//				}
		//			}
		//		}
		//		//PKY 08090402 E 모델브라우져에서 더블클릭시 NullPoint발생
		//
		//		manager.add(elementSubmenu);
		//		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));

	}

	//2008040203 PKY S  추가 
	//PKY 08060201 S 컴포넌트 하위에 클래스 다이어그램 생성되도록 추가
	private void fillUMLTreeModelAddComponent(IMenuManager manager) {
		MenuManager elementSubmenu = new MenuManager(N3Messages.POPUP_ADD); 
		MenuManager javaElementSubmenu = new MenuManager(N3Messages.POPUP_GENERATE_CODE); 
		elementSubmenu.removeAll();
//		manager.add(deleteModel);
		manager.add(exportComponent);
		manager.add(exportComponent_tar);
		manager.add(deleteModel);	//111027 SDM - 삭제메뉴 아래로 이동
		//		manager.add(saveLink);

		//PKY 08070301 E 툴바 추가작업
		//		elementSubmenu.add(addActivityDiagram);
		//		elementSubmenu.add(addInteractionDiagram);
		//		elementSubmenu.add(addCommunicationDiagram);
		//		elementSubmenu.add(addStateDiagram);
		//		elementSubmenu.add(addClassDiagram);
		//PKY 08090402 S 모델브라우져에서 더블클릭시 NullPoint발생
		IStructuredSelection sel = (IStructuredSelection)viewer.getSelection();
		Object selectModel = sel.getFirstElement();

		if(selectModel instanceof UMLTreeModel){
			UMLTreeModel modelUML = (UMLTreeModel)selectModel; 
			if(modelUML.getRefModel()!=null && modelUML.getRefModel() instanceof UMLModel ){
				UMLModel umlModel = (UMLModel) modelUML.getRefModel();
				if(umlModel.isReadOnlyModel()||!umlModel.isExistModel()){
					readOnlyMenuEnable();
				}
			}
		}
		//PKY 08090402 E 모델브라우져에서 더블클릭시 NullPoint발생

		manager.add(elementSubmenu);
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));

	}
	//PKY 08060201 E 컴포넌트 하위에 클래스 다이어그램 생성되도록 추가

	private void fillUMLTreeModelAddActor(IMenuManager manager) {
		MenuManager elementSubmenu = new MenuManager(N3Messages.POPUP_ADD); 
		MenuManager javaElementSubmenu = new MenuManager(N3Messages.POPUP_GENERATE_CODE); //PKY 08070301 S 툴바 추가작업
		elementSubmenu.removeAll();
//		manager.add(deleteModel);
		manager.add(findModelBrowser);
		//PKY 08070301 S 툴바 추가작업
		manager.add(javaElementSubmenu);	
		javaElementSubmenu.add(writeJava);
		javaElementSubmenu.add(writeH);
		manager.add(javaElementSubmenu);
		manager.add(deleteModel);	//111027 SDM - 삭제메뉴 아래로 이동
		//PKY 08070301 E 툴바 추가작업
		elementSubmenu.add(addActivityDiagram);
		elementSubmenu.add(addInteractionDiagram);
		elementSubmenu.add(addCommunicationDiagram);
		elementSubmenu.add(addStateDiagram);
		//PKY 08090402 S 모델브라우져에서 더블클릭시 NullPoint발생
		IStructuredSelection sel = (IStructuredSelection)viewer.getSelection();
		Object selectModel = sel.getFirstElement();

		if(selectModel instanceof UMLTreeModel){
			UMLTreeModel modelUML = (UMLTreeModel)selectModel; 
			if(modelUML.getRefModel()!=null && modelUML.getRefModel() instanceof UMLModel ){
				UMLModel umlModel = (UMLModel) modelUML.getRefModel();
				if(umlModel.isReadOnlyModel()||!umlModel.isExistModel()){
					readOnlyMenuEnable();
				}
			}
		}
		//PKY 08090402 E 모델브라우져에서 더블클릭시 NullPoint발생

		//		javaElementSubmenu.add(writeJava);

		manager.add(elementSubmenu);
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));

	}
	//20080721IJS
	private void fillUMLTreeModelAddRequireModel(IMenuManager manager) {
		MenuManager elementSubmenu = new MenuManager(N3Messages.POPUP_ADD); 
		MenuManager javaElementSubmenu = new MenuManager(N3Messages.POPUP_GENERATE_CODE); //PKY 08070301 S 툴바 추가작업
		elementSubmenu.removeAll();
//		manager.add(deleteModel);
		manager.add(findModelBrowser);
		manager.add(findReqModelBrowser);
		manager.add(excelReqModelSave);		// V1.02 WJH 080908 요구사항 추적표 추가
		manager.add(deleteModel);	//111027 SDM - 삭제메뉴 아래로 이동

		//PKY 08082201 E 팀 프로젝트 읽기전용일때 수정불가능하도록 작업
		//PKY 08090402 S 모델브라우져에서 더블클릭시 NullPoint발생
		IStructuredSelection sel = (IStructuredSelection)viewer.getSelection();
		Object selectModel = sel.getFirstElement();

		if(selectModel instanceof UMLTreeModel){
			UMLTreeModel modelUML = (UMLTreeModel)selectModel; 
			if(modelUML.getRefModel()!=null && modelUML.getRefModel() instanceof UMLModel ){
				UMLModel umlModel = (UMLModel) modelUML.getRefModel();
				if(umlModel.isReadOnlyModel()||!umlModel.isExistModel()){
					readOnlyMenuEnable();
					deleteModel.setEnabled(false);
				}
			}
		}
		//PKY 08090402 E 모델브라우져에서 더블클릭시 NullPoint발생
	}
	//PKY 08071101 S 모델 브라우져에서  Usecase UseCase다이어그램 추가 
	private void fillUMLTreeModelAddUseCase(IMenuManager manager) {
		MenuManager elementSubmenu = new MenuManager(N3Messages.POPUP_ADD); 
		MenuManager javaElementSubmenu = new MenuManager(N3Messages.POPUP_GENERATE_CODE); //PKY 08070301 S 툴바 추가작업
		elementSubmenu.removeAll();
		manager.add(deleteModel);
		manager.add(findModelBrowser);
		//PKY 08070301 S 툴바 추가작업
		manager.add(javaElementSubmenu);	
		javaElementSubmenu.add(writeJava);
		javaElementSubmenu.add(writeH);
		manager.add(javaElementSubmenu);
		//PKY 08070301 E 툴바 추가작업
		elementSubmenu.add(addUseCaseDiagram);
		elementSubmenu.add(addActivityDiagram);
		elementSubmenu.add(addInteractionDiagram);
		elementSubmenu.add(addCommunicationDiagram);
		elementSubmenu.add(addStateDiagram);

		//		javaElementSubmenu.add(writeJava);

		manager.add(elementSubmenu);
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		//PKY 08090402 S 모델브라우져에서 더블클릭시 NullPoint발생
		IStructuredSelection sel = (IStructuredSelection)viewer.getSelection();
		Object selectModel = sel.getFirstElement();

		if(selectModel instanceof UMLTreeModel){
			UMLTreeModel modelUML = (UMLTreeModel)selectModel; 
			if(modelUML.getRefModel()!=null && modelUML.getRefModel() instanceof UMLModel ){
				UMLModel umlModel = (UMLModel) modelUML.getRefModel();
				if(umlModel.isReadOnlyModel()||!umlModel.isExistModel()){
					readOnlyMenuEnable();
				}
			}
		}
		//PKY 08090402 E 모델브라우져에서 더블클릭시 NullPoint발생

	}
	//PKY 08071101 E 모델 브라우져에서  Usecase UseCase다이어그램 추가 

	private void fillUMLTreeModelAddState(IMenuManager manager) {
		MenuManager elementSubmenu = new MenuManager(N3Messages.POPUP_ADD); 

		elementSubmenu.removeAll();
		manager.add(deleteModel);
		manager.add(findModelBrowser);
		elementSubmenu.add(addActivityDiagram);
		elementSubmenu.add(addStateDiagram);
		manager.add(elementSubmenu);
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		//PKY 08090402 S 모델브라우져에서 더블클릭시 NullPoint발생
		IStructuredSelection sel = (IStructuredSelection)viewer.getSelection();
		Object selectModel = sel.getFirstElement();

		if(selectModel instanceof UMLTreeModel){
			UMLTreeModel modelUML = (UMLTreeModel)selectModel; 
			if(modelUML.getRefModel()!=null && modelUML.getRefModel() instanceof UMLModel ){
				UMLModel umlModel = (UMLModel) modelUML.getRefModel();
				if(umlModel.isReadOnlyModel()||!umlModel.isExistModel()){
					readOnlyMenuEnable();
				}
			}
		}
		//PKY 08090402 E 모델브라우져에서 더블클릭시 NullPoint발생

	}
	//2008040203 PKY E 추가 

	//참조관련
	private void fillUMLTreeModelContextMenu(IMenuManager manager) {
		try{
			List list = ProjectManager.getInstance().getSelectNodes();
			//PKY 08070901 S 시퀀스 라이프라인 브라우져에 참조추가 들어가있는문제 수정
			//			if (ProjectManager.getInstance().getSelectNodes() != null &&
			//			ProjectManager.getInstance().getSelectNodes().size() == 1) {
			//			Object obj = ProjectManager.getInstance().getSelectNodes().get(0);
			//			UMLEditPart classEditPart = (UMLEditPart)obj;
			//			this.get
			//			UMLModel umModel = (UMLModel)classEditPart.getModel();
			//			//20080328 PKY S 프로젝트 브라우져 참조 메뉴 삭제 
			////			if (umModel instanceof ClassifierModel) {
			////			ClassifierModel classifierModel = (ClassifierModel)umModel;
			////			TypeRefModel typeRefModel = (TypeRefModel)classifierModel.getClassModel();
			////			addRefModel.setText(typeRefModel.getName() + ":참조설정");
			////			manager.add(addRefModel);
			////			}
			//			//20080328 PKY E 프로젝트 브라우져 참조 메뉴 삭제
			//			//PKY 08070901 S 시퀀스 라이프라인 브라우져에 참조추가 들어가있는문제 수정
			//			if (umModel instanceof LifeLineModel) {
			////			LifeLineModel classifierModel = (LifeLineModel)umModel;
			////			addRefModel.setText(classifierModel.getName() + ":참조설정");
			////			manager.add(addRefModel);
			//			}
			//			//PKY 08070901 E 시퀀스 라이프라인 브라우져에 참조추가 들어가있는문제 수정
			//			}


			//PKY 08070901 E 시퀀스 라이프라인 브라우져에 참조추가 들어가있는문제 수정
			//			manager.add(deleteModel);
			//			manager.add(findModelBrowser);
			manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));

			//PKY 08082201 S ClassModel가져오는 오퍼레이션 추가
			if(ProjectManager.getInstance()!=null && ProjectManager.getInstance().getSelectPropertyUMLElementModel()!=null){
				UMLElementModel umlElementModel= ProjectManager.getInstance().getSelectPropertyUMLElementModel();
				if(umlElementModel!=null && umlElementModel instanceof UMLModel){
					if(((UMLModel)umlElementModel).isReadOnlyModel()||!((UMLModel)umlElementModel).isExistModel()){
						readOnlyMenuEnable();
					}
				}
			}
			//PKY 08082201 E ClassModel가져오는 오퍼레이션 추가

		}catch(Exception e){
			e.printStackTrace();
		}

	}

	private void fillLocalToolBar(IToolBarManager manager) {
		drillDownAdapter.addNavigationActions(manager);
	}

	public UMLTreeModel getUMLTreeModelSelected() {
		TreeSelection iSelection = (TreeSelection)viewer.getSelection();
		UMLTreeModel treeObject = (UMLTreeModel)iSelection.getFirstElement();
		return treeObject;
	}

	private void makeActions() {
		try{
			System.out.println("makeActions");
			openN3PropertyDialogAction = new OpenN3PropertyDialogAction();
			mainSave = new SaveAction();  
			mainOpen = new LoadAction();  
			mainSaveAS = new SaveAsAction();
			mainNew=new NewAction();//khg 10.4.26새로운다이어그램
			refresh = new RefreshAction();
			findModelBrowser = new Action(){
				public void run() {
					TreeSelection iSelection = (TreeSelection)viewer.getSelection();
					UMLTreeModel treeObject = (UMLTreeModel)iSelection.getFirstElement();
					ProjectManager.getInstance().setSearchModel(false);
					ProjectManager.getInstance().setisSearchDiagaramModel(false);//20080325 PKY S 검색

					String type = ProjectManager.getInstance().getModelTypeName(ProjectManager.getInstance().getModelType((UMLModel)treeObject.getRefModel(), -1));
					UMLModel um= (UMLModel)treeObject.getRefModel();

					ProjectManager.getInstance().getModelBrowser().searchModel(type, um.getID(), false,true,false);//20080325 PKY S 검색
				}

			};
			findModelBrowser.setText(N3Messages.POPUP_FIND_IN_DIAGRAMS);
			findModelBrowser.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(300)));//PKY 08070101 S 팝업 메뉴 이미지 삽입


			reGenAction = new Action(){
				public void run() {
					TreeSelection iSelection = (TreeSelection)viewer.getSelection();
					UMLTreeModel treeObject = (UMLTreeModel)iSelection.getFirstElement();

					UMLModel um= (UMLModel)treeObject.getRefModel();

					if(um instanceof AtomicComponentModel){
						AtomicComponentModel am = (AtomicComponentModel)um;
						// IJS 2010-08-13 2:16오후 소스생성  관련
//						am.setimplementLangProp("0");
//						am.setcompilerProp("0");
						int retCode = 0;

						if(am.getCoreUMLTreeModel()!=null){
							//							am.getCoreDiagramCmpModel();
							//							UMLTreeModel ut = am.getCoreUMLTreeModel();
							AtomicComponentModel am1  = am.getCoreDiagramCmpModel();
							File  f = new File(am1.getCmpFolder()+File.separator+am1.getName()+".cpp");

							if(f.exists() && am1.isNeedSourceModify()){
								OPRoSRegenSourceQuestionDialog dlg = new OPRoSRegenSourceQuestionDialog(ProjectManager.getInstance().window.getShell(),am1.isNeedSourceModify());
								dlg.open();
								retCode = dlg.getReturnCode();
							}
							if(retCode==OPRoSRegenSourceQuestionDialog.MODIFY||retCode==OPRoSRegenSourceQuestionDialog.NORMALSAVE){
								am1.writeProFile();
								am1.modifySource();
								am1.writeDataPortHeader();
								am1.writeServicePortHeader();
							}
							else{

								am1.writeProFile();
								am1.wirteSourceHeader();
								am1.writeSourceCPP();
								am1.writeDataPortHeader();
								am1.writeServicePortHeader();
							}

							OPRoSUtil.createMakeFile(null, null, am1);

						}
						else{
							am = am.getCoreDiagramCmpModel();
							File  f = new File(am.getCmpFolder()+File.separator+am.getName()+".cpp");
							if(f.exists()&& am.isNeedSourceModify()){
								OPRoSRegenSourceQuestionDialog dlg = new OPRoSRegenSourceQuestionDialog(ProjectManager.getInstance().window.getShell(),am.isNeedSourceModify());
								dlg.open();
								retCode = dlg.getReturnCode();
							}
							if(retCode==OPRoSRegenSourceQuestionDialog.MODIFY||retCode==OPRoSRegenSourceQuestionDialog.NORMALSAVE){
								am.writeProFile();
								am.modifySource();
								am.writeDataPortHeader();
								am.writeServicePortHeader();
							}
							else{
								am.writeProFile();
								am.wirteSourceHeader();
								am.writeSourceCPP();
								am.writeDataPortHeader();
								am.writeServicePortHeader();
							}
							//							am.writeServicePortProvidedHeader();
							//							am.writeServicePortRequiredHeader();
							//							am.writeProFile();
							OPRoSUtil.createMakeFile(null, null, am);
						}
						IWorkspace workspace = ResourcesPlugin.getWorkspace();//워크스페이스 핸들
						IWorkspaceRoot root = workspace.getRoot();//워크스페이스 루트핸들
						UMLTreeParentModel up = am.getUMLTreeModel().getParent();
						String name = "";
						if(up instanceof RootCmpEdtTreeModel){
							name = am.getName();
						}
						else{
							name = treeObject.getParent().getName();
						}
						final IProject newProjectHandle = root.getProject(name); 
//						String name = treeObject.getParent().getName();
						try{
							newProjectHandle.refreshLocal(IResource.DEPTH_INFINITE, null);
						}
						catch(Exception e){
							e.printStackTrace();
						}
					}



				}

			};
			reGenAction.setText("generate source");
			//			reGenAction.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(300)));//PKY 08070101 S 팝업 메뉴 이미지 삽입

//			//테스트 
//			teGenAction = new Action(){
//				public void run() {
//					TreeSelection iSelection = (TreeSelection)viewer.getSelection();
//					UMLTreeModel treeObject = (UMLTreeModel)iSelection.getFirstElement();
//
//					UMLModel um= (UMLModel)treeObject.getRefModel();
//
//					if(um instanceof AtomicComponentModel){
//						AtomicComponentModel am = (AtomicComponentModel)um;
//						// IJS 2010-08-13 2:16오후 소스생성  관련
////						am.setimplementLangProp("0");
////						am.setcompilerProp("0");
//						int retCode = 0;
//
//						if(am.getCoreUMLTreeModel()!=null){
//							//							am.getCoreDiagramCmpModel();
//							//							UMLTreeModel ut = am.getCoreUMLTreeModel();
//							AtomicComponentModel am1  = am.getCoreDiagramCmpModel();
//							File  f = new File(am1.getCmpFolder()+File.separator+am1.getName()+".cpp");
//
//							if(f.exists() && am1.isNeedSourceModify()){
//								OPRoSRegenSourceQuestionDialog dlg = new OPRoSRegenSourceQuestionDialog(ProjectManager.getInstance().window.getShell(),am1.isNeedSourceModify());
//								dlg.open();
//								retCode = dlg.getReturnCode();
//							}
//							if(retCode==OPRoSRegenSourceQuestionDialog.MODIFY||retCode==OPRoSRegenSourceQuestionDialog.NORMALSAVE){
//								am1.writeProFile();
//								am1.modifySource();
//								am1.writeDataPortHeader();
//								am1.writeServicePortHeader();
//							}
//							else{
//
//								am1.writeProFile();
//								am1.wirteSourceHeader();
//								am1.writeSourceCPP();
//								am1.writeDataPortHeader();
//								am1.writeServicePortHeader();
//							}
//
//							OPRoSUtil.createMakeFile(null, null, am1);
//
//						}
//						else{
//							am = am.getCoreDiagramCmpModel();
//							File  f = new File(am.getCmpFolder()+File.separator+am.getName()+".cpp");
//							if(f.exists()&& am.isNeedSourceModify()){
//								OPRoSRegenSourceQuestionDialog dlg = new OPRoSRegenSourceQuestionDialog(ProjectManager.getInstance().window.getShell(),am.isNeedSourceModify());
//								dlg.open();
//								retCode = dlg.getReturnCode();
//							}
//							if(retCode==OPRoSRegenSourceQuestionDialog.MODIFY||retCode==OPRoSRegenSourceQuestionDialog.NORMALSAVE){
//								am.writeProFile();
//								am.modifySource();
//								am.writeDataPortHeader();
//								am.writeServicePortHeader();
//							}
//							else{
//								am.writeProFile();
//								am.wirteSourceHeader();
//								am.writeSourceCPP();
//								am.writeDataPortHeader();
//								am.writeServicePortHeader();
//							}
//							//							am.writeServicePortProvidedHeader();
//							//							am.writeServicePortRequiredHeader();
//							//							am.writeProFile();
//							OPRoSUtil.createMakeFile(null, null, am);
//						}
//						IWorkspace workspace = ResourcesPlugin.getWorkspace();//워크스페이스 핸들
//						IWorkspaceRoot root = workspace.getRoot();//워크스페이스 루트핸들
//						UMLTreeParentModel up = am.getUMLTreeModel().getParent();
//						String name = "";
//						if(up instanceof RootCmpEdtTreeModel){
//							name = am.getName();
//						}
//						else{
//							name = treeObject.getParent().getName();
//						}
//						final IProject newProjectHandle = root.getProject(name); 
////						String name = treeObject.getParent().getName();
//						try{
//							newProjectHandle.refreshLocal(IResource.DEPTH_INFINITE, null);
//						}
//						catch(Exception e){
//							e.printStackTrace();
//						}
//					}
//
//
//
//				}
//
//			};
//			teGenAction.setText("test");
//			
			// IJS 2010-08-13 2:16오후 소스생성  관련 S

			reGenMSVCAction = new Action(){
				public void run() {
					TreeSelection iSelection = (TreeSelection)viewer.getSelection();
					UMLTreeModel treeObject = (UMLTreeModel)iSelection.getFirstElement();

					UMLModel um= (UMLModel)treeObject.getRefModel();

					if(um instanceof AtomicComponentModel){
						AtomicComponentModel am = (AtomicComponentModel)um;
						am.setimplementLangProp("1");
						am.setcompilerProp("1");
						if(am.getCoreUMLTreeModel()!=null){
							//							am.getCoreDiagramCmpModel();
							//							UMLTreeModel ut = am.getCoreUMLTreeModel();
							AtomicComponentModel am1  = am.getCoreDiagramCmpModel();
							am1.writeProFile();
							am1.wirteSourceHeader();
							am1.writeSourceCPP();
							am1.writeDataPortHeader();
							am1.writeServicePortHeader();

							OPRoSUtil.createMakeFile(null, null, am1);

						}
						else{
							am = am.getCoreDiagramCmpModel();
							am.writeProFile();
							am.wirteSourceHeader();
							am.writeSourceCPP();
							am.writeDataPortHeader();
							am.writeServicePortHeader();
							//							am.writeServicePortProvidedHeader();
							//							am.writeServicePortRequiredHeader();
							//							am.writeProFile();
							OPRoSUtil.createMakeFile(null, null, am);
						}
						IWorkspace workspace = ResourcesPlugin.getWorkspace();//워크스페이스 핸들
						IWorkspaceRoot root = workspace.getRoot();//워크스페이스 루트핸들
						final IProject newProjectHandle = root.getProject(am.getName()); 
						try{
							newProjectHandle.refreshLocal(IResource.DEPTH_INFINITE, null);
						}
						catch(Exception e){
							e.printStackTrace();
						}
					}



				}

			};
			reGenMSVCAction.setText("MSVC C++");
			//			reGenMSVCAction.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(300)));//PKY 08070101 S 팝업 메뉴 이미지 삽입

			// IJS 2010-08-13 2:16오후 소스생성  관련 E

			// IJS 2010-08-12 4:17오후  라이브러리 이동 관련 S

			exportAtomicComponent = new Action(){
				public void run() {
					TreeSelection iSelection = (TreeSelection)viewer.getSelection();
					UMLTreeModel treeObject = (UMLTreeModel)iSelection.getFirstElement();

					UMLModel um= (UMLModel)treeObject.getRefModel();

					if(um instanceof AtomicComponentModel){
						AtomicComponentModel am = (AtomicComponentModel)um;
						String str = am.getCmpFolder();
						HashMap map = new HashMap();
						map.put("name", am.getName());
						map.put("id", am.getId());
						map.put("description", am.getDesc());
						map.put("properties",am.getTags());
						String libfolder = ProjectManager.getInstance().getSourceFolder();
						File srcFile = new File(str+File.separator+"profile"+File.separator+am.getName()+".xml");
						File desproFile = new File(libfolder+File.separator+am.getName()+".xml");
						if(srcFile==null || !srcFile.exists()){
							 MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_WARNING);
							 dialog.setText("Message");
							 dialog.setMessage(am.getName()+".xml"+" does not exist.");
							 dialog.open();
							 return;
						}
						if(srcFile==null || !srcFile.exists()){
							 MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_WARNING);
							 dialog.setText("Message");
							 dialog.setMessage(am.getName()+".xml"+" does not exist.");
							 dialog.open();
							 return;
						}
						File dllFile = null;
						//					if(dllFile!=null)
						Preferences pref = Activator.getDefault().getPluginPreferences();
						String strDeploy= pref.getString(PreferenceConstants.OPROS_DEPLOY_OPTION);
						File desFile = new File(libfolder+File.separator+am.getlibNameProp());
						dllFile = new File(str+File.separator+"Debug"+File.separator+am.getlibNameProp());
						if(dllFile==null || !dllFile.exists()){
							 MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_WARNING);
							 dialog.setText("Message");
							 dialog.setMessage(am.getlibNameProp()+" does not exist.");
							 dialog.open();
							 return;
						}
						
						if(srcFile!=null && srcFile.exists()){
							CompAssemManager.getInstance().copyInstanceFile(srcFile, desproFile, map);
						}

						

						if(dllFile.exists()){
							CompAssemManager.getInstance().copyFile(dllFile, desFile);
						}
						else{
							dllFile = new File(str+File.separator+"Release"+File.separator+am.getlibNameProp());
							if(dllFile.exists()){
								CompAssemManager.getInstance().copyFile(dllFile, desFile);
							}
							else{

							}
						}

						CompAssemManager.getInstance().addLibComponent(desproFile,dllFile,am);



						//						IWorkspace workspace = ResourcesPlugin.getWorkspace();//워크스페이스 핸들
						//						IWorkspaceRoot root = workspace.getRoot();//워크스페이스 루트핸들
						//						final IProject newProjectHandle = root.getProject(am.getName()); 
						//						try{
						//						newProjectHandle.refreshLocal(IResource.DEPTH_INFINITE, null);
						//						}
						//						catch(Exception e){
						//							e.printStackTrace();
						//						}
					}



				}

			};
			exportAtomicComponent.setText("export Library");
			exportAtomicComponent.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(300)));//PKY 08070101 S 팝업 메뉴 이미지 삽입
			// IJS 2010-08-12 4:17오후  라이브러리 이동 관련 E			

			//모니터링 액션

			connectMonitor = new Action() {
				public void run() {
					//ETRI
					try{
						//				showMessage("Action 1 executed");
						TreeSelection iSelection = (TreeSelection)viewer.getSelection();
						UMLTreeParentModel treeObject = (UMLTreeParentModel)iSelection.getFirstElement();
						InputDialog inputDialog = new InputDialog(null,
								"connect Monitor",  "ip:port(ex:127.0.0.1:7000)", "", null);//PKY 08070101 S 영어로 변경
						inputDialog.open();
						int retCode = inputDialog.getReturnCode();
						if (retCode == 0) {
							String name = inputDialog.getValue();
							//KDI 080908 0002 s
							boolean isOverlapping = ProjectManager.getInstance().isOverlapping(treeObject, 0, name);
							if(isOverlapping) {
								MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_WARNING);
								dialog.setText("Message");
								dialog.setMessage(N3Messages.DIALOG_SAME_NAME_CONFLICT_MESSAGE);
								dialog.open();
								return ;
							}

							int i = name.indexOf(":");

							String ip = "";
							int port = -1;

							if(i>0){
								ip = name.substring(0,i);
								try{
									InetAddress addr = InetAddress.getByName(ip);
								}
								catch(Exception e){
									MessageDialog.openError(ProjectManager.getInstance().window.getShell(), "Error", "IP 의 값이 잘못되었습니다.");
									e.printStackTrace();
								}
							}
							else{
								MessageDialog.openError(ProjectManager.getInstance().window.getShell(), "Error", "Port 를 입력하여 주세요");

								return ;
							}


							String ports = name.substring(i+1);
							try{
								port = Integer.valueOf(ports);
							}
							catch(Exception e){
								MessageDialog.openError(ProjectManager.getInstance().window.getShell(), "Error", "Port 의 값이 잘못되었습니다.");
								e.printStackTrace();
								return ;
							}
							//KDI 080908 0002 e
							//							java.util.ArrayList list= NetManager.getInstance().getRepoComponentsInfo(name);
							//							if(list==null)
							//								return;

							ServerMonitorTreeModel srt= new ServerMonitorTreeModel(name);
							srt.setParent(treeObject);
							treeObject.addChild(srt);

							System.out.println("dddd");


							//							try{
							////								java.util.ArrayList list= NetManager.getInstance().getRepoComponentsInfo(name);
							//							
							//								NetManager.getInstance().makeTree(srt, list);
							//							}
							//							catch(Exception e){
							//								e.printStackTrace();
							//							}
							refresh(treeObject);
							expend(treeObject);

							FileWriter fw = null;
							PrintWriter bw=null;
							try{
								fw = new FileWriter(new File("c:\\ip2.txt"),true);
								bw=new PrintWriter(fw);

								bw.write(name+"\n");
								fw.close();
								bw.close();
								saveValue = -1;

							}
							catch(Exception e){
								e.printStackTrace();

							}
							finally{
								try{

								}
								catch(Exception e){
									if(fw!=null)
										fw.close();
									if(bw!=null)
										bw.close();
								}
							}

							//					ProjectManager.getInstance().addUMLModel(name, treeObject, null, 0,-1);
						}
					}
					catch(Exception e1){
						System.out.println("application:"+application);
						try{
							FileWriter fw = new FileWriter(new File("c:\\err1.log"));
							PrintWriter bw=new PrintWriter(fw);

							e1.printStackTrace(bw);
							//						bw.write(e1.printStackTrace());
							bw.close();
							saveValue = -1;

						}
						catch(Exception e){
							e.printStackTrace();

						}
						e1.printStackTrace();
					}
				}
			};
			connectMonitor.setText("Connect Monitoring Server");//2008040301 PKY S 
			connectMonitor.setToolTipText("Action 1 tooltip");
			appListAction = new Action(){
				public void run() {
					try{
						TreeSelection iSelection = (TreeSelection)viewer.getSelection();
						PackageTreeModel treeObject = (PackageTreeModel)iSelection.getFirstElement();
						NodeItemTreeModel nit = null;
						NodeItemModel nim = null;
						if(treeObject instanceof NodeItemTreeModel){
							nit = (NodeItemTreeModel)treeObject;
							nim = (NodeItemModel)nit.getRefModel();

						}


						HeaderSendData hrd = new HeaderSendData();
						hrd.target = "monitor";
						hrd.cmd = "app.list";
						hrd.payloadSize = String.valueOf("0");
						hrd.cmdType = 1;
						//					NetIPPort nipport = getNetIPPort(treeObject.getName());
						NetManager.getInstance().setIp(nim.getIP());
						NetManager.getInstance().setPort(String.valueOf(nim.getPort1()));
						if(!isTest)
							NetManager.getInstance().connectNetwork();
						boolean isTotalSuccess = true;
						if(!isTest)
							isTotalSuccess = NetManager.getInstance().cmd(hrd);
						String str ="ver=1.0;target=monitor;cmd=app.list;success=ok;ret=hello,def;payloadSize=0";
						if(!isTest)
							str = SocketUtil.read_line(NetManager.getInstance().m_is);

						if(str!=null && !str.trim().equals("")){

							HeaderSendData hrd1 = new HeaderSendData();

							hrd1.cmdType = 9;
							if(!isTest)
								isTotalSuccess = NetManager.getInstance().cmd(hrd1);
							String str2 = "ver=1.0;target=monitor;cmd=app.list;success=ok;ret=hello;payloadSize=0";
							if(!isTest)
								str2 = SocketUtil.read_line(NetManager.getInstance().m_is);
							HeaderReceiveData hrs2 = new HeaderReceiveData();

							hrs2.setHeaderReceiveData(str2);
							String[] ret2 = hrs2.getRet();

							System.out.println("응답"+str);
							java.util.ArrayList childs = new java.util.ArrayList(); 
							for(int i=0;i<treeObject.getChildren().length;i++){
								Object obj = treeObject.getChildren()[i];
								childs.add(obj);
							}
							for(int i=0;i<childs.size();i++){
								Object obj = childs.get(i);
								treeObject.removeChild((UMLTreeModel)obj);
							}
							HeaderReceiveData hrs = new HeaderReceiveData();
							hrs.setHeaderReceiveData(str);
							NetManager.getInstance().addMsg("서버 응답: "+hrs.ret);
							if(hrs!=null){
								if(NetManager.ok.equals(hrs.success)){
									UMLTreeParentModel upm = null;
									if(treeObject instanceof UMLTreeParentModel){
										upm = (UMLTreeParentModel)treeObject;
									}
									String[] rets = hrs.getRet();
									if(rets!=null){
										for(int i1=0;i1<rets.length;i1++){
											String t1 = rets[i1];
											int ii = t1.indexOf(".xml");
											if(ii>0){
												t1 = t1.substring(0,ii);
											}
											AppTreeModel app = new AppTreeModel(t1);
											app.ip = nim.getIP();
											app.port = String.valueOf(nim.getPort1());
											upm.addChild(app);
											if(ret2!=null){
												for(int i=0;i<ret2.length;i++){
													if(ret2[i].equals(t1)){
														app.isRun = true;
													}

												}
											}
										}
									}
								}
								else{
									NetManager.getInstance().setErrMsg(hrs.ret);
								}

							}
							ProjectManager.getInstance().getModelBrowser().refresh(treeObject);
							ProjectManager.getInstance().getModelBrowser().expend(treeObject);
						}
						if(!isTest)
							NetManager.getInstance().closeNetwork();
					}
					catch(Exception e){
						e.printStackTrace();
					}
					NetManager.getInstance().printMsg();
				}



			};
			appListAction.setText("Get Application List");
			//			appCompListAction.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(300)));//PKY 08070101 S 팝업 메뉴 이미지 삽입



			appCompListAction = new Action(){
				public void run() {
					//					dd
					try{
						TreeSelection iSelection = (TreeSelection)viewer.getSelection();
						PackageTreeModel  treeObject = (PackageTreeModel )iSelection.getFirstElement();
						HeaderSendData hrd = new HeaderSendData();
						hrd.target = "monitor";
						hrd.cmd = "app.comp_list";
						hrd.app_name = treeObject.getName();
						hrd.payloadSize = String.valueOf("0");
						hrd.cmdType = 2;
						//					NetIPPort nipport = getNetIPPort(treeObject.getName());
						NetManager.getInstance().setIp(treeObject.ip);
						NetManager.getInstance().setPort(String.valueOf(treeObject.port));
						if(!isTest)
							NetManager.getInstance().connectNetwork();
						boolean isTotalSuccess = true;
						if(!isTest)
							isTotalSuccess = NetManager.getInstance().cmd(hrd);
						String str ="ver=1.0;target=monitor;cmd=app.comp_list;app.name=hello;success=ok;ret=CompA:atomic,CompB:composite;payloadSize=0";
						if(!isTest)
							str = SocketUtil.read_line(NetManager.getInstance().m_is);

						java.util.ArrayList childs = new java.util.ArrayList(); 
						for(int i=0;i<treeObject.getChildren().length;i++){
							Object obj = treeObject.getChildren()[i];
							childs.add(obj);
						}
						for(int i=0;i<childs.size();i++){
							Object obj = childs.get(i);
							treeObject.removeChild((UMLTreeModel)obj);
						}

						if(str!=null && !str.trim().equals("")){
							System.out.println("응답"+str);

							HeaderReceiveData hrs = new HeaderReceiveData();
							hrs.setHeaderReceiveData(str);
							NetManager.getInstance().addMsg("서버 응답: "+hrs.ret);
							if(hrs!=null){
								if(NetManager.ok.equals(hrs.success)){
									UMLTreeParentModel upm = null;
									if(treeObject instanceof UMLTreeParentModel){
										upm = (UMLTreeParentModel)treeObject;
									}
									String[] rets = hrs.getRet();
									if(rets!=null){
										for(int i1=0;i1<rets.length;i1++){
											String temp = rets[i1];
											String sp[] = temp.split(":");
											MonitorCompTreeModel app = null;
											if(sp!=null && sp.length==2){
												app = new MonitorCompTreeModel(sp[0]);
												if(!"atomic".equals(sp[1])){
													app.isAtomic = false;
												}

											}
											else{
												app = new MonitorCompTreeModel(rets[i1]);
												//											 app.isAtomic
											}
											//										MonitorCompTreeModel app = new MonitorCompTreeModel(rets[i1]);
											app.ip = treeObject.ip;
											app.port = String.valueOf(treeObject.port);
											upm.addChild(app);
										}
									}
								}
								else{
									NetManager.getInstance().setErrMsg(hrs.ret);
								}

							}
							ProjectManager.getInstance().getModelBrowser().refresh(treeObject);
							ProjectManager.getInstance().getModelBrowser().expend(treeObject);
						}
						if(!isTest)
							NetManager.getInstance().closeNetwork();
					}
					catch(Exception e){
						e.printStackTrace();
					}
					NetManager.getInstance().printMsg();
				}



			};
			appCompListAction.setText("Get Component List");


			appRunAction = new Action(){
				public void run() {
					try{
						TreeSelection iSelection = (TreeSelection)viewer.getSelection();
						UMLTreeModel treeObject = (UMLTreeModel)iSelection.getFirstElement();
						HeaderSendData hrd = new HeaderSendData();
						hrd.app_name = treeObject.getName();
						hrd.cmdType = 3;
						//					NetIPPort nipport = getNetIPPort(treeObject.getName());
						NodeItemModel nim = null;

						//					if(treeObject.getParent() instanceof NodeItemTreeModel){
						//						NodeItemTreeModel nit = (NodeItemTreeModel)treeObject;
						//						nim = (NodeItemModel)nit.getRefModel();
						//					}
						NetManager.getInstance().setIp(treeObject.ip);
						NetManager.getInstance().setPort(String.valueOf(treeObject.port));
						if(!isTest)
							NetManager.getInstance().connectNetwork();
						boolean isTotalSuccess = true;
						if(!isTest)
							isTotalSuccess = NetManager.getInstance().cmd(hrd);
						String str ="ver=1.0;target=monitor;cmd=app.run;success=ok;ret=ok;payloadSize=0";
						if(!isTest)
							str = SocketUtil.read_line(NetManager.getInstance().m_is);

						if(str!=null && !str.trim().equals("")){
							System.out.println("응답"+str);

							HeaderReceiveData hrs = new HeaderReceiveData();

							hrs.setHeaderReceiveData(str);
							NetManager.getInstance().addMsg("서버 응답: "+hrs.ret);
							if(hrs!=null){
								if(NetManager.ok.equals(hrs.success)){
									AppTreeModel apt = (AppTreeModel)treeObject;
									apt.isRun = true;
									hookAppTreeModelModelContextMenu();
									refresh(treeObject.getParent());
								}
								else{
									NetManager.getInstance().setErrMsg(hrs.ret);
								}
							}

							//						if(hrs!=null){
							//							if(NetManager.ok.equals(hrs.success)){
							//								UMLTreeParentModel upm = null;
							//								if(treeObject instanceof UMLTreeParentModel){
							//									upm = (UMLTreeParentModel)treeObject;
							//								}
							//								String[] rets = hrs.getRet();
							//								if(rets!=null){
							//									for(int i1=0;i1<rets.length;i1++){
							//										TopCompTreeModel app = new TopCompTreeModel(rets[i1]);
							//										upm.addChild(app);
							//									}
							//								}
							//							}
							//							
							//						}
							//						ProjectManager.getInstance().getModelBrowser().refresh(treeObject);
							//						ProjectManager.getInstance().getModelBrowser().expend(treeObject);
						}
						if(!isTest)
							NetManager.getInstance().closeNetwork();
					}
					catch(Exception e){
						e.printStackTrace();
					}
					NetManager.getInstance().printMsg();
				}



			};
			appRunAction.setText("Run Application");


			appStopAction = new Action(){
				public void run() {
					try{
						TreeSelection iSelection = (TreeSelection)viewer.getSelection();
						UMLTreeModel treeObject = (UMLTreeModel)iSelection.getFirstElement();
						HeaderSendData hrd = new HeaderSendData();
						hrd.app_name = treeObject.getName();
						hrd.cmdType = 4;
						NodeItemModel nim = null;

						//					if(treeObject.getParent() instanceof NodeItemTreeModel){
						//						NodeItemTreeModel nit = (NodeItemTreeModel)treeObject;
						//						nim = (NodeItemModel)nit.getRefModel();
						//					}
						NetManager.getInstance().setIp(treeObject.ip);
						NetManager.getInstance().setPort(String.valueOf(treeObject.port));
						if(!isTest)
							NetManager.getInstance().connectNetwork();
						boolean isTotalSuccess = true;
						if(!isTest)
							isTotalSuccess = NetManager.getInstance().cmd(hrd);
						String str ="ver=1.0;target=monitor;cmd=app.stop;success=ok;ret=ok;payloadSize=0";
						if(!isTest)
							str = SocketUtil.read_line(NetManager.getInstance().m_is);

						if(str!=null && !str.trim().equals("")){
							System.out.println("응답"+str);

							HeaderReceiveData hrs = new HeaderReceiveData();
							hrs.setHeaderReceiveData(str);
							NetManager.getInstance().addMsg("서버 응답: "+hrs.ret);
							if(hrs!=null){
								if(NetManager.ok.equals(hrs.success)){
									AppTreeModel apt = (AppTreeModel)treeObject;
									apt.isRun = false;
									hookAppTreeModelModelContextMenu();
									refresh(treeObject.getParent());
								}
								else{
									NetManager.getInstance().setErrMsg(hrs.ret);
								}
							}
							//						if(hrs!=null){
							//							if(NetManager.ok.equals(hrs.success)){
							//								UMLTreeParentModel upm = null;
							//								if(treeObject instanceof UMLTreeParentModel){
							//									upm = (UMLTreeParentModel)treeObject;
							//								}
							//								String[] rets = hrs.getRet();
							//								if(rets!=null){
							//									for(int i1=0;i1<rets.length;i1++){
							//										TopCompTreeModel app = new TopCompTreeModel(rets[i1]);
							//										upm.addChild(app);
							//									}
							//								}
							//							}
							//							
							//						}
							//						ProjectManager.getInstance().getModelBrowser().refresh(treeObject);
							//						ProjectManager.getInstance().getModelBrowser().expend(treeObject);
						}
						if(!isTest)
							NetManager.getInstance().closeNetwork();
					}
					catch(Exception e){
						e.printStackTrace();
					}
					NetManager.getInstance().printMsg();
				}



			};
			appStopAction.setText("Stop Application");


			appStateAction = new Action(){
				public void run() {
					try{
						TreeSelection iSelection = (TreeSelection)viewer.getSelection();
						UMLTreeModel treeObject = (UMLTreeModel)iSelection.getFirstElement();
						HeaderSendData hrd = new HeaderSendData();
						hrd.app_name = treeObject.getName();
						hrd.cmdType = 5;
						NodeItemModel nim = null;

						//					if(treeObject.getParent() instanceof NodeItemTreeModel){
						//						NodeItemTreeModel nit = (NodeItemTreeModel)treeObject;
						//						nim = (NodeItemModel)nit.getRefModel();
						//					}
						NetManager.getInstance().setIp(treeObject.ip);
						NetManager.getInstance().setPort(String.valueOf(treeObject.port));
						if(!isTest)
							NetManager.getInstance().connectNetwork();
						boolean isTotalSuccess = true;
						if(!isTest)
							isTotalSuccess = NetManager.getInstance().cmd(hrd);
						String str ="ver=1.0;target=monitor;cmd=app.state;success=ok;ret=ACTIVATED;payloadSize=0";
						if(!isTest)
							str = SocketUtil.read_line(NetManager.getInstance().m_is);

						if(str!=null && !str.trim().equals("")){
							System.out.println("응답"+str);

							HeaderReceiveData hrs = new HeaderReceiveData();
							hrs.setHeaderReceiveData(str);
							if(NetManager.ok.equals(hrs.success)){
								NetManager.getInstance().addMsg("<서버 응답>");
								NetManager.getInstance().addMsg(hrs.getRet());
							}
							else{

								NetManager.getInstance().setErrMsg(hrs.ret);

							}
							//						if(hrs!=null){
							//							if(NetManager.ok.equals(hrs.success)){
							//								UMLTreeParentModel upm = null;
							//								if(treeObject instanceof UMLTreeParentModel){
							//									upm = (UMLTreeParentModel)treeObject;
							//								}
							//								String[] rets = hrs.getRet();
							//								if(rets!=null){
							//									for(int i1=0;i1<rets.length;i1++){
							//										TopCompTreeModel app = new TopCompTreeModel(rets[i1]);
							//										upm.addChild(app);
							//									}
							//								}
							//							}
							//							
							//						}
							//						ProjectManager.getInstance().getModelBrowser().refresh(treeObject);
							//						ProjectManager.getInstance().getModelBrowser().expend(treeObject);
						}
						if(!isTest)
							NetManager.getInstance().closeNetwork();
					}
					catch(Exception e){
						e.printStackTrace();
					}
					NetManager.getInstance().printMsg();
				}



			};
			appStateAction.setText("Get Application State");


			compStateAction = new Action(){
				public void run() {
					try{
						TreeSelection iSelection = (TreeSelection)viewer.getSelection();
						UMLTreeModel treeObject = (UMLTreeModel)iSelection.getFirstElement();
						HeaderSendData hrd = new HeaderSendData();
						hrd.comp_name = treeObject.getName();
						hrd.cmdType = 6;
						NetManager.getInstance().setIp(treeObject.ip);
						NetManager.getInstance().setPort(String.valueOf(treeObject.port));
						if(!isTest)
							NetManager.getInstance().connectNetwork();
						boolean isTotalSuccess = true;
						if(!isTest)
							isTotalSuccess = NetManager.getInstance().cmd(hrd);
						String str ="ver=1.0;target=monitor;cmd=comp.state;success=ok;ret=status:STARTED,type:atomic,exec_type:periodic,exec_period:100;payloadSize=0";
						if(!isTest)
							str = SocketUtil.read_line(NetManager.getInstance().m_is);

						if(str!=null && !str.trim().equals("")){
							System.out.println("응답"+str);

							HeaderReceiveData hrs = new HeaderReceiveData();
							hrs.setHeaderReceiveData(str);
							//						NetManager.getInstance().addMsg("서버 응답: "+hrs.ret);
							if(NetManager.ok.equals(hrs.success)){
								NetManager.getInstance().addMsg("<서버 응답>");
								NetManager.getInstance().addMsg(hrs.getRet());
							}
							else{
								NetManager.getInstance().setErrMsg(hrs.ret);
							}
							//						if(hrs!=null){
							//							if(NetManager.ok.equals(hrs.success)){
							//								UMLTreeParentModel upm = null;
							//								if(treeObject instanceof UMLTreeParentModel){
							//									upm = (UMLTreeParentModel)treeObject;
							//								}
							//								String[] rets = hrs.getRet();
							//								if(rets!=null){
							//									for(int i1=0;i1<rets.length;i1++){
							//										TopCompTreeModel app = new TopCompTreeModel(rets[i1]);
							//										upm.addChild(app);
							//									}
							//								}
							//							}
							//							
							//						}
							//						ProjectManager.getInstance().getModelBrowser().refresh(treeObject);
							//						ProjectManager.getInstance().getModelBrowser().expend(treeObject);
						}
						if(!isTest)
							NetManager.getInstance().closeNetwork();
					}
					catch(Exception e){
						e.printStackTrace();
					}
					NetManager.getInstance().printMsg();
				}



			};
			compStateAction.setText("Get Component State");


			compListAction = new Action(){
				public void run() {
					try{
						TreeSelection iSelection = (TreeSelection)viewer.getSelection();
						PackageTreeModel treeObject = (PackageTreeModel)iSelection.getFirstElement();
						HeaderSendData hrd = new HeaderSendData();
						hrd.comp_name = treeObject.getName();
						hrd.cmdType = 7;
						NetManager.getInstance().setIp(treeObject.ip);
						NetManager.getInstance().setPort(String.valueOf(treeObject.port));
						if(!isTest)
							NetManager.getInstance().connectNetwork();
						boolean isTotalSuccess = true;

						if(!isTest)
							isTotalSuccess = NetManager.getInstance().cmd(hrd);
						String str ="ver=1.0;target=monitor;cmd=app.comp_list;success=ok;ret=SA,SB;payloadSize=0";
						if(!isTest)
							str = SocketUtil.read_line(NetManager.getInstance().m_is);
						java.util.ArrayList childs = new java.util.ArrayList(); 
						for(int i=0;i<treeObject.getChildren().length;i++){
							Object obj = treeObject.getChildren()[i];
							childs.add(obj);
						}
						for(int i=0;i<childs.size();i++){
							Object obj = childs.get(i);
							treeObject.removeChild((UMLTreeModel)obj);
						}
						if(str!=null && !str.trim().equals("")){
							System.out.println("응답"+str);

							HeaderReceiveData hrs = new HeaderReceiveData();
							hrs.setHeaderReceiveData(str);
							NetManager.getInstance().addMsg("서버 응답: "+hrs.ret);
							if(hrs!=null){
								if(NetManager.ok.equals(hrs.success)){
									UMLTreeParentModel upm = null;
									if(treeObject instanceof UMLTreeParentModel){
										upm = (UMLTreeParentModel)treeObject;
									}
									String[] rets = hrs.getRet();
									if(rets!=null){
										for(int i1=0;i1<rets.length;i1++){
											String temp = rets[i1];
											String sp[] = temp.split(":");
											MonitorCompTreeModel app = null;
											if(sp!=null && sp.length==2){
												app = new MonitorCompTreeModel(sp[0]);
												if(!"atomic".equals(sp[1])){
													app.isAtomic = false;
												}

											}
											else{
												app = new MonitorCompTreeModel(rets[i1]);
												//											 app.isAtomic
											}
											app.ip = treeObject.ip;
											app.port = String.valueOf(treeObject.port);
											upm.addChild(app);
										}
									}
								}
								else{
									NetManager.getInstance().setErrMsg(hrs.ret);
								}

							}
							ProjectManager.getInstance().getModelBrowser().refresh(treeObject);
							ProjectManager.getInstance().getModelBrowser().expend(treeObject);
						}
						if(!isTest)
							NetManager.getInstance().closeNetwork();
					}
					catch(Exception e){
						e.printStackTrace();
					}
					NetManager.getInstance().printMsg();
				}



			};
			compListAction.setText("Get Component List");


			compPropAction = new Action(){
				public void run() {
					try{
						TreeSelection iSelection = (TreeSelection)viewer.getSelection();
						UMLTreeModel treeObject = (UMLTreeModel)iSelection.getFirstElement();
						HeaderSendData hrd = new HeaderSendData();
						hrd.comp_name = treeObject.getName();
						hrd.cmdType = 8;
						NetManager.getInstance().setIp(treeObject.ip);
						NetManager.getInstance().setPort(String.valueOf(treeObject.port));
						if(!isTest)
							NetManager.getInstance().connectNetwork();
						boolean isTotalSuccess = true;
						if(!isTest)
							isTotalSuccess = NetManager.getInstance().cmd(hrd);
						String str ="ver=1.0;target=monitor;cmd=comp.props;success=ok;ret=parity:1,port:com3;payloadSize=0";
						if(!isTest)
							str = SocketUtil.read_line(NetManager.getInstance().m_is);

						if(str!=null && !str.trim().equals("")){
							System.out.println("응답"+str);

							HeaderReceiveData hrs = new HeaderReceiveData();
							hrs.setHeaderReceiveData(str);
							//						NetManager.getInstance().addMsg("서버 응답 -> "+hrs.ret);
							if(NetManager.ok.equals(hrs.success)){
								NetManager.getInstance().addMsg("< 서버 응답  >");
								NetManager.getInstance().addMsg(hrs.getRet());
							}
							else{
								NetManager.getInstance().setErrMsg(hrs.ret);
							}
							//						if(hrs!=null){
							//							if(NetManager.ok.equals(hrs.success)){
							//								UMLTreeParentModel upm = null;
							//								if(treeObject instanceof UMLTreeParentModel){
							//									upm = (UMLTreeParentModel)treeObject;
							//								}
							//								String[] rets = hrs.getRet();
							//								if(rets!=null){
							//									for(int i1=0;i1<rets.length;i1++){
							//										TopCompTreeModel app = new TopCompTreeModel(rets[i1]);
							//										upm.addChild(app);
							//									}
							//								}
							//							}
							//							
							//						}
							//						ProjectManager.getInstance().getModelBrowser().refresh(treeObject);
							//						ProjectManager.getInstance().getModelBrowser().expend(treeObject);
						}
						if(!isTest)
							NetManager.getInstance().closeNetwork();
					}
					catch(Exception e){
						e.printStackTrace();
					}
					NetManager.getInstance().printMsg();
				}



			};
			compPropAction.setText("Get Component Props");




			findReqModelBrowser = new Action(){
				public void run() {
					TreeSelection iSelection = (TreeSelection)viewer.getSelection();
					UMLTreeModel treeObject = (UMLTreeModel)iSelection.getFirstElement();
					ProjectManager.getInstance().setSearchModel(false);
					ProjectManager.getInstance().setisSearchDiagaramModel(false);//20080325 PKY S 검색

					String type = ProjectManager.getInstance().getModelTypeName(ProjectManager.getInstance().getModelType((UMLModel)treeObject.getRefModel(), -1));
					UMLModel um= (UMLModel)treeObject.getRefModel();

					ProjectManager.getInstance().getModelBrowser().searchReqIDModel(um.getReqID(), null);
				}

			};
			findReqModelBrowser.setText(N3Messages.POPUP_TRACING_IN_REQUIREMENT);
			findReqModelBrowser.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(300)));//PKY 08070101 S 팝업 메뉴 이미지 삽입

			//		V1.02 WJH E 080904 S 요구사항 추적표 추가
			excelReqModelSave = new Action(){
				public void run() {
					IWorkbenchWindow workbenchWindow = ProjectManager.getInstance().window;
					FileDialog fsd = new FileDialog(workbenchWindow.getShell(),SWT.SAVE);
					fsd.setFilterExtensions(new String[] {"*.xls","*.*"});
					fsd.setText("Select Excel Files...");
					fsd.open();

					String fileName = fsd.getFileName();
					String path = fsd.getFilterPath();
					if(fileName.equals("")){
						return;
					}
					ExcelManager.getInstance().initCreateWorkbook(path+File.separator+fileName, 2);

					TreeSelection iSelection = (TreeSelection)viewer.getSelection();
					UMLTreeModel treeObject = (UMLTreeModel)iSelection.getFirstElement();
					ProjectManager.getInstance().setSearchModel(false);
					ProjectManager.getInstance().setisSearchDiagaramModel(false);//20080325 PKY S 검색

					String type = ProjectManager.getInstance().getModelTypeName(ProjectManager.getInstance().getModelType((UMLModel)treeObject.getRefModel(), -1));
					UMLModel um= (UMLModel)treeObject.getRefModel();

					ProjectManager.getInstance().getModelBrowser().searchReqIDModel(um.getReqID(), null);
					java.util.ArrayList list = ProjectManager.getInstance().getSearchModel();
					ExcelManager.getInstance().saveRequiredExcel(list, um.getName());
					ExcelManager.getInstance().closeWorkbook();
					System.out.println();
				}

			};
			excelReqModelSave.setText(N3Messages.POPUP_REQUIRED_EXCEL);
			excelReqModelSave.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(300)));//PKY 08070101 S 팝업 메뉴 이미지 삽입
			//		V1.02 WJH E 080904 E 요구사항 추적표 추가

			//참조관련
			addRefModel = new Action() {
				public void run() {
					TreeSelection iSelection = (TreeSelection)viewer.getSelection();
					UMLTreeModel treeObject = (UMLTreeModel)iSelection.getFirstElement();
					if (ProjectManager.getInstance().getSelectNodes() != null &&
							ProjectManager.getInstance().getSelectNodes().size() == 1) {
						Object obj = ProjectManager.getInstance().getSelectNodes().get(0);
						UMLEditPart classEditPart = (UMLEditPart)obj;
						UMLModel p = (UMLModel)classEditPart.getModel();
						if(p instanceof ClassifierModel){
							ClassifierModel umModel = (ClassifierModel)classEditPart.getModel();

							Object c = umModel.getClassModel();
							if (c instanceof TypeRefModel) {
								TypeRefModel typeRefModel = (TypeRefModel)c;
								//							TreeSelection iSelection = (TreeSelection)viewer.getSelection();
								//							UMLTreeModel treeObject = (UMLTreeModel)iSelection.getFirstElement();
								typeRefModel.setTypeRef((UMLModel)treeObject.getRefModel());
								Object obj1 = treeObject.getRefModel();
								if (obj1 instanceof ClassifierModel) {
									try {
										ClassifierModel um = (ClassifierModel)obj1;
										um.getClassModel().addUpdateListener(typeRefModel);
									}
									catch (Exception e) {
										e.printStackTrace();
									}
								}
							}//-->
						}

						//					}
					}
					//				ProjectManager.getInstance().deleteUMLModel(treeObject);
					//				ProjectManager.getInstance().removeUMLNode(treeObject.getParent(), treeObject);
				}
			};
			addRefModel.setText("참조추가");
			addRefModel.setToolTipText("Action 1 tooltip");
			addRefModel.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
					getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
			addPackage = new Action() {
				public void run() {
					//ETRI
					try{
						//				showMessage("Action 1 executed");
						TreeSelection iSelection = (TreeSelection)viewer.getSelection();
						UMLTreeParentModel treeObject = (UMLTreeParentModel)iSelection.getFirstElement();
						InputDialog inputDialog = new InputDialog(null,
								N3Messages.POPUP_PACKAGE_ADD,  N3Messages.POPUP_NAME, "", null);//PKY 08070101 S 영어로 변경
						inputDialog.open();
						int retCode = inputDialog.getReturnCode();
						if (retCode == 0) {
							String name = inputDialog.getValue();
							//KDI 080908 0002 s
							boolean isOverlapping = ProjectManager.getInstance().isOverlapping(treeObject, 0, name);
							if(isOverlapping) {
								MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_WARNING);
								dialog.setText("Message");
								dialog.setMessage(N3Messages.DIALOG_SAME_NAME_CONFLICT_MESSAGE);
								dialog.open();
								return ;
							}
							//KDI 080908 0002 e
							ProjectManager.getInstance().addUMLModel(name, treeObject, null, 0,-1);
						}
					}
					catch(Exception e1){
						System.out.println("application:"+application);
						try{
							FileWriter fw = new FileWriter(new File("c:\\err1.log"));
							PrintWriter bw=new PrintWriter(fw);

							e1.printStackTrace(bw);
							//						bw.write(e1.printStackTrace());
							bw.close();
							saveValue = -1;

						}
						catch(Exception e){
							e.printStackTrace();

						}
						e1.printStackTrace();
					}
				}
			};
			addPackage.setText(N3Messages.POPUP_PACKAGE_ADD);//2008040301 PKY S 
			addPackage.setToolTipText("Action 1 tooltip");
			addPackage.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(315)));//PKY 08070101 S 팝업 메뉴 이미지 삽입

			newApplication = new Action() {
				public void run() {
					//ETRI
					try{
						//				showMessage("Action 1 executed");
						TreeSelection iSelection = (TreeSelection)viewer.getSelection();
						UMLTreeParentModel treeObject = (UMLTreeParentModel)iSelection.getFirstElement();
						InputDialog inputDialog = new InputDialog(null,
								"Application Name" , N3Messages.POPUP_NAME, "", null);//PKY 08070101 S 영어로 변경
						inputDialog.open();
						int retCode = inputDialog.getReturnCode();
						if (retCode == 0) {
							String name = inputDialog.getValue();
							//KDI 080908 0002 s
							boolean isOverlapping = ProjectManager.getInstance().isOverlapping(treeObject, 0, name);
							if(isOverlapping) {
								MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_WARNING);
								dialog.setText("Message");
								dialog.setMessage(N3Messages.DIALOG_SAME_NAME_CONFLICT_MESSAGE);
								dialog.open();
								return ;
							}
							//KDI 080908 0002 e
							FinalPackageModel finalPackageModel = new FinalPackageModel();
							finalPackageModel.getUMLDataModel().setProperty("ID_TYPE", "Application");
							ProjectManager.getInstance().addUMLModel(name, treeObject, finalPackageModel, 0,5);
							isChange = true;
						}
					}
					catch(Exception e1){
						System.out.println("application:"+application);
						try{
							FileWriter fw = new FileWriter(new File("c:\\err1.log"));
							PrintWriter bw=new PrintWriter(fw);

							e1.printStackTrace(bw);
							//						bw.write(e1.printStackTrace());
							bw.close();
							saveValue = -1;

						}
						catch(Exception e){
							e.printStackTrace();

						}
						e1.printStackTrace();
					}
				}
			};
			newApplication.setText("Add Application");//2008040301 PKY S 
			newApplication.setToolTipText("Action 1 tooltip");
			newApplication.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(315)));//PKY 08070101 S 팝업 메뉴 이미지 삽입



			porperties = new Action() {
				public void run() {
					//ETRI
					try{
						//				showMessage("Action 1 executed");
						TreeSelection iSelection = (TreeSelection)viewer.getSelection();
						UMLTreeParentModel treeObject = (UMLTreeParentModel)iSelection.getFirstElement();
						InputDialog inputDialog = new InputDialog(null,
								N3Messages.POPUP_PACKAGE_ADD,  N3Messages.POPUP_NAME, "", null);//PKY 08070101 S 영어로 변경
						inputDialog.open();
						int retCode = inputDialog.getReturnCode();
						if (retCode == 0) {
							String name = inputDialog.getValue();
							//KDI 080908 0002 s
							boolean isOverlapping = ProjectManager.getInstance().isOverlapping(treeObject, 0, name);
							if(isOverlapping) {
								MessageBox dialog = new MessageBox(null,SWT.ICON_WARNING);
								dialog.setText("Message");
								dialog.setMessage(N3Messages.DIALOG_SAME_NAME_CONFLICT_MESSAGE);
								dialog.open();
								return ;
							}
							//KDI 080908 0002 e
							ProjectManager.getInstance().addUMLModel(name, treeObject, null, 0,-1);
						}
					}
					catch(Exception e){
						e.printStackTrace();
					}
				}
			};
			porperties.setText("Properties");//2008040301 PKY S 
			porperties.setToolTipText("Action 1 tooltip");
			porperties.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(315)));//PKY 08070101 S 팝업 메뉴 이미지 삽입



			connectRepository = new Action() {
				public void run() {
					//ETRI
					try{
						//				showMessage("Action 1 executed");
						TreeSelection iSelection = (TreeSelection)viewer.getSelection();
						UMLTreeParentModel treeObject = (UMLTreeParentModel)iSelection.getFirstElement();
						InputDialog inputDialog = new InputDialog(null,
								"connect repository",  "ip:port(ex:127.0.0.1:7000)", "", null);//PKY 08070101 S 영어로 변경
						inputDialog.open();
						int retCode = inputDialog.getReturnCode();
						if (retCode == 0) {
							String name = inputDialog.getValue();
							//KDI 080908 0002 s
							boolean isOverlapping = ProjectManager.getInstance().isOverlapping(treeObject, 0, name);
							if(isOverlapping) {
								MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_WARNING);
								dialog.setText("Message");
								dialog.setMessage(N3Messages.DIALOG_SAME_NAME_CONFLICT_MESSAGE);
								dialog.open();
								return ;
							}
							//KDI 080908 0002 e
							java.util.ArrayList list= NetManager.getInstance().getRepoComponentsInfo(name);


							ServerRepositoryTreeModel srt= new ServerRepositoryTreeModel(name);
							srt.setParent(treeObject);
							treeObject.addChild(srt);
							ProjectManager.getInstance().getModelBrowser().refresh(treeObject);
							ProjectManager.getInstance().getModelBrowser().expend(treeObject);

							System.out.println("dddd");


							try{
								//								java.util.ArrayList list= NetManager.getInstance().getRepoComponentsInfo(name);

								if(list!=null)
									NetManager.getInstance().makeTree(srt, list);
							}
							catch(Exception e){
								e.printStackTrace();
							}
							refresh(treeObject);
							expend(treeObject);

							FileWriter fw = null;
							PrintWriter bw=null;
							try{
								fw = new FileWriter(new File("c:\\ip.txt"),true);
								bw=new PrintWriter(fw);

								bw.write(name+"\n");
								fw.close();
								bw.close();
								saveValue = -1;

							}
							catch(Exception e){
								e.printStackTrace();

							}
							finally{
								try{

								}
								catch(Exception e){
									if(fw!=null)
										fw.close();
									if(bw!=null)
										bw.close();
								}
							}

							//					ProjectManager.getInstance().addUMLModel(name, treeObject, null, 0,-1);
						}
					}
					catch(Exception e1){
						System.out.println("application:"+application);
						try{
							FileWriter fw = new FileWriter(new File("c:\\err1.log"));
							PrintWriter bw=new PrintWriter(fw);

							e1.printStackTrace(bw);
							//						bw.write(e1.printStackTrace());
							bw.close();
							saveValue = -1;

						}
						catch(Exception e){
							e.printStackTrace();

						}
						e1.printStackTrace();
					}
				}
			};
			connectRepository.setText("Connect Repository");//2008040301 PKY S 
			connectRepository.setToolTipText("Action 1 tooltip");
			connectRepository.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(349)));//PKY 08070101 S 팝업 메뉴 이미지 삽입


			connect = new Action() {
				public void run() {
					//ETRI
					try{
						//				showMessage("Action 1 executed");
						TreeSelection iSelection = (TreeSelection)viewer.getSelection();
						UMLTreeParentModel treeObject = (UMLTreeParentModel)iSelection.getFirstElement();
                         




						try{
							java.util.ArrayList childs = new java.util.ArrayList(); 
							for(int i=0;i<treeObject.getChildren().length;i++){
								Object obj = treeObject.getChildren()[i];
								childs.add(obj);
							}
							for(int i=0;i<childs.size();i++){
								Object obj = childs.get(i);
								treeObject.removeChild((UMLTreeModel)obj);
							}
							java.util.ArrayList list= NetManager.getInstance().getRepoComponentsInfo(treeObject.getName());
							NetManager.getInstance().makeTree(treeObject, list);
						}
						catch(Exception e){
							e.printStackTrace();
							ProjectManager.getInstance().writeLog("error", e);
						}
						refresh(treeObject);
						expend(treeObject);



					}
					catch(Exception e1){
						System.out.println("application:"+application);
						try{
							FileWriter fw = new FileWriter(new File("c:\\err1.log"));
							PrintWriter bw=new PrintWriter(fw);

							e1.printStackTrace(bw);
							//						bw.write(e1.printStackTrace());
							bw.close();
							saveValue = -1;

						}
						catch(Exception e){
							e.printStackTrace();

						}
						e1.printStackTrace();
					}
				}
			};
			connect.setText("Connect");//2008040301 PKY S 
			connect.setToolTipText("Action 1 tooltip");
			connect.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(348)));//PKY 08070101 S 팝업 메뉴 이미지 삽입



			saveLink = new Action() {
				public void run() {
					if(ProjectManager.getInstance()!=null ){
						TreeSelection iSelection = (TreeSelection)viewer.getSelection();
						UMLTreeParentModel treeObject = (UMLTreeParentModel)iSelection.getFirstElement();

						FileDialog fsd = new FileDialog(ProjectManager.getInstance().window.getShell(),SWT.SAVE);

						//				fsd.setFilterPath(ProjectManager.getInstance().getTeamProjectFolder());
						fsd.setFilterExtensions(new String[] {"*.link","*.*"});
						fsd.setText("Select Link Files...");
						fsd.setFileName(treeObject.getName());
						String text = fsd.open();
						//PKY 08082201 S 팀 프로젝트 경로를 프로젝트에서 변경가능하도록



						if(!text.equals("") && fsd.getFilterPath().indexOf(ProjectManager.getInstance().getTeamProjectFolder()) > -1){
							String fileName = fsd.getFileName();
							String path = fsd.getFilterPath();

							System.out.print(ProjectManager.getInstance().getTeamProjectFolder());
							//					path = path.replace(ProjectManager.getInstance().getTeamProjectFolder(),"");
							if(fileName.equals("")){
								return;
							}
							//				showMessage("Action 1 executed");
							TeamProjectManager.getInstance().setLinkSave(true);
							writeLinkModel(path+File.separator+fileName,treeObject);//PKY 08082201 S 팀 프로젝트 경로를 프로젝트에서 변경가능하도록
							TeamProjectManager.getInstance().addLink(treeObject,path+File.separator+fileName);//PKY 08082201 S 팀 프로젝트 경로를 프로젝트에서 변경가능하도록
							TeamProjectManager.getInstance().setLinkSave(false);
							//					InputDialog inputDialog = new InputDialog(ProjectManager.getInstance().window.getShell(),
							//					"Save Link",  N3Messages.POPUP_NAME, "", null);//PKY 08070101 S 영어로 변경
							//					inputDialog.open();
							//					int retCode = inputDialog.getReturnCode();
							//					if (retCode == 0) {
							//					String name = inputDialog.getValue();
							//					ProjectManager.getInstance().addUMLModel(name, treeObject, null, 0,-1);
							//					}
						}else{
							if(!text.equals("")){
								MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_WARNING);
								dialog.setText("Message");
								dialog.setMessage(N3Messages.DIALOG_ADD_TEAM_FOLDER_WARNING);
								dialog.open();
							}
						}

					}else{
						MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_WARNING);
						dialog.setText("Message");
						dialog.setMessage(N3Messages.DIALOG_ADD_TEAM_FOLDER_WARNING);
						dialog.open();
					}
				}
			};

			//PKY 08082201 E 팀 프로젝트 경로를 프로젝트에서 변경가능하도록

			saveLink.setText("Save Link");//2008040301 PKY S 
			saveLink.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(315)));//PKY 08070101 S 팝업 메뉴 이미지 삽입
			//20080811IJS

			//20080811IJS 시작
			loadLink = new Action() {
				public void run() {
					ModelManager.getInstance().init();
					String fileName = "";
					String path = "";
					TreeSelection iSelection = (TreeSelection)viewer.getSelection();
					UMLTreeParentModel treeObject = (UMLTreeParentModel)iSelection.getFirstElement();
					boolean isPath = false;
					if(treeObject.getLinkPath()==null
							|| treeObject.getLinkPath().trim().equals("")){
						FileDialog fsd = new FileDialog(ProjectManager.getInstance().window.getShell(),SWT.OPEN);
						fsd.setFilterExtensions(new String[] {"*.link","*.*"});
						fsd.setText("Select Link Files...");
						fsd.setFileName(treeObject.getName());					
						fsd.open();


						fileName = fsd.getFileName();
						path = fsd.getFilterPath();
						if(fileName.equals("")){
							return;
						}
					}
					else{
						isPath = true;
					}

					//				ProjectManager.getInstance().setCurrentProjectName(fileName);
					//				ProjectManager.getInstance().setCurrentProjectPath(path);

					//				showMessage("Action 1 executed");
					try{
						TeamProjectManager.getInstance().setLinkLoad(true);
						ModelManager.getInstance().setLinkPackage(treeObject) ;
						File fLink = new File(path+File.separator+fileName);

						if(!isPath)
							fLink = new File(path+File.separator+fileName);
						else{
							//PKY 08082201 S 팀 프로젝트 경로를 프로젝트에서 변경가능하도록
							fLink = new File(treeObject.getLinkPath().replace(TeamProjectManager.getInstance().ID_TEAM_PROJECT_FOLDER, ProjectManager.getInstance().getTeamProjectFolder()));
							//PKY 08082201 E 팀 프로젝트 경로를 프로젝트에서 변경가능하도록
						}
						//PKY 08082201 S 팀 프로젝트 경로를 프로젝트에서 변경가능하도록
						if(!fLink.exists()){
							MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_WARNING);
							dialog.setText("Message");
							String url = treeObject.getLinkPath().replace(TeamProjectManager.getInstance().ID_TEAM_PROJECT_FOLDER, ProjectManager.getInstance().getTeamProjectFolder());
							dialog.setMessage(N3Messages.DIALOG_OPEN_TEAM_NOT_FILE_EXCEPTION+url);
							dialog.open();
							return;
						}
						//PKY 08082201 E 팀 프로젝트 경로를 프로젝트에서 변경가능하도록
						if(!fLink.canWrite()){
							TeamProjectManager.getInstance().setReadOnly(true);
							UMLModel um = (UMLModel)treeObject.getRefModel();
							if(um!=null)//PKY 08082201 S 새로운 프로젝트에서 저장되어있는 Link 불러올 경우 에러발생
								um.setReadOnlyModel(true);
						}
						//PKY 08090101 S 모델 브라우져 패키지 Enable에러 문제
						if(treeObject.getRefModel()!=null){
							UMLModel um = (UMLModel)treeObject.getRefModel();
							if(um!=null)
								um.setExistModel(true);
						}
						//PKY 08090101 E 모델 브라우져 패키지 Enable에러 문제

						ModelManager.getInstance().parse(fLink);
						ProgressMonitorDialog pd = new ProgressMonitorDialog(ProjectManager.getInstance().window.getShell());
						pd.run(true, true, (IRunnableWithProgress)ModelManager.getInstance().getParser());

						treeObject.setIsLinkType(1);

						refresh(treeObject);
						if(ModelManager.getInstance().getRefStore().size()>0){
							for(int i=0;i<ModelManager.getInstance().getRefStore().size();i++){
								RefLinkModel rf = (RefLinkModel)ModelManager.getInstance().getRefStore().get(i);
								rf.link();
							}
						}
						TeamProjectManager.getInstance().initModel();
						TeamProjectManager.getInstance().setReadOnly(false);//PKY 08082201 S 프로젝트에 링크파일을 불러온 후 프로젝트에 모델을 추가할 경우 ReadOnly로 되어있는문제
					}
					catch(Exception e){
						e.printStackTrace();
					}



					ProjectManager.getInstance().getModelBrowser().refresh(ProjectManager.getInstance().getModelBrowser().getRoot());
					ModelManager.getInstance().setLinkPackage(null) ;
					ModelManager.getInstance().init();


					ModelManager.getInstance().getOpenDiagarams().clear();
					//				InputDialog inputDialog = new InputDialog(ProjectManager.getInstance().window.getShell(),
					//				"Save Link",  N3Messages.POPUP_NAME, "", null);//PKY 08070101 S 영어로 변경
					//				inputDialog.open();
					//				int retCode = inputDialog.getReturnCode();
					//				if (retCode == 0) {
					//				String name = inputDialog.getValue();
					//				ProjectManager.getInstance().addUMLModel(name, treeObject, null, 0,-1);
					//				}
				}
			};
			loadLink.setText("Load Link");//2008040301 PKY S 
			loadLink.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(315)));//PKY 08070101 S 팝업 메뉴 이미지 삽입
			//20080811IJS 끝


			//20080811IJS 시작
			addLink = new Action() {
				public void run() {
					ModelManager.getInstance().init();
					ProjectManager.getInstance().getModelBrowser().intRootLibTreeModel();
					String fileName = "";
					String path = "";
					TreeSelection iSelection = (TreeSelection)viewer.getSelection();
					UMLTreeParentModel treeObject = (UMLTreeParentModel)iSelection.getFirstElement();
					boolean isPath = false;

					FileDialog fsd = new FileDialog(ProjectManager.getInstance().window.getShell(),SWT.OPEN);
					fsd.setFilterExtensions(new String[] {"*.link","*.*"});
					fsd.setText("Select Link Files...");
					fsd.open();

					fileName = fsd.getFileName();
					path = fsd.getFilterPath();
					//PKY 08082201 S 프로퍼티 오픈후 컬러쪽 화면을 보지 않을경우 에러발생
					if(fileName.equals("")){
						return;
					}
					//				if(ProjectManager.getInstance()!=null && ProjectManager.getInstance().getTeamProjectFolder()!=null)
					//				if(path.indexOf(ProjectManager.getInstance().getTeamProjectFolder())== -1 || ProjectManager.getInstance().getTeamProjectFolder().equals("") ){
					//					if(!fileName.equals("")){
					//					 MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_WARNING);
					//					 dialog.setText("Message");
					//					 dialog.setMessage(N3Messages.DIALOG_ADD_TEAM_FOLDER_WARNING);
					//					 dialog.open();
					//					}
					//					return;
					//				}
					//PKY 08082201 E 프로퍼티 오픈후 컬러쪽 화면을 보지 않을경우 에러발생


					//				ProjectManager.getInstance().setCurrentProjectName(fileName);
					//				ProjectManager.getInstance().setCurrentProjectPath(path);

					//				showMessage("Action 1 executed");
					try{
						TeamProjectManager.getInstance().setParent(treeObject);
						TeamProjectManager.getInstance().setAddLink(true);
						TeamProjectManager.getInstance().setAddLinkPath(path+File.separator+fileName);
						File fLink = new File(path+File.separator+fileName);

						TeamProjectManager.getInstance().setLinkLoad(true);
						if(!fLink.canWrite()){
							TeamProjectManager.getInstance().setReadOnly(true);
							UMLModel um = (UMLModel)treeObject.getRefModel();
							if(um!=null)//PKY 08082201 S 새로운 프로젝트에서 저장되어있는 Link 불러올 경우 에러발생
								um.setReadOnlyModel(true);
						}
						ModelManager.getInstance().parse(fLink);


						ProgressMonitorDialog pd = new ProgressMonitorDialog(ProjectManager.getInstance().window.getShell());
						pd.run(true, true, (IRunnableWithProgress)ModelManager.getInstance().getParser());


						refresh(treeObject);
						TeamProjectManager.getInstance().init();
					}
					catch(Exception e){
						e.printStackTrace();
					}



					ProjectManager.getInstance().getModelBrowser().refresh(ProjectManager.getInstance().getModelBrowser().getRoot());
					ModelManager.getInstance().setLinkPackage(null) ;
					TeamProjectManager.getInstance().initModel();
					ModelManager.getInstance().init();


					ModelManager.getInstance().getOpenDiagarams().clear();
					//				InputDialog inputDialog = new InputDialog(ProjectManager.getInstance().window.getShell(),
					//				"Save Link",  N3Messages.POPUP_NAME, "", null);//PKY 08070101 S 영어로 변경
					//				inputDialog.open();
					//				int retCode = inputDialog.getReturnCode();
					//				if (retCode == 0) {
					//				String name = inputDialog.getValue();
					//				ProjectManager.getInstance().addUMLModel(name, treeObject, null, 0,-1);
					//				}
				}
			};
			addLink.setText("add Link");//2008040301 PKY S 
			addLink.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(315)));//PKY 08070101 S 팝업 메뉴 이미지 삽입
			//20080811IJS 끝

			addDiagram = new Action() {
				public void run() {
					//				showMessage("Action 1 executed");
					TreeSelection iSelection = (TreeSelection)viewer.getSelection();
					UMLTreeParentModel treeObject = (UMLTreeParentModel)iSelection.getFirstElement();
					NewDiagramDialog newDiagramDialog = new NewDiagramDialog(ProjectManager.getInstance().window.getShell());
					newDiagramDialog.open();
					String name = newDiagramDialog.getDiagramName();
					//				newDiagramDialog.getShell().setSize(500, 500);
					if (newDiagramDialog.isOK){
						//KDI 080908 0002 s
						boolean isOverlapping = ProjectManager.getInstance().isOverlapping(treeObject, newDiagramDialog.getDiagramTYpe(), name, true);
						if(isOverlapping) {
							MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_WARNING);
							dialog.setText("Message");
							dialog.setMessage(N3Messages.DIALOG_SAME_NAME_CONFLICT_MESSAGE);
							dialog.open();
							return ;
						}
						//KDI 080908 0002 e
						ProjectManager.getInstance().addUMLDiagram(name, treeObject, null, 1, true,newDiagramDialog.getDiagramTYpe());
					}
				}
			};
			addDiagram.setText(N3Messages.POPUP_DIAGRAME_ADD);//2008040301 PKY S 
			addDiagram.setToolTipText("Action 1 tooltip");
			addDiagram.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(317)));//PKY 08070101 S 팝업 메뉴 이미지 삽입
			addModel = new Action() {
				public void run() {
					//				showMessage("Action 1 executed");
					try{
						//				showMessage("Action 1 executed");
						TreeSelection iSelection = (TreeSelection)viewer.getSelection();
						UMLTreeParentModel treeObject = (UMLTreeParentModel)iSelection.getFirstElement();
						InputDialog inputDialog = new InputDialog(null,
								N3Messages.POPUP_PACKAGE_ADD,  N3Messages.POPUP_NAME, "", null);//PKY 08070101 S 영어로 변경
						inputDialog.open();
						int retCode = inputDialog.getReturnCode();
						if (retCode == 0) {
							String name = inputDialog.getValue();
							//KDI 080908 0002 s
							boolean isOverlapping = ProjectManager.getInstance().isOverlapping(treeObject, 0, name);
							if(isOverlapping) {
								MessageBox dialog = new MessageBox(null,SWT.ICON_WARNING);
								dialog.setText("Message");
								dialog.setMessage(N3Messages.DIALOG_SAME_NAME_CONFLICT_MESSAGE);
								dialog.open();
								return ;
							}
							//KDI 080908 0002 e
							ProjectManager.getInstance().addUMLModel(name, treeObject, null, 29,5);
						}
					}
					catch(Exception e){
						e.printStackTrace();
					}

				}
			};
			addModel.setText("Add Composite");//2008040301 PKY S 
			addModel.setToolTipText("Add Composite");
			addModel.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(316)));//PKY 08070101 S 팝업 메뉴 이미지 삽입



			//		addModel = new Action() {
			//			public void run() {
			//				//				showMessage("Action 1 executed");
			//				TreeSelection iSelection = (TreeSelection)viewer.getSelection();
			//				UMLTreeParentModel treeObject = (UMLTreeParentModel)iSelection.getFirstElement();
			//				NewModelDialog newModelDialog = new NewModelDialog(ProjectManager.getInstance().window.getShell());
			//				newModelDialog.open();
			//				String name = newModelDialog.getName();
			//				//				newDiagramDialog.getShell().setSize(500, 500);
			//				if (newModelDialog.isOK)
			//					ProjectManager.getInstance().addUMLModel(name, treeObject, null, newModelDialog.getModelType(),-1);
			//			}
			//		};
			//		addModel.setText(N3Messages.POPUP_MODEL_ADD);//2008040301 PKY S 
			//		addModel.setToolTipText("Action 1 tooltip");
			//		addModel.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(316)));//PKY 08070101 S 팝업 메뉴 이미지 삽입


			saveDiagram = new Action() {
				public void run() {
					TreeSelection iSelection = (TreeSelection)viewer.getSelection();
					UMLTreeParentModel treeObject = (UMLTreeParentModel)iSelection.getFirstElement();
					StringBuffer sb = new StringBuffer();
					Object obj =  treeObject.getRefModel();
					if(obj instanceof ComponentLibModel){
						ComponentLibModel clm = (ComponentLibModel)obj;
						File f = clm.getFile();
						String path = f.getParent();
						path = path+File.separator+clm.getName()+"_view.xml";
						writeComponentViewXMLModel(path,treeObject);
					}
					//				ProjectManager.getInstance(






				}
			};
			saveDiagram.setText("Save Diagram");//2008040301 PKY S 
			saveDiagram.setToolTipText("Save Diagram");
			saveDiagram.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(316)));

			exportComponent = new Action() {
				public void run() {
					TreeSelection iSelection = (TreeSelection)viewer.getSelection();
					UMLTreeParentModel treeObject = (UMLTreeParentModel)iSelection.getFirstElement();

					FileDialog fsd = new FileDialog(ProjectManager.getInstance().window.getShell(),SWT.SAVE);
					fsd.setFilterExtensions(new String[] {"*.xml","*.*"});
					fsd.setText("Select xml Files...");
					fsd.setFileName(treeObject.getName());
					String text = fsd.open();
					
					if(fsd.getFileName()!=null){
						String fileName = fsd.getFileName();
						String path = fsd.getFilterPath();
						String tPath = path+File.separator+fileName;
						File target = new File(path);
						if(fileName.equals("")){
							return;
						}
						CompAssemManager.getInstance().setUmMake(treeObject);
						CompAssemManager.getInstance().setTargetMake(target);
						CompAssemManager.getInstance().setRunType(3);
						//						CompAssemManager.getInstance().makeCopyMain(treeObject, target);
						ProjectManager.getInstance().getModelBrowser().setRoot(ProjectManager.getInstance().getModelBrowser().getRootTree());
						ProgressMonitorDialog progress =  new ProgressMonitorDialog(ProjectManager.getInstance().window.getShell());

						//					CompAssemManager.getInstance().setRtm(ProjectManager.getInstance().getModelBrowser().getRootLibTreeModel());
						try{
							progress.run(true, true, CompAssemManager.getInstance());
						}
						catch(Exception e){
							e.printStackTrace();
						}

						//					CompAssemManager.getInstance().makeCopyComposite(treeObject, target);


					}else{
						if(!text.equals("")){
							MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_WARNING);
							dialog.setText("Message");
							dialog.setMessage(N3Messages.DIALOG_ADD_TEAM_FOLDER_WARNING);
							dialog.open();
						}
					}



				}
			};
			exportComponent.setText("Export Component");//2008040301 PKY S 
			exportComponent.setToolTipText("Action 1 tooltip");
			exportComponent.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(316)));//PKY 08070101 S 팝업 메뉴 이미지 삽입


			
			exportComponent_tar = new Action() {
				public void run() {
					TreeSelection iSelection = (TreeSelection)viewer.getSelection();
					UMLTreeParentModel treeObject = (UMLTreeParentModel)iSelection.getFirstElement();

					FileDialog fsd = new FileDialog(ProjectManager.getInstance().window.getShell(),SWT.SAVE);
					fsd.setFilterExtensions(new String[] {"*.xml","*.*"});
					fsd.setText("Select xml Files...");
					fsd.setFileName(treeObject.getName());
					String text = fsd.open();
					
					if(fsd.getFileName()!=null){
						NetManager.getInstance().isJar = true;
						String fileName = fsd.getFileName();
						String path = fsd.getFilterPath();
						String tPath = path+File.separator+fileName;
						File target = new File(path);
						if(fileName.equals("")){
							return;
						}
						CompAssemManager.getInstance().setUmMake(treeObject);
						CompAssemManager.getInstance().setTargetMake(target);
						CompAssemManager.getInstance().setRunType(3);
						//						CompAssemManager.getInstance().makeCopyMain(treeObject, target);
						ProjectManager.getInstance().getModelBrowser().setRoot(ProjectManager.getInstance().getModelBrowser().getRootTree());
						ProgressMonitorDialog progress =  new ProgressMonitorDialog(ProjectManager.getInstance().window.getShell());

						//					CompAssemManager.getInstance().setRtm(ProjectManager.getInstance().getModelBrowser().getRootLibTreeModel());
						try{
							progress.run(true, true, CompAssemManager.getInstance());
						}
						catch(Exception e){
							e.printStackTrace();
						}
						NetManager.getInstance().isJar = false;

						//					CompAssemManager.getInstance().makeCopyComposite(treeObject, target);


					}else{
						if(!text.equals("")){
							MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_WARNING);
							dialog.setText("Message");
							dialog.setMessage(N3Messages.DIALOG_ADD_TEAM_FOLDER_WARNING);
							dialog.open();
						}
					}



				}
			};
			exportComponent_tar.setText("Export Component(tar)");//2008040301 PKY S 
			exportComponent_tar.setToolTipText("Action 1 tooltip");
			exportComponent_tar.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(316)));//PKY 08070101 S 팝업 메뉴 이미지 삽입
			
			
			exportApplication = new Action() {
				public void run() {
								
					TreeSelection iSelection = (TreeSelection)viewer.getSelection();
					UMLTreeParentModel treeObject = (UMLTreeParentModel)iSelection.getFirstElement();
					//KHG 2010.05.20 Export 할경우 xml파일의 이름을 변경하지 못하다도록 디렉토리다이얼로그로 변경
					DirectoryDialog dd = new DirectoryDialog(ProjectManager.getInstance().window.getShell(),SWT.SINGLE);
					//					

					//					FileDialog fsd = new FileDialog(ProjectManager.getInstance().window.getShell(),SWT.SAVE);
					//					fsd.setFilterExtensions(new String[] {"*.xml","*.*"});
					//					fsd.setText("Select xml Files...");
					//					fsd.setFileName(treeObject.getName());
					//					String text = fsd.open();
					dd.setText("Select Folder...");//KHG 2010.05.20Export 할경우 xml파일의 이름을 변경하지 못하다도록 디렉토리다이얼로그로 변경
					String text = dd.open();//KHG 2010.05.20Export 할경우 xml파일의 이름을 변경하지 못하다도록 디렉토리다이얼로그로 변경
					if(!text.equals("") ){
						//String fileName = fsd.getFileName();
						String fileName = treeObject.getName();//KHG 2010.05.20Export 할경우 xml파일의 이름을 변경하지 못하다도록 디렉토리다이얼로그로 변경
						String path = dd.getFilterPath();
						String tPath = path+File.separator+fileName;
						File target = new File(path);
						if(fileName.equals("")){
							return;
						}

						ProjectManager.getInstance().getModelBrowser().setRoot(ProjectManager.getInstance().getModelBrowser().getRootTree());
						CompAssemManager.getInstance().setRunType(2);
						CompAssemManager.getInstance().setUmMake(treeObject);
						CompAssemManager.getInstance().setTargetMake(target);
						CompAssemManager.getInstance().setAppFileName(fileName);
						//					CompAssemManager.getInstance().makeCopyMain(treeObject, target);

						ProgressMonitorDialog progress =  new ProgressMonitorDialog(ProjectManager.getInstance().window.getShell());

						CompAssemManager.getInstance().setRtm(ProjectManager.getInstance().getModelBrowser().getRootLibTreeModel());
						try{
							progress.run(true, true, CompAssemManager.getInstance());
						}
						catch(Exception e){
							e.printStackTrace();
						}

					}else{
						if(!text.equals("")){
							MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_WARNING);
							dialog.setText("Message");
							dialog.setMessage(N3Messages.DIALOG_ADD_TEAM_FOLDER_WARNING);
							dialog.open();
						}
					}



				}
			};
			exportApplication.setText("Export Application");//2008040301 PKY S 
			exportApplication.setToolTipText("Action 1 tooltip");
			exportApplication.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(347)));//PKY 08070101 S 팝업 메뉴 이미지 삽입


			
			exportApplication_tar = new Action() {
				public void run() {
								
					TreeSelection iSelection = (TreeSelection)viewer.getSelection();
					UMLTreeParentModel treeObject = (UMLTreeParentModel)iSelection.getFirstElement();
					//KHG 2010.05.20 Export 할경우 xml파일의 이름을 변경하지 못하다도록 디렉토리다이얼로그로 변경
					DirectoryDialog dd = new DirectoryDialog(ProjectManager.getInstance().window.getShell(),SWT.SINGLE);
					//					

					//					FileDialog fsd = new FileDialog(ProjectManager.getInstance().window.getShell(),SWT.SAVE);
					//					fsd.setFilterExtensions(new String[] {"*.xml","*.*"});
					//					fsd.setText("Select xml Files...");
					//					fsd.setFileName(treeObject.getName());
					//					String text = fsd.open();
					dd.setText("Select Folder...");//KHG 2010.05.20Export 할경우 xml파일의 이름을 변경하지 못하다도록 디렉토리다이얼로그로 변경
					String text = dd.open();//KHG 2010.05.20Export 할경우 xml파일의 이름을 변경하지 못하다도록 디렉토리다이얼로그로 변경
					if(!text.equals("") ){
						//String fileName = fsd.getFileName();
						NetManager.getInstance().isJar = true;
						String fileName = treeObject.getName();//KHG 2010.05.20Export 할경우 xml파일의 이름을 변경하지 못하다도록 디렉토리다이얼로그로 변경
						String path = dd.getFilterPath();
						String tPath = path+File.separator+fileName;
						File target = new File(path);
						if(fileName.equals("")){
							return;
						}

						ProjectManager.getInstance().getModelBrowser().setRoot(ProjectManager.getInstance().getModelBrowser().getRootTree());
						CompAssemManager.getInstance().setRunType(2);
						CompAssemManager.getInstance().setUmMake(treeObject);
						CompAssemManager.getInstance().setTargetMake(target);
						CompAssemManager.getInstance().setAppFileName(fileName);
						//					CompAssemManager.getInstance().makeCopyMain(treeObject, target);

						ProgressMonitorDialog progress =  new ProgressMonitorDialog(ProjectManager.getInstance().window.getShell());

						CompAssemManager.getInstance().setRtm(ProjectManager.getInstance().getModelBrowser().getRootLibTreeModel());
						try{
							progress.run(true, true, CompAssemManager.getInstance());
						}
						catch(Exception e){
							e.printStackTrace();
						}
						NetManager.getInstance().isJar = false;

					}else{
						if(!text.equals("")){
							MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_WARNING);
							dialog.setText("Message");
							dialog.setMessage(N3Messages.DIALOG_ADD_TEAM_FOLDER_WARNING);
							dialog.open();
						}
					}



				}
			};
			exportApplication_tar.setText("Export Application(tar)");//2008040301 PKY S 
			exportApplication_tar.setToolTipText("Action 1 tooltip");
			exportApplication_tar.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(347)));//PKY 08070101 S 팝업 메뉴 이미지 삽입
			importApplication = new Action() {//Khg 2010.05.26 임포트 어플리케이션 
				public void run() {
					try{
						TreeSelection iSelection = (TreeSelection)viewer.getSelection();
						UMLTreeParentModel treeObject = (UMLTreeParentModel)iSelection.getFirstElement();
						RootCmpComposingTreeModel rcct = getRootCompComposeTreeModel();
						DirectoryDialog dd = new DirectoryDialog(ProjectManager.getInstance().window.getShell(),SWT.SINGLE);
						String d = dd.open();

//						CompAssemManager.getInstance().backup(d, ProjectManager.getInstance().getSourceFolder());
						CompAssemManager.getInstance().setDpath(d);
						CompAssemManager.getInstance().getModelStore().clear();
						CompAssemManager.getInstance().getViewStore().clear();

						CompAssemManager.getInstance().getDirModel().clear();
						CompAssemManager.getInstance().getDirView().clear();
						ProgressMonitorDialog progress =  new ProgressMonitorDialog(ProjectManager.getInstance().window.getShell());
						//		LoadLibProgress lp = new LoadLibProgress(true);
						CompAssemManager.getInstance().setRunType(5);
						CompAssemManager.getInstance().setPmMake(rcct);
						//CompAssemManager.getInstance().setRtm(ProjectManager.getInstance().getModelBrowser().getRootLibTreeModel());

						try{
							CompAssemManager.getInstance().setImport(true);
							//CompAssemManager.getInstance().makeApplication();

							//					CompAssemManager.getInstance().deleteModel(ProjectManager.getInstance().getModelBrowser().getRootLibTreeModel());
							progress.run(true, true, CompAssemManager.getInstance());
						}
						catch(Exception e){
							e.printStackTrace();
						}
					}
					catch(Exception e){
						e.printStackTrace();
					}

					ProjectManager.getInstance().getModelBrowser().refresh(ProjectManager.getInstance().getModelBrowser().getUMLTreeModelSelected());
					CompAssemManager.getInstance().setImport(false);
					//				System.out.println();

				}
			};
			importApplication.setText("Import Application");//2008040301 PKY S 
			importApplication.setToolTipText("Action 1 tooltip");
			importApplication.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(316)));//PKY 08070101 S 팝업 메뉴 이미지 삽입

			importModelComponent = new Action() {//Khg 2010.05.26 임포트 어플리케이션 
				public void run() {
					try{
						TreeSelection iSelection = (TreeSelection)viewer.getSelection();
						UMLTreeParentModel treeObject = (UMLTreeParentModel)iSelection.getFirstElement();
						DirectoryDialog dd = new DirectoryDialog(ProjectManager.getInstance().window.getShell(),SWT.SINGLE);
						String d = dd.open();
						CompAssemManager.getInstance().setPmMake(treeObject);
						CompAssemManager.getInstance().setDpath(d);
						ProgressMonitorDialog progress =  new ProgressMonitorDialog(ProjectManager.getInstance().window.getShell());
						//		LoadLibProgress lp = new LoadLibProgress(true);
						CompAssemManager.getInstance().setRunType(6);
						
						//CompAssemManager.getInstance().setRtm(ProjectManager.getInstance().getModelBrowser().getRootLibTreeModel());

						try{
							CompAssemManager.getInstance().setImport(true);
							//CompAssemManager.getInstance().makeApplication();

							//					CompAssemManager.getInstance().deleteModel(ProjectManager.getInstance().getModelBrowser().getRootLibTreeModel());
							progress.run(true, true, CompAssemManager.getInstance());
						}
						catch(Exception e){
							e.printStackTrace();
						}
						

					}
					catch(Exception e){
						e.printStackTrace();
					}

					ProjectManager.getInstance().getModelBrowser().refresh(ProjectManager.getInstance().getModelBrowser().getUMLTreeModelSelected());
					CompAssemManager.getInstance().setImport(false);
					//				System.out.println();
					//				System.out.println();

				}
			};
			importModelComponent.setText("Import Component");//2008040301 PKY S 
			importModelComponent.setToolTipText("Action 1 tooltip");
			importModelComponent.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(316)));//PKY 08070101 S 팝업 메뉴 이미지 삽입
			
			loadTemplate = new Action() {//Khg 2010.05.26 임포트 어플리케이션 
				public void run() {
					try{
//						TreeSelection iSelection = (TreeSelection)viewer.getSelection();
//						UMLTreeParentModel treeObject = (UMLTreeParentModel)iSelection.getFirstElement();
						RootCmpComposingTreeModel rcct = getRootCompComposeTreeModel();
//						DirectoryDialog dd = new DirectoryDialog(ProjectManager.getInstance().window.getShell(),SWT.SINGLE);
//						String d = dd.open();
//
////						CompAssemManager.getInstance().backup(d, ProjectManager.getInstance().getSourceFolder());
//						CompAssemManager.getInstance().setDpath(d);
//						CompAssemManager.getInstance().getModelStore().clear();
//						CompAssemManager.getInstance().getViewStore().clear();
//
//						CompAssemManager.getInstance().getDirModel().clear();
//						CompAssemManager.getInstance().getDirView().clear();
						ProgressMonitorDialog progress =  new ProgressMonitorDialog(ProjectManager.getInstance().window.getShell());
						//		LoadLibProgress lp = new LoadLibProgress(true);
						CompAssemManager.getInstance().setRunType(5);
						CompAssemManager.getInstance().setPmMake(rcct);
						//CompAssemManager.getInstance().setRtm(ProjectManager.getInstance().getModelBrowser().getRootLibTreeModel());

						try{
							CompAssemManager.getInstance().setImport(true);
							//CompAssemManager.getInstance().makeApplication();

							//					CompAssemManager.getInstance().deleteModel(ProjectManager.getInstance().getModelBrowser().getRootLibTreeModel());
							progress.run(true, true, CompAssemManager.getInstance());
						}
						catch(Exception e){
							e.printStackTrace();
						}
					}
					catch(Exception e){
						e.printStackTrace();
					}

					ProjectManager.getInstance().getModelBrowser().refresh(ProjectManager.getInstance().getModelBrowser().getUMLTreeModelSelected());
					CompAssemManager.getInstance().setImport(false);
					//				System.out.println();

				}
			};
			loadTemplate.setText("load Template");//2008040301 PKY S 
			loadTemplate.setToolTipText("Action 1 tooltip");
			loadTemplate.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(350)));//PKY 08070101 S 팝업 메뉴 이미지 삽입
			
			
			addTemplate = new Action() {//Khg 2010.05.26 임포트 어플리케이션 
				public void run() {
					try{
						
						
						TreeSelection iSelection = (TreeSelection)viewer.getSelection();
						UMLTreeParentModel treeObject = (UMLTreeParentModel)iSelection.getFirstElement();
						UMLModel um = (UMLModel)treeObject.getRefModel();
						InputDialog inputDialog = new InputDialog(null,
								"Component Name" , N3Messages.POPUP_NAME, um.getName(), null);//PKY 08070101 S 영어로 변경
						inputDialog.open();
						int retCode = inputDialog.getReturnCode();
						String name = "";
						UMLTreeParentModel treeP = getRcet(); 
						if (retCode == 0) {
							 name = inputDialog.getValue();
							 boolean isOverlapping = ProjectManager.getInstance().isOverlapping((UMLTreeParentModel)treeP,  name);
								if(isOverlapping) {
									MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_WARNING);
									dialog.setText("Message");
									dialog.setMessage(N3Messages.DIALOG_SAME_NAME_CONFLICT_MESSAGE);
									dialog.open();
									return ;
								}
						}
						else{
							return;
						}
						
//						UMLTreeParentModel treeP = getRcet(); 
//						boolean isOverlapping = ProjectManager.getInstance().isOverlapping((UMLTreeParentModel)treeP,  treeObject.getName());
//						String name =treeObject.getName();
//						if(isOverlapping) {
//							for(int i=0;i<1000;i++){
//								String tempname = name +i ;
//								isOverlapping = ProjectManager.getInstance().isOverlapping((UMLTreeParentModel)treeP,  tempname);
//								if(!isOverlapping){
//									name = tempname;
//									break;
//								}
//							}
//							
//						}
						ProjectManager.getInstance().setLang(0);
						
						ProjectManager.getInstance().addUMLModel(name, treeP, um, 1100,5);
						ProjectManager.getInstance().setLang(-1);

					}
					catch(Exception e){
						e.printStackTrace();
					}

					ProjectManager.getInstance().getModelBrowser().refresh(ProjectManager.getInstance().getModelBrowser().getUMLTreeModelSelected());
					CompAssemManager.getInstance().setImport(false);
					//				System.out.println();

				}
			};
			addTemplate.setText("add MinGW C++ Template");//2008040301 PKY S 
			addTemplate.setToolTipText("Action 1 tooltip");
//			addTemplate.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(316)));//PKY 08070101 S 팝업 메뉴 이미지 삽입
			
			
			addMSVCTemplate = new Action() {//Khg 2010.05.26 임포트 어플리케이션 
				public void run() {
					try{
						
						
						TreeSelection iSelection = (TreeSelection)viewer.getSelection();
						UMLTreeParentModel treeObject = (UMLTreeParentModel)iSelection.getFirstElement();
						UMLModel um = (UMLModel)treeObject.getRefModel();
						UMLTreeParentModel treeP = getRcet(); 
						InputDialog inputDialog = new InputDialog(null,
								"Component Name" , N3Messages.POPUP_NAME, um.getName(), null);//PKY 08070101 S 영어로 변경
						inputDialog.open();
						int retCode = inputDialog.getReturnCode();
						String name = "";
//						UMLTreeParentModel treeP = getRcet(); 
						if (retCode == 0) {
							 name = inputDialog.getValue();
							 boolean isOverlapping = ProjectManager.getInstance().isOverlapping((UMLTreeParentModel)treeP,  name);
								if(isOverlapping) {
									MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_WARNING);
									dialog.setText("Message");
									dialog.setMessage(N3Messages.DIALOG_SAME_NAME_CONFLICT_MESSAGE);
									dialog.open();
									return ;
								}
						}
						else{
							return;
						}
						ProjectManager.getInstance().setLang(1);
						
						ProjectManager.getInstance().addUMLModel(name, treeP, um, 1100,5);
						ProjectManager.getInstance().setLang(-1);

					}
					catch(Exception e){
						e.printStackTrace();
					}

					ProjectManager.getInstance().getModelBrowser().refresh(ProjectManager.getInstance().getModelBrowser().getUMLTreeModelSelected());
					CompAssemManager.getInstance().setImport(false);
					//				System.out.println();

				}
			};
			addMSVCTemplate.setText("add MSVC C++ Template");//2008040301 PKY S 
			addMSVCTemplate.setToolTipText("Action 1 tooltip");
			
			
			openTemplateSrcCPPComponent = new Action() {//Khg 2010.05.26 임포트 어플리케이션 
				public void run() {
//					try{
//						
//						
//						TreeSelection iSelection = (TreeSelection)viewer.getSelection();
//						UMLTreeParentModel treeObject = (UMLTreeParentModel)iSelection.getFirstElement();
//						UMLModel um = (UMLModel)treeObject.getRefModel();
//						TemplateComponentModel tcp = (TemplateComponentModel)um;
//						File f = tcp.file;
//						
//						
////						String cpp = f.getParent()+File.separator+tcp.getName()+".cpp";
////						File cppFile = new File(cpp);
//						IFileStore fileStore =  EFS.getLocalFileSystem().getStore(new Path(f.getParent()));
//						fileStore=  fileStore.getChild(tcp.getName()+".cpp");
//						if (!fileStore.fetchInfo().isDirectory() && fileStore.fetchInfo().exists())
//
//						{
//						     IWorkbenchPage page=  getSite().getWorkbenchWindow().getActivePage();
//						     try {
//						       IDE.openEditorOnFileStore(page, fileStore);
//						     } catch (Exception e) {
//						       /* some code */
//						     }
//						}
//						else{
//							
//						}
////						UMLTreeParentModel treeP = getRcet(); 
////						ProjectManager.getInstance().addUMLModel(treeObject.getName(), treeP, um, 1100,5);
//
//					}
//					catch(Exception e){
//						e.printStackTrace();
//					}

//					ProjectManager.getInstance().getModelBrowser().refresh(ProjectManager.getInstance().getModelBrowser().getUMLTreeModelSelected());
//					CompAssemManager.getInstance().setImport(false);
					//				System.out.println();

				}
			};
			openTemplateSrcCPPComponent.setText("open cpp file");//2008040301 PKY S 
			openTemplateSrcCPPComponent.setToolTipText("Action 1 tooltip");
			
			openMergecpp = new Action() {//Khg 2010.05.26 임포트 어플리케이션 
				public void run() {
//					try{
//						
////						Preferences pref = Activator.getDefault().getPluginPreferences();
////						String strMgPath = pref.getString(PreferenceConstants.P_MG);
////						if(strMgPath==null || "".equals(strMgPath.trim())){
////							MessageDialog.openWarning(getSite().getShell(), "Merge tool", "Merge tool does not exist");
////							return;
////						}
//						
//						Process process;
//						TreeSelection iSelection = (TreeSelection)viewer.getSelection();
//						UMLTreeParentModel treeObject = (UMLTreeParentModel)iSelection.getFirstElement();
//						UMLModel um = (UMLModel)treeObject.getRefModel();
//						if(um instanceof AtomicComponentModel){
//							AtomicComponentModel am = (AtomicComponentModel)um;
//							String templatPath = am.getSrcFolder();
//							String cmpPath = am.getCmpFolder();
//							File cmpFile = new File(cmpPath+File.separator+am.getName()+".cpp");
//							
//							
//							File templatFile = new File(templatPath);
//							String tName = templatFile.getName();
//							int index = tName.lastIndexOf(".");
////							String 
//							if(index>0){
//								tName = tName.substring(0, index);
//							}
//							
//							String t1 = templatFile.getParent();
//							File templatFile2 = new File(templatFile.getParent()+File.separator+tName+".cpp");
////							File templatFile2
//							if(!cmpFile.exists()){
//								MessageDialog.openWarning(getSite().getShell(), "Merge tool", am.getName()+".cpp"+" does not exist");
//								return;
//							}
//							if(!templatFile2.exists()){
//								MessageDialog.openWarning(getSite().getShell(), "Merge tool", "templateFile does not exist");
//								return;
//							}
//							
//							String path1 = cmpFile.getPath();
//							String path2 = templatFile2.getPath();
//							IFileStore fileStore = EFS.getLocalFileSystem().getStore(new Path(path1));
//							IFileStore pathStore = EFS.getLocalFileSystem().getStore(new Path(templatFile2.getParent()));
//							IFileStore fileStore2 =  pathStore.getChild(templatFile2.getName());
//							CompareConfiguration ccf = new CompareConfiguration();
//							CompareInput ci = new CompareInput(ccf);
//							ci.left = new CompareItem();
//							
//							ci.left.file = templatFile2;
//							ci.left.cdata = "template";
//							
//							ci.right = new CompareItem();
//							ci.right.file = cmpFile;
//							ci.right.cdata = "new";
//							ci.left.getWorkspaceFile(fileStore2);
//							ci.right.getWorkspaceFile(fileStore);
//							ccf.setLeftLabel(ci.left.getName());
//							ccf.setRightLabel(ci.right.getName());
//							CompareUI.openCompareEditor(ci);
//						
////						String[] cmd;
////						String tool = strMgPath;
////						
////						String Command1=templatFile2.getPath();
////						String Command2= cmpFile.getPath();
////						
////						cmd = new String[] { tool,  Command1,Command2 };
////						try{
////							Runtime.getRuntime().exec(cmd);
////							}
////							catch(Exception e){
////								e.printStackTrace();
////							}
//						}
//
//					}
//					catch(Exception e){
//						e.printStackTrace();
//					}
					

//					ProjectManager.getInstance().getModelBrowser().refresh(ProjectManager.getInstance().getModelBrowser().getUMLTreeModelSelected());
//					CompAssemManager.getInstance().setImport(false);
					//				System.out.println();

				}
			};
			openMergecpp.setText("cpp");//2008040301 PKY S 
			openMergecpp.setToolTipText("Action 1 tooltip");
			
			
			openMergeH = new Action() {//Khg 2010.05.26 임포트 어플리케이션 
				public void run() {
//					try{
//						
////						Preferences pref = Activator.getDefault().getPluginPreferences();
////						String strMgPath = pref.getString(PreferenceConstants.P_MG);
////						if(strMgPath==null || "".equals(strMgPath.trim())){
////							MessageDialog.openWarning(getSite().getShell(), "Merge tool", "Merge tool does not exist");
////							return;
////						}
//						
//						Process process;
//						TreeSelection iSelection = (TreeSelection)viewer.getSelection();
//						UMLTreeParentModel treeObject = (UMLTreeParentModel)iSelection.getFirstElement();
//						UMLModel um = (UMLModel)treeObject.getRefModel();
//						if(um instanceof AtomicComponentModel){
//							AtomicComponentModel am = (AtomicComponentModel)um;
//							String templatPath = am.getSrcFolder();
//							String cmpPath = am.getCmpFolder();
//							File cmpFile = new File(cmpPath+File.separator+am.getName()+".h");
//							
//							
//							File templatFile = new File(templatPath);
//							String tName = templatFile.getName();
//							int index = tName.lastIndexOf(".");
////							String 
//							if(index>0){
//								tName = tName.substring(0, index);
//							}
//							
//							String t1 = templatFile.getParent();
//							File templatFile2 = new File(templatFile.getParent()+File.separator+tName+".h");
////							File templatFile2
//							if(!cmpFile.exists()){
//								MessageDialog.openWarning(getSite().getShell(), "Merge tool", am.getName()+".h"+" does not exist");
//								return;
//							}
//							if(!templatFile2.exists()){
//								MessageDialog.openWarning(getSite().getShell(), "Merge tool", "templateFile does not exist");
//								return;
//							}
////							IFile file = new 
////							String filePath = "C:/download/addr.xml";
//							String path1 = cmpFile.getPath();
//							String path2 = templatFile2.getPath();
//							IFileStore fileStore = EFS.getLocalFileSystem().getStore(new Path(path1));
//							IFileStore pathStore = EFS.getLocalFileSystem().getStore(new Path(templatFile2.getParent()));
//							IFileStore fileStore2 =  pathStore.getChild(templatFile2.getName());
//							
//							CompareInput ci = new CompareInput();
//							ci.left = new CompareItem();
//							ci.left.file = templatFile2;
//							
//							ci.right = new CompareItem();
//							ci.right.file = cmpFile;
//							ci.left.getWorkspaceFile(fileStore2);
//							ci.right.getWorkspaceFile(fileStore);
////							EFS.getLocalFileSystem().get
//
////							fileStore.getFileSystem()
//							
//							CompareUI.openCompareEditor(ci);
//						
////						String[] cmd;
////						String tool = strMgPath;
////						
////						String Command1=templatFile2.getPath();
////						String Command2= cmpFile.getPath();
////						
////						cmd = new String[] { tool,  Command1,Command2 };
////						try{
////							Runtime.getRuntime().exec(cmd);
////							}
////							catch(Exception e){
////								e.printStackTrace();
////							}
//						}
//
//					}
//					catch(Exception e){
//						e.printStackTrace();
//					}
					

//					ProjectManager.getInstance().getModelBrowser().refresh(ProjectManager.getInstance().getModelBrowser().getUMLTreeModelSelected());
//					CompAssemManager.getInstance().setImport(false);
					//				System.out.println();

				}
			};
			openMergeH.setText("h");//2008040301 PKY S 
			openMergeH.setToolTipText("Action 1 tooltip");
			
			
			openTemplateSrcHComponent = new Action() {//Khg 2010.05.26 임포트 어플리케이션 
//				public void run() {
//					System.out.println("ddddd");
//
//				}
			};
			openTemplateSrcHComponent.setText("open header file");//2008040301 PKY S 
			openTemplateSrcHComponent.setToolTipText("Action 1 tooltip");
//			addTemplate.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(316)));//PKY 08070101 S 팝업 메뉴 이미지 삽입
			
			
			
			
			addAppTemplate = new Action() {//Khg 2010.05.26 임포트 어플리케이션 
				public void run() {
					RootCmpComposingTreeModel rcct = getRootCompComposeTreeModel();
					try{
						
						
						TreeSelection iSelection = (TreeSelection)viewer.getSelection();
						RootAppPackageTreeModel treeObject = (RootAppPackageTreeModel)iSelection.getFirstElement();
						
						InputDialog inputDialog = new InputDialog(null,
								"Application Name" , N3Messages.POPUP_NAME, treeObject.getName(), null);//PKY 08070101 S 영어로 변경
						inputDialog.open();
						int retCode = inputDialog.getReturnCode();
						String name = "";
						UMLTreeParentModel treeP =  getRcctm();
						if (retCode == 0) {
							 name = inputDialog.getValue();
							 boolean isOverlapping = ProjectManager.getInstance().isOverlapping((UMLTreeParentModel)treeP,  name);
								if(isOverlapping) {
									MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_WARNING);
									dialog.setText("Message");
									dialog.setMessage(N3Messages.DIALOG_SAME_NAME_CONFLICT_MESSAGE);
									dialog.open();
									return ;
								}
						}
						else{
							return;
						}
					
//						boolean isOverlapping = ProjectManager.getInstance().isOverlapping((UMLTreeParentModel)rcct,  treeObject.getName());
//						String name =treeObject.getName();
//						if(isOverlapping) {
//							for(int i=0;i<1000;i++){
//								String tempname = name +i ;
//								isOverlapping = ProjectManager.getInstance().isOverlapping((UMLTreeParentModel)rcct,  tempname);
//								if(!isOverlapping){
//									name = tempname;
//									break;
//								}
//							}
//							
//						}
//							MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_WARNING);
//							dialog.setText("Message");
//							dialog.setMessage(N3Messages.DIALOG_SAME_NAME_CONFLICT_MESSAGE);
//							dialog.open();
//							
//							InputDialog inputDialog = new InputDialog(null,
//									"New Application Name",  "Application Name", "", null);//PKY 08070101 S 영어로 변경
//							
//							
//							inputDialog.open();
//							int retCode = inputDialog.getReturnCode();
//							if(retCode==0){
//								 name = inputDialog.getValue();
//							}
//							
//						}
						
						
						PackageTreeModel rt = null;
						rt=new PackageTreeModel(name);
						treeObject.ap.nd =ProjectManager.getInstance().addUMLDiagram(rt.getName()+" diagram", (UMLTreeParentModel)rt, null, 1, false,5);
						File f = treeObject.dir;
//						treeObject.ap.nd = nd;
//						rt.addChild(child)
						rcct.addChild(rt);
						ProjectManager.getInstance().templateCmp.clear();
						ProjectManager.getInstance().setLang(0);
						addCmp(treeObject,name);
						treeObject.ap.makeTemplatView();
//						String str = f.getParent()+File.separator+treeObject.getName();
//						File path = new File(str);
//						if(path.isDirectory()){
//							CompAssemManager.getInstance().recurse2(path,(UMLTreeParentModel)rt,true);
//						}
//						UMLModel um = (UMLModel)treeObject.getRefModel();
						
//						File dir
						
						
//						UMLTreeParentModel treeP = getRcet(); 
//						ProjectManager.getInstance().addUMLModel(treeObject.getName(), treeP, um, 1100,5);
					}
					catch(Exception e){
						e.printStackTrace();
					}

					ProjectManager.getInstance().getModelBrowser().refresh((UMLTreeModel)rcct);
					CompAssemManager.getInstance().setImport(false);
					ProjectManager.getInstance().templateCmp.clear();
					ProjectManager.getInstance().setLang(-1);
					//				System.out.println();

				}
			};
			addAppTemplate.setText("add MinGW C++ Application Template");//2008040301 PKY S 
			addAppTemplate.setToolTipText("Action 1 tooltip");
			addAppTemplate.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(316)));//PKY 08070101 S 팝업 메뉴 이미지 삽입

			
			
			addMSVCAppTemplate = new Action() {//Khg 2010.05.26 임포트 어플리케이션 
				public void run() {
					RootCmpComposingTreeModel rcct = getRootCompComposeTreeModel();
					try{
						
						
						TreeSelection iSelection = (TreeSelection)viewer.getSelection();
						RootAppPackageTreeModel treeObject = (RootAppPackageTreeModel)iSelection.getFirstElement();
						
						
						InputDialog inputDialog = new InputDialog(null,
								"Application Name" , N3Messages.POPUP_NAME, treeObject.getName(), null);//PKY 08070101 S 영어로 변경
						inputDialog.open();
						int retCode = inputDialog.getReturnCode();
						String name = "";
						UMLTreeParentModel treeP = getRcctm();
						if (retCode == 0) {
							 name = inputDialog.getValue();
							 boolean isOverlapping = ProjectManager.getInstance().isOverlapping((UMLTreeParentModel)treeObject,  name);
								if(isOverlapping) {
									MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_WARNING);
									dialog.setText("Message");
									dialog.setMessage(N3Messages.DIALOG_SAME_NAME_CONFLICT_MESSAGE);
									dialog.open();
									return ;
								}
						}
						else{
							return;
						}
						
//						boolean isOverlapping = ProjectManager.getInstance().isOverlapping((UMLTreeParentModel)rcct,  treeObject.getName());
//						String name =treeObject.getName();
//						if(isOverlapping) {
//							for(int i=0;i<1000;i++){
//								String tempname = name +i ;
//								isOverlapping = ProjectManager.getInstance().isOverlapping((UMLTreeParentModel)rcct,  tempname);
//								if(!isOverlapping){
//									name = tempname;
//									break;
//								}
//							}
//							
//						}
//							MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_WARNING);
//							dialog.setText("Message");
//							dialog.setMessage(N3Messages.DIALOG_SAME_NAME_CONFLICT_MESSAGE);
//							dialog.open();
//							
//							InputDialog inputDialog = new InputDialog(null,
//									"New Application Name",  "Application Name", "", null);//PKY 08070101 S 영어로 변경
//							
//							
//							inputDialog.open();
//							int retCode = inputDialog.getReturnCode();
//							if(retCode==0){
//								 name = inputDialog.getValue();
//							}
//							
//						}
						
						
						PackageTreeModel rt = null;
						rt=new PackageTreeModel(name);
						treeObject.ap.nd =ProjectManager.getInstance().addUMLDiagram(rt.getName()+" diagram", (UMLTreeParentModel)rt, null, 1, false,5);
						File f = treeObject.dir;
//						treeObject.ap.nd = nd;
//						rt.addChild(child)
						rcct.addChild(rt);
						ProjectManager.getInstance().templateCmp.clear();
						ProjectManager.getInstance().setLang(1);
						addCmp(treeObject,name);
						treeObject.ap.makeTemplatView();
//						String str = f.getParent()+File.separator+treeObject.getName();
//						File path = new File(str);
//						if(path.isDirectory()){
//							CompAssemManager.getInstance().recurse2(path,(UMLTreeParentModel)rt,true);
//						}
//						UMLModel um = (UMLModel)treeObject.getRefModel();
						
//						File dir
						
						
//						UMLTreeParentModel treeP = getRcet(); 
//						ProjectManager.getInstance().addUMLModel(treeObject.getName(), treeP, um, 1100,5);
					}
					catch(Exception e){
						e.printStackTrace();
					}

					ProjectManager.getInstance().getModelBrowser().refresh((UMLTreeModel)rcct);
					CompAssemManager.getInstance().setImport(false);
					ProjectManager.getInstance().templateCmp.clear();
					ProjectManager.getInstance().setLang(-1);
					//				System.out.println();

				}
			};
			addMSVCAppTemplate.setText("add MSVC C++ Application Template");//2008040301 PKY S 
			addMSVCAppTemplate.setToolTipText("Action 1 tooltip");
			addMSVCAppTemplate.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(316)));//PKY 08070101 S 팝업 메뉴 이미지 삽입
			
			

			importComponent = new Action() {
				public void run() {
					try{
						DirectoryDialog dd = new DirectoryDialog(ProjectManager.getInstance().window.getShell(),SWT.SINGLE);
						String d = dd.open();
						
						if(d.equals(ProjectManager.getInstance().getSourceFolder())){
							MessageDialog.openWarning(ProjectManager.getInstance().window.getShell(), "ImportComponent", "The components in the Local Repository can not import the same Local Repository.");
							return;
						}

						CompAssemManager.getInstance().backup(d, ProjectManager.getInstance().getSourceFolder());
						CompAssemManager.getInstance().getModelStore().clear();
						CompAssemManager.getInstance().getViewStore().clear();

						CompAssemManager.getInstance().getDirModel().clear();
						CompAssemManager.getInstance().getDirView().clear();
						CompAssemManager.getInstance().setTotal(true);
						ProgressMonitorDialog progress =  new ProgressMonitorDialog(ProjectManager.getInstance().window.getShell());
						//				LoadLibProgress lp = new LoadLibProgress(true);
						CompAssemManager.getInstance().setRunType(1);
						CompAssemManager.getInstance().setRtm(ProjectManager.getInstance().getModelBrowser().getRootLibTreeModel());
						try{
//							CompAssemManager.getInstance().setImport(true);
												CompAssemManager.getInstance().deleteModel2(ProjectManager.getInstance().getModelBrowser().getRootLibTreeModel());
							progress.run(true, true, CompAssemManager.getInstance());
						}
						catch(Exception e){
							e.printStackTrace();
						}
					}
					catch(Exception e){
						e.printStackTrace();
					}
					ProjectManager.getInstance().getModelBrowser().refresh(ProjectManager.getInstance().getModelBrowser().getRootLibTreeModel());
					CompAssemManager.getInstance().setImport(false);
					CompAssemManager.getInstance().setTotal(false);
					//				System.out.println();

				}
			};
			importComponent.setText("Import Component");//2008040301 PKY S 
			importComponent.setToolTipText("Action 1 tooltip");
			importComponent.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(316)));//PKY 08070101 S 팝업 메뉴 이미지 삽입

			deleteModel = new Action() {
				public void run () {
					try{
						TreeSelection iSelection = (TreeSelection)viewer.getSelection();
						Object[] array=iSelection.toArray();
						//PKY 08060201 S 모델 브라우져 여러 개 삭제되도록 수정
						boolean isRep = false;
						boolean isNode = false;
						boolean isNodePkg = false;
						boolean isAtomic = false;
						AtomicComponentModel atm = null;
						//						NodePackageTreeModel
						//111206 SDM S - 불필요한 LOG작성 삭제(주석처리)
						System.out.println("[begin] " + null);
//						ProjectManager.getInstance().writeLog("begin", null);
						//111206 SDM E - 불필요한 LOG작성 삭제(주석처리)
						NodeItemTreeModel nit = null;
						NodePackageTreeModel npt = null;
						for(int k=0; k<array.length;k++){                	
							if(array[k] instanceof UMLTreeModel){
								//PKY 08060201 E 모델 브라우져 여러 개 삭제되도록 수정
								UMLTreeModel treeObject = (UMLTreeModel)array[k];
								if(treeObject instanceof ServerRepositoryTreeModel){
									isRep = true;
								}
								else if(treeObject instanceof NodeItemTreeModel){
									isNode = true;
									nit = (NodeItemTreeModel)treeObject;
								}
								else if(treeObject instanceof NodePackageTreeModel){
									isNode = true;
									npt = (NodePackageTreeModel)treeObject;
								}
								else if(treeObject.getRefModel() instanceof AtomicComponentModel) {
									atm = (AtomicComponentModel)treeObject.getRefModel();
									if(atm!=null && atm.getCoreUMLTreeModel()==null){
										isAtomic = true;


										//										UMLTreeModel ut = this.getCoreUMLTreeModel();
										//										if(ut==null){

										//										AtomicComponentModel atm = (AtomicComponentModel)ut.getRefModel();
										ProjectManager.getInstance().getModelBrowser().searchCoreDiagramIdModel2(atm.getUMLDataModel().getId());
										java.util.ArrayList al = ProjectManager.getInstance().getSearchModel();
										System.out.println("dddd");
										for(int i = 0; i<al.size();i++){
											UMLModel um1 = (UMLModel)al.get(i);
											if(um1!=atm){
												if(um1 instanceof AtomicComponentModel){
													UMLTreeModel ut = um1.getUMLTreeModel();
													if(ut!=null){
														ProjectManager.getInstance().deleteUMLModel(ut);
														ProjectManager.getInstance().removeUMLNode(ut.getParent(),ut);
													}
													//								     					treeObject.addN3UMLModelDeleteListener((IN3UMLModelDeleteListener)um1);
													UMLElementModel um = um1.getAcceptParentModel();
													if(um instanceof N3EditorDiagramModel){
														((N3EditorDiagramModel) um).removeModel(um1);

													}
													//								     		            }
												}




											}
										}
										
//										String delFolder = atm.getCmpFolder();
//										File f = new File(delFolder);
//										if(f.isDirectory()){
//											f.delete();
//										}
										//										}
										IWorkspace workspace = ResourcesPlugin.getWorkspace();//워크스페이스 핸들
										IWorkspaceRoot root = workspace.getRoot();//워크스페이스 루트핸들
										final IProject newProjectHandle = root.getProject(atm.getName()); //프로젝트 핸들을 가져와본다.
										if(newProjectHandle!=null){
											IProgressMonitor monitor = new NullProgressMonitor();
//											newProjectHandle.delete(true, monitor);
											newProjectHandle.delete(true, true, monitor);
										}

										

									}

									//									npt = (NodePackageTreeModel)treeObject;
								}
								//2008041607PKY S
								if(treeObject instanceof PackageTreeModel){
									//PKY 08071601 S 다이얼로그 UI작업
									if(((PackageTreeModel)treeObject).getRefModel() instanceof FinalPackageModel){
										PackageTreeModel packageTreeModel=(PackageTreeModel)treeObject;
										MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_QUESTION|SWT.YES|SWT.NO);
										dialog.setText("Message");
										dialog.setMessage(packageTreeModel.getName()+" 삭제 하시겠습니까?");
										int i=dialog.open();

										switch(i) {
										case SWT.YES:
											NetManager.getInstance().retMsg = "";
											//111108 SDM S - 서버에 해당 어플리케이션이 있는지 체크 후 함께 삭제
											N3EditorDiagramModel appDiagram = null;
											UMLTreeModel utm[] = ((PackageTreeModel)treeObject).getChildren();
											for(int j=0; j<utm.length; j++){
												if(utm[j].getRefModel() instanceof N3EditorDiagramModel){
													appDiagram = (N3EditorDiagramModel)utm[j].getRefModel();
													break;
												}
											}
											
											if(appDiagram != null){
												ProgressMonitorDialog progress =  new ProgressMonitorDialog(ProjectManager.getInstance().window.getShell());
												
												NetManager.getInstance().setAppDiagram(appDiagram);
												NetManager.getInstance().setText("All");
												NetManager.getInstance().setNodeItemModel(null);
												NetManager.getInstance().setRunLoop(1);
												progress.run(true, true, NetManager.getInstance());
											}
											
											if(NetManager.getInstance().isChk){
												if(!((NetManager.getInstance().retMsg.equals("ok")) || (NetManager.getInstance().retMsg.equals("")))){
													MessageBox dialog1 = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.OK|SWT.ICON_ERROR);
													dialog1.setText("Message");
													dialog1.setMessage("Delete Error. ->" +  NetManager.getInstance().retMsg);
													dialog1.open();
													
													return;
												}
												
											}
											else{
												MessageBox dialog1 = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.OK|SWT.ICON_ERROR);
												dialog1.setText("Message");
												dialog1.setMessage("Delete Error. -> deploy fail");
												dialog1.open();
												return;
											}
											break;
											
										case IDialogConstants.FINISH_ID:
											System.out.println("Scrip Wizard Finish!!");
											break;
										case SWT.NO:    
											return;
										case SWT.CANCEL:
											break;
										}
									}
									
									//111223 SDM S - ROBOT-NODE 쪽 삭제시 엔진쪽도 삭제되도록 추가
									else if(treeObject instanceof AppTreeModel){
										AppTreeModel app = (AppTreeModel)treeObject;
										String appName = app.getName();
		
										MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_QUESTION|SWT.YES|SWT.NO);
										dialog.setText("Message");
										dialog.setMessage(appName +" 삭제 하시겠습니까?");
										int i=dialog.open();

										switch(i) {
										case SWT.YES:
											
											
											NetManager.getInstance().retMsg = "";
											//111108 SDM S - 서버에 해당 어플리케이션이 있는지 체크 후 함께 삭제
											
											
											if(appName != null){										
												NetManager.getInstance().setIp(app.ip);
												NetManager.getInstance().setPort(app.port);
												NetManager.getInstance().setAppName(appName);
												NetManager.getInstance().setNodeItemModel(null);
												
												boolean isTrue = NetManager.getInstance().connectNetwork();
												if(isTrue){
													NetManager.getInstance().executeCommand(4);
												}
												NetManager.getInstance().closeNetwork();
											}
											
											if(!((NetManager.getInstance().retMsg.equals("ok")) || (NetManager.getInstance().retMsg.equals("")))){
												MessageBox dialog1 = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.OK|SWT.ICON_ERROR);
												dialog1.setText("Message");
												dialog1.setMessage("Delete Error. ->" +  NetManager.getInstance().retMsg);
												dialog1.open();
													
												return;
											}
											break;
										
										case IDialogConstants.FINISH_ID:
											System.out.println("Scrip Wizard Finish!!");
											break;
										case SWT.NO:    
											return;
										case SWT.CANCEL:
											break;
										}
									}
									//111223 SDM E - ROBOT-NODE 쪽 삭제시 엔진쪽도 삭제되도록 추가
									
									//PKY 08071601 E 다이얼로그 UI작업
									PackageTreeModel packageTreeModel=(PackageTreeModel)treeObject;
									UMLTreeModel[] childList= packageTreeModel.getChildren();
									for(int j=0;j<childList.length;j++){
										if(childList[j] instanceof UMLTreeModel){
											ProjectManager.getInstance().deleteUMLModel(childList[j]);
											ProjectManager.getInstance().removeUMLNode(childList[j].getParent(), childList[j]);
										}
									}

								}
								//2008041607PKY E
								//PKY 08060201 S 모델브라우져에서 삭제할경우 라인이 삭제가 안되는 경우 발생 수정
								if (treeObject.getRefModel() instanceof ClassifierModel) {
									ClassifierModel model=(ClassifierModel)treeObject.getRefModel();
									for(int i=0; i<model.getChildren().size();i++){
										model.getChildren().get(i);
										if (model.getChildren().get(i) instanceof PortContainerModel) {
											PortContainerModel ipc = (PortContainerModel)model.getChildren().get(i);
											System.out.print(":");
										}
									}
								}

								if(treeObject.getRefModel()!=null)
									if(treeObject.getRefModel() instanceof UMLModel){
										UMLModel uMLModel = (UMLModel)treeObject.getRefModel();
										//								UMLDeleteCommand.deleteConnections(uMLModel);
										Vector s=uMLModel.getTargetConnections();
										for(int j=0; j<s.size();j++){
											if( s.get(j) instanceof LineModel){
												LineModel lineModel=(LineModel)s.get(j);
												for (int i = 0; i < s.size(); i++) {
													LineModel wire = (LineModel)s.get(i);
													wire.detachSource();
													wire.detachTarget();
												}
												for (int i = 0; i < s.size(); i++) {
													LineModel wire = (LineModel)s.get(i);
													wire.detachSource();
													wire.detachTarget();
												}
											}						
										}
										s=uMLModel.getSourceConnections();
										for(int j=0; j<s.size();j++){
											if( s.get(j) instanceof LineModel){
												LineModel lineModel=(LineModel)s.get(j);
												for (int i = 0; i < s.size(); i++) {
													LineModel wire = (LineModel)s.get(i);
													wire.detachSource();
													wire.detachTarget();
												}
												for (int i = 0; i < s.size(); i++) {
													LineModel wire = (LineModel)s.get(i);
													wire.detachSource();
													wire.detachTarget();
												}
											}						
										}
									}
								//PKY 08060201 E 모델브라우져에서 삭제할경우 라인이 삭제가 안되는 경우 발생 수정
								ProjectManager.getInstance().deleteUMLModel(treeObject);
                                try{
								ProjectManager.getInstance().removeUMLNode(treeObject.getParent(), treeObject);
                                }
                                catch(Exception e){
                                	e.printStackTrace();
                                }
								//PKY 08060201 S 모델 브라우져 여러 개 삭제되도록 수정
								if(treeObject.getRefModel()!=null
										&& treeObject.getRefModel() instanceof PortModel){
									PortModel pm = (PortModel)treeObject.getRefModel();

									ProjectManager.getInstance().getModelBrowser().searchComponentDiagramIdModel(pm.getUMLDataModel().getId());
									java.util.ArrayList sm = ProjectManager.getInstance().getSearchModel();
									//						    System.out.println("------>");
									for(int i=0;i<sm.size();i++){
										UMLModel um =(UMLModel)sm.get(i);
										if(um instanceof PortModel){
											N3EditorDiagramModel nm =um.getN3EditorDiagramModelTemp();
											if(nm!=null){
												PortModel pm1 =(PortModel)um;
												ComponentModel cm = (ComponentModel)pm1.getPortContainerModel();
												
												// SDM 110822 S - 아토믹 컴포넌트 포트 삭제정보를 저장
												if(cm instanceof AtomicComponentModel){
													AtomicComponentModel am = ((AtomicComponentModel) cm).getCoreDiagramCmpModel();
													am.setDeleteList(pm1.getName());
												}
												// SDM 110822 E - 아토믹 컴포넌트 포트 삭제정보를 저장
												
												cm.getPortManager().remove(pm1);
												nm.removeChild(um);

											}
										}
									}
								}

							}
						}

						if(isRep){
							RootServerRepositoryTreeMode rs = ((N3ViewContentProvider)viewer.getContentProvider()).getRs();

							if(rs!=null){
								FileWriter fw = null;
								PrintWriter bw=null;
								fw = new FileWriter(new File("c:\\ip.txt"));
								bw=new PrintWriter(fw);
								for(int i=0;i<rs.getChildren().length;i++){
									Object obj =  rs.getChildren()[i];
									ServerRepositoryTreeModel srtm = (ServerRepositoryTreeModel)obj;
									bw.write(srtm.getName()+"\n");
									fw.close();
									bw.close();
								}
							}
						}
						else if(isNode){
							if(nit!=null){
								NodeItemModel nm = (NodeItemModel)nit.getRefModel();
								java.util.ArrayList list = ProjectManager.getInstance().getMapCmp(nm.getUMLDataModel().getId());
								if(list!=null){
									for(int i=0;i<list.size();i++){
										ComponentModel cm = (ComponentModel)list.get(i);
										cm.setNodeItemModel(null);
									}
									list.clear();

								}

							}

						}
						else if(isNodePkg){
							if(npt!=null){
								//								npt = (NodeItemModel)npt.getRefModel();
								for(int i1=0;i1<npt.getChildren().length;i1++){
									NodeItemTreeModel ntm = (NodeItemTreeModel)npt.getChildren()[i1];
									NodeItemModel nm = (NodeItemModel)ntm.getRefModel();

									java.util.ArrayList list = ProjectManager.getInstance().getMapCmp(nm.getUMLDataModel().getId());
									if(list!=null){
										for(int i=0;i<list.size();i++){
											ComponentModel cm = (ComponentModel)list.get(i);
											cm.setNodeItemModel(null);
										}
										list.clear();

									}

								}
							}

						}
						//						else if(isAtomic){
						//
						////							UMLTreeModel ut = this.getCoreUMLTreeModel();
						////							if(ut==null){
						//							
						////							AtomicComponentModel atm = (AtomicComponentModel)ut.getRefModel();
						//							 ProjectManager.getInstance().getModelBrowser().searchComponentDiagramIdModel(atm.getClassModel().getUMLDataModel().getId());
						//					 		java.util.ArrayList al = ProjectManager.getInstance().getSearchModel();
						//					 		System.out.println("dddd");
						//					 		for(int i = 0; i<al.size();i++){
						//					 			UMLModel um1 = (UMLModel)al.get(i);
						//					 			if(um1!=atm){
						//					     				if(um1 instanceof AtomicComponentModel){
						////					     					um1.
						////					     					ProjectManager.getInstance().deleteUMLModel(childList[j]);
						////											ProjectManager.getInstance().removeUMLNode(childList[j].getParent(), childList[j]);
						////					     					 deleteConnections(um1);
						////					     		            detachFromGuides(um1);
						////					     		            index = parent.getChildren().indexOf(child);
						////					     		            try{
						////					     		            parent.removeChild(child);
						////					     		            }
						////					     		            catch(Exception e){
						////					     		            	e.printStackTrace();
						////					     		            }
						//					     				}
						//					     					
						//					     				
						//
						//					 				
						//					 			}
						//					 		}
						////							}
						//						
						//						}
						isChange = true;



					}
					catch(Exception e ){
						ProjectManager.getInstance().writeLog(null, e);
						e.printStackTrace();
					}
					//PKY 08060201 E 모델 브라우져 여러 개 삭제되도록 수정
					//					WJH 090820 S 
					RootTreeModel root = ProjectManager.getInstance().getModelBrowser().getRootTree();
					UMLTreeModel[] childs = root.getChildren();
					for(int i=0; i<childs.length; i++){
						UMLTreeModel model = childs[i];
						if(model != null && model.getName().equals("Application")){
							if(model instanceof PackageTreeModel){
								UMLTreeModel[] diagrams = ((PackageTreeModel)model).getChildren();
								for(int j=0; j<diagrams.length; j++){
									UMLTreeModel diagram = diagrams[j];
									if(diagram.getRefModel() instanceof N3EditorDiagramModel){
										N3EditorDiagramModel dia = (N3EditorDiagramModel)diagram.getRefModel();																				
										ArrayList coms = (ArrayList)dia.getChildren();
										for(int k=0; k<coms.size(); k++){
											UMLModel obj = (UMLModel)coms.get(k);	
											obj.refresh();											
										}
									}
								}
							}
						}
					}
					//					WJH 090820 E
				}
			};
			deleteModel.setText("Delete");//2008040301 PKY S 
			deleteModel.setToolTipText("Action 1 tooltip");
			deleteModel.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(310)));//PKY 08070101 S 팝업 메뉴 이미지 삽입


			//20110720서동민 - 컴포넌트 동기화
			syncModel = new Action() {
				public void run () {
					AtomicComponentModel am = null;
					try{
						IStructuredSelection sel = (IStructuredSelection)ProjectManager.getInstance().getModelBrowser().getViewer().getSelection();
						Object selectModel = sel.getFirstElement();
						
						allSync((UMLTreeModel)selectModel);	//110822 SDM 동기화 부분 따로 뺌.
					}
					catch(Exception e){
						
					}//		WJH 090820 E
				}
			};
			syncModel.setText("Synchronization");//2008040301 PKY S 
			syncModel.setToolTipText("Action 1 tooltip");
			syncModel.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(341)));



			repoDeleteModel = new Action() {
				public void run () {

					TreeSelection iSelection = (TreeSelection)viewer.getSelection();
					UMLTreeParentModel treeObject = (UMLTreeParentModel)iSelection.getFirstElement();

					NetManager.getInstance().deleteRepoComp(treeObject.getParent(), treeObject);

					//					WJH 090820 E
				}
			};
			repoDeleteModel.setText("Delete");//2008040301 PKY S 
			repoDeleteModel.setToolTipText("Action 1 tooltip");
			repoDeleteModel.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(310)));//PKY 08070101 S 팝업 메뉴 이미지 삽입










			reName = new Action() {
				public void run() {
					InputDialog inputDialog = new InputDialog(ProjectManager.getInstance().window.getShell(),
							"ddd", "ddd", "", null);
					inputDialog.open();
				}
			};
			reName.setText(N3Messages.POPUP_RENAME);
			reName.setToolTipText("Action 1 tooltip");
			reName.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
					getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
			doubleClickAction = new Action() {
				public void run() {
					TreeSelection iSelection = (TreeSelection)viewer.getSelection();
					if (iSelection != null) {
						TreeViewer treeViewer = viewer;
						Object object = iSelection.getFirstElement();
						if (object instanceof UMLTreeModel) {
							UMLTreeModel treeObject = (UMLTreeModel)object;
							Object object2 = treeObject.getRefModel();
							if (treeObject.getModelType() == 1) {
								UMLDiagramModel uMLDiagramModel = (UMLDiagramModel)treeObject.getRefModel();
								ProjectManager.getInstance().openDiagram(uMLDiagramModel);
							} //if(object instanceof UMLEditor)
						} //object instanceof UMLTreeModel
					}
				}
			};
			//2008040203 PKY S 추가 

			//UsecaseDiagram Add
			addUseCaseDiagram = new Action() {
				public void run() {
					//				showMessage("Action 1 executed");
					TreeSelection iSelection = (TreeSelection)viewer.getSelection();
					UMLTreeParentModel treeObject = (UMLTreeParentModel)iSelection.getFirstElement();
					NewDiagramDialog newDiagramDialog = new NewDiagramDialog(ProjectManager.getInstance().window.getShell());
					String name = treeObject.getName(); //PKY 08070301 S 객체 하위에서 생성되는 객체 이름 상위 객체 이름으로 생성되도록 수정
					//				newDiagramDialog.getShell().setSize(500, 500);


					name = autoNamming(treeObject, 8, "Use Case", true); //KDI 080908 0002
					ProjectManager.getInstance().addUMLDiagram(name, treeObject, null, 1, true,8);
				}
			};
			addUseCaseDiagram.setText(N3Messages.PALETTE_TITLE_USECASE);
			addUseCaseDiagram.setToolTipText("Action 1 tooltip");
			addUseCaseDiagram.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getDiagramImage(8)));//PKY 08070101 S 팝업 메뉴 이미지 삽입
			//addClassDiagram Add
			addClassDiagram = new Action() {
				public void run() {
					//				showMessage("Action 1 executed");
					TreeSelection iSelection = (TreeSelection)viewer.getSelection();
					UMLTreeParentModel treeObject = (UMLTreeParentModel)iSelection.getFirstElement();
					NewDiagramDialog newDiagramDialog = new NewDiagramDialog(ProjectManager.getInstance().window.getShell());
					String name = treeObject.getName(); //PKY 08070301 S 객체 하위에서 생성되는 객체 이름 상위 객체 이름으로 생성되도록 수정
					//				newDiagramDialog.getShell().setSize(500, 500);

					name = autoNamming(treeObject, 2, "Class", true); //KDI 080908 0002
					ProjectManager.getInstance().addUMLDiagram(name, treeObject, null, 1, true,2);
				}
			};
			addClassDiagram.setText(N3Messages.PALETTE_TITLE_CLASS);
			addClassDiagram.setToolTipText("Action 1 tooltip");
			addClassDiagram.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getDiagramImage(2)));//PKY 08070101 S 팝업 메뉴 이미지 삽입

			//addActivityDiagram Add
			addActivityDiagram = new Action() {
				public void run() {
					//				showMessage("Action 1 executed");
					TreeSelection iSelection = (TreeSelection)viewer.getSelection();
					UMLTreeParentModel treeObject = (UMLTreeParentModel)iSelection.getFirstElement();
					NewDiagramDialog newDiagramDialog = new NewDiagramDialog(ProjectManager.getInstance().window.getShell());
					String name = treeObject.getName(); //PKY 08070301 S 객체 하위에서 생성되는 객체 이름 상위 객체 이름으로 생성되도록 수정
					//				newDiagramDialog.getShell().setSize(500, 500);

					name = autoNamming(treeObject, 9, "Activity", true); //KDI 080908 0002
					ProjectManager.getInstance().addUMLDiagram(name, treeObject, null, 1, true,9);
				}
			};
			addActivityDiagram.setText(N3Messages.PALETTE_TITLE_ACTIVITY);
			addActivityDiagram.setToolTipText("Action 1 tooltip");
			String url =ProjectManager.getInstance().getDiagramPath(9);
			addActivityDiagram.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getDiagramImage(9)));//PKY 08070101 S 팝업 메뉴 이미지 삽입

			//addCompositeDiagram Add
			addCompositeDiagram = new Action() {
				public void run() {
					//				showMessage("Action 1 executed");
					TreeSelection iSelection = (TreeSelection)viewer.getSelection();
					UMLTreeParentModel treeObject = (UMLTreeParentModel)iSelection.getFirstElement();
					NewDiagramDialog newDiagramDialog = new NewDiagramDialog(ProjectManager.getInstance().window.getShell());
					String name = treeObject.getName(); //PKY 08070301 S 객체 하위에서 생성되는 객체 이름 상위 객체 이름으로 생성되도록 수정
					//				newDiagramDialog.getShell().setSize(500, 500);

					name = autoNamming(treeObject, 4, "Composite", true); //KDI 080908 0002
					ProjectManager.getInstance().addUMLDiagram(name, treeObject, null, 1, true,4);
				}
			};
			addCompositeDiagram.setText(N3Messages.PALETTE_TITLE_COMPOSITE);
			addCompositeDiagram.setToolTipText("Action 1 tooltip");
			addCompositeDiagram.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getDiagramImage(4)));//PKY 08070101 S 팝업 메뉴 이미지 삽입

			//addInteractionDiagram Add
			addInteractionDiagram = new Action() {
				public void run() {
					//				showMessage("Action 1 executed");
					TreeSelection iSelection = (TreeSelection)viewer.getSelection();
					UMLTreeParentModel treeObject = (UMLTreeParentModel)iSelection.getFirstElement();
					NewDiagramDialog newDiagramDialog = new NewDiagramDialog(ProjectManager.getInstance().window.getShell());
					String name = treeObject.getName(); //PKY 08070301 S 객체 하위에서 생성되는 객체 이름 상위 객체 이름으로 생성되도록 수정
					//				newDiagramDialog.getShell().setSize(500, 500);

					name = autoNamming(treeObject, 12, "Sequence", true); //KDI 080908 0002
					ProjectManager.getInstance().addUMLDiagram(name, treeObject, null, 1, true,12);
				}
			};
			addInteractionDiagram.setText(N3Messages.PALETTE_TITLE_INTERACTION);
			addInteractionDiagram.setToolTipText("Action 1 tooltip");
			addInteractionDiagram.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getDiagramImage(14)));//PKY 08070101 S 팝업 메뉴 이미지 삽입

			//addComponenteDiagram Add
			addComponenteDiagram = new Action() {
				public void run() {
					//				showMessage("Action 1 executed");
					try{
						TreeSelection iSelection = (TreeSelection)viewer.getSelection();
						UMLTreeParentModel treeObject = (UMLTreeParentModel)iSelection.getFirstElement();
						NewDiagramDialog newDiagramDialog = new NewDiagramDialog(null);
						String name = treeObject.getName(); //PKY 08070301 S 객체 하위에서 생성되는 객체 이름 상위 객체 이름으로 생성되도록 수정
						//				newDiagramDialog.getShell().setSize(500, 500);

						name = autoNamming(treeObject, 5, "Component", true); //KDI 080908 0002
						ProjectManager.getInstance().addUMLDiagram(name, treeObject, null, 1, true,5);
					}
					catch(Exception e1){
						e1.printStackTrace();
						System.out.println("application:"+application);
						try{
							FileWriter fw = new FileWriter(new File("c:\\err1.log"));
							PrintWriter bw=new PrintWriter(fw);

							e1.printStackTrace(bw);
							//						bw.write(e1.printStackTrace());
							bw.close();
							saveValue = -1;

						}
						catch(Exception e){
							e.printStackTrace();

						}
					}
				}
			};
			addComponenteDiagram.setText(N3Messages.PALETTE_TITLE_COMPONENT);
			addComponenteDiagram.setToolTipText("Action 1 tooltip");
			addComponenteDiagram.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getDiagramImage(5)));//PKY 08070101 S 팝업 메뉴 이미지 삽입

			//addComponenteDiagram Add
			addStateDiagram = new Action() {
				public void run() {
					//				showMessage("Action 1 executed");
					TreeSelection iSelection = (TreeSelection)viewer.getSelection();
					UMLTreeParentModel treeObject = (UMLTreeParentModel)iSelection.getFirstElement();
					NewDiagramDialog newDiagramDialog = new NewDiagramDialog(ProjectManager.getInstance().window.getShell());
					String name = treeObject.getName(); //PKY 08070301 S 객체 하위에서 생성되는 객체 이름 상위 객체 이름으로 생성되도록 수정
					//				newDiagramDialog.getShell().setSize(500, 500);

					name = autoNamming(treeObject, 10, "State", true); //KDI 080908 0002
					ProjectManager.getInstance().addUMLDiagram(name, treeObject, null, 1, true,10);
				}
			};
			addStateDiagram.setText(N3Messages.PALETTE_TITLE_STATE);
			addStateDiagram.setToolTipText("Action 1 tooltip");
			addStateDiagram.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getDiagramImage(10)));//PKY 08070101 S 팝업 메뉴 이미지 삽입

			//addDeploymenteDiagram Add
			addDeploymenteDiagram = new Action() {
				public void run() {
					//				showMessage("Action 1 executed");
					TreeSelection iSelection = (TreeSelection)viewer.getSelection();
					UMLTreeParentModel treeObject = (UMLTreeParentModel)iSelection.getFirstElement();
					NewDiagramDialog newDiagramDialog = new NewDiagramDialog(ProjectManager.getInstance().window.getShell());
					String name = treeObject.getName(); //PKY 08070301 S 객체 하위에서 생성되는 객체 이름 상위 객체 이름으로 생성되도록 수정
					//				newDiagramDialog.getShell().setSize(500, 500);

					name = autoNamming(treeObject, 6, "Deployment", true); //KDI 080908 0002
					ProjectManager.getInstance().addUMLDiagram(name, treeObject, null, 1, true,6);
				}
			};
			addDeploymenteDiagram.setText(N3Messages.PALETTE_TITLE_DEPLOYMENT);
			addDeploymenteDiagram.setToolTipText("Action 1 tooltip");
			addDeploymenteDiagram.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getDiagramImage(6)));//PKY 08070101 S 팝업 메뉴 이미지 삽입

			//addCommunicationDiagram Add
			addCommunicationDiagram = new Action() {
				public void run() {
					//				showMessage("Action 1 executed");
					TreeSelection iSelection = (TreeSelection)viewer.getSelection();
					UMLTreeParentModel treeObject = (UMLTreeParentModel)iSelection.getFirstElement();
					NewDiagramDialog newDiagramDialog = new NewDiagramDialog(ProjectManager.getInstance().window.getShell());
					String name = treeObject.getName(); //PKY 08070301 S 객체 하위에서 생성되는 객체 이름 상위 객체 이름으로 생성되도록 수정
					//				newDiagramDialog.getShell().setSize(500, 500);

					name = autoNamming(treeObject, 11, "Communication", true); //KDI 080908 0002
					ProjectManager.getInstance().addUMLDiagram(name, treeObject, null, 1, true,11);
				}
			};
			addCommunicationDiagram.setText(N3Messages.PALETTE_TITLE_COMMUNICATION);
			addCommunicationDiagram.setToolTipText("Action 1 tooltip");
			addCommunicationDiagram.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getDiagramImage(11)));//PKY 08070101 S 팝업 메뉴 이미지 삽입

			//addTimingDiagram Add
			addTimingDiagram = new Action() {
				public void run() {
					//				showMessage("Action 1 executed");
					TreeSelection iSelection = (TreeSelection)viewer.getSelection();
					UMLTreeParentModel treeObject = (UMLTreeParentModel)iSelection.getFirstElement();
					NewDiagramDialog newDiagramDialog = new NewDiagramDialog(ProjectManager.getInstance().window.getShell());
					String name = treeObject.getName(); //PKY 08070301 S 객체 하위에서 생성되는 객체 이름 상위 객체 이름으로 생성되도록 수정
					//				newDiagramDialog.getShell().setSize(500, 500);

					name = autoNamming(treeObject, 13, "Timing", true); //KDI 080908 0002
					ProjectManager.getInstance().addUMLDiagram(name, treeObject, null, 1, true,13);
				}
			};
			addTimingDiagram.setText(N3Messages.PALETTE_TITLE_TIMING);
			addTimingDiagram.setToolTipText("Action 1 tooltip");
			addTimingDiagram.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getDiagramImage(13)));//PKY 08070101 S 팝업 메뉴 이미지 삽입
			//2008040203 PKY E

			// V1.01 WJH E 080423 S 추가
			saveExcel = new Action() {
				public void run() {
					try{
						IWorkbenchWindow workbenchWindow = ProjectManager.getInstance().window;
						FileDialog fsd = new FileDialog(workbenchWindow.getShell(),SWT.SAVE);
						fsd.setFilterExtensions(new String[] {"*.xls","*.*"});
						fsd.setText("Select Excel Files...");
						fsd.open();

						String fileName = fsd.getFileName();
						String path = fsd.getFilterPath();

						//					ProjectManager.getInstance().setCurrentProjectName(fileName);
						//					ProjectManager.getInstance().setCurrentProjectPath(path);
						if(fileName.equals("")){
							return;
						}
						TreeSelection iSelection = (TreeSelection)viewer.getSelection();

						UMLTreeParentModel treeObject = (UMLTreeParentModel)iSelection.getFirstElement();
						System.out.println("Excel save");
						System.out.println("ddd211111");

						ExcelManager.getInstance().initCreateWorkbook(path+File.separator+fileName, 1);
						ExcelManager.getInstance().writeExcelObject(treeObject);
						ExcelManager.getInstance().closeWorkbook();
						//					ProjectManager.getInstance().initCreateWorkbook(path+File.separator+fileName, 1);
						//					ProjectManager.getInstance().writeExcelObject(treeObject);
						//					ProjectManager.getInstance().closeWorkbook();
						System.out.println("Excel End");
					}
					catch(Exception e){
						e.printStackTrace();
					}

				}
			};
			saveExcel.setText(N3Messages.POPUP_EXCEL_SAVE);//PKY 08070101 S 팝업 메뉴 이미지 삽입
			saveExcel.setToolTipText("Excel파일 저장");
			saveExcel.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(309)));//PKY 08070101 S 팝업 메뉴 이미지 삽입
			// V1.01 WJH E 080423 E 추가

			// V1.01 WJH E 080423 S 추가
			loadExcel = new Action() {
				public void run() {
					IWorkbenchWindow workbenchWindow = ProjectManager.getInstance().window;
					FileDialog fsd = new FileDialog(workbenchWindow.getShell(),SWT.OPEN);
					fsd.setFilterExtensions(new String[] {"*.xls","*.*"});
					fsd.setText("Select Excel Files...");
					fsd.open();

					String fileName = fsd.getFileName();
					String path = fsd.getFilterPath();
					if(fileName.equals("")){
						return;
					}
					//				ProjectManager.getInstance().setCurrentProjectName(fileName);
					//				ProjectManager.getInstance().setCurrentProjectPath(path);

					TreeSelection iSelection = (TreeSelection)viewer.getSelection();

					UMLTreeParentModel treeObject = (UMLTreeParentModel)iSelection.getFirstElement();
					System.out.println("Excel load");
					System.out.println("ddd211111");
					ExcelManager.getInstance().initGetWorkbook(path+File.separator+fileName);
					ExcelManager.getInstance().readExcelObject(treeObject);
					//				ProjectManager.getInstance().initGetWorkbook(path+File.separator+fileName);
					//				ProjectManager.getInstance().readExcelObject(treeObject);
					//				ProjectManager.getInstance().closeWorkbook();
					System.out.println("Excel End");

				}
			};
			loadExcel.setText(N3Messages.POPUP_EXCEL_LOAD);//PKY 08070101 S 팝업 메뉴 이미지 삽입
			loadExcel.setToolTipText("Excel파일 읽기");
			loadExcel.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(309)));//PKY 08070101 S 팝업 메뉴 이미지 삽입
			// V1.01 WJH E 080423 E 추가

			//		V1.02 WJH E 080904 S 요구사항 추적표 추가
			requiredExcel = new Action() {
				public void run() {
					IWorkbenchWindow workbenchWindow = ProjectManager.getInstance().window;
					FileDialog fsd = new FileDialog(workbenchWindow.getShell(),SWT.SAVE);
					fsd.setFilterExtensions(new String[] {"*.xls","*.*"});
					fsd.setText("Select Excel Files...");
					fsd.open();

					String fileName = fsd.getFileName();
					String path = fsd.getFilterPath();
					if(fileName.equals("")){
						return;
					}

					ExcelManager.getInstance().initCreateWorkbook(path+File.separator+fileName, 2);
					//				ProjectManager.getInstance().setCurrentProjectName(fileName);
					//				ProjectManager.getInstance().setCurrentProjectPath(path);

					TreeSelection iSelection = (TreeSelection)viewer.getSelection();

					UMLTreeParentModel treeObject = (UMLTreeParentModel)iSelection.getFirstElement();

					System.out.println("Required Excel create");				
					//				ExcelManager.getInstance().initGetWorkbook(path+File.separator+fileName);				
					ProjectManager.getInstance().setSearchModel(false);
					ProjectManager.getInstance().setisSearchDiagaramModel(false);//20080325 PKY S 검색				
					saveRequirement(treeObject);
					ExcelManager.getInstance().closeWorkbook();
					//				ProjectManager.getInstance().initGetWorkbook(path+File.separator+fileName);
					//				ProjectManager.getInstance().readExcelObject(treeObject);
					//				ProjectManager.getInstance().closeWorkbook();
					System.out.println("Excel End");

				}
			};
			requiredExcel.setText(N3Messages.POPUP_REQUIRED_EXCEL);//PKY 08070101 S 팝업 메뉴 이미지 삽입
			requiredExcel.setToolTipText("Required Excel파일 생성");
			requiredExcel.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(309)));//PKY 08070101 S 팝업 메뉴 이미지 삽입
			//		V1.02 WJH E 080904 E 요구사항 추적표 추가

			//20080508 KDI s        
			//		reportTestAction =  new ReportTest(ProjectManager.getInstance().window); //20080507 KDI s
			//		reportTestAction.setText("리포트 테스트");
			//		reportTestAction.setToolTipText("리포트 테스트");


			reportAction = new Action() {
				public void run() {
					//				showMessage("Action 1 executed");
					//				TreeSelection iSelection = (TreeSelection)viewer.getSelection();
					//				UMLTreeParentModel treeObject = (UMLTreeParentModel)iSelection.getFirstElement();
					////				ReportDialog rwTest = new ReportDialog(ProjectManager.getInstance().window.getShell());                
					////				rwTest.open();
					//				
					//				ReportMainDialog rw = new ReportMainDialog(ProjectManager.getInstance().window.getShell());                
					//				rw.open();
					//	              
					////				try{
					////				RptApplication ap = new RptApplication();
					////				ap.run(null);                    
					////				}
					////				catch(Exception e){
					////				e.printStackTrace();
					////				}
					//
					////				String name = reportGuideTest.getDiagramName();
					////				//				newDiagramDialog.getShell().setSize(500, 500);
					////				if (reportGuideTest.isOK)
					//////				ProjectManager.getInstance().addUMLDiagram(name, treeObject, null, 1, true,newDiagramDialog.getDiagramTYpe());
					////				System.out.println("리포트 생성 다이어그램에서 오케이...");
				}
			};
			reportAction.setText(N3Messages.REPORT_GENERATE_MENU);//2008040301 PKY S 
			reportAction.setToolTipText(N3Messages.REPORT_GENERATE_MENU);
			reportAction.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(327)));//PKY 08070101 S 팝업 메뉴 이미지 삽입
			//		reportTestAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
			//		getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));

			//		reportTestAction =  
			//		reportTestAction.setText("리포트 테스트");
			//		reportTestAction.setToolTipText("리포트 테스트");
			//20080508 KDI e


			writeJava = new Action(){
				public void run() {
					TreeSelection iSelection = (TreeSelection)viewer.getSelection();
					UMLTreeModel treeObject = (UMLTreeModel)iSelection.getFirstElement();
					treeObject.writeJava();

				}

			};
			writeJava.setText(N3Messages.POPUP_JAVA);//PKY 08070101 S 팝업 메뉴 이미지 삽입
			writeJava.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(308)));//PKY 08070101 S 팝업 메뉴 이미지 삽입
			writeH = new Action(){
				public void run() {
					TreeSelection iSelection = (TreeSelection)viewer.getSelection();
					UMLTreeModel treeObject = (UMLTreeModel)iSelection.getFirstElement();
					treeObject.writeH();

				}

			};
			writeH.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(314)));//PKY 08070101 S 팝업 메뉴 이미지 삽입
			writeH.setText("C++");//PKY 08070101 S 팝업 메뉴 이미지 삽입
		}
		catch(Exception e){
			e.printStackTrace();
		}

		addNodePackage = new Action() {
			public void run() {
				//ETRI
				try{
					//				showMessage("Action 1 executed");
					TreeSelection iSelection = (TreeSelection)viewer.getSelection();
					UMLTreeParentModel treeObject = (UMLTreeParentModel)iSelection.getFirstElement();
					//111125 SDM S - 로봇명 디폴트 값 추가
					UMLTreeModel utm[] = treeObject.getChildren();
					
					int count = 1;
					String strRobot = "";
					
					if(utm!=null){
						int i = 0;
						while(i < utm.length){
							i++;
							if(utm[i-1] instanceof NodePackageTreeModel){
								if(utm[i-1].getName().equals("robot" + count)){
									count++;
									i = 0;
								}
							}
						}
						strRobot = "robot" + count;
					}
					
					InputDialog inputDialog = new InputDialog(null,
							"Add NodePackage",  N3Messages.POPUP_NAME, strRobot, null);//PKY 08070101 S 영어로 변경
					//111125 SDM E - 로봇명 디폴트 값 추가
					
					inputDialog.open();
					int retCode = inputDialog.getReturnCode();
					if (retCode == 0) {
						String name = inputDialog.getValue();
						//KDI 080908 0002 s
						boolean isOverlapping = ProjectManager.getInstance().isOverlapping(treeObject, 0, name);
						if(!isOverlapping){
							NodePackageTreeModel np = new NodePackageTreeModel(name);
							NodePackageModel npm = new NodePackageModel();

							npm.setName(name);

							np.setRefModel(npm);
							treeObject.addChild(np);
							npm.setTreeModel(np);
							np.setParent(treeObject);

							refresh(treeObject);
							expend(treeObject);
							isChange = true;

						}
						//					if(isOverlapping) {
						//						MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_WARNING);
						//						dialog.setText("Message");
						//						dialog.setMessage(N3Messages.DIALOG_SAME_NAME_CONFLICT_MESSAGE);
						//						dialog.open();
						//						return ;
						//					}
						//					//KDI 080908 0002 e
						//					ProjectManager.getInstance().addUMLModel(name, treeObject, null, 0,-1);
					}



				}
				catch(Exception e1){
					System.out.println("application:"+application);
					try{
						FileWriter fw = new FileWriter(new File("c:\\err1.log"));
						PrintWriter bw=new PrintWriter(fw);

						e1.printStackTrace(bw);
						//						bw.write(e1.printStackTrace());
						bw.close();
						saveValue = -1;

					}
					catch(Exception e){
						e.printStackTrace();

					}
					e1.printStackTrace();
				}
			}
		};
		addNodePackage.setText("Add Robot");//2008040301 PKY S 
		addNodePackage.setToolTipText("Action 1 tooltip");
		addNodePackage.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(343)));//PKY 08070101 S 팝업 메뉴 이미지 삽입



		addNodeItem = new Action() {
			public void run() {
				//ETRI
				try{
					//				showMessage("Action 1 executed");
					TreeSelection iSelection = (TreeSelection)viewer.getSelection();
					UMLTreeParentModel treeObject = (UMLTreeParentModel)iSelection.getFirstElement();
					
					//111125 SDM S - 노드명 디폴트 값 추가
					UMLTreeModel utm[] = treeObject.getChildren();
					
					int count = 1;
					String strNode = "";
					
					if(utm!=null){
						int i = 0;
						while(i < utm.length){
							i++;
							if(utm[i-1] instanceof NodeItemTreeModel){
								if(utm[i-1].getName().equals("node" + count)){
									count++;
									i = 0;
								}
							}
						}
						strNode = "node" + count;
					}
					
					InputDialog inputDialog = new InputDialog(null,
							"Add Node",  N3Messages.POPUP_NAME, strNode, null);//PKY 08070101 S 영어로 변경
					//111125 SDM S - 노드명 디폴트 값 추가
					
					inputDialog.open();
					int retCode = inputDialog.getReturnCode();
					if (retCode == 0) {
						String name = inputDialog.getValue();
						//KDI 080908 0002 s
						boolean isOverlapping = ProjectManager.getInstance().isOverlapping(treeObject, 900, name);
						if(!isOverlapping){
							NodeItemTreeModel np = new NodeItemTreeModel(name);
							NodeItemModel nodeItemModel = new NodeItemModel();
							nodeItemModel.setNode(name);
							np.setRefModel(nodeItemModel);
							treeObject.addChild(np);
							nodeItemModel.setTreeModel((UMLTreeModel)np);
							//							nodeItemModel.setParentModel(treeObject.get)
							np.setParent(treeObject);
							refresh(treeObject);
							expend(treeObject);
							isChange = true;
							
//							org.eclipse.compare.

						}
						//					if(isOverlapping) {
						//						MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_WARNING);
						//						dialog.setText("Message");
						//						dialog.setMessage(N3Messages.DIALOG_SAME_NAME_CONFLICT_MESSAGE);
						//						dialog.open();
						//						return ;
						//					}
						//					//KDI 080908 0002 e
						//					ProjectManager.getInstance().addUMLModel(name, treeObject, null, 0,-1);
					}
				}
				catch(Exception e1){
					System.out.println("application:"+application);
					try{
						FileWriter fw = new FileWriter(new File("c:\\err1.log"));
						PrintWriter bw=new PrintWriter(fw);

						e1.printStackTrace(bw);
						//						bw.write(e1.printStackTrace());
						bw.close();
						saveValue = -1;

					}
					catch(Exception e){
						e.printStackTrace();

					}
					e1.printStackTrace();
				}
			}
		};
		addNodeItem.setText("Add Node");//2008040301 PKY S 
		addNodeItem.setToolTipText("Action 1 tooltip");
		//		addNodeItem.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(315)));//PKY 08070101 S 팝업 메뉴 이미지 삽입

		//		MenuManager hElementSubmenu = new MenuManager("C++");  
		//		manager.add(hElementSubmenu);	
		//		javaElementSubmenu.add(writeH);

		//ijs atomic
		newCompCreateAction = new Action() {
			public void run() {
				//				showMessage("Action 1 executed");
				try{
					//				showMessage("Action 1 executed");
					//					String pp = ProjectManager.getInstance().getProjectPath();
					//					if(pp==null || "".equals(pp)){
					//						MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_WARNING);
					//						dialog.setText("Message");
					//						dialog.setMessage("Project Folder is not Exist");
					//						dialog.open();
					//						return;
					//					}
					TreeSelection iSelection = (TreeSelection)viewer.getSelection();
					UMLTreeParentModel treeObject = (UMLTreeParentModel)iSelection.getFirstElement();
//					InputDialog inputDialog = new InputDialog(null,
//							"New OPRoS Component",  "Component Name", "", null);//PKY 08070101 S 영어로 변경
//					inputDialog.open();
//					int retCode = inputDialog.getReturnCode();
					
					CInputDialog cInputDialog = new CInputDialog(ProjectManager.getInstance().window.getShell());
					cInputDialog.open();
					int retCode = cInputDialog.getReturnCode();
					if (retCode == 0) {
						String name = cInputDialog.getFileName();
						//KDI 080908 0002 s
						boolean isOverlapping = ProjectManager.getInstance().isOverlapping(treeObject, 0, name);
						if(isOverlapping) {
							MessageBox dialog = new MessageBox(null,SWT.ICON_WARNING);
							dialog.setText("Message");
							dialog.setMessage(N3Messages.DIALOG_SAME_NAME_CONFLICT_MESSAGE);
							dialog.open();
							return ;
						}
						//KDI 080908 0002 e
						
						IWorkspace workspace = ResourcesPlugin.getWorkspace();//워크스페이스 핸들
						IWorkspaceRoot root = workspace.getRoot();//워크스페이스 루트핸들
						//		root.getRawLocation()

						final IProject newProjectHandle = root.getProject(name); //프로젝트 핸들을 가져와본다.
						if(newProjectHandle.exists()){
							MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_WARNING);
							dialog.setText("Message");
							dialog.setMessage("The same component in Project Explorer folder exists");
							dialog.open();
							return ;
						}
						
						AtomicComponentModel atom = new AtomicComponentModel();
						atom.lang = cInputDialog.getComboIndex();

						
						ProjectManager.getInstance().addUMLModel(name, treeObject, atom, 29,5);
					}
				}
				catch(Exception e){
					e.printStackTrace();
				}

			}
		};
		newCompCreateAction.setText("Create New OPRoS Component");//2008040301 PKY S 
		newCompCreateAction.setToolTipText("Create New OPRoS Component");
		newCompCreateAction.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(315)));//PKY 08070101 S 팝업 메뉴 이미지 삽입

		
		
		// 20110729서동민 컴포넌트 에디터 프로젝트 읽어오기
		allLoad = new Action() {
			//20110729서동민
			public void run() {
				//				showMessage("Action 1 executed");
				try{
					IStructuredSelection sel = (IStructuredSelection)ProjectManager.getInstance().getModelBrowser().getViewer().getSelection();
					Object selectModel = sel.getFirstElement();
						
					ProgressMonitorDialog progress =  new ProgressMonitorDialog(ProjectManager.getInstance().window.getShell());
					if(selectModel instanceof UMLTreeModel){
						if(selectModel instanceof RootCmpFnshTreeModel){
							List listTempCmpEdt = new ArrayList();
							listTempCmpEdt = ((RootCmpFnshTreeModel) selectModel).getChildrenList();
							
							Iterator <StrcuturedPackageTreeModel>  itCmpEdt = listTempCmpEdt.iterator();
							StrcuturedPackageTreeModel temp;
							
							File dirFile = new File("D:\\etri\\runtime-EclipseApplication(1)");
							String contents[] = dirFile.list();
							
							CompAssemManager.getInstance().setTotal(true);
							ProjectManager.getInstance().writeConfig();
							CompAssemManager.getInstance().getModelStore().clear();
							CompAssemManager.getInstance().getViewStore().clear();
							CompAssemManager.getInstance().getDirModel().clear();
							CompAssemManager.getInstance().getDirView().clear();
							
							CompAssemManager.getInstance().setRcefm(ProjectManager.getInstance().getModelBrowser().getRootCmpFnshTreeModel());
							CompAssemManager.getInstance().setRunType(7);
							
							try{
								CompAssemManager.getInstance().deleteModel2(ProjectManager.getInstance().getModelBrowser().getRootCmpFnshTreeModel());
								progress.run(true, true, CompAssemManager.getInstance());
							}
							catch(Exception e){
								e.printStackTrace();
							}
							
//							CompAssemManager.getInstance().setImport(false);
							CompAssemManager.getInstance().setTotal(false);
							CompAssemManager.getInstance().setRcefm(ProjectManager.getInstance().getModelBrowser().getRootCmpFnshTreeModel());
							ProjectManager.getInstance().getModelBrowser().refresh(ProjectManager.getInstance().getModelBrowser().getRootCmpFnshTreeModel());
							
							
							
							
							System.out.println("sadasdasd완료");
						}
					}
				}
				catch(Exception e){
					e.printStackTrace();
				}

			}
		};
		allLoad.setText("ComponentEdit Project Load");//2008040301 PKY S 
		allLoad.setToolTipText("ComponentEdit Project Load");
		allLoad.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(342)));//PKY 08070101 S 팝업 메뉴 이미지 삽입

		
		// 20110728서동민 전체 동기화
		atCompRefreshAction = new Action() {
		
			
			//20110712서동민
			public void run() {
				//				showMessage("Action 1 executed");
				try{
					IStructuredSelection sel = (IStructuredSelection)ProjectManager.getInstance().getModelBrowser().getViewer().getSelection();
					Object selectModel = sel.getFirstElement();
					
					ProgressMonitorDialog progress =  new ProgressMonitorDialog(ProjectManager.getInstance().window.getShell());
					if(selectModel instanceof UMLTreeModel){
						RootCmpEdtTreeModel rpt = (RootCmpEdtTreeModel)selectModel;
						List listTempCmpEdt = new ArrayList();
						listTempCmpEdt = ((RootCmpEdtTreeModel) selectModel).getChildrenList();

						Iterator <StrcuturedPackageTreeModel>  itCmpEdt = listTempCmpEdt.iterator();
						StrcuturedPackageTreeModel temp;
						
						while(itCmpEdt.hasNext()){
							temp = itCmpEdt.next();
							
							allSync(temp);	//110822 SDM 동기화 부분 따로 뺌.
						}
						
						System.out.println("sss");
						
					}
				}
				catch(Exception e){
					e.printStackTrace();
				}

			}
		};
		atCompRefreshAction.setText("All Synchronization");//2008040301 PKY S 
		atCompRefreshAction.setToolTipText("Refresh");
		atCompRefreshAction.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(341)));//PKY 08070101 S 팝업 메뉴 이미지 삽입
		// 20110805서동민 트리상에서 컴포넌트 에디터를 호출
		openCmpEdt = new Action() {
				public void run () {
					AtomicComponentModel am = null;
					try{
						IStructuredSelection 
						sel = (IStructuredSelection)ProjectManager.getInstance().getModelBrowser().getViewer().getSelection();
						Object selectModel = sel.getFirstElement();
						
						N3EditorDiagramModel uc= new N3EditorDiagramModel();
						UMLTreeModel obj = new UMLTreeModel(null);
						
						if(selectModel instanceof UMLTreeModel){
							if(selectModel instanceof UMLTreeParentModel){
								UMLTreeParentModel ut = (UMLTreeParentModel)selectModel;
								for(int i=0;i<ut.getChildren().length;i++){
									obj = (UMLTreeModel)ut.getChildren()[i];
									
									if(obj.getRefModel() instanceof N3EditorDiagramModel){
										uc = (N3EditorDiagramModel)obj.getRefModel();
										
										for(int i1=0;i1<uc.getChildren().size();i1++){
											Object obj1 = uc.getChildren().get(i1);
											if(obj1 instanceof AtomicComponentModel){
												am = (AtomicComponentModel)obj1;
											}
										}
									}
								}
								
								//
								//
								OpenComponentEditorAction.getInstance().OpenCmpEdt(am);
								
								
								
							}
						}
						
					}catch(Exception e){
						
					}

				}
		};
		openCmpEdt.setText("Open Component Edit");//2008040301 PKY S 
		openCmpEdt.setToolTipText("Open Component Edit");
		openCmpEdt.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(353)));//PKY 08070101 S 팝업 메뉴 이미지 삽입

		//110818 SDM S ExportComponent - 아직 사용안함 메뉴생성X
		exportComp = new Action() {
			public void run () {
				try{
					AtomicComponentModel am = null;
					
					IStructuredSelection sel = (IStructuredSelection)ProjectManager.getInstance().getModelBrowser().getViewer().getSelection();
					Object selectModel = sel.getFirstElement();	
					
					N3EditorDiagramModel uc= new N3EditorDiagramModel();
					UMLTreeModel obj = new UMLTreeModel(null);
					
					if(selectModel instanceof UMLTreeModel){
						if(selectModel instanceof UMLTreeParentModel){
							UMLTreeParentModel ut = (UMLTreeParentModel)selectModel;
							for(int i=0;i<ut.getChildren().length;i++){
								obj = (UMLTreeModel)ut.getChildren()[i];
								
								if(obj.getRefModel() instanceof N3EditorDiagramModel){
									uc = (N3EditorDiagramModel)obj.getRefModel();
									
									for(int i1=0;i1<uc.getChildren().size();i1++){
										Object obj1 = uc.getChildren().get(i1);
										if(obj1 instanceof AtomicComponentModel){
											am = (AtomicComponentModel)obj1;
										}
									}
								}
							}
							
							DirectoryDialog dd = new DirectoryDialog(ProjectManager.getInstance().window.getShell(),SWT.SINGLE);
							String d = am.getFile().getPath();
							
							System.out.println("ExportComponentFilePath---->" + d);
							
							if(d.equals(ProjectManager.getInstance().getSourceFolder())){
								MessageDialog.openWarning(ProjectManager.getInstance().window.getShell(), "ImportComponent", "The components in the Local Repository can not import the same Local Repository.");
								return;
							}

							CompAssemManager.getInstance().backup(d, ProjectManager.getInstance().getSourceFolder());
							CompAssemManager.getInstance().getModelStore().clear();
							CompAssemManager.getInstance().getViewStore().clear();

							CompAssemManager.getInstance().getDirModel().clear();
							CompAssemManager.getInstance().getDirView().clear();
							CompAssemManager.getInstance().setTotal(true);
							ProgressMonitorDialog progress =  new ProgressMonitorDialog(ProjectManager.getInstance().window.getShell());
							//				LoadLibProgress lp = new LoadLibProgress(true);
							CompAssemManager.getInstance().setRunType(1);
							CompAssemManager.getInstance().setRtm(ProjectManager.getInstance().getModelBrowser().getRootLibTreeModel());
							try{
//								CompAssemManager.getInstance().setImport(true);
													CompAssemManager.getInstance().deleteModel2(ProjectManager.getInstance().getModelBrowser().getRootLibTreeModel());
								progress.run(true, true, CompAssemManager.getInstance());
							}
							catch(Exception e){
								e.printStackTrace();
							}
							
						}
					}
				}
				catch(Exception e){
					e.printStackTrace();
				}
				ProjectManager.getInstance().getModelBrowser().refresh(ProjectManager.getInstance().getModelBrowser().getRootLibTreeModel());
				CompAssemManager.getInstance().setImport(false);
				CompAssemManager.getInstance().setTotal(false);
				//				System.out.println();

			}
	};
	exportComp.setText("ExportComponent");//2008040301 PKY S 
	exportComp.setToolTipText("");
	exportComp.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(353)));//PKY 08070101 S 팝업 메뉴 이미지 삽입
	//110818 SDM E ExportComponent
	
	
	}

	
	
	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(
				new IDoubleClickListener() {
					public void doubleClick(DoubleClickEvent event) {
						doubleClickAction.run();
					}
				});
	}


	private void hookSelectAction() {
		//PKY 08071601 S 다이얼로그 UI작업
		viewer.addSelectionChangedListener(
				new ISelectionChangedListener() {
					public void selectionChanged(SelectionChangedEvent event) {
						IStructuredSelection sel = (IStructuredSelection)event.getSelection();
						Object obj = sel.getFirstElement();
						menuInit();//PKY 08082201 S 팀 프로젝트 읽기전용일때 수정불가능하도록 작업
						if(obj  instanceof StrcuturedPackageTreeLibModel ){
							StrcuturedPackageTreeLibModel stl = (StrcuturedPackageTreeLibModel)obj;
							if(stl.getRefModel() instanceof ComponentModel){
								ComponentModel cm = (ComponentModel)stl.getRefModel();
								if(cm instanceof TemplateComponentModel){
									ProjectManager.getInstance().getModelBrowser().hookContextTemplateComponentModelMenu();
									return;
								}
								
								if(cm.isInstance()){
									ProjectManager.getInstance().getModelBrowser().hookContextInstanceMenu();
									return;
								}
							}

							if("<<composite>>".equals(stl.getStereo())){
								ProjectManager.getInstance().getModelBrowser().hookTreeModelAddComponentLib();
							}
							else{
								//								stl.getRefModel();
								ProjectManager.getInstance().getModelBrowser().hookContextMenu();
							}
						}
						else if(obj  instanceof RootTemplateTreeModel){
							ProjectManager.getInstance().getModelBrowser().hookContextLoadTemplateModelModelMenu();
						}
						else if(obj  instanceof RootAppPackageTreeModel){
							ProjectManager.getInstance().getModelBrowser().hookContextAddAppTemplateModelModelMenu();
						}
						else if(obj  instanceof RootTemplatePackageTreeModel){
							ProjectManager.getInstance().getModelBrowser().hookContextRootTemplatePackageTreeModelMenu();
						}
						else if(obj  instanceof RootCmpComposingTreeModel){
							ProjectManager.getInstance().getModelBrowser().hookContextRootCmpComposingTreeModelMenu();
						}
						
						
						else if(obj  instanceof RootLibTreeModel){
							ProjectManager.getInstance().getModelBrowser().hookRootPackageTreeModelContextMenu();
						}
						else if(obj  instanceof RootPackageTreeModel ){
							ProjectManager.getInstance().getModelBrowser().hookRootPackageTreeModelContextMenu2();
						}
						else if(obj  instanceof NodeRootTreeModel ){
							ProjectManager.getInstance().getModelBrowser().hookNodeRootTreeModelContextMenu();
						}
						else if(obj  instanceof NodePackageTreeModel ){
							ProjectManager.getInstance().getModelBrowser().hookNodePackageTreeModelContextMenu();						}
						else if(obj  instanceof RootServerRepositoryTreeMode ){
							ProjectManager.getInstance().getModelBrowser().hookRootServerRepositoryTreeModeContextMenu();						}
						else if(obj  instanceof ServerRepositoryTreeModel ){
							ProjectManager.getInstance().getModelBrowser().hookServerRepositoryTreeModelContextMenu();					}
						else if(obj  instanceof NodeItemTreeModel ){
							ProjectManager.getInstance().getModelBrowser().hookNodeItemTreeModelContextMenu();
						}
						else if(obj  instanceof MonitorRootTreeModel ){
							ProjectManager.getInstance().getModelBrowser().hookMonitorRootTreeModelContextMenu();
						}
						else if(obj  instanceof ServerMonitorTreeModel ){
							ProjectManager.getInstance().getModelBrowser().hookMonitorTreeModelContextMenu();
						}
						else if(obj  instanceof AppTreeModel ){
							ProjectManager.getInstance().getModelBrowser().hookAppTreeModelModelContextMenu();
							appTreeModel = (UMLTreeModel)obj;
						}
						else if(obj  instanceof MonitorCompTreeModel ){
							ProjectManager.getInstance().getModelBrowser().hookMonitorCompTreeModelContextMenu();
							appTreeModel = (UMLTreeModel)obj;
						}
						else if(obj  instanceof RepoComponentTreeModel ){
							ProjectManager.getInstance().getModelBrowser().hookREpoCompTreeModelContextMenu();
							appTreeModel = (UMLTreeModel)obj;
						}
						else if(obj  instanceof RootCmpEdtTreeModel ){

							ProjectManager.getInstance().getModelBrowser().hookRootCmpEdtTreeModelContextMenu();
							appTreeModel = (UMLTreeModel)obj;

						}
						else if(obj  instanceof RootCmpFnshTreeModel ){

							ProjectManager.getInstance().getModelBrowser().hookRootCmpFnshTreeModelContextMenu();
							appTreeModel = (UMLTreeModel)obj;

						}

						else if (obj instanceof RootTreeModel) {
							ProjectManager.getInstance().getModelBrowser().hookRootTreeModelContextMenu();
							//PKY 08070301 S 툴바 추가작업
							//							application.getAddDiagramAction().setEnabled(false);//PKY 08071601 S 다이얼로그 UI작업
							//							application.getAddPackage().setEnabled(true);
							//
							//							application.getAddDiagramAction().setEnabled(false);
							//							application.getAddModelAction().setEnabled(false);
							//							application.getWriteCAction().setEnabled(false);                        
							//							application.getWriteJavaAction().setEnabled(false);
							//							application.getOpenApplyPatternAction().setEnabled(false);
							//PKY 08070301 E 툴바 추가작업
						}


						else if (obj instanceof UMLTreeMainModel) {
							ProjectManager.getInstance().getModelBrowser().hookMainTreeModelContextMenu();
						}
						else if (obj instanceof PackageTreeModel) {
							PackageTreeModel packageTreeModel=(PackageTreeModel)obj;
							//PKY 08070301 S 툴바 추가작업
							//ETRI
							//							application.getAddDiagramAction().setEnabled(true);
							//							application.getAddPackage().setEnabled(true);
							//							application.getAddModelAction().setEnabled(true);
							//							application.getWriteCAction().setEnabled(true);                        
							//							application.getWriteJavaAction().setEnabled(true);
							//							application.getOpenApplyPatternAction().setEnabled(false);
							//PKY 08070301 E 툴바 추가작업
							//2008040203 PKY S 
							if(packageTreeModel.getModelType()==0){
								ProjectManager.getInstance().getModelBrowser().hookPackageTreeModelContextMenu();
								//PKY 08090101 S 모델 브라우져 패키지 Enable에러 문제
								Object selectModel = sel.getFirstElement();
								if(selectModel instanceof PackageTreeModel){
									PackageTreeModel packageUML = (PackageTreeModel)selectModel; 
									if(packageUML.getRefModel()!=null){
										FinalPackageModel finalPackageModel = (FinalPackageModel) packageUML.getRefModel();
										if(finalPackageModel.isReadOnlyModel()||!finalPackageModel.isExistModel()){
											readOnlyMenuEnable();
											loadLink.setEnabled(true);

										}
									}
								}

							}else if(packageTreeModel.getRefModel() instanceof AtomicComponentModel){//PKY 08060201 S 컴포넌트 하위에 클래스 다이어그램 생성되도록 추가
								isMerge = false ;
								AtomicComponentModel acm = (AtomicComponentModel)packageTreeModel.getRefModel();
								if(acm.getSrcFolder()!=null && !acm.getSrcFolder().trim().equals("")){
									String path = acm.getSrcFolder();
									System.out.println("path====>"+path);
									isMerge = true;
								}
								
								ProjectManager.getInstance().getModelBrowser().hookTreeModelAtomicComponent();
							}

							else if(packageTreeModel.getRefModel() instanceof ComponentModel){//PKY 08060201 S 컴포넌트 하위에 클래스 다이어그램 생성되도록 추가
								//								application.getAddDiagramAction().setEnabled(false);
								//								application.getAddPackage().setEnabled(false);
								//								application.getAddModelAction().setEnabled(false);
								//								application.getWriteCAction().setEnabled(true);                        
								//								application.getWriteJavaAction().setEnabled(true);
								//								application.getOpenApplyPatternAction().setEnabled(false);//PKY 08072401 S 다이어그램 선택시 패턴 Enable True안되는문제 수정
								//								application.getReverseCplusEngineerAction().setEnabled(false);
								//								application.getReverseJavaEngineerAction().setEnabled(false);
								ProjectManager.getInstance().getModelBrowser().hookTreeModelAddComponent();
							}
							//PKY 08060201 E
							else if(packageTreeModel.getRefModel() instanceof FinalActorModel||packageTreeModel.getRefModel() instanceof UseCaseModel
									|| packageTreeModel.getRefModel() instanceof CollaborationModel|| packageTreeModel.getRefModel() instanceof InterfaceModel
									|| packageTreeModel.getRefModel() instanceof EnumerationModel|| packageTreeModel.getRefModel() instanceof FinalClassModel
									|| packageTreeModel.getRefModel() instanceof ComponentModel){
								//PKY 08070301 S 툴바 추가작업
								//								application.getAddDiagramAction().setEnabled(false);
								//								application.getAddPackage().setEnabled(false);
								//								application.getAddModelAction().setEnabled(false);
								//								application.getWriteCAction().setEnabled(true);                        
								//								application.getWriteJavaAction().setEnabled(true);
								//								application.getOpenApplyPatternAction().setEnabled(false);//PKY 08072401 S 다이어그램 선택시 패턴 Enable True안되는문제 수정
								//								application.getReverseCplusEngineerAction().setEnabled(false);
								//								application.getReverseJavaEngineerAction().setEnabled(false);

								//PKY 08070301 E 툴바 추가작업
								if(packageTreeModel.getRefModel() instanceof UseCaseModel)
									ProjectManager.getInstance().getModelBrowser().hookTreeModelAddUseCase();
								else
									ProjectManager.getInstance().getModelBrowser().hookTreeModelAddActor();
								//PKY 08071101 E 모델 브라우져에서  Usecase UseCase다이어그램 추가 

							}else if(packageTreeModel.getRefModel() instanceof FinalActivityModel||packageTreeModel.getRefModel() instanceof FinalStrcuturedActivityModel){
								//PKY 08070301 S 툴바 추가작업
								//								application.getAddDiagramAction().setEnabled(false);
								//								application.getAddPackage().setEnabled(false);
								//								application.getAddModelAction().setEnabled(false);
								//								application.getWriteCAction().setEnabled(true);                        
								//								application.getWriteJavaAction().setEnabled(true);
								//								application.getOpenApplyPatternAction().setEnabled(false);//PKY 08072401 S 다이어그램 선택시 패턴 Enable True안되는문제 수정
								//								application.getReverseCplusEngineerAction().setEnabled(false);
								//								application.getReverseJavaEngineerAction().setEnabled(false);
								//PKY 08070301 E 툴바 추가작업
								ProjectManager.getInstance().getModelBrowser().hookTreeModelAddState();
							}
							//2008040203 PKY E 

						}
						else if (obj instanceof UMLTreeModel) {


							//PKY 08070301 S 툴바 추가작업
							//							application.getAddDiagramAction().setEnabled(false);
							//							application.getAddPackage().setEnabled(false);
							//							application.getAddModelAction().setEnabled(false);
							//							application.getWriteCAction().setEnabled(false);                        
							//							application.getWriteJavaAction().setEnabled(false);
							//							application.getOpenApplyPatternAction().setEnabled(false);//PKY 08072401 S 다이어그램 선택시 패턴 Enable True안되는문제 수정
							//							application.getReverseCplusEngineerAction().setEnabled(false);
							//							application.getReverseJavaEngineerAction().setEnabled(false);
							//PKY 08070301 E 툴바 추가작업
							//20080721IJS
							UMLTreeModel uMLTreeModel=(UMLTreeModel)obj;
							if(uMLTreeModel.getRefModel() instanceof PortModel){
								PortModel pm = (PortModel)uMLTreeModel.getRefModel();
								if(!(pm.getPortContainerModel() instanceof ComponentLibModel)){
									ProjectManager.getInstance().getModelBrowser().hookTreeModelPortModel();
								}
							}
							//20080721IJS
							//PKY 08082201 S 팀 프로젝트 읽기전용일때 수정불가능하도록 작업			
							//							if(uMLTreeModel.getRefModel() instanceof N3EditorDiagramModel){
							//								if(((N3EditorDiagramModel)uMLTreeModel.getRefModel()).isReadOnlyModel()||!((N3EditorDiagramModel)uMLTreeModel.getRefModel()).isExistModel()){
							//									application.getAddDiagramAction().setEnabled(false);
							//									application.getAddPackage().setEnabled(false);
							//									application.getAddModelAction().setEnabled(false);
							//									application.getWriteCAction().setEnabled(false);                        
							//									application.getWriteJavaAction().setEnabled(false);
							//									application.getOpenApplyPatternAction().setEnabled(false);
							//									
							//
							//								}else{
							//									application.getOpenApplyPatternAction().setEnabled(true);//PKY 08072401 S 다이어그램 선택시 패턴 Enable True안되는문제 수정
							//									application.getReverseCplusEngineerAction().setEnabled(true);
							//									application.getReverseJavaEngineerAction().setEnabled(true);
							//								}
							//
							//							}

							else
								ProjectManager.getInstance().getModelBrowser().hookTreeModelContextMenu();
						}
					}
				});
		//PKY 08071601 E 다이얼로그 UI작업

	}

	private void showMessage(String message) {
		MessageDialog.openInformation(viewer.getControl().getShell(), "Sample View4", message);
	}

	/** Passing the focus request to the viewer's control. */
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	public void refresh(UMLTreeModel p) {
		viewer.refresh(p);
		this.items = viewer.getTree().getItems();	// WJH 100326 S 종료 시 저장로직 추가
	}

	public void select(UMLTreeModel p) {
		ISelection sel = new StructuredSelection(p);
		viewer.setSelection(sel);


	}
	public java.util.ArrayList DiagramList(UMLTreeModel p) {
		ISelection sel = new StructuredSelection(p);
		java.util.ArrayList array = new java.util.ArrayList();
		viewer.setSelection(sel);
		StructuredSelection structuredSelection =(StructuredSelection)sel;	
		Object[] ob=structuredSelection.toArray();
		UMLTreeModel uMLTreeModel;
		for(int i=0;i<ob.length;i++){
			if(ob[i] instanceof UMLTreeModel){
				uMLTreeModel=(UMLTreeModel)ob[i];
				if(uMLTreeModel instanceof PackageTreeModel){
					PackageTreeModel packageTreeModel=(PackageTreeModel)uMLTreeModel;
					if(packageTreeModel.getChildren()!=null){
						UMLTreeModel[] umlTreeModel=packageTreeModel.getChildren();
						for(int j=0;j<umlTreeModel.length;j++){
							UMLTreeModel diagram=(UMLTreeModel)umlTreeModel[j];
							if(diagram.getRefModel() instanceof N3EditorDiagramModel)
								if(diagram.getRefModel()!=null){
									N3EditorDiagramModel n3EditorPackageDiagramModel =(N3EditorDiagramModel)diagram.getRefModel();
									array.add(n3EditorPackageDiagramModel);
								}
						}
					}

				}
			}


		}
		return array;

	}

	public void expend(UMLTreeModel p) {
		viewer.setExpandedState(p, true);
	}
	//ijs 080520
	public UMLTreeParentModel makeParentTree(String path){
		UMLTreeParentModel up= null;
		for(int j = 0;j<viewer.getTree().getItems().length;j++){
			TreeItem ut = viewer.getTree().getItems()[j];
			if(ut.getData() instanceof RootTreeModel){
				RootTreeModel rt = (RootTreeModel)ut.getData();
				up = rt.getSearchUMLTreeModel(path);
				if(up!=null){
					break;
				}
			}
		}
		return up;
	}
	//ijs 080520
	public UMLTreeModel makeTreeModel(ReverseFromJavaToModel rtm,UMLModel um,UMLTreeParentModel up,N3EditorDiagramModel n3EditorDiagramModel,int i){

		UMLTreeModel to1 = null;
		if(up!=null){
			//			UMLModel um = rtm.getModel();
			//합성클래스
			String name = um.getName();
			to1 = new StrcuturedPackageTreeModel(name);//2008040203 PKY S 


			//				CollaborationModel collaborationModel = new CollaborationModel();
			to1.setRefModel(um);
			um.setName(name);
			um.setTreeModel(to1);
			to1.setParent(up);
			up.addChild(to1);


			//			n3EditorDiagramModel.addChild(um);
			//			to1.addN3UMLModelDeleteListener(n3EditorDiagramModel);

			return up;
		}


		return null;


	}

	//20080721IJS
	public void searchReqIDModel(String id,String type){
		ProjectManager.getInstance().getSearchModel().clear();
		ProjectManager.getInstance().setSearchModel(true);
		for(int i = 0;i<viewer.getTree().getItems().length;i++){
			TreeItem ut = viewer.getTree().getItems()[i];
			if(ut.getData() instanceof RootTreeModel){
				RootTreeModel rt = (RootTreeModel)ut.getData();
				rt.searchReqIDModel(id, type);
				//				rt.searchModel(type, text, isCase, isWwo, isDesc);
			}
		}
		ProjectManager.getInstance().getConsole().setProjectSearch(ProjectManager.getInstance().getSearchModel());

	}
	//PKY 08080501 S RequirementID를 다이얼로그 리스트로 보여주도록 
	public void searchReqIDModel(){
		ProjectManager.getInstance().getReqIdList().clear();
		for(int i = 0;i<viewer.getTree().getItems().length;i++){
			TreeItem ut = viewer.getTree().getItems()[i];
			if(ut.getData() instanceof RootTreeModel){
				RootTreeModel rt = (RootTreeModel)ut.getData();
				rt.searchReqIDModel();
				//				rt.searchModel(type, text, isCase, isWwo, isDesc);
			}
		}
	}
	//PKY 08080501 S RequirementID를 다이얼로그 리스트로 보여주도록 
	public void searchModel(String type,String text,boolean isCase,boolean isWwo, boolean isDesc){
		ProjectManager.getInstance().getSearchModel().clear();
		for(int i = 0;i<viewer.getTree().getItems().length;i++){
			TreeItem ut = viewer.getTree().getItems()[i];
			if(ut.getData() instanceof RootTreeModel){
				RootTreeModel rt = (RootTreeModel)ut.getData();
				rt.searchModel(type, text, isCase, isWwo, isDesc);
			}
		}
		ProjectManager.getInstance().getConsole().setProjectSearch(ProjectManager.getInstance().getSearchModel());


	}

	public RootTreeModel getRootTree(){
		for(int i = 0;i<viewer.getTree().getItems().length;i++){
			TreeItem ut = viewer.getTree().getItems()[i];
			if(ut.getData() instanceof RootTreeModel){
				RootTreeModel rt = (RootTreeModel)ut.getData();
				return rt;
			}
		}
		return null;
	}

	public RootLibTreeModel getRootLibTreeModel(){
		for(int i = 0;i<viewer.getTree().getItems().length;i++){
			TreeItem ut = viewer.getTree().getItems()[i];
			if(ut.getData() instanceof RootTreeModel){
				RootTreeModel rt = (RootTreeModel)ut.getData();
				for(int j=0;j<rt.getChildren().length;j++){
					Object obj = rt.getChildren()[j];
					if(obj instanceof RootLibTreeModel){

						RootLibTreeModel rt1 = (RootLibTreeModel)obj;
						return rt1;
					}
				}
			}

		}
		return null;
	}
	
	//20110805서동민
	public RootCmpFnshTreeModel getRootCmpFnshTreeModel(){
		for(int i = 0;i<viewer.getTree().getItems().length;i++){
			TreeItem ut = viewer.getTree().getItems()[i];
			if(ut.getData() instanceof RootTreeModel){
				RootTreeModel rt = (RootTreeModel)ut.getData();
				for(int j=0;j<rt.getChildren().length;j++){
					Object obj = rt.getChildren()[j];
					if(obj instanceof RootCmpFnshTreeModel){

						RootCmpFnshTreeModel rt1 = (RootCmpFnshTreeModel)obj;
						return rt1;
					}
				}
			}

		}
		return null;
	}
	
	// IJS 2010-08-13 3:56오후 어플리케이션 익스포트  관련
	public RootCmpComposingTreeModel getRootCompComposeTreeModel(){

		for(int i = 0;i<viewer.getTree().getItems().length;i++){
			TreeItem ut = viewer.getTree().getItems()[i];
			if(ut.getData() instanceof RootTreeModel){
				RootTreeModel rt = (RootTreeModel)ut.getData();
				for(int j=0;j<rt.getChildren().length;j++){
					Object obj = rt.getChildren()[j];
					if(obj instanceof RootCmpComposingTreeModel){

						RootCmpComposingTreeModel rt1 = (RootCmpComposingTreeModel)obj;
						return rt1;
					}
				}
			}

		}
		return null;
	}
	
	
	public RootTemplateTreeModel getRootTemplateTreeModel(){

		for(int i = 0;i<viewer.getTree().getItems().length;i++){
			TreeItem ut = viewer.getTree().getItems()[i];
			if(ut.getData() instanceof RootTreeModel){
				RootTreeModel rt = (RootTreeModel)ut.getData();
				for(int j=0;j<rt.getChildren().length;j++){
					Object obj = rt.getChildren()[j];
					if(obj instanceof RootTemplateTreeModel){

						RootTemplateTreeModel rt1 = (RootTemplateTreeModel)obj;
						return rt1;
					}
				}
			}

		}
		return null;
	}

	public void intRootLibTreeModel(){
		for(int i = 0;i<viewer.getTree().getItems().length;i++){
			TreeItem ut = viewer.getTree().getItems()[i];
			if(ut.getData() instanceof RootLibTreeModel){
				RootLibTreeModel rt = (RootLibTreeModel)ut.getData();
				this.rootLib = rt;
			}
		}
		//		return null;
	}

	public void searchCoreDiagramIdModel2(String id){
		ProjectManager.getInstance().getSearchModel().clear();
		for(int i = 0;i<viewer.getTree().getItems().length;i++){
			TreeItem ut = viewer.getTree().getItems()[i];
			if(ut.getData() instanceof RootTreeModel){
				RootTreeModel rt = (RootTreeModel)ut.getData();
				rt.searchCoreDiagramIdModel2(id);
			}
		}
		//		ProjectManager.getInstance().getConsole().setProjectSearch(ProjectManager.getInstance().getSearchModel());


	}


	//	RootLibTreeModel

	public void searchCoreDiagramIdModel(String id){
		ProjectManager.getInstance().getSearchModel().clear();
		for(int i = 0;i<viewer.getTree().getItems().length;i++){
			TreeItem ut = viewer.getTree().getItems()[i];
			if(ut.getData() instanceof RootTreeModel){
				RootTreeModel rt = (RootTreeModel)ut.getData();
				rt.searchCoreDiagramIdModel(id);
			}
		}
		//		ProjectManager.getInstance().getConsole().setProjectSearch(ProjectManager.getInstance().getSearchModel());


	}

	public void searchComponentDiagramIdModel(String id){
		ProjectManager.getInstance().getSearchModel().clear();
		for(int i = 0;i<viewer.getTree().getItems().length;i++){
			TreeItem ut = viewer.getTree().getItems()[i];
			if(ut.getData() instanceof RootTreeModel){
				RootTreeModel rt = (RootTreeModel)ut.getData();
				rt.searchComponentDiagramIdModel(id);
			}
		}
		//		ProjectManager.getInstance().getConsole().setProjectSearch(ProjectManager.getInstance().getSearchModel());


	}

	public N3EditorDiagramModel searchSubComponentDiagram(UMLTreeParentModel ut){
		for(int i=0;i<ut.getChildren().length;i++){
			UMLTreeModel um =(UMLTreeModel)ut.getChildren()[i];
			if(um.getRefModel() instanceof N3EditorDiagramModel)
				return (N3EditorDiagramModel)um.getRefModel();
		}
		return null;
		//		ProjectManager.getInstance().getConsole().setProjectSearch(ProjectManager.getInstance().getSearchModel());


	}

	//20080811IJS
	public UMLTreeModel searchId(String id){
		try{	
			for(int i = 0;i<viewer.getTree().getItems().length;i++){
				TreeItem ut = viewer.getTree().getItems()[i];
				if(ut.getData() instanceof RootTreeModel){
					RootTreeModel rt = (RootTreeModel)ut.getData();


					return rt.searchId(id);
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
			if(root!=null)
				return this.root.searchId(id);

		}
		return null;



	}

	public RootTreeModel getRoot() {
		return root;
	}

	public void setRoot(RootTreeModel root) {
		this.root = root;
	}

	public void reflushRoot(RootTreeModel root){
		this.refresh(root);
	}

	public IViewSite getIvewSite() {
		return ivewSite;
	}

	public void setIvewSite(IViewSite ivewSite) {
		this.ivewSite = ivewSite;
		this.viewer.setInput(ivewSite);
	}
	//PKY 08082201 S 팀 프로젝트 읽기전용일때 수정불가능하도록 작업
	public void menuInit(){
		try{
			if(saveExcel!=null)
				saveExcel.setEnabled(true);
			if(loadExcel!=null)
				loadExcel.setEnabled(true);
			// V1.02 WJH E 080904 S 요구사항 추적표 추가
			if(requiredExcel != null)
				requiredExcel.setEnabled(true);
			// V1.02 WJH E 080904 E 요구사항 추적표 추가
			if(addPackage!=null)
				addPackage.setEnabled(true);
			if(addDiagram!=null)
				addDiagram.setEnabled(true);
			if(addModel!=null)
				addModel.setEnabled(true);
			if(addClassDiagram!=null)
				addClassDiagram.setEnabled(true);
			if(deleteModel!=null)
				deleteModel.setEnabled(true);
			if(addRefModel!=null)
				addRefModel.setEnabled(true);
			if(reName!=null)
				reName.setEnabled(true);
			if(findModelBrowser!=null)
				findModelBrowser.setEnabled(true);
			if(findReqModelBrowser!=null)
				findReqModelBrowser.setEnabled(true);
			if(doubleClickAction!=null)
				doubleClickAction.setEnabled(true);
			if(addUseCaseDiagram!=null)
				addUseCaseDiagram.setEnabled(true);
			if(addActivityDiagram!=null)
				addActivityDiagram.setEnabled(true);
			if(addCompositeDiagram!=null)
				addCompositeDiagram.setEnabled(true);
			if(addInteractionDiagram!=null)
				addInteractionDiagram.setEnabled(true);
			if(addComponenteDiagram!=null)
				addComponenteDiagram.setEnabled(true);
			if(addStateDiagram!=null)
				addStateDiagram.setEnabled(true);
			if(addDeploymenteDiagram!=null)
				addDeploymenteDiagram.setEnabled(true);
			if(addCommunicationDiagram!=null)
				addCommunicationDiagram.setEnabled(true);
			if(addTimingDiagram!=null)
				addTimingDiagram.setEnabled(true);
			if(saveLink!=null)
				saveLink.setEnabled(true);
			if(loadLink!=null)
				loadLink.setEnabled(true);
			if(addLink!=null)
				addLink.setEnabled(true);
			if(writeJava!=null)
				writeJava.setEnabled(true);
			if(writeH!=null)
				writeH.setEnabled(true);
			if(reportAction!=null)
				reportAction.setEnabled(true); 
			if(application!=null){
				//		application.getAddDiagramAction().setEnabled(true);
				//		application.getAddPackage().setEnabled(true);
				//		application.getAddModelAction().setEnabled(true);
				//		application.getWriteCAction().setEnabled(true);                        
				//		application.getWriteJavaAction().setEnabled(true);
				//		application.getOpenApplyPatternAction().setEnabled(true);
				//		application.getReverseCplusEngineerAction().setEnabled(true);
				//		application.getReverseJavaEngineerAction().setEnabled(true);
			}
		}
		catch(Exception e1){
			//ETRI
			e1.printStackTrace();

			System.out.println("application:"+application);
			try{
				FileWriter fw = new FileWriter(new File("c:\\err1.log"));
				PrintWriter bw=new PrintWriter(fw);

				e1.printStackTrace(bw);
				//				bw.write(e1.printStackTrace());
				bw.close();
				saveValue = -1;

			}
			catch(Exception e){
				e.printStackTrace();

			}


		}
	}

	public void readOnlyMenuEnable(){
		this.addUseCaseDiagram.setEnabled(false);
		this.addActivityDiagram.setEnabled(false);
		this.addCompositeDiagram.setEnabled(false);
		this.addInteractionDiagram.setEnabled(false);
		this.addComponenteDiagram.setEnabled(false);
		this.addStateDiagram.setEnabled(false);
		this.addDeploymenteDiagram.setEnabled(false);
		this.addCommunicationDiagram.setEnabled(false);
		this.addTimingDiagram.setEnabled(false);
		this.saveLink.setEnabled(false);
		this.loadLink.setEnabled(false);
		this.addLink.setEnabled(false);
		this.reportAction.setEnabled(false); 
		this.writeJava.setEnabled(false);
		this.writeH.setEnabled(false);
		this.saveExcel.setEnabled(false);
		this.loadExcel.setEnabled(false);
		this.requiredExcel.setEnabled(false);		// V1.02 WJH E 080904 요구사항 추적표 추가
		this.addPackage.setEnabled(false);
		this.addDiagram.setEnabled(false);
		this.addModel.setEnabled(false);
		this.addClassDiagram.setEnabled(false);
		this.deleteModel.setEnabled(false);
		this.addRefModel.setEnabled(false);
		this.reName.setEnabled(false);
		this.doubleClickAction.setEnabled(false);
		application.getAddDiagramAction().setEnabled(false);
		application.getAddPackage().setEnabled(false);
		application.getAddModelAction().setEnabled(false);
		application.getWriteCAction().setEnabled(false);                        
		application.getWriteJavaAction().setEnabled(false);
		application.getOpenApplyPatternAction().setEnabled(false);
		application.getReverseCplusEngineerAction().setEnabled(false);
		application.getReverseJavaEngineerAction().setEnabled(false);
	}
	//PKY 08082201 E 팀 프로젝트 읽기전용일때 수정불가능하도록 작업

	//	 V1.02 WJH 080908 S 요구사항 추적표 추가
	public void saveRequirement(UMLTreeModel utm){
		if(utm instanceof RootTreeModel){
			UMLTreeModel[] tree = ((RootTreeModel)utm).getChildren();
			for(int k=0; k<tree.length; k++){
				saveRequirement((UMLTreeModel)tree[k]);
			}
		}
		if(utm instanceof PackageTreeModel){
			PackageTreeModel ptm = (PackageTreeModel)utm;
			UMLTreeModel[] tree = ptm.getChildren();
			for(int i=0; i<tree.length; i++){
				UMLTreeModel utm1 = (UMLTreeModel)tree[i];
				UMLModel um = (UMLModel)utm1.getRefModel();

				//				if(um instanceof PackageTreeModel)
			}
		}		
	}
	//	 V1.02 WJH 080908 E 요구사항 추적표 추가

	//	public void run(IProgressMonitor monitor) throws InvocationTargetException,
	//	InterruptedException {
	//	try	{
	//	monitor.beginTask("Save" +
	//	" Project",
	//	true ? IProgressMonitor.UNKNOWN : 1000);
	//	while(this.saveValue>=0){
	//	if(monitor.isCanceled()){
	//	monitor.done();
	//	}
	//	Thread.sleep(1000);
	//	monitor.worked(100);

	//	}
	//	monitor.done();

	//	}
	//	catch(Exception e){
	//	e.printStackTrace();
	//	}


	//	}

	//KDI 080908 0002 s
	public String autoNamming(UMLTreeParentModel treeObject, int type, String name, boolean isDgm){
		int idx = 0;
		boolean isOverlapping = true;

		while(isOverlapping){
			isOverlapping = ProjectManager.getInstance().isOverlapping(treeObject, type, name, isDgm);
			if(isOverlapping){
				char[] b = name.toCharArray();
				String _tag = "";
				for(int i=0;i<b.length;i++){
					if((int)b[i]>=48 && (int)b[i]<=57){
						//						 idx = (int)b[i]; //주석처리하면 0번부터 들어감
						break;						 
					}
					else{
						_tag = _tag + b[i];						 
					}					 
				}
				name = _tag + idx;
				idx++;
			}			 
		}
		return name;
	}
	//KDI 080908 0002 e
	public RootLibTreeModel getRootLib() {
		return rootLib;
	}
	public void setRootLib(RootLibTreeModel rootLib) {
		this.rootLib = rootLib;
	}
	public N3ViewContentProvider getN3ViewContentProvider() {
		return n3ViewContentProvider;
	}
	public void setN3ViewContentProvider(N3ViewContentProvider viewContentProvider) {
		n3ViewContentProvider = viewContentProvider;
	}

	public void refreshLib(){
		ProjectManager.getInstance().writeConfig();
		CompAssemManager.getInstance().getModelStore().clear();
		CompAssemManager.getInstance().getViewStore().clear();
		CompAssemManager.getInstance().getDirModel().clear();
		CompAssemManager.getInstance().getDirView().clear();
		//		CompAssemManager.getInstance().makeLib(ProjectManager.getInstance().getModelBrowser().getRootLibTreeModel());
		//		ProjectManager.getInstance().getModelBrowser().refresh(ProjectManager.getInstance().getModelBrowser().getRootLibTreeModel());
		ProgressMonitorDialog progress =  new ProgressMonitorDialog(ProjectManager.getInstance().window.getShell());
		////		LoadLibProgress lp = new LoadLibProgress(true);
		CompAssemManager.getInstance().setRtm(ProjectManager.getInstance().getModelBrowser().getRootLibTreeModel());
		CompAssemManager.getInstance().setRunType(1);
		try{
			CompAssemManager.getInstance().deleteModel2(ProjectManager.getInstance().getModelBrowser().getRootLibTreeModel());
			progress.run(true, true, CompAssemManager.getInstance());
		}
		catch(Exception e){
			e.printStackTrace();
		}
		ProjectManager.getInstance().getModelBrowser().refresh(ProjectManager.getInstance().getModelBrowser().getRootLibTreeModel());
	}

	public NetIPPort getNetIPPort(String name){
		int i = name.indexOf(":");

		String ip = "";
		int port = -1;

		if(i>0){
			ip = name.substring(0,i);
			try{
				InetAddress addr = InetAddress.getByName(ip);
			}
			catch(Exception e){
				MessageDialog.openError(ProjectManager.getInstance().window.getShell(), "Error", "IP 의 값이 잘못되었습니다.");
				e.printStackTrace();
			}
		}
		else{
			MessageDialog.openError(ProjectManager.getInstance().window.getShell(), "Error", "Port 를 입력하여 주세요");

			return null;
		}


		String ports = name.substring(i+1);
		try{
			port = Integer.valueOf(ports);
		}
		catch(Exception e){
			MessageDialog.openError(ProjectManager.getInstance().window.getShell(), "Error", "Port 의 값이 잘못되었습니다.");
			e.printStackTrace();
			return null;
		} 
		NetIPPort netIPPort = new NetIPPort();
		netIPPort.ip = ip;
		netIPPort.port = port;
		return netIPPort;


	}

	// WJH 100326 S 종료 시 저장로직 추가
	public void doSaveAs(Shell shell) {
		try{
			//			ProgressMonitorDialog pd = new ProgressMonitorDialog(workbenchWindow.getShell());
			//			pd.run(true, true, (IRunnableWithProgress)ProjectManager.getInstance().getModelBrowser());

			FileDialog fsd = new FileDialog(shell,SWT.SAVE);
			fsd.setFilterExtensions(new String[] {"*.nmdl","*.*"});
			fsd.setText("Save Select Project Files...");
			fsd.open();
			//			LoadProgressRun lr =	new LoadProgressRun(true);
			String fileName = fsd.getFileName();
			String path = fsd.getFilterPath();

			//			V1.02 WJH E 080822 S 세이브 할때 이미 있는 파일을 선택하면 저장 여부를 묻도록 수정
			String fullPath = fsd.getFilterPath()+File.separator+fsd.getFileName();
			File tempFile = new File(fullPath);
			if(fileName.equals(""))
				return;
			if(tempFile.exists()){
				MessageBox dialog = new MessageBox(shell,SWT.ICON_QUESTION|SWT.YES|SWT.NO);
				dialog.setText("Message");
				dialog.setMessage(N3Messages.DIALOG_SAVE_MESSAGE);
				int i=dialog.open();
				switch(i) {
				case IDialogConstants.FINISH_ID:
					System.out.println("Scrip Wizard Finish!!");
					break;
				case SWT.NO:
					return;
				case SWT.CANCEL:
					break;
				}
			}
			//			V1.02 WJH E 080822 E 세이브 할때 이미 있는 파일을 선택하면 저장 여부를 묻도록 수정

			//PKY 08062601 S 저장할때 경로지정이 제대로 안될경우 에러발생 처리
			//PKY 08072401 S 콘솔 창 닫은상태에서 저장 불러오기 안되는 문제 수정
			if(!fileName.trim().equals("")){
				ProjectManager.getInstance().setCurrentProjectName(fileName);
				ProjectManager.getInstance().setCurrentProjectPath(path);				
				ProjectManager.getInstance().getModelBrowser().writeModel2(ProjectManager.getInstance().getCurrentProject());
				//				String title = ProjectManager.getInstance().getCurrentProjectName().replaceAll(".nmdl", "");

			}
			//PKY 08072401 E 콘솔 창 닫은상태에서 저장 불러오기 안되는 문제 수정
			//20080811IJS
			java.util.ArrayList list = TeamProjectManager.getInstance().getLinks();
			TeamProjectManager.getInstance().setLinkSave(true);
			for(int i=0;i<list.size();i++){
				LinkModel lm = (LinkModel)list.get(i);
				int k = lm.getUt().getIsLinkType();
				if(k==1){
					ProjectManager.getInstance().getModelBrowser().writeLinkModel(lm.getLinkPath(),lm.getUt());
				}
			}
			TeamProjectManager.getInstance().setLinkSave(false);

		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	// WJH 100326 E 종료 시 저장로직 추가
	
	public void addCmp(UMLTreeParentModel ump,String appName){
		
		
		
		
		for(int i=0;i<ump.getChildren().length;i++){
			UMLTreeModel ut = (UMLTreeModel)ump.getChildren()[i];
			Object obj = ut.getRefModel();
			if(obj instanceof TemplateComponentModel){
				TemplateComponentModel tcm = (TemplateComponentModel)obj;
				UMLTreeParentModel treeP = getRcet(); 
//				UMLTreeParentModel treeP = getRcet(); 
				UMLTreeParentModel treeModel1 = ProjectManager.getInstance().getModelBrowser().getRcet();
//				boolean isApp = false;
//				UMLTreeParentModel app = null;
//				for(int i1=0;i1<treeModel1.getChildren().length;i1++){
//					UMLTreeParentModel up =	(UMLTreeParentModel)treeModel1.getChildren()[i1];
//					if(up.getName().equals(appName)){
////						isApp = true;
//						app = up;
//						break;
//					}
//				}
//				
//				if(app==null){
//					FinalPackageModel finalPackageModel = new FinalPackageModel();
//					app = new PackageTreeModel(appName);
//					app.setRefModel(finalPackageModel);
//					finalPackageModel.setName(appName);
//					finalPackageModel.setTreeModel(app);
//					app.setParent(treeModel1);
//					treeModel1.addChild(app);
//				}
				ProjectManager.getInstance().addUMLModel(tcm.getName(), treeP, tcm, 1100,5);
			}
			else if(ut instanceof UMLTreeParentModel){
				this.addCmp((UMLTreeParentModel)ut,appName);
			}
		}
		return;
		
	}
	
	public IFile getWorkspaceFile(File file){		
		IWorkspace workspace= ResourcesPlugin.getWorkspace();
		
		IPath path = Path.fromOSString(file.getAbsolutePath());		
		IFile[] files= workspace.getRoot().findFilesForLocation(path);		
		//files= filterNonExistentFiles(files);		
		if (files == null || files.length == 0)			
			return null;	
		
		return files[0];	
	}

//	WJH 110803 S 추가
	public void allSync(UMLTreeModel selectModel){
		AtomicComponentModel am = null;
		try{
			N3EditorDiagramModel uc= new N3EditorDiagramModel();
			UMLTreeModel obj = new UMLTreeModel(null);
			
			if(selectModel instanceof UMLTreeModel){
				if(selectModel instanceof UMLTreeParentModel){
					UMLTreeParentModel ut = (UMLTreeParentModel)selectModel;
					for(int i=0;i<ut.getChildren().length;i++){
						obj = (UMLTreeModel)ut.getChildren()[i];
						
						if(obj.getRefModel() instanceof N3EditorDiagramModel){
							uc = (N3EditorDiagramModel)obj.getRefModel();
							
							for(int i1=0;i1<uc.getChildren().size();i1++){
								Object obj1 = uc.getChildren().get(i1);
								if(obj1 instanceof AtomicComponentModel){
									am = (AtomicComponentModel)obj1;
								}
							}
						}
					}
					
					//SDM 110822 소스 위치 이동
					//SDM 110817 포트 이름 변경 및 삭제된 사항을 받아 리스트구성
					List listReName = new ArrayList();
					List listDelete = new ArrayList();
					String strReName = new String();	//SDM 110822
					String strDelete = new String();	//SDM 110822
					
//					System.out.println("ReName---->" + OPRoSActivator.getPreferenceValue(OPRoSActivator.KEY_PORT_RENAME));
//					System.out.println("Delete---->" + OPRoSActivator.getPreferenceValue(OPRoSActivator.KEY_PORT_DELETE));
//					if(!ProjectManager.getInstance().mtest){
//					StringTokenizer stkReName = new StringTokenizer(OPRoSActivator.getPreferenceValue(OPRoSActivator.KEY_PORT_RENAME),"&&");
//					StringTokenizer stkDelete = new StringTokenizer(OPRoSActivator.getPreferenceValue(OPRoSActivator.KEY_PORT_DELETE),"&&");
//					
//					
//					
//					//포트이름변경사항 리스트 구성
//					while(stkReName.hasMoreTokens()){
//						   String str = stkReName.nextToken();
//						   listReName.add(str);
//					}
//					
//					//포트삭제사항 리스트 구성
//					while(stkDelete.hasMoreTokens()){
//						   String str = stkDelete.nextToken();
//						   listDelete.add(str);
//					}
//					
//					//110817 SDM 포트 변경 및 삭제 사항이 있는지 확인 후 있으면 적용
//					Iterator <String>  itReName = listReName.iterator();
//					 
//					while(itReName.hasNext()){
//						String str = itReName.next();
//						int index = str.indexOf(")");
//						String name = str.substring(1, index);
//						
//						if(name.equals(am.getName())){
//							//메소드..-->>이름변경
//							uc.setPortReName(am, str.substring(index+1));
//						}
//						else{	//110822 SDM 해당 안되는 변경사항은 다시 String으로 구성
//							strReName = strReName + "&&" + str;
//						}
//					}
//											
//					Iterator <String>  itDelete = listDelete.iterator();
//					while(itDelete.hasNext()){
//						String str = itDelete.next();
//						int index = str.indexOf(")");
//						String name = str.substring(1, index);
//						
//						if(name.equals(am.getName())){
//							//메소드..-->>삭제
//							uc.setPortDel(am, str.substring(index+1));
//						}
//						else{	//110822 SDM 해당 안되는 변경사항은 다시 String으로 구성
//							strDelete = strDelete + "&&" + str;
//						}
//					}
//					
//					OPRoSActivator.setPreferenceValue(OPRoSActivator.KEY_PORT_RENAME, strReName);
//					OPRoSActivator.setPreferenceValue(OPRoSActivator.KEY_PORT_DELETE, strDelete);
//					}
					//110817 SDM 끝
					//110822 SDM 끝
					
					File fPath = new File(am.getFile() +  File.separator + "profile", am.getName() +".xml");
					
					DocComponentModel doc = new DocComponentModel();
					doc.load(fPath.getPath());
					
					//20110809서동민 type 동기화를 위한 type xml파일 경로 생성
					File fType = new File(am.getFile() +  File.separator + "profile");
					
					
					
					System.out.println("ID>>>>"+am.getId());
					System.out.println("File>>>>"+am.getFile());
					System.out.println("Name>>>>"+am.getName());
					System.out.println("Folder>>>>"+fType);
					
					uc.setModels(doc, am, fType);
					
//					CompAssemManager.getInstance().setAtmc(am);
//					CompAssemManager.getInstance().setTotal(true);
//					
//					CompAssemManager.getInstance().setRunType(700);
//					am = (AtomicComponentModel)selectModel.();
					
				}
			}
			
		}catch(Exception e){
			
		}


		//					WJH 090820 E
	}

//	WJH 110803 E 추가
	
//	//110810서동민 추가
//	public void CmpEdtPjtLoad(RootCmpFnshTreeModel selectModel){
//		try{
//			ProgressMonitorDialog progress =  new ProgressMonitorDialog(ProjectManager.getInstance().window.getShell());
//			if(selectModel instanceof UMLTreeModel){
//				if(selectModel instanceof RootCmpFnshTreeModel){
//					List listTempCmpEdt = new ArrayList();
//					listTempCmpEdt = ((RootCmpFnshTreeModel) selectModel).getChildrenList();
//					
//					Iterator <StrcuturedPackageTreeModel>  itCmpEdt = listTempCmpEdt.iterator();
//					StrcuturedPackageTreeModel temp;
//					
//					File dirFile = new File("D:\\etri\\runtime-EclipseApplication(1)");
//					String contents[] = dirFile.list();
//					
//					CompAssemManager.getInstance().setTotal(true);
//					ProjectManager.getInstance().writeConfig();
//					CompAssemManager.getInstance().getModelStore().clear();
//					CompAssemManager.getInstance().getViewStore().clear();
//					CompAssemManager.getInstance().getDirModel().clear();
//					CompAssemManager.getInstance().getDirView().clear();
//					
//					CompAssemManager.getInstance().setRcefm(ProjectManager.getInstance().getModelBrowser().getRootCmpFnshTreeModel());
//					CompAssemManager.getInstance().setRunType(7);
//					
//					try{
//						CompAssemManager.getInstance().deleteModel2(ProjectManager.getInstance().getModelBrowser().getRootCmpFnshTreeModel());
//						progress.run(true, true, CompAssemManager.getInstance());
//					}
//					catch(Exception e){
//						e.printStackTrace();
//					}
//					
////					CompAssemManager.getInstance().setImport(false);
//					CompAssemManager.getInstance().setTotal(false);
//					CompAssemManager.getInstance().setRcefm(ProjectManager.getInstance().getModelBrowser().getRootCmpFnshTreeModel());
//					ProjectManager.getInstance().getModelBrowser().refresh(ProjectManager.getInstance().getModelBrowser().getRootCmpFnshTreeModel());
//					
//					
//					
//					
//					System.out.println("sadasdasd완료");
//				}
//			}
//		}
//		catch(Exception e){
//			e.printStackTrace();
//		}
//	}
}
