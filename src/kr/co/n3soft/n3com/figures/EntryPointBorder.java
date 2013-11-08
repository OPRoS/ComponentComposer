package kr.co.n3soft.n3com.figures;

import org.eclipse.draw2d.AbstractBorder;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;

public class EntryPointBorder extends AbstractBorder {
    protected static Insets insets = new Insets(8, 6, 8, 6);
    protected static PointList connector = new PointList();
    protected static PointList bottomConnector = new PointList();

    public Insets getInsets(IFigure figure) {
        return insets;
    }

    public void paint(IFigure figure, Graphics g, Insets in) {
        Rectangle r = figure.getBounds().getCropped(in);
        //		r.setBounds(getBounds());
        r.width = r.width - 2;
        r.height = r.height - 2;
        g.drawOval(r);
        //		g.setForegroundColor(LogicColorConstants.logicGreen);
        //		g.setBackgroundColor(LogicColorConstants.logicGreen);
        //		
        //		//Draw the sides of the border
        //		g.fillRectangle(r.x, r.y + 2, r.width, 6);
        //		g.fillRectangle(r.x, r.bottom() - 8, r.width, 6);
        //		g.fillRectangle(r.x, r.y + 2, 6, r.height - 4);
        //		g.fillRectangle(r.right() - 6, r.y + 2, 6, r.height - 4);
        //
        //		//Outline the border
        //		g.setForegroundColor(LogicColorConstants.connectorGreen);
        //		g.drawLine(r.x, r.y + 2, r.right() - 1, r.y + 2);
        //		g.drawLine(r.x, r.bottom() - 3, r.right() - 1, r.bottom() - 3);
        //		g.drawLine(r.x, r.y + 2, r.x, r.bottom() - 3);
        //		g.drawLine(r.right() - 1, r.bottom() - 3, r.right() - 1, r.y + 2);
        //		
        //		r.crop(new Insets(1, 1, 0, 0));
        //		r.expand(1, 1);
        //		r.crop(getInsets(figure));
        //		drawConnectors(g, figure.getBounds().getCropped(in));
    }
}
