package kr.co.n3soft.n3com.model.communication;

import org.eclipse.draw2d.geometry.Dimension;

import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;

public class ArrowModel extends UMLDiagramModel {
    static final long serialVersionUID = 1;
    public static int count = 0;
    //	ElementLabelModel name = null;
    //	ElementLabelModel streotype = null;
    private Integer layout = null;
    private int parentLayout = 0;
    private boolean back = false;

    //	static{
    //		descriptors = new IPropertyDescriptor[]{
    //				
    ////			new PropertyDescriptor(ID_SIZE, LogicMessages.PropertyDescriptor_LogicSubPart_Size),
    ////			new PropertyDescriptor(ID_LOCATION,LogicMessages.PropertyDescriptor_LogicSubPart_Location),
    //			new PropertyDescriptor(ID_NAME,N3MessageFactory.getInstance().getMessage(N3MessageFactory.PropertyDescriptor_Index_Name))
    ////			new PropertyDescriptor("rrr","kkkk")
    //		};
    //	}
    public ArrowModel() {
    	 size.width = 50;
        size.height = 15;
    }
    
    public ArrowModel(boolean isBack) {
    	this.back = isBack;
   	 	size.width = 50;
   	 	size.height = 15;
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
       
    }
    //	public String toString() {
    //		return getStreotype()+" "+this.getName();
    ////		return elementLabel.getLabelContents();
    //	}
    //	 public Object clone(){
    //		 ActorModel clone = new ActorModel();
    //		 return clone();
    //	 }

	public boolean isBack() {
		return back;
	}

	public void setBack(boolean back) {
		this.back = back;
	}
}

