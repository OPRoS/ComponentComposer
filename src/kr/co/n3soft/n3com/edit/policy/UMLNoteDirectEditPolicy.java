package kr.co.n3soft.n3com.edit.policy;

import kr.co.n3soft.n3com.edit.UMLNoteEditPart;
import kr.co.n3soft.n3com.figures.UMLNoteFigure;
import kr.co.n3soft.n3com.model.comm.UMLNoteModel;
import kr.co.n3soft.n3com.model.comm.command.UMLNoteCommand;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;

public class UMLNoteDirectEditPolicy extends DirectEditPolicy {
    /** @see DirectEditPolicy#getDirectEditCommand(DirectEditRequest) */
    protected Command getDirectEditCommand(DirectEditRequest edit) {
        String labelText = (String)edit.getCellEditor().getValue();
        UMLNoteEditPart label = (UMLNoteEditPart)getHost();
        UMLNoteCommand command = new UMLNoteCommand((UMLNoteModel)label.getModel(), labelText);
        return command;
    }

    /** @see DirectEditPolicy#showCurrentEditValue(DirectEditRequest) */
    protected void showCurrentEditValue(DirectEditRequest request) {
        String value = (String)request.getCellEditor().getValue();
        ((UMLNoteFigure)getHostFigure()).setText(value);
        //hack to prevent async layout from placing the cell editor twice.
        getHostFigure().getUpdateManager().performUpdate();
    }
}
