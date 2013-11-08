package kr.co.n3soft.n3com.edit;

import kr.co.n3soft.n3com.figures.UMLFigureFactory;
import kr.co.n3soft.n3com.model.umlclass.DependencyLineModel;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.IFigure;

public class DependencyLineEditPart extends LineEditPart {
    protected IFigure createFigure() {
        Connection connx = UMLFigureFactory.createDependencyLineFigure((DependencyLineModel)getWire());
        return connx;
    }
}
