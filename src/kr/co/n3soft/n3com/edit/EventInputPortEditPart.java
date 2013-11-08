package kr.co.n3soft.n3com.edit;

import kr.co.n3soft.n3com.figures.EventInputPortFigure;

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.IFigure;

public class EventInputPortEditPart extends PortEditPart{
	protected IFigure createFigure() {
		EventInputPortFigure figure = new EventInputPortFigure();
        BorderLayout flowLayout = new BorderLayout();
        figure.setLayoutManager(flowLayout);
        figure.setOpaque(true);
        return figure;
    }

}
