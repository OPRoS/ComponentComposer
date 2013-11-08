package kr.co.n3soft.n3com.edit;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import kr.co.n3soft.n3com.edit.policy.UMLXYLayoutEditPolicy;
import kr.co.n3soft.n3com.figures.BoundaryBorder;
import kr.co.n3soft.n3com.figures.BoundaryFigure;
import kr.co.n3soft.n3com.model.usecase.BoundaryModel;
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

public class BoundaryEditPart extends UMLContainerEditPart {
    protected void createEditPolicies() {
        super.createEditPolicies();
        //		super.createEditPolicies();
        //		installEditPolicy(EditPolicy.LAYOUT_ROLE, new LogicFlowEditPolicy());
        installEditPolicy(EditPolicy.LAYOUT_ROLE, new UMLXYLayoutEditPolicy((XYLayout)getContentPane().getLayoutManager()));
        installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, new ContainerHighlightEditPolicy());
    }

    protected IFigure createFigure() {
        //		UseCaseFigure
        BoundaryFigure figure = new BoundaryFigure();
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

    protected BoundaryFigure getBoundaryFigureBoardFigure() {
        return (BoundaryFigure)getFigure();
    }

    public IFigure getContentPane() {
        return getBoundaryFigureBoardFigure().getContentsPane();
    }

    protected void refreshVisuals() {
        //		((UMLElementFigure)getFigure()).setText(getElementLabelModel().getLabelContents());
        if (getUMLModel() instanceof BoundaryModel) {
        	
        	
            BoundaryModel actorModel = (BoundaryModel)getUMLModel();
            if(this.getFigure() instanceof BoundaryFigure){
            	BoundaryFigure bf = (BoundaryFigure)this.getFigure();
            	BoundaryBorder bb = (BoundaryBorder)bf.getBorder();
            	bb.setMax(actorModel.getMaxb());
            	
            }
            if (actorModel.getParentLayout() == 1) {
                if (actorModel.getLayout() != null) {
                    ((GraphicalEditPart)getParent()).setLayoutConstraint(this, getFigure(), actorModel.getLayout());
                }
            }
            else {
                super.refreshVisuals();
            }
            
            
        }
        //		
        //		Point loc = getUMLModel().getLocation();
        //		Dimension size= getUMLModel().getSize();
        //		Rectangle r = new Rectangle(loc ,size);
        //		String name = getUMLModel().getName();
        //		Object obj = this.getModel();
        ////	    if(obj instanceof ClassModel){
        ////	    	ClassModel classModel = (ClassModel)obj;
        ////	    	classModel.setClassName(name);
        ////	    }
        //		((GraphicalEditPart) getParent()).setLayoutConstraint(
        //			this,
        //			getFigure(),
        //			r);
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
        //		this.getParent().setSelected(value);
        //			super.setSelected(value);
        //			if (selected == value)
        //				return;
        //			selected = value;
        //			fireSelectionChanged();
    }
    //	protected void refreshVisuals() {
    //		Point loc = getUMLModel().getLocation();
    //		Dimension size= getUMLModel().getSize();
    //		Rectangle r = new Rectangle(loc ,size);
    //		String name = getUMLModel().getName();
    //		Object obj = this.getModel();
    ////	    if(obj instanceof ClassModel){
    ////	    	ClassModel classModel = (ClassModel)obj;
    ////	    	classModel.setClassName(name);
    ////	    }
    //		((GraphicalEditPart) getParent()).setLayoutConstraint(
    //			this,
    //			getFigure(),
    //			r);
    //	}
}
