package kr.co.n3soft.n3com.rcp.actions;

import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.project.dialog.PatternDialog;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchWindow;

public class OpenApplyPatternAction extends Action {
	
	IWorkbenchWindow workbenchWindow;
	public static   int ID=0;
	IEditorInput input = null;
	public OpenApplyPatternAction(IWorkbenchWindow window) {
		setId("OpenApplyPatternAction");
		this.setText(N3Messages.MENU_APPLY_PATTERN);
		workbenchWindow = window;
		setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(207)));//PKY 08070101 S ÆË¾÷ ¸Þ´º ÀÌ¹ÌÁö »ðÀÔ
	}
	
	public void run() {
		try{

			PatternDialog pd = new PatternDialog(workbenchWindow.getShell());
			pd.open();
			 
		}
		catch(Exception e){
			e.printStackTrace();
		}
	    

		
	}
	

}
