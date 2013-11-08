package kr.co.n3soft.n3com.rcp.actions;

import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.comm.FinalPackageModel;
import kr.co.n3soft.n3com.project.browser.PackageTreeModel;
import kr.co.n3soft.n3com.project.browser.RootTreeModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeParentModel;
import kr.co.n3soft.n3com.project.dialog.NewDiagramDialog;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IWorkbenchWindow;
//PKY 08070301 S 툴바 추가작업
public class ADDPackageAction extends Action {

	public ADDPackageAction(IWorkbenchWindow window) {
        this.setId("ADDPackage");
        this.setText(N3Messages.POPUP_PACKAGE_ADD);
        setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(315)));//PKY 08070101 S 팝업 메뉴 이미지 삽입      
        this.setEnabled(false);
	}
	protected ADDPackageAction() {
		System.out.print("");
	}
	
	public void setEnable(boolean enable) {
		this.setEnabled(enable);
	}
	
	 public void run() {
		 
		 try{
			
			TreeSelection iSelection = (TreeSelection)ProjectManager.getInstance().getModelBrowser().getViewer().getSelection();
			
			Object obj=iSelection.getFirstElement();
			if(obj instanceof PackageTreeModel){
				if(((PackageTreeModel)obj).getRefModel() instanceof FinalPackageModel){
        		 UMLTreeParentModel treeObject = (UMLTreeParentModel)iSelection.getFirstElement();

                 InputDialog inputDialog = new InputDialog(ProjectManager.getInstance().window.getShell(),
                     N3Messages.POPUP_PACKAGE_ADD,  N3Messages.POPUP_NAME, "", null);//PKY 08070101 S 영어로 변경
                 inputDialog.open();
                 int retCode = inputDialog.getReturnCode();
                 if (retCode == 0) {
                     String name = inputDialog.getValue();
                     ProjectManager.getInstance().addUMLModel(name, treeObject, null, 0,-1);
                 }
             
        	 }
			}else if (obj instanceof RootTreeModel){			
					UMLTreeParentModel treeObject = (UMLTreeParentModel)iSelection.getFirstElement();

	                 InputDialog inputDialog = new InputDialog(ProjectManager.getInstance().window.getShell(),
	                     N3Messages.POPUP_PACKAGE_ADD,  N3Messages.POPUP_NAME, "", null);//PKY 08070101 S 영어로 변경
	                 inputDialog.open();
	                 int retCode = inputDialog.getReturnCode();
	                 if (retCode == 0) {
	                     String name = inputDialog.getValue();
	                     ProjectManager.getInstance().addUMLModel(name, treeObject, null, 0,-1);

	                 }

			}
		 }
		 catch(Exception e){
			 e.printStackTrace();
		 }
		 
		 
	 }

}
