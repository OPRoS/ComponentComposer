package kr.co.n3soft.n3com.rcp.actions;

import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.project.dialog.ProjectSearchDialog;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchWindow;

public class FindAction extends Action {
	
	IWorkbenchWindow workbenchWindow;
	public static   int ID=0;
	IEditorInput input = null;
	public FindAction(IWorkbenchWindow window) {
//		window.getSelectionService().add(this);
		setId("FindAction");
		this.setActionDefinitionId("FindAction");
//		this.setAccelerator(SWT.SHIFT|'')
//		this.setAccelerator(SWT.CTRL|'f');
		this.setText(N3Messages.POPUP_FIND);
		this.setAccelerator(SWT.CTRL|'f');//PKY 08062601 S ����Ű ����
		workbenchWindow = window;
		setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(300)));//PKY 08070101 S �˾� �޴� �̹��� ����
	}
	
	public void run() {

		ProjectSearchDialog dialog = new ProjectSearchDialog(null);
		dialog.open();
		
	}
	
	private String openFileDialog() {
		FileDialog dialog = new FileDialog(workbenchWindow.getShell(), SWT.OPEN);
		dialog.setText("shapes");
		//dialog.setFilterExtensions(new String[] { ".shapes" });
		return dialog.open();
	}
}
