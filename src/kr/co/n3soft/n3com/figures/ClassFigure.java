package kr.co.n3soft.n3com.figures;

import kr.co.n3soft.n3com.anchor.FreeChopboxAnchor;
import kr.co.n3soft.n3com.comm.figures.UMLElementFigure;
import kr.co.n3soft.n3com.comm.figures.UMLFigure;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.gef.examples.logicdesigner.figures.LogicColorConstants;
import org.eclipse.gef.handles.HandleBounds;

public class ClassFigure extends UMLFigure implements HandleBounds {
    private IFigure pane;
    private TextFlow textFlow;
    private boolean isComponent = false;
    private boolean isArtiface = false;
    private int cornerSize = 5;

    public ClassFigure() {
        ToolbarLayout layout = new ToolbarLayout();
        setLayoutManager(layout);
        setOpaque(true);
        createConnectionAnchors();
        this.setForegroundColor(ProjectManager.getInstance().getDefaultColor());//PKY 08070901 S 모델 컬러 변경
        //	this.setForegroundColor(LogicColorConstants.logicGreen);
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

    public void setIsComponent(boolean p) {
        this.isComponent = p;
    }

    public boolean getIsComponent() {
        return this.isComponent;
    }

    public void setIsArtiface(boolean p) {
        this.isArtiface = p;
    }

    public boolean getIsArtiface() {
        return this.isArtiface;
    }

    /** @see org.eclipse.draw2d.Figure#paintFigure(Graphics) */
    protected void paintFigure(Graphics graphics) {
        if (isComponent) {
//        	graphics.setForegroundColor(ProjectManager.getInstance().getDefaultColor());//PKY 08081101 S 프로젝트 전체의 객체 테두리 색상 설정 할  수있도록 수정
//            Rectangle r = Rectangle.SINGLETON;
//            r.setBounds(getBounds());
//            int y = r.y + r.height;
//            int x = r.x + r.width;
//            int x1 = r.x + r.width - 15;
//            int y1 = r.y;
//            graphics.setForegroundColor(ColorConstants.black);
//            graphics.drawRectangle(x1, y1 + 2, 13, 10);
//            graphics.setBackgroundColor(ColorConstants.yellow);
//            graphics.fillRectangle(x1, y1 + 2, 13, 10);
//            graphics.drawRectangle(x1 - 3, y1 + 3, 7, 3);
//            graphics.drawRectangle(x1 - 3, y1 + 8, 7, 3);
//            graphics.setBackgroundColor(ColorConstants.green);
//            graphics.fillRectangle(x1 - 3, y1 + 3, 7, 3);
//            graphics.fillRectangle(x1 - 3, y1 + 8, 7, 3);
        }
        else if (this.isArtiface) {
        	graphics.setForegroundColor(ProjectManager.getInstance().getDefaultColor());//PKY 08081101 S 프로젝트 전체의 객체 테두리 색상 설정 할  수있도록 수정
            Rectangle r = Rectangle.SINGLETON;
            r.setBounds(getBounds());
            int y = r.y + r.height;
            int x = r.x + r.width;
            int x1 = r.x + r.width - 15;
            int y1 = r.y;
            Rectangle rect = new Rectangle(x1, y1 + 2, 13, 15);
            //    	graphics.translate(getLocation());
            // fill the note
            PointList outline = new PointList();
            int x2 = x1;
            int y2 = y1 + 2;
            outline.addPoint(x2, y2);
            outline.addPoint(x2 + rect.width - cornerSize, y2);
            outline.addPoint(x2 + rect.width - 1, y2 + cornerSize);
            outline.addPoint(x2 + rect.width - 1, y2 + rect.height - 1);
            outline.addPoint(x2, y2 + rect.height - 1);
            graphics.setBackgroundColor(ColorConstants.yellow);
            graphics.fillPolygon(outline);
            // draw the inner outline
            PointList innerLine = new PointList();
            innerLine.addPoint(x2 + rect.width - cornerSize - 1, y2);
            innerLine.addPoint(x2 + rect.width - cornerSize - 1, y2 + cornerSize);
            innerLine.addPoint(x2 + rect.width - 1, y2 + cornerSize);
            innerLine.addPoint(x2 + rect.width - cornerSize - 1, y2);
            innerLine.addPoint(x2, y2);
            innerLine.addPoint(x2, y2 + rect.height - 1);
            innerLine.addPoint(x2 + rect.width - 1, y2 + rect.height - 1);
            innerLine.addPoint(x2 + rect.width - 1, y2 + cornerSize);
            graphics.drawPolygon(innerLine);
            
            graphics.drawLine(x2, y2 + 3, x2 + 5, y2 + 3);
            graphics.drawLine(x2, y2 + 5, x2 + 5, y2 + 5);
            graphics.drawLine(x2, y2 + 7, x2 + 7, y2 + 7);
            graphics.drawLine(x2, y2 + 10, x2 + 5, y2 + 10);
            //		graphics.translate(getLocation().getNegated());
            //    	graphics.drawRectangle(x1-3,y1+3,7,3);
            //    	graphics.drawRectangle(x1-3,y1+8,7,3);
            //    	graphics.setBackgroundColor(ColorConstants.green);
            //    	graphics.fillRectangle(x1-3,y1+3,7,3);
            //    	graphics.fillRectangle(x1-3,y1+8,7,3);
        }
    }

    protected void createConnectionAnchors() {
//        ChopboxAnchor in, out;
//        in = new ChopboxAnchor(this);
//        out = new ChopboxAnchor(this);
//        setOutputConnectionAnchor(out);
//        setInputConnectionAnchor(in);
//        outputConnectionAnchors.addElement(out);
//        inputConnectionAnchors.addElement(in);
    	
      ChopboxAnchor in, out;
      in = new FreeChopboxAnchor(this);
      out = new FreeChopboxAnchor(this);
      setOutputConnectionAnchor(out);
      setInputConnectionAnchor(in);
      outputConnectionAnchors.addElement(out);
      inputConnectionAnchors.addElement(in);
        //	}
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
        anchor.getLocation(p);
        //	inputConnectionAnchors.addElement(anchor);
        return anchor;
    }

    public ConnectionAnchor getSourceConnectionAnchorAt(Point p) {
        ConnectionAnchor anchor = new ChopboxAnchor(this);
        //	outputConnectionAnchors.addElement(anchor);
        return anchor;
    }

    /** @see org.eclipse.gef.handles.HandleBounds#getHandleBounds() */
    public Rectangle getHandleBounds() {
        return getBounds().getCropped(new Insets(2, 0, 2, 0));
    }

    public String toString() {
        return "ClassFigure"; //$NON-NLS-1$
    }

    public void validate() {
        super.validate();
        java.util.List list = this.getChildren();
        int count = list.size();
        int h1 = 0;
        int figureHeight = 0;
        for (int i = 0; i < count; i++) {
            Figure figure = (Figure)list.get(i);
            if (figure instanceof UMLElementFigure) {
                figureHeight = 15;
            }
            else if (figure instanceof CompartmentFigure) {
                if (figure.getBounds().height < 15) {
                    figureHeight = 15;
                    //				Rectangle r= figure.getBounds();
                    //				r.height = 40;
                    //				figure.setBounds(r);
                }
                else {
                    figureHeight = figure.getBounds().height;
                }
            }
            h1 = h1 + figureHeight;
        }
        Dimension defaultSize = new Dimension(this.getBounds().width, h1);
        this.setSize(defaultSize);
    }

    protected boolean useLocalCoordinates() { return true; }
}
