package kr.co.n3soft.n3com.edit;

import kr.co.n3soft.n3com.figures.MethodInputPortFigure;

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.IFigure;

public class MethodInputPortEditPart extends PortEditPart{
	protected IFigure createFigure() {
		MethodInputPortFigure figure = new MethodInputPortFigure();
        BorderLayout flowLayout = new BorderLayout();
        figure.setLayoutManager(flowLayout);
        figure.setOpaque(true);
        return figure;
    }

}
