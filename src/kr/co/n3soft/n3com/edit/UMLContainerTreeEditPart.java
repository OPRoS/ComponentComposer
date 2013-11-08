package kr.co.n3soft.n3com.edit;

import java.util.List;
import kr.co.n3soft.n3com.edit.policy.UMLContainerEditPolicy;
import kr.co.n3soft.n3com.edit.policy.UMLTreeContainerEditPolicy;
import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;

public class UMLContainerTreeEditPart extends UMLTreeEditPart {
    /** Constructor, which initializes this using the model given as input. */
    public UMLContainerTreeEditPart(Object model) {
        super(model);
    }

    /** Creates and installs pertinent EditPolicies. */
    protected void createEditPolicies() {
        super.createEditPolicies();
        installEditPolicy(EditPolicy.CONTAINER_ROLE, new UMLContainerEditPolicy());
        installEditPolicy(EditPolicy.TREE_CONTAINER_ROLE, new UMLTreeContainerEditPolicy());
        //If this editpart is the contents of the viewer, then it is not deletable!
        if (getParent() instanceof RootEditPart)
            installEditPolicy(EditPolicy.COMPONENT_ROLE, new RootComponentEditPolicy());
    }

    /**
     * Returns the model of this as a LogicDiagram.
     * @return  Model of this.
     */
    protected UMLDiagramModel getUMLDiagramModel() {
        return (UMLDiagramModel)getModel();
    }

    /**
     * Returns the children of this from the model, as this is capable enough of holding EditParts.
     * @return  List of children.
     */
    protected List getModelChildren() {
        return getUMLDiagramModel().getChildren();
    }
}
