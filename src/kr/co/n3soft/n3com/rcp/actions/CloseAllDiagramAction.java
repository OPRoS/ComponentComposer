package kr.co.n3soft.n3com.rcp.actions;

import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchWindow;

public class CloseAllDiagramAction extends Action {
	
	IWorkbenchWindow workbenchWindow;
	public static   int ID=0;
	IEditorInput input = null;
	public CloseAllDiagramAction(IWorkbenchWindow window) {
		setId("CloseAllDiagramAction");
		this.setText("Close &All Diagrams");
		workbenchWindow = window;
		setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(312)));//PKY 08070101 S ÆË¾÷ ¸Þ´º ÀÌ¹ÌÁö »ðÀÔ

	}
	
	public void run() {

		workbenchWindow.getActivePage().closeAllEditors(false);
		try{
//		workbenchWindow.getActivePage().sh("uuu.views.SampleView4");
//		ProjectManager.getInstance().getModelBrowser().reflushRoot(ProjectManager.getInstance().getRoot());
//		ProjectManager.getInstance().getModelBrowser().setIvewSite(ProjectManager.getInstance().getIvewSite());
		}
		catch(Exception e){
			e.printStackTrace();
		}
		}
	
	
}

