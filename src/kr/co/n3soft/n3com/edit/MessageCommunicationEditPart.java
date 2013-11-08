package kr.co.n3soft.n3com.edit;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import kr.co.n3soft.n3com.figures.MessageCommunicationFigure;
import kr.co.n3soft.n3com.model.communication.MessageCommunicationModel;
import kr.co.n3soft.n3com.project.dialog.CommunicationMessagePropertyDialog;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.AccessibleAnchorProvider;
import org.eclipse.gef.AutoexposeHelper;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.ExposeHelper;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.MouseWheelHelper;
import org.eclipse.gef.Tool;
import org.eclipse.gef.editparts.ViewportAutoexposeHelper;
import org.eclipse.gef.editparts.ViewportExposeHelper;
import org.eclipse.gef.editparts.ViewportMouseWheelHelper;
import org.eclipse.gef.examples.logicdesigner.edit.ContainerHighlightEditPolicy;
import org.eclipse.gef.tools.MarqueeSelectionTool;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;

public class MessageCommunicationEditPart extends UMLContainerEditPart {
    protected void createEditPolicies() {
        super.createEditPolicies();
        //		installEditPolicy(EditPolicy.NODE_ROLE, null);
        //		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new GraphicalNodeEditPolicy());
        //		installEditPolicy(EditPolicy.LAYOUT_ROLE, new UMLFlowEditPolicy());
        installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, null);
        installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, new ContainerHighlightEditPolicy());
    }

    protected IFigure createFigure() {
        //		UseCaseFigure
    	MessageCommunicationFigure figure = new MessageCommunicationFigure();
        		BorderLayout flowLayout = new BorderLayout();
        //		
        //
        //		FlowLayout flowLayout = new FlowLayout();
        //		flowLayout.setMinorSpacing(0);
        //		flowLayout.setMajorSpacing(0);
        		figure.setLayoutManager(flowLayout);
        //		figure.setBorder(new ClassBorderFigure());
        figure.setOpaque(false);
        return figure;
    }

    public Object getAdapter(Class key) {
        if (key == AutoexposeHelper.class)
            return new ViewportAutoexposeHelper(this);
        if (key == ExposeHelper.class)
            return new ViewportExposeHelper(this);
        if (key == AccessibleAnchorProvider.class)
            return new DefaultAccessibleAnchorProvider() {
                public List getSourceAnchorLocations() {
                    List list = new ArrayList();
                    Vector sourceAnchors = getNodeFigure().getSourceConnectionAnchors();
                    Vector targetAnchors = getNodeFigure().getTargetConnectionAnchors();
                    for (int i = 0; i < sourceAnchors.size(); i++) {
                        ConnectionAnchor sourceAnchor = (ConnectionAnchor)sourceAnchors.get(i);
                        ConnectionAnchor targetAnchor = (ConnectionAnchor)targetAnchors.get(i);
                        list.add(
                            new Rectangle(sourceAnchor.getReferencePoint(), targetAnchor.getReferencePoint()).getCenter());
                    }
                    return list;
                }
                public List getTargetAnchorLocations() {
                    return getSourceAnchorLocations();
                }
            };
        if (key == MouseWheelHelper.class)
            return new ViewportMouseWheelHelper(this);
        return super.getAdapter(key);
    }

    protected void refreshVisuals() {
        //		((UMLElementFigure)getFigure()).setText(getElementLabelModel().getLabelContents());
        if (getUMLModel() instanceof MessageCommunicationModel) {
        	MessageCommunicationModel actorModel = (MessageCommunicationModel)getUMLModel();
            if (actorModel.getParentLayout() == 1) {
                if (actorModel.getLayout() != null) {
                	
                    ((GraphicalEditPart)getParent()).setLayoutConstraint(this, getFigure(), actorModel.getLayout());
                }
            }
            else {
            	MessageCommunicationModel mcm = (MessageCommunicationModel)getUMLModel();

            	  Point loc = getUMLModel().getLocation();
                  Dimension size = getUMLModel().getSize();
                  Rectangle r = new Rectangle(loc, size);
                  getFigure().setBackgroundColor(ColorConstants.tooltipBackground);
                  if(getFigure() instanceof MessageCommunicationFigure){
           	    	  MessageCommunicationFigure mcf = (MessageCommunicationFigure)getFigure();
           	    	 
           	    	  mcf.setAngle(mcm.getAngel());
           	    	//PKY 08070301 S Communication Dialog 추가작업
           	    	  mcf.setBack(mcm.back);
           	    	 mcf.setSignature(mcm.signature);
           	    	 mcf.setSynch(mcm.synch);
           	    	 mcf.setKind(mcm.kind);
           	    //PKY 08070301 E Communication Dialog 추가작업
           	       }
                  
                  ((GraphicalEditPart)getParent()).setLayoutConstraint(this, getFigure(), r);
            }
        }
      
    }

    public void setSelected(int value) {
        ScrollingGraphicalViewer viewer = ProjectManager.getInstance().getUMLEditor().getScrollingGraphicalViewer();
        ;
        Tool tool = ProjectManager.getInstance().getUMLEditor().getUMLDefaultEditDomain().getActiveTool();
        if (!(tool instanceof MarqueeSelectionTool)) {
            if (value == 2) {
                viewer.select(this.getParent());
                super.setSelected(0);
            }
            else {
                super.setSelected(value);
            }
        }
        else {
            super.setSelected(value);
        }
    }
}
