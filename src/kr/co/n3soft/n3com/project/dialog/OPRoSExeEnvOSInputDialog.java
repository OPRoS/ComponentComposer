package kr.co.n3soft.n3com.project.dialog;

import kr.co.n3soft.n3com.OPRoSStrings;
import kr.co.n3soft.n3com.OPRoSUtil;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class OPRoSExeEnvOSInputDialog extends Dialog {
	private String osName;
	private String osVersion;
	private Text osNameText;
	private Text osVersionText;
	public OPRoSExeEnvOSInputDialog(Shell shell) {
		super(shell);
		osName=OPRoSStrings.getString("DefaultOSName");
		osVersion=OPRoSStrings.getString("DefaultOSVersion");
	}
	
	protected Control createDialogArea(Composite parent) {
		Composite container = OPRoSUtil.createComposite(parent, SWT.NONE, 2, GridData.HORIZONTAL_ALIGN_BEGINNING);//(Composite) super.createDialogArea(parent);
        
		OPRoSUtil.createLabel(container, OPRoSStrings.getString("OSInputDlgNameLabel"), SWT.NONE, 1, 0, 1, 0, GridData.BEGINNING);
		osNameText=OPRoSUtil.createText(container, SWT.BORDER|SWT.SINGLE, 1, 0, 1, 0, 200, 0, GridData.BEGINNING);
		osNameText.setText(osName);
		OPRoSUtil.createLabel(container, OPRoSStrings.getString("OSInputDlgVersionLabel"), SWT.NONE, 1, 0, 1, 0, GridData.BEGINNING);
		osVersionText=OPRoSUtil.createText(container, SWT.BORDER|SWT.SINGLE, 1, 0, 1, 0, 200, 0, GridData.BEGINNING);
		osVersionText.setText(osVersion);
		return container; 
	}
	
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(OPRoSStrings.getString("OSInputDlgTile"));
	}
	public String getOSName() {
		return osName;
	}
	public void setOSName(String osName) {
		this.osName=osName;
		osNameText.setText(osName);
	}
	public String getOSVersion() {
		return osVersion;
	}
	public void setOSVersion(String osVersion) {
		this.osVersion=osVersion;
		osVersionText.setText(osVersion);
	}

	protected void okPressed() {
		setOSName(osNameText.getText());
		setOSVersion(osVersionText.getText());
		super.okPressed();
	}
	
}
