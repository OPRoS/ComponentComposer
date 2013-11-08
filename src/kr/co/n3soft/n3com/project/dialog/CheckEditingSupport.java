package kr.co.n3soft.n3com.project.dialog;

import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;

public class CheckEditingSupport extends EditingSupport {
	private CellEditor editor;
	private int column;
	OpenMonitoringDialog opd = null;

	public CheckEditingSupport(ColumnViewer viewer, int column,OpenMonitoringDialog opd1 ) {
		super(viewer);
		opd = opd1;
		switch (column) {
//		case 2:
//			editor = new ComboBoxCellEditor(((TableViewer) viewer).getTable(),
//					gender);
//			break;
		case 0:
			editor = new CheckboxCellEditor(null, SWT.CHECK );
			break;
//		default:
//			editor = new TextCellEditor(((TableViewer) viewer).getTable());
		}
		this.column = column;
	}


	protected boolean canEdit(Object element) {
		if(this.opd.isConnectm){
		MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_WARNING);
	dialog.setText("Message");
	dialog.setMessage("Can not be checked in the run state"); //KDI 080908 0002

		dialog.open();
		return false;
		}
		else
		return true;
	}


	protected CellEditor getCellEditor(Object element) {
		return editor;
	}


	protected Object getValue(Object element) {
		DetailPropertyTableItem dt = (DetailPropertyTableItem) element;

		switch (this.column) {
		case 0:
			return dt.isCheck;
		case 1:
			return dt.tapName;
		case 2:
			return dt.sTagType;
		case 3:
			return dt.desc;
		default:
			break;
		}
		return null;
	}


	protected void setValue(Object element, Object value) {
		DetailPropertyTableItem dt = (DetailPropertyTableItem) element;

		switch (this.column) {
//		case 0:
//			pers.setFirstName(String.valueOf(value));
//			break;
//		case 1:
//			pers.setLastName(String.valueOf(value));
//			break;
//		case 2:
//			if (((Integer) value) == 0) {
//				pers.setGender("male");
//			} else {
//				pers.setGender("female");
//			}
//			break;
		case 0:
			dt.isCheck=  (Boolean) value;
			break;
		default:
			break;
		}

		getViewer().update(element, null);
	}

}
