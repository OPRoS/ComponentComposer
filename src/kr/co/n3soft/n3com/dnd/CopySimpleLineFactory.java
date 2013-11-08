package kr.co.n3soft.n3com.dnd;

import kr.co.n3soft.n3com.model.comm.LineModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.requests.SimpleFactory;

public class CopySimpleLineFactory extends SimpleFactory {
    LineModel copyUMLModel;
    public Dimension difference = new Dimension();

    public CopySimpleLineFactory(Class aClass) {
        super(aClass);
    }

    public Object getNewObject() {
        try {
            return copyUMLModel;
        } catch (Exception exc) {
            return null;
        }
    }

    public void setLineModel(LineModel p) {
        copyUMLModel = p;
    }

    public LineModel getLineModel() {
        return copyUMLModel;
    }

    public UMLModel getSource() {
        UMLModel uMLSource = copyUMLModel.getSource();
        UMLModel cloneUMLSource = (UMLModel)ProjectManager.getInstance().getCloneValue(uMLSource.getID());
        return cloneUMLSource;
    }

    public UMLModel getTarget() {
        UMLModel uMLTarget = copyUMLModel.getTarget();
        UMLModel cloneUMLTarget = (UMLModel)ProjectManager.getInstance().getCloneValue(uMLTarget.getID());
        return cloneUMLTarget;
    }
    public UMLModel createModle(UMLModel model) {
        return model;
    }
}
