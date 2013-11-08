package kr.co.n3soft.n3com.project.dialog;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;

import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;


public class PatternCellEditor extends DialogCellEditor
{

	/**
	 * @param parent
	 */
	public PatternCellEditor(Composite parent)
	{
		super(parent);
	}
	
	/**
	 * @see org.eclipse.jface.viewers.DialogCellEditor#openDialogBox(org.
eclipse.swt.widgets.Control)
	 */
	protected Object openDialogBox(Control cellEditorWindow)
	{
	
//		return new PatternModelRefDialog(cellEditorWindow.getShell()).open();//PKY 08061801 S 패턴 참조하여 작성될수 있도록 수정
		PatternModelRefDialog p = new PatternModelRefDialog(cellEditorWindow.getShell());
		p.open();
		if(!p.name.equals("")){
			return p.name;
		}
		return null;
	
	}	
	
}





