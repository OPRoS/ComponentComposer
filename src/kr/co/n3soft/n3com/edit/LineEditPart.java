package kr.co.n3soft.n3com.edit;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kr.co.n3soft.n3com.edit.policy.LineBendpointEditPolicy;
import kr.co.n3soft.n3com.edit.policy.LineEditPolicy;
import kr.co.n3soft.n3com.edit.policy.LineEndpointEditPolicy;
import kr.co.n3soft.n3com.figures.UMLFigureFactory;
import kr.co.n3soft.n3com.model.comm.LineBendpointModel;
import kr.co.n3soft.n3com.model.comm.LineModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.communication.MessageAssoicateLineModel;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ManhattanConnectionRouter;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.RelativeBendpoint;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.AccessibleEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.gef.examples.logicdesigner.LogicMessages;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public class LineEditPart extends AbstractConnectionEditPart implements PropertyChangeListener {
    AccessibleEditPart acc;

    public LineEditPart() {
        System.out.println("ddddfdf");
    }

    public static final Color alive = new Color(Display.getDefault(), 0, 74, 168),
        dead = new Color(Display.getDefault(), 0, 0, 0);

    public void activate() {
        super.activate();
        getWire().addPropertyChangeListener(this);
    }

    public void activateFigure() {
        super.activateFigure();

	/*Once the figure has been added to the ConnectionLayer, start listening for its
	 * router to change.
	 */

        getFigure().addPropertyChangeListener(Connection.PROPERTY_CONNECTION_ROUTER, this);
    }

    /** Adds extra EditPolicies as required. */
    protected void createEditPolicies() {
        //	installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE, null);
        installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE, new LineEndpointEditPolicy());
        //Note that the Connection is already added to the diagram and knows its Router.
        refreshBendpointEditPolicy();
        installEditPolicy(EditPolicy.CONNECTION_ROLE, new LineEditPolicy());
    }

    /**
     * Returns a newly created Figure to represent the connection.
     * @return  The created Figure.
     */
    protected IFigure createFigure() {
        Connection connx = null;
        //	if(getWire().getLineType()==LineModel.AssociateLine){
        //		 connx = UMLFigureFactory.createAssociateLineFigure(getWire());
        //	}
        //	else if(getWire().getLineType()==LineModel.ExtendLine){
        //		 connx = UMLFigureFactory.createExtendLineFigure(getWire());
        //	}
        //	else if(getWire().getLineType()==LineModel.IncludeLine){
        //		 connx = UMLFigureFactory.createIncludeLineFigure(getWire());
        //	}
        //	else{
        //		connx = UMLFigureFactory.createAssociateLineFigure(getWire());
        //	}
        return connx;
    }

    public void deactivate() {
        getWire().removePropertyChangeListener(this);
        super.deactivate();
    }

    public void deactivateFigure() {
        getFigure().removePropertyChangeListener(Connection.PROPERTY_CONNECTION_ROUTER, this);
        super.deactivateFigure();
    }

    public AccessibleEditPart getAccessibleEditPart() {
        if (acc == null)
            acc = new AccessibleGraphicalEditPart() {
                public void getName(AccessibleEvent e) {
                    e.result = LogicMessages.Wire_LabelText;
                }
            };
        return acc;
    }

    /**
     * Returns the model of this represented as a Wire.
     * @return  Model of this as <code>Wire</code>
     */
    protected LineModel getWire() {
        return (LineModel)getModel();
    }

    /**
     * Returns the Figure associated with this, which draws the Wire.
     * @return  Figure of this.
     */
    protected IFigure getWireFigure() {
        return (PolylineConnection)getFigure();
    }

    /**
     * Listens to changes in properties of the Wire (like the contents being carried), and reflects is in the visuals.
     * @param event  Event notifying the change.
     */
    public void propertyChange(PropertyChangeEvent event) {
        try {
            String property = event.getPropertyName();
            if (Connection.PROPERTY_CONNECTION_ROUTER.equals(property)) {
                refreshBendpoints();
                refreshBendpointEditPolicy();
            }
            if (UMLModel.ID_MESSAGE_PROPERTIES.equals(property)){
            	Object obj = this.getModel();
//            	if(obj instanceof MessageModel){
//            		MessageModel m = (MessageModel)obj;
//            		UMLFigureFactory.setMessageLine(m.getIsSynchronous(), m.getIsCall(),m.getIsReturn(),m.getIsNew(),
//                         (PolylineConnection)getWireFigure());
//            	}
            	
            	
            }
               
            if ("value".equals(property)) //$NON-NLS-1$
                    refreshVisuals();
            if ("bendpoint".equals(property)) //$NON-NLS-1$
                    refreshBendpoints();
            if ("LineStyle".equals(property)) {
                ((PolylineConnection)getWireFigure()).setLineStyle(this.getWire().getLineStyle());
            }
            if (LineModel.SOURCE_ROLE.equals(property)) {
                //		((PolylineConnection)getWireFigure()).setLineStyle(this.getWire().getLineStyle());
                UMLFigureFactory.setPolylineDecoration(true, this.getWire().getSourceRole(),
                    (PolylineConnection)getWireFigure());
            }
            if (LineModel.TARGET_ROLE.equals(property)) {
                UMLFigureFactory.setPolylineDecoration(false, this.getWire().getTargetRole(),
                    (PolylineConnection)getWireFigure());
            }
            if (LineModel.ID_NAME.equals(property)) {
                UMLFigureFactory.setNamePropertyPolylineDecoration((PolylineConnection)getWireFigure(),
                    LineModel.ID_NAME, this.getWire().getName(), this);
            }
            //PKY 08070904 S include,extends 스트레오값나오도록
            if (LineModel.ID_STEREOTYPE.equals(property)) {
                
                UMLFigureFactory.setStereotypePropertyPolylineDecoration((PolylineConnection)getWireFigure(),
                    LineModel.ID_STEREOTYPE, this.getWire().getStereotype(), this);
            }
          //PKY 08070904 E include,extends 스트레오값나오도록
            if (LineModel.CONSTRAINTS_ROLE.equals(property)) {
                HashMap hm = new HashMap();
                hm.put(LineModel.CONSTRAINTS_ROLE, this.getWire().getConstraints());
                UMLFigureFactory.setPropertyPolylineDecoration((PolylineConnection)getWireFigure(),
                    LineModel.CONSTRAINTS_ROLE, hm, this);
            }
            if (LineModel.ID_DETAIL.equals(property)) {
            	LineModel lm = (LineModel)this.getModel();
            	if(!(this.getModel() instanceof MessageAssoicateLineModel))//PKY 08081801 S MessageAssoicateLineModel 속성 바꿀경우 메시지가 사라지는문제
            	UMLFigureFactory.setDetailPropertyPolylineDecoration((PolylineConnection)getWireFigure(), LineModel.ID_DETAIL, lm.getDetailProp(), this);
            	lm.isChange = false;
            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
     
    }

    /** Updates the bendpoints, based on the model. */
    protected void refreshBendpoints() {
//    	getConnectionFigure().setConnectionRouter(new ManhattanConnectionRouter());
        if (getConnectionFigure().getConnectionRouter() instanceof ManhattanConnectionRouter)
            return;
        List modelConstraint = getWire().getBendpoints();
        List figureConstraint = new ArrayList();
        if(modelConstraint.size()==0){
	        	LineModel lmd = (LineModel)this.getModel();
	        	if(lmd.getTarget()==lmd.getSource()){
	        	LineBendpointModel lm = new LineBendpointModel();
	        	lm.setRelativeDimensions(new Dimension(100,0), new Dimension(100,0));
	        	
	        	modelConstraint.add(lm);
	        	lm = new LineBendpointModel();
	        	lm.setRelativeDimensions(new Dimension(100,100), new Dimension(100,100));
	        	modelConstraint.add(lm);
	        	lm = new LineBendpointModel();
	        	lm.setRelativeDimensions(new Dimension(0,100), new Dimension(0,100));
	        	modelConstraint.add(lm);
        	}
        }
        for (int i = 0; i < modelConstraint.size(); i++) {
            LineBendpointModel wbp = (LineBendpointModel)modelConstraint.get(i);
            RelativeBendpoint rbp = new RelativeBendpoint(getConnectionFigure());
            rbp.setRelativeDimensions(wbp.getFirstRelativeDimension(), wbp.getSecondRelativeDimension());
            rbp.setWeight((i + 1) / ((float)modelConstraint.size() + 1));
            figureConstraint.add(rbp);
        }
        getConnectionFigure().setRoutingConstraint(figureConstraint);
    }

    private void refreshBendpointEditPolicy() {
        if (getConnectionFigure().getConnectionRouter() instanceof ManhattanConnectionRouter)
            installEditPolicy(EditPolicy.CONNECTION_BENDPOINTS_ROLE, null);
        else
            installEditPolicy(EditPolicy.CONNECTION_BENDPOINTS_ROLE, new LineBendpointEditPolicy());
    }

    /**
     * Refreshes the visual aspects of this, based upon the
     * model (Wire). It changes the wire color depending on the state of Wire.
     */
    protected void refreshVisuals() {
        refreshBendpoints();
        if (getWire().getValue())
            getWireFigure().setForegroundColor(alive);
        else
            getWireFigure().setForegroundColor(dead);
    }

    public LineModel getCloneModel() {
        return (LineModel)this.getWire().clone();
    }
    
//    public void setSelected(int value) {
//    super.setSelected(value);
//    }
}
