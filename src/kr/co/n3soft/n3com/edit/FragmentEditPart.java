package kr.co.n3soft.n3com.edit;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import kr.co.n3soft.n3com.figures.FragmentFigure;

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.AccessibleAnchorProvider;
import org.eclipse.gef.AutoexposeHelper;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.ExposeHelper;
import org.eclipse.gef.MouseWheelHelper;
import org.eclipse.gef.editparts.ViewportAutoexposeHelper;
import org.eclipse.gef.editparts.ViewportExposeHelper;
import org.eclipse.gef.editparts.ViewportMouseWheelHelper;
import org.eclipse.gef.examples.logicdesigner.edit.ContainerHighlightEditPolicy;

public class FragmentEditPart extends UMLContainerEditPart {
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
    	FragmentFigure figure = new FragmentFigure();
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
    
    protected void refreshVisuals() {
       
    }

//    protected void refreshVisuals() {
//        //		((UMLElementFigure)getFigure()).setText(getElementLabelModel().getLabelContents());
//        if (getUMLModel() instanceof FragmentModel) {
//        	FragmentModel actorModel = (FragmentModel)getUMLModel();
//            if (actorModel.getParentLayout() == 1) {
//                if (actorModel.getLayout() != null) {
//                    ((GraphicalEditPart)getParent()).setLayoutConstraint(this, getFigure(), actorModel.getLayout());
//                }
//            }
//            else {
//                super.refreshVisuals();
//            }
//        }
//
//    }

    
}
