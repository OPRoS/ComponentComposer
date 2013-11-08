package kr.co.n3soft.n3com.figures;

import kr.co.n3soft.n3com.model.comm.FrameItem;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.draw2d.AbstractBorder;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.examples.logicdesigner.figures.LogicColorConstants;
import org.eclipse.swt.SWT;

public class UMLFrameBorder extends AbstractBorder {
    protected static Insets insets = new Insets(8, 6, 8, 6);
    protected static PointList connector = new PointList();
    protected static PointList bottomConnector = new PointList();
    FrameFigure f = null;
    String type = "";
    String fragmentName = "";
    private java.util.ArrayList condition = new java.util.ArrayList();

    //	static {
    //		connector.addPoint(-2, 0);
    //		connector.addPoint(1, 0);
    //		connector.addPoint(2, 1);
    //		connector.addPoint(2, 5);
    //		connector.addPoint(-1, 5);
    //		connector.addPoint(-1, 1);
    //
    //		bottomConnector.addPoint(-2, -1);
    //		bottomConnector.addPoint(1, -1);
    //		bottomConnector.addPoint(2, -2);
    //		bottomConnector.addPoint(2, -6);
    //		bottomConnector.addPoint(-1, -6);
    //		bottomConnector.addPoint(-1, -2);
    //	}
    //		
    //	private void drawConnectors(Graphics g, Rectangle rec) {
    //		int y1 = rec.y,
    //			width = rec.width,x1,
    //			bottom = y1 + rec.height;
    //		g.setBackgroundColor(LogicColorConstants.connectorGreen);
    //		for (int i = 0; i < 4; i++) {
    //			x1 = rec.x + (2 * i + 1) * width / 8;
    //			
    //			//Draw the "gap" for the connector
    //			g.setForegroundColor(ColorConstants.listBackground);
    //			g.drawLine(x1 - 2, y1 + 2, x1 + 3, y1 + 2);
    //			
    //			//Draw the connectors
    //			g.setForegroundColor(LogicColorConstants.connectorGreen);
    //			connector.translate(x1, y1);
    //			g.fillPolygon(connector);
    //			g.drawPolygon(connector);
    //			connector.translate(-x1, -y1);
    //			g.setForegroundColor(ColorConstants.listBackground);
    //			g.drawLine(x1 - 2, bottom - 3, x1 + 3, bottom - 3);
    //			g.setForegroundColor(LogicColorConstants.connectorGreen);
    //			bottomConnector.translate(x1, bottom);
    //			g.fillPolygon(bottomConnector);
    //			g.drawPolygon(bottomConnector);
    //			bottomConnector.translate(-x1, -bottom);
    //		}
    //	}
    public Insets getInsets(IFigure figure) {
        return insets;
    }

    public void paint(IFigure figure, Graphics graphics, Insets in) {
    	  //	this.paintBody(graphics);
    	
    	//PKY 08091002 S
    	
    	Rectangle r = figure.getBounds().getCropped(in);
//    	 Rectangle r = Rectangle.SINGLETON;
    	 
        
//        r.setBounds(getBounds());
    	if(figure instanceof FrameFigure){
    		f = (FrameFigure)figure;
    		type = f.getType();
    		fragmentName = f.getFragmentName();
    		condition = f.getCondition();
    		
    	}
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

        Rectangle rect = new Rectangle(r.x, r.y, (r.width/5)*2, 25);
//        graphics.drawRectangle(rect);
       
       
  	 PointList outline = new PointList();
       int x2 = x1;
       int y2 = y1 + 2;
       outline.addPoint(rect.x, rect.y);
       outline.addPoint(rect.x+rect.width, rect.y);
       outline.addPoint(rect.x+rect.width, rect.y+rect.height/3*2);
       outline.addPoint(rect.x+rect.width/3*2, rect.y+rect.height);
       outline.addPoint(rect.x, rect.y+rect.height);
//       outline.addPoint(rect.x, rect.y+rect.height);
       
       graphics.setBackgroundColor(ColorConstants.tooltipBackground);
       graphics.setForegroundColor(ProjectManager.getInstance().getDefaultColor());//PKY 08081101 S 프로젝트 전체의 객체 테두리 색상 설정 할  수있도록 수정
       graphics.drawPolygon(outline);
       graphics.fillPolygon(outline);
       graphics.setForegroundColor(ColorConstants.black);
       graphics.drawString(totalName, r.x+5, r.y+5);
//       graphics.drawRectangle(r.x,r.y,r.width-2,r.height-2);
       int y3 = 0;
       for(int i=0;i<this.condition.size();i++){
      	 FrameItem con = (FrameItem)condition.get(i);
      	 
      	 if(i==0){
      		y3 = r.y+30; 
      	 }
      	 else{
      		y3 = y3+15; 
      	 }
      	graphics.drawImage(con.getItemImage(), 0,0,con.getWidth(),con.getHeight(),r.x+5,y3,con.getWidth(),con.getHeight());
      	 graphics.drawString(""+con.getName()+"", r.x+23, y3+2);

       }
       Rectangle r1 = figure.getBounds().getCropped(in);
       //		r.setBounds(getBounds());
       r1.width = r1.width -5;
       r1.height = r1.height - 5;
       
       graphics.setForegroundColor(ProjectManager.getInstance().getDefaultColor());//PKY 08081101 S 프로젝트 전체의 객체 테두리 색상 설정 할  수있도록 수정
       graphics.drawRectangle(r1);
       graphics.setForegroundColor(ColorConstants.black);//PKY 08081101 S 프로젝트 전체의 객체 테두리 색상 설정 할  수있도록 수정
       graphics.setBackgroundColor(ColorConstants.buttonDarker);
       Rectangle r2 = new Rectangle(r1.x+r1.width+1,r1.y+3,r1.width+2,r1.height);
       graphics.fillRectangle(r2);
       Rectangle r3 = new Rectangle(r1.x+3,r1.y+r1.height+1,r1.width+10,r1.height);
       graphics.fillRectangle(r3);
    
       
//       for(int i=0;i<this.condition.size();i++){
//      	 String con = (String)condition.get(i);
//      	 graphics.drawString("["+con+"]", r.x+5, r.y+40*(i+1));
//      	 int y3 = r.y+40*(i+1);
//      	 graphics.setLineStyle(SWT.LINE_DASHDOT);
//      	 if(i<this.condition.size()-1)
//      	 graphics.drawLine(rect.x, y3+20,rect.x+r.width, y3+20);
//       }
      
    }
}
