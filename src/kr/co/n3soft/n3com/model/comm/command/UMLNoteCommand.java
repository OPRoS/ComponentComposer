package kr.co.n3soft.n3com.model.comm.command;

import kr.co.n3soft.n3com.model.comm.UMLNoteModel;
import org.eclipse.gef.commands.Command;

public class UMLNoteCommand extends Command {
    private String newName, oldName;
    private UMLNoteModel label;

    public UMLNoteCommand(UMLNoteModel l, String s) {
        label = l;
        if (s != null)
            newName = s;
        else
            newName = ""; //$NON-NLS-1$
    }

    public void execute() {
        oldName = label.getLabelContents();
        label.setLabelContents(newName);
    }

    public void undo() {
        label.setLabelContents(oldName);
    }
}
