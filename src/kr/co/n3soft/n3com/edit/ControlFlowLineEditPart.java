package kr.co.n3soft.n3com.edit;

import kr.co.n3soft.n3com.figures.UMLFigureFactory;
import kr.co.n3soft.n3com.model.activity.ControlFlowLineModel;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.IFigure;

//¼± µî·Ï
public class ControlFlowLineEditPart extends LineEditPart {
    protected IFigure createFigure() {
        Connection connx = UMLFigureFactory.createControlFlowLineFigure((ControlFlowLineModel)getWire());
        return connx;
    }
}
