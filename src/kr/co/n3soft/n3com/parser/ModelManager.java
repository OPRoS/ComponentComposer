package kr.co.n3soft.n3com.parser;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javac.test.TJavaBuilder;
import kr.co.n3soft.n3com.dnd.TreeSimpleFactory;
import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.activity.ActivityParameterModel;
import kr.co.n3soft.n3com.model.activity.CentralBufferNodeModel;
import kr.co.n3soft.n3com.model.activity.ControlFlowLineModel;
import kr.co.n3soft.n3com.model.activity.DataStoreModel;
import kr.co.n3soft.n3com.model.activity.DecisionModel;
import kr.co.n3soft.n3com.model.activity.ExceptionModel;
import kr.co.n3soft.n3com.model.activity.ExpansionNodeModel;
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
import kr.co.n3soft.n3com.model.activity.TextAttachModel;
import kr.co.n3soft.n3com.model.activity.VForkJoinModel;
import kr.co.n3soft.n3com.model.activity.VPartitionModel;
import kr.co.n3soft.n3com.model.comm.ClassModel;
import kr.co.n3soft.n3com.model.comm.ClassifierModel;
import kr.co.n3soft.n3com.model.comm.ClassifierModelTextAttach;
import kr.co.n3soft.n3com.model.comm.DataInputPortModel;
import kr.co.n3soft.n3com.model.comm.DataOutputPortModel;
import kr.co.n3soft.n3com.model.comm.EntryPointModel;
import kr.co.n3soft.n3com.model.comm.EventInputPortModel;
import kr.co.n3soft.n3com.model.comm.EventOutputPortModel;
import kr.co.n3soft.n3com.model.comm.ExitPointModel;
import kr.co.n3soft.n3com.model.comm.FinalPackageModel;
import kr.co.n3soft.n3com.model.comm.FrameModel;
import kr.co.n3soft.n3com.model.comm.LineBendpointModel;
import kr.co.n3soft.n3com.model.comm.LineModel;
import kr.co.n3soft.n3com.model.comm.LineTextModel;
import kr.co.n3soft.n3com.model.comm.MethodInputPortModel;
import kr.co.n3soft.n3com.model.comm.MethodOutputPortModel;
import kr.co.n3soft.n3com.model.comm.NodeContainerModel;
import kr.co.n3soft.n3com.model.comm.ObjectPortModel;
import kr.co.n3soft.n3com.model.comm.PartitionModel;
import kr.co.n3soft.n3com.model.comm.PortContainerModel;
import kr.co.n3soft.n3com.model.comm.PortModel;
import kr.co.n3soft.n3com.model.comm.StrcuturedActivityPackageTreeModel;
import kr.co.n3soft.n3com.model.comm.StrcuturedPackageTreeModel;
import kr.co.n3soft.n3com.model.comm.TypeRefModel;
import kr.co.n3soft.n3com.model.comm.UMLDataModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.comm.UMLNoteModel;
import kr.co.n3soft.n3com.model.communication.MessageAssoicateLineModel;
import kr.co.n3soft.n3com.model.communication.MessageCommunicationModel;
import kr.co.n3soft.n3com.model.component.ArtfifactModel;
import kr.co.n3soft.n3com.model.component.AtomicComponentModel;
import kr.co.n3soft.n3com.model.component.ComponentEditModel;
import kr.co.n3soft.n3com.model.component.ComponentModel;
import kr.co.n3soft.n3com.model.component.OPRoSDataTypeElementModel;
import kr.co.n3soft.n3com.model.component.OPRoSExeEnvironmentCPUElementModel;
import kr.co.n3soft.n3com.model.component.OPRoSExeEnvironmentElementModel;
import kr.co.n3soft.n3com.model.component.OPRoSExeEnvironmentOSElementModel;
import kr.co.n3soft.n3com.model.component.OPRoSExeSemanticsElementModel;
import kr.co.n3soft.n3com.model.component.OPRoSMonitorVariableElementModel;
import kr.co.n3soft.n3com.model.component.OPRoSPropertyElementModel;
import kr.co.n3soft.n3com.model.component.OPRoSServiceTypeElementModel;
import kr.co.n3soft.n3com.model.composite.ConnectorLineModel;
import kr.co.n3soft.n3com.model.composite.DelegateLineModel;
import kr.co.n3soft.n3com.model.composite.OccurrenceLineModel;
import kr.co.n3soft.n3com.model.composite.PartModel;
import kr.co.n3soft.n3com.model.composite.ProvidedInterfaceLineModel;
import kr.co.n3soft.n3com.model.composite.RepresentsLineModel;
import kr.co.n3soft.n3com.model.composite.RequiredInterfaceLineModel;
import kr.co.n3soft.n3com.model.composite.RoleBindingLineModel;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.model.node.NodeItemModel;
import kr.co.n3soft.n3com.model.node.NodePackageModel;
import kr.co.n3soft.n3com.model.node.NodeRootModel;
import kr.co.n3soft.n3com.model.umlclass.AttributeEditableTableItem;
import kr.co.n3soft.n3com.model.umlclass.DependencyLineModel;
import kr.co.n3soft.n3com.model.umlclass.EnumerationModel;
import kr.co.n3soft.n3com.model.umlclass.FinalClassModel;
import kr.co.n3soft.n3com.model.umlclass.ImportLineModel;
import kr.co.n3soft.n3com.model.umlclass.InterfaceModel;
import kr.co.n3soft.n3com.model.umlclass.MergeLineModel;
import kr.co.n3soft.n3com.model.umlclass.OperationEditableTableItem;
import kr.co.n3soft.n3com.model.umlclass.ParameterEditableTableItem;
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
import kr.co.n3soft.n3com.project.browser.N3ViewContentProvider;
import kr.co.n3soft.n3com.project.browser.NodeItemTreeModel;
import kr.co.n3soft.n3com.project.browser.NodePackageTreeModel;
import kr.co.n3soft.n3com.project.browser.NodeRootTreeModel;
import kr.co.n3soft.n3com.project.browser.PackageTreeModel;
import kr.co.n3soft.n3com.project.browser.RootCmpEdtTreeModel;
import kr.co.n3soft.n3com.project.browser.RootCmpFnshTreeModel;
import kr.co.n3soft.n3com.project.browser.RootTreeModel;
import kr.co.n3soft.n3com.project.browser.StrcuturedPackageTreeLibModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeParentModel;
import kr.co.n3soft.n3com.project.dialog.DetailPropertyTableItem;
import kr.co.n3soft.n3com.projectmanager.CompAssemManager;
import kr.co.n3soft.n3com.projectmanager.LifecycleMethodPortModel;
import kr.co.n3soft.n3com.projectmanager.MonitoringMethodPortModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.examples.shapes.LogicEditorInput;
import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.IEditorInput;


public class ModelManager implements IN3ModelTokenType{
	private static ModelManager instance;
	private HashMap nodeCmpData = new HashMap();
	private HashMap tempData = new HashMap();
	private HashMap modelStore =new HashMap();
	private HashMap lineStore =new HashMap();
	private HashMap viewStore =new HashMap();
	private HashMap inModelViewStore =new HashMap();
	private HashMap inModelViewListStore =new HashMap();
	private HashMap doubleCheck = new HashMap();
	private HashMap diagramFrame = new HashMap();//PKY 08080501 S Frame 저장이 안되는 문제 
	private HashMap diagramStore = new HashMap();//PKY 08080501 S Frame 저장이 안되는 문제
	private java.util.ArrayList viewLines =new java.util.ArrayList();
	private java.util.ArrayList remapLines = new java.util.ArrayList();
	private java.util.ArrayList subPartitons = new java.util.ArrayList();
	private java.util.ArrayList seqGroups = new java.util.ArrayList();
	private java.util.ArrayList msgs = new java.util.ArrayList();
	private java.util.ArrayList selfMsgs = new java.util.ArrayList();
	private java.util.ArrayList isState = new java.util.ArrayList();
	private java.util.ArrayList isTransition = new java.util.ArrayList();
	private java.util.ArrayList isTimingPoint = new java.util.ArrayList();
	private java.util.ArrayList isSelfMessage = new java.util.ArrayList();
	private java.util.ArrayList isFragment = new java.util.ArrayList();
	private java.util.ArrayList isGroupModel = new java.util.ArrayList();
	private java.util.ArrayList isGroupParent = new java.util.ArrayList();
	private java.util.ArrayList refStore = new java.util.ArrayList();
	private java.util.ArrayList openDiagarams =new java.util.ArrayList();
	private java.util.ArrayList isTransitions = new java.util.ArrayList(); //PKY 08071601 S State 저장안되는문제 
	private UMLModel tempUMLModel = null;
	private UMLTreeModel utm = null;
	private UMLTreeParentModel utp = null;
	private int current_model_type = -1;
	private UMLDataModel current_UMLDataModel = new UMLDataModel();
	private UMLDataModel current_ClassModel_UMLDataModel = new UMLDataModel();
	LoadProgressRun loadProgressRun = null;
	IProgressMonitor monitor;
	private UMLTreeParentModel linkPackage = null;

	/*
	 * 3:bendpoint
	 * 4:port
	 * 5:ID_DETAIL 
	 * 6:ID_TAG
	 * 7:ID_EXTENSIONPOINT
	 * 9:subPartition
	 * 10:seqGroup
	 * 11:msgs
	 * 12:TimingState //2008043001 PKY S Timing 객체 저장 로직 추가
	 * 13:Transition //2008043001 PKY S Timing 객체 저장 로직 추가
	 */
	private int objectTypes_begin =-1;

	RootTreeModel root = ProjectManager.getInstance().getModelBrowser().getRoot();
	java.util.ArrayList current_objects = null;
	java.util.Vector current_objects_v = null;
	private boolean inModel  = false;
	private int inModelType = -1;
	private boolean isView = false;
	TreeSimpleFactory factory = new TreeSimpleFactory();
	private boolean isLine  = false;
	private boolean isBoundary = false;
	public NodeContainerModel boundaryModel = new NodeContainerModel();
	String boundaryParentID = "";
	Parser parser;

	public void init(){
		nodeCmpData.clear();
		this.tempData.clear();
		this.modelStore.clear();
		this.lineStore.clear();
		this.viewStore.clear();
		this.inModelViewStore.clear();
		this.inModelViewListStore.clear();
		this.refStore.clear();

		this.viewLines.clear();
		this.remapLines.clear();
		this.subPartitons.clear();
		this.seqGroups.clear();
		this.msgs.clear();
		this.selfMsgs.clear();
		tempUMLModel = null;
		utm = null;
		utp = null;
		current_model_type = -1;
		current_UMLDataModel = new UMLDataModel();
		current_ClassModel_UMLDataModel = new UMLDataModel();
		objectTypes_begin =-1;

		RootTreeModel root = ProjectManager.getInstance().getModelBrowser().getRoot();
		current_objects = null;
		current_objects_v = null;
		inModel  = false;
		inModelType = -1;
		isView = false;
		factory = new TreeSimpleFactory();
		isLine  = false;
		isBoundary = false;
		boundaryModel = new NodeContainerModel();
		boundaryParentID = "";
		parser=null;




		this.root = ProjectManager.getInstance().getModelBrowser().getRoot();

	}

	public static ModelManager getInstance() {
		if (instance == null) {
			instance = new ModelManager();

			return instance;
		}
		else {
			return instance;
		}
	}
	//e_models_begin
	public void buildRoot(){
		HashMap tempModel = new HashMap(); 
	}

	public java.util.ArrayList getParams(String token){
		java.util.ArrayList params = new java.util.ArrayList();
		if(token!=null){
			String[] data = token.split(",");
			for(int i=0;i<data.length;i++){
				String param = data[i];
				if(param.indexOf(":")>0){
					String[] p = param.split(":");
					ParameterEditableTableItem pt = new ParameterEditableTableItem();
					pt.name = p[0];
					pt.stype = p[1];
					params.add(pt);
				}
			}
		}


		return params;

	}

	public MessageCommunicationModel getMessageCommunicationModel(String token){
		MessageCommunicationModel mcm = new MessageCommunicationModel();
		String[] data = token.split("\"");
		if(data.length==16){//PKY 08070301 S Communication Dialog 추가작업
			mcm.setMessageType(data[0]);
			mcm.setMessageIntType(Integer.parseInt(data[4]));
			if(mcm.getMessageIntType()==1){
				mcm.setNumber("0");
			}
			else{
				mcm.setAngle(data[6]);
				if("0".equals(data[5])){
					mcm.addRightArrowModel();
				}
				else{
					mcm.addLeftArrowModel();
				}
				int num = data[1].indexOf(":");

				boolean isNum = true;
				//					String numB = data[1].substring(0,num);

				//					mcm.setNumber(numB);

				mcm.setName(data[1].substring(num+1));

				mcm.setSignature(Integer.parseInt(data[11]));
				mcm.setSynch(Integer.parseInt(data[12]));
				mcm.setKind(Integer.parseInt(data[13]));
				mcm.setLifecycle(Integer.parseInt(data[14]));
				if(data[15].equals("false"))
					mcm.setBack(false);
				else if(data[15].equals("true")){
					mcm.setBack(true);
				}
				return mcm;

			}
			if(!data[3].equals("false")){
				UMLTreeModel utm = (UMLTreeModel)this.modelStore.get(data[3]);
				if(utm!=null){
					UMLModel um = (UMLModel)utm.getRefModel();
					if(um!=null && um instanceof ClassifierModel){
						ClassifierModel cm = (ClassifierModel)um;
						mcm.setTrm((TypeRefModel)cm.getClassModel());
						OperationEditableTableItem opm = cm.getClassModel().searchOperationEditableTableItem(data[1]);
						if(opm!=null){
							mcm.setOti(opm);
						}
					}else if(um!=null && um instanceof ClassifierModelTextAttach){//PKY 08081801 S 인터페이스 참조하여 해당 오퍼레이션 메시지로 설정 후 저장 불러오기 할 경우 메시지 안되는 문제
						ClassifierModelTextAttach cm = (ClassifierModelTextAttach)um;
						mcm.setTrm((TypeRefModel)cm.getClassModel());
						OperationEditableTableItem opm = cm.getClassModel().searchOperationEditableTableItem(data[1]);
						if(opm!=null){
							mcm.setOti(opm);
						}
					}
				}else{
					//PKY 08082201 S 시퀀스 같은 객체의 오퍼레이션이 두개 이상 일 경우 마지막 추가된 오퍼레이션이 들어가는문제
					if(TeamProjectManager.getInstance().getNoOperationModelMap().get(data[3]+","+data[1])!=null){
						ArrayList arrayItem = (ArrayList)TeamProjectManager.getInstance().getNoOperationModelMap().get(data[3]+","+data[1]);
						arrayItem.add(mcm);
						TeamProjectManager.getInstance().getNoOperationModelMap().put(data[3]+","+data[1], arrayItem);
					}else{
						ArrayList arrayItem = new ArrayList();
						arrayItem.add(mcm);
						TeamProjectManager.getInstance().getNoOperationModelMap().put(data[3]+","+data[1], arrayItem);
					}
					//PKY 08082201 E 시퀀스 같은 객체의 오퍼레이션이 두개 이상 일 경우 마지막 추가된 오퍼레이션이 들어가는문제
				}
				//20080811IJS 끝
			}
			else{
				mcm.setName(data[1]);
			}
			//PKY 08061801 S 시퀀스다이어그램 메시지 중앙메세지 사이즈저장안되는문제
			mcm.setSize(new Dimension(Integer.parseInt(data[7]),Integer.parseInt(data[8])));
			mcm.setLocation(new Point(Integer.parseInt(data[9]),Integer.parseInt(data[10])));
			//PKY 08061801 E 시퀀스다이어그램 메시지 중앙메세지 사이즈저장안되는문제


		}
		return mcm;
	}

	//	public SeqGroup getSeqGroup(String token){
	//		SeqGroup seq = new SeqGroup();
	//		String[] data = token.split(",");
	//		if(data.length==4){
	//			Rectangle rec = new Rectangle();
	//			rec.x = Integer.parseInt(data[0]);
	//			rec.y = Integer.parseInt(data[1]);
	//			rec.width = Integer.parseInt(data[2]);
	//			rec.height = Integer.parseInt(data[3]);
	//			seq.setR(rec);
	//		}
	//		return seq;
	//	}

	public Point getLocation(String token){
		int a=0;
		int b=0;
		String[] data = token.split(",");
		if(data.length==2){
			a = Integer.parseInt(data[0]);
			b = Integer.parseInt(data[1]);
		}
		return new Point(a,b);
	}


	public PortModel getPortModel(String token){

		PortModel pm = new PortModel();
		String[] data = token.split(",");
		//PKY 08061101 S 포트와 관련된 저장 불러오기 로직 보완작업
		if(data.length==15){

			pm.x = Integer.parseInt(data[0]);
			pm.y = Integer.parseInt(data[1]);
			pm.w = Integer.parseInt(data[2]);
			pm.h = Integer.parseInt(data[3]);
			pm.dw = Integer.parseInt(data[4]);
			pm.dh = Integer.parseInt(data[5]);			
			pm.pid = data[6];

			if(!data[7].trim().equals("")){
				pm.setPropertyValue("ID_NAME", data[7]);
				pm.setName(data[7]);
			}

			pm.setScope(data[8]);
			pm.setPropertyValue("ID_SCOPE", Integer.parseInt(data[8]));

			if(!data[9].trim().equals("")){
				pm.setStereotype(data[9]);
				pm.setPropertyValue("ID_STEREOTYPE", data[9]);
			}
			//PKY 08061101 E 포트와 관련된 저장 불러오기 로직 보완작업
			pm.setPtDifference(new Dimension(Integer.parseInt(data[4]),Integer.parseInt(data[5])));
			pm.getAttachElementLabelModel().setSize(new Dimension(Integer.parseInt(data[10]),Integer.parseInt(data[11])));//PKY 08060201 S 포트 텍스트 사이즈 늘어나지 않는문제
//			pm.setBackGroundColor(new Color(null,Integer.parseInt(data[12]),Integer.parseInt(data[13]),Integer.parseInt(data[14])));


			this.viewStore.put(data[6], pm);

		}
		return pm;

	}
	
	
	public OPRoSDataTypeElementModel getOPRoSDataTypeElementModel(String token,String id){
		OPRoSDataTypeElementModel oPRoSDataTypeElementModel = new OPRoSDataTypeElementModel();
//		String[] data = token.split(",");
		oPRoSDataTypeElementModel.setDataTypeFileName(token);
		
		
		return oPRoSDataTypeElementModel;
		
	}
	
	public OPRoSExeEnvironmentOSElementModel getOPRoSExeEnvironmentOSElementModel(String token,String id){
		OPRoSExeEnvironmentOSElementModel oPRoSExeEnvironmentOSElementModel = new OPRoSExeEnvironmentOSElementModel();
		String[] data = token.split(",");
		if(data.length==2){
			String os = data[0];
			String version = data[1];
			oPRoSExeEnvironmentOSElementModel.setOs(os);
			oPRoSExeEnvironmentOSElementModel.setOsVersion(version);
		}
		
		
		
		return oPRoSExeEnvironmentOSElementModel;
		
	}
	
	
	public OPRoSExeEnvironmentCPUElementModel getOPRoSExeEnvironmentCPUElementModel(String token,String id){
		OPRoSExeEnvironmentCPUElementModel oPRoSExeEnvironmentCPUElementModel = new OPRoSExeEnvironmentCPUElementModel();
//		String[] data = token.split(",");
//		if(data.length==2){
//			String os = data[0];
//			String version = data[1];
			oPRoSExeEnvironmentCPUElementModel.setCpu(token);
//			oPRoSExeEnvironmentOSElementModel.setOsVersion(version);
//		}
		
		
		
		return oPRoSExeEnvironmentCPUElementModel;
		
	}
	
	public OPRoSServiceTypeElementModel getOPRoSServiceTypeElementModel(String token,String id){
		OPRoSServiceTypeElementModel oPRoSServiceTypeElementModel = new OPRoSServiceTypeElementModel();
//		String[] data = token.split(",");
//		if(data.length==3){
//			String os = data[0];
//			String version = data[1];
			oPRoSServiceTypeElementModel.setServiceTypeFileName(token);
//			oPRoSExeEnvironmentOSElementModel.setOsVersion(version);
//		}
		
		
		
		return oPRoSServiceTypeElementModel;
		
	}
	
	public OPRoSPropertyElementModel getOPRoSPropertyElementModel(String token,String id){
		OPRoSPropertyElementModel oPRoSPropertyElementModel = new OPRoSPropertyElementModel();
		String[] data = token.split(",");
		if(data.length==3){
			String dv = data[0];
			String name = data[1];
			String dt = data[2];
			oPRoSPropertyElementModel.setDefaultValue(dv);
			oPRoSPropertyElementModel.setPropertyName(name);
			oPRoSPropertyElementModel.setDataType(dt);
//			oPRoSExeEnvironmentOSElementModel.setOsVersion(version);
		}
		
		
		
		return oPRoSPropertyElementModel;
		
	}
	
	public OPRoSMonitorVariableElementModel getOPRoSMonitorVariableElementModel(String token,String id){
		OPRoSMonitorVariableElementModel oPRoSMonitorVariableElementModel = new OPRoSMonitorVariableElementModel();
		String[] data = token.split(",");
		if(data.length==3){
			String dv = data[0];
			String name = data[1];
			String dt = data[2];
			oPRoSMonitorVariableElementModel.setDefaultValue(dv);
			oPRoSMonitorVariableElementModel.setPropertyName(name);
			oPRoSMonitorVariableElementModel.setDataType(dt);
//			oPRoSExeEnvironmentOSElementModel.setOsVersion(version);
		}
		
		
		
		return oPRoSMonitorVariableElementModel;
		
	}
	
	
	public String chkNULL(String str){
		if("null".equals(str))
			return "";
		else
			return str;
	}

	public PortModel getCompPortModel(String token,String id){

		PortModel pm = null;
		
		if(id.equals("MonitoringMethodPortModel")){
			pm = new MonitoringMethodPortModel();
			
			
		}
		else if(id.equals("LifecycleMethodPortModel")){
			pm = new LifecycleMethodPortModel();
		}
		else if(id.equals("MethodOutputPortModel")){
			pm = new MethodOutputPortModel();
			String[] data = token.split(",");
			if(data.length==16){
//				property MethodOutputPortModel="+pm.getLocation().x+","+pm.getLocation().y+","+pm.getSize().width+","+pm.getSize().height+","+pm.getPtDifference().width+","+pm.getPtDifference().height+","+pm.view_ID+","+pm.getName()+","+pm.getScope()+","+pm.getStereotype()+","+pm.getAttachElementLabelModel().getSize().width+","+pm.getAttachElementLabelModel().getSize().height+","+pm.getTypeRef()+","+pm.getDesc()+","+","+pm.getType()+","+pm.getUMLDataModel().getId()+IN3ModelTokenType.PROPERTY_N3EOF
				pm.x = Integer.parseInt(data[0]);
				pm.y = Integer.parseInt(data[1]);
				pm.w = Integer.parseInt(data[2]);
				pm.h = Integer.parseInt(data[3]);
				pm.dw = Integer.parseInt(data[4]);
				pm.dh = Integer.parseInt(data[5]);			
				pm.pid = data[6];

				if(!data[7].trim().equals("")){
					pm.setPropertyValue("ID_NAME", data[7]);
					pm.setName(data[7]);
				}

				pm.setScope(data[8]);
				pm.setPropertyValue("ID_SCOPE", Integer.parseInt(data[8]));

				if(!data[9].trim().equals("")){
					pm.setStereotype(data[9]);
					pm.setPropertyValue("ID_STEREOTYPE", data[9]);
				}
				//PKY 08061101 E 포트와 관련된 저장 불러오기 로직 보완작업
				pm.setPtDifference(new Dimension(Integer.parseInt(data[4]),Integer.parseInt(data[5])));
				pm.getAttachElementLabelModel().setSize(new Dimension(Integer.parseInt(data[10]),Integer.parseInt(data[11])));//PKY 08060201 S 포트 텍스트 사이즈 늘어나지 않는문제
				
				pm.setTypeRef(chkNULL(data[12]));
				pm.setDesc(chkNULL(data[13]));
				pm.setType(chkNULL(data[14]));
//				pm.setBackGroundColor(new Color(null,Integer.parseInt(data[12]),Integer.parseInt(data[13]),Integer.parseInt(data[14])));
				pm.getUMLDataModel().setId(data[15]);

				this.viewStore.put(data[6], pm);
				return pm;

			}
		}
		else if(id.equals("MethodInputPortModel")){
			pm = new MethodInputPortModel();
			String[] data = token.split(",");
			if(data.length==16){
//				property MethodOutputPortModel="+pm.getLocation().x+","+pm.getLocation().y+","+pm.getSize().width+","+pm.getSize().height+","+pm.getPtDifference().width+","+pm.getPtDifference().height+","+pm.view_ID+","+pm.getName()+","+pm.getScope()+","+pm.getStereotype()+","+pm.getAttachElementLabelModel().getSize().width+","+pm.getAttachElementLabelModel().getSize().height+","+pm.getTypeRef()+","+pm.getDesc()+","+","+pm.getType()+","+pm.getUMLDataModel().getId()+IN3ModelTokenType.PROPERTY_N3EOF
				pm.x = Integer.parseInt(data[0]);
				pm.y = Integer.parseInt(data[1]);
				pm.w = Integer.parseInt(data[2]);
				pm.h = Integer.parseInt(data[3]);
				pm.dw = Integer.parseInt(data[4]);
				pm.dh = Integer.parseInt(data[5]);			
				pm.pid = data[6];

				if(!data[7].trim().equals("")){
					pm.setPropertyValue("ID_NAME", data[7]);
					pm.setName(data[7]);
				}

				pm.setScope(data[8]);
				pm.setPropertyValue("ID_SCOPE", Integer.parseInt(data[8]));

				if(!data[9].trim().equals("")){
					pm.setStereotype(data[9]);
					pm.setPropertyValue("ID_STEREOTYPE", data[9]);
				}
				//PKY 08061101 E 포트와 관련된 저장 불러오기 로직 보완작업
				pm.setPtDifference(new Dimension(Integer.parseInt(data[4]),Integer.parseInt(data[5])));
				pm.getAttachElementLabelModel().setSize(new Dimension(Integer.parseInt(data[10]),Integer.parseInt(data[11])));//PKY 08060201 S 포트 텍스트 사이즈 늘어나지 않는문제
				
				pm.setTypeRef(chkNULL(data[12]));
				pm.setDesc(chkNULL(data[13]));
				pm.setType(chkNULL(data[14]));
//				pm.setBackGroundColor(new Color(null,Integer.parseInt(data[12]),Integer.parseInt(data[13]),Integer.parseInt(data[14])));
				pm.getUMLDataModel().setId(data[15]);

				this.viewStore.put(data[6], pm);
				return pm;

			}
		}
		else if(id.equals("DataInputPortModel")){
			pm = new DataInputPortModel();
			String[] data = token.split(",");
			if(data.length==18){
//				property MethodOutputPortModel="+pm.getLocation().x+","+pm.getLocation().y+","+pm.getSize().width+","+pm.getSize().height+","+pm.getPtDifference().width+","+pm.getPtDifference().height+","+pm.view_ID+","+pm.getName()+","+pm.getScope()+","+pm.getStereotype()+","+pm.getAttachElementLabelModel().getSize().width+","+pm.getAttachElementLabelModel().getSize().height+","+pm.getTypeRef()+","+pm.getDesc()+","+","+pm.getType()+","+pm.getUMLDataModel().getId()+IN3ModelTokenType.PROPERTY_N3EOF
				pm.x = Integer.parseInt(data[0]);
				pm.y = Integer.parseInt(data[1]);
				pm.w = Integer.parseInt(data[2]);
				pm.h = Integer.parseInt(data[3]);
				pm.dw = Integer.parseInt(data[4]);
				pm.dh = Integer.parseInt(data[5]);			
				pm.pid = data[6];

				if(!data[7].trim().equals("")){
					pm.setPropertyValue("ID_NAME", data[7]);
					pm.setName(data[7]);
				}

				pm.setScope(data[8]);
				pm.setPropertyValue("ID_SCOPE", Integer.parseInt(data[8]));

				if(!data[9].trim().equals("")){
					pm.setStereotype(data[9]);
					pm.setPropertyValue("ID_STEREOTYPE", data[9]);
				}
				//PKY 08061101 E 포트와 관련된 저장 불러오기 로직 보완작업
				pm.setPtDifference(new Dimension(Integer.parseInt(data[4]),Integer.parseInt(data[5])));
				pm.getAttachElementLabelModel().setSize(new Dimension(Integer.parseInt(data[10]),Integer.parseInt(data[11])));//PKY 08060201 S 포트 텍스트 사이즈 늘어나지 않는문제
				
				pm.setTypeRef(chkNULL(data[12]));
				pm.setDesc(chkNULL(data[13]));
				pm.setType(chkNULL(data[14]));
				pm.setQueueingPolicy(chkNULL(data[15]));
				pm.setQueueSize(chkNULL(data[16]));
//				pm.setBackGroundColor(new Color(null,Integer.parseInt(data[12]),Integer.parseInt(data[13]),Integer.parseInt(data[14])));
				pm.getUMLDataModel().setId(data[17]);

				this.viewStore.put(data[6], pm);
				return pm;

			}
		}
		else if(id.equals("DataOutputPortModel")){
			pm = new DataOutputPortModel();
			String[] data = token.split(",");
			if(data.length==18){
//				property MethodOutputPortModel="+pm.getLocation().x+","+pm.getLocation().y+","+pm.getSize().width+","+pm.getSize().height+","+pm.getPtDifference().width+","+pm.getPtDifference().height+","+pm.view_ID+","+pm.getName()+","+pm.getScope()+","+pm.getStereotype()+","+pm.getAttachElementLabelModel().getSize().width+","+pm.getAttachElementLabelModel().getSize().height+","+pm.getTypeRef()+","+pm.getDesc()+","+","+pm.getType()+","+pm.getUMLDataModel().getId()+IN3ModelTokenType.PROPERTY_N3EOF
				pm.x = Integer.parseInt(data[0]);
				pm.y = Integer.parseInt(data[1]);
				pm.w = Integer.parseInt(data[2]);
				pm.h = Integer.parseInt(data[3]);
				pm.dw = Integer.parseInt(data[4]);
				pm.dh = Integer.parseInt(data[5]);			
				pm.pid = data[6];

				if(!data[7].trim().equals("")){
					pm.setPropertyValue("ID_NAME", data[7]);
					pm.setName(data[7]);
				}

				pm.setScope(data[8]);
				pm.setPropertyValue("ID_SCOPE", Integer.parseInt(data[8]));

				if(!data[9].trim().equals("")){
					pm.setStereotype(data[9]);
					pm.setPropertyValue("ID_STEREOTYPE", data[9]);
				}
				//PKY 08061101 E 포트와 관련된 저장 불러오기 로직 보완작업
				pm.setPtDifference(new Dimension(Integer.parseInt(data[4]),Integer.parseInt(data[5])));
				pm.getAttachElementLabelModel().setSize(new Dimension(Integer.parseInt(data[10]),Integer.parseInt(data[11])));//PKY 08060201 S 포트 텍스트 사이즈 늘어나지 않는문제
				
				pm.setTypeRef(chkNULL(data[12]));
				pm.setDesc(chkNULL(data[13]));
				pm.setType(chkNULL(data[14]));
				pm.setQueueingPolicy(chkNULL(data[15]));
				pm.setQueueSize(chkNULL(data[16]));
//				pm.setBackGroundColor(new Color(null,Integer.parseInt(data[12]),Integer.parseInt(data[13]),Integer.parseInt(data[14])));
				pm.getUMLDataModel().setId(data[17]);

				this.viewStore.put(data[6], pm);
				return pm;

			}
			
		}
		else if(id.equals("EventInputPortModel")){
			pm = new EventInputPortModel();
			String[] data = token.split(",");
			if(data.length==15){
//				property MethodOutputPortModel="+pm.getLocation().x+","+pm.getLocation().y+","+pm.getSize().width+","+pm.getSize().height+","+pm.getPtDifference().width+","+pm.getPtDifference().height+","+pm.view_ID+","+pm.getName()+","+pm.getScope()+","+pm.getStereotype()+","+pm.getAttachElementLabelModel().getSize().width+","+pm.getAttachElementLabelModel().getSize().height+","+pm.getTypeRef()+","+pm.getDesc()+","+","+pm.getType()+","+pm.getUMLDataModel().getId()+IN3ModelTokenType.PROPERTY_N3EOF
				pm.x = Integer.parseInt(data[0]);
				pm.y = Integer.parseInt(data[1]);
				pm.w = Integer.parseInt(data[2]);
				pm.h = Integer.parseInt(data[3]);
				pm.dw = Integer.parseInt(data[4]);
				pm.dh = Integer.parseInt(data[5]);			
				pm.pid = data[6];

				if(!data[7].trim().equals("")){
					pm.setPropertyValue("ID_NAME", data[7]);
					pm.setName(data[7]);
				}

				pm.setScope(data[8]);
				pm.setPropertyValue("ID_SCOPE", Integer.parseInt(data[8]));

				if(!data[9].trim().equals("")){
					pm.setStereotype(data[9]);
					pm.setPropertyValue("ID_STEREOTYPE", data[9]);
				}
				//PKY 08061101 E 포트와 관련된 저장 불러오기 로직 보완작업
				pm.setPtDifference(new Dimension(Integer.parseInt(data[4]),Integer.parseInt(data[5])));
				pm.getAttachElementLabelModel().setSize(new Dimension(Integer.parseInt(data[10]),Integer.parseInt(data[11])));//PKY 08060201 S 포트 텍스트 사이즈 늘어나지 않는문제
				
//				pm.setTypeRef(data[12]);
				pm.setDesc(chkNULL(data[12]));
				pm.setType(chkNULL(data[13]));
			
//				pm.setBackGroundColor(new Color(null,Integer.parseInt(data[12]),Integer.parseInt(data[13]),Integer.parseInt(data[14])));
				pm.getUMLDataModel().setId(data[14]);

				this.viewStore.put(data[6], pm);
				return pm;

			}
		}
		else if(id.equals("EventOutputPortModel")){
			pm = new EventOutputPortModel();
			String[] data = token.split(",");
			if(data.length==15){
//				property MethodOutputPortModel="+pm.getLocation().x+","+pm.getLocation().y+","+pm.getSize().width+","+pm.getSize().height+","+pm.getPtDifference().width+","+pm.getPtDifference().height+","+pm.view_ID+","+pm.getName()+","+pm.getScope()+","+pm.getStereotype()+","+pm.getAttachElementLabelModel().getSize().width+","+pm.getAttachElementLabelModel().getSize().height+","+pm.getTypeRef()+","+pm.getDesc()+","+","+pm.getType()+","+pm.getUMLDataModel().getId()+IN3ModelTokenType.PROPERTY_N3EOF
				pm.x = Integer.parseInt(data[0]);
				pm.y = Integer.parseInt(data[1]);
				pm.w = Integer.parseInt(data[2]);
				pm.h = Integer.parseInt(data[3]);
				pm.dw = Integer.parseInt(data[4]);
				pm.dh = Integer.parseInt(data[5]);			
				pm.pid = data[6];

				if(!data[7].trim().equals("")){
					pm.setPropertyValue("ID_NAME", data[7]);
					pm.setName(data[7]);
				}

				pm.setScope(data[8]);
				pm.setPropertyValue("ID_SCOPE", Integer.parseInt(data[8]));

				if(!data[9].trim().equals("")){
					pm.setStereotype(data[9]);
					pm.setPropertyValue("ID_STEREOTYPE", data[9]);
				}
				//PKY 08061101 E 포트와 관련된 저장 불러오기 로직 보완작업
				pm.setPtDifference(new Dimension(Integer.parseInt(data[4]),Integer.parseInt(data[5])));
				pm.getAttachElementLabelModel().setSize(new Dimension(Integer.parseInt(data[10]),Integer.parseInt(data[11])));//PKY 08060201 S 포트 텍스트 사이즈 늘어나지 않는문제
				
//				pm.setTypeRef(data[12]);
				pm.setDesc(chkNULL(data[12]));
				pm.setType(chkNULL(data[13]));
			
//				pm.setBackGroundColor(new Color(null,Integer.parseInt(data[12]),Integer.parseInt(data[13]),Integer.parseInt(data[14])));
				pm.getUMLDataModel().setId(data[14]);

				this.viewStore.put(data[6], pm);
				return pm;

			}
		}
		String[] data = token.split(",");
		//PKY 08061101 S 포트와 관련된 저장 불러오기 로직 보완작업
		if(data.length==16){

			pm.x = Integer.parseInt(data[0]);
			pm.y = Integer.parseInt(data[1]);
			pm.w = Integer.parseInt(data[2]);
			pm.h = Integer.parseInt(data[3]);
			pm.dw = Integer.parseInt(data[4]);
			pm.dh = Integer.parseInt(data[5]);			
			pm.pid = data[6];

			if(!data[7].trim().equals("")){
				pm.setPropertyValue("ID_NAME", data[7]);
				pm.setName(data[7]);
			}

			pm.setScope(data[8]);
			pm.setPropertyValue("ID_SCOPE", Integer.parseInt(data[8]));

			if(!data[9].trim().equals("")){
				pm.setStereotype(data[9]);
				pm.setPropertyValue("ID_STEREOTYPE", data[9]);
			}
			//PKY 08061101 E 포트와 관련된 저장 불러오기 로직 보완작업
			pm.setPtDifference(new Dimension(Integer.parseInt(data[4]),Integer.parseInt(data[5])));
			pm.getAttachElementLabelModel().setSize(new Dimension(Integer.parseInt(data[10]),Integer.parseInt(data[11])));//PKY 08060201 S 포트 텍스트 사이즈 늘어나지 않는문제
			pm.setBackGroundColor(new Color(null,Integer.parseInt(data[12]),Integer.parseInt(data[13]),Integer.parseInt(data[14])));
			pm.getUMLDataModel().setId(data[15]);

			this.viewStore.put(data[6], pm);

		}
		return pm;

	}


	public ActivityParameterModel getActivityParameterModel(String token){

		ActivityParameterModel pm = new ActivityParameterModel();
		String[] data = token.split(",");
		if(data.length==15){

			pm.x = Integer.parseInt(data[0]);
			pm.y = Integer.parseInt(data[1]);
			pm.w = Integer.parseInt(data[2]);
			pm.h = Integer.parseInt(data[3]);
			pm.dw = Integer.parseInt(data[4]);
			pm.dh = Integer.parseInt(data[5]);			
			pm.pid = data[6];

			if(!data[7].trim().equals("")){
				pm.setPropertyValue("ID_NAME", data[7]);
				pm.setName(data[7]);
			}
			pm.setPtDifference(new Dimension(Integer.parseInt(data[4]),Integer.parseInt(data[5])));
			pm.setScope(data[8]);
			pm.setPropertyValue("ID_SCOPE", Integer.parseInt(data[8]));

			if(!data[9].trim().equals("")){
				pm.setStereotype(data[9]);
				pm.setPropertyValue("ID_STEREOTYPE", data[9]);
			}
			//PKY 08061101 E 포트와 관련된 저장 불러오기 로직 보완작업

			pm.getAttachElementLabelModel().setSize(new Dimension(Integer.parseInt(data[10]),Integer.parseInt(data[11])));//PKY 08060201 S 포트 텍스트 사이즈 늘어나지 않는문제
			pm.setBackGroundColor(new Color(null,Integer.parseInt(data[12]),Integer.parseInt(data[13]),Integer.parseInt(data[14])));


			this.viewStore.put(data[6], pm);

		}
		return pm;

	}



	public ExpansionNodeModel getExpansionNodeModel(String token){

		ExpansionNodeModel pm = new ExpansionNodeModel();
		String[] data = token.split(",");
		if(data.length==15){

			pm.x = Integer.parseInt(data[0]);
			pm.y = Integer.parseInt(data[1]);
			pm.w = Integer.parseInt(data[2]);
			pm.h = Integer.parseInt(data[3]);
			pm.dw = Integer.parseInt(data[4]);
			pm.dh = Integer.parseInt(data[5]);			
			pm.pid = data[6];

			if(!data[7].trim().equals("")){
				pm.setPropertyValue("ID_NAME", data[7]);
				pm.setName(data[7]);
			}
			pm.setPtDifference(new Dimension(Integer.parseInt(data[4]),Integer.parseInt(data[5])));
			pm.setScope(data[8]);
			pm.setPropertyValue("ID_SCOPE", Integer.parseInt(data[8]));

			if(!data[9].trim().equals("")){
				pm.setStereotype(data[9]);
				pm.setPropertyValue("ID_STEREOTYPE", data[9]);
			}
			//PKY 08061101 E 포트와 관련된 저장 불러오기 로직 보완작업

			pm.getAttachElementLabelModel().setSize(new Dimension(Integer.parseInt(data[10]),Integer.parseInt(data[11])));//PKY 08060201 S 포트 텍스트 사이즈 늘어나지 않는문제
			pm.setBackGroundColor(new Color(null,Integer.parseInt(data[12]),Integer.parseInt(data[13]),Integer.parseInt(data[14])));


			this.viewStore.put(data[6], pm);

		}
		return pm;

	}
	public ObjectPortModel getObjectPortModel(String token){

		ObjectPortModel pm = new ObjectPortModel();
		String[] data = token.split(",");
		if(data.length==16){

			pm.x = Integer.parseInt(data[0]);
			pm.y = Integer.parseInt(data[1]);
			pm.w = Integer.parseInt(data[2]);
			pm.h = Integer.parseInt(data[3]);
			pm.dw = Integer.parseInt(data[4]);
			pm.dh = Integer.parseInt(data[5]);			
			pm.pid = data[6];

			if(!data[7].trim().equals("")){
				pm.setPropertyValue("ID_NAME", data[7]);
				pm.setName(data[7]);
			}
			pm.setPtDifference(new Dimension(Integer.parseInt(data[4]),Integer.parseInt(data[5])));
			pm.setScope(data[8]);
			pm.setPropertyValue("ID_SCOPE", Integer.parseInt(data[8]));

			if(!data[9].trim().equals("")){
				pm.setStereotype(data[9]);
				pm.setPropertyValue("ID_STEREOTYPE", data[9]);
			}
			//PKY 08061101 E 포트와 관련된 저장 불러오기 로직 보완작업

			pm.getAttachElementLabelModel().setSize(new Dimension(Integer.parseInt(data[10]),Integer.parseInt(data[11])));//PKY 08060201 S 포트 텍스트 사이즈 늘어나지 않는문제
			pm.setBackGroundColor(new Color(null,Integer.parseInt(data[12]),Integer.parseInt(data[13]),Integer.parseInt(data[14])));
			String str = data[15];
			if(str!=null && !"null".equals(str));
			pm.setDesc(str);

			this.viewStore.put(data[6], pm);

		}
		return pm;

	}



	public ExitPointModel getExitPointModel(String token){

		ExitPointModel pm = new ExitPointModel();
		String[] data = token.split(",");
		if(data.length==15){

			pm.x = Integer.parseInt(data[0]);
			pm.y = Integer.parseInt(data[1]);
			pm.w = Integer.parseInt(data[2]);
			pm.h = Integer.parseInt(data[3]);
			pm.dw = Integer.parseInt(data[4]);
			pm.dh = Integer.parseInt(data[5]);			
			pm.pid = data[6];

			if(!data[7].trim().equals("")){
				pm.setPropertyValue("ID_NAME", data[7]);
				pm.setName(data[7]);
			}
			pm.setPtDifference(new Dimension(Integer.parseInt(data[4]),Integer.parseInt(data[5])));
			pm.setScope(data[8]);
			pm.setPropertyValue("ID_SCOPE", Integer.parseInt(data[8]));

			if(!data[9].trim().equals("")){
				pm.setStereotype(data[9]);
				pm.setPropertyValue("ID_STEREOTYPE", data[9]);
			}
			//PKY 08061101 E 포트와 관련된 저장 불러오기 로직 보완작업

			pm.getAttachElementLabelModel().setSize(new Dimension(Integer.parseInt(data[10]),Integer.parseInt(data[11])));//PKY 08060201 S 포트 텍스트 사이즈 늘어나지 않는문제
			pm.setBackGroundColor(new Color(null,Integer.parseInt(data[12]),Integer.parseInt(data[13]),Integer.parseInt(data[14])));


			this.viewStore.put(data[6], pm);

		}
		return pm;

	}

	public EntryPointModel getEntryPointModel(String token){

		EntryPointModel pm = new EntryPointModel();
		String[] data = token.split(",");

		if(data.length==15){

			pm.x = Integer.parseInt(data[0]);
			pm.y = Integer.parseInt(data[1]);
			pm.w = Integer.parseInt(data[2]);
			pm.h = Integer.parseInt(data[3]);
			pm.dw = Integer.parseInt(data[4]);
			pm.dh = Integer.parseInt(data[5]);			
			pm.pid = data[6];

			if(!data[7].trim().equals("")){
				pm.setPropertyValue("ID_NAME", data[7]);
				pm.setName(data[7]);
			}
			pm.setPtDifference(new Dimension(Integer.parseInt(data[4]),Integer.parseInt(data[5])));
			pm.setScope(data[8]);
			pm.setPropertyValue("ID_SCOPE", Integer.parseInt(data[8]));

			if(!data[9].trim().equals("")){
				pm.setStereotype(data[9]);
				pm.setPropertyValue("ID_STEREOTYPE", data[9]);
			}
			//PKY 08061101 E 포트와 관련된 저장 불러오기 로직 보완작업

			pm.getAttachElementLabelModel().setSize(new Dimension(Integer.parseInt(data[10]),Integer.parseInt(data[11])));//PKY 08060201 S 포트 텍스트 사이즈 늘어나지 않는문제
			pm.setBackGroundColor(new Color(null,Integer.parseInt(data[12]),Integer.parseInt(data[13]),Integer.parseInt(data[14])));


			this.viewStore.put(data[6], pm);

		}
		return pm;

	}



	public SubPartitonModel getSubPartitionModel(String token){


		SubPartitonModel pm = new SubPartitonModel();
		String[] data = token.split(",");
		Point location = new Point();
		Dimension size = new Dimension();

		if(data.length==6 ){

			location.x = Integer.parseInt(data[0]);
			location.y = Integer.parseInt(data[1]);
			size.width = Integer.parseInt(data[2]);
			size.height = Integer.parseInt(data[3]);

			String name = data[4];
			boolean isMode = Boolean.valueOf(data[5]).booleanValue();
			pm.setLocation(location);
			pm.setSize(size);
			pm.setIsMode(isMode);
			pm.setName(name);

		}
		return pm;

	}

	public LineBendpointModel getLineBendpointModel(String token){
		float weight = 0;
		int w1 = 0;
		int h1 = 0;
		int w2 = 0;
		int h2 = 0;
		String[] data = token.split(",");
		LineBendpointModel lbm = new LineBendpointModel();

		if(data.length==5){

			weight = Float.parseFloat(data[0]);
			w1 = Integer.parseInt(data[1]);
			h1 = Integer.parseInt(data[2]);
			w2 = Integer.parseInt(data[3]);
			h2 = Integer.parseInt(data[4]);

			lbm.setWeight(weight);
			lbm.setRelativeDimensions(new Dimension(w1,h1),new Dimension(w2,h2));
			return lbm;
		}
		return lbm;
	}

	public Dimension getSize(String token) {
		int a=0;
		int b=0;
		String[] data = token.split(",");
		if(data.length==2){

			a = Integer.parseInt(data[0]);
			b = Integer.parseInt(data[1]);

		}
		return new Dimension(a,b);
	}

	public void createLineModel(int type,UMLDataModel um,HashMap viewStore){
		LineModel lm = null;
		N3EditorDiagramModel ndm = null;
		String diagramId = um.getProperty("ID_DIAGRAM");
		String sourceId = um.getProperty("ID_SOURCE");
		String targetId = um.getProperty("ID_TARGET");
		String targetTerminal = um.getProperty("ID_TARGET_TERMINAL");
		String sourceTerminal = um.getProperty("ID_SOURCE_TERMINAL");
		String y = um.getProperty("ID_Y");

		String isCall= um.getProperty("ID_CALL");
		String isRet = um.getProperty("ID_RETURN");
		String isNew = um.getProperty("ID_NEW");
		String isSyn = um.getProperty("ID_SYN");

		//PKY 08071601 S 라인 프로퍼티 개행안되는 문제 수정
		if(um.getProperty("ID_DESCRIPTORS")!=null){
			um.setProperty("ID_DESCRIPTORS", ((String)um.getProperty("ID_DESCRIPTORS")).replaceAll("/n", "\n"));
		}
		if(um.getProperty("ID_SOURCE_ROLE_NOTE")!=null){
			um.setProperty("ID_SOURCE_ROLE_NOTE", ((String)um.getProperty("ID_SOURCE_ROLE_NOTE")).replaceAll("/n", "\n"));
		}
		if(um.getProperty("ID_TARGET_ROLE_NOTE")!=null){
			um.setProperty("ID_TARGET_ROLE_NOTE", ((String)um.getProperty("ID_TARGET_ROLE_NOTE")).replaceAll("/n", "\n"));
		}
		if(um.getProperty("ID_TRAN_EFFECTS")!=null){
			um.setProperty("ID_TRAN_EFFECTS", ((String)um.getProperty("ID_TRAN_EFFECTS")).replaceAll("/n", "\n"));
		}
		//PKY 08071601 E 라인 프로퍼티 개행안되는 문제 수정

		//property start
		HashMap detailProp = new HashMap();
		detailProp.put("ID_NAME",um.getProperty("ID_NAME"));
		detailProp.put("name",um.getProperty("ID_NAME"));//PKY 08070101 S 복사할때 라인 이름이 빠지고 복사되는문제
		detailProp.put("ID_STEREOTYPE",um.getProperty("ID_STEREOTYPE"));//PKY 08070904 S include,extends 스트레오값나오도록
		detailProp.put("ID_SIZE_MIDDLE",um.getProperty("ID_SIZE_MIDDLE"));
		detailProp.put("ID_SIZE_SOURCE",um.getProperty("ID_SIZE_SOURCE"));
		detailProp.put("ID_SIZE_TARGET",um.getProperty("ID_SIZE_TARGET"));
		detailProp.put("ID_TARGET_ALLOWDUPLICATES",um.getProperty("ID_TARGET_ALLOWDUPLICATES"));
		detailProp.put("ID_TARGET_QUALIFIER",um.getProperty("ID_TARGET_QUALIFIER"));
		detailProp.put("ID_TARGET_DERIVED",um.getProperty("ID_TARGET_DERIVED"));
		detailProp.put("ID_TARGET_DERIVEDUNION",um.getProperty("ID_TARGET_DERIVEDUNION"));
		detailProp.put("ID_SOURCE_OWNED",um.getProperty("ID_SOURCE_OWNED"));
		detailProp.put("ID_DESCRIPTORS",um.getProperty("ID_DESCRIPTORS"));
		detailProp.put("ID_ACTORIMAGE",um.getProperty("ID_ACTORIMAGE"));	
		detailProp.put("ID_TARGET_OWNED",um.getProperty("ID_TARGET_OWNED"));
		detailProp.put("ID_TARGET_ROLE_NOTE",um.getProperty("ID_TARGET_ROLE_NOTE"));
		detailProp.put("ID_SOURCE_ORDERED",um.getProperty("ID_SOURCE_ORDERED"));
		detailProp.put("ID_SOURCE_DERIVEDUNION",um.getProperty("ID_SOURCE_DERIVEDUNION"));
		detailProp.put("ID_TARGET_AGGREGATION",um.getProperty("ID_TARGET_AGGREGATION"));
		detailProp.put("ID_SOURCE_ROLE",um.getProperty("ID_SOURCE_ROLE"));
		detailProp.put("ID_SOURCE_NAVIGABILITY",um.getProperty("ID_SOURCE_NAVIGABILITY"));
		detailProp.put("ID_SOURCE_DERIVED",um.getProperty("ID_SOURCE_DERIVED"));
		detailProp.put("ID_TARGET_NAVIGABILITY",um.getProperty("ID_TARGET_NAVIGABILITY"));
		detailProp.put("ID_SOURCE_AGGREGATION",um.getProperty("ID_SOURCE_AGGREGATION"));
		detailProp.put("ID_TARGET_CONSTRAINT",um.getProperty("ID_TARGET_CONSTRAINT"));
		detailProp.put("ID_TARGET_ORDERED",um.getProperty("ID_TARGET_ORDERED"));
		detailProp.put("ID_TARGET_MUL",um.getProperty("ID_TARGET_MUL"));
		detailProp.put("ID_TARGET_ROLE",um.getProperty("ID_TARGET_ROLE"));
		detailProp.put("ID_SOURCE_ALLOWDUPLICATES",um.getProperty("ID_SOURCE_ALLOWDUPLICATES"));
		detailProp.put("ID_SOURCE_QUALIFIER",um.getProperty("ID_SOURCE_QUALIFIER"));
		detailProp.put("ID_SOURCE_CONSTRAINT",um.getProperty("ID_SOURCE_CONSTRAINT"));
		detailProp.put("ID_SOURCE_MUL",um.getProperty("ID_SOURCE_MUL"));
		detailProp.put("ID_SOURCE_ROLE_NOTE",um.getProperty("ID_SOURCE_ROLE_NOTE"));
		detailProp.put("ID_NAME_LOCATION_MIDDLE",um.getProperty("ID_NAME_LOCATION_MIDDLE"));
		detailProp.put("ID_NAME_POINT_MIDDLE",um.getProperty("ID_NAME_POINT_MIDDLE"));
		detailProp.put("ID_NAME_LOCATION_SOURCE",um.getProperty("ID_NAME_LOCATION_SOURCE"));
		detailProp.put("ID_NAME_POINT_SOURCE",um.getProperty("ID_NAME_POINT_SOURCE"));
		detailProp.put("ID_NAME_LOCATION_TARGET",um.getProperty("ID_NAME_LOCATION_TARGET"));
		detailProp.put("ID_NAME_POINT_TARGET",um.getProperty("ID_NAME_POINT_TARGET"));
		detailProp.put("ID_GUARD",um.getProperty("ID_GUARD"));
		detailProp.put("ID_WEIGHT",um.getProperty("ID_WEIGHT"));
		//PKY 08071601 S State  TransitionFlow 표시가 다른문제 수정
		detailProp.put("ID_TRAN_EFFECTS",um.getProperty("ID_TRAN_EFFECTS"));//PKY 08071601 S State 저장안되는문제
		if(um.getElementProperty("ID_TRAN_TRIGERS")!=null)
			detailProp.put("ID_TRAN_TRIGERS",um.getElementProperty("ID_TRAN_TRIGERS"));//PKY 08071601 S State 저장안되는문제


		//property end

		UMLModel umSource = (UMLModel)viewStore.get(sourceId);
		UMLModel umTarget = (UMLModel)viewStore.get(targetId);
		java.util.Vector bendpoints = (java.util.Vector)um.getElementProperty("Bendpoints");


		if (type == 1000) {
			lm=new ExtendLineModel();

			//			viewStore.get()
		}
		else if (type == 1001) {
			lm=new IncludeLineModel();
		}
		else if (type == 1002) {
			lm=new ConnectorLineModel();
		}
		else if (type == 1003) {
			lm=new MessageAssoicateLineModel();
			if(this.msgs.size()>0){
				//				LineTextModel ltm = ((MessageModel)lm).getMiddleLineTextModel();
				//				LineTextModel ltmS = ((MessageModel)lm).getSourceLineTextModel();
				//				LineTextModel ltmT = ((MessageModel)lm).getTargetLineTextModel();
				for(int i=0;i<this.msgs.size();i++){
					MessageCommunicationModel mm = (MessageCommunicationModel)msgs.get(i);
					if(mm.getKey().equals("centerMsg")){
						((MessageAssoicateLineModel)lm).addMiddleLineTextModel(mm);
					}
					else if(mm.getKey().equals("sourceMsg")){
						((MessageAssoicateLineModel)lm).addSourceLineTextModel(mm);
					}
					else if(mm.getKey().equals("targetMsg")){
						((MessageAssoicateLineModel)lm).addTargetLineTextModel(mm);
					}
				}
			}
			lm.setLoad(true);
			this.msgs.clear();
		}
		else if (type == 1004) {
			lm=new AssociateLineModel();


		}
		else if (type == 1005) {
			lm=new GeneralizeLineModel();
		}
		else if (type == 1006) {
			lm=new RealizeLineModel();
		}
		else if (type == 1007) {
			lm=new NoteLineModel();
		}
		else if (type == 1008) {
			lm=new DependencyLineModel();
		}
		else if (type == 1009) {
			lm=new ControlFlowLineModel();
		}
		else if (type == 1010) {
			lm=new RequiredInterfaceLineModel();
		}
		else if (type == 1011) {
			lm=new ProvidedInterfaceLineModel();
		}
		else if (type == 1012) {
			lm=new RoleBindingLineModel();
		}
		else if (type == 1013) {
			lm=new DelegateLineModel();
		}
		else if (type == 1014) {
			lm=new OccurrenceLineModel();
		}
		else if (type == 1015) {
			lm=new RepresentsLineModel();
		}
		//		else if (type == 1016) {
		//			lm=new ManifestLineModel();
		//		}
		//		else if (type == 1017) {
		//			lm=new DeployLineModel();
		//		}
		else if (type == 1018) {
//			lm=new TimingMessageLineModel();
		}
		//		else if (type == 1019) {
		//			lm=new CommunicationPathLineModel();
		//		}
		else if (type == 1020) {
			//			lm=new MessageModel();
			//			if(y!=null && !y.trim().equals("")){
			//				((MessageModel)lm).setY(Integer.valueOf(y).intValue());
			//			}
			//
			//			if(isCall!=null && isCall.trim().equals("true")){
			//				((MessageModel)lm).setIsCall(true);
			//			}
			//			else{
			//				((MessageModel)lm).setIsCall(false);
			//			}
			//
			//			if(isRet!=null && isRet.trim().equals("true")){
			//				((MessageModel)lm).setIsReturn(true);
			//
			//			}
			//			else{
			//				((MessageModel)lm).setIsReturn(false);
			//			}
			//			if(isNew!=null && isNew.trim().equals("true")){
			//				((MessageModel)lm).setIsNew(true);
			//			}
			//			else{
			//				((MessageModel)lm).setIsNew(false);
			//			}
			//			if(isSyn!=null && isSyn.trim().equals("true")){
			//				((MessageModel)lm).setIsSynchronous(true);
			//			}
			//			else{
			//				((MessageModel)lm).setIsSynchronous(false);
			//			}
			//
			//
			//
			//			//center
			//			if(this.msgs.size()>0){
			//
			//				for(int i=0;i<this.msgs.size();i++){
			//					MessageCommunicationModel mm = (MessageCommunicationModel)msgs.get(i);
			//					if(mm.getKey().equals("centerMsg")){
			//
			//						((MessageModel)lm).addMiddleLineTextModel(mm);
			//						//PKY 08061801 S 시퀀스다이어그램 메시지 중앙메세지 사이즈저장안되는문제
			//						int b = ((MessageModel)lm).getMiddleLineTextModel().getSize().width+((MessageModel)lm).getMiddleLineTextModel().getSize().height;
			//						int j = mm.getSize().width+mm.getSize().height;
			//
			//						if(b<j){
			//							((MessageModel)lm).getMiddleLineTextModel().setSize(new Dimension(mm.getSize().width,mm.getSize().height));
			//						}
			//						if(mm.getLocation().x!=14&&mm.getLocation().y!=20)
			//							((MessageModel)lm).getMiddleLineTextModel().setLocation(mm.getLocation());
			//						//PKY 08061801 E 시퀀스다이어그램 메시지 중앙메세지 사이즈저장안되는문제
			//					}
			//					else if(mm.getKey().equals("sourceMsg")){
			//						((MessageModel)lm).addSourceLineTextModel(mm);
			//						//PKY 08061801 S 시퀀스다이어그램 메시지 중앙메세지 사이즈저장안되는문제
			//						int b = ((MessageModel)lm).getSourceLineTextModel().getSize().width+((MessageModel)lm).getSourceLineTextModel().getSize().height;
			//						int j = mm.getSize().width+mm.getSize().height;
			//
			//						if(b<j){
			//							((MessageModel)lm).getSourceLineTextModel().setSize(new Dimension(mm.getSize().width,mm.getSize().height));
			//						}
			//						if(mm.getLocation().x!=14&&mm.getLocation().y!=20)
			//							((MessageModel)lm).getSourceLineTextModel().setLocation(mm.getLocation());
			//						//PKY 08061801 E 시퀀스다이어그램 메시지 중앙메세지 사이즈저장안되는문제
			//					}
			//					else if(mm.getKey().equals("targetMsg")){
			//						((MessageModel)lm).addTargetLineTextModel(mm);
			//						//PKY 08061801 S 시퀀스다이어그램 메시지 중앙메세지 사이즈저장안되는문제
			//						int b = ((MessageModel)lm).getTargetLineTextModel().getSize().width+((MessageModel)lm).getTargetLineTextModel().getSize().height;
			//						int j = mm.getSize().width+mm.getSize().height;
			//
			//						if(b<j){
			//							((MessageModel)lm).getTargetLineTextModel().setSize(new Dimension(mm.getSize().width,mm.getSize().height));
			//						}
			//						if(mm.getLocation().x!=14&&mm.getLocation().y!=20)
			//							((MessageModel)lm).getTargetLineTextModel().setLocation(mm.getLocation());
			//						//PKY 08061801 E 시퀀스다이어그램 메시지 중앙메세지 사이즈저장안되는문제
			//					}
			//				}
			//			}
			//			lm.setLoad(true);
			//			this.msgs.clear();
		}
		else if (type == 1021) {

		}

		else if (type == 1022) {
			lm=new ImportLineModel();
		}
		else if (type == 1023) {
			lm=new MergeLineModel();
		}
		else if (type == 1024) {
			//			Object obj = this.viewStore.get("diagram");
			//			if(obj!=null){
			//				if(obj instanceof N3EditorDiagramModel){
			//					ndm = (N3EditorDiagramModel)obj;
			//				}
			//			}
			//			String size= um.getProperty("ID_SIZE");
			//			String location = um.getProperty("ID_LOCATION");
			//			UMLModel child = new SelfMessageModel();
			//			child.setSize(this.getSize(size));
			//			child.setLocation(this.getLocation(location));
			//			SelfMessageModel seMsg=(SelfMessageModel)child;
			//			Object nameArray=this.current_UMLDataModel.getElementProperty("isSelfMessage");
			//			java.util.ArrayList list=(java.util.ArrayList)nameArray;
			//			String name=(String)list.get(0);
			//			String[] value=name.split(",");
			//			//PKY 08082201 S 오퍼레이션이 존재하지 않아도 뜨는 메시지 안뜨도록 
			//			if(value.length!=2){
			//				seMsg.SetText(value[0]);
			//			}
			////			seMsg.SetText(value[0]);//PKY 08082201 S 오퍼레이션이 존재하지 않아도 뜨는 메시지 안뜨도록 
			//			if(value.length==2){
			//				UMLTreeModel utm = (UMLTreeModel)this.modelStore.get(value[1]);
			//				if(utm!=null){
			//					UMLModel um1 = (UMLModel)utm.getRefModel();
			//					if(um1!=null && um1 instanceof ClassifierModel){
			//						ClassifierModel cm = (ClassifierModel)um1;
			//						seMsg.setTrm((TypeRefModel)cm.getClassModel());
			//						OperationEditableTableItem opm = cm.getClassModel().searchOperationEditableTableItem(value[0]);
			//						if(opm!=null){
			//							seMsg.setOti(opm);
			//							seMsg.SetText(value[0]);//PKY 08082201 S 오퍼레이션이 존재하지 않아도 뜨는 메시지 안뜨도록 
			//						}
			//					}else if(um1!=null && um1 instanceof ClassifierModelTextAttach){//PKY 08082201 S 인터페이스 셀프 메시지 오퍼레이션 리스트 안뜨는문제
			//						ClassifierModelTextAttach cm = (ClassifierModelTextAttach)um1;
			//						seMsg.setTrm((TypeRefModel)cm.getClassModel());
			//						OperationEditableTableItem opm = cm.getClassModel().searchOperationEditableTableItem(value[0]);
			//						if(opm!=null){
			//							seMsg.setOti(opm);
			//							seMsg.SetText(value[0]);//PKY 08082201 S 오퍼레이션이 존재하지 않아도 뜨는 메시지 안뜨도록 
			//						}
			//					}//PKY 08082201 E 인터페이스 셀프 메시지 오퍼레이션 리스트 안뜨는문제
			//
			//
			//				}else{//PKY 08082201 S 시퀀스 저장 불러올 경우 오퍼레이션 연결이 안되어 서로 정보가 호환안되는문제
			//					//PKY 08082201 S 시퀀스 같은 객체의 오퍼레이션이 두개 이상 일 경우 마지막 추가된 오퍼레이션이 들어가는문제
			//					if(TeamProjectManager.getInstance().getNoOperationModelMap().get(value[1]+","+value[0])!=null){
			//						ArrayList arrayItem = (ArrayList)TeamProjectManager.getInstance().getNoOperationModelMap().get(value[1]+","+value[0]);
			//						arrayItem.add(seMsg);
			//						TeamProjectManager.getInstance().getNoOperationModelMap().put(value[1]+","+value[0], arrayItem);
			//					}else{
			//						ArrayList arrayItem = new ArrayList();
			//						arrayItem.add(seMsg);
			//						TeamProjectManager.getInstance().getNoOperationModelMap().put(value[1]+","+value[0], arrayItem);
			//					}
			//					//PKY 08082201 E 시퀀스 같은 객체의 오퍼레이션이 두개 이상 일 경우 마지막 추가된 오퍼레이션이 들어가는문제
			////					TeamProjectManager.getInstance().getNoOperationModelMap().put(value[1]+","+value[0], seMsg);
			//				}
			//				//PKY 08082201 E 시퀀스 저장 불러올 경우 오퍼레이션 연결이 안되어 서로 정보가 호환안되는문제
			//
			//				
			//			}
			//			ndm.initSelfMessageModel((SelfMessageModel)child);
			//			ndm.addChild(child);
			return;
		}
		//PKY 08072401 S ObjectFlow모델 추가
		else if(type == 1026){
			lm=new ObjectFlowLineModel();
		}
		else {
			lm=new LineModel();
		}

		lm.setTargetTerminal(targetTerminal);
		Object obj = this.viewStore.get("diagram");
		if(obj!=null){
			if(obj instanceof N3EditorDiagramModel){
				ndm = (N3EditorDiagramModel)obj;
			}
		}
		if(umSource!=null){
			lm.setSource(umSource);
		}
		else{

			umSource = (UMLModel)this.inModelViewStore.get(sourceId);
			if(umSource!=null)
				lm.setSource(umSource);


		}

		lm.setSourceTerminal(sourceTerminal);



		if(umTarget!=null){
			lm.setTarget(umTarget);
		}
		else{

			umTarget = (UMLModel)this.inModelViewStore.get(targetId);
			if(umTarget!=null)
				lm.setTarget(umTarget);
		}


		if(this.isBoundary){
			Object obj1 = lineStore.get(diagramId);
			if(obj1==null){
				java.util.ArrayList list = new java.util.ArrayList();
				list.add(lm);
				lm.setSourceId(sourceId);
				lm.setTargetId(targetId);
				lineStore.put(diagramId, list);
			}
			else{
				java.util.ArrayList list = (java.util.ArrayList)obj1;
				lm.setSourceId(sourceId);
				lm.setTargetId(targetId);
				list.add(lm);
			}
		}
		else{

			lm.attachSource();
			if(ndm!=null)
				lm.attachTarget(ndm);

		}
		lm.setLoad(true);

		lm.setDetailProp(detailProp);
		//PKY 08081101 S 2회 저장할 경우 스트레오값이 사라지는 문제 
		if(detailProp.get(LineModel.ID_STEREOTYPE)!=null){
			lm.setStereotype((String)detailProp.get(LineModel.ID_STEREOTYPE));
		}
		//PKY 08081101 E 2회 저장할 경우 스트레오값이 사라지는 문제 

		if(bendpoints!=null)
			lm.setBendpoints(bendpoints);

	}


	public void parse(File file) throws Exception	{
		this.viewStore.clear();
		this.modelStore.clear();
		if (file.isDirectory())	{
			File[] list = file.listFiles();

			for (int i=0; i<list.length; i++)
				parse(list[i]);
		}
		else if (file.getName().endsWith(".nmdl")||file.getName().endsWith(".link"))	{
			parser = new N3ModelParser(
					file, new TJavaBuilder(new PrintWriter(System.out)),loadProgressRun);

			parser.parse();

		}
	}
	//e_create_model

	public String putLinesModel(UMLModel uMLModel){
		//2008043001 PKY S Timing 객체 저장 로직 추가
		
		//2008043001 PKY E Timing 객체 저장 로직 추가
		for (int i1 = 0; i1 < uMLModel.getSourceConnections().size(); i1++) {
			LineModel li = (LineModel)uMLModel.getSourceConnections().get(i1);
			this.viewLines.add(li);
		}
		return "";	
	}

	public String wirteDetailProp(HashMap detailProp){
		StringBuffer sb = new StringBuffer();
		Iterator iterator = detailProp.keySet().iterator();
		while (iterator.hasNext()) {
			String key = (String)iterator.next();
			Object obj = detailProp.get(key);
			if (obj instanceof String ) {
				String data = obj.toString();
				if(!key.equals("ID_SIZE")&&!key.equals("ID_NAME")
						&&!key.equals("ID_NAME_LOCATION_MIDDLE")&&!key.equals("ID_NAME_POINT_MIDDLE")&&!key.equals("ID_SIZE_MIDDLE")
						&&!key.equals("ID_NAME_LOCATION_SOURCE")&&!key.equals("ID_NAME_POINT_SOURCE")&&!key.equals("ID_SIZE_SOURCE")
						&&!key.equals("ID_NAME_LOCATION_TARGET")&&!key.equals("ID_NAME_POINT_TARGET")&&!key.equals("ID_SIZE_TARGET")//PKY 08052901 S 포함 에러발생 수정
						&&!key.equals("name")&&!key.equals("ID_STEREOTYPE"))//PKY 08072401 S 다이어그램에서 스트레오변경시 저장 불러오기 할때 변경안되는문제 수정
					sb.append("property "+key+"="+obj.toString()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			}
		}
		return sb.toString();
	}

	public String writeLineModel(LineModel lm){
		boolean bendPoint =false;//PKY 08052901 S Communication Line Message나오지않는 문제
		boolean propertysCheck=false;//PKY 08071601 S State 저장안되는문제 
		StringBuffer sb = new StringBuffer("model");
		String type = ProjectManager.getInstance().getLineModelName(lm);
		sb.append(" "+type +" (\n");
		sb.append("model_propertys" +" <\n");
		sb.append("property ID_DIAGRAM="+lm.getDiagram().getUMLDataModel().getId()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
		sb.append("property ID_SOURCE="+lm.getSource().getView_ID()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
		sb.append("property ID_SOURCE_TERMINAL="+lm.getSourceTerminal()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
		sb.append("property ID_TARGET="+lm.getTarget().getView_ID()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
		sb.append("property ID_TARGET_TERMINAL="+lm.getTargetTerminal()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;

		//PKY 08052101 S 타이밍 메시지 이름 저장안되는 문제
	
			sb.append("property ID_NAME="+lm.getName()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;//PKY 08052101 S LineNAME저장안되는문제
			sb.append("property ID_STEREOTYPE="+lm.getStereotype()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;//PKY 08070904 S include,extends 스트레오값나오도록
		
		//PKY 08052101 E 타이밍 메시지 이름 저장안되는 문제
		//PKY 08060201 S
		//		//PKY 08052801 S LineOladPoint 추가
		//		if(lm.getMiddleLineTextModel()!=null)
		//		if(lm.getMiddleLineTextModel().getN3ConnectionLocator()!=null){
		//		if(lm.getMiddleLineTextModel().getN3ConnectionLocator().getPp()!=null)
		//		sb.append("property ID_NAME_LOCATION="+lm.getMiddleLineTextModel().getN3ConnectionLocator().getPp().getLocation().x+","+lm.getMiddleLineTextModel().getN3ConnectionLocator().getPp().getLocation().y+IN3ModelTokenType.PROPERTY_N3EOF+"\n");
		//		sb.append("property ID_NAME_POINT="+lm.getMiddleLineTextModel().getN3ConnectionLocator().oldPoint.x+","+lm.getMiddleLineTextModel().getN3ConnectionLocator().oldPoint.y+IN3ModelTokenType.PROPERTY_N3EOF+"\n");
		//		}
		//		//PKY 08052801 E LineOladPoint 추가
		//PKY 08060201 E
		//		sb.append("property ID_SIZE="+lm.getMiddleLineTextModel().getSize().height+","+lm.getMiddleLineTextModel().getSize().width+IN3ModelTokenType.PROPERTY_N3EOF+"\n");//PKY 08052101 S LineNAME저장안되는문제
		sb.append(this.wirteDetailProp(lm.getDetailProp()));
		//PKY 08052901 S 라인(타켓,소스) 저장되도록 수정

		//PKY 08052801 S LineOladPoint 추가
		if(lm.getMiddleLineTextModel()!=null)
			if(lm.getMiddleLineTextModel().getN3ConnectionLocator()!=null){
				if(lm.getMiddleLineTextModel().getN3ConnectionLocator().getPp()!=null){
					sb.append("property ID_NAME_LOCATION_MIDDLE="+lm.getMiddleLineTextModel().getN3ConnectionLocator().getPp().getLocation().x+","+lm.getMiddleLineTextModel().getN3ConnectionLocator().getPp().getLocation().y+IN3ModelTokenType.PROPERTY_N3EOF+"\n");
					sb.append("property ID_NAME_POINT_MIDDLE="+lm.getMiddleLineTextModel().getN3ConnectionLocator().oldPoint.x+","+lm.getMiddleLineTextModel().getN3ConnectionLocator().oldPoint.y+IN3ModelTokenType.PROPERTY_N3EOF+"\n");
					sb.append("property ID_SIZE_MIDDLE="+lm.getMiddleLineTextModel().getSize().height+","+lm.getMiddleLineTextModel().getSize().width+IN3ModelTokenType.PROPERTY_N3EOF+"\n");//PKY 08052101 S LineNAME저장안되는문제
				}
			}
		//PKY 08052801 E LineOladPoint 추가
		if(lm.getSourceLineTextModel()!=null)
			if(lm.getSourceLineTextModel().getMessageSize()>0)			
				if(lm.getSourceLineTextModel().getN3ConnectionLocator()!=null){
					if(lm.getSourceLineTextModel().getN3ConnectionLocator().getPp()!=null){
						sb.append("property ID_NAME_LOCATION_SOURCE="+lm.getSourceLineTextModel().getN3ConnectionLocator().getPp().getLocation().x+","+lm.getSourceLineTextModel().getN3ConnectionLocator().getPp().getLocation().y+IN3ModelTokenType.PROPERTY_N3EOF+"\n");
						sb.append("property ID_NAME_POINT_SOURCE="+lm.getSourceLineTextModel().getN3ConnectionLocator().getOldPoint().x+","+lm.getSourceLineTextModel().getN3ConnectionLocator().getOldPoint().y+IN3ModelTokenType.PROPERTY_N3EOF+"\n");
						sb.append("property ID_SIZE_SOURCE="+lm.getSourceLineTextModel().getSize().height+","+lm.getSourceLineTextModel().getSize().width+IN3ModelTokenType.PROPERTY_N3EOF+"\n");//PKY 08052101 S LineNAME저장안되는문제
					}
				}
		if(lm.getTargetLineTextModel()!=null)
			if(lm.getTargetLineTextModel().getMessageSize()>0)
				if(lm.getTargetLineTextModel().getN3ConnectionLocator()!=null){
					if(lm.getTargetLineTextModel().getN3ConnectionLocator().getPp()!=null){
						sb.append("property ID_NAME_LOCATION_TARGET="+lm.getTargetLineTextModel().getN3ConnectionLocator().getPp().getLocation().x+","+lm.getTargetLineTextModel().getN3ConnectionLocator().getPp().getLocation().y+IN3ModelTokenType.PROPERTY_N3EOF+"\n");
						sb.append("property ID_NAME_POINT_TARGET="+lm.getTargetLineTextModel().getN3ConnectionLocator().getOldPoint().x+","+lm.getTargetLineTextModel().getN3ConnectionLocator().getOldPoint().y+IN3ModelTokenType.PROPERTY_N3EOF+"\n");
						sb.append("property ID_SIZE_TARGET="+lm.getTargetLineTextModel().getSize().height+","+lm.getTargetLineTextModel().getSize().width+IN3ModelTokenType.PROPERTY_N3EOF+"\n");//PKY 08052101 S LineNAME저장안되는문제
					}
				}
		//PKY 08052901 E 라인(타켓,소스) 저장되도록 수정


		//		if(lm instanceof MessageModel){
		//			MessageModel mm = (MessageModel)lm;
		//			LineTextModel ltm = mm.getMiddleLineTextModel();
		//			LineTextModel ltmS = mm.getSourceLineTextModel();
		//			LineTextModel ltmT = mm.getTargetLineTextModel();
		//			sb.append("property ID_CALL="+mm.getIsCall()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
		//			sb.append("property ID_RETURN="+mm.getIsReturn()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
		//			sb.append("property ID_NEW="+mm.getIsNew()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
		//			sb.append("property ID_SYN="+mm.getIsSynchronous()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
		//			sb.append("property ID_Y="+mm.getY()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
		//			if(ltm.getMessageSize()>0 || ltmS.getMessageSize()>0
		//					|| ltmT.getMessageSize()>0){
		//				//PKY 08071601 S State 저장안되는문제 
		//				sb.append("propertys_list [\n");
		//				//PKY 08071601 E State 저장안되는문제 
		//				sb.append("objects MessageCommunications+\n") ;
		//				for(int i=0;i<ltm.getMessageSize();i++){
		//					MessageCommunicationModel mc = ltm.getMessageCommunicationModel(i);
		//					sb.append("property centerMsg="+mc.wirteModel()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
		//
		//				}
		//				for(int i=0;i<ltmS.getMessageSize();i++){
		//					MessageCommunicationModel mc = ltmS.getMessageCommunicationModel(i);
		//					sb.append("property sourceMsg="+mc.wirteModel()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
		//
		//				}
		//				for(int i=0;i<ltmT.getMessageSize();i++){
		//					MessageCommunicationModel mc = ltmS.getMessageCommunicationModel(i);
		//					sb.append("property targetMsg="+mc.wirteModel()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
		//
		//				}
		//				sb.append("-\n") ;
		//				//PKY 08052901 S Communication Line Message나오지않는 문제
		//				if(lm.getBendpoints().size()>0){					
		//					sb.append("objects Bendpoints+\n") ;
		//					for(int i=0;i<lm.getBendpoints().size();i++){
		//						LineBendpointModel lbm =  (LineBendpointModel)lm.getBendpoints().get(i);
		//						sb.append("property Bendpoint="+lbm.writeModel()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
		//
		//					}
		//					sb.append("-\n") ;
		//					bendPoint=true;
		//				}
		//				//PKY 08052901 E Communication Line Message나오지않는 문제
		//				sb.append("]\n") ;
		//
		//			}
		//
		//		}
		//		else 
		if(lm instanceof MessageAssoicateLineModel){
			MessageAssoicateLineModel mm = (MessageAssoicateLineModel)lm;
			LineTextModel ltm = mm.getMiddleLineTextModel();
			LineTextModel ltmS = mm.getSourceLineTextModel();
			LineTextModel ltmT = mm.getTargetLineTextModel();
			if(ltm.getMessageSize()>0 || ltmS.getMessageSize()>0
					|| ltmT.getMessageSize()>0){
				//PKY 08071601 S State 저장안되는문제 
				sb.append("propertys_list [\n");
				//PKY 08071601 E State 저장안되는문제 

				sb.append("objects MessageCommunications+\n") ;
				for(int i=0;i<ltm.getMessageSize();i++){
					MessageCommunicationModel mc = ltm.getMessageCommunicationModel(i);
					sb.append("property centerMsg="+mc.wirteModel()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;

				}
				for(int i=0;i<ltmS.getMessageSize();i++){
					MessageCommunicationModel mc = ltmS.getMessageCommunicationModel(i);
					sb.append("property sourceMsg="+mc.wirteModel()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;

				}
				for(int i=0;i<ltmT.getMessageSize();i++){
					MessageCommunicationModel mc = ltmS.getMessageCommunicationModel(i);
					sb.append("property targetMsg="+mc.wirteModel()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;

				}
				sb.append("-\n") ;
				//PKY 08052901 S Communication Line Message나오지않는 문제
				if(lm.getBendpoints().size()>0){					
					sb.append("objects Bendpoints+\n") ;
					for(int i=0;i<lm.getBendpoints().size();i++){
						LineBendpointModel lbm =  (LineBendpointModel)lm.getBendpoints().get(i);
						sb.append("property Bendpoint="+lbm.writeModel()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;

					}
					sb.append("-\n") ;
					bendPoint=true;
				}
				//PKY 08052901 E Communication Line Message나오지않는 문제
				sb.append("]\n") ;
			}
		}

		if(bendPoint==false&&lm.getBendpoints().size()>0){//PKY 08052901 S Communication Line Message나오지않는 문제
			//PKY 08071601 S State 저장안되는문제 
			if(propertysCheck==false){
				sb.append("propertys_list [\n");
				propertysCheck=true;
			}
			//PKY 08071601 E State 저장안되는문제 
			sb.append("objects Bendpoints+\n") ;
			for(int i=0;i<lm.getBendpoints().size();i++){
				LineBendpointModel lbm =  (LineBendpointModel)lm.getBendpoints().get(i);
				sb.append("property Bendpoint="+lbm.writeModel()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;

			}
			sb.append("-\n") ;

		}
		//PKY 08071601 S State 저장안되는문제 
		if(propertysCheck==true){
			sb.append("]\n") ;
		}
		//PKY 08071601 E State 저장안되는문제 
		bendPoint=false;//PKY 08052901 E Communication Line Message나오지않는 문제
		sb.append(">\n");
		sb.append(")\n");
		return sb.toString();

	}

	public void writeViews(StringBuffer sb){
		Collection collection = viewStore.values();
		Collection collection1 = this.inModelViewStore.values();
		Object[] objs = collection.toArray();
		Object[] objs2 = collection1.toArray();

		for(int i=0;i<objs2.length;i++){
			Object obj = objs2[i];
			if(obj instanceof NodeContainerModel){
				sb.append(this.writeInModelViewDiagram((NodeContainerModel)obj));
			}
		}
		sb.append("views\n");
		for(int i=0;i<objs.length;i++){
			Object obj = objs[i];
			if(obj instanceof N3EditorDiagramModel){
				sb.append(this.writeDiagram((N3EditorDiagramModel)obj));
			}
		}
		sb.append(";\n");
		viewStore.clear();
	}

	public String writeInModelViewDiagram(NodeContainerModel boundaryModel){
		java.util.HashMap views = new java.util.HashMap();
		StringBuffer sb = new StringBuffer("model");
		UMLModel ump = (UMLModel)boundaryModel.getAcceptParentModel();
		if(ump!=null){//PKY 08051401 S NullPoint 에러 
			//			String type = ProjectManager.getInstance().getDiagramPath(n3EditorDiagramModel.getDiagramType());
			sb.append(" boundary (\n");
			sb.append("model_propertys" +" <\n");

			sb.append("property parentID="+ump.getUMLDataModel().getId()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			for(int i=0;i<boundaryModel.getChildren().size();i++){
				Object obj = boundaryModel.getChildren().get(i);
				if(obj instanceof SubPartitonModel){
					if(i==0){
						sb.append("propertys_list [\n");
						sb.append("objects subPartition +\n");

					}
					SubPartitonModel sm = (SubPartitonModel)obj;
					sb.append(sm.writeModel()+"\n");
					if(i==boundaryModel.getChildren().size()-1){
						sb.append("-\n");
						sb.append("]\n");
					}
				}
			}

			sb.append(">\n");
			sb.append(")\n");
			for(int i=0;i<boundaryModel.getChildren().size();i++){
				Object obj = boundaryModel.getChildren().get(i);
				if(obj instanceof UMLModel){
					UMLModel um = (UMLModel)obj;
					putLinesModel(um);
					//					this.viewStore.put(um.getView_ID(), um);
					this.writeView(um, um.getUMLDataModel().getId(),sb);//PKY 08050701 S Timing Line저장
				}
			}
			sb.append("lines\n");
			for(int i=0;i<viewLines.size();i++){
				Object obj = viewLines.get(i);
				if(obj instanceof LineModel){
					LineModel um = (LineModel)obj;
					sb.append(this.writeLineModel(um));

					//					this.viewStore.put(um.getView_ID(), um);
					//					sb.append(this.writeView(um, n3EditorDiagramModel.getID()));
				}
			}
			viewLines.clear();
			this.doubleCheck.clear();//PKY 08052101 S 타이밍 메시지 저장 후 다시 저장할경우 hash에 담겨있어서 저장쓰기가 불가능함

			return sb.toString();
		}//PKY 08051401 S NullPoint 에러 
		return "";
		//PKY 08051401 E NullPoint 에러 
	}

	//20080811IJS 시작

	public String writeLinkModel(UMLModel um,String path){
		StringBuffer sb = new StringBuffer("");
		if(um instanceof N3EditorDiagramModel){

			this.viewStore.put(um.getUMLDataModel().getId(), um);
		}
		else{
			//			StringBuffer sb = new StringBuffer("model");
			sb.append("model");
			String type = ProjectManager.getInstance().getModelTypeName(ProjectManager.getInstance().getModelType(um, -1));
			if(um instanceof TypeRefModel){
				type= "TypeRefModel";
			}
			else if(um instanceof ClassModel){
				type= "ClassModel";
			}
			sb.append(" "+type +" (\n");
			sb.append("model_propertys" +" <\n");



			sb.append("property Link=true"+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property LinkPath="+path+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID="+um.getUMLDataModel().getId()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;

			sb.append("property ID_NAME="+um.getName()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_STEREOTYPE="+um.getStereotype()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			UMLModel umRef  = (UMLModel)um.getUMLDataModel().getElementProperty("typeRefModel");
			if(umRef!=null){
				sb.append("property REF_ID="+umRef.getUMLDataModel().getId()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			}
			if(um.getUMLTreeModel()!=null && um.getUMLTreeModel().getParent()!=null){
				UMLModel up =(UMLModel)um.getUMLTreeModel().getParent().getRefModel();
				if(up!=null && !(um instanceof ClassModel)){
					sb.append("property parentID="+up.getID()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
				}
			}
			//PKY 08061101 S VForkJoinModel,HForkJoinModel 저장 불러올경우 VForkJoinModel로 작성되는문제
			if( um instanceof VForkJoinModel){
				sb.append("property ID_TYPE="+"VForkJoinModel"+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			}else{
				sb.append("property ID_TYPE="+"HForkJoinModel"+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			}
			//PKY 08061101 S VForkJoinModel,HForkJoinModel 저장 불러올경우 VForkJoinModel로 작성되는문제
			if(um instanceof ClassifierModel){
				ClassifierModel cm = (ClassifierModel)um;
				UMLDataModel umd = cm.getUMLDataModel();
				sb.append(umd.writeModel());
				//PKY 08051401 S 컨테이너 내용이없으면 컨테이너안보이는문제
				if(cm.getUMLDataModel().getProperty("Container")!=null){
					if(cm.getUMLDataModel().getProperty("Container").equals("true"))
						//PKY 08051401 E 컨테이너 내용이없으면 컨테이너안보이는문제

						this.inModelViewStore.put(um.getUMLDataModel().getId(), cm.getBoundaryModel());
				}if(cm.getBoundaryModel().getChildren().size()>0){//PKY 08052101 S 바운더리 안에있는 객체와 밖에있는 객체와 관계선이 안되는문제
					this.inModelViewStore.put(um.getUMLDataModel().getId(), cm.getBoundaryModel());
				}//PKY 08052101 S 바운더리 안에있는 객체와 밖에있는 객체와 관계선이 안되는문제

			}
			//PKY 08051401 S  Actor 이미지 저장안되는문제
			else if(um instanceof FinalActorModel){
				FinalActorModel cm = (FinalActorModel)um;
				//PKY 08060201 S 
				UMLDataModel umd = cm.getUMLDataModel();
				sb.append(umd.writeModel());

				//				if(cm.getUMLDataModel().getProperty("ID_ACTORIMAGE")!=null)
				//				sb.append("property actorImage="+cm.getUMLDataModel().getProperty("ID_ACTORIMAGE")+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
				//				//PKY 08060201 S Actor 디스크립션 들어가지 않는문제 수정
				//				if(cm.getUMLDataModel().getProperty("ID_DESCRIPTION")!=null)
				//				sb.append("property ID_DESCRIPTION="+cm.getUMLDataModel().getProperty("ID_DESCRIPTION")+IN3ModelTokenType.PROPERTY_N3EOF+"\n");
				//PKY 08060201 E Actor 디스크립션 들어가지 않는문제 수정
				//PKY 08060201 E
			}
			//PKY 08051401 E  Actor 이미지 저장안되는문제
			//			else if(um instanceof LifeLineModel){
			//				if(um instanceof LifeLineActorModel){
			//					sb.append("property isActor=true"+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			//				}
			//				else{
			//					sb.append("property isActor=false"+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			//				}
			//			}
			else{
				sb.append(um.getUMLDataModel().writeModel());
			}
			sb.append(">\n");
			sb.append(")\n");

		}
		return sb.toString();

	}
	//20080811IJS 끝

	public String writeDiagram(N3EditorDiagramModel n3EditorDiagramModel){
		System.out.println("ddddddddddddd");
		java.util.HashMap views = new java.util.HashMap();
		StringBuffer sb = new StringBuffer("model");
		String type = ProjectManager.getInstance().getDiagramPath(n3EditorDiagramModel.getDiagramType());
		sb.append(" diagram (\n");
		sb.append("model_propertys" +" <\n");
		if(n3EditorDiagramModel.getUMLTreeModel()!=null && n3EditorDiagramModel.getUMLTreeModel().getParent()!=null){
			UMLModel up =(UMLModel)n3EditorDiagramModel.getUMLTreeModel().getParent().getRefModel();
			if(up!=null ){
				sb.append("property parentID="+up.getID()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			}
		}
		
		sb.append("property ID_DTYPE="+n3EditorDiagramModel.getDtype()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;//PKY 08082201 S 링크 해당모델이 없는 View모델일 경우에도 Name이 표시되도록
		sb.append("property ID="+n3EditorDiagramModel.getUMLDataModel().getId()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
		sb.append("property ID_NAME="+n3EditorDiagramModel.getName()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
		sb.append("property ID_TYPE="+n3EditorDiagramModel.getDiagramType()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
		sb.append("property ID_OPEN="+n3EditorDiagramModel.isOpen()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
		sb.append(">\n");
		sb.append(")\n");
		for(int i=0;i<n3EditorDiagramModel.getChildren().size();i++){
			Object obj = n3EditorDiagramModel.getChildren().get(i);
			if(obj instanceof UMLModel){
				UMLModel um = (UMLModel)obj;
				putLinesModel(um);
				//				this.viewStore.put(um.getView_ID(), um);
				this.writeView(um, n3EditorDiagramModel.getID(),sb);//PKY 08050701 S Timing Line저장
			}
		}
		sb.append("lines\n");
		for(int i=0;i<viewLines.size();i++){
			Object obj = viewLines.get(i);
			if(obj instanceof LineModel){
				LineModel um = (LineModel)obj;
				sb.append(this.writeLineModel(um));

				//				this.viewStore.put(um.getView_ID(), um);
				//				sb.append(this.writeView(um, n3EditorDiagramModel.getID()));
			}
		}


		for(int j=0;j<this.selfMsgs.size();j++){
			Object obj = selfMsgs.get(j);
			//			if(obj instanceof SelfMessageModel){
			//				SelfMessageModel um = (SelfMessageModel)obj;
			//				sb.append(um.writeModel());
			//			}
		}
		viewLines.clear();
		this.doubleCheck.clear(); //PKY 08052101 S 타이밍 메시지 저장 후 다시 저장할경우 hash에 담겨있어서 저장쓰기가 불가능함

		this.selfMsgs.clear();


		return sb.toString();

	}

	public String writeView(UMLModel um,String parentId,StringBuffer sb){//PKY 08050701 S Timing Line저장
		String type = ProjectManager.getInstance().getModelTypeName(ProjectManager.getInstance().getModelType(um, -1));
		//		StringBuffer sb = new StringBuffer();

		if( um instanceof UMLNoteModel){
			UMLNoteModel UMLNoteModel=(UMLNoteModel)um;
			sb.append("model");
			sb.append(" "+"Note" +" (\n");
			sb.append("model_propertys" +" <\n");
			sb.append("property Text="+UMLNoteModel.getLabelContents()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property View_ID="+um.getView_ID()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID="+um.getUMLDataModel().getId()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property parentID="+parentId+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_SIZE="+um.getSize().width+","+um.getSize().height+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_LOCATION="+um.getLocation().x+","+um.getLocation().y+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_COLOR="+um.getBackGroundColor().getRed()+","+um.getBackGroundColor().getGreen()+","+um.getBackGroundColor().getBlue()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append(">\n");
			sb.append(")\n");
		}
		//2008043001 PKY E Note
		//PKY 08051401 S 시퀀스 FragmentModel저장안되는것 
		//		else if( um instanceof FragmentModel){
		//			FragmentModel fragmentModel=(FragmentModel)um;
		//			sb.append("model");
		//			sb.append(" "+"Fragment" +" (\n");
		//			sb.append("model_propertys" +" <\n");
		//			sb.append("property View_ID="+um.getView_ID()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
		//			sb.append("property ID="+um.getUMLDataModel().getId()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
		//			sb.append("property parentID="+parentId+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
		//			sb.append("property ID_SIZE="+um.getSize().width+","+um.getSize().height+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
		//			sb.append("property ID_LOCATION="+um.getLocation().x+","+um.getLocation().y+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
		//			sb.append("property ID_COLOR="+um.getBackGroundColor().getRed()+","+um.getBackGroundColor().getGreen()+","+um.getBackGroundColor().getBlue()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
		//			sb.append("property Name="+fragmentModel.getFragmentName()+IN3ModelTokenType.PROPERTY_N3EOF+"\n");
		//			sb.append("property Type="+fragmentModel.getType()+IN3ModelTokenType.PROPERTY_N3EOF+"\n");
		//			sb.append("property ParentLayout="+fragmentModel.getParentLayout()+IN3ModelTokenType.PROPERTY_N3EOF+"\n");
		//			sb.append("propertys_list [\n") ;
		//			sb.append("objects Conditions+\n") ;
		//			java.util.ArrayList list=fragmentModel.getFragmentCondition();
		//			for(int j=0;j<list.size();j++){
		//				sb.append("property Condition="+list.get(j)+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
		//			}
		//			sb.append("-\n") ;
		//			sb.append("]\n") ;
		//			sb.append(">\n");
		//			sb.append(")\n");			
		//		}
		//PKY 08051401 E 시퀀스 FragmentModel저장안되는것 

		//PKY 08052601 S endPoint저장안됨
		//		else if ( um instanceof EndPointModel){
		//			EndPointModel UMLNoteModel=(EndPointModel)um;
		//			sb.append("model");
		//			sb.append(" "+"EndPoint" +" (\n");
		//			sb.append("model_propertys" +" <\n");
		//			sb.append("property View_ID="+um.getView_ID()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
		//			sb.append("property ID="+um.getUMLDataModel().getId()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
		//			sb.append("property parentID="+parentId+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
		//			sb.append("property ID_SIZE="+um.getSize().width+","+um.getSize().height+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
		//			sb.append("property ID_LOCATION="+um.getLocation().x+","+um.getLocation().y+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
		//			sb.append("property ID_COLOR="+um.getBackGroundColor().getRed()+","+um.getBackGroundColor().getGreen()+","+um.getBackGroundColor().getBlue()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
		//			sb.append(">\n");
		//			sb.append(")\n");
		//		}
		//PKY 08052601 E endPoint저장안됨

		
		//2008043001 PKY E Note
		//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
		else if( um instanceof FinalBoundryModel){
			FinalBoundryModel UMLNoteModel=(FinalBoundryModel)um;
			String ViewId=um.getView_ID();
			sb.append("model");
			sb.append(" "+"Boundary" +" (\n");
			sb.append("model_propertys" +" <\n");
			sb.append("property View_ID="+um.getView_ID()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID="+um.getUMLDataModel().getId()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property parentID="+parentId+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_SIZE="+um.getSize().width+","+um.getSize().height+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_LOCATION="+um.getLocation().x+","+um.getLocation().y+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_COLOR="+um.getBackGroundColor().getRed()+","+um.getBackGroundColor().getGreen()+","+um.getBackGroundColor().getBlue()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_NAME="+um.getName()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			if(UMLNoteModel.getUMLDataModel()!=null){
				UMLDataModel umd=UMLNoteModel.getUMLDataModel();
				sb.append(umd.writeViewModel());
			}
			sb.append(">\n");
			sb.append(")\n");
		}
		else if( um instanceof HPartitionModel){
			HPartitionModel UMLNoteModel=(HPartitionModel)um;
			String ViewId=um.getView_ID();
			sb.append("model");
			sb.append(" "+"Swimlane" +" (\n");
			sb.append("model_propertys" +" <\n");
			sb.append("property View_ID="+um.getView_ID()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID="+um.getUMLDataModel().getId()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property parentID="+parentId+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_SIZE="+um.getSize().width+","+um.getSize().height+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_LOCATION="+um.getLocation().x+","+um.getLocation().y+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_COLOR="+um.getBackGroundColor().getRed()+","+um.getBackGroundColor().getGreen()+","+um.getBackGroundColor().getBlue()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_NAME="+um.getName()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_STEREOTYPE="+um.getStereotype()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			if(UMLNoteModel.getUMLDataModel()!=null){
				UMLDataModel umd=UMLNoteModel.getUMLDataModel();
				sb.append(umd.writeViewModel());
			}
			sb.append(">\n");
			sb.append(")\n");
		}
		else if( um instanceof SynchModel){
			SynchModel model=(SynchModel)um;
			String ViewId=um.getView_ID();
			sb.append("model");
			sb.append(" "+"Synch" +" (\n");
			sb.append("model_propertys" +" <\n");
			sb.append("property View_ID="+um.getView_ID()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID="+um.getUMLDataModel().getId()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property parentID="+parentId+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_SIZE="+um.getSize().width+","+um.getSize().height+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_LOCATION="+um.getLocation().x+","+um.getLocation().y+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_COLOR="+um.getBackGroundColor().getRed()+","+um.getBackGroundColor().getGreen()+","+um.getBackGroundColor().getBlue()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_NAME="+um.getName()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_STEREOTYPE="+um.getStereotype()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			if(model.getUMLDataModel()!=null){
				UMLDataModel umd=model.getUMLDataModel();
				sb.append(umd.writeViewModel());
			}
			sb.append(">\n");
			sb.append(")\n");
		}
		else if( um instanceof InitialModel){
			InitialModel model=(InitialModel)um;
			String ViewId=um.getView_ID();
			sb.append("model");
			sb.append(" "+"Initial" +" (\n");
			sb.append("model_propertys" +" <\n");
			sb.append("property View_ID="+um.getView_ID()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID="+um.getUMLDataModel().getId()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property parentID="+parentId+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_SIZE="+um.getSize().width+","+um.getSize().height+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_LOCATION="+um.getLocation().x+","+um.getLocation().y+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_COLOR="+um.getBackGroundColor().getRed()+","+um.getBackGroundColor().getGreen()+","+um.getBackGroundColor().getBlue()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_NAME="+um.getName()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_STEREOTYPE="+um.getStereotype()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			if(model.getUMLDataModel()!=null){
				UMLDataModel umd=model.getUMLDataModel();
				sb.append(umd.writeViewModel());
			}
			sb.append(">\n");
			sb.append(")\n");
		}
		else if( um instanceof FinalModel){
			FinalModel model=(FinalModel)um;
			String ViewId=um.getView_ID();
			sb.append("model");
			sb.append(" "+"Final" +" (\n");
			sb.append("model_propertys" +" <\n");
			sb.append("property View_ID="+um.getView_ID()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID="+um.getUMLDataModel().getId()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property parentID="+parentId+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_SIZE="+um.getSize().width+","+um.getSize().height+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_LOCATION="+um.getLocation().x+","+um.getLocation().y+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_COLOR="+um.getBackGroundColor().getRed()+","+um.getBackGroundColor().getGreen()+","+um.getBackGroundColor().getBlue()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_NAME="+um.getName()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_STEREOTYPE="+um.getStereotype()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			if(model.getUMLDataModel()!=null){
				UMLDataModel umd=model.getUMLDataModel();
				sb.append(umd.writeViewModel());
			}
			sb.append(">\n");
			sb.append(")\n");
		}
		else if( um instanceof FlowFinalModel){
			FlowFinalModel model=(FlowFinalModel)um;
			String ViewId=um.getView_ID();
			sb.append("model");
			sb.append(" "+"Flow_final" +" (\n");
			sb.append("model_propertys" +" <\n");
			sb.append("property View_ID="+um.getView_ID()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID="+um.getUMLDataModel().getId()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property parentID="+parentId+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_SIZE="+um.getSize().width+","+um.getSize().height+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_LOCATION="+um.getLocation().x+","+um.getLocation().y+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_COLOR="+um.getBackGroundColor().getRed()+","+um.getBackGroundColor().getGreen()+","+um.getBackGroundColor().getBlue()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_NAME="+um.getName()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_MULTIPLICITY="+um.getMultiplicity()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_STEREOTYPE="+um.getStereotype()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			if(model.getUMLDataModel()!=null){
				UMLDataModel umd=model.getUMLDataModel();
				sb.append(umd.writeViewModel());
			}
			sb.append(">\n");
			sb.append(")\n");
		}
		else if( um instanceof HForkJoinModel){
			HForkJoinModel model=(HForkJoinModel)um;
			String ViewId=um.getView_ID();
			sb.append("model");
			sb.append(" "+"HForkJoin" +" (\n");
			sb.append("model_propertys" +" <\n");
			sb.append("property View_ID="+um.getView_ID()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID="+um.getUMLDataModel().getId()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property parentID="+parentId+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_SIZE="+um.getSize().width+","+um.getSize().height+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_LOCATION="+um.getLocation().x+","+um.getLocation().y+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_COLOR="+um.getBackGroundColor().getRed()+","+um.getBackGroundColor().getGreen()+","+um.getBackGroundColor().getBlue()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_NAME="+um.getName()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_STEREOTYPE="+um.getStereotype()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			if(model.getUMLDataModel()!=null){
				UMLDataModel umd=model.getUMLDataModel();
				sb.append(umd.writeViewModel());
			}
			sb.append(">\n");
			sb.append(")\n");
		}
		else if( um instanceof VForkJoinModel){
			VForkJoinModel model=(VForkJoinModel)um;
			String ViewId=um.getView_ID();
			sb.append("model");
			sb.append(" "+"VForkJoin" +" (\n");
			sb.append("model_propertys" +" <\n");
			sb.append("property View_ID="+um.getView_ID()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID="+um.getUMLDataModel().getId()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property parentID="+parentId+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_SIZE="+um.getSize().width+","+um.getSize().height+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_LOCATION="+um.getLocation().x+","+um.getLocation().y+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_COLOR="+um.getBackGroundColor().getRed()+","+um.getBackGroundColor().getGreen()+","+um.getBackGroundColor().getBlue()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_NAME="+um.getName()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_STEREOTYPE="+um.getStereotype()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			if(model.getUMLDataModel()!=null){
				UMLDataModel umd=model.getUMLDataModel();
				sb.append(umd.writeViewModel());
			}
			sb.append(">\n");
			sb.append(")\n");
		}

		else if( um instanceof DecisionModel){
			DecisionModel model=(DecisionModel)um;
			String ViewId=um.getView_ID();
			sb.append("model");
			sb.append(" "+"Decision" +" (\n");
			sb.append("model_propertys" +" <\n");
			sb.append("property View_ID="+um.getView_ID()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID="+um.getUMLDataModel().getId()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property parentID="+parentId+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_SIZE="+um.getSize().width+","+um.getSize().height+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_LOCATION="+um.getLocation().x+","+um.getLocation().y+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_COLOR="+um.getBackGroundColor().getRed()+","+um.getBackGroundColor().getGreen()+","+um.getBackGroundColor().getBlue()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_NAME="+um.getName()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_STEREOTYPE="+um.getStereotype()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			if(model.getUMLDataModel()!=null){
				UMLDataModel umd=model.getUMLDataModel();
				sb.append(umd.writeViewModel());
			}
			sb.append(">\n");
			sb.append(")\n");
		}


		//PKY 08052601 S Partition 저장 불러올시 해제되는문제 
		//PKY 08080501 S Frame 저장이 안되는 문제 
		else if( um instanceof SubPartitonModel){
			SubPartitonModel partition=(SubPartitonModel)um;
			String ViewId=um.getView_ID();
			sb.append("model");
			sb.append(" "+"partiton" +" (\n");
			sb.append("model_propertys" +" <\n");
			sb.append("property View_ID="+um.getView_ID()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID="+um.getUMLDataModel().getId()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property parentID="+parentId+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property parentActivity="+um.getParentModel().getView_ID()+IN3ModelTokenType.PROPERTY_N3EOF+"\n");//PKY 08081801 S 엑티비티 파티션 움직임이 비정상적인 문제
			sb.append("property ID_SIZE="+um.getSize().width+","+um.getSize().height+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_LOCATION="+um.getLocation().x+","+um.getLocation().y+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_COLOR="+um.getBackGroundColor().getRed()+","+um.getBackGroundColor().getGreen()+","+um.getBackGroundColor().getBlue()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_NAME="+partition.getUMLDataModel().getElementProperty("NAME")+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;//PKY 08052601 S 파티션 이름 저장되지 않는문제 수정
			if(um.getUMLDataModel()!=null){
				UMLDataModel umd=um.getUMLDataModel();
				sb.append(umd.writeViewModel());
			}
			sb.append(">\n");
			sb.append(")\n");

		}
		//PKY 08072201 E 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint  
		else if( um instanceof FrameModel){
			FrameModel partition=(FrameModel)um;
			String ViewId=um.getView_ID();
			sb.append("model");
			sb.append(" "+"frame" +" (\n");
			sb.append("model_propertys" +" <\n");
			sb.append("property View_ID="+um.getView_ID()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID="+um.getUMLDataModel().getId()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property parentID="+parentId+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_SIZE="+um.getSize().width+","+um.getSize().height+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_LOCATION="+um.getLocation().x+","+um.getLocation().y+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_COLOR="+um.getBackGroundColor().getRed()+","+um.getBackGroundColor().getGreen()+","+um.getBackGroundColor().getBlue()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property parentDiagramID="+partition.getN3EditorDiagramModel().getID()+IN3ModelTokenType.PROPERTY_N3EOF+"\n");
			sb.append("property ID_NAME="+partition.getFragmentName()+IN3ModelTokenType.PROPERTY_N3EOF+"\n");//PKY 08082201 S 다이어그램 Frame Link에서 에러발생
			sb.append("property ID_TYPE="+partition.getType()+IN3ModelTokenType.PROPERTY_N3EOF+"\n");//PKY 08082201 S 다이어그램 Frame Link에서 에러발생
			if(um.getUMLDataModel()!=null){
				UMLDataModel umd=um.getUMLDataModel();
				sb.append(umd.writeViewModel());
			}
			sb.append(">\n");
			sb.append(")\n");
		}
		//PKY 08080501 E Frame 저장이 안되는 문제   
		//PKY 08081101 S Timing 구조 변경
		
		//PKY 08081101 E Timing 구조 변경

		else if(type!=null && !type.trim().equals("")){
			sb.append("model");
			sb.append(" "+type +" (\n");
			sb.append("model_propertys" +" <\n");
			sb.append("property View_ID="+um.getView_ID()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID="+um.getUMLDataModel().getId()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			if(um instanceof ComponentModel){
				ComponentModel cm = (ComponentModel)um;
				sb.append("property NODE_ID="+cm.getNodeItemModelId()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
				if(!"".equals(cm.getPackage()))
				sb.append("property FULL_NAME="+cm.getPackage()+"."+cm.getName()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
				else
					sb.append("property FULL_NAME="+cm.getName()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
				
				
			}
			
			sb.append("property ID_NAME="+um.getName()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;//PKY 08082201 S 링크 해당모델이 없는 View모델일 경우에도 Name이 표시되도록
			sb.append("property parentID="+parentId+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_SIZE="+um.getSize().width+","+um.getSize().height+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_LOCATION="+um.getLocation().x+","+um.getLocation().y+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_COLOR="+um.getBackGroundColor().getRed()+","+um.getBackGroundColor().getGreen()+","+um.getBackGroundColor().getBlue()+IN3ModelTokenType.PROPERTY_N3EOF+"\n");



			//PKY 08051401 S 컨테이너가 포함된 객체 저장 불러올경우 컨테이너를 두번 추가할 수 있는 문제
			if(um instanceof ClassifierModel){			 
				sb.append("property Container="+((ClassifierModel)um).isContainer()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			}
			//PKY 08051401 E 컨테이너가 포함된 객체 저장 불러올경우 컨테이너를 두번 추가할 수 있는 문제
			//PKY 08050701 S Timing Line저장

			//PKY 08061101 S StateLifelineModel,Time표시되는 형태를 저장하여 불러와두 동일한 형태로 적용되도록 수정
			
			//PKY 08061101 E StateLifelineModel,Time표시되는 형태를 저장하여 불러와두 동일한 형태로 적용되도록 수정
			//PKY 08081101 S Timing 구조 변경
			//			if(um instanceof StateLifelineModel){
			//			StateLifelineModel cm = (StateLifelineModel)um;
			//			if(cm.getBoundaryModel()!=null){
			//			BoundaryModel boundary=(BoundaryModel)cm.getBoundaryModel();
			//			java.util.List list=boundary.getChildren();
			//			sb.append("propertys_list [\n") ;
			//			sb.append("objects Point+\n") ;
			//			for(int i=0;i<list.size();i++){
			//			if(list.get(i) instanceof MessagePointModel){
			//			MessagePointModel msg= (MessagePointModel)list.get(i);
			//			sb.append("property messagePoint="+
			//			msg.getParentLayout()+","+
			//			msg.getTime()+","+
			//			msg.getIndex()+","+	
			//			msg.getLocation().x+","+	
			//			msg.getLocation().y+","+	
			//			msg.getView_ID()										
			//			+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			//			}						 
			//			}
			//			sb.append("-\n");
			//			sb.append("]\n");

			//			}
			//			}//PKY 08050701 E Timing Line저장
			//PKY 08081101 E Timing 구조 변경

			if(um instanceof PortContainerModel){
				PortContainerModel cm = (PortContainerModel)um;
				if(cm.getPorts().size()>0){
					sb.append("propertys_list [\n") ;
					sb.append("objects ports+\n") ;
					for(int i=0;i<cm.getPorts().size();i++){
						Object obj = cm.getPorts().get(i);
						//PKY 08061101 S 포트와 관련된 저장 불러오기 로직 보완작업
						if(obj instanceof ActivityParameterModel){
							PortModel pm = (PortModel)cm.getPorts().get(i);
							sb.append("property activityParamterport="+pm.getLocation().x+","+pm.getLocation().y+","+pm.getSize().width+","+pm.getSize().height+","+pm.getPtDifference().width+","+pm.getPtDifference().height+","+pm.view_ID+","+pm.getName()+","+pm.getScope()+","+pm.getStereotype()+","+pm.getAttachElementLabelModel().getSize().width+","+pm.getAttachElementLabelModel().getSize().height+","+pm.getBackGroundColor().getRed()+","+pm.getBackGroundColor().getGreen()+","+pm.getBackGroundColor().getBlue()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
						}
						else if(obj instanceof ExpansionNodeModel){
							PortModel pm = (PortModel)cm.getPorts().get(i);
							sb.append("property expansionNodeport="+pm.getLocation().x+","+pm.getLocation().y+","+pm.getSize().width+","+pm.getSize().height+","+pm.getPtDifference().width+","+pm.getPtDifference().height+","+pm.view_ID+","+pm.getName()+","+pm.getScope()+","+pm.getStereotype()+","+pm.getAttachElementLabelModel().getSize().width+","+pm.getAttachElementLabelModel().getSize().height+","+pm.getBackGroundColor().getRed()+","+pm.getBackGroundColor().getGreen()+","+pm.getBackGroundColor().getBlue()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
						}
						else if(obj instanceof ObjectPortModel){
							PortModel pm = (PortModel)cm.getPorts().get(i);
							sb.append("property objectPortModel="+pm.getLocation().x+","+pm.getLocation().y+","+pm.getSize().width+","+pm.getSize().height+","+pm.getPtDifference().width+","+pm.getPtDifference().height+","+pm.view_ID+","+pm.getName()+","+pm.getScope()+","+pm.getStereotype()+","+pm.getAttachElementLabelModel().getSize().width+","+pm.getAttachElementLabelModel().getSize().height+","+pm.getBackGroundColor().getRed()+","+pm.getBackGroundColor().getGreen()+","+pm.getBackGroundColor().getBlue()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
						}
						else if(obj instanceof ExitPointModel){
							PortModel pm = (PortModel)cm.getPorts().get(i);
							sb.append("property exitPointModel="+pm.getLocation().x+","+pm.getLocation().y+","+pm.getSize().width+","+pm.getSize().height+","+pm.getPtDifference().width+","+pm.getPtDifference().height+","+pm.view_ID+","+pm.getName()+","+pm.getScope()+","+pm.getStereotype()+","+pm.getAttachElementLabelModel().getSize().width+","+pm.getAttachElementLabelModel().getSize().height+","+pm.getBackGroundColor().getRed()+","+pm.getBackGroundColor().getGreen()+","+pm.getBackGroundColor().getBlue()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
						}
						else if(obj instanceof EntryPointModel){
							PortModel pm = (PortModel)cm.getPorts().get(i);
							sb.append("property entryPointModel="+pm.getLocation().x+","+pm.getLocation().y+","+pm.getSize().width+","+pm.getSize().height+","+pm.getPtDifference().width+","+pm.getPtDifference().height+","+pm.view_ID+","+pm.getName()+","+pm.getScope()+","+pm.getStereotype()+","+pm.getAttachElementLabelModel().getSize().width+","+pm.getAttachElementLabelModel().getSize().height+","+pm.getBackGroundColor().getRed()+","+pm.getBackGroundColor().getGreen()+","+pm.getBackGroundColor().getBlue()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
						}
						else if(obj instanceof MonitoringMethodPortModel){
							PortModel pm = (PortModel)cm.getPorts().get(i);
							sb.append("property MonitoringMethodPortModel="+pm.getLocation().x+","+pm.getLocation().y+","+pm.getSize().width+","+pm.getSize().height+","+pm.getPtDifference().width+","+pm.getPtDifference().height+","+pm.view_ID+","+pm.getName()+","+pm.getScope()+","+pm.getStereotype()+","+pm.getAttachElementLabelModel().getSize().width+","+pm.getAttachElementLabelModel().getSize().height+","+pm.getBackGroundColor().getRed()+","+pm.getBackGroundColor().getGreen()+","+pm.getBackGroundColor().getBlue()+","+pm.getUMLDataModel().getId()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;

						}
						else if(obj instanceof LifecycleMethodPortModel){
							PortModel pm = (PortModel)cm.getPorts().get(i);
							sb.append("property LifecycleMethodPortModel="+pm.getLocation().x+","+pm.getLocation().y+","+pm.getSize().width+","+pm.getSize().height+","+pm.getPtDifference().width+","+pm.getPtDifference().height+","+pm.view_ID+","+pm.getName()+","+pm.getScope()+","+pm.getStereotype()+","+pm.getAttachElementLabelModel().getSize().width+","+pm.getAttachElementLabelModel().getSize().height+","+pm.getBackGroundColor().getRed()+","+pm.getBackGroundColor().getGreen()+","+pm.getBackGroundColor().getBlue()+","+pm.getUMLDataModel().getId()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
						}
						else if(obj instanceof MethodOutputPortModel){
							PortModel pm = (PortModel)cm.getPorts().get(i);
//							sb.append("property MethodOutputPortModel="+pm.getLocation().x+","+pm.getLocation().y+","+pm.getSize().width+","+pm.getSize().height+","+pm.getPtDifference().width+","+pm.getPtDifference().height+","+pm.view_ID+","+pm.getName()+","+pm.getScope()+","+pm.getStereotype()+","+pm.getAttachElementLabelModel().getSize().width+","+pm.getAttachElementLabelModel().getSize().height+","+pm.getBackGroundColor().getRed()+","+pm.getBackGroundColor().getGreen()+","+pm.getBackGroundColor().getBlue()+","+pm.getUMLDataModel().getId()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;

							sb.append("property MethodOutputPortModel="+pm.getLocation().x+","+pm.getLocation().y+
									","+pm.getSize().width+","+pm.getSize().height
									+","+pm.getPtDifference().width+","+pm.getPtDifference().height
									+","+pm.view_ID+","+pm.getName()+","
									+pm.getScope()+","+pm.getStereotype()
									+","+pm.getAttachElementLabelModel().getSize().width
									+","+pm.getAttachElementLabelModel().getSize().height
									+","+pm.getTypeRef()+","+pm.getDesc()
									+","+pm.getType()+","+pm.getUMLDataModel().getId()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
						}
						else if(obj instanceof MethodInputPortModel){
							PortModel pm = (PortModel)cm.getPorts().get(i);
//							sb.append("property MethodInputPortModel="+pm.getLocation().x+","+pm.getLocation().y+","+pm.getSize().width+","+pm.getSize().height+","+pm.getPtDifference().width+","+pm.getPtDifference().height+","+pm.view_ID+","+pm.getName()+","+pm.getScope()+","+pm.getStereotype()+","+pm.getAttachElementLabelModel().getSize().width+","+pm.getAttachElementLabelModel().getSize().height+","+pm.getBackGroundColor().getRed()+","+pm.getBackGroundColor().getGreen()+","+pm.getBackGroundColor().getBlue()+","+pm.getUMLDataModel().getId()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
							sb.append("property MethodInputPortModel="+pm.getLocation().x+","+pm.getLocation().y+","+pm.getSize().width+","+pm.getSize().height+","+pm.getPtDifference().width+","+pm.getPtDifference().height+","+pm.view_ID+","+pm.getName()+","+pm.getScope()+","+pm.getStereotype()+","+pm.getAttachElementLabelModel().getSize().width+","+pm.getAttachElementLabelModel().getSize().height+","+pm.getTypeRef()+","+pm.getDesc()+","+pm.getType()+","+pm.getUMLDataModel().getId()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
						}
						else if(obj instanceof DataInputPortModel){
							PortModel pm = (PortModel)obj;
//							sb.append("property DataInputPortModel="+pm.getLocation().x+","+pm.getLocation().y+","+pm.getSize().width+","+pm.getSize().height+","+pm.getPtDifference().width+","+pm.getPtDifference().height+","+pm.view_ID+","+pm.getName()+","+pm.getScope()+","+pm.getStereotype()+","+pm.getAttachElementLabelModel().getSize().width+","+pm.getAttachElementLabelModel().getSize().height+","+pm.getBackGroundColor().getRed()+","+pm.getBackGroundColor().getGreen()+","+pm.getBackGroundColor().getBlue()+","+pm.getUMLDataModel().getId()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
							sb.append("property DataInputPortModel="+pm.getLocation().x+","+pm.getLocation().y+","+pm.getSize().width+","+pm.getSize().height+","+pm.getPtDifference().width+","+pm.getPtDifference().height+","+pm.view_ID+","+pm.getName()+","+pm.getScope()+","+pm.getStereotype()+","+pm.getAttachElementLabelModel().getSize().width+","+pm.getAttachElementLabelModel().getSize().height+","+pm.getTypeRef()+","+pm.getDesc()+","+pm.getType()+","+pm.getQueueingPolicy()+","+pm.getQueueSize()+","+pm.getUMLDataModel().getId()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
						}
						else if(obj instanceof DataOutputPortModel){
							PortModel pm = (PortModel)cm.getPorts().get(i);
//							sb.append("property DataOutputPortModel="+pm.getLocation().x+","+pm.getLocation().y+","+pm.getSize().width+","+pm.getSize().height+","+pm.getPtDifference().width+","+pm.getPtDifference().height+","+pm.view_ID+","+pm.getName()+","+pm.getScope()+","+pm.getStereotype()+","+pm.getAttachElementLabelModel().getSize().width+","+pm.getAttachElementLabelModel().getSize().height+","+pm.getBackGroundColor().getRed()+","+pm.getBackGroundColor().getGreen()+","+pm.getBackGroundColor().getBlue()+","+pm.getUMLDataModel().getId()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
							sb.append("property DataOutputPortModel="+pm.getLocation().x+","+pm.getLocation().y+","+pm.getSize().width+","+pm.getSize().height+","+pm.getPtDifference().width+","+pm.getPtDifference().height+","+pm.view_ID+","+pm.getName()+","+pm.getScope()+","+pm.getStereotype()+","+pm.getAttachElementLabelModel().getSize().width+","+pm.getAttachElementLabelModel().getSize().height+","+pm.getTypeRef()+","+pm.getDesc()+","+pm.getType()+","+pm.getQueueingPolicy()+","+pm.getQueueSize()+","+pm.getUMLDataModel().getId()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
						}
						else if(obj instanceof EventInputPortModel){
							PortModel pm = (PortModel)cm.getPorts().get(i);
//							sb.append("property EventInputPortModel="+pm.getLocation().x+","+pm.getLocation().y+","+pm.getSize().width+","+pm.getSize().height+","+pm.getPtDifference().width+","+pm.getPtDifference().height+","+pm.view_ID+","+pm.getName()+","+pm.getScope()+","+pm.getStereotype()+","+pm.getAttachElementLabelModel().getSize().width+","+pm.getAttachElementLabelModel().getSize().height+","+pm.getBackGroundColor().getRed()+","+pm.getBackGroundColor().getGreen()+","+pm.getBackGroundColor().getBlue()+","+pm.getUMLDataModel().getId()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
							sb.append("property EventInputPortModel="+pm.getLocation().x+","+pm.getLocation().y+","+pm.getSize().width+","+pm.getSize().height+","+pm.getPtDifference().width+","+pm.getPtDifference().height+","+pm.view_ID+","+pm.getName()+","+pm.getScope()+","+pm.getStereotype()+","+pm.getAttachElementLabelModel().getSize().width+","+pm.getAttachElementLabelModel().getSize().height+","+pm.getDesc()+","+pm.getType()+","+pm.getUMLDataModel().getId()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
						}
						else if(obj instanceof EventOutputPortModel){
							PortModel pm = (PortModel)cm.getPorts().get(i);
//							sb.append("property EventOutputPortModel="+pm.getLocation().x+","+pm.getLocation().y+","+pm.getSize().width+","+pm.getSize().height+","+pm.getPtDifference().width+","+pm.getPtDifference().height+","+pm.view_ID+","+pm.getName()+","+pm.getScope()+","+pm.getStereotype()+","+pm.getAttachElementLabelModel().getSize().width+","+pm.getAttachElementLabelModel().getSize().height+","+pm.getBackGroundColor().getRed()+","+pm.getBackGroundColor().getGreen()+","+pm.getBackGroundColor().getBlue()+","+pm.getUMLDataModel().getId()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
							sb.append("property EventOutputPortModel="+pm.getLocation().x+","+pm.getLocation().y+","+pm.getSize().width+","+pm.getSize().height+","+pm.getPtDifference().width+","+pm.getPtDifference().height+","+pm.view_ID+","+pm.getName()+","+pm.getScope()+","+pm.getStereotype()+","+pm.getAttachElementLabelModel().getSize().width+","+pm.getAttachElementLabelModel().getSize().height+","+pm.getDesc()+","+pm.getType()+","+pm.getUMLDataModel().getId()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
						}

						else{
							PortModel pm = (PortModel)cm.getPorts().get(i);
							//							System.out.print(pm.getLocation().x);
							//							System.out.print(pm.getLocation().y);
							//							System.out.print(pm.getSize().width);
							//							System.out.print(pm.getSize().height);
							//							System.out.print(pm.getPtDifference().width);
							//							System.out.print(pm.getPtDifference().height);
							//							System.out.print(pm.view_ID);
							//							System.out.print(pm.getName());
							//							System.out.print(pm.getScope());
							//							System.out.print(pm.getStereotype());
							//							System.out.print(pm.getAttachElementLabelModel().getSize().width);
							//							System.out.print(pm.getAttachElementLabelModel().getSize().height);
							//							System.out.print(pm.getBackGroundColor().getRed());
							sb.append("property port="+pm.getLocation().x+","+pm.getLocation().y+","+pm.getSize().width+","+pm.getSize().height+","+pm.getPtDifference().width+","+pm.getPtDifference().height+","+pm.view_ID+","+pm.getName()+","+pm.getScope()+","+pm.getStereotype()+","+pm.getAttachElementLabelModel().getSize().width+","+pm.getAttachElementLabelModel().getSize().height+","+pm.getBackGroundColor().getRed()+","+pm.getBackGroundColor().getGreen()+","+pm.getBackGroundColor().getBlue()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
						}

						//PKY 08061101 E 포트와 관련된 저장 불러오기 로직 보완작업
					}
					sb.append("-\n") ;
					sb.append("]\n") ;
				}
			}


			sb.append(">\n");
			sb.append(")\n");
		}
		return sb.toString();
	}


	public String writeModel(UMLModel um,String id){
		StringBuffer sb = new StringBuffer("");
		if(um instanceof AtomicComponentModel){
			AtomicComponentModel am = (AtomicComponentModel)um;
			if(am.getCoreUMLTreeModel()==null){
				AtomicComponentModel am1  = am.getCoreDiagramCmpModel();
				String path = am.getCmpFolder();
				am1.setCmpFolder(path);
				am1.writeProFile();
			}
		}
		if(um instanceof N3EditorDiagramModel){
			this.viewStore.put(um.getUMLDataModel().getId(), um);
		}
		else{
			//			StringBuffer sb = new StringBuffer("model");
			String type = ProjectManager.getInstance().getModelTypeName(ProjectManager.getInstance().getModelType(um, -1));
			if(type==null || type.trim().equals(""))
				return sb.toString();
			sb.append("model");
			//			String type = ProjectManager.getInstance().getModelTypeName(ProjectManager.getInstance().getModelType(um, -1));
			if(um instanceof TypeRefModel){
				type= "TypeRefModel";
			}
			else if(um instanceof ClassModel){
				type= "ClassModel";
			}
			if("Component".equals(type)){
				ComponentModel cm = (ComponentModel)um;
				
				if(cm.isInstance){
					if(cm.getInstanceUMLTreeModel()!=null){
						cm.setInstanceUMLTreeModel(cm.getInstanceUMLTreeModel());
					}
					
				}
			}
			
			sb.append(" "+type +" (\n");
			sb.append("model_propertys" +" <\n");
			if(id==null){
				sb.append("property ID="+um.getUMLDataModel().getId()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			}
			else{
				sb.append("property ID="+id+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			}
			sb.append("property ID_NAME="+um.getName()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			sb.append("property ID_STEREOTYPE="+um.getStereotype()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
//			if("")
			
			UMLModel umRef  = (UMLModel)um.getUMLDataModel().getElementProperty("typeRefModel");
			if(umRef!=null){
				sb.append("property REF_ID="+umRef.getUMLDataModel().getId()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			}
			if(um.getUMLTreeModel()!=null && um.getUMLTreeModel().getParent()!=null){
				UMLModel up =(UMLModel)um.getUMLTreeModel().getParent().getRefModel();
				if(up!=null && !(um instanceof ClassModel)){
					sb.append("property parentID="+up.getID()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
				}
			}
			//PKY 08061101 S VForkJoinModel,HForkJoinModel 저장 불러올경우 VForkJoinModel로 작성되는문제
			if( um instanceof VForkJoinModel){
				sb.append("property ID_TYPE="+"VForkJoinModel"+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			}else{
				if(um instanceof NodeRootModel){
					sb.append("property ID_TYPE="+"NodeRootModel"+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
				}
				else if(um instanceof NodePackageModel){
					sb.append("property ID_TYPE="+"NodePackageModel"+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
				}
				else if(um instanceof AtomicComponentModel){
					AtomicComponentModel cm = (AtomicComponentModel)um;
					sb.append("property ID_TYPE="+"AtomicComponentModel"+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
					OPRoSExeSemanticsElementModel sm = (OPRoSExeSemanticsElementModel)cm.getClassModel().getUMLDataModel().getElementProperty("OPRoSExeSemanticsElementModel");
					OPRoSExeEnvironmentElementModel am = (OPRoSExeEnvironmentElementModel)cm.getClassModel().getUMLDataModel().getElementProperty("OPRoSExeEnvironmentElementModel");
					if(cm.getCoreUMLTreeModel()==null){
						
						sb.append("property CORE_ID=CORE"+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
					}
					else{
						UMLTreeModel ut = cm.getCoreUMLTreeModel();
						UMLModel um1 = (UMLModel)ut.getRefModel();
						sb.append("property CORE_ID="+um1.getUMLDataModel().getId()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
					}
					if(sm!=null){
						sb.append("property EXE_TYPE="+sm.getexeTypeProp()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
						sb.append("property EXE_PERIOD="+sm.getexePeriodProp()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
						sb.append("property EXE_PRIORITY="+sm.getexePriorityProp()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
						sb.append("property EXE_INSTANCE_TYPE="+sm.getexeInstanceTypeProp()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
						sb.append("property EXE_LIFECYCLE_TYPE="+sm.getexeLifecycleTypeProp()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
					
					}
					
					if(am!=null){
						sb.append("property DOMAIN="+am.getexeEnvDomainProp()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
						sb.append("property ROBOT_TYPE="+am.getexeEnvRobotTypeProp()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
						sb.append("property LIBRARY_NAME="+am.getlibNameProp()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
						sb.append("property LIBRARY_TYPE="+am.getlibTypeProp()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
						sb.append("property IMPL_LANGUAGE="+am.getimplementLangProp()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
						sb.append("property COMPILER="+am.getcompilerProp()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
					}
					
					
					
//					OPRoSPropertiesElementModel pms = (OPRoSPropertiesElementModel)cm.getClassModel().getUMLDataModel().getElementProperty("OPRoSPropertiesElementModel");
//					OPRoSDataTypesElementModel dmts = (OPRoSDataTypesElementModel)cm.getClassModel().getUMLDataModel().getElementProperty("OPRoSDataTypesElementModel");
//					OPRoSServiceTypesElementModel smts = (OPRoSServiceTypesElementModel)cm.getClassModel().getUMLDataModel().getElementProperty("OPRoSServiceTypesElementModel");
					
					
					
				}
//				else				
//				sb.append("property ID_TYPE="+"HForkJoinModel"+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			}
			//PKY 08061101 S VForkJoinModel,HForkJoinModel 저장 불러올경우 VForkJoinModel로 작성되는문제
			if(um instanceof ClassifierModel){
				ClassifierModel cm = (ClassifierModel)um;
				UMLDataModel umd = cm.getUMLDataModel();
//				if(!(um instanceof AtomicComponentModel))
				sb.append(umd.writeModel());
				//PKY 08051401 S 컨테이너 내용이없으면 컨테이너안보이는문제
				if(cm.getUMLDataModel().getProperty("Container")!=null){
					if(cm.getUMLDataModel().getProperty("Container").equals("true"))
						//PKY 08051401 E 컨테이너 내용이없으면 컨테이너안보이는문제

						this.inModelViewStore.put(um.getUMLDataModel().getId(), cm.getBoundaryModel());
				}if(cm.getBoundaryModel().getChildren().size()>0){//PKY 08052101 S 바운더리 안에있는 객체와 밖에있는 객체와 관계선이 안되는문제
					this.inModelViewStore.put(um.getUMLDataModel().getId(), cm.getBoundaryModel());
				}//PKY 08052101 S 바운더리 안에있는 객체와 밖에있는 객체와 관계선이 안되는문제

			}
			//PKY 08051401 S  Actor 이미지 저장안되는문제
			else if(um instanceof FinalActorModel){
				FinalActorModel cm = (FinalActorModel)um;
				//PKY 08060201 S 
				UMLDataModel umd = cm.getUMLDataModel();
				sb.append(umd.writeModel());

				//				if(cm.getUMLDataModel().getProperty("ID_ACTORIMAGE")!=null)
				//				sb.append("property actorImage="+cm.getUMLDataModel().getProperty("ID_ACTORIMAGE")+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
				//				//PKY 08060201 S Actor 디스크립션 들어가지 않는문제 수정
				//				if(cm.getUMLDataModel().getProperty("ID_DESCRIPTION")!=null)
				//				sb.append("property ID_DESCRIPTION="+cm.getUMLDataModel().getProperty("ID_DESCRIPTION")+IN3ModelTokenType.PROPERTY_N3EOF+"\n");
				//PKY 08060201 E Actor 디스크립션 들어가지 않는문제 수정
				//PKY 08060201 E
			}
			//PKY 08051401 E  Actor 이미지 저장안되는문제

			else{
				sb.append(um.getUMLDataModel().writeModel());
			}
			sb.append(">\n");
			sb.append(")\n");

		}
		return sb.toString();

	}

	public int getIntValue(Object obj){
		if(obj==null){
			return -1; 
		}
		else{
			try{
				return Integer.valueOf((String)obj).intValue();
			}
			catch(Exception e){
				e.printStackTrace();
				return -1;
			}
		}

	}




	public void execute(int cmmd,String token){
		if(cmmd == e_create_model){
			if(objectTypes_begin==-1 && inModelType==-1){
				if(token.trim().equals("diagram")){
					current_model_type = 1;
					isLine = false;
					isBoundary = false;
					this.viewStore.clear();
				}
				else if(token.trim().equals("boundary")){
					current_model_type = 2000;
					isLine = false;
					isBoundary = true;
					this.viewStore.clear();
					boundaryModel = new NodeContainerModel();
				}
				//PKY 08050701 S Initial 생성 후 저장 불러오기 할경우 노트로 변경되어있는문제

				//PKY 08050701 E Initial 생성 후 저장 불러오기 할경우 노트로 변경되어있는문제
				else{
					if(!this.isLine){
						this.current_model_type = ProjectManager.getInstance().getModelTypeName(token);
						current_UMLDataModel = new UMLDataModel();
					}
					else{
						this.current_model_type = ProjectManager.getInstance().getLineModelType(token);
						current_UMLDataModel = new UMLDataModel();
					}
				}
			}
			else{
				//Attribute
				if(objectTypes_begin==1){


				}
				else if(inModelType>-1){
					current_ClassModel_UMLDataModel = new UMLDataModel(); 
				}

			}
		}
		else if(cmmd == e_views_begin){
			this.isView = true;

		}
		else if(cmmd == e_lines_begin){
			this.isLine = true;

			//PKY 08052101 S 컨테이너에서 그룹으로 변경

			if(isGroupModel!=null&&isGroupModel.size()>0){
				for(int i=0; i<isGroupModel.size();i++){
					HashMap hashGroup =(HashMap)isGroupModel.get(i);
					Iterator iterator = hashGroup.keySet().iterator();

					while (iterator.hasNext()) {
						String key = (String)iterator.next();
						Object obj = viewStore.get(key);
						if( obj !=null){
							Object obj1= viewStore.get(hashGroup.get(key));
							if(obj instanceof UMLModel){
								UMLModel model =(UMLModel)obj1;
								java.util.ArrayList arrayItem = new java.util.ArrayList();
								if(model!=null)
									if(model.getUMLDataModel().getElementProperty("GROUP_CHILDRENS")!=null){
										arrayItem=(java.util.ArrayList) model.getUMLDataModel().getElementProperty("GROUP_CHILDRENS");
										for(int j=0; j<arrayItem.size();j++){
											if(arrayItem.get(j)==obj1){
												arrayItem.remove(j);
											}

										}
									}
								//PKY 08052901 S 포함 에러발생 수정
								boolean check=true;
								if(obj!=null)//PKY 08052601 S Partition 저장 불러올시 해제되는문제
									for(int b=0; b<arrayItem.size();b++){
										if(arrayItem.get(b)==obj)
											check=false;
									}
								if(arrayItem.size()==0||check==true){
									arrayItem.add(obj);
									if(model!=null&&arrayItem.size()>0)//PKY 08052601 S Partition 저장 불러올시 해제되는문제 
										model.getUMLDataModel().setElementProperty("GROUP_CHILDRENS", arrayItem);
								}
								//PKY 08052901 E 포함 에러발생 수정

							}
						}
					}
				}
			}

			if(isGroupParent!=null&&isGroupParent.size()>0){
				for(int i=0; i<isGroupParent.size();i++){	
					HashMap hashParent =(HashMap)isGroupParent.get(i);
					Iterator iterator1 = hashParent.keySet().iterator();
					while (iterator1.hasNext()) {
						String key = (String)iterator1.next();
						Object obj = viewStore.get(key);
						if( obj !=null){
							Object obj1= viewStore.get(hashParent.get(key));
							if(obj instanceof UMLModel){
								UMLModel model =(UMLModel)obj;
								java.util.ArrayList arrayItem = new java.util.ArrayList();
								if(model.getUMLDataModel().getElementProperty("GROUP_PARENTS")!=null){
									arrayItem=(java.util.ArrayList) model.getUMLDataModel().getElementProperty("GROUP_PARENTS");
									for(int j=0; j<arrayItem.size();j++){
										if(arrayItem.get(j)==obj1){
											arrayItem.remove(j);
										}

									}
								}
								//PKY 08052901 S 포함 에러발생 수정

								boolean check=true;
								if(obj!=null)//PKY 08052601 S Partition 저장 불러올시 해제되는문제
									for(int b=0; b<arrayItem.size();b++){
										if(arrayItem.get(b)==obj)
											check=false;
									}
								if(arrayItem.size()==0||check==true){
									arrayItem.add(obj1);
									if(model!=null&&arrayItem.size()>0)//PKY 08052601 S Partition 저장 불러올시 해제되는문제 

										model.addGroupParent(obj1);//PKY 08052801 S 파티션 중복으로 저장 불러올경우 움직임이 비정상적인문제
									//PKY 08061801 S state pattion 저장 불러오기할경우 이동시 state밖으로 나가는문제

									if(obj instanceof SubPartitonModel){
										if(obj1 instanceof UMLModel){
											((SubPartitonModel)obj).setParentModel((UMLModel)obj1);
											((SubPartitonModel)obj).setAcceptParentModel((UMLModel)obj1);	
											((PartitionModel)obj1).addPartition((SubPartitonModel)obj);
										}
									}
									//PKY 08061801 E state pattion 저장 불러오기할경우 이동시 state밖으로 나가는문제
								}
								//PKY 08052901 E 포함 에러발생 수정


							}
						}
					}
				}
			}

			//PKY 08052101 E 컨테이너에서 그룹으로 변경
			//PKY 08080501 S Frame 저장이 안되는 문제 
			Iterator iterator = diagramFrame.keySet().iterator();
			while(iterator.hasNext()){
				String key = (String)iterator.next();
				if(diagramStore.get(key)!=null){
					ArrayList arryList = (ArrayList)diagramFrame.get(key);
					for( int i = 0;  i < arryList.size(); i ++){
						FrameModel frameModel = (FrameModel)arryList.get(i);
						frameModel.getFragmentCondition().clear();//PKY 08082201 S Frame 저장 불러와두 초기화 되지 않는문제
						Object obj =diagramStore.get(key);
						frameModel.setN3EditorDiagramModel((N3EditorDiagramModel)diagramStore.get(key));
						frameModel.setName(((N3EditorDiagramModel)diagramStore.get(key)).getName());
						frameModel.refreshDiagram((N3EditorDiagramModel)diagramStore.get(key));
						frameModel.setFragmentName(((N3EditorDiagramModel)diagramStore.get(key)).getName());

					}
					//					diagramStore.remove(key);
				}
			}

			//PKY 08080501 E Frame 저장이 안되는 문제 
			if(this.isBoundary){

			}
			else{
				N3EditorDiagramModel n3EditorDiagramModel = (N3EditorDiagramModel)this.viewStore.get("diagram");
				Object obj1 = lineStore.get(n3EditorDiagramModel.getUMLDataModel().getId());
				if(obj1!=null){
					java.util.ArrayList list = (java.util.ArrayList)obj1;
					for(int i=0;i<list.size();i++){
						LineModel lm1 = (LineModel)list.get(i);
						if(n3EditorDiagramModel!=null){
							UMLModel umS = (UMLModel)this.viewStore.get(lm1.getSourceId());
							UMLModel umT = (UMLModel)this.viewStore.get(lm1.getTargetId());
							if(umS!=null){
								lm1.setSource(umS);
							}
							if(umT!=null){

								lm1.setTarget(umT);
							}
							lm1.attachSource();
							lm1.attachTarget(n3EditorDiagramModel);
						}
					}
					list.clear();
					lineStore.put(n3EditorDiagramModel.getUMLDataModel().getId(), null);
				}
			}
		}
		else if(cmmd == e_property_push){
			if(objectTypes_begin==-1){
				if(inModelType!=-1){
					int keyIndex = token.indexOf("=");
					String id = token.substring(0,keyIndex);
					String value = token.substring(keyIndex+1,token.length());
					int index = value.lastIndexOf("PROPERTY_N3EOF");
					value = value.substring(0, index);
					current_ClassModel_UMLDataModel.setProperty(id, value);
				}
				else{
					int keyIndex = token.indexOf("=");
					String id = token.substring(0,keyIndex);
					String value = token.substring(keyIndex+1,token.length());
					int index = value.lastIndexOf("PROPERTY_N3EOF");
					value = value.substring(0, index);
					current_UMLDataModel.setProperty(id, value);
				}
			}
			else {
				if(objectTypes_begin==3){
					int keyIndex = token.indexOf("=");
					String id = token.substring(0,keyIndex);
					String value = token.substring(keyIndex+1,token.length());
					int index = value.lastIndexOf("PROPERTY_N3EOF");
					value = value.substring(0, index);
					current_objects_v.add(this.getLineBendpointModel(value));

					//					current_objects.add(o);

				}
				else if(objectTypes_begin==4){
					int keyIndex = token.indexOf("=");
					String id = token.substring(0,keyIndex);
					String value = token.substring(keyIndex+1,token.length());
					int index = value.lastIndexOf("PROPERTY_N3EOF");
					value = value.substring(0, index);
					if(id.equals("activityParamterport")){
						current_objects_v.add(this.getActivityParameterModel(value));
					}
					else if(id.equals("expansionNodeport")){
						current_objects_v.add(this.getExpansionNodeModel(value));
					}
					//PKY 08052101 S 포트가 불러올때 다른 포트로 불러오는문제

					else if(id.equals("objectPortModel")){
						current_objects_v.add(this.getObjectPortModel(value));
					}
					else if(id.equals("exitPointModel")){
						current_objects_v.add(this.getExitPointModel(value));
					}
					else if(id.equals("entryPointModel")){
						current_objects_v.add(this.getEntryPointModel(value));
					}
					//PKY 08052101 E 포트가 불러올때 다른 포트로 불러오는문제

					else{
						current_objects_v.add(this.getCompPortModel(value,id));
					}

				}
				else if(objectTypes_begin==9){
					int keyIndex = token.indexOf("=");
					String id = token.substring(0,keyIndex);
					String value = token.substring(keyIndex+1,token.length());
					int index = value.lastIndexOf("PROPERTY_N3EOF");
					value = value.substring(0, index);
					subPartitons.add(this.getSubPartitionModel(value));
				}
				//				else if(objectTypes_begin==10){
				//					int keyIndex = token.indexOf("=");
				//					String id = token.substring(0,keyIndex);
				//					String value = token.substring(keyIndex+1,token.length());
				//					int index = value.lastIndexOf("PROPERTY_N3EOF");
				//					value = value.substring(0, index);
				//					seqGroups.add(this.getSeqGroup(value));
				//
				//
				//				}
				else if(objectTypes_begin==11){
					int keyIndex = token.indexOf("=");
					String id = token.substring(0,keyIndex);
					String value = token.substring(keyIndex+1,token.length());
					int index = value.lastIndexOf("PROPERTY_N3EOF");
					value = value.substring(0, index);
					MessageCommunicationModel mcm = this.getMessageCommunicationModel(value);
					mcm.setKey(id);
					this.msgs.add(mcm);


				}
				//2008043001 PKY S Timing 객체 저장 로직 추가
				else if(objectTypes_begin==12){
					int keyIndex = token.indexOf("=");
					String id = token.substring(0,keyIndex);
					String value = token.substring(keyIndex+1,token.length());
					int index = value.lastIndexOf("PROPERTY_N3EOF");
					value = value.substring(0, index);

					this.isState.add(value);


				}
				//2008043001 PKY E Timing 객체 저장 로직 추가
				//2008043001 PKY S Timing 객체 저장 로직 추가
				else if(objectTypes_begin==13){
					int keyIndex = token.indexOf("=");
					String id = token.substring(0,keyIndex);
					String value = token.substring(keyIndex+1,token.length());
					int index = value.lastIndexOf("PROPERTY_N3EOF");
					value = value.substring(0, index);

					this.isTransition.add(value);


				}
				//PKY 08050701 S Timing Line저장
				else if(objectTypes_begin==14){
					int keyIndex = token.indexOf("=");
					String id = token.substring(0,keyIndex);
					String value = token.substring(keyIndex+1,token.length());
					int index = value.lastIndexOf("PROPERTY_N3EOF");
					value = value.substring(0, index);
					this.isTimingPoint.add(value);//PKY 08050701 S Timing Line저장


				}
				//PKY 08050701 E Timing Line저장
				//PKY 08050701 S Timing Line저장
				else if(objectTypes_begin==15){
					int keyIndex = token.indexOf("=");
					String id = token.substring(0,keyIndex);
					String value = token.substring(keyIndex+1,token.length());
					int index = value.lastIndexOf("PROPERTY_N3EOF");
					value = value.substring(0, index);

					this.isSelfMessage.add(value);//PKY 08050701 S Timing Line저장


				}
				//PKY 08050701 E Timing Line저장
				//PKY 08051401 S 시퀀스 FragmentModel저장안되는것 
				else if(objectTypes_begin==16){
					int keyIndex = token.indexOf("=");
					String id = token.substring(0,keyIndex);
					String value = token.substring(keyIndex+1,token.length());
					int index = value.lastIndexOf("PROPERTY_N3EOF");
					value = value.substring(0, index);

					this.isFragment.add(value);


				}
				//PKY 08051401 E 시퀀스 FragmentModel저장안되는것 
				//2008043001 PKY E Timing 객체 저장 로직 추가

				//PKY 08052101 S 컨테이너에서 그룹으로 변경
				else if(objectTypes_begin==17){
					int keyIndex = token.indexOf("=");
					String id = token.substring(0,keyIndex);
					String value = token.substring(keyIndex+1,token.length());
					int index = value.lastIndexOf("PROPERTY_N3EOF");
					value = value.substring(0, index);
					String[] value2=value.split(",");
					HashMap hash = new HashMap();
					hash.put(value2[0],value2[1]);
					this.isGroupParent.add(hash);
					hash = new HashMap();

				}
				else if(objectTypes_begin==18){
					int keyIndex = token.indexOf("=");
					String id = token.substring(0,keyIndex);
					String value = token.substring(keyIndex+1,token.length());
					int index = value.lastIndexOf("PROPERTY_N3EOF");
					value = value.substring(0, index);
					String[] value2=value.split(",");
					HashMap hash = new HashMap();
					hash.put(value2[0],value2[1]);
					this.isGroupModel.add(hash);
					hash = new HashMap();

				}
				//PKY 08052101 E 컨테이너에서 그룹으로 변경
				//PKY 08071601 S State 저장안되는문제 
				else if(objectTypes_begin==19){
					int keyIndex = token.indexOf("=");
					String id = token.substring(0,keyIndex);
					String value = token.substring(keyIndex+1,token.length());
					int index = value.lastIndexOf("PROPERTY_N3EOF");
					value = value.substring(0, index);
					String[] value2=value.split(",");
					DetailPropertyTableItem item = new DetailPropertyTableItem();
					item.sName=(String)value2[0];
					item.sType=Integer.parseInt(value2[1]);
					if(value2.length>2){
						item.sSpecification=(String)value2[2];
					}
					this.isTransitions.add(item);


				}
				else if(objectTypes_begin==20){
					int keyIndex = token.indexOf("=");
					String id = token.substring(0,keyIndex);
					String value = token.substring(keyIndex+1,token.length());
					int index = value.lastIndexOf("PROPERTY_N3EOF");
					value = value.substring(0, index);
					if("OPRoSDataTypeElementModel".equals(id))
					current_objects.add(this.getOPRoSDataTypeElementModel(value, id));

					//					current_objects.add(o);

				}
				else if(objectTypes_begin==21){
					int keyIndex = token.indexOf("=");
					String id = token.substring(0,keyIndex);
					String value = token.substring(keyIndex+1,token.length());
					int index = value.lastIndexOf("PROPERTY_N3EOF");
					value = value.substring(0, index);
					if("OPRoSExeEnvironmentCPUElementModel".equals(id))
						current_objects.add(this.getOPRoSExeEnvironmentCPUElementModel(value, id));
					else
						current_objects.add(this.getOPRoSExeEnvironmentOSElementModel(value, id));

					//					current_objects.add(o);

				}
				else if(objectTypes_begin==22){
					int keyIndex = token.indexOf("=");
					String id = token.substring(0,keyIndex);
					String value = token.substring(keyIndex+1,token.length());
					int index = value.lastIndexOf("PROPERTY_N3EOF");
					value = value.substring(0, index);
					if("OPRoSServiceTypeElementModel".equals(id))
						current_objects.add(this.getOPRoSServiceTypeElementModel(value, id));

					//					current_objects.add(o);

				}
				else if(objectTypes_begin==23){
					int keyIndex = token.indexOf("=");
					String id = token.substring(0,keyIndex);
					String value = token.substring(keyIndex+1,token.length());
					int index = value.lastIndexOf("PROPERTY_N3EOF");
					value = value.substring(0, index);
					if("OPRoSPropertyElementModel".equals(id))
						current_objects.add(this.getOPRoSPropertyElementModel(value, id));

					//					current_objects.add(o);

				}
//				else if(objectTypes_begin==24){
//					int keyIndex = token.indexOf("=");
//					String id = token.substring(0,keyIndex);
//					String value = token.substring(keyIndex+1,token.length());
//					int index = value.lastIndexOf("PROPERTY_N3EOF");
//					value = value.substring(0, index);
//					if("OPRoSMonitorVariableElementModel".equals(id))
//						current_objects.add(this.getOPRoSMonitorVariableElementModel(value, id));
//
//					//					current_objects.add(o);
//
//				}
				
				//PKY 08071601 E State 저장안되는문제 


				else{
					int keyIndex = token.indexOf("=");
					String id = token.substring(0,keyIndex);
					String value = token.substring(keyIndex+1,token.length());
					int index = value.lastIndexOf("PROPERTY_N3EOF");
					value = value.substring(0, index);
					tempData.put(id, value); 
				}
			}
		}
		else if(cmmd == e_objects_end){
			if(objectTypes_begin==1){
				this.current_ClassModel_UMLDataModel.setElementProperty("ATTR_A", this.current_objects);
				current_objects = new java.util.ArrayList();
				objectTypes_begin = -1;
			}
			else if(objectTypes_begin==2){
				this.current_ClassModel_UMLDataModel.setElementProperty("OPER_A", this.current_objects);
				current_objects = new java.util.ArrayList();
				objectTypes_begin = -1;
			}
			else if(objectTypes_begin==3){
				current_UMLDataModel.setElementProperty("Bendpoints", this.current_objects_v);
				current_objects_v = new java.util.Vector();
				objectTypes_begin = -1;
			}
			else if(objectTypes_begin==4){
				current_UMLDataModel.setElementProperty("ports", this.current_objects_v);
				current_objects_v = new java.util.Vector();
				objectTypes_begin = -1;
			}
			else if(objectTypes_begin==5){
				current_UMLDataModel.setElementProperty("ID_DETAIL", this.current_objects);
				current_objects = new java.util.ArrayList();
				objectTypes_begin = -1;
			}
			else if(objectTypes_begin==6){
				current_UMLDataModel.setElementProperty("ID_TAG", this.current_objects);
				current_objects = new java.util.ArrayList();
				objectTypes_begin = -1;
			}
			else if(objectTypes_begin==7){
				current_UMLDataModel.setElementProperty("ID_EXTENSIONPOINT", this.current_objects);
				current_objects = new java.util.ArrayList();
				objectTypes_begin = -1;
			}
			else if(objectTypes_begin==8){
				objectTypes_begin = -1;
			}
			else if(objectTypes_begin==9){

				objectTypes_begin = -1;
			}
			else if(objectTypes_begin==10){
				objectTypes_begin = -1;
			}
			else if(objectTypes_begin==11){


				objectTypes_begin = -1;
			}
			else if(objectTypes_begin==12){//2008043001 PKY S Timing 객체 저장 로직 추가
				current_UMLDataModel.setElementProperty("isState", isState);
				isState = new java.util.ArrayList();
				objectTypes_begin = -1;
			}
			//2008043001 PKY E Timing 객체 저장 로직 추가
			else if(objectTypes_begin==13){//2008043001 PKY S Timing 객체 저장 로직 추가
				current_UMLDataModel.setElementProperty("isTransition", isTransition);
				isTransition = new java.util.ArrayList();
				objectTypes_begin = -1;
			}
			//2008043001 PKY E Timing 객체 저장 로직 추가
			//PKY 08050701 S Timing Line저장
			else if(objectTypes_begin==14){
				current_UMLDataModel.setElementProperty("isTimingPoint", isTimingPoint);
				isTimingPoint = new java.util.ArrayList();
				objectTypes_begin = -1;
			}
			//PKY 08050701 E Timing Line저장
			else if(objectTypes_begin==15){
				current_UMLDataModel.setElementProperty("isSelfMessage", isSelfMessage);
				isSelfMessage = new java.util.ArrayList();
				objectTypes_begin = -1;
			}
			//PKY 08050701 E Timing Line저장
			//PKY 08051401 S 시퀀스 FragmentModel저장안되는것 
			else if(objectTypes_begin==16){
				current_UMLDataModel.setElementProperty("isFragment", isFragment);
				isFragment = new java.util.ArrayList();
				objectTypes_begin = -1;
			}
			//PKY 08051401 E 시퀀스 FragmentModel저장안되는것 

			//PKY 08052101 S 컨테이너에서 그룹으로 변경

			else if(objectTypes_begin==17){
				current_UMLDataModel.setElementProperty("GROUP_PARENTS", isGroupParent);
				//				isGroupParent = new java.util.ArrayList();
				objectTypes_begin = -1;
			}
			else if(objectTypes_begin==18){
				current_UMLDataModel.setElementProperty("GROUP_CHILDRENS", isGroupModel);
				//				isGroupModel = new java.util.ArrayList();
				objectTypes_begin = -1;
			}
			//PKY 08052101 E 컨테이너에서 그룹으로 변경
			//PKY 08071601 S State 저장안되는문제 
			else if(objectTypes_begin==19){
				current_UMLDataModel.setElementProperty(LineModel.ID_TRAN_TRIGERS, isTransitions);
				//				isGroupModel = new java.util.ArrayList();
				objectTypes_begin = -1;
			}
			
			else if(objectTypes_begin==20){
				current_UMLDataModel.setElementProperty("OPRoSDataTypesElementModel", this.current_objects);
				current_objects = new java.util.ArrayList();
				objectTypes_begin = -1;
			}
			
			else if(objectTypes_begin==21){
				current_UMLDataModel.setElementProperty("OPRoSExeEnvironmentElementModel", this.current_objects);
				current_objects = new java.util.ArrayList();
				objectTypes_begin = -1;
			}
			else if(objectTypes_begin==22){
				current_UMLDataModel.setElementProperty("OPRoSServiceTypesElementModel", this.current_objects);
				current_objects = new java.util.ArrayList();
				objectTypes_begin = -1;
			}
			else if(objectTypes_begin==23){
				current_UMLDataModel.setElementProperty("OPRoSPropertiesElementModel", this.current_objects);
				current_objects = new java.util.ArrayList();
				objectTypes_begin = -1;
			}
			else if(objectTypes_begin==24){
				current_UMLDataModel.setElementProperty("ID_MV", this.current_objects);
				current_objects = new java.util.ArrayList();
				objectTypes_begin = -1;
			}
			//PKY 08071601 E State 저장안되는문제 

			else if(inModelType==0){
				ClassModel cm = new ClassModel();
				String refId = (String)current_ClassModel_UMLDataModel.getProperty("REF_ID");
				if(refId!=null){
					RefLinkModel refLinkModel = new RefLinkModel();
					refLinkModel.id = (String)current_ClassModel_UMLDataModel.getProperty("ID");
					refLinkModel.refId = refId;
					this.refStore.add(refLinkModel);

				}
				cm.setLoadModel(current_ClassModel_UMLDataModel);
				current_UMLDataModel.setElementProperty("ClassModel", cm);
				this.current_ClassModel_UMLDataModel = new UMLDataModel();
				inModelType = -1;


			}
			else if(inModelType==1){
				TypeRefModel cm = new TypeRefModel();
				String refId = (String)current_ClassModel_UMLDataModel.getProperty("REF_ID");
				if(refId!=null){
					RefLinkModel refLinkModel = new RefLinkModel();
					refLinkModel.id = (String)current_ClassModel_UMLDataModel.getProperty("ID");
					refLinkModel.refId = refId;
					this.refStore.add(refLinkModel);

				}
				cm.setLoadModel(current_ClassModel_UMLDataModel);
				current_UMLDataModel.setElementProperty("ClassModel", cm);
				this.current_ClassModel_UMLDataModel = new UMLDataModel();
				inModelType = -1;

			}
		}
		else if(cmmd == e_object_create_objectType){
			current_objects = new java.util.ArrayList();
			current_objects_v = new java.util.Vector();
			if(token!=null && token.equals("ATTR_A")){
				objectTypes_begin = 1;
			}
			else if(token!=null && token.equals("OPER_A")){
				objectTypes_begin = 2;
			}
			else if(token!=null && token.equals("Bendpoints")){
				objectTypes_begin = 3;
			}
			else if(token!=null && token.equals("ports")){
				objectTypes_begin = 4;
			}
			else if(token!=null && token.equals("ID_DETAIL")){
				objectTypes_begin = 5;
			}
			else if(token!=null && token.equals("ID_TAG")){
				objectTypes_begin = 6;
			}
			else if(token!=null && token.equals("ID_EXTENSIONPOINT")){
				objectTypes_begin = 7;
			}
			else if(token!=null && token.equals("ID_UPDATE_LISTENER")){
				objectTypes_begin = 8;
			}
			else if(token!=null && token.equals("subPartition")){
				objectTypes_begin = 9;
			}
			else if(token!=null && token.equals("groups")){
				objectTypes_begin = 10;
			}
			else if(token!=null && token.equals("MessageCommunications")){
				objectTypes_begin = 11;
			}
			//2008043001 PKY S Timing 객체 저장 로직 추가
			else if(token!=null && token.equals("isState")){
				objectTypes_begin = 12;
			}//2008043001 PKY E Timing 객체 저장 로직 추가
			//2008043001 PKY S Timing 객체 저장 로직 추가
			else if(token!=null && token.equals("isTransition")){
				objectTypes_begin = 13;
			}//2008043001 PKY E Timing 객체 저장 로직 추가
			//PKY 08050701 S Timing Line저장
			else if(token!=null && token.equals("Point")){
				objectTypes_begin = 14;
			}//PKY 08050701 S Timing Line저장
			//PKY 08050701 S Timing Line저장
			else if(token!=null && token.equals("SelfMessageCommunications")){
				objectTypes_begin = 15;
			}//PKY 08050701 S Timing Line저장
			//PKY 08051401 S 시퀀스 FragmentModel저장안되는것 
			else if(token!=null && token.equals("Conditions")){
				objectTypes_begin = 16;
			}
			//PKY 08051401 E 시퀀스 FragmentModel저장안되는것 
			//PKY 08052101 S 컨테이너에서 그룹으로 변경
			else if(token!=null && token.equals("GROUP_PARENTS")){
				objectTypes_begin = 17;
			}
			//PKY 08052101 E 컨테이너에서 그룹으로 변경			
			//PKY 08052101 S 컨테이너에서 그룹으로 변경
			else if(token!=null && token.equals("GROUP_CHILDRENS")){
				objectTypes_begin = 18;
			}
			//PKY 08052101 E 컨테이너에서 그룹으로 변경
			//PKY 08071601 S State 저장안되는문제 
			else if(token!=null && token.equals("Transitions")){
				objectTypes_begin = 19;
			}
			//PKY 08071601 E State 저장안되는문제 
			else if(token!=null && token.equals("OPRoSDataTypesElementModel")){
				objectTypes_begin = 20;
			}
			else if(token!=null && token.equals("OPRoSExeEnvironmentElementModel")){
				objectTypes_begin = 21;
			}
			else if(token!=null && token.equals("OPRoSServiceTypesElementModel")){
				objectTypes_begin = 22;
			}
			else if(token!=null && token.equals("OPRoSPropertiesElementModel")){
				objectTypes_begin = 23;
			}
			else if(token!=null && token.equals("ID_MV")){
				objectTypes_begin = 24;
			}
//			else if(token!=null && token.equals("OPRoSMonitorVariablesElementModel")){
//				objectTypes_begin = 24;
//			}
			


			else if(token!=null && token.equals("ClassModel")){
				inModelType = 0;
			}
			else if(token!=null && token.equals("TypeRefModel")){
				inModelType = 1;
			}

		}
		else if(cmmd == e_model_end){
			if(objectTypes_begin==1){
				String scope = (String)this.tempData.get("scope");
				String name = (String)this.tempData.get("name");
				String initValue = (String)this.tempData.get("initValue");
				String stype = (String)this.tempData.get("stype");
				String isDerived = (String)this.tempData.get("derived");
				String isStatic = (String)this.tempData.get("STATIC");
				String isConst = (String)this.tempData.get("const");
				String desc=  (String)this.tempData.get("desc");

				AttributeEditableTableItem newItem 	= new AttributeEditableTableItem(new Integer(scope),name,stype,initValue);
				if(desc!=null)
					newItem.desc = desc.replaceAll("/n", "\n");
				if(isDerived!=null) 
					newItem.isDerived = new Integer(isDerived);			// V1.02 WJH E 080520 형변환  변경
				if(isConst!=null)//PKY 08050701 유즈케이스 불러올때 에러 
					newItem.isConst = new Integer(isConst);//2008043001 PKY S 어트리뷰트(속성,초기값,Derived,Const)저장안됨
				this.current_objects.add(newItem);

			}
			else if(objectTypes_begin==2){
				String scope = (String)this.tempData.get("scope");
				String name = (String)this.tempData.get("name");
				String initValue = (String)this.tempData.get("initValue");
				String stype = (String)this.tempData.get("stype");
				String isDerived = (String)this.tempData.get("DERIVED");
				String isStatic = (String)this.tempData.get("STATIC");
				String isConst = (String)this.tempData.get("CONST");
				String desc=  (String)this.tempData.get("desc");
				String stereo =  (String)this.tempData.get("stereo");//PKY 08071101 S 오퍼레이션 스트레오타입 입력가능하도록
				String params =  (String)this.tempData.get("params");
				String isState =  (String)this.tempData.get("isState");//PKY 08071601 S 저장 불러오기 할경우 State 오퍼레이션 형식이 달라지는문제
				OperationEditableTableItem newItem = new OperationEditableTableItem(new Integer(scope),name,stype,initValue);
				//PKY 08071101 S 오퍼레이션 스트레오타입 입력가능하도록
				if(stereo!=null && !stereo.trim().equals(""))
					newItem.stereo=stereo;
				if(isStatic!=null && !isStatic.trim().equals(""))
					newItem.setIsState(Boolean.parseBoolean(isStatic));
				//PKY 08071101 E 오퍼레이션 스트레오타입 입력가능하도록
				if(isState!=null && !isState.trim().equals(""))
					newItem.setIsState(Boolean.parseBoolean(isState));	

				//PKY 08071601 E 저장 불러오기 할경우 State 오퍼레이션 형식이 달라지는문제

				if(desc!=null)//PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제
					newItem.desc = desc.replaceAll("/n", "\n");//PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제

				newItem.setParams(this.getParams(params));
				this.current_objects.add(newItem);


			}
			else if(objectTypes_begin==5 || objectTypes_begin==6|| objectTypes_begin==7|| objectTypes_begin==24){
				DetailPropertyTableItem newItem = new DetailPropertyTableItem();
				newItem.key = (String)this.tempData.get("key");
				newItem.desc = (String)this.tempData.get("desc");
				newItem.index = getIntValue((String)this.tempData.get("index"));
				newItem.tapIndex = getIntValue((String)this.tempData.get("tapIndex"));
				newItem.tapName = (String)this.tempData.get("tapName");
				newItem.sName = (String)this.tempData.get("sName");
				newItem.sType = getIntValue(this.tempData.get("sType"));
				newItem.sSpecification=  (String)this.tempData.get("sSpecification");
				newItem.sTagType = (String)this.tempData.get("sTagType");
				//PKY 08071101 S 유즈케이스 대안 로드 부분 디스크립션 저장 로드 시개행 안됨				
				if(newItem.desc!=null&&!newItem.desc.trim().equals("")){
					newItem.desc=newItem.desc.replaceAll("/n", "\n");
				}
				//PKY 08071101 E 유즈케이스 대안 로드 부분 디스크립션 저장 로드 시개행 안됨
				this.current_objects.add(newItem);

			}

			else if(this.isView){
				if(current_model_type==1){
					String isOpen = current_UMLDataModel.getProperty("ID_OPEN");
					String id = current_UMLDataModel.getProperty("ID");
					String type = current_UMLDataModel.getProperty("ID_TYPE");
					String dtype = current_UMLDataModel.getProperty("ID_DTYPE");
					String name = current_UMLDataModel.getProperty("ID_NAME");
					String parentID = current_UMLDataModel.getProperty("parentID");
					Object parentObject = modelStore.get(parentID);
					//					UMLTreeParentModel uMLTreeParentModel = (UMLTreeParentModel)parentObject;
					int diagramType = Integer.valueOf(type).intValue();
					UMLTreeModel to1 =new UMLTreeModel(name);
					to1.setParent((UMLTreeParentModel)parentObject);
					UMLTreeParentModel up= (UMLTreeParentModel)parentObject;
					if (to1 != null) {
						up.addChild(to1);
					}
					N3EditorDiagramModel n3EditorDiagramModel = new N3EditorDiagramModel();
					if(dtype!=null)
					n3EditorDiagramModel.setDtype(Integer.parseInt(dtype));
					IEditorInput input = null;
					//PKY 08081101 S 저장 불러오기 할 경우 다이어그램 ToolTip초기화 되는문제 
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
					//PKY 08081101 E 저장 불러오기 할 경우 다이어그램 ToolTip초기화 되는문제
					to1.setRefModel(n3EditorDiagramModel);
					n3EditorDiagramModel.setName(name);
					n3EditorDiagramModel.setTreeModel(to1);
					n3EditorDiagramModel.getUMLDataModel().setId(id);
					to1.setParent(up);
					n3EditorDiagramModel.setIEditorInput(input);
					n3EditorDiagramModel.setDiagramType( diagramType);
					//PKY 08061101 S 패키지 저장 불러오기 한 후 더블클릭하여 오픈시에 다이어그램이 두개 생기는문제 수정
					if(parentID!=null)
						if(modelStore.get(parentID)!=null){
							if(modelStore.get(parentID) instanceof PackageTreeModel){
								if(((PackageTreeModel)modelStore.get(parentID)).getRefModel()!=null)
									if(((PackageTreeModel)modelStore.get(parentID)).getRefModel() instanceof FinalPackageModel){
										FinalPackageModel model=(FinalPackageModel)((PackageTreeModel)modelStore.get(parentID)).getRefModel();
										model.setN3EditorDiagramModel(n3EditorDiagramModel);
									}
							}
						}
					//PKY 08061101 E 패키지 저장 불러오기 한 후 더블클릭하여 오픈시에 다이어그램이 두개 생기는문제 수정
					this.viewStore.put(id, n3EditorDiagramModel);
					this.viewStore.put("diagram", n3EditorDiagramModel);
					if("true".equals(isOpen)){
						this.openDiagarams.add(n3EditorDiagramModel);
					}
					this.diagramStore.put(id, n3EditorDiagramModel);//PKY 08080501 S Frame 저장이 안되는 문제 

				}
				//2008043001 PKY S Note
				else if(current_model_type==41){
					String view_id = current_UMLDataModel.getProperty("View_ID");
					String id = current_UMLDataModel.getProperty("ID");
					String parentID = current_UMLDataModel.getProperty("parentID");
					String size = current_UMLDataModel.getProperty("ID_SIZE");
					String location = current_UMLDataModel.getProperty("ID_LOCATION");
					String ID_COLOR = current_UMLDataModel.getProperty("ID_COLOR");
					String Text = current_UMLDataModel.getProperty("Text");
					Object obj = this.viewStore.get(parentID);
					//PKY 08051401 S  COLOR Null 체크
					String[] dataColor=null;
					if(ID_COLOR!=null)
						dataColor=ID_COLOR.split(",");//PKY 08050701 S 색상 저장
					String[] dataSize =  size.split(",");
					String[] dataLocation =  location.split(",");
					UMLNoteModel UMLNoteModel=new UMLNoteModel();
					UMLNoteModel.setView_ID(view_id);
					UMLNoteModel.setID(id);

					if(Text!=null)//PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제
						UMLNoteModel.setLabelContents(Text.replaceAll("/n", "\n"));//PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제
					//PKY 08060201 S 컬러관련 재 생성할경우 적용안되는문제
					if(dataColor.length>0){
						UMLNoteModel.setBackGroundColor(new Color(null,Integer.parseInt(dataColor[0]),Integer.parseInt(dataColor[1]),Integer.parseInt(dataColor[2])));//PKY 08050701 S 색상 저장
						UMLNoteModel.getUMLDataModel().setElementProperty("ID_COLOR", UMLNoteModel.getBackGroundColor());
					}
					//PKY 08060201 E 컬러관련 재 생성할경우 적용안되는문제
					UMLNoteModel.setSize(new Dimension(Integer.parseInt(dataSize[0]),Integer.parseInt(dataSize[1])));
					UMLNoteModel.setLocation(new Point(Integer.parseInt(dataLocation[0]),Integer.parseInt(dataLocation[1])));
					if(obj instanceof N3EditorDiagramModel){
						N3EditorDiagramModel n3d = (N3EditorDiagramModel)obj;
						n3d.addChild(UMLNoteModel);
					}
					this.viewStore.put(view_id, UMLNoteModel);


				}
				//2008043001 PKY E Note
//				else if(current_model_type==42){//PKY 08050701 S Initial 생성 후 저장 불러오기 할경우 노트로 변경되어있는문제
//					String view_id = current_UMLDataModel.getProperty("View_ID");
//					String id = current_UMLDataModel.getProperty("ID");
//					String parentID = current_UMLDataModel.getProperty("parentID");
//					String size = current_UMLDataModel.getProperty("ID_SIZE");
//					String location = current_UMLDataModel.getProperty("ID_LOCATION");
//					String ID_COLOR = current_UMLDataModel.getProperty("ID_COLOR");
//					String Text = current_UMLDataModel.getProperty("Text");
//					Object obj = this.viewStore.get(parentID);
//					String[] dataColor=null;
//					if(ID_COLOR!=null)
//						dataColor=ID_COLOR.split(",");//PKY 08050701 S 색상 저장
//
//					String[] dataSize =  size.split(",");
//					String[] dataLocation =  location.split(",");
//					GroupModel UMLNoteModel=new GroupModel();
//					UMLNoteModel.setView_ID(view_id);
//					UMLNoteModel.setID(id);
//					//PKY 08060201 S 컬러관련 재 생성할경우 적용안되는문제
//					if(dataColor.length>0){
//						UMLNoteModel.setBackGroundColor(new Color(null,Integer.parseInt(dataColor[0]),Integer.parseInt(dataColor[1]),Integer.parseInt(dataColor[2])));//PKY 08050701 S 색상 저장
//						UMLNoteModel.getUMLDataModel().setElementProperty("ID_COLOR", UMLNoteModel.getBackGroundColor());
//					}
//					//PKY 08060201 E 컬러관련 재 생성할경우 적용안되는문제
//					UMLNoteModel.setSize(new Dimension(Integer.parseInt(dataSize[0]),Integer.parseInt(dataSize[1])));
//					UMLNoteModel.setLocation(new Point(Integer.parseInt(dataLocation[0]),Integer.parseInt(dataLocation[1])));
//					if(obj instanceof N3EditorDiagramModel){
//						N3EditorDiagramModel n3d = (N3EditorDiagramModel)obj;
//						n3d.addChild(UMLNoteModel);
//					}
//					this.viewStore.put(view_id, UMLNoteModel);
//
//
//				}
				//PKY 08050701 S  노트를 바운더리 안에 넣을경우 에러발생
				//PKY 08051401 S 시퀀스 FragmentModel저장안되는것 
				else if(current_model_type==43){



				}
				//PKY 08051401 S 시퀀스 FragmentModel저장안되는것 

				//PKY 08052101 S 컨테이너에서 그룹으로 변경
				else if(current_model_type==44){
					String view_id = current_UMLDataModel.getProperty("View_ID");
					String id = current_UMLDataModel.getProperty("ID");
					String parentID = current_UMLDataModel.getProperty("parentID");
					String size = current_UMLDataModel.getProperty("ID_SIZE");
					String location = current_UMLDataModel.getProperty("ID_LOCATION");
					String ID_COLOR = current_UMLDataModel.getProperty("ID_COLOR");
					//PKY 08052601 S 파티션 이름 저장되지 않는문제 수정
					String Text = current_UMLDataModel.getProperty("ID_NAME");
					String parentActivity = current_UMLDataModel.getProperty("parentActivity");//PKY 08081801 S 엑티비티 파티션 움직임이 비정상적인 문제


					Object obj = this.viewStore.get(parentID);
					String[] dataColor=null;
					if(ID_COLOR!=null)
						dataColor=ID_COLOR.split(",");//PKY 08050701 S 색상 저장
					String[] dataSize =  size.split(",");
					String[] dataLocation =  location.split(",");
					SubPartitonModel UMLNoteModel=new SubPartitonModel();
					UMLNoteModel.setIsMode(true);
					UMLNoteModel.setView_ID(view_id);
					UMLNoteModel.setID(id);
					//PKY 08052601 S 파티션 이름 저장되지 않는문제 수정
					if(Text!=null&&!(Text.trim().equals("")))
						UMLNoteModel.setName(Text);
					//PKY 08052601 E 파티션 이름 저장되지 않는문제 수정

					//PKY 08060201 S 컬러관련 재 생성할경우 적용안되는문제
					if(dataColor.length>0){
						UMLNoteModel.setBackGroundColor(new Color(null,Integer.parseInt(dataColor[0]),Integer.parseInt(dataColor[1]),Integer.parseInt(dataColor[2])));//PKY 08050701 S 색상 저장
						UMLNoteModel.getUMLDataModel().setElementProperty("ID_COLOR", UMLNoteModel.getBackGroundColor());
					}
					//PKY 08060201 E 컬러관련 재 생성할경우 적용안되는문제
					UMLNoteModel.setSize(new Dimension(Integer.parseInt(dataSize[0]),Integer.parseInt(dataSize[1])));
					UMLNoteModel.setLocation(new Point(Integer.parseInt(dataLocation[0]),Integer.parseInt(dataLocation[1])));
					if(obj instanceof N3EditorDiagramModel){
						N3EditorDiagramModel n3d = (N3EditorDiagramModel)obj;
						n3d.addChild(UMLNoteModel);
					}
					//PKY 08052601 S Partition 저장 불러올시 해제되는문제 
					//					if(this.current_UMLDataModel.getElementProperty("GROUP_PARENTS")!=null){
					//					UMLNoteModel.getUMLDataModel().setElementProperty("GROUP_PARENTS", this.current_UMLDataModel.getElementProperty("GROUP_PARENTS"));
					//					}
					//PKY 08052601 E Partition 저장 불러올시 해제되는문제 

					this.viewStore.put(view_id, UMLNoteModel);
					//PKY 08081801 S 엑티비티 파티션 움직임이 비정상적인 문제
					if(parentActivity!=null && !parentActivity.equals("") && viewStore.get(parentActivity)!=null){
						UMLNoteModel.setParentModel((UMLModel)viewStore.get(parentActivity));
						if((UMLModel)viewStore.get(parentActivity) instanceof FinalActivityModel){
							FinalActivityModel model = (FinalActivityModel)viewStore.get(parentActivity);
							model.getPm().addPartition(UMLNoteModel);
						}
						//PKY 08081801 E 저장 불러오기 할 경우 파티션이 모델에 Pm에 저장되지 않는 문제

					}
					//PKY 08081801 E 엑티비티 파티션 움직임이 비정상적인 문제

				}
				//PKY 08052101 E 컨테이너에서 그룹으로 변경
				//PKY 08052601 S endPoint저장안됨
				else if(current_model_type==45){
					


				}
//				else if(current_model_type==1010){
//					String view_id = current_UMLDataModel.getProperty("View_ID");
//					String id = current_UMLDataModel.getProperty("ID");
//					String parentID = current_UMLDataModel.getProperty("parentID");
//					String size = current_UMLDataModel.getProperty("ID_SIZE");
//					String location = current_UMLDataModel.getProperty("ID_LOCATION");
//					String ID_COLOR = current_UMLDataModel.getProperty("ID_COLOR");
//
//
//				}
				else if(current_model_type==4||current_model_type==14||current_model_type==15||
						current_model_type==16||
						current_model_type==17||current_model_type==18||current_model_type==24||
						current_model_type==25||current_model_type==33||current_model_type==34||current_model_type==36||
						current_model_type==46){
					String view_id = current_UMLDataModel.getProperty("View_ID");
					String id = current_UMLDataModel.getProperty("ID");
					String parentID = current_UMLDataModel.getProperty("parentID");
					String size = current_UMLDataModel.getProperty("ID_SIZE");
					String location = current_UMLDataModel.getProperty("ID_LOCATION");
					String ID_COLOR = current_UMLDataModel.getProperty("ID_COLOR");
					String name = current_UMLDataModel.getProperty("ID_NAME");					
					String stero = current_UMLDataModel.getProperty("ID_STEREOTYPE");
					Object obj = this.viewStore.get(parentID);
					String[] dataColor=null;
					String[] dataSize =  size.split(",");
					String[] dataLocation =  location.split(",");
					Object Tag =current_UMLDataModel.getElementProperty("ID_TAG");
					String ID_DESCRIPTION =current_UMLDataModel.getProperty("ID_DESCRIPTION");					
					String ID_SCOPE =current_UMLDataModel.getProperty("ID_SCOPE");
					String multiplicity =current_UMLDataModel.getProperty("ID_MULTIPLICITY");
					//20080729IJS
					String deepHistory =current_UMLDataModel.getProperty("ID_DEEP_HISTORY");

					UMLModel model=null;

					if(ID_COLOR!=null)
						dataColor=ID_COLOR.split(",");//PKY 08050701 S 색상 저장

					if(current_model_type==4)
						model=new FinalBoundryModel();
					else if(current_model_type==15)
						model=new FinalModel();
					else if(current_model_type==14)
						model=new InitialModel();
					else if(current_model_type==16)
						model=new FlowFinalModel();
					else if(current_model_type==17)
						model=new SynchModel();
					else if(current_model_type==18)
						model=new DecisionModel();
					else if(current_model_type==24)
						model=new HForkJoinModel();
					else if(current_model_type==25)
						model=new VForkJoinModel();
					else if(current_model_type==33){

					}

					else if(current_model_type==46)
						model=new HPartitionModel();

					model.setView_ID(view_id);
					model.setID(id);

					if(Tag!=null)
						model.setTags((ArrayList)Tag);					

					if(ID_DESCRIPTION!=null&&!ID_DESCRIPTION.equals("")){
						ID_DESCRIPTION=ID_DESCRIPTION.replaceAll("/n", "\n");
						model.setDesc(ID_DESCRIPTION);
					}

					if(ID_SCOPE!=null&&ID_SCOPE.trim().length()>0&&!ID_SCOPE.trim().equals(""))
						model.setScope(ID_SCOPE);

					if(name!=null&&name.trim().length()>0&&!name.trim().equals(""))
						model.setName(name);

					if(stero!=null&&stero.trim().length()>0&&!stero.trim().equals(""))
						model.setStereotype(stero);

					if(multiplicity!=null&&multiplicity.trim().length()>0&&!multiplicity.trim().equals(""))
						model.setMultiplicity(multiplicity);

					if(dataColor.length>0){
						model.setBackGroundColor(new Color(null,Integer.parseInt(dataColor[0]),Integer.parseInt(dataColor[1]),Integer.parseInt(dataColor[2])));//PKY 08050701 S 색상 저장
						model.getUMLDataModel().setElementProperty("ID_COLOR", model.getBackGroundColor());
					}

					model.setSize(new Dimension(Integer.parseInt(dataSize[0]),Integer.parseInt(dataSize[1])));

					model.setLocation(new Point(Integer.parseInt(dataLocation[0]),Integer.parseInt(dataLocation[1])));

					if(obj instanceof N3EditorDiagramModel){
						N3EditorDiagramModel n3d = (N3EditorDiagramModel)obj;
						n3d.addChild(model);

						//PKY 08072401 S 저장 불러오기 시 뷰 모델의 이름이 다이어그램에서 빠지는문제 수정
						if(current_model_type==15)
							n3d.addChild(((FinalModel)model).text);
						else if(current_model_type==14)
							n3d.addChild(((InitialModel)model).text);
						else if(current_model_type==16)
							n3d.addChild(((FlowFinalModel)model).text);
						else if(current_model_type==17)
							n3d.addChild(((SynchModel)model).text);
						else if(current_model_type==18)
							n3d.addChild(((DecisionModel)model).text);
						else if(current_model_type==24)
							n3d.addChild(((HForkJoinModel)model).text);
						else if(current_model_type==25)
							n3d.addChild(((VForkJoinModel)model).text);

						//PKY 08072401 E 저장 불러오기 시 뷰 모델의 이름이 다이어그램에서 빠지는문제 수정					
					}

					this.viewStore.put(view_id, model);

				}
				else if(current_model_type==47){//PKY 08080501 S Frame 저장이 안되는 문제 
					String view_id = current_UMLDataModel.getProperty("View_ID");
					String id = current_UMLDataModel.getProperty("ID");
					String parentID = current_UMLDataModel.getProperty("parentID");
					String size = current_UMLDataModel.getProperty("ID_SIZE");
					String location = current_UMLDataModel.getProperty("ID_LOCATION");
					String ID_COLOR = current_UMLDataModel.getProperty("ID_COLOR");
					Object obj = this.viewStore.get(parentID);
					String[] dataColor=null;
					String[] dataSize =  size.split(",");
					String[] dataLocation =  location.split(",");		
					String frameRefDiagram = current_UMLDataModel.getProperty("parentDiagramID");
					String ID_NAME = current_UMLDataModel.getProperty("ID_NAME");
					String ID_TYPE = current_UMLDataModel.getProperty("ID_TYPE");
					FrameModel model = new FrameModel();

					if(ID_NAME!=null)
						model.setFragmentName(ID_NAME);
					if(ID_TYPE!=null)
						model.setType(ID_TYPE);
					if(ID_COLOR!=null)
						dataColor=ID_COLOR.split(",");//PKY 08050701 S 색상 저장



					if(dataColor.length>0){
						model.setBackGroundColor(new Color(null,Integer.parseInt(dataColor[0]),Integer.parseInt(dataColor[1]),Integer.parseInt(dataColor[2])));//PKY 08050701 S 색상 저장
						model.getUMLDataModel().setElementProperty("ID_COLOR", model.getBackGroundColor());
					}

					model.setSize(new Dimension(Integer.parseInt(dataSize[0]),Integer.parseInt(dataSize[1])));

					model.setLocation(new Point(Integer.parseInt(dataLocation[0]),Integer.parseInt(dataLocation[1])));
					if(obj instanceof N3EditorDiagramModel){
						N3EditorDiagramModel n3d = (N3EditorDiagramModel)obj;
						n3d.addChild(model);
						//						n3d.frameModels.add(model);//PKY 08082201 S Frame 저장 불러와두 초기화 되지 않는문제
						model.setN3EditorDiagramModel(new N3EditorDiagramModel());//PKY 08082201 S 다이어그램 Frame Link에서 에러발생

					}
					this.viewStore.put(view_id, model);


					if(frameRefDiagram!=null&&frameRefDiagram.trim().length()>0&&!frameRefDiagram.trim().equals("")){
						if(diagramFrame.get(frameRefDiagram)!=null){
							ArrayList arry = (ArrayList)diagramFrame.get(frameRefDiagram);
							boolean isCheck=false;//PKY 08082201 S Frame 저장 불러와두 초기화 되지 않는문제
							model.getFragmentCondition().clear();//PKY 08082201 S Frame 저장 불러와두 초기화 되지 않는문제
							for(int i = 0; i < arry.size(); i ++){
								if(arry.get(i)==model){
									i= arry.size();
									isCheck=true;
								}
							}
							if(!isCheck){//PKY 08082201 S Frame 저장 불러와두 초기화 되지 않는문제

								arry.add(model);
								diagramFrame.put(frameRefDiagram, arry);							
							}
						}else{
							ArrayList arry = new ArrayList();
							arry.add(model);
							diagramFrame.put(frameRefDiagram, arry);
						}
					}


				}//PKY 08080501 E Frame 저장이 안되는 문제
				//PKY 08081101 S Timing 구조 변경
				
				
				else{
					if(current_model_type>=1000 && current_model_type!=2000){
						createLineModel(current_model_type,current_UMLDataModel,viewStore);

					}
					else{
						//20080721IJS
						String life_Cycle = current_UMLDataModel.getProperty("ID_LIFE_CYCLE");
						String view_id = current_UMLDataModel.getProperty("View_ID");
						String node_id = current_UMLDataModel.getProperty("NODE_ID");
						Object obj5 = this.modelStore.get(node_id);
						
						
						
						String id = current_UMLDataModel.getProperty("ID");
						
						String type = current_UMLDataModel.getProperty("ID_TYPE");
						String name = current_UMLDataModel.getProperty("ID_NAME");
						String fullName = current_UMLDataModel.getProperty("FULL_NAME");
						
						String parentID = current_UMLDataModel.getProperty("parentID");
						String size = current_UMLDataModel.getProperty("ID_SIZE");
						String location = current_UMLDataModel.getProperty("ID_LOCATION");
						String view_Type = current_UMLDataModel.getProperty("View_Type");
						String ID_COLOR = current_UMLDataModel.getProperty("ID_COLOR");//PKY 08050701 S 색상 저장
						String container = current_UMLDataModel.getProperty("Container");//PKY 08051401 S 컨테이너가 포함된 객체 저장 불러올경우 컨테이너를 두번 추가할 수 있는 문제
						String isNum = (String)current_UMLDataModel.getProperty("isNum"); //PKY 08061101 S StateLifelineModel,Time표시되는 형태를 저장하여 불러와두 동일한 형태로 적용되도록 수정
						//PKY 08051401 S 엑티비티 컨테이너 안 파티션 객체 추가 관계선 시 저장 불러오기 에러 발생
						String[] dataColor=null;
						if(ID_COLOR!=null){
							dataColor=ID_COLOR.split(",");//PKY 08050701 S 색상 저장
						}
						//PKY 08051401 E 엑티비티 컨테이너 안 파티션 객체 추가 관계선 시 저장 불러오기 에러 발생

						Object obj = this.viewStore.get(parentID);
						UMLTreeModel up = null;
						UMLModel um = null;
						if(obj instanceof N3EditorDiagramModel){
							N3EditorDiagramModel n3d = (N3EditorDiagramModel)obj;

							up = (UMLTreeModel)this.modelStore.get(id);
							if(up==null){
								up = (UMLTreeModel)CompAssemManager.getInstance().getModelStore().get(fullName);
								
							}
							if(up!=null){
								um = (UMLModel)up.getRefModel();
							}//20080811IJS 시작

							else{


								this.factory.setN3d(n3d);
								
								UMLModel child = this.factory.createModle(this.current_model_type);
								if(child instanceof ComponentModel){
									ComponentModel cm = (ComponentModel)child;
									if(obj5!=null){
										NodeItemTreeModel nit = (NodeItemTreeModel)obj5;
										cm.setNodeItemModel((NodeItemModel)nit.getRefModel());
									}
								}
								this.factory.setN3d(null);
								child.setSize(this.getSize(size));
								child.getUMLDataModel().setId(id);

								//PKY 08082201 S 링크 해당모델이 없는 View모델일 경우에도 Name이 표시되도록
								if(name!=null && !name.equals(""))
									child.setName(name);
								//PKY 08082201 E 링크 해당모델이 없는 View모델일 경우에도 Name이 표시되도록

								TeamProjectManager.getInstance().addNoModelMap(child);


								child.setLocation(this.getLocation(location));

								//PKY 08090101 S 시퀀스 다이어그램이 열린상태에서 링크에 있는 클래스 인터페이스 Actor를 불러들이면 시퀀스모양처럼 생성되는문제
								if(obj instanceof N3EditorDiagramModel){
									child.setN3EditorDiagramModelTemp((N3EditorDiagramModel)obj);
								}

								//PKY 08051401 S 컨테이너가 포함된 객체 저장 불러올경우 컨테이너를 두번 추가할 수 있는 문제
								if(container!=null)
									child.uMLDataModel.setElementProperty("Container", container);
								//PKY 08051401 E 컨테이너가 포함된 객체 저장 불러올경우 컨테이너를 두번 추가할 수 있는 문제

								//PKY 08060201 S 컬러관련 재 생성할경우 적용안되는문제
								if(dataColor.length>0){
									child.setBackGroundColor(new Color(null,Integer.parseInt(dataColor[0]),Integer.parseInt(dataColor[1]),Integer.parseInt(dataColor[2])));//PKY 08050701 S 색상 저장
									child.getUMLDataModel().setElementProperty("ID_COLOR", child.getBackGroundColor());
								}
								//PKY 08060201 E 컬러관련 재 생성할경우 적용안되는문제

								//PKY 08061101 S StateLifelineModel,Time표시되는 형태를 저장하여 불러와두 동일한 형태로 적용되도록 수정
								
								//PKY 08061101 E StateLifelineModel, 형태를 저장하여 불러와두 동일한 형태로 적용되도록 수정
								n3d.addChild(child);
								//20080811IJS
								if(up!=null)
									up.addN3UMLModelDeleteListener(n3d);
								if(child instanceof ClassifierModel){
									ClassifierModel cm = (ClassifierModel)child;
									//									cm.addContainer(this.boundaryModel);
									if(cm !=null
											&& cm.getBoundaryModel().getChildren().size()>0
											&& cm instanceof PartitionModel){
										PartitionModel pm =(PartitionModel)cm;
										for(int i=0;i<cm.getBoundaryModel().getChildren().size();i++){
											Object obj1 = cm.getBoundaryModel().getChildren().get(i);
											if(obj1 instanceof SubPartitonModel){
												SubPartitonModel sm =(SubPartitonModel)obj1;
												pm.getPm().addPartition(sm);
											}

										}

									}

								}
								
								//PKY 08050701 S Timing Line저장
								if(child instanceof PortContainerModel){
									java.util.ArrayList ports = new java.util.ArrayList();
									PortContainerModel cm = (PortContainerModel)child;
									Object oports = current_UMLDataModel.getElementProperty("ports");

									if(oports!=null){
										java.util.Vector aprts = (java.util.Vector)oports;
										if(cm instanceof ExceptionModel){
											//											PortModel pm = (PortModel)cm.getPortManager().getPorts().get(0);
											System.out.println("dddd");
											cm.getPortManager().getPorts().remove(0);
											if(aprts.size()==1){
												PortModel pm = (PortModel)aprts.get(0);
												pm.setPortContainerModel(cm);
												pm.setSize(new Dimension(pm.w,pm.h));
												pm.setPtDifference(new Dimension(pm.dw,pm.dh));
												cm.createPort(pm, n3d);
												pm.setLocation(new Point(pm.x,pm.y));
												ports.add(pm);

											}
										}
										else{
											for(int i=0;i<aprts.size();i++){

												PortModel pm = (PortModel)aprts.get(i);
												pm.setPortContainerModel(cm);
												pm.setSize(new Dimension(pm.w,pm.h));
												pm.setPtDifference(new Dimension(pm.dw,pm.dh));
												cm.createPort(pm, n3d);
												pm.setLocation(new Point(pm.x,pm.y));
												ports.add(pm);


											}
											cm.uMLDataModel.setElementProperty("port", ports);
										}
									}
								}

								if (child instanceof TextAttachModel) {
									TextAttachModel ipc = (TextAttachModel)child;
									ipc.addTextAttachParentDiagram(n3d, null);
								}

								this.viewStore.put(view_id, child);
							}






							//20080811IJS 끝

							if(um!=null){

								this.factory.setN3d(n3d);
								if(ProjectManager.getInstance()!=null && ProjectManager.getInstance().getUMLEditor()!=null && ProjectManager.getInstance().getUMLEditor().getDiagram() !=null){

								}
								Object core = null;
								if(um instanceof AtomicComponentModel){
									AtomicComponentModel atm = (AtomicComponentModel)um;
//									atm.tmp_core_id
									core = this.modelStore.get(atm.tmp_core_id);
									if(core instanceof UMLTreeModel){
										
//										atm.addOPRoS(core.get)
										if(um!=((UMLTreeModel)core).getRefModel()){
											AtomicComponentModel atm1 = (AtomicComponentModel)((UMLTreeModel)core).getRefModel();
											AtomicComponentModel atm2 = (AtomicComponentModel)um;
											atm2.addOPRoS(atm1.getClassModel(),atm1.getCmpFolder());
											
										}
									}
								}
								UMLModel child = this.factory.createModle(um);
								
								if(child instanceof ComponentModel){
									ComponentModel cm = (ComponentModel)child;
									if(obj5!=null){
										NodeItemTreeModel nit = (NodeItemTreeModel)obj5;
										cm.setNodeItemModel((NodeItemModel)nit.getRefModel());
									}
									if(um instanceof AtomicComponentModel){
										AtomicComponentModel atm = (AtomicComponentModel)um;
//										atm.tmp_core_id
										core = this.modelStore.get(atm.tmp_core_id);
									
										if(core instanceof UMLTreeModel){
											((AtomicComponentModel)(child)).setCoreUMLTreeModel((UMLTreeModel)core);
//											atm.addOPRoS(core.get)
											if(um==((UMLTreeModel)core).getRefModel()){
												core = null;
											}
										}
										
									}
								}
								this.factory.setN3d(null);
								child.setSize(this.getSize(size));

								//20080721IJS 끝
								child.setLocation(this.getLocation(location));

								//PKY 08051401 S 컨테이너가 포함된 객체 저장 불러올경우 컨테이너를 두번 추가할 수 있는 문제
								if(container!=null)
									child.uMLDataModel.setElementProperty("Container", container);
								//PKY 08051401 E 컨테이너가 포함된 객체 저장 불러올경우 컨테이너를 두번 추가할 수 있는 문제

								//PKY 08060201 S 컬러관련 재 생성할경우 적용안되는문제
								if(dataColor.length>0){
									child.setBackGroundColor(new Color(null,Integer.parseInt(dataColor[0]),Integer.parseInt(dataColor[1]),Integer.parseInt(dataColor[2])));//PKY 08050701 S 색상 저장
									child.getUMLDataModel().setElementProperty("ID_COLOR", child.getBackGroundColor());
								}
								//PKY 08060201 E 컬러관련 재 생성할경우 적용안되는문제

								//PKY 08061101 S StateLifelineModel,Time표시되는 형태를 저장하여 불러와두 동일한 형태로 적용되도록 수정
								
								//PKY 08061101 E StateLifelineModel, 형태를 저장하여 불러와두 동일한 형태로 적용되도록 수정
								n3d.addChild(child);
								up.addN3UMLModelDeleteListener(n3d);
								if(child instanceof ClassifierModel){
									ClassifierModel cm = (ClassifierModel)child;
									//									cm.addContainer(this.boundaryModel);
									if(cm !=null
											&& cm.getBoundaryModel().getChildren().size()>0
											&& cm instanceof PartitionModel){
										PartitionModel pm =(PartitionModel)cm;
										for(int i=0;i<cm.getBoundaryModel().getChildren().size();i++){
											Object obj1 = cm.getBoundaryModel().getChildren().get(i);
											if(obj1 instanceof SubPartitonModel){
												SubPartitonModel sm =(SubPartitonModel)obj1;
												pm.getPm().addPartition(sm);
											}

										}

									}

								}
								
								//PKY 08050701 S Timing Line저장
								if(child instanceof PortContainerModel){
									java.util.ArrayList ports = new java.util.ArrayList();
									PortContainerModel cm = (PortContainerModel)child;
									Object oports = current_UMLDataModel.getElementProperty("ports");
									//									Object obj1 = ()this.modelStore.get(cm.getUMLDataModel().getId());
									if(oports!=null){
										java.util.Vector aprts = (java.util.Vector)oports;
										if(cm instanceof ExceptionModel){
											//											PortModel pm = (PortModel)cm.getPortManager().getPorts().get(0);
											System.out.println("dddd");
											cm.getPortManager().getPorts().remove(0);
											if(aprts.size()==1){
												PortModel pm = (PortModel)aprts.get(0);
												pm.setPortContainerModel(cm);
												pm.setSize(new Dimension(pm.w,pm.h));
												pm.setPtDifference(new Dimension(pm.dw,pm.dh));
												cm.createPort(pm, n3d);
												pm.setLocation(new Point(pm.x,pm.y));
												ports.add(pm);

											}
										}
										else{
											if(core!=null){
												for(int i=0;i<aprts.size();i++){

													PortModel pm = (PortModel)aprts.get(i);
													pm.setPortContainerModel(cm);
													pm.setSize(new Dimension(pm.w,pm.h));
													pm.setPtDifference(new Dimension(pm.dw,pm.dh));
													
													UMLTreeParentModel to1 = (UMLTreeParentModel)core;
													boolean isExist = false;
													PortModel pm1 = null;
													UMLTreeModel ut = null;
													for(int i1=0;i1<to1.getChildren().length;i1++){
														ut = (UMLTreeModel)to1.getChildren()[i1];
														if(ut.getRefModel() instanceof PortModel){
															pm1 =(PortModel)ut.getRefModel(); 
															if(pm.getUMLDataModel().getId().equals(pm1.getUMLDataModel().getId())){

																isExist = true;
																break;
															}
															else if(pm.getName().equals(pm1.getName())){

																isExist = true;
																break;
															}

														}
													}
													cm.createPort(pm, n3d);
													pm.setLocation(new Point(pm.x,pm.y));
													ports.add(pm);
//													if(!isExist){
////														UMLTreeModel port = new UMLTreeModel(pm.getName());
////														to1.addChild(port);
////														port.setRefModel(pm);
//														pm.getElementLabelModel().setTreeModel(port);
//														pm.getElementLabelModel().setPortId(pm.getUMLDataModel().getId());
//
//													}
//													else{
//														ut.setRefModel(pm);
														pm.getElementLabelModel().setTreeModel(ut);
														if(pm1!=null){
															pm.getUMLDataModel().setId(pm1.getUMLDataModel().getId());
															pm.getElementLabelModel().setPortId(pm1.getElementLabelModel().getPortId());
														}
//													}
													//												else{
														//													pm.setElementLabelModel(pm1.getElementLabelModel());
													//													pm.getElementLabelModel().setTreeModel(ut);
													//													pm.setName(ut.getName());
													//												}








												}
											}
											else{
											
											for(int i=0;i<aprts.size();i++){

												PortModel pm = (PortModel)aprts.get(i);
												pm.setPortContainerModel(cm);
												pm.setSize(new Dimension(pm.w,pm.h));
												pm.setPtDifference(new Dimension(pm.dw,pm.dh));
												
												UMLTreeParentModel to1 = (UMLTreeParentModel)cm.getUMLTreeModel();
												boolean isExist = false;
												PortModel pm1 = null;
												UMLTreeModel ut = null;
												for(int i1=0;i1<to1.getChildren().length;i1++){
													ut = (UMLTreeModel)to1.getChildren()[i1];
													if(ut.getRefModel() instanceof PortModel){
														pm1 =(PortModel)ut.getRefModel(); 
														if(pm.getUMLDataModel().getId().equals(pm1.getUMLDataModel().getId())){

															isExist = true;
															break;
														}
														else if(pm.getName().equals(pm1.getName())){

															isExist = true;
															break;
														}

													}
												}
												cm.createPort(pm, n3d);
												pm.setLocation(new Point(pm.x,pm.y));
												ports.add(pm);
												if(!isExist){
													UMLTreeModel port = new UMLTreeModel(pm.getName());
													to1.addChild(port);
													port.setRefModel(pm);
													pm.getElementLabelModel().setTreeModel(port);
													pm.getElementLabelModel().setPortId(pm.getUMLDataModel().getId());

												}
												else{
													ut.setRefModel(pm);
													pm.getElementLabelModel().setTreeModel(ut);
													pm.getElementLabelModel().setPortId(pm.getUMLDataModel().getId());
													pm.getElementLabelModel().setPortId(pm1.getElementLabelModel().getPortId());	
												}
												//												else{
													//													pm.setElementLabelModel(pm1.getElementLabelModel());
												//													pm.getElementLabelModel().setTreeModel(ut);
												//													pm.setName(ut.getName());
												//												}








											}
											}
											cm.uMLDataModel.setElementProperty("port", ports);
										}
									}
								}

								if (child instanceof TextAttachModel) {
									TextAttachModel ipc = (TextAttachModel)child;
									ipc.addTextAttachParentDiagram(n3d, null);
								}

								this.viewStore.put(view_id, child);
							}
						}
						
					}
				}
			}
			else if(objectTypes_begin==-1 && inModelType==-1){
				if(current_model_type==2000 ){
					this.boundaryParentID = current_UMLDataModel.getProperty("parentID");
				}
				else if(current_model_type>=1000 && current_model_type!=2000){
					createLineModel(current_model_type,current_UMLDataModel,viewStore);

				}
				else if(current_model_type==0 ){

					if(this.isBoundary){

					}
					else{


						String id = current_UMLDataModel.getProperty("ID");
						//20080811IJS 시작
						String link = current_UMLDataModel.getProperty("Link");
						String path = current_UMLDataModel.getProperty("LinkPath");

						if(this.linkPackage!=null
								&& ((FinalPackageModel)this.linkPackage.getRefModel()).getUMLDataModel().getId().equals(id)){
							modelStore.put(id , this.linkPackage);
							ProjectManager.getInstance().addTypeModel(this.linkPackage);
						}
						else{
							current_UMLDataModel.setId(id);
							String name = current_UMLDataModel.getProperty("ID_NAME");
							String parentID = current_UMLDataModel.getProperty("parentID");
							String desc =current_UMLDataModel.getProperty("ID_DESCRIPTION");//2008043001 PKY STag 바운리더,패키지 저장안되는문제
							String stereo = current_UMLDataModel.getProperty("ID_STEREOTYPE");//PKY 08060201 S

							//PKY 08060201 S
							String scope = current_UMLDataModel.getProperty("ID_SCOPE");
							String precondition = current_UMLDataModel.getProperty("ID_PRECONDITION");
							String postcondition = current_UMLDataModel.getProperty("ID_POSTCONDITION");
							String readonly = current_UMLDataModel.getProperty("ID_READONLY");
							String parametername = current_UMLDataModel.getProperty("ID_PARAMETERNAME");
							String multi = current_UMLDataModel.getProperty("ID_MULTI");
							String singleexecution = current_UMLDataModel.getProperty("ID_SINGLEEXECUTION");
							String type = current_UMLDataModel.getProperty("ID_TYPE");
							//PKY 08060201 E

							java.util.ArrayList tag = new java.util.ArrayList();
							Object obj= current_UMLDataModel.getElementProperty("ID_TAG");
							if(obj!=null && obj instanceof java.util.ArrayList){
								tag = (java.util.ArrayList)obj;

							}
							//2008043001 PKY E
							PackageTreeModel  to1 = null;
							if("NodeRootModel".equals(type)){
								to1 = new NodeRootTreeModel("Robots");
								ProjectManager.getInstance().setNodeRootTreeModel((NodeRootTreeModel)to1);
							}
							else if("NodePackageModel".equals(type)){
								to1 = new NodePackageTreeModel(name);
							}
							else if("ComponentEditModel".equals(type)){
								to1 = new RootCmpEdtTreeModel("Component Editing");
//								 ComponentEditModel npm = new ComponentEditModel();
////								 npm.set
//
//								 npm.setName("Component Editing");
//
//								 to1.setRefModel(npm);
//
//								 npm.setTreeModel(rcetm);
							}
							else if("ComponentFinishModel".equals(type)){
								to1 = new RootCmpFnshTreeModel("ComponentFinishModel");
							}
							else
							  to1 = new PackageTreeModel(name);
							to1.setLinkPath(path);
							if("true".equals(link)){
								to1.setLink(true);
								to1.setLinkPath(path);
								to1.setIsLinkType(0);
								TeamProjectManager.getInstance().addLink(to1, path);
							}
							else if(TeamProjectManager.getInstance().isAddLink()){
								to1.setLink(true);
								to1.setLinkPath(TeamProjectManager.getInstance().getAddLinkPath());
								to1.setIsLinkType(1);
								TeamProjectManager.getInstance().addLink(to1, TeamProjectManager.getInstance().getAddLinkPath());
								//								TeamProjectManager.getInstance().setAddLink(false);
							}
							UMLModel finalPackageModel = null;
							if("NodeRootModel".equals(type)){
								finalPackageModel = new NodeRootModel();
//								ProjectManager.getInstance().setNodeRootTreeModel((NodeRootTreeModel)to1);
							}
							else if("NodePackageModel".equals(type)){
								finalPackageModel = new NodePackageModel();
							}
							else if("ComponentEditModel".equals(type)){
								finalPackageModel = new ComponentEditModel();
							}
							else
							finalPackageModel = new FinalPackageModel();

							finalPackageModel.getUMLDataModel().setId(id);
							finalPackageModel.getUMLDataModel().setProperty("ID_TYPE", type);
							to1.setRefModel(finalPackageModel);
							finalPackageModel.setName(name);
							if(desc!=null)//PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제						
								finalPackageModel.setDesc(desc.replaceAll("/n", "\n"));//2008043001 PKY STag 바운리더,패키지 저장안되는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제	
							if(tag.size()>0)
								finalPackageModel.setTags(tag);
							finalPackageModel.setTreeModel(to1);
							//PKY 08090101 S 모델 브라우져 패키지 Enable에러 문제
							if("true".equals(link)){
								finalPackageModel.setExistModel(false);
								finalPackageModel.setReadOnlyModel(false);
							}
							//PKY 08090101 E 모델 브라우져 패키지 Enable에러 문제

							//PKY 08060201 S
							if(stereo!=null&&stereo.trim().length()>0&&!stereo.trim().equals("")){
								finalPackageModel.setStereotype(stereo);
							}

							if(scope!=null&&scope.trim().length()>0&&!scope.trim().equals("")){
								finalPackageModel.setScope(scope);
							}

							if(precondition!=null&&precondition.trim().length()>0&&!precondition.trim().equals("")){
								finalPackageModel.setPreCondition(precondition);
							}
							if(postcondition!=null&&postcondition.trim().length()>0&&!postcondition.trim().equals("")){
								finalPackageModel.setPostCondition(postcondition);
							}
							if(readonly!=null&&readonly.trim().length()>0&&!readonly.trim().equals("")){
								finalPackageModel.setReadOnly(readonly);
							}
							if(parametername!=null&&parametername.trim().length()>0&&!parametername.trim().equals("")){
								finalPackageModel.setParameterName(parametername);
							}
							if(multi!=null&&multi.trim().length()>0&&!multi.trim().equals("")){
								finalPackageModel.setMultiplicity(multi);
							}
							if(singleexecution!=null&&singleexecution.trim().length()>0&&!singleexecution.trim().equals("")){
								finalPackageModel.setSingleExecution(singleexecution);
							}
							//PKY 08060201 E
							Object parentObject = modelStore.get(parentID);
							if(parentObject==null){
								//20080811IJS 시작
								if(TeamProjectManager.getInstance().isAddLink()){
									to1.setParent(TeamProjectManager.getInstance().getParent());
									TeamProjectManager.getInstance().getParent().addChild(to1);
									TeamProjectManager.getInstance().setAddLink(false);
								}
								else{
//									to1.setParent(root);
//									root.addChild(to1);
									if(to1.getRefModel() instanceof FinalPackageModel){
										FinalPackageModel fm = (FinalPackageModel)to1.getRefModel();
										if("Application".equals(fm.getUMLDataModel().getProperty("ID_TYPE"))){
											if(ProjectManager.getInstance().getModelBrowser()!=null){
												N3ViewContentProvider n3ViewContentProvider= ProjectManager.getInstance().getModelBrowser().getN3ViewContentProvider();
												to1.setParent(n3ViewContentProvider.getRcctm());
												n3ViewContentProvider.getRcctm().addChild(to1);
												
											}
										}
										else{
											to1.setParent(root);
											root.addChild(to1);
										}
									}
									else{
										to1.setParent(root);
										root.addChild(to1);
									}
								}
								//20080811IJS 끝
							}
							else{
								to1.setParent((UMLTreeParentModel)parentObject);
								UMLTreeParentModel up= (UMLTreeParentModel)parentObject;
								if (to1 != null) {
									up.addChild(to1);
								}
							}
							modelStore.put(id , to1);


							ProjectManager.getInstance().addTypeModel(to1);//PKY 08052901 S 저장 불러오기할경우 참조타입정보가 없다 참조타입정보를 넣어 줄수 있도록 수정
						}
						//20080811IJS 끝
					}

				}
				else if(current_model_type==2){
					//2008043001 PKY S  컨테이너 안에 객체를넣고 저장할경우 사라지는문제

					if(this.isBoundary){

					}
					else{
						String id = current_UMLDataModel.getProperty("ID");
						String name = current_UMLDataModel.getProperty("ID_NAME");
						String parentID = current_UMLDataModel.getProperty("parentID");
						String desc = current_UMLDataModel.getProperty("ID_DESCRIPTION");//2008043001 PKY S 속성 저장되지 않는문제
						String container = current_UMLDataModel.getProperty("Container");//PKY 08051401 S 컨테이너가 포함된 객체 저장 불러올경우 컨테이너를 두번 추가할 수 있는 문제
						java.util.ArrayList tag = new java.util.ArrayList();//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
						Object obj=current_UMLDataModel.getElementProperty("ID_TAG");
						//PKY 08060201 S
						String stereo = current_UMLDataModel.getProperty("ID_STEREOTYPE");
						String scope = current_UMLDataModel.getProperty("ID_SCOPE");
						String precondition = current_UMLDataModel.getProperty("ID_PRECONDITION");
						String postcondition = current_UMLDataModel.getProperty("ID_POSTCONDITION");
						String readonly = current_UMLDataModel.getProperty("ID_READONLY");
						String parametername = current_UMLDataModel.getProperty("ID_PARAMETERNAME");
						String multi = current_UMLDataModel.getProperty("ID_MULTI");
						String singleexecution = current_UMLDataModel.getProperty("ID_SINGLEEXECUTION");
						String requirment = current_UMLDataModel.getProperty("ID_REQ_ID");//PKY 08072401 S Requirement 저장 불러오기 로직 추가
						//PKY 08081801 S 오퍼레이션 어트리뷰트 숨길수 있는 기능
						String attrHidden = current_UMLDataModel.getProperty("ID_ATTR_HIDDEN");
						String operHidden = current_UMLDataModel.getProperty("ID_OPER_HIDDEN");
						//PKY 08081801 E 오퍼레이션 어트리뷰트 숨길수 있는 기능
						//PKY 08060201 E

						if(obj!=null && obj instanceof java.util.ArrayList){
							tag = (java.util.ArrayList)obj;

						}
						//2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
						StrcuturedPackageTreeModel  to1 = new StrcuturedPackageTreeModel(name);
						UMLModel umlModel = null;
						umlModel = new UseCaseModel(current_UMLDataModel,true);
						to1.setRefModel(umlModel);
						umlModel.setName(name);
						if(desc!=null)//PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제						
							umlModel.setDesc(desc.replaceAll("/n", "\n"));//2008043001 PKY STag 바운리더,패키지 저장안되는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제	
						//						umlModel.setDesc(desc);//2008043001 PKY S 속성 저장되지 않는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제

						//PKY 08051401 S 컨테이너가 포함된 객체 저장 불러올경우 컨테이너를 두번 추가할 수 있는 문제
						if(container!=null)
							umlModel.uMLDataModel.setElementProperty("Container", container);
						//PKY 08051401 E 컨테이너가 포함된 객체 저장 불러올경우 컨테이너를 두번 추가할 수 있는 문제

						//PKY 08072401 S Requirement 저장 불러오기 로직 추가
						if(requirment!=null&&requirment.trim().length()>0&&!requirment.trim().equals("")){
							umlModel.setReqID(requirment);
						}

						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
						if(tag.size()>0)
							umlModel.setTags(tag);
						//2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
						umlModel.setTreeModel(to1);
						//PKY 08060201 S
						//PKY 08081801 S 오퍼레이션 어트리뷰트 숨길수 있는 기능
						if(attrHidden!=null&&attrHidden.trim().length()>0&&!attrHidden.trim().equals("")&&attrHidden.equals("1"))
							umlModel.setShowAttr(attrHidden);
						if(operHidden!=null&&operHidden.trim().length()>0&&!operHidden.trim().equals("")&&operHidden.equals("1"))
							umlModel.setShowOper(operHidden);
						//PKY 08081801 E 오퍼레이션 어트리뷰트 숨길수 있는 기능
						if(stereo!=null&&stereo.trim().length()>0&&!stereo.trim().equals("")){
							umlModel.setStereotype(stereo);
						}

						if(scope!=null&&scope.trim().length()>0&&!scope.trim().equals("")){
							umlModel.setScope(scope);
						}

						if(precondition!=null&&precondition.trim().length()>0&&!precondition.trim().equals("")){
							umlModel.setPreCondition(precondition);
						}
						if(postcondition!=null&&postcondition.trim().length()>0&&!postcondition.trim().equals("")){
							umlModel.setPostCondition(postcondition);
						}
						if(readonly!=null&&readonly.trim().length()>0&&!readonly.trim().equals("")){
							umlModel.setReadOnly(readonly);
						}
						if(parametername!=null&&parametername.trim().length()>0&&!parametername.trim().equals("")){
							umlModel.setParameterName(parametername);
						}
						if(multi!=null&&multi.trim().length()>0&&!multi.trim().equals("")){
							umlModel.setMultiplicity(multi);
						}
						if(singleexecution!=null&&singleexecution.trim().length()>0&&!singleexecution.trim().equals("")){
							umlModel.setSingleExecution(singleexecution);
						}
						//PKY 08060201 E
						Object parentObject = modelStore.get(parentID);
						if(parentObject==null){
							to1.setParent(root);
							root.addChild(to1);
						}
						else{
							to1.setParent((UMLTreeParentModel)parentObject);
							UMLTreeParentModel up= (UMLTreeParentModel)parentObject;
							if (to1 != null) {
								up.addChild(to1);
							}
						}
						modelStore.put(id, to1);
						ProjectManager.getInstance().addTypeModel(to1);//PKY 08052901 S 저장 불러오기할경우 참조타입정보가 없다 참조타입정보를 넣어 줄수 있도록 수정
					}
					//2008043001 PKY E 컨테이너 안에 객체를넣고 저장할경우 사라지는문제


				}
				else if(current_model_type==3){
					if(this.isBoundary){

					}
					else{
						String id = current_UMLDataModel.getProperty("ID");
						String name = current_UMLDataModel.getProperty("ID_NAME");
						String parentID = current_UMLDataModel.getProperty("parentID");

						String stere = current_UMLDataModel.getProperty("ID_STEREOTYPE");
						String scope = current_UMLDataModel.getProperty("ID_SCOPE");
						String multi = current_UMLDataModel.getProperty("ID_MULTI");
						String desc = current_UMLDataModel.getProperty("ID_DESCRIPTION");
						String actorImg = current_UMLDataModel.getProperty("ID_ACTORIMAGE");//PKY 08060201 S 

						//PKY 08060201 S
						String stereo = current_UMLDataModel.getProperty("ID_STEREOTYPE");
						String precondition = current_UMLDataModel.getProperty("ID_PRECONDITION");
						String postcondition = current_UMLDataModel.getProperty("ID_POSTCONDITION");
						String readonly = current_UMLDataModel.getProperty("ID_READONLY");
						String parametername = current_UMLDataModel.getProperty("ID_PARAMETERNAME");
						String singleexecution = current_UMLDataModel.getProperty("ID_SINGLEEXECUTION");
						String requirment = current_UMLDataModel.getProperty("ID_REQ_ID");//PKY 08072401 S Requirement 저장 불러오기 로직 추가
						//PKY 08060201 E

						String ID_COLOR = current_UMLDataModel.getProperty("ID_COLOR");

						//2008043001 PKY S 속성 저장되지 않는문제
						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
						java.util.ArrayList tag = new java.util.ArrayList();
						Object obj1=current_UMLDataModel.getElementProperty("ID_TAG");
						if(obj1!=null && obj1 instanceof java.util.ArrayList){
							tag = (java.util.ArrayList)obj1;

						}
						//2008043001 PKY E Tag 바운리더,패키지 저장안되는문제


						UMLTreeModel  to1 = new UMLTreeModel(name);
						UMLModel umlModel = null;
						umlModel = new FinalActorModel();

						to1.setRefModel(umlModel);
						umlModel.setName(name);
						umlModel.setTreeModel(to1);
						//PKY 08061101 S ACTOR COLOR 불러오기 할 경우 마우스로 클릭해야 변경되는문제
						if(ID_COLOR!=null&&!ID_COLOR.trim().equals("")){
							String[] dataColor=ID_COLOR.split(",");//PKY 08050701 S 색상 저장
							if(dataColor.length>0){
								umlModel.setBackGroundColor(new Color(null,Integer.parseInt(dataColor[0]),Integer.parseInt(dataColor[1]),Integer.parseInt(dataColor[2])));//PKY 08050701 S 색상 저장
								umlModel.getUMLDataModel().setElementProperty("ID_COLOR", umlModel.getBackGroundColor());
							}
						}
						//PKY 08061101 E ACTOR COLOR 불러오기 할 경우 마우스로 클릭해야 변경되는문제
						//PKY 08060201 E 컬러관련 재 생성할경우 적용안되는문제
						if(desc!=null)//PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제						
							umlModel.setDesc(desc.replaceAll("/n", "\n"));//2008043001 PKY STag 바운리더,패키지 저장안되는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제	
						//						umlModel.setDesc(desc);//2008043001 PKY S 속성 저장되지 않는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제
						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
						if(tag.size()>0)
							umlModel.setTags(tag);
						//						2008043001 PKY E Tag 바운리더,패키지 저장안되는문제

						//PKY 08051401 S  Actor 이미지 저장안되는문제
						if(actorImg!=null){
							umlModel.setActorImage(actorImg);
							//							umlModel.uMLDataModel.setElementProperty("ID_ACTORIMAGE", actorImg);
						}
						//PKY 08051401 E  Actor 이미지 저장안되는문제

						Object parentObject = modelStore.get(parentID);
						if(parentObject==null){
							to1.setParent(root);
							root.addChild(to1);
						}
						else{
							to1.setParent((UMLTreeParentModel)parentObject);
							UMLTreeParentModel up= (UMLTreeParentModel)parentObject;
							if (to1 != null) {
								up.addChild(to1);
							}
						}
						//PKY 08072401 S Requirement 저장 불러오기 로직 추가
						if(requirment!=null&&requirment.trim().length()>0&&!requirment.trim().equals("")){
							umlModel.setReqID(requirment);
						}
						if(stere!=null && stere.trim().length()>0){
							umlModel.setStereotype(stere);
						}
						if(scope!=null && scope.trim().length()>0){
							umlModel.setScope(scope);
						}
						if(multi!=null && multi.trim().length()>0){
							umlModel.setMultiplicity(multi);
						}
						if(desc!=null && desc.trim().length()>0){
							umlModel.setDesc(desc.replaceAll("/n", "\n"));//PKY 08080501 S 객체 개행안되는문제 수정
						}
						//PKY 08060201 S
						if(stereo!=null&&stereo.trim().length()>0&&!stereo.trim().equals("")){
							umlModel.setStereotype(stereo);
						}

						if(scope!=null&&scope.trim().length()>0&&!scope.trim().equals("")){
							umlModel.setScope(scope);
						}

						if(precondition!=null&&precondition.trim().length()>0&&!precondition.trim().equals("")){
							umlModel.setPreCondition(precondition);
						}
						if(postcondition!=null&&postcondition.trim().length()>0&&!postcondition.trim().equals("")){
							umlModel.setPostCondition(postcondition);
						}
						if(readonly!=null&&readonly.trim().length()>0&&!readonly.trim().equals("")){
							umlModel.setReadOnly(readonly);
						}
						if(parametername!=null&&parametername.trim().length()>0&&!parametername.trim().equals("")){
							umlModel.setParameterName(parametername);
						}
						if(multi!=null&&multi.trim().length()>0&&!multi.trim().equals("")){
							umlModel.setMultiplicity(multi);
						}
						if(singleexecution!=null&&singleexecution.trim().length()>0&&!singleexecution.trim().equals("")){
							umlModel.setSingleExecution(singleexecution);
						}
						//PKY 08060201 E

						Object obj = current_UMLDataModel.getElementProperty("ID_DETAIL");
						if(obj!=null && obj instanceof java.util.ArrayList){
							java.util.ArrayList arrest = (java.util.ArrayList)obj;
							if(arrest.size()>0){
								umlModel.setDetailProperty(arrest);
							}
						}
						obj = current_UMLDataModel.getElementProperty("ID_TAG");
						if(obj!=null && obj instanceof java.util.ArrayList){
							java.util.ArrayList arrest = (java.util.ArrayList)obj;
							if(arrest.size()>0){
								umlModel.setTags(arrest);
							}
						}
						obj = current_UMLDataModel.getElementProperty("ID_EXTENSIONPOINT");
						if(obj!=null && obj instanceof java.util.ArrayList){
							java.util.ArrayList arrest = (java.util.ArrayList)obj;
							if(arrest.size()>0){
								umlModel.setExtendsPoints(arrest);
							}
						}
						obj = current_UMLDataModel.getElementProperty("ATTR_A");
						if(obj!=null && obj instanceof java.util.ArrayList){
							java.util.ArrayList arrest = (java.util.ArrayList)obj;
							if(arrest.size()>0){

							}
						}
						modelStore.put(id, to1);
						ProjectManager.getInstance().addTypeModel(to1);//PKY 08052901 S 저장 불러오기할경우 참조타입정보가 없다 참조타입정보를 넣어 줄수 있도록 수정
					}
				}
				else if(current_model_type==4){
					if(this.isBoundary){

					}
					else{
						String id = current_UMLDataModel.getProperty("ID");
						String name = current_UMLDataModel.getProperty("ID_NAME");
						String parentID = current_UMLDataModel.getProperty("parentID");
						String desc = current_UMLDataModel.getProperty("ID_DESCRIPTION");
						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제

						//PKY 08060201 S
						String stereo = current_UMLDataModel.getProperty("ID_STEREOTYPE");
						String scope = current_UMLDataModel.getProperty("ID_SCOPE");
						String precondition = current_UMLDataModel.getProperty("ID_PRECONDITION");
						String postcondition = current_UMLDataModel.getProperty("ID_POSTCONDITION");
						String readonly = current_UMLDataModel.getProperty("ID_READONLY");
						String parametername = current_UMLDataModel.getProperty("ID_PARAMETERNAME");
						String multi = current_UMLDataModel.getProperty("ID_MULTI");
						String singleexecution = current_UMLDataModel.getProperty("ID_SINGLEEXECUTION");
						String requirment = current_UMLDataModel.getProperty("ID_REQ_ID");//PKY 08072401 S Requirement 저장 불러오기 로직 추가
						//PKY 08060201 E

						java.util.ArrayList tag = new java.util.ArrayList();
						Object obj=current_UMLDataModel.getElementProperty("ID_TAG");
						if(obj!=null && obj instanceof java.util.ArrayList){
							tag = (java.util.ArrayList)obj;

						}
						//2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
						UMLTreeModel  to1 = new UMLTreeModel(name);
						UMLModel umlModel = null;
						umlModel = new FinalBoundryModel();
						to1.setRefModel(umlModel);
						umlModel.setName(name);
						umlModel.setTreeModel(to1);

						//PKY 08072401 S Requirement 저장 불러오기 로직 추가
						if(requirment!=null&&requirment.trim().length()>0&&!requirment.trim().equals("")){
							umlModel.setReqID(requirment);
						}

						//PKY 08060201 S
						if(stereo!=null&&stereo.trim().length()>0&&!stereo.trim().equals("")){
							umlModel.setStereotype(stereo);
						}

						if(scope!=null&&scope.trim().length()>0&&!scope.trim().equals("")){
							umlModel.setScope(scope);
						}

						if(precondition!=null&&precondition.trim().length()>0&&!precondition.trim().equals("")){
							umlModel.setPreCondition(precondition);
						}
						if(postcondition!=null&&postcondition.trim().length()>0&&!postcondition.trim().equals("")){
							umlModel.setPostCondition(postcondition);
						}
						if(readonly!=null&&readonly.trim().length()>0&&!readonly.trim().equals("")){
							umlModel.setReadOnly(readonly);
						}
						if(parametername!=null&&parametername.trim().length()>0&&!parametername.trim().equals("")){
							umlModel.setParameterName(parametername);
						}
						if(multi!=null&&multi.trim().length()>0&&!multi.trim().equals("")){
							umlModel.setMultiplicity(multi);
						}
						if(singleexecution!=null&&singleexecution.trim().length()>0&&!singleexecution.trim().equals("")){
							umlModel.setSingleExecution(singleexecution);
						}
						//PKY 08060201 E

						if(desc!=null)//PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제						
							umlModel.setDesc(desc.replaceAll("/n", "\n"));//2008043001 PKY STag 바운리더,패키지 저장안되는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제	
						//						umlModel.setDesc(desc);//2008043001 PKY S 속성 저장되지 않는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제
						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
						if(tag.size()>0)
							umlModel.setTags(tag);
						//2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
						Object parentObject = modelStore.get(parentID);
						if(parentObject==null){
							to1.setParent(root);
							root.addChild(to1);
						}
						else{
							to1.setParent((UMLTreeParentModel)parentObject);
							UMLTreeParentModel up= (UMLTreeParentModel)parentObject;
							if (to1 != null) {
								up.addChild(to1);
							}
						}
						modelStore.put(id, to1);
						ProjectManager.getInstance().addTypeModel(to1);//PKY 08052901 S 저장 불러오기할경우 참조타입정보가 없다 참조타입정보를 넣어 줄수 있도록 수정
					}
				}
				else if(current_model_type==5){
					if(this.isBoundary){

					}
					else{
						String id = current_UMLDataModel.getProperty("ID");
						String name = current_UMLDataModel.getProperty("ID_NAME");
						String parentID = current_UMLDataModel.getProperty("parentID");
						String desc = current_UMLDataModel.getProperty("ID_DESCRIPTION");//2008043001 PKY S 속성 저장되지 않는문제
						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
						//PKY 08060201 S
						String stereo = current_UMLDataModel.getProperty("ID_STEREOTYPE");
						String scope = current_UMLDataModel.getProperty("ID_SCOPE");
						String precondition = current_UMLDataModel.getProperty("ID_PRECONDITION");
						String postcondition = current_UMLDataModel.getProperty("ID_POSTCONDITION");
						String readonly = current_UMLDataModel.getProperty("ID_READONLY");
						String parametername = current_UMLDataModel.getProperty("ID_PARAMETERNAME");
						String multi = current_UMLDataModel.getProperty("ID_MULTI");
						String singleexecution = current_UMLDataModel.getProperty("ID_SINGLEEXECUTION");
						String requirment = current_UMLDataModel.getProperty("ID_REQ_ID");//PKY 08072401 S Requirement 저장 불러오기 로직 추가
						//PKY 08081801 S 오퍼레이션 어트리뷰트 숨길수 있는 기능
						String attrHidden = current_UMLDataModel.getProperty("ID_ATTR_HIDDEN");
						String operHidden = current_UMLDataModel.getProperty("ID_OPER_HIDDEN");
						//PKY 08081801 E 오퍼레이션 어트리뷰트 숨길수 있는 기능
						//PKY 08060201 E
						java.util.ArrayList tag = new java.util.ArrayList();
						Object obj=current_UMLDataModel.getElementProperty("ID_TAG");
						if(obj!=null && obj instanceof java.util.ArrayList){
							tag = (java.util.ArrayList)obj;

						}
						//2008043001 PKY E Tag 바운리더,패키지 저장안되는문제

						StrcuturedPackageTreeModel  to1 = new StrcuturedPackageTreeModel(name);
						UMLModel umlModel = null;
						umlModel = new CollaborationModel(current_UMLDataModel,true);//PKY 08051401 S 저장 불러올 경우 Class로 변경되어있는문제 

						to1.setRefModel(umlModel);
						//PKY 08072401 S Requirement 저장 불러오기 로직 추가
						if(name!=null&&name.trim().length()>0&&!name.trim().equals(""))
							umlModel.setName(name);
						umlModel.setTreeModel(to1);
						if(desc!=null&&desc.trim().length()>0&&!desc.trim().equals(""))
							umlModel.setDesc(desc.replaceAll("/n", "\n"));//PKY 08080501 S 객체 개행안되는문제 수정

						if(requirment!=null&&requirment.trim().length()>0&&!requirment.trim().equals("")){
							umlModel.setReqID(requirment);
						}
						//PKY 08072401 E Requirement 저장 불러오기 로직 추가
						//PKY 08081801 S 오퍼레이션 어트리뷰트 숨길수 있는 기능
						if(attrHidden!=null&&attrHidden.trim().length()>0&&!attrHidden.trim().equals("")&&attrHidden.equals("1"))
							umlModel.setShowAttr(attrHidden);
						if(operHidden!=null&&operHidden.trim().length()>0&&!operHidden.trim().equals("")&&operHidden.equals("1"))
							umlModel.setShowOper(operHidden);
						//PKY 08081801 E 오퍼레이션 어트리뷰트 숨길수 있는 기능
						//PKY 08060201 S
						if(stereo!=null&&stereo.trim().length()>0&&!stereo.trim().equals("")){
							umlModel.setStereotype(stereo);
						}

						if(scope!=null&&scope.trim().length()>0&&!scope.trim().equals("")){
							umlModel.setScope(scope);
						}

						if(precondition!=null&&precondition.trim().length()>0&&!precondition.trim().equals("")){
							umlModel.setPreCondition(precondition);
						}
						if(postcondition!=null&&postcondition.trim().length()>0&&!postcondition.trim().equals("")){
							umlModel.setPostCondition(postcondition);
						}
						if(readonly!=null&&readonly.trim().length()>0&&!readonly.trim().equals("")){
							umlModel.setReadOnly(readonly);
						}
						if(parametername!=null&&parametername.trim().length()>0&&!parametername.trim().equals("")){
							umlModel.setParameterName(parametername);
						}
						if(multi!=null&&multi.trim().length()>0&&!multi.trim().equals("")){
							umlModel.setMultiplicity(multi);
						}
						if(singleexecution!=null&&singleexecution.trim().length()>0&&!singleexecution.trim().equals("")){
							umlModel.setSingleExecution(singleexecution);
						}
						//PKY 08060201 E
						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
						if(tag.size()>0)
							umlModel.setTags(tag);
						//2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
						Object parentObject = modelStore.get(parentID);
						if(parentObject==null){
							to1.setParent(root);
							root.addChild(to1);
						}
						else{
							to1.setParent((UMLTreeParentModel)parentObject);
							UMLTreeParentModel up= (UMLTreeParentModel)parentObject;
							if (to1 != null) {
								up.addChild(to1);
							}
						}
						modelStore.put(id, to1);
						ProjectManager.getInstance().addTypeModel(to1);//PKY 08052901 S 저장 불러오기할경우 참조타입정보가 없다 참조타입정보를 넣어 줄수 있도록 수정
					}
				}
				else if(current_model_type==6){
					if(this.isBoundary){

					}
					else{
						String id = current_UMLDataModel.getProperty("ID");
						String name = current_UMLDataModel.getProperty("ID_NAME");
						String parentID = current_UMLDataModel.getProperty("parentID");
						String desc = current_UMLDataModel.getProperty("ID_DESCRIPTION");//2008043001 PKY S 속성 저장되지 않는문제
						//PKY 08060201 S
						String stereo = current_UMLDataModel.getProperty("ID_STEREOTYPE");
						String scope = current_UMLDataModel.getProperty("ID_SCOPE");
						String precondition = current_UMLDataModel.getProperty("ID_PRECONDITION");
						String postcondition = current_UMLDataModel.getProperty("ID_POSTCONDITION");
						String readonly = current_UMLDataModel.getProperty("ID_READONLY");
						String parametername = current_UMLDataModel.getProperty("ID_PARAMETERNAME");
						String multi = current_UMLDataModel.getProperty("ID_MULTI");
						String singleexecution = current_UMLDataModel.getProperty("ID_SINGLEEXECUTION");
						String requirment = current_UMLDataModel.getProperty("ID_REQ_ID");//PKY 08072401 S Requirement 저장 불러오기 로직 추가
						//PKY 08081801 S 오퍼레이션 어트리뷰트 숨길수 있는 기능
						String attrHidden = current_UMLDataModel.getProperty("ID_ATTR_HIDDEN");
						String operHidden = current_UMLDataModel.getProperty("ID_OPER_HIDDEN");
						//PKY 08081801 E 오퍼레이션 어트리뷰트 숨길수 있는 기능

						//PKY 08060201 E
						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
						java.util.ArrayList tag = new java.util.ArrayList();
						Object obj=current_UMLDataModel.getElementProperty("ID_TAG");
						if(obj!=null && obj instanceof java.util.ArrayList){
							tag = (java.util.ArrayList)obj;

						}
						//2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
						StrcuturedPackageTreeModel  to1 = new StrcuturedPackageTreeModel(name);
						UMLModel umlModel = null;
						umlModel = new FinalClassModel(current_UMLDataModel,true);
						to1.setRefModel(umlModel);
						umlModel.setName(name);
						umlModel.setTreeModel(to1);
						//PKY 08081801 S 오퍼레이션 어트리뷰트 숨길수 있는 기능
						if(attrHidden!=null&&attrHidden.trim().length()>0&&!attrHidden.trim().equals("")&&attrHidden.equals("1"))
							umlModel.setShowAttr(attrHidden);
						if(operHidden!=null&&operHidden.trim().length()>0&&!operHidden.trim().equals("")&&operHidden.equals("1"))
							umlModel.setShowOper(operHidden);
						//PKY 08081801 E 오퍼레이션 어트리뷰트 숨길수 있는 기능

						//PKY 08072401 S Requirement 저장 불러오기 로직 추가
						if(requirment!=null&&requirment.trim().length()>0&&!requirment.trim().equals("")){
							umlModel.setReqID(requirment);
						}
						//PKY 08060201 S
						if(stereo!=null&&stereo.trim().length()>0&&!stereo.trim().equals("")){
							umlModel.setStereotype(stereo);
						}

						if(scope!=null&&scope.trim().length()>0&&!scope.trim().equals("")){
							umlModel.setScope(scope);
						}

						if(precondition!=null&&precondition.trim().length()>0&&!precondition.trim().equals("")){
							umlModel.setPreCondition(precondition);
						}
						if(postcondition!=null&&postcondition.trim().length()>0&&!postcondition.trim().equals("")){
							umlModel.setPostCondition(postcondition);
						}
						if(readonly!=null&&readonly.trim().length()>0&&!readonly.trim().equals("")){
							umlModel.setReadOnly(readonly);
						}
						if(parametername!=null&&parametername.trim().length()>0&&!parametername.trim().equals("")){
							umlModel.setParameterName(parametername);
						}
						if(multi!=null&&multi.trim().length()>0&&!multi.trim().equals("")){
							umlModel.setMultiplicity(multi);
						}
						if(singleexecution!=null&&singleexecution.trim().length()>0&&!singleexecution.trim().equals("")){
							umlModel.setSingleExecution(singleexecution);
						}
						//PKY 08060201 E
						if(desc!=null)//PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제						
							umlModel.setDesc(desc.replaceAll("/n", "\n"));//2008043001 PKY STag 바운리더,패키지 저장안되는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제	
						//						umlModel.setDesc(desc);//2008043001 PKY S 속성 저장되지 않는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제
						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
						if(tag.size()>0)
							umlModel.setTags(tag);
						//2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
						Object parentObject = modelStore.get(parentID);
						if(parentObject==null){
							to1.setParent(root);
							root.addChild(to1);
						}
						else{
							to1.setParent((UMLTreeParentModel)parentObject);
							UMLTreeParentModel up= (UMLTreeParentModel)parentObject;
							if (to1 != null) {
								up.addChild(to1);
							}
						}
						modelStore.put(id, to1);
						ProjectManager.getInstance().addTypeModel(to1);//PKY 08052901 S 저장 불러오기할경우 참조타입정보가 없다 참조타입정보를 넣어 줄수 있도록 수정
					}

				}
				else if(current_model_type==7){
					if(this.isBoundary){

					}
					else{
						String id = current_UMLDataModel.getProperty("ID");
						String isMode = current_UMLDataModel.getProperty("isMode");
						String name = current_UMLDataModel.getProperty("ID_NAME");
						String parentID = current_UMLDataModel.getProperty("parentID");
						String desc = current_UMLDataModel.getProperty("ID_DESCRIPTION");//2008043001 PKY S 속성 저장되지 않는문제

						//PKY 08060201 S
						String stereo = current_UMLDataModel.getProperty("ID_STEREOTYPE");
						String scope = current_UMLDataModel.getProperty("ID_SCOPE");
						String precondition = current_UMLDataModel.getProperty("ID_PRECONDITION");
						String postcondition = current_UMLDataModel.getProperty("ID_POSTCONDITION");
						String readonly = current_UMLDataModel.getProperty("ID_READONLY");
						String parametername = current_UMLDataModel.getProperty("ID_PARAMETERNAME");
						String multi = current_UMLDataModel.getProperty("ID_MULTI");
						String singleexecution = current_UMLDataModel.getProperty("ID_SINGLEEXECUTION");
						String requirment = current_UMLDataModel.getProperty("ID_REQ_ID");//PKY 08072401 S Requirement 저장 불러오기 로직 추가
						//PKY 08081801 S 오퍼레이션 어트리뷰트 숨길수 있는 기능
						String attrHidden = current_UMLDataModel.getProperty("ID_ATTR_HIDDEN");
						String operHidden = current_UMLDataModel.getProperty("ID_OPER_HIDDEN");
						//PKY 08081801 E 오퍼레이션 어트리뷰트 숨길수 있는 기능
						//PKY 08060201 E
						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
						java.util.ArrayList tag = new java.util.ArrayList();
						Object obj=current_UMLDataModel.getElementProperty("ID_TAG");
						if(obj!=null && obj instanceof java.util.ArrayList){
							tag = (java.util.ArrayList)obj;

						}
						//2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
						StrcuturedPackageTreeModel  to1 = new StrcuturedPackageTreeModel(name);
						UMLModel umlModel = null;
						umlModel = new InterfaceModel(current_UMLDataModel,true);
						to1.setRefModel(umlModel);
						umlModel.setName(name);
						umlModel.setTreeModel(to1);
						//PKY 08081801 S 오퍼레이션 어트리뷰트 숨길수 있는 기능
						if(attrHidden!=null&&attrHidden.trim().length()>0&&!attrHidden.trim().equals("")&&attrHidden.equals("1"))
							umlModel.setShowAttr(attrHidden);
						if(operHidden!=null&&operHidden.trim().length()>0&&!operHidden.trim().equals("")&&operHidden.equals("1"))
							umlModel.setShowOper(operHidden);
						//PKY 08081801 E 오퍼레이션 어트리뷰트 숨길수 있는 기능
						if(desc!=null)//PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제						
							umlModel.setDesc(desc.replaceAll("/n", "\n"));//2008043001 PKY STag 바운리더,패키지 저장안되는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제	
						//						umlModel.setDesc(desc);//2008043001 PKY S 속성 저장되지 않는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제
						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
						if(tag.size()>0)
							umlModel.setTags(tag);
						//2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
						//PKY 08072401 S Requirement 저장 불러오기 로직 추가
						if(requirment!=null&&requirment.trim().length()>0&&!requirment.trim().equals("")){
							umlModel.setReqID(requirment);
						}
						//PKY 08050701 S 불러올때 에러 차장님 수정사항 주석처리 
						if(isMode!=null && (isMode.equals("true")|| isMode.equals("false"))){
							((InterfaceModel)umlModel).setMode(Boolean.parseBoolean(isMode));//PKY 08051401 S 모양이 원으로 변경됨
						}
						//PKY 08050701 E 불러올때 에러 차장님 수정사항 주석처리 

						//PKY 08060201 S
						if(stereo!=null&&stereo.trim().length()>0&&!stereo.trim().equals("")){
							//							umlModel.setStereotype(stereo);//PKY 08081801 S 인터페이스 스트레오타입 에러나는문제

						}

						if(scope!=null&&scope.trim().length()>0&&!scope.trim().equals("")){
							umlModel.setScope(scope);
						}

						if(precondition!=null&&precondition.trim().length()>0&&!precondition.trim().equals("")){
							umlModel.setPreCondition(precondition);
						}
						if(postcondition!=null&&postcondition.trim().length()>0&&!postcondition.trim().equals("")){
							umlModel.setPostCondition(postcondition);
						}
						if(readonly!=null&&readonly.trim().length()>0&&!readonly.trim().equals("")){
							umlModel.setReadOnly(readonly);
						}
						if(parametername!=null&&parametername.trim().length()>0&&!parametername.trim().equals("")){
							umlModel.setParameterName(parametername);
						}
						if(multi!=null&&multi.trim().length()>0&&!multi.trim().equals("")){
							umlModel.setMultiplicity(multi);
						}
						if(singleexecution!=null&&singleexecution.trim().length()>0&&!singleexecution.trim().equals("")){
							umlModel.setSingleExecution(singleexecution);
						}
						//PKY 08060201 E

						Object parentObject = modelStore.get(parentID);
						if(parentObject==null){
							to1.setParent(root);
							root.addChild(to1);
						}
						else{
							to1.setParent((UMLTreeParentModel)parentObject);
							UMLTreeParentModel up= (UMLTreeParentModel)parentObject;
							if (to1 != null) {
								up.addChild(to1);
							}
						}
						modelStore.put(id, to1);
						ProjectManager.getInstance().addTypeModel(to1);//PKY 08052901 S 저장 불러오기할경우 참조타입정보가 없다 참조타입정보를 넣어 줄수 있도록 수정
					}
				}
				else if(current_model_type==8){
					if(this.isBoundary){

					}
					else{
						String id = current_UMLDataModel.getProperty("ID");
						String name = current_UMLDataModel.getProperty("ID_NAME");
						String parentID = current_UMLDataModel.getProperty("parentID");
						String desc = current_UMLDataModel.getProperty("ID_DESCRIPTION");//2008043001 PKY S 속성 저장되지 않는문제
						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
						//PKY 08060201 S
						String stereo = current_UMLDataModel.getProperty("ID_STEREOTYPE");
						String scope = current_UMLDataModel.getProperty("ID_SCOPE");
						String precondition = current_UMLDataModel.getProperty("ID_PRECONDITION");
						String postcondition = current_UMLDataModel.getProperty("ID_POSTCONDITION");
						String readonly = current_UMLDataModel.getProperty("ID_READONLY");
						String parametername = current_UMLDataModel.getProperty("ID_PARAMETERNAME");
						String multi = current_UMLDataModel.getProperty("ID_MULTI");
						String singleexecution = current_UMLDataModel.getProperty("ID_SINGLEEXECUTION");
						String requirment = current_UMLDataModel.getProperty("ID_REQ_ID");//PKY 08072401 S Requirement 저장 불러오기 로직 추가
						//PKY 08081801 S 오퍼레이션 어트리뷰트 숨길수 있는 기능
						String attrHidden = current_UMLDataModel.getProperty("ID_ATTR_HIDDEN");
						String operHidden = current_UMLDataModel.getProperty("ID_OPER_HIDDEN");
						//PKY 08081801 E 오퍼레이션 어트리뷰트 숨길수 있는 기능
						//PKY 08060201 E

						java.util.ArrayList tag = new java.util.ArrayList();
						Object obj=current_UMLDataModel.getElementProperty("ID_TAG");
						if(obj!=null && obj instanceof java.util.ArrayList){
							tag = (java.util.ArrayList)obj;

						}
						//2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
						StrcuturedPackageTreeModel  to1 = new StrcuturedPackageTreeModel(name);
						UMLModel umlModel = null;
						umlModel = new EnumerationModel(current_UMLDataModel,true);
						to1.setRefModel(umlModel);
						umlModel.setName(name);
						if(desc!=null)//PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제						
							umlModel.setDesc(desc.replaceAll("/n", "\n"));//2008043001 PKY STag 바운리더,패키지 저장안되는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제	
						//						umlModel.setDesc(desc);//2008043001 PKY S 속성 저장되지 않는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제
						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
						if(tag.size()>0)
							umlModel.setTags(tag);
						//2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
						umlModel.setTreeModel(to1);
						//PKY 08081801 S 오퍼레이션 어트리뷰트 숨길수 있는 기능
						if(attrHidden!=null&&attrHidden.trim().length()>0&&!attrHidden.trim().equals("")&&attrHidden.equals("1"))
							umlModel.setShowAttr(attrHidden);
						if(operHidden!=null&&operHidden.trim().length()>0&&!operHidden.trim().equals("")&&operHidden.equals("1"))
							umlModel.setShowOper(operHidden);
						//PKY 08081801 E 오퍼레이션 어트리뷰트 숨길수 있는 기능
						//PKY 08072401 S Requirement 저장 불러오기 로직 추가
						if(requirment!=null&&requirment.trim().length()>0&&!requirment.trim().equals("")){
							umlModel.setReqID(requirment);
						}
						//PKY 08060201 S
						if(stereo!=null&&stereo.trim().length()>0&&!stereo.trim().equals("")){
							umlModel.setStereotype(stereo);
						}
						if(scope!=null&&scope.trim().length()>0&&!scope.trim().equals("")){
							umlModel.setScope(scope);
						}						
						if(precondition!=null&&precondition.trim().length()>0&&!precondition.trim().equals("")){
							umlModel.setPreCondition(precondition);
						}
						if(postcondition!=null&&postcondition.trim().length()>0&&!postcondition.trim().equals("")){
							umlModel.setPostCondition(postcondition);
						}
						if(readonly!=null&&readonly.trim().length()>0&&!readonly.trim().equals("")){
							umlModel.setReadOnly(readonly);
						}
						if(parametername!=null&&parametername.trim().length()>0&&!parametername.trim().equals("")){
							umlModel.setParameterName(parametername);
						}
						if(multi!=null&&multi.trim().length()>0&&!multi.trim().equals("")){
							umlModel.setMultiplicity(multi);
						}
						if(singleexecution!=null&&singleexecution.trim().length()>0&&!singleexecution.trim().equals("")){
							umlModel.setSingleExecution(singleexecution);
						}
						//PKY 08060201 E
						Object parentObject = modelStore.get(parentID);
						if(parentObject==null){
							to1.setParent(root);
							root.addChild(to1);
						}
						else{
							to1.setParent((UMLTreeParentModel)parentObject);
							UMLTreeParentModel up= (UMLTreeParentModel)parentObject;
							if (to1 != null) {
								up.addChild(to1);
							}
						}
						modelStore.put(id, to1);
						ProjectManager.getInstance().addTypeModel(to1);//PKY 08052901 S 저장 불러오기할경우 참조타입정보가 없다 참조타입정보를 넣어 줄수 있도록 수정
					}
				}
				else if(current_model_type==9){
					if(this.isBoundary){

					}
					else{
						String id = current_UMLDataModel.getProperty("ID");
						String name = current_UMLDataModel.getProperty("ID_NAME");
						String parentID = current_UMLDataModel.getProperty("parentID");
						String desc = current_UMLDataModel.getProperty("ID_DESCRIPTION");//2008043001 PKY S 속성 저장되지 않는문제
						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제

						//PKY 08060201 S
						String stereo = current_UMLDataModel.getProperty("ID_STEREOTYPE");
						String scope = current_UMLDataModel.getProperty("ID_SCOPE");
						String precondition = current_UMLDataModel.getProperty("ID_PRECONDITION");
						String postcondition = current_UMLDataModel.getProperty("ID_POSTCONDITION");
						String readonly = current_UMLDataModel.getProperty("ID_READONLY");
						String parametername = current_UMLDataModel.getProperty("ID_PARAMETERNAME");
						String multi = current_UMLDataModel.getProperty("ID_MULTI");
						String singleexecution = current_UMLDataModel.getProperty("ID_SINGLEEXECUTION");
						//PKY 08060201 E
						String requirment = current_UMLDataModel.getProperty("ID_REQ_ID");//PKY 08072401 S Requirement 저장 불러오기 로직 추가

						java.util.ArrayList tag = new java.util.ArrayList();
						Object obj=current_UMLDataModel.getElementProperty("ID_TAG");
						if(obj!=null && obj instanceof java.util.ArrayList){
							tag = (java.util.ArrayList)obj;

						}
						//2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
						UMLTreeModel  to1 = new UMLTreeModel(name);
						UMLModel umlModel = null;
						umlModel = new ExceptionModel();
						to1.setRefModel(umlModel);
						umlModel.setName(name);
						if(desc!=null)//PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제						
							umlModel.setDesc(desc.replaceAll("/n", "\n"));//2008043001 PKY STag 바운리더,패키지 저장안되는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제	
						//						umlModel.setDesc(desc);//2008043001 PKY S 속성 저장되지 않는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제

						//PKY 08072401 S Requirement 저장 불러오기 로직 추가
						if(requirment!=null&&requirment.trim().length()>0&&!requirment.trim().equals("")){
							umlModel.setReqID(requirment);
						}

						//PKY 08060201 S
						if(stereo!=null&&stereo.trim().length()>0&&!stereo.trim().equals("")){
							umlModel.setStereotype(stereo);
						}
						if(scope!=null&&scope.trim().length()>0&&!scope.trim().equals("")){
							umlModel.setScope(scope);
						}						
						if(precondition!=null&&precondition.trim().length()>0&&!precondition.trim().equals("")){
							umlModel.setPreCondition(precondition);
						}
						if(postcondition!=null&&postcondition.trim().length()>0&&!postcondition.trim().equals("")){
							umlModel.setPostCondition(postcondition);
						}
						if(readonly!=null&&readonly.trim().length()>0&&!readonly.trim().equals("")){
							umlModel.setReadOnly(readonly);
						}
						if(parametername!=null&&parametername.trim().length()>0&&!parametername.trim().equals("")){
							umlModel.setParameterName(parametername);
						}
						if(multi!=null&&multi.trim().length()>0&&!multi.trim().equals("")){
							umlModel.setMultiplicity(multi);
						}
						if(singleexecution!=null&&singleexecution.trim().length()>0&&!singleexecution.trim().equals("")){
							umlModel.setSingleExecution(singleexecution);
						}
						//PKY 08060201 E
						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
						if(tag.size()>0)
							umlModel.setTags(tag);
						//						2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
						umlModel.setTreeModel(to1);
						Object parentObject = modelStore.get(parentID);
						if(parentObject==null){
							to1.setParent(root);
							root.addChild(to1);
						}
						else{
							to1.setParent((UMLTreeParentModel)parentObject);
							UMLTreeParentModel up= (UMLTreeParentModel)parentObject;
							if (to1 != null) {
								up.addChild(to1);
							}
						}
						modelStore.put(id, to1);
						ProjectManager.getInstance().addTypeModel(to1);//PKY 08052901 S 저장 불러오기할경우 참조타입정보가 없다 참조타입정보를 넣어 줄수 있도록 수정
					}
				}
				else if(current_model_type==10){
					if(this.isBoundary){

					}
					else{
						String id = current_UMLDataModel.getProperty("ID");
						String name = current_UMLDataModel.getProperty("ID_NAME");
						String parentID = current_UMLDataModel.getProperty("parentID");
						String desc = current_UMLDataModel.getProperty("ID_DESCRIPTION");//2008043001 PKY S 속성 저장되지 않는문제

						//PKY 08060201 S
						String stereo = current_UMLDataModel.getProperty("ID_STEREOTYPE");
						String scope = current_UMLDataModel.getProperty("ID_SCOPE");
						String precondition = current_UMLDataModel.getProperty("ID_PRECONDITION");
						String postcondition = current_UMLDataModel.getProperty("ID_POSTCONDITION");
						String readonly = current_UMLDataModel.getProperty("ID_READONLY");
						String parametername = current_UMLDataModel.getProperty("ID_PARAMETERNAME");
						String multi = current_UMLDataModel.getProperty("ID_MULTI");
						String singleexecution = current_UMLDataModel.getProperty("ID_SINGLEEXECUTION");
						String requirment = current_UMLDataModel.getProperty("ID_REQ_ID");//PKY 08072401 S Requirement 저장 불러오기 로직 추가
						//PKY 08060201 E
						//PKY 08081801 S 오퍼레이션 어트리뷰트 숨길수 있는 기능
						String attrHidden = current_UMLDataModel.getProperty("ID_ATTR_HIDDEN");
						String operHidden = current_UMLDataModel.getProperty("ID_OPER_HIDDEN");
						//PKY 08081801 E 오퍼레이션 어트리뷰트 숨길수 있는 기능
						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
						java.util.ArrayList tag = new java.util.ArrayList();
						Object obj=current_UMLDataModel.getElementProperty("ID_TAG");
						if(obj!=null && obj instanceof java.util.ArrayList){
							tag = (java.util.ArrayList)obj;

						}
						//2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
						StrcuturedPackageTreeModel  to1 = new StrcuturedPackageTreeModel(name);
						UMLModel umlModel = null;
						umlModel = new FinalActivityModel(current_UMLDataModel,true);
						to1.setRefModel(umlModel);
						umlModel.setName(name);
						//PKY 08081801 S 오퍼레이션 어트리뷰트 숨길수 있는 기능
						if(attrHidden!=null&&attrHidden.trim().length()>0&&!attrHidden.trim().equals("")&&attrHidden.equals("1"))
							umlModel.setShowAttr(attrHidden);
						if(operHidden!=null&&operHidden.trim().length()>0&&!operHidden.trim().equals("")&&operHidden.equals("1"))
							umlModel.setShowOper(operHidden);
						//PKY 08081801 E 오퍼레이션 어트리뷰트 숨길수 있는 기능
						if(desc!=null)//PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제						
							umlModel.setDesc(desc.replaceAll("/n", "\n"));//2008043001 PKY STag 바운리더,패키지 저장안되는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제	
						//						umlModel.setDesc(desc);//2008043001 PKY S 속성 저장되지 않는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제
						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
						if(tag.size()>0)
							umlModel.setTags(tag);
						//						2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
						umlModel.setTreeModel(to1);
						//PKY 08072401 S Requirement 저장 불러오기 로직 추가
						if(requirment!=null&&requirment.trim().length()>0&&!requirment.trim().equals("")){
							umlModel.setReqID(requirment);
						}
						//PKY 08060201 S
						if(stereo!=null&&stereo.trim().length()>0&&!stereo.trim().equals("")){
							umlModel.setStereotype(stereo);
						}
						if(scope!=null&&scope.trim().length()>0&&!scope.trim().equals("")){
							umlModel.setScope(scope);
						}						
						if(precondition!=null&&precondition.trim().length()>0&&!precondition.trim().equals("")){
							umlModel.setPreCondition(precondition);
						}
						if(postcondition!=null&&postcondition.trim().length()>0&&!postcondition.trim().equals("")){
							umlModel.setPostCondition(postcondition);
						}
						if(readonly!=null&&readonly.trim().length()>0&&!readonly.trim().equals("")){
							umlModel.setReadOnly(readonly);
						}
						if(parametername!=null&&parametername.trim().length()>0&&!parametername.trim().equals("")){
							umlModel.setParameterName(parametername);
						}
						if(multi!=null&&multi.trim().length()>0&&!multi.trim().equals("")){
							umlModel.setMultiplicity(multi);
						}
						if(singleexecution!=null&&singleexecution.trim().length()>0&&!singleexecution.trim().equals("")){
							umlModel.setSingleExecution(singleexecution);
						}
						//PKY 08060201 E
						Object parentObject = modelStore.get(parentID);
						if(parentObject==null){
							to1.setParent(root);
							root.addChild(to1);
						}
						else{
							to1.setParent((UMLTreeParentModel)parentObject);
							UMLTreeParentModel up= (UMLTreeParentModel)parentObject;
							if (to1 != null) {
								up.addChild(to1);
							}
						}
						modelStore.put(id, to1);
						ProjectManager.getInstance().addBehaviorActivityList(to1);//PKY 08080501 S 저장 불러오기 할 경우 엑션 참조할 엑티비티가 나오지 않는문제
						ProjectManager.getInstance().addTypeModel(to1);//PKY 08052901 S 저장 불러오기할경우 참조타입정보가 없다 참조타입정보를 넣어 줄수 있도록 수정
					}
				}
				else if(current_model_type==11){
					if(this.isBoundary){

					}
					else{
						String id = current_UMLDataModel.getProperty("ID");
						String name = current_UMLDataModel.getProperty("ID_NAME");
						String parentID = current_UMLDataModel.getProperty("parentID");
						String desc = current_UMLDataModel.getProperty("ID_DESCRIPTION");//2008043001 PKY S 속성 저장되지 않는문제
						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제

						//PKY 08060201 S
						String stereo = current_UMLDataModel.getProperty("ID_STEREOTYPE");
						String scope = current_UMLDataModel.getProperty("ID_SCOPE");
						String precondition = current_UMLDataModel.getProperty("ID_PRECONDITION");
						String postcondition = current_UMLDataModel.getProperty("ID_POSTCONDITION");
						String readonly = current_UMLDataModel.getProperty("ID_READONLY");
						String parametername = current_UMLDataModel.getProperty("ID_PARAMETERNAME");
						String multi = current_UMLDataModel.getProperty("ID_MULTI");
						String singleexecution = current_UMLDataModel.getProperty("ID_SINGLEEXECUTION");
						String requirment = current_UMLDataModel.getProperty("ID_REQ_ID");//PKY 08072401 S Requirement 저장 불러오기 로직 추가
						String actionType = current_UMLDataModel.getProperty("ActionType");//PKY 08072401 S Requirement 저장 불러오기 로직 추가
						//PKY 08060201 E

						java.util.ArrayList tag = new java.util.ArrayList();
						Object obj=current_UMLDataModel.getElementProperty("ID_TAG");
						if(obj!=null && obj instanceof java.util.ArrayList){
							tag = (java.util.ArrayList)obj;

						}
						//2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
						UMLTreeModel  to1 = new UMLTreeModel(name);
						UMLModel umlModel = null;
						umlModel = new FinalActiionModel(current_UMLDataModel,true);
						to1.setRefModel(umlModel);
						umlModel.setName(name);
						//PKY 08072401 S Requirement 저장 불러오기 로직 추가
						if(requirment!=null&&requirment.trim().length()>0&&!requirment.trim().equals("")){
							umlModel.setReqID(requirment);
						}
						//PKY 08072401 E Requirement 저장 불러오기 로직 추가
						//PKY 08060201 S
						if(stereo!=null&&stereo.trim().length()>0&&!stereo.trim().equals("")){
							umlModel.setStereotype(stereo);
						}
						if(scope!=null&&scope.trim().length()>0&&!scope.trim().equals("")){
							umlModel.setScope(scope);
						}						
						if(precondition!=null&&precondition.trim().length()>0&&!precondition.trim().equals("")){
							umlModel.setPreCondition(precondition);
						}
						if(postcondition!=null&&postcondition.trim().length()>0&&!postcondition.trim().equals("")){
							umlModel.setPostCondition(postcondition);
						}
						if(readonly!=null&&readonly.trim().length()>0&&!readonly.trim().equals("")){
							umlModel.setReadOnly(readonly);
						}
						if(parametername!=null&&parametername.trim().length()>0&&!parametername.trim().equals("")){
							umlModel.setParameterName(parametername);
						}
						if(multi!=null&&multi.trim().length()>0&&!multi.trim().equals("")){
							umlModel.setMultiplicity(multi);
						}
						if(singleexecution!=null&&singleexecution.trim().length()>0&&!singleexecution.trim().equals("")){
							umlModel.setSingleExecution(singleexecution);
						}
						//PKY 08060201 E
						if(desc!=null)//PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제						
							umlModel.setDesc(desc.replaceAll("/n", "\n"));//2008043001 PKY STag 바운리더,패키지 저장안되는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제	
						//						umlModel.setDesc(desc);//2008043001 PKY S 속성 저장되지 않는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제
						umlModel.setTreeModel(to1);
						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
						if(tag.size()>0)
							umlModel.setTags(tag);
						//2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
						umlModel.setTreeModel(to1);
						Object parentObject = modelStore.get(parentID);
						if(parentObject==null){
							to1.setParent(root);
							root.addChild(to1);
						}
						else{
							to1.setParent((UMLTreeParentModel)parentObject);
							UMLTreeParentModel up= (UMLTreeParentModel)parentObject;
							if (to1 != null) {
								up.addChild(to1);
							}
						}
						modelStore.put(id, to1);
						ProjectManager.getInstance().addTypeModel(to1);//PKY 08052901 S 저장 불러오기할경우 참조타입정보가 없다 참조타입정보를 넣어 줄수 있도록 수정
					}
				}
				else if(current_model_type==12){
					if(this.isBoundary){

					}
					else{
						String id = current_UMLDataModel.getProperty("ID");
						String name = current_UMLDataModel.getProperty("ID_NAME");
						String parentID = current_UMLDataModel.getProperty("parentID");
						String desc = current_UMLDataModel.getProperty("ID_DESCRIPTION");//2008043001 PKY S 속성 저장되지 않는문제

						//PKY 08060201 S
						String stereo = current_UMLDataModel.getProperty("ID_STEREOTYPE");
						String scope = current_UMLDataModel.getProperty("ID_SCOPE");
						String precondition = current_UMLDataModel.getProperty("ID_PRECONDITION");
						String postcondition = current_UMLDataModel.getProperty("ID_POSTCONDITION");
						String readonly = current_UMLDataModel.getProperty("ID_READONLY");
						String parametername = current_UMLDataModel.getProperty("ID_PARAMETERNAME");
						String multi = current_UMLDataModel.getProperty("ID_MULTI");
						String singleexecution = current_UMLDataModel.getProperty("ID_SINGLEEXECUTION");
						String requirment = current_UMLDataModel.getProperty("ID_REQ_ID");//PKY 08072401 S Requirement 저장 불러오기 로직 추가
						//PKY 08060201 E

						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
						java.util.ArrayList tag = new java.util.ArrayList();
						Object obj=current_UMLDataModel.getElementProperty("ID_TAG");
						if(obj!=null && obj instanceof java.util.ArrayList){
							tag = (java.util.ArrayList)obj;

						}
						//2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
						UMLTreeModel  to1 = new UMLTreeModel(name);
						UMLModel umlModel = null;
						umlModel = new SendModel();
						to1.setRefModel(umlModel);
						umlModel.setName(name);
						//PKY 08072401 S Requirement 저장 불러오기 로직 추가
						if(requirment!=null&&requirment.trim().length()>0&&!requirment.trim().equals("")){
							umlModel.setReqID(requirment);
						}

						//PKY 08060201 S
						if(stereo!=null&&stereo.trim().length()>0&&!stereo.trim().equals("")){
							umlModel.setStereotype(stereo);
						}
						if(scope!=null&&scope.trim().length()>0&&!scope.trim().equals("")){
							umlModel.setScope(scope);
						}						
						if(precondition!=null&&precondition.trim().length()>0&&!precondition.trim().equals("")){
							umlModel.setPreCondition(precondition);
						}
						if(postcondition!=null&&postcondition.trim().length()>0&&!postcondition.trim().equals("")){
							umlModel.setPostCondition(postcondition);
						}
						if(readonly!=null&&readonly.trim().length()>0&&!readonly.trim().equals("")){
							umlModel.setReadOnly(readonly);
						}
						if(parametername!=null&&parametername.trim().length()>0&&!parametername.trim().equals("")){
							umlModel.setParameterName(parametername);
						}
						if(multi!=null&&multi.trim().length()>0&&!multi.trim().equals("")){
							umlModel.setMultiplicity(multi);
						}
						if(singleexecution!=null&&singleexecution.trim().length()>0&&!singleexecution.trim().equals("")){
							umlModel.setSingleExecution(singleexecution);
						}
						//PKY 08060201 E

						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
						if(tag.size()>0)
							umlModel.setTags(tag);
						//2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
						if(desc!=null)//PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제						
							umlModel.setDesc(desc.replaceAll("/n", "\n"));//2008043001 PKY STag 바운리더,패키지 저장안되는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제	
						//						umlModel.setDesc(desc);//2008043001 PKY S 속성 저장되지 않는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제
						umlModel.setTreeModel(to1);
						Object parentObject = modelStore.get(parentID);
						if(parentObject==null){
							to1.setParent(root);
							root.addChild(to1);
						}
						else{
							to1.setParent((UMLTreeParentModel)parentObject);
							UMLTreeParentModel up= (UMLTreeParentModel)parentObject;
							if (to1 != null) {
								up.addChild(to1);
							}
						}
						modelStore.put(id, to1);
						ProjectManager.getInstance().addTypeModel(to1);//PKY 08052901 S 저장 불러오기할경우 참조타입정보가 없다 참조타입정보를 넣어 줄수 있도록 수정
					}
				}
				else if(current_model_type==13){
					if(this.isBoundary){

					}
					else{
						String id = current_UMLDataModel.getProperty("ID");
						String name = current_UMLDataModel.getProperty("ID_NAME");
						String parentID = current_UMLDataModel.getProperty("parentID");
						String desc = current_UMLDataModel.getProperty("ID_DESCRIPTION");//2008043001 PKY S 속성 저장되지 않는문제

						//PKY 08060201 S
						String stereo = current_UMLDataModel.getProperty("ID_STEREOTYPE");
						String scope = current_UMLDataModel.getProperty("ID_SCOPE");
						String precondition = current_UMLDataModel.getProperty("ID_PRECONDITION");
						String postcondition = current_UMLDataModel.getProperty("ID_POSTCONDITION");
						String readonly = current_UMLDataModel.getProperty("ID_READONLY");
						String parametername = current_UMLDataModel.getProperty("ID_PARAMETERNAME");
						String multi = current_UMLDataModel.getProperty("ID_MULTI");
						String singleexecution = current_UMLDataModel.getProperty("ID_SINGLEEXECUTION");
						String requirment = current_UMLDataModel.getProperty("ID_REQ_ID");//PKY 08072401 S Requirement 저장 불러오기 로직 추가
						//PKY 08060201 E

						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
						java.util.ArrayList tag = new java.util.ArrayList();
						Object obj=current_UMLDataModel.getElementProperty("ID_TAG");
						if(obj!=null && obj instanceof java.util.ArrayList){
							tag = (java.util.ArrayList)obj;

						}
						//2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
						UMLTreeModel  to1 = new UMLTreeModel(name);
						UMLModel umlModel = null;
						umlModel = new ReceiveModel();
						to1.setRefModel(umlModel);
						umlModel.setName(name);

						//PKY 08072401 S Requirement 저장 불러오기 로직 추가
						if(requirment!=null&&requirment.trim().length()>0&&!requirment.trim().equals("")){
							umlModel.setReqID(requirment);
						}
						//PKY 08060201 S
						if(stereo!=null&&stereo.trim().length()>0&&!stereo.trim().equals("")){
							umlModel.setStereotype(stereo);
						}
						if(scope!=null&&scope.trim().length()>0&&!scope.trim().equals("")){
							umlModel.setScope(scope);
						}						
						if(precondition!=null&&precondition.trim().length()>0&&!precondition.trim().equals("")){
							umlModel.setPreCondition(precondition);
						}
						if(postcondition!=null&&postcondition.trim().length()>0&&!postcondition.trim().equals("")){
							umlModel.setPostCondition(postcondition);
						}
						if(readonly!=null&&readonly.trim().length()>0&&!readonly.trim().equals("")){
							umlModel.setReadOnly(readonly);
						}
						if(parametername!=null&&parametername.trim().length()>0&&!parametername.trim().equals("")){
							umlModel.setParameterName(parametername);
						}
						if(multi!=null&&multi.trim().length()>0&&!multi.trim().equals("")){
							umlModel.setMultiplicity(multi);
						}
						if(singleexecution!=null&&singleexecution.trim().length()>0&&!singleexecution.trim().equals("")){
							umlModel.setSingleExecution(singleexecution);
						}
						//PKY 08060201 E


						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
						if(tag.size()>0)
							umlModel.setTags(tag);
						//2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
						if(desc!=null)//PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제						
							umlModel.setDesc(desc.replaceAll("/n", "\n"));//2008043001 PKY STag 바운리더,패키지 저장안되는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제	
						//						umlModel.setDesc(desc);//2008043001 PKY S 속성 저장되지 않는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제
						umlModel.setTreeModel(to1);
						Object parentObject = modelStore.get(parentID);
						if(parentObject==null){
							to1.setParent(root);
							root.addChild(to1);
						}
						else{
							to1.setParent((UMLTreeParentModel)parentObject);
							UMLTreeParentModel up= (UMLTreeParentModel)parentObject;
							if (to1 != null) {
								up.addChild(to1);
							}
						}
						modelStore.put(id, to1);
						ProjectManager.getInstance().addTypeModel(to1);//PKY 08052901 S 저장 불러오기할경우 참조타입정보가 없다 참조타입정보를 넣어 줄수 있도록 수정
					}
				}
				else if(current_model_type==14){
					if(this.isBoundary){

					}
					else{
						String id = current_UMLDataModel.getProperty("ID");
						String name = current_UMLDataModel.getProperty("ID_NAME");
						String parentID = current_UMLDataModel.getProperty("parentID");
						String desc = current_UMLDataModel.getProperty("ID_DESCRIPTION");//2008043001 PKY S 속성 저장되지 않는문제

						//PKY 08060201 S
						String stereo = current_UMLDataModel.getProperty("ID_STEREOTYPE");
						String scope = current_UMLDataModel.getProperty("ID_SCOPE");
						String precondition = current_UMLDataModel.getProperty("ID_PRECONDITION");
						String postcondition = current_UMLDataModel.getProperty("ID_POSTCONDITION");
						String readonly = current_UMLDataModel.getProperty("ID_READONLY");
						String parametername = current_UMLDataModel.getProperty("ID_PARAMETERNAME");
						String multi = current_UMLDataModel.getProperty("ID_MULTI");
						String singleexecution = current_UMLDataModel.getProperty("ID_SINGLEEXECUTION");
						String requirment = current_UMLDataModel.getProperty("ID_REQ_ID");//PKY 08072401 S Requirement 저장 불러오기 로직 추가
						//PKY 08060201 E

						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
						java.util.ArrayList tag = new java.util.ArrayList();
						Object obj=current_UMLDataModel.getElementProperty("ID_TAG");
						if(obj!=null && obj instanceof java.util.ArrayList){
							tag = (java.util.ArrayList)obj;

						}
						//2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
						UMLTreeModel  to1 = new UMLTreeModel(name);
						UMLModel umlModel = null;
						umlModel = new InitialModel();
						//PKY 08072401 S Requirement 저장 불러오기 로직 추가
						if(requirment!=null&&requirment.trim().length()>0&&!requirment.trim().equals("")){
							umlModel.setReqID(requirment);
						}

						//PKY 08060201 S
						if(stereo!=null&&stereo.trim().length()>0&&!stereo.trim().equals("")){
							umlModel.setStereotype(stereo);
						}
						if(scope!=null&&scope.trim().length()>0&&!scope.trim().equals("")){
							umlModel.setScope(scope);
						}						
						if(precondition!=null&&precondition.trim().length()>0&&!precondition.trim().equals("")){
							umlModel.setPreCondition(precondition);
						}
						if(postcondition!=null&&postcondition.trim().length()>0&&!postcondition.trim().equals("")){
							umlModel.setPostCondition(postcondition);
						}
						if(readonly!=null&&readonly.trim().length()>0&&!readonly.trim().equals("")){
							umlModel.setReadOnly(readonly);
						}
						if(parametername!=null&&parametername.trim().length()>0&&!parametername.trim().equals("")){
							umlModel.setParameterName(parametername);
						}
						if(multi!=null&&multi.trim().length()>0&&!multi.trim().equals("")){
							umlModel.setMultiplicity(multi);
						}
						if(singleexecution!=null&&singleexecution.trim().length()>0&&!singleexecution.trim().equals("")){
							umlModel.setSingleExecution(singleexecution);
						}
						//PKY 08060201 E

						to1.setRefModel(umlModel);

						umlModel.setName(name);
						if(desc!=null)//PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제						
							umlModel.setDesc(desc.replaceAll("/n", "\n"));//2008043001 PKY STag 바운리더,패키지 저장안되는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제	
						//						umlModel.setDesc(desc);//2008043001 PKY S 속성 저장되지 않는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제
						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
						if(tag.size()>0)
							umlModel.setTags(tag);
						//2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
						umlModel.setTreeModel(to1);
						Object parentObject = modelStore.get(parentID);
						if(parentObject==null){
							to1.setParent(root);
							root.addChild(to1);
						}
						else{
							to1.setParent((UMLTreeParentModel)parentObject);
							UMLTreeParentModel up= (UMLTreeParentModel)parentObject;
							if (to1 != null) {
								up.addChild(to1);
							}
						}
						modelStore.put(id, to1);
					}
				}
				else if(current_model_type==15){
					if(this.isBoundary){

					}
					else{
						String id = current_UMLDataModel.getProperty("ID");
						String name = current_UMLDataModel.getProperty("ID_NAME");
						String parentID = current_UMLDataModel.getProperty("parentID");
						String desc = current_UMLDataModel.getProperty("ID_DESCRIPTION");//2008043001 PKY S 속성 저장되지 않는문제
						//PKY 08060201 S
						String stereo = current_UMLDataModel.getProperty("ID_STEREOTYPE");
						String scope = current_UMLDataModel.getProperty("ID_SCOPE");
						String precondition = current_UMLDataModel.getProperty("ID_PRECONDITION");
						String postcondition = current_UMLDataModel.getProperty("ID_POSTCONDITION");
						String readonly = current_UMLDataModel.getProperty("ID_READONLY");
						String parametername = current_UMLDataModel.getProperty("ID_PARAMETERNAME");
						String multi = current_UMLDataModel.getProperty("ID_MULTI");
						String singleexecution = current_UMLDataModel.getProperty("ID_SINGLEEXECUTION");
						String requirment = current_UMLDataModel.getProperty("ID_REQ_ID");//PKY 08072401 S Requirement 저장 불러오기 로직 추가
						//PKY 08060201 E
						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
						java.util.ArrayList tag = new java.util.ArrayList();
						Object obj=current_UMLDataModel.getElementProperty("ID_TAG");
						if(obj!=null && obj instanceof java.util.ArrayList){
							tag = (java.util.ArrayList)obj;

						}
						//2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
						UMLTreeModel  to1 = new UMLTreeModel(name);
						UMLModel umlModel = null;
						umlModel = new FinalModel();
						to1.setRefModel(umlModel);
						umlModel.setName(name);
						umlModel.setTreeModel(to1);
						//PKY 08072401 S Requirement 저장 불러오기 로직 추가
						if(requirment!=null&&requirment.trim().length()>0&&!requirment.trim().equals("")){
							umlModel.setReqID(requirment);
						}
						//PKY 08060201 S
						if(stereo!=null&&stereo.trim().length()>0&&!stereo.trim().equals("")){
							umlModel.setStereotype(stereo);
						}
						if(scope!=null&&scope.trim().length()>0&&!scope.trim().equals("")){
							umlModel.setScope(scope);
						}						
						if(precondition!=null&&precondition.trim().length()>0&&!precondition.trim().equals("")){
							umlModel.setPreCondition(precondition);
						}
						if(postcondition!=null&&postcondition.trim().length()>0&&!postcondition.trim().equals("")){
							umlModel.setPostCondition(postcondition);
						}
						if(readonly!=null&&readonly.trim().length()>0&&!readonly.trim().equals("")){
							umlModel.setReadOnly(readonly);
						}
						if(parametername!=null&&parametername.trim().length()>0&&!parametername.trim().equals("")){
							umlModel.setParameterName(parametername);
						}
						if(multi!=null&&multi.trim().length()>0&&!multi.trim().equals("")){
							umlModel.setMultiplicity(multi);
						}
						if(singleexecution!=null&&singleexecution.trim().length()>0&&!singleexecution.trim().equals("")){
							umlModel.setSingleExecution(singleexecution);
						}
						//PKY 08060201 E
						if(desc!=null)//PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제						
							umlModel.setDesc(desc.replaceAll("/n", "\n"));//2008043001 PKY STag 바운리더,패키지 저장안되는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제	
						//						umlModel.setDesc(desc);//2008043001 PKY S 속성 저장되지 않는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제
						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
						if(tag.size()>0)
							umlModel.setTags(tag);
						//2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
						Object parentObject = modelStore.get(parentID);
						if(parentObject==null){
							to1.setParent(root);
							root.addChild(to1);
						}
						else{
							to1.setParent((UMLTreeParentModel)parentObject);
							UMLTreeParentModel up= (UMLTreeParentModel)parentObject;
							if (to1 != null) {
								up.addChild(to1);
							}
						}
						modelStore.put(id, to1);
						ProjectManager.getInstance().addTypeModel(to1);//PKY 08052901 S 저장 불러오기할경우 참조타입정보가 없다 참조타입정보를 넣어 줄수 있도록 수정
					}
				}
				else if(current_model_type==16){
					if(this.isBoundary){

					}
					else{
						String id = current_UMLDataModel.getProperty("ID");
						String name = current_UMLDataModel.getProperty("ID_NAME");
						String parentID = current_UMLDataModel.getProperty("parentID");
						String desc = current_UMLDataModel.getProperty("ID_DESCRIPTION");//2008043001 PKY S 속성 저장되지 않는문제
						//PKY 08060201 S
						String stereo = current_UMLDataModel.getProperty("ID_STEREOTYPE");
						String scope = current_UMLDataModel.getProperty("ID_SCOPE");
						String precondition = current_UMLDataModel.getProperty("ID_PRECONDITION");
						String postcondition = current_UMLDataModel.getProperty("ID_POSTCONDITION");
						String readonly = current_UMLDataModel.getProperty("ID_READONLY");
						String parametername = current_UMLDataModel.getProperty("ID_PARAMETERNAME");
						String multi = current_UMLDataModel.getProperty("ID_MULTI");
						String singleexecution = current_UMLDataModel.getProperty("ID_SINGLEEXECUTION");
						String requirment = current_UMLDataModel.getProperty("ID_REQ_ID");//PKY 08072401 S Requirement 저장 불러오기 로직 추가
						//PKY 08060201 E

						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
						java.util.ArrayList tag = new java.util.ArrayList();
						Object obj=current_UMLDataModel.getElementProperty("ID_TAG");
						if(obj!=null && obj instanceof java.util.ArrayList){
							tag = (java.util.ArrayList)obj;

						}
						//2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
						UMLTreeModel  to1 = new UMLTreeModel(name);
						UMLModel umlModel = null;
						umlModel = new FlowFinalModel();
						to1.setRefModel(umlModel);
						umlModel.setName(name);
						//PKY 08060201 S
						//PKY 08072401 S Requirement 저장 불러오기 로직 추가
						if(requirment!=null&&requirment.trim().length()>0&&!requirment.trim().equals("")){
							umlModel.setReqID(requirment);
						}
						if(stereo!=null&&stereo.trim().length()>0&&!stereo.trim().equals("")){
							umlModel.setStereotype(stereo);
						}
						if(scope!=null&&scope.trim().length()>0&&!scope.trim().equals("")){
							umlModel.setScope(scope);
						}						
						if(precondition!=null&&precondition.trim().length()>0&&!precondition.trim().equals("")){
							umlModel.setPreCondition(precondition);
						}
						if(postcondition!=null&&postcondition.trim().length()>0&&!postcondition.trim().equals("")){
							umlModel.setPostCondition(postcondition);
						}
						if(readonly!=null&&readonly.trim().length()>0&&!readonly.trim().equals("")){
							umlModel.setReadOnly(readonly);
						}
						if(parametername!=null&&parametername.trim().length()>0&&!parametername.trim().equals("")){
							umlModel.setParameterName(parametername);
						}
						if(multi!=null&&multi.trim().length()>0&&!multi.trim().equals("")){
							umlModel.setMultiplicity(multi);
						}
						if(singleexecution!=null&&singleexecution.trim().length()>0&&!singleexecution.trim().equals("")){
							umlModel.setSingleExecution(singleexecution);
						}
						//PKY 08060201 E
						if(desc!=null)//PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제						
							umlModel.setDesc(desc.replaceAll("/n", "\n"));//2008043001 PKY STag 바운리더,패키지 저장안되는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제	
						//						umlModel.setDesc(desc);//2008043001 PKY S 속성 저장되지 않는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제
						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
						if(tag.size()>0)
							umlModel.setTags(tag);
						//						2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
						umlModel.setTreeModel(to1);
						Object parentObject = modelStore.get(parentID);
						if(parentObject==null){
							to1.setParent(root);
							root.addChild(to1);
						}
						else{
							to1.setParent((UMLTreeParentModel)parentObject);
							UMLTreeParentModel up= (UMLTreeParentModel)parentObject;
							if (to1 != null) {
								up.addChild(to1);
							}
						}
						modelStore.put(id, to1);
						ProjectManager.getInstance().addTypeModel(to1);//PKY 08052901 S 저장 불러오기할경우 참조타입정보가 없다 참조타입정보를 넣어 줄수 있도록 수정
					}
				}
				else if(current_model_type==17){
					if(this.isBoundary){

					}
					else{
						String id = current_UMLDataModel.getProperty("ID");
						String name = current_UMLDataModel.getProperty("ID_NAME");
						String parentID = current_UMLDataModel.getProperty("parentID");
						String desc = current_UMLDataModel.getProperty("ID_DESCRIPTION");//2008043001 PKY S 속성 저장되지 않는문제
						//PKY 08060201 S
						String stereo = current_UMLDataModel.getProperty("ID_STEREOTYPE");
						String scope = current_UMLDataModel.getProperty("ID_SCOPE");
						String precondition = current_UMLDataModel.getProperty("ID_PRECONDITION");
						String postcondition = current_UMLDataModel.getProperty("ID_POSTCONDITION");
						String readonly = current_UMLDataModel.getProperty("ID_READONLY");
						String parametername = current_UMLDataModel.getProperty("ID_PARAMETERNAME");
						String multi = current_UMLDataModel.getProperty("ID_MULTI");
						String singleexecution = current_UMLDataModel.getProperty("ID_SINGLEEXECUTION");
						String requirment = current_UMLDataModel.getProperty("ID_REQ_ID");//PKY 08072401 S Requirement 저장 불러오기 로직 추가
						//PKY 08060201 E
						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
						java.util.ArrayList tag = new java.util.ArrayList();
						Object obj=current_UMLDataModel.getElementProperty("ID_TAG");
						if(obj!=null && obj instanceof java.util.ArrayList){
							tag = (java.util.ArrayList)obj;

						}
						//2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
						UMLTreeModel  to1 = new UMLTreeModel(name);
						UMLModel umlModel = null;
						umlModel = new SynchModel();
						to1.setRefModel(umlModel);
						umlModel.setName(name);
						umlModel.setTreeModel(to1);
						//PKY 08060201 S
						//PKY 08072401 S Requirement 저장 불러오기 로직 추가
						if(requirment!=null&&requirment.trim().length()>0&&!requirment.trim().equals("")){
							umlModel.setReqID(requirment);
						}
						if(stereo!=null&&stereo.trim().length()>0&&!stereo.trim().equals("")){
							umlModel.setStereotype(stereo);
						}
						if(scope!=null&&scope.trim().length()>0&&!scope.trim().equals("")){
							umlModel.setScope(scope);
						}						
						if(precondition!=null&&precondition.trim().length()>0&&!precondition.trim().equals("")){
							umlModel.setPreCondition(precondition);
						}
						if(postcondition!=null&&postcondition.trim().length()>0&&!postcondition.trim().equals("")){
							umlModel.setPostCondition(postcondition);
						}
						if(readonly!=null&&readonly.trim().length()>0&&!readonly.trim().equals("")){
							umlModel.setReadOnly(readonly);
						}
						if(parametername!=null&&parametername.trim().length()>0&&!parametername.trim().equals("")){
							umlModel.setParameterName(parametername);
						}
						if(multi!=null&&multi.trim().length()>0&&!multi.trim().equals("")){
							umlModel.setMultiplicity(multi);
						}
						if(singleexecution!=null&&singleexecution.trim().length()>0&&!singleexecution.trim().equals("")){
							umlModel.setSingleExecution(singleexecution);
						}
						//PKY 08060201 E
						if(desc!=null)//PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제						
							umlModel.setDesc(desc.replaceAll("/n", "\n"));//2008043001 PKY STag 바운리더,패키지 저장안되는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제	
						//						umlModel.setDesc(desc);//2008043001 PKY S 속성 저장되지 않는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제
						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
						if(tag.size()>0)
							umlModel.setTags(tag);
						//2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
						Object parentObject = modelStore.get(parentID);
						if(parentObject==null){
							to1.setParent(root);
							root.addChild(to1);
						}
						else{
							to1.setParent((UMLTreeParentModel)parentObject);
							UMLTreeParentModel up= (UMLTreeParentModel)parentObject;
							if (to1 != null) {
								up.addChild(to1);
							}
						}
						modelStore.put(id, to1);
						ProjectManager.getInstance().addTypeModel(to1);//PKY 08052901 S 저장 불러오기할경우 참조타입정보가 없다 참조타입정보를 넣어 줄수 있도록 수정
					}
				}
				else if(current_model_type==18){
					if(this.isBoundary){

					}
					else{
						String id = current_UMLDataModel.getProperty("ID");
						String name = current_UMLDataModel.getProperty("ID_NAME");
						String parentID = current_UMLDataModel.getProperty("parentID");
						String desc = current_UMLDataModel.getProperty("ID_DESCRIPTION");//2008043001 PKY S 속성 저장되지 않는문제
						//PKY 08060201 S
						String stereo = current_UMLDataModel.getProperty("ID_STEREOTYPE");
						String scope = current_UMLDataModel.getProperty("ID_SCOPE");
						String precondition = current_UMLDataModel.getProperty("ID_PRECONDITION");
						String postcondition = current_UMLDataModel.getProperty("ID_POSTCONDITION");
						String readonly = current_UMLDataModel.getProperty("ID_READONLY");
						String parametername = current_UMLDataModel.getProperty("ID_PARAMETERNAME");
						String multi = current_UMLDataModel.getProperty("ID_MULTI");
						String singleexecution = current_UMLDataModel.getProperty("ID_SINGLEEXECUTION");
						String requirment = current_UMLDataModel.getProperty("ID_REQ_ID");//PKY 08072401 S Requirement 저장 불러오기 로직 추가
						//PKY 08060201 E
						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
						java.util.ArrayList tag = new java.util.ArrayList();
						Object obj=current_UMLDataModel.getElementProperty("ID_TAG");
						if(obj!=null && obj instanceof java.util.ArrayList){
							tag = (java.util.ArrayList)obj;

						}
						//2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
						UMLTreeModel  to1 = new UMLTreeModel(name);
						UMLModel umlModel = null;
						umlModel = new DecisionModel();
						to1.setRefModel(umlModel);
						umlModel.setName(name);
						umlModel.setTreeModel(to1);
						//PKY 08060201 S
						//PKY 08072401 S Requirement 저장 불러오기 로직 추가
						if(requirment!=null&&requirment.trim().length()>0&&!requirment.trim().equals("")){
							umlModel.setReqID(requirment);
						}
						if(stereo!=null&&stereo.trim().length()>0&&!stereo.trim().equals("")){
							umlModel.setStereotype(stereo);
						}
						if(scope!=null&&scope.trim().length()>0&&!scope.trim().equals("")){
							umlModel.setScope(scope);
						}						
						if(precondition!=null&&precondition.trim().length()>0&&!precondition.trim().equals("")){
							umlModel.setPreCondition(precondition);
						}
						if(postcondition!=null&&postcondition.trim().length()>0&&!postcondition.trim().equals("")){
							umlModel.setPostCondition(postcondition);
						}
						if(readonly!=null&&readonly.trim().length()>0&&!readonly.trim().equals("")){
							umlModel.setReadOnly(readonly);
						}
						if(parametername!=null&&parametername.trim().length()>0&&!parametername.trim().equals("")){
							umlModel.setParameterName(parametername);
						}
						if(multi!=null&&multi.trim().length()>0&&!multi.trim().equals("")){
							umlModel.setMultiplicity(multi);
						}
						if(singleexecution!=null&&singleexecution.trim().length()>0&&!singleexecution.trim().equals("")){
							umlModel.setSingleExecution(singleexecution);
						}
						//PKY 08060201 E
						if(desc!=null)//PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제						
							umlModel.setDesc(desc.replaceAll("/n", "\n"));//2008043001 PKY STag 바운리더,패키지 저장안되는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제	
						//						umlModel.setDesc(desc);//2008043001 PKY S 속성 저장되지 않는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제
						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
						if(tag.size()>0)
							umlModel.setTags(tag);
						//2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
						Object parentObject = modelStore.get(parentID);
						if(parentObject==null){
							to1.setParent(root);
							root.addChild(to1);
						}
						else{
							to1.setParent((UMLTreeParentModel)parentObject);
							UMLTreeParentModel up= (UMLTreeParentModel)parentObject;
							if (to1 != null) {
								up.addChild(to1);
							}
						}
						modelStore.put(id, to1);
						ProjectManager.getInstance().addTypeModel(to1);//PKY 08052901 S 저장 불러오기할경우 참조타입정보가 없다 참조타입정보를 넣어 줄수 있도록 수정
					}
				}
				else if(current_model_type==19){
					if(this.isBoundary){

					}
					else{
						String id = current_UMLDataModel.getProperty("ID");
						String name = current_UMLDataModel.getProperty("ID_NAME");
						String parentID = current_UMLDataModel.getProperty("parentID");
						String desc = current_UMLDataModel.getProperty("ID_DESCRIPTION");//2008043001 PKY S 속성 저장되지 않는문제
						//PKY 08060201 S
						String stereo = current_UMLDataModel.getProperty("ID_STEREOTYPE");
						String scope = current_UMLDataModel.getProperty("ID_SCOPE");
						String precondition = current_UMLDataModel.getProperty("ID_PRECONDITION");
						String postcondition = current_UMLDataModel.getProperty("ID_POSTCONDITION");
						String readonly = current_UMLDataModel.getProperty("ID_READONLY");
						String parametername = current_UMLDataModel.getProperty("ID_PARAMETERNAME");
						String multi = current_UMLDataModel.getProperty("ID_MULTI");
						String singleexecution = current_UMLDataModel.getProperty("ID_SINGLEEXECUTION");
						String objectState = current_UMLDataModel.getProperty("ID_OBJECT_STATE");//PKY 08061101 S Object 객체 상태 저장안되는 문제
						String requirment = current_UMLDataModel.getProperty("ID_REQ_ID");//PKY 08072401 S Requirement 저장 불러오기 로직 추가
						//PKY 08081801 S 오퍼레이션 어트리뷰트 숨길수 있는 기능
						String attrHidden = current_UMLDataModel.getProperty("ID_ATTR_HIDDEN");
						String operHidden = current_UMLDataModel.getProperty("ID_OPER_HIDDEN");
						//PKY 08081801 E 오퍼레이션 어트리뷰트 숨길수 있는 기능

						//PKY 08060201 E

						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
						java.util.ArrayList tag = new java.util.ArrayList();
						Object obj=current_UMLDataModel.getElementProperty("ID_TAG");
						if(obj!=null && obj instanceof java.util.ArrayList){
							tag = (java.util.ArrayList)obj;

						}
						//2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
						UMLTreeModel  to1 = new UMLTreeModel(name);
						UMLModel umlModel = null;
						umlModel = new FinalObjectNodeModel(current_UMLDataModel,true);
						to1.setRefModel(umlModel);
						umlModel.setName(name);
						umlModel.setTreeModel(to1);
						//PKY 08081801 S 오퍼레이션 어트리뷰트 숨길수 있는 기능
						if(attrHidden!=null&&attrHidden.trim().length()>0&&!attrHidden.trim().equals("")&&attrHidden.equals("1"))
							umlModel.setShowAttr(attrHidden);
						if(operHidden!=null&&operHidden.trim().length()>0&&!operHidden.trim().equals("")&&operHidden.equals("1"))
							umlModel.setShowOper(operHidden);
						//PKY 08081801 E 오퍼레이션 어트리뷰트 숨길수 있는 기능
						//PKY 08072401 S Requirement 저장 불러오기 로직 추가
						if(requirment!=null&&requirment.trim().length()>0&&!requirment.trim().equals("")){
							umlModel.setReqID(requirment);
						}
						//PKY 08060201 S
						//PKY 08061101 S Object 객체 상태 저장안되는 문제
						if(objectState!=null&&objectState.trim().length()>0&&!objectState.trim().equals("")){
							umlModel.setObjectState(objectState);
						}
						//PKY 08061101 E Object 객체 상태 저장안되는 문제
						if(stereo!=null&&stereo.trim().length()>0&&!stereo.trim().equals("")){
							umlModel.setStereotype(stereo);
						}
						if(scope!=null&&scope.trim().length()>0&&!scope.trim().equals("")){
							umlModel.setScope(scope);
						}						
						if(precondition!=null&&precondition.trim().length()>0&&!precondition.trim().equals("")){
							umlModel.setPreCondition(precondition);
						}
						if(postcondition!=null&&postcondition.trim().length()>0&&!postcondition.trim().equals("")){
							umlModel.setPostCondition(postcondition);
						}
						if(readonly!=null&&readonly.trim().length()>0&&!readonly.trim().equals("")){
							umlModel.setReadOnly(readonly);
						}
						if(parametername!=null&&parametername.trim().length()>0&&!parametername.trim().equals("")){
							umlModel.setParameterName(parametername);
						}
						if(multi!=null&&multi.trim().length()>0&&!multi.trim().equals("")){
							umlModel.setMultiplicity(multi);
						}
						if(singleexecution!=null&&singleexecution.trim().length()>0&&!singleexecution.trim().equals("")){
							umlModel.setSingleExecution(singleexecution);
						}
						//PKY 08060201 E
						if(desc!=null)//PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제						
							umlModel.setDesc(desc.replaceAll("/n", "\n"));//2008043001 PKY STag 바운리더,패키지 저장안되는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제	
						//						umlModel.setDesc(desc);//2008043001 PKY S 속성 저장되지 않는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제
						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
						if(tag.size()>0)
							umlModel.setTags(tag);
						//2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
						Object parentObject = modelStore.get(parentID);
						if(parentObject==null){
							to1.setParent(root);
							root.addChild(to1);
						}
						else{
							to1.setParent((UMLTreeParentModel)parentObject);
							UMLTreeParentModel up= (UMLTreeParentModel)parentObject;
							if (to1 != null) {
								up.addChild(to1);
							}
						}
						modelStore.put(id, to1);
						ProjectManager.getInstance().addTypeModel(to1);//PKY 08052901 S 저장 불러오기할경우 참조타입정보가 없다 참조타입정보를 넣어 줄수 있도록 수정
					}
				}
				else if(current_model_type==20){
					if(this.isBoundary){

					}
					else{
						String id = current_UMLDataModel.getProperty("ID");
						String name = current_UMLDataModel.getProperty("ID_NAME");
						String parentID = current_UMLDataModel.getProperty("parentID");
						String desc = current_UMLDataModel.getProperty("ID_DESCRIPTION");//2008043001 PKY S 속성 저장되지 않는문제
						//PKY 08060201 S
						String stereo = current_UMLDataModel.getProperty("ID_STEREOTYPE");
						String scope = current_UMLDataModel.getProperty("ID_SCOPE");
						String precondition = current_UMLDataModel.getProperty("ID_PRECONDITION");
						String postcondition = current_UMLDataModel.getProperty("ID_POSTCONDITION");
						String readonly = current_UMLDataModel.getProperty("ID_READONLY");
						String parametername = current_UMLDataModel.getProperty("ID_PARAMETERNAME");
						String multi = current_UMLDataModel.getProperty("ID_MULTI");
						String singleexecution = current_UMLDataModel.getProperty("ID_SINGLEEXECUTION");
						String requirment = current_UMLDataModel.getProperty("ID_REQ_ID");//PKY 08072401 S Requirement 저장 불러오기 로직 추가
						//PKY 08060201 E
						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
						java.util.ArrayList tag = new java.util.ArrayList();
						Object obj=current_UMLDataModel.getElementProperty("ID_TAG");
						if(obj!=null && obj instanceof java.util.ArrayList){
							tag = (java.util.ArrayList)obj;

						}
						//2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
						UMLTreeModel  to1 = new UMLTreeModel(name);
						UMLModel umlModel = null;
						umlModel = new CentralBufferNodeModel(current_UMLDataModel,true);
						to1.setRefModel(umlModel);
						umlModel.setName(name);
						umlModel.setTreeModel(to1);
						//PKY 08072401 S Requirement 저장 불러오기 로직 추가
						if(requirment!=null&&requirment.trim().length()>0&&!requirment.trim().equals("")){
							umlModel.setReqID(requirment);
						}
						//PKY 08060201 S
						if(stereo!=null&&stereo.trim().length()>0&&!stereo.trim().equals("")){
							umlModel.setStereotype(stereo);
						}
						if(scope!=null&&scope.trim().length()>0&&!scope.trim().equals("")){
							umlModel.setScope(scope);
						}						
						if(precondition!=null&&precondition.trim().length()>0&&!precondition.trim().equals("")){
							umlModel.setPreCondition(precondition);
						}
						if(postcondition!=null&&postcondition.trim().length()>0&&!postcondition.trim().equals("")){
							umlModel.setPostCondition(postcondition);
						}
						if(readonly!=null&&readonly.trim().length()>0&&!readonly.trim().equals("")){
							umlModel.setReadOnly(readonly);
						}
						if(parametername!=null&&parametername.trim().length()>0&&!parametername.trim().equals("")){
							umlModel.setParameterName(parametername);
						}
						if(multi!=null&&multi.trim().length()>0&&!multi.trim().equals("")){
							umlModel.setMultiplicity(multi);
						}
						if(singleexecution!=null&&singleexecution.trim().length()>0&&!singleexecution.trim().equals("")){
							umlModel.setSingleExecution(singleexecution);
						}
						//PKY 08060201 E
						if(desc!=null)//PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제						
							umlModel.setDesc(desc.replaceAll("/n", "\n"));//2008043001 PKY STag 바운리더,패키지 저장안되는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제	
						//						umlModel.setDesc(desc);//2008043001 PKY S 속성 저장되지 않는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제
						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
						if(tag.size()>0)
							umlModel.setTags(tag);
						//						2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
						Object parentObject = modelStore.get(parentID);
						if(parentObject==null){
							to1.setParent(root);
							root.addChild(to1);
						}
						else{
							to1.setParent((UMLTreeParentModel)parentObject);
							UMLTreeParentModel up= (UMLTreeParentModel)parentObject;
							if (to1 != null) {
								up.addChild(to1);
							}
						}
						modelStore.put(id, to1);
						ProjectManager.getInstance().addTypeModel(to1);//PKY 08052901 S 저장 불러오기할경우 참조타입정보가 없다 참조타입정보를 넣어 줄수 있도록 수정
					}
				}
				else if(current_model_type==21){
					if(this.isBoundary){

					}
					else{
						String id = current_UMLDataModel.getProperty("ID");
						String name = current_UMLDataModel.getProperty("ID_NAME");
						String parentID = current_UMLDataModel.getProperty("parentID");
						String desc = current_UMLDataModel.getProperty("ID_DESCRIPTION");//2008043001 PKY S 속성 저장되지 않는문제
						//PKY 08060201 S
						String stereo = current_UMLDataModel.getProperty("ID_STEREOTYPE");
						String scope = current_UMLDataModel.getProperty("ID_SCOPE");
						String precondition = current_UMLDataModel.getProperty("ID_PRECONDITION");
						String postcondition = current_UMLDataModel.getProperty("ID_POSTCONDITION");
						String readonly = current_UMLDataModel.getProperty("ID_READONLY");
						String parametername = current_UMLDataModel.getProperty("ID_PARAMETERNAME");
						String multi = current_UMLDataModel.getProperty("ID_MULTI");
						String singleexecution = current_UMLDataModel.getProperty("ID_SINGLEEXECUTION");
						String objcetState = current_UMLDataModel.getProperty("ID_OBJECT_STATE");//PKY 08062601 S Datastore OBJECT_STAT 저장불러온 후 사라지는문제
						String requirment = current_UMLDataModel.getProperty("ID_REQ_ID");//PKY 08072401 S Requirement 저장 불러오기 로직 추가
						//PKY 08081801 S 오퍼레이션 어트리뷰트 숨길수 있는 기능
						String attrHidden = current_UMLDataModel.getProperty("ID_ATTR_HIDDEN");
						String operHidden = current_UMLDataModel.getProperty("ID_OPER_HIDDEN");
						//PKY 08081801 E 오퍼레이션 어트리뷰트 숨길수 있는 기능
						//PKY 08060201 E
						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
						java.util.ArrayList tag = new java.util.ArrayList();
						Object obj=current_UMLDataModel.getElementProperty("ID_TAG");

						if(obj!=null && obj instanceof java.util.ArrayList){
							tag = (java.util.ArrayList)obj;

						}
						//2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
						UMLTreeModel  to1 = new UMLTreeModel(name);
						UMLModel umlModel = null;
						umlModel = new DataStoreModel(current_UMLDataModel,true);
						to1.setRefModel(umlModel);
						umlModel.setName(name);
						umlModel.setTreeModel(to1);
						umlModel.setObjectState(objcetState); //PKY 08062601 S Datastore OBJECT_STAT 저장불러온 후 사라지는문제
						//PKY 08081801 S 오퍼레이션 어트리뷰트 숨길수 있는 기능
						if(attrHidden!=null&&attrHidden.trim().length()>0&&!attrHidden.trim().equals("")&&attrHidden.equals("1"))
							umlModel.setShowAttr(attrHidden);
						if(operHidden!=null&&operHidden.trim().length()>0&&!operHidden.trim().equals("")&&operHidden.equals("1"))
							umlModel.setShowOper(operHidden);
						//PKY 08081801 E 오퍼레이션 어트리뷰트 숨길수 있는 기
						//PKY 08072401 S Requirement 저장 불러오기 로직 추가
						if(requirment!=null&&requirment.trim().length()>0&&!requirment.trim().equals("")){
							umlModel.setReqID(requirment);
						}
						//PKY 08060201 S
						if(stereo!=null&&stereo.trim().length()>0&&!stereo.trim().equals("")){
							umlModel.setStereotype(stereo);
						}
						if(scope!=null&&scope.trim().length()>0&&!scope.trim().equals("")){
							umlModel.setScope(scope);
						}						
						if(precondition!=null&&precondition.trim().length()>0&&!precondition.trim().equals("")){
							umlModel.setPreCondition(precondition);
						}
						if(postcondition!=null&&postcondition.trim().length()>0&&!postcondition.trim().equals("")){
							umlModel.setPostCondition(postcondition);
						}
						if(readonly!=null&&readonly.trim().length()>0&&!readonly.trim().equals("")){
							umlModel.setReadOnly(readonly);
						}
						if(parametername!=null&&parametername.trim().length()>0&&!parametername.trim().equals("")){
							umlModel.setParameterName(parametername);
						}
						if(multi!=null&&multi.trim().length()>0&&!multi.trim().equals("")){
							umlModel.setMultiplicity(multi);
						}
						if(singleexecution!=null&&singleexecution.trim().length()>0&&!singleexecution.trim().equals("")){
							umlModel.setSingleExecution(singleexecution);
						}
						//PKY 08060201 E
						if(desc!=null)//PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제						
							umlModel.setDesc(desc.replaceAll("/n", "\n"));//2008043001 PKY STag 바운리더,패키지 저장안되는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제	
						//						umlModel.setDesc(desc);//2008043001 PKY S 속성 저장되지 않는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제
						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
						if(tag.size()>0)
							umlModel.setTags(tag);
						//						2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
						Object parentObject = modelStore.get(parentID);
						if(parentObject==null){
							to1.setParent(root);
							root.addChild(to1);
						}
						else{
							to1.setParent((UMLTreeParentModel)parentObject);
							UMLTreeParentModel up= (UMLTreeParentModel)parentObject;
							if (to1 != null) {
								up.addChild(to1);
							}
						}
						modelStore.put(id, to1);
						ProjectManager.getInstance().addTypeModel(to1);//PKY 08052901 S 저장 불러오기할경우 참조타입정보가 없다 참조타입정보를 넣어 줄수 있도록 수정
					}
				}
				else if(current_model_type==22){
					if(this.isBoundary){

					}
					else{
						String id = current_UMLDataModel.getProperty("ID");
						String name = current_UMLDataModel.getProperty("ID_NAME");
						String parentID = current_UMLDataModel.getProperty("parentID");
						String desc = current_UMLDataModel.getProperty("ID_DESCRIPTION");//2008043001 PKY S 속성 저장되지 않는문제
						//PKY 08060201 S
						String stereo = current_UMLDataModel.getProperty("ID_STEREOTYPE");
						String scope = current_UMLDataModel.getProperty("ID_SCOPE");
						String precondition = current_UMLDataModel.getProperty("ID_PRECONDITION");
						String postcondition = current_UMLDataModel.getProperty("ID_POSTCONDITION");
						String readonly = current_UMLDataModel.getProperty("ID_READONLY");
						String parametername = current_UMLDataModel.getProperty("ID_PARAMETERNAME");
						String multi = current_UMLDataModel.getProperty("ID_MULTI");
						String singleexecution = current_UMLDataModel.getProperty("ID_SINGLEEXECUTION");
						String requirment = current_UMLDataModel.getProperty("ID_REQ_ID");//PKY 08072401 S Requirement 저장 불러오기 로직 추가
						//PKY 08060201 E

						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
						java.util.ArrayList tag = new java.util.ArrayList();
						Object obj=current_UMLDataModel.getElementProperty("ID_TAG");

						if(obj!=null && obj instanceof java.util.ArrayList){
							tag = (java.util.ArrayList)obj;

						}
						//2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
						UMLTreeModel  to1 = new UMLTreeModel(name);
						UMLModel umlModel = null;
						umlModel = new HPartitionModel();
						to1.setRefModel(umlModel);
						umlModel.setName(name);
						umlModel.setTreeModel(to1);
						//PKY 08060201 S
						//PKY 08072401 S Requirement 저장 불러오기 로직 추가
						if(requirment!=null&&requirment.trim().length()>0&&!requirment.trim().equals("")){
							umlModel.setReqID(requirment);
						}
						if(stereo!=null&&stereo.trim().length()>0&&!stereo.trim().equals("")){
							umlModel.setStereotype(stereo);
						}
						if(scope!=null&&scope.trim().length()>0&&!scope.trim().equals("")){
							umlModel.setScope(scope);
						}						
						if(precondition!=null&&precondition.trim().length()>0&&!precondition.trim().equals("")){
							umlModel.setPreCondition(precondition);
						}
						if(postcondition!=null&&postcondition.trim().length()>0&&!postcondition.trim().equals("")){
							umlModel.setPostCondition(postcondition);
						}
						if(readonly!=null&&readonly.trim().length()>0&&!readonly.trim().equals("")){
							umlModel.setReadOnly(readonly);
						}
						if(parametername!=null&&parametername.trim().length()>0&&!parametername.trim().equals("")){
							umlModel.setParameterName(parametername);
						}
						if(multi!=null&&multi.trim().length()>0&&!multi.trim().equals("")){
							umlModel.setMultiplicity(multi);
						}
						if(singleexecution!=null&&singleexecution.trim().length()>0&&!singleexecution.trim().equals("")){
							umlModel.setSingleExecution(singleexecution);
						}
						//PKY 08060201 E
						if(desc!=null)//PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제						
							umlModel.setDesc(desc.replaceAll("/n", "\n"));//2008043001 PKY STag 바운리더,패키지 저장안되는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제	
						//						umlModel.setDesc(desc);//2008043001 PKY S 속성 저장되지 않는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제

						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
						if(tag.size()>0)
							umlModel.setTags(tag);
						//						2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
						Object parentObject = modelStore.get(parentID);
						if(parentObject==null){
							to1.setParent(root);
							root.addChild(to1);
						}
						else{
							to1.setParent((UMLTreeParentModel)parentObject);
							UMLTreeParentModel up= (UMLTreeParentModel)parentObject;
							if (to1 != null) {
								up.addChild(to1);
							}
						}
						modelStore.put(id, to1);
						ProjectManager.getInstance().addTypeModel(to1);//PKY 08052901 S 저장 불러오기할경우 참조타입정보가 없다 참조타입정보를 넣어 줄수 있도록 수정
					}
				}
				else if(current_model_type==23){
					if(this.isBoundary){

					}
					else{
						String id = current_UMLDataModel.getProperty("ID");
						String name = current_UMLDataModel.getProperty("ID_NAME");
						String parentID = current_UMLDataModel.getProperty("parentID");
						String desc = current_UMLDataModel.getProperty("ID_DESCRIPTION");//2008043001 PKY S 속성 저장되지 않는문제
						//PKY 08060201 S
						String stereo = current_UMLDataModel.getProperty("ID_STEREOTYPE");
						String scope = current_UMLDataModel.getProperty("ID_SCOPE");
						String precondition = current_UMLDataModel.getProperty("ID_PRECONDITION");
						String postcondition = current_UMLDataModel.getProperty("ID_POSTCONDITION");
						String readonly = current_UMLDataModel.getProperty("ID_READONLY");
						String parametername = current_UMLDataModel.getProperty("ID_PARAMETERNAME");
						String multi = current_UMLDataModel.getProperty("ID_MULTI");
						String singleexecution = current_UMLDataModel.getProperty("ID_SINGLEEXECUTION");
						String requirment = current_UMLDataModel.getProperty("ID_REQ_ID");//PKY 08072401 S Requirement 저장 불러오기 로직 추가
						//PKY 08060201 E

						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
						java.util.ArrayList tag = new java.util.ArrayList();
						Object obj=current_UMLDataModel.getElementProperty("ID_TAG");
						if(obj!=null && obj instanceof java.util.ArrayList){
							tag = (java.util.ArrayList)obj;

						}
						//2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
						UMLTreeModel  to1 = new UMLTreeModel(name);
						UMLModel umlModel = null;
						umlModel = new VPartitionModel();
						to1.setRefModel(umlModel);
						umlModel.setName(name);
						//PKY 08072401 S Requirement 저장 불러오기 로직 추가
						if(requirment!=null&&requirment.trim().length()>0&&!requirment.trim().equals("")){
							umlModel.setReqID(requirment);
						}
						//PKY 08060201 S
						if(stereo!=null&&stereo.trim().length()>0&&!stereo.trim().equals("")){
							umlModel.setStereotype(stereo);
						}
						if(scope!=null&&scope.trim().length()>0&&!scope.trim().equals("")){
							umlModel.setScope(scope);
						}						
						if(precondition!=null&&precondition.trim().length()>0&&!precondition.trim().equals("")){
							umlModel.setPreCondition(precondition);
						}
						if(postcondition!=null&&postcondition.trim().length()>0&&!postcondition.trim().equals("")){
							umlModel.setPostCondition(postcondition);
						}
						if(readonly!=null&&readonly.trim().length()>0&&!readonly.trim().equals("")){
							umlModel.setReadOnly(readonly);
						}
						if(parametername!=null&&parametername.trim().length()>0&&!parametername.trim().equals("")){
							umlModel.setParameterName(parametername);
						}
						if(multi!=null&&multi.trim().length()>0&&!multi.trim().equals("")){
							umlModel.setMultiplicity(multi);
						}
						if(singleexecution!=null&&singleexecution.trim().length()>0&&!singleexecution.trim().equals("")){
							umlModel.setSingleExecution(singleexecution);
						}
						//PKY 08060201 E

						umlModel.setTreeModel(to1);
						if(desc!=null)//PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제						
							umlModel.setDesc(desc.replaceAll("/n", "\n"));//2008043001 PKY STag 바운리더,패키지 저장안되는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제	
						//						umlModel.setDesc(desc);//2008043001 PKY S 속성 저장되지 않는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제
						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
						if(tag.size()>0)
							umlModel.setTags(tag);
						//						2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
						Object parentObject = modelStore.get(parentID);
						if(parentObject==null){
							to1.setParent(root);
							root.addChild(to1);
						}
						else{
							to1.setParent((UMLTreeParentModel)parentObject);
							UMLTreeParentModel up= (UMLTreeParentModel)parentObject;
							if (to1 != null) {
								up.addChild(to1);
							}
						}
						modelStore.put(id, to1);
						ProjectManager.getInstance().addTypeModel(to1);//PKY 08052901 S 저장 불러오기할경우 참조타입정보가 없다 참조타입정보를 넣어 줄수 있도록 수정
					}
				}
				else if(current_model_type==24){
					if(this.isBoundary){

					}
					else{
						String id = current_UMLDataModel.getProperty("ID");
						String name = current_UMLDataModel.getProperty("ID_NAME");
						String parentID = current_UMLDataModel.getProperty("parentID");
						String desc = current_UMLDataModel.getProperty("ID_DESCRIPTION");//2008043001 PKY S 속성 저장되지 않는문제
						//PKY 08060201 S
						String stereo = current_UMLDataModel.getProperty("ID_STEREOTYPE");
						String scope = current_UMLDataModel.getProperty("ID_SCOPE");
						String precondition = current_UMLDataModel.getProperty("ID_PRECONDITION");
						String postcondition = current_UMLDataModel.getProperty("ID_POSTCONDITION");
						String readonly = current_UMLDataModel.getProperty("ID_READONLY");
						String parametername = current_UMLDataModel.getProperty("ID_PARAMETERNAME");
						String multi = current_UMLDataModel.getProperty("ID_MULTI");
						String singleexecution = current_UMLDataModel.getProperty("ID_SINGLEEXECUTION");
						String type =current_UMLDataModel.getProperty("ID_TYPE");
						String requirment = current_UMLDataModel.getProperty("ID_REQ_ID");//PKY 08072401 S Requirement 저장 불러오기 로직 추가
						//PKY 08060201 E
						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
						java.util.ArrayList tag = new java.util.ArrayList();
						Object obj=current_UMLDataModel.getElementProperty("ID_TAG");
						if(obj!=null && obj instanceof java.util.ArrayList){
							tag = (java.util.ArrayList)obj;

						}
						//2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
						UMLTreeModel  to1 = new UMLTreeModel(name);
						UMLModel umlModel = null;
						//PKY 08061101 S VForkJoinModel,HForkJoinModel 저장 불러올경우 VForkJoinModel로 작성되는문제
						if(type.equals("VForkJoinModel")){
							umlModel = new VForkJoinModel();
						}else{
							umlModel = new HForkJoinModel();
						}				
						//PKY 08061101 E VForkJoinModel,HForkJoinModel 저장 불러올경우 VForkJoinModel로 작성되는문제
						to1.setRefModel(umlModel);
						umlModel.setName(name);
						umlModel.setTreeModel(to1);
						//PKY 08072401 S Requirement 저장 불러오기 로직 추가
						if(requirment!=null&&requirment.trim().length()>0&&!requirment.trim().equals("")){
							umlModel.setReqID(requirment);
						}
						//PKY 08060201 S
						if(stereo!=null&&stereo.trim().length()>0&&!stereo.trim().equals("")){
							umlModel.setPropertyValue("ID_STEREOTYPE",stereo);
							umlModel.setStereotype(stereo);
						}
						if(scope!=null&&scope.trim().length()>0&&!scope.trim().equals("")){
							umlModel.setScope(scope);
						}						
						if(precondition!=null&&precondition.trim().length()>0&&!precondition.trim().equals("")){
							umlModel.setPreCondition(precondition);
						}
						if(postcondition!=null&&postcondition.trim().length()>0&&!postcondition.trim().equals("")){
							umlModel.setPostCondition(postcondition);
						}
						if(readonly!=null&&readonly.trim().length()>0&&!readonly.trim().equals("")){
							umlModel.setReadOnly(readonly);
						}
						if(parametername!=null&&parametername.trim().length()>0&&!parametername.trim().equals("")){
							umlModel.setParameterName(parametername);
						}
						if(multi!=null&&multi.trim().length()>0&&!multi.trim().equals("")){
							umlModel.setMultiplicity(multi);
						}
						if(singleexecution!=null&&singleexecution.trim().length()>0&&!singleexecution.trim().equals("")){
							umlModel.setSingleExecution(singleexecution);
						}
						//PKY 08060201 E
						if(desc!=null)//PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제						
							umlModel.setDesc(desc.replaceAll("/n", "\n"));//2008043001 PKY STag 바운리더,패키지 저장안되는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제	
						//						umlModel.setDesc(desc);//2008043001 PKY S 속성 저장되지 않는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제
						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
						if(tag.size()>0)
							umlModel.setTags(tag);
						//						2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
						Object parentObject = modelStore.get(parentID);
						if(parentObject==null){
							to1.setParent(root);
							root.addChild(to1);
						}
						else{
							to1.setParent((UMLTreeParentModel)parentObject);
							UMLTreeParentModel up= (UMLTreeParentModel)parentObject;
							if (to1 != null) {
								up.addChild(to1);
							}
						}
						modelStore.put(id, to1);
						ProjectManager.getInstance().addTypeModel(to1);//PKY 08052901 S 저장 불러오기할경우 참조타입정보가 없다 참조타입정보를 넣어 줄수 있도록 수정
					}
				}
				else if(current_model_type==25){
					if(this.isBoundary){

					}
					else{
						String id = current_UMLDataModel.getProperty("ID");
						String name = current_UMLDataModel.getProperty("ID_NAME");
						String parentID = current_UMLDataModel.getProperty("parentID");
						String desc = current_UMLDataModel.getProperty("ID_DESCRIPTION");//2008043001 PKY S 속성 저장되지 않는문제
						//PKY 08060201 S
						String stereo = current_UMLDataModel.getProperty("ID_STEREOTYPE");
						String scope = current_UMLDataModel.getProperty("ID_SCOPE");
						String precondition = current_UMLDataModel.getProperty("ID_PRECONDITION");
						String postcondition = current_UMLDataModel.getProperty("ID_POSTCONDITION");
						String readonly = current_UMLDataModel.getProperty("ID_READONLY");
						String parametername = current_UMLDataModel.getProperty("ID_PARAMETERNAME");
						String multi = current_UMLDataModel.getProperty("ID_MULTI");
						String singleexecution = current_UMLDataModel.getProperty("ID_SINGLEEXECUTION");
						String requirment = current_UMLDataModel.getProperty("ID_REQ_ID");//PKY 08072401 S Requirement 저장 불러오기 로직 추가
						//PKY 08060201 E
						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
						java.util.ArrayList tag = new java.util.ArrayList();
						Object obj=current_UMLDataModel.getElementProperty("ID_TAG");
						if(obj!=null && obj instanceof java.util.ArrayList){
							tag = (java.util.ArrayList)obj;

						}
						//2008043001 PKY E Tag 바운리더,패키지 저장안되는문제

						UMLTreeModel  to1 = new UMLTreeModel(name);
						UMLModel umlModel = null;
						umlModel = new VForkJoinModel();
						to1.setRefModel(umlModel);
						umlModel.setName(name);
						umlModel.setTreeModel(to1);
						//PKY 08060201 S
						//PKY 08072401 S Requirement 저장 불러오기 로직 추가
						if(requirment!=null&&requirment.trim().length()>0&&!requirment.trim().equals("")){
							umlModel.setReqID(requirment);
						}
						if(stereo!=null&&stereo.trim().length()>0&&!stereo.trim().equals("")){
							umlModel.setStereotype(stereo);
						}
						if(scope!=null&&scope.trim().length()>0&&!scope.trim().equals("")){
							umlModel.setScope(scope);
						}						
						if(precondition!=null&&precondition.trim().length()>0&&!precondition.trim().equals("")){
							umlModel.setPreCondition(precondition);
						}
						if(postcondition!=null&&postcondition.trim().length()>0&&!postcondition.trim().equals("")){
							umlModel.setPostCondition(postcondition);
						}
						if(readonly!=null&&readonly.trim().length()>0&&!readonly.trim().equals("")){
							umlModel.setReadOnly(readonly);
						}
						if(parametername!=null&&parametername.trim().length()>0&&!parametername.trim().equals("")){
							umlModel.setParameterName(parametername);
						}
						if(multi!=null&&multi.trim().length()>0&&!multi.trim().equals("")){
							umlModel.setMultiplicity(multi);
						}
						if(singleexecution!=null&&singleexecution.trim().length()>0&&!singleexecution.trim().equals("")){
							umlModel.setSingleExecution(singleexecution);
						}
						//PKY 08060201 E
						if(desc!=null)//PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제						
							umlModel.setDesc(desc.replaceAll("/n", "\n"));//2008043001 PKY STag 바운리더,패키지 저장안되는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제	
						//						umlModel.setDesc(desc);//2008043001 PKY S 속성 저장되지 않는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제
						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
						if(tag.size()>0)
							umlModel.setTags(tag);
						//						2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
						Object parentObject = modelStore.get(parentID);
						if(parentObject==null){
							to1.setParent(root);
							root.addChild(to1);
						}
						else{
							to1.setParent((UMLTreeParentModel)parentObject);
							UMLTreeParentModel up= (UMLTreeParentModel)parentObject;
							if (to1 != null) {
								up.addChild(to1);
							}
						}
						modelStore.put(id, to1);
						ProjectManager.getInstance().addTypeModel(to1);//PKY 08052901 S 저장 불러오기할경우 참조타입정보가 없다 참조타입정보를 넣어 줄수 있도록 수정
					}
				}
				else if(current_model_type==26){
					if(this.isBoundary){

					}
					else{
						String id = current_UMLDataModel.getProperty("ID");
						String name = current_UMLDataModel.getProperty("ID_NAME");
						String parentID = current_UMLDataModel.getProperty("parentID");
						String desc = current_UMLDataModel.getProperty("ID_DESCRIPTION");//2008043001 PKY S 속성 저장되지 않는문제
						String strcuturedActivityType = current_UMLDataModel.getProperty("StrcuturedActivityType");//PKY 08050701 S strcuturedActivity
						String stereo = current_UMLDataModel.getProperty("ID_STEREOTYPE");//PKY 08060201 S
						//PKY 08060201 S
						String scope = current_UMLDataModel.getProperty("ID_SCOPE");
						String precondition = current_UMLDataModel.getProperty("ID_PRECONDITION");
						String postcondition = current_UMLDataModel.getProperty("ID_POSTCONDITION");
						String readonly = current_UMLDataModel.getProperty("ID_READONLY");
						String parametername = current_UMLDataModel.getProperty("ID_PARAMETERNAME");
						String multi = current_UMLDataModel.getProperty("ID_MULTI");
						String singleexecution = current_UMLDataModel.getProperty("ID_SINGLEEXECUTION");
						String requirment = current_UMLDataModel.getProperty("ID_REQ_ID");//PKY 08072401 S Requirement 저장 불러오기 로직 추가
						//PKY 08081801 S 오퍼레이션 어트리뷰트 숨길수 있는 기능
						String attrHidden = current_UMLDataModel.getProperty("ID_ATTR_HIDDEN");
						String operHidden = current_UMLDataModel.getProperty("ID_OPER_HIDDEN");
						//PKY 08081801 E 오퍼레이션 어트리뷰트 숨길수 있는 기능
						//PKY 08060201 E
						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
						java.util.ArrayList tag = new java.util.ArrayList();
						Object obj=current_UMLDataModel.getElementProperty("ID_TAG");
						if(obj!=null && obj instanceof java.util.ArrayList){
							tag = (java.util.ArrayList)obj;

						}
						//2008043001 PKY E Tag 바운리더,패키지 저장안되는문제

						UMLTreeModel  to1 = new StrcuturedActivityPackageTreeModel(name);
						UMLModel umlModel = null;
						umlModel = new FinalStrcuturedActivityModel(current_UMLDataModel,true);//PKY 08072201 S StcutureActivity 어트리뷰트 오퍼레이션 저장안되는문제
						//PKY 08060201 S
						if(stereo!=null&&!stereo.trim().equals("")){
							umlModel.setStereotype(stereo);
						}
						//PKY 08060201 E
						to1.setRefModel(umlModel);
						umlModel.setName(name);
						umlModel.setTreeModel(to1);
						//PKY 08081801 S 오퍼레이션 어트리뷰트 숨길수 있는 기능
						if(attrHidden!=null&&attrHidden.trim().length()>0&&!attrHidden.trim().equals("")&&attrHidden.equals("1"))
							umlModel.setShowAttr(attrHidden);
						if(operHidden!=null&&operHidden.trim().length()>0&&!operHidden.trim().equals("")&&operHidden.equals("1"))
							umlModel.setShowOper(operHidden);
						//PKY 08081801 E 오퍼레이션 어트리뷰트 숨길수 있는 기능
						//PKY 08060201 S
						//PKY 08072201 S StcutureActivity 어트리뷰트 오퍼레이션 저장안되는문제
						//PKY 08072401 S Requirement 저장 불러오기 로직 추가

						if(requirment!=null&&requirment.trim().length()>0&&!requirment.trim().equals("")){
							umlModel.setReqID(requirment);
						}
						if(scope!=null&&scope.trim().length()>0&&!scope.trim().equals("")){
							umlModel.setScope(scope);
						}				
						//PKY 08072201 E StcutureActivity 어트리뷰트 오퍼레이션 저장안되는문제

						if(precondition!=null&&precondition.trim().length()>0&&!precondition.trim().equals("")){
							umlModel.setPreCondition(precondition);
						}
						if(postcondition!=null&&postcondition.trim().length()>0&&!postcondition.trim().equals("")){
							umlModel.setPostCondition(postcondition);
						}
						if(readonly!=null&&readonly.trim().length()>0&&!readonly.trim().equals("")){
							umlModel.setReadOnly(readonly);
						}
						if(parametername!=null&&parametername.trim().length()>0&&!parametername.trim().equals("")){
							umlModel.setParameterName(parametername);
						}
						if(multi!=null&&multi.trim().length()>0&&!multi.trim().equals("")){
							umlModel.setMultiplicity(multi);
						}
						if(singleexecution!=null&&singleexecution.trim().length()>0&&!singleexecution.trim().equals("")){
							umlModel.setSingleExecution(singleexecution);
						}
						//PKY 08060201 E
						//PKY 08060201 S Activity 저장 불러올경우 타입정보가 사라지는문제
						if(strcuturedActivityType!=null)
							((FinalStrcuturedActivityModel)umlModel).setType(Integer.parseInt(strcuturedActivityType));
						//PKY 08060201 E Activity 저장 불러올경우 타입정보가 사라지는문제
						//						((FinalStrcuturedActivityModel)umlModel).setType(Integer.parseInt(strcuturedActivityType));//PKY 08050701 S strcuturedActivity
						if(desc!=null)//PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제						
							umlModel.setDesc(desc.replaceAll("/n", "\n"));//2008043001 PKY STag 바운리더,패키지 저장안되는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제	
						//						umlModel.setDesc(desc);//2008043001 PKY S 속성 저장되지 않는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제
						to1.setRefModel(umlModel);
						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
						if(tag.size()>0)
							umlModel.setTags(tag);
						//						2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
						Object parentObject = modelStore.get(parentID);
						if(parentObject==null){
							to1.setParent(root);
							root.addChild(to1);
						}
						else{
							to1.setParent((UMLTreeParentModel)parentObject);
							UMLTreeParentModel up= (UMLTreeParentModel)parentObject;
							if (to1 != null) {
								up.addChild(to1);
							}
						}
						modelStore.put(id, to1);
						ProjectManager.getInstance().addTypeModel(to1);//PKY 08052901 S 저장 불러오기할경우 참조타입정보가 없다 참조타입정보를 넣어 줄수 있도록 수정
					}
				}
				else if(current_model_type==27){
					if(this.isBoundary){

					}
					else{
						String id = current_UMLDataModel.getProperty("ID");
						String name = current_UMLDataModel.getProperty("ID_NAME");
						String parentID = current_UMLDataModel.getProperty("parentID");
						String desc = current_UMLDataModel.getProperty("ID_DESCRIPTION");//2008043001 PKY S 속성 저장되지 않는문제
						String reference = current_UMLDataModel.getProperty("ID_REFERENCE");//PKY 08060201 S Composite Instance 저장되지 않는문제
						//PKY 08060201 S
						String stereo = current_UMLDataModel.getProperty("ID_STEREOTYPE");
						String scope = current_UMLDataModel.getProperty("ID_SCOPE");
						String precondition = current_UMLDataModel.getProperty("ID_PRECONDITION");
						String postcondition = current_UMLDataModel.getProperty("ID_POSTCONDITION");
						String readonly = current_UMLDataModel.getProperty("ID_READONLY");
						String parametername = current_UMLDataModel.getProperty("ID_PARAMETERNAME");
						String multi = current_UMLDataModel.getProperty("ID_MULTI");
						String singleexecution = current_UMLDataModel.getProperty("ID_SINGLEEXECUTION");
						String requirment = current_UMLDataModel.getProperty("ID_REQ_ID");//PKY 08072401 S Requirement 저장 불러오기 로직 추가
						//PKY 08081801 S 오퍼레이션 어트리뷰트 숨길수 있는 기능
						String attrHidden = current_UMLDataModel.getProperty("ID_ATTR_HIDDEN");
						String operHidden = current_UMLDataModel.getProperty("ID_OPER_HIDDEN");
						//PKY 08081801 E 오퍼레이션 어트리뷰트 숨길수 있는 기능
						//PKY 08060201 E
						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
						java.util.ArrayList tag = new java.util.ArrayList();
						Object obj=current_UMLDataModel.getElementProperty("ID_TAG");
						if(obj!=null && obj instanceof java.util.ArrayList){
							tag = (java.util.ArrayList)obj;

						}
						//2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
						UMLTreeModel  to1 = new StrcuturedActivityPackageTreeModel(name);
						UMLModel umlModel = null;
						umlModel = new PartModel(current_UMLDataModel,true);
						to1.setRefModel(umlModel);
						umlModel.setName(name);
						umlModel.setTreeModel(to1);
						//PKY 08060201 S

						//PKY 08081801 S 오퍼레이션 어트리뷰트 숨길수 있는 기능
						if(attrHidden!=null&&attrHidden.trim().length()>0&&!attrHidden.trim().equals("")&&attrHidden.equals("1"))
							umlModel.setShowAttr(attrHidden);
						if(operHidden!=null&&operHidden.trim().length()>0&&!operHidden.trim().equals("")&&operHidden.equals("1"))
							umlModel.setShowOper(operHidden);
						//PKY 08081801 E 오퍼레이션 어트리뷰트 숨길수 있는 기능
						//PKY 08072401 S Requirement 저장 불러오기 로직 추가
						if(requirment!=null&&requirment.trim().length()>0&&!requirment.trim().equals("")){
							umlModel.setReqID(requirment);
						}
						if(stereo!=null&&stereo.trim().length()>0&&!stereo.trim().equals("")){
							umlModel.setStereotype(stereo);
						}
						if(scope!=null&&scope.trim().length()>0&&!scope.trim().equals("")){
							umlModel.setScope(scope);
						}						
						if(precondition!=null&&precondition.trim().length()>0&&!precondition.trim().equals("")){
							umlModel.setPreCondition(precondition);
						}
						if(postcondition!=null&&postcondition.trim().length()>0&&!postcondition.trim().equals("")){
							umlModel.setPostCondition(postcondition);
						}
						if(readonly!=null&&readonly.trim().length()>0&&!readonly.trim().equals("")){
							umlModel.setReadOnly(readonly);
						}
						if(parametername!=null&&parametername.trim().length()>0&&!parametername.trim().equals("")){
							umlModel.setParameterName(parametername);
						}
						if(multi!=null&&multi.trim().length()>0&&!multi.trim().equals("")){
							umlModel.setMultiplicity(multi);
						}
						if(singleexecution!=null&&singleexecution.trim().length()>0&&!singleexecution.trim().equals("")){
							umlModel.setSingleExecution(singleexecution);
						}
						//PKY 08060201 E
						//PKY 08060201 S Composite Instance 저장되지 않는문제
						if(reference!=null)
							umlModel.setReference(reference);
						//PKY 08060201 E Composite Instance 저장되지 않는문제
						if(desc!=null)//PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제						
							umlModel.setDesc(desc.replaceAll("/n", "\n"));//2008043001 PKY STag 바운리더,패키지 저장안되는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제	
						//						umlModel.setDesc(desc);//2008043001 PKY S 속성 저장되지 않는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제
						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
						if(tag.size()>0)
							umlModel.setTags(tag);
						//						2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
						Object parentObject = modelStore.get(parentID);
						if(parentObject==null){
							to1.setParent(root);
							root.addChild(to1);
						}
						else{
							to1.setParent((UMLTreeParentModel)parentObject);
							UMLTreeParentModel up= (UMLTreeParentModel)parentObject;
							if (to1 != null) {
								up.addChild(to1);
							}
						}
						modelStore.put(id, to1);
						ProjectManager.getInstance().addTypeModel(to1);//PKY 08052901 S 저장 불러오기할경우 참조타입정보가 없다 참조타입정보를 넣어 줄수 있도록 수정
					}
				}
				else if(current_model_type==50){
					if(this.isBoundary){

					}
					else{

					}
				}
				else if(current_model_type==28){
					if(this.isBoundary){

					}
					else{
						//						String id = current_UMLDataModel.getProperty("ID");
						//						String name = current_UMLDataModel.getProperty("ID_NAME");
						//						String isActor = current_UMLDataModel.getProperty("isActor");
						//						String parentID = current_UMLDataModel.getProperty("parentID");
						//						String ref_ID = current_UMLDataModel.getProperty("REF_ID");
						//						//PKY 08060201 S
						//						String stereo = current_UMLDataModel.getProperty("ID_STEREOTYPE");
						//						String scope = current_UMLDataModel.getProperty("ID_SCOPE");
						//						String precondition = current_UMLDataModel.getProperty("ID_PRECONDITION");
						//						String postcondition = current_UMLDataModel.getProperty("ID_POSTCONDITION");
						//						String readonly = current_UMLDataModel.getProperty("ID_READONLY");
						//						String parametername = current_UMLDataModel.getProperty("ID_PARAMETERNAME");
						//						String multi = current_UMLDataModel.getProperty("ID_MULTI");
						//						String singleexecution = current_UMLDataModel.getProperty("ID_SINGLEEXECUTION");
						//						String requirment = current_UMLDataModel.getProperty("ID_REQ_ID");//PKY 08072401 S Requirement 저장 불러오기 로직 추가
						//						String desc = current_UMLDataModel.getProperty("ID_DESCRIPTION");//PKY 08081801 S 시퀀스,엑터,오브젝트 프로퍼티 정보가 저장안되는문제
						//						//PKY 08060201 E
						//						UMLTreeModel  to1 = new UMLTreeModel(name);
						//						UMLModel umlModel = null;
						//						//PKY 08081801 S 시퀀스,엑터,오브젝트 프로퍼티 정보가 저장안되는문제
						//						Object obj=current_UMLDataModel.getElementProperty("ID_TAG");
						//						java.util.ArrayList tag = new java.util.ArrayList();
						//						if(obj!=null && obj instanceof java.util.ArrayList){
						//							tag = (java.util.ArrayList)obj;
						//						}
						//						//PKY 08081801 E 시퀀스,엑터,오브젝트 프로퍼티 정보가 저장안되는문제
						//						if("true".equals(isActor)){
						//							umlModel = new LifeLineActorModel();
						//							if(ref_ID!=null && !ref_ID.trim().equals("")){
						//								umlModel.getUMLDataModel().setProperty("REF_ID",ref_ID);
						//							}
						//						}
						//						else{
						//							umlModel = new LifeLineModel();
						//							if(ref_ID!=null && !ref_ID.trim().equals("")){
						//								umlModel.getUMLDataModel().setProperty("REF_ID",ref_ID);
						//							}
						//						}
						//						//PKY 08081801 S 시퀀스,엑터,오브젝트 프로퍼티 정보가 저장안되는문제
						//						if(tag.size()>0)
						//							umlModel.setTags(tag);
						//						if(desc!=null&&desc.trim().length()>0&&!desc.trim().equals(""))						
						//							umlModel.setDesc(desc.replaceAll("/n", "\n"));
						//						//PKY 08081801 E 시퀀스,엑터,오브젝트 프로퍼티 정보가 저장안되는문제
						//						//ijs 080521
						//						umlModel.getUMLDataModel().setId(id);
						//
						//						to1.setRefModel(umlModel);
						//
						//						umlModel.setName(name);
						//						umlModel.setTreeModel(to1);
						//						//PKY 08060201 S
						//						//PKY 08072401 S Requirement 저장 불러오기 로직 추가
						//						if(requirment!=null&&requirment.trim().length()>0&&!requirment.trim().equals("")){
						//							umlModel.setReqID(requirment);
						//						}
						//						if(stereo!=null&&stereo.trim().length()>0&&!stereo.trim().equals("")){
						//							umlModel.setStereotype(stereo);
						//						}
						//						if(scope!=null&&scope.trim().length()>0&&!scope.trim().equals("")){
						//							umlModel.setScope(scope);
						//						}						
						//						if(precondition!=null&&precondition.trim().length()>0&&!precondition.trim().equals("")){
						//							umlModel.setPreCondition(precondition);
						//						}
						//						if(postcondition!=null&&postcondition.trim().length()>0&&!postcondition.trim().equals("")){
						//							umlModel.setPostCondition(postcondition);
						//						}
						//						if(readonly!=null&&readonly.trim().length()>0&&!readonly.trim().equals("")){
						//							umlModel.setReadOnly(readonly);
						//						}
						//						if(parametername!=null&&parametername.trim().length()>0&&!parametername.trim().equals("")){
						//							umlModel.setParameterName(parametername);
						//						}
						//						if(multi!=null&&multi.trim().length()>0&&!multi.trim().equals("")){
						//							umlModel.setMultiplicity(multi);
						//						}
						//						if(singleexecution!=null&&singleexecution.trim().length()>0&&!singleexecution.trim().equals("")){
						//							umlModel.setSingleExecution(singleexecution);
						//						}
						//						//PKY 08060201 E
						//						Object parentObject = modelStore.get(parentID);
						//						if(parentObject==null){
						//							to1.setParent(root);
						//							root.addChild(to1);
						//						}
						//						else{
						//							to1.setParent((UMLTreeParentModel)parentObject);
						//							UMLTreeParentModel up= (UMLTreeParentModel)parentObject;
						//							if (to1 != null) {
						//								up.addChild(to1);
						//							}
						//						}
						//						modelStore.put(id, to1);
						//						ProjectManager.getInstance().addTypeModel(to1);//PKY 08052901 S 저장 불러오기할경우 참조타입정보가 없다 참조타입정보를 넣어 줄수 있도록 수정
					}
				}

				else if(current_model_type==29){
					if(this.isBoundary){

					}
					else{
						String id = current_UMLDataModel.getProperty("ID");
						String name = current_UMLDataModel.getProperty("ID_NAME");
						
						String id_id = current_UMLDataModel.getProperty("ID_ID");
						String NodeItemModel_id = current_UMLDataModel.getProperty("NodeItemModel_id");
						
						String parentID = current_UMLDataModel.getProperty("parentID");
						String desc = current_UMLDataModel.getProperty("ID_DESCRIPTION");//2008043001 PKY S 속성 저장되지 않는문제
						//PKY 08060201 S
						String stereo = current_UMLDataModel.getProperty("ID_STEREOTYPE");
						String scope = current_UMLDataModel.getProperty("ID_SCOPE");
						String precondition = current_UMLDataModel.getProperty("ID_PRECONDITION");
						String postcondition = current_UMLDataModel.getProperty("ID_POSTCONDITION");
						String readonly = current_UMLDataModel.getProperty("ID_READONLY");
						String parametername = current_UMLDataModel.getProperty("ID_PARAMETERNAME");
						String multi = current_UMLDataModel.getProperty("ID_MULTI");
						String singleexecution = current_UMLDataModel.getProperty("ID_SINGLEEXECUTION");
						String requirment = current_UMLDataModel.getProperty("ID_REQ_ID");//PKY 08072401 S Requirement 저장 불러오기 로직 추가
						//PKY 08081801 S 오퍼레이션 어트리뷰트 숨길수 있는 기능
						String attrHidden = current_UMLDataModel.getProperty("ID_ATTR_HIDDEN");
						String operHidden = current_UMLDataModel.getProperty("ID_OPER_HIDDEN");
						
						String type = current_UMLDataModel.getProperty("ID_TYPE");
						String isInstance = current_UMLDataModel.getProperty("IS_INSTANCE");
						String instanceName = current_UMLDataModel.getProperty("INSTANCE_NAME");
						//PKY 08081801 E 오퍼레이션 어트리뷰트 숨길수 있는 기능
						//PKY 08060201 E
						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제

						String idPort = current_UMLDataModel.getProperty("ID_PORT");
						String idip = current_UMLDataModel.getProperty("ID_IP");
						String idnode = current_UMLDataModel.getProperty("ID_NODE");
						String idversion = current_UMLDataModel.getProperty("ID_VERSION");
						
						
						String file_str = current_UMLDataModel.getProperty("file_str");
						String dllFile_str = current_UMLDataModel.getProperty("dllFile_str");
						String dir_str = current_UMLDataModel.getProperty("dir_str");
						String fsmFile_str = current_UMLDataModel.getProperty("fsmFile_str");
						String dllFileName = current_UMLDataModel.getProperty("dllFileName");
						String fsmFileName = current_UMLDataModel.getProperty("fsmFileName");
//						String idversion = current_UMLDataModel.getProperty("ID_VERSION");
						//AtomicComponentModel
						String Implemention_Language = current_UMLDataModel.getProperty("IMPL_LANGUAGE");
						String Execution_Lifecycle_Type = current_UMLDataModel.getProperty("Execution Lifecycle Type");
						String Library_Name = current_UMLDataModel.getProperty("Library Name");
						String SrcFolder = current_UMLDataModel.getProperty("SrcFolder");
						String CmpFolder = current_UMLDataModel.getProperty("CmpFolder");
						String Company_Homepage = current_UMLDataModel.getProperty("Company Homepage");
						String Excution_Priority = current_UMLDataModel.getProperty("Excution Priority");
						
						String Execution_Type = current_UMLDataModel.getProperty("Execution Type");
						String Execution_Environment_Robot_Type = current_UMLDataModel.getProperty("Execution Environment Robot Type");
						String Compiler = current_UMLDataModel.getProperty("COMPILER");
						String Library_Type = current_UMLDataModel.getProperty("LIBRARY_TYPE");
						String Company_Phone = current_UMLDataModel.getProperty("Company Phone");
						String Company_Address = current_UMLDataModel.getProperty("Company Address");
						
						String License_Policy = current_UMLDataModel.getProperty("License Policy");
						String Company_Name = current_UMLDataModel.getProperty("Company Name");
						String core_id = current_UMLDataModel.getProperty("CORE_ID");
//						String Company_Address = current_UMLDataModel.getProperty("Company Address");
						
						
						java.util.ArrayList tag = new java.util.ArrayList();
						Object obj=current_UMLDataModel.getElementProperty("ID_TAG");
						if(obj!=null && obj instanceof java.util.ArrayList){
							tag = (java.util.ArrayList)obj;

						}
						java.util.ArrayList mv = new java.util.ArrayList();
						 obj=current_UMLDataModel.getElementProperty("ID_MV");
						if(obj!=null && obj instanceof java.util.ArrayList){
							mv = (java.util.ArrayList)obj;

						}
						//2008043001 PKY E Tag 바운리더,패키지 저장안되는문제	
						StrcuturedPackageTreeModel  to1 = new StrcuturedPackageTreeModel(name);	//110826 SDM SAVE>LOAD 문제 해결
						
						ComponentModel umlModel = null;
						if("AtomicComponentModel".equals(type)){
							if(!"CORE".equals(core_id)){
								StrcuturedPackageTreeLibModel  temp = new StrcuturedPackageTreeLibModel(name);	//110826 SDM SAVE>LOAD 문제 해결
								to1 = temp;												//110826 SDM SAVE>LOAD 문제 해결
								umlModel = new AtomicComponentModel(current_UMLDataModel, -1);
								umlModel.tmp_core_id = core_id;
							}
							else{
								StrcuturedPackageTreeModel  temp = new StrcuturedPackageTreeModel(name);	//110826 SDM SAVE>LOAD 문제 해결
								to1 = temp;												//110826 SDM SAVE>LOAD 문제 해결
								umlModel = new AtomicComponentModel(current_UMLDataModel, true);
								umlModel.setimplementLangProp(Implemention_Language);
								umlModel.setexeexeLifecycleTypeProp(Execution_Lifecycle_Type);
								umlModel.setlibNameProp(Library_Name);
								umlModel.setSrcFolder(SrcFolder);
								umlModel.setcompHomepageProp(Company_Homepage);
								umlModel.setexePriorityProp(Excution_Priority);
								umlModel.setexeTypeProp(Execution_Type);
								umlModel.setexeEnvRobotTypeProp(Execution_Environment_Robot_Type);
								umlModel.setcompilerProp(Compiler);
								umlModel.setlibTypeProp(Library_Type);
								umlModel.setcompPhomeProp(Company_Phone);
								umlModel.setCompAddressProp(Company_Address);
								umlModel.setlicensePolicyProp(License_Policy);
								umlModel.setcompNameProp(Company_Name);
								umlModel.setCmpFolder(CmpFolder);
								((AtomicComponentModel)umlModel).addOPRoS(CmpFolder,mv);
								if(mv!=null && mv.size()>0){
									
								}
							}
							
							
						
						}
						else{
						umlModel = new ComponentModel(current_UMLDataModel,true);
						if(mv.size()>0)
						umlModel.setMonitorVariables(mv);
//						umlModel.
						}
						
						umlModel.setStrDllFile(dllFile_str);
						umlModel.setStrFsmFile(fsmFile_str);
						umlModel.setStrFile(file_str);
						umlModel.setStrDirFile(dir_str);
						
						umlModel.setDllFileName(dllFileName);
						umlModel.setFsmFileName(fsmFileName);
//						umlModel.setFsmFileName();
//						umlModel.set
//						if(umlModel)
						umlModel.setNodeItemModelId("NodeItemModel_id");
						if(NodeItemModel_id!=null && !"".equals(NodeItemModel_id)){
							if(this.nodeCmpData.get(NodeItemModel_id)==null){
								java.util.ArrayList list = new java.util.ArrayList();
								list.add(umlModel);
								this.nodeCmpData.put(NodeItemModel_id,list);
							}
							else{
								java.util.ArrayList list = (java.util.ArrayList)this.nodeCmpData.get(NodeItemModel_id);
								list.add(umlModel);
								this.nodeCmpData.put(NodeItemModel_id,list);
							}
							
						}
						to1.setRefModel(umlModel);
						umlModel.setName(name);
						umlModel.setTreeModel(to1);
//						if(idPort!=null)
//							umlModel.setIP(idip);
//						if(idnode!=null)
//							umlModel.setNode(idnode);
						if(idversion!=null)
							umlModel.setVersion(idversion);
//						if(idPort!=null)
//							umlModel.setPort(idPort);
						//PKY 08081801 S 오퍼레이션 어트리뷰트 숨길수 있는 기능
						if(attrHidden!=null&&attrHidden.trim().length()>0&&!attrHidden.trim().equals("")&&attrHidden.equals("1"))
							umlModel.setShowAttr(attrHidden);
						if(operHidden!=null&&operHidden.trim().length()>0&&!operHidden.trim().equals("")&&operHidden.equals("1"))
							umlModel.setShowOper(operHidden);
						//PKY 08081801 E 오퍼레이션 어트리뷰트 숨길수 있는 기능
						//PKY 08060201 S
						//PKY 08072401 S Requirement 저장 불러오기 로직 추가

						if(requirment!=null&&requirment.trim().length()>0&&!requirment.trim().equals("")){
							umlModel.setReqID(requirment);
						}
						if(stereo!=null&&stereo.trim().length()>0&&!stereo.trim().equals("")){
							umlModel.setStereotype(stereo);
						}
						if(scope!=null&&scope.trim().length()>0&&!scope.trim().equals("")){
							umlModel.setScope(scope);
						}						
						if(precondition!=null&&precondition.trim().length()>0&&!precondition.trim().equals("")){
							umlModel.setPreCondition(precondition);
						}
						if(postcondition!=null&&postcondition.trim().length()>0&&!postcondition.trim().equals("")){
							umlModel.setPostCondition(postcondition);
						}
						if(readonly!=null&&readonly.trim().length()>0&&!readonly.trim().equals("")){
							umlModel.setReadOnly(readonly);
						}
						if(parametername!=null&&parametername.trim().length()>0&&!parametername.trim().equals("")){
							umlModel.setParameterName(parametername);
						}
						if(multi!=null&&multi.trim().length()>0&&!multi.trim().equals("")){
							umlModel.setMultiplicity(multi);
						}
						if(singleexecution!=null&&singleexecution.trim().length()>0&&!singleexecution.trim().equals("")){
							umlModel.setSingleExecution(singleexecution);
						}
						//PKY 08060201 E
						if(desc!=null)//PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제						
							umlModel.setDesc(desc.replaceAll("/n", "\n"));//2008043001 PKY STag 바운리더,패키지 저장안되는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제	
						//						umlModel.setDesc(desc);//2008043001 PKY S 속성 저장되지 않는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제
						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
						if(tag.size()>0)
							umlModel.setTags(tag);
//						if(mv.size()>0)
//							umlModel.setMonitorVariables(mv);
						//						2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
						Object parentObject = modelStore.get(parentID);

						if(parentObject==null){
							to1.setParent(ProjectManager.getInstance().getModelBrowser().getRootLib());
							ProjectManager.getInstance().getModelBrowser().getRootLib().addChild(to1);
						}
						else{
							to1.setParent((UMLTreeParentModel)parentObject);
							UMLTreeParentModel up= (UMLTreeParentModel)parentObject;
							if (to1 != null) {
								up.addChild(to1);
							}
						}
						
						if("true".equals(isInstance)){
							UMLTreeModel up = (UMLTreeModel)CompAssemManager.getInstance().getModelStore().get(instanceName);
							umlModel.setInstance(true);
							if(up!=null){
								umlModel.setInstanceUMLTreeModel(up);
							}
							else{
								if(instanceName!=null)
								umlModel.setInstanceName(instanceName);
//								umlModel.
							}
//							umlModel.
							
							
						}
						
						
						
						
						modelStore.put(id, to1);
						ProjectManager.getInstance().addTypeModel(to1);//PKY 08052901 S 저장 불러오기할경우 참조타입정보가 없다 참조타입정보를 넣어 줄수 있도록 수정
					}
				}
				else if(current_model_type==30){
					if(this.isBoundary){

					}
					else{
						String id = current_UMLDataModel.getProperty("ID");
						String name = current_UMLDataModel.getProperty("ID_NAME");
						String parentID = current_UMLDataModel.getProperty("parentID");
						String desc = current_UMLDataModel.getProperty("ID_DESCRIPTION");//2008043001 PKY S 속성 저장되지 않는문제
						//PKY 08060201 S
						String stereo = current_UMLDataModel.getProperty("ID_STEREOTYPE");
						String scope = current_UMLDataModel.getProperty("ID_SCOPE");
						String precondition = current_UMLDataModel.getProperty("ID_PRECONDITION");
						String postcondition = current_UMLDataModel.getProperty("ID_POSTCONDITION");
						String readonly = current_UMLDataModel.getProperty("ID_READONLY");
						String parametername = current_UMLDataModel.getProperty("ID_PARAMETERNAME");
						String multi = current_UMLDataModel.getProperty("ID_MULTI");
						String singleexecution = current_UMLDataModel.getProperty("ID_SINGLEEXECUTION");
						String requirment = current_UMLDataModel.getProperty("ID_REQ_ID");//PKY 08072401 S Requirement 저장 불러오기 로직 추가
						//PKY 08081801 S 오퍼레이션 어트리뷰트 숨길수 있는 기능
						String attrHidden = current_UMLDataModel.getProperty("ID_ATTR_HIDDEN");
						String operHidden = current_UMLDataModel.getProperty("ID_OPER_HIDDEN");
						//PKY 08081801 E 오퍼레이션 어트리뷰트 숨길수 있는 기능
						//PKY 08060201 E
						String fileName = current_UMLDataModel.getProperty("ID_FILENAME");//PKY 08090904 S 

						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
						java.util.ArrayList tag = new java.util.ArrayList();
						Object obj=current_UMLDataModel.getElementProperty("ID_TAG");
						if(obj!=null && obj instanceof java.util.ArrayList){
							tag = (java.util.ArrayList)obj;

						}
						//2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
						UMLTreeModel  to1 = new UMLTreeModel(name);
						UMLModel umlModel = null;
						umlModel = new ArtfifactModel(current_UMLDataModel,true);
						to1.setRefModel(umlModel);
						umlModel.setName(name);
						umlModel.setTreeModel(to1);
						//PKY 08060201 S
						//PKY 08072401 S Requirement 저장 불러오기 로직 추가
						//PKY 08081801 S 오퍼레이션 어트리뷰트 숨길수 있는 기능
						if(attrHidden!=null&&attrHidden.trim().length()>0&&!attrHidden.trim().equals("")&&attrHidden.equals("1"))
							umlModel.setShowAttr(attrHidden);
						if(operHidden!=null&&operHidden.trim().length()>0&&!operHidden.trim().equals("")&&operHidden.equals("1"))
							umlModel.setShowOper(operHidden);
						//PKY 08081801 E 오퍼레이션 어트리뷰트 숨길수 있는 기능
						//PKY 08090904 S 
						if(fileName!=null&&fileName.trim().length()>0&&!fileName.trim().equals("")){
							umlModel.setFileName(fileName);
						}
						//PKY 08090904 E 

						if(requirment!=null&&requirment.trim().length()>0&&!requirment.trim().equals("")){
							umlModel.setReqID(requirment);
						}
						if(stereo!=null&&stereo.trim().length()>0&&!stereo.trim().equals("")){
							umlModel.setStereotype(stereo);
						}
						if(scope!=null&&scope.trim().length()>0&&!scope.trim().equals("")){
							umlModel.setScope(scope);
						}						
						if(precondition!=null&&precondition.trim().length()>0&&!precondition.trim().equals("")){
							umlModel.setPreCondition(precondition);
						}
						if(postcondition!=null&&postcondition.trim().length()>0&&!postcondition.trim().equals("")){
							umlModel.setPostCondition(postcondition);
						}
						if(readonly!=null&&readonly.trim().length()>0&&!readonly.trim().equals("")){
							umlModel.setReadOnly(readonly);
						}
						if(parametername!=null&&parametername.trim().length()>0&&!parametername.trim().equals("")){
							umlModel.setParameterName(parametername);
						}
						if(multi!=null&&multi.trim().length()>0&&!multi.trim().equals("")){
							umlModel.setMultiplicity(multi);
						}
						if(singleexecution!=null&&singleexecution.trim().length()>0&&!singleexecution.trim().equals("")){
							umlModel.setSingleExecution(singleexecution);
						}
						//PKY 08060201 E
						if(desc!=null)//PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제						
							umlModel.setDesc(desc.replaceAll("/n", "\n"));//2008043001 PKY STag 바운리더,패키지 저장안되는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제	
						//						umlModel.setDesc(desc);//2008043001 PKY S 속성 저장되지 않는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제
						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
						if(tag.size()>0)
							umlModel.setTags(tag);
						//						2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
						Object parentObject = modelStore.get(parentID);
						if(parentObject==null){
							to1.setParent(root);
							root.addChild(to1);
						}
						else{
							to1.setParent((UMLTreeParentModel)parentObject);
							UMLTreeParentModel up= (UMLTreeParentModel)parentObject;
							if (to1 != null) {
								up.addChild(to1);
							}
						}
						modelStore.put(id, to1);
						ProjectManager.getInstance().addTypeModel(to1);//PKY 08052901 S 저장 불러오기할경우 참조타입정보가 없다 참조타입정보를 넣어 줄수 있도록 수정
					}
				}
				else if(current_model_type==31){
					if(this.isBoundary){

					}
					else{

					}
				}
				else if(current_model_type==32){
					if(this.isBoundary){

					}
					else{

					}
				}
				else if(current_model_type==33){
					if(this.isBoundary){

					}
					else{

					}
				}
				else if(current_model_type==34){
					if(this.isBoundary){

					}
					else{

					}
				}
				else if(current_model_type==36){
					if(this.isBoundary){

					}
					else{

					}
				}
				//				else if(current_model_type==35){
				//					if(this.isBoundary){
				//
				//					}
				//					else{
				//						String id = current_UMLDataModel.getProperty("ID");
				//						String name = current_UMLDataModel.getProperty("ID_NAME");
				//						String parentID = current_UMLDataModel.getProperty("parentID");
				//						String desc = current_UMLDataModel.getProperty("ID_DESCRIPTION");//2008043001 PKY S 속성 저장되지 않는문제
				//						//PKY 08060201 S
				//						String stereo = current_UMLDataModel.getProperty("ID_STEREOTYPE");
				//						String scope = current_UMLDataModel.getProperty("ID_SCOPE");
				//						String precondition = current_UMLDataModel.getProperty("ID_PRECONDITION");
				//						String postcondition = current_UMLDataModel.getProperty("ID_POSTCONDITION");
				//						String readonly = current_UMLDataModel.getProperty("ID_READONLY");
				//						String parametername = current_UMLDataModel.getProperty("ID_PARAMETERNAME");
				//						String multi = current_UMLDataModel.getProperty("ID_MULTI");
				//						String singleexecution = current_UMLDataModel.getProperty("ID_SINGLEEXECUTION");
				//						String requirment = current_UMLDataModel.getProperty("ID_REQ_ID");//PKY 08072401 S Requirement 저장 불러오기 로직 추가
				//						//PKY 08081801 S 오퍼레이션 어트리뷰트 숨길수 있는 기능
				//						String attrHidden = current_UMLDataModel.getProperty("ID_ATTR_HIDDEN");
				//						String operHidden = current_UMLDataModel.getProperty("ID_OPER_HIDDEN");
				//						//PKY 08081801 E 오퍼레이션 어트리뷰트 숨길수 있는 기능
				//						//PKY 08060201 E
				//						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
				//						java.util.ArrayList tag = new java.util.ArrayList();
				//						Object obj=current_UMLDataModel.getElementProperty("ID_TAG");
				//						if(obj!=null && obj instanceof java.util.ArrayList){
				//							tag = (java.util.ArrayList)obj;
				//
				//						}
				//						//2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
				//						StrcuturedPackageTreeModel  to1 = new StrcuturedPackageTreeModel(name);
				//						UMLModel umlModel = null;
				//						umlModel = new NodeModel(current_UMLDataModel,true);
				//						to1.setRefModel(umlModel);
				//						umlModel.setName(name);
				//						umlModel.setTreeModel(to1);
				//						//PKY 08060201 S
				//						//PKY 08072401 S Requirement 저장 불러오기 로직 추가
				//						//PKY 08081801 S 오퍼레이션 어트리뷰트 숨길수 있는 기능
				//						if(attrHidden!=null&&attrHidden.trim().length()>0&&!attrHidden.trim().equals("")&&attrHidden.equals("1"))
				//							umlModel.setShowAttr(attrHidden);
				//						if(operHidden!=null&&operHidden.trim().length()>0&&!operHidden.trim().equals("")&&operHidden.equals("1"))
				//							umlModel.setShowOper(operHidden);
				//						//PKY 08081801 E 오퍼레이션 어트리뷰트 숨길수 있는 기능
				//						if(requirment!=null&&requirment.trim().length()>0&&!requirment.trim().equals("")){
				//							umlModel.setReqID(requirment);
				//						}
				//						if(stereo!=null&&stereo.trim().length()>0&&!stereo.trim().equals("")){
				//							umlModel.setStereotype(stereo);
				//						}
				//						if(scope!=null&&scope.trim().length()>0&&!scope.trim().equals("")){
				//							umlModel.setScope(scope);
				//						}						
				//						if(precondition!=null&&precondition.trim().length()>0&&!precondition.trim().equals("")){
				//							umlModel.setPreCondition(precondition);
				//						}
				//						if(postcondition!=null&&postcondition.trim().length()>0&&!postcondition.trim().equals("")){
				//							umlModel.setPostCondition(postcondition);
				//						}
				//						if(readonly!=null&&readonly.trim().length()>0&&!readonly.trim().equals("")){
				//							umlModel.setReadOnly(readonly);
				//						}
				//						if(parametername!=null&&parametername.trim().length()>0&&!parametername.trim().equals("")){
				//							umlModel.setParameterName(parametername);
				//						}
				//						if(multi!=null&&multi.trim().length()>0&&!multi.trim().equals("")){
				//							umlModel.setMultiplicity(multi);
				//						}
				//						if(singleexecution!=null&&singleexecution.trim().length()>0&&!singleexecution.trim().equals("")){
				//							umlModel.setSingleExecution(singleexecution);
				//						}
				//						//PKY 08060201 E
				//						if(desc!=null)//PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제						
				//							umlModel.setDesc(desc.replaceAll("/n", "\n"));//2008043001 PKY STag 바운리더,패키지 저장안되는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제	
				////						umlModel.setDesc(desc);//2008043001 PKY S 속성 저장되지 않는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제
				//						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
				//						if(tag.size()>0)
				//							umlModel.setTags(tag);
				////						2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
				//						Object parentObject = modelStore.get(parentID);
				//						if(parentObject==null){
				//							to1.setParent(root);
				//							root.addChild(to1);
				//						}
				//						else{
				//							to1.setParent((UMLTreeParentModel)parentObject);
				//							UMLTreeParentModel up= (UMLTreeParentModel)parentObject;
				//							if (to1 != null) {
				//								up.addChild(to1);
				//							}
				//						}
				//						modelStore.put(id, to1);
				//						ProjectManager.getInstance().addTypeModel(to1);//PKY 08052901 S 저장 불러오기할경우 참조타입정보가 없다 참조타입정보를 넣어 줄수 있도록 수정
				//					}
				//				}
				//				else if(current_model_type==37){
				//					if(this.isBoundary){
				//
				//					}
				//					else{
				//						String id = current_UMLDataModel.getProperty("ID");
				//						String name = current_UMLDataModel.getProperty("ID_NAME");
				//						String parentID = current_UMLDataModel.getProperty("parentID");
				//						String desc = current_UMLDataModel.getProperty("ID_DESCRIPTION");//2008043001 PKY S 속성 저장되지 않는문제
				//						//PKY 08060201 S
				//						String stereo = current_UMLDataModel.getProperty("ID_STEREOTYPE");
				//						String scope = current_UMLDataModel.getProperty("ID_SCOPE");
				//						String precondition = current_UMLDataModel.getProperty("ID_PRECONDITION");
				//						String postcondition = current_UMLDataModel.getProperty("ID_POSTCONDITION");
				//						String readonly = current_UMLDataModel.getProperty("ID_READONLY");
				//						String parametername = current_UMLDataModel.getProperty("ID_PARAMETERNAME");
				//						String multi = current_UMLDataModel.getProperty("ID_MULTI");
				//						String singleexecution = current_UMLDataModel.getProperty("ID_SINGLEEXECUTION");
				//						String requirment = current_UMLDataModel.getProperty("ID_REQ_ID");//PKY 08072401 S Requirement 저장 불러오기 로직 추가
				//						//PKY 08081801 S 오퍼레이션 어트리뷰트 숨길수 있는 기능
				//						String attrHidden = current_UMLDataModel.getProperty("ID_ATTR_HIDDEN");
				//						String operHidden = current_UMLDataModel.getProperty("ID_OPER_HIDDEN");
				//						//PKY 08081801 E 오퍼레이션 어트리뷰트 숨길수 있는 기능
				//						//PKY 08060201 E
				//						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
				//						java.util.ArrayList tag = new java.util.ArrayList();
				//						Object obj=current_UMLDataModel.getElementProperty("ID_TAG");
				//						if(obj!=null && obj instanceof java.util.ArrayList){
				//							tag = (java.util.ArrayList)obj;
				//
				//						}
				//						//2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
				//						UMLTreeModel  to1 = new UMLTreeModel(name);
				//						UMLModel umlModel = null;
				//						umlModel = new DeviceModel(current_UMLDataModel,true);
				//						to1.setRefModel(umlModel);
				//						umlModel.setName(name);
				//						umlModel.setTreeModel(to1);
				//
				//						//PKY 08081801 S 오퍼레이션 어트리뷰트 숨길수 있는 기능
				//						if(attrHidden!=null&&attrHidden.trim().length()>0&&!attrHidden.trim().equals("")&&attrHidden.equals("1"))
				//							umlModel.setShowAttr(attrHidden);
				//						if(operHidden!=null&&operHidden.trim().length()>0&&!operHidden.trim().equals("")&&operHidden.equals("1"))
				//							umlModel.setShowOper(operHidden);
				//						//PKY 08081801 E 오퍼레이션 어트리뷰트 숨길수 있는 기능
				//						//PKY 08060201 S
				//						//PKY 08072401 S Requirement 저장 불러오기 로직 추가
				//						if(requirment!=null&&requirment.trim().length()>0&&!requirment.trim().equals("")){
				//							umlModel.setReqID(requirment);
				//						}
				//						if(stereo!=null&&stereo.trim().length()>0&&!stereo.trim().equals("")){
				//							umlModel.setStereotype(stereo);
				//						}
				//						if(scope!=null&&scope.trim().length()>0&&!scope.trim().equals("")){
				//							umlModel.setScope(scope);
				//						}						
				//						if(precondition!=null&&precondition.trim().length()>0&&!precondition.trim().equals("")){
				//							umlModel.setPreCondition(precondition);
				//						}
				//						if(postcondition!=null&&postcondition.trim().length()>0&&!postcondition.trim().equals("")){
				//							umlModel.setPostCondition(postcondition);
				//						}
				//						if(readonly!=null&&readonly.trim().length()>0&&!readonly.trim().equals("")){
				//							umlModel.setReadOnly(readonly);
				//						}
				//						if(parametername!=null&&parametername.trim().length()>0&&!parametername.trim().equals("")){
				//							umlModel.setParameterName(parametername);
				//						}
				//						if(multi!=null&&multi.trim().length()>0&&!multi.trim().equals("")){
				//							umlModel.setMultiplicity(multi);
				//						}
				//						if(singleexecution!=null&&singleexecution.trim().length()>0&&!singleexecution.trim().equals("")){
				//							umlModel.setSingleExecution(singleexecution);
				//						}
				//						//PKY 08060201 E
				//						if(desc!=null)//PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제						
				//							umlModel.setDesc(desc.replaceAll("/n", "\n"));//2008043001 PKY STag 바운리더,패키지 저장안되는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제	
				////						umlModel.setDesc(desc);//2008043001 PKY S 속성 저장되지 않는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제
				//						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
				//						if(tag.size()>0)
				//							umlModel.setTags(tag);
				////						2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
				//						Object parentObject = modelStore.get(parentID);
				//						if(parentObject==null){
				//							to1.setParent(root);
				//							root.addChild(to1);
				//						}
				//						else{
				//							to1.setParent((UMLTreeParentModel)parentObject);
				//							UMLTreeParentModel up= (UMLTreeParentModel)parentObject;
				//							if (to1 != null) {
				//								up.addChild(to1);
				//							}
				//						}
				//						modelStore.put(id, to1);
				//						ProjectManager.getInstance().addTypeModel(to1);//PKY 08052901 S 저장 불러오기할경우 참조타입정보가 없다 참조타입정보를 넣어 줄수 있도록 수정
				//					}
				//				}
				//				else if(current_model_type==38){
				//					if(this.isBoundary){
				//
				//					}
				//					else{
				//						String id = current_UMLDataModel.getProperty("ID");
				//						String name = current_UMLDataModel.getProperty("ID_NAME");
				//						String parentID = current_UMLDataModel.getProperty("parentID");
				//						String desc = current_UMLDataModel.getProperty("ID_DESCRIPTION");//2008043001 PKY S 속성 저장되지 않는문제
				//						//PKY 08060201 S
				//						String stereo = current_UMLDataModel.getProperty("ID_STEREOTYPE");
				//						String scope = current_UMLDataModel.getProperty("ID_SCOPE");
				//						String precondition = current_UMLDataModel.getProperty("ID_PRECONDITION");
				//						String postcondition = current_UMLDataModel.getProperty("ID_POSTCONDITION");
				//						String readonly = current_UMLDataModel.getProperty("ID_READONLY");
				//						String parametername = current_UMLDataModel.getProperty("ID_PARAMETERNAME");
				//						String multi = current_UMLDataModel.getProperty("ID_MULTI");
				//						String singleexecution = current_UMLDataModel.getProperty("ID_SINGLEEXECUTION");
				//						String requirment = current_UMLDataModel.getProperty("ID_REQ_ID");//PKY 08072401 S Requirement 저장 불러오기 로직 추가
				//						//PKY 08081801 S 오퍼레이션 어트리뷰트 숨길수 있는 기능
				//						String attrHidden = current_UMLDataModel.getProperty("ID_ATTR_HIDDEN");
				//						String operHidden = current_UMLDataModel.getProperty("ID_OPER_HIDDEN");
				//						//PKY 08081801 E 오퍼레이션 어트리뷰트 숨길수 있는 기능
				//						//PKY 08060201 E
				//
				//						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
				//						java.util.ArrayList tag = new java.util.ArrayList();
				//						Object obj=current_UMLDataModel.getElementProperty("ID_TAG");
				//						if(obj!=null && obj instanceof java.util.ArrayList){
				//							tag = (java.util.ArrayList)obj;
				//
				//						}
				//						//2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
				//						UMLTreeModel  to1 = new UMLTreeModel(name);
				//						UMLModel umlModel = null;
				//						umlModel = new ExecutionEnvironmentModel(current_UMLDataModel,true);
				//						to1.setRefModel(umlModel);
				//						umlModel.setName(name);
				//						umlModel.setTreeModel(to1);
				//
				//						//PKY 08081801 S 오퍼레이션 어트리뷰트 숨길수 있는 기능
				//						if(attrHidden!=null&&attrHidden.trim().length()>0&&!attrHidden.trim().equals("")&&attrHidden.equals("1"))
				//							umlModel.setShowAttr(attrHidden);
				//						if(operHidden!=null&&operHidden.trim().length()>0&&!operHidden.trim().equals("")&&operHidden.equals("1"))
				//							umlModel.setShowOper(operHidden);
				//						//PKY 08081801 E 오퍼레이션 어트리뷰트 숨길수 있는 기능
				//						//PKY 08060201 S
				//						//PKY 08072401 S Requirement 저장 불러오기 로직 추가
				//						if(requirment!=null&&requirment.trim().length()>0&&!requirment.trim().equals("")){
				//							umlModel.setReqID(requirment);
				//						}
				//						if(stereo!=null&&stereo.trim().length()>0&&!stereo.trim().equals("")){
				//							umlModel.setStereotype(stereo);
				//						}
				//						if(scope!=null&&scope.trim().length()>0&&!scope.trim().equals("")){
				//							umlModel.setScope(scope);
				//						}						
				//						if(precondition!=null&&precondition.trim().length()>0&&!precondition.trim().equals("")){
				//							umlModel.setPreCondition(precondition);
				//						}
				//						if(postcondition!=null&&postcondition.trim().length()>0&&!postcondition.trim().equals("")){
				//							umlModel.setPostCondition(postcondition);
				//						}
				//						if(readonly!=null&&readonly.trim().length()>0&&!readonly.trim().equals("")){
				//							umlModel.setReadOnly(readonly);
				//						}
				//						if(parametername!=null&&parametername.trim().length()>0&&!parametername.trim().equals("")){
				//							umlModel.setParameterName(parametername);
				//						}
				//						if(multi!=null&&multi.trim().length()>0&&!multi.trim().equals("")){
				//							umlModel.setMultiplicity(multi);
				//						}
				//						if(singleexecution!=null&&singleexecution.trim().length()>0&&!singleexecution.trim().equals("")){
				//							umlModel.setSingleExecution(singleexecution);
				//						}
				//						//PKY 08060201 E
				//						if(desc!=null)//PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제						
				//							umlModel.setDesc(desc.replaceAll("/n", "\n"));//2008043001 PKY STag 바운리더,패키지 저장안되는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제	
				////						umlModel.setDesc(desc);//2008043001 PKY S 속성 저장되지 않는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제
				//						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
				//						if(tag.size()>0)
				//							umlModel.setTags(tag);
				////						2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
				//						Object parentObject = modelStore.get(parentID);
				//						if(parentObject==null){
				//							to1.setParent(root);
				//							root.addChild(to1);
				//						}
				//						else{
				//							to1.setParent((UMLTreeParentModel)parentObject);
				//							UMLTreeParentModel up= (UMLTreeParentModel)parentObject;
				//							if (to1 != null) {
				//								up.addChild(to1);
				//							}
				//						}
				//						modelStore.put(id, to1);
				//						ProjectManager.getInstance().addTypeModel(to1);//PKY 08052901 S 저장 불러오기할경우 참조타입정보가 없다 참조타입정보를 넣어 줄수 있도록 수정
				//					}
				//				}
				//				else if(current_model_type==39){
				//					if(this.isBoundary){
				//
				//					}
				//					else{
				//						String id = current_UMLDataModel.getProperty("ID");
				//						String name = current_UMLDataModel.getProperty("ID_NAME");
				//						String parentID = current_UMLDataModel.getProperty("parentID");
				//						String desc = current_UMLDataModel.getProperty("ID_DESCRIPTION");//2008043001 PKY S 속성 저장되지 않는문제
				//						//PKY 08060201 S
				//						String stereo = current_UMLDataModel.getProperty("ID_STEREOTYPE");
				//						String scope = current_UMLDataModel.getProperty("ID_SCOPE");
				//						String precondition = current_UMLDataModel.getProperty("ID_PRECONDITION");
				//						String postcondition = current_UMLDataModel.getProperty("ID_POSTCONDITION");
				//						String readonly = current_UMLDataModel.getProperty("ID_READONLY");
				//						String parametername = current_UMLDataModel.getProperty("ID_PARAMETERNAME");
				//						String multi = current_UMLDataModel.getProperty("ID_MULTI");
				//						String singleexecution = current_UMLDataModel.getProperty("ID_SINGLEEXECUTION");
				//						String requirment = current_UMLDataModel.getProperty("ID_REQ_ID");//PKY 08072401 S Requirement 저장 불러오기 로직 추가
				//						//PKY 08081801 S 오퍼레이션 어트리뷰트 숨길수 있는 기능
				//						String attrHidden = current_UMLDataModel.getProperty("ID_ATTR_HIDDEN");
				//						String operHidden = current_UMLDataModel.getProperty("ID_OPER_HIDDEN");
				//						//PKY 08081801 E 오퍼레이션 어트리뷰트 숨길수 있는 기능
				//						//PKY 08060201 E
				//
				//						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
				//						java.util.ArrayList tag = new java.util.ArrayList();
				//						Object obj=current_UMLDataModel.getElementProperty("ID_TAG");
				//						if(obj!=null && obj instanceof java.util.ArrayList){
				//							tag = (java.util.ArrayList)obj;
				//
				//						}
				//						//2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
				//						UMLTreeModel  to1 = new UMLTreeModel(name);
				//						UMLModel umlModel = null;
				//						umlModel = new DeploymentSpecificationModel(current_UMLDataModel,true);
				//						to1.setRefModel(umlModel);
				//						umlModel.setName(name);
				//						umlModel.setTreeModel(to1);
				//
				//						//PKY 08060201 S
				//						//PKY 08072401 S Requirement 저장 불러오기 로직 추가
				//						//PKY 08081801 S 오퍼레이션 어트리뷰트 숨길수 있는 기능
				//						if(attrHidden!=null&&attrHidden.trim().length()>0&&!attrHidden.trim().equals("")&&attrHidden.equals("1"))
				//							umlModel.setShowAttr(attrHidden);
				//						if(operHidden!=null&&operHidden.trim().length()>0&&!operHidden.trim().equals("")&&operHidden.equals("1"))
				//							umlModel.setShowOper(operHidden);
				//						//PKY 08081801 E 오퍼레이션 어트리뷰트 숨길수 있는 기능
				//						if(requirment!=null&&requirment.trim().length()>0&&!requirment.trim().equals("")){
				//							umlModel.setReqID(requirment);
				//						}
				//						if(stereo!=null&&stereo.trim().length()>0&&!stereo.trim().equals("")){
				//							umlModel.setStereotype(stereo);
				//						}
				//						if(scope!=null&&scope.trim().length()>0&&!scope.trim().equals("")){
				//							umlModel.setScope(scope);
				//						}						
				//						if(precondition!=null&&precondition.trim().length()>0&&!precondition.trim().equals("")){
				//							umlModel.setPreCondition(precondition);
				//						}
				//						if(postcondition!=null&&postcondition.trim().length()>0&&!postcondition.trim().equals("")){
				//							umlModel.setPostCondition(postcondition);
				//						}
				//						if(readonly!=null&&readonly.trim().length()>0&&!readonly.trim().equals("")){
				//							umlModel.setReadOnly(readonly);
				//						}
				//						if(parametername!=null&&parametername.trim().length()>0&&!parametername.trim().equals("")){
				//							umlModel.setParameterName(parametername);
				//						}
				//						if(multi!=null&&multi.trim().length()>0&&!multi.trim().equals("")){
				//							umlModel.setMultiplicity(multi);
				//						}
				//						if(singleexecution!=null&&singleexecution.trim().length()>0&&!singleexecution.trim().equals("")){
				//							umlModel.setSingleExecution(singleexecution);
				//						}
				//						//PKY 08060201 E
				//						if(desc!=null)//PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제						
				//							umlModel.setDesc(desc.replaceAll("/n", "\n"));//2008043001 PKY STag 바운리더,패키지 저장안되는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제	
				////						umlModel.setDesc(desc);//2008043001 PKY S 속성 저장되지 않는문제 //PKY 08051401 S 디스크립션 단략이 적용되서 나오지 않는 문제
				//						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
				//						if(tag.size()>0)
				//							umlModel.setTags(tag);
				////						2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
				//						Object parentObject = modelStore.get(parentID);
				//						if(parentObject==null){
				//							to1.setParent(root);
				//							root.addChild(to1);
				//						}
				//						else{
				//							to1.setParent((UMLTreeParentModel)parentObject);
				//							UMLTreeParentModel up= (UMLTreeParentModel)parentObject;
				//							if (to1 != null) {
				//								up.addChild(to1);
				//							}
				//						}
				//						modelStore.put(id, to1);
				//						ProjectManager.getInstance().addTypeModel(to1);//PKY 08052901 S 저장 불러오기할경우 참조타입정보가 없다 참조타입정보를 넣어 줄수 있도록 수정
				//					}
				//				}
				//2008043001 PKY S Timing 객체 저장 로직 추가
				
				else  if(current_model_type==41){
					//					if(this.isBoundary){

					//					String view_id = current_UMLDataModel.getProperty("View_ID");
					//					String id = current_UMLDataModel.getProperty("ID");
					//					String name = current_UMLDataModel.getProperty("ID_NAME");
					//					String parentID = current_UMLDataModel.getProperty("parentID");
					//					String size = current_UMLDataModel.getProperty("ID_SIZE");
					//					String location = current_UMLDataModel.getProperty("ID_LOCATION");
					//					if(this.boundaryModel!=null){
					////					UMLTreeModel up = (UMLTreeModel)this.modelStore.get(parentID);
					////					UMLModel um = (UMLModel)up.getRefModel();
					////					if(um!=null){
					//					UMLNoteModel child = new UMLNoteModel();
					//					child.setSize(this.getSize(size));
					//					child.setLocation(this.getLocation(location));
					//					boundaryModel.addChild(child);
					////					this.viewStore.put(view_id, child);
					////					this.inModelViewListStore.put(parentID, child);
					////					this.inModelViewStore.put(view_id, child);
					////					}
					//					}
					//					}
					////					else{
					////					String id = current_UMLDataModel.getProperty("ID");
					////					String name = current_UMLDataModel.getProperty("ID_NAME");
					////					String parentID = current_UMLDataModel.getProperty("parentID");
					////					String desc = current_UMLDataModel.getProperty("ID_DESCRIPTION");//2008043001 PKY S 속성 저장되지 않는문제
					////					java.util.ArrayList state =(java.util.ArrayList) current_UMLDataModel.getElementProperty("isState");//2008043001 PKY S 속성 저장되지 않는문제
					////					java.util.ArrayList isTransition =(java.util.ArrayList) current_UMLDataModel.getElementProperty("isTransition");//2008043001 PKY S 속성 저장되지 않는문제
					//////				stateLifelineModel.
					////					//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
					////					java.util.ArrayList tag = new java.util.ArrayList();
					////					Object obj=current_UMLDataModel.getElementProperty("ID_TAG");
					////					if(obj!=null && obj instanceof java.util.ArrayList){
					////					tag = (java.util.ArrayList)obj;

					////					}
					////					//2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
					////					UMLTreeModel  to1 = new UMLTreeModel(name);
					////					UMLModel umlModel = null;
					////					umlModel = new StateLifelineModel();
					////					to1.setRefModel(umlModel);
					////					umlModel.setName(name);
					////					umlModel.setTreeModel(to1);
					////					umlModel.setDesc(desc);//2008043001 PKY S 속성 저장되지 않는문제
					////					java.util.ArrayList isstate = new java.util.ArrayList();
					////					java.util.ArrayList isTransitions = new java.util.ArrayList();
					////					for(int i=0;i<state.size();i++){
					////					String[] data = ((String) state.get(i)).split(",");
					////					StateEditableTableItem stateLifelineModel =new StateEditableTableItem(data[0]);
					////					stateLifelineModel.setX(Integer.parseInt(data[1]));
					////					stateLifelineModel.setY(Integer.parseInt(data[2]));
					////					stateLifelineModel.setTenValue(Integer.parseInt(data[3]));
					////					stateLifelineModel.setZero(Integer.parseInt(data[4]));
					////					stateLifelineModel.setStateValue(Integer.parseInt(data[5]));
					////					isstate.add(stateLifelineModel);
					////					}
					////					for(int j=0;j<isTransition.size();j++){
					////					String[] data = ((String) isTransition.get(j)).split(",");
					////					TransitionPointEditableTableItem stateLifelineModel =new TransitionPointEditableTableItem(data[3],data[2],data[1],data[0],data[4],Integer.parseInt(data[5]));
					////					isTransitions.add(stateLifelineModel);
					////					}
					//////				((StateLifelineModel)umlModel).uMLDataModel.setElementProperty("isState", isstate);
					////					((StateLifelineModel)umlModel).setConfigureTimeline(isstate,isTransitions);
					//////				umlModel.uMLDataModel.setElementProperty("isState", value)


					////					//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
					////					if(tag.size()>0)
					////					umlModel.setTags(tag);
					//////				2008043001 PKY E Tag 바운리더,패키지 저장안되는문제
					////					Object parentObject = modelStore.get(parentID);
					////					if(parentObject==null){
					////					to1.setParent(root);
					////					root.addChild(to1);
					////					}
					////					else{
					////					to1.setParent((UMLTreeParentModel)parentObject);
					////					UMLTreeParentModel up= (UMLTreeParentModel)parentObject;
					////					if (to1 != null) {
					////					up.addChild(to1);

					////					}
					////					}
					////					modelStore.put(id, to1);
					////					}

					//					//2008043001 PKY E Timing 객체 저장 로직 추가
					//					current_model_type = -1;
					//PKY 08050701 S Initial 생성 후 저장 불러오기 할경우 노트로 변경되어있는문제
					String view_id = current_UMLDataModel.getProperty("View_ID");
					String id = current_UMLDataModel.getProperty("ID");
					String parentID = current_UMLDataModel.getProperty("parentID");
					String size = current_UMLDataModel.getProperty("ID_SIZE");
					String location = current_UMLDataModel.getProperty("ID_LOCATION");
					String ID_COLOR = current_UMLDataModel.getProperty("ID_COLOR");
					String Text = current_UMLDataModel.getProperty("Text");


					Object obj = this.viewStore.get(parentID);
					String[] dataColor=ID_COLOR.split(",");//PKY 08050701 S 색상 저장
					if(this.isBoundary){

					}
					String[] dataSize =  size.split(",");
					String[] dataLocation =  location.split(",");
					UMLNoteModel UMLNoteModel=new UMLNoteModel();
					UMLNoteModel.setView_ID(view_id);
					UMLNoteModel.setID(id);
					UMLNoteModel.setLabelContents(Text);

					//PKY 08060201 S 컬러관련 재 생성할경우 적용안되는문제
					if(dataColor.length>0){
						UMLNoteModel.setBackGroundColor(new Color(null,Integer.parseInt(dataColor[0]),Integer.parseInt(dataColor[1]),Integer.parseInt(dataColor[2])));//PKY 08050701 S 색상 저장
						UMLNoteModel.getUMLDataModel().setElementProperty("ID_COLOR", UMLNoteModel.getBackGroundColor());
					}
					//PKY 08060201 E 컬러관련 재 생성할경우 적용안되는문제
					UMLNoteModel.setSize(new Dimension(Integer.parseInt(dataSize[0]),Integer.parseInt(dataSize[1])));
					UMLNoteModel.setLocation(new Point(Integer.parseInt(dataLocation[0]),Integer.parseInt(dataLocation[1])));
					if(obj instanceof N3EditorDiagramModel){
						N3EditorDiagramModel n3d = (N3EditorDiagramModel)obj;
						n3d.addChild(UMLNoteModel);
					}
					this.viewStore.put(view_id, UMLNoteModel);



				}
				
				else if(current_model_type==900){
					if(this.isBoundary){

					}
					else{
						String id = current_UMLDataModel.getProperty("ID");
						String name = current_UMLDataModel.getProperty("ID_NAME");

						String ip = current_UMLDataModel.getProperty("ID_IP");//2008043001 PKY S 속성 저장되지 않는문제
						//2008043001 PKY S Tag 바운리더,패키지 저장안되는문제
						//PKY 08060201 S
						String port = current_UMLDataModel.getProperty("ID_PORT");
						String node = current_UMLDataModel.getProperty("ID_NODE");
						String parentID = current_UMLDataModel.getProperty("parentID");
						String color = current_UMLDataModel.getProperty("ID_COLOR");
						String[] dataColor =  {"255","247","205"};
						if(color!=null){
						   dataColor=color.split(",");
						}//PKY 08050701 S 색상 저장
						
						
						NodeItemTreeModel to1 = new NodeItemTreeModel(node);
						NodeItemModel nodeItemModel = new NodeItemModel();
						nodeItemModel.setBackGroundColor(new Color(null,Integer.parseInt(dataColor[0]),Integer.parseInt(dataColor[1]),Integer.parseInt(dataColor[2])));//PKY 08050701 S 색상 저장
						nodeItemModel.setNode(node);
						nodeItemModel.setIP(ip);
						nodeItemModel.setPort(port);
						to1.setRefModel(nodeItemModel);
						nodeItemModel.setTreeModel(to1);
						
						//PKY 08060201 E
						Object parentObject = modelStore.get(parentID);
						if(parentObject==null){
							to1.setParent(root);
							root.addChild(to1);
						}
						else{
							to1.setParent((UMLTreeParentModel)parentObject);
							UMLTreeParentModel up= (UMLTreeParentModel)parentObject;
							if (to1 != null) {
								up.addChild(to1);
							}
						}
						java.util.ArrayList list = (java.util.ArrayList)this.nodeCmpData.get(id);
						if(list!=null){
							for(int i=0;i<list.size();i++){
								ComponentModel cm = (ComponentModel)list.get(i);
								cm.setNodeItemModel(nodeItemModel);
							}
						}
						
						
						modelStore.put(id, to1);
						
				}
				}
				//20080721IJS 
				current_model_type = -1;
			}



		}

	}

	public void eCcreateModel(String token){

		this.current_model_type = ProjectManager.getInstance().getModelTypeName(token);
		//		this.buildModel(current_model_type);
	}

	public void eModelPropertysBegin(String token){


		//		this.buildModel(current_model_type);
	}

	public void ePropertysListBegin(int type){
		UMLTreeModel to1 = null;
		//package
		if(this.current_model_type==0){
			PackageTreeModel packageTreeModel = new PackageTreeModel(""); 
			FinalPackageModel finalPackageModel = new FinalPackageModel();
			packageTreeModel.setRefModel(finalPackageModel);
			finalPackageModel.setTreeModel(to1);

		}//usecase
		else if(this.current_model_type==2){

		}
		//actor
		else if(this.current_model_type==3){

		}
		//컬레보레이션
		else if(this.current_model_type==4){

		}


	}
	public java.util.ArrayList  getViewLines() {
		return viewLines;
	}
	public void setViewLines(java.util.ArrayList viewLines) {
		this.viewLines = viewLines;
	}
	public HashMap getModelStore() {
		return modelStore;
	}
	public void setModelStore(HashMap modelStore) {
		this.modelStore = modelStore;
	}

	public Parser getParser() {
		return parser;
	}

	public void setParser(Parser parser) {
		this.parser = parser;
	}

	public LoadProgressRun getLoadProgressRun() {
		return loadProgressRun;
	}

	public void setLoadProgressRun(LoadProgressRun loadProgressRun) {
		this.loadProgressRun = loadProgressRun;
	}

	public java.util.ArrayList getOpenDiagarams() {
		return openDiagarams;
	}

	public void setOpenDiagarams(java.util.ArrayList openDiagarams) {
		this.openDiagarams = openDiagarams;
	}

	public java.util.ArrayList getRefStore() {
		return refStore;
	}

	public void setRefStore(java.util.ArrayList refStore) {
		this.refStore = refStore;
	}
	//PKY 08050701 S strcuturedActivity 저장 후 불러오기 할 경우 Activity로 변경되어있는문제
	public UMLDataModel getCurrent_UMLDataModel() {
		return current_UMLDataModel;
	}

	public void setCurrent_UMLDataModel(UMLDataModel current_UMLDataModel) {
		this.current_UMLDataModel = current_UMLDataModel;
	}
	//PKY 08050701 S strcuturedActivity 저장 후 불러오기 할 경우 Activity로 변경되어있는문제
	public UMLTreeParentModel getLinkPackage() {
		return linkPackage;
	}

	public void setLinkPackage(UMLTreeParentModel linkPackage) {
		this.linkPackage = linkPackage;
	}
	//PKY 08082201 S Frame 저장 불러와두 초기화 되지 않는문제
	public HashMap getDiagramFrame() {
		return diagramFrame;
	}

	public void setDiagramFrame(HashMap diagramFrame) {
		this.diagramFrame = diagramFrame;
	}

	public HashMap getDiagramStore() {
		return diagramStore;
	}

	public void setDiagramStore(HashMap diagramStore) {
		this.diagramStore = diagramStore;
	}
	//PKY 08082201 E Frame 저장 불러와두 초기화 되지 않는문제

	public HashMap getViewStore() {
		return viewStore;
	}

	public void setViewStore(HashMap viewStore) {
		this.viewStore = viewStore;
	}


}
