package kr.co.n3soft.n3com.edit;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import kr.co.n3soft.n3com.comm.figures.UMLFigure;
import kr.co.n3soft.n3com.figures.ActorFigure;
import kr.co.n3soft.n3com.figures.FinalActorFigure;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.usecase.FinalActorModel;

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

public class FinalActorEditPart extends UMLContainerEditPart {
    protected void createEditPolicies() {
        super.createEditPolicies();
        //		installEditPolicy(EditPolicy.NODE_ROLE, null);
        //		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new GraphicalNodeEditPolicy());
        //		installEditPolicy(EditPolicy.LAYOUT_ROLE, new UMLFlowEditPolicy());
        installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, new ContainerHighlightEditPolicy());
    }

    protected IFigure createFigure() {
        //		UseCaseFigure
        FinalActorFigure figure = new FinalActorFigure();
        BorderLayout flowLayout = new BorderLayout();
        //		ActorFigure actorFigure = new ActorFigure();
        //		FlowLayout flowLayout = new FlowLayout();
        //		flowLayout.setMinorSpacing(0);
        //		flowLayout.setMajorSpacing(0);
        figure.setLayoutManager(flowLayout);
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
        String name = getUMLModel().getName();
        Object obj = this.getModel();
        getFigure().setBackgroundColor(getUMLModel().getBackGroundColor());
        FinalActorFigure uf = (FinalActorFigure)getFigure();
        for(int i=0;i<uf.getChildren().size();i++){
        	UMLFigure ufe = (UMLFigure)uf.getChildren().get(i);
        	if(ufe instanceof ActorFigure){
        		ufe.setBackgroundColor(getUMLModel().getBackGroundColor());
        	}
        }
        if(uf.getChildren().size()==0){
        	FinalActorModel fm = (FinalActorModel)obj;
        	fm.getActorModel().setBackGroundColor(getUMLModel().getBackGroundColor());
        }
        //20080822IJS 시작
        try{
        uf.getProperty().put(UMLModel.ID_ACTORIMAGE, getUMLModel().getActorImage());
        
        uf.getProperty().put(UMLModel.ID_W, getUMLModel().getUMLDataModel().getProperty(UMLModel.ID_W));
        uf.getProperty().put(UMLModel.ID_H, getUMLModel().getUMLDataModel().getProperty(UMLModel.ID_H));
        uf.getProperty().put(UMLModel.ID_IMAGEDATA, getUMLModel().getUMLDataModel().getElementProperty(UMLModel.ID_IMAGEDATA));

        }
        catch(Exception e){
        	e.printStackTrace();
        }
        System.out.println("====================================>액터");
        //20080822IJS 끝
//        uf.getProperty().put(UMLModel.ID_ACTORIMAGE, getUMLModel().getp);
        
        //	    if(obj instanceof ClassModel){
        //	    	ClassModel classModel = (ClassModel)obj;
        //	    	classModel.setClassName(name);
        //	    }
        ((GraphicalEditPart)getParent()).setLayoutConstraint(this, getFigure(), r);
    }
}
