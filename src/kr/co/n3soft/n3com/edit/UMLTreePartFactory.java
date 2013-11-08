package kr.co.n3soft.n3com.edit;

import kr.co.n3soft.n3com.model.comm.FinalPackageModel;
import kr.co.n3soft.n3com.model.comm.PortModel;
import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

public class UMLTreePartFactory implements EditPartFactory {
    public EditPart createEditPart(EditPart context, Object model) {
        //		if (model instanceof LED)
        //			return new LogicTreeEditPart(model);
//        if (model instanceof ClassifierModel || model instanceof ClassifierModelTextAttach || model instanceof  CompartmentModel
//        		|| model instanceof  ClassModel)
    	if(model instanceof UMLDiagramModel && !(model instanceof FinalPackageModel) )
            return new UMLContainerTreeEditPart(model);
    	else if(!(model instanceof FinalPackageModel))
        return new UMLTreeEditPart(model);
    	else 
    		return null;
    }
}
