package kr.co.n3soft.n3com.model.comm.command;

import org.eclipse.draw2d.Bendpoint;

public class UMLDeleteBendpointCommand extends UMLBendpointCommand {
    private Bendpoint bendpoint;

    public void execute() {
        bendpoint = (Bendpoint)getWire().getBendpoints().get(getIndex());
        getWire().removeBendpoint(getIndex());
        super.execute();
    }

    public void undo() {
        super.undo();
        getWire().insertBendpoint(getIndex(), bendpoint);
    }
}
