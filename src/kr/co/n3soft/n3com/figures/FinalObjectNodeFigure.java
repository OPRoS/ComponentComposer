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
import org.eclipse.gef.examples.logicdesigner.figures.FixedConnectionAnchor;
import org.eclipse.gef.examples.logicdesigner.figures.LogicColorConstants;
import org.eclipse.gef.examples.logicdesigner.model.Circuit;
import org.eclipse.gef.handles.HandleBounds;

public class FinalObjectNodeFigure extends UMLFigure implements HandleBounds {
    private IFigure pane;
    private TextFlow textFlow;
    String multi = "";
    String objectState = "";
    private boolean isStereo=false;//2008041401PKY S

    public FinalObjectNodeFigure() {
//        setBorder(new FinalObjectNodeBorder());//PKY 08080501 S 모델 그림자 넣기 작업
    	setBorder(new UMLModelBorder());//PKY 08080501 S 모델 그림자 넣기 작업
        createConnectionAnchors();
        setBackgroundColor(ColorConstants.tooltipBackground);
        this.setForegroundColor(ProjectManager.getInstance().getDefaultColor());//PKY 08070901 S 모델 컬러 변경
        setOpaque(true);
    }

    public void setObjectState(String p){
    	this.objectState = p;
    }
    public void setMultiplicity(String p){
    	this.multi = p;
    }
    
    protected void createConnectionAnchors() {
        ChopboxAnchor in, out;
        //	for(int i=0;i<10;i++){
        in = new ChopboxAnchor(this);
        out = new ChopboxAnchor(this);
        setOutputConnectionAnchor("B", out);
        setInputConnectionAnchor("A", in);
        outputConnectionAnchors.addElement(out);
        inputConnectionAnchors.addElement(in);
        //	}
    }

    //protected FixedConnectionAnchorFigure getInputConnectionAnchor(int i) {
    //	return (FixedConnectionAnchorFigure) connectionAnchors.get(Circuit.TERMINALS_IN[i]);
    //}
    public void setInputConnectionAnchor(String anName, ConnectionAnchor c) {
        connectionAnchors.put(anName, c);
    }

    public void setOutputConnectionAnchor(String anName, ConnectionAnchor c) {
        connectionAnchors.put(anName, c);
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

    public IFigure getContentsPane() {
        return pane;
    }

    protected FixedConnectionAnchor getInputConnectionAnchor(int i) {
        return (FixedConnectionAnchor)connectionAnchors.get(Circuit.TERMINALS_IN[i]);
    }

    /** @see org.eclipse.gef.handles.HandleBounds#getHandleBounds() */
    public Rectangle getHandleBounds() {
        return getBounds().getCropped(new Insets(2, 0, 2, 0));
    }

    protected FixedConnectionAnchor getOutputConnectionAnchor(int i) {
        return (FixedConnectionAnchor)connectionAnchors.get(Circuit.TERMINALS_OUT[i]);
    }

    public Dimension getPreferredSize(int w, int h) {
        Dimension prefSize = super.getPreferredSize(w, h);
        Dimension defaultSize = new Dimension(100, 50);
        prefSize.union(defaultSize);
        return prefSize;
    }

    protected void layoutConnectionAnchors() {
        int x;
        for (int i = 0; i < 4; i++) {
            x = (2 * i + 1) * getSize().width / 8;
            getOutputConnectionAnchor(i + 4).setOffsetH(x - 1);
            getInputConnectionAnchor(i).setOffsetH(x - 1);
            getInputConnectionAnchor(i + 4).setOffsetH(x);
            getOutputConnectionAnchor(i).setOffsetH(x);
        }
    }

    /** @see org.eclipse.draw2d.Figure#paintFigure(Graphics) */
    protected void paintFigure(Graphics graphics) {
        Rectangle r = Rectangle.SINGLETON;
        r.setBounds(getBounds());
        r.width = r.width - 2;
        r.height = r.height - 2;
//        multi = "1..0";
//        objectState = "22";
        graphics.fillRectangle(r);
        graphics.setForegroundColor(ProjectManager.getInstance().getDefaultColor());//PKY 08081101 S 프로젝트 전체의 객체 테두리 색상 설정 할  수있도록 수정
        //PKY 08052901 S Object Line그려지는문제
        //2008042302PKY S
        if(isStereo()==true){
//        	graphics.drawLine(r.x+20, r.y + 30, r.x + r.width - 20, r.y + 30);
        	graphics.drawLine(r.x+20, r.y + 30, r.x + r.width - 20, r.y + 30);

        }else{
            graphics.drawLine(r.x+20, r.y + 14, r.x + r.width - 20, r.y + 14);
        	
        }
      //2008042302PKY E
      //PKY 08052901 E Object Line그려지는문제
//        graphics.drawLine(r.x+20, r.y + 30, r.x + r.width - 20, r.y + 30);//PKY 08052901 S Object Line그려지는문제
        if(this.objectState!=null && this.objectState.trim().length()>0){
        	int index = objectState.length();
        	int x = r.x+r.width/2-index*8/2;
        	graphics.drawString("["+this.objectState+"]",x, r.y + 35);
        
        }
        if(this.multi!=null && this.multi.trim().length()>0){
        	int index = this.multi.length();
        	graphics.drawString(this.multi,r.x+r.width-6*index, r.y +2);
        }
        this.setForegroundColor(ProjectManager.getInstance().getDefaultColor());//PKY 08081101 S 프로젝트 전체의 객체 테두리 색상 설정 할  수있도록 수정
    }

   

    public void setInputConnectionAnchor(int i, ConnectionAnchor c) {
        connectionAnchors.put(Circuit.TERMINALS_IN[i], c);
    }

    public void setOutputConnectionAnchor(int i, ConnectionAnchor c) {
        connectionAnchors.put(Circuit.TERMINALS_OUT[i], c);
    }

    public String toString() {
        return "CircuitBoardFigure"; //$NON-NLS-1$
    }

    public void validate() {
        if (isValid()) return;
        //	layoutConnectionAnchors();
        super.validate();
    }

    protected boolean useLocalCoordinates() { return true; }
    //2008041401PKY S
	public boolean isStereo() {
		return isStereo;
	}

	public void setStereo(boolean isStereo) {
		this.isStereo = isStereo;
	}
	//2008041401PKY E
}
