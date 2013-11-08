package kr.co.n3soft.n3com.project.dialog;

import java.util.HashMap;

import kr.co.n3soft.n3com.OPRoSStrings;
import kr.co.n3soft.n3com.OPRoSUtil;
import kr.co.n3soft.n3com.model.comm.PortModel;
import kr.co.n3soft.n3com.model.component.AtomicComponentModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.jdom.Document;


public class OPRoSServicePortDialog extends Dialog {
	private String portName;
	private String portType;
	private String portDescript;
	private String portRefer;
	private HashMap<String,Document> map;
	private boolean isRequired=false;
	private boolean isNew = false;
	protected AtomicComponentModel atomicComponentModel;
	public boolean isNew() {
		return isNew;
	}
	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}
	private OPRoSServicePortComposite contents;
	
	private PortModel pm = null;
	public PortModel getPm() {
		return pm;
	}
	public void setPm(PortModel pm) {
		this.pm = pm;
	}
	//	private OPRoSElementBaseModel model=null;
//	private OPRoSBodyElementModel bodyModel=null;
	public OPRoSServicePortDialog(Shell parentShell, boolean isRequired,boolean isNew,PortModel pm) {
		super(parentShell);
		map=new HashMap<String,Document>();
		this.isRequired=isRequired;
		this.isNew = isNew;
		this.pm = pm;
		if(pm!=null){
			Object obj = pm.getPortContainerModel();
			if(obj instanceof AtomicComponentModel){
				AtomicComponentModel atomicComponentModel1 = (AtomicComponentModel)obj;
				if(atomicComponentModel1.getCoreUMLTreeModel()==null){
					this.atomicComponentModel = atomicComponentModel1;
				}
				else{
					UMLTreeModel ut = atomicComponentModel1.getCoreUMLTreeModel();
					this.atomicComponentModel = (AtomicComponentModel)ut.getRefModel();
					
				}
			}
		}
//		this.pm = ProjectManager.getInstance().getSelectPort();
//		this.model=model;
//		this.bodyModel=bodyModel;
	}
	
	protected Control createDialogArea(Composite parent) {
		contents = new OPRoSServicePortComposite(parent, SWT.NULL, 1, GridData.FILL_BOTH,isRequired,this.isNew,this.pm);
//		contents.setPm(this.pm);
//		contents.setNew(isNew());
		return contents;
	}

	
	protected void okPressed() {
		portName=contents.getPortName();
		portType= contents.getPortType();
		portRefer=contents.getPortRefer();
		if(getPortName().isEmpty()){
			OPRoSUtil.openMessageBox(OPRoSStrings.getString("ServicePortErrorMessage0"), SWT.ERROR|SWT.ICON_ERROR);
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
			OPRoSUtil.openMessageBox(OPRoSStrings.getString("ServicePortErrorMessage3"), SWT.ERROR|SWT.ICON_ERROR);
			return;
		}
		if(getPortRefer().isEmpty()){
			OPRoSUtil.openMessageBox(OPRoSStrings.getString("ServicePortErrorMessage1"), SWT.ERROR|SWT.ICON_ERROR);
			return;
		}
		portDescript=contents.getPortDescript();
		map=contents.getServiceTypeMap();
		
		pm.setName(portName);
		pm.setTypeRef(portRefer);
		pm.setDesc(portDescript);
		pm.setType(portType);
//		atomicComponentModel.set
		
		
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
	
	protected void configureShell(Shell newShell) {
		newShell.setText(OPRoSStrings.getString("ServicePortTitle"));
		super.configureShell(newShell);
	}
	
}
