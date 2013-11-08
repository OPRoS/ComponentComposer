package kr.co.n3soft.n3com.model.comm.descriptor;

import kr.co.n3soft.n3com.model.umlclass.OperationEditableTableItem;
import kr.co.n3soft.n3com.project.dialog.ParameterDialog;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

/**
 * 20080721 KDI s 파라미터 셀 에디터 클래스 생성
 * @author Administrator
 *
 */
public class ParameterCellEditor extends DialogCellEditor
{
	
//	private OperationEditableTableItem item = null;

	/**
	 * @param parent
	 */
	public ParameterCellEditor(Composite parent)
	{
		super(parent);
	}
	
	/**
	 * @see org.eclipse.jface.viewers.DialogCellEditor#openDialogBox(org.
eclipse.swt.widgets.Control)
	 */
	protected Object openDialogBox(Control cellEditorWindow)
	{

		ParameterDialog p = new ParameterDialog(cellEditorWindow.getShell());
		Table table = null;
		if(cellEditorWindow.getParent() instanceof Table){
			table = (Table)cellEditorWindow.getParent();
			TableItem tableItem = (TableItem)table.getItem(table.getSelectionIndex());
			p.selectTableItem = (OperationEditableTableItem)tableItem.getData();
		}
		int btn = p.open();
		switch(btn) {
		case IDialogConstants.OK_ID:
//			if(table != null){
//				table.
//			}
			return p.selectTableItem.getParams();			
		case SWT.CANCEL:
			return null;
		}
		return null;
	
	}	
	
//	public void setOperationItem(OperationEditableTableItem obj){
//		item = obj;
//	}
//	
//	public OperationEditableTableItem getOperationItem(){
//		return item;
//	}
	
}
