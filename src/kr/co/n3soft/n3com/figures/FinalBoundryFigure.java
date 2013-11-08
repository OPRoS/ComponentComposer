package kr.co.n3soft.n3com.figures;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import kr.co.n3soft.n3com.comm.figures.UMLFigure;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.FreeformViewport;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.ScrollPane;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.gef.examples.logicdesigner.figures.FixedConnectionAnchor;
import org.eclipse.gef.examples.logicdesigner.figures.LogicColorConstants;
import org.eclipse.gef.examples.logicdesigner.model.Circuit;
import org.eclipse.gef.handles.HandleBounds;

public class FinalBoundryFigure extends UMLFigure implements HandleBounds {
    private IFigure pane;
    private TextFlow textFlow;

    public FinalBoundryFigure() {
	setBorder(new UMLModelBorder());//PKY 08080501 S 모델 그림자 넣기 작업
	setBackgroundColor(ProjectManager.getInstance().getDefaultColor());//PKY 08070901 S 모델 컬러 변경
	this.setForegroundColor(ProjectManager.getInstance().getDefaultColor());//PKY 08070901 S 모델 컬러 변경
	setOpaque(true);
//	setBorder(new LineBorder(ColorConstants.buttonDarkest, 1));
//	setBackgroundColor(ProjectManager.getInstance().getDefaultColor());//PKY 08070901 S 모델 컬러 변경
//	setForegroundColor(ProjectManager.getInstance().getDefaultColor());//PKY 08070901 S 모델 컬러 변경

//	createConnectionAnchors();
//	setOpaque(true);
    }

    protected void createConnectionAnchors() {
	FixedConnectionAnchor in, out;
	for (int i = 0; i < 8; i++) {
	    in = new FixedConnectionAnchor(this);
	    out = new FixedConnectionAnchor(this);
	    if (i > 3) {
		in.topDown = false;
		in.offsetV = 5;
		out.topDown = false;
	    } else {
		out.offsetV = 5;
	    }
	    setOutputConnectionAnchor(i, out);
	    setInputConnectionAnchor(i, in);
	    outputConnectionAnchors.addElement(out);
	    inputConnectionAnchors.addElement(in);
	}
    }

    protected void createConnectionAnchors(Point pt) {
	FixedConnectionAnchor in, out;
	out = new FixedConnectionAnchor(this);
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
	//PKY 08070904 S 바운더리 투명 문제

	Rectangle r = Rectangle.SINGLETON;
	r.setBounds(getBounds());
	//PKY 08071601 S 모델 투명 문제 좌표가 마이너스로 가게 되면 모델 투명되는현상

	int y = 0;
	int x=0;
	if(r.x>=0){
	    x = r.x + r.width;
	}else{
	    x = r.width -r.x ;
	}
	if(r.x>=0){
	    y=r.y + r.height;
	}else{
	    y=r.y- r.height ;
	}

	int x1 = r.x + r.width - 15;
	int y1 = r.y;

	graphics.drawRectangle(r.x, r.y,x, y);
	graphics.setBackgroundColor(getBackgroundColor());//PKY 08090907 S
		graphics.fillRectangle(r);
	this.setForegroundColor(ProjectManager.getInstance().getDefaultColor());//PKY 08081101 S 프로젝트 전체의 객체 테두리 색상 설정 할  수있도록 수정
    }
    //PKY 08070904 E 바운더리 투명 문제
    //PKY 08071601 E 모델 투명 문제 좌표가 마이너스로 가게 되면 모델 투명되는현상

//  graphics.drawRectangle(x1 - 3, y1 + 3, 7, 3);
//  graphics.drawRectangle(x1 - 3, y1 + 8, 7, 3);
//  graphics.setBackgroundColor(ColorConstants.green);
//  graphics.fillRectangle(x1 - 3, y1 + 3, 7, 3);
//  graphics.fillRectangle(x1 - 3, y1 + 8, 7, 3);


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
}
