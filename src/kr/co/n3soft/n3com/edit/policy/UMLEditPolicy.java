package kr.co.n3soft.n3com.edit.policy;

import java.util.Iterator;

import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.comm.command.CreateAddElementCommand;
import kr.co.n3soft.n3com.model.comm.command.UMLAddCommand;
import kr.co.n3soft.n3com.model.comm.command.UMLCloneCommand;
import kr.co.n3soft.n3com.model.comm.command.UMLCreateCommand;
import kr.co.n3soft.n3com.model.comm.command.UMLReorderPartCommand;
import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;
import kr.co.n3soft.n3com.projectmanager.CreateElementRequest;
import kr.co.n3soft.n3com.projectmanager.N3RequestConstants;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

public class UMLEditPolicy extends org.eclipse.gef.editpolicies.FlowLayoutEditPolicy {
    /**
     * Override to return the <code>Command</code> to perform an {@link
     * RequestConstants#REQ_CLONE CLONE}. By default, <code>null</code> is returned.
     * @param request the Clone Request
     * @return A command to perform the Clone.
     */
	public Command getCommand(Request request) {
		if (N3RequestConstants.REQ_ADD_ELEMENT.equals(request.getType()))
			return createAddElementCommand((CreateElementRequest)request);
		return super.getCommand(request);

		
	}
	protected Command getCloneCommand(ChangeBoundsRequest request) {
        UMLCloneCommand clone = new UMLCloneCommand();
        clone.setParent((UMLDiagramModel)getHost().getModel());
        EditPart after = getInsertionReference(request);
        int index = getHost().getChildren().indexOf(after);
        Iterator i = request.getEditParts().iterator();
        GraphicalEditPart currPart = null;
        while (i.hasNext()) {
            currPart = (GraphicalEditPart)i.next();
            clone.addPart((UMLModel)currPart.getModel(), index++);
        }
        return clone;
    }

    protected Command createAddCommand(EditPart child, EditPart after) {
        UMLAddCommand command = new UMLAddCommand();
        command.setChild((UMLModel)child.getModel());
        command.setParent((UMLDiagramModel)getHost().getModel());
        int index = getHost().getChildren().indexOf(after);
        command.setIndex(index);
        return command;
    }

    /** @see org.eclipse.gef.editpolicies.LayoutEditPolicy#createChildEditPolicy(org.eclipse.gef.EditPart) */
    protected EditPolicy createChildEditPolicy(EditPart child) {
        UMLResizableEditPolicy policy = new UMLResizableEditPolicy();
        policy.setResizeDirections(0);
        return policy;
    }

    protected Command createMoveChildCommand(EditPart child, EditPart after) {
        UMLModel childModel = (UMLModel)child.getModel();
        UMLDiagramModel parentModel = (UMLDiagramModel)getHost().getModel();
        int oldIndex = getHost().getChildren().indexOf(child);
        int newIndex = getHost().getChildren().indexOf(after);
        if (newIndex > oldIndex)
            newIndex--;
        UMLReorderPartCommand command = new UMLReorderPartCommand(childModel, parentModel, newIndex);
        return command;
    }

    protected Command getCreateCommand(CreateRequest request) {
        UMLCreateCommand command = new UMLCreateCommand();
        EditPart after = getInsertionReference(request);
        command.setChild((UMLModel)request.getNewObject());
        command.setParent((UMLDiagramModel)getHost().getModel());
        int index = getHost().getChildren().indexOf(after);
        command.setIndex(index);
        return command;
    }
    
    protected Command createAddElementCommand(CreateElementRequest request) {
    	CreateAddElementCommand command = new CreateAddElementCommand();
//        EditPart after = getInsertionReference(request);
//        command.setChild((UMLModel)request.getNewObject());
//        command.setParent((UMLDiagramModel)getHost().getModel());
//        int index = getHost().getChildren().indexOf(after);
//        command.setIndex(index);
        return command;
    }
}
