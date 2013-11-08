package kr.co.n3soft.n3com.figures;

import kr.co.n3soft.n3com.comm.figures.UMLFigure;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.gef.handles.HandleBounds;
import org.eclipse.swt.graphics.Image;

public class MethodInputPortFigure extends UMLFigure implements HandleBounds {
    private IFigure pane;
    private TextFlow textFlow;
    private Image image;	// WJH 090801
    private Image image2;	// WJH 090819
    public MethodInputPortFigure() {
//        setBorder(new PortBorder());
        createConnectionAnchors();
//        setBackgroundColor(ColorConstants.tooltipBackground);
        this.setForegroundColor(ProjectManager.getInstance().getDefaultColor());//PKY 08070901 S 모델 컬러 변경
        setOpaque(true);
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
//    	WJH 090801 S
    	 Rectangle r = Rectangle.SINGLETON;
    	 r.setBounds(getBounds());
//         r.width = r.width - 2;
//         r.height = r.height - 2;
//         WJH 090818 S
//    	 WJH 090819 S
         if(image == null){
        	 image = ProjectManager.getInstance().getImageReSize(10006, r.width, r.height);
        	 image2 = ProjectManager.getInstance().getImageReSize(10015, 32, 32);
         }
//       WJH 090818 E
         if(image != null){
        	 if(r.width==16 && r.height == 16){	        		
	        		graphics.drawImage(image, r.x, r.y);
	        	}
	        	else if(r.width ==32 && r.height == 32){	        		
	        		graphics.drawImage(image2, r.x, r.y);
	        	}     		
         }
//    	 WJH 090819 E
//         WJH 090801 E
         
//         r.setBounds(getBounds());
//         r.width = r.width - 2;
//         r.height = r.height - 2;
//         int x = r.x;
//         int y = r.y;
//         int height = 20;
//         Rectangle r2 = null;
//         Rectangle r3 = null;
//         Rectangle r2Back = null;
//       
//         if(arrow==1){
//        	 int x1 = x+r.width/2;
//        	 int y1 = y+r.height;
//        	 graphics.drawLine(new Point(x1,y1), new Point(x1,y1-height));
//        	 int x2 = x+height/2-1;
//        	 int y2 = y-1;
//        	 r2 = new Rectangle(x2,y2,height,height);
//        	 r3 = new Rectangle(r2.x+6,r2.y+r2.height/2-2,height/2-2,height/2-2);
//        	 graphics.setBackgroundColor(ColorConstants.red);
//        	 graphics.fillOval(r3);
//        	 graphics.setForegroundColor(ColorConstants.darkBlue);
//        	 graphics.setLineWidth(3);
//        	 graphics.drawArc(r2, 180, 180);
//        	 this.setBackgroundColor(ColorConstants.menuBackground);
//         }
//         else if(arrow==2){
//        	 int x1 = x;
//        	 int y1 = y+r.height/2;
//        	 graphics.drawLine(new Point(x1,y1), new Point(x1+height,y1));
//        	 int x2 = x+height-1;
//        	 int y2 = y+height/2-1;
//        	 r2 = new Rectangle(x2,y2,height,height);
//        	 r3 = new Rectangle(r2.x+6,r2.y+r2.height/2-2,height/2-2,height/2-2);
////        	 r2Back = new Rectangle(x2+1,y2+1,height-2,height-1);
//        	 graphics.setBackgroundColor(ColorConstants.red);
//        	 graphics.fillOval(r3);
//        	 graphics.setForegroundColor(ColorConstants.darkBlue);
//        	 graphics.setLineWidth(3);
//        	 graphics.drawArc(r2, 90, 180);
//        	 this.setBackgroundColor(ColorConstants.menuBackground);
//        	 
//        	
//         }
//         else if(arrow==3){
//        	 int x1 = x+r.width/2;
//        	 int y1 = y;
//        	 graphics.drawLine(new Point(x1,y1), new Point(x1,y1+height));
//        	 int x2 = x+height/2-1;
//        	 int y2 = y+height-1;
//        	 r2 = new Rectangle(x2,y2,height,height);
//        	 r3 = new Rectangle(r2.x+6,r2.y+r2.height/2-2,height/2-2,height/2-2);
//        	 graphics.setBackgroundColor(ColorConstants.red);
//        	 graphics.fillOval(r3);
//        	 graphics.setForegroundColor(ColorConstants.darkBlue);
//        	 graphics.setLineWidth(3);
//        	 graphics.drawArc(r2, 0, 180);
//        	 this.setBackgroundColor(ColorConstants.menuBackground);
//         }
//         else if(arrow==4){
//        	 int x1 = x+r.width;
//        	 int y1 = y+r.height/2;
//        	 graphics.drawLine(new Point(x1,y1), new Point(x1-height,y1));
//        	 int x2 = x+1;
//        	 int y2 = y+height/2-1;
//        	 r2 = new Rectangle(x2,y2,height,height);
//        	 r3 = new Rectangle(r2.x+6,r2.y+r2.height/2-2,height/2-2,height/2-2);
//        	 graphics.setBackgroundColor(ColorConstants.red);
//        	 graphics.fillOval(r3);
//        	 graphics.setForegroundColor(ColorConstants.darkBlue);
//        	 graphics.setLineWidth(3);
//        	 graphics.drawArc(r2, 270, 180);
//        	 this.setBackgroundColor(ColorConstants.menuBackground);
//         }
//    	
////        Rectangle r = Rectangle.SINGLETON;
////        r.setBounds(getBounds());
////        r.width = r.width - 2;
////        r.height = r.height - 2;
////        Rectangle r2 = new Rectangle(r.x+r.width/2,r.y,r.width,r.height);
////        graphics.fillRectangle(r);
////        graphics.drawLine(new Point(r.x,r.y+r.height/2), new Point(r2.x,r.y+r.height/2));
//       
//        this.setForegroundColor(ProjectManager.getInstance().getDefaultColor());//PKY 08081101 S 프로젝트 전체의 객체 테두리 색상 설정 할  수있도록 수정
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
//  WJH 090819 S
	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
//  WJH 090819 E
}
