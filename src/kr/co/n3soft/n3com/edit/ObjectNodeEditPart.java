package kr.co.n3soft.n3com.edit;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import kr.co.n3soft.n3com.edit.policy.UMLXYLayoutEditPolicy;
import kr.co.n3soft.n3com.figures.ActivityFigure;
import kr.co.n3soft.n3com.figures.ObjectNodeFigure;
import kr.co.n3soft.n3com.model.activity.ObjectNodeModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.XYLayout;
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

public class ObjectNodeEditPart extends UMLContainerEditPart {
    protected void createEditPolicies() {
        super.createEditPolicies();
        //		super.createEditPolicies();
        //		installEditPolicy(EditPolicy.LAYOUT_ROLE, new LogicFlowEditPolicy());
        installEditPolicy(EditPolicy.LAYOUT_ROLE, new UMLXYLayoutEditPolicy((XYLayout)getContentPane().getLayoutManager()));
        installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, new ContainerHighlightEditPolicy());
    }

    protected IFigure createFigure() {
        //		UseCaseFigure
        ObjectNodeFigure figure = new ObjectNodeFigure();
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

    protected ObjectNodeFigure getObjectNodeFigureBoardFigure() {
        return (ObjectNodeFigure)getFigure();
    }

    public IFigure getContentPane() {
        return getObjectNodeFigureBoardFigure().getContentsPane();
    }

    protected void refreshVisuals() {
        //		((UMLElementFigure)getFigure()).setText(getElementLabelModel().getLabelContents());
        if (getUMLModel() instanceof ObjectNodeModel) {
            ObjectNodeModel actorModel = (ObjectNodeModel)getUMLModel();
            if (actorModel.getParentLayout() == 1) {
                if (actorModel.getLayout() != null) {
                    ((GraphicalEditPart)getParent()).setLayoutConstraint(this, getFigure(), actorModel.getLayout());
                }
            }
            else {
                super.refreshVisuals();
            }
        }
    }

    public void setSelected(int value) {
        ScrollingGraphicalViewer viewer = ProjectManager.getInstance().getUMLEditor().getScrollingGraphicalViewer();
        ;
        Tool tool = ProjectManager.getInstance().getUMLEditor().getUMLDefaultEditDomain().getActiveTool();
        if (!(tool instanceof MarqueeSelectionTool)) {
            if (value == 2) {
                //					ProjectManager.getInstance().setUMLElementEditPart(this);
                viewer.select(this.getParent());
                super.setSelected(0);
            }
            else {
                super.setSelected(value);
            }
        }
        else {
            super.setSelected(value);
            //				ProjectManager.getInstance().initIsDoubleClick();
        }
    }
}
