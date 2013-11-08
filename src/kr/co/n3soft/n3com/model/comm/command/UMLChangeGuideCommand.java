package kr.co.n3soft.n3com.model.comm.command;

import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.comm.UMLModelGuide;
import org.eclipse.gef.commands.Command;

public class UMLChangeGuideCommand extends Command {
    private UMLModel part;
    private UMLModelGuide oldGuide, newGuide;
    private int oldAlign, newAlign;
    private boolean horizontal;

    public UMLChangeGuideCommand(UMLModel part, boolean horizontalGuide) {
        super();
        this.part = part;
        horizontal = horizontalGuide;
    }

    protected void changeGuide(UMLModelGuide oldGuide, UMLModelGuide newGuide, int newAlignment) {
        if (oldGuide != null && oldGuide != newGuide) {
            oldGuide.detachPart(part);
        }
        // You need to re-attach the part even if the oldGuide and the newGuide are the same
        // because the alignment could have changed
        if (newGuide != null) {
            newGuide.attachPart(part, newAlignment);
        }
    }

    public void execute() {
        // Cache the old values
        oldGuide = horizontal ? part.getHorizontalGuide() : part.getVerticalGuide();
        if (oldGuide != null)
            oldAlign = oldGuide.getAlignment(part);
        redo();
    }

    public void redo() {
        changeGuide(oldGuide, newGuide, newAlign);
    }

    public void setNewGuide(UMLModelGuide guide, int alignment) {
        newGuide = guide;
        newAlign = alignment;
    }

    public void undo() {
        changeGuide(newGuide, oldGuide, oldAlign);
    }
}
