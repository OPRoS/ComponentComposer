package kr.co.n3soft.n3com.model.activity;

import kr.co.n3soft.n3com.model.comm.ElementLabelModel;
import kr.co.n3soft.n3com.model.comm.IPort;
import kr.co.n3soft.n3com.model.comm.LineModel;
import kr.co.n3soft.n3com.model.comm.PortContainerModel;
import kr.co.n3soft.n3com.model.comm.PortModel;
import kr.co.n3soft.n3com.model.comm.UMLContainerModel;
import kr.co.n3soft.n3com.model.comm.UMLDataModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;
import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;

public class ExceptionModel extends PortContainerModel {
    static final long serialVersionUID = 1;
    public static int count = 0;
    ElementLabelModel name = null;
    ElementLabelModel streotype = null;

    //port
    //	public PortModel exceptionObjectNode = null;
    public ExceptionModel() {
        streotype = new ElementLabelModel(PositionConstants.CENTER);
        streotype.setType("STREOTYPE");
        streotype.setLayout(BorderLayout.TOP);
        streotype.setParentLayout(1);
        size.width = 130;
        size.height = 80;
        name = new ElementLabelModel(PositionConstants.CENTER);
        name.setType("NAME");
        name.setLayout(BorderLayout.CENTER);
        name.setParentLayout(1);
        this.addChild(name);
        //		exceptionObjectNode = new PortModel(this);
        this.createNewPort(new PortModel(this));
        //		this.createNewPort(new PortModel(this));
        //		this.createPort(new PortModel(this));
        this.uMLDataModel.setElementProperty(name.getType(), name);
        this.uMLDataModel.setElementProperty(streotype.getType(), streotype);
        this.uMLDataModel.setElementProperty("port", this.getPorts());
    }

    public ExceptionModel(UMLDataModel p) {
        size.width = 130;
        size.height = 80;
        streotype = new ElementLabelModel(PositionConstants.CENTER);
        name = (ElementLabelModel)p.getElementProperty("NAME");
        java.util.ArrayList ports = (java.util.ArrayList) p.getElementProperty("port");
        for (int i = 0; i < ports.size(); i++) {
            PortModel pm = (PortModel)ports.get(i);
            pm.setPortContainerModel(this);
        }
        this.setPorts(ports);
        //		exceptionObjectNode.setPortContainerModel(this);
        this.addChild(name);
        this.setUMLDataModel(p);
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

    public Object clone() {
        ExceptionModel clone = new ExceptionModel((UMLDataModel)this.getUMLDataModel().clone());
        //		clone.setUMLDataModel((UMLDataModel)this.getUMLDataModel().clone());
        clone.setSize(this.getSize());
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
    //	public void setLocation(Point p) {
    //		//port
    //		System.out.println("p1:"+p);
    //		if (location.equals(p))
    //			return;
    //		location = p;
    //		
    //		
    //		firePropertyChange("location", null, p);  //$NON-NLS-1$
    //		
    //		this.exceptionObjectNode.move(location);
    //	}
    //	public void createPort(IPort ip){
    //		exceptionObjectNode = (PortModel)ip;
    //	}
    //	public void addPort(IPort ip,UMLContainerModel p){
    //		PortModel exceptionObjectNode = (PortModel)this.getPort();
    //		p.addChild(exceptionObjectNode.getElementLabelModel());
    //		Point pt = new Point(this.getLocation().x,this.getLocation().y-10);
    //		exceptionObjectNode.setLocation(pt);
    //		if(exceptionObjectNode.getElementLabelModel().getLabelContents().trim().equals(""))
    //			exceptionObjectNode.getElementLabelModel().setLabelContents("objectnode");
    //		exceptionObjectNode.getElementLabelModel().setSize(new Dimension(exceptionObjectNode.getElementLabelModel().getSize().width,15));
    //		
    //	
    //	}
    //	public void removePort(IPort ip,UMLContainerModel p){
    ////		ExceptionObjectNode exceptionObjectNode = (ExceptionObjectNode)this.getPort();
    //		p.removeChild(exceptionObjectNode);
    //		p.removeChild(exceptionObjectNode.getElementLabelModel());
    //		
    //	}
    //	public void setPortLocation(Point p){
    //		
    //	}
    //	public UMLModel getPort(){
    //		return this.exceptionObjectNode;
    //	}
    //	public void createPort(IPort ip,UMLContainerModel p){
    //		PortModel exceptionObjectNode = (PortModel)this.getPort();
    //		p.addChild(exceptionObjectNode.getElementLabelModel());
    //		Point pt = new Point(this.getLocation().x,this.getLocation().y-10);
    //		exceptionObjectNode.setLocation(pt);
    //		if(exceptionObjectNode.getElementLabelModel().getLabelContents().trim().equals(""))
    //			exceptionObjectNode.getElementLabelModel().setLabelContents("objectnode");
    //		exceptionObjectNode.getElementLabelModel().setSize(new Dimension(exceptionObjectNode.getElementLabelModel().getSize().width,15));
    //		
    //		
    //	}
    //	
    //	public void undoCreatePort(IPort ip,UMLContainerModel p){
    //		this.removePort(null, p);
    //		
    //	}
    //	
    //	public void deletePort(IPort ip,UMLContainerModel p){
    //		this.removePort(null, p);
    //		
    //	}
    //	
    //	public void undoDeletePort(IPort ip,UMLContainerModel p){
    //		p.addChild(exceptionObjectNode);
    //		p.addChild(exceptionObjectNode.getElementLabelModel());
    //		
    //	}
}
