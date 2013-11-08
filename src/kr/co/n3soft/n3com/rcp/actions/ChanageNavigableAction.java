package kr.co.n3soft.n3com.rcp.actions;

import java.util.HashMap;

import kr.co.n3soft.n3com.edit.AssociateLineEditPart;
import kr.co.n3soft.n3com.edit.LineEditPart;
import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.comm.LineModel;
import kr.co.n3soft.n3com.model.comm.command.ChanageNavigableCommand;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorPart;

public class ChanageNavigableAction extends SelectionAction {
	boolean isSource = true;
	boolean isNavigable = false;
	LineModel lm = null;
    public ChanageNavigableAction(IEditorPart editor) {
        super(editor);
        this.setId("ChanageNavigableAction");
        this.setText(N3Messages.POPUP_NAVIGABLE);
    }

    protected boolean calculateEnabled() {
        // TODO Workaround for Bug 82622/39369.  Should be removed when 39369 is fixed.
        Object obj1 = this.getSelection();
        StructuredSelection list = (StructuredSelection)this.getSelection();
        if (list != null && list.size() == 1) {
            Object obj = list.getFirstElement();
            if (obj instanceof AssociateLineEditPart) {
            	LineEditPart u = (LineEditPart)obj;
                lm = (LineModel)u.getModel();
             
                PolylineConnection n3line = (PolylineConnection)u.getFigure();
               
                Point pt1  = n3line.getStart();
                Point pt2  =  n3line.getEnd();
                Point pt = ProjectManager.getInstance().getMouseSelectPoint();
                
                double d = pt.getDistance(pt1);
                double d1 = pt.getDistance(pt2);
               if(d>d1){
            	   isSource = false;  
            	   HashMap hm = lm.getDetailProp();
                   String value = (String)hm.get(LineModel.ID_TARGET_NAVIGABILITY);//PKY 08060201 S
                   if(value!=null && value.equals("Navigable")){
                	   isNavigable = true;
                	  
                   }
                   else{
                	   isNavigable = false;
                	  
                   }
               }
               else{
            	   HashMap hm = lm.getDetailProp();
                   String value = (String)hm.get(LineModel.ID_SOURCE_NAVIGABILITY);//PKY 08060201 S
                   isSource = true;  
                   if(value!=null && value.equals("Navigable")){
                	   isNavigable = true;
                	   
                   }
                   else{
                	   isNavigable = false;
                	   
                   }
               }
               setChecked(isNavigable);
                 return true;
                 
              
            }
        }
        return false;
    }

    public void run() {
        //		this.getCommandStack().execute(command)
//        if(lm!=null){
//        	if(this.isSource){
//        		if(isNavigable){
//        			lm.getDetailProp().put(LineModel.ID_SOURCE_NAVIGABILITY, "0");
//        		}
//        		else{
//        			
//        			lm.getDetailProp().put(LineModel.ID_SOURCE_NAVIGABILITY, "Navigable");
//        		}
//        	}
//        	else{
//        		if(isNavigable){
//        			lm.getDetailProp().put(LineModel.ID_TARGET_NAVIGABILITY, "0");
//        		}
//        		else{
//        			lm.getDetailProp().put(LineModel.ID_TARGET_NAVIGABILITY, "Navigable");
//        		}
//        	}
//        	lm.setDetailProp(lm.getDetailProp());
//			lm.isChange = true;
//        }
    	ChanageNavigableCommand cnc = new ChanageNavigableCommand();
    	cnc.setLm(lm);
    	cnc.setNavigable(isNavigable);
    	cnc.setSource(isSource);
    	
    	ProjectManager.getInstance().getCommandStack().execute(cnc);
    	
    }
}
