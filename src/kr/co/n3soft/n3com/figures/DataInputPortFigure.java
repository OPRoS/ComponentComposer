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
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.gef.handles.HandleBounds;
import org.eclipse.swt.graphics.Image;

public class DataInputPortFigure extends UMLFigure implements HandleBounds {
    private IFigure pane;
    private TextFlow textFlow;
    private Image image; // WJH 090801
    private Image image2;	// WJH 090819

    public DataInputPortFigure() {
        createConnectionAnchors();
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
//    	    	WJH 090801 S
    	   	 Rectangle r = Rectangle.SINGLETON;
    	   	 r.setBounds(getBounds());
//    	        r.width = r.width - 2;
//    	        r.height = r.height - 2;    	   	 
//    	      WJH 090818 S
//    	   	 WJH 090819 S
    	        if(image == null ){
    	        	image = ProjectManager.getInstance().getImageReSize(10002, r.width, r.height);
    	        	image2 = ProjectManager.getInstance().getImageReSize(10011, 32, 32);
    	        }
//      	  WJH 090818 E
    	        if(image != null){
    	        	if(r.width==16 && r.height == 16){    	        		
    	        		graphics.drawImage(image, r.x, r.y);
    	        	}
    	        	else if(r.width ==32 && r.height == 32){    	        		
    	        		graphics.drawImage(image2, r.x, r.y);
    	        	}
    	        }
//       	   	 WJH 090819 E
//    	        WJH 090801 E
//        	 Rectangle r = Rectangle.SINGLETON;
//             r.setBounds(getBounds());
//             r.width = r.width - 2;
//             r.height = r.height - 2;
//             int x = r.x;
//             int y = r.y;
//             int height = 20;
//             int width = 20;
//             Rectangle r2 = null;
//             Rectangle r2Back = null;
//           
//             if(arrow==1){
//            	  height = 20;
//                  width = 20;
//            	 double REC_PERCENT = 0.7;
//                 x = r.x;
//                 y = r.y;
//            	 int x1 = x+r.width/2-10;
//            	 int y1 = y+r.height;
//                 
//                PointList pt = new PointList();
//                pt.addPoint(x1, y1);
////                graphics.drawOval(x1, y1, 1, 1);
//                pt.addPoint(x1, y1- height);
//                pt.addPoint((int)(width / 2) + x1, y1-(int)(height * REC_PERCENT) );
//                pt.addPoint(width + x1, y1-height);
//                pt.addPoint(width + x1, y1);
//                Rectangle circle = new Rectangle(x1+5,y1-30,10,10);
//                
////                this.setBackgroundColor(ColorConstants.orange);
//                graphics.setBackgroundColor(ColorConstants.orange);
//                graphics.fillPolygon(pt);
//                    graphics.drawPolyline(pt);
//                    graphics.setBackgroundColor(ColorConstants.red);
//                    graphics.fillOval(circle);
////                    graphics.drawOval(r) 
//                this.setForegroundColor(ProjectManager.getInstance().getDefaultColor());//PKY 08081101 S 프로젝트 전체의 객체 테두리 색상 설정 할  수있도록 수정
//             }
//             else if(arrow==2){
//            	 height = 20;
//                 width = 20;
//            	 double REC_PERCENT = 0.7;
//                  x = r.x;
//                  y = r.y;
//             	 int x1 = x;
//             	 int y1 = y+r.height/2-10;
//                  
//                 PointList pt = new PointList();
//                 pt.addPoint(x1, y1);
//                 pt.addPoint(x1+width, y1);
//                 pt.addPoint((int)(width * REC_PERCENT)+x1, y1+height/2);
//                 pt.addPoint(x1+width, y1+height);
//                 pt.addPoint(x1, height + y1);
//                 Rectangle circle = new Rectangle(x1+width+5,y1+5,10,10);
//                 graphics.setBackgroundColor(ColorConstants.orange);
//                 graphics.fillPolygon(pt);
//                     graphics.drawPolyline(pt);
//                     graphics.setBackgroundColor(ColorConstants.red);
//                     graphics.fillOval(circle);
//                 this.setForegroundColor(ProjectManager.getInstance().getDefaultColor());//PKY 08081101 S 프로젝트 전체의 객체 테두리 색상 설정 할  수있도록 수정
//             }
//             else if(arrow==3){
////            	 int x1 = x+r.width/2;
////            	 int y1 = y;
////            	 height = 38;
////                 width = 20;
//           	 double REC_PERCENT = 0.7;
//                x = r.x;
//                y = r.y;
//           	 int x1 = x+r.width/2-10;
//           	 int y1 = y;
//                
//             PointList pt = new PointList();
//             pt.addPoint(x1, y1);
//             pt.addPoint(x1, y1+height);
//             pt.addPoint((int)(width/2)+x1, y1+(int)(height * REC_PERCENT));
//             pt.addPoint(x1+width, y1+height);
//             pt.addPoint(x1+width, height);
//             Rectangle circle = new Rectangle(x1+5,y1+20,10,10);
//             graphics.setBackgroundColor(ColorConstants.orange);
//             graphics.fillPolygon(pt);
//                 graphics.drawPolyline(pt);
//                 graphics.setBackgroundColor(ColorConstants.red);
//                 graphics.fillOval(circle);
//             this.setForegroundColor(ProjectManager.getInstance().getDefaultColor());//PKY 08081101 S 프로젝트 전체의 객체 테두리 색상 설정 할  수있도록 수정
//             }
//             else if(arrow==4){
////            	 int x1 = x+r.width;
////            	 int y1 = y+r.height/2;
//            	 double REC_PERCENT = 0.7;
//                 x = r.x;
//                 y = r.y;
//            	 int x1 = x+r.width;
//            	 int y1  = y+r.height/2-10;
//                 
//            	 PointList pt = new PointList();
//                 pt.addPoint(x1, y1);
////                 graphics.drawOval(x1, y1, 1, 1);
//                 pt.addPoint(x1-width, y1);
//                 pt.addPoint(x1-(int)(width * REC_PERCENT), y1+height/2);
//                 pt.addPoint(x1-width, y1+height);
//                 pt.addPoint(x1, y1+height);
//                 Rectangle circle = new Rectangle(x1-width-10,y1+5,10,10);
//                 graphics.setBackgroundColor(ColorConstants.orange);
//                 graphics.fillPolygon(pt);
//                     graphics.drawPolyline(pt);
//                     graphics.setBackgroundColor(ColorConstants.red);
//                     graphics.fillOval(circle);
//                 this.setForegroundColor(ProjectManager.getInstance().getDefaultColor());//PKY 08081101 S 프로젝트 전체의 객체 테두리 색상 설정 할  수있도록 수정
//             }
//             r2 = new Rectangle(r.x+r.width/2,r.y+1,r.width/2,r.height);
          
//             graphics.drawRectangle(r);
//             graphics.fillRectangle(r);
//             graphics.drawLine(new Point(r.x,r.y+r.height/2), new Point(r2.x,r.y+r.height/2));
//             graphics.drawArc(r2, 90, 1000);
//             graphics.drawOval(r2);
//             graphics.fillOval(r2);
//            this.setForegroundColor(ProjectManager.getInstance().getDefaultColor());//PKY 08081101 S 프로젝트 전체의 객체 테두리 색상 설정 할  수있도록 수정
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
