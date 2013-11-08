package kr.co.n3soft.n3com.projectmanager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import kr.co.n3soft.n3com.OPRoSStrings;
import kr.co.n3soft.n3com.UMLEditor;
import kr.co.n3soft.n3com.dnd.TreeSimpleFactory;
import kr.co.n3soft.n3com.edit.UMLEditPart;
import kr.co.n3soft.n3com.edit.UMLElementEditPart;
import kr.co.n3soft.n3com.edit.UMLTreeEditPart;
import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.activity.BehaviorActivityTableItem;
import kr.co.n3soft.n3com.model.activity.CentralBufferNodeModel;
import kr.co.n3soft.n3com.model.activity.ControlFlowLineModel;
import kr.co.n3soft.n3com.model.activity.DataStoreModel;
import kr.co.n3soft.n3com.model.activity.DecisionModel;
import kr.co.n3soft.n3com.model.activity.ExceptionModel;
import kr.co.n3soft.n3com.model.activity.FinalActiionModel;
import kr.co.n3soft.n3com.model.activity.FinalActivityModel;
import kr.co.n3soft.n3com.model.activity.FinalModel;
import kr.co.n3soft.n3com.model.activity.FinalObjectNodeModel;
import kr.co.n3soft.n3com.model.activity.FinalStrcuturedActivityModel;
import kr.co.n3soft.n3com.model.activity.FlowFinalModel;
import kr.co.n3soft.n3com.model.activity.HForkJoinModel;
import kr.co.n3soft.n3com.model.activity.HPartitionModel;
import kr.co.n3soft.n3com.model.activity.InitialModel;
import kr.co.n3soft.n3com.model.activity.ObjectFlowLineModel;
import kr.co.n3soft.n3com.model.activity.ReceiveModel;
import kr.co.n3soft.n3com.model.activity.SendModel;
import kr.co.n3soft.n3com.model.activity.SubPartitonModel;
import kr.co.n3soft.n3com.model.activity.SynchModel;
import kr.co.n3soft.n3com.model.activity.VForkJoinModel;
import kr.co.n3soft.n3com.model.activity.VPartitionModel;
import kr.co.n3soft.n3com.model.comm.AttributeElementModel;
import kr.co.n3soft.n3com.model.comm.ClassModel;
import kr.co.n3soft.n3com.model.comm.ClassifierModel;
import kr.co.n3soft.n3com.model.comm.ClassifierModelTextAttach;
import kr.co.n3soft.n3com.model.comm.DataInputPortModel;
import kr.co.n3soft.n3com.model.comm.DataOutputPortModel;
import kr.co.n3soft.n3com.model.comm.ElementLabelModel;
import kr.co.n3soft.n3com.model.comm.EventInputPortModel;
import kr.co.n3soft.n3com.model.comm.EventOutputPortModel;
import kr.co.n3soft.n3com.model.comm.FinalPackageModel;
import kr.co.n3soft.n3com.model.comm.FrameModel;
import kr.co.n3soft.n3com.model.comm.LineModel;
import kr.co.n3soft.n3com.model.comm.MethodInputPortModel;
import kr.co.n3soft.n3com.model.comm.MethodOutputPortModel;
import kr.co.n3soft.n3com.model.comm.NodeContainerModel;
import kr.co.n3soft.n3com.model.comm.OperationElementModel;
import kr.co.n3soft.n3com.model.comm.PortModel;
import kr.co.n3soft.n3com.model.comm.StrcuturedActivityPackageTreeModel;
import kr.co.n3soft.n3com.model.comm.StrcuturedPackageTreeModel;
import kr.co.n3soft.n3com.model.comm.UMLContainerModel;
import kr.co.n3soft.n3com.model.comm.UMLDataModel;
import kr.co.n3soft.n3com.model.comm.UMLElementModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.comm.UMLNoteModel;
import kr.co.n3soft.n3com.model.communication.MessageAssoicateLineModel;
import kr.co.n3soft.n3com.model.component.ArtfifactModel;
import kr.co.n3soft.n3com.model.component.AtomicComponentModel;
import kr.co.n3soft.n3com.model.component.ComponentModel;
import kr.co.n3soft.n3com.model.component.OPRoSDataTypeElementModel;
import kr.co.n3soft.n3com.model.component.OPRoSExeEnvironmentElementModel;
import kr.co.n3soft.n3com.model.component.OPRoSServiceTypeElementModel;
import kr.co.n3soft.n3com.model.component.OPRoSServiceTypesElementModel;
import kr.co.n3soft.n3com.model.component.SyncManager;
import kr.co.n3soft.n3com.model.component.TemplateComponentModel;
import kr.co.n3soft.n3com.model.composite.ConnectorLineModel;
import kr.co.n3soft.n3com.model.composite.DelegateLineModel;
import kr.co.n3soft.n3com.model.composite.OccurrenceLineModel;
import kr.co.n3soft.n3com.model.composite.PartModel;
import kr.co.n3soft.n3com.model.composite.ProvidedInterfaceLineModel;
import kr.co.n3soft.n3com.model.composite.RepresentsLineModel;
import kr.co.n3soft.n3com.model.composite.RequiredInterfaceLineModel;
import kr.co.n3soft.n3com.model.composite.RoleBindingLineModel;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;
import kr.co.n3soft.n3com.model.node.NodeItemModel;
import kr.co.n3soft.n3com.model.umlclass.CompartmentModel;
import kr.co.n3soft.n3com.model.umlclass.DependencyLineModel;
import kr.co.n3soft.n3com.model.umlclass.EnumerationModel;
import kr.co.n3soft.n3com.model.umlclass.FinalClassModel;
import kr.co.n3soft.n3com.model.umlclass.ImportLineModel;
import kr.co.n3soft.n3com.model.umlclass.InterfaceModel;
import kr.co.n3soft.n3com.model.umlclass.MergeLineModel;
import kr.co.n3soft.n3com.model.usecase.AssociateLineModel;
import kr.co.n3soft.n3com.model.usecase.CollaborationModel;
import kr.co.n3soft.n3com.model.usecase.ExtendLineModel;
import kr.co.n3soft.n3com.model.usecase.FinalActorModel;
import kr.co.n3soft.n3com.model.usecase.FinalBoundryModel;
import kr.co.n3soft.n3com.model.usecase.GeneralizeLineModel;
import kr.co.n3soft.n3com.model.usecase.IncludeLineModel;
import kr.co.n3soft.n3com.model.usecase.NoteLineModel;
import kr.co.n3soft.n3com.model.usecase.RealizeLineModel;
import kr.co.n3soft.n3com.model.usecase.UseCaseModel;
import kr.co.n3soft.n3com.parser.ModelManager;
import kr.co.n3soft.n3com.parser.TeamProjectManager;
import kr.co.n3soft.n3com.project.browser.ModelBrowser;
import kr.co.n3soft.n3com.project.browser.NodeRootTreeModel;
import kr.co.n3soft.n3com.project.browser.PackageTreeModel;
import kr.co.n3soft.n3com.project.browser.RootCmpEdtTreeModel;
import kr.co.n3soft.n3com.project.browser.RootTreeModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeParentModel;
import kr.co.n3soft.n3com.project.dialog.OPRoSDataPortDialogComposite;
import kr.co.n3soft.n3com.project.dialog.OPRoSDataTypeInputDialogComposite;
import kr.co.n3soft.n3com.project.dialog.OPRoSEventPortDialogComposite;
import kr.co.n3soft.n3com.project.dialog.OPRoSServicePortComposite;
import kr.co.n3soft.n3com.project.dialog.OPRoSServiceTypeInputDialogComposite;
import kr.co.n3soft.n3com.rcp.actions.OpenProjectPopAction;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.examples.shapes.LogicEditorInput;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.internal.WorkbenchWindow;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;

import uuu.Activator;
import uuu.ApplicationActionBarAdvisor;
import uuu.views.AttributeView;
import uuu.views.DescriptionView;
import uuu.views.OperationView;
import uuu.views.SampleView;

public class ProjectManager {
	MenuManager recentMenu;
	//20080908IJS
	public int Vvalue = 0;
	boolean isLoad = false;
	boolean isBlock = true;
	
	public static boolean mtest = false;
	
	
	public java.util.ArrayList cms = new java.util.ArrayList();
	
	public Thread thread = null;
	
	
	public Thread getThread() {
		return thread;
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}
	int lang = 1; 
	
	public int getLang() {
		return lang;
	}

	public void setLang(int lang) {
		this.lang = lang;
	}
	public boolean isLinux = false;
	
	public int dType = -1;

	public java.util.HashMap templateCmp = new java.util.HashMap();
	// WJH 090801 S
	//	WJH 090817 S
	private boolean isSizeChange = false;
	public boolean getSizeChange(){
		return isSizeChange;
	}

	public void setSizeChanage(boolean b){
		isSizeChange = b;
	}
	//	WJH 090817 E
	private boolean isComponent = false;

	public boolean isComponent() {
		return isComponent;
	}

	public void setComponent(boolean isComponent) {
		this.isComponent = isComponent;
	}
	private boolean isAlignAction = false; 

	public boolean isAlignAction() {
		return isAlignAction;
	}

	public void setAlignAction(boolean isAlignAction) {
		this.isAlignAction = isAlignAction;
	}
	//	WJH 090801 E
	public int copyNum = 0;
	static public int tempDiagramType = -1;
	public static int index = 0;
	private static ProjectManager instance;
	public Activator at = null;
	public IWorkbenchWindow window = null;
	private UMLElementEditPart uMLElementEditPart = null;
	private UMLEditor mUMLEditor = null;
	private UMLModel dragUMLModel = null;
	private UMLElementModel selectPropertyUMLElementModel = null;
	private UMLTreeModel dragUMLTreeModel = null;
	boolean isDoubleClick = false;
	boolean isDrag = false;
	private ModelBrowser modelBrowser = null;
	private UMLDiagramModel openDiagramModel = null;
	private UMLModel deleteModel = null;
	private Point dragPoint = null;
	private java.util.ArrayList selectLineModels = new java.util.ArrayList();
	public Point copyTarget = new Point();
	public Point copySource = new Point();
	private Point mouseSelectPoint = new Point();
	public HashMap tempCopyMap = new HashMap();
	private java.util.ArrayList behaviorActivityList = new java.util.ArrayList();
	private java.util.ArrayList modelList = new java.util.ArrayList();
	private UMLModel selectContainModel = null;
	private java.util.ArrayList childCopyList = new java.util.ArrayList();
	private UMLTreeModel copyTreeModel = null;
	private SampleView console;


	private boolean useBlock = false;		// WJH 100730 S 추가
	private OPRoSServiceTypeInputDialogComposite serviceTypeComp;	// WJH 100802 추가
	private OPRoSServicePortComposite contents;						// WJH 100802 추가

	private Document serviceTypeDoc;	// WJH 100802 추가
	private UMLTreeModel newMakeModel = null;			// V1.01 WJH E 080519 S 추가
	public N3EditorDiagramModel tempDiagram = null;	// V1.03 WJH E 080526 S 추가
	public N3EditorDiagramModel appDiagram = null;

	// WJH 100730 S 추가
	public void setUseBlock(boolean b){
		useBlock = b;
	}

	public boolean getUseBlock(){
		return useBlock;
	}
	// WJH 100730 E 추가


	public HashMap nodeMapCmps = new HashMap();


	public NodeRootTreeModel nodeRootTreeModel = null;

	public UMLModel selectedModel;

	public String projectPath = "";

	public String templatePath = "";


	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	public String getProjectPath() {
		return projectPath;
	}

	public void setProjectPath(String projectPath) {
		this.projectPath = projectPath;
	}

	public UMLModel getSelectedModel() {
		return selectedModel;
	}

	public void setSelectedModel(UMLModel selectedModel) {
		this.selectedModel = selectedModel;
	}

	public NodeRootTreeModel getNodeRootTreeModel() {
		return nodeRootTreeModel;
	}

	public void setNodeRootTreeModel(NodeRootTreeModel nodeRootTreeModel) {
		this.nodeRootTreeModel = nodeRootTreeModel;
	}

	public void mapNode(String id,ComponentModel cm){
		java.util.ArrayList cms = (java.util.ArrayList)nodeMapCmps.get(id);
		if(cms!=null){
			cms.add(cm);
		}
		else{
			java.util.ArrayList cms1 = new java.util.ArrayList();
			cms1.add(cm);
			nodeMapCmps.put(id, cms1);
		}

	}

	public java.util.ArrayList getMapCmp(String id){
		java.util.ArrayList cms = (java.util.ArrayList)nodeMapCmps.get(id);
		return cms;

	}

	public N3EditorDiagramModel getAppDiagram() {
		return appDiagram;
	}

	public void setAppDiagram(N3EditorDiagramModel appDiagram) {
		this.appDiagram = appDiagram;
	}

	private DescriptionView descriptionView; //20080725 KDI s

	private OperationView operationView;//PKY 08081801 S 오퍼레이션,어트리뷰트 창 추가

	private AttributeView attributeView;//PKY 08081801 S 오퍼레이션,어트리뷰트 창 추가


	private java.util.ArrayList searchText = new java.util.ArrayList();
	private java.util.ArrayList searchModel = new java.util.ArrayList();
	private java.util.ArrayList reqIdList = new java.util.ArrayList();//PKY 08080501 S RequirementID를 다이얼로그 리스트로 보여주도록
	private java.util.ArrayList actorImageList = new java.util.ArrayList();//PKY 08080501 S Image Dialog 이미지 리스트 및 불러오기 편하도록 개선
	private java.util.ArrayList typeModel = new java.util.ArrayList();
	private boolean addLine = true;
	private boolean isSearchModel = true;

	private boolean isSearchDiagaramModel = true; //20080325 PKY S 검색
	private java.util.ArrayList diagramsSub;  //2008040401PKY S "패키지 오른쪽 클릭 시 OpenDiagram 메뉴 추가    여러 다이어그램이있을경우 리스트 나오도록"
	public UMLDataModel uMLDataModel = null; //20080325 PKY S 검색
	public String oldName="";
	private  List copyList=null;
	private  List copyattList=null;//2008042401PKY S
	private  List copyoperList=null;//2008042401PKY S
	private boolean isOutlineAutoSize=false;//2008042401PKY S
	HashMap imageMap = new  HashMap();
	HashMap actorDialogPrevieweImgMap = new  HashMap();//PKY 08080501 S Image Dialog 이미지 리스트 및 불러오기 편하도록 개선
	//ijs080429 config
	private String currentProjectPath = "";
	private String currentProjectName = "";
	private java.util.ArrayList openProjects = new java.util.ArrayList();
	private String sourceFolder = "";
	private String teamProjectFolder = "";//PKY 08082201 S 팀 프로젝트 경로를 프로젝트에서 변경가능하도록

	IWorkbenchWindowConfigurer configurer;
	//ijs0507
	private java.util.HashMap reverseModelMap = new java.util.HashMap();
	private java.util.ArrayList reversePackage = new java.util.ArrayList();

	private ApplicationActionBarAdvisor application=null;//PKY 08070301 S 툴바 추가작업
	private Color defaultColor = new Color(null,   0,   0,   0); //PKY 08070901 S 모델 컬러 변경
	//	private java.util.ArrayList pkg = new java.util.ArrayList();
	//	private java.util.HashMap reverseModelMap = new java.util.HashMap();
	IViewSite ivewSite = null;
	private TreeViewer viewer;
	private java.util.ArrayList recentReportPath = new java.util.ArrayList(); //KDI 20080619

	private java.util.ArrayList frameModels = new java.util.ArrayList();//PKY 08091003 S

	public String defaultName = "";						// V1.02 WJH E 080822 추가

	RootTreeModel root;

	private HashMap reportWordTreeMap = new HashMap(); //20080812 KDI s

	private HashMap reportTempTreeBrowserMap = new HashMap(); //20080812 KDI s

	private int searchDialogModelType = -1; //20080813 KDI s

	private String reportOutPutPath = "";//20080902 KDI s

	private String reportTemplatePath = "";//20080903 KDI s

	//20080729IJS
	boolean isTreeMove = false;

	boolean isViewProperty = true;

	//20080908IJS
	boolean isMsgDrag  = false;

	String libPath;

	boolean isCopy = false;

	boolean isMainLoad = false;

	private String mPath = "";
	private String sPath = "";

	private String etc1Path = "";
	public String getEtc1Path() {
		return etc1Path;
	}

	public void setEtc1Path(String etc1Path) {
		this.etc1Path = etc1Path;
	}

	private String ect2Path = "";


	public String getEct2Path() {
		return ect2Path;
	}

	public void setEct2Path(String ect2Path) {
		this.ect2Path = ect2Path;
	}

	private String vsPath = "";

	public String getVsPath() {
		return vsPath;
	}

	public void setVsPath(String vsPath) {
		this.vsPath = vsPath;
	}

	public String getSPath() {
		//111125 SDM - 시뮬레이션 PATH 디폴트값 입력
		if(sPath == null || sPath.equals("")){
			this.setSPath("C:"+File.separator+"Program Files"+File.separator+"OPRoS"+File.separator+"OPRoS_Simulator"+File.separator+"KETI_Simulator.exe");
		}
		return sPath;
	}

	public void setSPath(String path) {
		sPath = path;
	}

	public String getMPath() {
		return mPath;
	}

	public void setMPath(String path) {
		mPath = path;
	}

	public static final String[] SCOPE_SET = new String[] {
		"public", "protected", "private"
	};

	public static final String[] SCOPEA_SET = new String[] {
		"+", "#", "-"
	};

	public static String[] TYPE_SET = new String[] {
		"void", "int", "boolean", "char", "double", "float", "long", "short", "String",""
	};

	public static String[] STATE_TYPE_SET = new String[] {
		"do", "entry", "exit"
	};
	// ijs 080520
	public void addReversePackage(PackageTreeModel ptm){
		this.reversePackage.add(ptm);
	}

	public java.util.ArrayList getReversePackage(){
		return this.reversePackage;
	}
	//ijs 080520
	public N3EditorDiagramModel makeDiagram(UMLTreeParentModel up){

		//		UMLTreeModel to1 =new UMLTreeModel(name);
		UMLTreeModel to1 =new UMLTreeModel("reverseDiagram");
		to1.setParent(up);

		if (to1 != null) {
			up.addChild(to1);
		}
		N3EditorDiagramModel n3EditorDiagramModel = new N3EditorDiagramModel();
		IEditorInput input = null;
		//PKY 08080501 S 다이어그램 전체 부분에서 "쓰임새도"라고 나오는 문제 수정
		input = new LogicEditorInput(new Path(N3Messages.PALETTE_TITLE_CLASS));
		//PKY 08080501 E 다이어그램 전체 부분에서 "쓰임새도"라고 나오는 문제 수정
		input = new LogicEditorInput(new Path("쓰임새도"));
		to1.setRefModel(n3EditorDiagramModel);
		n3EditorDiagramModel.setName("reverseDiagram");
		n3EditorDiagramModel.setTreeModel(to1);
		//		n3EditorDiagramModel.getUMLDataModel().setId(id);
		to1.setParent(up);
		n3EditorDiagramModel.setIEditorInput(input);
		n3EditorDiagramModel.setDiagramType(2);
		return n3EditorDiagramModel;

		//		for(int i=0;)
	}
	//ijs 080520 


	//ijs080429
	//PKY 08072401 S OpenList 개선
	public void removeOpenProject(String openProject){
		if(openProject.trim().equals("")){
			return;
		}
		for(int i=0;i<openProjects.size();i++){
			String op = (String)openProjects.get(i);
			if(op.equals(openProject)){
				openProjects.remove(i);
				i=0;
			}
		}
		if(recentMenu!=null){
			recentMenu.removeAll();
			Object obj=ProjectManager.getInstance().getOpenProjects();
			for(int i=0;i<ProjectManager.getInstance().getOpenProjects().size();i++){
				OpenProjectPopAction oppA = new OpenProjectPopAction(ProjectManager.getInstance().window);
				String str = (String)ProjectManager.getInstance().getOpenProjects().get(i);
				oppA.setText(str);
				oppA.setInit();
				recentMenu.add(oppA);
			}
		}
	}
	public void addOpenProject(String openProject){

		if(openProject.trim().equals("")){
			return;
		}
		boolean isExist = false;
		for(int i=0;i<openProjects.size();i++){
			String op = (String)openProjects.get(i);
			if(op.equals(openProject)){
				//PKY 08072401 S OpenList 개선
				openProjects.remove(i);
				i=0;
				//PKY 08072401 E OpenList 개선
			}
		}
		if(!isExist){
			this.openProjects.add(openProject);
		}
		if(openProjects.size()==6){
			openProjects.remove(0);
		}
		if(recentMenu!=null){
			recentMenu.removeAll();
			Object obj=ProjectManager.getInstance().getOpenProjects();
			for(int i=0;i<ProjectManager.getInstance().getOpenProjects().size();i++){
				OpenProjectPopAction oppA = new OpenProjectPopAction(ProjectManager.getInstance().window);
				String str = (String)ProjectManager.getInstance().getOpenProjects().get(i);
				oppA.setText(str);
				oppA.setInit();
				recentMenu.add(oppA);
			}
		}

	}
	//PKY 08072401 E OpenList 개선
	public void setDragPoint(Point p) {
		this.dragPoint = p;
	}

	public Point getDragPoint() {
		return this.dragPoint;
	}

	public void setCopyTreeModel(UMLTreeModel p) {
		this.copyTreeModel = p;
	}

	public UMLTreeModel getCopyTreeModel() {
		return this.copyTreeModel;
	}

	public void addchildCopyList(UMLModel p) {
		this.childCopyList.add(p);
	}

	public void initChildCopyList() {
		this.childCopyList.clear();
	}

	public java.util.ArrayList getChildCopyList() {
		return this.childCopyList;
	}

	public java.util.ArrayList getBehaviorActivityList() {
		return this.behaviorActivityList;
	}

	public void addSelectLineModel(LineModel p) {
		System.out.println("");

		if(this.isAddLine())
			selectLineModels.add(p);
	}

	public void removeSelectLineModel(LineModel p) {
		selectLineModels.remove(p);
	}

	public ArrayList getSelectLineModel() {
		return selectLineModels;
	}

	public void initSelectLineModel() {
		selectLineModels.clear();
	}

	public Dimension getDifference() {
		return this.copySource.getDifference(copyTarget);
	}

	public void setCopyTarget(Point p) {
		if (p == null)
			this.copyTarget = mouseSelectPoint;
		else
			this.copyTarget = p;
	}

	public void setTempCopyMap(String sourceId, Object cloneValue) {
		this.tempCopyMap.put(sourceId, cloneValue);
	}

	public Object getCloneValue(String sourceId) {
		return this.tempCopyMap.get(sourceId);
	}

	public void initTempCopyMap() {
		this.tempCopyMap.clear();
		//		this.selectContainModel = null;
	}

	public Point getCopyTarget() {
		return this.copyTarget;
	}

	public Point getCopySource() {
		return this.copySource;
	}

	public void setCopySource(Point p) {
		if (p == null)
			this.copySource = mouseSelectPoint;
		else
			this.copySource = p;
	}

	public void setMouseSelectPoint(Point p) {
		this.mouseSelectPoint = p;
	}

	public Point getMouseSelectPoint() {
		return this.mouseSelectPoint;
	}

	public void setDeleteModel(UMLModel uMLModel) {
		this.deleteModel = uMLModel;
	}

	public UMLModel getDeleteModel() {
		return this.deleteModel;
	}

	public void setOpenDiagramModel(UMLDiagramModel p) {
		this.openDiagramModel = p;
	}

	public UMLDiagramModel getOpenDiagramModel() {
		return this.openDiagramModel;
	}

	//	ScrollingGraphicalViewer viewer
	//tree
	//diagram
	//워크벤치윈도우
	public static ProjectManager getInstance() {
		if (instance == null) {
			instance = new ProjectManager();
			return instance;
		}
		else {
			return instance;
		}
	}

	public void init(){
		copyNum = 0;
		tempDiagramType = -1;
		index = 0;


		uMLElementEditPart = null;
		mUMLEditor = null;
		dragUMLModel = null;
		selectPropertyUMLElementModel = null;
		dragUMLTreeModel = null;
		isDoubleClick = false;
		isDrag = false;
		//  private ModelBrowser modelBrowser = null;
		openDiagramModel = null;
		deleteModel = null;
		dragPoint = null;
		selectLineModels = new java.util.ArrayList();
		copyTarget = new Point();
		copySource = new Point();
		mouseSelectPoint = new Point();
		tempCopyMap = new HashMap();
		behaviorActivityList = new java.util.ArrayList();
		modelList = new java.util.ArrayList();
		selectContainModel = null;
		childCopyList = new java.util.ArrayList();
		copyTreeModel = null;
		//		private SampleView console;


		searchText = new java.util.ArrayList();
		searchModel = new java.util.ArrayList();
		typeModel = new java.util.ArrayList();
		addLine = true;
		isSearchModel = true;

		oldName="";
		modelBrowser.init();
		RootTreeModel root  = modelBrowser.getRoot();
		//		ijs080429
		currentProjectPath = "";
		currentProjectName = "";
		//		this.configurer.setTitle("N3COM Constructor V3.0");
		this.nodeMapCmps.clear();


		ModelManager.getInstance().init();
		//20080811IJS
		TeamProjectManager.getInstance().init();




	}

	public void setModelBrowser(ModelBrowser p) {
		modelBrowser = p;
	}

	public ModelBrowser getModelBrowser() {
		return modelBrowser;
	}

	public void setConsole(SampleView p) {
		this.console = p;
	}

	public SampleView getConsole() {
		return console;
	}

	public UMLEditor getUMLEditor() {
		return this.mUMLEditor;
	}

	public void setUMLEditor(UMLEditor pMLEditor) {
		this.mUMLEditor = pMLEditor;
	}

	public void setIsDoubleClick(boolean p) {
		this.isDoubleClick = p;
	}

	public boolean getIsDoubleClick() {
		return this.isDoubleClick;
	}

	public void setUMLElementEditPart(UMLElementEditPart p) {
		this.uMLElementEditPart = p;
	}

	public UMLElementEditPart getUMLElementEditPart() {
		return this.uMLElementEditPart;
	}

	public void initIsDoubleClick() {
		this.isDoubleClick = false;
	}

	public void setDragUMLModel(UMLModel p) {
		this.dragUMLModel = p;
	}

	public void setDragUMLTreeModel(UMLTreeModel p) {
		this.dragUMLTreeModel = p;
	}

	public UMLModel getDragUMLModel() {
		if (isDrag) {
			return this.dragUMLModel;
		}
		else {
			return null;
		}
	}

	public UMLTreeModel getDragUMLTreeModel() {
		if (isDrag) {
			return this.dragUMLTreeModel;
		}
		else {
			return null;
		}
	}

	public boolean isDrag() {
		return this.isDrag;
	}

	public void InitDragUMLModel() {
		this.isDrag = true;
		TreeSelection umlTreeModels = (TreeSelection)this.getModelBrowser().getViewer().getSelection();
		UMLTreeModel uMLTreeModel = this.modelBrowser.getUMLTreeModelSelected();
		Object obj = uMLTreeModel.getRefModel();
		if (obj instanceof UMLModel)
			this.setDragUMLModel((UMLModel)obj);
		this.setDragUMLTreeModel(uMLTreeModel);
	}

	public void successDragUMLModel() {
		this.isDrag = false;
		this.setDragUMLModel(null);
		this.setDragUMLTreeModel(null);
	}


	//	public int getHeightFinalClass(ClassModel cm){
	//	int height = 0;
	//	int opCount = cm.getOperations().size();
	//	int attrCount = cm.getAttributes().size();
	//	int opHeight = opCount*17;
	//	int attrHeight = attrCount*17;
	//	if(opHeight<70){
	//	opHeight = 70;
	//	}

	//	if(attrHeight<55){
	//	attrHeight = 55;
	//	}

	//	String stero = cm.getStereotype();
	//	if(stero!=null && !stero.trim().equals("")){
	//	height = opHeight + attrHeight+30;
	//	}
	//	else{
	//	height = opHeight + attrHeight+15;
	//	}

	//	return height;
	//	}

	//	public int getHeightU(ClassModel cm){
	//	int height = 0;
	//	int opCount = cm.getOperations().size();
	//	int attrCount = cm.getAttributes().size();
	//	int opHeight = opCount*17;
	//	int attrHeight = attrCount*17;
	//	if(opHeight<70){
	//	opHeight = 70;
	//	}

	//	if(attrHeight<55){
	//	attrHeight = 55;
	//	}

	//	String stero = cm.getStereotype();
	//	if(stero!=null && !stero.trim().equals("")){
	//	height = opHeight + attrHeight+30;
	//	}
	//	else{
	//	height = opHeight + attrHeight+15;
	//	}

	//	return height;
	//	}
	public int  widthAutoSize(String s){
		int width=0;
		for(int j = 0; j < s.length(); j++){
			char c = s.charAt(j);
			if ( c  <  0xac00 || 0xd7a3 < c ){				
				if(s.substring(j, j+1).equals("A")){
					width=width+8;
				}else if(s.substring(j, j+1).equals("B")){
					width=width+8;
				}else if(s.substring(j, j+1).equals("C")){
					width=width+9;
				}else if(s.substring(j, j+1).equals("D")){
					width=width+8;
				}else if(s.substring(j, j+1).equals("E")){
					width=width+8;
				}else if(s.substring(j, j+1).equals("F")){
					width=width+8;
				}else if(s.substring(j, j+1).equals("G")){
					width=width+9;
				}else if(s.substring(j, j+1).equals("H")){
					width=width+8;
				}else if(s.substring(j, j+1).equals("I")){
					width=width+3;
				}else if(s.substring(j, j+1).equals("J")){
					width=width+6;
				}else if(s.substring(j, j+1).equals("K")){
					width=width+8;
				}else if(s.substring(j, j+1).equals("L")){
					width=width+7;
				}else if(s.substring(j, j+1).equals("M")){
					width=width+11;
				}else if(s.substring(j, j+1).equals("N")){
					width=width+9;
				}else if(s.substring(j, j+1).equals("O")){
					width=width+9;
				}else if(s.substring(j, j+1).equals("P")){
					width=width+8;
				}else if(s.substring(j, j+1).equals("Q")){
					width=width+9;
				}else if(s.substring(j, j+1).equals("R")){
					width=width+8;
				}else if(s.substring(j, j+1).equals("S")){
					width=width+8;
				}else if(s.substring(j, j+1).equals("T")){
					width=width+8;
				}else if(s.substring(j, j+1).equals("U")){
					width=width+8;
				}else if(s.substring(j, j+1).equals("V")){
					width=width+8;
				}else if(s.substring(j, j+1).equals("W")){
					width=width+10;
				}else if(s.substring(j, j+1).equals("X")){
					width=width+8;
				}else if(s.substring(j, j+1).equals("Y")){
					width=width+8;
				}else if(s.substring(j, j+1).equals("Z")){
					width=width+8;
				}else if(s.substring(j, j+1).equals("a")){
					width=width+7;
				}else if(s.substring(j, j+1).equals("b")){
					width=width+7;
				}else if(s.substring(j, j+1).equals("c")){
					width=width+7;
				}else if(s.substring(j, j+1).equals("d")){
					width=width+7;
				}else if(s.substring(j, j+1).equals("e")){
					width=width+7;
				}else if(s.substring(j, j+1).equals("f")){
					width=width+3;
				}else if(s.substring(j, j+1).equals("g")){
					width=width+7;
				}else if(s.substring(j, j+1).equals("h")){
					width=width+7;
				}else if(s.substring(j, j+1).equals("i")){
					width=width+3;
				}else if(s.substring(j, j+1).equals("j")){
					width=width+3;
				}else if(s.substring(j, j+1).equals("k")){
					width=width+6;
				}else if(s.substring(j, j+1).equals("l")){
					width=width+3;
				}else if(s.substring(j, j+1).equals("m")){
					width=width+11;
				}else if(s.substring(j, j+1).equals("n")){
					width=width+7;
				}else if(s.substring(j, j+1).equals("o")){
					width=width+7;
				}else if(s.substring(j, j+1).equals("p")){
					width=width+7;
				}else if(s.substring(j, j+1).equals("q")){
					width=width+7;
				}else if(s.substring(j, j+1).equals("r")){
					width=width+4;
				}else if(s.substring(j, j+1).equals("s")){
					width=width+7;
				}else if(s.substring(j, j+1).equals("t")){
					width=width+3;
				}else if(s.substring(j, j+1).equals("u")){
					width=width+7;
				}else if(s.substring(j, j+1).equals("v")){
					width=width+6;
				}else if(s.substring(j, j+1).equals("w")){
					width=width+10;
				}else if(s.substring(j, j+1).equals("x")){
					width=width+7;
				}else if(s.substring(j, j+1).equals("y")){
					width=width+7;
				}else if(s.substring(j, j+1).equals("z")){
					width=width+7;
				}else if(s.substring(j, j+1).equals(",")){
					width=width+6;//PKY 08071601 S State TransitionFlow 이름 넣은 후 저장 불러오기하면 타켓쪽으로 나오는 문제 수정
				}else if(s.substring(j, j+1).equals("(")){
					width=width+5;
				}else if(s.substring(j, j+1).equals(")")){
					width=width+5;
				}else if(s.substring(j, j+1).equals("+")){
					width=width+3;
				}else if(s.substring(j, j+1).equals("<")){//PKY 08082201 S RoleName 삭제시 NullPoint 메시지 뜨는문제
					width=width+10;
				}
				else if(s.substring(j, j+1).equals(">")){
					width=width+10;
				}//PKY 08082201 E RoleName 삭제시 NullPoint 메시지 뜨는문제

				else{
					width=width+8;
				}


			} else  
				width=width+12;  //한글이다..
		}

		return width;
	}
	//PKY 08082201 S Link모델 오토사이즈로 늘어나지 않는문제
	public void autoSize(ClassModel s){
		//PKY 08081101 S AutoSize 개선 
		UMLModel cm =null;
		if(selectPropertyUMLElementModel !=null&& selectPropertyUMLElementModel instanceof UMLModel ){
			cm = (UMLModel) selectPropertyUMLElementModel;
		}else{
			return;
		}

		int operationSize = s.getOperations().size();
		int attributeSize = s.getAttributes().size();
		int aftHSize = 15;
		int aftWSize = 30;
		String longText="";
		boolean steroType = false;



		//PKY 08081801 S 오퍼레이션 어트리뷰트 숨길수 있는 기능
		if(cm.getUMLDataModel().getElementProperty(UMLModel.ID_ATTR_HIDDEN)!=null){
			String attIs = (String) cm.getUMLDataModel().getElementProperty(UMLModel.ID_ATTR_HIDDEN);
			if(attIs.equals("1"))
				attributeSize=0;
		}

		if(cm.getUMLDataModel().getElementProperty(UMLModel.ID_OPER_HIDDEN)!=null){
			String operIs = (String) cm.getUMLDataModel().getElementProperty(UMLModel.ID_OPER_HIDDEN);
			if(operIs.equals("1"))
				operationSize=0;
		}
		//PKY 08081801 E 오퍼레이션 어트리뷰트 숨길수 있는 기능


		if(cm.getStereotype()!=null && !cm.getStereotype().equals("")){
			aftHSize = aftHSize + 15;
			steroType=true;
		}
		//PKY 08082201 S 팀 프로젝트 읽기전용일때 수정불가능하도록 작업
		if(cm instanceof UseCaseModel||cm instanceof CollaborationModel){
			aftHSize = aftHSize + 30;
		}
		//PKY 08082201 E 팀 프로젝트 읽기전용일때 수정불가능하도록 작업
		/**
		 *  어트리뷰트, 오퍼레이션 중 가장 글 길이가 긴것을 선정 
		 */
		//		for(int i = 0 ; i < cm.getOperations().size(); i++){
		//		if(((OperationEditableTableItem)cm.getOperations().get(i)).name.length() > longText.length()){
		//		longText = ((OperationEditableTableItem)cm.getOperations().get(i)).name;
		//		}
		//		}

		//		for(int i = 0 ; i < cm.getAttributes().size(); i++){
		//		if(((AttributeEditableTableItem)cm.getAttributes().get(i)).name.length() > longText.length()){
		//		longText = ((OperationEditableTableItem)cm.getOperations().get(i)).name;
		//		}
		//		}
		//		if(steroType)
		//		if(cm.getStereotype().length() > longText.length()){
		//		longText = cm.getStereotype();
		//		}
		//		if(cm.getName().length() > longText.length()){
		//		longText = cm.getName();
		//		}

		/**
		 * 너비 사이즈 구하기 오퍼레이션 어트리뷰트 중 어떤것이 긴지 조사 
		 */

		//		aftWSize = widthAutoSize(longText);




		if(attributeSize==0){
			attributeSize ++;
		}
		if(operationSize==0){
			operationSize ++;
		}

		if(cm instanceof FinalStrcuturedActivityModel)
			operationSize++;

		aftHSize = aftHSize + operationSize*17 + attributeSize*17;


		if(cm.getSize().height < aftHSize)
			cm.setSize(new Dimension(cm.getSize().width,aftHSize+4));


		//		if(cm.getParentModel().getSize().width < aftWSize)
		//		cm.getParentModel().setSize(new Dimension(aftWSize,cm.getParentModel().getSize().height));
		//PKY 08081101 E AutoSize 개선 

	}

	/*
	 * 다이어그램 열기
	 */

	public void openDiagram(UMLDiagramModel opendg) {
		ProjectManager.getInstance().setOpenDiagramModel(opendg);
		//		WJH 090809 S
		opendg.setGridEnabled(true);
		//		System.out.println("diagram bg color : "+opendg.getBackGroundColor());
		//		opendg.setBackGroundColor(ColorConstants.buttonDarkest);
		//		WJH 090809 E
		//				uMLEditor.get
		//				showMessage("Double-click detected on "+obj.toString());
		IWorkbenchWindow workbenchWindow = ProjectManager.getInstance().at.getWorkbench().getActiveWorkbenchWindow();
		//		IEditorInput input = null;
		//		input = new LogicEditorInput(new Path("쓰임새도"));
		try {
			//PKY 08082201 S 프로젝트 저장 불러온 경우 팔레트창이 자동으로 오픈되지 않는 문제
			if(opendg instanceof N3EditorDiagramModel){
				N3EditorDiagramModel nd = (N3EditorDiagramModel)opendg;
				tempDiagramType = nd.getDiagramType();
				//				N3Plugin.getDefault().open(nd.getDiagramType());
			}
			//PKY 08082201 E 프로젝트 저장 불러온 경우 팔레트창이 자동으로 오픈되지 않는 문제

			UMLEditor u = (UMLEditor)workbenchWindow.getActivePage().openEditor(opendg.getIEditorInput(), UMLEditor.ID);
			u.setTitleName(opendg.getName());			
			ProjectManager.getInstance().setOpenDiagramModel(null);
			//PKY 08082201 S 프로젝트 저장 불러온 경우 팔레트창이 자동으로 오픈되지 않는 문제
			//			if(opendg instanceof N3EditorDiagramModel){
			//				N3EditorDiagramModel nd = (N3EditorDiagramModel)opendg;
			//
			////				N3Plugin.getDefault().open(nd.getDiagramType());
			//			}
			//PKY 08082201 E 프로젝트 저장 불러온 경우 팔레트창이 자동으로 오픈되지 않는 문제

		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}

	public void addBehaviorActivityList(UMLTreeModel child) {
		if (child != null && child.getRefModel() != null) {
			UMLModel um = (UMLModel)child.getRefModel();
			if (child.getModelType() == 26 || child.getModelType() == 10) {
				BehaviorActivityTableItem bati = new BehaviorActivityTableItem("", um.getName(), "Activity", "");
				bati.setRef(um);
				this.behaviorActivityList.add(bati);
			}
		}
	}

	public void removeBehaviorActivityList(UMLTreeModel child) {
		if (child != null && child.getRefModel() != null) {
			UMLModel um = (UMLModel)child.getRefModel();
			if (child.getModelType() == 26 || child.getModelType() == 10) {
				for (int i = 0; i < this.behaviorActivityList.size(); i++) {
					BehaviorActivityTableItem bati = (BehaviorActivityTableItem)this.behaviorActivityList.get(i);
					if (um.getID().equals(bati.getRefId())) {
						this.behaviorActivityList.remove(i);
					}
				}
				//          		BehaviorActivityTableItem bati = new BehaviorActivityTableItem("",um.getName(),"Activity","");
				//          		bati.setRefId(um.getID());
			}
		}
	}

	/*
	 * 모델 삭제
	 */

	public void deleteUMLModel(UMLTreeModel child) {
		this.removeBehaviorActivityList(child);
		if (child instanceof PackageTreeModel) {
			PackageTreeModel ptm = (PackageTreeModel)child;
			for (int i = 0; i < ptm.getChildren().length; i++) {
				Object obj = ptm.getChildren() [i];
				if (obj instanceof PackageTreeModel) {
					this.deleteUMLModel((UMLTreeModel)obj);
				}
				UMLTreeModel ptm1 = (UMLTreeModel)obj;
				if (ptm1.getRefModel() instanceof N3EditorDiagramModel) {
					if (ptm1 != null) {
						IWorkbenchWindow workbenchWindow = ProjectManager.getInstance().window;
						IEditorInput input = null;
						//						input = new LogicEditorInput(new Path("쓰임새도"));
						N3EditorDiagramModel n3EditorDiagramModel = (N3EditorDiagramModel)ptm1.getRefModel();
						workbenchWindow.getActivePage().closeEditor(n3EditorDiagramModel.getUMLEditor(), false);
					}
				}
				else
					ptm1.fireChildRemoved();
				//				this.removeUMLNode(ptm1.getParent(), ptm1);
			}
		}
		if (child.getRefModel() instanceof N3EditorDiagramModel) {
			if (child != null) {
				IWorkbenchWindow workbenchWindow = ProjectManager.getInstance().window;
				IEditorInput input = null;
				//				input = new LogicEditorInput(new Path("쓰임새도"));
				N3EditorDiagramModel n3EditorDiagramModel = (N3EditorDiagramModel)child.getRefModel();
				Object obj = child.getParent().getRefModel();
				if (obj instanceof FinalPackageModel) {
					FinalPackageModel finalPackageModel = (FinalPackageModel)obj;
					finalPackageModel.getUMLDataModel().setN3EditorDiagramModel(null);
				}
				workbenchWindow.getActivePage().closeEditor(n3EditorDiagramModel.getUMLEditor(), false);
			}
		}
		else
			child.fireChildRemoved();
		return;
	}

	public void deleteUMLModel2(UMLTreeModel child) {
		this.removeBehaviorActivityList(child);
		if (child instanceof PackageTreeModel) {
			PackageTreeModel ptm = (PackageTreeModel)child;
			for (int i = 0; i < ptm.getChildren().length; i++) {
				Object obj = ptm.getChildren() [i];
				if (obj instanceof PackageTreeModel) {
					this.deleteUMLModel2((UMLTreeModel)obj);
				}
				UMLTreeModel ptm1 = (UMLTreeModel)obj;
				if (ptm1.getRefModel() instanceof N3EditorDiagramModel) {
					if (ptm1 != null) {
						IWorkbenchWindow workbenchWindow = ProjectManager.getInstance().window;
						IEditorInput input = null;
						//						input = new LogicEditorInput(new Path("쓰임새도"));
						N3EditorDiagramModel n3EditorDiagramModel = (N3EditorDiagramModel)ptm1.getRefModel();
						workbenchWindow.getActivePage().closeEditor(n3EditorDiagramModel.getUMLEditor(), false);
					}
				}
				//				else
				//					ptm1.fireChildRemoved();
				//				this.removeUMLNode(ptm1.getParent(), ptm1);
			}
		}
		if (child.getRefModel() instanceof N3EditorDiagramModel) {
			if (child != null) {
				IWorkbenchWindow workbenchWindow = ProjectManager.getInstance().window;
				IEditorInput input = null;
				//				input = new LogicEditorInput(new Path("쓰임새도"));
				N3EditorDiagramModel n3EditorDiagramModel = (N3EditorDiagramModel)child.getRefModel();
				Object obj = child.getParent().getRefModel();
				if (obj instanceof FinalPackageModel) {
					FinalPackageModel finalPackageModel = (FinalPackageModel)obj;
					finalPackageModel.getUMLDataModel().setN3EditorDiagramModel(null);
				}
				workbenchWindow.getActivePage().closeEditor(n3EditorDiagramModel.getUMLEditor(), false);
			}
		}
		//		else
		//			child.fireChildRemoved();
		return;
	}



	/*
	 * 모델 생성
	 */

	//등록
	public void addUMLModel(String name, UMLTreeParentModel tp, UMLModel child, int modelType,int diagramType) {
		//		UMLTreeModel treeModel = parent.getUMLTreeModel().getParent();
		//default

		System.out.println("dddddd:"+diagramType);
		
		if (child != null && modelType!=1000 &&  modelType!=1100) {
			modelType = this.getModelType(child, modelType);

			if (modelType == -1)
				return;
		}
		if (tp != null && tp instanceof UMLTreeParentModel) {
			UMLTreeParentModel uMLTreeParentModel = (UMLTreeParentModel)tp;
			UMLTreeModel to1 = null;
			if (name == null) {
				//				V1.02 WJH E 080822 S 객체 생성시 기본 이름이 숫자 0으로 시작하도록 수정 
				if(modelType==1000){
					defaultName = this.getDefaultName(29, uMLTreeParentModel);

				}
				else
					defaultName = this.getDefaultName(modelType, uMLTreeParentModel);
				//				V1.02 WJH 080909 S 뷰모델 이름이 안보이게 생성
				if(defaultName != null && !defaultName.equals("")){
					name = defaultName+0;
					name = tp.getCompName(name);
				}
				else
					name = "";
				//				V1.02 WJH 080909 E 뷰모델 이름이 안보이게 생성
				//				V1.02 WJH E 080822 E 객체 생성시 기본 이름이 숫자 0으로 시작하도록 수정 
				//				name = this.getDefaultName(modelType, uMLTreeParentModel);

			}
			//			int oldNum = this.copyNum;
			//			this.oldName = name;
			//			int num = this.getCopyName(tp, child, name,modelType);
			//
			//			//20080326 PKY S 복사 시에 뒤에 숫자 들어가는 문제 
			////			V1.02 WJH 080909 S 뷰모델 이름이 안보이게 생성
			//			if(modelType!=17&& name != null && !name.equals("")||modelType!=16&& name != null &&!name.equals("")){
			////				V1.02 WJH 080909 E 뷰모델 이름이 안보이게 생성
			////				V1.02 WJH E 080822 S 객체 생성시 기본 이름이 숫자 0으로 시작하도록 수정 
			//				if(defaultName.equals("")){
			//					if(num!=-1 && oldNum!=num){
			//						name = name+num;
			//					}
			//				}
			//				else{
			//					name = defaultName+num;
			//				}
			////				V1.02 WJH E 080822 E 객체 생성시 기본 이름이 숫자 0으로 시작하도록 수정 
			////				if(num!=-1 && oldNum!=num){
			////					name = name+num;       
			////				}
			//			} 
			//20080326 PKY E 복사 시에 뒤에 숫자 들어가는 문제 
			this.copyNum = 0;
			defaultName = "";//				V1.02 WJH E 080822 객체 생성시 기본 이름이 숫자 0으로 시작하도록 수정

			if (modelType == 0) {
				to1 = new PackageTreeModel(name);
				UMLModel finalPackageModel = null;
				if (child == null) {
					finalPackageModel = new FinalPackageModel();
				}
				else {
					finalPackageModel = child;
					//KHG 2010.05.19 어플리케이션과 다이어그램 이름이 일치하도록 수정
					N3EditorDiagramModel n3EditorDiagramModel =
						this.addUMLDiagram(name+" diagram", (UMLTreeParentModel)to1, null, 1, false,diagramType);
					((FinalPackageModel)finalPackageModel).setN3EditorDiagramModel(n3EditorDiagramModel);


				}
				to1.setRefModel(finalPackageModel);
				finalPackageModel.setName(name);
				finalPackageModel.setTreeModel(to1);
				to1.setParent(uMLTreeParentModel);
			}
			if (modelType == 2) {
				to1 = new StrcuturedPackageTreeModel(name);
				UMLModel useCaseModel = null;
				if (child == null) {
					useCaseModel = new UseCaseModel();
				}
				else {
					useCaseModel = child;
				}
				to1.setRefModel(useCaseModel);
				useCaseModel.setName(name);
				useCaseModel.setTreeModel(to1);
				to1.setParent(uMLTreeParentModel);
			}
			else if (modelType == 3) {
				to1 = new StrcuturedPackageTreeModel(name); //2008040203 PKY S 
				UMLModel finalActorModel = null;
				if (child == null) {
					finalActorModel = new FinalActorModel();
				}
				else {
					finalActorModel = child;
				}
				//				FinalActorModel finalActorModel = new FinalActorModel();
				to1.setRefModel(finalActorModel);
				finalActorModel.setName(name);
				finalActorModel.setTreeModel(to1);
				to1.setParent(uMLTreeParentModel);
			}
			else if (modelType == 4) {
				//PKY 08071601 S 바운더리 트리추가안되도록 하는 부분 수정하였으나 통합하면서 누락된부분 추가
				//				to1 = new UMLTreeModel(name);
				UMLModel finalBoundryModel = null;
				if (child == null) {
					finalBoundryModel = new FinalBoundryModel();
				}
				else {
					finalBoundryModel = child;
				}
				//				FinalBoundryModel finalBoundryModel = new FinalBoundryModel();
				//				to1.setRefModel(finalBoundryModel);
				finalBoundryModel.setName(name);
				//				finalBoundryModel.setTreeModel(to1);
				//				to1.setParent(uMLTreeParentModel);
				//PKY 08071601 E 바운더리 트리추가안되도록 하는 부분 수정하였으나 통합하면서 누락된부분 추가
			}
			else if (modelType == 5) {
				to1 = new StrcuturedPackageTreeModel(name);
				UMLModel collaborationModel = null;
				if (child == null) {
					collaborationModel = new CollaborationModel();
				}
				else {
					collaborationModel = child;
				}
				//				CollaborationModel collaborationModel = new CollaborationModel();
				to1.setRefModel(collaborationModel);
				collaborationModel.setName(name);
				collaborationModel.setTreeModel(to1);
				to1.setParent(uMLTreeParentModel);
			}
			else if (modelType == 6) {
				//합성클래스
				to1 = new StrcuturedPackageTreeModel(name);//2008040203 PKY S 
				UMLModel classModel = null;
				if (child == null) {
					classModel = new FinalClassModel();
				}
				else {
					classModel = child;
				}
				//				CollaborationModel collaborationModel = new CollaborationModel();
				to1.setRefModel(classModel);
				classModel.setName(name);
				classModel.setTreeModel(to1);
				to1.setParent(uMLTreeParentModel);
			}
			else if (modelType == 7) {
				to1 = new StrcuturedPackageTreeModel(name);//2008040203 PKY S 
				UMLModel classModel = null;
				if (child == null) {
					classModel = new InterfaceModel();
				}
				else {
					classModel = child;
				}
				//				CollaborationModel collaborationModel = new CollaborationModel();
				to1.setRefModel(classModel);
				classModel.setName(name);
				classModel.setTreeModel(to1);
				to1.setParent(uMLTreeParentModel);
			}
			else if (modelType == 8) {
				to1 = new UMLTreeModel(name);
				UMLModel classModel = null;
				if (child == null) {
					classModel = new EnumerationModel();
				}
				else {
					classModel = child;
				}
				//				CollaborationModel collaborationModel = new CollaborationModel();
				to1.setRefModel(classModel);
				classModel.setName(name);
				classModel.setTreeModel(to1);
				to1.setParent(uMLTreeParentModel);
			}
			else if (modelType == 9) {
				to1 = new UMLTreeModel(name);
				UMLModel exceptionModel = null;
				if (child == null) {
					exceptionModel = new ExceptionModel();
				}
				else {
					exceptionModel = child;
				}
				//				CollaborationModel collaborationModel = new CollaborationModel();
				to1.setRefModel(exceptionModel);
				exceptionModel.setName(name);
				exceptionModel.setTreeModel(to1);
				to1.setParent(uMLTreeParentModel);
			}
			else if (modelType == 10) {
				to1 = new StrcuturedPackageTreeModel(name);//2008040203 PKY S 
				UMLModel finalActivityModel = null;
				if (child == null) {
					finalActivityModel = new FinalActivityModel();
				}
				else {
					finalActivityModel = child;
				}
				//				this.behaviorActivityList.add(finalActivityModel);
				//				CollaborationModel collaborationModel = new CollaborationModel();
				to1.setRefModel(finalActivityModel);
				finalActivityModel.setName(name);
				finalActivityModel.setTreeModel(to1);
				to1.setParent(uMLTreeParentModel);
				this.addBehaviorActivityList(to1);
			}
			else if (modelType == 11) {
				to1 = new UMLTreeModel(name);
				UMLModel finalActiionModel = null;
				if (child == null) {
					finalActiionModel = new FinalActiionModel();
				}
				else {
					finalActiionModel = child;
				}
				//				CollaborationModel collaborationModel = new CollaborationModel();
				to1.setRefModel(finalActiionModel);
				finalActiionModel.setName(name);
				finalActiionModel.setTreeModel(to1);
				to1.setParent(uMLTreeParentModel);
			}
			else if (modelType == 12) {
				to1 = new UMLTreeModel(name);
				UMLModel sendModel = null;
				if (child == null) {
					sendModel = new SendModel();
				}
				else {
					sendModel = child;
				}
				//				CollaborationModel collaborationModel = new CollaborationModel();
				to1.setRefModel(sendModel);
				sendModel.setName(name);
				sendModel.setTreeModel(to1);
				to1.setParent(uMLTreeParentModel);
			}
			else if (modelType == 13) {
				to1 = new UMLTreeModel(name);
				UMLModel receiveModel = null;
				if (child == null) {
					receiveModel = new ReceiveModel();
				}
				else {
					receiveModel = child;
				}
				//				CollaborationModel collaborationModel = new CollaborationModel();
				to1.setRefModel(receiveModel);
				receiveModel.setName(name);
				receiveModel.setTreeModel(to1);
				to1.setParent(uMLTreeParentModel);
			}
			else if (modelType == 14) {
				//				to1 = new UMLTreeModel(name);//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
				UMLModel initialModel = null;
				if (child == null) {
					initialModel = new InitialModel();
				}
				else {
					initialModel = child;
				}
				//				CollaborationModel collaborationModel = new CollaborationModel();
				//				to1.setRefModel(initialModel);//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
				initialModel.setName(name);
				//				initialModel.setTreeModel(to1);//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
				//				to1.setParent(uMLTreeParentModel);//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
			}
			else if (modelType == 15) {
				//				to1 = new UMLTreeModel(name);//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
				UMLModel finalModel = null;
				if (child == null) {
					finalModel = new FinalModel();
				}
				else {
					finalModel = child;
				}
				//				CollaborationModel collaborationModel = new CollaborationModel();
				//				to1.setRefModel(finalModel);//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
				finalModel.setName(name);
				//				finalModel.setTreeModel(to1);//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
				//				to1.setParent(uMLTreeParentModel);//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
			}
			else if (modelType == 16) {
				//				to1 = new UMLTreeModel(name);//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
				UMLModel flowFinalModel = null;
				if (child == null) {
					flowFinalModel = new FlowFinalModel();
				}
				else {
					flowFinalModel = child;
				}
				//				CollaborationModel collaborationModel = new CollaborationModel();
				//				to1.setRefModel(flowFinalModel);//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint
				flowFinalModel.setName(name);
				//				flowFinalModel.setTreeModel(to1);//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
				//				to1.setParent(uMLTreeParentModel);//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
			}
			else if (modelType == 17) {
				//				to1 = new UMLTreeModel(name);//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
				UMLModel synchModel = null;
				if (child == null) {
					synchModel = new SynchModel();
				}
				else {
					synchModel = child;
				}
				//				CollaborationModel collaborationModel = new CollaborationModel();
				//				to1.setRefModel(synchModel);//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
				synchModel.setName(name);
				//				synchModel.setTreeModel(to1);//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
				//				to1.setParent(uMLTreeParentModel);//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
			}
			else if (modelType == 18) {
				//				to1 = new UMLTreeModel(name);//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
				UMLModel decisionModel = null;
				if (child == null) {
					decisionModel = new DecisionModel();
				}
				else {
					decisionModel = child;
				}
				//				CollaborationModel collaborationModel = new CollaborationModel();
				//				to1.setRefModel(decisionModel);//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
				decisionModel.setName(name);
				//				decisionModel.setTreeModel(to1);//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
				//				to1.setParent(uMLTreeParentModel);//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
			}
			else if (modelType == 19) {
				to1 = new UMLTreeModel(name);
				UMLModel finalObjectNodeModel = null;
				if (child == null) {
					finalObjectNodeModel = new FinalObjectNodeModel();
				}
				else {
					finalObjectNodeModel = child;
				}
				//				CollaborationModel collaborationModel = new CollaborationModel();
				to1.setRefModel(finalObjectNodeModel);
				finalObjectNodeModel.setName(name);
				finalObjectNodeModel.setTreeModel(to1);
				to1.setParent(uMLTreeParentModel);
			}
			else if (modelType == 20) {
				to1 = new UMLTreeModel(name);
				UMLModel centralBufferNodeModel = null;
				if (child == null) {
					centralBufferNodeModel = new CentralBufferNodeModel();
				}
				else {
					centralBufferNodeModel = child;
				}
				//				CollaborationModel collaborationModel = new CollaborationModel();
				to1.setRefModel(centralBufferNodeModel);
				centralBufferNodeModel.setName(name);
				centralBufferNodeModel.setTreeModel(to1);
				to1.setParent(uMLTreeParentModel);
			}
			else if (modelType == 21) {
				to1 = new UMLTreeModel(name);
				UMLModel dataStoreModel = null;
				if (child == null) {
					dataStoreModel = new DataStoreModel();
				}
				else {
					dataStoreModel = child;
				}
				//				CollaborationModel collaborationModel = new CollaborationModel();
				to1.setRefModel(dataStoreModel);
				dataStoreModel.setName(name);
				dataStoreModel.setTreeModel(to1);
				to1.setParent(uMLTreeParentModel);
			}
			else if (modelType == 22) {
				//PKY 08071601 S 바운더리 트리추가안되도록 하는 부분 수정하였으나 통합하면서 누락된부분 추가
				//				to1 = new UMLTreeModel(name);//PKY 08070904 S 바운더리 swimlane 모델 브라우져에 추가안되도록 수정
				UMLModel hPartitionModel = null;
				if (child == null) {
					hPartitionModel = new HPartitionModel();
				}
				else {
					hPartitionModel = child;
				}
				//				CollaborationModel collaborationModel = new CollaborationModel();
				//				to1.setRefModel(hPartitionModel);//PKY 08070904 S 바운더리 swimlane 모델 브라우져에 추가안되도록 수정
				hPartitionModel.setName(name);
				//				hPartitionModel.setTreeModel(to1);//PKY 08070904 S 바운더리 swimlane 모델 브라우져에 추가안되도록 수정
				//				to1.setParent(uMLTreeParentModel);//PKY 08070904 S 바운더리 swimlane 모델 브라우져에 추가안되도록 수정
				//PKY 08071601 E 바운더리 트리추가안되도록 하는 부분 수정하였으나 통합하면서 누락된부분 추가

			}
			else if (modelType == 23) {
				to1 = new UMLTreeModel(name);
				UMLModel vPartitionModel = null;
				if (child == null) {
					vPartitionModel = new VPartitionModel();
				}
				else {
					vPartitionModel = child;
				}
				//				CollaborationModel collaborationModel = new CollaborationModel();
				to1.setRefModel(vPartitionModel);
				vPartitionModel.setName(name);
				vPartitionModel.setTreeModel(to1);
				to1.setParent(uMLTreeParentModel);
			}
			else if (modelType == 24) {
				//				to1 = new UMLTreeModel(name);//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
				UMLModel hForkJoinModel = null;
				if (child == null) {
					hForkJoinModel = new HForkJoinModel();
				}
				else {
					hForkJoinModel = child;
				}
				//				CollaborationModel collaborationModel = new CollaborationModel();
				//				to1.setRefModel(hForkJoinModel);//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
				hForkJoinModel.setName(name);
				//				hForkJoinModel.setTreeModel(to1);//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
				//				to1.setParent(uMLTreeParentModel);//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
			}
			else if (modelType == 25) {
				//				to1 = new UMLTreeModel(name);//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
				UMLModel vForkJoinModel = null;
				if (child == null) {
					vForkJoinModel = new VForkJoinModel();
				}
				else {
					vForkJoinModel = child;
				}
				//				CollaborationModel collaborationModel = new CollaborationModel();
				//				to1.setRefModel(vForkJoinModel);//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
				vForkJoinModel.setName(name);
				//				vForkJoinModel.setTreeModel(to1);//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
				//				to1.setParent(uMLTreeParentModel);//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
			}
			else if (modelType == 26) {
				to1 = new StrcuturedActivityPackageTreeModel(name);
				UMLModel finalStrcuturedActivityModel = null;
				if (child == null) {
					finalStrcuturedActivityModel = new FinalStrcuturedActivityModel();
				}
				else {
					N3EditorDiagramModel n3EditorDiagramModel =
						this.addUMLDiagram(name, (UMLTreeParentModel)to1, null, 1, false,-1);
					((FinalStrcuturedActivityModel)child).setN3EditorDiagramModel(n3EditorDiagramModel);
					n3EditorDiagramModel.setDiagramType(9);//PKY 08061801 S StrcuturedActivity 생성 시 다이어그램 하위 타입 지정하도록 수정
					finalStrcuturedActivityModel = child;
				}
				//				this.addBehaviorActivityList(finalStrcuturedActivityModel);
				//				CollaborationModel collaborationModel = new CollaborationModel();
				to1.setRefModel(finalStrcuturedActivityModel);
				finalStrcuturedActivityModel.setName(name);
				finalStrcuturedActivityModel.setTreeModel(to1);
				to1.setParent(uMLTreeParentModel);
				this.addBehaviorActivityList(to1);
			}
			else if (modelType == 27) {
				to1 = new UMLTreeModel(name);
				UMLModel partModel = null;
				if (child == null) {
					partModel = new PartModel();
				}
				else {
					partModel = child;
				}
				//				CollaborationModel collaborationModel = new CollaborationModel();
				to1.setRefModel(partModel);
				partModel.setName(name);
				partModel.setTreeModel(to1);
				to1.setParent(uMLTreeParentModel);
			}
			else if (modelType == 50) {

			}
			else if (modelType == 28) {

			}
			else if (modelType == 29) {
				to1 = new StrcuturedPackageTreeModel(name);//2008040203 PKY S 
				UMLModel componentModel = null;
				if (child == null) {
					componentModel = new ComponentModel();
				}
				else {
					componentModel = child;
				}
				//				CollaborationModel collaborationModel = new CollaborationModel();

				N3EditorDiagramModel n3EditorDiagramModel =
					this.addUMLDiagram(name+" diagram", (UMLTreeParentModel)to1, null, 1, false,diagramType);
				((ComponentModel)componentModel).setN3EditorDiagramModel(n3EditorDiagramModel);
				to1.setRefModel(componentModel);
				componentModel.setName(name);
				componentModel.setTreeModel(to1);
				to1.setParent(uMLTreeParentModel);
				TreeSimpleFactory ts1 = new TreeSimpleFactory();
				ComponentModel n = (ComponentModel)ts1.createModle(componentModel);
				n.setN3EditorDiagramModel(n3EditorDiagramModel);
				
				System.out.println("id1=====>"+componentModel.getUMLDataModel().getId());
				System.out.println("id2=====>"+n.getUMLDataModel().getId());
				if(child instanceof AtomicComponentModel){
					componentModel.setStereotype("atomic");
					n3EditorDiagramModel.setDtype(2);
					n.setSize(new Dimension(200,200));
//					this.createNewProject((AtomicComponentModel)componentModel);
					n.setCmpFolder(componentModel.getCmpFolder());
					((AtomicComponentModel)child).setimplementLangProp(String.valueOf(child.lang ));
					((AtomicComponentModel)child).setcompilerProp(String.valueOf(child.lang ));
					//						n3EditorDiagramModel.setDiagramType(2);

					//					n3EditorDiagramModel.setDiagramType(2);
				}
				else{
					n.setSize(new Dimension(400,400));
					componentModel.setStereotype("composite");
					n3EditorDiagramModel.setDtype(1);
					//					n3EditorDiagramModel.setDiagramType(1);
				}
				n3EditorDiagramModel.addChild(n);
			}
			else if (modelType == 30) {
				to1 = new UMLTreeModel(name);
				UMLModel artfifactModel = null;
				if (child == null) {
					artfifactModel = new ArtfifactModel();
				}
				else {
					artfifactModel = child;
				}
				//				CollaborationModel collaborationModel = new CollaborationModel();
				to1.setRefModel(artfifactModel);
				artfifactModel.setName(name);
				artfifactModel.setTreeModel(to1);
				to1.setParent(uMLTreeParentModel);
			}
			else if (modelType == 31) {

			}
			else if (modelType == 32) {

			}
			else if (modelType == 33) {

			}
			else if (modelType == 34) {

			}
			else if (modelType == 36) {

			}
			else if (modelType == 35) {
				//				to1 = new StrcuturedPackageTreeModel(name);//2008040203 PKY S 
				//				UMLModel nodeModel = null;
				//				if (child == null) {
				//					nodeModel = new NodeModel();
				//				}
				//				else {
				//					nodeModel = child;
				//				}
				//				//				this.addBehaviorActivityList(finalStrcuturedActivityModel);
				//				//				CollaborationModel collaborationModel = new CollaborationModel();
				//				to1.setRefModel(nodeModel);
				//				nodeModel.setName(name);
				//				nodeModel.setTreeModel(to1);
				//				to1.setParent(uMLTreeParentModel);
				//				this.addBehaviorActivityList(to1);
			}
			else if (modelType == 37) {
				//				to1 = new UMLTreeModel(name);
				//				UMLModel deviceModel = null;
				//				if (child == null) {
				//					deviceModel = new DeviceModel();
				//				}
				//				else {
				//					deviceModel = child;
				//				}
				//				//				this.addBehaviorActivityList(finalStrcuturedActivityModel);
				//				//				CollaborationModel collaborationModel = new CollaborationModel();
				//				to1.setRefModel(deviceModel);
				//				deviceModel.setName(name);
				//				deviceModel.setTreeModel(to1);
				//				to1.setParent(uMLTreeParentModel);
				//				this.addBehaviorActivityList(to1);
			}
			else if (modelType == 38) {
				//				to1 = new UMLTreeModel(name);
				//				UMLModel executionEnvironmentModel = null;
				//				if (child == null) {
				//					executionEnvironmentModel = new ExecutionEnvironmentModel();
				//				}
				//				else {
				//					executionEnvironmentModel = child;
				//				}
				//				//				this.addBehaviorActivityList(finalStrcuturedActivityModel);
				//				//				CollaborationModel collaborationModel = new CollaborationModel();
				//				to1.setRefModel(executionEnvironmentModel);
				//				executionEnvironmentModel.setName(name);
				//				executionEnvironmentModel.setTreeModel(to1);
				//				to1.setParent(uMLTreeParentModel);
				//				this.addBehaviorActivityList(to1);
			}
			else if (modelType == 39) {
				//				to1 = new UMLTreeModel(name);
				//				UMLModel deploymentSpecificationModel = null;
				//				if (child == null) {
				//					deploymentSpecificationModel = new DeploymentSpecificationModel();
				//				}
				//				else {
				//					deploymentSpecificationModel = child;
				//				}
				//				//				this.addBehaviorActivityList(finalStrcuturedActivityModel);
				//				//				CollaborationModel collaborationModel = new CollaborationModel();
				//				to1.setRefModel(deploymentSpecificationModel);
				//				deploymentSpecificationModel.setName(name);
				//				deploymentSpecificationModel.setTreeModel(to1);
				//				to1.setParent(uMLTreeParentModel);
				//				this.addBehaviorActivityList(to1);
			}
			else if (modelType == 1000) {



				to1 = new StrcuturedPackageTreeModel(name);//2008040203 PKY S 
				AtomicComponentModel componentModel = null;
				child.setName(name);
				String path = this.getProjectPath();
				UMLModel newModel = (UMLModel)child.clone();
				componentModel = (AtomicComponentModel)newModel;
				((AtomicComponentModel)child).setCoreUMLTreeModel(to1);
				child.getUMLTreeModel().setName(name);
				((AtomicComponentModel)child).setName(name);
				N3EditorDiagramModel n3EditorDiagramModel =
					this.addUMLDiagram(name+" diagram", (UMLTreeParentModel)to1, null, 1, false,diagramType);
				((ComponentModel)componentModel).setN3EditorDiagramModel(n3EditorDiagramModel);
				to1.setRefModel(componentModel);
				componentModel.setName(name);
				componentModel.setTreeModel(to1);
				to1.setParent(uMLTreeParentModel);
				TreeSimpleFactory ts1 = new TreeSimpleFactory();
				ComponentModel n = (ComponentModel)ts1.createModle(componentModel);
				n.setN3EditorDiagramModel(n3EditorDiagramModel);
				n.setSize(new Dimension(200,200));
				System.out.println("id1=====>"+componentModel.getUMLDataModel().getId());
				System.out.println("id2=====>"+n.getUMLDataModel().getId());
				if(newModel instanceof AtomicComponentModel){
					componentModel.setStereotype("atomic");
					n3EditorDiagramModel.setDtype(2);
					componentModel.lang = child.lang;
					this.createNewProject((AtomicComponentModel)componentModel);
					((AtomicComponentModel)child).setCmpFolder(componentModel.getCmpFolder());
					((AtomicComponentModel)child).setimplementLangProp(String.valueOf(child.lang ));
					((AtomicComponentModel)child).setcompilerProp(String.valueOf(child.lang ));
				}
				else{	
					componentModel.setStereotype("composite");
					n3EditorDiagramModel.setDtype(1);
				}
				n3EditorDiagramModel.addChild(n);
			}
			else if (modelType == 1100) {

				boolean isOverlapping = ProjectManager.getInstance().isOverlapping((UMLTreeParentModel)uMLTreeParentModel,  name);
				//				String name =treeObject.getName();
				String oldName = name;
				if(isOverlapping) {
					for(int i=0;i<1000;i++){
						String tempname = name +i ;
						isOverlapping = ProjectManager.getInstance().isOverlapping(uMLTreeParentModel,  tempname);
						if(!isOverlapping){
							name = tempname;
							break;
						}
					}

				}

				UMLTreeParentModel to2 = new StrcuturedPackageTreeModel(name);//2008040203 PKY S 
				UMLModel componentModel = null;
				if (child == null) {
					componentModel = new ComponentModel();
				}
				else {
					if(child instanceof TemplateComponentModel){
						TemplateComponentModel tcm = (TemplateComponentModel)child;
						componentModel = tcm.getAtomicComponentModel();
						if(this.lang!=-1)
						componentModel.lang =  this.lang;
					}
				}
				N3EditorDiagramModel n3EditorDiagramModel =
					this.addUMLDiagram(name+" diagram", (UMLTreeParentModel)to2, null, 1, false,diagramType);
				((ComponentModel)componentModel).setN3EditorDiagramModel(n3EditorDiagramModel);
				to2.setRefModel(componentModel);
				componentModel.setName(name);
				componentModel.setTreeModel(to2);
				to2.setParent(uMLTreeParentModel);
				TreeSimpleFactory ts1 = new TreeSimpleFactory();
				ComponentModel n = (ComponentModel)ts1.createModle(componentModel);
				UMLTreeParentModel upm  = (UMLTreeParentModel)child.getUMLTreeModel();
				n.setN3EditorDiagramModel(n3EditorDiagramModel);
				n.setSize(new Dimension(200,200));
				System.out.println("id1=====>"+componentModel.getUMLDataModel().getId());
				System.out.println("id2=====>"+n.getUMLDataModel().getId());
				componentModel.setStereotype("atomic");
				n3EditorDiagramModel.setDtype(2);
				this.createNewProject((AtomicComponentModel)componentModel);
				((AtomicComponentModel)componentModel).setimplementLangProp(String.valueOf(componentModel.lang ));
				((AtomicComponentModel)componentModel).setcompilerProp(String.valueOf(componentModel.lang ));
				System.out.println("cmpPath   nnn=====>"+n.getCmpFolder());
				n.setCmpFolder(n.getCmpFolder());
				String cmpPath = n.getCmpFolder();
				OPRoSExeEnvironmentElementModel aa = (OPRoSExeEnvironmentElementModel)((ComponentModel)componentModel).getClassModel().getUMLDataModel().getElementProperty("OPRoSExeEnvironmentElementModel");
				System.out.println("cmpPath=====>"+cmpPath);
				aa.setexeCmpFolderProp(cmpPath);
				n3EditorDiagramModel.addChild(n);
				for(int i=0;i<upm.getChildren().length;i++){
					UMLTreeModel um = (UMLTreeModel)upm.getChildren()[i];
					if(!(um.getRefModel() instanceof PortModel))
						continue;
					PortModel pm1 = (PortModel)um.getRefModel();
					PortModel pm2 = null;
					if(pm1 instanceof MonitoringMethodPortModel){
						pm2 = new MonitoringMethodPortModel(n);
					}
					else if(pm1 instanceof LifecycleMethodPortModel){
						pm2 = new LifecycleMethodPortModel(n);
					}
					else if(pm1 instanceof MethodOutputPortModel){
						pm2 = new MethodOutputPortModel(n);
					}
					else if(pm1 instanceof MethodInputPortModel){
						pm2 = new MethodInputPortModel(n);
					}
					else if(pm1 instanceof DataInputPortModel){
						pm2 = new DataInputPortModel(n);
					}
					else if(pm1 instanceof DataOutputPortModel){
						pm2 = new DataOutputPortModel(n);
					}
					else if(pm1 instanceof EventInputPortModel){
						pm2 = new EventInputPortModel(n);
					}
					else if(pm1 instanceof EventOutputPortModel){
						pm2 = new EventOutputPortModel(n);
					}
					pm2.getElementLabelModel().setReadOnly(true);
					UMLTreeModel port = new UMLTreeModel(pm1.getAttachElementLabelModel().getLabelContents());
					to2.addChild(port);
					port.setRefModel(pm2);
					pm2.getElementLabelModel().setTreeModel(port);
					pm2.getElementLabelModel().setPortId(pm2.getID());
					pm2.getUMLDataModel().setId(pm2.getID());
					pm2.getAttachElementLabelModel().setText(pm1.getAttachElementLabelModel().getLabelContents());
					pm2.getElementLabelModel().setTreeModel(port);
					n.getPortManager().getPorts().add(pm2);
					pm2.setType(pm1.getType());
					String typeref = pm1.getTypeRef();
					String queueingPolicy = pm1.getQueueingPolicy();
					String portQueueSize = pm1.getQueueSize();
					pm2.setTypeRef(typeref);
					pm2.setQueueingPolicy(queueingPolicy);
					pm2.setQueueSize(portQueueSize);
					System.out.println("typeRef===================>"+typeref);
					System.out.println("queueingPolicy===================>"+queueingPolicy);
					System.out.println("portQueueSize===================>"+portQueueSize);
					ProjectManager.getInstance().getModelBrowser().refresh(to1);
					n.createPort2(pm2, n3EditorDiagramModel);
					pm2.setPortContainerModel(n);

				}
				ProjectManager.getInstance().templateCmp.put(oldName,to2);
				System.out.println("templateCmp==================================>"+oldName);
				CompAssemManager.getInstance().autoLayoutPort(n.getPortManager().getPorts());
				to1 = to2;
			}


			//			else if(modelType==27){
			//				to1 = new UMLTreeModel(name);
			//				UMLModel vForkJoinModel = null;
			//				if(child==null){
			//					vForkJoinModel = new VForkJoinModel();
			//				}
			//				else{
			//					vForkJoinModel = child;
			//				}
			////				CollaborationModel collaborationModel = new CollaborationModel();
			//				to1.setRefModel(vForkJoinModel);
			//				vForkJoinModel.setName(name);
			//				vForkJoinModel.setTreeModel(to1);
			//				
			//				to1.setParent(uMLTreeParentModel);
			//
			//			}
			if (to1 != null) {
				uMLTreeParentModel.addChild(to1);
			}
			//			UMLTreeModel to1 = null;
			//			if(modelType!=0){
			//				 to1 = new UMLTreeModel(name);
			//				
			//			}
			//			else{
			//				to1 = new PackageTreeModel(name);
			////				child.setTreeModel((UMLTreeModel)to1);
			//			}
			//			if(child!=null){
			//				child.setTreeModel(to1);
			//				child.setName(name);
			//			}
			//			ProjectManager.getInstance().getCopyName(uMLTreeParentModel, child);
			//PKY 08071601 S 바운더리 트리추가안되도록 하는 부분 수정하였으나 통합하면서 누락된부분 추가

			ProjectManager.getInstance().getModelBrowser().refresh(uMLTreeParentModel);
			ProjectManager.getInstance().getModelBrowser().expend(tp);
			//PKY 08082201 S 선을 선택한다음 객체를 생성하면 에러메세지 발생
			//			if(to1!=null)//PKY 08070904 S 바운더리 swimlane 모델 브라우져에 추가안되도록 수정
			//				ProjectManager.getInstance().getModelBrowser().select(to1);
			//PKY 08082201 E 선을 선택한다음 객체를 생성하면 에러메세지 발생

			if (modelType != 0 && modelType != 40 && modelType != 50 && modelType != 28) {
				this.getTypeModel().add(to1);
			}
			//PKY 08071601 E 바운더리 트리추가안되도록 하는 부분 수정하였으나 통합하면서 누락된부분 추가
			this.newMakeModel = to1;			// V1.01 WJH E 080519 추가
		}
	}

	//	V1.01 WJH E 080519 S 함수 추가
	public UMLTreeModel addUMLModel(String name, UMLTreeParentModel tp, UMLModel child, int modelType,int diagramType, String id) {

		addUMLModel(name,  tp,  child,  modelType, diagramType);

		if(newMakeModel != null)
			((UMLModel)newMakeModel.getRefModel()).getUMLDataModel().setId(id);

		//		newMakeModel = null;
		return newMakeModel;
	}
	//	V1.01 WJH E 080519 E 함수 추가  

	/*
	 * 다이어그램  생성
	 */

	public N3EditorDiagramModel addUMLDiagram(String name, UMLTreeParentModel tp, UMLEditor child,
			int modelType, boolean isOpen,int diagramType) {

		//		UMLTreeModel treeModel = parent.getUMLTreeModel().getParent();
		//default
		try{
			if (tp != null && tp instanceof UMLTreeParentModel) {
				UMLTreeParentModel uMLTreeParentModel = (UMLTreeParentModel)tp;
				UMLTreeModel to1 = null;
				if (modelType == 1) {
					if (name == null) {
						name = this.getDefaultName(1, uMLTreeParentModel);
					}
					to1 = new UMLTreeModel(name);
					uMLTreeParentModel.addChild(to1);

				}
				if (child == null && isOpen) {
					IWorkbenchWindow workbenchWindow = ProjectManager.getInstance().at.getWorkbench().getActiveWorkbenchWindow();
					IEditorInput input = null;
					//PKY 08080501 S 다이어그램 전체 부분에서 "쓰임새도"라고 나오는 문제 수정
					if(diagramType==1)
						input = new LogicEditorInput(new Path(N3Messages.DIALOG_PACKAGE));
					else if(diagramType==2)
						input = new LogicEditorInput(new Path(N3Messages.PALETTE_TITLE_CLASS));
					else if(diagramType==3)
						input = new LogicEditorInput(new Path(N3Messages.DIALOG_OBJECT));
					else if(diagramType==4)
						input = new LogicEditorInput(new Path(N3Messages.PALETTE_TITLE_COMPOSITE));
					else if(diagramType==5)
						input = new LogicEditorInput(new Path(N3Messages.PALETTE_TITLE_COMPONENT));
					else if(diagramType==6)
						input = new LogicEditorInput(new Path(N3Messages.PALETTE_TITLE_DEPLOYMENT));
					else if(diagramType==7)
						input = new LogicEditorInput(new Path(N3Messages.DIALOG_STRUCTURAL));
					else if(diagramType==8)
						input = new LogicEditorInput(new Path(N3Messages.PALETTE_TITLE_USECASE));
					else if(diagramType==9)
						input = new LogicEditorInput(new Path(N3Messages.PALETTE_TITLE_ACTIVITY));
					else if(diagramType==10)
						input = new LogicEditorInput(new Path(N3Messages.PALETTE_TITLE_STATE));
					else if(diagramType==11)
						input = new LogicEditorInput(new Path(N3Messages.PALETTE_TITLE_COMMUNICATION));
					else if(diagramType==12)
						input = new LogicEditorInput(new Path(N3Messages.PALETTE_TITLE_INTERACTION));
					else if(diagramType==13)
						input = new LogicEditorInput(new Path(N3Messages.PALETTE_TITLE_TIMING));
					else if(diagramType==14)
						input = new LogicEditorInput(new Path(N3Messages.DIALOG_INTERACTION_OVERVIEW));
					else if(diagramType==15)
						input = new LogicEditorInput(new Path(N3Messages.DIALOG_BEHAVIORAL));
					else if(diagramType==17)
						input = new LogicEditorInput(new Path(N3Messages.PALETTE_REQUIRMENT));
					else  
						input = new LogicEditorInput(new Path("Diagram"));
					//PKY 08080501 E 다이어그램 전체 부분에서 "쓰임새도"라고 나오는 문제 수정
					try {

						tempDiagramType = diagramType;
						UMLEditor u = (UMLEditor)workbenchWindow.getActivePage().openEditor(input, UMLEditor.ID, true);
						u.setTitleName(name);
						if(u.getDiagram().getDiagramType()==-1){
							u.getDiagram().setDiagramType(diagramType);
							//							N3Plugin.getDefault().open(diagramType);
						}
						to1.setRefModel(u.getDiagram());
						u.getDiagram().setName(name);
						u.getDiagram().setTreeModel(to1);
						to1.setParent(uMLTreeParentModel);
						u.getDiagram().setIEditorInput(input);
						ProjectManager.getInstance().getModelBrowser().refresh(uMLTreeParentModel);
						ProjectManager.getInstance().getModelBrowser().expend(tp);
						ProjectManager.getInstance().getModelBrowser().select(to1);
						this.tempDiagram = u.getDiagram();				// V1.03 WJH E 080526 추가

						//PKY 08061101 S 패키지 하위에 다이어그램을 삭제 후 다이어그램을 재 생성하여 더블클릭하면 오픈되지 않던 문제 수정
						if(uMLTreeParentModel instanceof PackageTreeModel){
							if(uMLTreeParentModel.getRefModel()!=null)
								if(uMLTreeParentModel.getRefModel() instanceof FinalPackageModel){
									FinalPackageModel model=(FinalPackageModel)uMLTreeParentModel.getRefModel();
									if(to1 instanceof UMLTreeModel){
										if(to1.getRefModel()!=null){
											if(to1.getRefModel() instanceof N3EditorDiagramModel)
												model.setN3EditorDiagramModel((N3EditorDiagramModel)to1.getRefModel());		
										}
									}

								}
						}
						//PKY 08061101 E 패키지 하위에 다이어그램을 삭제 후 다이어그램을 재 생성하여 더블클릭하면 오픈되지 않던 문제 수정


						return null;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				else {
					N3EditorDiagramModel n3EditorDiagramModel = new N3EditorDiagramModel();
					IEditorInput input = null;
					//PKY 08080501 S 다이어그램 전체 부분에서 "쓰임새도"라고 나오는 문제 수정
					if(diagramType==1)
						input = new LogicEditorInput(new Path(N3Messages.DIALOG_PACKAGE));
					else if(diagramType==2)
						input = new LogicEditorInput(new Path(N3Messages.PALETTE_TITLE_CLASS));
					else if(diagramType==3)
						input = new LogicEditorInput(new Path(N3Messages.DIALOG_OBJECT));
					else if(diagramType==4)
						input = new LogicEditorInput(new Path(N3Messages.PALETTE_TITLE_COMPOSITE));
					else if(diagramType==5)
						input = new LogicEditorInput(new Path(N3Messages.PALETTE_TITLE_COMPONENT));
					else if(diagramType==6)
						input = new LogicEditorInput(new Path(N3Messages.PALETTE_TITLE_DEPLOYMENT));
					else if(diagramType==7)
						input = new LogicEditorInput(new Path(N3Messages.DIALOG_STRUCTURAL));
					else if(diagramType==8)
						input = new LogicEditorInput(new Path(N3Messages.PALETTE_TITLE_USECASE));
					else if(diagramType==9)
						input = new LogicEditorInput(new Path(N3Messages.PALETTE_TITLE_ACTIVITY));
					else if(diagramType==10)
						input = new LogicEditorInput(new Path(N3Messages.PALETTE_TITLE_STATE));
					else if(diagramType==11)
						input = new LogicEditorInput(new Path(N3Messages.PALETTE_TITLE_COMMUNICATION));
					else if(diagramType==12)
						input = new LogicEditorInput(new Path(N3Messages.PALETTE_TITLE_INTERACTION));
					else if(diagramType==13)
						input = new LogicEditorInput(new Path(N3Messages.PALETTE_TITLE_TIMING));
					else if(diagramType==14)
						input = new LogicEditorInput(new Path(N3Messages.DIALOG_INTERACTION_OVERVIEW));
					else if(diagramType==15)
						input = new LogicEditorInput(new Path(N3Messages.DIALOG_BEHAVIORAL));
					else if(diagramType==17)
						input = new LogicEditorInput(new Path(N3Messages.PALETTE_REQUIRMENT));
					else  
						input = new LogicEditorInput(new Path("Diagram"));
					//PKY 08080501 E 다이어그램 전체 부분에서 "쓰임새도"라고 나오는 문제 수정
					to1.setRefModel(n3EditorDiagramModel);
					n3EditorDiagramModel.setName(name);
					n3EditorDiagramModel.setTreeModel(to1);
					to1.setParent(uMLTreeParentModel);
					n3EditorDiagramModel.setIEditorInput(input);
					n3EditorDiagramModel.setDiagramType(diagramType);
					return n3EditorDiagramModel;
				}
			}
		}
		catch(Exception e1){
			e1.printStackTrace();
			System.out.println("application:"+application);
			try{
				FileWriter fw = new FileWriter(new File("c:\\err1.log"));
				PrintWriter bw=new PrintWriter(fw);

				e1.printStackTrace(bw);
				//				bw.write(e1.printStackTrace());
				bw.close();
				//				saveValue = -1;

			}
			catch(Exception e){
				e.printStackTrace();

			}
		}
		return null;
	}
	
	
	//20110712서동민
	/*
	 * 모델 생성
	 */

	//등록
	public void reComponent(String name, UMLTreeParentModel tp, UMLModel child, int modelType,int diagramType) {
		//		UMLTreeModel treeModel = parent.getUMLTreeModel().getParent();
		//default

		System.out.println("dddddd:"+diagramType);
		
		if (child != null && modelType!=1000 &&  modelType!=1100) {
			modelType = this.getModelType(child, modelType);

			if (modelType == -1)
				return;
		}
		if (tp != null && tp instanceof UMLTreeParentModel) {
			UMLTreeParentModel uMLTreeParentModel = (UMLTreeParentModel)tp;
			UMLTreeModel to1 = null;
			if (name == null) {
				//				V1.02 WJH E 080822 S 객체 생성시 기본 이름이 숫자 0으로 시작하도록 수정 
				if(modelType==1000){
					defaultName = this.getDefaultName(29, uMLTreeParentModel);

				}
				else
					defaultName = this.getDefaultName(modelType, uMLTreeParentModel);
				//				V1.02 WJH 080909 S 뷰모델 이름이 안보이게 생성
				if(defaultName != null && !defaultName.equals("")){
					name = defaultName+0;
					name = tp.getCompName(name);
				}
				else
					name = "";
				//				V1.02 WJH 080909 E 뷰모델 이름이 안보이게 생성
				//				V1.02 WJH E 080822 E 객체 생성시 기본 이름이 숫자 0으로 시작하도록 수정 
				//				name = this.getDefaultName(modelType, uMLTreeParentModel);

			}
			//			int oldNum = this.copyNum;
			//			this.oldName = name;
			//			int num = this.getCopyName(tp, child, name,modelType);
			//
			//			//20080326 PKY S 복사 시에 뒤에 숫자 들어가는 문제 
			////			V1.02 WJH 080909 S 뷰모델 이름이 안보이게 생성
			//			if(modelType!=17&& name != null && !name.equals("")||modelType!=16&& name != null &&!name.equals("")){
			////				V1.02 WJH 080909 E 뷰모델 이름이 안보이게 생성
			////				V1.02 WJH E 080822 S 객체 생성시 기본 이름이 숫자 0으로 시작하도록 수정 
			//				if(defaultName.equals("")){
			//					if(num!=-1 && oldNum!=num){
			//						name = name+num;
			//					}
			//				}
			//				else{
			//					name = defaultName+num;
			//				}
			////				V1.02 WJH E 080822 E 객체 생성시 기본 이름이 숫자 0으로 시작하도록 수정 
			////				if(num!=-1 && oldNum!=num){
			////					name = name+num;       
			////				}
			//			} 
			//20080326 PKY E 복사 시에 뒤에 숫자 들어가는 문제 
			this.copyNum = 0;
			defaultName = "";//				V1.02 WJH E 080822 객체 생성시 기본 이름이 숫자 0으로 시작하도록 수정

			if (modelType == 0) {
				to1 = new PackageTreeModel(name);
				UMLModel finalPackageModel = null;
				if (child == null) {
					finalPackageModel = new FinalPackageModel();
				}
				else {
					finalPackageModel = child;
					//KHG 2010.05.19 어플리케이션과 다이어그램 이름이 일치하도록 수정
					N3EditorDiagramModel n3EditorDiagramModel =
						this.addUMLDiagram(name+" diagram", (UMLTreeParentModel)to1, null, 1, false,diagramType);
					((FinalPackageModel)finalPackageModel).setN3EditorDiagramModel(n3EditorDiagramModel);


				}
				to1.setRefModel(finalPackageModel);
				finalPackageModel.setName(name);
				finalPackageModel.setTreeModel(to1);
				to1.setParent(uMLTreeParentModel);
			}
			if (modelType == 2) {
				to1 = new StrcuturedPackageTreeModel(name);
				UMLModel useCaseModel = null;
				if (child == null) {
					useCaseModel = new UseCaseModel();
				}
				else {
					useCaseModel = child;
				}
				to1.setRefModel(useCaseModel);
				useCaseModel.setName(name);
				useCaseModel.setTreeModel(to1);
				to1.setParent(uMLTreeParentModel);
			}
			else if (modelType == 3) {
				to1 = new StrcuturedPackageTreeModel(name); //2008040203 PKY S 
				UMLModel finalActorModel = null;
				if (child == null) {
					finalActorModel = new FinalActorModel();
				}
				else {
					finalActorModel = child;
				}
				//				FinalActorModel finalActorModel = new FinalActorModel();
				to1.setRefModel(finalActorModel);
				finalActorModel.setName(name);
				finalActorModel.setTreeModel(to1);
				to1.setParent(uMLTreeParentModel);
			}
			else if (modelType == 4) {
				//PKY 08071601 S 바운더리 트리추가안되도록 하는 부분 수정하였으나 통합하면서 누락된부분 추가
				//				to1 = new UMLTreeModel(name);
				UMLModel finalBoundryModel = null;
				if (child == null) {
					finalBoundryModel = new FinalBoundryModel();
				}
				else {
					finalBoundryModel = child;
				}
				//				FinalBoundryModel finalBoundryModel = new FinalBoundryModel();
				//				to1.setRefModel(finalBoundryModel);
				finalBoundryModel.setName(name);
				//				finalBoundryModel.setTreeModel(to1);
				//				to1.setParent(uMLTreeParentModel);
				//PKY 08071601 E 바운더리 트리추가안되도록 하는 부분 수정하였으나 통합하면서 누락된부분 추가
			}
			else if (modelType == 5) {
				to1 = new StrcuturedPackageTreeModel(name);
				UMLModel collaborationModel = null;
				if (child == null) {
					collaborationModel = new CollaborationModel();
				}
				else {
					collaborationModel = child;
				}
				//				CollaborationModel collaborationModel = new CollaborationModel();
				to1.setRefModel(collaborationModel);
				collaborationModel.setName(name);
				collaborationModel.setTreeModel(to1);
				to1.setParent(uMLTreeParentModel);
			}
			else if (modelType == 6) {
				//합성클래스
				to1 = new StrcuturedPackageTreeModel(name);//2008040203 PKY S 
				UMLModel classModel = null;
				if (child == null) {
					classModel = new FinalClassModel();
				}
				else {
					classModel = child;
				}
				//				CollaborationModel collaborationModel = new CollaborationModel();
				to1.setRefModel(classModel);
				classModel.setName(name);
				classModel.setTreeModel(to1);
				to1.setParent(uMLTreeParentModel);
			}
			else if (modelType == 7) {
				to1 = new StrcuturedPackageTreeModel(name);//2008040203 PKY S 
				UMLModel classModel = null;
				if (child == null) {
					classModel = new InterfaceModel();
				}
				else {
					classModel = child;
				}
				//				CollaborationModel collaborationModel = new CollaborationModel();
				to1.setRefModel(classModel);
				classModel.setName(name);
				classModel.setTreeModel(to1);
				to1.setParent(uMLTreeParentModel);
			}
			else if (modelType == 8) {
				to1 = new UMLTreeModel(name);
				UMLModel classModel = null;
				if (child == null) {
					classModel = new EnumerationModel();
				}
				else {
					classModel = child;
				}
				//				CollaborationModel collaborationModel = new CollaborationModel();
				to1.setRefModel(classModel);
				classModel.setName(name);
				classModel.setTreeModel(to1);
				to1.setParent(uMLTreeParentModel);
			}
			else if (modelType == 9) {
				to1 = new UMLTreeModel(name);
				UMLModel exceptionModel = null;
				if (child == null) {
					exceptionModel = new ExceptionModel();
				}
				else {
					exceptionModel = child;
				}
				//				CollaborationModel collaborationModel = new CollaborationModel();
				to1.setRefModel(exceptionModel);
				exceptionModel.setName(name);
				exceptionModel.setTreeModel(to1);
				to1.setParent(uMLTreeParentModel);
			}
			else if (modelType == 10) {
				to1 = new StrcuturedPackageTreeModel(name);//2008040203 PKY S 
				UMLModel finalActivityModel = null;
				if (child == null) {
					finalActivityModel = new FinalActivityModel();
				}
				else {
					finalActivityModel = child;
				}
				//				this.behaviorActivityList.add(finalActivityModel);
				//				CollaborationModel collaborationModel = new CollaborationModel();
				to1.setRefModel(finalActivityModel);
				finalActivityModel.setName(name);
				finalActivityModel.setTreeModel(to1);
				to1.setParent(uMLTreeParentModel);
				this.addBehaviorActivityList(to1);
			}
			else if (modelType == 11) {
				to1 = new UMLTreeModel(name);
				UMLModel finalActiionModel = null;
				if (child == null) {
					finalActiionModel = new FinalActiionModel();
				}
				else {
					finalActiionModel = child;
				}
				//				CollaborationModel collaborationModel = new CollaborationModel();
				to1.setRefModel(finalActiionModel);
				finalActiionModel.setName(name);
				finalActiionModel.setTreeModel(to1);
				to1.setParent(uMLTreeParentModel);
			}
			else if (modelType == 12) {
				to1 = new UMLTreeModel(name);
				UMLModel sendModel = null;
				if (child == null) {
					sendModel = new SendModel();
				}
				else {
					sendModel = child;
				}
				//				CollaborationModel collaborationModel = new CollaborationModel();
				to1.setRefModel(sendModel);
				sendModel.setName(name);
				sendModel.setTreeModel(to1);
				to1.setParent(uMLTreeParentModel);
			}
			else if (modelType == 13) {
				to1 = new UMLTreeModel(name);
				UMLModel receiveModel = null;
				if (child == null) {
					receiveModel = new ReceiveModel();
				}
				else {
					receiveModel = child;
				}
				//				CollaborationModel collaborationModel = new CollaborationModel();
				to1.setRefModel(receiveModel);
				receiveModel.setName(name);
				receiveModel.setTreeModel(to1);
				to1.setParent(uMLTreeParentModel);
			}
			else if (modelType == 14) {
				//				to1 = new UMLTreeModel(name);//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
				UMLModel initialModel = null;
				if (child == null) {
					initialModel = new InitialModel();
				}
				else {
					initialModel = child;
				}
				//				CollaborationModel collaborationModel = new CollaborationModel();
				//				to1.setRefModel(initialModel);//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
				initialModel.setName(name);
				//				initialModel.setTreeModel(to1);//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
				//				to1.setParent(uMLTreeParentModel);//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
			}
			else if (modelType == 15) {
				//				to1 = new UMLTreeModel(name);//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
				UMLModel finalModel = null;
				if (child == null) {
					finalModel = new FinalModel();
				}
				else {
					finalModel = child;
				}
				//				CollaborationModel collaborationModel = new CollaborationModel();
				//				to1.setRefModel(finalModel);//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
				finalModel.setName(name);
				//				finalModel.setTreeModel(to1);//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
				//				to1.setParent(uMLTreeParentModel);//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
			}
			else if (modelType == 16) {
				//				to1 = new UMLTreeModel(name);//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
				UMLModel flowFinalModel = null;
				if (child == null) {
					flowFinalModel = new FlowFinalModel();
				}
				else {
					flowFinalModel = child;
				}
				//				CollaborationModel collaborationModel = new CollaborationModel();
				//				to1.setRefModel(flowFinalModel);//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint
				flowFinalModel.setName(name);
				//				flowFinalModel.setTreeModel(to1);//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
				//				to1.setParent(uMLTreeParentModel);//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
			}
			else if (modelType == 17) {
				//				to1 = new UMLTreeModel(name);//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
				UMLModel synchModel = null;
				if (child == null) {
					synchModel = new SynchModel();
				}
				else {
					synchModel = child;
				}
				//				CollaborationModel collaborationModel = new CollaborationModel();
				//				to1.setRefModel(synchModel);//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
				synchModel.setName(name);
				//				synchModel.setTreeModel(to1);//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
				//				to1.setParent(uMLTreeParentModel);//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
			}
			else if (modelType == 18) {
				//				to1 = new UMLTreeModel(name);//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
				UMLModel decisionModel = null;
				if (child == null) {
					decisionModel = new DecisionModel();
				}
				else {
					decisionModel = child;
				}
				//				CollaborationModel collaborationModel = new CollaborationModel();
				//				to1.setRefModel(decisionModel);//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
				decisionModel.setName(name);
				//				decisionModel.setTreeModel(to1);//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
				//				to1.setParent(uMLTreeParentModel);//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
			}
			else if (modelType == 19) {
				to1 = new UMLTreeModel(name);
				UMLModel finalObjectNodeModel = null;
				if (child == null) {
					finalObjectNodeModel = new FinalObjectNodeModel();
				}
				else {
					finalObjectNodeModel = child;
				}
				//				CollaborationModel collaborationModel = new CollaborationModel();
				to1.setRefModel(finalObjectNodeModel);
				finalObjectNodeModel.setName(name);
				finalObjectNodeModel.setTreeModel(to1);
				to1.setParent(uMLTreeParentModel);
			}
			else if (modelType == 20) {
				to1 = new UMLTreeModel(name);
				UMLModel centralBufferNodeModel = null;
				if (child == null) {
					centralBufferNodeModel = new CentralBufferNodeModel();
				}
				else {
					centralBufferNodeModel = child;
				}
				//				CollaborationModel collaborationModel = new CollaborationModel();
				to1.setRefModel(centralBufferNodeModel);
				centralBufferNodeModel.setName(name);
				centralBufferNodeModel.setTreeModel(to1);
				to1.setParent(uMLTreeParentModel);
			}
			else if (modelType == 21) {
				to1 = new UMLTreeModel(name);
				UMLModel dataStoreModel = null;
				if (child == null) {
					dataStoreModel = new DataStoreModel();
				}
				else {
					dataStoreModel = child;
				}
				//				CollaborationModel collaborationModel = new CollaborationModel();
				to1.setRefModel(dataStoreModel);
				dataStoreModel.setName(name);
				dataStoreModel.setTreeModel(to1);
				to1.setParent(uMLTreeParentModel);
			}
			else if (modelType == 22) {
				//PKY 08071601 S 바운더리 트리추가안되도록 하는 부분 수정하였으나 통합하면서 누락된부분 추가
				//				to1 = new UMLTreeModel(name);//PKY 08070904 S 바운더리 swimlane 모델 브라우져에 추가안되도록 수정
				UMLModel hPartitionModel = null;
				if (child == null) {
					hPartitionModel = new HPartitionModel();
				}
				else {
					hPartitionModel = child;
				}
				//				CollaborationModel collaborationModel = new CollaborationModel();
				//				to1.setRefModel(hPartitionModel);//PKY 08070904 S 바운더리 swimlane 모델 브라우져에 추가안되도록 수정
				hPartitionModel.setName(name);
				//				hPartitionModel.setTreeModel(to1);//PKY 08070904 S 바운더리 swimlane 모델 브라우져에 추가안되도록 수정
				//				to1.setParent(uMLTreeParentModel);//PKY 08070904 S 바운더리 swimlane 모델 브라우져에 추가안되도록 수정
				//PKY 08071601 E 바운더리 트리추가안되도록 하는 부분 수정하였으나 통합하면서 누락된부분 추가

			}
			else if (modelType == 23) {
				to1 = new UMLTreeModel(name);
				UMLModel vPartitionModel = null;
				if (child == null) {
					vPartitionModel = new VPartitionModel();
				}
				else {
					vPartitionModel = child;
				}
				//				CollaborationModel collaborationModel = new CollaborationModel();
				to1.setRefModel(vPartitionModel);
				vPartitionModel.setName(name);
				vPartitionModel.setTreeModel(to1);
				to1.setParent(uMLTreeParentModel);
			}
			else if (modelType == 24) {
				//				to1 = new UMLTreeModel(name);//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
				UMLModel hForkJoinModel = null;
				if (child == null) {
					hForkJoinModel = new HForkJoinModel();
				}
				else {
					hForkJoinModel = child;
				}
				//				CollaborationModel collaborationModel = new CollaborationModel();
				//				to1.setRefModel(hForkJoinModel);//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
				hForkJoinModel.setName(name);
				//				hForkJoinModel.setTreeModel(to1);//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
				//				to1.setParent(uMLTreeParentModel);//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
			}
			else if (modelType == 25) {
				//				to1 = new UMLTreeModel(name);//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
				UMLModel vForkJoinModel = null;
				if (child == null) {
					vForkJoinModel = new VForkJoinModel();
				}
				else {
					vForkJoinModel = child;
				}
				//				CollaborationModel collaborationModel = new CollaborationModel();
				//				to1.setRefModel(vForkJoinModel);//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
				vForkJoinModel.setName(name);
				//				vForkJoinModel.setTreeModel(to1);//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
				//				to1.setParent(uMLTreeParentModel);//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
			}
			else if (modelType == 26) {
				to1 = new StrcuturedActivityPackageTreeModel(name);
				UMLModel finalStrcuturedActivityModel = null;
				if (child == null) {
					finalStrcuturedActivityModel = new FinalStrcuturedActivityModel();
				}
				else {
					N3EditorDiagramModel n3EditorDiagramModel =
						this.addUMLDiagram(name, (UMLTreeParentModel)to1, null, 1, false,-1);
					((FinalStrcuturedActivityModel)child).setN3EditorDiagramModel(n3EditorDiagramModel);
					n3EditorDiagramModel.setDiagramType(9);//PKY 08061801 S StrcuturedActivity 생성 시 다이어그램 하위 타입 지정하도록 수정
					finalStrcuturedActivityModel = child;
				}
				//				this.addBehaviorActivityList(finalStrcuturedActivityModel);
				//				CollaborationModel collaborationModel = new CollaborationModel();
				to1.setRefModel(finalStrcuturedActivityModel);
				finalStrcuturedActivityModel.setName(name);
				finalStrcuturedActivityModel.setTreeModel(to1);
				to1.setParent(uMLTreeParentModel);
				this.addBehaviorActivityList(to1);
			}
			else if (modelType == 27) {
				to1 = new UMLTreeModel(name);
				UMLModel partModel = null;
				if (child == null) {
					partModel = new PartModel();
				}
				else {
					partModel = child;
				}
				//				CollaborationModel collaborationModel = new CollaborationModel();
				to1.setRefModel(partModel);
				partModel.setName(name);
				partModel.setTreeModel(to1);
				to1.setParent(uMLTreeParentModel);
			}
			else if (modelType == 50) {

			}
			else if (modelType == 28) {

			}
			else if (modelType == 29) {
				to1 = new StrcuturedPackageTreeModel(name);//2008040203 PKY S 
				UMLModel componentModel = null;
				if (child == null) {
					componentModel = new ComponentModel();
				}
				else {
					componentModel = child;
				}
				//				CollaborationModel collaborationModel = new CollaborationModel();

				N3EditorDiagramModel n3EditorDiagramModel =
					this.addUMLDiagram(name+" diagram", (UMLTreeParentModel)to1, null, 1, false,diagramType);
				((ComponentModel)componentModel).setN3EditorDiagramModel(n3EditorDiagramModel);
				to1.setRefModel(componentModel);
				componentModel.setName(name);
				componentModel.setTreeModel(to1);
				to1.setParent(uMLTreeParentModel);
				TreeSimpleFactory ts1 = new TreeSimpleFactory();
				ComponentModel n = (ComponentModel)ts1.createModle(componentModel);
				n.setN3EditorDiagramModel(n3EditorDiagramModel);
				
				System.out.println("id1=====>"+componentModel.getUMLDataModel().getId());
				System.out.println("id2=====>"+n.getUMLDataModel().getId());
				if(child instanceof AtomicComponentModel){
					componentModel.setStereotype("atomic");
					n3EditorDiagramModel.setDtype(2);
					n.setSize(new Dimension(200,200));
//					this.createNewProject((AtomicComponentModel)componentModel);
					n.setCmpFolder(componentModel.getCmpFolder());
					((AtomicComponentModel)child).setimplementLangProp(String.valueOf(child.lang ));
					((AtomicComponentModel)child).setcompilerProp(String.valueOf(child.lang ));
					//						n3EditorDiagramModel.setDiagramType(2);

					//					n3EditorDiagramModel.setDiagramType(2);
				}
				else{
					n.setSize(new Dimension(400,400));
					componentModel.setStereotype("composite");
					n3EditorDiagramModel.setDtype(1);
					//					n3EditorDiagramModel.setDiagramType(1);
				}
				n3EditorDiagramModel.addChild(n);
			}
			else if (modelType == 30) {
				to1 = new UMLTreeModel(name);
				UMLModel artfifactModel = null;
				if (child == null) {
					artfifactModel = new ArtfifactModel();
				}
				else {
					artfifactModel = child;
				}
				//				CollaborationModel collaborationModel = new CollaborationModel();
				to1.setRefModel(artfifactModel);
				artfifactModel.setName(name);
				artfifactModel.setTreeModel(to1);
				to1.setParent(uMLTreeParentModel);
			}
			else if (modelType == 31) {

			}
			else if (modelType == 32) {

			}
			else if (modelType == 33) {

			}
			else if (modelType == 34) {

			}
			else if (modelType == 36) {

			}
			else if (modelType == 35) {
				//				to1 = new StrcuturedPackageTreeModel(name);//2008040203 PKY S 
				//				UMLModel nodeModel = null;
				//				if (child == null) {
				//					nodeModel = new NodeModel();
				//				}
				//				else {
				//					nodeModel = child;
				//				}
				//				//				this.addBehaviorActivityList(finalStrcuturedActivityModel);
				//				//				CollaborationModel collaborationModel = new CollaborationModel();
				//				to1.setRefModel(nodeModel);
				//				nodeModel.setName(name);
				//				nodeModel.setTreeModel(to1);
				//				to1.setParent(uMLTreeParentModel);
				//				this.addBehaviorActivityList(to1);
			}
			else if (modelType == 37) {
				//				to1 = new UMLTreeModel(name);
				//				UMLModel deviceModel = null;
				//				if (child == null) {
				//					deviceModel = new DeviceModel();
				//				}
				//				else {
				//					deviceModel = child;
				//				}
				//				//				this.addBehaviorActivityList(finalStrcuturedActivityModel);
				//				//				CollaborationModel collaborationModel = new CollaborationModel();
				//				to1.setRefModel(deviceModel);
				//				deviceModel.setName(name);
				//				deviceModel.setTreeModel(to1);
				//				to1.setParent(uMLTreeParentModel);
				//				this.addBehaviorActivityList(to1);
			}
			else if (modelType == 38) {
				//				to1 = new UMLTreeModel(name);
				//				UMLModel executionEnvironmentModel = null;
				//				if (child == null) {
				//					executionEnvironmentModel = new ExecutionEnvironmentModel();
				//				}
				//				else {
				//					executionEnvironmentModel = child;
				//				}
				//				//				this.addBehaviorActivityList(finalStrcuturedActivityModel);
				//				//				CollaborationModel collaborationModel = new CollaborationModel();
				//				to1.setRefModel(executionEnvironmentModel);
				//				executionEnvironmentModel.setName(name);
				//				executionEnvironmentModel.setTreeModel(to1);
				//				to1.setParent(uMLTreeParentModel);
				//				this.addBehaviorActivityList(to1);
			}
			else if (modelType == 39) {
				//				to1 = new UMLTreeModel(name);
				//				UMLModel deploymentSpecificationModel = null;
				//				if (child == null) {
				//					deploymentSpecificationModel = new DeploymentSpecificationModel();
				//				}
				//				else {
				//					deploymentSpecificationModel = child;
				//				}
				//				//				this.addBehaviorActivityList(finalStrcuturedActivityModel);
				//				//				CollaborationModel collaborationModel = new CollaborationModel();
				//				to1.setRefModel(deploymentSpecificationModel);
				//				deploymentSpecificationModel.setName(name);
				//				deploymentSpecificationModel.setTreeModel(to1);
				//				to1.setParent(uMLTreeParentModel);
				//				this.addBehaviorActivityList(to1);
			}
			else if (modelType == 1000) {



				to1 = new StrcuturedPackageTreeModel(name);//2008040203 PKY S 
				AtomicComponentModel componentModel = null;
				child.setName(name);
				String path = this.getProjectPath();
				UMLModel newModel = (UMLModel)child.clone();
				componentModel = (AtomicComponentModel)newModel;
				((AtomicComponentModel)child).setCoreUMLTreeModel(to1);
				child.getUMLTreeModel().setName(name);
				((AtomicComponentModel)child).setName(name);
				N3EditorDiagramModel n3EditorDiagramModel =
					this.addUMLDiagram(name+" diagram", (UMLTreeParentModel)to1, null, 1, false,diagramType);
				((ComponentModel)componentModel).setN3EditorDiagramModel(n3EditorDiagramModel);
				to1.setRefModel(componentModel);
				componentModel.setName(name);
				componentModel.setTreeModel(to1);
				to1.setParent(uMLTreeParentModel);
				TreeSimpleFactory ts1 = new TreeSimpleFactory();
				ComponentModel n = (ComponentModel)ts1.createModle(componentModel);
				n.setN3EditorDiagramModel(n3EditorDiagramModel);
				n.setSize(new Dimension(200,200));
				System.out.println("id1=====>"+componentModel.getUMLDataModel().getId());
				System.out.println("id2=====>"+n.getUMLDataModel().getId());
				if(newModel instanceof AtomicComponentModel){
					componentModel.setStereotype("atomic");
					n3EditorDiagramModel.setDtype(2);
					componentModel.lang = child.lang;
					this.createNewProject((AtomicComponentModel)componentModel);
					((AtomicComponentModel)child).setCmpFolder(componentModel.getCmpFolder());
					((AtomicComponentModel)child).setimplementLangProp(String.valueOf(child.lang ));
					((AtomicComponentModel)child).setcompilerProp(String.valueOf(child.lang ));
				}
				else{	
					componentModel.setStereotype("composite");
					n3EditorDiagramModel.setDtype(1);
				}
				n3EditorDiagramModel.addChild(n);
			}
			else if (modelType == 1100) {

				boolean isOverlapping = ProjectManager.getInstance().isOverlapping((UMLTreeParentModel)uMLTreeParentModel,  name);
				//				String name =treeObject.getName();
				String oldName = name;
				if(isOverlapping) {
					for(int i=0;i<1000;i++){
						String tempname = name +i ;
						isOverlapping = ProjectManager.getInstance().isOverlapping(uMLTreeParentModel,  tempname);
						if(!isOverlapping){
							name = tempname;
							break;
						}
					}

				}

				UMLTreeParentModel to2 = new StrcuturedPackageTreeModel(name);//2008040203 PKY S 
				UMLModel componentModel = null;
				if (child == null) {
					componentModel = new ComponentModel();
				}
				else {
					if(child instanceof TemplateComponentModel){
						TemplateComponentModel tcm = (TemplateComponentModel)child;
						componentModel = tcm.getAtomicComponentModel();
						if(this.lang!=-1)
						componentModel.lang =  this.lang;
					}
				}
				N3EditorDiagramModel n3EditorDiagramModel =
					this.addUMLDiagram(name+" diagram", (UMLTreeParentModel)to2, null, 1, false,diagramType);
				((ComponentModel)componentModel).setN3EditorDiagramModel(n3EditorDiagramModel);
				to2.setRefModel(componentModel);
				componentModel.setName(name);
				componentModel.setTreeModel(to2);
				to2.setParent(uMLTreeParentModel);
				TreeSimpleFactory ts1 = new TreeSimpleFactory();
				ComponentModel n = (ComponentModel)ts1.createModle(componentModel);
				UMLTreeParentModel upm  = (UMLTreeParentModel)child.getUMLTreeModel();
				n.setN3EditorDiagramModel(n3EditorDiagramModel);
				n.setSize(new Dimension(200,200));
				System.out.println("id1=====>"+componentModel.getUMLDataModel().getId());
				System.out.println("id2=====>"+n.getUMLDataModel().getId());
				componentModel.setStereotype("atomic");
				n3EditorDiagramModel.setDtype(2);
				this.createNewProject((AtomicComponentModel)componentModel);
				((AtomicComponentModel)componentModel).setimplementLangProp(String.valueOf(componentModel.lang ));
				((AtomicComponentModel)componentModel).setcompilerProp(String.valueOf(componentModel.lang ));
				System.out.println("cmpPath   nnn=====>"+n.getCmpFolder());
				n.setCmpFolder(n.getCmpFolder());
				String cmpPath = n.getCmpFolder();
				OPRoSExeEnvironmentElementModel aa = (OPRoSExeEnvironmentElementModel)((ComponentModel)componentModel).getClassModel().getUMLDataModel().getElementProperty("OPRoSExeEnvironmentElementModel");
				System.out.println("cmpPath=====>"+cmpPath);
				aa.setexeCmpFolderProp(cmpPath);
				n3EditorDiagramModel.addChild(n);
				for(int i=0;i<upm.getChildren().length;i++){
					UMLTreeModel um = (UMLTreeModel)upm.getChildren()[i];
					if(!(um.getRefModel() instanceof PortModel))
						continue;
					PortModel pm1 = (PortModel)um.getRefModel();
					PortModel pm2 = null;
					if(pm1 instanceof MonitoringMethodPortModel){
						pm2 = new MonitoringMethodPortModel(n);
					}
					else if(pm1 instanceof LifecycleMethodPortModel){
						pm2 = new LifecycleMethodPortModel(n);
					}
					else if(pm1 instanceof MethodOutputPortModel){
						pm2 = new MethodOutputPortModel(n);
					}
					else if(pm1 instanceof MethodInputPortModel){
						pm2 = new MethodInputPortModel(n);
					}
					else if(pm1 instanceof DataInputPortModel){
						pm2 = new DataInputPortModel(n);
					}
					else if(pm1 instanceof DataOutputPortModel){
						pm2 = new DataOutputPortModel(n);
					}
					else if(pm1 instanceof EventInputPortModel){
						pm2 = new EventInputPortModel(n);
					}
					else if(pm1 instanceof EventOutputPortModel){
						pm2 = new EventOutputPortModel(n);
					}
					pm2.getElementLabelModel().setReadOnly(true);
					UMLTreeModel port = new UMLTreeModel(pm1.getAttachElementLabelModel().getLabelContents());
					to2.addChild(port);
					port.setRefModel(pm2);
					pm2.getElementLabelModel().setTreeModel(port);
					pm2.getElementLabelModel().setPortId(pm2.getID());
					pm2.getUMLDataModel().setId(pm2.getID());
					pm2.getAttachElementLabelModel().setText(pm1.getAttachElementLabelModel().getLabelContents());
					pm2.getElementLabelModel().setTreeModel(port);
					n.getPortManager().getPorts().add(pm2);
					pm2.setType(pm1.getType());
					String typeref = pm1.getTypeRef();
					String queueingPolicy = pm1.getQueueingPolicy();
					String portQueueSize = pm1.getQueueSize();
					pm2.setTypeRef(typeref);
					pm2.setQueueingPolicy(queueingPolicy);
					pm2.setQueueSize(portQueueSize);
					System.out.println("typeRef===================>"+typeref);
					System.out.println("queueingPolicy===================>"+queueingPolicy);
					System.out.println("portQueueSize===================>"+portQueueSize);
					ProjectManager.getInstance().getModelBrowser().refresh(to1);
					n.createPort2(pm2, n3EditorDiagramModel);
					pm2.setPortContainerModel(n);

				}
				ProjectManager.getInstance().templateCmp.put(oldName,to2);
				System.out.println("templateCmp==================================>"+oldName);
				CompAssemManager.getInstance().autoLayoutPort(n.getPortManager().getPorts());
				to1 = to2;
			}


			//			else if(modelType==27){
			//				to1 = new UMLTreeModel(name);
			//				UMLModel vForkJoinModel = null;
			//				if(child==null){
			//					vForkJoinModel = new VForkJoinModel();
			//				}
			//				else{
			//					vForkJoinModel = child;
			//				}
			////				CollaborationModel collaborationModel = new CollaborationModel();
			//				to1.setRefModel(vForkJoinModel);
			//				vForkJoinModel.setName(name);
			//				vForkJoinModel.setTreeModel(to1);
			//				
			//				to1.setParent(uMLTreeParentModel);
			//
			//			}
			if (to1 != null) {
				uMLTreeParentModel.addChild(to1);
			}
			//			UMLTreeModel to1 = null;
			//			if(modelType!=0){
			//				 to1 = new UMLTreeModel(name);
			//				
			//			}
			//			else{
			//				to1 = new PackageTreeModel(name);
			////				child.setTreeModel((UMLTreeModel)to1);
			//			}
			//			if(child!=null){
			//				child.setTreeModel(to1);
			//				child.setName(name);
			//			}
			//			ProjectManager.getInstance().getCopyName(uMLTreeParentModel, child);
			//PKY 08071601 S 바운더리 트리추가안되도록 하는 부분 수정하였으나 통합하면서 누락된부분 추가

			ProjectManager.getInstance().getModelBrowser().refresh(uMLTreeParentModel);
			ProjectManager.getInstance().getModelBrowser().expend(tp);
			//PKY 08082201 S 선을 선택한다음 객체를 생성하면 에러메세지 발생
			//			if(to1!=null)//PKY 08070904 S 바운더리 swimlane 모델 브라우져에 추가안되도록 수정
			//				ProjectManager.getInstance().getModelBrowser().select(to1);
			//PKY 08082201 E 선을 선택한다음 객체를 생성하면 에러메세지 발생

			if (modelType != 0 && modelType != 40 && modelType != 50 && modelType != 28) {
				this.getTypeModel().add(to1);
			}
			//PKY 08071601 E 바운더리 트리추가안되도록 하는 부분 수정하였으나 통합하면서 누락된부분 추가
			this.newMakeModel = to1;			// V1.01 WJH E 080519 추가
		}
	}

//	//	V1.01 WJH E 080519 S 함수 추가
//	public UMLTreeModel addUMLModel(String name, UMLTreeParentModel tp, UMLModel child, int modelType,int diagramType, String id) {
//
//		addUMLModel(name,  tp,  child,  modelType, diagramType);
//
//		if(newMakeModel != null)
//			((UMLModel)newMakeModel.getRefModel()).getUMLDataModel().setId(id);
//
//		//		newMakeModel = null;
//		return newMakeModel;
//	}
//	//	V1.01 WJH E 080519 E 함수 추가  
//
//	/*
//	 * 다이어그램  생성
//	 */
//
//	public N3EditorDiagramModel addUMLDiagram(String name, UMLTreeParentModel tp, UMLEditor child,
//			int modelType, boolean isOpen,int diagramType) {
//
//		//		UMLTreeModel treeModel = parent.getUMLTreeModel().getParent();
//		//default
//		try{
//			if (tp != null && tp instanceof UMLTreeParentModel) {
//				UMLTreeParentModel uMLTreeParentModel = (UMLTreeParentModel)tp;
//				UMLTreeModel to1 = null;
//				if (modelType == 1) {
//					if (name == null) {
//						name = this.getDefaultName(1, uMLTreeParentModel);
//					}
//					to1 = new UMLTreeModel(name);
//					uMLTreeParentModel.addChild(to1);
//
//				}
//				if (child == null && isOpen) {
//					IWorkbenchWindow workbenchWindow = ProjectManager.getInstance().at.getWorkbench().getActiveWorkbenchWindow();
//					IEditorInput input = null;
//					//PKY 08080501 S 다이어그램 전체 부분에서 "쓰임새도"라고 나오는 문제 수정
//					if(diagramType==1)
//						input = new LogicEditorInput(new Path(N3Messages.DIALOG_PACKAGE));
//					else if(diagramType==2)
//						input = new LogicEditorInput(new Path(N3Messages.PALETTE_TITLE_CLASS));
//					else if(diagramType==3)
//						input = new LogicEditorInput(new Path(N3Messages.DIALOG_OBJECT));
//					else if(diagramType==4)
//						input = new LogicEditorInput(new Path(N3Messages.PALETTE_TITLE_COMPOSITE));
//					else if(diagramType==5)
//						input = new LogicEditorInput(new Path(N3Messages.PALETTE_TITLE_COMPONENT));
//					else if(diagramType==6)
//						input = new LogicEditorInput(new Path(N3Messages.PALETTE_TITLE_DEPLOYMENT));
//					else if(diagramType==7)
//						input = new LogicEditorInput(new Path(N3Messages.DIALOG_STRUCTURAL));
//					else if(diagramType==8)
//						input = new LogicEditorInput(new Path(N3Messages.PALETTE_TITLE_USECASE));
//					else if(diagramType==9)
//						input = new LogicEditorInput(new Path(N3Messages.PALETTE_TITLE_ACTIVITY));
//					else if(diagramType==10)
//						input = new LogicEditorInput(new Path(N3Messages.PALETTE_TITLE_STATE));
//					else if(diagramType==11)
//						input = new LogicEditorInput(new Path(N3Messages.PALETTE_TITLE_COMMUNICATION));
//					else if(diagramType==12)
//						input = new LogicEditorInput(new Path(N3Messages.PALETTE_TITLE_INTERACTION));
//					else if(diagramType==13)
//						input = new LogicEditorInput(new Path(N3Messages.PALETTE_TITLE_TIMING));
//					else if(diagramType==14)
//						input = new LogicEditorInput(new Path(N3Messages.DIALOG_INTERACTION_OVERVIEW));
//					else if(diagramType==15)
//						input = new LogicEditorInput(new Path(N3Messages.DIALOG_BEHAVIORAL));
//					else if(diagramType==17)
//						input = new LogicEditorInput(new Path(N3Messages.PALETTE_REQUIRMENT));
//					else  
//						input = new LogicEditorInput(new Path("Diagram"));
//					//PKY 08080501 E 다이어그램 전체 부분에서 "쓰임새도"라고 나오는 문제 수정
//					try {
//
//						tempDiagramType = diagramType;
//						UMLEditor u = (UMLEditor)workbenchWindow.getActivePage().openEditor(input, UMLEditor.ID, true);
//						u.setTitleName(name);
//						if(u.getDiagram().getDiagramType()==-1){
//							u.getDiagram().setDiagramType(diagramType);
//							//							N3Plugin.getDefault().open(diagramType);
//						}
//						to1.setRefModel(u.getDiagram());
//						u.getDiagram().setName(name);
//						u.getDiagram().setTreeModel(to1);
//						to1.setParent(uMLTreeParentModel);
//						u.getDiagram().setIEditorInput(input);
//						ProjectManager.getInstance().getModelBrowser().refresh(uMLTreeParentModel);
//						ProjectManager.getInstance().getModelBrowser().expend(tp);
//						ProjectManager.getInstance().getModelBrowser().select(to1);
//						this.tempDiagram = u.getDiagram();				// V1.03 WJH E 080526 추가
//
//						//PKY 08061101 S 패키지 하위에 다이어그램을 삭제 후 다이어그램을 재 생성하여 더블클릭하면 오픈되지 않던 문제 수정
//						if(uMLTreeParentModel instanceof PackageTreeModel){
//							if(uMLTreeParentModel.getRefModel()!=null)
//								if(uMLTreeParentModel.getRefModel() instanceof FinalPackageModel){
//									FinalPackageModel model=(FinalPackageModel)uMLTreeParentModel.getRefModel();
//									if(to1 instanceof UMLTreeModel){
//										if(to1.getRefModel()!=null){
//											if(to1.getRefModel() instanceof N3EditorDiagramModel)
//												model.setN3EditorDiagramModel((N3EditorDiagramModel)to1.getRefModel());		
//										}
//									}
//
//								}
//						}
//						//PKY 08061101 E 패키지 하위에 다이어그램을 삭제 후 다이어그램을 재 생성하여 더블클릭하면 오픈되지 않던 문제 수정
//
//
//						return null;
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//				else {
//					N3EditorDiagramModel n3EditorDiagramModel = new N3EditorDiagramModel();
//					IEditorInput input = null;
//					//PKY 08080501 S 다이어그램 전체 부분에서 "쓰임새도"라고 나오는 문제 수정
//					if(diagramType==1)
//						input = new LogicEditorInput(new Path(N3Messages.DIALOG_PACKAGE));
//					else if(diagramType==2)
//						input = new LogicEditorInput(new Path(N3Messages.PALETTE_TITLE_CLASS));
//					else if(diagramType==3)
//						input = new LogicEditorInput(new Path(N3Messages.DIALOG_OBJECT));
//					else if(diagramType==4)
//						input = new LogicEditorInput(new Path(N3Messages.PALETTE_TITLE_COMPOSITE));
//					else if(diagramType==5)
//						input = new LogicEditorInput(new Path(N3Messages.PALETTE_TITLE_COMPONENT));
//					else if(diagramType==6)
//						input = new LogicEditorInput(new Path(N3Messages.PALETTE_TITLE_DEPLOYMENT));
//					else if(diagramType==7)
//						input = new LogicEditorInput(new Path(N3Messages.DIALOG_STRUCTURAL));
//					else if(diagramType==8)
//						input = new LogicEditorInput(new Path(N3Messages.PALETTE_TITLE_USECASE));
//					else if(diagramType==9)
//						input = new LogicEditorInput(new Path(N3Messages.PALETTE_TITLE_ACTIVITY));
//					else if(diagramType==10)
//						input = new LogicEditorInput(new Path(N3Messages.PALETTE_TITLE_STATE));
//					else if(diagramType==11)
//						input = new LogicEditorInput(new Path(N3Messages.PALETTE_TITLE_COMMUNICATION));
//					else if(diagramType==12)
//						input = new LogicEditorInput(new Path(N3Messages.PALETTE_TITLE_INTERACTION));
//					else if(diagramType==13)
//						input = new LogicEditorInput(new Path(N3Messages.PALETTE_TITLE_TIMING));
//					else if(diagramType==14)
//						input = new LogicEditorInput(new Path(N3Messages.DIALOG_INTERACTION_OVERVIEW));
//					else if(diagramType==15)
//						input = new LogicEditorInput(new Path(N3Messages.DIALOG_BEHAVIORAL));
//					else if(diagramType==17)
//						input = new LogicEditorInput(new Path(N3Messages.PALETTE_REQUIRMENT));
//					else  
//						input = new LogicEditorInput(new Path("Diagram"));
//					//PKY 08080501 E 다이어그램 전체 부분에서 "쓰임새도"라고 나오는 문제 수정
//					to1.setRefModel(n3EditorDiagramModel);
//					n3EditorDiagramModel.setName(name);
//					n3EditorDiagramModel.setTreeModel(to1);
//					to1.setParent(uMLTreeParentModel);
//					n3EditorDiagramModel.setIEditorInput(input);
//					n3EditorDiagramModel.setDiagramType(diagramType);
//					return n3EditorDiagramModel;
//				}
//			}
//		}
//		catch(Exception e1){
//			e1.printStackTrace();
//			System.out.println("application:"+application);
//			try{
//				FileWriter fw = new FileWriter(new File("c:\\err1.log"));
//				PrintWriter bw=new PrintWriter(fw);
//
//				e1.printStackTrace(bw);
//				//				bw.write(e1.printStackTrace());
//				bw.close();
//				//				saveValue = -1;
//
//			}
//			catch(Exception e){
//				e.printStackTrace();
//
//			}
//		}
//		return null;
//	}
	

	public int getDeleteModelType(UMLModel child){
		int modelType = -1;
		if (child instanceof FinalPackageModel) {
			modelType = 0;
		}
		if (child instanceof N3EditorDiagramModel) {
			modelType = 1;
		}
		else if (child instanceof UseCaseModel) {
			return 2;
		}
		else if (child instanceof FinalActorModel) {
			return 3;
		}
		else if (child instanceof FinalBoundryModel) {
			return 4;
		}
		else if (child instanceof CollaborationModel) {
			return 5;
		}
		else if (child instanceof FinalClassModel) {
			return 6;
		}
		else if (child instanceof InterfaceModel) {
			return 7;
		}
		else if (child instanceof EnumerationModel) {
			return 8;
		}
		else if (child instanceof ExceptionModel) {
			return 9;
		}
		else if (child instanceof FinalStrcuturedActivityModel) {
			return 26;
		}
		else if (child instanceof FinalActivityModel) {
			return 10;
		}
		else if (child instanceof FinalActiionModel) {
			return 11;
		}
		else if (child instanceof SendModel) {
			return 12;
		}
		else if (child instanceof ReceiveModel) {
			return 13;
		}
		else if (child instanceof InitialModel) {
			return 14;
		}
		else if (child instanceof FinalModel) {
			return 15;
		}
		else if (child instanceof FlowFinalModel) {
			return 16;
		}
		else if (child instanceof SynchModel) {
			return 17;
		}
		else if (child instanceof DecisionModel) {
			return 18;
		}
		else if (child instanceof FinalObjectNodeModel) {
			return 19;
		}
		else if (child instanceof CentralBufferNodeModel) {
			return 20;
		}
		else if (child instanceof DataStoreModel) {
			return 21;
		}
		else if (child instanceof HPartitionModel) {
			return 22;
		}
		else if (child instanceof VPartitionModel) {
			return 23;
		}
		else if (child instanceof HForkJoinModel) {
			return 24;
		}
		else if (child instanceof VForkJoinModel) {
			return 25;
		}
		else if (child instanceof PartModel) {
			return 27;
		}


		else if (child instanceof ComponentModel) {
			return 29;
		}
		else if (child instanceof ArtfifactModel) {
			return 30;
		}



		//		else if (child instanceof FrameModel) {
		//		return 39;
		//		}
		//		else if (child instanceof StateLifelineModel) {
		//		return 40;
		//		}

		else if (child instanceof UMLNoteModel) {
			return 500;
		}

		else if (child instanceof FrameModel) {
			return 502;
		}
		//20080325 PKY S PORT 삭제 문제
		else if (child instanceof PortModel) {
			return 503;
		}
		//20080325 PKY E PORT 삭제 문제
		//20080327 PKY S Partiton 삭제 문제
		else if(child instanceof SubPartitonModel){
			return 504;
		}
		//2008041601PKY S

		//2008041601PKY E
		//2008041602PKY S


		//2008043001 PKY E EndPoint 삭제안되는 문제
		//20080721IJS

		return modelType;
	}

	public int getCopyModelType(UMLModel child) {
		if (child == null)
			return -1;
		return this.getModelType(child, -1);
	}

	public String getDiagramPath(int type){
		StringBuffer path = new StringBuffer("");
		if(type==1){//패키지 다이어그램
			path.append("package diagram");
		}//클래스 다이어그램
		else if(type==2){
			path.append("class diagram");
		}//객체 다이어그램
		else if(type==3){
			path.append("object diagram");
		}//합성 다이어그램
		else if(type==4){
			path.append("composite diagram");
		}//컴포넌트 다이어그램
		else if(type==5){
			path.append("component diagram");
		}//배치 다이어그램
		else if(type==6){
			path.append("deployment diagram");
		}//구조 다이어그램
		else if(type==7){
			path.append("composite diagram");
		}//유즈케이스  다이어그램
		else if(type==8){
			path.append("usecase diagram");
		}//액티비티 다이어그램
		else if(type==9){
			path.append("activity diagram");
		}//상태 다이어그램
		else if(type==10){
			path.append("state diagram");
		}//커뮤니케이션 다이어그램
		else if(type==11){
			path.append("communication diagram");
		}//시퀀스 다이어그램
		else if(type==12){
			path.append("interaction diagram");
		}//다이밍 다이어그램
		else if(type==13){
			path.append("timing diagram");
		}//인터렉션 오버뷰 다이어그램
		else if(type==14){
			path.append("interactionoverview diagram");//KDI 20080710 path.append("interaction diagram");
		}//행위 다이어그램
		else if(type==15){
			path.append("interaction diagram");
		}//Initial
		return path.toString();
	}

	public String getModelTypeName(int type) {

		StringBuffer path = new StringBuffer("");
		if(type==0){//유즈케이스
			path.append("Package");
		}//액터
		if(type==1){//유즈케이스
			path.append("Diagram");
		}//액터
		if(type==2){//유즈케이스
			path.append("Usecase");
		}//액터
		else if(type==3){
			path.append("Actor");
		}//바운더
		else if(type==4){
			path.append("Boundary");
		}//컬레보레이션
		else if(type==5){
			path.append("Collaboration");
		}//클래스
		else if(type==6){
			path.append("Class");
		}//인터페이스
		else if(type==7){
			path.append("Interface");
		}//Enumeration

		else if(type==8){
			path.append("Enumeration");
		}//Exception
		else if(type==9){
			path.append("Exception");
		}//액티비티
		else if(type==10){
			path.append("Activity");
		}//액션
		else if(type==11){
			path.append("Action");
		}//Send
		else if(type==12){
			path.append("Send");
		}//Receive
		else if(type==13){
			path.append("Receive");
		}//Initial
		else if(type==14){
			path.append("Initial");
		}//Final
		else if(type==15){
			path.append("Final");
		}//FlowFinal
		else if(type==16){
			path.append("Flow_final");
		}//Synch
		else if(type==17){
			path.append("Synch");
		}//Decision
		else if(type==18){
			path.append("Decision");
		}//Object
		else if(type==19){
			path.append("Object");
		}//CentralBufferNode
		else if(type==20){
			path.append("Centralbuffernode");
		}//DataStore
		else if(type==21){
			path.append("Datastore");
		}//Swimlaine
		else if(type==22){
			path.append("Partition");
		}
		else if(type==23){
			path.append("Usecase");
		}//Fork/Join
		else if(type==24){
			path.append("Forkjoin");
		}//Fork/Join
		else if(type==25){
			path.append("Forkjoin");
		}//StrcuturedActivity
		else if(type==26){
			path.append("Structured_activity");
		}//Part
		else if(type==27){
			path.append("Part");
		}//LifeLine
		else if(type==50){
			path.append("LifeLine");
		}//Component
		else if(type==28){
			path.append("LifeLine");
		}//Component
		else if(type==29){
			path.append("Component");
		}//Artfifact
		else if(type==30){
			path.append("Artifact");
		}//State
		else if(type==31){
			path.append("State");
		}//StrcuturedState
		else if(type==32){
			path.append("Submachinestate");
		}//History
		else if(type==33){
			path.append("History");
		}//Terminate
		else if(type==34){
			path.append("Terminate");
		}//Node
		else if(type==35){
			path.append("Node");
		}//EntryPoint
		else if(type==36){
			path.append("Entity");
		}//Device
		else if(type==37){
			path.append("Device");
		}//ExecutionEnvironment
		else if(type==38){
			path.append("Executionenvironment");
		}//DeploymentSpecification
		else if(type==39){
			path.append("Deployment_spec");
		}//StateLifeline
		else if(type==40){
			path.append("Statelifeline");
		}
		else if(type==41){
			path.append("Usecase");
		}
		else if(type==42){
			path.append("tank");
		}

		else if(type==200){
			path.append("basic");
		}
		else if(type==201){
			path.append("alternative");
		}
		else if(type==202){
			path.append("exception");
		}
		else if(type==502){//PKY 08052101 S 컨테이너에서 그룹으로 변경
			path.append("partiton");
		}//PKY 08052101 E 컨테이너에서 그룹으로 변경
		else if(type==600){//PKY 08052101 S 컨테이너에서 그룹으로 변경
			path.append("requirement");
		}
		else if(type==900){//PKY 08052101 S 컨테이너에서 그룹으로 변경
			path.append("NodeItemModel");
		}

		//		else if(type==1004){
		//			path.append("MethodOutputPort");
		//		}
		//		else if(type==1003){
		//			path.append("MethodInputPort");
		//		}
		//		else if(type==1005){
		//			path.append("DataInputPort");
		//		}
		//		else if(type==1006){
		//			path.append("DataOutputPort");
		//		}
		//		else if(type==1007){
		//			path.append("EventInputPort");
		//		}
		//		else if(type==1009){
		//			path.append("EventOutputPort");
		//		}
		//		else if(type==1010){
		//			path.append("req1.gif");
		//		}
		//		else if(type==1011){
		//			path.append("req2.gif");
		//		}


		//		else if(type==600){
		//		path.append("selfMsg");
		//		}
		return path.toString();
	}

	public int getModelTypeName(String type) {

		if(type.equals("Package")){//유즈케이스
			return 0;
		}
		else if(type.equals("Usecase")){//유즈케이스
			return 2;
		}//액터
		else if(type.equals("Actor")){
			return 3;
		}//바운더
		else if(type.equals("Boundary")){
			return 4;
		}//컬레보레이션
		else if(type.equals("Collaboration")){

			return 5;
		}//클래스
		else if(type.equals("Class")){

			return 6;
		}//인터페이스
		else if(type.equals("Interface")){

			return 7;
		}//Enumeration
		else if(type.equals("Enumeration")){

			return 8;
		}//Exception
		else if(type.equals("Exception")){

			return 9;
		}//액티비티
		else if(type.equals("Activity")){

			return 10;
		}//액션
		else if(type.equals("Action")){

			return 11;
		}//Send
		else if(type.equals("Send")){

			return 12;
		}//Receive
		else if(type.equals("Receive")){

			return 13;
		}//Initial
		else if(type.equals("Initial")){

			return 14;
		}//Final
		else if(type.equals("Final")){

			return 15;
		}//FlowFinal
		else if(type.equals("Flow_final")){

			return 16;
		}//Synch
		else if(type.equals("Synch")){

			return 17;
		}//Decision
		else if(type.equals("Decision")){

			return 18;
		}//Object
		else if(type.equals("Object")){

			return 19;
		}//CentralBufferNode
		else if(type.equals("Centralbuffernode")){

			return 20;
		}//DataStore
		else if(type.equals("Datastore")){

			return 21;
		}//Swimlaine
		else if(type.equals("Partition")){

			return 22;
		}
		else if(type.equals("Usecase")){

			return 23;
		}//Fork/Join
		//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
		else if(type.equals("HForkJoin")){

			return 24;
		}//Fork/Join
		else if(type.equals("VForkJoin")){

			return 25;
		}//StrcuturedActivity
		//PKY 08072201 E 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint
		else if(type.equals("Structured_activity")){

			return 26;
		}//Part
		else if(type.equals("Part")){

			return 27;
		}//LifeLine
		else if(type.equals("LifeLine")){

			return 28;
		}//Component
		else if(type.equals("Component")){

			return 29;
		}//Artfifact
		else if(type.equals("Artifact")){

			return 30;
		}//State
		else if(type.equals("State")){

			return 31;
		}//StrcuturedState
		else if(type.equals("Submachinestate")){

			return 32;
		}//History
		else if(type.equals("History")){

			return 33;
		}//Terminate
		else if(type.equals("Terminate")){

			return 34;
		}//Node
		else if(type.equals("Node")){

			return 35;
		}//EntryPoint
		else if(type.equals("Entity")){

			return 36;
		}//Device
		else if(type.equals("Device")){

			return 37;
		}//ExecutionEnvironment
		else if(type.equals("Executionenvironment")){

			return 38;
		}//DeploymentSpecification
		else if(type.equals("Deployment_spec")){

			return 39;
		}//StateLifeline
		else if(type.equals("Statelifeline")){

			return 40;
		}
		else if(type.equals("Note")){

			return 41;
		}
		else if(type.equals("Group")){//PKY 08050701 S Initial 생성 후 저장 불러오기 할경우 노트로 변경되어있는문제

			return 42;
		}
		//PKY 08050701 E Initial 생성 후 저장 불러오기 할경우 노트로 변경되어있는문제
		else if(type.equals("Fragment")){//PKY 08051401 S 시퀀스 FragmentModel저장안되는것 

			return 43;
		}
		//PKY 08051401 E 시퀀스 FragmentModel저장안되는것 

		//PKY 08052101 S 컨테이너에서 그룹으로 변경
		else if(type.equals("partiton")){

			return 44;
		}
		//PKY 08052101 E 컨테이너에서 그룹으로 변경

		//PKY 08052601 S endPoint저장안됨
		else if (type.equals("EndPoint")){

			return 45;
		}
		//PKY 08052601 E endPoint저장안됨
		//PKY 08071601 S 바운더리 트리추가안되도록 하는 부분 수정하였으나 통합하면서 누락된부분 추가
		//PKY 08070904 S 바운더리 swimlane 모델 브라우져에 추가안되도록 수정
		else if (type.equals("Swimlane")){
			return 46;
		}
		//PKY 08070904 E 바운더리 swimlane 모델 브라우져에 추가안되도록 수정
		//PKY 08071601 E 바운더리 트리추가안되도록 하는 부분 수정하였으나 통합하면서 누락된부분 추가
		//20080721IJS
		//PKY 08080501 S Frame 저장이 안되는 문제 
		else if(type.equals("frame")){
			return 47;
		}
		//PKY 08080501 E Frame 저장이 안되는 문제
		//PKY 08081101 S Timing 구조 변경
		else if(type.equals("MessagePoint")){
			return 48;
		}
		//PKY 08081101 E Timing 구조 변경

		else if (type.equals("requirement")){

			return 600;
		}
		else if (type.equals("NodeItemModel")){

			return 900;
		}


		return -1;
	}

	public int getLineModelType(String type) {
		if (type.equals("ExtendLine")){
			return 1000;
		}
		else if (type.equals("IncludeLine")){
			return 1001;
		}
		else if (type.equals("ConnectorLine")){
			return  1002;
		}
		else if (type.equals("MessageAssoicateLine")){
			return  1003;
		}
		else if (type.equals("AssociateLine")){
			return  1004;
		}
		else if (type.equals("GeneralizeLine")){
			return  1005;
		}
		else if (type.equals("RealizeLine")){
			return  1006;
		}
		else if (type.equals("NoteLineModel")||type.equals("NoteLine")){//2008043001 PKY S Note
			return  1007;
		}
		else if (type.equals("DependencyLine")){
			return  1008;
		}
		else if (type.equals("ControlFlowLine")){
			return  1009;
		}
		else if (type.equals("RequiredInterfaceLine")){
			return  1010;
		}
		else if (type.equals("ProvidedInterfaceLine")){
			return  1011;
		}
		else if (type.equals("RoleBindingLine")){
			return  1012;
		}
		else if (type.equals("DelegateLine")){
			return  1013;
		}
		else if (type.equals("OccurrenceLine")){
			return  1014;
		}
		else if (type.equals("RepresentsLine")){
			return  1015;
		}
		else if (type.equals("ManifestLine")){
			return  1016;
		}
		else if (type.equals("DeployLine")){
			return  1017;
		}
		else if (type.equals("TimingMessageLine")){
			return  1018;
		}
		else if (type.equals("CommunicationPathLine")){
			return  1019;
		}
		else if (type.equals("Message")){
			return  1020;
		}
		else if (type.equals("TransitionLine")){
			return  1021;
		}

		else if (type.equals("ImportLine")){
			return  1022;
		}
		else if (type.equals("MergeLine")){
			return  1023;
		}
		else if (type.equals("SelfMessage")){
			return  1024;
		}

		//PKY 08052601 S EditMessage저장안됨
		else if(type.equals("Condition")){
			return 1020;
		}
		//PKY 08052601 E EditMessage저장안됨
		//PKY 08072401 S ObjectFlow모델 추가
		else if(type.equals("ObjectFlowLine")){
			return 1026;
		}
		else {
			return  1025;
		}


	}

	public String getLineModelName(LineModel type) {
		if (type instanceof ExtendLineModel) {
			return "ExtendLine";
		}
		else if (type instanceof IncludeLineModel) {
			return "IncludeLine";
		}
		else if (type instanceof ConnectorLineModel) {
			return "ConnectorLine";
		}
		else if (type instanceof MessageAssoicateLineModel) {
			return "MessageAssoicateLine";
		}
		else if (type instanceof AssociateLineModel) {
			return "AssociateLine";
		}
		else if (type instanceof GeneralizeLineModel) {
			return "GeneralizeLine";
		}
		else if (type instanceof RealizeLineModel) {
			return "RealizeLine";
		}
		else if (type instanceof NoteLineModel) {
			return "NoteLine";
		}
		else if (type instanceof DependencyLineModel) {
			return "DependencyLine";
		}
		//PKY 08072401 S ObjectFlow모델 추가
		else if (type instanceof ObjectFlowLineModel) {
			return "ObjectFlowLine";
		}
		else if (type instanceof ControlFlowLineModel) {
			return "ControlFlowLine";
		}
		else if (type instanceof RequiredInterfaceLineModel) {
			return "RequiredInterfaceLine";
		}
		else if (type instanceof ProvidedInterfaceLineModel) {
			return "ProvidedInterfaceLine";
		}
		else if (type instanceof RoleBindingLineModel) {
			return "RoleBindingLine";
		}
		else if (type instanceof DelegateLineModel) {
			return "DelegateLine";
		}
		else if (type instanceof OccurrenceLineModel) {
			return "OccurrenceLine";
		}
		else if (type instanceof RepresentsLineModel) {
			return "RepresentsLine";
		}
		//		else if (type instanceof ManifestLineModel) {
		//			return "ManifestLine";
		//		}
		//		else if (type instanceof DeployLineModel) {
		//			return "DeployLine";
		//		}

		//		else if (type instanceof CommunicationPathLineModel) {
		//			return "CommunicationPathLine";
		//		}



		else if (type instanceof ImportLineModel) {
			return "ImportLine";
		}
		else if (type instanceof MergeLineModel) {
			return "MergeLine";
		}

		else {
			return "Line";
		}


	}

	public int getWriteViewModelType(UMLModel child, int modelType) {
		modelType =  this.getModelType(child, modelType);
		if(modelType==-1){

		}

		return modelType;
	}

	//등록
	public int getModelType(UMLModel child, int modelType) {
		if (child instanceof FinalPackageModel) {
			modelType = 0;
		}
		if (child instanceof N3EditorDiagramModel) {
			modelType = 1;
		}
		else if (child instanceof CollaborationModel) {
			return 5;
		}
		else if (child instanceof UseCaseModel) {
			return 2;
		}
		else if (child instanceof FinalActorModel) {
			return 3;
		}
		else if (child instanceof FinalBoundryModel) {
			return 4;
		}
		else if (child instanceof EnumerationModel) {
			return 8;
		}
		else if (child instanceof FinalClassModel) {
			return 6;
		}
		else if (child instanceof InterfaceModel) {
			return 7;
		}
		else if (child instanceof ExceptionModel) {
			return 9;
		}
		else if (child instanceof FinalStrcuturedActivityModel) {
			return 26;
		}
		else if (child instanceof FinalActivityModel) {
			return 10;
		}
		else if (child instanceof FinalActiionModel) {
			return 11;
		}
		else if (child instanceof SendModel) {
			return 12;
		}
		else if (child instanceof ReceiveModel) {
			return 13;
		}
		else if (child instanceof InitialModel) {
			return 14;
		}
		else if (child instanceof FinalModel) {
			return 15;
		}
		else if (child instanceof FlowFinalModel) {
			return 16;
		}
		else if (child instanceof SynchModel) {
			return 17;
		}
		else if (child instanceof DecisionModel) {
			return 18;
		}
		else if (child instanceof FinalObjectNodeModel) {
			return 19;
		}
		else if (child instanceof CentralBufferNodeModel) {
			return 20;
		}
		else if (child instanceof DataStoreModel) {
			return 21;
		}
		else if (child instanceof HPartitionModel) {
			return 22;
		}
		else if (child instanceof VPartitionModel) {
			return 23;
		}
		else if (child instanceof HForkJoinModel) {
			return 24;
		}
		else if (child instanceof VForkJoinModel) {
			return 25;
		}
		else if (child instanceof PartModel) {
			return 27;
		}


		else if (child instanceof ComponentModel) {
			return 29;
		}
		else if (child instanceof ArtfifactModel) {
			return 30;
		}

		//		else if (child instanceof NodeModel) {
		//			return 35;
		//		}

		//		else if (child instanceof DeviceModel) {
		//			return 37;
		//		}
		//		else if (child instanceof ExecutionEnvironmentModel) {
		//			return 38;
		//		}
		//		else if (child instanceof DeploymentSpecificationModel) {
		//			return 39;
		//		}

		else if (child instanceof UMLNoteModel) {
			return 500;
		}


		else if (child instanceof SubPartitonModel){//PKY 08052101 S 컨테이너에서 그룹으로 변경
			return 502;
		}
		//20080721IJS

		else if (child instanceof NodeItemModel){//PKY 08052101 S 컨테이너에서 그룹으로 변경
			return 900;
		}
		//PKY 08052101 E 컨테이너에서 그룹으로 변경
		//PKY 08080501 S Frame Zoder 안나오는 문제 수정
		else if (child instanceof FrameModel){
			return 235;
		}
		//PKY 08080501 E Frame Zoder 안나오는 문제 수정

		else if (child instanceof LifecycleMethodPortModel) {
			return 1010;
		}
		else if (child instanceof MonitoringMethodPortModel) {
			return 1011;
		}
		//		WJH 090809 S

		else if (child instanceof MethodInputPortModel) {
			return 10006;
		}
		else if (child instanceof MethodOutputPortModel) {
			return 10007;
		}
		else if (child instanceof DataInputPortModel) {
			return 10002;
		}
		else if (child instanceof DataOutputPortModel) {
			return 10003;
		}
		else if (child instanceof EventInputPortModel) {
			return 10004;
		}
		else if (child instanceof EventOutputPortModel) {
			return 10005;
		}
		//		WJH 090809 E
		else if (child instanceof NodeItemModel) {
			return 1010;
		}
		//		else if (child instanceof ComponentEditModel) {
		//			return 20010;
		//		}


		return modelType;
	}


	public int getElementType(UMLModel child, int modelType) {
		if (child instanceof FinalPackageModel) {
			modelType = 0;
		}
		if (child instanceof N3EditorDiagramModel) {
			modelType = 1;
		}
		else if (child instanceof UseCaseModel) {
			return 2;
		}
		else if (child instanceof FinalActorModel) {
			return 3;
		}
		else if (child instanceof FinalBoundryModel) {
			return 4;
		}
		else if (child instanceof CollaborationModel) {
			return 5;
		}
		else if (child instanceof FinalClassModel) {
			return 6;
		}
		else if (child instanceof InterfaceModel) {
			return 7;
		}
		else if (child instanceof EnumerationModel) {
			return 8;
		}
		else if (child instanceof ExceptionModel) {
			return 9;
		}
		else if (child instanceof FinalStrcuturedActivityModel) {
			return 26;
		}
		else if (child instanceof FinalActivityModel) {
			return 10;
		}
		else if (child instanceof FinalActiionModel) {
			return 11;
		}
		else if (child instanceof SendModel) {
			return 12;
		}
		else if (child instanceof ReceiveModel) {
			return 13;
		}

		else if (child instanceof FinalModel) {
			return 15;
		}
		else if (child instanceof FlowFinalModel) {
			return 16;
		}
		else if (child instanceof SynchModel) {
			return 17;
		}
		else if (child instanceof DecisionModel) {
			return 18;
		}
		else if (child instanceof FinalObjectNodeModel) {
			return 19;
		}
		else if (child instanceof CentralBufferNodeModel) {
			return 20;
		}
		else if (child instanceof DataStoreModel) {
			return 21;
		}
		else if (child instanceof HPartitionModel) {
			return 22;
		}
		else if (child instanceof VPartitionModel) {
			return 23;
		}
		else if (child instanceof HForkJoinModel) {
			return 24;
		}
		else if (child instanceof VForkJoinModel) {
			return 25;
		}
		else if (child instanceof PartModel) {
			return 27;
		}

		else if (child instanceof AtomicComponentModel) {
			return 30;
		}
		else if (child instanceof ComponentModel) {

			ComponentModel clm = (ComponentModel)child;
			String ste = clm.getStereotype();
			if("<<atomic>>".equals(ste)){
				return 30;
			}
			else
				return 29;
		}
		else if (child instanceof ArtfifactModel) {
			return 30;
		}
		else if (child instanceof AttributeElementModel) {
			return 100;
		}
		else if (child instanceof OperationElementModel) {
			return 101;
		}
		else if (child instanceof CompartmentModel) {
			CompartmentModel cm = (CompartmentModel)child;
			if("ATTR".equals(cm.getCompartmentModelType())){
				return 102;
			}
			else if("OPER".equals(cm.getCompartmentModelType())){
				return 103;
			}
			else if("-1".equals(cm.getCompartmentModelType())){
				return 106;
			}
			else{
				return 106;
			}

		}
		else if (child instanceof ElementLabelModel) {
			ElementLabelModel cm = (ElementLabelModel)child;
			if("NAME".equals(cm.getType())){
				return 104;
			}
			else if("STREOTYPE".equals(cm.getType())){
				return 105;
			} 

		}
		else if (child instanceof ClassModel) {
			return 106;
		}

		else if (child instanceof NodeContainerModel) {
			return 108;
		}
		//PKY 08061801 S 파티션 이미지 삽입
		else if (child instanceof SubPartitonModel) {
			return 109;
		}


		//PKY 08071501 S FrameModel 이미지 추가
		else if( child instanceof FrameModel){
			return 235;
		}
		//PKY 08071501 E FrameModel 이미지 추가
		//PKY 08081101 S Timing, MessagePoint 이미지 추가

		//PKY 08081101 E Timing, MessagePoint 이미지 추가


		else if( child instanceof NodeItemModel){
			return 900;
		}

		//PKY 08061801 E 파티션 이미지 삽입
		return modelType;
	}

	//등록
	public String getDefaultName(int modelType, UMLTreeParentModel parent) {
		String name = "";
		//패키지
		//		if (modelType == 0) {
		//		name = UMLTreeParentModel.PackageName + parent.getNewPackageCount();
		//		} //다이어그램
		//		else if (modelType == 1) {
		//		name = UMLTreeParentModel.UseCaseDiagramDefaultName + parent.getNewUseCaseDiagramCount();
		//		//			parent.addUseCaseCount();
		//		} //유즈케이스
		//		else if (modelType == 2) {
		//		name = UMLTreeParentModel.UseCaseName + parent.getNewUseCaseCount();
		//		} //액터
		//		else if (modelType == 3) {
		//		name = UMLTreeParentModel.ActorName + parent.getNewActorCountCount();
		//		} //바운더리
		//		else if (modelType == 4) {
		//		name = UMLTreeParentModel.BoundaryName + parent.getNewBoundaryCountCount();
		//		} //컬레보레이션
		//		else if (modelType == 5) {
		//		name = UMLTreeParentModel.CollaborationName + parent.getNewCollaborationCountCount();
		//		}
		//		//클래스
		//		else if (modelType == 6) {
		//		name = UMLTreeParentModel.ClassName + parent.getNewClassCountCount();
		//		}
		//		//인터페이스
		//		else if (modelType == 7) {
		//		name = UMLTreeParentModel.InterfaceName + parent.getNewInterfaceCount();
		//		} //Enumeration
		//		else if (modelType == 8) {
		//		name = UMLTreeParentModel.EnumerationName + parent.getNewEnumerationCount();
		//		}
		//		//ExceptionHandler
		//		else if (modelType == 9) {
		//		name = UMLTreeParentModel.ExceptionHandlerName + parent.getNewExceptionHandlerCount();
		//		}
		//		//액티비티
		//		else if (modelType == 10) {
		//		name = UMLTreeParentModel.FinalActivityModelName + parent.getNewFinalActivityCount();
		//		}
		//		//액션
		//		else if (modelType == 11) {
		//		name = UMLTreeParentModel.FinalActiionModelName + parent.getNewFinalActivityCount();
		//		}
		//		//send
		//		else if (modelType == 12) {
		//		name = UMLTreeParentModel.SendModelName + parent.getNewSendModelCount();
		//		}
		//		//Receive
		//		else if (modelType == 13) {
		//		name = UMLTreeParentModel.ReceiveModelName + parent.getNewReceiveModelCount();
		//		}
		//		//ObjectNodeModel
		//		else if (modelType == 19) {
		//		name = UMLTreeParentModel.FinalObjectNodeModelName + parent.getNewFinalObjectNodeModelCount();
		//		}
		//		//CentralBufferNodeModel
		//		else if (modelType == 20) {
		//		name = UMLTreeParentModel.CentralBufferNodeModelName + parent.getNewCentralBufferNodeModelCount();
		//		}
		//		//DataStoreModel
		//		else if (modelType == 21) {
		//		name = UMLTreeParentModel.DataStoreModelName + parent.getNewDataStoreModelCount();
		//		}
		//		//HSwinlane
		//		else if (modelType == 22) {
		//		name = UMLTreeParentModel.PartitionModelName + parent.getNewPackageCount();
		//		}
		//		//VSwinlane
		//		else if (modelType == 23) {
		//		name = UMLTreeParentModel.PartitionModelName + parent.getNewPackageCount();
		//		} //StrcuturedActivity
		//		else if (modelType == 26) {
		//		name = UMLTreeParentModel.StrcuturedActivityModelName + parent.getNewStrcuturedActivityModelNameCount();
		//		} //PartModel
		//		else if (modelType == 27) {
		//		name = UMLTreeParentModel.PartModellName + parent.getNewPartModellNameCount();
		//		} //LifeLine
		//		else if (modelType == 50) {
		//		name = UMLTreeParentModel.ActorName + parent.getNewActorCountCount();
		//		} //Component
		//		else if (modelType == 28) {
		//		name = UMLTreeParentModel.LifeLineModelName + parent.getNewLifeLineModelNameCount();
		//		} //Component
		//		else if (modelType == 29) {
		//		name = UMLTreeParentModel.ComponentModelName + parent.getNewComponentModelNameCount();
		//		} //Artfifact
		//		else if (modelType == 30) {
		//		name = UMLTreeParentModel.ArtfifactModelName + parent.getNewArtfifactModelNameCount();
		//		} //State
		//		else if (modelType == 31) {
		//		name = UMLTreeParentModel.StateModelName + parent.getNewStateModelNameCount();
		//		} //StrcuturedStateModel
		//		else if (modelType == 32) {
		//		name = UMLTreeParentModel.StrcuturedStateModelName + parent.getNewStrcuturedStateModelNameCount();
		//		}
		////		else if (modelType == 33) {
		////		name = UMLTreeParentModel.StrcuturedStateModelName + parent.getNewStrcuturedStateModelNameCount();
		////		}//NodeModel
		//		else if (modelType == 35) {
		//		name = UMLTreeParentModel.NodeModelName + parent.getNewNodeModelNameCount();
		//		}
		//		else if (modelType == 37) {
		//		name = UMLTreeParentModel.DeviceModelName + parent.getNewDeviceModelNameCount();
		//		}
		//		else if (modelType == 38) {
		//		name = UMLTreeParentModel.ExecutionEnvironmentModelName + parent.getNewExecutionEnvironmentModelNameCount();
		//		}
		//		else if (modelType == 39) {
		//		name = UMLTreeParentModel.DeploymentSpecificationModelName + parent.getNewDeploymentSpecificationModelNameCount();
		//		}
		//		else if (modelType == 40) {
		//		name = UMLTreeParentModel.StateLifelineModelName + parent.getNewStateLifelineModelNameCount();
		//		}



		if (modelType == 0) {
			name = UMLTreeParentModel.PackageName ;
		} //다이어그램
		else if (modelType == 1) {
			name = UMLTreeParentModel.UseCaseDiagramDefaultName ;
			//			parent.addUseCaseCount();
		} //유즈케이스
		else if (modelType == 2) {
			name = N3Messages.PALETTE_USECASE;
		} //액터
		else if (modelType == 3) {
			name = UMLTreeParentModel.ActorName ;
		} //바운더리
		else if (modelType == 4) {
			name = UMLTreeParentModel.BoundaryName ;
		} //컬레보레이션
		else if (modelType == 5) {
			name = UMLTreeParentModel.CollaborationName ;
		}
		//클래스
		else if (modelType == 6) {
			name = UMLTreeParentModel.ClassName;
		}
		//인터페이스
		else if (modelType == 7) {
			name = UMLTreeParentModel.InterfaceName ;
		} //Enumeration
		else if (modelType == 8) {
			name = UMLTreeParentModel.EnumerationName ;
		}
		//ExceptionHandler
		else if (modelType == 9) {
			name = UMLTreeParentModel.ExceptionHandlerName;
		}
		//액티비티
		else if (modelType == 10) {
			name = UMLTreeParentModel.FinalActivityModelName ;
		}
		//액션
		else if (modelType == 11) {
			name = UMLTreeParentModel.FinalActiionModelName ;
		}
		//send
		else if (modelType == 12) {
			name = UMLTreeParentModel.SendModelName ;
		}
		//Receive
		else if (modelType == 13) {
			name = UMLTreeParentModel.ReceiveModelName ;
		}
		//ObjectNodeModel
		else if (modelType == 19) {
			name = UMLTreeParentModel.FinalObjectNodeModelName ;
		}
		//CentralBufferNodeModel
		else if (modelType == 20) {
			name = UMLTreeParentModel.CentralBufferNodeModelName ;
		}
		//DataStoreModel
		else if (modelType == 21) {
			name = UMLTreeParentModel.DataStoreModelName ;
		}
		//HSwinlane
		else if (modelType == 22) {
			name = UMLTreeParentModel.PartitionModelName ;
		}
		//VSwinlane
		else if (modelType == 23) {
			name = UMLTreeParentModel.PartitionModelName ;
		} //StrcuturedActivity
		else if (modelType == 26) {
			name = UMLTreeParentModel.StrcuturedActivityModelName ;
		} //PartModel
		else if (modelType == 27) {
			name = UMLTreeParentModel.PartModellName ;
		} //LifeLine
		else if (modelType == 50) {
			name = UMLTreeParentModel.ActorName ;
		} //Component
		else if (modelType == 28) {
			name = UMLTreeParentModel.LifeLineModelName ;
		} //Component
		else if (modelType == 29) {
			name = UMLTreeParentModel.ComponentModelName ;
		} //Artfifact
		else if (modelType == 30) {
			name = UMLTreeParentModel.ArtfifactModelName ;
		} //State
		else if (modelType == 31) {
			name = UMLTreeParentModel.StateModelName ;
		} //StrcuturedStateModel
		else if (modelType == 32) {
			name = UMLTreeParentModel.StrcuturedStateModelName ;
		}
		//		else if (modelType == 33) {
		//		name = UMLTreeParentModel.StrcuturedStateModelName + parent.getNewStrcuturedStateModelNameCount();
		//		}//NodeModel
		else if (modelType == 35) {
			name = UMLTreeParentModel.NodeModelName ;
		}
		else if (modelType == 37) {
			name = UMLTreeParentModel.DeviceModelName ;
		}
		else if (modelType == 38) {
			name = UMLTreeParentModel.ExecutionEnvironmentModelName ;
		}
		else if (modelType == 39) {
			name = UMLTreeParentModel.DeploymentSpecificationModelName ;
		}
		else if (modelType == 40) {
			name = UMLTreeParentModel.StateLifelineModelName ;
		}
		//20080721IJS
		else if (modelType == 600) {
			name = UMLTreeParentModel.RequirementModelName ;
		}

		if(parent!=null){
			for(int i=0;i<parent.getChildren().length;i++){

			}
		}


		return name;
	}

	/*
	 * 노드 추가
	 */

	public void addUMLNode(UMLTreeParentModel parent, UMLTreeModel child) {
		//		UMLTreeModel treeModel = parent.getUMLTreeModel().getParent();
		//default
		try {
			child.setParent(parent);
			parent.addChild(child);
			ProjectManager.getInstance().getModelBrowser().refresh(parent);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 노드 삭제
	 */

	public void removeUMLNode(UMLTreeParentModel parent, UMLTreeModel child) {
		//		UMLTreeModel treeModel = parent.getUMLTreeModel().getParent();
		//default
		if (child != null) {
			//			UpdateEvent e = new UpdateEvent(IUpdateType.REMOVE_NAME, null);
			//			if(child.getRefModel() instanceof UMLModel){
			//				UMLModel um = (UMLModel)child.getRefModel();
			//				if (um instanceof ClassifierModel) {
			//					try {
			//						ClassifierModel um1 = (ClassifierModel)um;
			//						//PKY 08081801 S 엑티비티 파티션 움직임이 비정상적인 문제
			//						if(um.getAcceptParentModel()!=null && um.getAcceptParentModel() instanceof N3EditorDiagramModel){
			//							N3EditorDiagramModel n3 = (N3EditorDiagramModel)um.getAcceptParentModel();
			//							removeSubPartitonModel(um,n3);//PKY 08082201 S Activity파티션 한 개만 삭제되고 나머지는 삭제안되는 문제
			//						}
			//						//PKY 08081801 E 엑티비티 파티션 움직임이 비정상적인 문제
			//
			//						um1.getClassModel().getElementLabelModelName().fireChildUpdate(e);
			//						um1.getClassModel().removeUpdateListener((UMLModel)um);
			//					}
			//					catch (Exception e1) {
			//						e1.printStackTrace();
			//					}
			//				}
			////				child.fireChildUpdate(e);
			//			}
			//
			//
			//			this.getTypeModel().remove(child);
			//			this.removeBehaviorActivityList(child);
			parent.removeChild(child);
			try{

				ProjectManager.getInstance().getModelBrowser().refresh(parent);
			}
			catch(Exception e1){
				e1.printStackTrace();
			}
		}
	}
	//PKY 08082201 S Activity파티션 한 개만 삭제되고 나머지는 삭제안되는 문제
	public void removeSubPartitonModel(UMLModel um,N3EditorDiagramModel n3 ){
		for( int i = 0 ; i < n3.getChildren().size(); i ++ ){
			if(n3.getChildren().get(i) instanceof SubPartitonModel){
				SubPartitonModel sub = (SubPartitonModel) n3.getChildren().get(i);
				if(sub.getParentModel() == um){
					n3.removeChild(sub);
					removeSubPartitonModel(um,n3);
				}

			}
		}
	}
	/*
	 * 노드 생성
	 */

	//	public void addUMLNode(String name,UMLTreeParentModel parent,UMLModel child,int modelType){
	////		UMLTreeModel treeModel = parent.getUMLTreeModel().getParent();
	//		//default
	//		try{
	//			if(parent!=null && parent instanceof UMLTreeParentModel && !this.isDrag()){
	//				UMLTreeParentModel uMLTreeParentModel =(UMLTreeParentModel)parent;
	//				modelType = this.getModelType(child, modelType);
	//				if(modelType==0){
	//					if(name==null){
	//						name =this.getDefaultName(0, uMLTreeParentModel);
	//					}
	//					if(child ==null){
	////						UseCaseModel childp = new UseCaseModel();
	////						childp.setName(name);
	////						child = childp;
	//					}
	//
	//				}
	//				else if(modelType==2){
	//					if(name==null){
	//						name =this.getDefaultName(2, uMLTreeParentModel);
	//					}
	//					if(child ==null){
	//						UseCaseModel childp = new UseCaseModel();
	////						childp.setName(name);
	//						child = childp;
	//					}
	////					else{
	////					child.setName(name);
	////					}
	//				}
	//				else if(modelType==3){
	//					if(name==null){
	//						name =this.getDefaultName(3, uMLTreeParentModel);
	//					}
	//					if(child ==null){
	//						FinalActorModel childp = new FinalActorModel();
	////						childp.setName(name);
	//						child = childp;
	//					}
	////					else{
	////					child.setName(name);
	////					}
	//				}
	//				else if(modelType==4){
	//					if(name==null){
	//						name =this.getDefaultName(4, uMLTreeParentModel);
	//					}
	//					if(child ==null){
	//						FinalBoundryModel childp = new FinalBoundryModel();
	//
	//						child = childp;
	//					}
	////					else{
	////					child.setName(name);
	////					}
	//				}
	//				else if(modelType==5){
	//					if(name==null){
	//						name =this.getDefaultName(5, uMLTreeParentModel);
	//					}
	//					if(child ==null){
	//						CollaborationModel childp = new CollaborationModel();
	////						childp.setName(name);
	//						child = childp;
	//					}
	////					else{
	////					child.setName(name);
	////					}
	//				}
	//				UMLTreeModel to1 = null;
	//				if(modelType!=0){
	//					to1 = new UMLTreeModel(name);
	//
	//				}
	//				else{
	//					to1 = new PackageTreeModel(name);
	////					child.setTreeModel((UMLTreeModel)to1);
	//				}
	//				if(child!=null){
	//					child.setTreeModel(to1);
	//					child.setName(name);
	//				}
	//
	//				uMLTreeParentModel.addChild(to1);
	//				to1.setRefModel(child);
	//				to1.setParent(uMLTreeParentModel);
	//				ProjectManager.getInstance().getModelBrowser().refresh(uMLTreeParentModel);
	////				ProjectManager.getInstance().getModelBrowser().expend(tp);
	////				ProjectManager.getInstance().getModelBrowser().select(to1);
	//
	//			}
	//		}
	//		catch(Exception e){
	//			e.printStackTrace();
	//		}
	//	}

	/*
	 * 노드 삭제
	 */

	public void removeUMLNode(UMLModel child) {
		//		UMLTreeModel treeModel = parent.getUMLTreeModel().getParent();
		//default
		//		if(child!=null){
		//		UMLTreeModel uMLTreeModel = child.getUMLTreeModel();
		//		if(uMLTreeModel!=null){
		//		UMLTreeParentModel uMLTreeParentModel =(UMLTreeParentModel)uMLTreeModel.getParent();
		//		uMLTreeParentModel.removeChild(uMLTreeModel);
		//		ProjectManager.getInstance().getModelBrowser().refresh(uMLTreeParentModel);
		//		}
		//		}
	}

	//	public LogicDiagram getDiagram() {
	//	if (index == 0) {
	//	LogicDiagram logicDiagram = new LogicDiagram();
	//	this.diagrams.add(logicDiagram);
	//	this.index++;
	//	return logicDiagram;
	//	}
	//	if (index == 1) {
	//	LogicDiagram logicDiagram = new LogicDiagram();
	//	this.diagrams.add(logicDiagram);
	//	this.index++;
	//	return logicDiagram;
	//	}
	//	if (index > 1) {
	//	//			LogicDiagram logicDiagram = new LogicDiagram();
	//	//			this.diagrams.add(logicDiagram);
	//	this.index++;
	//	return (LogicDiagram)this.diagrams.get(0);
	//	}
	//	return null;
	//	}

	public PortModel getSelectPort(){
		java.util.List list = ProjectManager.getInstance().getSelectNodes();
		if (list != null && list.size() == 1) {
			Object obj = list.get(0);
			if (obj instanceof UMLEditPart) {
				UMLEditPart u = (UMLEditPart)obj;
				UMLModel um = (UMLModel)u.getModel();
				if(um instanceof PortModel){
					PortModel pm = (PortModel)um;
					return pm;
				}
			}
		}
		return null;
	}

	public List getSelectNodes() {
		if(ProjectManager.getInstance().getUMLEditor()!=null)//PKY 08052101 S 컨테이너에서 그룹으로 변경
			//2008043001 PKY S NullPoint 에러
			if(ProjectManager.getInstance().getUMLEditor().getScrollingGraphicalViewer()!=null){
				ScrollingGraphicalViewer viewer = ProjectManager.getInstance().getUMLEditor().getScrollingGraphicalViewer();

				List list = viewer.getSelectedEditParts();
				return list;
			}
		return null;
		//2008043001 PKY E NullPoint 에러
		//		Tool tool = ProjectManager.getInstance().getUMLEditor().getUMLDefaultEditDomain().getActiveTool();
		//		if(!(tool instanceof MarqueeSelectionTool) && !ProjectManager.getInstance().getIsDoubleClick()){
		//		if(value==2){
		//		ProjectManager.getInstance().setUMLElementEditPart(this);
		//		viewer.select(this.getParent());
		//		}
		//		else{
		//		super.setSelected(value);	
		//		}
		//		}
		//		else{
		//		super.setSelected(value);
		//		ProjectManager.getInstance().initIsDoubleClick();
		//		}
		//		this.window.getSelectionService().getSelection()
	}

	public Vector diagrams = new Vector();

	public static String getID(Object obj, String prefix) {
		String hashcode = Integer.toHexString(obj.hashCode());
		String time = Long.toHexString(new Date().getTime());
		String second = Long.toHexString(new Date().getSeconds());
		String random = Integer.toHexString((int)(Math.random() * 10000));
		return prefix + "@" + hashcode + time + second + ":" + random;
	}
	public static String getID2(Object obj) {
		String hashcode = Integer.toHexString(obj.hashCode());
		String time = Long.toHexString(new Date().getTime());
		String second = Long.toHexString(new Date().getSeconds());
		String random = Integer.toHexString((int)(Math.random() * 10000));
		return  hashcode +"."+time+"."+second+"."+random;
	}





	public static String getViewId(Object obj){
		return getID(obj, "");
	}
	public static String getID(Object obj) {
		return getID(obj, obj.toString());
	}
	public String getIconDiagramPath(int type){
		StringBuffer path = new StringBuffer("icons/");
		if(type==1){//패키지 다이어그램
			path.append("package_titel.gif");
		}//클래스 다이어그램
		else if(type==2){
			path.append("class_title.gif");
		}//객체 다이어그램
		else if(type==3){
			path.append("object_titel.gif");
		}//합성 다이어그램
		else if(type==4){
			path.append("composite_titel.gif");
		}//컴포넌트 다이어그램
		else if(type==5){
			//이미지 교체
			path.append("icno_16x16/10_Diagram.png");
//			path.append("component_title.gif");
		}//배치 다이어그램
		else if(type==6){
			path.append("deployment_titel.gif");
		}//구조 다이어그램
		else if(type==7){
			path.append("composite_titel.gif");
		}//유즈케이스  다이어그램
		else if(type==8){
			path.append("usecase_title.gif");
		}//액티비티 다이어그램
		else if(type==9){
			path.append("activity_title.gif");
		}//상태 다이어그램
		else if(type==10){
			path.append("state_title.gif");
		}//커뮤니케이션 다이어그램
		else if(type==11){
			path.append("communication_titel.gif");
		}//시퀀스 다이어그램
		else if(type==12){
			path.append("interaction_title.gif");
		}//다이밍 다이어그램
		else if(type==13){
			path.append("timing_title.gif");
		}//인터렉션 오버뷰 다이어그램
		else if(type==14){
			path.append("interaction_overview.gif");
		}//행위 다이어그램
		else if(type==15){
			path.append("interaction_title.gif");
		}//Initial
		//20080721IJS
		else if(type==16){
			path.append("extends_title.gif");
		}//행위 다이어그램
		else if(type==17){
			path.append("requirements_title.gif");
		}
		return path.toString();
	}

	public String getFrameType(int type){
		StringBuffer path = new StringBuffer("");
		if(type==1){//패키지 다이어그램
			path.append("pkg");
		}//클래스 다이어그램
		else if(type==2){
			path.append("class");
		}//객체 다이어그램
		else if(type==3){
			path.append("object");
		}//합성 다이어그램
		else if(type==4){
			path.append("composite structure");
		}//컴포넌트 다이어그램
		else if(type==5){
			path.append("cop");
		}//배치 다이어그램
		else if(type==6){
			path.append("deployment");
		}//구조 다이어그램
		else if(type==7){
			path.append("");
		}//유즈케이스  다이어그램
		else if(type==8){
			path.append("usecase");
		}//액티비티 다이어그램
		else if(type==9){
			path.append("act");
		}//상태 다이어그램
		else if(type==10){
			path.append("stm");
		}//커뮤니케이션 다이어그램
		else if(type==11){
			path.append("sd");
		}//시퀀스 다이어그램
		else if(type==12){
			path.append("sd");
		}//다이밍 다이어그램
		else if(type==13){
			path.append("sd");
		}//인터렉션 오버뷰 다이어그램
		else if(type==14){
			path.append("sd");
		}//행위 다이어그램
		else if(type==15){
			path.append("");
		}//Initial
		return path.toString();
	}


	public String getIconPath(int type){
		StringBuffer path = new StringBuffer("icons/");
		if(type==0){//패키지
			//이미지 교체
			path.append("icno_16x16/09_ComponentPackage.png");
//			path.append("package.gif");
		}//액터
		if(type==2){//유즈케이스
			path.append("usecase.gif");
		}//액터
		else if(type==3){
			path.append("actor.gif");
		}//바운더
		else if(type==4){
			path.append("boundary.gif");
		}//컬레보레이션
		else if(type==5){
			path.append("collaboration.gif");
		}//클래스
		else if(type==6){
			path.append("class.gif");
		}//인터페이스
		else if(type==7){
			//20080822IJS
			path.append("interface.gif");
		}//Enumeration
		else if(type==8){
			path.append("enumeration.gif");
		}//Exception
		else if(type==9){
			path.append("exception.gif");
		}//액티비티
		else if(type==10){
			path.append("activity.gif");
		}//액션
		else if(type==11){
			path.append("action.gif");
		}//Send
		else if(type==12){
			path.append("send.gif");
		}//Receive
		else if(type==13){
			path.append("receive.gif");
		}//Initial
		else if(type==14){
			path.append("initial.gif");
		}//Final
		else if(type==15){
			path.append("final.gif");
		}//FlowFinal
		else if(type==16){
			path.append("flow_final.gif");
		}//Synch
		else if(type==17){
			path.append("synch.gif");
		}//Decision
		else if(type==18){
			path.append("decision.gif");
		}//Object
		else if(type==19){
			path.append("object.gif");
		}//CentralBufferNode
		else if(type==20){
			path.append("centralbuffernode.gif");
		}//DataStore
		else if(type==21){
			path.append("datastore.gif");
		}//Swimlaine
		else if(type==22){
			path.append("partition.gif");
		}
		else if(type==23){
			path.append("usecase.gif");
		}//Fork/Join
		else if(type==24){
			path.append("forkjoin_h.gif");
		}//Fork/Join
		else if(type==25){
			path.append("forkjoin_v.gif");
		}//StrcuturedActivity
		else if(type==26){
			path.append("structured_activity.gif");
		}//Part
		else if(type==27){
			path.append("part.gif");
		}//LifeLine
		else if(type==28){
			path.append("lifeLine.gif");
		}//Component
		else if(type==29){
			//이미지 교체
			path.append("icno_16x16/23_Composite.png");
//			path.append("16x16_Composite.gif");	// WJH 090809
		}//Artfifact
		else if(type==30){
			//이미지 교체
			path.append("icno_16x16/11_Automic.png");
//			path.append("16x16_Atomic.gif");	// WJH 090809
		}//State
		else if(type==31){
			path.append("state.gif");
		}//StrcuturedState
		else if(type==32){
			path.append("submachinestate.gif");
		}//History
		else if(type==33){
			path.append("history.gif");
		}//Terminate
		else if(type==34){
			path.append("terminate.gif");
		}//Node
		else if(type==35){
			//이미지교체
			path.append("icno_16x16/14_Node.png");
//			path.append("node.gif");
		}//EntryPoint
		else if(type==36){
			path.append("entity.gif");
		}//Device
		else if(type==37){
			//이미지 교체
			path.append("icno_16x16/14_Node.png");
//			path.append("device.gif");
		}//ExecutionEnvironment
		else if(type==38){
			path.append("executionenvironment.gif");
		}//DeploymentSpecification
		else if(type==39){
			path.append("deployment_spec.gif");
		}//StateLifeline
		else if(type==40){
			path.append("statelifeline.gif");
		}
		else if(type==41){
			path.append("usecase.gif");
		}
		else if(type==42){
			path.append("tank.gif");
		}
		else if(type==50){
			path.append("actor.gif");
		}
		else if(type==100){
			path.append("attribute.gif");
		}
		else if(type==101){
			path.append("operation.gif");
		}
		else if(type==102){
			path.append("attributes.gif");
		}
		else if(type==103){
			path.append("operations.gif");
		}
		else if(type==104){
			path.append("object_name.gif");
		}
		else if(type==105){
			path.append("stereotype.gif");
		}
		else if(type==106){
			path.append("compartment.gif");
		}
		else if(type==107){
			path.append("selfMessage.gif");
		}
		else if(type==108){
			path.append("container.gif");
		}
		else if(type==109){
			path.append("Partiton.gif");
		}
		else if(type==200){
			path.append("basic.gif");
		}
		else if(type==201){
			path.append("alternative.gif");
		}
		else if(type==202){
			path.append("exception.gif");
		}
		else if(type==203){
			path.append("add.gif");
		}
		else if(type==204){
			path.append("delete.gif");
		}
		else if(type==205||type==229){
			path.append("model.gif");
		}
		else if(type>=206&&type<=232){
			path.append("pattion.gif");
		} 
		else if(type==233){
			path.append("group.gif");
		} 
		else if(type==234){
			path.append("fragment.gif");
		} 
		//PKY 08071501 S FrameModel 이미지 추가
		else if(type==235){
			path.append("diagram.gif");
		}
		//PKY 08081101 S Timing, MessagePoint 이미지 추가
		else if(type==236){
			path.append("timing_point.gif");
		}
		else if( type==300){
			path.append("find.gif");
		}
		else if( type==301){
			path.append("save.gif");
		}
		else if( type==302){
			//이미지 교체
			path.append("icno_16x16/20_Preferences.png");
//			path.append("property.gif");
		}
		else if( type==303){
			path.append("pro_open.gif");
		}
		else if( type==304){
			path.append("print.gif");
		}
		else if( type==305){
			path.append("paste.gif");
		}
		else if( type==306){
			path.append("newproject.gif");
		}
		else if( type==307){
			path.append("java_reverse.gif");
		}
		else if( type==308){
			path.append("java_generate_code.gif");
		}
		else if( type==309){
			path.append("excel.gif");
		}
		else if( type==310){
			path.append("delete_popup.gif");
		}
		else if( type==311){
			path.append("copy.gif");
		}
		else if( type==312){
			path.append("close_all.gif");
		}
		else if( type==313){
			path.append("c_reverse.gif");
		}
		else if( type==314){
			path.append("c_generate_code.gif");
		}
		else if( type==315){
			//이미지 교체
			path.append("icno_16x16/30_AddApplication.png");
//			path.append("add_package.gif");
		}
		else if( type==316){
			//이미지 교체
			path.append("icno_16x16/18_ImportComponent.png");
//			path.append("add_model.gif");
		}
		else if( type==317){
			path.append("add_diagram.gif");
		}
		else if( type==318){
			path.append("z_oder_forward.gif");
		}
		else if( type==319){
			path.append("z_oder_back.gif");
		}
		//PKY 08070301 S 다이얼로그 이름 및 아이콘 넣기
		else if( type==320){
			path.append("port.gif");
		}
		else if( type==321){
			path.append("exit_popup.gif");
		}
		else if( type==322){
			path.append("expansion_node.gif");
		}
		else if( type==323){
			path.append("entry.gif");
		}
		else if( type==324){
			path.append("port_popup.gif");
		}
		//PKY 08070904 S 창 관련 Outline 아이콘 추가
		else if(type==325){
			path.append("propertie.gif");
		}
		else if(type==326){
			path.append("outline.gif");
		}
		else if(type==327){
			path.append("report_doc.gif");
		}

		else if(type==329){
			path.append("console.gif");
		}
		else if(type==330){
			//이미지 교체
			path.append("icno_16x16/19_Refresh.png");
//			path.append("state_title.gif");
		}

		//PKY 08072201 S 모든 오퍼레이션및 어트리뷰트등 정보 입력한 값에 대한 위치변경할 수있도록 수정
		else if(type==330){
			path.append("up.gif");
		}
		else if(type==331){
			path.append("down.gif");
		}
		//20080724 KDI s
		else if(type==332){
			path.append("associate.gif");
		}
		else if(type==333){
			path.append("control_flow.gif");
		}
		else if(type==334){
			path.append("transition.gif");
		}
		//20080724 KDI e
		//20080728 KDI s 디스크립션 뷰 아이콘
		else if(type==335){
			path.append("circuit16.gif");
		}
		
		//20110808서동민 아이콘 추가
		else if(type==341){	//동기화
			path.append("icno_16x16/301_Sync.png");
		}
		else if(type==342){	//Load Editer Project>>110811팝업메뉴 삭제로 사용 안함
			path.append("icno_16x16/110_LdAt.png");
		}
		else if(type==343){	//Add Robot
			path.append("icno_16x16/120_AddRobot.png");
		}
		else if(type==344){	//Import Component (사용안함)
			path.append("icno_16x16/111_IpAt.png");
		}

		//20110811서동민 아이콘 추가
		else if(type==347){	//Application Export
			path.append("icno_16x16/302_ApExport.png");
		}
		else if(type==348){	//Connect
			path.append("icno_16x16/001_Connect.png");
		}
		else if(type==349){	//RepositoryConnect
			path.append("icno_16x16/000_ConnectRepository.png");
		}
		else if(type==350){	//templateLoad
			path.append("icno_16x16/002_templateLd.png");
		}
		else if(type==351){	//Stop Monitoring
			path.append("icno_16x16/032_MoStop.png");
		}
		else if(type==352){	//Start Monitoring
			path.append("icno_16x16/031_MoStart.png");
		}
		else if(type==353){	//OpenComponentEditor
			path.append("icno_16x16/004_OpenEdit.png");
		}
		else if(type==354){	//OpenProperty
			path.append("icno_16x16/005_OpenProperties.png");
		}
		else if(type==355){	//Open Monitoring Dialog
			path.append("icno_16x16/033_MoOpen.png");
		}
		else if(type==356){	//Open Application Diagrm
			path.append("icno_16x16/006_OpenDiagramt.png");
		}
		else if(type==357){	//Z-Order
			path.append("icno_16x16/009_ZOrder.png");
		}

		
		//20080728 KDI e
		//20080721IJS
		else if(type==600){
			path.append("change.gif");
		}
		//PKY 08072201 E 모든 오퍼레이션및 어트리뷰트등 정보 입력한 값에 대한 위치변경할 수있도록 수정
		//20080811IJS
		else if(type==1000){
			path.append("link_n_load.gif");
		}
		else if(type==1001){
			path.append("link_y_load.gif");
		}
		else if(type==1002){
			path.append("link_r_load.gif");
		}


		else if(type==1004){
			path.append("pro.gif");
		}
		else if(type==1003){
			path.append("req.gif");
		}
		else if(type==1005){
			path.append("receive.gif");
		}
		else if(type==1006){
			path.append("send.gif");
		}
		else if(type==1007){
			path.append("eventInputPort.gif");
		}
		else if(type==1009){
			path.append("eventOutputPort.gif");
		}
		else if(type==1010){
			path.append("req1.gif");
		}
		else if(type==1011){
			path.append("req2.gif");
		}
		//		WJH 090801 S
		else if(type==10000){
			path.append("Original_Composite_C.png");			
		}
		else if(type==10008){
			path.append("Original_Atomic_AR.png");
			//path.append("Method_Required.gif");
		}
		else if(type==10009){
			path.append("Original_Atomic_AL.png");
			//path.append("Method_Required.gif");
		}
		//이미지 교체 ->아토믹컴포넌트 자리배치를 위함
		else if(type==18111){
			path.append("Original_Atomic_C.png");
			//path.append("Method_Required.gif");
		}
		else if(type==10009){
			path.append("Composite.png");
		}
		else if(type==10010){
			path.append("BAR2_C.png");
		}
		else if(type==10001){
			path.append("BAR_C.png");
		}
		else if(type==100100){
			path.append("webAsm16.gif");
		}
		//		WJH 090819 S
		
		//이미지교체
		else if(type==10002){
			path.append("icno_16x16/24_DataInputPort.png");
		}
		else if(type==10003){
			path.append("icno_16x16/24_DataOutputPort.png");
		}
		else if(type==10004){
			path.append("icno_16x16/24_EventInputPort.png");
		}
		else if(type==10005){
			path.append("icno_16x16/24_EventOutputPort.png");
		}
		else if(type==10007){
			path.append("icno_16x16/24_MethodProvided.png");
		}
		else if(type==10006){
			path.append("icno_16x16/24_MethodRequired.png");
		}
		else if(type==10011){
			path.append("icon_32x32/24_DataInputPort@2x.png");
		}
		else if(type==10012){
			path.append("icon_32x32/24_DataOutPort@2x.png");
		}
		else if(type==10013){
			path.append("icon_32x32/24_EventInputPort@2x.png");
		}
		else if(type==10014){
			path.append("icon_32x32/24_EventOutPort@2x.png");
		}
		else if(type==10016){
			path.append("icon_32x32/24_MethodProvided@2x.png");
		}
		else if(type==10015){
			path.append("icon_32x32/24_MethodRequired@2x.png");
		}
		/*
		else if(type==10002){
			path.append("16x16_Data_InputPort.png");
		}
		else if(type==10003){
			path.append("16x16_Data_OutputPort.png");
		}
		else if(type==10004){
			path.append("16x16_Event_InputPort.png");
		}
		else if(type==10005){
			path.append("16x16_Event_OutputPort.png");
		}
		else if(type==10007){
			path.append("16x16_Method_Required.png");
		}
		else if(type==10006){
			path.append("16x16_Method_Provided.png");
		}
		else if(type==10011){
			path.append("32x32_Data_InputPort.png");
		}
		else if(type==10012){
			path.append("32x32_Data_OutputPort.png");
		}
		else if(type==10013){
			path.append("32x32_Event_InputPort.png");
		}
		else if(type==10014){
			path.append("32x32_Event_OutputPort.png");
		}
		else if(type==10016){
			path.append("32x32_Method_Required.png");
		}
		else if(type==10015){
			path.append("32x32_Method_Provided.png");
		}
		*/
		//		WJH 090819 E

		else if(type==10110){
			path.append("icno_16x16/05_Environment.png");
		}
		else if(type==10111){
			path.append("icno_16x16/04_Semantics.png");
		}
		else if(type==10112){
			path.append("icno_16x16/06_Properties.png");
		}
		else if(type==10113){
			path.append("icno_16x16/06_Properties.png");
		}
		else if(type==10114){
			path.append("property_icon.gif");
		}
		else if(type==10115){
			path.append("icno_16x16/07_DataTypes.png");
		}
		else if(type==10116){
			path.append("data_type_icon.gif");
		}
		else if(type==10117){
			path.append("icno_16x16/08_ServiceTypes.png");
		}
		else if(type==10118){
			path.append("service_type_icon.gif");
		}
		else if(type==10118){
			path.append("service_type_icon.gif");
		}
		else if(type==10119){
			path.append("cpu_icon.gif");
		}
		else if(type==10120){
			path.append("os_icon.gif");
		}
		else if(type==10201){
			path.append("BAR3_C.png");
		}


		else if(type==10202){
			path.append("start.png");
		}
		else if(type==10203){
			path.append("stop.png");
		}
		else if(type==10204){
			path.append("check.png");
		}
		
		//테두리 부분 추가
		//컴포넌트 부분
		else if(type==19001){
			path.append("Original_Atomic_ATR.png");
		}
		else if(type==19004){
			path.append("Original_Atomic_ATL.png");
		}
		else if(type==19002){
			path.append("Original_Atomic_CT.png");
		}
		else if(type==19003){
			path.append("Original_Composite_CT.png");
		}
		
		//BAR 부분
		else if(type==19011){
			path.append("BAR_CT.png");
		}
		else if(type==19012){
			path.append("BAR3_CT.png");
		}
		else if(type==19013){
			path.append("BAR2_CT.png");
		}



		//		WJH 090801 E
		//16x16_Method_Provided

		//PKY 08070904 E 창 관련 Outline 아이콘 추가
		return path.toString();
		//PKY 08070301 E 다이얼로그 이름 및 아이콘 넣기

	}

	//	WJH 090801
	public Image getImageReSize(int p,int width,int height){
		try{
			ImageLoader loader = new ImageLoader();
			String path = this.getIconPath(p);


			String currentDir = System.getProperty("java.home");
			if(this.actorDialogPrevieweImgMap.get(path)==null){
				Image img = ImageDescriptor.createFromFile(UseCaseModel.class, path).createImage();

				//	    		  File file = new File(path);
				//	    		  if(file.canRead()){
				//	    			  ImageData[] imageData =loader.load(path);
				if(img.getImageData() != null){//imageData.length>0){
					//	    				  img.getImageData().s
					//	    				  img.getImageData().height
					ImageData miniViewImageData=img.getImageData().scaledTo(width, height);
					//imageData[0].scaledTo(width,height);
					ImageDescriptor miniViewImage = ImageDescriptor.createFromImageData(miniViewImageData);
					Image miniViewImg =miniViewImage.createImage();

					this.actorDialogPrevieweImgMap.put(path, miniViewImageData);
					return miniViewImg;
				}
				else 
					return null;
				//	    		  }else{
				//
				//	    			  return null;	
				//	    		  }	
			}	
			else{
				ImageData imageData = (ImageData)this.actorDialogPrevieweImgMap.get(path);
				ImageData miniViewImageData=imageData.scaledTo(width, height);
				//	    			  imageData[0].scaledTo(width,height);
				//	    		  ImageData miniViewImageData=imageData.scaledTo(imageData.width,  imageData.height);
				ImageDescriptor miniViewImage = ImageDescriptor.createFromImageData(miniViewImageData);
				Image miniViewImg =miniViewImage.createImage(); 
				return miniViewImg;
			}
		}catch(Exception e){   
			//   e.printStackTrace();
			Image img 	= ImageDescriptor.createFromFile(UseCaseModel.class,  "icons/"+p).createImage();
			return null;//PKY 08101509 S
		}	      	
	}
	//	  WJH 090801


	public Image getImageReSize2(int p,int width,int height){
		try{
			ImageLoader loader = new ImageLoader();
			String path = this.getIconPath(p);


			String currentDir = System.getProperty("java.home");
			if(this.actorDialogPrevieweImgMap.get(path)==null){
				Image img = ImageDescriptor.createFromFile(UseCaseModel.class, path).createImage();

				//	    		  File file = new File(path);
				//	    		  if(file.canRead()){
				//	    			  ImageData[] imageData =loader.load(path);
				if(img.getImageData() != null){//imageData.length>0){
					//	    				  img.getImageData().s
					//	    				  img.getImageData().height
					//	    				  ImageData miniViewImageData=img.getImageData().scaledTo(width, height);
					//imageData[0].scaledTo(width,height);
					ImageDescriptor miniViewImage = ImageDescriptor.createFromImageData(img.getImageData());
					Image miniViewImg =miniViewImage.createImage();

					this.actorDialogPrevieweImgMap.put(path, img.getImageData());
					return miniViewImg;
				}
				else 
					return null;
				//	    		  }else{
				//
				//	    			  return null;	
				//	    		  }	
			}	
			else{
				ImageData imageData = (ImageData)this.actorDialogPrevieweImgMap.get(path);
				ImageData miniViewImageData=imageData.scaledTo(width, height);
				//	    			  imageData[0].scaledTo(width,height);
				//	    		  ImageData miniViewImageData=imageData.scaledTo(imageData.width,  imageData.height);
				ImageDescriptor miniViewImage = ImageDescriptor.createFromImageData(miniViewImageData);
				Image miniViewImg =miniViewImage.createImage(); 
				return miniViewImg;
			}
		}catch(Exception e){   
			//   e.printStackTrace();
			Image img 	= ImageDescriptor.createFromFile(UseCaseModel.class,  "icons/"+p).createImage();
			return null;//PKY 08101509 S
		}	      	
	}
	//	  WJH 090801

	public Image getDiagramImage(int type){
		try{
			if(this.imageMap.get(this.getIconDiagramPath(type))==null){
				Image img = ImageDescriptor.createFromFile(UseCaseModel.class, this.getIconDiagramPath(type)).createImage();
				this.imageMap.put(this.getIconDiagramPath(type), img);
				return img;
			}
			else{
				Image img = (Image)this.imageMap.get(this.getIconDiagramPath(type));
				return img;
			}
		}
		catch(Exception e){

		}
		return null;
		//		return ImageDescriptor.createFromFile(UseCaseModel.class, this.getIconDiagramPath(type)).createImage();


	}

	public Image getImage(int type){
		try{
			if(this.imageMap.get(this.getIconPath(type))==null){
				Image img = ImageDescriptor.createFromFile(UseCaseModel.class, this.getIconPath(type)).createImage();
				this.imageMap.put(this.getIconPath(type), img);
				return img;
			}
			else{
				Image img = (Image)this.imageMap.get(this.getIconPath(type));
				return img;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;

	}
	//PKY 08080501 S Image Dialog 이미지 리스트 및 불러오기 편하도록 개선
	public Image getActorImage(String p){
		//20080822IJS 전체 수정
		try{
			ImageLoader loader = new ImageLoader();
			if(!p.trim().equals("")){
				if(this.imageMap.get(p)==null){
					File file = new File(p);
					if(file.canRead()){
						ImageData[] imageData =loader.load(p);
						if(imageData.length>0){
							ImageData ViewImageData=imageData[0];
							ImageDescriptor ViewImage = ImageDescriptor.createFromImageData(ViewImageData);
							Image ViewImg =ViewImage.createImage();
							this.imageMap.put(p, ViewImg);
							return ViewImg;
						}
						else 
							return null;
					}else{
						return null;
					}
				}
				else{
					Image img = (Image)this.imageMap.get(p);
					return img;
				}
			}


		}catch(Exception e){			
			e.printStackTrace();

		}
		return null;

	}
	public Image getActorImageDialog(String p){
		try{
			ImageLoader loader = new ImageLoader();

			if(this.actorDialogPrevieweImgMap.get(p)==null){
				File file = new File(p);
				if(file.canRead()){
					ImageData[] imageData =loader.load(p);
					if(imageData.length>0){
						ImageData miniViewImageData=imageData[0].scaledTo(288,389);
						ImageDescriptor miniViewImage = ImageDescriptor.createFromImageData(miniViewImageData);
						Image miniViewImg =miniViewImage.createImage();
						this.actorDialogPrevieweImgMap.put(p, miniViewImg);
						return miniViewImg;
					}
					else 
						return null;
				}else{
					return null;
				}
			}
			else{
				Image img = (Image)this.actorDialogPrevieweImgMap.get(p);
				return img;
			}
		}catch(Exception e){			
			e.printStackTrace();
			Image img = ImageDescriptor.createFromFile(UseCaseModel.class,  "icons/"+p).createImage();
			return img;
		}

	}
	//PKY 08080501 E Image Dialog 이미지 리스트 및 불러오기 편하도록 개선
	//ijs080429
	public Image getImage(String p){
		if(this.imageMap.get(p)==null){

			Image img = ImageDescriptor.createFromFile(UseCaseModel.class,  "icons/"+p).createImage();
			if(img.getImageData().width>-1){
				System.out.print("");
			}
			this.imageMap.put(p, img);
			return img;
		}
		else{
			Image img = (Image)this.imageMap.get(p);
			return img;
		}

	}



	public java.util.ArrayList getSearchModel() {
		return searchModel;
	}

	public void setSearchModel(java.util.ArrayList searchModel) {
		this.searchModel = searchModel;
	}

	public java.util.ArrayList getSearchText() {
		return searchText;
	}

	public void setSearchText(java.util.ArrayList searchText) {
		this.searchText = searchText;
	}

	public boolean isAddLine() {
		return addLine;
	}

	public void setAddLine(boolean addLine) {
		this.addLine = addLine;
	}

	public java.util.ArrayList getModelList() {
		return modelList;
	}

	public void setModelList(java.util.ArrayList modelList) {
		this.modelList = modelList;
	}

	public boolean isSearchModel() {
		return isSearchModel;
	}

	public void setSearchModel(boolean isSearchModel) {
		this.isSearchModel = isSearchModel;
	}

	public UMLElementModel getSelectPropertyUMLElementModel() {
		return selectPropertyUMLElementModel;
	}

	public void setSelectPropertyUMLElementModel(UMLElementModel p) {
		System.out.println("p------------------->"+p);
		
		//20110816SDM perspective 변경 및  allsync 기능 추가>> 삭제 UMLEditor와 PerspectiveChangActoin 클래스에서 처리
////		WJH 110805 S perspective 변경 및  allsync 기능 추가
//		 try{
////				 IPerspectiveDescriptor[] pre = PlatformUI.getWorkbench().getPerspectiveRegistry().getPerspectives();
//			 WorkbenchWindow openWorkbench = (WorkbenchWindow)PlatformUI.getWorkbench().getActiveWorkbenchWindow();
//			 System.out.println("open Perspective : "+openWorkbench.getActivePage().getPerspective().getId());
//			 if(!openWorkbench.getActivePage().getPerspective().getId().equals("uuu.perspective")){
//				 PlatformUI.getWorkbench().showPerspective("uuu.perspective", this.window);
//				 RootCmpEdtTreeModel editorModel = getModelBrowser().getRcet();
//				 getModelBrowser().allSync(editorModel);
//			 }
////				 PlatformUI.getWorkbench().showPerspective("org.eclipse.team.ui.TeamSynchronizingPerspective", this.window);
//		 }
//		 catch(Exception e){
//			 e.printStackTrace();
//		 }
////		WJH 110805 E perspective 변경 및  allsync 기능 추가
		if(p!=null && !p.toString().trim().equals("")){
			this.selectPropertyUMLElementModel = p;
		}
		//		else{
		//			if(p instanceof TypeRefModel){
		//				if(p.getAcceptParentModel()!=null)
		//				p = p.getAcceptParentModel();
		//				
		//			}
		//			System.out.println("p------------------->fdfdfdfd"+p);
		//		}
	}

	public java.util.ArrayList getTypeModel() {
		return typeModel;
	}

	public void setTypeModel(java.util.ArrayList typeModel) {
		this.typeModel = typeModel;
	}
	//PKY 08052901 S 저장 불러오기할경우 참조타입정보가 없다 참조타입정보를 넣어 줄수 있도록 수정

	public void addTypeModel(Object obj) {
		this.typeModel.add(obj);
		//20080811IJS 시작
		UMLTreeModel ut = (UMLTreeModel)obj; 
		//		TeamProjectManager.getInstance().setModel((UMLModel)ut.getRefModel());
		//20080811IJS 끝
	}
	
	//20110721서동민>>코어 수정
	public void modTypeModel(Object oldObj, Object newObj) {
		int index = this.typeModel.indexOf(oldObj);
		this.typeModel.remove(index);
		this.typeModel.add(index, newObj);
	}
	
	//PKY 08052901 E 저장 불러오기할경우 참조타입정보가 없다 참조타입정보를 넣어 줄수 있도록 수정
	public int getCopyName(UMLTreeParentModel packages ,UMLModel copyChild,String name,int mType){
		if(packages!=null){
			for(int i=0;i<packages.getChildren().length;i++){
				UMLTreeModel ut = (UMLTreeModel)packages.getChildren()[i];
				Object obj = ut.getRefModel();
				if(obj instanceof UMLModel){
					UMLModel um = (UMLModel)obj;
					if(um.getName().equals(name) && mType==ut.getModelType()){
						++this.copyNum;
						String pname = this.oldName+this.copyNum;
						//						this.copyNum++;
						return this.getCopyName(packages, copyChild,pname,mType);
					}
					if(um instanceof N3EditorDiagramModel){
						N3EditorDiagramModel n3Diagram = (N3EditorDiagramModel)um;
						for( int j = 0 ; j < n3Diagram.getChildren().size(); j ++){
							if(n3Diagram.getChildren().get(j) instanceof UMLModel){
								UMLModel model = (UMLModel)n3Diagram.getChildren().get(j);
								if(model.getName().equals(name)){
									++this.copyNum;
									//									V1.02 WJH E 080822 S 객체 생성시 기본 이름이 숫자 0으로 시작하도록 수정 
									String pname = "";
									if(name.startsWith(defaultName)){
										pname = name.substring(0, defaultName.length())+this.copyNum;										
									}
									else{
										pname = this.oldName+this.copyNum;
									}
									//									V1.02 WJH E 080822 E 객체 생성시 기본 이름이 숫자 0으로 시작하도록 수정
									//									String pname = this.oldName+this.copyNum;
									//									this.copyNum++;
									return this.getCopyName(packages, copyChild,pname,mType);
								}
							}
						}
					}
				}

			}
			return this.copyNum;

		}
		return -1;
	}


	public boolean isOverlapping(UMLTreeParentModel packages ,String name){
		if(packages!=null){

			for(int i=0;i<=packages.getChildren().length-1;i++){
				UMLTreeModel ut = (UMLTreeModel)packages.getChildren()[i];
				//				Object obj = ut.getRefModel();
				if(ut.getName().equals(name) ){
					return true;
				}
				//				if(obj instanceof UMLModel){
				//					UMLModel um = (UMLModel)obj;
				//					int type2 = ut.getModelType();
				//					if(ut.getName().equals(name) ){
				//						return true;
				//					}
				//				}

			}
			return false;

		}
		return false;
	}

	public boolean isOverlapping(UMLTreeParentModel packages ,int type,String name){
		if(packages!=null){

			for(int i=0;i<packages.getChildren().length;i++){
				UMLTreeModel ut = (UMLTreeModel)packages.getChildren()[i];
				Object obj = ut.getRefModel();
				if(obj instanceof UMLModel){
					UMLModel um = (UMLModel)obj;
					int type2 = ut.getModelType();
					if(ut.getName().equals(name) && type==ut.getModelType()){
						return true;
					}
				}

			}
			return false;

		}
		return false;
	}
	//20080811IJS
	public boolean isOverlapping(UMLTreeParentModel packages ,int type,String name,UMLTreeModel ud){
		if(packages!=null){
			for(int i=0;i<packages.getChildren().length;i++){
				UMLTreeModel ut = (UMLTreeModel)packages.getChildren()[i];
				Object obj = ut.getRefModel();
				if(obj instanceof UMLModel && ud!=ut){
					UMLModel um = (UMLModel)obj;
					if(um.getName().equals(name) && type==ut.getModelType()){
						return true;
					}
				}

			}
			return false;

		}
		return false;
	}

	//KDI 080908 0002 s
	public boolean isOverlapping(UMLTreeParentModel packages, int type, String name, boolean isDgm){
		if(packages!=null){
			for(int i=0;i<packages.getChildren().length;i++){
				UMLTreeModel ut = (UMLTreeModel)packages.getChildren()[i];
				Object obj = ut.getRefModel();
				if(obj instanceof UMLModel){
					UMLModel um = (UMLModel)obj;
					if(isDgm){
						if(um instanceof N3EditorDiagramModel){
							if(((N3EditorDiagramModel)um).getName().equals(name) 
									&& ((N3EditorDiagramModel)um).getDiagramType() == type){
								return true;
							}
						}
					}
					else{
						if(um.getName().equals(name) && type==ut.getModelType()){
							return true;
						}
					}					
				}

			}
			return false;

		}
		return false;
	}
	//KDI 080908 0002 e

	//20080325 PKY S 검색
	public boolean isSearchDiagaramModel() {
		return isSearchDiagaramModel;
	}

	public void setisSearchDiagaramModel(boolean isSearchDiagaramModel) {
		this.isSearchDiagaramModel = isSearchDiagaramModel;
	}

	public  List getCopyList() {
		return copyList;
	}

	public  void setCopyList(List p) {
		copyList = p;
	}
	//2008040401PKY S "패키지 오른쪽 클릭 시 OpenDiagram 메뉴 추가    여러 다이어그램이있을경우 리스트 나오도록"

	public java.util.ArrayList getDiagramsSub() {
		return diagramsSub;
	}

	public void setDiagramsSub(java.util.ArrayList diagramsUsb) {
		this.diagramsSub = diagramsUsb;
	}
	//2008040401PKY E "패키지 오른쪽 클릭 시 OpenDiagram 메뉴 추가    여러 다이어그램이있을경우 리스트 나오도록"

	public List getCopyattList() {
		return copyattList;
	}

	public void setCopyattList(List copyattList) {
		this.copyattList = copyattList;
	}

	public List getCopyoperList() {
		return copyoperList;
	}

	public void setCopyoperList(List copyoperList) {
		this.copyoperList = copyoperList;
	}

	public boolean isOutlineAutoSize() {
		return isOutlineAutoSize;
	}

	public void setOutlineAutoSize(boolean isOutlineAutoSize) {
		this.isOutlineAutoSize = isOutlineAutoSize;
	}

	public String getCurrentProjectPath() {
		return currentProjectPath;
	}

	public void setCurrentProjectPath(String currentProjectPath) {
		this.currentProjectPath = currentProjectPath;
	}

	public String getCurrentProjectName() {
		return currentProjectName;
	}

	public void setCurrentProjectName(String currentProjectName) {
		this.currentProjectName = currentProjectName;
		int index = currentProjectName.lastIndexOf(".nmdl");
		if(index>0){
			String pName = currentProjectName.substring(0,index);
			this.modelBrowser.getRoot().setName(pName);
			try{
				this.modelBrowser.refresh(this.modelBrowser.getRoot());
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}



	}
	//PKY 08072401 S O 일부상황에서 OpenList에서 오픈 시 에러 발생문제 수정
	public String getCurrentProject(){	//111206 SDM S - 리눅스 파일경로 문제 수정. "\\"부분을 File.separator으로 수정
		if(this.currentProjectPath.lastIndexOf(File.separator)==this.currentProjectPath.length()-1){
			return this.currentProjectPath+this.currentProjectName;//PKY 08070701 S 파일 오픈 시 java.io.FileNotFoundException 문제 수정
		}else{
			return this.currentProjectPath+File.separator+this.currentProjectName;//PKY 08070701 S 파일 오픈 시 java.io.FileNotFoundException 문제 수정		}
		}
	}
	//PKY 08072401 E O 일부상황에서 OpenList에서 오픈 시 에러 발생문제 수정

	public IWorkbenchWindowConfigurer getConfigurer() {
		return configurer;
	}

	public void setConfigurer(IWorkbenchWindowConfigurer configurer) {
		this.configurer = configurer;
	}
	public void loadConfig(){
		try{
			FileReader fw = new FileReader(new File("c:\\config.txt"));

			BufferedReader bf = new BufferedReader(fw);
			String line = null;
			while((line=bf.readLine())!=null){
				int indexOpen = line.indexOf("PEN=");
				int indexSourceFolder = line.indexOf("OURCE_FOLDER=");
				int indexColorFill = line.indexOf("COLOR_FILL=");//PKY 08080501 S 사용자가 컬러색상을 수정 할 수있도록 수정
				int indexActorImg = line.indexOf("OPEN_ACTOR_IMG=");
				int indexTeamProjetFolder = line.indexOf("TEAM_PROJECT_FOLDER=");//PKY 08082201 S 팀 프로젝트 경로를 프로젝트에서 변경가능하도록

				int indexReportTemplate = line.indexOf("REPORT_TEMPLATE_FILE="); //20080904 KDI s


				int indexmPath = line.indexOf("M_FOLDER=");

				int indexsPath = line.indexOf("S_FOLDER=");

				int indexetc1Path = line.indexOf("ETC1_FOLDER=");

				int indexect2Path = line.indexOf("ETC2_FOLDER=");

				if(indexOpen==1){
					String openPath = line.substring(5);
					this.addOpenProject(openPath);
				}

				if(indexSourceFolder==1){
					String sourceFolder = line.substring(14);
					this.setSourceFolder();	//110905 SDM - 로컬경로부분 수정
				}

				if(indexmPath==0){
					String sourceFolder = line.substring(9);
					this.setMPath(sourceFolder);
				}

				if(indexsPath==0){
					String sourceFolder = line.substring(9);
					this.setSPath(sourceFolder);
				}

				if(indexetc1Path==0){
					String sourceFolder = line.substring(12);
					this.setEtc1Path(sourceFolder);
				}

				if(indexect2Path==0){
					String sourceFolder = line.substring(12);
					this.setEct2Path(sourceFolder);
				}

				//PKY 08082201 S 팀 프로젝트 경로를 프로젝트에서 변경가능하도록
				if(indexTeamProjetFolder==0){
					String sourceFolder = line.substring(20);
					this.setTeamProjectFolder(sourceFolder);
				}
				//PKY 08082201 E 팀 프로젝트 경로를 프로젝트에서 변경가능하도록

				//PKY 08080501 S 사용자가 컬러색상을 수정 할 수있도록 수정
				if(indexColorFill==0){
					String [] colorFill  = line.substring(11).split(",");

					this.setDefaultColor(new Color(null,Integer.parseInt(colorFill[0]),Integer.parseInt(colorFill[1]),Integer.parseInt(colorFill[2])));					
				}
				if(indexActorImg==0){
					String actorImg = line.substring(15);
					this.addActorImageList(actorImg);
				}
				//PKY 08080501 E 사용자가 컬러색상을 수정 할 수있도록 수정

				//20080904 KDI s
				if(indexReportTemplate==0){
					String path = line.substring(21);
					this.setReportTemplatePath(path);
				}
				//20080904 KDI e


			}
			bf.close();

		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public void writeConfig(){
		try{
			//PKY 08080501 S 사용자가 컬러색상을 수정 할 수있도록 수정
			FileWriter fw = new FileWriter(new File("c://config.txt"),false);
			PrintWriter bw=new PrintWriter(fw);
			for(int i=0;i<this.openProjects.size();i++){
				String op = (String)this.openProjects.get(i);
				bw.println("OPEN="+op);
			}
			if(this.sourceFolder!=null){
				bw.println("SOURCE_FOLDER="+sourceFolder);
			}

			if(this.defaultColor!=null){
				bw.println("COLOR_FILL="+defaultColor.getRed()+","+defaultColor.getGreen()+","+defaultColor.getBlue()+"\n");//PKY 08081101 S N3Com 프로퍼티를 오픈 후 OK버튼을 누루면 내용이 저장되도록 기존에는 정상적인 종료를 했을때만 프로그램 내용이 저장되도록 되어있었음
			}
			for(int i=0;i<this.actorImageList.size();i++){
				bw.println("OPEN_ACTOR_IMG="+actorImageList.get(i));
			}
			//PKY 08080501 E 사용자가 컬러색상을 수정 할 수있도록 수정
			//PKY 08082201 S 팀 프로젝트 경로를 프로젝트에서 변경가능하도록
			if(this.teamProjectFolder!=null){
				bw.println("TEAM_PROJECT_FOLDER="+teamProjectFolder);
			}
			//PKY 08082201 E 팀 프로젝트 경로를 프로젝트에서 변경가능하도록

			//			bw.write(sb.toString());
			//20080904 KDI s
			if(this.reportTemplatePath != null){
				bw.println("REPORT_TEMPLATE_FILE="+reportTemplatePath);
			}
			//20080904 KDI e

			if(this.mPath != null){
				bw.println("M_FOLDER="+mPath);
			}

			if(this.sPath != null){
				bw.println("S_FOLDER="+sPath);
			}

			if(this.etc1Path != null){
				bw.println("ETC1_FOLDER="+etc1Path);
			}

			if(this.ect2Path != null){
				bw.println("ETC2_FOLDER="+ect2Path);
			}


			bw.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}

	public java.util.ArrayList getOpenProjects() {
		return openProjects;
	}

	public void setOpenProjects(java.util.ArrayList openProjects) {
		this.openProjects = openProjects;
	}

	public MenuManager getRecentMenu() {
		return recentMenu;
	}

	public void setRecentMenu(MenuManager recentMenu) {
		this.recentMenu = recentMenu;
	}

	//	public java.util.HashMap getReverseModelMap() {
	//	return reverseModelMap;
	//	}

	//	public void setReverseModelMap(java.util.HashMap reverseModelMap) {
	//	this.reverseModelMap = reverseModelMap;
	//	}
	//ijs0507
	public java.util.HashMap getReverseModelMap() {
		return reverseModelMap;
	}
	//ijs0507
	public void setReverseModelMap(java.util.HashMap reverseModelMap) {
		this.reverseModelMap = reverseModelMap;
	}
	
	//111026 SDM S - 어플리케이션 저작권 정보 받아오기
	//버전
	public String getAppVersion() {
		return org.opros.mainpreference.Activator.getDefault().getPreferenceStore().getString(org.opros.mainpreference.preferences.PreferenceConstants.COPYRIGHT_VERSION).toString();	//110905 SDM Local Repository Path 설정
	}
	//라이센스
	public String getAppLicense() {
		return org.opros.mainpreference.Activator.getDefault().getPreferenceStore().getString(org.opros.mainpreference.preferences.PreferenceConstants.COPYRIGHT_LICENSE).toString();	//110905 SDM Local Repository Path 설정
	}
	//저작자
	public String getAppName() {
		return org.opros.mainpreference.Activator.getDefault().getPreferenceStore().getString(org.opros.mainpreference.preferences.PreferenceConstants.COPYRIGHT_NAME).toString();	//110905 SDM Local Repository Path 설정
	}
	//전화
	public String getAppPhone() {
		return org.opros.mainpreference.Activator.getDefault().getPreferenceStore().getString(org.opros.mainpreference.preferences.PreferenceConstants.COPYRIGHT_PHONE).toString();	//110905 SDM Local Repository Path 설정
	}
	//주소
	public String getAppAddress() {
		return org.opros.mainpreference.Activator.getDefault().getPreferenceStore().getString(org.opros.mainpreference.preferences.PreferenceConstants.COPYRIGHT_ADDRESS).toString();	//110905 SDM Local Repository Path 설정
	}
	//홈페이지
	public String getAppHomepage() {
		return org.opros.mainpreference.Activator.getDefault().getPreferenceStore().getString(org.opros.mainpreference.preferences.PreferenceConstants.COPYRIGHT_HOMEPAGE).toString();	//110905 SDM Local Repository Path 설정
	}
	//111026 SDM E - 어플리케이션 저작권 정보 받아오기
	

	public String getSourceFolder() {
		System.out.println("=======>" + sourceFolder);
//		return sourceFolder;
		
		//110905 S SDM - 로컬리파지토리경로 받아오기
		
		return org.opros.mainpreference.Activator.getDefault().getPreferenceStore().getString(org.opros.mainpreference.preferences.PreferenceConstants.LOCAL_REPOSITORY_PATH).toString();	//110905 SDM Local Repository Path 설정
		//110905 E SDM - 로컬리파지토리경로 받아오기
	}

	public void setSourceFolder() {
		this.sourceFolder = org.opros.mainpreference.Activator.getDefault().getPreferenceStore().getString(org.opros.mainpreference.preferences.PreferenceConstants.LOCAL_REPOSITORY_PATH).toString();	//110905 SDM Local Repository Path 설정
	//		this.sourceFolder = sourceFolder;
		
		

	}
	//ijs08619
	public CommandStack getCommandStack() {
		if(this.getUMLEditor()!=null)
			return (CommandStack)this.getUMLEditor().getAdapter(CommandStack.class);
		else 
			return null;
	}
	//PKY 08070301 S 툴바 추가작업
	public ApplicationActionBarAdvisor getApplication() {
		return application;
	}

	public void setApplication(ApplicationActionBarAdvisor application) {
		this.application = application;
	}
	//PKY 08070301 E 툴바 추가작업

	public IViewSite getIvewSite() {
		return ivewSite;
	}

	public void setIvewSite(IViewSite ivewSite) {
		this.ivewSite = ivewSite;
	}

	public TreeViewer getViewer() {
		return viewer;
	}

	public void setViewer(TreeViewer viewer) {
		this.viewer = viewer;
	}

	public RootTreeModel getRoot() {
		return root;
	}

	public void setRoot(RootTreeModel root) {
		this.root = root;
	}

	public boolean isViewProperty() {
		return isViewProperty;
	}

	public void setViewProperty(boolean isViewProperty) {
		this.isViewProperty = isViewProperty;
	}
	//PKY 08070901 S 모델 컬러 변경
	public Color getDefaultColor() {
		return defaultColor;
	}

	public void setDefaultColor(Color defaultColor) {
		this.defaultColor = defaultColor;
	}
	//PKY 08070901 E 모델 컬러 변경

	//20080619 KDI s
	public java.util.ArrayList getRecentReportPath(){
		return this.recentReportPath;
	}

	public void setRecentReportPath(java.util.ArrayList list){
		this.recentReportPath = list;
	}
	//20080619 KDI e

	//20080725 KDI s
	public DescriptionView getDescriptionView(){
		return descriptionView;
	}
	public void setDescriptionView(DescriptionView p) {
		this.descriptionView = p;
	}
	//20080725 KDI e

	//PKY 08081801 S 오퍼레이션,어트리뷰트 창 추가
	public OperationView getOperationView(){
		return operationView;
	}
	public void setOperationView(OperationView p) {
		this.operationView = p;
	}
	public AttributeView getAttributeView(){
		return attributeView;
	}
	public void setAttributeView(AttributeView p) {
		this.attributeView = p;
	}
	//PKY 08081801 E 오퍼레이션,어트리뷰트 창 추가

	//PKY 08080501 S RequirementID를 다이얼로그 리스트로 보여주도록 
	public java.util.ArrayList getReqIdList() {
		return reqIdList;
	}

	public void setReqIdList(java.util.ArrayList reqIdList) {
		this.reqIdList = reqIdList;
	}
	public void addActorImageList(String s) {
		for(int i = 0 ; i < this.actorImageList.size(); i++){
			if(((String)this.actorImageList.get(i)).trim().equals(s)){
				this.actorImageList.remove(i);
				i=this.actorImageList.size();
			}
		}
		//PKY 08081101 S 프로젝트 전체의 객체 테두리 색상 설정 할  수있도록 수정
		if(this.actorImageList.size()==21){
			this.actorImageList.remove(0);
		}
		//PKY 08081101 E 프로젝트 전체의 객체 테두리 색상 설정 할  수있도록 수정

		this.actorImageList.add(s);
	}
	public java.util.ArrayList getActorImageList() {
		return actorImageList;
	}

	public void setActorImageList(java.util.ArrayList actorImageList) {
		this.actorImageList = actorImageList;
	}
	//PKY 08080501 E RequirementID를 다이얼로그 리스트로 보여주도록 
	public boolean isTreeMove() {
		return isTreeMove;
	}

	public void setTreeMove(boolean isTreeMove) {
		this.isTreeMove = isTreeMove;
	}
	//PKY 08082201 S ClassModel가져오는 오퍼레이션 추가
	public ClassModel getClassModel(UMLModel umlModel){

		if(umlModel instanceof ClassifierModel){
			return ((ClassifierModel)umlModel).getClassModel();
		}else if(umlModel instanceof ClassifierModelTextAttach){
			return ((ClassifierModelTextAttach)umlModel).getClassModel();
		}
		return null;
	}


	public boolean isItemEnable(Object model){

		if(model instanceof UMLModel){
			ClassModel classModel=getClassModel((UMLModel)model);

			if(classModel!=null){
				if((classModel.isReadOnlyModel()||!classModel.isExistModel())||((UMLModel)model).isReadOnlyModel()||!((UMLModel)model).isExistModel()){
					return false;
				}
			}else {
				UMLModel umlModel = (UMLModel)model;
				if( umlModel.getUMLTreeModel()==null&&( umlModel.isReadOnlyModel()||! umlModel.isExistModel())){
					return false;
				}
			}

		}else if(model instanceof LineModel){
			if(((LineModel)model).getDiagram()!=null){
				if(((LineModel)model).getDiagram().isReadOnlyModel()||!((LineModel)model).getDiagram().isExistModel()){
					return false;
				}
			}
		}else if(model instanceof UMLTreeEditPart){
			UMLTreeEditPart editPart = (UMLTreeEditPart)model; 

			ClassModel classModel=getClassModel((UMLModel)editPart.getModel());
			if(classModel!=null){
				if(((UMLModel)editPart.getModel()).getUMLTreeModel()==null||classModel.isReadOnlyModel()||!classModel.isExistModel()){
					return false;
				}
			}else {
				UMLModel umlModel = (UMLModel)model;
				if( umlModel.getUMLTreeModel()==null|| umlModel.isReadOnlyModel()||! umlModel.isExistModel()){
					return false;
				}
			}


		}
		return true;
	}
	//PKY 08082201 E ClassModel가져오는 오퍼레이션 추가
	//PKY 08082201 S 팀 프로젝트 경로를 프로젝트에서 변경가능하도록
	public String getTeamProjectFolder() {
		return teamProjectFolder;
	}

	public void setTeamProjectFolder(String teamProjectFolder) {
		this.teamProjectFolder = teamProjectFolder;
	}
	//PKY 08082201 E 팀 프로젝트 경로를 프로젝트에서 변경가능하도록
	//PKY 08090201 S 링크파일 하위로 트리를이동하려고할경우 안되도록 막도록 수정
	public boolean isTreeModelLink(UMLTreeModel umlTreeModel){
		boolean isLink = false;
		if(umlTreeModel!=null)
			if(umlTreeModel instanceof PackageTreeModel){
				PackageTreeModel treModel = (PackageTreeModel)umlTreeModel;
				if(treModel.isLink()){
					isLink = true;
					return true;

				}
				else if(treModel.getParent()!=null && treModel.getParent() instanceof PackageTreeModel ){
					isLink = isTreeModelLink(treModel.getParent());
				}
			}
		return isLink;
	}
	//PKY 08090201 E 링크파일 하위로 트리를이동하려고할경우 안되도록 막도록 수정


	//20080812 KDI s
	public HashMap getWordTreeMap(){
		return reportWordTreeMap;
	}

	public void addWordTreeMap(Object key, Object obj){
		reportWordTreeMap.put(key, obj); 
	}

	public void clearWordTreeMap(){
		reportWordTreeMap.clear();
	}
	public boolean containKeyWordTreeMap(Object key){
		return reportWordTreeMap.containsKey(key);
	}
	//20080812 KDI e

	//20080813 KDI s
	public void setSearchDialogModelType(int idx){
		searchDialogModelType = idx;
	}

	public int getSearchDialogModelType(){
		return searchDialogModelType;
	}

	public HashMap getTempTreeBrowserMap(){
		return reportTempTreeBrowserMap;
	}

	public void addTempTreeBrowserMap(Object key, Object obj){
		reportTempTreeBrowserMap.put(key, obj); 
	}

	public void clearTempTreeBrowserMap(){
		reportTempTreeBrowserMap.clear();
	}

	public boolean containKeyTempTreeBrowserMap(Object key){
		return reportTempTreeBrowserMap.containsKey(key);
	}
	//20080813 KDI e

	//20080902 KDI s
	public String getReportOutPutPath(){
		return reportOutPutPath;
	}

	public void setReportOutPutPath(String str){
		reportOutPutPath = str;
	}//20080902 KDI e

	//20080903 KDI s
	public String getReportTemplatePath(){
		return reportTemplatePath;
	}

	public void setReportTemplatePath(String str){
		reportTemplatePath = str;
	}
	//20080903 KDI e
	//PKY 08091003 S

	public java.util.ArrayList getFrameModels() {
		return frameModels;
	}

	public void setFrameModels(java.util.ArrayList frameModels) {
		this.frameModels = frameModels;
	}

	public void addFrameModel(FrameModel frame){
		this.frameModels.add(frame);
	}
	public void fraeModelrefreshDiagram(){
		for(int i = 0; i < this.frameModels.size(); i ++){
			FrameModel frameModel = (FrameModel)this.frameModels.get(i);
			frameModel.refreshDiagram(frameModel.getN3EditorDiagramModel());
		}
	}

	//PKY 08091003 E

	//20080908IJS 시작
	public int getVvalue() {
		return Vvalue;
	}

	public void setVvalue(int vvalue) {
		Vvalue = vvalue;
	}


	public boolean isMsgDrag() {
		return isMsgDrag;
	}

	public void setMsgDrag(boolean isMsgDrag) {
		this.isMsgDrag = isMsgDrag;
	}
	//20080908IJS 끝	

	public void writeLog(String str,Exception e1){
		try{
			//111206 SDM  S - 로그파일 생성 이클립스 폴더 내부로 옮김.
			Properties P = System.getProperties();
			String strEclipseDir = P.get("user.dir").toString();
			FileWriter fw = new FileWriter(new File(strEclipseDir + File.separator + "composer_err.log"),true);
			//111206 SDM  E - 로그파일 생성 이클립스 폴더 내부로 옮김.
			PrintWriter bw=new PrintWriter(fw);

			if(e1!=null)
				e1.printStackTrace(bw);
			if(str!=null)
				bw.println(str);

			bw.close();


		}
		catch(Exception e){
			e.printStackTrace();

		}

	}

	public boolean isCopy() {
		return isCopy;
	}

	public void setCopy(boolean isCopy) {
		this.isCopy = isCopy;
	}

	public boolean isLoad() {
		return isLoad;
	}

	public void setLoad(boolean isLoad) {
		this.isLoad = isLoad;
	}

	public boolean isMainLoad() {
		return isMainLoad;
	}

	public void setMainLoad(boolean isMainLoad) {
		this.isMainLoad = isMainLoad;
	}


	private void createProject2(String path,AtomicComponentModel atm){

		IWorkbenchPage page= this.getModelBrowser().getSite().getWorkbenchWindow().getActivePage();


		try {
			IPath location1 = Path.fromOSString(path+File.separator+atm.getName());
			String cmpfolder = location1.toOSString();
			System.out.println("cmpfolder====>"+cmpfolder);
			atm.setCmpFolder(location1.toOSString());

			String pName = atm.getName();
			IWorkspace ws = ResourcesPlugin.getWorkspace();
			IProjectDescription projectDesc = ws.newProjectDescription(pName); 
			projectDesc.setLocation (location1); 

			IProject project = ws.getRoot().getProject (pName);

			//			 try{
			////				 IProject	newProject = CCorePlugin.getDefault().createCDTProject(projectDesc, new NullProgressMonitor());
			//			 
			//			 } catch (CoreException e){
			//					e.printStackTrace();
			//				}

			//			 project.
			//			
			//			
			//		 System.gc();
			if (!project.exists()){
				project.create(projectDesc, null); 
				IFolder compFolder = project.getFolder("dddd");
				IFolder profileFolder = compFolder.getFolder("profile");
				if(!profileFolder.exists()){
					try{
						profileFolder.create(false, true, null);
					} catch(CoreException e){
						e.printStackTrace();
					}
				}
				IFolder debugFolder = compFolder.getFolder("Debug");
				if(!debugFolder.exists()){
					try{
						debugFolder.create(false, true, null);
					}catch(CoreException e){
						e.printStackTrace();
					}
				}
				IFolder releaseFolder = compFolder.getFolder("Release");
				if(!releaseFolder.exists()){
					try{
						releaseFolder.create(false, true, null);
					}catch(CoreException e){
						e.printStackTrace();
					}
				}
			}
			//			 project.c
			//			 if (!project.isOpen())
			//				 project.open(null);
			//			
			//			//project.setHidden(true);
		} catch (CoreException e) {
			e.printStackTrace();
		}




	}

	private void createProject(String path,AtomicComponentModel atm){

		IWorkbenchPage page= this.getModelBrowser().getSite().getWorkbenchWindow().getActivePage();


		try {
			IPath location1 = Path.fromOSString(path+File.separator+atm.getName());
			String cmpfolder = location1.toOSString();
			System.out.println("cmpfolder====>"+cmpfolder);
			atm.setCmpFolder(location1.toOSString());


			////			 int cnt=path.lastIndexOf("\\");
			////			 String pName=path.substring(cnt+1);
			String pName = atm.getName();
			IWorkspace ws = ResourcesPlugin.getWorkspace();
			IProjectDescription projectDesc = ws.newProjectDescription(pName); 
			projectDesc.setLocation (location1); 
			//			 projectDesc.
			//			 projectDesc.setComment ("Desc of My Project"); 
			//			 
			//			 
			IProject project = ws.getRoot().getProject (pName);

			//			 try{
			////				 IProject	newProject = CCorePlugin.getDefault().createCDTProject(projectDesc, new NullProgressMonitor());
			//			 
			//			 } catch (CoreException e){
			//					e.printStackTrace();
			//				}

			//			 project.
			//			
			//			
			//		 System.gc();
			if (!project.exists()){
				project.create(projectDesc, null); 
				IFolder compFolder = project.getFolder("dddd");
				IFolder profileFolder = compFolder.getFolder("profile");
				if(!profileFolder.exists()){
					try{
						profileFolder.create(false, true, null);
					} catch(CoreException e){
						e.printStackTrace();
					}
				}
				IFolder debugFolder = compFolder.getFolder("Debug");
				if(!debugFolder.exists()){
					try{
						debugFolder.create(false, true, null);
					}catch(CoreException e){
						e.printStackTrace();
					}
				}
				IFolder releaseFolder = compFolder.getFolder("Release");
				if(!releaseFolder.exists()){
					try{
						releaseFolder.create(false, true, null);
					}catch(CoreException e){
						e.printStackTrace();
					}
				}
			}
			//			 project.c
			//			 if (!project.isOpen())
			//				 project.open(null);
			//			
			//			//project.setHidden(true);
		} catch (CoreException e) {
			e.printStackTrace();
		}




	}

	protected void createNewProject(AtomicComponentModel atm){
//		IProject newProject= null;
//
//		IWorkspace workspace = ResourcesPlugin.getWorkspace();//워크스페이스 핸들
//		IWorkspaceRoot root = workspace.getRoot();//워크스페이스 루트핸들
//		//		root.getRawLocation()
//		UMLTreeParentModel up1 = atm.getUMLTreeModel().getParent();
//		String pname = "";
//		if(up1 instanceof RootCmpEdtTreeModel){
//			pname = atm.getName();
//		}
//		else{
//			pname= up1.getName();
//
//			//			pname = up1.getName();
//
//		}
//
//		final IProject newProjectHandle = root.getProject(pname); 
//
//
//		//		final IProject newProjectHandle = root.getProject(atm.getName()); //프로젝트 핸들을 가져와본다.
//		if(!newProjectHandle.exists()){
//			IProjectDescription prjDescription = workspace.newProjectDescription(newProjectHandle.getName());
//			IPath projectLocation = newProjectHandle.getRawLocation();
//			if((projectLocation!=null)&&(!projectLocation.equals(Platform.getLocation()))){
//				prjDescription.setLocation(projectLocation);
//			}
//			//구현언어에 따른 프로젝트 리소스 생성 
//			//			if(prjInfo.getImplLanguage().equals(WizardMessages.getString("NewPjtWizardPage.SelectLanguageGroup.Combo0"))){
//			try{
//				newProject = CCorePlugin.getDefault().createCDTProject(prjDescription, newProjectHandle, new NullProgressMonitor());
//
//			} catch (OperationCanceledException e){
//				e.printStackTrace();
//			} catch (CoreException e){
//				e.printStackTrace();
//			}
//		}
//		else{
//			IWorkspaceRunnable runnable = new IWorkspaceRunnable() {
//
//				public void run(IProgressMonitor monitor) throws CoreException {
//					newProjectHandle.refreshLocal(IResource.DEPTH_INFINITE, monitor);
//				}
//			};
//			NullProgressMonitor monitor = new NullProgressMonitor();
//			try	{
//				workspace.run(runnable, root, IWorkspace.AVOID_UPDATE, monitor);
//			} catch(CoreException e){
//				e.printStackTrace();
//			}
//			newProject = newProjectHandle;
////			newProject.set
//		}
//		
//		try{
//			//ManagedCProjectNature.addNature(newProject,WizardMessages.getString("NewPjtWizard.CPPNature"),null);
//			//새 프로젝트에 Nature 할당
//			CCProjectNature.addCCNature(newProject, new NullProgressMonitor());
//			//	CProjectNature.addCNature(newProject, new NullProgressMonitor());
//			ICProjectDescriptionManager prjManager = CoreModel.getDefault().getProjectDescriptionManager();
//			//CoreModel의 기본 CProjectDescriptionManager를 통해 CProjectDescription 생성
//			ICProjectDescription prjCDescription = prjManager.createProjectDescription(newProject, false);
//			//ManageBuildManager를 통해 ManagedBuildInfo 생성
//			IManagedBuildInfo cdtBuildInfo = ManagedBuildManager.createBuildInfo(newProject);
//			//cdt.managedbuild.target.gnu.mingw.exe ProjectType을 가져옮 
//			IProjectType cdtPrjType=null;
//			//cdt.managedbuild.toolchain.gnu.mingw.base Toolchain을 가져옮
//			IToolChain cdtToolChain=null;
//			//			if(prjInfo.getImplLanguage().equals(WizardMessages.getString("NewPjtWizardPage.SelectLanguageGroup.Combo0"))){
//			String str = WizardMessages.getString("NewPjtWizard.CDTType");
//			if(atm.lang==0){
//				cdtPrjType = 
//					ManagedBuildManager.getExtensionProjectType(WizardMessages.getString("NewPjtWizard.CDTType"));
//				cdtToolChain = 
//					ManagedBuildManager.getExtensionToolChain(WizardMessages.getString("NewPjtWizard.CDTToolChain"));
//			}
//			else{
//				cdtPrjType = 
//					ManagedBuildManager.getExtensionProjectType(WizardMessages.getString("NewPjtWizard.CDTMSVCType"));
//				cdtToolChain = 
//					ManagedBuildManager.getExtensionToolChain(WizardMessages.getString("NewPjtWizard.CDTMSVCToolChain"));
//			}
//		
//			//위의 프로젝트 타입과 툴체인을 가지는 ManagedProject 생성
//			IManagedProject cdtPrj = new ManagedProject(newProject,cdtPrjType);
//			//			IManagedProject cdtPrj = new ManagedProject(prjCDescription);
//			//ManagedBuildInfo에 ManagedProject를 설정
//			cdtBuildInfo.setManagedProject(cdtPrj);
//			
//
//			IConfiguration[] cdtConfigs = ManagedBuildManager.getExtensionConfigurations(cdtToolChain,cdtPrjType);
//			for(IConfiguration iCfg : cdtConfigs){
//				if(!(iCfg instanceof Configuration)){
//					continue;
//				}
//				Configuration cfg = (Configuration) iCfg;
//				String calId = ManagedBuildManager.calculateChildId(cfg.getId(),null);
//				Configuration cfg1 = new Configuration((ManagedProject) cdtPrj,cfg,calId,false,true);
//
//				cfg1.enableInternalBuilder(false);
//				ICConfigurationDescription cfgDes =
//					prjCDescription.createConfiguration(ManagedBuildManager.CFG_DATA_PROVIDER_ID, cfg1.getConfigurationData());
//				cfg1.setConfigurationDescription(cfgDes);
//				cfg1.exportArtifactInfo();
//				IBuilder cdtBuilder = cfg1.getEditableBuilder();
//
//				if(cdtBuilder != null){
//					cdtBuilder.setAutoBuildEnable(false);
//					cdtBuilder.setManagedBuildOn(false);
//					//					String buildPath = cdtBuilder.getBuildPath();
//					//					int index = buildPath.lastIndexOf("/");
//					//					String lastBuildPath = buildPath.substring(index);
//					cdtBuilder.setBuildPath("${workspace_loc:/"+atm.getName()/*+"/"+compInfo.getComponentName()+prjInfo.getBinFolder()*/+"/}");
//
//				}
//				cfg1.setName(cfg.getName());
//				cfg1.setArtifactName(newProject.getName());
//			}
//			try{
//				prjManager.setProjectDescription(newProject, prjCDescription);
//				if(!newProject.isOpen()){
//					newProject.open(new NullProgressMonitor());
//				}
//			} catch (CoreException e){
//				e.printStackTrace();
//			}
//
//			IFolder compFolder = newProject.getFolder(atm.getName());
//			if(!compFolder.exists()){
//				try{
//					compFolder.create(false, true, null);
//				}catch(CoreException e){
//					e.printStackTrace();
//				}
//			}
//
//			//			IFolder srcFolder = compFolder.getFolder(prjInfo.getSrcFolder());
//			//			if(!srcFolder.exists()){
//			//				try{
//			//					srcFolder.create(false,true,null);
//			//				} catch (CoreException e){
//			//					e.printStackTrace();
//			//				}
//			//			}
//			//			IFolder binFolder = compFolder.getFolder(prjInfo.getBinFolder());
//			//			if(!binFolder.exists()){
//			//				try{
//			//					binFolder.create(false,true,null);
//			//				} catch (CoreException e){
//			//					e.printStackTrace();
//			//				}
//			//			}
//			IFolder profileFolder = compFolder.getFolder("profile");
//			if(!profileFolder.exists()){
//				try{
//					profileFolder.create(false, true, null);
//				} catch(CoreException e){
//					e.printStackTrace();
//				}
//			}
//			IFolder debugFolder = compFolder.getFolder("Debug");
//			if(!debugFolder.exists()){
//				try{
//					debugFolder.create(false, true, null);
//				}catch(CoreException e){
//					e.printStackTrace();
//				}
//			}
//			IFolder releaseFolder = compFolder.getFolder("Release");
//			if(!releaseFolder.exists()){
//				try{
//					releaseFolder.create(false, true, null);
//				}catch(CoreException e){
//					e.printStackTrace();
//				}
//			}
//			ICConfigurationDescription cfgs[] = prjCDescription.getConfigurations();
//			for(ICConfigurationDescription cfg : cfgs){
//				ICSourceEntry[] entries = cfg.getSourceEntries();
//				Set<ICSourceEntry> set=new HashSet<ICSourceEntry>();
//				set.add(new CSourceEntry(compFolder,null,0));
//				//				set.add(new CSourceEntry(srcFolder,null,0));
//				entries=set.toArray(new ICSourceEntry[set.size()]);
//				cfg.setSourceEntries(entries);
//			}
//			//			String str1 = newProjectHandle.getFullPath().toOSString();
//			String str2 = compFolder.getLocation().toOSString();
//			//			String str = newProject.getRawLocation().toOSString();
//			//			root.get
//			atm.setCmpFolder(str2);
//			atm.setlibNameProp(atm.getName()+".dll");
//			atm.setlibTypeProp("dll");
//			//	WJH 100726 S 컴포넌트 생성시 block 파일 생성 유무 추가
//			if(useBlock){
//
//				System.out.println("Create Block File!!");
//				String block = newProject.getName()+"_bl.block";
//				File file = new File(str2, block);
//				String path = OPRoSUtil.getOPRoSFilesPath();
//				//					String oriPath = "E:\\Work\\서울대 제어블록 관련 파일\\보조파일\\";
//				String newPath = str2+File.separator;
//				path = path+="BlockFiles"+File.separator;
//				try{
//					if(!file.exists()){
//						file.createNewFile();
//						copyFile(path+"Log.H", newPath+"Log.H");
//						copyFile(path+"data_log.c", newPath+"data_log.c");
//						copyFile(path+"call_Val.h", newPath+"call_Val.h");
//						copyFile(path+"macro.h", newPath+"macro.h");
//						copyFile(path+"ode0.c", newPath+"ode0.c");
//						copyFile(path+"ode_rk4.c", newPath+"ode_rk4.c");					
//					}
//				}
//				catch(Exception e){
//					e.printStackTrace();
//				}
//				useBlock = true;
//			}
//			//					createBlockComponent(atm);
//			newProject.refreshLocal(IResource.DEPTH_INFINITE, null);
//			//					ProjectManager.getInstance().getUMLEditor().doSaveAs(newShell);
//			//					break;
//			//				case SWT.NO:
//			//					return;
//			//				case SWT.CANCEL:
//			//					break;
//			//				}
//			//				}
//			//				WJH 100726 E 컴포넌트 생성시 block 파일 생성 유무 추가
//			//			changePerspective(org.eclipse.cdt.)
//		}catch (CoreException e){
//			e.printStackTrace();
//		}

		//		}

	}


	//	WJH 100730 S 컴포넌트 포트 추가
	public void createBlockComponent(AtomicComponentModel acm){
		N3EditorDiagramModel nd =	ProjectManager.getInstance().getUMLEditor().getDiagram();	// nd = N3EditorDiagramModel
		AtomicComponentModel um = acm;
		UMLTreeModel utm = acm.getUMLTreeModel();

		System.out.println("acm id : "+acm.getId());
		System.out.println("um id : "+um.getId());
		if(um instanceof AtomicComponentModel){
			AtomicComponentModel atomicComponentModel1 = (AtomicComponentModel)um;
			if(atomicComponentModel1.getCoreUMLTreeModel()==null){
				um = atomicComponentModel1;
			}
			else{
				UMLTreeParentModel ut = (UMLTreeParentModel)atomicComponentModel1.getCoreUMLTreeModel();
				um = ((AtomicComponentModel)ut.getRefModel()).getCoreDiagramCmpModel();
				System.out.println("um id : "+um.getId());
				//				WJH 100909 S 블럭 컴포넌트 추가
				if(acm.isBlockComponent())
					um.setBlockComponent(true);
				//				WJH 100909 E 블럭 컴포넌트 추가
				utm = um.getUMLTreeModel();
			}
		}

		makeMethodInputPortModel(um);
		makeMethodOutputPortModel(um);
		makeDataInputPortModel(um);
		makeDataOutputPortModel(um);
		makeEventInputPortModel(um);
		makeEventOutputPortModel(um);
		AtomicComponentModel cm = um.getCoreDiagramCmpModel();
		
		CompAssemManager.getInstance().autoLayoutPort(acm.getPortManager().getPorts());
		
//		acm.setSize(new Dimension(acm.getSize().width,acm.getSize().height+100));
		

		CompAssemManager.getInstance().autoLayoutPort(cm.getPortManager().getPorts());

		um.writeProFile();
	}

	public void makeMethodInputPortModel(AtomicComponentModel um){
		PortModel portmodel = new MethodInputPortModel();
		PortModel pm = null;		

		pm = new MethodInputPortModel(um);


		contents = new OPRoSServicePortComposite(this.window.getShell(), SWT.NULL, 1, GridData.FILL_BOTH,false,true, pm);//isRequired = true, pm = MethodOutputPortModel
		serviceTypeComp = new OPRoSServiceTypeInputDialogComposite(this.window.getShell(),SWT.NULL, 1, GridData.FILL_BOTH,true,null);
		//////////////////OPRoSServiceTypeInputDialog  =======================================================

		Element serviceTypeRoot = new Element(OPRoSStrings.getString("ServiceTypeRoot"));

		int serviceCnt=0;
		int paramCnt=0;
		Element ele;
		Element subEle;
		Element subEle1;
		Element subEle2;
		Attribute attr;
		String itemStr;
		int seper=0;
		int seper2=0;

		ele = new Element(OPRoSStrings.getString("ServiceTypeEle"));
		itemStr = "DoublePrint";
		subEle = new Element(OPRoSStrings.getString("ServiceTypeNameEle"));
		subEle.setText(itemStr);
		ele.addContent(subEle);

		subEle = new Element(OPRoSStrings.getString("ServiceEle"));
		itemStr = "print():void:blocking";
		seper = itemStr.indexOf(OPRoSStrings.getString("ServiceSeper1"));
		seper2 = itemStr.lastIndexOf(OPRoSStrings.getString("ServiceSeper2"));
		attr = new Attribute(OPRoSStrings.getString("ServiceAttr1"),itemStr.substring(0, seper));
		subEle.setAttribute(attr);
		attr = new Attribute(OPRoSStrings.getString("ServiceAttr2"),itemStr.substring(seper+3, seper2));
		subEle.setAttribute(attr);
		attr = new Attribute(OPRoSStrings.getString("ServiceAttr3"),itemStr.substring(seper2+1, itemStr.length()));
		subEle.setAttribute(attr);
		ele.addContent(subEle);

		subEle1 = new Element(OPRoSStrings.getString("ServiceParamEle"));
		itemStr = "num:double";
		seper = itemStr.indexOf(OPRoSStrings.getString("ServiceSeper2"));
		attr = new Attribute(OPRoSStrings.getString("ServiceParamAttr"),Integer.toString(1));
		subEle1.setAttribute(attr);
		subEle2 = new Element(OPRoSStrings.getString("ServiceParamNameEle"));
		subEle2.setText(itemStr.substring(0,seper));
		subEle1.addContent(subEle2);
		subEle2 = new Element(OPRoSStrings.getString("ServiceParamTypeEle"));
		subEle2.setText(itemStr.substring(seper+1,itemStr.length()));
		subEle1.addContent(subEle2);

		subEle.addContent(subEle1);


		serviceTypeRoot.addContent(ele);

		serviceTypeDoc = new Document(serviceTypeRoot);
		//		if(!serviceTypeComp.getServiceTypeFileName().endsWith(".xml"))
		serviceTypeComp.setServiceTypeFileName("DoublePrint.xml");
		contents.setPortName("DoublePrint");
		contents.setPortRefer("DoublePrint.xml");
		contents.setPortType("DoublePrint");
		String serviceTypeFileName=serviceTypeComp.getServiceTypeFileName();
		//====================================================================================================================

		///////////  OPRoSServeicePortComposite  =============================================================================
		ComponentModel cm =  (ComponentModel)pm.getPortContainerModel();
		System.out.println("cm id : "+cm.getId());
		System.out.println("um id : "+um.getId());
		if(cm instanceof AtomicComponentModel){
			//			AtomicComponentModel acm = (AtomicComponentModel)cm;
			OPRoSServiceTypeElementModel element = new OPRoSServiceTypeElementModel();
			element.setServiceTypeFileName(serviceTypeFileName);	// OPRoSServiceTypeInputDialogComposite.getServiceTypeFileName()
			element.setServiceTypeDoc(serviceTypeDoc);	// Document serviceTypeDoc

			String str = element.getServiceTypeFileName();

			OPRoSServiceTypesElementModel ops = (OPRoSServiceTypesElementModel)um.getClassModel().getUMLDataModel().getElementProperty("OPRoSServiceTypesElementModel");
			ops.addChild(element);

			//			contents.getServiceTypeList().add(element.getServiceTypeFileName());
			//			contents.getServiceTypeMap().put(element.getServiceTypeFileName(), element.getServiceTypeDoc());//		OPRoSServicePortComposite.map
		}

		//====================================================================================================================
		pm.setName(contents.getPortName());	// bbb
		pm.setTypeRef(contents.getPortRefer());	//bbb.xml
		pm.setDesc(contents.getPortDescript());	//""
		pm.setType(contents.getPortType());		//bbb

		if(um.getAcceptParentModel()==null){
			um.setAcceptParentModel(um.getN3EditorDiagramModel());
		}

		if(um.getAcceptParentModel()!=null){
			um.createPort( pm, (UMLContainerModel)um.getAcceptParentModel());
		}

		UMLTreeParentModel to1 = (UMLTreeParentModel)um.getUMLTreeModel();
		UMLTreeModel port = new UMLTreeModel(pm.getName());
		to1.addChild(port);
		port.setRefModel(pm);
		pm.getElementLabelModel().setTreeModel(port);
		ProjectManager.getInstance().getModelBrowser().refresh(to1);
		pm.getUMLDataModel().setId(portmodel.getID());
		pm.getElementLabelModel().setPortId(portmodel.getID());

		System.out.println("um id : "+um.getId());
		ProjectManager.getInstance().getModelBrowser().searchComponentDiagramIdModel(um.getUMLDataModel().getId());
		java.util.ArrayList al = ProjectManager.getInstance().getSearchModel();
		for(int j = 0; j<al.size();j++){
			UMLModel um1 = (UMLModel)al.get(j);
			System.out.println("um1 id : "+um1.getId());
			if(um1!=um){
				if(um1 instanceof ComponentModel){
					cm = (ComponentModel)um1;
					PortModel pm2 = new MethodInputPortModel(cm);
					pm2.setName(pm.getName());
					cm.createPort(pm2, (UMLContainerModel)cm.getAcceptParentModel());
					pm2.getUMLDataModel().setId(pm.getID());

					pm2.getElementLabelModel().setPortId(pm.getID());
				}
			}
		}
		ProjectManager.getInstance().getSearchModel().clear();
		System.out.println("um id : "+um.getId());
		if(um instanceof AtomicComponentModel){
			AtomicComponentModel atm = (AtomicComponentModel)um;
			SyncManager.getInstance().ExeSync(atm, pm, null, 1);
		}
	}

	public void makeMethodOutputPortModel(AtomicComponentModel um){
		PortModel portmodel = new MethodOutputPortModel();
		PortModel pm = null;

		pm = new MethodOutputPortModel(um);

		//		contents = new OPRoSServicePortComposite(this.window.getShell(), SWT.NULL, 1, GridData.FILL_BOTH,false,true, pm);//isRequired = true, pm = MethodOutputPortModel
		//		serviceTypeComp = new OPRoSServiceTypeInputDialogComposite(this.window.getShell(),SWT.NULL, 1, GridData.FILL_BOTH,true,null);
		//////////////////OPRoSServiceTypeInputDialog  =======================================================

		//		Element serviceTypeRoot = new Element(OPRoSStrings.getString("ServiceTypeRoot"));
		//						
		//		int serviceCnt=0;
		//		int paramCnt=0;
		//		Element ele;
		//		Element subEle;
		//		Element subEle1;
		//		Element subEle2;
		//		Attribute attr;
		//		String itemStr;
		//		int seper=0;
		//		int seper2=0;
		//		
		//		ele = new Element(OPRoSStrings.getString("ServiceTypeEle"));
		//		itemStr = "DoublePrint";
		//		subEle = new Element(OPRoSStrings.getString("ServiceTypeNameEle"));
		//		subEle.setText(itemStr);
		//		ele.addContent(subEle);
		//		
		//		subEle = new Element(OPRoSStrings.getString("ServiceEle"));
		//		itemStr = "print():void:blocking";
		//		seper = itemStr.indexOf(OPRoSStrings.getString("ServiceSeper1"));
		//		seper2 = itemStr.lastIndexOf(OPRoSStrings.getString("ServiceSeper2"));
		//		attr = new Attribute(OPRoSStrings.getString("ServiceAttr1"),itemStr.substring(0, seper));
		//		subEle.setAttribute(attr);
		//		attr = new Attribute(OPRoSStrings.getString("ServiceAttr2"),itemStr.substring(seper+3, seper2));
		//		subEle.setAttribute(attr);
		//		attr = new Attribute(OPRoSStrings.getString("ServiceAttr3"),itemStr.substring(seper2+1, itemStr.length()));
		//		subEle.setAttribute(attr);
		//		ele.addContent(subEle);
		//					
		//		
		//		
		//		subEle1 = new Element(OPRoSStrings.getString("ServiceParamEle"));
		//		itemStr = "num:double";
		//		seper = itemStr.indexOf(OPRoSStrings.getString("ServiceSeper2"));
		//		attr = new Attribute(OPRoSStrings.getString("ServiceParamAttr"),Integer.toString(1));
		//		subEle1.setAttribute(attr);
		//		subEle2 = new Element(OPRoSStrings.getString("ServiceParamNameEle"));
		//		subEle2.setText(itemStr.substring(0,seper));
		//		subEle1.addContent(subEle2);
		//		subEle2 = new Element(OPRoSStrings.getString("ServiceParamTypeEle"));
		//		subEle2.setText(itemStr.substring(seper+1,itemStr.length()));
		//		subEle1.addContent(subEle2);
		//							
		//		subEle.addContent(subEle1);
		//				
		//		
		//		serviceTypeRoot.addContent(ele);

		//		Document serviceTypeDoc = new Document(serviceTypeRoot);
		//		if(!serviceTypeComp.getServiceTypeFileName().endsWith(".xml"))
		//		serviceTypeComp.setServiceTypeFileName("DoublePrint.xml");
		contents.setPortName("DoublePrint_in");
		contents.setPortRefer("DoublePrint.xml");
		contents.setPortType("DoublePrint");
		String serviceTypeFileName=serviceTypeComp.getServiceTypeFileName();
		//====================================================================================================================

		///////////  OPRoSServeicePortComposite  =============================================================================
		ComponentModel cm =  (ComponentModel)pm.getPortContainerModel();
		System.out.println("cm id : "+cm.getId());
		System.out.println("um id : "+um.getId());
		if(cm instanceof AtomicComponentModel){
			//			AtomicComponentModel acm = (AtomicComponentModel)cm;
			//			OPRoSServiceTypeElementModel element = new OPRoSServiceTypeElementModel();
			//			element.setServiceTypeFileName(serviceTypeFileName);	// OPRoSServiceTypeInputDialogComposite.getServiceTypeFileName()
			//			element.setServiceTypeDoc(serviceTypeDoc);	// Document serviceTypeDoc
			//
			//			String str = element.getServiceTypeFileName();

			//			OPRoSServiceTypesElementModel ops = (OPRoSServiceTypesElementModel)um.getClassModel().getUMLDataModel().getElementProperty("OPRoSServiceTypesElementModel");
			//			ops.addChild(element);

			//			contents.getServiceTypeList().add(element.getServiceTypeFileName());
			//			contents.getServiceTypeMap().put(element.getServiceTypeFileName(), element.getServiceTypeDoc());//		OPRoSServicePortComposite.map
		}

		//====================================================================================================================
		pm.setName(contents.getPortName());	// bbb
		pm.setTypeRef(contents.getPortRefer());	//bbb.xml
		pm.setDesc(contents.getPortDescript());	//""
		pm.setType(contents.getPortType());		//bbb

		if(um.getAcceptParentModel()!=null){
			um.createPort( pm, (UMLContainerModel)um.getAcceptParentModel());
		}

		UMLTreeParentModel to1 = (UMLTreeParentModel)um.getUMLTreeModel();
		UMLTreeModel port = new UMLTreeModel(pm.getName());
		to1.addChild(port);
		port.setRefModel(pm);
		pm.getElementLabelModel().setTreeModel(port);
		ProjectManager.getInstance().getModelBrowser().refresh(to1);
		pm.getUMLDataModel().setId(portmodel.getID());
		pm.getElementLabelModel().setPortId(portmodel.getID());

		System.out.println("um id : "+um.getId());
		ProjectManager.getInstance().getModelBrowser().searchComponentDiagramIdModel(um.getUMLDataModel().getId());
		java.util.ArrayList al = ProjectManager.getInstance().getSearchModel();
		for(int j = 0; j<al.size();j++){
			UMLModel um1 = (UMLModel)al.get(j);
			System.out.println("um1 id : "+um1.getId());
			if(um1!=um){
				if(um1 instanceof ComponentModel){
					cm = (ComponentModel)um1;
					PortModel pm2 = new MethodOutputPortModel(cm);
					pm2.setName(pm.getName());
					cm.createPort(pm2, (UMLContainerModel)cm.getAcceptParentModel());
					pm2.getUMLDataModel().setId(pm.getID());

					pm2.getElementLabelModel().setPortId(pm.getID());
				}
			}
		}
		ProjectManager.getInstance().getSearchModel().clear();
		System.out.println("um id : "+um.getId());
		if(um instanceof AtomicComponentModel){
			AtomicComponentModel atm = (AtomicComponentModel)um;
			SyncManager.getInstance().ExeSync(atm, pm, null, 1);
		}
	}

	public void makeDataInputPortModel(AtomicComponentModel um){
		PortModel portmodel = new DataInputPortModel();
		PortModel pm = null;
		pm = new DataInputPortModel(um);

		OPRoSDataPortDialogComposite contents = new OPRoSDataPortDialogComposite(this.window.getShell(), SWT.NULL, 1, GridData.FILL_BOTH,false,true, pm);//isRequired = true, pm = MethodOutputPortModel
		OPRoSDataTypeInputDialogComposite dataTypeComp = new OPRoSDataTypeInputDialogComposite(this.window.getShell(),SWT.NULL, 1, GridData.FILL_BOTH,true,null);
		//////////////////OPRoSServiceTypeInputDialog  =======================================================


		//		Element dataTypeRoot = new Element(OPRoSStrings.getString("DataTypeRoot"));
		//		
		//		Element ele;
		//		Element subEle;
		//		Attribute attr;
		//		String itemStr;
		//		String name;
		//		String type;
		//		
		//		ele = new Element(OPRoSStrings.getString("DataTypeEle"));
		//		attr = new Attribute(OPRoSStrings.getString("DataTypeEleAttr"),root.getItem(i).getText());
		//		ele.setAttribute(attr);
		//			
		//		subEle = new Element(OPRoSStrings.getString("DataTypeSubEle"));
		//		itemStr = root.getItem(i).getItem(j).getText();
		//		int seper = itemStr.indexOf(OPRoSStrings.getString("DataTypeSubEleSeperator"));
		//		name = itemStr.substring(0, seper);
		//		type = itemStr.substring(seper+1, itemStr.length());
		//		attr = new Attribute(OPRoSStrings.getString("DataTypeSubEleAttr"),type);
		//		subEle.setAttribute(attr);
		//		subEle.setText(name);
		//		ele.addContent(subEle);
		//		
		//		dataTypeRoot.addContent(ele);
		//		
		//		Document serviceTypeDoc = new Document(dataTypeRoot);
		//		if(!serviceTypeComp.getServiceTypeFileName().endsWith(".xml"))
		//		dataTypeComp.setDataTypeFileName("DoublePrint.xml");
		contents.setPortName("DataIn");
		//		contents.setPortRefer("DoublePrint.xml");
		contents.setPortType("Double");
		//		contents.setPortPolicy(1);
		//		contents.setPOrtQueueSize("20");

		String serviceTypeFileName=dataTypeComp.getDataTypeFileName();
		//====================================================================================================================

		///////////  OPRoSServeicePortComposite  =============================================================================
		ComponentModel cm =  (ComponentModel)pm.getPortContainerModel();
		System.out.println("cm id : "+cm.getId());
		System.out.println("um id : "+um.getId());
		if(cm instanceof AtomicComponentModel){
			//			AtomicComponentModel acm = (AtomicComponentModel)cm;
			OPRoSDataTypeElementModel element = new OPRoSDataTypeElementModel();
			element.setDataTypeFileName(serviceTypeFileName);	// OPRoSServiceTypeInputDialogComposite.getServiceTypeFileName()
			//			element.setDataTypeDoc(serviceTypeDoc);	// Document serviceTypeDoc

			String str = element.getServiceTypeFileName();

			//			OPRoSServiceTypesElementModel ops = (OPRoSServiceTypesElementModel)um.getClassModel().getUMLDataModel().getElementProperty("OPRoSDataTypesElementModel");
			//			ops.addChild(element);
			//			
			//			contents.getDataTypeList().add(element.getServiceTypeFileName());
			//			contents.getDataTypeMap().put(element.getServiceTypeFileName(), element.getDataTypeDoc());//		OPRoSServicePortComposite.map
		}

		//====================================================================================================================
		pm.setName(contents.getPortName());	// bbb
		pm.setTypeRef(contents.getPortRefer());	//bbb.xml
		pm.setDesc(contents.getPortDescript());	//""
		pm.setType(contents.getPortType());		//bbb
		pm.setQueueingPolicy("FIFO");
		pm.setQueueSize("9");
		pm.setUsage("input");

		if(um.getAcceptParentModel()!=null){
			um.createPort( pm, (UMLContainerModel)um.getAcceptParentModel());
		}

		UMLTreeParentModel to1 = (UMLTreeParentModel)um.getUMLTreeModel();
		UMLTreeModel port = new UMLTreeModel(pm.getName());
		to1.addChild(port);
		port.setRefModel(pm);
		pm.getElementLabelModel().setTreeModel(port);
		ProjectManager.getInstance().getModelBrowser().refresh(to1);
		pm.getUMLDataModel().setId(portmodel.getID());
		pm.getElementLabelModel().setPortId(portmodel.getID());

		System.out.println("um id : "+um.getId());
		ProjectManager.getInstance().getModelBrowser().searchComponentDiagramIdModel(um.getUMLDataModel().getId());
		java.util.ArrayList al = ProjectManager.getInstance().getSearchModel();
		for(int j = 0; j<al.size();j++){
			UMLModel um1 = (UMLModel)al.get(j);
			System.out.println("um1 id : "+um1.getId());			
			if(um1!=um){
				if(um1 instanceof ComponentModel){
					cm = (ComponentModel)um1;
					PortModel pm2 = new DataInputPortModel(cm);
					pm2.setName(pm.getName());
					cm.createPort(pm2, (UMLContainerModel)cm.getAcceptParentModel());
					pm2.getUMLDataModel().setId(pm.getID());

					pm2.getElementLabelModel().setPortId(pm.getID());
				}
			}
		}
		ProjectManager.getInstance().getSearchModel().clear();
		System.out.println("um id : "+um.getId());
		if(um instanceof AtomicComponentModel){
			AtomicComponentModel atm = (AtomicComponentModel)um;
			SyncManager.getInstance().ExeSync(atm, pm, null, 1);
		}
	}

	public void makeDataOutputPortModel(AtomicComponentModel um){
		PortModel portmodel = new DataOutputPortModel();
		PortModel pm = null;

		pm = new DataOutputPortModel(um);

		OPRoSDataPortDialogComposite contents = new OPRoSDataPortDialogComposite(this.window.getShell(), SWT.NULL, 1, GridData.FILL_BOTH,false,true, pm);//isRequired = true, pm = MethodOutputPortModel
		OPRoSDataTypeInputDialogComposite dataTypeComp = new OPRoSDataTypeInputDialogComposite(this.window.getShell(),SWT.NULL, 1, GridData.FILL_BOTH,true,null);
		//////////////////OPRoSServiceTypeInputDialog  =======================================================

		//		Element serviceTypeRoot = new Element(OPRoSStrings.getString("ServiceTypeRoot"));
		//						
		//		int serviceCnt=0;
		//		int paramCnt=0;
		//		Element ele;
		//		Element subEle;
		//		Element subEle1;
		//		Element subEle2;
		//		Attribute attr;
		//		String itemStr;
		//		int seper=0;
		//		int seper2=0;
		//		
		//		ele = new Element(OPRoSStrings.getString("DataTypeEle"));
		//		itemStr = "DoublePrint";
		//		subEle = new Element(OPRoSStrings.getString("ServiceTypeNameEle"));
		//		subEle.setText(itemStr);
		//		ele.addContent(subEle);
		//		
		//		subEle = new Element(OPRoSStrings.getString("ServiceEle"));
		//		itemStr = "print():void:blocking";
		//		seper = itemStr.indexOf(OPRoSStrings.getString("ServiceSeper1"));
		//		seper2 = itemStr.lastIndexOf(OPRoSStrings.getString("ServiceSeper2"));
		//		attr = new Attribute(OPRoSStrings.getString("ServiceAttr1"),itemStr.substring(0, seper));
		//		subEle.setAttribute(attr);
		//		attr = new Attribute(OPRoSStrings.getString("ServiceAttr2"),itemStr.substring(seper+3, seper2));
		//		subEle.setAttribute(attr);
		//		attr = new Attribute(OPRoSStrings.getString("ServiceAttr3"),itemStr.substring(seper2+1, itemStr.length()));
		//		subEle.setAttribute(attr);
		//		ele.addContent(subEle);
		//					
		//		
		//		
		//		subEle1 = new Element(OPRoSStrings.getString("ServiceParamEle"));
		//		itemStr = "num:double";
		//		seper = itemStr.indexOf(OPRoSStrings.getString("ServiceSeper2"));
		//		attr = new Attribute(OPRoSStrings.getString("ServiceParamAttr"),Integer.toString(1));
		//		subEle1.setAttribute(attr);
		//		subEle2 = new Element(OPRoSStrings.getString("ServiceParamNameEle"));
		//		subEle2.setText(itemStr.substring(0,seper));
		//		subEle1.addContent(subEle2);
		//		subEle2 = new Element(OPRoSStrings.getString("ServiceParamTypeEle"));
		//		subEle2.setText(itemStr.substring(seper+1,itemStr.length()));
		//		subEle1.addContent(subEle2);
		//							
		//		subEle.addContent(subEle1);
		//				
		//		
		//		serviceTypeRoot.addContent(ele);
		//		
		//		Document serviceTypeDoc = new Document(serviceTypeRoot);
		//		if(!serviceTypeComp.getServiceTypeFileName().endsWith(".xml"))
		//		dataTypeComp.setDataTypeFileName("DoublePrint.xml");
		contents.setPortName("DataOut");
		//		contents.setPortRefer("DoublePrint.xml");
		contents.setPortType("Double");
		String serviceTypeFileName=dataTypeComp.getDataTypeFileName();
		//====================================================================================================================

		///////////  OPRoSServeicePortComposite  =============================================================================
		ComponentModel cm =  (ComponentModel)pm.getPortContainerModel();
		System.out.println("cm id : "+cm.getId());
		System.out.println("um id : "+um.getId());
		if(cm instanceof AtomicComponentModel){
			//			AtomicComponentModel acm = (AtomicComponentModel)cm;
			OPRoSServiceTypeElementModel element = new OPRoSServiceTypeElementModel();
			element.setServiceTypeFileName(serviceTypeFileName);	// OPRoSServiceTypeInputDialogComposite.getServiceTypeFileName()
			//			element.setServiceTypeDoc(serviceTypeDoc);	// Document serviceTypeDoc

			String str = element.getServiceTypeFileName();

			OPRoSServiceTypesElementModel ops = (OPRoSServiceTypesElementModel)um.getClassModel().getUMLDataModel().getElementProperty("OPRoSServiceTypesElementModel");
			ops.addChild(element);

			contents.getDataTypeList().add(element.getServiceTypeFileName());
			contents.getDataTypeMap().put(element.getServiceTypeFileName(), element.getServiceTypeDoc());//		OPRoSServicePortComposite.map
		}

		//====================================================================================================================
		pm.setName(contents.getPortName());	// bbb
		pm.setTypeRef(contents.getPortRefer());	//bbb.xml
		pm.setDesc(contents.getPortDescript());	//""
		pm.setType(contents.getPortType());		//bbb

		if(um.getAcceptParentModel()!=null){
			um.createPort( pm, (UMLContainerModel)um.getAcceptParentModel());
		}

		UMLTreeParentModel to1 = (UMLTreeParentModel)um.getUMLTreeModel();
		UMLTreeModel port = new UMLTreeModel(pm.getName());
		to1.addChild(port);
		port.setRefModel(pm);
		pm.getElementLabelModel().setTreeModel(port);
		ProjectManager.getInstance().getModelBrowser().refresh(to1);
		pm.getUMLDataModel().setId(portmodel.getID());
		pm.getElementLabelModel().setPortId(portmodel.getID());

		System.out.println("um id : "+um.getId());
		ProjectManager.getInstance().getModelBrowser().searchComponentDiagramIdModel(um.getUMLDataModel().getId());
		java.util.ArrayList al = ProjectManager.getInstance().getSearchModel();
		for(int j = 0; j<al.size();j++){
			UMLModel um1 = (UMLModel)al.get(j);
			System.out.println("um1 id : "+um1.getId());			
			if(um1!=um){
				if(um1 instanceof ComponentModel){
					cm = (ComponentModel)um1;
					PortModel pm2 = new DataOutputPortModel(cm);
					pm2.setName(pm.getName());
					cm.createPort(pm2, (UMLContainerModel)cm.getAcceptParentModel());
					pm2.getUMLDataModel().setId(pm.getID());

					pm2.getElementLabelModel().setPortId(pm.getID());
				}
			}
		}
		ProjectManager.getInstance().getSearchModel().clear();
		System.out.println("um id : "+um.getId());
		if(um instanceof AtomicComponentModel){
			AtomicComponentModel atm = (AtomicComponentModel)um;
			SyncManager.getInstance().ExeSync(atm, pm, null, 1);
		}
	}

	public void makeEventInputPortModel(AtomicComponentModel um){
		PortModel portmodel = new EventInputPortModel();
		PortModel pm = null;
		pm = new EventInputPortModel(um);	

		OPRoSEventPortDialogComposite contents = new OPRoSEventPortDialogComposite(window.getShell(), SWT.NULL, 1, GridData.FILL_BOTH, true, true, pm);		
		//		((AtomicComponentModel)um).addDataTypeReference(opd.getUsingDataTypeFileName());

		contents.setPortName("EventIn");		
		contents.setPortType("Double");

		pm.setName(contents.getPortName());	// bbb
		pm.setType(contents.getPortType());	//bbb.xml
		if(um.getAcceptParentModel()!=null){
			um.createPort( pm, (UMLContainerModel)um.getAcceptParentModel());
		}

		UMLTreeParentModel to1 = (UMLTreeParentModel)um.getUMLTreeModel();
		UMLTreeModel port = new UMLTreeModel(pm.getName());
		to1.addChild(port);
		port.setRefModel(pm);
		pm.getElementLabelModel().setTreeModel(port);
		ProjectManager.getInstance().getModelBrowser().refresh(to1);
		pm.getUMLDataModel().setId(portmodel.getID());
		pm.getElementLabelModel().setPortId(portmodel.getID());
		ComponentModel cm =  (ComponentModel)pm.getPortContainerModel();
		System.out.println("um id : "+um.getId());
		ProjectManager.getInstance().getModelBrowser().searchComponentDiagramIdModel(um.getUMLDataModel().getId());
		java.util.ArrayList al = ProjectManager.getInstance().getSearchModel();
		for(int j = 0; j<al.size();j++){
			UMLModel um1 = (UMLModel)al.get(j);
			System.out.println("um1 id : "+um1.getId());			
			if(um1!=um){
				if(um1 instanceof ComponentModel){
					cm = (ComponentModel)um1;
					PortModel pm2 = new EventInputPortModel(cm);
					pm2.setName(pm.getName());
					cm.createPort(pm2, (UMLContainerModel)cm.getAcceptParentModel());
					pm2.getUMLDataModel().setId(pm.getID());

					pm2.getElementLabelModel().setPortId(pm.getID());
				}
			}
		}
		ProjectManager.getInstance().getSearchModel().clear();
		System.out.println("um id : "+um.getId());
		if(um instanceof AtomicComponentModel){
			AtomicComponentModel atm = (AtomicComponentModel)um;
			SyncManager.getInstance().ExeSync(atm, pm, null, 1);
		}
	}

	public void makeEventOutputPortModel(AtomicComponentModel um){
		PortModel portmodel = new EventOutputPortModel();
		PortModel pm = null;
		pm = new EventOutputPortModel(um);	

		OPRoSEventPortDialogComposite contents = new OPRoSEventPortDialogComposite(window.getShell(), SWT.NULL, 1, GridData.FILL_BOTH, true, true, pm);		
		//		((AtomicComponentModel)um).addDataTypeReference(opd.getUsingDataTypeFileName());

		contents.setPortName("EventOut");		
		contents.setPortType("Double");

		pm.setName(contents.getPortName());	// bbb
		pm.setType(contents.getPortType());	//bbb.xml
		if(um.getAcceptParentModel()!=null){
			um.createPort( pm, (UMLContainerModel)um.getAcceptParentModel());
		}

		UMLTreeParentModel to1 = (UMLTreeParentModel)um.getUMLTreeModel();
		UMLTreeModel port = new UMLTreeModel(pm.getName());
		to1.addChild(port);
		port.setRefModel(pm);
		pm.getElementLabelModel().setTreeModel(port);
		ProjectManager.getInstance().getModelBrowser().refresh(to1);
		pm.getUMLDataModel().setId(portmodel.getID());
		pm.getElementLabelModel().setPortId(portmodel.getID());
		ComponentModel cm =  (ComponentModel)pm.getPortContainerModel();
		System.out.println("um id : "+um.getId());
		ProjectManager.getInstance().getModelBrowser().searchComponentDiagramIdModel(um.getUMLDataModel().getId());
		java.util.ArrayList al = ProjectManager.getInstance().getSearchModel();
		for(int j = 0; j<al.size();j++){
			UMLModel um1 = (UMLModel)al.get(j);			
			System.out.println("um1 id : "+um1.getId());			
			if(um1!=um){
				if(um1 instanceof ComponentModel){
					cm = (ComponentModel)um1;
					PortModel pm2 = new EventOutputPortModel(cm);
					pm2.setName(pm.getName());
					cm.createPort(pm2, (UMLContainerModel)cm.getAcceptParentModel());
					pm2.getUMLDataModel().setId(pm.getID());

					pm2.getElementLabelModel().setPortId(pm.getID());
				}
			}
		}
		ProjectManager.getInstance().getSearchModel().clear();
		System.out.println("um id : "+um.getId());
		if(um instanceof AtomicComponentModel){
			AtomicComponentModel atm = (AtomicComponentModel)um;
			SyncManager.getInstance().ExeSync(atm, pm, null, 1);
		}
	}

	public Document doc(){

		Element serviceTypeRoot = new Element(OPRoSStrings.getString("ServiceTypeRoot"));

		int serviceCnt=0;
		int paramCnt=0;
		Element ele;
		Element subEle;
		Element subEle1;
		Element subEle2;
		Attribute attr;
		String itemStr;
		int seper=0;
		int seper2=0;

		ele = new Element(OPRoSStrings.getString("ServiceTypeEle"));

		itemStr = "DoublePrint";			

		subEle = new Element(OPRoSStrings.getString("ServiceTypeNameEle"));
		subEle.setText(itemStr);
		ele.addContent(subEle);

		subEle = new Element(OPRoSStrings.getString("ServiceEle"));
		itemStr = "print():void:blocking";
		seper = itemStr.indexOf(OPRoSStrings.getString("ServiceSeper1"));
		seper2 = itemStr.lastIndexOf(OPRoSStrings.getString("ServiceSeper2"));
		attr = new Attribute(OPRoSStrings.getString("ServiceAttr1"),itemStr.substring(0, seper));
		subEle.setAttribute(attr);
		attr = new Attribute(OPRoSStrings.getString("ServiceAttr2"),itemStr.substring(seper+3, seper2));
		subEle.setAttribute(attr);
		attr = new Attribute(OPRoSStrings.getString("ServiceAttr3"),itemStr.substring(seper2+1, itemStr.length()));
		subEle.setAttribute(attr);
		ele.addContent(subEle);

		subEle1 = new Element(OPRoSStrings.getString("ServiceParamEle"));
		itemStr = "num:double";
		seper = itemStr.indexOf(OPRoSStrings.getString("ServiceSeper2"));
		attr = new Attribute(OPRoSStrings.getString("ServiceParamAttr"),Integer.toString(1));
		subEle1.setAttribute(attr);
		subEle2 = new Element(OPRoSStrings.getString("ServiceParamNameEle"));
		subEle2.setText(itemStr.substring(0,seper));
		subEle1.addContent(subEle2);
		subEle2 = new Element(OPRoSStrings.getString("ServiceParamTypeEle"));
		subEle2.setText(itemStr.substring(seper+1,itemStr.length()));
		subEle1.addContent(subEle2);

		subEle.addContent(subEle1);


		serviceTypeRoot.addContent(ele);

		Document serviceTypeDoc = new Document(serviceTypeRoot);
		//		if(!serviceTypeComp.getServiceTypeFileName().endsWith(".xml"))
		//			serviceTypeComp.setServiceTypeFileName(serviceTypeComp.getServiceTypeFileName()+".xml");
		//		String serviceTypeFileName = "DoublePrint.xml";
		return serviceTypeDoc;
	}

	public static void copyFile(String source, String target) throws IOException {
		FileChannel inChannel = new FileInputStream(source).getChannel();
		FileChannel outChannel = new FileOutputStream(target).getChannel();
		try {
			// magic number for Windows, 64Mb - 32Kb
			int maxCount = (64 * 1024 * 1024) - (32 * 1024);
			long size = inChannel.size();
			long position = 0;
			while (position < size) {
				position += inChannel.transferTo(position, maxCount, outChannel);
			}
		} catch (IOException e) {
			throw e;
		} finally {
			if (inChannel != null)
				inChannel.close();
			if (outChannel != null)
				outChannel.close();
		}
	}
	//	WJH 100730 S 컴포넌트 포트 추가
	
	public void setViewModel(String id){
		
	}

}
