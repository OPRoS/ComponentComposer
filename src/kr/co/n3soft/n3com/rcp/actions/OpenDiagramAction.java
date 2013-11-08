package kr.co.n3soft.n3com.rcp.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kr.co.n3soft.n3com.edit.ComponentEditPart;
import kr.co.n3soft.n3com.edit.FinalActivityEditPart;
import kr.co.n3soft.n3com.edit.FinalActorEditPart;
import kr.co.n3soft.n3com.edit.FinalClassEditPart;
import kr.co.n3soft.n3com.edit.FinalPackageEditPart;
import kr.co.n3soft.n3com.edit.FinalStrcuturedActivityEditPart;
import kr.co.n3soft.n3com.edit.FrameEditPart;
import kr.co.n3soft.n3com.edit.InterfaceEditPart;
import kr.co.n3soft.n3com.edit.LifeLineEditPart;
import kr.co.n3soft.n3com.edit.NodeEditPart;
import kr.co.n3soft.n3com.edit.StateEditPart;
import kr.co.n3soft.n3com.edit.StrcuturedStateEditPart;
import kr.co.n3soft.n3com.edit.UseCaseEditPart;
import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.component.AtomicComponentModel;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IEditorPart;

public class OpenDiagramAction extends SelectionAction {
	N3EditorDiagramModel n3EditorDiagramModel;
	N3EditorDiagramModel n3EditorPackageDiagramModel;//2008040401PKY S "패키지 오른쪽 클릭 시 OpenDiagram 메뉴 추가  여러 다이어그램이있을경우 리스트 나오도록"
	private UMLDiagramModel parent;
	UMLTreeModel treeModel = null;
	  private UMLModel child;
    public OpenDiagramAction(IEditorPart editor) {
        super(editor);
        this.setId("OpenDiagramAction");
        this.setText(N3Messages.POPUP_OPEN_DIAGRAM);
//        this.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(356)));//PKY 08070101 S 팝업 메뉴 이미지 삽입

    }
    public OpenDiagramAction(){
   	 super(null);
   }

    protected boolean calculateEnabled() {
        // TODO Workaround for Bug 82622/39369.  Should be removed when 39369 is fixed.
    	List list = ProjectManager.getInstance().getSelectNodes();
        if (list != null && list.size() == 1) {
            Object obj = list.get(0);
            if (obj instanceof LifeLineEditPart||obj instanceof FinalPackageEditPart||//2008042902 PKY S 
        			obj instanceof UseCaseEditPart||obj instanceof FinalActorEditPart
					||obj instanceof FinalClassEditPart||obj instanceof InterfaceEditPart
					||obj instanceof FinalStrcuturedActivityEditPart||obj instanceof ComponentEditPart
					||obj instanceof StateEditPart||obj instanceof StrcuturedStateEditPart
					||obj instanceof NodeEditPart||obj instanceof FinalActivityEditPart
            		||obj instanceof FrameEditPart) {//2008042902 PKY S
            	if(obj instanceof LifeLineEditPart){
            	
               
            	}else if(obj instanceof FinalPackageEditPart||//2008042902 PKY S 
            			obj instanceof UseCaseEditPart||obj instanceof FinalActorEditPart
						||obj instanceof FinalClassEditPart||obj instanceof InterfaceEditPart
						||obj instanceof FinalStrcuturedActivityEditPart||obj instanceof ComponentEditPart
						||obj instanceof StateEditPart||obj instanceof StrcuturedStateEditPart
						||obj instanceof NodeEditPart||obj instanceof FinalActivityEditPart||obj instanceof FrameEditPart){//2008042902 PKY S
            		 ArrayList diagram=ProjectManager.getInstance().getDiagramsSub();
//            		FinalPackageEditPart u = (FinalPackageEditPart)obj;
//                	FinalPackageModel um = (FinalPackageModel)u.getModel();
//                	java.util.ArrayList list1 = new java.util.ArrayList();
                	if(diagram!=null){
                		if(obj instanceof ComponentEditPart){
                			ComponentEditPart cep = (ComponentEditPart)obj;
                			if(cep.getModel() instanceof AtomicComponentModel){
                				return false;
                			}
                		}
//                	n3EditorPackageDiagramModel = um.getN3EditorDiagramModel();
//                	ArrayList diagramList=ProjectManager.getInstance().getDiagramsSub();
//                    if (diagramList != null ) {
                    	return true;
//                    }
//                	if(um.getDiagramList()!=null){
//                	ArrayList ss=um.getDiagramList();
//                	System.out.print("");
//                	}
                
                	}else{
                		return false;
                	}
                	//2008040304 PKY E 추가 
//                	List childrendList=um.getChildren();
//                	for(int j=0;j<childrendList.size();j++){
//                		Object diagramObj= childrendList.get(j);
//System.out.print("");
//                	}
                  

                    
            	}
            }
        }
        //		return false;
        return false;
    }

    public void run() {
    	boolean action=false;
//    	 N3EditorDiagramModel n3EditorDiagramModel = finalPackageModel.getN3EditorDiagramModel();
         if (n3EditorDiagramModel != null && n3EditorDiagramModel.getUMLTreeModel() != null){
        	 action=true;
             ProjectManager.getInstance().getModelBrowser().select(n3EditorDiagramModel.getUMLTreeModel());
             }
         if (n3EditorDiagramModel != null) {
        	 action=true;
             ProjectManager.getInstance().openDiagram(n3EditorDiagramModel);
         }
       //2008040401PKY S "패키지 오른쪽 클릭 시 OpenDiagram 메뉴 추가  여러 다이어그램이있을경우 리스트 나오도록"

         if( this.TEXT!=null&&action==false){
//        	 ProjectManager.getInstance().openDiagram(n3EditorPackageDiagramModel);
        	  ArrayList diagram=ProjectManager.getInstance().getDiagramsSub();
        	  for(int i = 0; i< diagram.size(); i++){
        		  N3EditorDiagramModel n3EditorDiagramModel= (N3EditorDiagramModel) diagram.get(i);
        		 OpenDiagramAction openDiagramAction=(OpenDiagramAction)this;
        		 
        		  if(n3EditorDiagramModel.getName().equals(openDiagramAction.getText())){
        			  n3EditorPackageDiagramModel=n3EditorDiagramModel;
        			  ProjectManager.getInstance().openDiagram(n3EditorPackageDiagramModel);
        	  }
         }
         }
         //2008040401PKY E "패키지 오른쪽 클릭 시 OpenDiagram 메뉴 추가  여러 다이어그램이있을경우 리스트 나오도록"
         HashMap map = new HashMap();
//         this.ssss();
//         OPRoSActivator.newProjectForComposer(map);
//         OPRoSActivator.

         



    }
    //2008040401PKY S "패키지 오른쪽 클릭 시 OpenDiagram 메뉴 추가  여러 다이어그램이있을경우 리스트 나오도록"
	public N3EditorDiagramModel getN3EditorPackageDiagramModel() {
		return n3EditorPackageDiagramModel;
	}
	public void setN3EditorPackageDiagramModel(	N3EditorDiagramModel editorPackageDiagramModel) {
		n3EditorPackageDiagramModel = editorPackageDiagramModel;
	}
    //2008040401PKY E "패키지 오른쪽 클릭 시 OpenDiagram 메뉴 추가  여러 다이어그램이있을경우 리스트 나오도록"
	
	public void ssss(){
//		IPerspectiveRegistry reg = (IPerspectiveRegistry)ProjectManager.getInstance().at.getWorkbench().getPerspectiveRegistry();
//		IPerspectiveDescriptor desc=  reg.findPerspectiveWithId(OPRoSPerspectiveFactory.ID);
//		PlatformUI.getWorkbench().getWorkbenchWindows()[0].getActivePage().setPerspective(desc);
	}
}
