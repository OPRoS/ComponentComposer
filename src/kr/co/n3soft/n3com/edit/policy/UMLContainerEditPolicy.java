package kr.co.n3soft.n3com.edit.policy;

import java.util.List;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.comm.command.UMLOrphanChildCommand;
import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editpolicies.ContainerEditPolicy;
import org.eclipse.gef.examples.logicdesigner.LogicMessages;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.requests.GroupRequest;

public class UMLContainerEditPolicy extends ContainerEditPolicy {
    protected Command getCreateCommand(CreateRequest request) {
        return null;
    }

    public Command getOrphanChildrenCommand(GroupRequest request) {
        List parts = request.getEditParts();
        CompoundCommand result = new CompoundCommand(LogicMessages.LogicContainerEditPolicy_OrphanCommandLabelText);
        for (int i = 0; i < parts.size(); i++) {
            UMLOrphanChildCommand orphan = new UMLOrphanChildCommand();
            orphan.setChild((UMLModel)((EditPart)parts.get(i)).getModel());
            orphan.setParent((UMLDiagramModel)getHost().getModel());
            orphan.setLabel(LogicMessages.LogicElementEditPolicy_OrphanCommandLabelText);
            result.add(orphan);
        }
        return result.unwrap();
    }
}
