package kr.co.n3soft.n3com.edit;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import kr.co.n3soft.n3com.figures.FinalClassFigure;
import kr.co.n3soft.n3com.figures.FinalObjectNodeFigure;
import kr.co.n3soft.n3com.model.comm.ClassModel;
import kr.co.n3soft.n3com.model.umlclass.FinalClassModel;
import kr.co.n3soft.n3com.model.umlclass.InterfaceModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

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

public class FinalClassEditPart extends UMLContainerEditPart {
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
        //
        //		CollaborationFigure figure = new CollaborationFigure();
        //		BorderLayout flowLayout = new BorderLayout();
        //		figure.setLayoutManager(flowLayout);
        FinalClassFigure figure = new FinalClassFigure();
        BorderLayout flowLayout = new BorderLayout();
        figure.setLayoutManager(flowLayout);
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

    public ClassModel getClassModel() {
        FinalClassModel fcm = (FinalClassModel)this.getModel();
        return fcm.getClassModel();
    }

    protected void refreshVisuals() {
        Point loc = getUMLModel().getLocation();
        Dimension size = getUMLModel().getSize();
        Rectangle r = new Rectangle(loc, size);
        //		String name = getUMLModel().getName();
        Object obj = this.getModel();
        if(getFigure() instanceof FinalClassFigure){
        	FinalClassFigure fa = (FinalClassFigure)getFigure();
        	fa.setMultiplicity(getUMLModel().getMultiplicity());
//        	fa.setObjectState(getUMLModel().getObjectState());
//        	fa.setPostCondition(getUMLModel().getPostCondition());
        }
        //		if(obj instanceof InterfaceModel){
        //			InterfaceModel im = (InterfaceModel)obj;
        //		
        //			if(getFigure() instanceof FinalClassFigure){
        //				FinalClassFigure fcf = (FinalClassFigure)getFigure();
        //				fcf.setMode(im.getMode());
        //			}
        //		}
        getFigure().setBackgroundColor(getUMLModel().getBackGroundColor());
        getFigure().setForegroundColor(ProjectManager.getInstance().getDefaultColor());
        ((GraphicalEditPart)getParent()).setLayoutConstraint(this, getFigure(), r);
    }
    //	public void setSelected(int value) {
    ////		super.setSelected(value);
    //	}
}
