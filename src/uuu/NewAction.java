package uuu;

import kr.co.n3soft.n3com.UMLEditor;

import org.eclipse.core.runtime.Path;
import org.eclipse.gef.examples.shapes.LogicEditorInput;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;

public class NewAction extends Action {
	
	IWorkbenchWindow workbenchWindow;
	public static   int ID=0;
	IEditorInput input = null;
	public NewAction(IWorkbenchWindow window) {
		setId("New Shapes");
		setText("New");
		workbenchWindow = window;
		setImageDescriptor(uuu.Activator.getImageDescriptor("/icons/newproject.gif"));
	}
	
	public void run() {
//		String path = openFileDialog();
//		if (path != null) {
//			IEditorInput input = new ShapesEditorInput(new Path("쓰임새도"+ID));
//		if(input==null)
		input = new LogicEditorInput(new Path("쓰임새도"+ID));
		
//		input = new N3EditorInput(new Path("쓰임새도"+ID));
		
		//		ClassEditorInput
//		IEditorInput input = new ClassEditorInput(new Path("쓰임새도"+ID));
			try {
//				workbenchWindow.getActivePage().openEditor(
//						input, 
//						ClassDiagramEditor.ID, 
//						true);
				
				workbenchWindow.getActivePage().openEditor(
						input, 
						UMLEditor.ID, 
						true);
//				
//				workbenchWindow.getActivePage().openEditor(
//						input, 
//						LogicEditor.ID, 
//						true);
//				workbenchWindow.getActivePage().openEditor(
//						input, 
//						ShapesEditor.ID, 
//						true);
//				ID++;
			} catch (PartInitException e) {
				e.printStackTrace();
			}
//		}
		
	}
	
	private String openFileDialog() {
		FileDialog dialog = new FileDialog(workbenchWindow.getShell(), SWT.OPEN);
		dialog.setText("shapes");
		//dialog.setFilterExtensions(new String[] { ".shapes" });
		return dialog.open();
	}
}