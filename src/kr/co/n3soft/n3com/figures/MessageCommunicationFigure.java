package kr.co.n3soft.n3com.figures;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import kr.co.n3soft.n3com.comm.figures.UMLFigure;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.gef.handles.HandleBounds;

public class MessageCommunicationFigure extends UMLFigure implements HandleBounds {
    private IFigure pane;
    private TextFlow textFlow;
//    private boolean isArrow = false; 


    public MessageCommunicationFigure() {
        //	setBorder(new UseCaseBorder());
//        createConnectionAnchors();
        setBackgroundColor(ColorConstants.tooltipBackground);
        this.setForegroundColor(ColorConstants.black);
        setOpaque(true);
    }

    public IFigure getContentsPane() {
        return pane;
    }

    public Dimension getPreferredSize(int w, int h) {
        Dimension prefSize = super.getPreferredSize(w, h);
        Dimension defaultSize = new Dimension(50, 20);
        prefSize.union(defaultSize);
        return prefSize;
    }

    /** @see org.eclipse.draw2d.Figure#paintFigure(Graphics) */
    protected void paintFigure(Graphics graphics) {
    	this.setForegroundColor(ProjectManager.getInstance().getDefaultColor());//PKY 08081101 S 프로젝트 전체의 객체 테두리 색상 설정 할  수있도록 수정
    }
    
    public void setAngle(String p){
    	for(int i=0;i<this.getChildren().size();i++){
    		Object obj= this.getChildren().get(i);
    		if(obj instanceof ArrowFigure){
    			ArrowFigure af = (ArrowFigure)obj;
    			af.setAngle(p);
    		}
    	}
    }
  //PKY 08070301 S Communication Dialog 추가작업
    public void setBack(boolean isBack) {
    	for(int i=0;i<this.getChildren().size();i++){
    		Object obj= this.getChildren().get(i);
    		if(obj instanceof ArrowFigure){
    			ArrowFigure af = (ArrowFigure)obj;
    			af.setBack(isBack);
    		}
    	}
	}
    public void setSignature(int arrowType) {
    	for(int i=0;i<this.getChildren().size();i++){
    		Object obj= this.getChildren().get(i);
    		if(obj instanceof ArrowFigure){
    			ArrowFigure af = (ArrowFigure)obj;
    			af.setSignature(arrowType);
    		}
    	}
	}
    public void setSynch(int arrowType) {
    	for(int i=0;i<this.getChildren().size();i++){
    		Object obj= this.getChildren().get(i);
    		if(obj instanceof ArrowFigure){
    			ArrowFigure af = (ArrowFigure)obj;
    			af.setSynch(arrowType);
    		}
    	}
	}
    public void setKind(int arrowType) {
    	for(int i=0;i<this.getChildren().size();i++){
    		Object obj= this.getChildren().get(i);
    		if(obj instanceof ArrowFigure){
    			ArrowFigure af = (ArrowFigure)obj;
    			af.setKind(arrowType);
    		}
    	}
	}
  //PKY 08070301 E Communication Dialog 추가작업
    

    public String toString() {
        return "CircuitBoardFigure"; //$NON-NLS-1$
    }

    public void validate() {
        if (isValid()) return;
        super.validate();
    }

    protected boolean useLocalCoordinates() { return true; }

    protected void createConnectionAnchors() {
        ChopboxAnchor in, out;
        in = new ChopboxAnchor(this);
        out = new ChopboxAnchor(this);
        setOutputConnectionAnchor(out);
        setInputConnectionAnchor(in);
        outputConnectionAnchors.addElement(out);
        inputConnectionAnchors.addElement(in);
    }


    public void setInputConnectionAnchor(ConnectionAnchor c) {
        connectionAnchors.put("A", c);
    }

    public void setOutputConnectionAnchor(ConnectionAnchor c) {
        connectionAnchors.put("B", c);
    }

    public ConnectionAnchor getTargetConnectionAnchorAt(Point p) {
        ConnectionAnchor anchor = new ChopboxAnchor(this);
        //	inputConnectionAnchors.addElement(anchor);
        return anchor;
    }

    public ConnectionAnchor getSourceConnectionAnchorAt(Point p) {
        ConnectionAnchor anchor = new ChopboxAnchor(this);
        //	outputConnectionAnchors.addElement(anchor);
        return anchor;
    }


    protected void createConnectionAnchors(Point pt) {
        FixedConnectionAnchorFigure in, out;
        out = new FixedConnectionAnchorFigure(this);
        out.getLocation(pt);
        outputConnectionAnchors.addElement(out);
    }

    /** @see org.eclipse.gef.handles.HandleBounds#getHandleBounds() */
    public Rectangle getHandleBounds() {
        return getBounds().getCropped(new Insets(2, 0, 2, 0));
    }
}