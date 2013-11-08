package kr.co.n3soft.n3com.model.comm.command;

import kr.co.n3soft.n3com.model.comm.UMLModelGuide;
import kr.co.n3soft.n3com.model.comm.UMLModelRuler;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.examples.logicdesigner.LogicMessages;

public class CreateGuideCommand extends Command {
    private UMLModelGuide guide;
    private UMLModelRuler parent;
    private int position;

    public CreateGuideCommand(UMLModelRuler parent, int position) {
        super(LogicMessages.CreateGuideCommand_Label);
        this.parent = parent;
        this.position = position;
    }

    public boolean canUndo() {
        return true;
    }

    public void execute() {
        if (guide == null)
            guide = new UMLModelGuide(!parent.isHorizontal());
        guide.setPosition(position);
        parent.addGuide(guide);
    }

    public void undo() {
        parent.removeGuide(guide);
    }
}
