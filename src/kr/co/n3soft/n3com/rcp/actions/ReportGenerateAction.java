package kr.co.n3soft.n3com.rcp.actions;

import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.project.browser.ModelBrowser;
import kr.co.n3soft.n3com.project.browser.UMLTreeParentModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;


import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchWindow;

/**
 * 20080903 KDI s

 * @author ±Ëµø¿œ 
 *
 */
public class ReportGenerateAction extends Action {

	IWorkbenchWindow workbenchWindow;
		
	public ReportGenerateAction(IWorkbenchWindow window) {
		setId("ReportGenerateAction");
		this.setText(N3Messages.REPORT_GENERATE_MENU);
		workbenchWindow = window;
		setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(327)));
//		this.setAccelerator(SWT.CTRL|'n');
	}

	public void run() {


////		TreeSelection iSelection = (TreeSelection)ProjectManager.getInstance().getModelBrowser().getViewer().getSelection();
////		UMLTreeParentModel treeObject = (UMLTreeParentModel)ProjectManager.getInstance().getModelBrowser().getRoot();
////		ReportDialog rwTest = new ReportDialog(ProjectManager.getInstance().window.getShell());                
////		rwTest.open();
//		
//		ReportMainDialog rw = new ReportMainDialog(ProjectManager.getInstance().window.getShell());                
//		rw.open();
	
	}
}
