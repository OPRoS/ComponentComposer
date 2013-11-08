package kr.co.n3soft.n3com.edit;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import kr.co.n3soft.n3com.comm.figures.UMLFigure;
import kr.co.n3soft.n3com.figures.FinalPackageFigure;
import kr.co.n3soft.n3com.figures.TempBorderLayoutContainerFigure;
import kr.co.n3soft.n3com.figures.UMLMainPackageFigure;
import kr.co.n3soft.n3com.figures.UMLNamePackageFigure;
import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Figure;
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

public class FinalPackageEditPart extends UMLContainerEditPart {
    protected void createEditPolicies() {
        super.createEditPolicies();
        //		installEditPolicy(EditPolicy.NODE_ROLE, null);
        //		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new GraphicalNodeEditPolicy());
        //		installEditPolicy(EditPolicy.LAYOUT_ROLE, new UMLFlowEditPolicy());
        installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, new ContainerHighlightEditPolicy());
    }

    protected IFigure createFigure() {
        //		UseCaseFigure
        FinalPackageFigure figure = new FinalPackageFigure();
        BorderLayout flowLayout = new BorderLayout();
        //		ActorFigure actorFigure = new ActorFigure();
        //		FlowLayout flowLayout = new FlowLayout();
        //		flowLayout.setMinorSpacing(0);
        //		flowLayout.setMajorSpacing(0);
        figure.setLayoutManager(flowLayout);
        //		figure.setLayoutManager(flowLayout);
        //		figure.add(actorFigure);
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
        Point loc = getUMLModel().getLocation();
        Dimension size = getUMLModel().getSize();
        Rectangle r = new Rectangle(loc, size);
        //		String name = getUMLModel().getName();
        //		Object obj = this.getModel();
        Figure figure = (Figure)getFigure();
        getFigure().setBackgroundColor(getUMLModel().getBackGroundColor());
        for (int i = 0; i < figure.getChildren().size(); i++) {
            Object obj = figure.getChildren().get(i);
            if (obj instanceof UMLFigure) {
                UMLFigure e1 = (UMLFigure)obj;
                if (e1 instanceof TempBorderLayoutContainerFigure) {
                    for (int i1 = 0; i1 < e1.getChildren().size(); i1++) {
                        Object obj2 = e1.getChildren().get(i1);
                        if (obj2 instanceof UMLFigure) {
                            UMLFigure e2 = (UMLFigure)obj2;
                            if (e2 instanceof UMLNamePackageFigure) {
                                e2.setBackgroundColor(getUMLModel().getBackGroundColor());
                            }
                        }
                    }
                }
                else if (e1 instanceof UMLMainPackageFigure) {
                    e1.setBackgroundColor(getUMLModel().getBackGroundColor());
                }
            }
        }
        //	    if(obj instanceof ClassModel){
        //	    	ClassModel classModel = (ClassModel)obj;
        //	    	classModel.setClassName(name);
        //	    }
        ((GraphicalEditPart)getParent()).setLayoutConstraint(this, getFigure(), r);
    }
}
