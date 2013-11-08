package kr.co.n3soft.n3com.edit;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.List;

import kr.co.n3soft.n3com.edit.policy.UMLElementEditPolicy;
import kr.co.n3soft.n3com.model.comm.ElementLabelModel;
import kr.co.n3soft.n3com.model.comm.FinalPackageModel;
import kr.co.n3soft.n3com.model.comm.LineTextModel;
import kr.co.n3soft.n3com.model.comm.PortModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.comm.UMLNoteModel;
import kr.co.n3soft.n3com.model.component.ComponentModel;
import kr.co.n3soft.n3com.model.umlclass.CompartmentModel;
import kr.co.n3soft.n3com.model.usecase.ActorModel;
import kr.co.n3soft.n3com.model.usecase.BoundaryModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.examples.logicdesigner.edit.LogicTreeEditPolicy;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public class UMLTreeEditPart extends org.eclipse.gef.editparts.AbstractTreeEditPart implements PropertyChangeListener {
    /**
     * Constructor initializes this with the given model.
     * @param model  Model for this.
     */
	
	java.util.ArrayList parents = new java.util.ArrayList();
    public UMLTreeEditPart(Object model) {
        super(model);
    }

    public void activate() {
        super.activate();
        getUMLModel().addPropertyChangeListener(this);
    }

    /** Creates and installs pertinent EditPolicies for this. */
    protected void createEditPolicies() {
        EditPolicy component;
        //	if (getModel() instanceof LED)
        //		component = new LEDEditPolicy();
        //	else
        component = new UMLElementEditPolicy();
        installEditPolicy(EditPolicy.COMPONENT_ROLE, component);
        installEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE, new LogicTreeEditPolicy());
    }

    public void deactivate() {
        getUMLModel().removePropertyChangeListener(this);
        super.deactivate();
    }

    /**
     * Returns the model of this as a LogicSubPart.
     * @return Model of this.
     */
    protected UMLModel getUMLModel() {
        return (UMLModel)getModel();
    }

    /**
     * Returns <code>null</code> as a Tree EditPart holds no children under it.
     * @return <code>null</code>
     */
    protected List getModelChildren() {
        return Collections.EMPTY_LIST;
    }
    
    protected void addChild(EditPart child, int index) {
    	try{
    		//PKY 08051401 S NullPoint 에러 
    		if(child!=null){
    	if(child.getModel() instanceof LineTextModel){
    		return;
    	}
    	if(child.getModel() instanceof UMLNoteModel){
    		return;
    	}
    	
    	if(child.getModel() instanceof FinalPackageModel){
    		return;
    	}
    	if(child.getModel() instanceof PortModel){
    		return;
    	}
    	if(child.getModel() instanceof ActorModel){
    		return;
    	}
    	
    	if(child.getModel() instanceof BoundaryModel){
    		return;
    	}
    	if(child.getModel() instanceof ElementLabelModel){
    		return;
    	}
    	if(child.getModel() instanceof CompartmentModel){
    		CompartmentModel cm = (CompartmentModel)child.getModel();
    		return;
//    		if("-1".equals(cm.getCompartmentModelType())){
//    			return;
//    		}
    	}
//    	if(child.getModel() instanceof ComponentModel)
    	super.addChild(child, index);
//    	}
    		}
    		//PKY 08051401 E NullPoint 에러 
    	}
    	catch(java.lang.IndexOutOfBoundsException e1){
    		index=-1;
    		super.addChild(child, index);
    		e1.printStackTrace();
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    	
    }

    public void propertyChange(PropertyChangeEvent change) {
        if (change.getPropertyName().equals("Children")) {
            if (change.getOldValue() instanceof Integer){
                // new child
            	if(change.getNewValue() instanceof LineTextModel){
            		return;
            	}
            	if(change.getNewValue() instanceof UMLNoteModel){
            		return;
            	}
            	
            	if(change.getNewValue() instanceof FinalPackageModel){
            		return;
            	}
            	if(change.getNewValue() instanceof PortModel){
            		return;
            	}
            	
                    addChild(createChild(change.getNewValue()), ((Integer)change.getOldValue()).intValue());
            }
                    else
                // remove child
                if (getViewer().getEditPartRegistry().get(change.getOldValue()) == null) {
                    for (int i = 0; i < this.children.size(); i++) {
                        Object obj = this.children.get(i);
                        if (obj instanceof UMLContainerTreeEditPart) {
                            UMLContainerTreeEditPart uMLEditPart = (UMLContainerTreeEditPart)obj;
                            UMLModel uMLModel = ProjectManager.getInstance().getDeleteModel();
                            if (uMLEditPart.getModel() instanceof UMLModel) {
                                UMLModel p = (UMLModel)uMLEditPart.getModel();
                                if (p.getID().equals(uMLModel.getID())) {
                                    removeChild(uMLEditPart);
                                    break;
                                }
                            }
                        }
                    }
            }
            else
                removeChild((EditPart)getViewer().getEditPartRegistry().get(change.getOldValue()));
        } else{
        	this.getParentUMLTreeEditPart(this.getParent());
            refreshVisuals();
        }
    }

    /** Refreshes the visual properties of the TreeItem for this part. */
    protected void refreshVisuals() {
        if (getWidget() instanceof Tree)
            return;
        Image image = getUMLModel().getIcon();
        TreeItem item = (TreeItem)getWidget();
        if (image != null)
            image.setBackground(item.getParent().getBackground());
        if(image!=null){
        	setWidgetImage(image);
        }
       //20080908IJS
       
        	setWidgetText(getUMLModel().toString());
       
        
       
    }
    
    public void getParentUMLTreeEditPart(Object p){
    	Object obj = p;
    	if(obj instanceof UMLContainerTreeEditPart){
    		UMLContainerTreeEditPart parent = (UMLContainerTreeEditPart)obj;
    		parent.refreshParentVisuals();
    		if(parent.getParent()!=null){
    			this.getParentUMLTreeEditPart(parent.getParent());
    		}
    	}
    	else
    		return;
    	
    }
    
    protected void refreshParentVisuals() {
        if (getWidget() instanceof Tree)
            return;
        Image image = getUMLModel().getIcon();
        TreeItem item = (TreeItem)getWidget();
        if (image != null)
            image.setBackground(item.getParent().getBackground());
        if(image!=null){
        	setWidgetImage(image);
        }
        setWidgetText(getUMLModel().toString());
    }
    
    
}
