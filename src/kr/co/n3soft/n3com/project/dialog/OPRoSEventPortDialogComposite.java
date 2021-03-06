/*******************************************************************************
* Copyright (C) 2008 ED Corp., All rights reserved
*  
*  
* This programs is the production of ED Copr. research & development activity;
* you cannot use it and cannot redistribute it and cannot modify it and
* cannot  store it in any media(disk, photo or otherwise) without the prior
* permission of ED.
* 
* You should have received the license for this program to use any purpose.
* If not, contact the ED CORPORATION, 517-15, SangDaeWon-Dong, JungWon-Gu,
* SeongNam-City, GyeongGi-Do, Korea. (Zip : 462-806), http://www.ed.co.kr
* 
* NO PART OF THIS WORK BY THE THIS COPYRIGHT HEREON MAY BE REPRODUCED, STORED
* IN RETRIEVAL SYSTENS, IN ANY FORM OR BY ANY MEANS, ELECTRONIC, MECHANICAL,
* PHOTOCOPYING, RECORDING OR OTHERWISE, WITHOUT THE PRIOR PERMISSION OF ED Corp.
*
* @Author: sevensky(Juwon Kim), (jwkim@ed.co.kr, sevensky@mju.ac.kr)
********************************************************************************/
package kr.co.n3soft.n3com.project.dialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import kr.co.n3soft.n3com.OPRoSStrings;
import kr.co.n3soft.n3com.OPRoSUtil;
import kr.co.n3soft.n3com.WizardMessages;
import kr.co.n3soft.n3com.model.comm.PortModel;
import kr.co.n3soft.n3com.model.component.AtomicComponentModel;
import kr.co.n3soft.n3com.model.component.OPRoSDataTypeElementModel;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
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

public class OPRoSEventPortDialogComposite extends Composite {
	protected Text eventNameText;
	protected Combo eventTypeCombo;
	protected Text eventUsageText;
	protected Text eventPortDesciptText;
	protected Label messageLabel;
	protected boolean isInput=true;
	protected HashMap<String,String> map;
	private boolean isNew = false;
	protected AtomicComponentModel atomicComponentModel;
	private PortModel pm = null;
	
	public OPRoSEventPortDialogComposite(Composite parent, int style, int column, int gridStyle, 
			boolean isInput,boolean isNew,PortModel _pm) {
		super(parent, style);
		this.isNew = isNew;
		pm = _pm;
		if(pm!=null){
			Object obj = pm.getPortContainerModel();
			if(obj instanceof AtomicComponentModel){
				atomicComponentModel = (AtomicComponentModel)obj;
			}
		}
		this.isInput=isInput;
		map = new HashMap<String,String>();
		this.setFont(parent.getFont());
        GridLayout layout = new GridLayout();
        layout.numColumns = column;
        this.setLayout(layout);
        this.setLayoutData(new GridData(gridStyle));
        createEventPortsGroup(this);
	}
	
	protected void createEventPortsGroup(Composite parent){
		Group compGroup=null;
		if(isInput)
			compGroup = OPRoSUtil.createGroup(parent, SWT.NONE, OPRoSStrings.getString("EventInPortTitle"),
				4, GridData.BEGINNING);
		else
			compGroup = OPRoSUtil.createGroup(parent, SWT.NONE, OPRoSStrings.getString("EventOutPortTitle"),
					4, GridData.BEGINNING);
	    OPRoSUtil.createLabel(compGroup, OPRoSStrings.getString("EventPortNameLabel"),
	    		SWT.NONE,1,5,1,0,GridData.BEGINNING);
	    eventNameText = OPRoSUtil.createText(compGroup,SWT.BORDER|SWT.SINGLE,1,0,1,0,100,0,GridData.BEGINNING);
	    eventNameText.addModifyListener(new ModifyListener(){
		
			public void modifyText(ModifyEvent evt) {
				if(isNew){
					if(OPRoSUtil.isDuplicatePortName(eventNameText.getText(),false,atomicComponentModel)){
						messageLabel.setText(OPRoSStrings.getString("PortNameDuplicateError"));
					}else{
						messageLabel.setText("");
					}
				}else{
					if(OPRoSUtil.isDuplicatePortName(eventNameText.getText(),true,atomicComponentModel)){
						messageLabel.setText(OPRoSStrings.getString("PortNameDuplicateError"));
					}else{
						messageLabel.setText("");
					}
				}
			}
        });
	    OPRoSUtil.createLabel(compGroup, OPRoSStrings.getString("EventPortTypeLabel"),
	    		SWT.NONE,1,5,1,0,GridData.BEGINNING);
	    eventTypeCombo = OPRoSUtil.createCombo(compGroup, SWT.SIMPLE|SWT.SINGLE,OPRoSUtil.dataTypes,-1,1,0,1,0,100,0,GridData.BEGINNING);
		if(this.pm!=null && this.atomicComponentModel!=null){
			Iterator it = atomicComponentModel.getDataTypesModel().iterator();
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
					eventTypeCombo.add(ele.getAttributeValue(OPRoSStrings.getString("DataTypeEleAttr")), 0);
					map.put(ele.getAttributeValue(OPRoSStrings.getString("DataTypeEleAttr")), dataModel.getDataTypeFileName());
				}
			}
			}
		}
	    OPRoSUtil.createLabel(compGroup, OPRoSStrings.getString("EventPortUsageLabe"),
	    		SWT.NONE,1,5,1,0,GridData.BEGINNING);
	    eventUsageText =OPRoSUtil.createText(compGroup,SWT.BORDER|SWT.SINGLE,1,0,1,0,100,0,GridData.BEGINNING);
	    eventUsageText.setEditable(false);
	    if(isInput)
	    	eventUsageText.setText(OPRoSStrings.getString("DataPortUsage0"));
	    else
	    	eventUsageText.setText(OPRoSStrings.getString("DataPortUsage1"));
	    
	    OPRoSUtil.createLabel(compGroup, OPRoSStrings.getString("EventPortDescriptLabel"),
	    		SWT.NONE,1,5,1,0,GridData.BEGINNING);
	    eventPortDesciptText = OPRoSUtil.createText(compGroup,SWT.BORDER|SWT.SINGLE,1,0,1,0,100,0,GridData.BEGINNING);
	    eventTypeCombo.addModifyListener(new ModifyListener(){
			
			public void modifyText(ModifyEvent e) {
				String strDataType = eventTypeCombo.getText();
				if(strDataType.compareTo(OPRoSStrings.getString("DataType7"))==0||
						strDataType.compareTo(OPRoSStrings.getString("DataType8"))==0||
		  				strDataType.compareTo(OPRoSStrings.getString("DataType9"))==0||
		  				strDataType.compareTo(OPRoSStrings.getString("DataType10"))==0||
		  				strDataType.compareTo(OPRoSStrings.getString("DataType11"))==0||
		  				strDataType.compareTo(OPRoSStrings.getString("DataType12"))==0||
		  				strDataType.compareTo(OPRoSStrings.getString("DataType13"))==0)
				{
					OPRoSValueTypeOfDataTypeDialog dlg=null;
					dlg = new OPRoSValueTypeOfDataTypeDialog(getShell(),strDataType);
					dlg.open();
					if(dlg.getReturnCode()==Dialog.OK){
						if(!dlg.getValueType().isEmpty()){
							eventTypeCombo.add(strDataType+OPRoSStrings.getString("DataTypeSymbol1")+dlg.getValueType()+OPRoSStrings.getString("DataTypeSymbol2"));
							eventTypeCombo.select(eventTypeCombo.getItemCount()-1);
						}
					}
				}
			}
        });
	    messageLabel =  OPRoSUtil.createLabel(compGroup, "                                        ",
        		SWT.NONE,4,5,1,0,GridData.BEGINNING);
        messageLabel.setForeground(ColorConstants.red);
        Label label = OPRoSUtil.createLabel(compGroup, OPRoSStrings.getString("NotifyNeedSourceModify"),
        		SWT.NONE,8,5,1,5,GridData.BEGINNING);
        label.setForeground(ColorConstants.blue);
        if(!this.isNew && this.pm!=null && this.atomicComponentModel!=null){
//    		OPRoSPortElementBaseModel element;
//       		element = (OPRoSPortElementBaseModel)modifyModel;
       		eventNameText.setText(pm.getName());
       		eventUsageText.setText(pm.getUsage());
       		
       		
       		int nIndex = eventTypeCombo.indexOf(pm.getType());
       		if(nIndex!=-1)
       			eventTypeCombo.select(nIndex);
       		else{
       			eventTypeCombo.add(pm.getType());
       			eventTypeCombo.select(eventTypeCombo.indexOf(pm.getType()));
       		}
       		
       		eventPortDesciptText.setText(pm.getDesc());
        }
	}
	public String getPortName(){
		return eventNameText.getText();
	}
	public String getPortType(){
		return eventTypeCombo.getText();
	}
	public String getPortDescript(){
		return eventPortDesciptText.getText();
	}
	public HashMap<String,String> getMap(){
		return map;
	}
	
//	WJH 100806 S �߰�
	public void setPortName(String str){
		eventNameText.setText(str);
	}
	public void setPortType(String str){
		eventTypeCombo.setText(str);
	}
//	WJH 100806 E �߰�
}
