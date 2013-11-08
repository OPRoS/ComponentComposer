package kr.co.n3soft.n3com.model.comm.command;

import java.util.HashMap;

import kr.co.n3soft.n3com.model.comm.LineModel;

import org.eclipse.gef.commands.Command;

public class ChangeDetailPropCommand extends Command {

	LineModel lm = null;
	HashMap oldProp = null;
	HashMap newProp = null;
	
	 public void execute() {
		 this.redo();
	 }
	 public void redo() {
	
		 lm.setDetailProp(newProp);
			lm.isChange = true;
	 }
	 public void undo() {
		 lm.setDetailProp(oldProp);
			lm.isChange = true;
	 }
	
	public LineModel getLm() {
		return lm;
	}
	public void setLm(LineModel lm) {
		this.lm = lm;
	}
	public HashMap getOldProp() {
		return oldProp;
	}
	public void setOldProp(HashMap oldProp) {
		this.oldProp = oldProp;
	}
	public HashMap getNewProp() {
		return newProp;
	}
	public void setNewProp(HashMap newProp) {
		this.newProp = newProp;
	}
}
