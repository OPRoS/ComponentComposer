package kr.co.n3soft.n3com.rcp.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import kr.co.n3soft.n3com.model.comm.StrcuturedPackageTreeModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.project.browser.RootCmpEdtTreeModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPerspectiveRegistry;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchWindow;
import org.eclipse.ui.progress.IProgressService;

//20110816SDM PerspectiveChangeAction클래스 추가
//PerspectiveChangeAction클래스>>컴포저 다이얼그램 선택시 컴포저도구(uuu.perspective)로 전환 및 에디터와 동기화
public class PerspectiveChangeAction extends SelectionAction {
	
	IWorkbenchWindow workbenchWindow;
	UMLModel selectModel; 

	public PerspectiveChangeAction(IEditorPart editor) {
		super(editor);
		this.setId("PerspectiveChange");
		// TODO Auto-generated constructor stub
	}

		
	
	protected boolean calculateEnabled() {
		try{
			//110901 SDM S 컴포저로 이미 변환 되어 있을땐 동기화 및 퍼스팩티브 변환을 실행 하지 않도록 IF문 추가
			if(!((WorkbenchWindow)PlatformUI.getWorkbench().getActiveWorkbenchWindow()).getActivePage().getPerspective().getId().equals("uuu.perspective")){
				 PlatformUI.getWorkbench().showPerspective("uuu.perspective", ProjectManager.getInstance().window);
				//110823 SDM 자동동기화 복구 및 동기화 부분 소스 수정
				 RootCmpEdtTreeModel editorModel = ProjectManager.getInstance().getModelBrowser().getRcet();
				 
				 List listTempCmpEdt = new ArrayList();
					listTempCmpEdt = ((RootCmpEdtTreeModel) editorModel).getChildrenList();
	
					Iterator <StrcuturedPackageTreeModel>  itCmpEdt = listTempCmpEdt.iterator();
					StrcuturedPackageTreeModel temp;
					
					while(itCmpEdt.hasNext()){
						temp = itCmpEdt.next();
						
						ProjectManager.getInstance().getModelBrowser().allSync(temp);	//110822 SDM 동기화 부분 따로 뺌.
					}
				 //110823 SDM 자동동기화 복구
			}
			//110901 SDM E 컴포저로 이미 변환 되어 있을땐 동기화 및 퍼스팩티브 변환을 실행 하지 않도록 IF문 추가
			 
			return true;
		}
		catch(Exception e){
			return false;
		}
	}
}
	