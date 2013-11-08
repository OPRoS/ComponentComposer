package kr.co.n3soft.n3com.model.comm.command;

import java.util.List;

import kr.co.n3soft.n3com.edit.LineEditPart;
import kr.co.n3soft.n3com.model.comm.LineModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.draw2d.Bendpoint;
import org.eclipse.gef.commands.Command;

public class DirectLineCommand extends Command {
	public java.util.Vector vbendpoints = new java.util.Vector();
	LineModel lineModel;
	public java.util.Vector getBendpoints() {
		return vbendpoints;
	}

	public void setBendpoints(java.util.List bendpoints) {
		for(int i=0;i<bendpoints.size();i++){
			Bendpoint point = (Bendpoint)bendpoints.get(i);
			vbendpoints.add(point);
			
		
//		this.bendpoints
		}
	}
	
	
	 public void execute() {
		 this.redo();
	 }
	 public void redo() {
		
	    lineModel.directBendpoint();
	    					 
	    		
	 }
	 
	 public void undo(){
		 lineModel.setBendpoints(vbendpoints);
	 }

	public LineModel getLineModel() {
		return lineModel;
	}

	public void setLineModel(LineModel lineModel) {
		this.lineModel = lineModel;
	}
	
	
	
	
	
	
	

}
