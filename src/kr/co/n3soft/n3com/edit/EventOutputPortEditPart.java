package kr.co.n3soft.n3com.edit;

import kr.co.n3soft.n3com.figures.EventOutputPortFigure;

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.IFigure;

public class EventOutputPortEditPart extends PortEditPart{
	protected IFigure createFigure() {
		EventOutputPortFigure figure = new EventOutputPortFigure();
        BorderLayout flowLayout = new BorderLayout();
        figure.setLayoutManager(flowLayout);
        figure.setOpaque(true);
        return figure;
    }

}