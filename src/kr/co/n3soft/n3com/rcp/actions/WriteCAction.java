package kr.co.n3soft.n3com.rcp.actions;

import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.comm.FinalPackageModel;
import kr.co.n3soft.n3com.project.browser.PackageTreeModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeParentModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IWorkbenchWindow;

public class WriteCAction  extends Action{

	public WriteCAction(IWorkbenchWindow window) {
        this.setId("ADDPackage");
        this.setText(N3Messages.POPUP_GENERATE_CODE+" C++");
        setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(314)));//PKY 08070101 S ÆË¾÷ ¸Þ´º ÀÌ¹ÌÁö »ðÀÔ
        this.setEnabled(false);

	}
	protected WriteCAction() {
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
				treeObject.writeH();
			}
		}


	}

}
