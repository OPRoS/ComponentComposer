package kr.co.n3soft.n3com.model.comm;

import java.util.ArrayList;

import kr.co.n3soft.n3com.model.activity.SubPartitonModel;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.draw2d.BorderLayout;

public class FrameModel extends UMLDiagramModel {
	static final long serialVersionUID = 1;
	public static int count = 0;
	//	ElementLabelModel name = null;
	//	ElementLabelModel streotype = null;
	private Integer layout = null;
	private int parentLayout = 0;
	private String type = "";
	private String fragmentName="";
	private java.util.ArrayList condition = new java.util.ArrayList();
	private N3EditorDiagramModel refDiagram = null;
	int pType = -1;

	private NodeContainerModel diagram = new NodeContainerModel();


	public FrameModel() {
		size.height = 200;
		size.width = 300;
		diagram.setAcceptParentModel(this);
		diagram.setParentModel(this);
		diagram.setLayout(BorderLayout.CENTER);
		diagram.setParentLayout(1);
//		this.addChild(diagram);
//		diagram = new TempBorderLayoutContainerModel();
		//PKY 08091003 S
		if(ProjectManager.getInstance()!=null)
		ProjectManager.getInstance().addFrameModel(this);
		//PKY 08091003 E

	}
	public void setPType(int p){
		this.pType = p;
	}
	public int getPType(){
		return this.pType;
	}

	public void setType(String p){
		this.type = p;
	}
	public String getType(){
		return this.type;
	}

	public void setFragmentName(String p){
		this.fragmentName = p;
	}
	public String getFragmentName(){
		return this.fragmentName;
	}

	public void setFragmentCondition(java.util.ArrayList p){
		condition = p;
	}
	public ArrayList getFragmentCondition(){
		return condition;
	}

	public void setNodeContainerModel(NodeContainerModel p){
		this.diagram = p;

	}

	public NodeContainerModel getNodeContainerModel(){
		return this.diagram;
	}

	public void setN3EditorDiagramModel(N3EditorDiagramModel p){
		this.refDiagram = p;
		p.frameModels.add(this);
		this.refreshDiagram();

	}
	//20080326 PKY S 다이어그램 드래그 시 드래그한 다이어그램 정보 변경에 따른 반영이 안되는문제
	public void refreshDiagram(N3EditorDiagramModel p){
		this.refDiagram = p;
		this.condition.clear();
		this.setFragmentName(p.getName());//PKY 08080501 S 트리에서 다이어그램 자체를 다른 다이어그램에 드레그 할 경우 프로퍼티에서 이름 변경시 변경되지 않고, 직접 드레그한 다이어그램의 이름을 변경해도 드레그한 모델의 이름은 병경되지 않음
		for(int i=0; i<this.refDiagram.getChildren().size();i++){
			Object obj = this.refDiagram.getChildren().get(i);
			if(obj instanceof UMLModel){
				UMLModel um = (UMLModel)obj;
			
				int type = ProjectManager.getInstance().getModelType(um, -1);
				if(type!=-1){
					FrameItem fi = new FrameItem();
					fi.setType(type);
					//PKY 08091004 S
					if(um instanceof FrameModel){
						if(((FrameModel)um).getN3EditorDiagramModel()!=null){
							FrameModel frameModel = (FrameModel)um;
							fi.setName(frameModel.getN3EditorDiagramModel().getName());
						}
					}else
					fi.setName(um.getName());
					//PKY 08091004 E
					if(!(um instanceof SubPartitonModel))//PKY 08091203 S
					this.condition.add(fi);

				}
			
			}
		}
		firePropertyChange(this.ID_REFRESH, null, null); //$NON-NLS-1$
	}
	//20080326 PKY E 다이어그램 드래그 시 드래그한 다이어그램 정보 변경에 따른 반영이 안되는문제

	public void refreshDiagram(){

		for(int i=0; i<this.refDiagram.getChildren().size();i++){
			Object obj = this.refDiagram.getChildren().get(i);
			if(obj instanceof UMLModel){
				UMLModel um = (UMLModel)obj;
				int type = ProjectManager.getInstance().getModelType(um, -1);
				if(type!=-1){
					FrameItem fi = new FrameItem();
					fi.setType(type);
					fi.setName(um.getName());
					this.condition.add(fi);

				}
			}
		}
		firePropertyChange(this.ID_REFRESH, null, null); //$NON-NLS-1$
	}

//	public void 

public N3EditorDiagramModel getN3EditorDiagramModel(){
	return this.refDiagram;
}

public void changeProperty(){
	firePropertyChange(ID_CHANGE_PROPERTY, null, null); //$NON-NLS-1$
}

public static String TERMINALS_OUT[] = new String[] {
	"1", "2", "3", "4", //$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
	"5", "6", "7", "8"
}; //$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$

public static String TERMINALS_IN[] = new String[] {
	"A", "B", "C", "D", //$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
	"E", "F", "G", "H"
}; //$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$

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

public void update() {
	//		Enumeration elements = outputs.elements();
	//		Wire w;
	//		int val = 0;
	//		while (elements.hasMoreElements()) {
	//			w = (Wire) elements.nextElement();
	////			if (w.getSourceTerminal().equals(terminal) && this.equals(w.getSource()))
	//				w.setValue(false);
	//		}
	//		
	//		for (int i=0; i<8;i++)
	//			setOutput(TERMINALS_OUT[i],getInput(TERMINALS_IN[i]));
}
//	public String toString() {
//		return getStreotype()+" "+this.getName();
////		return elementLabel.getLabelContents();
//	}
//	 public Object clone(){
//		 ActorModel clone = new ActorModel();
//		 return clone();
//	 }
}
