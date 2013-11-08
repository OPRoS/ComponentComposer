package kr.co.n3soft.n3com.model.comm.command;

import org.eclipse.gef.commands.Command;

public class SeqMessagePropCommand extends Command {

//	String condition ="";
//	MessageModel model;
	boolean isR = false;
	boolean isS = false;
	boolean isK = false;
	boolean isNew = false;
	
	boolean isOldR = false;
	boolean isOldS = false;
	boolean isOldK = false;
	boolean isOldNew = false;
	
	 public void execute() {
		 this.redo();
	 }
	 public void redo() {
//		 this.model.setProperty(isS, isK, isR,isNew);
		
	 }
	 public void undo() {
//		 this.model.setProperty(isOldS, isOldK, isOldR,isOldNew);
	 }
	
	public boolean isR() {
		return isR;
	}
	public void setR(boolean isR) {
		this.isR = isR;
	}
	public boolean isS() {
		return isS;
	}
	public void setS(boolean isS) {
		this.isS = isS;
	}
	public boolean isK() {
		return isK;
	}
	public void setK(boolean isK) {
		this.isK = isK;
	}
	public boolean isNew() {
		return isNew;
	}
	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}
	public boolean isOldR() {
		return isOldR;
	}
	public void setOldR(boolean isOldR) {
		this.isOldR = isOldR;
	}
	public boolean isOldS() {
		return isOldS;
	}
	public void setOldS(boolean isOldS) {
		this.isOldS = isOldS;
	}
	public boolean isOldK() {
		return isOldK;
	}
	public void setOldK(boolean isOldK) {
		this.isOldK = isOldK;
	}
	public boolean isOldNew() {
		return isOldNew;
	}
	public void setOldNew(boolean isOldNew) {
		this.isOldNew = isOldNew;
	}
	
	
	
}
