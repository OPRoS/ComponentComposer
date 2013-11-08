package kr.co.n3soft.n3com.model.comm;

import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;
import org.eclipse.draw2d.BorderLayout;

public class BorderContainlModel extends UMLDiagramModel {
    static final long serialVersionUID = 1;
    public static int count = 0;
    //	ElementLabelModel name = null;
    //	ElementLabelModel streotype = null;
    private Integer layout = null;
    private int parentLayout = 0;
    private UMLModel centerModel = null;
    private UMLModel leftModel = null;
    
    private UMLModel topModel = null;
    private UMLModel bottomModel = null;
    private UMLModel rightModel = null;

    public BorderContainlModel() {
        size.width = 100;
        size.height = 20;
    }

    public UMLModel getCenterModel() {
        return this.centerModel;
    }

    public UMLModel getLeftModel() {
        return this.leftModel;
    }

    public UMLModel getRightModel() {
        return this.rightModel;
    }

    public UMLModel getBottomModel() {
        return this.bottomModel;
    }

    public UMLModel getTopModel() {
        return this.topModel;
    }

    public void setCenterModel(UMLModel p) {
        if (p == null)
            return;
        this.centerModel = p;
        //		centerModel.setParentModel(this);
        centerModel.setLayout(BorderLayout.CENTER);
        centerModel.setParentLayout(1);
        //		this.uMLDataModel.setElementProperty(String.valueOf(BorderLayout.CENTER), centerModel);
        this.addChild(centerModel);
    }

    public void setLeftModel(UMLModel p) {
        if (p == null)
            return;
        this.leftModel = p;
        //		leftModel.setParentModel(this);
        leftModel.setLayout(BorderLayout.LEFT);
        leftModel.setParentLayout(1);
        //		this.uMLDataModel.setElementProperty(String.valueOf(BorderLayout.LEFT), leftModel);
        this.addChild(leftModel);
    }

    public void setRightModel(UMLModel p) {
        if (p == null)
            return;
        this.rightModel = p;
        //		rightModel.setParentModel(this);
        rightModel.setLayout(BorderLayout.RIGHT);
        rightModel.setParentLayout(1);
        this.addChild(rightModel);
        //		this.uMLDataModel.setElementProperty(String.valueOf(BorderLayout.RIGHT), rightModel);
    }

    public void setBottomModel(UMLModel p) {
        if (p == null)
            return;
        this.bottomModel = p;
        //		bottomModel.setParentModel(this);
        bottomModel.setLayout(BorderLayout.BOTTOM);
        bottomModel.setParentLayout(1);
        this.addChild(bottomModel);
        //		this.uMLDataModel.setElementProperty(String.valueOf(BorderLayout.BOTTOM), bottomModel);
    }

    public void setTopModel(UMLModel p) {
        if (p == null)
            return;
        this.topModel = p;
        //		topModel.setParentModel(this);
        topModel.setLayout(BorderLayout.TOP);
        topModel.setParentLayout(1);
        this.addChild(topModel);
        //		this.uMLDataModel.setElementProperty(String.valueOf(BorderLayout.TOP), topModel);
    }

    public BorderContainlModel(UMLDataModel p) {
        this.setTopModel((UMLModel)p.getElementProperty(String.valueOf(BorderLayout.TOP)));
        this.setBottomModel((UMLModel)p.getElementProperty(String.valueOf(BorderLayout.BOTTOM)));
        this.setRightModel((UMLModel)p.getElementProperty(String.valueOf(BorderLayout.RIGHT)));
        this.setLeftModel((UMLModel)p.getElementProperty(String.valueOf(BorderLayout.LEFT)));
        this.setCenterModel((UMLModel)p.getElementProperty(String.valueOf(BorderLayout.CENTER)));
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
    public Object clone() {
        BorderContainlModel clone = new BorderContainlModel();
        try {
            clone.setTopModel((UMLModel)this.uMLDataModel.getElementProperty(String.valueOf(BorderLayout.TOP)));
            clone.setBottomModel((UMLModel)this.uMLDataModel.getElementProperty(String.valueOf(BorderLayout.BOTTOM)));
            clone.setRightModel((UMLModel)this.uMLDataModel.getElementProperty(String.valueOf(BorderLayout.RIGHT)));
            clone.setLeftModel((UMLModel)this.uMLDataModel.getElementProperty(String.valueOf(BorderLayout.LEFT)));
            clone.setCenterModel((UMLModel)this.uMLDataModel.getElementProperty(String.valueOf(BorderLayout.CENTER)));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return clone();
    }
}
