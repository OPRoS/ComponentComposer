package kr.co.n3soft.n3com.project.dialog;

import java.util.HashMap;
import java.util.Iterator;

import kr.co.n3soft.n3com.OPRoSStrings;
import kr.co.n3soft.n3com.OPRoSUtil;
import kr.co.n3soft.n3com.model.comm.PortModel;
import kr.co.n3soft.n3com.model.component.AtomicComponentModel;
import kr.co.n3soft.n3com.model.component.ComponentModel;
import kr.co.n3soft.n3com.model.component.OPRoSServiceTypeElementModel;
import kr.co.n3soft.n3com.model.component.OPRoSServiceTypesElementModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;
import org.jdom.Document;
import org.jdom.Element;

public class OPRoSServicePortComposite extends Composite {
	protected Text servicePortNameText;
	protected Combo servicePortTypeCombo;
	protected Text servicePortUsageText;
	protected Text servicePortReferenceText;
	protected Text servicePortDescriptionText;
	protected List serviceTypeList;
	protected Label messageLabel;
	protected HashMap<String,Document> map;
	protected boolean isProvided=false;
	protected Button serviceNewReferenceButton;
	private boolean isNew = false;
	
	public boolean isNew() {
		return isNew;
	}
	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}
	protected AtomicComponentModel atomicComponentModel;
	private PortModel pm = null;
	
//	protected OPRoSElementBaseModel modifyModel=null;
//	protected OPRoSComponentElementModel compEle=null;
	
	
	
	public PortModel getPm() {
		return pm;
	}
	public void setPm(PortModel pm) {
		this.pm = pm;
	}
	private static final String[] serviceTypes = {OPRoSStrings.getString("ServiceTypeDefaultValue")};
	public OPRoSServicePortComposite(Composite parent, int style, int column, int gridStyle, 
			boolean isProvided,boolean isNew,PortModel _pm) {
		super(parent, style);
//		this.modifyModel=model;
//		this.compEle=compEle;
		this.isProvided=isProvided;
		this.isNew = isNew;
		pm = _pm;
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
		
		map=new HashMap<String,Document>();
		this.setFont(parent.getFont());
        GridLayout layout = new GridLayout();
        layout.numColumns = column;
        this.setLayout(layout);
        this.setLayoutData(new GridData(gridStyle));
//        pm = ProjectManager.getInstance().getSelectPort();
        createMethodPortsGroup(this);
	}
	
	protected void createMethodPortsGroup(Composite parent){
		Group compGroup=null;
		if(isProvided)
			compGroup = OPRoSUtil.createGroup(parent, SWT.NONE, OPRoSStrings.getString("ServiceProvidedPortTitle"),
				8, GridData.BEGINNING);
		else
			compGroup = OPRoSUtil.createGroup(parent, SWT.NONE, OPRoSStrings.getString("ServiceRequiredPortTitle"),
					8, GridData.BEGINNING);
        OPRoSUtil.createLabel(compGroup, OPRoSStrings.getString("ServicePortNameLabel"),
        		SWT.NONE,2,5,1,0,GridData.BEGINNING);
        servicePortNameText = OPRoSUtil.createText(compGroup,SWT.BORDER|SWT.SINGLE,2,0,1,0,120,0,GridData.BEGINNING);
        servicePortNameText.addModifyListener(new ModifyListener(){
			
			public void modifyText(ModifyEvent evt) {
				System.out.println("modifyText:"+evt);
				if(isNew){
					if(OPRoSUtil.isDuplicatePortName(servicePortNameText.getText(),false,atomicComponentModel)){
						messageLabel.setText(OPRoSStrings.getString("PortNameDuplicateError"));
				}else{
						messageLabel.setText("");
					}
				}else{
					if(OPRoSUtil.isDuplicatePortName(servicePortNameText.getText(),true,atomicComponentModel)){
						messageLabel.setText(OPRoSStrings.getString("PortNameDuplicateError"));
					}else{
						messageLabel.setText("");
					}
				}
			}
        });
        OPRoSUtil.createLabel(compGroup, OPRoSStrings.getString("ServicePortTypeLabel"),
        		SWT.NONE,2,5,1,0,GridData.BEGINNING);
        servicePortTypeCombo =OPRoSUtil.createCombo(compGroup, SWT.READ_ONLY|SWT.SINGLE,serviceTypes,0,2,0,1,0,80,0,GridData.BEGINNING);
        OPRoSUtil.createLabel(compGroup, OPRoSStrings.getString("ServicePortUsageLabel"),
        		SWT.NONE,2,5,1,0,GridData.BEGINNING);
        servicePortUsageText = OPRoSUtil.createText(compGroup,SWT.BORDER|SWT.SINGLE,2,0,1,0,100,0,GridData.BEGINNING);
        servicePortUsageText.setEditable(false);
        if(isProvided){
        	servicePortUsageText.setText(OPRoSStrings.getString("ServicePortUsage0"));
        }else{
        	servicePortUsageText.setText(OPRoSStrings.getString("ServicePortUsage1"));
        }
        OPRoSUtil.createLabel(compGroup, OPRoSStrings.getString("ServicePortReferLabel"),
        		SWT.NONE,2,5,1,0,GridData.BEGINNING);
        servicePortReferenceText = OPRoSUtil.createText(compGroup,SWT.BORDER|SWT.SINGLE,2,0,1,0,100,0,GridData.BEGINNING);
        servicePortReferenceText.setEditable(false);
        OPRoSUtil.createLabel(compGroup, OPRoSStrings.getString("ServicePortDesciptLabel"),
        		SWT.NONE,2,5,1,0,GridData.BEGINNING);
        servicePortDescriptionText = OPRoSUtil.createText(compGroup,SWT.BORDER|SWT.SINGLE,6,0,1,0,340,0,GridData.BEGINNING);
        OPRoSUtil.createLabel(compGroup, OPRoSStrings.getString("ServiceTypeListLabel"),
        		SWT.NONE,5,5,1,5,GridData.BEGINNING);
        serviceNewReferenceButton = OPRoSUtil.createButton(compGroup,SWT.NONE,"NewType or Import",
        		3,50,1,0,148,20,GridData.BEGINNING);
        
        serviceNewReferenceButton.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
//				try{
				OPRoSServiceTypeInputDialog dlg = new OPRoSServiceTypeInputDialog(null,null);
				dlg.open();
//				}
//				catch(Exception e1){
//					e1.printStackTrace();
//				}
				
				if(dlg.getReturnCode()==InputDialog.OK){
					if(pm!=null){
						ComponentModel cm =  (ComponentModel)pm.getPortContainerModel();
//						OPRoSElementBaseModel servTypesModel = compEle.getServiceTypesModel();
						if(cm instanceof AtomicComponentModel){
							AtomicComponentModel acm = (AtomicComponentModel)cm;
							OPRoSServiceTypeElementModel element = new OPRoSServiceTypeElementModel();
							element.setServiceTypeFileName(dlg.getServiceTypeFileName());
							element.setServiceTypeDoc(dlg.getServiceTypeDoc());
//							servTypesModel.addChild(element);
							String str = element.getServiceTypeFileName();
//							acm.addOPRoSServiceTypeElementModel(element);
							OPRoSServiceTypesElementModel ops = (OPRoSServiceTypesElementModel)acm.getClassModel().getUMLDataModel().getElementProperty("OPRoSServiceTypesElementModel");
							ops.addChild(element);
							
							
							serviceTypeList.add(element.getServiceTypeFileName());
							map.put(element.getServiceTypeFileName(), element.getServiceTypeDoc());
						}
					}
					else{
						
					}
				}
			}
		});
        serviceTypeList = OPRoSUtil.createList(compGroup,SWT.SINGLE|SWT.BORDER,8,5,1,0,430,100,GridData.BEGINNING);
        if( this.pm!=null && this.atomicComponentModel!=null){
        	serviceTypeList.removeAll();
        	if(this.atomicComponentModel.getServiceTypesModel()!=null){
        	Iterator it = this.atomicComponentModel.getServiceTypesModel().iterator();
        	while(it.hasNext()){
        		Object obj = it.next();
    		if(obj instanceof OPRoSServiceTypeElementModel){
//	        		OPRoSServiceTypeElementModel serviceModel=(OPRoSServiceTypeElementModel)obj;
//	    		serviceTypeList.add(serviceModel.getServiceTypeFileName());
//	    		map.put(serviceModel.getServiceTypeFileName(), serviceModel.getServiceTypeDoc());
       			OPRoSServiceTypeElementModel serviceModel=(OPRoSServiceTypeElementModel)obj;
       			//    			WJH 100817 S 내용없는 리스트 제거
       			    			if(serviceModel.getServiceTypeFileName() != null && !serviceModel.getServiceTypeFileName().equals("")){
       					    		serviceTypeList.add(serviceModel.getServiceTypeFileName());
       					    		map.put(serviceModel.getServiceTypeFileName(), serviceModel.getServiceTypeDoc());
       			    			}
       			//    			WJH 100817 S 내용없는 리스트 제거
    		}
        	}
    	}
        	
        }

        serviceTypeList.addSelectionListener(new SelectionAdapter(){
        	public void widgetSelected(SelectionEvent e) {
				int index = serviceTypeList.getSelectionIndex();
				servicePortReferenceText.setText(serviceTypeList.getItem(index));
				if(map.containsKey(serviceTypeList.getItem(index))){
					Document doc = map.get(serviceTypeList.getItem(index));
					Iterator<Element> eles;
					Element ele;
					eles = doc.getRootElement().getChildren(OPRoSStrings.getString("ServiceTypeEle")).iterator();
					servicePortTypeCombo.removeAll();
					while(eles.hasNext()){
						ele = eles.next();
						servicePortTypeCombo.add(ele.getChildTextTrim("type_name"));
					}
				}
			}
		});
        serviceTypeList.addMouseListener(new MouseAdapter(){
        	public void mouseDoubleClick(MouseEvent e){
        		int index = serviceTypeList.getSelectionIndex();
        		OPRoSServiceTypeInputDialog dlg = 
        			new OPRoSServiceTypeInputDialog(getShell(),serviceTypeList.getItem(index),map.get(serviceTypeList.getItem(index)),false,pm);
        		dlg.open();
        	}
        });
        messageLabel =  OPRoSUtil.createLabel(compGroup, "                                        ",
        		SWT.NONE,8,5,1,0,GridData.BEGINNING);
        messageLabel.setForeground(ColorConstants.red);
        Label label = OPRoSUtil.createLabel(compGroup, OPRoSStrings.getString("NotifyNeedSourceModify"),
        		SWT.NONE,8,5,1,5,GridData.BEGINNING);
        label.setForeground(ColorConstants.blue);
     
        if(!this.isNew && this.pm!=null && this.atomicComponentModel!=null){
        	servicePortNameText.setText(pm.getName());
        	servicePortUsageText.setText(pm.getUsage());
        	int index = serviceTypeList.indexOf(pm.getTypeRef());
        	if(index>=0){
	        	serviceTypeList.select(index);
	        	servicePortReferenceText.setText(serviceTypeList.getItem(index));
				if(map.containsKey(serviceTypeList.getItem(index))){
					Document doc = map.get(serviceTypeList.getItem(index));
					Iterator<Element> eles;
					Element ele;
					eles = doc.getRootElement().getChildren(OPRoSStrings.getString("ServiceTypeEle")).iterator();
					servicePortTypeCombo.removeAll();
					while(eles.hasNext()){
						ele = eles.next();
						servicePortTypeCombo.add(ele.getChildTextTrim("type_name"));
					}
					servicePortTypeCombo.select(servicePortTypeCombo.indexOf(pm.getType()));
				}
	        	servicePortDescriptionText.setText(pm.getDesc());
        	}
        	
        }
        
        
        
//        if(modifyModel!=null){
//        	OPRoSPortElementBaseModel element;
//       		element = (OPRoSPortElementBaseModel)modifyModel;
//        	servicePortNameText.setText(element.getName());
//        	servicePortUsageText.setText(element.getUsage());
//        	int index;
//        	if(isProvided)
//        		index=serviceTypeList.indexOf(((OPRoSServiceProvidedPortElementModel)element).getReference());
//        	else
//        		index=serviceTypeList.indexOf(((OPRoSServiceRequiredPortElementModel)element).getReference());
//        	if(index>=0){
//	        	serviceTypeList.select(index);
//	        	servicePortReferenceText.setText(serviceTypeList.getItem(index));
//				if(map.containsKey(serviceTypeList.getItem(index))){
//					Document doc = map.get(serviceTypeList.getItem(index));
//					Iterator<Element> eles;
//					Element ele;
//					eles = doc.getRootElement().getChildren(OPRoSStrings.getString("ServiceTypeEle")).iterator();
//					servicePortTypeCombo.removeAll();
//					while(eles.hasNext()){
//						ele = eles.next();
//						servicePortTypeCombo.add(ele.getChildTextTrim("type_name"));
//					}
//					servicePortTypeCombo.select(servicePortTypeCombo.indexOf(element.getType()));
//				}
//	        	servicePortDescriptionText.setText(element.getDescription());
//        	}
//        }
	}
	public String getPortName(){
		return servicePortNameText.getText();
	}
	public String getPortType(){
		return servicePortTypeCombo.getText();
	}
	public String getPortRefer(){
		return servicePortReferenceText.getText();
	}
	public String getPortDescript(){
		return servicePortDescriptionText.getText();
	}
	public HashMap<String,Document> getServiceTypeMap(){
		return map;
	}
	
//	WJH 100806 S 추가
	public void setPortName(String str){
		servicePortNameText.setText(str);
	}
	public void setPortType(String str){
		servicePortTypeCombo.setText(str);
	}
	public void setPortRefer(String str){
		servicePortReferenceText.setText(str);
	}
	public void setPortDescript(String str){
		servicePortDescriptionText.setText(str);
	}
	public List getServiceTypeList(){
		return serviceTypeList;
	}
//	WJH 100806 E 추가
}

