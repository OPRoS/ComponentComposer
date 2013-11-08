package kr.co.n3soft.n3com.model.comm.command;

import java.util.HashMap;

import kr.co.n3soft.n3com.model.comm.LineModel;

import org.eclipse.gef.commands.Command;

public class ChanageAggregateCommand extends Command {
	boolean isSource = true;
	boolean isAggregate = false;
	LineModel lm = null;
	HashMap oldProp = null;
	
	 public void execute() {
		 this.redo();
	 }
	 public void redo() {
		 System.out.println("redo");
		 oldProp = (HashMap)lm.getDetailProp().clone();
		 if(lm!=null){
			 
	        	if(this.isSource){
	        		
	        		if(isAggregate){
	        			lm.getDetailProp().put(LineModel.ID_SOURCE_AGGREGATION, "0");
	        		}
	        		else{
	        			lm.getDetailProp().put(LineModel.ID_SOURCE_AGGREGATION, "Aggregate");
	        		}
	        	}
	        	else{
	        		if(isAggregate){
	        			lm.getDetailProp().put(LineModel.ID_TARGET_AGGREGATION, "0");
	        		}
	        		else{
	        			lm.getDetailProp().put(LineModel.ID_TARGET_AGGREGATION, "Aggregate");
	        		}
	        	}
	        	lm.setDetailProp(lm.getDetailProp());
				lm.isChange = true;
	        }
	 }
	 public void undo() {
		 System.out.println("undo");
		 lm.setDetailProp(oldProp);
			lm.isChange = true;
	 }
	public boolean isSource() {
		return isSource;
	}
	public void setSource(boolean isSource) {
		this.isSource = isSource;
	}
	public boolean isAggregate() {
		return isAggregate;
	}
	public void setAggregate(boolean isAggregate) {
		this.isAggregate = isAggregate;
	}
	public LineModel getLm() {
		return lm;
	}
	public void setLm(LineModel lm) {
		this.lm = lm;
	}
}
