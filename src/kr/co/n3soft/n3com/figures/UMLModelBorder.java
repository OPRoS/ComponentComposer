package kr.co.n3soft.n3com.figures;

import org.eclipse.draw2d.AbstractBorder;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
//PKY 08080501 S 모델 그림자 넣기 작업

public class UMLModelBorder extends AbstractBorder {
    protected static Insets insets = new Insets(0, 0, 0, 0);
    protected static PointList connector = new PointList();
    protected static PointList bottomConnector = new PointList();

    public Insets getInsets(IFigure figure) {
        return insets;
    }

    public void paint(IFigure figure, Graphics g, Insets in) {
    	//PKY 08072401 S 객체 테두리 넣기
        Rectangle r = figure.getBounds().getCropped(in);
        //		r.setBounds(getBounds());
        r.width = r.width -5;
        r.height = r.height - 5;
        g.drawRectangle(r);
        g.setBackgroundColor(ColorConstants.buttonDarker);
        Rectangle r2 = new Rectangle(r.x+r.width+1,r.y+3,r.width+2,r.height);
        g.fillRectangle(r2);
        Rectangle r3 = new Rectangle(r.x+3,r.y+r.height+1,r.width+10,r.height);
        g.fillRectangle(r3);
      //PKY 08072401 E 객체 테두리 넣기

//    	Rectangle r = Rectangle.SINGLETON;
//        r.setBounds(getBounds());
//        int width = r.width - 2;
//        int height = r.height - 2;
//        int frontX = r.x;
//        int frontY = r.y+(int) (height * 0.1);
//        int frontWidth = r.x+(int) (width * 0.9) ;
//        int frontHeight =r.y+ height;
//        int backOneX = r.x+(int) (width * 0.1) ;
//        int backOneY = r.y;
//        int backTwoX = r.x+width;
//        int backTwoY = r.y;
//        int backThreeX = r.x+width;
//        int backThreeY = r.y+(int) (height * 0.9);
//        int[] polygonX = {frontX, frontX, frontWidth, backThreeX, backTwoX, backOneX};
//        int[] polygonY = {frontY, frontHeight, frontHeight, backThreeY, backTwoY, backOneY};
//        int[] polygon = {frontX,frontY, frontWidth,frontHeight, backThreeX,frontHeight, backThreeX,backThreeY, backTwoX,backTwoY,backOneX, backOneY};
//       
////        graphics.fillPolygon(polygon);
//        g.fillRectangle(frontX, frontY, (int) (width * 0.9), height-(int) (height * 0.1) );
//        g.drawRectangle(frontX, frontY, (int) (width * 0.9), height-(int) (height * 0.1) );
//        g.drawLine(frontX, frontY, backOneX, backOneY);
//        g.drawLine(backOneX, backOneY, backTwoX, backTwoY);
//        g.drawLine(frontWidth, frontY, backTwoX, backTwoY);
//        g.drawLine(backTwoX, backTwoY, backThreeX, backThreeY);
//        g.drawLine(frontWidth, frontHeight, backThreeX, backThreeY);
    }
}
