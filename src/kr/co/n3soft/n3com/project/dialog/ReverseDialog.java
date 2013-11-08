package kr.co.n3soft.n3com.project.dialog;

import kr.co.n3soft.n3com.file.browser.FileBrowserComposite;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.DirectoryDialog;



public class ReverseDialog extends Dialog {
	
	public ReverseDialog(Shell parentShell) {
		super(parentShell);
		this.setShellStyle(SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE);
		
	}
	
	protected Control createDialogArea(Composite parent) {
		Composite comp = (Composite)super.createDialogArea(parent);
//		FileBrowserComposite fc = new FileBrowserComposite(comp);
//		DirectoryDialog fd = new  DirectoryDialog(comp,SWT.OPEN);
		
		return comp;
		
	}
}
