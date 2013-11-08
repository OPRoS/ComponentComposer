package kr.co.n3soft.n3com.figures;

import kr.co.n3soft.n3com.comm.figures.UMLFigure;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.gef.examples.logicdesigner.figures.LogicColorConstants;
import org.eclipse.gef.handles.HandleBounds;
import org.eclipse.swt.SWT;

public class SelfMessageFigure extends UMLFigure implements HandleBounds {
    private IFigure pane;
    private TextFlow textFlow;
    private String type = "alt";
    private String fragmentName="";
    private java.util.ArrayList condition = new java.util.ArrayList();
    private int cornerSize = 5;
//    private boolean isArrow = false; 


    public SelfMessageFigure() {
//    	 setBorder(new LineBorder(ColorConstants.black, 1));
        //	setBorder(new UseCaseBorder());
//        createConnectionAnchors();
//        setBackgroundColor(ColorConstants.tooltipBackground);
//        this.setForegroundColor(ColorConstants.black);
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

    	  Rectangle r = Rectangle.SINGLETON;
          r.setBounds(getBounds());
          Rectangle rect = new Rectangle(r.x+5, r.y+18, r.width-10, r.height-30);//PKY 08050701 S Timing Line저장
          graphics.drawLine(rect.x, rect.y, rect.x+rect.width,rect.y);//PKY 08050701 S Timing Line저장
          graphics.drawLine(rect.x+rect.width,rect.y+0, rect.x+rect.width,rect.y+rect.height);//PKY 08050701 S Timing Line저장
          graphics.drawLine(rect.x+rect.width,rect.y+rect.height, rect.x+15,rect.y+rect.height);//PKY 08050701 S Timing 
          Rectangle rect2 = new Rectangle(rect.x, rect.y+rect.height-15, 10, 20);
          Rectangle rect3 = new Rectangle(rect.x+1, rect.y+rect.height-14, 9, 19);
          graphics.setBackgroundColor(ColorConstants.lightBlue);
      	graphics.setForegroundColor(ColorConstants.black);
          graphics.drawRectangle(rect2);
          graphics.fillRectangle(rect3);
          PointList outline = new PointList();
          outline.addPoint(rect.x+15, rect.y+rect.height-10);
          outline.addPoint(rect.x+15, rect.y+rect.height+10);
          outline.addPoint(rect.x+5, rect.y+rect.height);
          graphics.drawPolygon(outline);
          graphics.setBackgroundColor(ColorConstants.white);
          PointList outline2 = new PointList();
          outline2.addPoint(rect.x+14, rect.y+rect.height-9);
          outline2.addPoint(rect.x+14, rect.y+rect.height+9);
          outline2.addPoint(rect.x+6, rect.y+rect.height);
          graphics.fillPolygon(outline2);
        
          
//          PointList outline = new PointList();
//          outline.addPoint(rect.x+5, rect.y);
//          outline.addPoint(rect.x+5+r.width-10, rect.y);
//          graphics.drawRectangle(rect);
          
          
          
          /*
          if(type==null){
        	  type="";
          }
          if(fragmentName==null){
        	  fragmentName="";
          }
       String totalName = type+" "+fragmentName;
      
      
          int y = r.y + r.height;
          int x = r.x ;
          int x1 = r.x + r.width - 15;
          int y1 = r.y;
          int w =   totalName.length()*10;

          Rectangle rect = new Rectangle(r.x, r.y, r.width/3, 20);

         
         
    	 PointList outline = new PointList();
         int x2 = x1;
         int y2 = y1 + 2;
         outline.addPoint(rect.x, rect.y);
         outline.addPoint(rect.x+rect.width, rect.y);
         outline.addPoint(rect.x+rect.width, rect.y+rect.height/3*2);
         outline.addPoint(rect.x+rect.width/3*2, rect.y+rect.height);
         outline.addPoint(rect.x, rect.y+rect.height);
//         outline.addPoint(rect.x, rect.y+rect.height);
         
         graphics.setBackgroundColor(LogicColorConstants.defaultFillColor);
     	graphics.setForegroundColor(ColorConstants.buttonDarker);
         graphics.drawPolygon(outline);
         graphics.fillPolygon(outline);
         graphics.setForegroundColor(ColorConstants.black);
         graphics.drawString(totalName, r.x+5, r.y+5);
      
         
         for(int i=0;i<this.condition.size();i++){
        	 String con = (String)condition.get(i);
        	 graphics.drawString("["+con+"]", r.x+5, r.y+40*(i+1));
        	 int y3 = r.y+40*(i+1);
        	 graphics.setLineStyle(SWT.LINE_DASHDOT);
        	 if(i<this.condition.size()-1)
        	 graphics.drawLine(rect.x, y3+20,rect.x+r.width, y3+20);
         }
         */
         
          
    }
    
    public void setType(String p){
    	this.type = p;
    }
    public String getType(){
    	return this.type;
    }
    
    public void setFragmentName(String p){
    	this.fragmentName = p;
    }
    public String getFragmentName(){
    	return this.fragmentName;
    }
    
    public void setFragmentCondition(java.util.ArrayList p){
    	condition = p;
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
