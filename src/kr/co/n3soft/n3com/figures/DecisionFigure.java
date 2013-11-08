package kr.co.n3soft.n3com.figures;

import java.util.Enumeration;
import kr.co.n3soft.n3com.anchor.FixedAnchor;
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
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.gef.examples.logicdesigner.figures.FixedConnectionAnchor;
import org.eclipse.gef.examples.logicdesigner.figures.LogicColorConstants;
import org.eclipse.gef.handles.HandleBounds;

public class DecisionFigure extends UMLFigure implements HandleBounds {
    private IFigure pane;
    private TextFlow textFlow;

    public DecisionFigure() {
        setBorder(new DecisionBorder());
        createConnectionAnchors();
        setBackgroundColor(ColorConstants.black);
        this.setForegroundColor(ProjectManager.getInstance().getDefaultColor());//PKY 08070901 S 모델 컬러 변경
        setOpaque(false);
    }

    public IFigure getContentsPane() {
        return pane;
    }

    public Dimension getPreferredSize(int w, int h) {
        Dimension prefSize = super.getPreferredSize(w, h);
        Dimension defaultSize = new Dimension(10, 10);
        prefSize.union(defaultSize);
        return prefSize;
    }

    /** @see org.eclipse.draw2d.Figure#paintFigure(Graphics) */
    protected void paintFigure(Graphics graphics) {
        //	  int[] xValues = {0, getWidth()/2, getWidth(), getWidth()/2};
        //      int[] yValues = {getHeight()/2, 0, getHeight()/2, getHeight()-1};
        Rectangle r = Rectangle.SINGLETON;
        r.setBounds(getBounds());
        int width = r.width - 2;
        int height = r.height - 2;
        PointList pt = new PointList();
        pt.addPoint(r.x, r.y + height / 2);
        pt.addPoint(r.x + width / 2, r.y);
        pt.addPoint(r.x + width, r.y + height / 2);
        pt.addPoint(r.x + width / 2, r.y + height);
        graphics.fillPolygon(pt);
        this.setForegroundColor(ProjectManager.getInstance().getDefaultColor());//PKY 08081101 S 프로젝트 전체의 객체 테두리 색상 설정 할  수있도록 수정
        //	graphics.drawPolygon(pt);
        //	graphics.fillOval(r);
    }

    public String toString() {
        return "CircuitBoardFigure"; //$NON-NLS-1$
    }

    public void validate() {
        try {
            if (isValid()) return;
            //	layoutConnectionAnchors();
            super.validate();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected boolean useLocalCoordinates() { return true; }

    protected void createConnectionAnchors() {
        //	FixedAnchor c;
        FixedAnchor in1 = new FixedAnchor(this);
        in1.place = new Point(1, 0);
        FixedAnchor in2 = new FixedAnchor(this);
        in2.place = new Point(2, 1);
        FixedAnchor in3 = new FixedAnchor(this);
        in3.place = new Point(1, 2);
        FixedAnchor in4 = new FixedAnchor(this);
        in4.place = new Point(0, 1);
    	
//    	  FixedAnchor in1 = new FixedAnchor(this);
//          in1.place = new Point(2, 2);
//          FixedAnchor in2 = new FixedAnchor(this);
//          in2.place = new Point(2, 2);
//          FixedAnchor in3 = new FixedAnchor(this);
//          in3.place = new Point(2, 2);
//          FixedAnchor in4 = new FixedAnchor(this);
//          in4.place = new Point(2, 2);
    	
    	
        //	c.offsetH = 51;
        //	connectionAnchors.put(LED.TERMINAL_1_IN, c);
        //	inputConnectionAnchors.addElement(c);
        //	
        //	ChopboxAnchor in,out;
        //
        ////	for(int i=0;i<10;i++){
        //	in = new ChopboxAnchor(this);
        //	out= new ChopboxAnchor(this);
        setOutputConnectionAnchor("B1", in1);
        setInputConnectionAnchor("A1", in1);
        setOutputConnectionAnchor("B2", in2);
        setInputConnectionAnchor("A2", in2);
        setOutputConnectionAnchor("B3", in3);
        setInputConnectionAnchor("A3", in3);
        setOutputConnectionAnchor("B4", in4);
        setInputConnectionAnchor("A4", in4);
        outputConnectionAnchors.addElement(in1);
        inputConnectionAnchors.addElement(in1);
        outputConnectionAnchors.addElement(in2);
        inputConnectionAnchors.addElement(in2);
        outputConnectionAnchors.addElement(in3);
        inputConnectionAnchors.addElement(in3);
        outputConnectionAnchors.addElement(in4);
        inputConnectionAnchors.addElement(in4);
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
        ConnectionAnchor closest = null;
        long min = Long.MAX_VALUE;
        Enumeration e = getTargetConnectionAnchors().elements();
        while (e.hasMoreElements()) {
            ConnectionAnchor c = (ConnectionAnchor)e.nextElement();
            Point p2 = c.getLocation(null);
            long d = p.getDistance2(p2);
            if (d < min) {
                min = d;
                closest = c;
            }
        }
        return closest;
    }

    public ConnectionAnchor getSourceConnectionAnchorAt(Point p) {
        ConnectionAnchor closest = null;
        long min = Long.MAX_VALUE;
        Enumeration e = getTargetConnectionAnchors().elements();
        while (e.hasMoreElements()) {
            ConnectionAnchor c = (ConnectionAnchor)e.nextElement();
            Point p2 = c.getLocation(null);
            long d = p.getDistance2(p2);
            if (d < min) {
                min = d;
                closest = c;
            }
        }
        return closest;
    }

    protected void createConnectionAnchors(Point pt) {
        FixedConnectionAnchorFigure in, out;
        out = new FixedConnectionAnchorFigure(this);
        out.getLocation(pt);
        outputConnectionAnchors.addElement(out);
    }

    //public void setLocation(Point p) {
    //	if (getLocation().equals(p))
    //		return;
    //	
    //	Rectangle r = new Rectangle(getBounds());
    //	r.setLocation(this.getLocation(p));
    //	setBounds(r);
    //}
    protected Rectangle getBox() {
        return this.getParent().getBounds();
    }

    public Point getLocation(Point reference) {
        Rectangle r = Rectangle.SINGLETON;
        r.setBounds(getBox());
        r.translate(-1, -1);
        r.resize(1, 1);
        this.getParent().translateToAbsolute(r);
        float centerX = r.x + 0.5f * r.width;
        float centerY = r.y + 0.5f * r.height;
        if (r.isEmpty() || (reference.x == (int)centerX && reference.y == (int)centerY))
            return new Point((int)centerX, (int)centerY); //This avoids divide-by-zero
        float dx = reference.x - centerX;
        float dy = reference.y - centerY;
        //r.width, r.height, dx, and dy are guaranteed to be non-zero.
        float scale = 0.5f / Math.max(Math.abs(dx) / r.width, Math.abs(dy) / r.height);
        dx *= scale;
        dy *= scale;
        centerX += dx;
        centerY += dy;
        return new Point(Math.round(centerX), Math.round(centerY));
    }

    /** @see org.eclipse.gef.handles.HandleBounds#getHandleBounds() */
    public Rectangle getHandleBounds() {
        return getBounds().getCropped(new Insets(2, 0, 2, 0));
    }
}
