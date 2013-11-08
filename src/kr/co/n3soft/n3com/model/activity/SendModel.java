package kr.co.n3soft.n3com.model.activity;

import kr.co.n3soft.n3com.model.comm.ElementLabelModel;
import kr.co.n3soft.n3com.model.comm.LineModel;
import kr.co.n3soft.n3com.model.comm.UMLDataModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;
import kr.co.n3soft.n3com.model.usecase.UseCaseModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;
import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.PositionConstants;

public class SendModel extends UMLDiagramModel {
    static final long serialVersionUID = 1;
    public static int count = 0;
    ElementLabelModel name = null;
    ElementLabelModel streotype = null;
    //port
    public UMLModel pt = null;

    public SendModel() {
        streotype = new ElementLabelModel(PositionConstants.CENTER);
        streotype.setType("STREOTYPE");
        streotype.setLayout(BorderLayout.TOP);
        streotype.setParentLayout(1);
        streotype.setLabelContents("<<ppp>>");
        size.width = 80;
        size.height = 40;
        name = new ElementLabelModel(PositionConstants.CENTER);
        name.setType("NAME");
        name.setLayout(BorderLayout.CENTER);
        name.setParentLayout(1);
        //		this.addChild(streotype);
        this.addChild(name);
        this.uMLDataModel.setElementProperty(name.getType(), name);
        this.uMLDataModel.setElementProperty(streotype.getType(), streotype);
    }

    //	public void addChild(UMLElementModel child){
    //		
    //		try{
    //		if(child instanceof UseCaseModel){
    //			System.out.println("ddfdfdf");
    //			return;
    //		}
    //		addChild(child, -1);
    //		}
    //		catch(Exception e){
    //			e.printStackTrace();
    //		}
    //		
    //	}
    //	
    //	public void addChild(UMLElementModel child, int index){
    ////		child.setAcceptParentModel(this);
    //		if(child instanceof UseCaseModel){
    //			System.out.println("ddfdfdf");
    //			return;
    //		}
    //
    //		if (index >= 0)
    //			children.add(index,child);
    //		else
    //			children.add(child);
    //		fireChildAdded(CHILDREN, child, new Integer(index));
    //		if(child instanceof UMLDiagramModel){
    //			UMLModel childUMLModel = (UMLModel)child;
    //			if(childUMLModel.getUMLTreeModel()!=null){
    //				childUMLModel.getUMLTreeModel().addN3UMLModelDeleteListener(this);
    //			}
    //		}
    //	}
    public SendModel(UMLDataModel p) {
        size.width = 100;
        size.height = 50;
        streotype = new ElementLabelModel(PositionConstants.CENTER);
        name = (ElementLabelModel)p.getElementProperty("NAME");
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
        SendModel clone = new SendModel((UMLDataModel)this.getUMLDataModel().clone());
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
}
