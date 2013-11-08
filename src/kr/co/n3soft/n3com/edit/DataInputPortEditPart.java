package kr.co.n3soft.n3com.edit;

import kr.co.n3soft.n3com.figures.DataInputPortFigure;

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.IFigure;

public class DataInputPortEditPart extends PortEditPart{
	protected IFigure createFigure() {
		DataInputPortFigure figure = new DataInputPortFigure();
        BorderLayout flowLayout = new BorderLayout();
        figure.setLayoutManager(flowLayout);
        figure.setOpaque(true);
        return figure;
    }

}
