package kr.co.n3soft.n3com.parser;

import kr.co.n3soft.n3com.model.comm.ClassifierModel;
import kr.co.n3soft.n3com.model.comm.ClassifierModelTextAttach;
import kr.co.n3soft.n3com.model.comm.TypeRefModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.usecase.FinalActorModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;
//20080811IJS ½ÃÀÛ
public class RefLinkModel {
	public String id = "";
	public String refId = "";
	public boolean isLink = false;

	public void link(){
		if(id!=null && !id.trim().equals("") &&
				refId!=null && !refId.trim().equals("")){
			Object obj = null;
			Object selectedObj = null;
			if(!this.isLink){
				obj = ModelManager.getInstance().getModelStore().get(refId);
				selectedObj = ModelManager.getInstance().getModelStore().get(id);
			}
			else{
				obj =  (UMLTreeModel)ProjectManager.getInstance().getModelBrowser().searchId(refId);
				selectedObj  = (UMLTreeModel)ProjectManager.getInstance().getModelBrowser().searchId(id);
//				if(objut!=null){
//				obj = objut.getRefModel();
//				}
//				if(selectedObjut!=null){
//				selectedObj = selectedObjut.getRefModel();
//				}
			}
			if(obj!=null && selectedObj!=null){

				UMLModel um = (UMLModel)((UMLTreeModel)obj).getRefModel();
				UMLModel selectedModel = (UMLModel)((UMLTreeModel)selectedObj).getRefModel();

				if(selectedModel instanceof ClassifierModel){
					ClassifierModel umModel = (ClassifierModel)selectedModel;



					Object c = umModel.getClassModel();
					if (c instanceof TypeRefModel) {
						TypeRefModel typeRefModel = (TypeRefModel)c;
						typeRefModel.setTypeRef(um);
						Object obj1 = um;
						if (obj1 instanceof ClassifierModel) {
							try {
								ClassifierModel um1 = (ClassifierModel)obj1;
								um1.getClassModel().addUpdateListener(typeRefModel);
							}
							catch (Exception e) {
								e.printStackTrace();
							}
						}
						else if(obj1 instanceof ClassifierModelTextAttach) {
							try {
								ClassifierModelTextAttach um1 = (ClassifierModelTextAttach)obj1;
								um1.getClassModel().addUpdateListener(typeRefModel);
							}
							catch (Exception e) {
								e.printStackTrace();
							}
						}
						else if(obj1 instanceof FinalActorModel) {
							System.out.println("ddddd7777");
							try {
								FinalActorModel um1 = (FinalActorModel)obj1;
								um1.addUpdateListener(typeRefModel);
							}
							catch (Exception e) {
								e.printStackTrace();
							}
						}
					}//-->
				}
				
			}
			else{
				this.isLink = true;
				TeamProjectManager.getInstance().getNoRefLinkMap().add(this);
			}
		}
	}
	//20080811IJS ³¡

}
