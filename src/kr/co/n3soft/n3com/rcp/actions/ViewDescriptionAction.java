package kr.co.n3soft.n3com.rcp.actions;

import kr.co.n3soft.n3com.edit.LineEditPart;
import kr.co.n3soft.n3com.model.comm.LineModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.gef.EditPart;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;

import uuu.views.DescriptionView;

/**
 * 20080728 KDI s 디스크립션 뷰 액션 클래스 추가
 * @author Administrator
 *
 */
public class ViewDescriptionAction extends Action {

	IWorkbenchWindow workbenchWindow;
	public ViewDescriptionAction(IWorkbenchWindow window) {
		setId("ViewDescriptionAction");
		this.setText("Description");
		workbenchWindow = window;
		setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(335)));
	}

	protected boolean calculateEnabled() {	    	
		return true;	      
	}

	public void run() {
		try{
			IViewPart iv = this.workbenchWindow.getActivePage().findView("uuu.views.DescriptionView");
			if(iv==null){
				workbenchWindow.getActivePage().showView("uuu.views.DescriptionView");
				DescriptionView descriptionView = ProjectManager.getInstance().getDescriptionView();
				if(descriptionView != null){
					java.util.List list = ProjectManager.getInstance().getSelectNodes();
					if(list != null && list.size()>0){
						if(list.get(0) instanceof EditPart){
							Object obj = ((EditPart)list.get(0)).getModel();
							if(obj instanceof UMLModel){
								descriptionView.setDescriptionViwe(((UMLModel)obj).getDesc());
							}
						}
						else if(list.get(0) instanceof LineEditPart){
							Object obj = ((LineEditPart)list.get(0)).getModel();
							if(obj instanceof LineModel){
								String str = "";
					   			if(((LineModel)obj).getDetailProp().get(((LineModel)obj).ID_DESCRIPTORS)==null){
					   				str = "";
					   			}
					   			str = (String)((LineModel)obj).getDetailProp().get(((LineModel)obj).ID_DESCRIPTORS);
					   			if(str != null)
					   				descriptionView.setDescriptionViwe(str);
					   			else
					   				descriptionView.setDescriptionViwe("");
							}				   			
						}
					}
					//셀렉트 노드 없을경우 트리 포커스 노드로 셋..
					else{
						UMLTreeModel uMLTreeModel = ProjectManager.getInstance().getModelBrowser().getUMLTreeModelSelected();
						if(uMLTreeModel != null){
							if(uMLTreeModel.getRefModel() instanceof UMLModel){
								descriptionView.setDescriptionViwe(((UMLModel)uMLTreeModel.getRefModel()).getDesc());
							}
						}
					}
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}


	}
	public IWorkbenchWindow getWorkbenchWindow() {
		return workbenchWindow;
	}
	public void setWorkbenchWindow(IWorkbenchWindow workbenchWindow) {
		this.workbenchWindow = workbenchWindow;
	}
}