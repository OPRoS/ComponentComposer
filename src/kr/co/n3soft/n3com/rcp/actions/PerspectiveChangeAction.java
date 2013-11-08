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

//20110816SDM PerspectiveChangeActionŬ���� �߰�
//PerspectiveChangeActionŬ����>>������ ���̾�׷� ���ý� ����������(uuu.perspective)�� ��ȯ �� �����Ϳ� ����ȭ
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
			//110901 SDM S �������� �̹� ��ȯ �Ǿ� ������ ����ȭ �� �۽���Ƽ�� ��ȯ�� ���� ���� �ʵ��� IF�� �߰�
			if(!((WorkbenchWindow)PlatformUI.getWorkbench().getActiveWorkbenchWindow()).getActivePage().getPerspective().getId().equals("uuu.perspective")){
				 PlatformUI.getWorkbench().showPerspective("uuu.perspective", ProjectManager.getInstance().window);
				//110823 SDM �ڵ�����ȭ ���� �� ����ȭ �κ� �ҽ� ����
				 RootCmpEdtTreeModel editorModel = ProjectManager.getInstance().getModelBrowser().getRcet();
				 
				 List listTempCmpEdt = new ArrayList();
					listTempCmpEdt = ((RootCmpEdtTreeModel) editorModel).getChildrenList();
	
					Iterator <StrcuturedPackageTreeModel>  itCmpEdt = listTempCmpEdt.iterator();
					StrcuturedPackageTreeModel temp;
					
					while(itCmpEdt.hasNext()){
						temp = itCmpEdt.next();
						
						ProjectManager.getInstance().getModelBrowser().allSync(temp);	//110822 SDM ����ȭ �κ� ���� ��.
					}
				 //110823 SDM �ڵ�����ȭ ����
			}
			//110901 SDM E �������� �̹� ��ȯ �Ǿ� ������ ����ȭ �� �۽���Ƽ�� ��ȯ�� ���� ���� �ʵ��� IF�� �߰�
			 
			return true;
		}
		catch(Exception e){
			return false;
		}
	}
}
	