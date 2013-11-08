package kr.co.n3soft.n3com.edit.policy;

import kr.co.n3soft.n3com.model.comm.LineModel;
import kr.co.n3soft.n3com.model.comm.command.UMLConnectionCommand;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.GroupRequest;

public class LineEditPolicy extends org.eclipse.gef.editpolicies.ConnectionEditPolicy {
    protected Command getDeleteCommand(GroupRequest request) {
        UMLConnectionCommand c = new UMLConnectionCommand();
        c.setWire((LineModel)getHost().getModel());
        return c;
    }
}
