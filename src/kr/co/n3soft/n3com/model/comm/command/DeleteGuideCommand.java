package kr.co.n3soft.n3com.model.comm.command;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.comm.UMLModelGuide;
import kr.co.n3soft.n3com.model.comm.UMLModelRuler;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.examples.logicdesigner.LogicMessages;

public class DeleteGuideCommand extends Command {
    private UMLModelRuler parent;
    private UMLModelGuide guide;
    private Map oldParts;

    public DeleteGuideCommand(UMLModelGuide guide, UMLModelRuler parent) {
        super(LogicMessages.DeleteGuideCommand_Label);
        this.guide = guide;
        this.parent = parent;
    }

    public boolean canUndo() {
        return true;
    }

    public void execute() {
        oldParts = new HashMap(guide.getMap());
        Iterator iter = oldParts.keySet().iterator();
        while (iter.hasNext()) {
            guide.detachPart((UMLModel)iter.next());
        }
        parent.removeGuide(guide);
    }

    public void undo() {
        parent.addGuide(guide);
        Iterator iter = oldParts.keySet().iterator();
        while (iter.hasNext()) {
            UMLModel part = (UMLModel)iter.next();
            guide.attachPart(part, ((Integer)oldParts.get(part)).intValue());
        }
    }
}
