package kr.co.n3soft.n3com.model.activity;

import kr.co.n3soft.n3com.model.comm.ElementLabelModel;
import kr.co.n3soft.n3com.model.comm.LineModel;
import kr.co.n3soft.n3com.model.comm.UMLDataModel;
import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;

public class FlowFinalModel extends TextAttachModel {
    static final long serialVersionUID = 1;
    public static int count = 0;
    ElementLabelModel name = null;
    //	ElementLabelModel streotype = null;
    private Integer layout = null;
    private int parentLayout = 0;

    //	static{
    //		descriptors = new IPropertyDescriptor[]{
    //				
    ////			new PropertyDescriptor(ID_SIZE, LogicMessages.PropertyDescriptor_LogicSubPart_Size),
    ////			new PropertyDescriptor(ID_LOCATION,LogicMessages.PropertyDescriptor_LogicSubPart_Location),
    //			new PropertyDescriptor(ID_NAME,N3MessageFactory.getInstance().getMessage(N3MessageFactory.PropertyDescriptor_Index_Name))
    ////			new PropertyDescriptor("rrr","kkkk")
    //		};
    //	}
    public FlowFinalModel() {
        name = new ElementLabelModel();
        name.setType("NAME");
        this.size.height = 20;
        this.size.width = 20;
        this.uMLDataModel.setElementProperty(name.getType(), name);
        this.setAttachElementLabelModel(name);
    }

    public FlowFinalModel(UMLDataModel p) {
        name = (ElementLabelModel)p.getElementProperty("NAME");
        this.setUMLDataModel(p);
        this.setAttachElementLabelModel(name);
    }

    public ElementLabelModel getElementLabelModel() {
        return this.name;
    }

    public FlowFinalModel(ExceptionModel p) {
        this.size.height = 20;
        this.size.width = 20;
        name = new ElementLabelModel();
        name.setType("NAME");
        this.uMLDataModel.setElementProperty(name.getType(), name);
        this.setAttachElementLabelModel(name);
    }

    //	public void setExceptionModel(ExceptionModel p){
    //		this.emp = p;
    //	}
    //	
    //	public ExceptionModel getExceptionModel(){
    //		return this.emp;
    //	}
    public void setName(String uname) {
        name.setLabelContents(uname);
    	//PKY 08071601 S 모델브라우져에서 모델을 추가하려고하면 추가되지않는 문제 수정
    	if(!uname.trim().equals(""))
    		if( this.getUMLTreeModel()!=null)     	
    			if(ProjectManager.getInstance()!=null&&  ProjectManager.getInstance().getModelBrowser()!=null){
    				this.getUMLTreeModel().setName(uname);
    				ProjectManager.getInstance().getModelBrowser().refresh(this.getUMLTreeModel());
    			}

    }
    	//PKY 08071601 E 모델브라우져에서 모델을 추가하려고하면 추가되지않는 문제 수정

      

    //	public void setStreotype(String uname){
    //		streotype.setLabelContents(uname);
    //	}
    public String getName() {
        return name.getLabelContents();
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

    //	public void setLocation(Point p) {
    //		//port
    //		System.out.println("p1:"+p);
    //		if (location.equals(p))
    //			return;
    //		location = p;
    //		Point pt3 = new Point(p.x,p.y-10);
    //		name.setLocation(pt3);
    //		firePropertyChange("location", null, p);  //$NON-NLS-1$
    //	}
    public Object clone() {
        FlowFinalModel clone = new FlowFinalModel((UMLDataModel)this.getUMLDataModel().clone());
        //		clone.setUMLDataModel((UMLDataModel)this.getUMLDataModel().clone());
        clone.setSize(this.getSize());
        clone.setBackGroundColor(this.getBackGroundColor());
        clone.setLocation(this.getLocation());
        clone.sourceModel = this;
        ProjectManager.getInstance().setTempCopyMap(clone.sourceModel.getID(), clone);
        for (int i1 = 0; i1 < this.getSourceConnections().size(); i1++) {
            LineModel li = (LineModel)this.getSourceConnections().get(i1);
            LineModel liCopy = (LineModel)li.clone();
            ProjectManager.getInstance().addSelectLineModel(liCopy);
        }
        return clone;
    }

    public void setSize(Dimension d) {
        if (size.equals(d))
            return;
        //		if(33 !=d.height)
        //			return;
        //		if(31 !=d.width)
        //			return;
        //20080326 PKY S 복사 시에 사이즈 커지는 문제 
//      d.height = 33;
//      d.width = 31;
    //20080326 PKY E 복사 시에 사이즈 커지는 문제 
        size = d;
        firePropertyChange(ID_SIZE, null, size); //$NON-NLS-1$
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
