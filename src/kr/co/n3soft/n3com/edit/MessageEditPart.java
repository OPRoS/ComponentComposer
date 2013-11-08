package kr.co.n3soft.n3com.edit;

import java.util.List;

import org.eclipse.draw2d.AbsoluteBendpoint;
import org.eclipse.draw2d.Bendpoint;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.requests.BendpointRequest;

public class MessageEditPart extends LineEditPart {
	//20080908IJS 전체 수정
    protected IFigure createFigure() {
        Connection connx = null;
        return connx;
    }

    public void checkBendPoint() {
    }

    //seq
    public void setSource(EditPart editPart) {
       
       


      }
       
  //seq
    public void setTarget(EditPart editPart) {
    	
        }
    
    protected void showMoveBendpointFeedback(BendpointRequest request) {
        Point p = new Point(request.getLocation());
        List constraint;
        
        this.getConnectionFigure().translateToRelative(p);
        Bendpoint bp = new AbsoluteBendpoint(p);
        //		if (originalConstraint == null) {
        //			saveOriginalConstraint();
        //			constraint = (List)getConnection().getRoutingConstraint();
        //			constraint.add(request.getIndex(), bp);
        //		} else {
        constraint = (List)this.getConnectionFigure().getRoutingConstraint();
        //		}
        try {
            Object obj = constraint.get(request.getIndex());
        }
        catch (Exception e) {
            constraint.add(request.getIndex(), bp);
        }
        //			if(obj==null){
        //				constraint.add(request.getIndex(), bp);
        //			}
        constraint.set(request.getIndex(), bp);
        this.getConnectionFigure().setRoutingConstraint(constraint);
        //		Point p = new Point(request.getLocation());
        //		if (!isDeleting)
        //			setReferencePoints(request);
        //		
        //		if (lineContainsPoint(ref1, ref2, p)) {
        //			if (!isDeleting) {
        //				isDeleting = true;
        //				eraseSourceFeedback(request);
        //				showDeleteBendpointFeedback(request);
        //			}
        //			return;
        //		}
        //		if (isDeleting) {
        //			isDeleting = false;
        //			eraseSourceFeedback(request);
        //		}
        //		if (originalConstraint == null)
        //			saveOriginalConstraint();
        //		List constraint = (List)getConnection().getRoutingConstraint();
        //		getConnection().translateToRelative(p);
        //		Bendpoint bp = new AbsoluteBendpoint(p);
        //		constraint.set(request.getIndex(), bp);
        //		getConnection().setRoutingConstraint(constraint);
    }

      
   
//    }

    //seq
//    public void setTarget(EditPart editPart) {
//    	ZoomManager manager = (ZoomManager)ProjectManager.getInstance().getUMLEditor().getGraphicalViewerp().getProperty(ZoomManager.class.toString());
//        double zoom = 0;
//       
//        if(manager!=null){
//        	 pt = ProjectManager.getInstance().getDragPoint();
//        	
//        	zoom=manager.getZoom();
////        	pt.y;
//        	pt.y = (int)(pt.y*zoom);
//        }
//        Point pt =null;
//        try{
//         pt = new Point(ProjectManager.getInstance().getDragPoint().x,ProjectManager.getInstance().getDragPoint().y/zoom);
//        }
//        catch(Exception e){
//        	e.printStackTrace();
//        	pt = new Point(100,500);
//        }
//        super.setTarget(editPart);
//
//        BendpointRequest request = new BendpointRequest();
//        request.setIndex(0);
//        request.setSource(this);
//        request.setType("move bendpoint");
////        request.setLocation(new Point(ProjectManager.getInstance().getDragPoint().x, ProjectManager.getInstance().getDragPoint().y));
//        LineModel um = (LineModel)this.getModel();
////        if(um.getSource() instanceof LifeLineModel && um.getTarget() instanceof LifeLineModel){
//        UMLModel ums = (UMLModel)um.getSource();
//        UMLModel umt = (UMLModel)um.getTarget();
//	      int x1 = 0;
//	      if(ums.getLocation().x>umt.getLocation().x){
//	    	  x1 = umt.getLocation().x+umt.getSize().width;
//	    	  
//	      }
//	      else{
//	    	  x1 = ums.getLocation().x+ums.getSize().width;
//	      }
////	      request.setLocation(new Point((int)(x1*zoom), ProjectManager.getInstance().getDragPoint().y));
//	      request.setLocation(new Point((int)(x1*zoom), 200));
////        }
//        this.showSourceFeedback(request);
//        ConnectionAnchor connectionAnchor = this.getTargetConnectionAnchor();
////        ProjectManager.getInstance().getUMLEditor().get
////        ZoomManager manager = (ZoomManager)ProjectManager.getInstance().getUMLEditor().getGraphicalViewerp().getProperty(ZoomManager.class.toString());
//        if (connectionAnchor instanceof SeqChopboxAnchor) {
//            SeqChopboxAnchor seq = (SeqChopboxAnchor)connectionAnchor;
////            if (seq.isMove) {
//                seq.y =pt.y;
//                seq.isMove = false;
//                connectionAnchor = this.getSourceConnectionAnchor();
//                MessageModel m  = (MessageModel)this.getModel();
//                m.setMessageEditPart(this);
//              
//                m.setY((int)(seq.y));
//                m.setSourceAnchor(seq);
//                if(m.getSource() instanceof LifeLineModel){
//                	 LifeLineModel lf = (LifeLineModel)m.getSource();
//                     lf.addMessage(m);
//                     lf.changeGroup();
//                }
//            	if(m.getTarget() instanceof LifeLineModel){
//            		LifeLineModel lf = (LifeLineModel)m.getTarget();
//                     lf.addMessage(m);
//                     lf.changeGroup();
//            	}
//               
//               
//            }
//        }

      
   
//    }
}
