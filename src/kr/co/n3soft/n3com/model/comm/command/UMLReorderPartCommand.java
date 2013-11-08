package kr.co.n3soft.n3com.model.comm.command;

import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.examples.logicdesigner.LogicMessages;

public class UMLReorderPartCommand extends Command {
    private int oldIndex, newIndex;
    private UMLModel child;
    private UMLDiagramModel parent;

    public UMLReorderPartCommand(UMLModel child, UMLDiagramModel parent, int newIndex) {
        super(LogicMessages.ReorderPartCommand_Label);
        this.child = child;
        this.parent = parent;
        this.newIndex = newIndex;
    }

    public void execute() {
        oldIndex = parent.getChildren().indexOf(child);
        parent.removeChild(child);
        parent.addChild(child, newIndex);
    }

    public void undo() {
        parent.removeChild(child);
        parent.addChild(child, oldIndex);
    }
}
