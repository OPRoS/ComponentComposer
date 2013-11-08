package kr.co.n3soft.n3com.project.dialog;

import java.util.HashMap;

import kr.co.n3soft.n3com.OPRoSStrings;
import kr.co.n3soft.n3com.OPRoSUtil;
import kr.co.n3soft.n3com.model.comm.PortModel;
import kr.co.n3soft.n3com.model.component.AtomicComponentModel;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.jdom.Document;

public class OPRoSEventPortDialog extends Dialog {
	private String portName="";
	private String portType="";
	private String portDescript="";
	private boolean isInput=true;
	private String usingDataTypeFileName="";
	private OPRoSEventPortDialogComposite contents;
	protected AtomicComponentModel atomicComponentModel;
	private PortModel pm = null;
	private boolean isNew = false;
	public OPRoSEventPortDialog(Shell parentShell,boolean isInput,boolean isNew,PortModel pm) {
		super(parentShell);
		this.isInput=isInput;
		this.isNew = isNew;
		this.pm = pm;
		if(pm!=null){
			Object obj = pm.getPortContainerModel();
			if(obj instanceof AtomicComponentModel){
				atomicComponentModel = (AtomicComponentModel)obj;
			}
		}
	}

	protected Control createDialogArea(Composite parent) {
		contents = new OPRoSEventPortDialogComposite(parent, SWT.NULL, 1, GridData.FILL_BOTH,isInput,
				this.isNew,this.pm);
		return contents;
	}

	protected void okPressed() {
		portName=contents.getPortName();
		portType= contents.getPortType();
		if(getPortName().isEmpty()){
			OPRoSUtil.openMessageBox(OPRoSStrings.getString("EventPortErrorMessage0"), 
					SWT.ERROR|SWT.ICON_ERROR);
			return;
		}
		if(!this.isNew){
			if(OPRoSUtil.isDuplicatePortName(getPortName(),true,atomicComponentModel)){
				OPRoSUtil.openMessageBox(OPRoSStrings.getString("PortNameDuplicateError"), 
						SWT.ERROR|SWT.ICON_ERROR);
				return;
			}
		}else{
			if(OPRoSUtil.isDuplicatePortName(getPortName(),false,atomicComponentModel)){
				OPRoSUtil.openMessageBox(OPRoSStrings.getString("PortNameDuplicateError"), 
						SWT.ERROR|SWT.ICON_ERROR);
				return;
			}			
		}
		if(getPortType().isEmpty()||
				getPortType().compareTo(OPRoSStrings.getString("ServiceTypeDefaultValue"))==0){
			OPRoSUtil.openMessageBox(OPRoSStrings.getString("EventPortErrorMessage1"), 
					SWT.ERROR|SWT.ICON_ERROR);
			return;
		}
		portDescript=contents.getPortDescript();
		if(contents.getMap().containsKey(portType)){
			usingDataTypeFileName=contents.getMap().get(portType);
		}
		pm.setType(portType);
		pm.setName(portName);
		super.okPressed();
	}
	public String getPortName(){
		return portName;
	}
	public String getPortType(){
		return portType;
	}
	public String getPortDescript(){
		return portDescript;
	}
	public String getUsingDataTypeFileName(){
		return usingDataTypeFileName;
	}
	
	protected void configureShell(Shell newShell) {
		newShell.setText(OPRoSStrings.getString("EventPortTitle"));
		super.configureShell(newShell);
	}
	
}
