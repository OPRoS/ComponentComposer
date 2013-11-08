package kr.co.n3soft.n3com.model.comm;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

import kr.co.n3soft.n3com.edit.UMLGraphicalPartFactory;
import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.activity.DecisionModel;
import kr.co.n3soft.n3com.model.activity.FinalActivityModel;
import kr.co.n3soft.n3com.model.activity.FinalModel;
import kr.co.n3soft.n3com.model.activity.FlowFinalModel;
import kr.co.n3soft.n3com.model.activity.HForkJoinModel;
import kr.co.n3soft.n3com.model.activity.HPartitionModel;
import kr.co.n3soft.n3com.model.activity.InitialModel;
import kr.co.n3soft.n3com.model.activity.SubPartitonModel;
import kr.co.n3soft.n3com.model.activity.SynchModel;
import kr.co.n3soft.n3com.model.activity.VForkJoinModel;
import kr.co.n3soft.n3com.model.comm.descriptor.ActionTypePropertyDescriptor;
import kr.co.n3soft.n3com.model.comm.descriptor.ActorImageManagerDescriptor;
import kr.co.n3soft.n3com.model.comm.descriptor.AttrPropertyDescriptor;
import kr.co.n3soft.n3com.model.comm.descriptor.ConfigureTimeLinePropertyDescriptor;
import kr.co.n3soft.n3com.model.comm.descriptor.DetailPropertyDescriptor;
import kr.co.n3soft.n3com.model.comm.descriptor.DirDialogPropertyDescriptor;
import kr.co.n3soft.n3com.model.comm.descriptor.FontPropertyDescriptor;
import kr.co.n3soft.n3com.model.comm.descriptor.FragmentPropertyDescriptor;
import kr.co.n3soft.n3com.model.comm.descriptor.MessagePropertiestPropertyDescriptor;
import kr.co.n3soft.n3com.model.comm.descriptor.OperPropertyDescriptor;
import kr.co.n3soft.n3com.model.comm.descriptor.StructuredActivityTypePropertyDescriptor;
import kr.co.n3soft.n3com.model.comm.descriptor.TypePropertyDescriptor;
import kr.co.n3soft.n3com.model.comm.descriptor.UMLColorPropertyDescriptor;
import kr.co.n3soft.n3com.model.communication.MessageCommunicationModel;
import kr.co.n3soft.n3com.model.component.AtomicComponentModel;
import kr.co.n3soft.n3com.model.component.ComponentEditModel;
import kr.co.n3soft.n3com.model.component.ComponentLibModel;
import kr.co.n3soft.n3com.model.component.ComponentModel;
import kr.co.n3soft.n3com.model.component.AtomicLibModel;
import kr.co.n3soft.n3com.model.component.OPRoSDataTypeElementModel;
import kr.co.n3soft.n3com.model.component.OPRoSExeEnvironmentCPUElementModel;
import kr.co.n3soft.n3com.model.component.OPRoSExeEnvironmentElementModel;
import kr.co.n3soft.n3com.model.component.OPRoSExeEnvironmentOSElementModel;
import kr.co.n3soft.n3com.model.component.OPRoSExeSemanticsElementModel;
import kr.co.n3soft.n3com.model.component.OPRoSPropertiesElementModel;
import kr.co.n3soft.n3com.model.component.OPRoSPropertyElementModel;
import kr.co.n3soft.n3com.model.component.OPRoSServiceTypeElementModel;
import kr.co.n3soft.n3com.model.composite.PartModel;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.model.node.NodeItemModel;
import kr.co.n3soft.n3com.model.node.NodePackageModel;
import kr.co.n3soft.n3com.model.umlclass.AttributeEditableTableItem;
import kr.co.n3soft.n3com.model.umlclass.OperationEditableTableItem;
import kr.co.n3soft.n3com.model.usecase.FinalBoundryModel;
import kr.co.n3soft.n3com.model.usecase.UseCaseModel;
import kr.co.n3soft.n3com.parser.TeamProjectManager;
import kr.co.n3soft.n3com.project.browser.PackageTreeModel;
import kr.co.n3soft.n3com.project.browser.RootCmpComposingTreeModel;
import kr.co.n3soft.n3com.project.browser.RootTreeModel;
import kr.co.n3soft.n3com.project.browser.StrcuturedPackageTreeLibModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.project.dialog.DetailPropertyTableItem;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.examples.logicdesigner.figures.LogicColorConstants;
import org.eclipse.gef.examples.logicdesigner.model.DimensionPropertySource;
import org.eclipse.gef.examples.logicdesigner.model.LocationPropertySource;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import uuu.views.DescriptionView;



abstract public class UMLModel extends UMLElementModel implements IUMLModelUpdateListener {

    public int lang = 0;
    
    public boolean isMonitor = false;
	private int attributeCount = 0;
	private int operationCount = 0;
	private boolean isAddLine = true;
	private boolean isPartition = false; //20080327 PKY S 해당 객체에 파티션이 먼져 들어가있는지 없는지 확인
	private java.util.ArrayList groupParent  = new java.util.ArrayList();//PKY 08052101 S 컨테이너에서 그룹으로 변경
	private NodeContainerModel saveContainer=null;//2008042106PKY S
	private boolean isStero=false;//2008041401PKY S
	private String id;
	private UMLModelGuide verticalGuide, horizontalGuide;
	//	protected Hashtable inputs = new Hashtable (7);
	protected Vector inputs = new Vector();
	protected Point location = new Point(0, 0);
	protected Vector outputs = new Vector();
	static final long serialVersionUID = 1;
	protected Dimension size = new Dimension(-1, -1);
	//	protected String name = "";
	private UMLTreeModel Model = null;
	//	private Text tempText = new Text(SWT);
	public UMLDataModel uMLDataModel = null;
	private UMLModel parentModel = null;
	public UMLModel sourceModel = null;
	protected Integer layout = null;
	protected int parentLayout = 0;
	protected IPropertyDescriptor[] descriptors = null;
	java.util.List<IPropertyDescriptor> properties = new ArrayList<IPropertyDescriptor>();
	
	public String temp_name = ""; //111025 SDM - 이름중복 에러창 중복 발생 임시해결
	
	public static String ID = "ID"; //$NON-NLS-1$
	public static String ID_PACKAGE = "ID_PACKAGE"; //$NON-NLS-1$
	public static String ID_ANGLE = "angle"; //$NON-NLS-1$
	public static String ID_SIZE = "ID_SIZE"; //$NON-NLS-1$
	public static String ID_LOCATION = "ID_SIZE"; //$NON-NLS-1$
	public static String ID_NAME = "ID_NAME"; //$NON-NLS-1$
	
	public static String ID_INSTANCE = "ID_INSTANCE"; //$NON-NLS-1$
	
	public static String ID_COLOR = "ID_COLOR"; //$NON-NLS-1$
	public static String ID_DESCRIPTION = "ID_DESCRIPTION"; //$NON-NLS-1$
	public static String ID_FONT = "ID_FONT"; //$NON-NLS-1$


	//PKY 08070301 S Communication Dialog 추가작업
	public static String ID_SIGNATURE = "ID_FONT";
	public static String ID_SYNCH = "ID_FONT";
	public static String ID_KIND = "ID_FONT";
	public static String ID_LIFECYCEL = "ID_FONT";
	//PKY 08070301 E Communication Dialog 추가작업

	public static String ID_REFRESH = "ID_REFRESH"; //$NON-NLS-1$
	//	uMLDataModel = new UMLDataModel();
	protected String stereotype = "";
	protected String name = "";
	protected String instance = "";
	protected String description = "";
	protected Integer scope =new Integer(0);
	protected Integer active =new Integer(0);
	protected Integer reference =new Integer(0);
	protected String multiplicity;
	protected Color backGroundColor = LogicColorConstants.defaultFillColor;
	protected Font font = null;
	public static String ID_W = "ID_W";
	public static String ID_H = "ID_H";
	public static String ID_PARENTS = "ID_PARENTS";
	public static String ID_INTERFACES = "ID_INTERFACES";
	public static String ID_ATTRIBUTE = "ID_ATTRIBUTE";
	public static String ID_OPERATION = "ID_OPERATION";
	public static String ID_STEREOTYPE = "ID_STEREOTYPE";
	public static String ID_CONFIGURE_TIMELINE = "ID_CONFIGURE_TIMELINE";

	public static String ID_DETAIL = "ID_DETAIL";

	public static String ID_EXTENSIONPOINT = "ID_EXTENSIONPOINT";

	public static String ID_MULTI = "ID_MULTI";

	public static String ID_SCOPE = "ID_SCOPE";

	public static String ID_CHANGE_DRAG = "ID_CHANGE_DRAG";
	public static String ID_CHANGE_PROPERTY = "ID_CHANGE_PROPERTY";

	public static String ID_CHANGE_GROUP = "ID_CHANGE_GROUP";
	public static String ID_CONFIGURE_NUM = "ID_CONFIGURE_NUM";

	public static String ID_ACTIVE= "ID_ACTIVE";
	public static String ID_REFERENCE= "ID_REFERENCE";

	public static String ID_READONLY= "ID_READONLY";
	public static String ID_PRECONDITION= "ID_PRECONDITION";

	public static String ID_MAX= "ID_MAX";


	public static String ID_POSTCONDITION= "ID_POSTCONDITION";
	public static String ID_SINGLEEXECUTION= "ID_SINGLEEXECUTION";
	public static String ID_PARAMETERNAME= "ID_PARAMETERNAME";

	public static String ID_OBJECT_STATE= "ID_OBJECT_STATE";

	public static String ID_EFFECT= "ID_EFFECT";

	public static String ID_CONTEXT= "ID_CONTEXT";

	public static String ID_TAG= "ID_TAG";
	
	public static String ID_MV= "ID_MV";


	public static String ID_SIMPLE= "ID_SIMPLE";

	public static String ID_SUBMACHINESTATE= "ID_SUBMACHINESTATE";


	public static String ID_ORTHOGONAL= "ID_ORTHOGONAL";

	public static String ID_COMPOSITE= "ID_COMPOSITE";

	public static String ID_INDIRECTLYINSTANTIATED = "ID_INDIRECTLYINSTANTIATED";

	public static String ID_FILENAME = "ID_FILENAME";

	public static String ID_ACTORIMAGE = "ID_ACTORIMAGE";
	public static String ID_IMAGEDATA = "ID_IMAGEDATA";

	public static String ID_MESSAGE_PROPERTIES = "ID_MESSAGE_PROPERTIES";

	public static String ID_TYPE = "ID_TYPE";

	public static String ID_SELECT = "ID_SELECT";

	public static String ID_REF_TYPE = "ID_REF_TYPE";
	public static String ID_UPDATE_LISTENER = "ID_UPDATE_LISTENER";
	public static String ID_FRAGMENT = "ID_FRAGMENT";
	//20080721IJS
	public static String ID_REQ_ID = "ID_REQ_ID";

	//PKY 08081801 S 오퍼레이션 어트리뷰트 숨길수 있는 기능
	public static String ID_ATTR_HIDDEN = "ID_ATTR_HIDDEN";
	public static String ID_OPER_HIDDEN = "ID_OPER_HIDDEN";
	
	
	public static String ID_ID = "ID_ID";
	public static String ID_VERSION = "ID_VERSION";
	public static String ID_NODE = "ID_NODE";
	public static String ID_IP = "ID_IP";
	public static String ID_PORT = "ID_PORT";
	
	
	
	public static String ID_OS = "ID_OS";
	public static String ID_OS_VERSION = "ID_OS_VERSION";
	public static String ID_CPU = "ID_CPU";
	public static String ID_LIBRARY_TYPE = "ID_LIBRARY_TYPE";
	public static String ID_IMPL_LANGUAGE = "ID_IMPL_LANGUAGE";
	public static String ID_COMPILER = "ID_COMPILER";
	
	public static String ID_PRIORITY = "ID_PRIORITY";	//111102 SDM - PRIORITY항목 추가
	
	public String instanceName = "";
	
	//PKY 08081801 E 오퍼레이션 어트리뷰트 숨길수 있는 기능
//	public static String ID_CONFIGURE_NUM = "configureNum";
	
//	public static String ID_SCOPE = "ID_SCOPE";

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
		this.getUMLDataModel().setProperty("INSTANCE_NAME", instanceName);
	}

	private java.util.ArrayList updateListener = new java.util.ArrayList();
	//	static{
//	public static PropertyDescriptor actorProp = new FileDialogPropertyDescriptor(ID_ACTORIMAGE, N3Messages.DIALOG_IMAGE);//PKY 08080501 S Image Dialog 이미지 리스트 및 불러오기 편하도록 개선
	public static PropertyDescriptor actorProp = new ActorImageManagerDescriptor(ID_ACTORIMAGE, N3Messages.DIALOG_IMAGE);//PKY 08080501 S Image Dialog 이미지 리스트 및 불러오기 편하도록 개선
	public static PropertyDescriptor packageProp = new TextPropertyDescriptor(ID_PACKAGE, N3Messages.POPUP_PACKAGE);//2008040302 PKY S 
	public static PropertyDescriptor nameProp = new TextPropertyDescriptor(ID_NAME, N3Messages.POPUP_NAME);//2008040302 PKY S 
	
	public static PropertyDescriptor idProp = new TextPropertyDescriptor(ID_ID, "ID");//2008040302 PKY S 
	public static PropertyDescriptor versionProp = new TextPropertyDescriptor(ID_VERSION, "Version");//2008040302 PKY S 
	
	
	public static PropertyDescriptor nodeProp = new TextPropertyDescriptor(ID_NODE, "NodeId");//2008040302 PKY S 
	public static PropertyDescriptor ipProp = new TextPropertyDescriptor(ID_IP, "IP");//2008040302 PKY S 
	
	
	public static PropertyDescriptor idOs = new TextPropertyDescriptor(ID_OS, "Exe_env_os");//2008040302 PKY S 
	
	public static PropertyDescriptor idOsVer = new TextPropertyDescriptor(ID_OS_VERSION, "Exe_env_os_version");//2008040302 PKY S 
	
	public static PropertyDescriptor idCpu= new TextPropertyDescriptor(ID_CPU, "Exe_env_cpu");//2008040302 PKY S 
	
	public static PropertyDescriptor idLubType = new TextPropertyDescriptor(ID_LIBRARY_TYPE, "Exe_env_library_type");//2008040302 PKY S 
	
	public static PropertyDescriptor idImplang = new TextPropertyDescriptor(ID_IMPL_LANGUAGE, "Exe_env_impl_language");//2008040302 PKY S 
	
	public static PropertyDescriptor idCompiler= new TextPropertyDescriptor(ID_COMPILER, "Exe_env_compiler");//2008040302 PKY S 
	
	
	public static PropertyDescriptor portProp = new TextPropertyDescriptor(ID_PORT, "Port");//2008040302 PKY S 
	
	public static PropertyDescriptor instanceProp = new TextPropertyDescriptor(ID_INSTANCE, "instance");//2008040302 PKY S 

	public static PropertyDescriptor maxProp = new TextPropertyDescriptor(ID_MAX, "Max");//2008040302 PKY S
	//20080721IJS
	
	public static PropertyDescriptor streoProp = new TextPropertyDescriptor(ID_STEREOTYPE, N3Messages.POPUP_STEREOTYPE);//2008040302 PKY S 
//	public static ColorPropertyDescriptor colorPropertyDescriptor = new ColorPropertyDescriptor(ID_COLOR, N3Messages.POPUP_BACKGROUND_COLOR);//PKY 08071601 S 다이얼로그 UI작업 
//	public static DescriptionPropertyDescriptor descriptionPropertyDescriptor =
//	new DescriptionPropertyDescriptor(ID_DESCRIPTION, "설명");
	public static FontPropertyDescriptor fontPropertyDescriptor = new FontPropertyDescriptor(ID_FONT, "폰트");
	public static PropertyDescriptor attrProp = new AttrPropertyDescriptor(ID_ATTRIBUTE, N3Messages.POPUP_ATTRIBUTES);//2008040302 PKY S 
	public static PropertyDescriptor fragmProp = new FragmentPropertyDescriptor(ID_FRAGMENT,"Fragment");//2008041501PKY S
	public static PropertyDescriptor configureTimeLineProp = new ConfigureTimeLinePropertyDescriptor(ID_CONFIGURE_TIMELINE, N3Messages.POPUP_CONFIGURE_TIMELINE);//PKY 08072201 S 오퍼레이션 어트리뷰트 아이콘 삽입
	public static PropertyDescriptor messagePropertiestProp = new MessagePropertiestPropertyDescriptor(ID_MESSAGE_PROPERTIES,N3Messages.DIALOG_MESSAGE_PROPERTIEST);//PKY 08072201 S 오퍼레이션 어트리뷰트 아이콘 삽입
	public static PropertyDescriptor typeProp = new TypePropertyDescriptor(ID_TYPE, N3Messages.POPUP_INSTANCE_CLASSIFIER);//PKY 08072201 S 오퍼레이션 어트리뷰트 아이콘 삽입

	public static PropertyDescriptor DetailPropertyDescriptorProp = new DetailPropertyDescriptor(ID_DETAIL,  N3Messages.POPUP_PROPERTIES);//2008040301 PKY S 
	public static PropertyDescriptor operProp = new OperPropertyDescriptor(ID_OPERATION, N3Messages.POPUP_OPERATION);//2008040302 PKY S 
	public static PropertyDescriptor colorProp= new UMLColorPropertyDescriptor(ID_COLOR,  N3Messages.POPUP_BACKGROUND_COLOR);//PKY 08071601 S 다이얼로그 UI작업
	public static PropertyDescriptor actionTyperProp= new ActionTypePropertyDescriptor(N3Messages.DIALOG_NEW_ACTION,  N3Messages.DIALOG_NEW_ACTION);//PKY 08072401 S 액션 추가 시 타입묻는 다이어그램 오픈되지 않도록 기본타입 노멀
	public static PropertyDescriptor structuredActivityTypeProp = new StructuredActivityTypePropertyDescriptor(N3Messages.DIALOG_NEW_STCUTUREACTIVITY,  N3Messages.DIALOG_NEW_STCUTUREACTIVITY);//PKY 08072401 S 액션 추가 시 타입묻는 다이어그램 오픈되지 않도록 기본타입 노멀
	public static  PropertyDescriptor multiplicityPrope = new TextPropertyDescriptor(ID_MULTI, N3Messages.DIALOG_MULTIPLICITY);//2008043001 PKY S
	public static  PropertyDescriptor effectPrope = new TextPropertyDescriptor(ID_MULTI, "다중성");

	public static  PropertyDescriptor scopeProp = new ComboBoxPropertyDescriptor(ID_SCOPE, N3Messages.POPUP_SCOPE,
			new String[] { "Public", "Protected", "Private", "Package"});//2008040302 PKY S 
	public static  PropertyDescriptor activeProp = new ComboBoxPropertyDescriptor(ID_ACTIVE, N3Messages.DIALOG_IS_ACTIVE,//2008043001 PKY S
			new String[] { "True", "False"});
	public static  PropertyDescriptor referenceProp = new ComboBoxPropertyDescriptor(ID_REFERENCE, N3Messages.DIALOG_IS_REFERENCE,
			new String[] { "True", "False"});//PKY 08080501 S Part에 프로퍼티 속성이 잘못들어간것


	public static  PropertyDescriptor readOnlyProp = new ComboBoxPropertyDescriptor(ID_READONLY , "isReadOnly",
			new String[] { "True", "False"});
	public static  PropertyDescriptor singleExecutionProp = new ComboBoxPropertyDescriptor(ID_SINGLEEXECUTION, N3Messages.DIALOG_IS_SINGLEEXECUTION,
			new String[] { "True", "False"});

	public static PropertyDescriptor preConditionProp = new TextPropertyDescriptor(ID_PRECONDITION , N3Messages.DIALOG_PRECONDITION);
	public static PropertyDescriptor postConditionProp = new TextPropertyDescriptor(ID_POSTCONDITION , N3Messages.DIALOG_POSTCONDITION);
	public static PropertyDescriptor parameterNameProp = new TextPropertyDescriptor(ID_PARAMETERNAME , N3Messages.DIALOG_IS_PARAMETER_NAME);
	public static PropertyDescriptor objectStateProp = new TextPropertyDescriptor(ID_OBJECT_STATE , N3Messages.DIALOG_SET_OBJECT_STATE);//PKY 08061101 S

	public static PropertyDescriptor effectProp = new TextPropertyDescriptor(ID_EFFECT , "effect");
	public static PropertyDescriptor contextProp = new TextPropertyDescriptor(ID_CONTEXT , "context");

	public static PropertyDescriptor fileNameProp = new TextPropertyDescriptor(ID_FILENAME , "fileName");

	public static  PropertyDescriptor simpleProp = new ComboBoxPropertyDescriptor(ID_SIMPLE , "simple",
			new String[] { "True", "False"});
	public static  PropertyDescriptor submachineStateProp = new ComboBoxPropertyDescriptor(ID_SUBMACHINESTATE, "submachineState",
			new String[] { "True", "False"});
	public static  PropertyDescriptor orthogonalProp = new ComboBoxPropertyDescriptor(ID_ORTHOGONAL , "orthogonal",
			new String[] { "True", "False"});
	public static  PropertyDescriptor compositeProp = new ComboBoxPropertyDescriptor(ID_COMPOSITE, "composite",
			new String[] { "True", "False"});
	public java.util.ArrayList operSCOPE;//2008042101PKY S
	public java.util.ArrayList attSCOPE;//2008042101PKY S

	public static  PropertyDescriptor indirectlyInstantiatedProp = new ComboBoxPropertyDescriptor(ID_INDIRECTLYINSTANTIATED, "indirectlyInstantiated",
			new String[] { "True", "False"});

	//PKY 08081801 S 오퍼레이션 어트리뷰트 숨길수 있는 기능
	public static  PropertyDescriptor attrHiddenProp = new ComboBoxPropertyDescriptor(ID_ATTR_HIDDEN, N3Messages.DIALOG_SHOW_ATTR,
			new String[] { "True", "False"});
	public static  PropertyDescriptor operHiddenProp = new ComboBoxPropertyDescriptor(ID_OPER_HIDDEN, N3Messages.DIALOG_SHOW_OPER,
			new String[] { "True", "False"});
	
	//Atomic 프로퍼티 관련
	public static PropertyDescriptor compAddressProp = new TextPropertyDescriptor(IAtomicComponentProperty.PROPERTY_COMPANY_ADDRESS , IAtomicComponentProperty.PROPERTY_COMPANY_ADDRESS);
	public static PropertyDescriptor compHomepageProp = new TextPropertyDescriptor(IAtomicComponentProperty.PROPERTY_COMPANY_HOMEPAGE, IAtomicComponentProperty.PROPERTY_COMPANY_HOMEPAGE);
	public static PropertyDescriptor compPhomeProp = new TextPropertyDescriptor(IAtomicComponentProperty.PROPERTY_COMPANY_PHONE, IAtomicComponentProperty.PROPERTY_COMPANY_PHONE);
	public static PropertyDescriptor compNameProp = new TextPropertyDescriptor(IAtomicComponentProperty.PROPERTY_COMPANY_NAME, IAtomicComponentProperty.PROPERTY_COMPANY_NAME);
	public static PropertyDescriptor licensePolicyProp = new ComboBoxPropertyDescriptor(IAtomicComponentProperty.PROPERTY_LICENSE_POLICY, IAtomicComponentProperty.PROPERTY_LICENSE_POLICY,IAtomicComponentProperty.PROPERTY_LICENSE_POLICY_ARRAY);
	
	//Atomic 프로퍼티 관련
	public static PropertyDescriptor exeEnvDomainProp = new TextPropertyDescriptor(IAtomicComponentProperty.PROPERTY_DOMAIN , IAtomicComponentProperty.PROPERTY_DOMAIN);
	public static PropertyDescriptor exeEnvRobotTypeProp = new TextPropertyDescriptor(IAtomicComponentProperty.PROPERTY_ROBOT_TYPE, IAtomicComponentProperty.PROPERTY_ROBOT_TYPE);
	
	public static PropertyDescriptor exePeriodProp = new TextPropertyDescriptor(IAtomicComponentProperty.PROPERTY_EXE_PERIOD, IAtomicComponentProperty.PROPERTY_EXE_PERIOD);
	public static PropertyDescriptor exePriorityProp = new TextPropertyDescriptor(IAtomicComponentProperty.PROPERTY_EXE_PRIORITY, IAtomicComponentProperty.PROPERTY_EXE_PRIORITY);
	public static PropertyDescriptor exeInstanceTypeProp = new ComboBoxPropertyDescriptor(IAtomicComponentProperty.PROPERTY_EXE_INSTANCE_TYPE, IAtomicComponentProperty.PROPERTY_EXE_INSTANCE_TYPE,IAtomicComponentProperty.PROPERTY_EXE_INSTANCE_TYPE_ARRAY);
	public static PropertyDescriptor exeLifecycleTypeProp = new ComboBoxPropertyDescriptor(IAtomicComponentProperty.PROPERTY_EXE_LIFECYCLE_TYPE, IAtomicComponentProperty.PROPERTY_EXE_LIFECYCLE_TYPE,IAtomicComponentProperty.PROPERTY_EXE_LIFECYCLE_TYPE_ARRAY);
	public static PropertyDescriptor exeTypeProp = new ComboBoxPropertyDescriptor(IAtomicComponentProperty.PROPERTY_EXE_TYPE, IAtomicComponentProperty.PROPERTY_EXE_TYPE,IAtomicComponentProperty.PROPERTY_EXE_TYPE_ARRAY);
	
	public static PropertyDescriptor compilerProp = new ComboBoxPropertyDescriptor(IAtomicComponentProperty.PROPERTY_COMPILER , IAtomicComponentProperty.PROPERTY_COMPILER,IAtomicComponentProperty.PROPERTY_COMPILER_ARRAY);
//	public static PropertyDescriptor implementLangProp = new TextPropertyDescriptor(IAtomicComponentProperty.PROPERTY_IMPL_LANGUAGE, IAtomicComponentProperty.PROPERTY_IMPL_LANGUAGE);
	public static PropertyDescriptor implementLangProp  = new ComboBoxPropertyDescriptor(IAtomicComponentProperty.PROPERTY_IMPL_LANGUAGE, IAtomicComponentProperty.PROPERTY_IMPL_LANGUAGE,
			IAtomicComponentProperty.PROPERTY_IMPL_LANGUAGE_ARRAY);
	
	
	public static PropertyDescriptor libNameProp = new TextPropertyDescriptor(IAtomicComponentProperty.PROPERTY_LIBRARY_NAME, IAtomicComponentProperty.PROPERTY_LIBRARY_NAME);
	public static PropertyDescriptor libTypeProp = new TextPropertyDescriptor(IAtomicComponentProperty.PROPERTY_LIBRARY_TYPE, IAtomicComponentProperty.PROPERTY_LIBRARY_TYPE);
	
	public static PropertyDescriptor serviceTypeProp = new TextPropertyDescriptor("Service Type File Name", "Service Type File Name");
	public static PropertyDescriptor dataTypeProp = new TextPropertyDescriptor("Data Type File Name", "Data Type File Name");
	
	public static PropertyDescriptor propertyNameProp = new TextPropertyDescriptor("Component Property Name", "Component Property Name");
	public static PropertyDescriptor dataTypePropProp = new TextPropertyDescriptor("Component Property Type", "Component Property Type");
	public static PropertyDescriptor defaultValueProp = new TextPropertyDescriptor("Component Property Default Value", "Component Property Default Value");
	
	public static PropertyDescriptor srcFolder = new DirDialogPropertyDescriptor("Source Folder", "Project Folder");
	public static PropertyDescriptor cmpFolder = new TextPropertyDescriptor("Component Folder", "Source Folder");
	
	public static PropertyDescriptor priorityProp = new TextPropertyDescriptor(ID_PRIORITY, "Priority");//111102 SDM - PRIORITY 항목 추가
	
	
	
	//PKY 08081801 S 오퍼레이션 어트리뷰트 숨길수 있는 기능
	java.util.ArrayList detailProperty = new java.util.ArrayList();
	java.util.ArrayList extendsPoints = new java.util.ArrayList();
	java.util.ArrayList tags = new java.util.ArrayList();
	java.util.ArrayList interfaces = new java.util.ArrayList();
	java.util.ArrayList parents = new java.util.ArrayList();
	java.util.ArrayList refType = new java.util.ArrayList();
	java.util.ArrayList monitorVariables = new java.util.ArrayList();
	String desc = "";
	N3EditorDiagramModel n3EditorDiagramModelTemp ;
	public String view_ID = "";

	//20080811IJS 시작
	public boolean isExistModel = true;
	public boolean isReadOnlyModel = false;
	//20080811IJS 끝
	
	public boolean isView = false;
	
//  WJH 090818 S
    private boolean bSizeChange = false;
    
    public boolean getSizeChange(){
    	return bSizeChange;
    }
    
    public void setSizeChange(boolean b){
    	bSizeChange = b;
    }
//  WJH 090818 E

	protected static Image createImage(Class rsrcClass, String name) {
		InputStream stream = rsrcClass.getResourceAsStream(name);
		Image image = new Image(null, stream);
		try {

			stream.close();
		} catch (IOException ioe) {
		}
		return image;
	}

	public int addAttributeCount(){
		return this.attributeCount++;
	}

	public int addOperationCount(){
		return this.operationCount++;
	}

	//2008042502PKY S
	public void downAttributeCount(){
		if(!(this.attributeCount<=0)){
			--this.attributeCount;
		}
	}

	public void downOperationCount(){
		if(!(this.operationCount<=0)){
			--this.operationCount;
		}
	}
	//2008042502PKY E


	public void setExtendsPoints(java.util.ArrayList p){
		try{
		this.extendsPoints = p;
		uMLDataModel.setElementProperty(this.ID_EXTENSIONPOINT, p);
		firePropertyChange(ID_EXTENSIONPOINT, null, null); //$NON-NLS-1$
		//PKY 08090806 S 

		if(this.getUMLTreeModel()!=null&&this.getUMLTreeModel().getIN3UMLModelDeleteListeners()!=null)//PKY 08091009 S
		if(this.getUMLTreeModel().getIN3UMLModelDeleteListeners().size()>0){
			for(int i = 0; i < this.getUMLTreeModel().getIN3UMLModelDeleteListeners().size(); i ++){
				N3EditorDiagramModel n3 = (N3EditorDiagramModel)this.getUMLTreeModel().getIN3UMLModelDeleteListeners().get(i);
				for(int j = 0; j < n3.getChildren().size(); j ++){
					if(n3.getChildren().get(j) instanceof UseCaseModel){
						UseCaseModel useCaseModel = (UseCaseModel) n3.getChildren().get(j);
						if(useCaseModel.getUMLTreeModel()==this.getUMLTreeModel()){
							useCaseModel.firePropertyChange(ID_EXTENSIONPOINT, null, null); //$NON-NLS-1$
						}
					}
				}
			}
		}
		//PKY 08090806 E
		}catch(Exception e){
			
		}

	}

	public java.util.ArrayList getExtendsPoints(){
		if(uMLDataModel.getElementProperty(this.ID_EXTENSIONPOINT)==null){
			this.setExtendsPoints(this.extendsPoints);
		}
		return (java.util.ArrayList)uMLDataModel.getElementProperty(this.ID_EXTENSIONPOINT);
	}

	public void setTags(java.util.ArrayList p){
		this.tags = p;
//		for(int i=0;i<p.size();i++){
//			Object obj = p.get(i);
//			if(obj instanceof DetailPropertyTableItem){
//				
//			}
//		}
		uMLDataModel.setElementProperty(this.ID_TAG, p);

	}

	public java.util.ArrayList getMonitorVariables(){
//		if(uMLDataModel.getElementProperty(this.ID_MV)==null){
//			this.setMonitorVariables(this.monitorVariables);
//		}
		if(this instanceof AtomicComponentModel){
			AtomicComponentModel atm = (AtomicComponentModel)this;
		
		UMLTreeModel ut = atm.getCoreUMLTreeModel();
        if(ut!=null){
        	AtomicComponentModel um = (AtomicComponentModel)ut.getRefModel();
        	return (java.util.ArrayList)um.getUMLDataModel().getElementProperty(this.ID_MV);
        	
        }
        else{
        	return (java.util.ArrayList)atm.getUMLDataModel().getElementProperty(this.ID_MV);
        }
        	
		
		}
		else{
			return (java.util.ArrayList)this.getUMLDataModel().getElementProperty(this.ID_MV);
		}
//		return null;
	}
	
	
	public void setMonitorVariables(java.util.ArrayList p){
		this.monitorVariables = p;
//		for(int i=0;i<p.size();i++){
//			Object obj = p.get(i);
//			if(obj instanceof DetailPropertyTableItem){
//				
//			}
//		}
		uMLDataModel.setElementProperty(this.ID_MV, p);

	}
	
	public void setMonitorVariables2(java.util.ArrayList p){
		this.monitorVariables = p;
//		for(int i=0;i<p.size();i++){
//			Object obj = p.get(i);
//			if(obj instanceof DetailPropertyTableItem){
//				
//			}
//		}
		uMLDataModel.setElementProperty(this.ID_MV, p);

	}

	public java.util.ArrayList getTags(){
		if(uMLDataModel.getElementProperty(this.ID_TAG)==null){
			this.setTags(this.tags);
		}
		return (java.util.ArrayList)uMLDataModel.getElementProperty(this.ID_TAG);
	}

	public void setDesc(String p){
		uMLDataModel.setProperty(this.ID_DESCRIPTION, p);



	}

	public String getDesc(){
		if(uMLDataModel.getProperty(this.ID_DESCRIPTION)==null){
			this.setDesc("");
		}
		return uMLDataModel.getProperty(this.ID_DESCRIPTION);
	}

	public UMLModel() {

		uMLDataModel = new UMLDataModel();
		

		//20080811IJS
		if(TeamProjectManager.getInstance().isReadOnly()){
			this.setReadOnlyModel(true);
		}
		//111206 SDM S - 불필요한 LOG작성 삭제(주석처리)
		System.out.println("[umlModel11:" + TeamProjectManager.getInstance().isReadOnly() + "] " + null);
//		ProjectManager.getInstance().writeLog( "umlModel11:=>"+TeamProjectManager.getInstance().isReadOnly(),null);
		//111206 SDM E - 불필요한 LOG작성 삭제(주석처리)
		
		try {
			//공통
			//유즈케이스 
			//클래스
			//상태
			//액티비티
			//시퀀스
			//복합
			//컴포넌트
			//커뮤니케이션
			//타이밍

			//유즈케이스
		
			 if(this instanceof FinalBoundryModel){
				descriptors = new IPropertyDescriptor[] {
						nameProp, colorProp,  DetailPropertyDescriptorProp//PKY 08072401 S 모델별 프로퍼티에 Requirement추가 
				};
			}
			 else if(this instanceof ComponentEditModel){
					descriptors = new IPropertyDescriptor[] {
							nameProp, srcFolder

					};
				}
			
			else if(this instanceof FinalPackageModel){
				descriptors = new IPropertyDescriptor[] {
						// 110909 SDM - colorProp항목 제외
						versionProp,idProp,nameProp, streoProp,DetailPropertyDescriptorProp,scopeProp,packageProp

				};
			}


			else if(this instanceof PartModel){
				//시나리오
				//extension point
				descriptors = new IPropertyDescriptor[] {
						nameProp, colorProp,  attrProp, operProp, streoProp,DetailPropertyDescriptorProp,scopeProp,multiplicityPrope,referenceProp,packageProp,typeProp,attrHiddenProp,operHiddenProp//PKY 08081801 S 오퍼레이션 어트리뷰트 숨길수 있는 기능
				};
			}
			//액티비티



			else if(this instanceof FlowFinalModel){

				descriptors = new IPropertyDescriptor[] {
						nameProp, colorProp,  DetailPropertyDescriptorProp,scopeProp,multiplicityPrope,streoProp //PKY 08072401 S 모델별 프로퍼티에 Requirement추가 
				};
			}

			else if(this instanceof InitialModel){
				descriptors = new IPropertyDescriptor[] {
						nameProp, colorProp,   DetailPropertyDescriptorProp,scopeProp,streoProp//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
				};
			}
			else if(this instanceof FinalModel){
				//시나리오
				//extension point
				descriptors = new IPropertyDescriptor[] {
						nameProp, colorProp,  DetailPropertyDescriptorProp,scopeProp
				};
			}//PKY 08091103 S
				
			else if(this instanceof SynchModel){
				//시나리오
				//extension point
				descriptors = new IPropertyDescriptor[] {
						nameProp, colorProp,   DetailPropertyDescriptorProp,scopeProp//PKY 08072401 S 모델별 프로퍼티에 Requirement추가
				};
			}
			else if(this instanceof DecisionModel){
				descriptors = new IPropertyDescriptor[] {
						nameProp, colorProp,   DetailPropertyDescriptorProp,scopeProp//PKY 08072401 S 모델별 프로퍼티에 Requirement추가
				};
			}


			else if(this instanceof HPartitionModel){
				//시나리오
				//extension point
				descriptors = new IPropertyDescriptor[] {
						nameProp, colorProp,   streoProp,DetailPropertyDescriptorProp,scopeProp//PKY 08072401 S 모델별 프로퍼티에 Requirement추가
				};
			}
			else if(this instanceof HForkJoinModel){
				descriptors = new IPropertyDescriptor[] {
						nameProp, colorProp,DetailPropertyDescriptorProp,scopeProp//PKY 08072401 S 모델별 프로퍼티에 Requirement추가
				};
			}
			else if(this instanceof VForkJoinModel){
				descriptors = new IPropertyDescriptor[] {
						nameProp, colorProp ,DetailPropertyDescriptorProp,scopeProp//PKY 08072401 S 모델별 프로퍼티에 Requirement추가
				};
			}

			//상태
			
			else if(this instanceof InitialModel){
				descriptors = new IPropertyDescriptor[] {
						nameProp, colorProp,  streoProp,DetailPropertyDescriptorProp,scopeProp//PKY 08072401 S 모델별 프로퍼티에 Requirement추가
				};

			}  
			else if(this instanceof FinalModel){
				descriptors = new IPropertyDescriptor[] {
						nameProp, colorProp,  streoProp,DetailPropertyDescriptorProp,scopeProp//PKY 08072401 S 모델별 프로퍼티에 Requirement추가
				};

			}  
			else if(this instanceof FlowFinalModel){
				descriptors = new IPropertyDescriptor[] {
						nameProp, colorProp,  streoProp,DetailPropertyDescriptorProp,scopeProp,streoProp//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
				};

			} 
			else if(this instanceof SynchModel){
				descriptors = new IPropertyDescriptor[] {
						nameProp, colorProp,  streoProp,DetailPropertyDescriptorProp,scopeProp,streoProp//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
				};
				
			} 
			else if(this instanceof DecisionModel){
				descriptors = new IPropertyDescriptor[] {
						nameProp, colorProp,  streoProp,DetailPropertyDescriptorProp,scopeProp//PKY 08072401 S 모델별 프로퍼티에 Requirement추가
				};

			}  

			else if(this instanceof HForkJoinModel){
				descriptors = new IPropertyDescriptor[] {
						nameProp, colorProp,  streoProp,DetailPropertyDescriptorProp,scopeProp//PKY 08072401 S 모델별 프로퍼티에 Requirement추가
				};

			}  
			else if(this instanceof VForkJoinModel){
				descriptors = new IPropertyDescriptor[] {
						nameProp, colorProp,  streoProp,DetailPropertyDescriptorProp,scopeProp//PKY 08072401 S 모델별 프로퍼티에 Requirement추가
				};

			}  
			else if(this instanceof VForkJoinModel){
				descriptors = new IPropertyDescriptor[] {
						nameProp, colorProp,  streoProp,DetailPropertyDescriptorProp,scopeProp//PKY 08072401 S 모델별 프로퍼티에 Requirement추가
				};

			} 
			//컴포넌트
//			else if(this instanceof ComponentModel && this instanceof ComponentLibModel){
//				
//				descriptors = new IPropertyDescriptor[] {
//						versionProp,idProp,nameProp, colorProp,  streoProp,packageProp,DetailPropertyDescriptorProp//PKY 08081801 S 오퍼레이션 어트리뷰트 숨길수 있는 기능
//				};
//
//
//			}
			else if(this instanceof OPRoSExeEnvironmentElementModel){
				exeEnvDomainProp.setCategory("Execution Environment Info");
				exeEnvRobotTypeProp.setCategory("Execution Environment Info");
				compilerProp.setCategory("Library Info");
				implementLangProp.setCategory("Library Info");
				libNameProp.setCategory("Library Info");
				libTypeProp.setCategory("Library Info");
				descriptors = new IPropertyDescriptor[] {
//						versionProp,idProp,nameProp, nodeProp,
//						compAddressProp,
//						compHomepageProp,
//						compNameProp,
//						compPhomeProp,
//						licensePolicyProp,
						exeEnvDomainProp,
						exeEnvRobotTypeProp,
//						exePeriodProp,
//						exePriorityProp,
//						exeInstanceTypeProp,
//						exeLifecycleTypeProp,
//						exeTypeProp,
						compilerProp,
						implementLangProp,
						libNameProp,
						libTypeProp
						//PKY 08081801 S 오퍼레이션 어트리뷰트 숨길수 있는 기능
				};
			}
			else if(this instanceof OPRoSExeSemanticsElementModel){
				exePeriodProp.setCategory("Execution Semantics");
				exePriorityProp.setCategory("Execution Semantics");
				exeInstanceTypeProp.setCategory("Execution Semantics");
				exeLifecycleTypeProp.setCategory("Execution Semantics");
				exeTypeProp.setCategory("Execution Semantics");
				descriptors = new IPropertyDescriptor[] {
//						versionProp,idProp,nameProp, nodeProp,
//						compAddressProp,
//						compHomepageProp,
//						compNameProp,
//						compPhomeProp,
//						licensePolicyProp,
//						exeEnvDomainProp,
//						exeEnvRobotTypeProp,
						exePeriodProp,
						exePriorityProp,
						exeInstanceTypeProp,
						exeLifecycleTypeProp,
						exeTypeProp,
//						compilerProp,
//						implementLangProp,
//						libNameProp,
//						libTypeProp
						//PKY 08081801 S 오퍼레이션 어트리뷰트 숨길수 있는 기능
				};
			}
			else if(this instanceof OPRoSExeEnvironmentCPUElementModel){
				idCpu.setCategory("Execution CPU Info");
//				exePriorityProp.setCategory("Execution Semantics");
//				exeInstanceTypeProp.setCategory("Execution Semantics");
//				exeLifecycleTypeProp.setCategory("Execution Semantics");
//				exeTypeProp.setCategory("Execution Semantics");
				descriptors = new IPropertyDescriptor[] {
//						versionProp,idProp,nameProp, nodeProp,
//						compAddressProp,
//						compHomepageProp,
//						compNameProp,
//						compPhomeProp,
//						licensePolicyProp,
//						exeEnvDomainProp,
//						exeEnvRobotTypeProp,
						idCpu,
//						exePriorityProp,
//						exeInstanceTypeProp,
//						exeLifecycleTypeProp,
//						exeTypeProp,
//						compilerProp,
//						implementLangProp,
//						libNameProp,
//						libTypeProp
						//PKY 08081801 S 오퍼레이션 어트리뷰트 숨길수 있는 기능
				};
			}
			 
			else if(this instanceof OPRoSExeEnvironmentOSElementModel){
				idOs.setCategory("Execution OS Info");
				idOsVer.setCategory("Execution OS Info");
//				exePriorityProp.setCategory("Execution Semantics");
//				exeInstanceTypeProp.setCategory("Execution Semantics");
//				exeLifecycleTypeProp.setCategory("Execution Semantics");
//				exeTypeProp.setCategory("Execution Semantics");
				descriptors = new IPropertyDescriptor[] {
//						versionProp,idProp,nameProp, nodeProp,
//						compAddressProp,
//						compHomepageProp,
//						compNameProp,
//						compPhomeProp,
//						licensePolicyProp,
//						exeEnvDomainProp,
//						exeEnvRobotTypeProp,
						idOs,
						idOsVer
//						exePriorityProp,
//						exeInstanceTypeProp,
//						exeLifecycleTypeProp,
//						exeTypeProp,
//						compilerProp,
//						implementLangProp,
//						libNameProp,
//						libTypeProp
						//PKY 08081801 S 오퍼레이션 어트리뷰트 숨길수 있는 기능
				};
			}
			else if(this instanceof OPRoSPropertyElementModel){
				defaultValueProp.setCategory("Property Info");
				dataTypePropProp.setCategory("Property Info");
				propertyNameProp.setCategory("Property Info");
//				exePriorityProp.setCategory("Execution Semantics");
//				exeInstanceTypeProp.setCategory("Execution Semantics");
//				exeLifecycleTypeProp.setCategory("Execution Semantics");
//				exeTypeProp.setCategory("Execution Semantics");
				descriptors = new IPropertyDescriptor[] {
//						versionProp,idProp,nameProp, nodeProp,
//						compAddressProp,
//						compHomepageProp,
//						compNameProp,
//						compPhomeProp,
//						licensePolicyProp,
//						exeEnvDomainProp,
//						exeEnvRobotTypeProp,
						defaultValueProp,
						dataTypePropProp,
						propertyNameProp,
						
//						exePriorityProp,
//						exeInstanceTypeProp,
//						exeLifecycleTypeProp,
//						exeTypeProp,
//						compilerProp,
//						implementLangProp,
//						libNameProp,
//						libTypeProp
						//PKY 08081801 S 오퍼레이션 어트리뷰트 숨길수 있는 기능
				};
			}
			else if(this instanceof OPRoSDataTypeElementModel){
				dataTypeProp.setCategory("Data Type Info");
				
//				exePriorityProp.setCategory("Execution Semantics");
//				exeInstanceTypeProp.setCategory("Execution Semantics");
//				exeLifecycleTypeProp.setCategory("Execution Semantics");
//				exeTypeProp.setCategory("Execution Semantics");
				descriptors = new IPropertyDescriptor[] {
//						versionProp,idProp,nameProp, nodeProp,
//						compAddressProp,
//						compHomepageProp,
//						compNameProp,
//						compPhomeProp,
//						licensePolicyProp,
//						exeEnvDomainProp,
//						exeEnvRobotTypeProp,
						dataTypeProp
						
						
//						exePriorityProp,
//						exeInstanceTypeProp,
//						exeLifecycleTypeProp,
//						exeTypeProp,
//						compilerProp,
//						implementLangProp,
//						libNameProp,
//						libTypeProp
						//PKY 08081801 S 오퍼레이션 어트리뷰트 숨길수 있는 기능
				};
			}
			else if(this instanceof OPRoSServiceTypeElementModel){
				serviceTypeProp.setCategory("Service Type Info");
				
//				exePriorityProp.setCategory("Execution Semantics");
//				exeInstanceTypeProp.setCategory("Execution Semantics");
//				exeLifecycleTypeProp.setCategory("Execution Semantics");
//				exeTypeProp.setCategory("Execution Semantics");
				descriptors = new IPropertyDescriptor[] {
//						versionProp,idProp,nameProp, nodeProp,
//						compAddressProp,
//						compHomepageProp,
//						compNameProp,
//						compPhomeProp,
//						licensePolicyProp,
//						exeEnvDomainProp,
//						exeEnvRobotTypeProp,
						serviceTypeProp,
						
						
//						exePriorityProp,
//						exeInstanceTypeProp,
//						exeLifecycleTypeProp,
//						exeTypeProp,
//						compilerProp,
//						implementLangProp,
//						libNameProp,
//						libTypeProp
						//PKY 08081801 S 오퍼레이션 어트리뷰트 숨길수 있는 기능
				};
			}
			 
			 
		
			 
			 
			else if(this instanceof ComponentModel ){
				String stereo = this.getStereotype();
				
//				if("".equals(this.getStereotype())){
//					
//				}
//				else
				//111110 SDM S - 아톰/컴포지트 라이브러리 구분
				if(this instanceof ComponentLibModel){
					if(this instanceof AtomicLibModel){

						idProp.setCategory("Component Info");
						nameProp.setCategory("Component Info");
						versionProp.setCategory("Component Info");
											
						compAddressProp.setCategory("Copyright Info");
						compHomepageProp.setCategory("Copyright Info");
						compNameProp.setCategory("Copyright Info");
						compPhomeProp.setCategory("Copyright Info");
						licensePolicyProp.setCategory("Copyright Info");
						
						exeEnvDomainProp.setCategory("Execution Environment Info");
						exeEnvRobotTypeProp.setCategory("Execution Environment Info");
						
						exePeriodProp.setCategory("Execution Semantics");
						exePriorityProp.setCategory("Execution Semantics");
						exeInstanceTypeProp.setCategory("Execution Semantics");
						exeLifecycleTypeProp.setCategory("Execution Semantics");
						exeTypeProp.setCategory("Execution Semantics");
						
						compilerProp.setCategory("Library Info");
						implementLangProp.setCategory("Library Info");
						libNameProp.setCategory("Library Info");
						libTypeProp.setCategory("Library Info");
						
						srcFolder.setCategory("Component Source Info");
						
						
						
						descriptors = new IPropertyDescriptor[] {
								//110909 S SDM - Property 항목 수정>>이전항목은 주석처리로 남김
								versionProp,nameProp,streoProp,packageProp,DetailPropertyDescriptorProp,nodeProp,priorityProp	//111102 SDM - PRIORITY 항목 추가
						};

//						descriptors = new IPropertyDescriptor[] {
//								//110909 SDM - colorProp항목만 제
//								versionProp,nameProp,streoProp,packageProp,DetailPropertyDescriptorProp,nodeProp,priorityProp//PKY 08081801 S 오퍼레이션 어트리뷰트 숨길수 있는 기능
//						};
					}
					else{
						descriptors = new IPropertyDescriptor[] {
								//110909 SDM - colorProp항목만 제
								versionProp,nameProp,streoProp,packageProp,DetailPropertyDescriptorProp,nodeProp//PKY 08081801 S 오퍼레이션 어트리뷰트 숨길수 있는 기능
						};
					}
				}
				//111110 SDM S - 아톰/컴포지트 라이브러리 구분
				else if(this instanceof AtomicComponentModel){
					idProp.setCategory("Component Info");
					nameProp.setCategory("Component Info");
					versionProp.setCategory("Component Info");
										
					compAddressProp.setCategory("Copyright Info");
					compHomepageProp.setCategory("Copyright Info");
					compNameProp.setCategory("Copyright Info");
					compPhomeProp.setCategory("Copyright Info");
					licensePolicyProp.setCategory("Copyright Info");
					
					exeEnvDomainProp.setCategory("Execution Environment Info");
					exeEnvRobotTypeProp.setCategory("Execution Environment Info");
					
					exePeriodProp.setCategory("Execution Semantics");
					exePriorityProp.setCategory("Execution Semantics");
					exeInstanceTypeProp.setCategory("Execution Semantics");
					exeLifecycleTypeProp.setCategory("Execution Semantics");
					exeTypeProp.setCategory("Execution Semantics");
					
					compilerProp.setCategory("Library Info");
					implementLangProp.setCategory("Library Info");
					libNameProp.setCategory("Library Info");
					libTypeProp.setCategory("Library Info");
					
					srcFolder.setCategory("Component Source Info");
					
					
					
					descriptors = new IPropertyDescriptor[] {
							//110909 S SDM - Property 항목 수정>>이전항목은 주석처리로 남김
							versionProp,nameProp,streoProp,packageProp,DetailPropertyDescriptorProp,nodeProp,priorityProp	//111102 SDM - PRIORITY 항목 추가
							/*
							versionProp, nodeProp,
							compAddressProp,
							compHomepageProp,
							compNameProp,
							compPhomeProp,
							licensePolicyProp,
							exeEnvDomainProp,
							exeEnvRobotTypeProp,
							exePeriodProp,
							exePriorityProp,
							exeInstanceTypeProp,
							exeLifecycleTypeProp,
							exeTypeProp,
							compilerProp,
							implementLangProp,
							libNameProp,
							libTypeProp,
							cmpFolder,
							packageProp,
							DetailPropertyDescriptorProp,
							*/
							//110909 E SDM - Property 항목 수정>>이전항목은 주석처리로 남김
							//PKY 08081801 S 오퍼레이션 어트리뷰트 숨길수 있는 기능
					};
//					OPRoSUnEditableTextPropertyDescriptor componentNameDescriptor = 
//						new OPRoSUnEditableTextPropertyDescriptor(IAtomicComponentProperty.PROPERTY_COMP_NAME, IAtomicComponentProperty.PROPERTY_COMP_NAME);
//					componentNameDescriptor.setCategory("test");
//					properties.add(componentNameDescriptor);
//					descriptors = properties.toArray(new IPropertyDescriptor[0]);
					
				}
				
				else
				descriptors = new IPropertyDescriptor[] {
						//110909 S SDM - Property 항목 수정>>이전항목은 주석처리로 남김	//111102 SDM - 이전 항목으로 돌림.
//						versionProp,idProp,nameProp,streoProp,packageProp,DetailPropertyDescriptorProp,nodeProp,idOs
//						,idOsVer,idCpu,idLubType,idImplang,idCompiler//PKY 08081801 S 오퍼레이션 어트리뷰트 숨길수 있는 기능
						
						versionProp,nameProp,   streoProp,packageProp,DetailPropertyDescriptorProp,nodeProp,idOs
						,idOsVer,idCpu,idLubType,idImplang,idCompiler//PKY 08081801 S 오퍼레이션 어트리뷰트 숨길수 있는 기능
						
						//110909 E SDM - Property 항목 수정>>이전항목은 주석처리로 남김
				};


			}
			else if(this instanceof NodeItemModel){
				
				descriptors = new IPropertyDescriptor[] {
						// 110909 SDM - colorProp항목 제외
						packageProp,nodeProp,ipProp,portProp//PKY 08081801 S 오퍼레이션 어트리뷰트 숨길수 있는 기능
				};


			}
			
			

			

			
			
			
			

			

			else if(this instanceof MessageCommunicationModel){
				descriptors = new IPropertyDescriptor[] {
						DetailPropertyDescriptorProp
				};
			}
			
			//2008042204PKY E
			//PKY 08080501 S 다이어그램 프로퍼티에 이름넣을 수 있도록 수정
			else if(this instanceof PortModel){
				descriptors = new IPropertyDescriptor[] {
						nameProp//PKY 08071601 S Port 패키지 경로안나오도록 
				};

			}  


			else if(this instanceof N3EditorDiagramModel){
				descriptors = new IPropertyDescriptor[] {
						nameProp
				};
			}
			//PKY 08080501 S 다이어그램 프로퍼티에 이름넣을 수 있도록 수정


			else{
				descriptors = new IPropertyDescriptor[] {
						//PKY 08072401 S 모델별 프로퍼티에 Requirement추가
				}; 
			}
			uMLDataModel.setElementProperty(this.ID_UPDATE_LISTENER, this.updateListener);
			this.view_ID = ProjectManager.getInstance().getViewId(this);//2008042901 PKY S 

		}
		catch (Exception e) {
			e.printStackTrace();
			ProjectManager.getInstance().writeLog( "umlModel",e);
		}
	}

	public void setDetailProperty(java.util.ArrayList p){
		this.detailProperty = p;

		uMLDataModel.setElementProperty(this.ID_DETAIL, p);
		//PKY 08090806 S 
		if(this instanceof UseCaseModel){
			if(this.getUMLTreeModel().getIN3UMLModelDeleteListeners()!=null){
				for(int i = 0 ; i < this.getUMLTreeModel().getIN3UMLModelDeleteListeners().size(); i ++){
					N3EditorDiagramModel n3Diagram= (N3EditorDiagramModel)this.getUMLTreeModel().getIN3UMLModelDeleteListeners().get(i);
					for(int j = 0; j <n3Diagram.getChildren().size(); j++){
						if(n3Diagram.getChildren().get(j) instanceof UseCaseModel){
							if(((UseCaseModel)n3Diagram.getChildren().get(j)).getUMLTreeModel()== this.getUMLTreeModel()){
								((UseCaseModel)n3Diagram.getChildren().get(j)).refresh();
								j=n3Diagram.getChildren().size();
							}

						}
					}
	
				}
			}

		}
		//PKY 08090806 E 

	}

	public java.util.ArrayList getDetailProperty(){
		if(uMLDataModel.getElementProperty(this.ID_DETAIL)==null){
			this.setDetailProperty(this.detailProperty);
		}
		return (java.util.ArrayList)uMLDataModel.getElementProperty(this.ID_DETAIL);
	}

	public void select(boolean isSelect){
		//111206 SDM S - 불필요한 LOG작성 삭제(주석처리)
		System.out.println("[select:" + isSelect + "] " + null);
//		ProjectManager.getInstance().writeLog("select:"+isSelect, null);
		//111206 SDM S - 불필요한 LOG작성 삭제(주석처리)
		firePropertyChange(this.ID_SELECT, null, isSelect); //$NON-NLS-1$
	}

	//참조관련
	public void addUpdateListener(UMLModel p) {
		updateListener = (java.util.ArrayList)uMLDataModel.getElementProperty(this.ID_UPDATE_LISTENER);
		updateListener.add(p);
	}

	//참조관련
	public void addUpdateLineListener(IUMLModelUpdateListener p) {
		updateListener = (java.util.ArrayList)uMLDataModel.getElementProperty(this.ID_UPDATE_LISTENER);
		updateListener.add(p);
	}

	//참조관련
	public void removeUpdateListener(UMLModel p) {
		updateListener = (java.util.ArrayList)uMLDataModel.getElementProperty(this.ID_UPDATE_LISTENER);
		this.updateListener.remove(p);
	}

	//참조관련
	public void fireChildUpdate(UpdateEvent e) {
		for (int i = 0; i < this.updateListener.size(); i++) {

			IUMLModelUpdateListener iu = (IUMLModelUpdateListener)this.updateListener.get(i);
//			if(!(iu instanceof LifeLineModel))
			iu.updateModel(e);
		}
	}

	//라인 연결시 부모에게 연결시켜야 하는 경우
	public void setParentModel(UMLModel p) {
		this.parentModel = p;
	}

	public UMLModel getParentModel() {
		return this.parentModel;
	}

	public void setLayout(Integer s) {
		this.layout = s;
		//		text = s;
		//		firePropertyChange("labelContents", null, text); //$NON-NLS-2$//$NON-NLS-1$
	}

	public void setParentLayout(int s) {
		this.parentLayout = s;
		//		text = s;
		//		firePropertyChange("labelContents", null, text); //$NON-NLS-2$//$NON-NLS-1$
	}

	public Integer getLayout() {
		return this.layout;
		//		text = s;
		//		firePropertyChange("labelContents", null, text); //$NON-NLS-2$//$NON-NLS-1$
	}

	public int getParentLayout() {
		return this.parentLayout;
		//		text = s;
		//		firePropertyChange("labelContents", null, text); //$NON-NLS-2$//$NON-NLS-1$
	}

	//	protected String getNewID(){
	//	return "";
	////	this.
	//	}
	public void connectInput(LineModel w) {
		//		inputs.put(w.getTargetTerminal(), w);
		inputs.addElement(w);
		update();
		fireStructureChange(INPUTS, w);
	}

	public void connectOutput(LineModel w) {
		outputs.addElement(w);
		update();
		fireStructureChange(OUTPUTS, w);
	}

	public void disconnectInput(LineModel w) {
		inputs.remove(w);
		update();
		fireStructureChange(INPUTS, w);
	}

	public void disconnectOutput(LineModel w) {
		outputs.removeElement(w);
		update();
		fireStructureChange(OUTPUTS, w);
	}

	public Vector getConnections() {
		Vector v = (Vector)outputs.clone();
		Enumeration ins = inputs.elements();
		while (ins.hasMoreElements())
			v.addElement(ins.nextElement());
		return v;
	}

	protected void setOutput(String terminal, boolean val) {
//		Enumeration elements = outputs.elements();
//		Wire w;
//		while (elements.hasMoreElements()) {
//		w = (Wire)elements.nextElement();
//		if (w.getSourceTerminal().equals(terminal) && this.equals(w.getSource()))
//		w.setValue(val);
//		}
	}

	//	protected boolean getInput(String terminal) {
	//	LineModel w = (LineModel)inputs.get(terminal);
	//	return (w == null) ? false : w.getValue();
	//	}
	public UMLModelGuide getHorizontalGuide() {
		return horizontalGuide;
	}

	public Image getIcon() {
		return getIconImage();
	}

	abstract public Image getIconImage();

	public String getID() {
		return uMLDataModel.getId();
	}

	public Point getLocation() {
		return location;
	}

	protected String getNewID() {
		return "";
	}

	/**
	 * Returns useful property descriptors for the use in property sheets. this supports location and size.
	 * @return  Array of property descriptors.
	 */
	public IPropertyDescriptor[] getPropertyDescriptors() {

		//20080728 KDI s 디스크립션 뷰에 설명 나타나도록 수정
		IViewPart iv = ProjectManager.getInstance().window.getActivePage().findView("uuu.views.DescriptionView");		
		if(iv!=null){	   		
			DescriptionView descriptionView = ProjectManager.getInstance().getDescriptionView();
			if(descriptionView != null){
				if(this instanceof AttributeElementModel || this instanceof OperationElementModel){
					if(this instanceof AttributeElementModel){
						AttributeElementModel attr = (AttributeElementModel)this;
						AttributeEditableTableItem item = attr.getAttributeEditableTableItem();
						if(item != null){
							ProjectManager.getInstance().getDescriptionView().setDescriptionViwe(item.getDesc());
						}						
					}
					else if(this instanceof OperationElementModel){
						OperationElementModel oper = (OperationElementModel)this;
						OperationEditableTableItem item = oper.getAttributeEditableTableItem();
						if(item != null){
							ProjectManager.getInstance().getDescriptionView().setDescriptionViwe(item.getDesc());
						}
					}
				}	   				
				else
					descriptionView.setDescriptionViwe(getDesc());
			}
		}	   	
		//20080728 KDI e 디스크립션 뷰에 설명 나타나도록 수정

		return descriptors;
	}

	/**
	 * Returns an Object which represents the appropriate value for the property name supplied.
	 * @param propName  Name of the property for which the the values are needed.
	 * @return  Object which is the value of the property.
	 */
	public Object getPropertyValue(Object propName) {
		try{
			ProjectManager.getInstance().setSelectPropertyUMLElementModel(this);
			if (ID_SIZE.equals(propName))
				return new DimensionPropertySource(getSize());
			else if (ID_LOCATION.equals(propName))
				return new LocationPropertySource(getLocation());
			else if (ID_NAME.equals(propName)) {
				return this.getName();
			}
			else if (this.ID_INSTANCE.equals(propName)) {
				return this.getInstance();
			}
			else if (this.ID_COLOR.equals(propName)) {
				return this.backGroundColor.getRGB();
			} else if (this.ID_DESCRIPTION.equals(propName)) {
				return this.description;
			}
			else if (this.ID_FONT.equals(propName)) {
				return this.font.getFontData();
			}
			else if (this.ID_STEREOTYPE.equals(propName)) {
				return this.getStereotype();
			}
			else if (this.ID_SCOPE.equals(propName)) {
				return this.getScope();
			}
			else if (this.ID_ACTIVE.equals(propName)) {
				return this.getActive();
			}
			else if (this.ID_REFERENCE.equals(propName)) {
				return this.getReference();
			}
			else if (this.ID_MULTI.equals(propName)) {
				return this.getMultiplicity();
			}

			else if (this.ID_READONLY.equals(propName)) {
				return this.getReadOnly();
			}
			else if (this.ID_PRECONDITION.equals(propName)) {
				return this.getPreCondition();
			}
			else if (this.ID_POSTCONDITION.equals(propName)) {
				return this.getPostCondition();
			}
			else if (this.ID_SINGLEEXECUTION.equals(propName)) {
				return this.getSingleExecution();
			}
			else if (this.ID_PARAMETERNAME.equals(propName)) {
				return this.getParameterName();
			}
			else if (this.ID_OBJECT_STATE.equals(propName)) {
				return this.getObjectState();
			}

			else if (this.ID_EFFECT.equals(propName)) {
				return this.getEffect();
			}
			else if (this.ID_CONTEXT.equals(propName)) {
				return this.getContext();
			}

			else if (this.ID_SIMPLE.equals(propName)) {
				return this.getSimple();
			}
			else if (this.ID_SUBMACHINESTATE.equals(propName)) {
				return this.getSubmachineState();
			}
			else if (this.ID_ORTHOGONAL.equals(propName)) {
				return this.getOrthogonal();
			}
			else if (this.ID_COMPOSITE.equals(propName)) {
				return this.getComposite();
			}
			else if (this.ID_INDIRECTLYINSTANTIATED.equals(propName)) {
				return this.getIndirectlyInstantiated();
			}
			else if (this.ID_ACTORIMAGE.equals(propName)) {
				return this.getActorImage();
			}
			else if (this.ID_PACKAGE.equals(propName)) {
				return this.getPackage();
			}
			else if (this.ID_MAX.equals(propName)) {
				return this.getMax();
			}//20080721IJS
			else if (this.ID_REQ_ID.equals(propName)) {

				return this.getReqID();
			}
			//PKY 08081801 S 오퍼레이션 어트리뷰트 숨길수 있는 기능
			else if (this.ID_ATTR_HIDDEN.equals(propName)) {

				return this.getShowAttr();
			}
			else if (this.ID_OPER_HIDDEN.equals(propName)) {

				return this.getShowOper();
			}
			//PKY 08081801 E 오퍼레이션 어트리뷰트 숨길수 있는 기능
			//PKY 08090904 S 
			else if(this.ID_FILENAME.equals(propName)){
				return this.getFileName();
			}
			//PKY 08090904 E 
			else if(this.ID_VERSION.equals(propName)){
				return this.getVersion();
			}
			else if(this.ID_ID.equals(propName)){
				return this.getId();
			}
			else if(this.ID_IP.equals(propName)){
				return this.getIP();
			}
			else if(this.ID_NODE.equals(propName)){
				return this.getNode();
			}
			else if(this.ID_PORT.equals(propName)){
				return this.getPort1();
			}
			
			else if(this.ID_OS.equals(propName)){
				return this.getOs();
			}
			else if(this.ID_OS_VERSION.equals(propName)){
				return this.getOsVersion();
			}
			else if(this.ID_CPU.equals(propName)){
				return this.getCpu();
			}
			else if(this.ID_LIBRARY_TYPE.equals(propName)){
				return this.getLibType();
			}
			else if(this.ID_IMPL_LANGUAGE.equals(propName)){
				return this.getImplLang();
			}
			else if(this.ID_COMPILER.equals(propName)){
				return this.getCompiler();
			}
			//atomic-------------------
			else if(IAtomicComponentProperty.PROPERTY_COMPANY_ADDRESS.equals(propName)){
				return this.getCompAddressProp();
			}
			else if(IAtomicComponentProperty.PROPERTY_COMPANY_HOMEPAGE.equals(propName)){
				return this.getcompHomepageProp();
			}
			else if(IAtomicComponentProperty.PROPERTY_COMPANY_PHONE.equals(propName)){
				return this.getcompPhomeProp();
			}
			else if(IAtomicComponentProperty.PROPERTY_COMPANY_NAME.equals(propName)){
				return this.getcompNameProp();
			}
			else if(IAtomicComponentProperty.PROPERTY_LICENSE_POLICY.equals(propName)){
				return this.getlicensePolicyProp();
			}
			else if(IAtomicComponentProperty.PROPERTY_DOMAIN.equals(propName)){
				return this.getexeEnvDomainProp();
			}
			else if(IAtomicComponentProperty.PROPERTY_ROBOT_TYPE.equals(propName)){
				return this.getexeEnvRobotTypeProp();
			}
				else if(IAtomicComponentProperty.PROPERTY_EXE_PERIOD.equals(propName)){
				return this.getexePeriodProp();
			}
			else if(IAtomicComponentProperty.PROPERTY_EXE_PRIORITY.equals(propName)){
				return this.getexePriorityProp();
			}
			else if(IAtomicComponentProperty.PROPERTY_EXE_INSTANCE_TYPE.equals(propName)){
				return this.getexeInstanceTypeProp();
			}
			else if(IAtomicComponentProperty.PROPERTY_EXE_LIFECYCLE_TYPE.equals(propName)){
				return this.getexeLifecycleTypeProp();
			}
			else if(IAtomicComponentProperty.PROPERTY_EXE_TYPE.equals(propName)){
				return this.getexeTypeProp();
			}
			else if(IAtomicComponentProperty.PROPERTY_COMPILER.equals(propName)){
				return this.getcompilerProp();
			}
			else if(IAtomicComponentProperty.PROPERTY_IMPL_LANGUAGE.equals(propName)){
				return this.getimplementLangProp();
			}
			else if(IAtomicComponentProperty.PROPERTY_LIBRARY_NAME.equals(propName)){
				return this.getlibNameProp();
			}
				else if(IAtomicComponentProperty.PROPERTY_LIBRARY_TYPE.equals(propName)){
				return this.getlibTypeProp();
			}
			
			
				else if("Service Type File Name".equals(propName)){
					return this.getServiceTypeFileName();
				}
				else if("Data Type File Name".equals(propName)){
					return this.getDataTypeFileName();
				}
				else if("Component Property Name".equals(propName)){
					return this.getPropertyName();
				}
				else if("Component Property Type".equals(propName)){
					return this.getDataType();
				}
					else if("Component Property Default Value".equals(propName)){
					return this.getDefaultValue();
				}
			
					else if("Source Folder".equals(propName)){
						return this.getSrcFolder();
					}
					else if("Component Folder".equals(propName)){
						return this.getCmpFolder();
					}
			
			//111102 SDM S - PRIORITY 항목 추가
			else if (ID_PRIORITY.equals(propName)) {
				return this.getexePriorityProp();
			}
			//111102 SDM E - PRIORITY 항목 추가
		
			//--------------------------

//			else{
				
//			}
		}
		catch(Exception e){
			ProjectManager.getInstance().writeLog( "getPropertyValue",e);
			e.printStackTrace();
		}
		return null;
	}

	public String getPackage(){
		StringBuffer sb = new StringBuffer();
		java.util.ArrayList list = new java.util.ArrayList();
		if(this.Model!=null){
			this.Model.getPackage(list);
		}
		//2008040303 PKY S 
//       sb.append("a");
		for(int i=list.size()-1;i>0;i--){
			if(i!=list.size()-1){
				String p = (String)list.get(i);

				sb.append(p);
				if(i<list.size()-1){
					if(i!=1)
						sb.append(".");
				}
			}
		}
		//2008040303 PKY E 

		return sb.toString();
	}
	public Dimension getSize() {
		return size;
	}

	public String getName() {
		return uMLDataModel.getName();
	}

	public Vector getSourceConnections() {
		return (Vector)outputs.clone();
	}

	public Vector getTargetConnections() {
		Enumeration elements = inputs.elements();
		Vector v = new Vector(inputs.size());
		while (elements.hasMoreElements())
			v.addElement(elements.nextElement());
		return v;
	}

	public UMLModelGuide getVerticalGuide() {
		return verticalGuide;
	}

	/**  */
	public boolean isPropertySet() {
		return true;
	}

	public void setHorizontalGuide(UMLModelGuide hGuide) {
		horizontalGuide = hGuide;

		/*
		 * @TODO:Pratik   firePropertyChanged?
		 */
	}

	/*
	 * Does nothing for the present, but could be
	 * used to reset the properties of this to
	 * whatever values are desired.
	 *
	 * @param id  Parameter which is to be reset.
	 *
public void resetPropertyValue(Object id){
	if(ID_SIZE.equals(id)){;}
	if(ID_LOCATION.equals(id)){;}
}*/

	public void setID(String s) {
		id = s;
	}

	public void setLocation(Point p) {
		//port
//		System.out.println("p1:" + p);
//		if (location.equals(p))
//		return;
//		location = p;
//		firePropertyChange(ID_LOCATION, null, p); //$NON-NLS-1$
		//PKY 08052101 S 컨테이너에서 그룹으로 변경

		//port
		//PKY 08081101 E 포함관계 오른쪽 메뉴로 해서 합치는 불편함 문제 개선
		int x = this.getLocation().x;
		int y = this.getLocation().y;
		System.out.println("p1:" + p);
		//PKY 08081101 E 포함관계 오른쪽 메뉴로 해서 합치는 불편함 문제 개선
		//		pm.checkMove(this.getRectangle());
		//		if (location.equals(p)){
		//			
		//			return;
		//		}

		location = p;
		firePropertyChange(ID_LOCATION, null, p); //$NON-NLS-1$
		if(ProjectManager.getInstance().getSelectNodes()!=null)
			if(ProjectManager.getInstance().getSelectNodes().size()>1){
				return;
			}
//		pm.setPortLocations(p);
		java.util.ArrayList models = new  java.util.ArrayList();

		if( this.getUMLDataModel().getElementProperty("GROUP_CHILDRENS")!=null){
			x=p.x-x;
			y=p.y-y;	
			java.util.List list=ProjectManager.getInstance().getSelectNodes();
			if(list.size()>0){
				UMLModel model1=null;
				if(list.get(0) instanceof EditPart){
					EditPart EditPart =(EditPart)list.get(0);
					model1=(UMLModel)EditPart.getModel();
				}
				else if(list.get(0) instanceof UMLModel){
					model1=(UMLModel)list.get(0);
				}
				if(model1!=null){
					if(ProjectManager.getInstance().getCopyModelType(model1)!=-1){
						if(model1==this){   

							models=(java.util.ArrayList)this.getUMLDataModel().getElementProperty("GROUP_CHILDRENS");
							for(int i=0; i<models.size();i++){
								if(models.get(i) instanceof UMLModel){
									UMLModel model=(UMLModel)models.get(i);
									Point point = new Point(model.getLocation().x+x, model.getLocation().y+y);
									model.setLocation(point);
								}
							}
						}
					}
				}

			}
		}
		if( this.getUMLDataModel().getElementProperty("GROUP_PARENTS")!=null){
			models=(java.util.ArrayList)this.getUMLDataModel().getElementProperty("GROUP_PARENTS");
			for(int i=0; i<models.size();i++){
				System.out.println("x"+((UMLModel)models.get(i)).getLocation().x);
				System.out.println("width"+((UMLModel)models.get(i)).getSize().width);
				System.out.println("y"+((UMLModel)models.get(i)).getLocation().y);
				System.out.println("height"+((UMLModel)models.get(i)).getSize().height);
//				int width=((UMLModel)models.get(i)).getLocation().x+((UMLModel)models.get(i)).getSize().width;
//				int height=((UMLModel)models.get(i)).getLocation().y+((UMLModel)models.get(i)).getSize().height;
				int width=((UMLModel)models.get(i)).getLocation().x+((UMLModel)models.get(i)).getSize().width;
				int height=((UMLModel)models.get(i)).getLocation().y+((UMLModel)models.get(i)).getSize().height;
				if(width<this.location.x||((UMLModel)models.get(i)).getLocation().x>this.location.x){
					if(((UMLModel)models.get(i)).getUMLDataModel().getElementProperty("GROUP_CHILDRENS")!=null){
						java.util.ArrayList arrayList=(java.util.ArrayList)((UMLModel)models.get(i)).getUMLDataModel().getElementProperty("GROUP_CHILDRENS");
						for(int j=0;j<arrayList.size();j++){
							if(arrayList.get(j)==this){
								arrayList.remove(j);    						   
							}
						}
						((UMLModel)models.get(i)).getUMLDataModel().setElementProperty("GROUP_CHILDRENS", arrayList);
					}
					this.getUMLDataModel().removeProperty("GROUP_PARENTS");
				}
				else if(height<this.location.y||((UMLModel)models.get(i)).getLocation().y>this.location.y){
					if(((UMLModel)models.get(i)).getUMLDataModel().getElementProperty("GROUP_CHILDRENS")!=null){
						java.util.ArrayList arrayList=(java.util.ArrayList)((UMLModel)models.get(i)).getUMLDataModel().getElementProperty("GROUP_CHILDRENS");
						for(int j=0;j<arrayList.size();j++){
							if(arrayList.get(j)==this){
								arrayList.remove(j);    						   
							}
						}
						((UMLModel)models.get(i)).getUMLDataModel().setElementProperty("GROUP_CHILDRENS", arrayList);
					}
					this.getUMLDataModel().removeProperty("GROUP_PARENTS");
				}
			}
		}
		//PKY 08052101 E 컨테이너에서 그룹으로 변경
	}

	/**
	 * Sets the value of a given property with the value supplied. Also fires a property change if necessary.
	 * @param id  Name of the parameter to be changed.
	 * @param value  Value to be set to the given parameter.
	 */
	public void setPropertyValue(Object id, Object value) {
		//PKY 08082201 S 팀 프로젝트 읽기전용일때 수정불가능하도록 작업
		//111206 SDM S - 불필요한 LOG작성 삭제(주석처리)
		System.out.println("[setPropertyValue:" + id + "] " + null);
//		ProjectManager.getInstance().writeLog("setPropertyValue"+id, null);
		//111206 SDM E - 불필요한 LOG작성 삭제(주석처리)
		try{
		if(this.ID_ATTR_HIDDEN.equals(id)){
			this.setShowAttr(value.toString());
			return;
		}
		else if(this.ID_OPER_HIDDEN.equals(id)){
			this.setShowOper(value.toString());
			return;
		}
		if(ProjectManager.getInstance()!=null && ProjectManager.getInstance().getSelectPropertyUMLElementModel()!=null)					
			if(!ProjectManager.getInstance().isItemEnable(ProjectManager.getInstance().getSelectPropertyUMLElementModel()))
				return;
		//PKY 08082201 E 팀 프로젝트 읽기전용일때 수정불가능하도록 작업
		
		if (ID_SIZE.equals(id))
			setSize((Dimension)value);
		else if (ID_LOCATION.equals(id))
			setLocation((Point)value);
		else if (ID_NAME.equals(id)) {
			if(this instanceof ComponentLibModel && this.getName()!=null && !this.getName().trim().equals("")){
				ComponentLibModel clm = (ComponentLibModel)this;
				if(clm.getClassModel().getElementLabelModelName().isReadOnly){
					return;
				}
			}
			//110823 SDM S 프로퍼티에서 포트명 수정시 기록 남김
			if(this instanceof PortModel){
				PortModel pm = (PortModel)this;
				ComponentModel cm = (ComponentModel)pm.getPortContainerModel();
				
				if(cm instanceof AtomicComponentModel){
					AtomicComponentModel am = null;
					if(((AtomicComponentModel) cm).getCoreUMLTreeModel()!=null){
						am = ((AtomicComponentModel) cm).getCoreDiagramCmpModel();
					}
					if(!getName().equals(value)){
						Iterator <String> it = am.getReNameList().iterator();
						boolean bool = false;
						String str1 = new String();
						
						while(it.hasNext()){
							str1 = it.next();
							StringTokenizer stk = new StringTokenizer(str1,",");
							while(stk.hasMoreTokens()){
								String str2 = stk.nextToken();
								if(str2.equals(getName())){
									bool = true;
									break;
								}
							}
						}
						
						if(bool){	//이전에 포트명 변경이 있었을 때
							String str2 = str1 + value + ",";
							am.setReNameList(str1, str2);
						}
						else{	//포트명 변경이 없었을 때
							String str = getName() + "," + value + ",";
							am.setReNameList(str);
						}
					}
				}
			}
			//110823 SDM E 프로퍼티에서 포트명 수정시 기록 남김
			
			setName((String)value);
			 ProjectManager.getInstance().getModelBrowser().searchComponentDiagramIdModel(this.getUMLDataModel().getId());
				for(int i=0;i<ProjectManager.getInstance().getSearchModel().size();i++){
					UMLModel port = (UMLModel)ProjectManager.getInstance().getSearchModel().get(i);
					if(this != port)
					port.setName((String)value);
				}
				
			//111206 SDM S - 로봇이름 변경시 다이어그램 새로고침 되도록 추가
			if(this instanceof NodePackageModel){
				this.allRefresh();
			}
			//111206 SDM E - 로봇이름 변경시 다이어그램 새로고침 되도록 추가
		}
		else if (this.ID_INSTANCE.equals(id)) {
			this.setInstance((String)value);
		}
		else if (ID_COLOR.equals(id)) {
			Color newColor = new Color(null, (RGB)value);
			this.setBackGroundColor(newColor);
		}
		else if (this.ID_DESCRIPTION.equals(id)) {
			this.description = (String)value;
		}
		else if (this.ID_FONT.equals(id)) {
			Font newFont = new Font(null, (FontData)value);
			this.font = newFont;
		}
		else if (this.ID_STEREOTYPE.equals(id)) {
			if(this instanceof ComponentLibModel && this.getName()!=null && !this.getName().trim().equals("")){
				ComponentLibModel clm = (ComponentLibModel)this;
				if(clm.getClassModel().getElementLabelModelName().isReadOnly){
					return;
				}
				
				String streo = (String)value;
			}
			this.setStereotype((String)value);
		}
		else if (this.ID_STEREOTYPE.equals(id)) {
			if(this instanceof ComponentLibModel && this.getName()!=null && !this.getName().trim().equals("")){
				ComponentLibModel clm = (ComponentLibModel)this;
				if(clm.getClassModel().getElementLabelModelName().isReadOnly){
					return;
				}
			}
			//2008041604PKY S
			if(this.getStereotype()!=null||!this.getStereotype().equals(""))
				this.setStero(true);	
			//2008041604PKY E		
			this.setStereotype((String)value);
		}
		else if (this.ID_ACTIVE.equals(id)) {
			this.setActive(value.toString());
		}
		else if (this.ID_REFERENCE.equals(id)) {
			this.setReference(value.toString());
		}
		else if (this.ID_MULTI.equals(id)) {
			this.setMultiplicity((String)value);
		}
		else if (this.ID_OBJECT_STATE.equals(id)) {
			this.setObjectState((String)value);
		}

		else if (this.ID_READONLY.equals(id)) {
			this.setReadOnly(value.toString());//PKY 08060201 S 엑티비티 isReadOnly 입력안됬던 문제 수정
		}
		else if (this.ID_PRECONDITION.equals(id)) {
			this.setPreCondition(value.toString());
		}
		else if (this.ID_POSTCONDITION.equals(id)) {
			this.setPostCondition(value.toString());
		}
		else if (this.ID_SINGLEEXECUTION.equals(id)) {
			this.setSingleExecution(value.toString());
		}
		else if (this.ID_PARAMETERNAME.equals(id)) {
			this.setParameterName((String)value);
		}

		else if (this.ID_EFFECT.equals(id)) {
			this.setEffect(value.toString());
		}
		else if (this.ID_CONTEXT.equals(id)) {
			this.setContext((String)value);
		}

		else if (this.ID_SIMPLE.equals(id)) {
			this.setSimple(String.valueOf(value));//PKY 08061801 S State 프로퍼티 제대로 변경안되는문제 수정
		}
		else if (this.ID_SUBMACHINESTATE.equals(id)) {
			this.setSubmachineState(String.valueOf(value));//PKY 08061801 S State 프로퍼티 제대로 변경안되는문제 수정
		}
		else if (this.ID_ORTHOGONAL.equals(id)) {
			this.setOrthogonal(String.valueOf(value));//PKY 08061801 S State 프로퍼티 제대로 변경안되는문제 수정
		}
		else if (this.ID_COMPOSITE.equals(id)) {
			this.setComposite(String.valueOf(value));	//PKY 08061801 S State 프로퍼티 제대로 변경안되는문제 수정
		}
		else if (this.ID_INDIRECTLYINSTANTIATED.equals(id)) {
			this.setIndirectlyInstantiated((String)value);
		}
		else if (this.ID_ACTORIMAGE.equals(id)) {
			this.setActorImage((String)value);	
		}else if (this.ID_SCOPE.equals(id)){//PKY 08060201 S Class Scope저장안됨
			this.getUMLDataModel().setElementProperty(this.ID_SCOPE,String.valueOf(value));
		}
		else if (this.ID_MAX.equals(id)){//PKY 08060201 S Class Scope저장안됨
			this.setMax(String.valueOf(value));
		}//20080721IJS
		else if (this.ID_REQ_ID.equals(id)){
			this.setReqID(String.valueOf(value));
		}
		//PKY 08060201 E Class Scope저장안됨
		//PKY 08081801 S 오퍼레이션 어트리뷰트 숨길수 있는 기능
		else if(this.ID_ATTR_HIDDEN.equals(id)){
			this.setShowAttr(value.toString());
		}
		else if(this.ID_OPER_HIDDEN.equals(id)){
			this.setShowOper(value.toString());
		}
		//PKY 08081801 E 오퍼레이션 어트리뷰트 숨길수 있는 기능
		//PKY 08090904 S 
		else if(this.ID_FILENAME.equals(id)){
			this.setFileName(value.toString());
		}
		else if(this.ID_VERSION.equals(id)){
			if(this instanceof ComponentLibModel && this.getVersion()!=null && !this.getVersion().trim().equals("")){
				ComponentLibModel clm = (ComponentLibModel)this;
				if(clm.getClassModel().getElementLabelModelName().isReadOnly){
					return;
				}
			}
			this.setVersion(value.toString());
		}
		else if(this.ID_IP.equals(id)){
			this.setIP(value.toString());
		}
		else if(this.ID_NODE.equals(id)){
			this.setNode(value.toString());
			this.getUMLTreeModel().setName(value.toString());
			ProjectManager.getInstance().getModelBrowser().refresh(this.getUMLTreeModel().getParent());
			//WJH 090820 S 
			RootTreeModel root = ProjectManager.getInstance().getModelBrowser().getRootTree();
			UMLTreeModel[] childs = root.getChildren();
			for(int i=0; i<childs.length; i++){
				UMLTreeModel model = childs[i];
//				if(model != null && model.getName().equals("Application")){
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
//				}
			}
		//WJH 090820 E
	
		}
		else if(this.ID_PORT.equals(id)){
			this.setPort(value.toString());
		}
		else if(this.ID_OS.equals(id)){
			this.setOs(value.toString());
		}
		else if(this.ID_OS_VERSION.equals(id)){
			this.setOsVersion(value.toString());
		}
		else if(this.ID_CPU.equals(id)){
			this.setCpu(value.toString());
		}
		else if(this.ID_LIBRARY_TYPE.equals(id)){
			this.setLibType(value.toString());
		}
		else if(this.ID_IMPL_LANGUAGE.equals(id)){
			this.setImplLang(value.toString());
		}
		else if(this.ID_COMPILER.equals(id)){
			this.setCompiler(value.toString());
		}
		//atomic
		
		else if(IAtomicComponentProperty.PROPERTY_COMPANY_ADDRESS.equals(id)){
			this.setCompAddressProp(value.toString());
		}
		else if(IAtomicComponentProperty.PROPERTY_COMPANY_HOMEPAGE.equals(id)){
			this.setcompHomepageProp(value.toString());
		}
		else if(IAtomicComponentProperty.PROPERTY_COMPANY_PHONE.equals(id)){
			this.setcompPhomeProp(value.toString());
		}
		else if(IAtomicComponentProperty.PROPERTY_COMPANY_NAME.equals(id)){
			this.setcompNameProp(value.toString());
		}
		else if(IAtomicComponentProperty.PROPERTY_LICENSE_POLICY.equals(id)){
			this.setlicensePolicyProp(value.toString());
		}
		else if(IAtomicComponentProperty.PROPERTY_DOMAIN.equals(id)){
			this.setexeEnvDomainProp(value.toString());
		}
		else if(IAtomicComponentProperty.PROPERTY_ROBOT_TYPE.equals(id)){
			this.setexeEnvRobotTypeProp(value.toString());
		}
			else if(IAtomicComponentProperty.PROPERTY_EXE_PERIOD.equals(id)){
			this.setexePeriodProp(value.toString());
		}
		else if(IAtomicComponentProperty.PROPERTY_EXE_PRIORITY.equals(id)){
			this.setexePriorityProp(value.toString());
		}
		else if(IAtomicComponentProperty.PROPERTY_EXE_INSTANCE_TYPE.equals(id)){
			this.setexeInstanceTypeProp(value.toString());
		}
		else if(IAtomicComponentProperty.PROPERTY_EXE_LIFECYCLE_TYPE.equals(id)){
			this.setexeexeLifecycleTypeProp(value.toString());
		}
		else if(IAtomicComponentProperty.PROPERTY_EXE_TYPE.equals(id)){
			this.setexeTypeProp(value.toString());
		}
		else if(IAtomicComponentProperty.PROPERTY_COMPILER.equals(id)){
			this.setcompilerProp(value.toString());
		}
		else if(IAtomicComponentProperty.PROPERTY_IMPL_LANGUAGE.equals(id)){
			this.setimplementLangProp(value.toString());
		}
		else if(IAtomicComponentProperty.PROPERTY_LIBRARY_NAME.equals(id)){
			this.setlibNameProp(value.toString());
		}
			else if(IAtomicComponentProperty.PROPERTY_LIBRARY_TYPE.equals(id)){
			this.setlibTypeProp(value.toString());
		}
		
		
			else if("Service Type File Name".equals(id)){
				 this.setServiceTypeFileName(value.toString());
			}
			else if("Data Type File Name".equals(id)){
				 this.setDataTypeFileName(value.toString());
			}
			else if("Component Property Name".equals(id)){
				 this.setPropertyName(value.toString());
			}
			else if("Component Property Type".equals(id)){
				 this.setDataType(value.toString());
			}
				else if("Component Property Default Value".equals(id)){
				 this.setDefaultValue(value.toString());
			}
			else if("Source Folder".equals(id)){
				this.setSrcFolder(value.toString());
			}
			else if("Component Folder".equals(id)){
				this.setCmpFolder(value.toString());
			}
		
			//111102 SDM S - PRIORITY 항목 추가
			else if (ID_PRIORITY.equals(id)) {
				this.setexePriorityProp(value.toString());
			}
			//111102 SDM E - PRIORITY 항목 추가
		}
		catch(Exception e){
			ProjectManager.getInstance().writeLog("setPropertyValue", e);
		}
		//PKY 08090904 E 

	}

	public void setSize(Dimension d) {
		if (size.equals(d))
			return;
		//2008042301PKY S
		if(this instanceof FinalPackageModel){
			if(d.height<62){
				d.height=62;
			}
			if(d.width<100){
				d.width=100;
			}
		}else if(!(this instanceof LineTextModel)){//PKY 08091603 S
			//PKY 08062601 S 시퀀스다이어그램 linetextModel이 생겨서 이동시에 불편한 문제 수정
			if(!(this instanceof LineTextModel)){
				if(d.height<25){
					d.height=30;
				}
				if(d.width<30){
					d.width=25;
				}
				//PKY 08062601 E 시퀀스다이어그램 linetextModel이 생겨서 이동시에 불편한 문제 수정
			}
		}

		//2008042301PKY E
		size = d;
		firePropertyChange(this.ID_SIZE, null, size); //$NON-NLS-1$
		

	}

	public void setObjectState(String p){
		uMLDataModel.setProperty(this.ID_OBJECT_STATE, p);
		firePropertyChange(ID_OBJECT_STATE, null, null); //$NON-NLS-1$
	}

	public String getObjectState(){
		if(uMLDataModel.getProperty(this.ID_OBJECT_STATE)==null){
			this.setObjectState("");
		}
		return uMLDataModel.getProperty(this.ID_OBJECT_STATE);
	}


	public void setEffect(String p){
		uMLDataModel.setProperty(this.ID_EFFECT, p);

	}

	public String getEffect(){
		if(uMLDataModel.getProperty(this.ID_EFFECT)==null){
			this.setEffect("");
		}
		return uMLDataModel.getProperty(this.ID_EFFECT);
	}

	public void setIndirectlyInstantiated(String p){
		uMLDataModel.setProperty(this.ID_INDIRECTLYINSTANTIATED, p);

	}

	public String getIndirectlyInstantiated(){
		if(uMLDataModel.getProperty(this.ID_INDIRECTLYINSTANTIATED)==null){
			this.setIndirectlyInstantiated("");
		}
		return uMLDataModel.getProperty(this.ID_INDIRECTLYINSTANTIATED);
	}




	public void setContext(String p){
		uMLDataModel.setProperty(this.ID_CONTEXT, p);

	}

	public String getContext(){
		if(uMLDataModel.getProperty(this.ID_CONTEXT)==null){
			this.setContext("");
		}
		return uMLDataModel.getProperty(this.ID_CONTEXT);
	}

	public void setFileName(String p){
		uMLDataModel.setProperty(this.ID_FILENAME, p);

	}

	public String getFileName(){
		if(uMLDataModel.getProperty(this.ID_FILENAME)==null){
			this.setFileName("");
		}
		return uMLDataModel.getProperty(this.ID_FILENAME);
	}
	
	public String getId(){
		if(uMLDataModel.getProperty(this.ID_ID)==null
				|| uMLDataModel.getProperty(this.ID_ID).trim().equals("")){
			this.setId(uMLDataModel.getCmpid());
		}
		return uMLDataModel.getProperty(this.ID_ID);
	}
	
	public void setId(String p){
		uMLDataModel.setProperty(this.ID_ID, p);
		uMLDataModel.setCmpid(p);

	}
	
	public String getVersion(){
		if(uMLDataModel.getProperty(this.ID_VERSION)==null){
			this.setVersion("1.0");
		}
		return uMLDataModel.getProperty(this.ID_VERSION);
	}
	
	
	
	public void setVersion(String p){
		uMLDataModel.setProperty(this.ID_VERSION, p);

	}
	
	public String getOs(){
		if(uMLDataModel.getProperty(this.ID_OS)==null){
			this.setOs("");
		}
		return uMLDataModel.getProperty(this.ID_OS);
	}
	
	public void setOs(String os){
		uMLDataModel.setProperty(this.ID_OS, os);
		firePropertyChange(this.ID_ACTORIMAGE, null, null);

		
	}
	
	public String getOsVersion(){
		if(uMLDataModel.getProperty(this.ID_OS_VERSION)==null){
			this.setOsVersion("");
		}
		return uMLDataModel.getProperty(this.ID_OS_VERSION);
	}
	
	public void setOsVersion(String osV){
		uMLDataModel.setProperty(this.ID_OS_VERSION, osV);
		firePropertyChange(this.ID_ACTORIMAGE, null, null);

		
	}
	
	public String getLibType(){
		if(uMLDataModel.getProperty(this.ID_LIBRARY_TYPE)==null){
			this.setLibType("");
		}
		return uMLDataModel.getProperty(this.ID_LIBRARY_TYPE);
	}
	
	public void setLibType(String libType){
		uMLDataModel.setProperty(this.ID_LIBRARY_TYPE, libType);
		
	}
	
	public String getImplLang(){
		if(uMLDataModel.getProperty(this.ID_IMPL_LANGUAGE)==null){
			this.setImplLang("");
		}
		return uMLDataModel.getProperty(this.ID_IMPL_LANGUAGE);
	}
	
	public void setImplLang(String impllang){
		uMLDataModel.setProperty(this.ID_IMPL_LANGUAGE, impllang);
		
	}
	
	public String getCompiler(){
		if(uMLDataModel.getProperty(this.ID_COMPILER)==null){
			this.setCompiler("");
		}
		return uMLDataModel.getProperty(this.ID_COMPILER);
	}
	
	public void setCompiler(String compiler){
		uMLDataModel.setProperty(this.ID_COMPILER, compiler);
		
	}
	
	public String getCpu(){
		if(uMLDataModel.getProperty(this.ID_CPU)==null){
			this.setCpu("");
		}
		return uMLDataModel.getProperty(this.ID_CPU);
	}
	
	public void setCpu(String cpu){
		uMLDataModel.setProperty(this.ID_CPU, cpu);
		firePropertyChange(this.ID_ACTORIMAGE, null, null);
		
	}
	
	

	public void setIP(String p){
		try{
			if(p!=null && !p.trim().equals("")){
				InetAddress addr = InetAddress.getByName(p); 
				uMLDataModel.setProperty(this.ID_IP, p);
			}
			else{
				uMLDataModel.setProperty(this.ID_IP, p);
			}
			
		}
		catch(Exception e){
//			e.printStackTrace();
//			MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_WARNING);
//			 dialog.setText("Error");
//			 dialog.setMessage("invalid ip ["+p+"]");
//			 dialog.open();
			ProjectManager.getInstance().getConsole().addMessage(this.getName()+" Component -> invalid ip ["+p+"]");
			
		}
		
	}
	
	public String getNode(){
//		if(uMLDataModel.getProperty(this.ID_IP)==null){
//			this.setVersion("1.0");
//		}
		if(uMLDataModel.getProperty(this.ID_NODE)==null){
			this.setNode("");
		}
		return uMLDataModel.getProperty(this.ID_NODE);
	}
	
	public void setNode(String p){
		uMLDataModel.setProperty(this.ID_NODE, p);
		this.allRefresh();	//111206 SDM - 노드나 로봇 이름변화에 따른 다이어그램 전체 새로고침.
	}
	
	public String getPort1(){
//		if(uMLDataModel.getProperty(this.ID_IP)==null){
//			this.setVersion("1.0");
		if(uMLDataModel.getProperty(this.ID_PORT)==null){
			this.setPort("2003");
		}
//		}
		return uMLDataModel.getProperty(this.ID_PORT);
	}
	
	public void setPort(String p){
		uMLDataModel.setProperty(this.ID_PORT, p);

	}
	
	public String getIP(){
		if(uMLDataModel.getProperty(this.ID_IP)==null){
			this.setIP("127.0.0.1");
		}
		return uMLDataModel.getProperty(this.ID_IP);
	}
	
	
	public void setMax(String p){
		uMLDataModel.setProperty(this.ID_MAX, p);
		firePropertyChange(ID_MAX, null, null); //$NON-NLS-1$
	}
	//20080721IJS
	public void setReqID(String p){
		uMLDataModel.setProperty(this.ID_REQ_ID, p);
//		firePropertyChange(ID_REQ_ID, null, null); //$NON-NLS-1$
	}
	public String getMax(){
		if(uMLDataModel.getProperty(this.ID_MAX)==null){
			this.setMax("100");
		}
		return uMLDataModel.getProperty(this.ID_MAX);
	}
	//20080721IJS
	public String getReqID(){
		if(uMLDataModel.getProperty(this.ID_REQ_ID)==null){
			this.setReqID("");
		}
		return uMLDataModel.getProperty(this.ID_REQ_ID);
	}

	public void setPreCondition(String p){
		uMLDataModel.setProperty(this.ID_PRECONDITION, p);
		firePropertyChange(ID_PRECONDITION, null, null); //$NON-NLS-1$
	}

	public String getPreCondition(){
		if(uMLDataModel.getProperty(this.ID_PRECONDITION)==null){
			this.setPreCondition("");
		}
		return uMLDataModel.getProperty(this.ID_PRECONDITION);
	}

	public void setPostCondition(String p){
		uMLDataModel.setProperty(this.ID_POSTCONDITION, p);
		firePropertyChange(ID_POSTCONDITION, null, null); //$NON-NLS-1$
	}

	public String getPostCondition(){
		if(uMLDataModel.getProperty(this.ID_POSTCONDITION)==null){
			this.setPostCondition("");
		}
		return uMLDataModel.getProperty(this.ID_POSTCONDITION);
	}

	public void setParameterName(String p){
		uMLDataModel.setProperty(this.ID_PARAMETERNAME, p);
		firePropertyChange(ID_PARAMETERNAME, null, null); //$NON-NLS-1$
	}

	public String getParameterName(){
		if(uMLDataModel.getProperty(this.ID_PARAMETERNAME)==null){
			this.setParameterName("");
		}

		return uMLDataModel.getProperty(this.ID_PARAMETERNAME);
	}

	public void setReadOnly(String p){
		uMLDataModel.setProperty(this.ID_READONLY , p);
	}

	public Integer getReadOnly(){
		if(uMLDataModel.getProperty(this.ID_READONLY)==null){
			this.setReadOnly("1");
		}
		return Integer.valueOf(uMLDataModel.getProperty(this.ID_READONLY));
	}

	public void setSingleExecution(String p){
		uMLDataModel.setProperty(this.ID_SINGLEEXECUTION , p);
	}

	public Integer getSingleExecution(){
		if(uMLDataModel.getProperty(this.ID_SINGLEEXECUTION)==null){
			this.setSingleExecution("1");
		}
		return Integer.valueOf(uMLDataModel.getProperty(this.ID_SINGLEEXECUTION));
	}



	public void setSimple(String p){
		uMLDataModel.setProperty(this.ID_SIMPLE , p);
	}

	public Integer getSimple(){
		if(uMLDataModel.getProperty(this.ID_SIMPLE)==null){
			this.setSimple("1");
		}
		return Integer.valueOf(uMLDataModel.getProperty(this.ID_SIMPLE));
	}

	public void setSubmachineState(String p){
		uMLDataModel.setProperty(this.ID_SUBMACHINESTATE , p);
	}

	public Integer getSubmachineState(){
		if(uMLDataModel.getProperty(this.ID_SUBMACHINESTATE)==null){
			this.setSubmachineState("1");
		}
		return Integer.valueOf(uMLDataModel.getProperty(this.ID_SUBMACHINESTATE));
	}


	public void setOrthogonal(String p){
		uMLDataModel.setProperty(this.ID_ORTHOGONAL , p);
	}

	public Integer getOrthogonal(){
		if(uMLDataModel.getProperty(this.ID_ORTHOGONAL)==null){
			this.setOrthogonal("1");
		}
		return Integer.valueOf(uMLDataModel.getProperty(this.ID_ORTHOGONAL));
	}
	//------------atomic
	
	
	
	public void setCompAddressProp(String p){
		if(this instanceof AtomicComponentModel){
			AtomicComponentModel acm = (AtomicComponentModel)this;
			 UMLTreeModel ut = acm.getCoreUMLTreeModel();
			 if(ut!=null){
				 AtomicComponentModel am = (AtomicComponentModel)ut.getRefModel();
				 am.setCompAddressProp(p);
				 uMLDataModel.setProperty(IAtomicComponentProperty.PROPERTY_COMPANY_ADDRESS , p);
			 }
		}
		
		uMLDataModel.setProperty(IAtomicComponentProperty.PROPERTY_COMPANY_ADDRESS , p);
	}

	public String getCompAddressProp(){
		if(this instanceof AtomicComponentModel){
			AtomicComponentModel acm = (AtomicComponentModel)this;
			 UMLTreeModel ut = acm.getCoreUMLTreeModel();
			 if(ut!=null){
				 AtomicComponentModel am = (AtomicComponentModel)ut.getRefModel();
				 return am.getCompAddressProp();
			 }
		}
		
		
		if(uMLDataModel.getProperty(IAtomicComponentProperty.PROPERTY_COMPANY_ADDRESS)==null){
			this.setCompAddressProp("");
		}
		
		return uMLDataModel.getProperty(IAtomicComponentProperty.PROPERTY_COMPANY_ADDRESS);
	}
	
	public void setcompHomepageProp(String p){
		if(this instanceof AtomicComponentModel){
			AtomicComponentModel acm = (AtomicComponentModel)this;
			 UMLTreeModel ut = acm.getCoreUMLTreeModel();
			 if(ut!=null){
				 AtomicComponentModel am = (AtomicComponentModel)ut.getRefModel();
				 am.setcompHomepageProp(p);
				 uMLDataModel.setProperty(IAtomicComponentProperty.PROPERTY_COMPANY_HOMEPAGE , p);
			 }
		}
		uMLDataModel.setProperty(IAtomicComponentProperty.PROPERTY_COMPANY_HOMEPAGE , p);
	}

	public String getcompHomepageProp(){
		
		if(this instanceof AtomicComponentModel){
			AtomicComponentModel acm = (AtomicComponentModel)this;
			 UMLTreeModel ut = acm.getCoreUMLTreeModel();
			 if(ut!=null){
				 AtomicComponentModel am = (AtomicComponentModel)ut.getRefModel();
				 return am.getcompHomepageProp();
			 }
		}
		if(uMLDataModel.getProperty(IAtomicComponentProperty.PROPERTY_COMPANY_HOMEPAGE)==null){
			this.setcompHomepageProp("");
		}
		
		return uMLDataModel.getProperty(IAtomicComponentProperty.PROPERTY_COMPANY_HOMEPAGE);
	}
	
	public void setcompNameProp(String p){
		if(this instanceof AtomicComponentModel){
			AtomicComponentModel acm = (AtomicComponentModel)this;
			 UMLTreeModel ut = acm.getCoreUMLTreeModel();
			 if(ut!=null){
				 AtomicComponentModel am = (AtomicComponentModel)ut.getRefModel();
				 am.setcompNameProp(p);
				 uMLDataModel.setProperty(IAtomicComponentProperty.PROPERTY_COMPANY_NAME , p);
			 }
		}
		uMLDataModel.setProperty(IAtomicComponentProperty.PROPERTY_COMPANY_NAME , p);
	}

	public String getcompNameProp(){
		if(this instanceof AtomicComponentModel){
			AtomicComponentModel acm = (AtomicComponentModel)this;
			 UMLTreeModel ut = acm.getCoreUMLTreeModel();
			 if(ut!=null){
				 AtomicComponentModel am = (AtomicComponentModel)ut.getRefModel();
				 return am.getcompNameProp();
			 }
		}
		if(uMLDataModel.getProperty(IAtomicComponentProperty.PROPERTY_COMPANY_NAME)==null){
			this.setcompNameProp("");
		}
		
		return uMLDataModel.getProperty(IAtomicComponentProperty.PROPERTY_COMPANY_NAME);
	}
	
	public void setcompPhomeProp(String p){
		if(this instanceof AtomicComponentModel){
			AtomicComponentModel acm = (AtomicComponentModel)this;
			 UMLTreeModel ut = acm.getCoreUMLTreeModel();
			 if(ut!=null){
				 AtomicComponentModel am = (AtomicComponentModel)ut.getRefModel();
				 am.setcompPhomeProp(p);
				 uMLDataModel.setProperty(IAtomicComponentProperty.PROPERTY_COMPANY_PHONE , p);
			 }
		}
		uMLDataModel.setProperty(IAtomicComponentProperty.PROPERTY_COMPANY_PHONE , p);
	}

	public String getcompPhomeProp(){
		if(this instanceof AtomicComponentModel){
			AtomicComponentModel acm = (AtomicComponentModel)this;
			 UMLTreeModel ut = acm.getCoreUMLTreeModel();
			 if(ut!=null){
				 AtomicComponentModel am = (AtomicComponentModel)ut.getRefModel();
				 return am.getcompPhomeProp();
			 }
		}
		if(uMLDataModel.getProperty(IAtomicComponentProperty.PROPERTY_COMPANY_PHONE)==null){
			this.setcompPhomeProp("");
		}
		return uMLDataModel.getProperty(IAtomicComponentProperty.PROPERTY_COMPANY_PHONE);
	}
	
	public void setlicensePolicyProp(String p){
		if(this instanceof AtomicComponentModel){
			AtomicComponentModel acm = (AtomicComponentModel)this;
			 UMLTreeModel ut = acm.getCoreUMLTreeModel();
			 if(ut!=null){
				 AtomicComponentModel am = (AtomicComponentModel)ut.getRefModel();
				 am.setlicensePolicyProp(p);
				 uMLDataModel.setProperty(IAtomicComponentProperty.PROPERTY_LICENSE_POLICY , p);
			 }
		}
		uMLDataModel.setProperty(IAtomicComponentProperty.PROPERTY_LICENSE_POLICY , p);
	}
	
	public String getgetlicensePolicyString(){
		Integer it = this.getlicensePolicyProp();
		return IAtomicComponentProperty.PROPERTY_LICENSE_POLICY_ARRAY[it.intValue()];
		
	}

	public Integer getlicensePolicyProp(){
		if(this instanceof AtomicComponentModel){
			AtomicComponentModel acm = (AtomicComponentModel)this;
			 UMLTreeModel ut = acm.getCoreUMLTreeModel();
			 if(ut!=null){
				 AtomicComponentModel am = (AtomicComponentModel)ut.getRefModel();
				 return am.getlicensePolicyProp();
			 }
		}
		if(uMLDataModel.getProperty(IAtomicComponentProperty.PROPERTY_LICENSE_POLICY)==null){
			this.setlicensePolicyProp("0");
		}
		
		return new Integer(uMLDataModel.getProperty(IAtomicComponentProperty.PROPERTY_LICENSE_POLICY));
	}
	
	public String getPropertyName(){
		if(this instanceof AtomicComponentModel){
			AtomicComponentModel acm = (AtomicComponentModel)this;
			 UMLTreeModel ut = acm.getCoreUMLTreeModel();
			 if(ut!=null){
				 AtomicComponentModel am = (AtomicComponentModel)ut.getRefModel();
				 return am.getPropertyName();
			 }
		}
		if(uMLDataModel.getProperty("PropertyName")==null){
			this.setPropertyName("");
		}
		return uMLDataModel.getProperty("PropertyName");
	}
	public void setPropertyName(String propertyName) {
		if(this instanceof AtomicComponentModel){
			AtomicComponentModel acm = (AtomicComponentModel)this;
			 UMLTreeModel ut = acm.getCoreUMLTreeModel();
			 if(ut!=null){
				 AtomicComponentModel am = (AtomicComponentModel)ut.getRefModel();
				 am.setPropertyName(propertyName);
			 }
		}

		uMLDataModel.setProperty("PropertyName", propertyName);
	}
	
	public String getDataType(){
		if(this instanceof AtomicComponentModel){
			AtomicComponentModel acm = (AtomicComponentModel)this;
			 UMLTreeModel ut = acm.getCoreUMLTreeModel();
			 if(ut!=null){
				 AtomicComponentModel am = (AtomicComponentModel)ut.getRefModel();
				 return am.getDataType();
			 }
		}
		if(uMLDataModel.getProperty("DataType")==null){
			this.setDataType("");
		}
		return uMLDataModel.getProperty("DataType");
	}
	public void setDataType(String dataType) {
		if(this instanceof AtomicComponentModel){
			AtomicComponentModel acm = (AtomicComponentModel)this;
			 UMLTreeModel ut = acm.getCoreUMLTreeModel();
			 if(ut!=null){
				 AtomicComponentModel am = (AtomicComponentModel)ut.getRefModel();
				 am.setDataType(dataType);
			 }
		}

		uMLDataModel.setProperty("DataType", dataType);
	}
	
	
	public String getSrcFolder(){
		if(uMLDataModel.getProperty("SrcFolder")==null){
			this.setSrcFolder("");
		}
		return uMLDataModel.getProperty("SrcFolder");
	}
	public void setSrcFolder(String srcFolder) {

		uMLDataModel.setProperty("SrcFolder", srcFolder);
		ProjectManager.getInstance().setProjectPath(srcFolder);
	}
	
	
	public String getCmpFolder(){
		if(this instanceof AtomicComponentModel){
			AtomicComponentModel acm = (AtomicComponentModel)this;
			UMLTreeModel acm1 = acm.getCoreUMLTreeModel();
			if(acm1!=null){
//				String str = uMLDataModel.getProperty("CmpFolder");
				
				return ((AtomicComponentModel)acm1.getRefModel()).getCmpFolder();
			}
			else{
				if(uMLDataModel.getProperty("CmpFolder")==null){
					this.setCmpFolder("");
				}
				String str = uMLDataModel.getProperty("CmpFolder");
				if(str==null || "".equals(str)){
					OPRoSExeEnvironmentElementModel opem = (OPRoSExeEnvironmentElementModel)acm.getClassModel().getUMLDataModel().getElementProperty("OPRoSExeEnvironmentElementModel");
					if(opem!=null)
					return opem.getexeCmpFolderProp();
				}
				return uMLDataModel.getProperty("CmpFolder");
			}
		}
		else{
			if(uMLDataModel.getProperty("CmpFolder")==null){
				this.setCmpFolder("");
			}
			return uMLDataModel.getProperty("CmpFolder");
		}
		
//		return "";
	}
	
	public void setCmpFolder(String srcFolder) {
		
		if(this instanceof AtomicComponentModel){
			AtomicComponentModel acm = (AtomicComponentModel)this;
//			acm.getClassModel().get
			UMLTreeModel acm1 = acm.getCoreUMLTreeModel();
//			uMLDataModel.setProperty("CmpFolder",srcFolder);
			OPRoSExeEnvironmentElementModel opem = (OPRoSExeEnvironmentElementModel)acm.getClassModel().getUMLDataModel().getElementProperty("OPRoSExeEnvironmentElementModel");
//			opem.set
			if(opem!=null)
			opem.setexeCmpFolderProp(srcFolder);
			if(acm1!=null){
				((AtomicComponentModel)acm1.getRefModel()).setCmpFolder(srcFolder);
			}
			else{
				
				uMLDataModel.setProperty("CmpFolder",srcFolder);
			}
			
		}
		else{
			uMLDataModel.setProperty("CmpFolder",srcFolder);	
		}
		
//		ProjectManager.getInstance().setProjectPath(srcFolder);
	}
	
	public String getDefaultValue(){
		if(uMLDataModel.getProperty("DefaultValue")==null){
			this.setDefaultValue("");
		}
		return uMLDataModel.getProperty("DefaultValue");
	}
	public void setDefaultValue(String defaultValue) {

		uMLDataModel.setProperty("DefaultValue", defaultValue);
	}
	
	
	
	public String getDataTypeFileName() {
		if(uMLDataModel.getProperty("DataTypeFileName")==null){
			this.setDataTypeFileName("");
		}
		return uMLDataModel.getProperty("DataTypeFileName");
	}
	public void setDataTypeFileName(String dataTypeFileName) {

		uMLDataModel.setProperty("DataTypeFileName", dataTypeFileName);
	}

	public String getServiceTypeFileName() {
		if(uMLDataModel.getProperty("ServiceTypeFileName")==null){
			this.setServiceTypeFileName("");
		}
		return uMLDataModel.getProperty("ServiceTypeFileName");
	}
	public void setServiceTypeFileName(String serviceTypeFileName) {
		uMLDataModel.setProperty("ServiceTypeFileName", serviceTypeFileName);
//		getListeners().firePropertyChange(PROPERTY_SERVICE_TYPE_FILENAME, oldValue, serviceTypeFileName);
	}
	
	public void setTempPath(String path) {
		uMLDataModel.setProperty("TempPath", path);
//		getListeners().firePropertyChange(PROPERTY_SERVICE_TYPE_FILENAME, oldValue, serviceTypeFileName);
	}
	
	public String getTempPath() {
		String path = uMLDataModel.getProperty("TempPath");
		return path;
//		getListeners().firePropertyChange(PROPERTY_SERVICE_TYPE_FILENAME, oldValue, serviceTypeFileName);
	}
	
	public void setexeCmpFolderProp(String p){
		uMLDataModel.setProperty("exeCmpFolderProp" , p);
	}
	
	public String getexeCmpFolderProp(){
		 return uMLDataModel.getProperty("exeCmpFolderProp");
	}
	
	public void setexeEnvDomainProp(String p){
//		if(this instanceof OPRoSExeEnvironmentElementModel){
//			uMLDataModel.setProperty(IAtomicComponentProperty.PROPERTY_DOMAIN , p);
//			OPRoSExeEnvironmentElementModel ope = (OPRoSExeEnvironmentElementModel)this;
//			Object obj = ope.getAcceptParentModel();
//			if(obj instanceof AtomicComponentModel){
//				AtomicComponentModel acm = (AtomicComponentModel)obj;
//			}
//			
//		}
		
		if(this instanceof AtomicComponentModel){
			AtomicComponentModel acm = (AtomicComponentModel)this;
			OPRoSExeEnvironmentElementModel opem = (OPRoSExeEnvironmentElementModel)acm.getClassModel().getUMLDataModel().getElementProperty("OPRoSExeEnvironmentElementModel");
			if(opem!=null){
				opem.setexeEnvDomainProp(p);
				uMLDataModel.setProperty(IAtomicComponentProperty.PROPERTY_DOMAIN , p);
			}
		}
		else
		uMLDataModel.setProperty(IAtomicComponentProperty.PROPERTY_DOMAIN , p);
	}

	public String getexeEnvDomainProp(){
		
		if(this instanceof AtomicComponentModel){
			AtomicComponentModel acm = (AtomicComponentModel)this;
			OPRoSExeEnvironmentElementModel opem = (OPRoSExeEnvironmentElementModel)acm.getClassModel().getUMLDataModel().getElementProperty("OPRoSExeEnvironmentElementModel");
			if(opem!=null){
				String ddd = opem.getexeEnvDomainProp();
				System.out.println("ddd:========================>"+ddd);
//				uMLDataModel.setProperty(IAtomicComponentProperty.PROPERTY_DOMAIN , String.valueOf(opem.getexeEnvDomainProp()));
				return opem.getexeEnvDomainProp();
			}
		}
		else{
		
			if(uMLDataModel.getProperty(IAtomicComponentProperty.PROPERTY_DOMAIN)==null){
				this.setexeEnvDomainProp("");
			}
			return uMLDataModel.getProperty(IAtomicComponentProperty.PROPERTY_DOMAIN);
		}
		return "";
	}
	
	public void setexeEnvRobotTypeProp(String p){
		if(this instanceof AtomicComponentModel){
			AtomicComponentModel acm = (AtomicComponentModel)this;
			OPRoSExeEnvironmentElementModel opem = (OPRoSExeEnvironmentElementModel)acm.getClassModel().getUMLDataModel().getElementProperty("OPRoSExeEnvironmentElementModel");
			if(opem!=null){
				opem.setexeEnvRobotTypeProp(p);
				uMLDataModel.setProperty(IAtomicComponentProperty.PROPERTY_ROBOT_TYPE , p);
			}
		}
		else
		uMLDataModel.setProperty(IAtomicComponentProperty.PROPERTY_ROBOT_TYPE , p);
	}

	public String getexeEnvRobotTypeProp(){
		if(this instanceof AtomicComponentModel){
			AtomicComponentModel acm = (AtomicComponentModel)this;
			OPRoSExeEnvironmentElementModel opem = (OPRoSExeEnvironmentElementModel)acm.getClassModel().getUMLDataModel().getElementProperty("OPRoSExeEnvironmentElementModel");
			if(opem!=null){
				String ddd = opem.getexeEnvRobotTypeProp();
				System.out.println("ddd:========================>"+ddd);
//				uMLDataModel.setProperty(IAtomicComponentProperty.PROPERTY_ROBOT_TYPE , String.valueOf(opem.getexeEnvRobotTypeProp()));
				return opem.getexeEnvRobotTypeProp();
			}
		}
		else{
		if(uMLDataModel.getProperty(IAtomicComponentProperty.PROPERTY_ROBOT_TYPE)==null){
			this.setexeEnvRobotTypeProp("");
		}
		return uMLDataModel.getProperty(IAtomicComponentProperty.PROPERTY_ROBOT_TYPE);
		}
		return "";
	}
	
	public void setexePeriodProp(String p){
		if(this instanceof AtomicComponentModel){
			AtomicComponentModel acm = (AtomicComponentModel)this;
			OPRoSExeSemanticsElementModel opem = (OPRoSExeSemanticsElementModel)acm.getClassModel().getUMLDataModel().getElementProperty("OPRoSExeSemanticsElementModel");
			if(opem!=null){
				opem.setexePeriodProp(p);
				uMLDataModel.setProperty(IAtomicComponentProperty.PROPERTY_EXE_PERIOD , p);
			}
		}
		else
		uMLDataModel.setProperty(IAtomicComponentProperty.PROPERTY_EXE_PERIOD , p);
	}

	public String getexePeriodProp(){
		if(this instanceof AtomicComponentModel){
			AtomicComponentModel acm = (AtomicComponentModel)this;
			OPRoSExeSemanticsElementModel opem = (OPRoSExeSemanticsElementModel)acm.getClassModel().getUMLDataModel().getElementProperty("OPRoSExeSemanticsElementModel");
			if(opem!=null){
//				String ddd = opem.getexePeriodProp();
//				System.out.println("ddd:========================>"+ddd);
//				uMLDataModel.setProperty(IAtomicComponentProperty.PROPERTY_EXE_PERIOD , String.valueOf(opem.getexePeriodProp()));
				return opem.getexePeriodProp();
			}
		}
		else{
		if(uMLDataModel.getProperty(IAtomicComponentProperty.PROPERTY_EXE_PERIOD)==null){
			this.setexePeriodProp("");
		}
		return uMLDataModel.getProperty(IAtomicComponentProperty.PROPERTY_EXE_PERIOD);
		}
		return "";
	}
	
	public void setexePriorityProp(String p){
		if(this instanceof AtomicComponentModel){
			AtomicComponentModel acm = (AtomicComponentModel)this;
			OPRoSExeSemanticsElementModel opem = (OPRoSExeSemanticsElementModel)acm.getClassModel().getUMLDataModel().getElementProperty("OPRoSExeSemanticsElementModel");
			if(opem!=null){
				opem.setexePriorityProp(p);
				uMLDataModel.setProperty(IAtomicComponentProperty.PROPERTY_EXE_PRIORITY , p);
			}
		}
		else
		uMLDataModel.setProperty(IAtomicComponentProperty.PROPERTY_EXE_PRIORITY , p);
	}

	public String getexePriorityProp(){
		if(this instanceof AtomicComponentModel){
			AtomicComponentModel acm = (AtomicComponentModel)this;
			OPRoSExeSemanticsElementModel opem = (OPRoSExeSemanticsElementModel)acm.getClassModel().getUMLDataModel().getElementProperty("OPRoSExeSemanticsElementModel");
			if(opem!=null){
//				String ddd = opem.getexePeriodProp();
//				System.out.println("ddd:========================>"+ddd);
//				uMLDataModel.setProperty(IAtomicComponentProperty.PROPERTY_EXE_PRIORITY , String.valueOf(opem.getexePriorityProp()));
				return opem.getexePriorityProp();
			}
		}
		else{
		
		if(uMLDataModel.getProperty(IAtomicComponentProperty.PROPERTY_EXE_PRIORITY)==null){
			this.setexePriorityProp("");
		}
		return uMLDataModel.getProperty(IAtomicComponentProperty.PROPERTY_EXE_PRIORITY);
		}
		return "";
	}
	
	public void setexeInstanceTypeProp(String p){
		if(this instanceof AtomicComponentModel){
			AtomicComponentModel acm = (AtomicComponentModel)this;
			OPRoSExeSemanticsElementModel opem = (OPRoSExeSemanticsElementModel)acm.getClassModel().getUMLDataModel().getElementProperty("OPRoSExeSemanticsElementModel");
			if(opem!=null){
				opem.setexeInstanceTypeProp(p);
				uMLDataModel.setProperty(IAtomicComponentProperty.PROPERTY_EXE_INSTANCE_TYPE , p);
			}
		}
		else
		uMLDataModel.setProperty(IAtomicComponentProperty.PROPERTY_EXE_INSTANCE_TYPE , p);
	}
	public String getexeInstanceTypeString(){
		Integer it = this.getexeInstanceTypeProp();
		return IAtomicComponentProperty.PROPERTY_EXE_INSTANCE_TYPE_ARRAY[it.intValue()];
	}

	public Integer getexeInstanceTypeProp(){
		if(this instanceof AtomicComponentModel){
			AtomicComponentModel acm = (AtomicComponentModel)this;
			OPRoSExeSemanticsElementModel opem = (OPRoSExeSemanticsElementModel)acm.getClassModel().getUMLDataModel().getElementProperty("OPRoSExeSemanticsElementModel");
			if(opem!=null){
//				String ddd = opem.getexePeriodProp();
//				System.out.println("ddd:========================>"+ddd);
//				uMLDataModel.setProperty(IAtomicComponentProperty.PROPERTY_EXE_INSTANCE_TYPE , String.valueOf(opem.getexeInstanceTypeProp()));
				return opem.getexeInstanceTypeProp();
			}
		}
		else{
		if(uMLDataModel.getProperty(IAtomicComponentProperty.PROPERTY_EXE_INSTANCE_TYPE)==null){
			this.setexeInstanceTypeProp("0");
		}
		return new Integer(uMLDataModel.getProperty(IAtomicComponentProperty.PROPERTY_EXE_INSTANCE_TYPE));
		}
		return new Integer(0);
	}
	
	public void setexeexeLifecycleTypeProp(String p){
		if(this instanceof AtomicComponentModel){
			AtomicComponentModel acm = (AtomicComponentModel)this;
			OPRoSExeSemanticsElementModel opem = (OPRoSExeSemanticsElementModel)acm.getClassModel().getUMLDataModel().getElementProperty("OPRoSExeSemanticsElementModel");
			if(opem!=null){
				opem.setexeexeLifecycleTypeProp(p);
				uMLDataModel.setProperty(IAtomicComponentProperty.PROPERTY_EXE_LIFECYCLE_TYPE , p);
			}
		}
		else
		uMLDataModel.setProperty(IAtomicComponentProperty.PROPERTY_EXE_LIFECYCLE_TYPE , p);
	}
	
	public String getexeLifecycleTypeString(){
		Integer it = this.getexeLifecycleTypeProp();
		return IAtomicComponentProperty.PROPERTY_EXE_LIFECYCLE_TYPE_ARRAY[it.intValue()];
	}

	public Integer getexeLifecycleTypeProp(){
		if(this instanceof AtomicComponentModel){
			AtomicComponentModel acm = (AtomicComponentModel)this;
			OPRoSExeSemanticsElementModel opem = (OPRoSExeSemanticsElementModel)acm.getClassModel().getUMLDataModel().getElementProperty("OPRoSExeSemanticsElementModel");
			if(opem!=null){
//				String ddd = opem.getexePeriodProp();
//				System.out.println("ddd:========================>"+ddd);
//				uMLDataModel.setProperty(IAtomicComponentProperty.PROPERTY_EXE_LIFECYCLE_TYPE , String.valueOf(opem.getexeLifecycleTypeProp()));
				return new Integer(opem.getexeLifecycleTypeProp());
			}
		}
		else{
		if(uMLDataModel.getProperty(IAtomicComponentProperty.PROPERTY_EXE_LIFECYCLE_TYPE)==null){
			this.setexeexeLifecycleTypeProp("0");
		}
		return new Integer(uMLDataModel.getProperty(IAtomicComponentProperty.PROPERTY_EXE_LIFECYCLE_TYPE));
		}
		return  new Integer(0);
	}
	
	public void setexeTypeProp(String p){
		if(this instanceof AtomicComponentModel){
			AtomicComponentModel acm = (AtomicComponentModel)this;
			OPRoSExeSemanticsElementModel opem = (OPRoSExeSemanticsElementModel)acm.getClassModel().getUMLDataModel().getElementProperty("OPRoSExeSemanticsElementModel");
			if(opem!=null){
				opem.setexeTypeProp(p);
				uMLDataModel.setProperty(IAtomicComponentProperty.PROPERTY_EXE_TYPE , p);
			}
		}
		else
		uMLDataModel.setProperty(IAtomicComponentProperty.PROPERTY_EXE_TYPE , p);
	}
	public String getexeTypeString(){
		Integer it = this.getexeTypeProp();
		return IAtomicComponentProperty.PROPERTY_EXE_TYPE_ARRAY[it.intValue()];
	}

	public Integer getexeTypeProp(){
		if(this instanceof AtomicComponentModel){
			AtomicComponentModel acm = (AtomicComponentModel)this;
			OPRoSExeSemanticsElementModel opem = (OPRoSExeSemanticsElementModel)acm.getClassModel().getUMLDataModel().getElementProperty("OPRoSExeSemanticsElementModel");
			if(opem!=null){
//				String ddd = opem.getexePeriodProp();
//				System.out.println("ddd:========================>"+ddd);
				return opem.getexeTypeProp();
			}
		}
		else{
		if(uMLDataModel.getProperty(IAtomicComponentProperty.PROPERTY_EXE_TYPE)==null){
			this.setexeTypeProp("0");
		}
		return new Integer(uMLDataModel.getProperty(IAtomicComponentProperty.PROPERTY_EXE_TYPE));
		}
		return new Integer("0");
	}
	
	public void setcompilerProp(String p){
		if(this instanceof AtomicComponentModel){
			AtomicComponentModel acm = (AtomicComponentModel)this;
			OPRoSExeEnvironmentElementModel opem = (OPRoSExeEnvironmentElementModel)acm.getClassModel().getUMLDataModel().getElementProperty("OPRoSExeEnvironmentElementModel");
			if(opem!=null){
				opem.setcompilerProp(p);
				uMLDataModel.setProperty(IAtomicComponentProperty.PROPERTY_COMPILER , p);
			}
		}
		else
		uMLDataModel.setProperty(IAtomicComponentProperty.PROPERTY_COMPILER , p);
	}
	
	public String getcompilerString(){
		Integer it = this.getcompilerProp();
		return IAtomicComponentProperty.PROPERTY_COMPILER_ARRAY[it.intValue()];
	}

	public Integer getcompilerProp(){
		if(this instanceof AtomicComponentModel){
			AtomicComponentModel acm = (AtomicComponentModel)this;
			OPRoSExeEnvironmentElementModel opem = (OPRoSExeEnvironmentElementModel)acm.getClassModel().getUMLDataModel().getElementProperty("OPRoSExeEnvironmentElementModel");
			if(opem!=null){
//				String ddd = opem.getexeEnvDomainProp();
//				System.out.println("ddd:========================>"+ddd);
				return opem.getcompilerProp();
			}
		}
		else{
		
		if(uMLDataModel.getProperty(IAtomicComponentProperty.PROPERTY_COMPILER)==null){
			this.setcompilerProp("0");
		}
		return new Integer(uMLDataModel.getProperty(IAtomicComponentProperty.PROPERTY_COMPILER));
		}
		return new Integer("0");
	}
	
	public void setimplementLangProp(String p){
		if(this instanceof AtomicComponentModel){
			AtomicComponentModel acm = (AtomicComponentModel)this;
			OPRoSExeEnvironmentElementModel opem = (OPRoSExeEnvironmentElementModel)acm.getClassModel().getUMLDataModel().getElementProperty("OPRoSExeEnvironmentElementModel");
			if(opem!=null){
				opem.setimplementLangProp(p);
				uMLDataModel.setProperty(IAtomicComponentProperty.PROPERTY_IMPL_LANGUAGE , p);
			}
		}
		else
		uMLDataModel.setProperty(IAtomicComponentProperty.PROPERTY_IMPL_LANGUAGE , p);
	}
	
	public String getimplementLangString(){
		Integer it = this.getimplementLangProp();
		return IAtomicComponentProperty.PROPERTY_IMPL_LANGUAGE_ARRAY[it.intValue()];
		
	}

	public Integer getimplementLangProp(){
		if(this instanceof AtomicComponentModel){
			AtomicComponentModel acm = (AtomicComponentModel)this;
			OPRoSExeEnvironmentElementModel opem = (OPRoSExeEnvironmentElementModel)acm.getClassModel().getUMLDataModel().getElementProperty("OPRoSExeEnvironmentElementModel");
			if(opem!=null){
				String ddd = opem.getexeEnvDomainProp();
				System.out.println("ddd:========================>"+ddd);
				return opem.getimplementLangProp();
			}
		}
		else{
		if(uMLDataModel.getProperty(IAtomicComponentProperty.PROPERTY_IMPL_LANGUAGE)==null){
			this.setimplementLangProp("0");
		}
		return new Integer(uMLDataModel.getProperty(IAtomicComponentProperty.PROPERTY_IMPL_LANGUAGE));
		}
		return new Integer("0");
	}
	
	public void setlibNameProp(String p){
		if(this instanceof AtomicComponentModel){
			AtomicComponentModel acm = (AtomicComponentModel)this;
			OPRoSExeEnvironmentElementModel opem = (OPRoSExeEnvironmentElementModel)acm.getClassModel().getUMLDataModel().getElementProperty("OPRoSExeEnvironmentElementModel");
			if(opem!=null){
				String type = this.getlibTypeProp();
//				String libName = this.getlibNameProp();
				if(p!=null){
					int i = p.lastIndexOf(".");
					if(i>0){
						String name = p.substring(0, i);
						p = name+"."+type;
//						this.setlibNameProp(libName);
					}
					else if(i==-1){
						p = p+"."+type;
					}
				}
				
				opem.setlibNameProp(p);
				uMLDataModel.setProperty(IAtomicComponentProperty.PROPERTY_LIBRARY_NAME , p);
			}
		}
		else
		uMLDataModel.setProperty(IAtomicComponentProperty.PROPERTY_LIBRARY_NAME , p);
	}

	public String getlibNameProp(){
		if(this instanceof AtomicComponentModel){
			AtomicComponentModel acm = (AtomicComponentModel)this;
			OPRoSExeEnvironmentElementModel opem = (OPRoSExeEnvironmentElementModel)acm.getClassModel().getUMLDataModel().getElementProperty("OPRoSExeEnvironmentElementModel");
			if(opem!=null){
				String ddd = opem.getexeEnvDomainProp();
				System.out.println("ddd:========================>"+ddd);
				return opem.getlibNameProp();
			}
		}
		else{
		if(uMLDataModel.getProperty(IAtomicComponentProperty.PROPERTY_LIBRARY_NAME)==null){
			this.setlibNameProp("");
		}
		return uMLDataModel.getProperty(IAtomicComponentProperty.PROPERTY_LIBRARY_NAME);
		}
		return "";
	}
	
	public void setlibTypeProp(String p){
		if(this instanceof AtomicComponentModel){
			AtomicComponentModel acm = (AtomicComponentModel)this;
			OPRoSExeEnvironmentElementModel opem = (OPRoSExeEnvironmentElementModel)acm.getClassModel().getUMLDataModel().getElementProperty("OPRoSExeEnvironmentElementModel");
			if(opem!=null){
				opem.setlibTypeProp(p);
				String libName = this.getlibNameProp();
				if(libName!=null){
					int i = libName.lastIndexOf(".");
					if(i>0){
						String name = libName.substring(0, i);
						libName = name+"."+p;
						this.setlibNameProp(libName);
					}
				}
				
				uMLDataModel.setProperty(IAtomicComponentProperty.PROPERTY_LIBRARY_TYPE , p);
			}
		}
		else
		uMLDataModel.setProperty(IAtomicComponentProperty.PROPERTY_LIBRARY_TYPE , p);
	}

	public String getlibTypeProp(){
		if(this instanceof AtomicComponentModel){
			AtomicComponentModel acm = (AtomicComponentModel)this;
			OPRoSExeEnvironmentElementModel opem = (OPRoSExeEnvironmentElementModel)acm.getClassModel().getUMLDataModel().getElementProperty("OPRoSExeEnvironmentElementModel");
			if(opem!=null){
				String ddd = opem.getexeEnvDomainProp();
				System.out.println("ddd:========================>"+ddd);
				return opem.getlibTypeProp();
			}
		}
		else{
		if(uMLDataModel.getProperty(IAtomicComponentProperty.PROPERTY_LIBRARY_TYPE)==null){
			this.setlibTypeProp("");
		}
		return uMLDataModel.getProperty(IAtomicComponentProperty.PROPERTY_LIBRARY_TYPE);
		}
		return "";
	}
	
	
	
	
	//------------------atomic

	public void setComposite(String p){
		uMLDataModel.setProperty(this.ID_COMPOSITE , p);
	}

	public Integer getComposite(){
		if(uMLDataModel.getProperty(this.ID_COMPOSITE)==null){
			this.setComposite("1");
		}
		return Integer.valueOf(uMLDataModel.getProperty(this.ID_COMPOSITE));
	}




	public void setActive(String p){
		uMLDataModel.setProperty(this.ID_ACTIVE, p);
	}

	public Integer getActive(){
		if(uMLDataModel.getProperty(this.ID_ACTIVE)==null){
			this.setActive("1");
		}
		return Integer.valueOf(uMLDataModel.getProperty(this.ID_ACTIVE));
	}
	public void setReference(String p){
		uMLDataModel.setProperty(this.ID_REFERENCE, p);
		firePropertyChange(this.ID_REFERENCE, null, null); //$NON-NLS-1$
	}

	public Integer getReference(){
		if(uMLDataModel.getProperty(this.ID_REFERENCE)==null){
			this.setReference("1");
		}
		return Integer.valueOf(uMLDataModel.getProperty(this.ID_REFERENCE));
	}

	public void setActorImage(String p){
		uMLDataModel.setProperty(this.ID_ACTORIMAGE, p);
		Image image = this.getActorImage(p);
		
		//20080822IJS
		if(image!=null && image.getBounds().width>-1&&image.getBounds().height>-1){
			uMLDataModel.setProperty(this.ID_W, String.valueOf(image.getBounds().width));
			uMLDataModel.setProperty(this.ID_H, String.valueOf(image.getBounds().height));
			uMLDataModel.setElementProperty(this.ID_IMAGEDATA, image);
			firePropertyChange(this.ID_ACTORIMAGE, null, null); //$NON-NLS-1$
		}else{//PKY 08090906 S
			firePropertyChange(this.ID_ACTORIMAGE, null, null); //$NON-NLS-1$
		}
		//PKY 08090906 E

//		firePropertyChange(this.ID_IMAGEDATA, null, null); //$NON-NLS-1$
	}

	public String getActorImage(){
		if(uMLDataModel.getProperty(this.ID_ACTORIMAGE)==null){
			this.setActorImage("");
		}
		return uMLDataModel.getProperty(this.ID_ACTORIMAGE);
	}
	//PKY 08080501 S Image Dialog 이미지 리스트 및 불러오기 편하도록 개선
	public Image getActorImage(String p){
		return ProjectManager.getInstance().getActorImage(p);
	}
	//PKY 08080501 E Image Dialog 이미지 리스트 및 불러오기 편하도록 개선
	public Image getImage(String p){
		//ijs080429
		return ProjectManager.getInstance().getImage(p);


	}



	public void setScope(String p){
		uMLDataModel.setProperty(this.ID_SCOPE, p);
	}

	public Integer getScope(){
		if(uMLDataModel.getProperty(this.ID_SCOPE)==null){
			this.setScope("0");
		}
		return Integer.valueOf(uMLDataModel.getProperty(this.ID_SCOPE));
	}

	public void setMultiplicity(String p){
		uMLDataModel.setProperty(this.ID_MULTI, p);
		firePropertyChange(ID_MULTI, null, null); //$NON-NLS-1$

	}

	public String getMultiplicity(){
		if(uMLDataModel.getProperty(this.ID_MULTI)==null){
			this.setMultiplicity("");
		}
		return uMLDataModel.getProperty(this.ID_MULTI);
	}

	public void setName(String _name) {
//		if (uMLDataModel.getName().equals(_name))
//			return;
		
//		if(this instanceof AtomicComponentModel){
//			if(!"".equals(uMLDataModel.getName())){
//				MessageDialog.openWarning(ProjectManager.getInstance().at.getWorkbench().getActiveWorkbenchWindow().getShell(), "ComponentName", "not change ComponentName");
//				return;
//			}
//		}
		//		name = _name;
		uMLDataModel.setName(_name);
		firePropertyChange(ID_NAME, null, _name); //$NON-NLS-1$
	}
	
	
	
	

	public void setStereotype(String p) {
		this.stereotype = p;
		
	}

	public String getStereotype() {
		return this.stereotype;
	}

	public void setBackGroundColor(Color d) {
		this.backGroundColor = d;
//		this.uMLDataModel.setElementProperty(ID_COLOR, d);
		firePropertyChange(ID_COLOR, null, backGroundColor); //$NON-NLS-1$
	}

	public Color getBackGroundColor() {
		return this.backGroundColor;
	}

	public void setVerticalGuide(UMLModelGuide vGuide) {
		verticalGuide = vGuide;
	}

	public void setTreeModel(UMLTreeModel p) {
		this.Model = p;
	}

	public UMLTreeModel getUMLTreeModel() {
		return this.Model;
	}

	public void setUMLDataModel(UMLDataModel p) {
		this.uMLDataModel = p;
	}

	public UMLDataModel getUMLDataModel() {
		return this.uMLDataModel;
	}

	public EditPart getTempEditPart() {
		UMLGraphicalPartFactory ugp = new UMLGraphicalPartFactory();
		return ugp.createEditPart(null, this);
	}

	public Object clone() {
		return null;
	}

	public Vector getCloneOutPutLine() {
		Vector vc = this.getSourceConnections();
		for (int i = 0; i < vc.size(); i++) {
			LineModel li = (LineModel)vc.get(i);
		}
		return null;
	}


	public boolean isAddLine() {
		return isAddLine;
	}


	public void setAddLine(boolean isAddLine) {
		this.isAddLine = isAddLine;
	}


	public java.util.ArrayList getInterfaces() {
		return interfaces;
	}


	public void setInterfaces(java.util.ArrayList interfaces) {
		this.interfaces = interfaces;
	}


	public java.util.ArrayList getParents() {
		return parents;
	}


	public void setParents(java.util.ArrayList parents) {
		this.parents = parents;
	}

	public int getAttributeCount() {
		return attributeCount;
	}

	public void setAttributeCount(int attributeCount) {
		this.attributeCount = attributeCount;
	}

	public int getOperationCount() {
		return operationCount;
	}

	public void setOperationCount(int operationCount) {
		this.operationCount = operationCount;
	}

	public N3EditorDiagramModel getN3EditorDiagramModelTemp() {
		return n3EditorDiagramModelTemp;
	}

	public void setN3EditorDiagramModelTemp(
			N3EditorDiagramModel editorDiagramModelTemp) {
		n3EditorDiagramModelTemp = editorDiagramModelTemp;
	}

	public java.util.ArrayList getRefType() {
		return refType;
	}

	public void setRefType(java.util.ArrayList refType) {
		this.refType = refType;
	}

	public void updateModel(UpdateEvent p) {
//		//rename
//		if (p.getType() == IUpdateType.REMOVE_TYPE) {
//		setName(this.getName());
//		}
	}

	public String getView_ID() {
		return view_ID;
	}

	public void setView_ID(String view_ID) {
		this.view_ID = view_ID;
	}
	//20080327 PKY S 해당 객체에 파티션이 먼져 들어가있는지 없는지 확인
	public boolean isPartition() {
		return isPartition;
	}

	public void setPartition(boolean isPartition) {
		this.isPartition = isPartition;
	}
	//20080327 PKY E 해당 객체에 파티션이 먼져 들어가있는지 없는지 확인
	//2008042106PKY S

	public NodeContainerModel getSaveContainer() {
		return saveContainer;
	}

	public void setSaveContainer(NodeContainerModel saveContainer) {
		this.saveContainer = saveContainer;
	}
	//2008042106PKY E

	public boolean isStero() {
		return isStero;
	}

	public void setStero(boolean isStero) {
		this.isStero = isStero;
//		uMLDataModel.setElementProperty(this.ID_EXTENSIONPOINT, p);
//		firePropertyChange(ID_EXTENSIONPOINT, null, null); //$NON-NLS-1$
	}
	//PKY 08052101 S 컨테이너에서 그룹으로 변경
	public java.util.ArrayList getGroupParent() {
		return groupParent;
	}

	public void addGroupParent(Object model) {
		//PKY 08052901 S 포함 에러발생 수정
		boolean check=false;
//		if(!(this instanceof SubPartitonModel)){//PKY 08061801 S 2회 저장할경우 파티션이 떨어져나가는문제

		for(int i=0; i<groupParent.size();i++){
			if(groupParent.get(i)==model){
				check=true;
			}
		}
		if(check==false){
			this.groupParent.add(model);
			this.getUMLDataModel().setElementProperty("GROUP_PARENTS", groupParent);
		}

//		}//PKY 08061801 E 2회 저장할경우 파티션이 떨어져나가는문제
		//PKY 08052901 E 포함 에러발생 수정
	}
	//PKY 08052101 E 컨테이너에서 그룹으로 변경

	public Vector getOutputs() {
		return outputs;
	}

	public void setOutputs(Vector outputs) {
		this.outputs = outputs;
	}

	//PKY 08081801 S 오퍼레이션 어트리뷰트 숨길수 있는 기능
	public void setShowAttr(String i){
		ClassModel classModel= null;
		if(this instanceof ClassifierModel){
			ClassifierModel umlClass = (ClassifierModel)this;
			if(umlClass.getClassModel()!=null)
				classModel = umlClass.getClassModel();
		}else if(this instanceof ClassifierModelTextAttach){
			ClassifierModelTextAttach umlClass = (ClassifierModelTextAttach)this;
			if(umlClass.getClassModel()!=null)
				classModel = umlClass.getClassModel();
		}
		if(classModel!=null){
			if(classModel.isMode())
				if(i.equals("0")){
					classModel.setShowAttr(true);
					this.getUMLDataModel().setElementProperty(this.ID_ATTR_HIDDEN, "0");
				}else{
					classModel.setShowAttr(false);
					this.getUMLDataModel().setElementProperty(this.ID_ATTR_HIDDEN, "1");
				}
		}
	}
	public Integer getShowAttr(){
		if(this.getUMLDataModel().getElementProperty(this.ID_ATTR_HIDDEN)!=null){
			return Integer.valueOf((String)this.getUMLDataModel().getElementProperty(this.ID_ATTR_HIDDEN));
		}else{
			this.getUMLDataModel().setElementProperty(this.ID_ATTR_HIDDEN, "0");
			return 0;
		}
	}

	public void setShowOper(String i){
		ClassModel classModel= null;
		if(this instanceof ClassifierModel){
			ClassifierModel umlClass = (ClassifierModel)this;
			if(umlClass.getClassModel()!=null)
				classModel = umlClass.getClassModel();
		}else if(this instanceof ClassifierModelTextAttach){
			ClassifierModelTextAttach umlClass = (ClassifierModelTextAttach)this;
			if(umlClass.getClassModel()!=null)
				classModel = umlClass.getClassModel();
		}
		if(classModel!=null){
			if(classModel.isMode())
				if(i.equals("0")){
					classModel.setShowOper(true);
					this.getUMLDataModel().setElementProperty(this.ID_OPER_HIDDEN, "0");
				}else{
					classModel.setShowOper(false);
					this.getUMLDataModel().setElementProperty(this.ID_OPER_HIDDEN, "1");
				}
		}
	}
	public int getShowOper(){
		if(this.getUMLDataModel().getElementProperty(this.ID_OPER_HIDDEN)!=null){
			return Integer.valueOf((String)this.getUMLDataModel().getElementProperty(this.ID_OPER_HIDDEN));
		}else{
			this.getUMLDataModel().setElementProperty(this.ID_OPER_HIDDEN, "0");
			return 0;
		}	
	}
	//PKY 08081801 E 오퍼레이션 어트리뷰트 숨길수 있는 기능

	//PKY 08081101 S 포함관계 오른쪽 메뉴로 해서 합치는 불편함 문제 개선
	//PKY 08081801 S 
	/**
	 * lumpLocation 설명 
	 * 
	 *  #1 자신의 선택해서 이동하는 모델의 위치를 파악한다
	 *  
	 *  #2 자기 자신 위에 있는 다이어그램들의 모델들을 for문을 돌면서 차례대로 이동시킨다.
	 *  
	 *  #3 LineTextModel,PortModel,SubPartitonModel 모델은 제외한 모든 모델을 이동 시킨다
	 *  이 이유는 해당 ParentModel이 이동하지 않아도 위 모델중 하나가 선택한 모델 안에 위치하게되면 ParnetModel을 떨어져서 이동하게 된다 그래서 별도로 
	 *  ParentModel이 움직일때 해당 모델들을 같이 움직여 주도록 한다.
	 *  
	 *  #4 #3에서 설명한것처럼 해당 모델들에서 따로 움직여 주도록 설정한다.
	 *  
	 *  #5 마우스로 선택한 모델이 ActivityModel일 경우 해당 하위에 SubPartitonModel을 조사하여 움직여 준다.
	 *  
	 *  
	 *  
	 *  작성자 : 박광용 2008년 8월 20일 
	 */
	public void lumpLocation(UMLModel model,org.eclipse.draw2d.geometry.Point p){
		try{
			if(p.x==0 && p.y==0)
				return;

			int modelZPosition = -1; 
			int width=model.getLocation().x + model.getSize().width;
			int height=model.getLocation().y + model.getSize().height;

			if(model.getAcceptParentModel()!=null){
				if(ProjectManager.getInstance().getSelectNodes()!=null)
					if(!(ProjectManager.getInstance().getSelectNodes().size()>1)){
						if(model.getAcceptParentModel() instanceof N3EditorDiagramModel){
							N3EditorDiagramModel n3DiagramModel = (N3EditorDiagramModel) model.getAcceptParentModel();
							//#1
							for(int i = 0; i < n3DiagramModel.getChildren().size(); i++){
								if(n3DiagramModel.getChildren().get(i)== model){
									modelZPosition = i+1 ;
									i = n3DiagramModel.getChildren().size();
								}
							}
							//#2
							if(modelZPosition!=-1 && n3DiagramModel.getChildren().size()>=modelZPosition)
								for(int i = modelZPosition ; i < n3DiagramModel.getChildren().size(); i++){
									UMLModel drmChildrenModel = (UMLModel) n3DiagramModel.getChildren().get(i);
									int drmChildWidth = drmChildrenModel.getLocation().x + drmChildrenModel.getSize().width;
									int drmChildHeight = drmChildrenModel.getLocation().y + drmChildrenModel.getSize().height;
//									if(!(drmChildrenModel instanceof ComponentModel))
//										continue;
									//#3
									if(!(drmChildrenModel instanceof LineTextModel || drmChildrenModel instanceof PortModel || drmChildrenModel instanceof SubPartitonModel ||drmChildrenModel instanceof ElementLabelModel)) //PortModel의 경우 해당 모델이 움직이지 않아도 Port가 걸쳐져 있을경우 따라오는 문제로 들어오지않도록
//										if(!(drmChildrenModel instanceof StateLifelineModel && model instanceof StateLifelineModel))// 같은 StateLifeLineModel일 경우 MessagePoint에 움직임등의 문제로 안들어오도록 
											if(width >= drmChildrenModel.getLocation().x && model.getLocation().x <= drmChildrenModel.getLocation().x && width >= drmChildWidth)
												if(height >= drmChildrenModel.getLocation().y && drmChildrenModel.getLocation().y >=  model.getLocation().y	&& height >= drmChildHeight){
													Point moveLocation = new Point();

													moveLocation.x = drmChildrenModel.getLocation().x + p.x;
													moveLocation.y = drmChildrenModel.getLocation().y + p.y;
//													drmChildrenModel.location = moveLocation;
													drmChildrenModel.setLocation(moveLocation);
//													drmChildrenModel.firePropertyChange(ID_LOCATION, null, moveLocation); 

													//#4
//													if(drmChildrenModel instanceof FinalActivityModel){													
//														for(int k = 0 ; k < ((FinalActivityModel)drmChildrenModel).getPm().getPartitions().size(); k ++ ){
//															Point movePartLocation = new Point();
//															SubPartitonModel subPart = (SubPartitonModel)((FinalActivityModel)drmChildrenModel).getPm().getPartitions().get(k);
//
//															movePartLocation.x = drmChildrenModel.getLocation().x;
//															movePartLocation.y = subPart.getLocation().y + p.y;
//															subPart.location = movePartLocation;
//															subPart.firePropertyChange(ID_LOCATION, null, movePartLocation);		
//														}
//													}	
													

//													if(drmChildrenModel instanceof ClassifierModel){
//														ClassifierModel classif = (ClassifierModel)drmChildrenModel;
//														for(int k = 0 ; k < classif.pm.getPorts().size(); k ++){
//															Point portMoveLocation = new Point();
//															PortModel port = (PortModel)classif.pm.getPorts().get(k);
//															portMoveLocation.x = port.getLocation().x + p.x;
//															portMoveLocation.y = port.getLocation().y + p.y;
//															port.location = portMoveLocation;
//															
//															port.firePropertyChange(ID_LOCATION, null, portMoveLocation);															
//														}
//													}else if(drmChildrenModel instanceof ExceptionModel){//PKY 08090903 S 
//														ExceptionModel classif = (ExceptionModel)drmChildrenModel;
//														for(int k = 0 ; k < classif.getPorts().size(); k ++){
//															Point portMoveLocation = new Point();
//															PortModel port = (PortModel)classif.getPorts().get(k);
//															portMoveLocation.x = port.getLocation().x + p.x;
//															portMoveLocation.y = port.getLocation().y + p.y;
//															port.location = portMoveLocation;
//															port.firePropertyChange(ID_LOCATION, null, portMoveLocation);															
//														}
//													}

												}						
								}
							//#5
							if(model instanceof FinalActivityModel){													
								for(int k = 0 ; k < ((FinalActivityModel)model).getPm().getPartitions().size(); k ++ ){
									Point movePartLocation = new Point();
									SubPartitonModel subPart = (SubPartitonModel)((FinalActivityModel)model).getPm().getPartitions().get(k);

									movePartLocation.x = model.getLocation().x + p.x;
									movePartLocation.y = subPart.getLocation().y + p.y;
									subPart.location = movePartLocation;
									subPart.firePropertyChange(ID_LOCATION, null, movePartLocation);		
								}
							}	
						}

					}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	//PKY 08081101 E 포함관계 오른쪽 메뉴로 해서 합치는 불편함 문제 개선
	//20080811IJS 시작
	public boolean isExistModel() {
		return isExistModel;
	}

	public void setExistModel(boolean isExistModel) {
		this.isExistModel = isExistModel;
	}
	//20080811IJS 끝

	public boolean isReadOnlyModel() {
		return isReadOnlyModel;
	}

	public void setReadOnlyModel(boolean isReadOnlyModel) {
		this.isReadOnlyModel = isReadOnlyModel;
	}
	
//		WJH 090820 S
	 public void refresh(){
		  firePropertyChange(this.ID_REFRESH, null, null);
	 }
//		WJH 090820 E	
	 
	 //111206 SDM S - 전체 새로고침
	 public void allRefresh(){
		 RootTreeModel root = ProjectManager.getInstance().getModelBrowser().getRootTree();
		 UMLTreeModel[] rootChilds = root.getChildren();
		 
		 for(int i=0; i<rootChilds.length; i++){
			 if(rootChilds[i] instanceof RootCmpComposingTreeModel){
				 RootCmpComposingTreeModel rcct = (RootCmpComposingTreeModel)rootChilds[i];
				 UMLTreeModel[] cctChilds = rcct.getChildren();
				 
				 for(int j=0; j<cctChilds.length; j++){
					 if(cctChilds[j] instanceof PackageTreeModel){
						 PackageTreeModel pm = (PackageTreeModel)cctChilds[j];
						 UMLTreeModel[] pmChilds = pm.getChildren();
						 
						 for(int k=0; k<pmChilds.length; k++){
							 if(pmChilds[k] instanceof StrcuturedPackageTreeModel){
								 StrcuturedPackageTreeModel stm = (StrcuturedPackageTreeModel)pmChilds[k];
								 Object um = stm.getRefModel();
								 
								 if(um instanceof UMLModel){
									 ((UMLModel) um).refresh();
								 }
							 }
						 }
					 }
				 }
			 }
		 }
	 }
	 //111206 SDM E - 전체 새로고침
	
//	public Vector getOutputs() {
//	return outputs;
//	}

//	public void setOutputs(Vector outputs) {
//	this.outputs = outputs;
//	}

//	public Vector getInputs() {
//	return inputs;
//	}

//	public void setInputs(Vector inputs) {
//	this.inputs = inputs;
//	}

//	public String writeView(){
//	StringBuffer sb = new StringBuffer();
//	return sb.toString();
//	}
	
	//20080821 KDI s
	public IPropertyDescriptor[] getAllDescriptors(){
		return descriptors;
	}
	//20080821 KDI e

	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}

	public boolean isView() {
		return isView;
	}

	public void setView(boolean isView) {
		this.isView = isView;
	}
}
