package kr.co.n3soft.n3com.rcp.actions;

import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.comm.FinalPackageModel;
import kr.co.n3soft.n3com.project.browser.PackageTreeModel;
import kr.co.n3soft.n3com.project.browser.RootTreeModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeParentModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchWindow;

public class WriteJavaAction extends Action {
	//PKY 08070301 S 툴바 추가작업
	IWorkbenchWindow workbenchWindow;
	public static   int ID=0;
	IEditorInput input = null;
	public WriteJavaAction(IWorkbenchWindow window) {
		setId("WriteJavaAction");
		this.setText(N3Messages.POPUP_GENERATE_CODE+" "+N3Messages.POPUP_JAVA);
		workbenchWindow = window;
		setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(308)));
		this.setEnabled(false);
	}
	
	public void run() {
		try{
			TreeSelection iSelection = (TreeSelection)ProjectManager.getInstance().getModelBrowser().getViewer().getSelection();
			Object obj=iSelection.getFirstElement();
			if(obj instanceof PackageTreeModel){
				if(((PackageTreeModel)obj).getRefModel() instanceof FinalPackageModel){
	        				UMLTreeModel treeObject = (UMLTreeModel)iSelection.getFirstElement();
	        				treeObject.writeJava();
	        		 }
					
	        	 }
	         }
		catch(Exception e){
			e.printStackTrace();
		}
	    
//	    ProjectManager.getInstance().getModelBrowser().writeModel();
		
	}
	
//	private String openFileDialog() {
//		FileDialog dialog = new FileDialog(workbenchWindow.getShell(), SWT.OPEN);
//		dialog.setText("shapes");
//		//dialog.setFilterExtensions(new String[] { ".shapes" });
//		return dialog.open();
//	}
}
