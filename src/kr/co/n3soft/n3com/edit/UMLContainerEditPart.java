package kr.co.n3soft.n3com.edit;

import java.util.List;
import kr.co.n3soft.n3com.edit.policy.UMLContainerEditPolicy;
import kr.co.n3soft.n3com.edit.policy.UMLEditPolicy;
import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;
import org.eclipse.gef.AccessibleEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Tool;
import org.eclipse.gef.tools.MarqueeSelectionTool;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.swt.accessibility.AccessibleEvent;

abstract class UMLContainerEditPart extends UMLEditPart {
    protected AccessibleEditPart createAccessible() {
        return new AccessibleGraphicalEditPart() {
            public void getName(AccessibleEvent e) {
                e.result = getUMLDiagramModel().toString();
            }
        };
    }

    /** Installs the desired EditPolicies for this. */
    protected void createEditPolicies() {
        super.createEditPolicies();
//        installEditPolicy(EditPolicy.LAYOUT_ROLE, new UMLEditPolicy());
        installEditPolicy(EditPolicy.CONTAINER_ROLE, new UMLContainerEditPolicy());
    }

    /**
     * Returns the model of this as a LogicDiagram.
     * @return  LogicDiagram of this.
     */
    protected UMLDiagramModel getUMLDiagramModel() {
        return (UMLDiagramModel)getModel();
    }

    /**
     * Returns the children of this through the model.
     * @return  Children of this as a List.
     */
    protected List getModelChildren() {
        return getUMLDiagramModel().getChildren();
    }
}
