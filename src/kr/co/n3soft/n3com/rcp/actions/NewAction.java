//khg 10.4.26 ���ο� ���̾�׷�
package kr.co.n3soft.n3com.rcp.actions;

import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchWindow;

public class NewAction extends Action {

	IWorkbenchWindow workbenchWindow;
	public static   int ID=0;
	IEditorInput input = null;
	public NewAction(IWorkbenchWindow window) {
		setId("mainNew");
		this.setText("New Project");
		workbenchWindow = window;
		//setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(301)));//PKY 08070101 S �˾� �޴� �̹��� ����
		//this.setAccelerator(SWT.CTRL |'s'); //PKY 08061801 S ����Ű ����
	}
	
	public NewAction() {
		setId("mainNew");
		this.setText("New Project");
		//workbenchWindow = window;
		//setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(301)));//PKY 08070101 S �˾� �޴� �̹��� ����
	}

	public void run() {

		MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_QUESTION|SWT.YES|SWT.NO);
		dialog.setText("Message");
		dialog.setMessage("���� �Ͻðڽ��ϱ�?");
		int i=dialog.open();
		switch(i) {
		case SWT.YES:
			SaveAction sa =new SaveAction();
			sa.run();
			break;
		case SWT.NO:
			break;
		}
		
		//ProjectManager.getInstance().getUMLEditor().doSave();
		ProjectManager.getInstance().init();
		ProjectManager.getInstance().window.getActivePage().closeAllEditors(false);
		
		
	}
	

//	private String openFileDialog() {
//	FileDialog dialog = new FileDialog(workbenchWindow.getShell(), SWT.OPEN);
//	dialog.setText("shapes");
//	//dialog.setFilterExtensions(new String[] { ".shapes" });
//	return dialog.open();
//	}
}
	