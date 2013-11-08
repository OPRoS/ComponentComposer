package kr.co.n3soft.n3com.figures;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import kr.co.n3soft.n3com.comm.figures.UMLFigure;
import kr.co.n3soft.n3com.model.comm.UMLModel;
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
import org.eclipse.gef.examples.logicdesigner.model.Circuit;
import org.eclipse.gef.handles.HandleBounds;

public class CallBehaviorFigure extends UMLFigure implements HandleBounds {
    private IFigure pane;
    private TextFlow textFlow;
//    private UMLModel model;//PKY 08072401 S Action Action 저장 불러오기시 그림이 안되는문제

    public CallBehaviorFigure() {
        //	setBorder(new BoundaryBorder());
        //
        createConnectionAnchors();
        setBackgroundColor(ColorConstants.tooltipBackground);
        this.setForegroundColor(ProjectManager.getInstance().getDefaultColor());//PKY 08070901 S 모델 컬러 변경
        setOpaque(true);
    }

    protected void createConnectionAnchors() {
        ChopboxAnchor in, out;
        in = new ChopboxAnchor(this);
        out = new ChopboxAnchor(this);
        setOutputConnectionAnchor(1, out);
        setInputConnectionAnchor(1, in);
        outputConnectionAnchors.addElement(out);
        inputConnectionAnchors.addElement(in);
    }

    //protected FixedConnectionAnchorFigure getInputConnectionAnchor(int i) {
    //	return (FixedConnectionAnchorFigure) connectionAnchors.get(Circuit.TERMINALS_IN[i]);
    //}
    public void setInputConnectionAnchor(int i, ConnectionAnchor c) {
        connectionAnchors.put(Circuit.TERMINALS_IN[i], c);
    }

    public void setOutputConnectionAnchor(int i, ConnectionAnchor c) {
        connectionAnchors.put(Circuit.TERMINALS_OUT[i], c);
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

    public Dimension getPreferredSize(int w, int h) {
        Dimension prefSize = super.getPreferredSize(w, h);
        Dimension defaultSize = new Dimension(50, 20);
        prefSize.union(defaultSize);
        return prefSize;
    }

    /** @see org.eclipse.draw2d.Figure#paintFigure(Graphics) */
    protected void paintFigure(Graphics graphics) {
//        Rectangle r = Rectangle.SINGLETON;
//        r.setBounds(getBounds());
//        int y = r.y + r.height;
//        int x = r.x + r.width;
//        int x1 = r.x + r.width / 2 - 5;
//        int y1 = r.y;
//        graphics.drawText("(:)", x1, y1);
    	this.setForegroundColor(ProjectManager.getInstance().getDefaultColor());//PKY 08081101 S 프로젝트 전체의 객체 테두리 색상 설정 할  수있도록 수정
        Rectangle r = Rectangle.SINGLETON;
        r.setBounds(getBounds());
        Line2D[] _lines = new Line2D[4];
        int y = r.y + r.height;
        int x = r.x + r.width;
        int bottomX = x - 20;
        int bottomY = y;
        int topX = bottomX;
        int topY = bottomY - 15;
        int leftX = bottomX - 5;
        int leftY = topY + 5;
        int rightX = bottomX + 5;
        int rightY = leftY;
        int bottomX1 = leftX;
        int bottomY1 = leftY + 5;
        int topX1 = leftX;
        int topY1 = leftY;
        int bottomX2 = rightX;
        int bottomY2 = rightY + 5;
        int topX2 = rightX;
        int topY2 = rightY;
        _lines[0] = new Line2D.Float(bottomX, bottomY, topX, topY);
        _lines[1] = new Line2D.Float(leftX, leftY, rightX, rightY);
        _lines[2] = new Line2D.Float(bottomX1, bottomY1, topX1, topY1);
        _lines[3] = new Line2D.Float(bottomX2, bottomY2, topX2, topY2);
        for (int i = 0; i < _lines.length; i++) {
            graphics.drawLine((int)_lines[i].getX1(), (int)_lines[i].getY1(), (int)_lines[i].getX2(), (int)_lines[i].getY2());
        
    	}
    }

    public Rectangle getActorBounds(Rectangle bounds) {
        int height = bounds.height * 7 / 10;
        int width = bounds.width;
        int x = 0;
        int y = 0;
        if (width * 4 >= height * 3) { // Width가 더 클경우
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
        //오른쪽에 있는 컴포넌트의 사각형을 그린다 ///
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
        //N3soft V1.3013 2004.04.08 안성수 2줄이상 보여지도록 수정 end
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
  //PKY 08072401 S Action Action 저장 불러오기시 그림이 안되는문제
//	public UMLModel getModel() {
//		return model;
//	}
//
//	public void setModel(UMLModel model) {
//		this.model = model;
//	}
	//PKY 08072401 E Action Action 저장 불러오기시 그림이 안되는문제

}
