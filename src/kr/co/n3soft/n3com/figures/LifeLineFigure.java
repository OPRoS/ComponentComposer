package kr.co.n3soft.n3com.figures;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import kr.co.n3soft.n3com.comm.figures.UMLFigure;
import kr.co.n3soft.n3com.model.comm.SeqChopboxAnchor;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.gef.examples.logicdesigner.figures.FixedConnectionAnchor;
import org.eclipse.gef.examples.logicdesigner.model.Circuit;
import org.eclipse.gef.handles.HandleBounds;

public class LifeLineFigure extends UMLFigure implements HandleBounds {
    private IFigure pane;
    private TextFlow textFlow;
//    SequenceGraphicManager sgm = null;
    java.util.ArrayList groups = new java.util.ArrayList();
    boolean isActor = false;
    boolean isDrag = false;
    String refDiagramName;
    int y = 0;
//    boolean isDelete = false;
    int lifeCycle = 0;
    //-1:none,0:new,1:delete

    public LifeLineFigure() {
//        //	BoundaryBorder
//        //	setBorder(new BoundaryBorder());
//        ScrollPane scrollpane = new ScrollPane();
//        pane = new FreeformLayer();
//        pane.setLayoutManager(new FreeformLayout());
//        setLayoutManager(new StackLayout());
//        add(scrollpane);
//        scrollpane.setViewport(new FreeformViewport());
//        scrollpane.setContents(pane);
//        createConnectionAnchors();
    	
//    }
//    	  setBorder(new LineBorder(ColorConstants.black, 1));
//          setBackgroundColor(ColorConstants.tooltipBackground);
//          this.setForegroundColor(LogicColorConstants.logicGreen);
          createConnectionAnchors();
          setOpaque(true);
    }

    protected void createConnectionAnchors() {
        SeqChopboxAnchor in, out;
        in = new SeqChopboxAnchor(this);
        out = new SeqChopboxAnchor(this);
        setOutputConnectionAnchor("1", out);
        setInputConnectionAnchor("2", in);
        SeqChopboxAnchor in1, out1;
        in1 = new SeqChopboxAnchor(this);
        out1 = new SeqChopboxAnchor(this);
        setOutputConnectionAnchor("3", out1);
        setInputConnectionAnchor("4", in1);
        SeqChopboxAnchor in2, out2;
        in2 = new SeqChopboxAnchor(this);
        out2 = new SeqChopboxAnchor(this);
        setOutputConnectionAnchor("5", out2);
        setInputConnectionAnchor("6", in2);
//        setOutputConnectionAnchor("kr.co.n3soft.n3com.model.comm.UMLDataModel@e71d5e@e71d5e11955061cfb36:26f9", out);
//        setInputConnectionAnchor("kr.co.n3soft.n3com.model.comm.UMLDataModel@e71d5e@e71d5e11955061cfb36:26f9", in);

        //	}
    }
    
//    public

    //protected FixedConnectionAnchorFigure getInputConnectionAnchor(int i) {
    //	return (FixedConnectionAnchorFigure) connectionAnchors.get(Circuit.TERMINALS_IN[i]);
    //}
    public void setInputConnectionAnchor(String anName, ConnectionAnchor c) {
        connectionAnchors.put(anName, c);
    }

    public void setOutputConnectionAnchor(String anName, ConnectionAnchor c) {
        connectionAnchors.put(anName, c);
    }
    
//    public void setSequenceGraphicManager(SequenceGraphicManager p){
////    	this.sgm=p;
//    }
    
    public void setRefDiagramName(String p){
    	this.refDiagramName = p;
    }
    
//    public ConnectionAnchor getTargetConnectionAnchorAt(Point p) {
//        ConnectionAnchor anchor = new ChopboxAnchor(this);
//        //	inputConnectionAnchors.addElement(anchor);
//        return anchor;
//    }
//
//    public ConnectionAnchor getSourceConnectionAnchorAt(Point p) {
//        ConnectionAnchor anchor = new ChopboxAnchor(this);
//        //	outputConnectionAnchors.addElement(anchor);
//        return anchor;
//    }

    public ConnectionAnchor getTargetConnectionAnchorAt(Point p) {
        //seq
        //	FixedConnectionAnchorFigure anchor =  new FixedConnectionAnchorFigure(this);
        //	
        SeqChopboxAnchor anchor = new SeqChopboxAnchor(this);

        System.out.println("getTargetConnectionAnchorAt==>" + p);

        return anchor;
    }
    

    public ConnectionAnchor getSourceConnectionAnchorAt(Point p) {
        SeqChopboxAnchor anchor = new SeqChopboxAnchor(this);
        System.out.println("anchor==>");

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
        return getBounds().getCropped(new Insets(0, 0, 0, 0));
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
    
    public void setGroups(java.util.ArrayList p){
    	this.groups = p;
    }
    
    public void setActor(boolean p){
    	this.isActor = p;
    }
    
    public boolean getActor(){
    	return this.isActor;
    }
    
    public void setDrag(boolean p){
    	this.isDrag = p;
    }
    
    public boolean getDrag(){
    	return this.isDrag;
    }
    
    
    public void setLifeCycle(int p){
    	this.lifeCycle = p;
    }
    
    public int getLifeCycle(){
    	return this.lifeCycle;
    }
    
    public void setDragY(int y){
    	this.y = y;
    	
    }

    /** @see org.eclipse.draw2d.Figure#paintFigure(Graphics) */
    protected void paintFigure(Graphics graphics) {
    	
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
    


    protected void paintActor(Graphics g2,Rectangle p) {
//    	r.y = r.y+20;
    	  Rectangle r = p.getCopy();
//          r.setBounds(p.getCopy());
    	  r.y = r.y+20;
    	  r.x = r.x +20;
    	  r.width = r.width-40;
    	  r.height = r.height;
        Ellipse2D.Float bounds = getCircle(r);
//        g2.setBackgroundColor(LogicColorConstants.defaultFillColor);
        g2.fillOval((int)bounds.getX() + (int)Math.round(1), (int)bounds.getY() + (int)Math.round(1), (int)bounds.getWidth() - (int)Math.round(1),
            (int)bounds.getHeight() - (int)Math.round(1));
        //오른쪽에 있는 컴포넌트의 사각형을 그린다 ///
        g2.fillOval((int)bounds.getX(), (int)bounds.getY(), (int)bounds.getWidth() - (int)Math.round(2),
            (int)bounds.getHeight() - (int)Math.round(2));
        g2.drawOval((int)bounds.getX(), (int)bounds.getY(), (int)bounds.getWidth() - (int)Math.round(2),
            (int)bounds.getHeight() - (int)Math.round(2));
        Line2D[] lines = getBodyLines(r);
        for (int i = 0; i < lines.length; i++) {
            g2.drawLine((int)lines[i].getX1(), (int)lines[i].getY1(), (int)lines[i].getX2(), (int)lines[i].getY2());
        }
    }

    protected Line2D[] getBodyLines(Rectangle r) {
  
        Rectangle actorBounds = r;
        int x = actorBounds.x;
        int y = actorBounds.y;
        Line2D[] _lines = new Line2D[4];
        _lines[0] = new Line2D.Float(x + actorBounds.width / 2, y + actorBounds.height * 4 / 10 + 1, x + actorBounds.width / 2,
            y + actorBounds.height * 7 / 10);
        _lines[1] = new Line2D.Float(x + 1, y + actorBounds.height * 5 / 10, x + actorBounds.width - 1 * 2,
            y + actorBounds.height * 5 / 10);
        _lines[2] = new Line2D.Float(x + actorBounds.width / 2, y + actorBounds.height * 7 / 10, x + 1,
            y + actorBounds.height - 1);
        _lines[3] = new Line2D.Float(x + actorBounds.width / 2, y + actorBounds.height * 7 / 10, x + actorBounds.width - 1 * 2,
            y + actorBounds.height - 1);
        //N3soft V1.3013 2004.04.08 안성수 2줄이상 보여지도록 수정 end
        return _lines;
    }

    protected Ellipse2D.Float getCircle(Rectangle r) {
//        Rectangle r = Rectangle.SINGLETON;
//        r.setBounds(getBounds());
        Rectangle actorBounds = r;
        float _x = actorBounds.width * 2 / 9 + actorBounds.x;
        float _y = 1 + actorBounds.y;
        float _width = actorBounds.width * 5 / 9;
        float _height = 1 + actorBounds.height * 4 / 10;
        return new Ellipse2D.Float(_x, _y, _width, _height);
    }

    protected boolean useLocalCoordinates() { return true; }
}
