package kr.co.n3soft.n3com.edit.policy;

import kr.co.n3soft.n3com.comm.figures.UMLElementFigure;
import kr.co.n3soft.n3com.edit.UMLElementEditPart;
import kr.co.n3soft.n3com.model.comm.ElementLabelModel;
import kr.co.n3soft.n3com.model.comm.command.UMLElementLabelCommand;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;

public class UMLElementDirectEditPolicy extends DirectEditPolicy {
    /** @see DirectEditPolicy#getDirectEditCommand(DirectEditRequest) */
    protected Command getDirectEditCommand(DirectEditRequest edit) {
        String labelText = (String)edit.getCellEditor().getValue();
        UMLElementEditPart label = (UMLElementEditPart)getHost();
        UMLElementLabelCommand command = new UMLElementLabelCommand((ElementLabelModel)label.getModel(), labelText);
        return command;
    }

    /** @see DirectEditPolicy#showCurrentEditValue(DirectEditRequest) */
    protected void showCurrentEditValue(DirectEditRequest request) {
        String value = (String)request.getCellEditor().getValue();
        ((UMLElementFigure)getHostFigure()).setText(value);
        //hack to prevent async layout from placing the cell editor twice.
        getHostFigure().getUpdateManager().performUpdate();
    }
}
