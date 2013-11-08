package kr.co.n3soft.n3com.model.usecase;

import kr.co.n3soft.n3com.model.comm.ElementLabelModel;
import kr.co.n3soft.n3com.model.comm.IUpdateType;
import kr.co.n3soft.n3com.model.comm.LineModel;
import kr.co.n3soft.n3com.model.comm.UMLDataModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.comm.UpdateEvent;
import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;
import kr.co.n3soft.n3com.model.umlclass.CompartmentModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.swt.graphics.Image;

public class FinalActorModel extends UMLDiagramModel {
    static final long serialVersionUID = 1;
    public static int count = 0;
    ElementLabelModel name = null;
    ElementLabelModel streotype = null;
    CompartmentModel borderContainlModel = new CompartmentModel();
    ActorModel actorModel = null;
    public UMLModel typeRefModel = null;
  //ijs080429
//    public  Image LOGIC_ICON = createImage(FinalActorModel.class, "icons/actor.gif"); //$NON-NLS-1$

    public FinalActorModel() {
        actorModel = new ActorModel();
        actorModel.setParentModel(this);
        actorModel.setLayout(BorderLayout.CENTER);
        actorModel.setParentLayout(1);
        this.addChild(actorModel);
        streotype = new ElementLabelModel(PositionConstants.CENTER);
        streotype.setType("STREOTYPE");
        streotype.setLayout(BorderLayout.BOTTOM);
        streotype.setParentLayout(1);
        
        size.width = 35;//2008041702PKY S
        size.height = 62;//2008041702PKY S
        name = new ElementLabelModel(PositionConstants.CENTER);
        name.setType("NAME");
        borderContainlModel.setParentModel(this);
        borderContainlModel.setLayout(BorderLayout.BOTTOM);
        borderContainlModel.setParentLayout(1);
        borderContainlModel.addChild(name);
        borderContainlModel.setIsBorder(false);
        borderContainlModel.setSize(new Dimension(10,10));
        borderContainlModel.setCompartmentModelType("-1");
//        borderContainlModel.setParentLayout(1);
      
        this.addChild(borderContainlModel);
//        this.uMLDataModel.setElementProperty(String.valueOf(BorderLayout.TOP), streotype);
        this.uMLDataModel.setElementProperty(name.getType(), name);
//        this.uMLDataModel.setElementProperty(streotype.getType(), streotype);
        

    }

    public FinalActorModel(UMLDataModel p) {
        actorModel = new ActorModel();
        actorModel.setParentModel(this);
        actorModel.setLayout(BorderLayout.CENTER);
        actorModel.setParentLayout(1);
        this.addChild(actorModel);
//        streotype = new ElementLabelModel(PositionConstants.CENTER);
        size.width = 50;
        size.height = 100;
        name = (ElementLabelModel)p.getElementProperty("NAME");
        borderContainlModel.setParentModel(this);
        borderContainlModel.setLayout(BorderLayout.BOTTOM);
        borderContainlModel.setParentLayout(1);
        borderContainlModel.setIsBorder(false);
//        borderContainlModel.setParentLayout(1);
      
        this.addChild(borderContainlModel);
        ElementLabelModel streotypeTemp = (ElementLabelModel)p.getElementProperty("STREOTYPE");
        if (streotypeTemp != null) {
            streotype = streotypeTemp;
            borderContainlModel.addChild(streotype);
        }
        borderContainlModel.addChild(name);
        this.setUMLDataModel(p);
    }

    public void setActorModel(ActorModel p) {
        this.actorModel = p;
    }

    public ActorModel getActorModel() {
        return this.actorModel;
    }

//    public void setName(String uname) {
//        name.setLabelContents(uname);
//    }

    public void setStereotype(String type) {
    	 try {
//             if (!this.isMode)
//                 return;
    		
             if (type != null && type.trim().length() > 0) {
            	 if (this.uMLDataModel.getElementProperty(streotype.getType()) == null) {
            		 this.uMLDataModel.setElementProperty(streotype.getType(), streotype);
                     //				this.removeChild(streotype);
            		 streotype.setLabelContents(type);
                     borderContainlModel.addChild(streotype, 0); 
            	 }
            	 else{
            		 streotype.setLabelContents(type);
            	 }
            	 
//                 type = "<<" + type + ">>";
//                 if (!streotype.getLabelContents().equals(type)) {
//                     streotype.setLabelContents(type);
//                     try {
//                         if (this.uMLDataModel.getElementProperty(streotype.getType()) == null) {
//                        	 borderContainlModel.removeChild(streotype);
//                         }
//                     }
//                     catch (Exception e) {
//                         e.printStackTrace();
//                     }
//                     this.uMLDataModel.setElementProperty(streotype.getType(), streotype);
//                     //				this.removeChild(streotype);
//                     borderContainlModel.addChild(streotype, 0);
//                 }
             }
             else {
                 if (this.uMLDataModel.getElementProperty(streotype.getType()) != null) {
                	 borderContainlModel.removeChild(streotype);
                     streotype.setLabelContents("");
                 }
                 //			this.removeChild(streotype);
                 this.uMLDataModel.setElementProperty(streotype.getType(), null);
             }
         }
         catch (Exception e) {
             e.printStackTrace();
         }
         this.setName(this.getName());
    }

    public String getName() {
        return name.getLabelContents();
    }

    public String getStereotype() {
    	if(streotype!=null)
    	return streotype.getLabelContents();
    	else
    		return "";
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
    }

    public String toString() {
    	String dd = getStereotype() + " " + this.getName();
        return getStereotype() + " " + this.getName();
    }

    public void setUMLDataModel(UMLDataModel p) {
        uMLDataModel = p;
    }

    public void setTreeModel(UMLTreeModel p) {
        super.setTreeModel(p);
        name.setTreeModel(p);
    }
    
    public Image getIconImage() {
        return LOGIC_ICON;
    }
    
    public void setName(String uname) {
        name.setLabelContents(uname);
        UpdateEvent e = new UpdateEvent(IUpdateType.RENAME_TYPE, null);
        name.fireChildUpdate(e);
        firePropertyChange(ID_NAME, null, uname); //$NON-NLS-1$
//		firePropertyChange("name", null, _name); //$NON-NLS-1$
    }
    
    public void setTypeRef(UMLModel p) {
        this.typeRefModel = p;
        this.uMLDataModel.setElementProperty("typeRefModel", p);
        this.setRefName(p);
        this.setName(this.getName());
    }

    public void setRefName(UMLModel p) {
        this.name.setRefModel(p);
    }

    public UMLModel getTypeRef() {
        return (UMLModel)this.uMLDataModel.getElementProperty("typeRefModel");
    }

    public void addUpdateListener(UMLModel p) {
        this.name.addUpdateListener(p);
    }

    public void removeUpdateListener(UMLModel p) {
        this.name.removeUpdateListener(p);
    }

    public void updateModel(UpdateEvent p) {
    	 if (p.getType() == IUpdateType.REMOVE_NAME) {
         	this.setTypeRef(null);
         	
         	
         }
         else  if (p.getType() == IUpdateType.RENAME_TYPE) {
         	this.setName(this.getName());
         	
         	
         }
//        this.name.fireChildUpdate(p);
    }

    public Object clone() {
        FinalActorModel clone = new FinalActorModel((UMLDataModel)this.getUMLDataModel().clone());
        //		clone.setUMLDataModel((UMLDataModel)this.getUMLDataModel().clone());
        clone.setSize(this.getSize());
        clone.setBackGroundColor(this.getBackGroundColor());
        clone.actorModel.setBackGroundColor(this.getBackGroundColor());
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
	public void refresh(){
		firePropertyChange(this.ID_REFRESH, null, null);
	}
    
}
