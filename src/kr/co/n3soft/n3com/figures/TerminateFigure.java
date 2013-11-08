package kr.co.n3soft.n3com.figures;

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
import org.eclipse.gef.examples.logicdesigner.figures.LogicColorConstants;
import org.eclipse.gef.handles.HandleBounds;

public class TerminateFigure extends UMLFigure implements HandleBounds {
    private IFigure pane;
    private TextFlow textFlow;
    private boolean isDeepHistory = false;

    public TerminateFigure() {
        //	setBorder(new FinalBorder());
        createConnectionAnchors();
        setBackgroundColor(ColorConstants.black);
        this.setForegroundColor(ProjectManager.getInstance().getDefaultColor());//PKY 08070901 S 모델 컬러 변경
        setOpaque(false);
    }

    public IFigure getContentsPane() {
        return pane;
    }

    public void setIsDeepHistory(boolean p) {
        this.isDeepHistory = p;
    }

    public boolean getIsDeepHistory() {
        return this.isDeepHistory;
    }

    public Dimension getPreferredSize(int w, int h) {
        Dimension prefSize = super.getPreferredSize(w, h);
        Dimension defaultSize = new Dimension(10, 10);
        prefSize.union(defaultSize);
        return prefSize;
    }

    /** @see org.eclipse.draw2d.Figure#paintFigure(Graphics) */
    protected void paintFigure(Graphics graphics) {
        Rectangle r = Rectangle.SINGLETON;
        r.setBounds(getBounds());
        int width = r.width;
        int height = r.height;
        Rectangle r2 = Rectangle.SINGLETON;
        graphics.drawLine(r.x, r.y, r.x + width, r.y + height);
        graphics.drawLine(r.x, r.y + height, r.x + width, r.y);
        this.setForegroundColor(ProjectManager.getInstance().getDefaultColor());//PKY 08081101 S 프로젝트 전체의 객체 테두리 색상 설정 할  수있도록 수정
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
