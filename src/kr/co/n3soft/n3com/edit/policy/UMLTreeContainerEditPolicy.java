package kr.co.n3soft.n3com.edit.policy;

import java.util.List;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.comm.command.UMLCreateCommand;
import kr.co.n3soft.n3com.model.comm.command.UMLReorderPartCommand;
import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.editpolicies.TreeContainerEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

public class UMLTreeContainerEditPolicy extends TreeContainerEditPolicy {

	protected Command createCreateCommand(UMLModel child, Rectangle r, int index, String label) {
        UMLCreateCommand cmd = new UMLCreateCommand();
        Rectangle rect;
        if (r == null) {
            rect = new Rectangle();
            rect.setSize(new Dimension(-1, -1));
        } else {
            rect = r;
        }
        cmd.setLocation(rect);
        cmd.setParent((UMLDiagramModel)getHost().getModel());
        cmd.setChild(child);
        cmd.setLabel(label);
        if (index >= 0)
            cmd.setIndex(index);
        return cmd;
    }

    protected Command getAddCommand(ChangeBoundsRequest request) {
        CompoundCommand command = new CompoundCommand();
        command.setDebugLabel("Add in LogicTreeContainerEditPolicy"); //$NON-NLS-1$
        List editparts = request.getEditParts();
        int index = findIndexOfTreeItemAt(request.getLocation());
        for (int i = 0; i < editparts.size(); i++) {
            EditPart child = (EditPart)editparts.get(i);
            if (isAncestor(child, getHost()))
                command.add(UnexecutableCommand.INSTANCE);
            else {
                UMLModel childModel = (UMLModel)child.getModel();
                command.add(createCreateCommand(childModel,
                    new Rectangle(new org.eclipse.draw2d.geometry.Point(), childModel.getSize()), index, "Reparent UMLModel")); //$NON-NLS-1$
            }
        }
        return command;
    }

    protected Command getCreateCommand(CreateRequest request) {
        UMLModel child = (UMLModel)request.getNewObject();
        int index = findIndexOfTreeItemAt(request.getLocation());
        return createCreateCommand(child, null, index, "Create UMLModel"); //$NON-NLS-1$
    }

    protected Command getMoveChildrenCommand(ChangeBoundsRequest request) {
        CompoundCommand command = new CompoundCommand();
        List editparts = request.getEditParts();
        List children = getHost().getChildren();
        int newIndex = findIndexOfTreeItemAt(request.getLocation());
        for (int i = 0; i < editparts.size(); i++) {
            EditPart child = (EditPart)editparts.get(i);
            int tempIndex = newIndex;
            int oldIndex = children.indexOf(child);
            if (oldIndex == tempIndex || oldIndex + 1 == tempIndex) {
                command.add(UnexecutableCommand.INSTANCE);
                return command;
            } else if (oldIndex <= tempIndex) {
                tempIndex--;
            }
            command.add(
                new UMLReorderPartCommand((UMLModel)child.getModel(), (UMLDiagramModel)getHost().getModel(), tempIndex));
        }
        return command;
    }

    protected boolean isAncestor(EditPart source, EditPart target) {
        if (source == target)
            return true;
        if (target.getParent() != null)
            return isAncestor(source, target.getParent());
        return false;
    }
}
