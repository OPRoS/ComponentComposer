package kr.co.n3soft.n3com.edit;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import kr.co.n3soft.n3com.figures.LifeLineFigure;

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

public class LifeLineEditPart extends UMLContainerEditPart {
    protected void createEditPolicies() {
        super.createEditPolicies();
        //		installEditPolicy(EditPolicy.NODE_ROLE, null);
        //		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new GraphicalNodeEditPolicy());
        //		installEditPolicy(EditPolicy.LAYOUT_ROLE, new UMLFlowEditPolicy());
        installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, new ContainerHighlightEditPolicy());
    }

    protected IFigure createFigure() {
        LifeLineFigure figure = new LifeLineFigure();
        BorderLayout flowLayout = new BorderLayout();
        figure.setLayoutManager(flowLayout);
        figure.setOpaque(false);
//        LifeLineModel lm = (LifeLineModel)this.getModel();
//        figure.setSequenceGraphicManager(lm.getSequenceGraphicManager());
        return figure;
    }
    
    public void createSourceConnectionAnchor(ConnectionAnchor ctor,String id){
//    	  SeqChopboxAnchor in, out;
////          in = new SeqChopboxAnchor(this);
////          out = new SeqChopboxAnchor(this);
    	LifeLineFigure figure = (LifeLineFigure)this.getFigure();
    	figure.setOutputConnectionAnchor(id, ctor);
//          setInputConnectionAnchor("A", in);
    }
    
    public void createTargetConnectionAnchor(ConnectionAnchor ctor,String id){
    	LifeLineFigure figure = (LifeLineFigure)this.getFigure();
    	figure.setInputConnectionAnchor(id, ctor);
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
    
    public void refresh() {
    	super.refresh();
    	
    }

    protected void refreshVisuals() {
        
    }
    //seq
    //	public ConnectionAnchor getTargetConnectionAnchor(ConnectionEditPart connEditPart) {
    //		LineModel wire = (LineModel) connEditPart.getModel();
    //		ConnectionAnchor connectionAnchor = getNodeFigure().getConnectionAnchor(wire.getSourceTerminal());
    //		if(connectionAnchor instanceof SeqChopboxAnchor){
    //			SeqChopboxAnchor seq = (SeqChopboxAnchor)connectionAnchor;
    //			seq.isMove = false;
    //		}
    //		return getNodeFigure().getConnectionAnchor(wire.getTargetTerminal());
    //	}
}
