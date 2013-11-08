package kr.co.n3soft.n3com.model.comm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.comm.descriptor.CommunicationMessageDescriptor;
import kr.co.n3soft.n3com.model.comm.descriptor.DetailPropertyDescriptor;
import kr.co.n3soft.n3com.model.comm.descriptor.SequenceMessageDescriptor;
import kr.co.n3soft.n3com.model.communication.MessageAssoicateLineModel;
import kr.co.n3soft.n3com.model.communication.MessageCommunicationModel;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.model.usecase.NoteLineModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.draw2d.Bendpoint;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import uuu.views.DescriptionView;

public class LineModel extends UMLElementModel implements IUMLModelUpdateListener{
	private String id = "";
	boolean isLoad = false;
	boolean isUndo = false;

	private String sourceId = "";
	private String targetId = "";

	public static final int ExtendLine = 0;
	public static final int IncludeLine = 1;
	public static final int AssociateLine = 2;


	N3EditorDiagramModel diagram = null;


	public boolean isChange = false;
	protected RoleModel sourceRole = new RoleModel();
	protected RoleModel tatgetRole = new RoleModel();
	protected ConstraintsModel constraintsModel = new ConstraintsModel();
	public static final Integer SOLID_CONNECTION = new Integer(Graphics.LINE_SOLID);

	/**
	 * Used for indicating that a Connection with dashed line style should be created.
	 * @see org.eclipse.gef.examples.shapes.parts.ShapeEditPart#createEditPolicies()
	 */
	public static final Integer DASHED_CONNECTION = new Integer(Graphics.LINE_DASH);

	/** Property ID to use when the line style of this connection is modified. */
	public static final String LINESTYLE_PROP = "LineStyle";
	//	private static final IPropertyDescriptor[] descriptors = new IPropertyDescriptor[1];
	private static final String SOLID_STR = "Solid";
	private static final String DASHED_STR = "Dashed";
	public static final String SOURCE_ROLE = "Source_role";
	public static final String TARGET_ROLE = "Target_role";
	public static final String CONSTRAINTS_ROLE = "Constraints";
	public static String ID_DETAIL = "detailProperty";
	public static String ID_DESCRIPTORS = "ID_DESCRIPTORS"; //2008041605PKY S
	public static String ID_SOURCE_ROLE = "ID_SOURCE_ROLE";
	public static String ID_SOURCE_ROLE_NOTE = "ID_SOURCE_ROLE_NOTE";
	public static String ID_SOURCE_CONSTRAINT = "ID_SOURCE_CONSTRAINT";
	public static String ID_SOURCE_QUALIFIER = "ID_SOURCE_QUALIFIER";
	public static String ID_SOURCE_DERIVED = "ID_SOURCE_DERIVED";
	public static String ID_SOURCE_DERIVEDUNION = "ID_SOURCE_DERIVEDUNION";
	public static String ID_SOURCE_OWNED = "ID_SOURCE_OWNED";
	public static String ID_SOURCE_ORDERED = "ID_SOURCE_ORDERED";
	public static String ID_SOURCE_ALLOWDUPLICATES = "ID_SOURCE_ALLOWDUPLICATES";
	public static String ID_SOURCE_MUL = "ID_SOURCE_MUL";
	public static String ID_SOURCE_AGGREGATION = "ID_SOURCE_AGGREGATION";
	public static String ID_SOURCE_NAVIGABILITY = "ID_SOURCE_NAVIGABILITY";
	public static String ID_GUARD = "ID_GUARD";
	public static String ID_WEIGHT = "ID_WEIGHT";


	public static String ID_TARGET_ROLE = "ID_TARGET_ROLE";
	public static String ID_TARGET_ROLE_NOTE = "ID_TARGET_ROLE_NOTE";
	public static String ID_TARGET_CONSTRAINT = "ID_TARGET_CONSTRAINT";
	public static String ID_TARGET_QUALIFIER = "ID_TARGET_QUALIFIER";
	public static String ID_TARGET_DERIVED = "ID_TARGET_DERIVED";
	public static String ID_TARGET_DERIVEDUNION = "ID_TARGET_DERIVEDUNION";
	public static String ID_TARGET_OWNED = "ID_TARGET_OWNED";
	public static String ID_TARGET_ORDERED = "ID_TARGET_ORDERED";
	public static String ID_TARGET_ALLOWDUPLICATES = "ID_TARGET_ALLOWDUPLICATES";
	public static String ID_TARGET_MUL = "ID_TARGET_MUL";
	public static String ID_TARGET_AGGREGATION = "ID_TARGET_AGGREGATION";
	public static String ID_TARGET_NAVIGABILITY = "ID_TARGET_NAVIGABILITY";

	public static String ID_TRAN_GUARD = "ID_TRAN_GUARD";
	public static String ID_TRAN_EFFECTS = "ID_TRAN_EFFECTS";
	public static String ID_TRAN_TRIGERS = "ID_TRAN_TRIGERS";

	private boolean isName=false;



	public static final String ID_NAME = "name";
	public static final String ID_STEREOTYPE = "ID_STEREOTYPE";//PKY 08081101 S 2회 저장할 경우 스트레오값이 사라지는 문제 
	public static final String ID_COMMUNICATIONLINE = "communicationLine";//PKY 08070301 S Communication Dialog 추가작업
	public static PropertyDescriptor nameProp = new TextPropertyDescriptor(ID_NAME, N3Messages.POPUP_NAME);//2008040302 PKY S
	public static PropertyDescriptor streoProp = new TextPropertyDescriptor(ID_STEREOTYPE, N3Messages.POPUP_STEREOTYPE);//PKY 08070101 S 라인에 스트레오 값 넣을 수 있도록 수정

	protected IPropertyDescriptor[] descriptors = null;//PKY 08081101 S 라인 프로퍼티 가장 마지막에 진행된 프로퍼티로 적용되는문제 
	public static PropertyDescriptor DetailPropertyDescriptorProp = new DetailPropertyDescriptor(ID_DETAIL, N3Messages.POPUP_PROPERTIES);//2008040301 PKY S    public static PropertyDescriptor MessageAssoicateLineProp = new CommunicationMessageDescriptor(ID_COMMUNICATIONLINE, "ddddddddddddddd");//PKY 08070301 S Communication Dialog 추가작업
	//PKY 08072201 S 커뮤니케이션 메시지 수정하는 다이얼로그 통합 누락된부분 수정
	public static PropertyDescriptor MessageAssoicateLineProp = new CommunicationMessageDescriptor(ID_COMMUNICATIONLINE, N3Messages.POPUP_EDIT_MESSAGE);//PKY 08070301 S Communication Dialog 추가작업
	public static PropertyDescriptor SequenceMessageLineProp = new SequenceMessageDescriptor(ID_COMMUNICATIONLINE, N3Messages.POPUP_EDIT_MESSAGE);//PKY 08070301 S Communication Dialog 추가작업
	private HashMap detailProp = new HashMap();

	//PKY 08081101 S 라인 프로퍼티 가장 마지막에 진행된 프로퍼티로 적용되는문제 
//	static {
//		descriptors = new IPropertyDescriptor[] {
//				DetailPropertyDescriptorProp,
//				nameProp,streoProp
//		};
//	}
	//PKY 08081101 E 라인 프로퍼티 가장 마지막에 진행된 프로퍼티로 적용되는문제 


	static final long serialVersionUID = 1;
	protected boolean value;
	protected UMLModel source, target;
	protected UMLModel oldSource, oldTarget;
	protected String sourceTerminal, targetTerminal;
	protected List bendpoints = new ArrayList();
	private int lineStyle = Graphics.LINE_SOLID;
	private int type = 0;
	private String name = "";
	private String stereotype="";
	UMLDataModel uMLDataModel = new UMLDataModel();
	public LineTextModel middleLineTextModel = new LineTextModel();
	public LineTextModel sourceLineTextModel = new LineTextModel();
	public LineTextModel targetLineTextModel = new LineTextModel();




	public LineModel(){
		//PKY 08072201 S 다이어그램에 텍스트필드 나오는문제 수정
		if(this.getSourceLineTextModel()!=null)
			this.getSourceLineTextModel().setSize(new Dimension(0,0));
		if(this.getTargetLineTextModel()!=null)
			this.getTargetLineTextModel().setSize(new Dimension(0,0));
		if(this.getMiddleLineTextModel()!=null)
			this.getMiddleLineTextModel().setSize(new Dimension(0,0));
		//PKY 08072201 E 다이어그램에 텍스트필드 나오는문제 수정
		middleLineTextModel.setLineModel(this);
		sourceLineTextModel.setLineModel(this);
		targetLineTextModel.setLineModel(this);
		//PKY 08070301 S  Communication Dialog 추가작업
		//PKY 08081101 S 저장 불러올 경우 Communication Message불러오지 못하는 문제
		if(this instanceof MessageAssoicateLineModel){
			descriptors = new IPropertyDescriptor[] {
					DetailPropertyDescriptorProp,
					MessageAssoicateLineProp
			};

		}
		//PKY 08081101 E 저장 불러올 경우 Communication Message불러오지 못하는 문제
		
		else if(this instanceof NoteLineModel){
			descriptors = new IPropertyDescriptor[] {

			};
		}
		
		//PKY 08081101 E Timing 구조 변경

		else {
			descriptors = new IPropertyDescriptor[] {
					DetailPropertyDescriptorProp,
					nameProp,streoProp
			};
		}
		//PKY 08070301 S  Communication Dialog 추가작업
		//PKY 08072201 E 커뮤니케이션 메시지 수정하는 다이얼로그 통합 누락된부분 수정

	}

	public void removeAllReqNum(){
		middleLineTextModel.removeAllReqNum();

	}



	public void addRightArrowModel(){
		middleLineTextModel.addRightArrowModel();
	}

	public void addMiddleLineTextModel(UMLElementModel child) {
		middleLineTextModel.addChild(child);
	}

	public void addSourceLineTextModel(UMLElementModel child) {
		sourceLineTextModel.addChild(child);
	}

	public void addTargetLineTextModel(UMLElementModel child) {
		targetLineTextModel.addChild(child);
	}

	public void removeMiddleLineTextModel(UMLElementModel child) {
		middleLineTextModel.removeChild(child);
	}
	//PKY 08052101 S 컨테이너 안에 라인 Text를 지웠다가 재 생성할 경우 생성안됨

	public void removeMiddleLineTextModel() {
		middleLineTextModel= new LineTextModel();
	}
	//PKY 08052101 E 컨테이너 안에 라인 Text를 지웠다가 재 생성할 경우 생성안됨

	public void removeSourceLineTextModel(UMLElementModel child) {
		sourceLineTextModel.removeChild(child);
	}

	public void removeTargetLineTextModel(UMLElementModel child) {
		targetLineTextModel.removeChild(child);
	}

	public LineTextModel getMiddleLineTextModel() {
		return middleLineTextModel;
	}

	public LineTextModel getSourceLineTextModel() {
		return sourceLineTextModel;
	}

	public LineTextModel getTargetLineTextModel() {
		return targetLineTextModel;
	}

	public void setText(String text, int index, int loc) {
		if (loc == 0) {
			sourceLineTextModel.setText(text, index);
		}
		else if (loc == 1) {
			middleLineTextModel.setText(text, index);
		}
		else if (loc == 2) {
			targetLineTextModel.setText(text, index);
		}
	}

	public String getId() {
		return uMLDataModel.getId();
	}

	public void setOldTraget(UMLModel p) {
		this.oldTarget = p;
	}

	public UMLModel getOldTraget() {
		return this.oldTarget;
	}

	public void setOldSource(UMLModel p) {
		this.oldSource = p;
	}

	public UMLModel getOldSource() {
		return this.oldSource;
	}

	public void setLineType(int p) {
		this.type = p;
	}

	public int getLineType() {
		return this.type;
	}

	public void attachSource() {
		if (getSource() == null || getSource().getSourceConnections().contains(this))
			return;
		getSource().connectOutput(this);
	}

	public void cloneLineModel(UMLContainerModel p) {
		if (getSource() instanceof UMLModel) {
			UMLModel u = (UMLModel)getSource();
			N3EditorDiagramModel uc=  ProjectManager.getInstance().getUMLEditor().getDiagram();
			uc.addChild(sourceLineTextModel);
			uc.addChild(middleLineTextModel);
			uc.addChild(targetLineTextModel);

		}
	}

	public void attachTarget() {
		if (getTarget() == null || getTarget().getTargetConnections().contains(this))
			return;
		//inmsg
		getTarget().connectInput(this);
		if (getSource() instanceof UMLModel) {
			UMLModel u = (UMLModel)getSource();
//          V1.03 WJH E 080526 S 널포인트 참조 수정
            if(ProjectManager.getInstance().getUMLEditor() != null){
            	diagram=  ProjectManager.getInstance().getUMLEditor().getDiagram();
				if(diagram!=null){
					LineTextModel e = this.getMiddleLineTextModel();
					LineTextModel e1 = this.getTargetLineTextModel();
					LineTextModel e2 = this.getSourceLineTextModel();
					if(e!=null){
						middleLineTextModel = e;
	//					e.setLocation(new Point(400,176));
					}
					if(e1!=null){
						targetLineTextModel = e1;
					}
					if(e2!=null){
						sourceLineTextModel = e2;
					}
					diagram.addChild(sourceLineTextModel);
					diagram.addChild(middleLineTextModel);
					diagram.addChild(targetLineTextModel);
				}
            }
			else{
            }
//          V1.03 WJH E 080526 E 널포인트 참조 수정
		}
	}

	public void attachTarget( N3EditorDiagramModel uc) {
		if (getTarget() == null || getTarget().getTargetConnections().contains(this))
			return;
		//inmsg
		getTarget().connectInput(this);
		if (getSource() instanceof UMLModel) {
			UMLModel u = (UMLModel)getSource();
			diagram = uc;
//			N3EditorDiagramModel uc=  ProjectManager.getInstance().getUMLEditor().getDiagram();
			if(uc!=null){
				try{
					uc.addChild(sourceLineTextModel);
					uc.addChild(middleLineTextModel);
					uc.addChild(targetLineTextModel);
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}


		}
	}

	public void detachSource() {
		if (getSource() == null)
			return;
		getSource().disconnectOutput(this);
		
		
	}

	public void detachTarget() {
		if (getTarget() == null)
			return;
		getTarget().disconnectInput(this);
		if (getSource() instanceof UMLModel) {
			UMLModel u = (UMLModel)getSource();
			if(ProjectManager.getInstance().getUMLEditor()!=null){
			N3EditorDiagramModel uc =  ProjectManager.getInstance().getUMLEditor().getDiagram();
			
			uc.removeModel(sourceLineTextModel);
			uc.removeModel(middleLineTextModel);
			uc.removeModel(targetLineTextModel);
			this.removeAllReqNum();
		
			}
		}
	}

	public List getBendpoints() {
		return bendpoints;
	}

	public UMLModel getSource() {
		return source;
	}

	public String getSourceTerminal() {
		return sourceTerminal;
	}

	public UMLModel getTarget() {
		return target;
	}

	public String getTargetTerminal() {
		return targetTerminal;
	}

	public boolean getValue() {
		return value;
	}

	public void insertBendpoint(int index, Bendpoint point) {
		getBendpoints().add(index, point);
		firePropertyChange("bendpoint", null, null); //$NON-NLS-1$
		//		}
}
	//PKY 08051401 S 라인 꺽인것 바로 직선으로 만들기
	public void directBendpoint() {
		getBendpoints().clear();
		firePropertyChange("bendpoint", null, null); //$NON-NLS-1$
		//		}
	}
	//PKY 08051401 E 라인 꺽인것 바로 직선으로 만들기
	public void removeBendpoint(int index) {
		getBendpoints().remove(index);
		firePropertyChange("bendpoint", null, null); //$NON-NLS-1$
	}

	public void setBendpoint(int index, Bendpoint point) {
		getBendpoints().set(index, point);
		firePropertyChange("bendpoint", null, null); //$NON-NLS-1$
	}

	public void setBendpoints(Vector points) {
		bendpoints = points;
		firePropertyChange("bendpoint", null, null); //$NON-NLS-1$
	}



	public void setSource(UMLModel e) {
		Object old = source;
		source = e;
		if (e!=null && e.getParentModel() != null) {
			source = e.getParentModel();
		}
		firePropertyChange("source", old, source); //$NON-NLS-1$
	}

	public void setSourceTerminal(String s) {
		Object old = sourceTerminal;
		sourceTerminal = s;
		firePropertyChange("sourceTerminal", old, sourceTerminal); //$NON-NLS-1$
	}

	public String getName() {
		return name;
	}

	public void setTarget(UMLModel e) {
		Object old = target;
		target = e;
		if (e!=null && e.getParentModel() != null) {
			target = e.getParentModel();
		}
		firePropertyChange("target", old, target); //$NON-NLS-1$
	}

	public void setTargetTerminal(String s) {
		Object old = targetTerminal;
		targetTerminal = s;
		firePropertyChange("targetTerminal", old, targetTerminal); //$NON-NLS-1$
	}

	public void setValue(boolean value) {
		if (value == this.value) return;
		this.value = value;
		if (target != null)
			target.update();
		firePropertyChange("value", null, null); //$NON-NLS-1$
	}

	public String toString() {
		return "Wire(" + getSource() + "," + getSourceTerminal() + "->" + getTarget() + "," + getTargetTerminal() + ")"; //$NON-NLS-5$//$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
	}

	/**
	 * Returns the line drawing style of this connection.
	 * @return an int value (Graphics.LINE_DASH or Graphics.LINE_SOLID)
	 */
	public int getLineStyle() {
		return lineStyle;
	}

	/**
	 * Returns the descriptor for the lineStyle property
	 * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyDescriptors()
	 */
	public IPropertyDescriptor[] getPropertyDescriptors() {
		//20080728 KDI s 디스크립션 뷰에 설명 나타나도록 수정
		IViewPart iv = ProjectManager.getInstance().window.getActivePage().findView("uuu.views.DescriptionView");		
	   	if(iv!=null){	   		
	   		DescriptionView descriptionView = ProjectManager.getInstance().getDescriptionView();
	   		if(descriptionView != null){
	   			String str = "";
	   			if(detailProp.get(this.ID_DESCRIPTORS)==null){
	   				str = "";
	   			}
	   			str = (String)detailProp.get(this.ID_DESCRIPTORS);
	   			if(str != null)
	   				descriptionView.setDescriptionViwe(str);
	   			else
	   				descriptionView.setDescriptionViwe("");
	   		}
	   	}	   	
		//20080728 KDI e 디스크립션 뷰에 설명 나타나도록 수정
	   	
		return descriptors;
	}

	/**
	 * Returns the lineStyle as String for the Property Sheet
	 * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(java.lang.Object)
	 */
	public Object getPropertyValue(Object propName) {
		if (this.SOURCE_ROLE.equals(propName))
			return new RolePropertySource(this.sourceRole.clone());
		else if (this.TARGET_ROLE.equals(propName))
			return new RolePropertySource(this.tatgetRole.clone());
		else if (this.CONSTRAINTS_ROLE.equals(propName))
			return new ConstraintsProperty(this.constraintsModel.clone());
		else if (ID_NAME.equals(propName)) {
			return this.getName();
		}
		//PKY 08070101 S 라인에 스트레오 값 넣을 수 있도록 수정
		else if (ID_STEREOTYPE.equals(propName)) {
			if(this.getMiddleLineTextModel()!=null)
				for(int i =0; i<this.getMiddleLineTextModel().getLineTextm().getLineTextModels().size(); i++){
					MessageCommunicationModel ob=(MessageCommunicationModel)this.getMiddleLineTextModel().getLineTextm().getLineTextModels().get(i);
					if(ob.getLabelContents().getMessageIntType()==Integer.valueOf(MessageCommunicationModel.TYPE_STEREO)){

						if(ob.getName().lastIndexOf("<")>=0&&ob.getName().lastIndexOf(">")>=0){
							return ob.getName().substring(ob.getName().lastIndexOf("<")+1, ob.getName().lastIndexOf(">")-1);
						}
						else
							return ob.getName();
					}
				}
			return "";
		}
		//PKY 08070101 E 라인에 스트레오 값 넣을 수 있도록 수정
		else{
			ProjectManager.getInstance().setSelectPropertyUMLElementModel((UMLElementModel)this);
		}
		return null;
	}

	/**
	 * Set the line drawing style of this connection.
	 * @param lineStyle one of following values: Graphics.LINE_DASH or Graphics.LINE_SOLID
	 * @see Graphics#LINE_DASH
	 * @see Graphics#LINE_SOLID
	 * @throws IllegalArgumentException if lineStyle does not have one of the above values
	 */
	public void setLineStyle(int lineStyle) {
		if (lineStyle != Graphics.LINE_DASH && lineStyle != Graphics.LINE_SOLID) {
			throw new IllegalArgumentException();
		}
		this.lineStyle = lineStyle;
		firePropertyChange(LINESTYLE_PROP, null, new Integer(this.lineStyle));
	}

	/**
	 * Sets the lineStyle based on the String provided by the PropertySheet
	 * @see org.eclipse.ui.views.properties.IPropertySource#setPropertyValue(java.lang.Object, java.lang.Object)
	 */
	public void setPropertyValue(Object id, Object value) {
		//PKY 08082201 S 팀 프로젝트 읽기전용일때 수정불가능하도록 작업
		if(ProjectManager.getInstance()!=null && ProjectManager.getInstance().getSelectPropertyUMLElementModel()!=null)					
			if(!ProjectManager.getInstance().isItemEnable(ProjectManager.getInstance().getSelectPropertyUMLElementModel()))
				return;
				
		if (SOURCE_ROLE.equals(id))
			setSourceRole((RoleModel)value);
		else if (TARGET_ROLE.equals(id))
			setTargetRole((RoleModel)value);
		else if (this.CONSTRAINTS_ROLE.equals(id))
			this.setConstraints((ConstraintsModel)value);
		else if (ID_NAME.equals(id)) {
			setName((String)value);
		}
		//PKY 08070101 S 라인에 스트레오 값 넣을 수 있도록 수정
		else if (ID_STEREOTYPE.equals(id)) {
			//PKY 08071501 S 스트레오타입 누락된부분 추가
//			if(this.getMiddleLineTextModel()!=null)
//			for(int i =0; i<this.getMiddleLineTextModel().getLineTextm().getLineTextModels().size(); i++){
//			MessageCommunicationModel ob=(MessageCommunicationModel)this.getMiddleLineTextModel().getLineTextm().getLineTextModels().get(i);
//			if(ob.getLabelContents().getMessageIntType()==Integer.valueOf(MessageCommunicationModel.TYPE_STEREO)){
//			String text=(String)value;
//			boolean ok =false;
//			if(text.indexOf("<<")!=0)
//			if(text.lastIndexOf(">>")!=0){
//			ob.setName("<<"+text+">>");
//			ok=true;
//			}
//			if(ok==false){
//			ob.setName(text);
//			}
//			}
//			}
//			else{
//			set
//			}

			setStereotype((String)value);

			//PKY 08071501 E 스트레오타입 누락된부분 추가
		}
		//PKY 08070101 E 라인에 스트레오 값 넣을 수 있도록 수정
	}

	public void setTargetRole(RoleModel p) {
		this.tatgetRole = p;
		firePropertyChange(this.TARGET_ROLE, null, this.tatgetRole);
	}

	public void setConstraints(ConstraintsModel p) {
		this.constraintsModel = p;
		firePropertyChange(this.CONSTRAINTS_ROLE, null, this.constraintsModel);
	}


	public RoleModel getTargetRole() {
		return this.tatgetRole;
	}

	public RoleModel getSourceRole() {
		return this.sourceRole;
	}

	public ConstraintsModel getConstraints() {
		return this.constraintsModel;
	}

	public void setSourceRole(RoleModel p) {
		this.sourceRole = p;
		firePropertyChange(this.SOURCE_ROLE, null, this.sourceRole);
	}

	public void setName(String _name) {
		this.name = _name;
		firePropertyChange(this.ID_NAME, null, _name); //$NON-NLS-1$
	}
	//PKY 08071601 S 다이어그램에서 이름을 지울경우 에러발생
	public void setLienName(String _name) {
		this.name = _name;

	}
	//PKY 08071601 E 다이어그램에서 이름을 지울경우 에러발생
	public String getname() {
		return this.name;
	}

	public Object clone() {
		LineModel clone = new LineModel();
		return clone;
	}

	public HashMap getDetailProp() {
		return detailProp;
	}

	public void setDetailProp(HashMap detailProp) {
		this.detailProp = detailProp;
		firePropertyChange(this.ID_DETAIL, null, null); //$NON-NLS-1$
	}

	public N3EditorDiagramModel getDiagram() {
		return diagram;
	}

	public void setDiagram(N3EditorDiagramModel diagram) {
		this.diagram = diagram;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public boolean isLoad() {
		return isLoad;
	}
	public void setLoad(boolean isLoad) {
		this.isLoad = isLoad;
	}

	public boolean isUndo() {
		return isUndo;
	}

	public void setUndo(boolean isUndo) {
		this.isUndo = isUndo;
	}

	public void updateModel(UpdateEvent p){


	}

	public String getStereotype() {
		return stereotype;
	}

	public void setStereotype(String stereotype) {
		//PKY 08081801 S 스트레오타입,이름 두개가 존재할 경우 복사 붙여넣기 하면 스트레오타입만 복사 되는 문제
		//PKY 08081101 S 2회 저장할 경우 스트레오값이 사라지는 문제 
		if(stereotype.indexOf("<<")==0&&stereotype.lastIndexOf(">>")==stereotype.length()-2){
			stereotype = stereotype.substring(stereotype.indexOf("<<")+2, stereotype.lastIndexOf(">>"));
		}
		
		String name = getName();
		//PKY 08082201 S RoleName 삭제시 NullPoint 메시지 뜨는문제
		if(!name.trim().equals("")){
			setName("");			
		}
		this.stereotype =  stereotype;
		this.detailProp.put(this.ID_STEREOTYPE, stereotype);//PKY 08071601 S 라인 스트레오타입 복사 할 경우 복사안되는문제
		firePropertyChange(this.ID_STEREOTYPE, null, stereotype); //$NON-NLS-1$
		if(!name.trim().equals("")){
			setName(name);			
		}
		//PKY 08082201 E RoleName 삭제시 NullPoint 메시지 뜨는문제

		//PKY 08071501 E 스트레오값 자동 생성시 위치저장안되는문제
	}
}
