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

public class OPRoSDataPortDialog extends Dialog {
	private String portName="";
	private String portType="";
	private String portDescript="";
	private String portRefer="";
	private String portPolicy="";
	private String portQueueSize="";
	private HashMap<String,Document> map;
	private boolean isInput=true;
	private OPRoSDataPortDialogComposite contents;
	protected AtomicComponentModel atomicComponentModel;
	private PortModel pm = null;
	private boolean isNew = false;
	public OPRoSDataPortDialog(Shell parentShell, boolean isInput,boolean isNew,PortModel pm) {
		super(parentShell);
		map=new HashMap<String,Document>();
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
		contents = new OPRoSDataPortDialogComposite(parent, SWT.NULL, 1, GridData.FILL_BOTH,isInput,this.isNew,this.pm);
		return contents;
	}

	protected void okPressed() {
		portName=contents.getPortName();
		portType= contents.getPortType();
		portRefer=contents.getPortRefer();
		if(getPortName().isEmpty()){
			OPRoSUtil.openMessageBox(OPRoSStrings.getString("DataPortErrorMessage0"), SWT.ERROR|SWT.ICON_ERROR);
			return;
		}
		if(!this.isNew){
			if(OPRoSUtil.isDuplicatePortName(getPortName(),true,atomicComponentModel)){
				OPRoSUtil.openMessageBox(OPRoSStrings.getString("PortNameDuplicateError"), SWT.ERROR|SWT.ICON_ERROR);
				return;
			}
		}else{
			if(OPRoSUtil.isDuplicatePortName(getPortName(),false,atomicComponentModel)){
				OPRoSUtil.openMessageBox(OPRoSStrings.getString("PortNameDuplicateError"), SWT.ERROR|SWT.ICON_ERROR);
				return;
			}
		}
		if(getPortType().isEmpty()||getPortType().compareTo(OPRoSStrings.getString("ServiceTypeDefaultValue"))==0){
			OPRoSUtil.openMessageBox(OPRoSStrings.getString("DataPortErrorMessage3"), SWT.ERROR|SWT.ICON_ERROR);
			return;
		}
		if(isInput){
			portPolicy = contents.getPortPolicy();
			portQueueSize = contents.getPOrtQueueSize();
			if(portQueueSize.isEmpty()){
				OPRoSUtil.openMessageBox(OPRoSStrings.getString("DataPortErrorMessage2"), SWT.ERROR|SWT.ICON_ERROR);
				return;
			}
			pm.setQueueingPolicy(portPolicy);
			pm.setQueueSize(portQueueSize);
			pm.setUsage("input");
		}
		portDescript=contents.getPortDescript();
		pm.setName(portName);
		pm.setTypeRef(portRefer);
		pm.setDesc(portDescript);
		pm.setType(portType);
		map=contents.getDataTypeMap();
		super.okPressed();
	}
	public String getPortName(){
		return portName;
	}
	public String getPortType(){
		return portType;
	}
	public String getPortRefer(){
		return portRefer;
	}
	public String getPortDescript(){
		return portDescript;
	}
	public HashMap<String,Document> getServiceTypeMap(){
		return map;
	}
	public String getPortPolicy(){
		return portPolicy;
	}
	public String getPortQueueSize(){
		return portQueueSize;
	}


	protected void configureShell(Shell newShell) {
		newShell.setText(OPRoSStrings.getString("DataPortTitle"));
		super.configureShell(newShell);
	}
	
}
