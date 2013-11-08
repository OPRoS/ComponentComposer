package kr.co.n3soft.n3com.figures;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import kr.co.n3soft.n3com.comm.figures.UMLFigure;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.usecase.UseCaseModel;
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
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

public class ActorFigure extends UMLFigure implements HandleBounds {
    private IFigure pane;
    private TextFlow textFlow;
    Image image = null;
    int w1 = 0;
    int h1 = 0;
    private String actorName = "";

    public ActorFigure() {
	//	setBorder(new UseCaseBorder());
	createConnectionAnchors();
	setBackgroundColor(LogicColorConstants.defaultFillColor);
	this.setForegroundColor(ProjectManager.getInstance().getDefaultColor());//PKY 08070901 S 모델 컬러 변경
	if(image!=null){
	    w1 = image.getBounds().width;
	    h1 = image.getBounds().height;
	}
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

    public Image getActorImage(String p){
//	return ImageDescriptor.createFromFile(UseCaseModel.class, "icons/"+p).createImage();
	return ProjectManager.getInstance().getActorImage(p);//PKY 08080501 S Image Dialog 이미지 리스트 및 불러오기 편하도록 개선


    }

    /** @see org.eclipse.draw2d.Figure#paintFigure(Graphics) */
    protected void paintFigure(Graphics graphics) {
    	this.setForegroundColor(ProjectManager.getInstance().getDefaultColor());//PKY 08081101 S 프로젝트 전체의 객체 테두리 색상 설정 할  수있도록 수정
	String str = (String)((UMLFigure)this.getParent()).getProperty().get(UMLModel.ID_ACTORIMAGE);
	if(str!=null && !actorName.equals(str)){
	    actorName = str;
	    image = this.getActorImage(actorName);
	}
	else if(str==null || str.trim().equals("")){
	    image = null;
	}
	if(!str.trim().equals(""))//PKY 08081101 S 이미지 해제 후 객체가 붉게 표시되는 문제 수정
	if(image!=null){
	    Rectangle r = Rectangle.SINGLETON;
	    r.setBounds(getBounds());


	    String sw1 = (String)((UMLFigure)this.getParent()).getProperty().get(UMLModel.ID_W);
	    String sh1 = (String)((UMLFigure)this.getParent()).getProperty().get(UMLModel.ID_H);
	    if(sw1!=null && sh1!=null){
		try{
		    w1 = Integer.valueOf(sw1).intValue();
		    h1 = Integer.valueOf(sh1).intValue();
		}
		catch(Exception e){
		    w1 = image.getBounds().width;
		    h1 = image.getBounds().height;
		    e.printStackTrace();
		}
	    }

	    graphics.drawImage(image, 0,0,w1,h1,0,0,r.width,r.height);
	}
	else{
	    this.paintBody(graphics);
	    
	}
	else{
		  this.paintBody(graphics);
	}
	//PKY 08081101 S 이미지 해제 후 객체가 붉게 표시되는 문제 수정





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
