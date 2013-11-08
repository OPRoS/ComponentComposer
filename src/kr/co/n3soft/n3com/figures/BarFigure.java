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
import org.eclipse.gef.examples.logicdesigner.figures.LogicColorConstants;
import org.eclipse.gef.handles.HandleBounds;

public class BarFigure extends UMLFigure implements HandleBounds {
    private IFigure pane;
    private TextFlow textFlow;
//    private boolean isArrow = false; 


    public BarFigure() {
        //	setBorder(new UseCaseBorder());
        createConnectionAnchors();
        setBackgroundColor(ColorConstants.tooltipBackground);
        this.setForegroundColor(ProjectManager.getInstance().getDefaultColor());//PKY 08070901 S �� �÷� ����
        setOpaque(true);
    }

    public IFigure getContentsPane() {
        return pane;
    }

    public Dimension getPreferredSize(int w, int h) {
        Dimension prefSize = super.getPreferredSize(w, h);
        Dimension defaultSize = new Dimension(100, 50);
        prefSize.union(defaultSize);
        return prefSize;
    }

    /** @see org.eclipse.draw2d.Figure#paintFigure(Graphics) */
    protected void paintFigure(Graphics graphics) {
        //	this.paintBody(graphics);
        System.out.println("paintFigure");
        Rectangle r = Rectangle.SINGLETON;
        r.setBounds(getBounds());
        int width = r.width - 2;
        int height = r.height - 2;
        int x = r.x + 5;
        int y = r.y + 5;
        //	graphics.drawRectangle(r);
        graphics.drawLine(r.x + 20, r.y + 5, r.x + width - 20, r.y + 5);
        //	graphics.setBackgroundColor(ColorConstants.black);
        //	graphics.fillRectangle(r);
    }

    public Rectangle getActorBounds(Rectangle bounds) {
        int height = bounds.height * 7 / 10;
        int width = bounds.width;
        int x = 0;
        int y = 0;
        if (width * 4 >= height * 3) { // Width�� �� Ŭ���
            width = height * 3 / 4;
            x += (bounds.width - width) / 2;
        } else {
            height = width * 4 / 3;
            y += (bounds.height * 7 / 10 - height) / 2;
        }
        return new Rectangle(x, y, width, height);
    }

    protected void paintBody(Graphics g2) {
        Ellipse2D.Float bounds = getCircle();
        g2.fillOval((int)bounds.getX() + (int)Math.round(1), (int)bounds.getY() + (int)Math.round(1), (int)bounds.getWidth() - (int)Math.round(1),
            (int)bounds.getHeight() - (int)Math.round(1));
        //�����ʿ� �ִ� ������Ʈ�� �簢���� �׸��� ///
        g2.fillOval((int)bounds.getX(), (int)bounds.getY(), (int)bounds.getWidth() - (int)Math.round(2),
            (int)bounds.getHeight() - (int)Math.round(2));
        g2.drawOval((int)bounds.getX(), (int)bounds.getY(), (int)bounds.getWidth() - (int)Math.round(2),
            (int)bounds.getHeight() - (int)Math.round(2));
        Line2D[] lines = getBodyLines();
        for (int i = 0; i < lines.length; i++) {
            g2.drawLine((int)lines[i].getX1(), (int)lines[i].getY1(), (int)lines[i].getX2(), (int)lines[i].getY2());
        }
    }

    protected Line2D[] getBodyLines() {
        Rectangle r = Rectangle.SINGLETON;
        r.setBounds(getBounds());
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
        //N3soft V1.3013 2004.04.08 �ȼ��� 2���̻� ���������� ���� end
        return _lines;
    }

    protected Ellipse2D.Float getCircle() {
        Rectangle r = Rectangle.SINGLETON;
        r.setBounds(getBounds());
        Rectangle actorBounds = r;
        float _x = actorBounds.width * 2 / 9 + actorBounds.x;
        float _y = 1 + actorBounds.y;
        float _width = actorBounds.width * 5 / 9;
        float _height = 1 + actorBounds.height * 4 / 10;
        return new Ellipse2D.Float(_x, _y, _width, _height);
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

    protected void createConnectionAnchors() {
        ChopboxAnchor in, out;
        in = new ChopboxAnchor(this);
        out = new ChopboxAnchor(this);
        setOutputConnectionAnchor(out);
        setInputConnectionAnchor(in);
        outputConnectionAnchors.addElement(out);
        inputConnectionAnchors.addElement(in);
    }

    //protected FixedConnectionAnchorFigure getInputConnectionAnchor(int i) {
    //	return (FixedConnectionAnchorFigure) connectionAnchors.get(Circuit.TERMINALS_IN[i]);
    //}
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

    //protected void layoutConnectionAnchors() {
    //	int x;
    //	for (int i = 0; i < 4; i++){
    //		x = (2*i+1) * getSize().width / 8;
    //		getOutputConnectionAnchor(i+4).setOffsetH(x-1);
    //		getInputConnectionAnchor(i).setOffsetH(x-1);
    //		getInputConnectionAnchor(i+4).setOffsetH(x);
    //		getOutputConnectionAnchor(i).setOffsetH(x);
    //	}
    //}
    //
    //
    //
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
