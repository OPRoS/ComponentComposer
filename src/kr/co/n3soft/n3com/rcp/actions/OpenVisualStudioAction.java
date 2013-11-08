//khg 2010.05.03 ºñÁê¾ó ½ºÆ©µð¿À ¿ÀÇÂ
package kr.co.n3soft.n3com.rcp.actions;

import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchWindow;

public class OpenVisualStudioAction extends Action {
	IWorkbenchWindow workbenchWindow;
	public static   int ID=0;
	IEditorInput input = null;
	public OpenVisualStudioAction(IWorkbenchWindow window) {
		setId("visualStudio");
		this.setText("Open VisualStudio");
		workbenchWindow = window;
		//setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(301)));//PKY 08070101 S ÆË¾÷ ¸Þ´º ÀÌ¹ÌÁö »ðÀÔ
		//this.setAccelerator(SWT.CTRL |'s'); //PKY 08061801 S ´ÜÃàÅ° Á¤ÀÇ
	}
	
	public OpenVisualStudioAction() {
		setId("visualStudio");
		this.setText("Open VisualStudio");
		//workbenchWindow = window;
		//setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(301)));//PKY 08070101 S ÆË¾÷ ¸Þ´º ÀÌ¹ÌÁö »ðÀÔ
	}

	public void run() {
		
		Process process;
		
		String[] cmd;
		
		String Command="C:/Documents and Settings/User/My Documents/Visual Studio 2008/Projects/dddddd/dddddd/dddddd.vcproj"; 
		
		cmd = new String[] { ProjectManager.getInstance().getVsPath(),  Command };

		
		
		try{
			

			


			Runtime.getRuntime().exec(cmd);

			


			}
			catch(Exception e){
				e.printStackTrace();
				
			}
		
		
	}
	

//	private String openFileDialog() {
//	FileDialog dialog = new FileDialog(workbenchWindow.getShell(), SWT.OPEN);
//	dialog.setText("shapes");
//	//dialog.setFilterExtensions(new String[] { ".shapes" });
//	return dialog.open();
//	}
}