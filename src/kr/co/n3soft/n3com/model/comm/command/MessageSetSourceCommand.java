package kr.co.n3soft.n3com.model.comm.command;

import org.eclipse.gef.commands.Command;

public class MessageSetSourceCommand extends Command {

	int y=-1;
	
	public boolean canUndo() {
		return true;
	}

	public void execute() {
		this.redo();

	}

	public void undo() {
		System.out.println("dddd");

	}
	public void redo(){

		
	}

	
	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}

