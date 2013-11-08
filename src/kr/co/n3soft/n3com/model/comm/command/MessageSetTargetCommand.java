package kr.co.n3soft.n3com.model.comm.command;

import kr.co.n3soft.n3com.edit.MessageEditPart;
import kr.co.n3soft.n3com.model.comm.SeqChopboxAnchor;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;

public class MessageSetTargetCommand extends Command {
	int oldy;
	int y;
	
	 SeqChopboxAnchor seq;
	 boolean isMove;
	 MessageEditPart mep;
	 EditPart editPart;
	 public boolean canUndo() {
	        return true;
	    }

	    public void execute() {
	      this.redo();
	    }

	    public void undo() {

            
            	System.out.println("UNDO");
            
	    }
	    public void redo(){
	    	/*
	    	mep.setSuperTarget(editPart);
	     	ZoomManager manager = (ZoomManager)ProjectManager.getInstance().getUMLEditor().getGraphicalViewerp().getProperty(ZoomManager.class.toString());
	         double zoom = 0;
	         Point pt = null;
	         if(manager!=null){
	         	
	         	zoom=manager.getZoom();
	         }
	         if(ProjectManager.getInstance().getDragPoint()!=null){
	         	pt = new Point(ProjectManager.getInstance().getDragPoint().x,ProjectManager.getInstance().getDragPoint().y/zoom);
	         }
	         else{
	         	 MessageModel m  = (MessageModel)mep.getModel();
	         	 pt = new Point(0,m.getY());
	         }
	        
	        

	         BendpointRequest request = new BendpointRequest();
	         request.setIndex(0);
	         request.setSource(mep);
	         request.setType("move bendpoint");
	         LineModel um = (LineModel)mep.getModel();
	         UMLModel ums = (UMLModel)um.getSource();
	         UMLModel umt = (UMLModel)um.getTarget();
	 	      int x1 = 0;
	 	      if(ums!=null)//PKY 08052601 S 저장불러오기할경우 에러발생
	 	      if(ums.getLocation().x>umt.getLocation().x){
	 	    	  x1 = umt.getLocation().x+umt.getSize().width;
	 	    	  
	 	      }
	 	      else{
	 	    	  x1 = ums.getLocation().x+ums.getSize().width;
	 	      }
	 	      if(ProjectManager.getInstance().getDragPoint()!=null)
	 	    	  request.setLocation(new Point((int)(x1*zoom), ProjectManager.getInstance().getDragPoint().y));
	 	      else
	 	    	  request.setLocation(new Point((int)(x1*zoom), pt.y));
	 	      
	         ConnectionAnchor connectionAnchor = mep.getTargetConnectionAnchorPublic();
	         if (connectionAnchor instanceof SeqChopboxAnchor) {
	             SeqChopboxAnchor seq = (SeqChopboxAnchor)connectionAnchor;
	                 seq.y =pt.y;
	                 seq.isMove = false;
	                 connectionAnchor = mep.getSourceConnectionAnchorPublic();
	                 MessageModel m  = (MessageModel)mep.getModel();
	                 m.setMessageEditPart(mep);
	               
	                 m.setY((int)(seq.y));
	                 if(seq.y==237){
	                 	System.out.println("237=====================================>");
	                 }
	                 m.setSourceAnchor(seq);
	                 if(m.getSource() instanceof LifeLineModel){
	                 	 LifeLineModel lf = (LifeLineModel)m.getSource();
	                      lf.addMessage(m);
	                      lf.changeGroup();
	                 }
	             	if(m.getTarget() instanceof LifeLineModel){
	             		LifeLineModel lf = (LifeLineModel)m.getTarget();
	                      lf.addMessage(m);
	                      lf.changeGroup();
	             	}
	                
	                
	             }
	         if(ProjectManager.getInstance().getDragPoint()!=null){
	        	 mep.showSourceFeedback(request);

	         }
	         else{
	        	 mep.showMoveBendpointFeedbackPublic(request);
	         }
	         */
	           
            
	    }

		public int getOldy() {
			return oldy;
		}

		public void setOldy(int oldy) {
			this.oldy = oldy;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		

		public SeqChopboxAnchor getSeq() {
			return seq;
		}

		public void setSeq(SeqChopboxAnchor seq) {
			this.seq = seq;
		}

		public boolean isMove() {
			return isMove;
		}

		public void setMove(boolean isMove) {
			this.isMove = isMove;
		}

		public MessageEditPart getMep() {
			return mep;
		}

		public void setMep(MessageEditPart mep) {
			this.mep = mep;
		}

		public EditPart getEditPart() {
			return editPart;
		}

		public void setEditPart(EditPart editPart) {
			this.editPart = editPart;
		}
	}
