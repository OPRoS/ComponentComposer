package kr.co.n3soft.n3com.figures;

import org.eclipse.draw2d.AbstractBorder;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;

public class NodeBorder extends AbstractBorder {
    protected static Insets insets = new Insets(10, 10, 10, 10);
    protected static PointList connector = new PointList();
    protected static PointList bottomConnector = new PointList();

    public Insets getInsets(IFigure figure) {
        return insets;
    }

    public void paint(IFigure figure, Graphics g, Insets in) {
        Rectangle r = figure.getBounds().getCropped(in);
//        r.width = r.width - 2;
//        r.height = r.height - 2;
//        g.drawRectangle(r);
//    	Rectangle r = Rectangle.SINGLETON;
//        r.setBounds(getBounds());
        double value1 = 0.1;
        double value2 = 1-value1;
        int width = r.width - 2;
        int height = r.height - 2;
        int frontX = r.x;
        int frontY = r.y+(int) (height * value1);
        int frontWidth = r.x+(int) (width * value2) ;
        int frontHeight =r.y+ height;
        int backOneX = r.x+(int) (width * value1) ;
        int backOneY = r.y;
        int backTwoX = r.x+width;
        int backTwoY = r.y;
        int backThreeX = r.x+width;
        int backThreeY = r.y+(int) (height * value2);
        int[] polygonX = {frontX, frontX, frontWidth, backThreeX, backTwoX, backOneX};
        int[] polygonY = {frontY, frontHeight, frontHeight, backThreeY, backTwoY, backOneY};
        int[] polygon = {frontX,frontY, frontWidth,frontHeight, backThreeX,frontHeight, backThreeX,backThreeY, backTwoX,backTwoY,backOneX, backOneY};
       
//        graphics.fillPolygon(polygon);
//        g.fillRectangle(frontX, frontY, (int) (width * 0.9), height-(int) (height * 0.1) );
        g.drawRectangle(frontX, frontY, (int) (width * value2), height-(int) (height * value1) );
        g.drawLine(frontX, frontY, backOneX, backOneY);
        g.drawLine(backOneX, backOneY, backTwoX, backTwoY);
        g.drawLine(frontWidth, frontY, backTwoX, backTwoY);
        g.drawLine(backTwoX, backTwoY, backThreeX, backThreeY);
        g.drawLine(frontWidth, frontHeight, backThreeX, backThreeY);
    }
}
