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

//import kr.co.n3soft.n3com.project.dialog.OPRoSServiceTypeInputWiget;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import kr.co.n3soft.n3com.OPRoSStrings;
import kr.co.n3soft.n3com.OPRoSUtil;
import kr.co.n3soft.n3com.model.comm.PortModel;
import kr.co.n3soft.n3com.model.component.AtomicComponentModel;
import kr.co.n3soft.n3com.model.component.OPRoSDataTypeElementModel;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.jdom.Document;
import org.jdom.Element;

public class OPRoSServiceTypeInputDialogComposite extends Composite {
	protected Tree serviceTypeTree;
	protected TreeItem rootItem;
	protected TreeItem selectedItem;
	protected MenuManager popupMenu;
	protected Text serviceTypeFileNameText;
	protected Label messageLabel;
	protected boolean canModify=true;
	protected AtomicComponentModel atomicComponentModel;
	private PortModel pm = null;
	
	public OPRoSServiceTypeInputWiget inputWiget;
	public Composite changeContainer;
	public boolean isFileNameDuplicate;
//	protected OPRoSComponentElementModel compEle=null;

	public OPRoSServiceTypeInputDialogComposite(Composite parent, int style, int column, int gridStyle, 
			boolean canModify,PortModel _pm) {
		super(parent, style);
		pm = _pm;
		if(pm!=null){
			Object obj = pm.getPortContainerModel();
			if(obj instanceof AtomicComponentModel){
				atomicComponentModel = (AtomicComponentModel)obj;
			}
		}
		this.canModify=canModify;
//		this.compEle=compEle;
		this.setFont(parent.getFont());
        GridLayout layout = new GridLayout();
        layout.numColumns = column;
        this.setLayout(layout);
        this.setLayoutData(new GridData(gridStyle));
        popupMenu = new MenuManager();
        createDataTypeGroup(this);
	}
	protected void createDataTypeGroup(Composite parent){		
		GridLayout layout = new GridLayout();
		parent.setLayout(layout);
		layout.numColumns = 2;
		
		Label label = new Label(parent, SWT.NONE);
		label.setText("OPRoS Service Types");
		GridData gd = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
		label.setLayoutData(gd);
		
		Composite container1 = new Composite(parent, SWT.BORDER);
		container1.setBackground(new Color(null, 255, 255, 255));
		layout = new GridLayout();
		container1.setLayout(layout);
		layout.numColumns = 2;	
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(container1);
		
		changeContainer = new Composite(parent, SWT.BORDER);
		layout = new GridLayout();
		changeContainer.setLayout(layout);
		layout.numColumns = 3;
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(changeContainer);
		
		
		
		
		//Group compGroup = OPRoSUtil.createGroup(container1, SWT.NONE, 
		//		OPRoSStrings.getString("ServiceTypeInputDlgCompGroup"),2, GridData.BEGINNING);
		
		if(serviceTypeTree==null)
			serviceTypeTree = new Tree(container1, SWT.NONE);
		gd= new GridData(SWT.FILL, SWT.CENTER, true, true, 2, 1);
        gd.widthHint=170;
        gd.heightHint=300;
        gd.horizontalSpan = 2;
        serviceTypeTree.setLayoutData(gd);
        //GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(serviceTypeTree);
        
        rootItem = new TreeItem(serviceTypeTree,SWT.NONE);
        rootItem.setText(OPRoSStrings.getString("ServiceTypeRoot"));
        selectedItem = rootItem;
        //serviceTypeTree.setLinesVisible(true);
        serviceTypeTree.deselectAll();
        rootItem.setExpanded(true);
        
        inputWiget = new OPRoSServiceTypeInputWiget(this);	
        changeContainer.layout(inputWiget.createServiceRootGroup(rootItem));
        
        if(canModify){
        	serviceTypeTree.addMouseListener(new MouseListener(){
				@Override
				public void mouseDoubleClick(MouseEvent arg0) {}
				@Override
				public void mouseDown(MouseEvent e) {
					if(e.button==3){
						selectedItem = ((Tree)e.getSource()).getItem(new Point(e.x,e.y));
						serviceTypeTree.setMenu(getMenu(selectedItem));
					}
				}
				@Override
				public void mouseUp(MouseEvent arg0) {}
        		
        	});
        	
        	serviceTypeTree.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {	
					//위젯 삭제
					for(Control control : changeContainer.getChildren()) {
						control.dispose();
					}
					
					TreeItem item = ((Tree)e.getSource()).getSelection()[0];
					if(item.getParentItem()!=null){
						if(item.getParentItem().getParentItem()==null){
							changeContainer.layout(inputWiget.createMethodRootGroup(item));
						}else if(item.getParentItem().getParentItem().getParentItem()==null){
							changeContainer.layout(inputWiget.createParameterRootGroup(item));
						}else{
							changeContainer.layout(inputWiget.createParameterGroup(item));
						}
					}
					else {
						changeContainer.layout(inputWiget.createServiceRootGroup(item));
					}
					
				}
        		
			});
        }
        
        Composite container3 = new Composite(parent, SWT.NONE);
        gd = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
		layout = new GridLayout();
		container3.setLayout(layout);
		container3.setLayoutData(gd);
		layout.numColumns = 2;
        
        OPRoSUtil.createLabel(container3, OPRoSStrings.getString("NewServiceTypeDlgLabel"), 
        		SWT.NONE,1,0,1,0,GridData.BEGINNING);
        serviceTypeFileNameText = new Text(container3, SWT.BORDER);
        gd = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
        serviceTypeFileNameText.setLayoutData(gd);
        
        if(!canModify){
        	serviceTypeFileNameText.setEnabled(false);
        }else{
        	serviceTypeFileNameText.addModifyListener(new ModifyListener(){
				@Override
				public void modifyText(ModifyEvent evt) {
					String fileName = serviceTypeFileNameText.getText();
					if(!fileName.endsWith(OPRoSStrings.getString("DataTypeExtension")))
						fileName = fileName+OPRoSStrings.getString("DataTypeExtension");
					
					if(OPRoSUtil.isDuplicateServiceTypeName(fileName, false, atomicComponentModel)){
						messageLabel.setText(OPRoSStrings.getString("ServiceTypeFileNameDuplicationError"));
						isFileNameDuplicate = true;
					}else{
						messageLabel.setText("");
						isFileNameDuplicate = false;
					}
				}
        	});
        }
       
        messageLabel =  OPRoSUtil.createLabel(container1, "                                        ",
        		SWT.NONE,2,0,1,0,GridData.BEGINNING);
        messageLabel.setForeground(ColorConstants.red);
       
	}
	public TreeItem getRootItem() {
		return rootItem;
	}
	public void setRootItem(TreeItem rootItem) {
		this.rootItem = rootItem;
	}
	private class NewServiceTypeAction extends Action{
		public NewServiceTypeAction(){
			super(OPRoSStrings.getString("NewServiceTypeDlgContextMenu1"));
		}
		public void run(){
			if(selectedItem!=null){
				if(selectedItem.equals(rootItem)){
					/*
					OPRoSNewServiceTypePopup dlg = new OPRoSNewServiceTypePopup(getShell(),0,compEle);
					dlg.open();
					if(dlg.getReturnCode()==InputDialog.OK){
						if(!dlg.getName().isEmpty()){
							if(!isExistItem(rootItem, dlg.getName())){
								TreeItem item = new TreeItem(rootItem,SWT.NONE);
								String str=dlg.getName();//+WizardMessages.getString("MethodType.Seper2")+dlg.getType();
								item.setText(str);
							}
							else{
								OPRoSUtil.openMessageBox(OPRoSStrings.getString("DuplicationErrorMessage"),
										SWT.OK|SWT.ICON_WARNING);
							}
						}
					}
					*/
					
					inputWiget.disposeWiget(changeContainer);
					changeContainer.layout(inputWiget.createServiceCreateGroup(selectedItem, true));
					
				}
			}
		}
	}
	private class NewServiceItemAction extends Action{
		public NewServiceItemAction(){
			super(OPRoSStrings.getString("NewServiceTypeDlgContextMenu2"));
		}
		public void run(){
			if(selectedItem!=null){
				if(selectedItem.getParentItem().equals(rootItem)){
					/*
					OPRoSNewServiceTypePopup dlg = new OPRoSNewServiceTypePopup(getShell(),1,compEle);
					dlg.open();
					if(dlg.getReturnCode()==InputDialog.OK){
						if(!dlg.getName().isEmpty()&&!dlg.getType().isEmpty()){
							TreeItem item = new TreeItem(selectedItem,SWT.NONE);
							String str=dlg.getName()+OPRoSStrings.getString("ServiceSeper3")+dlg.getType();
							if(dlg.getCallType()!=null){
								str+=OPRoSStrings.getString("ServiceSeper2");
								str+=dlg.getCallType();
							}
							item.setText(str);
							selectedItem.getParentItem().setExpanded(true);
						}
					}
					*/
					inputWiget.disposeWiget(changeContainer);
					changeContainer.layout(inputWiget.createMethodCreateGroup(selectedItem, true));
				}
			}
		}
	}
	private class NewServiceParamItemAction extends Action{
		public NewServiceParamItemAction(){
			super(OPRoSStrings.getString("NewServiceTypeDlgContextMenu3"));
		}
		public void run(){
			if(selectedItem!=null){
				if(selectedItem.getParentItem().getParentItem().equals(rootItem)){
					/*
					OPRoSNewServiceTypePopup dlg = new OPRoSNewServiceTypePopup(getShell(),2,compEle);
					dlg.open();
					if(dlg.getReturnCode()==InputDialog.OK){
						if(!dlg.getName().isEmpty()&&!dlg.getType().isEmpty()){
							TreeItem item = new TreeItem(selectedItem,SWT.NONE);
							item.setText(dlg.getName()+
									OPRoSStrings.getString("ServiceSeper2")+
									dlg.getType());
							selectedItem.getParentItem().setExpanded(true);
						}
					}
					*/
					inputWiget.disposeWiget(changeContainer);
					changeContainer.layout(inputWiget.createParameterCreateGroup(selectedItem, true));
				}
			}
		}
	}
	private class NewServiceTypeDeleteItemAction extends Action{
		public NewServiceTypeDeleteItemAction(){
			super(OPRoSStrings.getString("NewServiceTypeDlgContextMenu4"));
		}
		public void run(){
			if(selectedItem!=null){
				if(!selectedItem.equals(rootItem)){
					selectedItem.removeAll();
					selectedItem.dispose();
				}
				else{
					selectedItem.removeAll();
				}
			}
		}
	}
	public Tree getServiceTypeTree() {
		return serviceTypeTree;
	}
	private boolean isExistItem(TreeItem parent, String itemName){
		int cnt = parent.getItemCount();
		for(int i=0;i<cnt;i++){
			if(parent.getItem(i).getText().compareTo(itemName)==0)
				return true;
		}
		return false;
	}
	public void setServiceTypeFileName(String str){
		serviceTypeFileNameText.setText(str);
	}
	public String getServiceTypeFileName(){
		return serviceTypeFileNameText.getText();
	}
	public void setEditableServiceTypeFileNameText(boolean bEnable){
		serviceTypeFileNameText.setEditable(bEnable);
	}
	private Menu getMenu(TreeItem item){
		popupMenu.removeAll();
		if(item.getParentItem()!=null){
			if(item.getParentItem().getParentItem()==null){
				popupMenu.add(new NewServiceItemAction());
				popupMenu.add(new NewServiceTypeDeleteItemAction());
			}else if(item.getParentItem().getParentItem().getParentItem()==null){
				popupMenu.add(new NewServiceParamItemAction());
				popupMenu.add(new NewServiceTypeDeleteItemAction());
			}else{
				popupMenu.add(new NewServiceTypeDeleteItemAction());
			}
		}else{
			popupMenu.add(new NewServiceTypeAction());
			popupMenu.add(new NewServiceTypeDeleteItemAction());
		}
		return popupMenu.createContextMenu(serviceTypeTree);
	}
	
	
	public Composite getChangeContainer() {
		return changeContainer;
	}
	
	/**
	 * 모델에 등록되어있는 데이터 타입을 구한다.
	 * @return
	 */
	public List<String> getTypeItem(boolean isVoid) {
		List<String> list = new ArrayList<String>();
		if(isVoid)
			list.add("void");
		
		if(atomicComponentModel!=null){
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
					list.add(ele.getAttributeValue(OPRoSStrings.getString("DataTypeEleAttr")));
				}
				}
			}
		}
		return list;
	}
	
	/**
	 * 해당 모델의 컴포넌트 모델을 구한다.
	 * @param model
	 * @return
	 */
	public AtomicComponentModel getAtomicComponentModel(AtomicComponentModel model) {
		if(model instanceof AtomicComponentModel)
			return (AtomicComponentModel)model;
		
//		if(model.getParent() != null) {			
//			return getComponentElementModel(model.getParent());
//		}		
		return null;
	}
}
