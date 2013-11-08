package kr.co.n3soft.n3com.model.umlclass;

import java.util.ArrayList;
import kr.co.n3soft.n3com.model.comm.AttributeElementModel;
import kr.co.n3soft.n3com.model.comm.ElementLabelModel;
import kr.co.n3soft.n3com.model.comm.OperationElementModel;
import kr.co.n3soft.n3com.model.comm.UMLDataModel;
import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;
import kr.co.n3soft.n3com.model.usecase.ActorModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.PositionConstants;

public class CompartmentModel extends UMLDiagramModel {
    static final long serialVersionUID = 1;
    public static int count = 0;
    ElementLabelModel name = null;
    ElementLabelModel streotype = null;
    ActorModel actorModel = null;
    private String type = "ATTR";
    private Integer layout = null;
    private int parentLayout = 0;
    boolean isBorder = true;
    java.util.ArrayList items = new ArrayList();

    public CompartmentModel() {
        size.width = 100;
        size.height = 40;
    }

    public void setCompartmentModelType(String p) {
        this.type = p;
        LOGIC_ICON	= ProjectManager.getInstance().getImage(ProjectManager.getInstance().getElementType(this, -1));
    }

    public String getCompartmentModelType() {
        return this.type;
    }

    public void setIsBorder(boolean p) {
        this.isBorder = p;
    }

    public boolean getIsBorder() {
        return this.isBorder;
    }

    public CompartmentModel(UMLDataModel p) {
        actorModel = new ActorModel();
        actorModel.setParentModel(this);
        actorModel.setLayout(BorderLayout.CENTER);
        actorModel.setParentLayout(1);
        size.width = 100;
        size.height = 40;
        this.addChild(actorModel);
        streotype = new ElementLabelModel(PositionConstants.CENTER);
        name = (ElementLabelModel)p.getElementProperty("NAME");
        this.addChild(name);
        this.setUMLDataModel(p);
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

    public void setActorModel(ActorModel p) {
        this.actorModel = p;
    }

    public ActorModel getActorModel() {
        return this.actorModel;
    }

    public void setName(String uname) {
        name.setLabelContents(uname);
    }

    public void setStreotype(String uname) {
        streotype.setLabelContents(uname);
    }

    public String getName() {
        if (name != null)
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
    }

    public String toString() {
        return "";
    }

    public void setUMLDataModel(UMLDataModel p) {
        uMLDataModel = p;
    }

    public void setTreeModel(UMLTreeModel p) {
        super.setTreeModel(p);
        name.setTreeModel(p);
    }

    public void removeAll() {
        try {
            if (this.children.size() == 0)
                return;
            while (this.children.size() != 0) {
                if (this.children.size() == 0) {
                    break;
                }
                Object obj = this.children.get(0);
                if (obj instanceof AttributeElementModel) {
                    this.removeChild((AttributeElementModel)this.children.get(0));
                }
                else if (obj instanceof OperationElementModel) {
                    this.removeChild((OperationElementModel)this.children.get(0));
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object clone() {
        CompartmentModel clone = new CompartmentModel();
        return clone;
    }
}
