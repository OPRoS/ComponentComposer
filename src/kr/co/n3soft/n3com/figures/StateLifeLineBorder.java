package kr.co.n3soft.n3com.figures;

import org.eclipse.draw2d.AbstractBorder;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;

public class StateLifeLineBorder extends AbstractBorder {
    protected static Insets insets = new Insets(0, 0, 0, 0);
    protected static PointList connector = new PointList();
    protected static PointList bottomConnector = new PointList();

    public Insets getInsets(IFigure figure) {
        return insets;
    }

    public void paint(IFigure figure, Graphics g, Insets in) {
//        Rectangle r = figure.getBounds().getCropped(in);
//        r.width = r.width - 2;
//        r.height = r.height - 2;
//        g.drawRectangle(r);
    	//PKY 08081101 S 모델에 그림자 넣기
    	UMLModelBorder model = new UMLModelBorder();
	model.paint(figure, g, in);
    	//PKY 08081101 E 모델에 그림자 넣기

    }
}
