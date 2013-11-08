package kr.co.n3soft.n3com.model.comm.command;

import kr.co.n3soft.n3com.model.comm.LineBendpointModel;
import org.eclipse.draw2d.Bendpoint;

public class UMLMoveBendpointCommand extends UMLBendpointCommand {
    private Bendpoint oldBendpoint;

    public void execute() {
        LineBendpointModel bp = new LineBendpointModel();
        bp.setRelativeDimensions(getFirstRelativeDimension(), getSecondRelativeDimension());
        int index = getIndex();
        if(getWire().getBendpoints().size()>0){
        	setOldBendpoint((Bendpoint)getWire().getBendpoints().get(getIndex()));
        	getWire().setBendpoint(getIndex(), bp);
        }
    }

    protected Bendpoint getOldBendpoint() {
        return oldBendpoint;
    }

    public void setOldBendpoint(Bendpoint bp) {
        oldBendpoint = bp;
    }

    public void undo() {
        super.undo();
        getWire().setBendpoint(getIndex(), getOldBendpoint());
    }
}
