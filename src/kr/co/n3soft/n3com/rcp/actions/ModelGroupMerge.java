package kr.co.n3soft.n3com.rcp.actions;

import java.util.ArrayList;
import java.util.List;

import kr.co.n3soft.n3com.UMLEditor;
import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.activity.SubPartitonModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IEditorPart;

public class ModelGroupMerge extends SelectionAction {
	//PKY 08071601 S 모델 포함할때 모델 포함하려는 모델보다 뷰가 뒤에 위치할 경우 해당 모델을 제외하고 모델 추가하도록 수정

	public ModelGroupMerge(IEditorPart editor) {
		super(editor);
		this.setId("ModelGroupMerge");
		this.setText(N3Messages.POPUP_MODEL_INCLUSION);
	}

	@Override
	protected boolean calculateEnabled() {
		// TODO Auto-generated method stub

		return true;
	}
	public void run() {

		int size=0;
		Object modelManager=null;
		List list= ProjectManager.getInstance().getSelectNodes();
		java.util.ArrayList grops = new java.util.ArrayList();
		for(int i = 0; i<list.size(); i++){
			if(list.get(i) instanceof EditPart){
				if(((EditPart)list.get(i)).getModel() instanceof UMLModel){
					UMLModel model=(UMLModel)((EditPart)list.get(i)).getModel();
					if(ProjectManager.getInstance().getCopyModelType(model)!=-1||model instanceof SubPartitonModel){					
						grops.add(model);
						if(size<model.getSize().height+model.getSize().width){
							size=model.getSize().height+model.getSize().width;
							modelManager=model;
						}
					}
				}
			}
		}
		if(modelManager!=null){

//			for(int i=0; i<grops.size();i++){
//			if(modelManager==grops.get(i)){
//			grops.remove(i);
//			}

//			}
//			for(int i=0; i<grops.size();i++){
//			UMLModel umlModel =(UMLModel)grops.get(i);
//			umlModel.addGroupParent(modelManager);
//			}
//			for(int i=0; i<grops.size();i++){
//			UMLModel uml=(UMLModel)modelManager;
//			UMLModel uml1=((UMLModel)grops.get(i));
//			int width=uml.getLocation().x+uml.getSize().width;
//			int height=uml.getLocation().y+uml.getSize().height;    
//			System.out.println(width+"<"+uml1.getLocation().x+"||"+uml.getLocation().x+"<"+uml1.getLocation().x);
//			System.out.println(height+"<"+uml1.getLocation().y+"||"+uml.getLocation().y+"<"+uml1.getLocation().y);
//			if(width<uml1.getLocation().x&&uml.getLocation().x<uml1.getLocation().x){
//			grops.remove(i);
//			}
//			else if(height<uml1.getLocation().y&&uml.getLocation().y<uml1.getLocation().y){
//			grops.remove(i);
//			}    		
//			}
			remove(grops,modelManager);
			UMLEditor ue	 = ProjectManager.getInstance().getUMLEditor();	
			N3EditorDiagramModel nd = ue.getDiagram();
			for(int i = 0; i < nd.getChildren().size(); i++){
				if(nd.getChildren().get(i)==modelManager){
					removeList(grops,i);
				}
			}
			for(int i=0; i<grops.size();i++){
				UMLModel umlModel =(UMLModel)grops.get(i);
				umlModel.addGroupParent(modelManager);
			}
			if(grops.size()>0)
				//PKY 08052801 S 파티션 중복으로 저장 불러올경우 움직임이 비정상적인문제
				((UMLModel)modelManager).getUMLDataModel().setElementProperty("GROUP_CHILDRENS", grops);
			if(((UMLModel)modelManager).getUMLDataModel().getElementProperty("GROUP_PARENTS")!=null){
				ArrayList arryItem=(ArrayList)((UMLModel)modelManager).getUMLDataModel().getElementProperty("GROUP_PARENTS");
				for(int j=0;j<arryItem.size();j++){
					if(((UMLModel)arryItem.get(j)).getUMLDataModel().getElementProperty("GROUP_CHILDRENS")!=null){
						ArrayList arry=(ArrayList)((UMLModel)arryItem.get(j)).getUMLDataModel().getElementProperty("GROUP_CHILDRENS");
						for(int b=0;b<grops.size();b++){
							UMLModel umlModel =(UMLModel)grops.get(b);
							umlModel.addGroupParent((UMLModel)arryItem.get(j));
							arry.add(grops.get(b));	
						}
						((UMLModel)arryItem.get(j)).getUMLDataModel().setElementProperty("GROUP_CHILDRENS", arry);

					}
				}
			}
			//PKY 08052801 E 파티션 중복으로 저장 불러올경우 움직임이 비정상적인문제

		}

	}
	public void remove(ArrayList grops,Object modelManager){
		for(int i=0; i<grops.size();i++){

			UMLModel uml=(UMLModel)modelManager;
			UMLModel uml1=((UMLModel)grops.get(i));
			int width=uml.getLocation().x+uml.getSize().width;
			int height=uml.getLocation().y+uml.getSize().height;    
			System.out.println(width+"<"+uml1.getLocation().x+"||"+uml.getLocation().x+"<"+uml1.getLocation().x);
			System.out.println(height+"<"+uml1.getLocation().y+"||"+uml.getLocation().y+"<"+uml1.getLocation().y);
			if(width<uml1.getLocation().x&&uml.getLocation().x<uml1.getLocation().x){
				grops.remove(i);
				i=0;
				remove(grops,modelManager);

			}
			else if(height<uml1.getLocation().y&&uml.getLocation().y<uml1.getLocation().y){
				grops.remove(i);
				i=0;
				remove(grops,modelManager);
			}
			if(modelManager==grops.get(i)){

				grops.remove(i);
				i=0;
				remove(grops,modelManager);
			}

//			if(grops.get(i)instanceof SubPartitonModel){
//			if(((UMLModel)grops.get(i)).getUMLDataModel().getElementProperty("GROUP_PARENTS")!=null){
//			ArrayList array=(ArrayList)((UMLModel)grops.get(i)).getUMLDataModel().getElementProperty("GROUP_PARENTS");
//			for(int j=0; j<array.size();j++){
//			if(array.get(j)!=modelManager){
//			grops.remove(i);
//			i=0;
//			remove(grops,modelManager);				
//			}
//			}
//			}

//			}


		}
	}
	public void removeList(ArrayList grops,int i){
		UMLEditor ue	 = ProjectManager.getInstance().getUMLEditor();	
		N3EditorDiagramModel nd = ue.getDiagram();
		for(int k = 0; k < nd.getChildren().size(); k++){
			for(int j = 0; j < grops.size(); j++){			
				if(grops.get(j)==nd.getChildren().get(k)){
				if(i>k){
					grops.remove(j);
					removeList(grops,i);
				}
				}
			}
		}
	}
}