package kr.co.n3soft.n3com.rcp.actions;

import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.comm.FinalPackageModel;
import kr.co.n3soft.n3com.project.browser.PackageTreeModel;
import kr.co.n3soft.n3com.project.browser.RootTreeModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeParentModel;
import kr.co.n3soft.n3com.project.dialog.NewDiagramDialog;
import kr.co.n3soft.n3com.project.dialog.NewModelDialog;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IWorkbenchWindow;
//PKY 08070301 S 툴바 추가작업
public class ADDModelAction extends Action {

	public ADDModelAction(IWorkbenchWindow window) {
        this.setId("ADDPackage");
        this.setText(N3Messages.POPUP_MODEL_ADD);
        setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(316)));//PKY 08070101 S 팝업 메뉴 이미지 삽입
        this.setEnabled(false);

	}
	protected ADDModelAction() {
		System.out.print("");
	}
	
	public void setEnable(boolean enable) {
		this.setEnabled(enable);
	}
	
	 public void run() {

			TreeSelection iSelection = (TreeSelection)ProjectManager.getInstance().getModelBrowser().getViewer().getSelection();
			Object obj=iSelection.getFirstElement();
			if(obj instanceof PackageTreeModel){
				if(((PackageTreeModel)obj).getRefModel() instanceof FinalPackageModel){
        		 UMLTreeParentModel treeObject = (UMLTreeParentModel)iSelection.getFirstElement();
        		 NewModelDialog newModelDialog = new NewModelDialog(ProjectManager.getInstance().window.getShell());
                 newModelDialog.open();
                 String name = newModelDialog.getName();
                 //				newDiagramDialog.getShell().setSize(500, 500);
                 if (newModelDialog.isOK)
                     ProjectManager.getInstance().addUMLModel(name, treeObject, null, newModelDialog.getModelType(),-1);
             }
         }
		 
		 
	 }

}
