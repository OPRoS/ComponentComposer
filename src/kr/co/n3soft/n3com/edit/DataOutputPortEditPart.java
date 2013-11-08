package kr.co.n3soft.n3com.edit;

import kr.co.n3soft.n3com.figures.DataOutputPortFigure;

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.IFigure;

public class DataOutputPortEditPart extends PortEditPart{
	protected IFigure createFigure() {
		DataOutputPortFigure figure = new DataOutputPortFigure();
        BorderLayout flowLayout = new BorderLayout();
        figure.setLayoutManager(flowLayout);
        figure.setOpaque(true);
        return figure;
    }
}
