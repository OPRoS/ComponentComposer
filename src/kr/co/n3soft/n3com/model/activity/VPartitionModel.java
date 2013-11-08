package kr.co.n3soft.n3com.model.activity;

import kr.co.n3soft.n3com.model.comm.ElementLabelModel;
import kr.co.n3soft.n3com.model.comm.UMLDataModel;
import kr.co.n3soft.n3com.model.comm.UMLElementModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;
import kr.co.n3soft.n3com.model.usecase.BoundaryModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.PositionConstants;

public class VPartitionModel extends UMLDiagramModel {
    static final long serialVersionUID = 1;
    public static int count = 0;
    ElementLabelModel name = null;
    ElementLabelModel streotype = null;
    BoundaryModel boundaryModel = null;
    java.util.List temp_childs = null;

    //	static{
    //		descriptors = new IPropertyDescriptor[]{
    //				
    ////			new PropertyDescriptor(ID_SIZE, LogicMessages.PropertyDescriptor_LogicSubPart_Size),
    ////			new PropertyDescriptor(ID_LOCATION,LogicMessages.PropertyDescriptor_LogicSubPart_Location),
    //			new PropertyDescriptor(ID_NAME,N3MessageFactory.getInstance().getMessage(N3MessageFactory.PropertyDescriptor_Index_Name))
    ////			new PropertyDescriptor("rrr","kkkk")
    //		};
    //	}
    public VPartitionModel() {
        boundaryModel = new BoundaryModel();
        boundaryModel.setParentModel(this);
        boundaryModel.setLayout(BorderLayout.CENTER);
        boundaryModel.setParentLayout(1);
        this.addChild(boundaryModel);
        streotype = new ElementLabelModel(PositionConstants.CENTER);
        size.width = 400;
        size.height = 200;
        name = new ElementLabelModel(PositionConstants.HORIZONTAL);
        name.setType("NAME");
        //		name.setLabelContents("바운더리");
        name.setLayout(BorderLayout.LEFT);
        name.setParentLayout(1);
        this.addChild(name);
        this.uMLDataModel.setElementProperty(name.getType(), name);
    }

    public VPartitionModel(UMLDataModel p) {
        boundaryModel = new BoundaryModel();
        boundaryModel.setParentModel(this);
        boundaryModel.setLayout(BorderLayout.CENTER);
        boundaryModel.setParentLayout(1);
        this.addChild(boundaryModel);
        streotype = new ElementLabelModel(PositionConstants.CENTER);
        size.width = 100;
        size.height = 200;
        name = (ElementLabelModel)p.getElementProperty("NAME");
        this.addChild(name);
        this.setUMLDataModel(p);
    }

    public boolean isChild(UMLElementModel child) {
        return temp_childs.contains(child);
    }

    public void setName(String uname) {
        name.setLabelContents(uname);
    }

    public void setStreotype(String uname) {
        streotype.setLabelContents(uname);
    }

    public String getName() {
        return name.getLabelContents();
    }

    public String getStreotype() {
        return "<<" + streotype.getLabelContents() + ">>";
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
        return getStreotype() + " " + this.getName();
        //		return elementLabel.getLabelContents();
    }

    public void setUMLDataModel(UMLDataModel p) {
        uMLDataModel = p;
    }

    public void setTreeModel(UMLTreeModel p) {
        super.setTreeModel(p);
        name.setTreeModel(p);
    }

    public BoundaryModel getBoundaryModel() {
        return this.boundaryModel;
    }

    public Object clone() {
        HPartitionModel clone = new HPartitionModel((UMLDataModel)this.getUMLDataModel().clone());
        for (int i = 0; i < this.boundaryModel.getChildren().size(); i++) {
            UMLModel um = (UMLModel)this.boundaryModel.getChildren().get(i);
            clone.boundaryModel.addChild((UMLElementModel)um.clone());
        }
        clone.setSize(this.getSize());
        clone.setLocation(this.getLocation());
        return clone;
    }
}
