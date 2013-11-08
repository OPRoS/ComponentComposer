package kr.co.n3soft.n3com.project.dialog;

import java.util.HashMap;
import java.util.Iterator;

import kr.co.n3soft.n3com.OPRoSStrings;
import kr.co.n3soft.n3com.OPRoSUtil;
import kr.co.n3soft.n3com.model.component.AtomicComponentModel;
import kr.co.n3soft.n3com.model.component.OPRoSDataTypeElementModel;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.jdom.Document;
import org.jdom.Element;

public class OPRoSPropertyInputDialog extends Dialog {
	private String name;
	private String type;
	private String defaultValue;
	private String usingDataTypeFileName="";
	private Text nameText;
	private Text defaultValueText;
	private Combo typeCombo;
	protected Label messageLabel;
	private HashMap<String,String> map;
	protected AtomicComponentModel compEle;
	
	public OPRoSPropertyInputDialog(Shell parentShell,AtomicComponentModel _atomicComponentModel) {
		super(parentShell);
		this.compEle=_atomicComponentModel;
		name=OPRoSStrings.getString("DefaultPropertyName");
		defaultValue=OPRoSStrings.getString("DefaultPropertyDefaultValue");
		map=new HashMap<String,String>();
	}

	protected Control createDialogArea(Composite parent) {
		parent.getShell().setText(OPRoSStrings.getString("PropertyInputDlgTitle"));
		Composite comp = OPRoSUtil.createComposite(parent, SWT.NONE, 2, SWT.NONE);
		OPRoSUtil.createLabel(comp, OPRoSStrings.getString("PropertyInputDlgNameLabel"),	SWT.NONE, 1, 5, 0, 0, GridData.BEGINNING);
		nameText = OPRoSUtil.createText(comp, SWT.BORDER|SWT.SINGLE, 1, 0, 0, 0, 150, 0, GridData.BEGINNING);
		nameText.addModifyListener(new ModifyListener(){
		
			public void modifyText(ModifyEvent evt) {
				if(OPRoSUtil.isDuplicatePropertyName(nameText.getText(),false,compEle)){
					messageLabel.setText(OPRoSStrings.getString("PropertyNameDuplicateError"));
				}else{
					messageLabel.setText("");
				}
			}
        });
		OPRoSUtil.createLabel(comp, OPRoSStrings.getString("PropertyInputDlgTypeLabel"),	SWT.NONE, 1, 5, 0, 0, GridData.BEGINNING);
		typeCombo = OPRoSUtil.createCombo(comp, SWT.SIMPLE|SWT.SINGLE,OPRoSUtil.dataTypes,0,1,0,0,0,150,0,GridData.BEGINNING);
		OPRoSUtil.createLabel(comp, OPRoSStrings.getString("PropertyInputDlgDefaultValueLabel"),	SWT.NONE, 1, 5, 0, 0, GridData.BEGINNING);
		defaultValueText = OPRoSUtil.createText(comp, SWT.BORDER|SWT.SINGLE, 1, 0, 0, 0, 150, 0, GridData.BEGINNING);
		if(compEle!=null){
			Iterator it = compEle.getDataTypesModel().iterator();
			while(it.hasNext()){
				Object obj = it.next();
				if(obj instanceof OPRoSDataTypeElementModel){
				OPRoSDataTypeElementModel dataModel=(OPRoSDataTypeElementModel)obj;
				Iterator<Element> eles;
				Element ele;
				Document doc = dataModel.getDataTypeDoc();
				eles = doc.getRootElement().getChildren(OPRoSStrings.getString("DataTypeEle")).iterator();
				while(eles.hasNext()){
					ele=eles.next();
					typeCombo.add(ele.getAttributeValue(OPRoSStrings.getString("DataTypeEleAttr")), 0);
					map.put(ele.getAttributeValue(OPRoSStrings.getString("DataTypeEleAttr")), dataModel.getDataTypeFileName());
				}
			}
			}
		}
		typeCombo.addModifyListener(new ModifyListener(){
			
			public void modifyText(ModifyEvent e) {
				String strDataType = typeCombo.getText();
				if(strDataType.compareTo(OPRoSStrings.getString("DataType7"))==0||
  				   strDataType.compareTo(OPRoSStrings.getString("DataType8"))==0||
  				   strDataType.compareTo(OPRoSStrings.getString("DataType9"))==0||
  				   strDataType.compareTo(OPRoSStrings.getString("DataType10"))==0||
  				   strDataType.compareTo(OPRoSStrings.getString("DataType11"))==0||
  				   strDataType.compareTo(OPRoSStrings.getString("DataType12"))==0||
  				   strDataType.compareTo(OPRoSStrings.getString("DataType13"))==0)
				{
					OPRoSValueTypeOfDataType dlg=null;
					dlg = new OPRoSValueTypeOfDataType(getShell(),strDataType);
					dlg.open();
					if(dlg.getReturnCode()==Dialog.OK){
						if(!dlg.getValueType().isEmpty()){
							typeCombo.add(strDataType+"<"+dlg.getValueType()+">");
							typeCombo.select(typeCombo.getItemCount()-1);
						}
					}
				}
			}
        });
		messageLabel =  OPRoSUtil.createLabel(comp, "                                        ",
        		SWT.NONE,2,5,0,0,GridData.BEGINNING);
        messageLabel.setForeground(ColorConstants.red);
		return comp;
	}
	
	protected void buttonPressed(int buttonId) {
		if(buttonId==Dialog.OK){
			setName(nameText.getText());
			if(getName().isEmpty()){
				OPRoSUtil.openMessageBox(OPRoSStrings.getString("PropertyNameEmptyError"), SWT.ERROR|SWT.ICON_ERROR);
				return;
			}
			setType(typeCombo.getText());
			if(getType().isEmpty()){
				OPRoSUtil.openMessageBox(OPRoSStrings.getString("PropertyTypeEmptyError"), SWT.ERROR|SWT.ICON_ERROR);
				return;
			}
			if(OPRoSUtil.isDuplicatePropertyName(getName(),false,compEle)){
				OPRoSUtil.openMessageBox(OPRoSStrings.getString("PropertyNameDuplicateError"), SWT.ERROR|SWT.ICON_ERROR);
				return;
			}
			setDefaultValue(defaultValueText.getText());
			if(map.containsKey(type)){
				setUsingDataTypeFileName(map.get(getType()));
			}
		}
		super.buttonPressed(buttonId);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public String getUsingDataTypeFileName() {
		return usingDataTypeFileName;
	}
	public void setUsingDataTypeFileName(String usingDataTypeFileName) {
		this.usingDataTypeFileName = usingDataTypeFileName;
	}
}
