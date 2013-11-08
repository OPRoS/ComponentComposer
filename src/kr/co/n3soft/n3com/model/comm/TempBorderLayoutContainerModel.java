package kr.co.n3soft.n3com.model.comm;

import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;
import kr.co.n3soft.n3com.model.umlclass.CompartmentModel;

import org.eclipse.draw2d.BorderLayout;

public class TempBorderLayoutContainerModel extends UMLDiagramModel {
    static final long serialVersionUID = 1;
    public static int count = 0;
    //	ElementLabelModel name = null;
    //	ElementLabelModel streotype = null;
    private Integer layout = null;
    private int parentLayout = 0;
    TempModel tempModel = null;
    UMLNamePackageModel uMLNamePackageModel = null;
//    CompartmentModel borderContainlModel = new CompartmentModel();

    //	static{
    //		descriptors = new IPropertyDescriptor[]{
    //				
    ////			new PropertyDescriptor(ID_SIZE, LogicMessages.PropertyDescriptor_LogicSubPart_Size),
    ////			new PropertyDescriptor(ID_LOCATION,LogicMessages.PropertyDescriptor_LogicSubPart_Location),
    //			new PropertyDescriptor(ID_NAME,N3MessageFactory.getInstance().getMessage(N3MessageFactory.PropertyDescriptor_Index_Name))
    ////			new PropertyDescriptor("rrr","kkkk")
    //		};
    //	}
    public TempBorderLayoutContainerModel() {
    	
        tempModel = new TempModel();
        tempModel.setParentModel(this.getParentModel());
        tempModel.setLayout(BorderLayout.RIGHT);
        tempModel.setParentLayout(1);
        uMLNamePackageModel = new UMLNamePackageModel();
        uMLNamePackageModel.setParentModel(this.getParentModel());
        uMLNamePackageModel.setLayout(BorderLayout.CENTER);
        uMLNamePackageModel.setParentLayout(1);
        this.addChild(uMLNamePackageModel);
        this.addChild(tempModel);
    }

    public TempBorderLayoutContainerModel(UMLDataModel p) {
        tempModel = new TempModel();
        tempModel.setParentModel(this.getParentModel());
        tempModel.setLayout(BorderLayout.RIGHT);
        tempModel.setParentLayout(1);
        uMLNamePackageModel = new UMLNamePackageModel(p);
        uMLNamePackageModel.setParentModel(this.getParentModel());
        uMLNamePackageModel.setLayout(BorderLayout.CENTER);
        uMLNamePackageModel.setParentLayout(1);
        this.addChild(uMLNamePackageModel);
        this.addChild(tempModel);
    }

    public UMLNamePackageModel getUMLNamePackageModel() {
        return uMLNamePackageModel;
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
