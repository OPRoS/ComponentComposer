package kr.co.n3soft.n3com.model.comm;

import kr.co.n3soft.n3com.anchor.N3ConnectionLocator;
import kr.co.n3soft.n3com.model.communication.MessageCommunicationModel;
import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.projectmanager.LineTextManager;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;

public class LineTextModel extends UMLDiagramModel {
	static final long serialVersionUID = 1;
	public static int count = 0;
	MessageCommunicationModel name = null;
	ElementLabelModel streotype = null;
	public Figure fi = null;
	private Integer layout = null;
	private int parentLayout = 0;
	boolean isBorder = true;
	LineTextManager lineTextm = new LineTextManager();
	boolean isAutoMode = false;
	LineModel lineModel = null;
	private int nameIndex = 0;
	private boolean isName=false; //PKY 08051401 S 라인 name변경하면 이름이 계속 하단에 추가되어 들어가는문제
	private N3ConnectionLocator N3ConnectionLocator;//PKY 08052801 S LineOladPoint 추가
	public LineTextModel() {
		size.height = 20;


//		name = new MessageCommunicationModel();
//		this.addChild(name);

	}

	public void removeAllReqNum(){
		lineTextm.removeAll();

	}

	public void addRightArrowModel(){
		lineTextm.addRightArrowModel();
	}

	public void setIsAutoMode(boolean p) {
		this.isAutoMode = p;
	}

	public LineTextModel(UMLDataModel p) {
	}

	public void addChild(UMLElementModel child) {
		if (child instanceof MessageCommunicationModel)
			lineTextm.addElementLabelModel((MessageCommunicationModel)child);
		super.addChild(child);
		int h = 20;
		for(int i=1;i<lineTextm.getLineTextModels().size();i++){
			h = h+20;
		}
		this.setSize(new Dimension(this.getSize().width,h));
	}

	public void removeChild(UMLElementModel child) {
		if (child instanceof MessageCommunicationModel)
			lineTextm.removeElementLabelModel((MessageCommunicationModel)child);
		super.removeChild(child);
		int h = 20;
		for(int i=1;i<lineTextm.getLineTextModels().size();i++){
			h = h+20;
		}
		this.setSize(new Dimension(this.getSize().width,h));
	}


	public void setText(String text, int index) {
		if(lineTextm.getLineTextModels().size()-1>=index)
			lineTextm.setElementLabelModel(text, index);
		//PKY 08061801 S LineText모델 자동으로 글씨 크기에따라서 늘어나도록
		int strlen = 0;
		int size=0;
		for(int i = 0; i < lineTextm.getLineTextModels().size(); i++){
			MessageCommunicationModel lineModel=(MessageCommunicationModel)lineTextm.getLineTextModels().get(i);
				strlen=ProjectManager.getInstance().widthAutoSize(lineModel.getName());
				if(strlen>size){
					size=strlen;
				}
			
		}
		//PKY 08061801 S LineText모델 자동으로 글씨 크기에따라서 늘어나도록
		this.setSize(new Dimension(size+6,this.getSize().height));
	}

	public String getText(int index) {
		return lineTextm.getElementLabelModel(index);
	}

	public MessageCommunicationModel getMessageCommunicationModel(int index) {
		return (MessageCommunicationModel)lineTextm.getLineTextModels().get(index);
	}

	public int getMessageSize(){
		return lineTextm.getLineTextModels().size();
	}
	//PKY 08061801 S LineText모델 자동으로 글씨 크기에따라서 늘어나도록
	public LineTextManager getLineTextm(){
		return lineTextm;
	}
	//PKY 08061801 E LineText모델 자동으로 글씨 크기에따라서 늘어나도록

	public  MessageCommunicationModel getMessageCommunicationModelType(String type) {
		for(int i=0;i<lineTextm.getLineTextModels().size();i++){
			MessageCommunicationModel m = (MessageCommunicationModel)lineTextm.getLineTextModels().get(i);
			if(type.equals(m.getType())){
				return m;
			}
		}
		return null;
	}


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

	public void setLocation(Point p) {

		int y = 0;
		if (this.fi != null && this.isAutoMode) {
//			fi.translateToRelative(p);
fi.translateToAbsolute(p);
//if(fi instanceof ActionFigure){
//	ActionFigure af=(ActionFigure)fi;
//y = af.getY();
//}

//p.y = p.y - y;
		}
		if(this.getLineModel()!=null){
			UMLModel s = this.getLineModel().getSource();
			UMLModel t = this.getLineModel().getTarget();

		}
		location = p;
		firePropertyChange(ID_LOCATION, null, p); //$NON-NLS-1$
		isAutoMode = false;
		if(this.getLineModel()!=null){
			UMLModel s = this.getLineModel().getSource();
			UMLModel t = this.getLineModel().getTarget();
			/*
			 * angle = 0 오른쪽
			 * angle = 1 아래
			 * angle = 2 왼쪽
			 * angle = 3 위
			 */
			if(s!=null&&t!=null)//PKY 08061801 S 시퀀스 메시지 위치가 저장될수있도록 수정
			if(s.getLocation().y+s.size.height<t.getLocation().y){
				if(s.getLocation().x<=t.getLocation().x+t.getSize().width
						&&
						s.getLocation().x+s.getSize().width>=t.getLocation().x){
					this.setAngle("1");
				}
				else if(s.getLocation().x>t.getLocation().x+t.getSize().width){
					this.setAngle("2");
				}
				else if(s.getLocation().x+s.getSize().width<t.getLocation().x){
					this.setAngle("0");
				}
			}
			else  if(s.getLocation().y>t.getLocation().y+t.getSize().height){
				if(s.getLocation().x<=t.getLocation().x+t.getSize().width
						&&
						s.getLocation().x+s.getSize().width>=t.getLocation().x){
					this.setAngle("3");
				}
				else if(s.getLocation().x>t.getLocation().x+t.getSize().width){
					this.setAngle("2");
				}
				else if(s.getLocation().x+s.getSize().width<t.getLocation().x){
					this.setAngle("0");
				}
			}
			else {
				if(s.getLocation().x>t.getLocation().x+t.getSize().width){
					this.setAngle("2");
				}
				else if(s.getLocation().x+s.getSize().width<t.getLocation().x){
					this.setAngle("0");
				}
			}



		}
	}

	public void setAngle(String p){
		lineTextm.setAngle(p);
	}

	public void setLineModel(LineModel p){
		this.lineModel = p;
	}

	public LineModel getLineModel(){
		return this.lineModel;
	}

	public void update() {
	}

	public String toString() {
		return "";
	}

	public int getLength(){
		return this.lineTextm.getLineTextModels().size();
	}

	public void setUMLDataModel(UMLDataModel p) {
		uMLDataModel = p;
	}

	public void setTreeModel(UMLTreeModel p) {
		super.setTreeModel(p);
		name.setTreeModel(p);
	}

	public void removeAll() {
		try {
			if (this.children.size() == 0)
				return;
			while (this.children.size() != 0) {
				if (this.children.size() == 0) {
					break;
				}
				Object obj = this.children.get(0);
				if (obj instanceof AttributeElementModel) {
					this.removeChild((AttributeElementModel)this.children.get(0));
				}
				else if (obj instanceof OperationElementModel) {
					this.removeChild((OperationElementModel)this.children.get(0));
				}
				else if (obj instanceof MessageCommunicationModel) {
					this.removeChild((MessageCommunicationModel)this.children.get(0));
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Object clone() {
		LineTextModel clone = new LineTextModel();
		for (int i = 0; i < this.lineTextm.getLineTextModels().size(); i++) {
			MessageCommunicationModel lt = (MessageCommunicationModel)this.lineTextm.getLineTextModels().get(i);
			clone.addChild((MessageCommunicationModel)lt.clone());
		}
		//		clone.set ;
		return clone;
	}

	public int getNameIndex() {
		return nameIndex;
	}

	public void setNameIndex(int nameIndex) {
		this.nameIndex = nameIndex;
	}
	//PKY 08051401 S 라인 name변경하면 이름이 계속 하단에 추가되어 들어가는문제
	public boolean isName() {
		return isName;
	}

	public void setName(boolean isName) {
		this.isName = isName;
	}
	//PKY 08051401 E 라인 name변경하면 이름이 계속 하단에 추가되어 들어가는문제

	//PKY 08052801 S LineOladPoint 추가

	public N3ConnectionLocator getN3ConnectionLocator() {
		return N3ConnectionLocator;
	}

	public void setN3ConnectionLocator(N3ConnectionLocator connectionLocator) {
		N3ConnectionLocator = connectionLocator;
	}
	//PKY 08052801 E LineOladPoint 추가
}
