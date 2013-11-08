package kr.co.n3soft.n3com.figures;

import org.eclipse.draw2d.AbstractBorder;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;

public class SendBorder extends AbstractBorder {
    protected static Insets insets = new Insets(8, 6, 8, 6);
    protected static PointList connector = new PointList();
    protected static PointList bottomConnector = new PointList();

    public Insets getInsets(IFigure figure) {
        return insets;
    }

    public void paint(IFigure figure, Graphics g, Insets in) {
        double REC_PERCENT = 0.9;
        //		 in.right=5;
        //		 in.top=5;
        //		 in.bottom = 5;
        //		 in.left = 5;
        Rectangle r = figure.getBounds().getCropped(in);
        int width = r.width - 3;
        int height = r.height - 3;
        int x = r.x;
        int y = r.y;
        PointList pt = new PointList();
        pt.addPoint(x, y);
        pt.addPoint((int)(width * REC_PERCENT) + x, y);
        pt.addPoint(width + x, (int)(height / 2) + y);
        pt.addPoint((int)(width * REC_PERCENT) + x, height + y);
        pt.addPoint(x, height + y);
        //		pt.addPoint(x, y);
        //		pt.addPoint(width,y);
        //		pt.addPoint(width, (int) (height/2));
        //		pt.addPoint((int) (width * REC_PERCENT),height);
        //		g.drawRectangle(r);
        //
        g.drawPolygon(pt);
    }
}
