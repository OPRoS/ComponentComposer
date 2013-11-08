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

import kr.co.n3soft.n3com.OPRoSStrings;
import kr.co.n3soft.n3com.OPRoSUtil;
import kr.co.n3soft.n3com.model.comm.PortModel;
import kr.co.n3soft.n3com.model.component.AtomicComponentModel;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public class OPRoSDataTypeInputDialogComposite extends Composite {
	
	protected Tree dataTypeTree;
	protected TreeItem rootItem;
	protected TreeItem selectedItem;
	protected MenuManager popupMenu;
	protected Text dataTypeFileNameText;
	
	protected Label messageLabel;
	protected boolean canModify=true;
	protected AtomicComponentModel atomicComponentModel;
	private PortModel pm = null;
	
	public OPRoSDataTypeInputDialogComposite(Composite parent, int style, int column, int gridStyle, 
			boolean canModify,PortModel _pm) {
		super(parent, style);
		this.canModify=canModify;
		pm = _pm;
		if(pm!=null){
			Object obj = pm.getPortContainerModel();
			if(obj instanceof AtomicComponentModel){
				atomicComponentModel = (AtomicComponentModel)obj;
			}
		}
		this.setFont(parent.getFont());
        GridLayout layout = new GridLayout();
        layout.numColumns = column;
        this.setLayout(layout);
        this.setLayoutData(new GridData(gridStyle));
        popupMenu = new MenuManager();
        createDataTypeGroup(this);
	}
	protected void createDataTypeGroup(Composite parent){
		Group compGroup = OPRoSUtil.createGroup(parent, SWT.NONE, 
				OPRoSStrings.getString("DataTypeInputDlgCompGroup"), 1, GridData.BEGINNING);
		if(dataTypeTree==null)
			dataTypeTree = new Tree(compGroup,SWT.VIRTUAL|SWT.BORDER);
        GridData gd= new GridData(SWT.SINGLE|SWT.BORDER);
        gd.widthHint=300;
        gd.heightHint=400;
        gd.horizontalSpan=3;
        dataTypeTree.setLayoutData(gd);
        rootItem = new TreeItem(dataTypeTree,SWT.NONE);
        rootItem.setText(OPRoSStrings.getString("DataTypeRoot"));
        selectedItem = rootItem;
        dataTypeTree.deselectAll();
        dataTypeTree.setLinesVisible(true);
        rootItem.setExpanded(true);
        if(canModify){
        	dataTypeTree.addMouseListener(new MouseListener(){
			
				public void mouseDoubleClick(MouseEvent arg0) {}
			
				public void mouseDown(MouseEvent e) {
					if(e.button==3){
						selectedItem = ((Tree)e.getSource()).getItem(new Point(e.x,e.y));
						dataTypeTree.setMenu(getMenu(selectedItem));
					}
				}
			
				public void mouseUp(MouseEvent arg0) {}
        		
        	});
        }
        OPRoSUtil.createLabel(compGroup, OPRoSStrings.getString("FileNameLabel"),SWT.NONE,1,0,1,0,GridData.BEGINNING);
        dataTypeFileNameText=OPRoSUtil.createText(compGroup, SWT.BORDER|SWT.SINGLE,1,50,1,0,200,0,GridData.BEGINNING);
        if(!canModify)
        	dataTypeFileNameText.setEnabled(false);
        else{
        	dataTypeFileNameText.addModifyListener(new ModifyListener(){
			
				public void modifyText(ModifyEvent evt) {
					if(OPRoSUtil.isDuplicateDataTypeName(dataTypeFileNameText.getText(),false,atomicComponentModel)){
						messageLabel.setText(OPRoSStrings.getString("DataTypeFileNameDuplicationError"));
					}else{
						messageLabel.setText("");
					}
				}
        	});
        }
        messageLabel =  OPRoSUtil.createLabel(compGroup, "                                        ",
        		SWT.NONE,2,0,1,0,GridData.BEGINNING);
        messageLabel.setForeground(ColorConstants.red);
	}
	public TreeItem getRootItem() {
		return rootItem;
	}
	public void setRootItem(TreeItem rootItem) {
		this.rootItem = rootItem;
	}
	private class NewDataTypeAction extends Action{
		public NewDataTypeAction(){
			super(OPRoSStrings.getString("NewDataTypeAction"));
		}
		public void run(){
			if(selectedItem!=null){
				if(selectedItem.equals(rootItem)){
					InputDialog dlg = new InputDialog(getShell(),
							OPRoSStrings.getString("NewDataTypeDlgTitle"),
							OPRoSStrings.getString("NewDataTypeDlgLabel"),
							OPRoSStrings.getString("NewDataTypeDlgDefaultValue"),null);
					dlg.open();
					if(!dlg.getValue().isEmpty()&&dlg.getReturnCode()==InputDialog.OK){
						if(!isExistItem(rootItem,dlg.getValue())){
							TreeItem item = new TreeItem(rootItem,SWT.NONE);
							item.setText(dlg.getValue());
							rootItem.setExpanded(true);
						}
						else{
							OPRoSUtil.openMessageBox(OPRoSStrings.getString("DuplicationErrorMessage"), SWT.OK|SWT.ICON_WARNING);
						}
					}
				}
			}
		}
	}
	private class NewDataTypeItemAction extends Action{
		public NewDataTypeItemAction(){
			super(OPRoSStrings.getString("NewDataTypeItemAction"));
		}
		public void run(){
			if(selectedItem!=null){
				if(!selectedItem.equals(rootItem)){
					OPRoSNewDataTypeItemPopup dlg=null;
					dlg = new OPRoSNewDataTypeItemPopup(getShell());
					dlg.open();
					if(dlg.getReturnCode()==InputDialog.OK){
						if(!dlg.getName().isEmpty()&&!dlg.getType().isEmpty()){
							if(!isExistItem(selectedItem,dlg.getName())){
								TreeItem item = new TreeItem(selectedItem,SWT.NONE);
								item.setText(dlg.getName()+OPRoSStrings.getString("DataTypeSubEleSeperator")+dlg.getType());
								selectedItem.setExpanded(true);
							}
							else{
								OPRoSUtil.openMessageBox(OPRoSStrings.getString("DuplicationErrorMessage"), SWT.OK|SWT.ICON_WARNING);
							}
						}
					}
				}
			}
		}
	}
	private class NewDataTypeDeleteItemAction extends Action{
		public NewDataTypeDeleteItemAction(){
			super(OPRoSStrings.getString("DelDataTypeItemAction"));
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
	public Tree getDataTypeTree() {
		return dataTypeTree;
	}
	public void setDataTypeTree(Tree tree){
		dataTypeTree=tree;
		this.redraw();
	}
	private boolean isExistItem(TreeItem parent, String itemName){
		int cnt = parent.getItemCount();
		for(int i=0;i<cnt;i++){
			if(parent.getItem(i).getText().compareTo(itemName)==0)
				return true;
		}
		return false;
	}
	public void setDataTypeFileName(String str){
		dataTypeFileNameText.setText(str);
	}
	public String getDataTypeFileName(){
		return dataTypeFileNameText.getText();
	}
	public void setEditableDataTypeFileNameText(boolean bEnable){
		dataTypeFileNameText.setEditable(bEnable);
	}
	private Menu getMenu(TreeItem item){
		popupMenu.removeAll();
		if(item.getParentItem()!=null){
			if(item.getParentItem().getParentItem()!=null){
				popupMenu.add(new NewDataTypeDeleteItemAction());
			}else{
				popupMenu.add(new NewDataTypeItemAction());
				popupMenu.add(new NewDataTypeDeleteItemAction());
			}
		}else{
			popupMenu.add(new NewDataTypeAction());
			popupMenu.add(new NewDataTypeDeleteItemAction());
		}
		return popupMenu.createContextMenu(dataTypeTree);
	}
}
