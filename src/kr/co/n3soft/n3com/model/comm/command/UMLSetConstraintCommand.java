package kr.co.n3soft.n3com.model.comm.command;

import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.examples.logicdesigner.LogicMessages;

public class UMLSetConstraintCommand extends org.eclipse.gef.commands.Command {
    private Point newPos;
    private Dimension newSize;
    private Point oldPos;
    private Dimension oldSize;
    private UMLModel part;

    public void execute() {
        oldSize = part.getSize();
        oldPos = part.getLocation();
        redo();
    }

    public String getLabel() {
        if (oldSize.equals(newSize))
            return LogicMessages.SetLocationCommand_Label_Location;
        return LogicMessages.SetLocationCommand_Label_Resize;
    }

    public void redo() {
    		part.setSize(newSize);
    		part.setLocation(newPos);
    }

    public void setLocation(Rectangle r) {
        setLocation(r.getLocation());
        setSize(r.getSize());
    }

    public void setLocation(Point p) {
        newPos = p;
    }

    public void setPart(UMLModel part) {
        this.part = part;
    }

    public void setSize(Dimension p) {
        newSize = p;
    }

    public void undo() {
        part.setSize(oldSize);
        part.setLocation(oldPos);
    }
}
