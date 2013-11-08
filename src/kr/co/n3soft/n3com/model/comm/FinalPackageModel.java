package kr.co.n3soft.n3com.model.comm;

import kr.co.n3soft.n3com.model.activity.FinalStrcuturedActivityModel;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.projectmanager.OPRoSManifest;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.draw2d.BorderLayout;

public class FinalPackageModel extends UMLDiagramModel {
    static final long serialVersionUID = 1;
    public static int count = 0;
    //	ElementLabelModel name = null;
    //	ElementLabelModel streotype = null;
    private Integer layout = null;
    private int parentLayout = 0;
    ElementLabelModel name = null;
    UMLMainPackageModel uMLMainPackageModel = null;
    TempBorderLayoutContainerModel tempBorderLayoutContainerModel = null;
    ElementLabelModel streotype = null;
    N3EditorDiagramModel n3EditorDiagramModel = null;
    
   

    //	static{
    //		descriptors = new IPropertyDescriptor[]{
    //				
    ////			new PropertyDescriptor(ID_SIZE, LogicMessages.PropertyDescriptor_LogicSubPart_Size),
    ////			new PropertyDescriptor(ID_LOCATION,LogicMessages.PropertyDescriptor_LogicSubPart_Location),
    //			new PropertyDescriptor(ID_NAME,N3MessageFactory.getInstance().getMessage(N3MessageFactory.PropertyDescriptor_Index_Name))
    ////			new PropertyDescriptor("rrr","kkkk")
    //		};
    //	}
    public FinalPackageModel() {
        size.width = 150;
        size.height = 100;
        tempBorderLayoutContainerModel = new TempBorderLayoutContainerModel();
        tempBorderLayoutContainerModel.setAcceptParentModel(this);
        tempBorderLayoutContainerModel.setParentModel(this);
        tempBorderLayoutContainerModel.setLayout(BorderLayout.TOP);
        tempBorderLayoutContainerModel.setParentLayout(1);
        tempBorderLayoutContainerModel.tempModel.setParentModel(this);
        tempBorderLayoutContainerModel.uMLNamePackageModel.setParentModel(this);
        
        uMLMainPackageModel = new UMLMainPackageModel();
        uMLMainPackageModel.setParentModel(this);
        uMLMainPackageModel.setLayout(BorderLayout.CENTER);
        uMLMainPackageModel.setParentLayout(1);
        this.setUMLDataModel(tempBorderLayoutContainerModel.getUMLNamePackageModel().getUMLDataModel());
        name = (ElementLabelModel)getUMLDataModel().getElementProperty("NAME");
        streotype = (ElementLabelModel)getUMLDataModel().getElementProperty("STREOTYPE");
        this.addChild(tempBorderLayoutContainerModel);
        this.addChild(uMLMainPackageModel);
    }

    public FinalPackageModel(UMLDataModel p) {
        size.width = 150;
        size.height = 100;
        tempBorderLayoutContainerModel = new TempBorderLayoutContainerModel(p);
        tempBorderLayoutContainerModel.setParentModel(this);
        tempBorderLayoutContainerModel.setLayout(BorderLayout.TOP);
        tempBorderLayoutContainerModel.setParentLayout(1);
        tempBorderLayoutContainerModel.setAcceptParentModel(this);
        uMLMainPackageModel = new UMLMainPackageModel();
        uMLMainPackageModel.setParentModel(this);
        uMLMainPackageModel.setLayout(BorderLayout.CENTER);
        uMLMainPackageModel.setParentLayout(1);
        this.setUMLDataModel(p);
        name = (ElementLabelModel)getUMLDataModel().getElementProperty("NAME");
        streotype = (ElementLabelModel)getUMLDataModel().getElementProperty("STREOTYPE");
        this.addChild(tempBorderLayoutContainerModel);
        this.addChild(uMLMainPackageModel);
    }
    public void setName(String uname) {
        name.setLabelContents(uname);
    }
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
    
    public void setStereotype(String type) {
   	 try {
//            if (!this.isMode)
//                return;
   		 streotype.setLabelContents(type);
   		 this.setName(this.getName());
//            if (type != null && type.trim().length() > 0) {
//                type = "<<" + type + ">>";
//                if (!streotype.getLabelContents().equals(type)) {
//                    streotype.setLabelContents(type);
//                   
//                }
//            }
//            else{
//            	 streotype.setLabelContents("");
//            }
            
                    
              
        }
        catch (Exception e) {
            e.printStackTrace();
        }
   }

  

   public String getStereotype() {
	   if(streotype!=null)
	   return streotype.getLabelContents();
	   return "";
//   	if (streotype != null && streotype.getLabelContents().trim().length() > 0) {
//           String temp = streotype.getLabelContents().replaceFirst("<<", "");
//           temp = temp.replaceFirst(">>", "");
//           return temp;
//       }
//       else {
//           return "";
//       }
   }

    public void setN3EditorDiagramModel(N3EditorDiagramModel p) {
        //		this.n3EditorDiagramModel = p;
        this.getUMLDataModel().setN3EditorDiagramModel(p);
    }

    public N3EditorDiagramModel getN3EditorDiagramModel() {
        return this.getUMLDataModel().getN3EditorDiagramModel();
    }

    public void setTreeModel(UMLTreeModel p) {
        super.setTreeModel(p);
        name.setTreeModel(p);
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
    
    public Object clone() {
    	FinalPackageModel clone = new FinalPackageModel((UMLDataModel)this.getUMLDataModel().clone());
        //		clone.temp_childs = this.activityModel.getChildren();
//        for (int i = 0; i < this.boundaryModel.getChildren().size(); i++) {
//            UMLModel um = (UMLModel)this.boundaryModel.getChildren().get(i);
//            clone.boundaryModel.addChild((UMLElementModel)um.clone());
//        }
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
    	public String toString() {
    		
    		String value = getStereotype() + " " + this.getName();
    		return getStereotype() + " " + this.getName();
    //		return elementLabel.getLabelContents();
    	}
    //	 public Object clone(){
    //		 ActorModel clone = new ActorModel();
    //		 return clone();
    //	 }
}
