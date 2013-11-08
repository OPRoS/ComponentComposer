package kr.co.n3soft.n3com.edit;

import kr.co.n3soft.n3com.figures.UMLFigureFactory;
import kr.co.n3soft.n3com.model.composite.RoleBindingLineModel;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.IFigure;

public class RoleBindingLineEditPart extends LineEditPart {
    protected IFigure createFigure() {
        Connection connx = UMLFigureFactory.createRoleBindingLineFigure((RoleBindingLineModel)getWire());
        return connx;
    }
}
