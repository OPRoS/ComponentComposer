package kr.co.n3soft.n3com.edit;

import kr.co.n3soft.n3com.figures.MethodInputPortFigure;
import kr.co.n3soft.n3com.figures.MethodOutputPortFigure;

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.IFigure;

public class MethodOutputPortEditPart extends PortEditPart{
	protected IFigure createFigure() {
		MethodOutputPortFigure figure = new MethodOutputPortFigure();
        BorderLayout flowLayout = new BorderLayout();
        figure.setLayoutManager(flowLayout);
        figure.setOpaque(true);
        return figure;
    }
}
