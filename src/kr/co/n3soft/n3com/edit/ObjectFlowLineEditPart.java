package kr.co.n3soft.n3com.edit;

import kr.co.n3soft.n3com.figures.UMLFigureFactory;
import kr.co.n3soft.n3com.model.activity.ObjectFlowLineModel;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.IFigure;
//PKY 08072401 S ObjectFlow¸ðµ¨ Ãß°¡

public class ObjectFlowLineEditPart extends ControlFlowLineEditPart {
	protected IFigure createFigure() {
        Connection connx = UMLFigureFactory.createControlFlowLineFigure((ObjectFlowLineModel)getWire());
        return connx;
    }

}
