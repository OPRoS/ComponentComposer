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
import org.eclipse.gef.handles.HandleBounds;
import org.eclipse.swt.SWT;

public class ArrowFigure extends UMLFigure implements HandleBounds {
    private IFigure pane;
    private TextFlow textFlow;
    
    /*
     * angle = 0 오른쪽
     * angle = 1 아래
     * angle = 2 왼쪽
     * angle = 3 위
     */
    private String angle = "0";
    
    private boolean back = true;
    //0 defalult
    //1 
  //PKY 08070301 S Communication Dialog 추가작업
    private int signature = 0;
    private int synch = 0;
    private int kind = 0;
   //PKY 08070301 E Communication Dialog 추가작업
//    private boolean isArrow = false; 


    public ArrowFigure() {
        //	setBorder(new UseCaseBorder());
        createConnectionAnchors();
        setBackgroundColor(ColorConstants.tooltipBackground);
        this.setForegroundColor(ColorConstants.black);
        setOpaque(true);
    }
    public void setAngle(String p){
    	this.angle = p;
    }

    public IFigure getContentsPane() {
        return pane;
    }

    public Dimension getPreferredSize(int w, int h) {
        Dimension prefSize = super.getPreferredSize(w, h);
        Dimension defaultSize = new Dimension(25, 20);
        prefSize.union(defaultSize);
        return prefSize;
    }

    /** @see org.eclipse.draw2d.Figure#paintFigure(Graphics) */
    protected void paintFigure(Graphics graphics) {
    	
    	Rectangle r = Rectangle.SINGLETON;
    	r.setBounds(getBounds());
    	int width = r.width/2;
    	int height = 5;
    	int x = r.x;
    	int y = r.y+height;
    	graphics.setBackgroundColor(ColorConstants.black);
    	if(!this.back){
    		if(this.angle.equals("0")){

//  			graphics.drawLine(x, y, x + height, y);
    			if(synch==1){
    				int[] points ={x + height,y+height,x + height*2, y,x + height,y-height};
    				graphics.drawPolyline(points);
//  				graphics.setLineStyle(SWT.LINE_DOT);
    				graphics.drawLine(x, y, x + height, y);

    			}else if(signature==1){
    				int[] points ={x + height,y+height,x + height*2, y,x + height,y-height};
    				graphics.drawPolyline(points);
    				graphics.setLineStyle(SWT.LINE_DOT);
    				graphics.drawLine(x, y, x + height, y);

    			}
    			else{
    				int[] points ={x + height,y-height,x + height,y+height,x + height*2, y};
    				graphics.drawPolyline(points);
    				graphics.drawLine(x, y, x + height, y);
    				graphics.fillPolygon(points);
    			}


    		}
    		else if(this.angle.equals("1")){//아래
    			x = r.x+height;
    			y = r.y;
    			if(synch==1){
    				int[] points ={x-height,y+height,x, y+height*2,x + height,y+height};
    				graphics.drawPolyline(points);
//  				graphics.setLineStyle(SWT.LINE_DOT);
    				graphics.drawLine(x, y, x, y+height);

    			}else if(signature==1){
    				int[] points ={x-height,y+height,x, y+height*2,x + height,y+height};
    				graphics.drawPolyline(points);
    				graphics.setLineStyle(SWT.LINE_DOT);
    				graphics.drawLine(x, y, x, y+height);

    			}else{
    				graphics.drawLine(x, y, x, y+height);
    				int[] points ={x-height,y+height,x + height,y+height,x, y+height*2};
    				graphics.drawPolygon(points);
    				graphics.fillPolygon(points);
    			}

    		}
    		else if(this.angle.equals("2")){
    			x = r.x+r.width;
    			y = r.y+height;
    			if(synch==1){
    				int[] points ={x-height, y+height,x-height*2,y,x-height, y-height};
    				graphics.drawPolyline(points);
//  				graphics.setLineStyle(SWT.LINE_DOT);
    				graphics.drawLine(x, y, x-height, y);

    			}else if(signature==1){
    				int[] points ={x-height, y+height,x-height*2,y,x-height, y-height};
    				graphics.drawPolyline(points);
    				graphics.setLineStyle(SWT.LINE_DOT);
    				graphics.drawLine(x, y, x-height, y);

    			}else{
    				graphics.drawLine(x, y, x-height, y);
    				int[] points ={x-height,y+height,x - height,y-height,x-height*2, y};
    				graphics.drawPolygon(points);
    				graphics.fillPolygon(points);
    			}
    		}
    		else if(this.angle.equals("3")){
    			x = r.x+height;
    			y = r.y+r.height-height;
    			if(synch==1){
    				int[] points ={x - height,y-height,x, y-height*2,x + height,y-height};
    				graphics.drawPolyline(points);
//  				graphics.setLineStyle(SWT.LINE_DOT);
    				graphics.drawLine(x, y, x, y-height);

    			}else if(signature==1){
    				int[] points ={x - height,y-height,x, y-height*2,x + height,y-height};
    				graphics.drawPolyline(points);
    				graphics.setLineStyle(SWT.LINE_DOT);
    				graphics.drawLine(x, y, x, y-height);

    			}else{
    				graphics.drawLine(x, y, x, y-height);
    				int[] points ={x + height,y-height,x - height,y-height,x, y-height*2};
    				graphics.drawPolygon(points);
    				graphics.fillPolygon(points);
    			}

    		}
    	}
    	else{
    		//PKY 08070701 S 커뮤니케이션 모양 변경
    		if(this.angle.equals("0")){
    			x = r.x+r.width;
    			y = r.y+height;
    			if(synch==1){
    				int[] points ={x-height-13, y+height,x-height*2-13,y,x-height-13, y-height};
    				graphics.drawPolyline(points);
//  				graphics.setLineStyle(SWT.LINE_DOT);
    				graphics.drawLine(x-13, y, x-height-13, y);

    			}else if(signature==1){
    				int[] points ={x-height-13, y+height,x-height*2-13,y,x-height-13, y-height};
    				graphics.drawPolyline(points);
    				graphics.setLineStyle(SWT.LINE_DOT);
    				graphics.drawLine(x-13, y, x-height-13, y);

    			}else{
    				graphics.drawLine(x-13, y, x-height-13, y);
    				int[] points ={x-height-13,y+height,x - height-13,y-height,x-height*2-13, y};
    				graphics.drawPolygon(points);
    				graphics.fillPolygon(points);
    			}
    		}
    		else if(this.angle.equals("1")){
    			x = r.x+height;
    			y = r.y+r.height-height;
    			if(synch==1){
    				int[] points ={x - height,y-height,x, y-height*2,x + height,y-height};
    				graphics.drawPolyline(points);
//  				graphics.setLineStyle(SWT.LINE_DOT);
    				graphics.drawLine(x, y, x, y-height);

    			}else if(signature==1){
    				int[] points ={x - height,y-height,x, y-height*2,x + height,y-height};
    				graphics.drawPolyline(points);
    				graphics.setLineStyle(SWT.LINE_DOT);
    				graphics.drawLine(x, y, x, y-height);

    			}else{
    				graphics.drawLine(x, y, x, y-height);
    				int[] points ={x + height,y-height,x - height,y-height,x, y-height*2};
    				graphics.drawPolygon(points);
    				graphics.fillPolygon(points);
    			}

    		}
    		else if(this.angle.equals("2")){

//  			graphics.drawLine(x, y, x + height, y);
    			if(synch==1){
    				int[] points ={x + height+13,y+height,x + height*2+13, y,x + height+13,y-height};
    				graphics.drawPolyline(points);
//  				graphics.setLineStyle(SWT.LINE_DOT);
    				graphics.drawLine(x+13, y, x + height+13, y);

    			}else if(signature==1){
    				int[] points ={x + height+13,y+height,x + height*2+13, y,x + height+13,y-height};
    				graphics.drawPolyline(points);
    				graphics.setLineStyle(SWT.LINE_DOT);
    				graphics.drawLine(x+13, y, x + height+13, y);

    			}
    			else{
    				int[] points ={x + height+13,y-height,x + height+13,y+height,x + height*2+13, y};
    				graphics.drawPolyline(points);
    				graphics.drawLine(x+13, y, x + height+13, y);
    				graphics.fillPolygon(points);
    			}


    		}
    		else if(this.angle.equals("3")){//아래
    			x = r.x+height;
    			y = r.y;
    			if(synch==1){
    				int[] points ={x-height,y+height,x, y+height*2,x + height,y+height};
    				graphics.drawPolyline(points);
//  				graphics.setLineStyle(SWT.LINE_DOT);
    				graphics.drawLine(x, y, x, y+height);

    			}else if(signature==1){
    				int[] points ={x-height,y+height,x, y+height*2,x + height,y+height};
    				graphics.drawPolyline(points);
    				graphics.setLineStyle(SWT.LINE_DOT);
    				graphics.drawLine(x, y, x, y+height);

    			}else{
    				graphics.drawLine(x, y, x, y+height);
    				int[] points ={x-height,y+height,x + height,y+height,x, y+height*2};
    				graphics.drawPolygon(points);
    				graphics.fillPolygon(points);
    			}

    		}
    	}
    	//PKY 08070701 E 커뮤니케이션 모양 변경

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
	public boolean isBack() {
		return back;
	}
	public void setBack(boolean back) {
		this.back = back;
	}
	  //PKY 08070301 S Communication Dialog 추가작업
	public void setSignature(int signature) {
		this.signature = signature;
	}
	public void setSynch(int synch) {
		this.synch = synch;
	}
	public void setKind(int kind) {
		this.kind = kind;
	}
	  //PKY 08070301 E Communication Dialog 추가작업

}
