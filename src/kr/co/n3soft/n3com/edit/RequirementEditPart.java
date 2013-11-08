package kr.co.n3soft.n3com.edit;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import kr.co.n3soft.n3com.figures.RequirementFigure;

import org.eclipse.draw2d.BorderLayout;
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
import org.eclipse.gef.editparts.ViewportAutoexposeHelper;
import org.eclipse.gef.editparts.ViewportExposeHelper;
import org.eclipse.gef.editparts.ViewportMouseWheelHelper;
import org.eclipse.gef.examples.logicdesigner.edit.ContainerHighlightEditPolicy;

public class RequirementEditPart extends UMLContainerEditPart {
    public RequirementEditPart() {
        System.out.println("ddd");
    }

    protected void createEditPolicies() {
        super.createEditPolicies();
        //		installEditPolicy(EditPolicy.NODE_ROLE, null);
        //		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new GraphicalNodeEditPolicy());
        //		installEditPolicy(EditPolicy.LAYOUT_ROLE, new UMLFlowEditPolicy());
        installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, new ContainerHighlightEditPolicy());
    }

    protected IFigure createFigure() {
        //		UseCaseFigure
    	RequirementFigure figure = new RequirementFigure();
        BorderLayout flowLayout = new BorderLayout();
        //		FlowLayout flowLayout = new FlowLayout();
        //		flowLayout.setMinorSpacing(0);
        //		flowLayout.setMajorSpacing(0);
        figure.setLayoutManager(flowLayout);
        //		figure.setBorder(new ClassBorderFigure());
        figure.setOpaque(true);
        return figure;
    }

    public Object getAdapter(Class key) {
        System.out.println("key==>" + key);
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
        try {
            Point loc = getUMLModel().getLocation();
            Dimension size = getUMLModel().getSize();
            Rectangle r = new Rectangle(loc, size);
            getFigure().setBackgroundColor(getUMLModel().getBackGroundColor());
            if(getFigure() instanceof RequirementFigure){
            	RequirementFigure uf = (RequirementFigure)getFigure();
            	uf.setExtendsPoints(getUMLModel().getExtendsPoints());
            	//2008041401PKY S
            	Object Usecase=this.getModel();
            	
//          	2008041401PKY E
            	
            	System.out.print("'");
            }
            ((GraphicalEditPart)getParent()).setLayoutConstraint(this, getFigure(), r);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    //	public void setSelected(int value) {
    ////		ScrollingGraphicalViewer viewer = ProjectManager.getInstance().getUMLEditor().getScrollingGraphicalViewer();
    ////		int i = this.getParent().getParent().getSelected();
    ////		if(i==1){
    //////			if(value==1){
    ////				viewer.select(this.getParent().getParent());
    ////				return;
    //////			}
    ////		}
    ////		else{
    ////			super.setSelected(value);
    ////		}
    //	}
}
