package kr.co.n3soft.n3com.model.usecase;

import kr.co.n3soft.n3com.model.comm.ElementLabelModel;
import kr.co.n3soft.n3com.model.comm.IUpdateType;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.comm.UpdateEvent;
import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;

public class ActorModel extends UMLDiagramModel {
    static final long serialVersionUID = 1;
    public static int count = 0;
    //	ElementLabelModel name = null;
    //	ElementLabelModel streotype = null;
    private Integer layout = null;
    private int parentLayout = 0;
    public UMLModel typeRefModel = null;//2008040702PKY S
    ElementLabelModel name = null;

    //	static{
    //		descriptors = new IPropertyDescriptor[]{
    //				
    ////			new PropertyDescriptor(ID_SIZE, LogicMessages.PropertyDescriptor_LogicSubPart_Size),
    ////			new PropertyDescriptor(ID_LOCATION,LogicMessages.PropertyDescriptor_LogicSubPart_Location),
    //			new PropertyDescriptor(ID_NAME,N3MessageFactory.getInstance().getMessage(N3MessageFactory.PropertyDescriptor_Index_Name))
    ////			new PropertyDescriptor("rrr","kkkk")
    //		};
    //	}
    public ActorModel() {
        //		streotype= new ElementLabelModel(PositionConstants.CENTER);
        //		size.width = 50;
        //		size.height= 100;
        //		name = new ElementLabelModel(PositionConstants.CENTER);
        //		name.setType("NAME");
        //		name.setLabelContents("����");
        //		name.setLayout(BorderLayout.BOTTOM);
        //		name.setParentLayout(1);
        //		this.addChild(name);
    }

    //	public void setName(String uname){
    //		name.setLabelContents(uname);
    //	}
    //	public void setStreotype(String uname){
    //		streotype.setLabelContents(uname);
    //	}
    //	public String getName(){
    //		return name.getLabelContents();
    //	}
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
    
    public void setName(String _name) {
		try{
			uMLDataModel.setName(_name);
			name.setLabelContents(_name);
		    UpdateEvent e = new UpdateEvent(IUpdateType.RENAME_TYPE, null);
	        name.fireChildUpdate(e);
		}catch(Exception e){
			e.printStackTrace();
		}
    }
    public void setRefName(UMLModel p) {
    	try{
        this.name.setRefModel(p);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
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
