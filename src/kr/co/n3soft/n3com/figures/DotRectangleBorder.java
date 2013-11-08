package kr.co.n3soft.n3com.figures;

import org.eclipse.draw2d.AbstractBorder;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;

public class DotRectangleBorder extends AbstractBorder {
    protected static Insets insets = new Insets(0, 0, 0, 0);
    protected static PointList connector = new PointList();
    protected static PointList bottomConnector = new PointList();

    public Insets getInsets(IFigure figure) {
        return insets;
    }

    public void paint(IFigure figure, Graphics g, Insets in) {
        Rectangle r = figure.getBounds().getCropped(in);
        //		r.setBounds(getBounds());
        r.width = r.width -5;
        r.height = r.height - 5;
        g.setLineStyle(SWT.LINE_DOT);
        g.drawRectangle(r);
        g.setBackgroundColor(ColorConstants.buttonDarker);
        Rectangle r2 = new Rectangle(r.x+r.width+1,r.y+3,r.width+2,r.height);
        g.fillRectangle(r2);
        Rectangle r3 = new Rectangle(r.x+3,r.y+r.height+1,r.width+10,r.height);
        g.fillRectangle(r3);
    }
}
