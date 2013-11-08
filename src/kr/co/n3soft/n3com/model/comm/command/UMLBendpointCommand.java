package kr.co.n3soft.n3com.model.comm.command;

import kr.co.n3soft.n3com.model.comm.LineBendpointModel;
import kr.co.n3soft.n3com.model.comm.LineModel;
import org.eclipse.draw2d.Bendpoint;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;

public class UMLBendpointCommand extends Command {
    protected int index;
    protected Point location;
    protected LineModel wire;
    private Dimension d1, d2;

    protected Dimension getFirstRelativeDimension() {
        return d1;
    }

    protected Dimension getSecondRelativeDimension() {
        return d2;
    }

    protected int getIndex() {
        return index;
    }

    protected Point getLocation() {
        return location;
    }

    protected LineModel getWire() {
        return wire;
    }

    public void redo() {
        execute();
    }

    public void setRelativeDimensions(Dimension dim1, Dimension dim2) {
        d1 = dim1;
        d2 = dim2;
    }

    public void setIndex(int i) {
        index = i;
    }

    public void setLocation(Point p) {
        location = p;
    }

    public void setWire(LineModel w) {
        wire = w;
    }
}
