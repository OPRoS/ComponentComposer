package kr.co.n3soft.n3com.dnd;

import kr.co.n3soft.n3com.model.comm.UMLModel;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.requests.SimpleFactory;

public class CopySimpleFactory extends SimpleFactory {
    UMLModel copyUMLModel;
    public Dimension difference = new Dimension();

    public CopySimpleFactory(Class aClass) {
        super(aClass);
    }

    public Object getNewObject() {
        try {
            return copyUMLModel;
        } catch (Exception exc) {
            return null;
        }
    }

    public void setUMLModel(UMLModel p) {
        copyUMLModel = p;
    }

    public UMLModel getUMLModel() {
        return copyUMLModel;
    }

    public void calculateDifference(Point source) {
        if (copyUMLModel != null)
            difference = source.getDifference(copyUMLModel.getLocation());
    }

    public Point calculateLocation(Point source) {
        System.out.println("source======>" + source);
        Point newLocation = new Point(source.x - this.difference.width, source.y - this.difference.height);
        return newLocation;
    }

    //	public void setUMLTreeModel(UMLTreeModel p){
    //		UMLTreeModel = p;
    //		
    //	}
    //	
    //	public UMLTreeModel getUMLTreeModel(){
    //		return UMLTreeModel;
    //		
    //	}
    public UMLModel createModle(UMLModel model) {
        return model;
    }
}
