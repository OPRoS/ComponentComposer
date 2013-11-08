package kr.co.n3soft.n3com.rcp.actions;

import kr.co.n3soft.n3com.UMLEditor;
import kr.co.n3soft.n3com.edit.UMLEditPart;
import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.activity.ExceptionModel;
import kr.co.n3soft.n3com.model.activity.FinalActivityModel;
import kr.co.n3soft.n3com.model.activity.SubPartitonModel;
import kr.co.n3soft.n3com.model.comm.ClassifierModel;
import kr.co.n3soft.n3com.model.comm.PortModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.component.ComponentModel;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;

public class ZOrderBackAction extends SelectionAction {
	
	IWorkbenchWindow workbenchWindow;
	public static   int ID=0;
	IEditorInput input = null;
	UMLModel selectModel; 
	 public ZOrderBackAction(IEditorPart editor) {
	        super(editor);
	        this.setId("ZOrderBackAction");
	        this.setText(N3Messages.POPUP_Z_ORDER_BACK);
	        setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(319)));//PKY 08070101 S 팝업 메뉴 이미지 삽입
//	        setChecked(isChecked());
	    }
	 protected boolean calculateEnabled() {
		
	        StructuredSelection list = (StructuredSelection)this.getSelection();
	        if (list != null && list.size() == 1) {
	            Object obj = list.getFirstElement();
	            if(obj instanceof UMLEditPart){
	            UMLEditPart u = (UMLEditPart)obj;
	            selectModel= (UMLModel)u.getModel();
	          //PKY 08060201 S 시퀀스 다이어그램 앞뒤 변경하는로직 나오지 않도록
	            if(selectModel.getAcceptParentModel()!=null
								&&selectModel.getAcceptParentModel() instanceof N3EditorDiagramModel
								&&((N3EditorDiagramModel)selectModel.getAcceptParentModel()).getDiagramType()!=12
								&&!(selectModel instanceof SubPartitonModel)){
	            int i = ProjectManager.getInstance().getModelType(selectModel, -1);
	          //PKY 08091602 S
	            if(selectModel instanceof PortModel){
	            	return true;
	            }
	          //PKY 08091602 E

	            if(i>=0){
	            	return true;
	            }
	            }
	          //PKY 08060201 E 시퀀스 다이어그램 앞뒤 변경하는로직 나오지 않도록

	            }
	        }
		 return false;
	 }
	public void run() {
		try{
			UMLEditor ue	 = ProjectManager.getInstance().getUMLEditor();	
			N3EditorDiagramModel nd = ue.getDiagram();
			//PKY 08062601 S 모델 앞뒤 배치 객체가 한개일경우 에러발생 수정
			if(nd.getChildren().size()>1){
//			nd.addChild(selectModel);
			UMLModel addModel = selectModel;
			
			Point pt = addModel.getLocation();
			//PKY 08091202 S
			if(addModel instanceof ExceptionModel ){
				ExceptionModel exceptionModel = (ExceptionModel)addModel;
				for(int i = 0 ; i < exceptionModel.getPorts().size(); i ++){
					nd.removeChild((UMLModel)exceptionModel.getPorts().get(i),false);//PKY 08081101 S Zoder 이용시 선 끊기는문제 
					nd.addChild((UMLModel)exceptionModel.getPorts().get(i),0);
				}
			}else if(addModel instanceof ClassifierModel){
				ClassifierModel classifierModel = (ClassifierModel)addModel;
				for(int i = 0 ; i < classifierModel.getPorts().size(); i ++){
					nd.removeChild((UMLModel)classifierModel.getPorts().get(i),false);//PKY 08081101 S Zoder 이용시 선 끊기는문제 
					nd.addChild((UMLModel)classifierModel.getPorts().get(i),0);
				}
				if(addModel instanceof FinalActivityModel){
					FinalActivityModel umlModel = (FinalActivityModel)addModel;
					for(int i = 0 ; i < umlModel.getPm().getPartitions().size(); i ++){
						nd.removeChild((UMLModel)umlModel.getPm().getPartitions().get(i),false);//PKY 08081101 S Zoder 이용시 선 끊기는문제 
						nd.addChild((UMLModel)umlModel.getPm().getPartitions().get(i),0);
					}
				}
				
			}
			//PKY 08091202 E
			nd.removeChild(selectModel,false);//PKY 08081101 S Zoder 이용시 선 끊기는문제 
			addModel.setLocation(pt);	
			nd.addChild(addModel,0);


			}
			//PKY 08062601 E 모델 앞뒤 배치 객체가 한개일경우 에러발생 수정
//			nd.addChild(selectModel);
//			nd.getChildren().set(nd.getChildren().size()-1, selectModel);
			
			 
		}
		catch(Exception e){
			e.printStackTrace();
		}
	    
//	    ProjectManager.getInstance().getModelBrowser().writeModel();
		
	}
	
//	private String openFileDialog() {
//		FileDialog dialog = new FileDialog(workbenchWindow.getShell(), SWT.OPEN);
//		dialog.setText("shapes");
//		//dialog.setFilterExtensions(new String[] { ".shapes" });
//		return dialog.open();
//	}
}

