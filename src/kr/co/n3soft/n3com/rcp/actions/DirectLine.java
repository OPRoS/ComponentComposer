package kr.co.n3soft.n3com.rcp.actions;

import java.util.List;

import kr.co.n3soft.n3com.edit.LineEditPart;
import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.comm.ClassModel;
import kr.co.n3soft.n3com.model.comm.LineModel;
import kr.co.n3soft.n3com.model.comm.command.DirectLineCommand;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IEditorPart;


/**
 * ���� ���� //PKY 08051401 S ���� ���ΰ� �ٷ� �������� ����� 
 */
public class DirectLine extends SelectionAction {
	ClassModel classModel = null;
	java.util.ArrayList attributes = null;
    public DirectLine(IEditorPart editor) {
        super(editor);
        this.setId("DirectLine");
        this.setText(N3Messages.POPUP_DIRECT_LINE);
    }

    protected boolean calculateEnabled() {
        // TODO Workaround for Bug 82622/39369.  Should be removed when 39369 is fixed.
//      Object obj1 = this.getSelection();
      java.util.List list = ProjectManager.getInstance().getSelectNodes();
  	if(list.size()>0){
  		for(int i=0; i<list.size();i++){
  			if(list.get(i) instanceof LineEditPart){
  				LineEditPart lineEditPart=(LineEditPart)list.get(i);
  				if(lineEditPart.getModel() instanceof LineModel){//PKY 08052601 S ������ �޽��� ���̷�Ʈ POPUP����
  					 LineModel lineModel=( LineModel)lineEditPart.getModel();
  					 return true;
  				}
  			}
  		}
  	}
//      if (list != null && list.size() == 1) {
//          Object obj = list.get(0);
//          if (obj instanceof UMLEditPart) {
//              UMLEditPart u = (UMLEditPart)obj;
//              UMLModel um = (UMLModel)u.getModel();
//              if ((um instanceof ClassifierModel || um instanceof ClassifierModelTextAttach)
//              		&& !(um instanceof FinalBoundryModel)) {
//              	
//              	if(um instanceof ClassifierModel){
//              		ClassifierModel classifierModel = null;
//              		classifierModel = (ClassifierModel)um;
//              		classModel = classifierModel.getClassModel();
//          			attributes = (java.util.ArrayList) classModel.getAttributes().clone();
//              	}
//              	else{
//              		ClassifierModelTextAttach classifierModel = null;
//              		classifierModel = (ClassifierModelTextAttach)um;
//              		classModel = classifierModel.getClassModel();
//          			attributes = (java.util.ArrayList) classModel.getAttributes().clone();
//              	}
//      			
//      			return true;
//      		}
//          }
//      }
      return false;
  }

    public void run() {
    	
        //		this.getCommandStack().execute(command)
    	List list=ProjectManager.getInstance().getSelectNodes();
    	LineModel lineModel = null;
    	if(list.size()>0){
    		for(int i=0; i<list.size();i++){
    			if(list.get(i) instanceof LineEditPart){
    				LineEditPart lineEditPart=(LineEditPart)list.get(i);
    				if(lineEditPart.getModel() instanceof LineModel){
    					  lineModel=( LineModel)lineEditPart.getModel();
//    					 lineModel.directBendpoint();
    					 
    				}
    			}
    		}
    	}
    	DirectLineCommand dc = new DirectLineCommand();
    	dc.setBendpoints(lineModel.getBendpoints());
        dc.setLineModel(lineModel);
        ProjectManager.getInstance().getCommandStack().execute(dc);
    	
        
    }
}
