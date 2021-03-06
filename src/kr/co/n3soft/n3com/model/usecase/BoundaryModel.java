package kr.co.n3soft.n3com.model.usecase;

import java.util.List;
import kr.co.n3soft.n3com.edit.UseCaseEditPart;
import kr.co.n3soft.n3com.model.comm.ElementLabelModel;
import kr.co.n3soft.n3com.model.comm.UMLElementModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;
import org.eclipse.draw2d.geometry.Point;

public class BoundaryModel extends UMLDiagramModel {
	static final long serialVersionUID = 1;
	public static int count = 0;
	ElementLabelModel name = null;
	ElementLabelModel streotype = null;
	private Integer layout = null;
	private int parentLayout = 0;
	public java.util.ArrayList copyLines = new java.util.ArrayList();
	int maxb = 100;

	//	static{
		//		descriptors = new IPropertyDescriptor[]{
	//				
	////			new PropertyDescriptor(ID_SIZE, LogicMessages.PropertyDescriptor_LogicSubPart_Size),
	////			new PropertyDescriptor(ID_LOCATION,LogicMessages.PropertyDescriptor_LogicSubPart_Location),
	//			new PropertyDescriptor(ID_NAME,N3MessageFactory.getInstance().getMessage(N3MessageFactory.PropertyDescriptor_Index_Name))
	////			new PropertyDescriptor("rrr","kkkk")
	//		};
	//	}
	public BoundaryModel() {
		System.out.println("ddd");
		//		streotype = new ElementLabelModel(PositionConstants.CENTER);
		//		streotype.setType("");
		//		streotype.setLabelContents("jjjj");
		//		this.addChild(streotype);
		//		name = new ElementLabelModel(PositionConstants.CENTER);
		//		name.setType("NAME");
		//		name.setLabelContents("�ٿ����");
		//		
		////		name.setLayout(BorderLayout.CENTER);
		////		name.setParentLayout(1);
		//		this.addChild(name);
	}

	public void setName(String uname) {
	}

	//	public void setStreotype(String uname){
	//		streotype.setLabelContents(uname);
	//	}
	public String getName() {
		if(name!=null)
			return name.getLabelContents();
		else
			return "";
	}

	//	public String getStreotype(){
	//		return "<<"+streotype.getLabelContents()+">>";
	//	}
	public static String TERMINALS_OUT[] = new String[] {
		"1", "2", "3", "4", //$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
		"5", "6", "7", "8"
	}; //$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$

	public static String TERMINALS_IN[] = new String[] {
		"A", "B", "C", "D", //$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
		"E", "F", "G", "H"
	}; //$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$

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

	public String toString() {
		return "";
		//		return elementLabel.getLabelContents();
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


	public void removeChild(UMLElementModel child) {
		//		child.setAcceptParentModel(null);
		children.remove(child);
		fireChildRemoved(CHILDREN, child);
	}

	public Object clone() {
		BoundaryModel clone = new BoundaryModel();
		for (int i = 0; i < this.getChildren().size(); i++) {
			Object obj = this.getChildren().get(i);
			if (obj instanceof UMLModel) {
				UMLModel child = (UMLModel)obj;
				List l = ProjectManager.getInstance().getSelectNodes();
				UMLModel childClone = (UMLModel)child.clone();
				clone.addChild((UMLModel)childClone);
			}
		}
		return clone;
	}

	public UMLModel getChild(UMLModel source) {
		for (int i = 0; i < this.getChildren().size(); i++) {
			Object obj = this.getChildren().get(i);
			if (obj instanceof UMLModel) {
				UMLModel copy = (UMLModel)obj;
				if (copy.sourceModel == source) {
					return copy;
				}
			}
		}
		return null;
	}

	public int getMaxb() {
		return maxb;
	}

	public void setMaxb(int maxb) {
		this.maxb = maxb;
	}

	

	
}
