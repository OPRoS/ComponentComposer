package kr.co.n3soft.n3com.edit;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import kr.co.n3soft.n3com.comm.figures.UMLFigure;
import kr.co.n3soft.n3com.edit.policy.UMLElementEditPolicy;
import kr.co.n3soft.n3com.edit.policy.UMLNodeEditPolicy;
import kr.co.n3soft.n3com.model.comm.LineModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.AccessibleEditPart;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.requests.DropRequest;

public abstract class UMLEditPart extends org.eclipse.gef.editparts.AbstractGraphicalEditPart implements NodeEditPart,
    PropertyChangeListener {
        private AccessibleEditPart acc;

        public void activate() {
            if (isActive())
                return;
            super.activate();
            getUMLModel().addPropertyChangeListener(this);
        }

        protected void createEditPolicies() {

//        	installEditPolicy(EditPolicy., new UMLElementEditPolicy());
        	
        	installEditPolicy(EditPolicy.COMPONENT_ROLE, new UMLElementEditPolicy());
            installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new UMLNodeEditPolicy());
        }

        abstract protected AccessibleEditPart createAccessible();

        /** Makes the EditPart insensible to changes in the model by removing itself from the model's list of listeners. */
        public void deactivate() {
            if (!isActive())
                return;
            super.deactivate();
            getUMLModel().removePropertyChangeListener(this);
        }

        protected AccessibleEditPart getAccessibleEditPart() {
            if (acc == null)
                acc = createAccessible();
            return acc;
        }

        /**
         * Returns the model associated with this as a LogicSubPart.
         * @return  The model of this as a LogicSubPart.
         */
        protected UMLModel getUMLModel() {
            return (UMLModel)getModel();
        }

        /**
         * Returns a list of connections for which this is the source.
         * @return List of connections.
         */
        protected List getModelSourceConnections() {
            return getUMLModel().getSourceConnections();
        }

        /**
         * Returns a list of connections for which this is the target.
         * @return  List of connections.
         */
        protected List getModelTargetConnections() {
            return getUMLModel().getTargetConnections();
        }

        /**
         * Returns the Figure of this, as a node type figure.
         * @return  Figure as a NodeFigure.
         */
        protected UMLFigure getNodeFigure() {
            return (UMLFigure)getFigure();
        }

        /**
         * Returns the connection anchor for the given ConnectionEditPart's source.
         * @return  ConnectionAnchor.
         */
        public ConnectionAnchor getSourceConnectionAnchor(ConnectionEditPart connEditPart) {
            LineModel wire = (LineModel)connEditPart.getModel();
            ConnectionAnchor connectionAnchor = getNodeFigure().getConnectionAnchor(wire.getSourceTerminal());
            
            return connectionAnchor;
        }

        /**
         * Returns the connection anchor of a source connection which is at the given point.
         * @return  ConnectionAnchor.
         */
        public ConnectionAnchor getSourceConnectionAnchor(Request request) {
            Point pt = new Point(((DropRequest)request).getLocation());
            return getNodeFigure().getSourceConnectionAnchorAt(pt);
        }

        /**
         * Returns the connection anchor for the given ConnectionEditPart's target.
         * @return  ConnectionAnchor.
         */
        public ConnectionAnchor getTargetConnectionAnchor(ConnectionEditPart connEditPart) {
            LineModel wire = (LineModel)connEditPart.getModel();
            ConnectionAnchor connectionAnchor = getNodeFigure().getConnectionAnchor(wire.getSourceTerminal());
            return getNodeFigure().getConnectionAnchor(wire.getTargetTerminal());
        }

        /**
         * Returns the connection anchor of a terget connection which is at the given point.
         * @return  ConnectionAnchor.
         */
        public ConnectionAnchor getTargetConnectionAnchor(Request request) {
            Point pt = new Point(((DropRequest)request).getLocation());
            return getNodeFigure().getTargetConnectionAnchorAt(pt);
        }

        /**
         * Returns the name of the given connection anchor.
         * @return  The name of the ConnectionAnchor as a String.
         */
        final public String mapConnectionAnchorToTerminal(ConnectionAnchor c) {
            return getNodeFigure().getConnectionAnchorName(c);
        }

        /**
         * Handles changes in properties of this. It is activated through the PropertyChangeListener.
         * It updates children, source and target connections, and the visuals of this based on the property changed.
         * @param evt  Event which details the property change.
         */
        public void propertyChange(PropertyChangeEvent evt) {
            try {
                String prop = evt.getPropertyName();
                if (UMLModel.CHILDREN.equals(prop)) {
                    if (evt.getOldValue() instanceof Integer) {
                        // new child
                        //			//port
                        //			if(evt.getNewValue() instanceof UseCaseModel
                        //					&& evt.getSource() instanceof BoundaryModel
                        //					){
                        ////				System.out.println("dddd");
                        //				UseCaseModel um = (UseCaseModel)evt.getNewValue();
                        //				if(um.pt!=null)
                        //					return;
                        //				um.pt = (UMLModel)evt.getSource();
                        //				UMLModel up = (UMLModel)evt.getSource();
                        //				UMLModel up1 = (UMLModel)up.getParentModel();
                        //				UMLContainerModel up2 = (UMLContainerModel)up1.getAcceptParentModel();
                        //				um.setLocation(up1.getLocation());
                        //				up2.addChild(um);
                        //				
                        //				return;
                        //			}
                        addChild(createChild(evt.getNewValue()), ((Integer)evt.getOldValue()).intValue());
                    } else {
                        // remove child
                        if (getViewer().getEditPartRegistry().get(evt.getOldValue()) == null) {
                            for (int i = 0; i < this.children.size(); i++) {
                                Object obj = this.children.get(i);
                                if (obj instanceof UMLEditPart) {
                                    UMLEditPart uMLEditPart = (UMLEditPart)obj;
                                    UMLModel uMLModel = ProjectManager.getInstance().getDeleteModel();
                                    if (uMLEditPart.getModel() instanceof UMLModel) {
                                        UMLModel p = (UMLModel)uMLEditPart.getModel();
                                        if (uMLModel != null && p.getID().equals(uMLModel.getID())) {
                                            removeChild(uMLEditPart);
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        else
                            removeChild((EditPart)getViewer().getEditPartRegistry().get(evt.getOldValue()));
                    }
                }
                else if (UMLModel.INPUTS.equals(prop))
                    refreshTargetConnections();
                else if (UMLModel.OUTPUTS.equals(prop))
                    refreshSourceConnections();
                else if (UMLModel.ID_SELECT.equals(prop)){
                   Boolean b = (Boolean)evt.getNewValue();
                   if(b.booleanValue()==true){
                	this.setSelected(1);
                   }
                   else{
                	   this.setSelected(0);
                   }
                }
                else if (prop.equals(UMLModel.ID_SIZE) || prop.equals(UMLModel.ID_LOCATION) || prop.equals(UMLModel.ID_NAME) ||
                    prop.equals(UMLModel.ID_COLOR) || prop.equals(UMLModel.ID_ATTRIBUTE) ||
                    prop.equals(UMLModel.ID_STEREOTYPE)||prop.equals(UMLModel.ID_ANGLE)
                    ||prop.equals(UMLModel.ID_CONFIGURE_TIMELINE)||prop.equals(UMLModel.ID_CONFIGURE_NUM)
                    ||prop.equals(UMLModel.ID_CHANGE_GROUP)||prop.equals(UMLModel.ID_CHANGE_DRAG)||prop.equals(UMLModel.ID_CHANGE_PROPERTY)||prop.equals(UMLModel.ID_EXTENSIONPOINT)
                    ||prop.equals(UMLModel.ID_REFERENCE)||prop.equals(UMLModel.ID_PARAMETERNAME)
                    ||prop.equals(UMLModel.ID_PRECONDITION)||prop.equals(UMLModel.ID_POSTCONDITION)||prop.equals(UMLModel.ID_OBJECT_STATE)||prop.equals(UMLModel.ID_MULTI)||prop.equals(UMLModel.ID_ACTORIMAGE)
                    ||prop.equals(UMLModel.ID_REFRESH) ||prop.equals(UMLModel.ID_SIGNATURE) ||prop.equals(UMLModel.ID_SYNCH)
                    ||prop.equals(UMLModel.ID_KIND) ||prop.equals(UMLModel.ID_LIFECYCEL)
                    ||prop.equals(UMLModel.ID_MAX))//PKY 08070301 S Communication Dialog 추가작업
                        refreshVisuals();
  

              //PKY 08060201 S 컬러관련 재 생성할경우 적용안되는문제
              if(prop.equals(UMLModel.ID_COLOR))
                getUMLModel().getUMLDataModel().setElementProperty("ID_COLOR", getUMLModel().getBackGroundColor());

            //PKY 08060201 E 컬러관련 재 생성할경우 적용안되는문제
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        /** Updates the visual aspect of this. */
        protected void refreshVisuals() {
            Point loc = getUMLModel().getLocation();
            Dimension size = getUMLModel().getSize();
            Rectangle r = new Rectangle(loc, size);
            getFigure().setBackgroundColor(ColorConstants.tooltipBackground);
           
            
            ((GraphicalEditPart)getParent()).setLayoutConstraint(this, getFigure(), r);
        }

        public UMLModel getCloneModel() {
            return (UMLModel)this.getUMLModel().clone();
        }


}
