package kr.co.n3soft.n3com.model.comm.command;

import java.util.Iterator;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.comm.UMLModelGuide;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.examples.logicdesigner.LogicMessages;


public class MoveGuideCommand extends Command {
    private int pDelta;
    private UMLModelGuide guide;

    public MoveGuideCommand(UMLModelGuide guide, int positionDelta) {
        super(LogicMessages.MoveGuideCommand_Label);
        this.guide = guide;
        pDelta = positionDelta;
    }

    public void execute() {
        guide.setPosition(guide.getPosition() + pDelta);
        Iterator iter = guide.getParts().iterator();
        while (iter.hasNext()) {
            UMLModel part = (UMLModel)iter.next();
            Point location = part.getLocation().getCopy();
            if (guide.isHorizontal()) {
                location.y += pDelta;
            } else {
                location.x += pDelta;
            }
            part.setLocation(location);
        }
    }

    public void undo() {
        guide.setPosition(guide.getPosition() - pDelta);
        Iterator iter = guide.getParts().iterator();
        while (iter.hasNext()) {
            UMLModel part = (UMLModel)iter.next();
            Point location = part.getLocation().getCopy();
            if (guide.isHorizontal()) {
                location.y -= pDelta;
            } else {
                location.x -= pDelta;
            }
            part.setLocation(location);
        }
    }
}
