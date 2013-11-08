package comp.action;

import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;



public class MRunAction implements IWorkbenchWindowActionDelegate {
	
	private IWorkbenchWindow window;

	
	public void run(IAction action){
		String path = ProjectManager.getInstance().getMPath();
		MessageDialog.openInformation(null,"bmi",path);
		System.out.println("dddd");
	
	
		

	}

	public void dispose(){

	}

	public void init(IWorkbenchWindow window){
		this.window = window;
	}

	public void selectionChanged(IAction action, ISelection selection){

	}

}
