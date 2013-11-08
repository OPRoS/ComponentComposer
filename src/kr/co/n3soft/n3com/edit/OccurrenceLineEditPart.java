package kr.co.n3soft.n3com.edit;

import kr.co.n3soft.n3com.figures.UMLFigureFactory;
import kr.co.n3soft.n3com.model.composite.OccurrenceLineModel;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.IFigure;

public class OccurrenceLineEditPart extends LineEditPart {
    protected IFigure createFigure() {
        Connection connx = UMLFigureFactory.createOccurrenceLineFigure((OccurrenceLineModel)getWire());
        return connx;
    }
}
