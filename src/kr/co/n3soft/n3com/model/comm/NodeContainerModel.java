package kr.co.n3soft.n3com.model.comm;

import kr.co.n3soft.n3com.figures.ActionFigure;
import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;

public class NodeContainerModel extends UMLDiagramModel {
    static final long serialVersionUID = 1;
    public static int count = 0;
    ElementLabelModel name = null;
    ElementLabelModel streotype = null;
    private Integer layout = null;
    private int parentLayout = 0;
    //inmsg
    public ActionFigure fi = null;
    public java.util.ArrayList copyLines = new java.util.ArrayList();

    public NodeContainerModel() {
        this.size.height = 100;
        this.size.width = 100;
    }

    public void setName(String uname) {
    }

    public String getName() {
    	if(name!=null)
        return name.getLabelContents();
    	return "";
    }

    public static String TERMINALS_OUT[] = new String[] {
        "1", "2", "3", "4", //$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
            "5", "6", "7", "8"
    }; //$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$

    public static String TERMINALS_IN[] = new String[] {
        "A", "B", "C", "D", //$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
            "E", "F", "G", "H"
    }; //$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$

    public void update() {
    }

    public String toString() {
        return "";
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

    public boolean isContainModel(UMLModel p) {
        for (int i = 0; i < this.getChildren().size(); i++) {
            Object obj = this.getChildren().get(i);
            if (obj instanceof UMLModel) {
                UMLModel child = (UMLModel)obj;
                if (p == child)
                    return true;
            }
        }
        return false;
    }
    
//    public void reload(HashMap hm){
//    	for (int i = 0; i < this.getChildren().size(); i++) {
//            Object obj = this.getChildren().get(i);
//            if (obj instanceof UMLModel) {
//                UMLModel child = (UMLModel)obj;
//               
//            }
//        }
//    }
    
    public void reload(NodeContainerModel clone ){
    	
    }

    public Object clone() {
        NodeContainerModel clone = new NodeContainerModel();
        for (int i = 0; i < this.getChildren().size(); i++) {
            Object obj = this.getChildren().get(i);
            if (obj instanceof UMLModel) {
                UMLModel child = (UMLModel)obj;
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
}
