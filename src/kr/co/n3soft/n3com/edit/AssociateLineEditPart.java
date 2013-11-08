package kr.co.n3soft.n3com.edit;

import kr.co.n3soft.n3com.figures.UMLFigureFactory;
import kr.co.n3soft.n3com.model.comm.SeqChopboxAnchor;
import kr.co.n3soft.n3com.model.usecase.AssociateLineModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPart;

//import org.eclipse.gef.editparts.AbstractEditPart.EditPolicyIterator;
public class AssociateLineEditPart extends LineEditPart {
    protected IFigure createFigure() {
        Connection connx = UMLFigureFactory.createAssociateLineFigure((AssociateLineModel)getWire());
        return connx;
    }
}
