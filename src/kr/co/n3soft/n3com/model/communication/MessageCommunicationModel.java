package kr.co.n3soft.n3com.model.communication;

import kr.co.n3soft.n3com.anchor.N3ConnectionLocator;
import kr.co.n3soft.n3com.model.comm.ConstraintsModel;
import kr.co.n3soft.n3com.model.comm.ElementLabelModel;
import kr.co.n3soft.n3com.model.comm.LineModel;
import kr.co.n3soft.n3com.model.comm.LineTextModel;
import kr.co.n3soft.n3com.model.comm.OperationElementModel;
import kr.co.n3soft.n3com.model.comm.RoleModel;
import kr.co.n3soft.n3com.model.comm.TypeRefModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;
import kr.co.n3soft.n3com.model.umlclass.OperationEditableTableItem;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.PositionConstants;

public class MessageCommunicationModel extends UMLDiagramModel {
    static final long serialVersionUID = 1;
    public static int count = 0;
    ElementLabelModel name = null;
    private Integer layout = null;
    private int parentLayout = 0;
    private ArrowModel leftArrowModel = new ArrowModel(true);
    private ArrowModel rightArrowModel = new ArrowModel(false);
    private String type = "";
    public static String TYPE_NAME = "0";
    public static String TYPE_ROLE = "1";
    public static String TYPE_CONSTRAINTS = "2";
    public static String TYPE_QUALIFIER = "3";
    public static String TYPE_DERIVED = "4";
    public static String TYPE_DERIVED_UNION = "5";
    public static String TYPE_OWNED = "6";
    public static String TYPE_ORDERED = "7";
    public static String TYPE_ALLOW_DUPLICATES = "8";
    public static String TYPE_MULTPLICITY = "9";
    public static String TYPE_GUARD = "10";
    public static String TYPE_WEIGHT = "11";
    public static String TYPE_STATE_GUARD= "13";
    public static String TYPE_STATE_EFFECT = "14";
    public static String TYPE_STATE_TRIGER = "15";
    public static String TYPE_STEREO = "16";//PKY 08070101 S 라인에 스트레오 값 넣을 수 있도록 수정
    public static String TYPE_COMMUNICATION_MESSAGE = "17";//PKY 08072401 S 라인 메세지들 다이어그램에서 내용을 없애면 사라지도록 수정
    
    public static String TARGET_TYPE_NAME = "100";
    public static String TARGET_TYPE_ROLE = "101";
    public static String TARGET_TYPE_CONSTRAINTS = "102";
    public static String TARGET_TYPE_QUALIFIER = "103";
    public static String TARGET_TYPE_DERIVED = "104";
    public static String TARGET_TYPE_DERIVED_UNION = "105";
    public static String TARGET_TYPE_OWNED = "106";
    public static String TARGET_TYPE_ORDERED = "107";
    public static String TARGET_TYPE_ALLOW_DUPLICATES = "108";
    public static String TARGET_TYPE_MULTPLICITY = "109";
    public static String TARGET_TYPE_GUARD = "110";
    public static String TARGET_TYPE_WEIGHT = "111";
    public static String TARGET_TYPE_STATE_GUARD= "113";
    public static String TARGET_TYPE_STATE_EFFECT = "114";
    public static String TARGET_TYPE_STATE_TRIGER = "115";
  //PKY 08070301 S Communication Dialog 추가작업
    public int signature=0;
    public int synch=0;
    public int kind=0;
    public int lifecycle=0;
    public boolean back=false;
  //PKY 08070301 E Communication Dialog 추가작업
    private N3ConnectionLocator N3ConnectionLocator;//PKY 08052901 S 라인(타켓,소스) 저장되도록 수정

    /*
     * arrow = 0; right
     * arrow = 1:left
     */
    private int arrow = 0;
    private String num="";
    OperationEditableTableItem oti;
	OperationElementModel oem;
	TypeRefModel trm;
	String key= "";
	/*
	 * 0:communication
	 * 1:sequence
	 */
	int messageIntType = 0;
    /*
     * angle = 0 오른쪽
     * angle = 1 아래
     * angle = 2 왼쪽
     * angle = 3 위
     */
    private String angle = "0";

    public MessageCommunicationModel() {
    	size.width = 0;
        size.height = 15;
    	name = new ElementLabelModel(PositionConstants.LEFT);
    	name.setType("Message");
    	name.setParentLayout(1);
    	name.setLayout(BorderLayout.CENTER);
    	leftArrowModel.setLayout(BorderLayout.LEFT);
    	leftArrowModel.setParentLayout(1);
    	rightArrowModel.setLayout(BorderLayout.RIGHT);
    	rightArrowModel.setParentLayout(1);
    	this.addChild(name);
//    	this.addChild(leftArrowModel);
//    	this.addChild(rightArrowModel);
    	
    }

    
    public void setNumber(String p){
    	num = p;
    	this.name.setNumber(p);
//    	this.setName(this.getName());
    }

    public String getNumber(){
    	return num;
    }
    
    public void setMessageType(String p){
    	name.setType(p);
    }
    
    public void checkCondition(){
    	name.setType("Condition");
//    	name.align = PositionConstants.CENTER;
    }
    
    public String getCondition(){
    	int index1 = name.getLabelContents().indexOf("{");
    	int index2 = name.getLabelContents().indexOf("}");
    	if(index1==0 && index2==name.getLabelContents().length()-1){
    		String con = name.getLabelContents().substring(1, index2);
    		return con;
    	}
    	return name.getLabelContents();
    }
    
    public String getMessage(){
    	return name.getMessage();
    	
    }
    
    public void addLeftArrowModel(){
    	if(leftArrowModel!=null){
    		this.arrow = 1;
    		this.addChild(leftArrowModel);
    	}
    }
    
    public void addRightArrowModel(){
    	if(rightArrowModel!=null){
    		this.arrow = 0;
    		this.addChild(rightArrowModel);
    	}
    }

    public static String TERMINALS_OUT[] = new String[] {
        "1", "2", "3", "4", 
            "5", "6", "7", "8"
    }; 
    
    public void setAngle(String p){
    	this.angle = p;
    	firePropertyChange(ID_ANGLE, null, p); //$NON-NLS-1$
    }
    
  //PKY 08070301 S Communication Dialog 추가작업
    public void setSignature(String p){
 
    	firePropertyChange(ID_SIGNATURE, null, p); //$NON-NLS-1$
    }
    public void setSynch(String p){

    	firePropertyChange(ID_SYNCH, null, p); //$NON-NLS-1$
    }
    public void setKind(String p){

    	firePropertyChange(ID_KIND, null, p); //$NON-NLS-1$
    }
    public void setLifeCycel(String p){

    	firePropertyChange(ID_LIFECYCEL, null, p); //$NON-NLS-1$
    }
    
  //PKY 08070301 E Communication Dialog 추가작업
    
    public String getAngel(){
    	return this.angle;
    }

    public static String TERMINALS_IN[] = new String[] {
        "A", "B", "C", "D", //$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
            "E", "F", "G", "H"
    }; //$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$

    public void setLayout(Integer s) {
        this.layout = s;
       
    }

    public void setParentLayout(int s) {
        this.parentLayout = s;
      
    }

    public Integer getLayout() {
        return this.layout;
       
    }

    public int getParentLayout() {
        return this.parentLayout;
      
    }

    public void update() {
      
    }
    public void setName(String uname) {
    	name.setLabelContents(uname);

    }
    public void setName(String uname,boolean bo) {
    	name.setName(uname);

    }
    public String getName() {
       return  name.getLabelContents();
    }
    public ElementLabelModel getLabelContents() {
        return  name;
     }
    public Object clone() {
    	MessageCommunicationModel clone = new MessageCommunicationModel();
        //		clone.setUMLDataModel((UMLDataModel)this.getUMLDataModel().clone());
        clone.setSize(this.getSize());
        clone.setLocation(this.getLocation());
        clone.sourceModel = this;
        System.out.print(this.getName());
        clone.setName(this.getName(),false);  //PKY 08070701 S 커뮤니케이션 모양 변경
      //PKY 08070301 S Communication Dialog 추가작업
        clone.setSignature(this.signature);
        clone.setSynch(this.synch);
        clone.setKind(this.kind);
        clone.setLifecycle(this.lifecycle);
      //PKY 08070301 E Communication Dialog 추가작업
        clone.setID(this.getID());
        clone.getUMLDataModel().setId(clone.getUMLDataModel().getId());
        clone.setView_ID(this.getView_ID());
        ProjectManager.getInstance().setTempCopyMap(clone.sourceModel.getID(), clone);
       
        return clone;
    }

	public OperationEditableTableItem getOti() {
		return oti;
	}

	public void setOti(OperationEditableTableItem oti) {
		this.oti = oti;
		name.setOti(oti);
		oti.setMessageCommunicationModel(this);
	}

	public OperationElementModel getOem() {
		return oem;
	}

	public void setOem(OperationElementModel oem) {
		this.oem = oem;
		name.setOem(oem);
	}

	public TypeRefModel getTrm() {
		return trm;
	}

	public void setTrm(TypeRefModel trm) {
		this.trm = trm;
		name.setTrm(trm);
	}
	
	public String wirteModel(){
		StringBuffer sb = new StringBuffer();
		String sOti = "false";
		String sTrm = "false";
		
		if(this.oti!=null){
			sOti = "true";
		}
		if(this.trm!=null){
			sTrm = ((UMLModel)trm.getAcceptParentModel()).getUMLDataModel().getId();
		}
		//PKY 08070301 S Communication Dialog 추가작업
		if(name.getType()!=null && name.getType().equals("Condition")){
			sb.append(name.getType()+"\""+this.getCondition()+"\""+sOti+"\""+sTrm+"\""+this.getMessageIntType()+"\""+this.arrow+"\""+this.angle+"\""+((LineTextModel)this.getAcceptParentModel()).getSize().width+"\""+((LineTextModel)this.getAcceptParentModel()).getSize().height+"\""+((LineTextModel)this.getAcceptParentModel()).getLocation().x+"\""+((LineTextModel)this.getAcceptParentModel()).getLocation().y+"\""+this.signature+"\""+this.synch+"\""+this.kind+"\""+this.lifecycle+"\""+this.back);
		}
		else
		sb.append(name.getType()+"\""+name.getLabelContents()+"\""+sOti+"\""+sTrm+"\""+this.getMessageIntType()+"\""+this.arrow+"\""+this.angle+"\""+((LineTextModel)this.getAcceptParentModel()).getSize().width+"\""+((LineTextModel)this.getAcceptParentModel()).getSize().height+"\""+((LineTextModel)this.getAcceptParentModel()).getLocation().x+"\""+((LineTextModel)this.getAcceptParentModel()).getLocation().y+"\""+this.signature+"\""+this.synch+"\""+this.kind+"\""+this.lifecycle+"\""+this.back);
		//PKY 08070301 E Communication Dialog 추가작업
		return sb.toString();
	}

//	public int getNessageType() {
//		return nessageType;
//	}
//
//	public void setNessageType(int nessageType) {
//		this.nessageType = nessageType;
//	}

	public int getMessageIntType() {
		return messageIntType;
	}

	public void setMessageIntType(int p) {
		this.messageIntType = p;
		name.setMessageIntType(messageIntType);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	//PKY 08052901 S 라인(타켓,소스) 저장되도록 수정
	public N3ConnectionLocator getN3ConnectionLocator() {
		return N3ConnectionLocator;
	}

	public void setN3ConnectionLocator(N3ConnectionLocator connectionLocator) {
		N3ConnectionLocator = connectionLocator;
	}

	//PKY 08070301 S Communication Dialog 추가작업
	public int getSignature() {
		return signature;
	}

	public void setSignature(int signature) {
		this.signature = signature;
	}

	public int getSynch() {
		return synch;
	}

	public void setSynch(int synch) {
		this.synch = synch;
	}

	public int getKind() {
		return kind;
	}

	public void setKind(int kind) {
		this.kind = kind;
	}

	public int getLifecycle() {
		return lifecycle;
	}

	public void setLifecycle(int lifecycle) {
		this.lifecycle = lifecycle;
	}

	public boolean isBack() {
		return back;
	}

	public void setBack(boolean back) {
		this.back = back;
	}
	
	//PKY 08070301 E Communication Dialog 추가작업

	//PKY 08052901 E 라인(타켓,소스) 저장되도록 수정


   
}
