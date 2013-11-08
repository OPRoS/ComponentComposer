package kr.co.n3soft.n3com.model.comm.command;

import kr.co.n3soft.n3com.edit.MessageEditPart;

import org.eclipse.draw2d.geometry.Point;

public class MessageLineSetContraintCommand extends org.eclipse.gef.commands.Command {
	//20080908IJS ÀüÃ¼
//	MessageModel m;
	Point oldLocation;
	int y = 0;
	int oldY = 0;
	MessageEditPart u;
	boolean isBegin = false;
	int vValue = 0;
	boolean isF = false;
	

    public void execute() {
//    	  if(part instanceof LineTextModel){

        redo();
    }

    public String getLabel() {
      return "";
    }

    public void redo() {
    	
       
    }

    
    public void undo() {
    	
		
    }

	

	public Point getOldLocation() {
		return oldLocation;
	}

	public void setOldLocation(Point oldLocation) {
		this.oldLocation = oldLocation;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getOldY() {
		return oldY;
	}

	public void setOldY(int oldY) {
		this.oldY = oldY;
	}

	public MessageEditPart getU() {
		return u;
	}

	public void setU(MessageEditPart u) {
		this.u = u;
	}

	public boolean isF() {
		return isF;
	}

	public void setF(boolean isF) {
		this.isF = isF;
	}
	
	
}
