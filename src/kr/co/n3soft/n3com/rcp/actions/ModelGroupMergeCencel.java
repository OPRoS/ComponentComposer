package kr.co.n3soft.n3com.rcp.actions;

import java.util.ArrayList;
import java.util.List;

import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.activity.FinalActivityModel;
import kr.co.n3soft.n3com.model.activity.FinalStrcuturedActivityModel;
import kr.co.n3soft.n3com.model.activity.SubPartitonModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IEditorPart;

public class ModelGroupMergeCencel extends SelectionAction {
	//PKY 08060201 S GRoup 포함형태로 변경
	//PKY 08052601 S POPUP에 이름이없을경우 공란으로 표시되던문제 수정(포함메뉴)

	public ModelGroupMergeCencel(IEditorPart editor) {
		super(editor);
		this.setId("ModelGroupMergeCencel");
		this.setText(N3Messages.POPUP_MODEL_INCLUSION_CENCEL);
	}

	@Override
	protected boolean calculateEnabled() {
		// TODO Auto-generated method stub
		int size=0;
		Object modelManager=null;
		List list= ProjectManager.getInstance().getSelectNodes();
		java.util.ArrayList grops = new java.util.ArrayList();
		for(int i = 0; i<list.size(); i++){
			if(list.get(i) instanceof EditPart){
				if(((EditPart)list.get(i)).getModel() instanceof UMLModel){
					UMLModel model=(UMLModel)((EditPart)list.get(i)).getModel();
					if(ProjectManager.getInstance().getCopyModelType(model)!=-1){
						if(model.getUMLDataModel().getElementProperty("GROUP_CHILDRENS")!=null){
							//PKY 08052601 S 파티션만 포함되어있을경우 포함해제 뜨지 않도록 수정
							ArrayList arrayItem=(ArrayList)model.getUMLDataModel().getElementProperty("GROUP_CHILDRENS");
							for(int k=0;k<arrayItem.size();k++){
								if(!(arrayItem.get(k) instanceof SubPartitonModel)){
									grops.add(model);
								}
							}
							//PKY 08052601 E 파티션만 포함되어있을경우 포함해제 뜨지 않도록 수정

						}
						else if(model.getUMLDataModel().getElementProperty("GROUP_PARENTS")!=null){
							//PKY 08052601 S 파티션만 포함되어있을경우 포함해제 뜨지 않도록 수정
							if(!(model instanceof SubPartitonModel)){
								grops.add(model);
							}
							//PKY 08052601 E 파티션만 포함되어있을경우 포함해제 뜨지 않도록 수정
						}

					}
				}
			}
		}
		if(grops.size()>0){
			return true;
		}
		return false;
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
					if(ProjectManager.getInstance().getCopyModelType(model)!=-1){

						grops.add(model);

					}
				}
			}
		}	
		for(int i=0; i<grops.size();i++){
			UMLModel umlModel =(UMLModel)grops.get(i);
			if(umlModel instanceof FinalActivityModel||umlModel instanceof FinalStrcuturedActivityModel){
				if(umlModel.getUMLDataModel().getElementProperty("GROUP_CHILDRENS")!=null){
					ArrayList arrayList=(ArrayList)umlModel.getUMLDataModel().getElementProperty("GROUP_CHILDRENS");
					for(int k=0;k<arrayList.size();k++){
						//PKY 08052601 S 엑티비티위에 엑티비티 그리고 파티션 있을경우 파티션해제안되는문제 수정
						if((arrayList.get(k) instanceof SubPartitonModel)){
							if(((UMLModel)arrayList.get(k)).getUMLDataModel().getElementProperty("GROUP_PARENTS")!=null){
								ArrayList parents=(ArrayList)((UMLModel)arrayList.get(k)).getUMLDataModel().getElementProperty("GROUP_PARENTS");
								for(int b=0;b<parents.size();b++){
									if(parents.get(b)!=umlModel){
										arrayList.remove(k);
									}
								}
							}
							//PKY 08052601 E 엑티비티위에 엑티비티 그리고 파티션 있을경우 파티션해제안되는문제 수정
						}else{
							arrayList.remove(k);
							umlModel.getUMLDataModel().setElementProperty("GROUP_CHILDRENS",arrayList);
							run();
						}
					}
				}
			}
			//PKY 08052601 S 포함 해제 할 시 Partition 수정 
			else if(umlModel instanceof SubPartitonModel){

			}
			//PKY 08052601 E 포함 해제 할 시 Partition 수정 

			else{
				umlModel.getUMLDataModel().removeProperty("GROUP_CHILDRENS");
				if(umlModel.getUMLDataModel().getElementProperty("GROUP_PARENTS")!=null){
					ArrayList  obj=(ArrayList)umlModel.getUMLDataModel().getElementProperty("GROUP_PARENTS");
					for(int k=0;k<obj.size();k++){
						if(obj.get(k) instanceof UMLModel){
							UMLModel model=(UMLModel)obj.get(k);
							if(model.getUMLDataModel().getElementProperty("GROUP_CHILDRENS")!=null){
								boolean check=false;
								ArrayList arrayItem=(ArrayList)model.getUMLDataModel().getElementProperty("GROUP_CHILDRENS");
								for(int b=0;b<arrayItem.size();b++){
									if(check==false)
									if(arrayItem.get(b)==umlModel){
										arrayItem.remove(b);
										check=true;
									}									
								}
								model.getUMLDataModel().setElementProperty("GROUP_CHILDRENS",arrayItem);
							}
						}
					}
				}
				umlModel.getUMLDataModel().removeProperty("GROUP_PARENTS");
			}
		}





	}

}
