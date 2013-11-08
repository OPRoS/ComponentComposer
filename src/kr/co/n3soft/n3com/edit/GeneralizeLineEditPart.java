package kr.co.n3soft.n3com.edit;

import kr.co.n3soft.n3com.figures.UMLFigureFactory;
import kr.co.n3soft.n3com.model.usecase.GeneralizeLineModel;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.IFigure;

public class GeneralizeLineEditPart extends LineEditPart {
    protected IFigure createFigure() {
        Connection connx = UMLFigureFactory.createGeneralizeLineFigure((GeneralizeLineModel)getWire());
        return connx;
    }
    //	protected AssociateLineModel getWire() {
    //		return (AssociateLineModel)getModel();
    //	}
}
