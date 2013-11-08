package kr.co.n3soft.n3com.model.comm.descriptor;

import kr.co.n3soft.n3com.project.dialog.TypeDialog;

import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class TypeCellEditor extends DialogCellEditor
{

	/**
	 * @param parent
	 */
	public TypeCellEditor(Composite parent)
	{
		super(parent);
	}
	
	/**
	 * @see org.eclipse.jface.viewers.DialogCellEditor#openDialogBox(org.
eclipse.swt.widgets.Control)
	 */
	protected Object openDialogBox(Control cellEditorWindow){

//	    return new PatternModelRefDialog(cellEditorWindow.getShell()).open();//PKY 08061801 S ���� �����Ͽ� �ۼ��ɼ� �ֵ��� ����
	    TypeDialog p = new TypeDialog(cellEditorWindow.getShell());
	    p.open();
	    if(p.isCheckOkButton()==true)
		if(p.isCheckBoolean())
		    if(!p.getName().equals(""))
			return p.getName();
		    else
			return "String";
		else
		    if(!p.getSelectedModel().getName().equals(""))
			return p.getSelectedModel().getName();//PKY 08080501 S ���۷��̼� Ÿ�� ���� �� �ش�𵨿� ��Ʈ������ �����Ǿ�������� �ش� ���۷��̼ǿ��� ��Ʈ����Ÿ���� ���� ���� 
		    else
			return "String";
	    else
		return null;

	}	
	
}
