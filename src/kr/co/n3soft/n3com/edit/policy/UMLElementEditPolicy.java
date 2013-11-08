package kr.co.n3soft.n3com.edit.policy;

import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.comm.command.UMLDeleteCommand;
import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.GroupRequest;

public class UMLElementEditPolicy extends org.eclipse.gef.editpolicies.ComponentEditPolicy {
    protected Command createDeleteCommand(GroupRequest request) {
        Object parent = getHost().getParent().getModel();
        UMLDeleteCommand deleteCmd = new UMLDeleteCommand();
        deleteCmd.setParent((UMLDiagramModel)parent);
        deleteCmd.setChild((UMLModel)getHost().getModel());
        return deleteCmd;
    }
}
