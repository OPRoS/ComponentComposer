package kr.co.n3soft.n3com.edit;

import kr.co.n3soft.n3com.figures.UMLFigureFactory;
import kr.co.n3soft.n3com.model.umlclass.MergeLineModel;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.IFigure;

public class MergeLineEditPart  extends LineEditPart {
    protected IFigure createFigure() {
        Connection connx = UMLFigureFactory.createMergeLineFigure((MergeLineModel)getWire());
        System.out.println("dddddd");
        return connx;
    }
}
