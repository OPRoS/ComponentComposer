package kr.co.n3soft.n3com.rcp.actions;

import java.util.HashMap;

import kr.co.n3soft.n3com.edit.AssociateLineEditPart;
import kr.co.n3soft.n3com.edit.LineEditPart;
import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.comm.LineModel;
import kr.co.n3soft.n3com.model.comm.command.ChanageAggregateCommand;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorPart;

public class ChanageAggregateAction extends SelectionAction {
	boolean isSource = true;
	boolean isAggregate = false;
	LineModel lm = null;
	LineEditPart u = null;
    public ChanageAggregateAction(IEditorPart editor) {
        super(editor, AS_CHECK_BOX);
        this.setId("ChanageAggregateAction");
        this.setText(N3Messages.POPUP_AGGREGATE);
//        setChecked(isChecked());
    }
//    public boolean isChecked() {
//    	
//    		return true;
//    	
//    }
    protected boolean calculateEnabled() {
        // TODO Workaround for Bug 82622/39369.  Should be removed when 39369 is fixed.
        Object obj1 = this.getSelection();
        StructuredSelection list = (StructuredSelection)this.getSelection();
        if (list != null && list.size() == 1) {
            Object obj = list.getFirstElement();
            if (obj instanceof AssociateLineEditPart) {
            	u = (LineEditPart)obj;
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
                   String value = (String)hm.get(LineModel.ID_TARGET_AGGREGATION);
                   if(value!=null && value.equals("Aggregate")){
                	   isAggregate = true;
                	  
                   }
                   else{
                	   isAggregate = false;
                	  
                   }
               }
               else{
            	   HashMap hm = lm.getDetailProp();
                   String value = (String)hm.get(LineModel.ID_SOURCE_AGGREGATION);
                   isSource = true;  
                   if(value!=null && value.equals("Aggregate")){
                	   isAggregate = true;
                	   
                   }
                   else{
                	   isAggregate = false;
                	   
                   }
               }
               setChecked(isAggregate);
                 return true;
                 
              
            }
        }
        return false;
    }

    public void run() {
    	ChanageAggregateCommand ca = new ChanageAggregateCommand();
    	ca.setSource(isSource);
    	ca.setAggregate(isAggregate);
    	ca.setLm(lm);
        		this.getCommandStack().execute(ca);
    	
        
    }
}
