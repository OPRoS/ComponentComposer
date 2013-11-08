package kr.co.n3soft.n3com.figures;

import org.eclipse.draw2d.AbstractBorder;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;


public class FlowFinalBorder extends AbstractBorder {
    protected static Insets insets = new Insets(8, 6, 8, 6);
    protected static PointList connector = new PointList();
    protected static PointList bottomConnector = new PointList();

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

    public void paint(IFigure figure, Graphics g, Insets in) {
        Rectangle r = figure.getBounds().getCropped(in);
        //		r.setBounds(getBounds());
        int width = r.width;
        int height = r.height;
        //		Rectangle r2 = r.scale(0.5, 0.5);
        //		graphics.fillOval(r2);
        //		graphics.drawOval(r2);
        
        g.drawOval(r.x + 3, r.y + 3, width - 4, height - 4);
//        Rectangle r2 = new Rectangle(r.x + 3, r.y + 3, width - 4, height - 4);
//        Point center = r2.getCenter();
//      
//        g.drawLine(center, new Point(center.x,r2.y));
       
       
    }
}
