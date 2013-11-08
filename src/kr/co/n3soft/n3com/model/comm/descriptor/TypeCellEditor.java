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

//	    return new PatternModelRefDialog(cellEditorWindow.getShell()).open();//PKY 08061801 S 패턴 참조하여 작성될수 있도록 수정
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
			return p.getSelectedModel().getName();//PKY 08080501 S 오퍼레이션 타입 참조 시 해당모델에 스트레오이 지정되어있을경우 해당 오퍼레이션에도 스트레오타입이 들어가는 문제 
		    else
			return "String";
	    else
		return null;

	}	
	
}
