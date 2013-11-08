package kr.co.n3soft.n3com.project.dialog;

import java.util.ArrayList;

import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.comm.ClassModel;
import kr.co.n3soft.n3com.model.comm.UMLElementModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.comm.descriptor.TypeCellEditor;
import kr.co.n3soft.n3com.model.umlclass.OperationEditableTableItem;
import kr.co.n3soft.n3com.model.umlclass.ParameterEditableTableItem;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

/**
 *  20080721 KDI s 클래스 작성
 * @author Administrator
 *
 */
public class ParameterDialog extends Dialog {
	
	//	private TextArea descriptionField;
	
	Group Param;
	
	//	private TreeViewer viewer;
	ClassModel classModel = null;
	
	private TableViewer paramTableViewer;
	
	
	public java.util.ArrayList attributes = new ArrayList();
	public int paramCount = 0;

	Button buttonParamAdd = null;
	Button buttonParamRemove = null;
	
	//PKY 08072201 S 모든 오퍼레이션및 어트리뷰트등 정보 입력한 값에 대한 위치변경할 수있도록 수정
	Button buttonParamUp = null;
	Button buttonParamDown = null;
	//PKY 08072201 E 모든 오퍼레이션및 어트리뷰트등 정보 입력한 값에 대한 위치변경할 수있도록 수정
	
	OperationEditableTableItem oldSit = null;
	
	public OperationEditableTableItem operationEditableTableItem = null;
	
	public OperationEditableTableItem selectTableItem = null;
	
	private static final String NAME_PROPERTY = N3Messages.POPUP_NAME; 
	private static final String TYPE_PROPERTY = N3Messages.POPUP_TYPE; 
	

	public ParameterDialog(Shell parentShell) {
		super(parentShell);
		this.setShellStyle(SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE);
		//		if(ProjectManager.getInstance())
//		ProjectManager.getInstance().getSelectNodes();
//
//		if (ProjectManager.getInstance().getSelectPropertyUMLElementModel() != null) {
//			Object obj = ProjectManager.getInstance().getSelectPropertyUMLElementModel();
//			
//			UMLModel umModel = (UMLModel)obj;
//			if (umModel instanceof ClassifierModel) {
//				ClassifierModel classifierModel = (ClassifierModel)umModel;
//				classModel = classifierModel.getClassModel();				
//				attributes = (java.util.ArrayList) classModel.getOperations().clone();
//			}
//			else if(umModel instanceof ClassifierModelTextAttach){
//				ClassifierModelTextAttach classifierModel = (ClassifierModelTextAttach)umModel;
//				
//				classModel = classifierModel.getClassModel();				
//				attributes = (java.util.ArrayList) classModel.getOperations().clone();
//			}
//		}
	}
	
	protected Control createDialogArea(Composite parent) {
		Composite comp = (Composite)super.createDialogArea(parent);
		try {
			comp.getShell().setText(N3Messages.DIALOG_PARAMETERS);
			
			comp.getShell().setImage(ProjectManager.getInstance().getImage(101));//20080724 KDI s
			
			SashForm sf2 = new SashForm(parent, SWT.VERTICAL); 
			GridLayout layout2 = (GridLayout)comp.getLayout();
			layout2.numColumns = 2;
			FillLayout flowLayout2 = new FillLayout();
			sf2.setLayout(flowLayout2);
			GridData data6 = new GridData(GridData.FILL_BOTH);
//			data6.heightHint = 600;
			data6.widthHint = 600;
			sf2.setLayoutData(data6);

			GridLayout layout = (GridLayout)comp.getLayout();
			FillLayout flowLayout = new FillLayout();

			GridData data1 = new GridData(GridData.FILL_BOTH);
			data1.heightHint = 400;
			data1.widthHint = 400;
			
			GridData data3 = new GridData(GridData.FILL_BOTH);
			data3.horizontalSpan = 2;
			data3.heightHint = 300;
			data3.widthHint = 400;

			Param = new Group(sf2, SWT.SHADOW_ETCHED_IN);
			Param.setLayout(layout2);
			Param.setText(N3Messages.DIALOG_PARAMETERS);//2008040301 PKY S 
			Param.setLayoutData(data3);
			final Table tableParam = new Table(Param, SWT.FULL_SELECTION);
			data1 = new GridData(GridData.FILL_HORIZONTAL);
			data1.horizontalSpan = 2;
			data1.heightHint = 200;
			data1.widthHint = 400;
			tableParam.setLayoutData(data1);
			
			buttonParamAdd = new Button(Param,SWT.PUSH);
			buttonParamRemove = new Button(Param,SWT.PUSH);
			buttonParamAdd.setImage(ProjectManager.getInstance().getImage(203));
			buttonParamRemove.setImage(ProjectManager.getInstance().getImage(204));
					
			//PKY 08072201 S 모든 오퍼레이션및 어트리뷰트등 정보 입력한 값에 대한 위치변경할 수있도록 수정
			buttonParamUp = new Button(Param,SWT.PUSH);
			buttonParamDown= new Button(Param,SWT.PUSH);

			buttonParamUp.setImage(ProjectManager.getInstance().getImage(330));
			buttonParamDown.setImage(ProjectManager.getInstance().getImage(331));
			
			buttonParamUp.addSelectionListener(new SelectionListener() {
				public void widgetSelected(SelectionEvent e)
				{
					ArrayList arryList= new ArrayList();
					StructuredSelection iSelection = (StructuredSelection)paramTableViewer.getSelection();
					if(iSelection!=null){
					Object obj = iSelection.getFirstElement();
					Table table=paramTableViewer.getTable();
					for(int i=0; i < table.getItems().length; i++){
						if(table.getItem(i).getData()== obj){
							if(i>0)
							arryList.add(i-1,table.getItem(i).getData());
							else{
								arryList.add(table.getItem(i).getData());
							}
						}else{
							arryList.add(table.getItem(i).getData());
						}
					}
					paramTableViewer.setInput(arryList.toArray());
					}
				}
				public void widgetDefaultSelected(SelectionEvent e)
				{
				}
			});
			buttonParamDown.addSelectionListener(new SelectionListener() {
				public void widgetSelected(SelectionEvent e)
				{
					int chanage= 0;
					boolean ischanage=false;
					StructuredSelection iSelection = (StructuredSelection)paramTableViewer.getSelection();
					if(iSelection!=null){
					Object obj = iSelection.getFirstElement();
					Table table=paramTableViewer.getTable();
					java.util.Vector arryList= new java.util.Vector (table.getItems().length);
					for(int i=0; i < table.getItems().length; i++){
						if(table.getItem(i).getData()== obj){
							chanage=i;
							ischanage=true;
						}else{
							arryList.add(table.getItem(i).getData());
						}
						
					}
					if(ischanage==true){
						arryList.add(chanage+1,obj);
					}
//					removeNullArray(arryList);
					paramTableViewer.setInput(arryList.toArray());
					}
				}
				public void widgetDefaultSelected(SelectionEvent e)
				{
				}
			});
			//PKY 08072201 E 모든 오퍼레이션및 어트리뷰트등 정보 입력한 값에 대한 위치변경할 수있도록 수정
		
			buttonParamAdd.addSelectionListener(new SelectionListener() {

    			public void widgetSelected(SelectionEvent e)
    			{	
    				if(selectTableItem != null){
    					if(selectTableItem instanceof OperationEditableTableItem){
    						OperationEditableTableItem newItem = (OperationEditableTableItem)selectTableItem;    	    				
    	    				ParameterEditableTableItem p = new ParameterEditableTableItem("param" + paramCount, "String", "");
    	    				java.util.ArrayList list = newItem.getParams();
    	    				if(list == null){
    	    					list = new java.util.ArrayList();
    	    					list.add(p);
    	    					newItem.setParams(list);
    	    				}
    	    				else{
    	    					newItem.getParams().add(p);
    	    				}
    	    				
    	    				paramTableViewer.add(p);
    	    				paramCount++;
    					}
    				}   		           
    			}

    			public void widgetDefaultSelected(SelectionEvent e)
    			{
    			}

    		});
			
			buttonParamRemove.addSelectionListener(new SelectionListener() {

    			public void widgetSelected(SelectionEvent e)
    			{    				
    				int selectIndex=paramTableViewer.getTable().getSelectionIndex();
    				StructuredSelection iSelection = (StructuredSelection)paramTableViewer.getSelection();
    				Object obj = iSelection.getFirstElement();
    				ParameterEditableTableItem newItem = (ParameterEditableTableItem)obj;
					if (obj != null) {
						try{
							paramTableViewer.remove(obj);
							paramCount--;
						}
						catch(Exception e1){
							e1.printStackTrace();
						}

						obj = paramTableViewer.getTable().getItem(paramTableViewer.getTable().getItemCount()-1);
						if(obj instanceof TableItem ){													
							if(paramTableViewer.getTable().getItemCount()>=selectIndex+1){
								paramTableViewer.getTable().select(selectIndex);
							}else{
								paramTableViewer.getTable().select(paramTableViewer.getTable().getItemCount()-1);
							}							
						}

					}
    				
    			}

    			public void widgetDefaultSelected(SelectionEvent e)
    			{
    			}

    		});
			
			
			
			
			MenuManager popupMenu1 = new MenuManager();
			IAction newParamRowAction = new NewParamRowAction();
			IAction deleteParamRowAction = new DeleteParamRowAction();
//			popupMenu1.add(newParamRowAction);
//			popupMenu1.add(deleteParamRowAction);
			Menu menu1 = popupMenu1.createContextMenu(tableParam);
			tableParam.setMenu(menu1);
			paramTableViewer = buildAndLayoutParamTable(tableParam);
			attachContentProviderParam(paramTableViewer);
			attachLabelProviderParam(paramTableViewer);
			attachCellEditorsParam(paramTableViewer, tableParam);
			paramTableViewer.getTable().pack();
			
//			if(attributes.size()>0){
//
//				this.oldSit = (OperationEditableTableItem)attributes.get(0);
//				if(this.oldSit.desc!=null){					
//					OperationEditableTableItem newItem = (OperationEditableTableItem)oldSit;
//					for (int i = 0; i < newItem.getParams().size(); i++) {
//						ParameterEditableTableItem p = (ParameterEditableTableItem)newItem.getParams().get(i);
//						paramTableViewer.add(p);
//					}
//					paramTableViewer.getTable().select(0);
//					
//				}
//			}
			if(selectTableItem != null){
				if(selectTableItem instanceof OperationEditableTableItem){
					OperationEditableTableItem opTableItem = (OperationEditableTableItem)selectTableItem;
					if(opTableItem != null){
						if(opTableItem.getParams() != null){
							for(int i=0;i<opTableItem.getParams().size();i++){
								ParameterEditableTableItem p = (ParameterEditableTableItem)opTableItem.getParams().get(i);
								paramTableViewer.add(p);
							}
							paramTableViewer.getTable().select(0);
						}						
					}					
				}
			}
			//PKY 08082201 S 팀 프로젝트 읽기전용일때 수정불가능하도록 작업			
			if(ProjectManager.getInstance()!=null && ProjectManager.getInstance().getSelectPropertyUMLElementModel()!=null){
				UMLElementModel umlElementModel= ProjectManager.getInstance().getSelectPropertyUMLElementModel();
				if(!ProjectManager.getInstance().isItemEnable(ProjectManager.getInstance().getSelectPropertyUMLElementModel())){
					boolean isEnable = ProjectManager.getInstance().isItemEnable(ProjectManager.getInstance().getSelectPropertyUMLElementModel());
						buttonParamAdd.setEnabled(isEnable);
						buttonParamRemove.setEnabled(isEnable);
						buttonParamUp.setEnabled(isEnable);
						buttonParamDown.setEnabled(isEnable);


					}
				}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return comp;
	}

	protected void createButtonsForButtonBar(Composite parent) {
		super.createButtonsForButtonBar(parent);
		//		createButton(parent, RESET_ID, "Reset All", false);
	}

	protected void buttonPressed(int buttonId) {
		if(buttonId == IDialogConstants.OK_ID || buttonId == IDialogConstants.CANCEL_ID){
			OperationEditableTableItem newItem = (OperationEditableTableItem)selectTableItem;    	    				

			if(newItem.getParams() != null){
				newItem.getParams().clear();
				TableItem[] items = paramTableViewer.getTable().getItems();
				ArrayList list = new ArrayList();
				if(items != null){
					for(int i=0;i<items.length;i++){
						ParameterEditableTableItem p = (ParameterEditableTableItem)(items[i]).getData();
						list.add(p);						
					}
				}
				newItem.setParams(list);
			}
			super.buttonPressed(buttonId);
		}
//		else if(buttonId == IDialogConstants.CANCEL_ID){
//			
//		}
	}
	private class NewParamRowAction extends Action {
		public NewParamRowAction() {
			super("파라미터추가");
		}

		public void run() {
			try {
				if(selectTableItem != null){
					if(selectTableItem instanceof OperationEditableTableItem){
						OperationEditableTableItem newItem = (OperationEditableTableItem)selectTableItem;						
						ParameterEditableTableItem p = new ParameterEditableTableItem("param" + paramCount, "String", "");
	    				java.util.ArrayList list = newItem.getParams();
	    				if(list == null){
	    					list = new java.util.ArrayList();
	    					list.add(p);
	    					newItem.setParams(list);
	    				}
	    				else{
	    					newItem.getParams().add(p);
	    				}
						paramCount++;								
					}
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	private class DeleteParamRowAction extends Action {
		public DeleteParamRowAction() {
			super("파라미터삭제");
		}

		public void run() {
			StructuredSelection iSelection = (StructuredSelection)paramTableViewer.getSelection();
			Object obj = iSelection.getFirstElement();
			if (obj != null) {
				paramTableViewer.remove(obj);
			}
		}
	}


//	protected void buttonPressed(int buttonId) {
//		
//		if(buttonId == IDialogConstants.OK_ID){
//			System.out.println("abc");
//		}
////		if (buttonId == RESET_ID) {
////			usernameField.setText("");
////			passwordField.setText("");
////		}
////		else {
////			if (buttonId == this.OK_ID) {
////				TableItem[] tableItem = tableViewer.getTable().getItems();
////				
////				this.attributes.clear();
////				for (int i = 0; i < tableItem.length; i++) {
////					this.attributes.add(tableItem[i].getData());
////					
////					//					classModel.setAttributes((AttributeEditableTableItem)tableItem[i].getData());
////				}
////				int opCount = attributes.size();
////				int attrCount = classModel.getAttributes().size();
////				
////				
////				
////				
////				
////				classModel.setOperations(this.attributes);
////				
////				 if(oldSit!=null)
////	                	oldSit.desc = "";
////			}
////			else{
////				classModel.setOperationCount(this.operCount);
////			}
////			ProjectManager.getInstance().autoSize(classModel);
//			super.buttonPressed(buttonId);
////		}
//	}

	private void attachLabelProviderParam(TableViewer viewer) {
		viewer.setLabelProvider(
				new ITableLabelProvider() {
					public Image getColumnImage(Object element, int columnIndex) {
						return null;
					}
					public String getColumnText(Object element, int columnIndex) {
						switch (columnIndex) {
						case 0:
							String name = ((ParameterEditableTableItem)element).name;
							return name;
						case 1:
							String stype =  ((ParameterEditableTableItem)element).stype;
							return stype;
//						case 2:
//							String initValue = ((ParameterEditableTableItem)element).defalut;
//							return initValue;
//						case 3:
//							 initValue = ((ParameterEditableTableItem)element).desc;
//							return initValue;
						default:
							return "Invalid column: " + columnIndex;
						}
					}
					public void addListener(ILabelProviderListener listener) {
					}
					public void dispose() {
					}
					public boolean isLabelProperty(Object element, String property) {
						return false;
					}
					public void removeListener(ILabelProviderListener lpl) {
					}
				});
	}


	private void attachContentProviderParam(TableViewer viewer) {
		viewer.setContentProvider(
				new IStructuredContentProvider() {
					public Object[] getElements(Object inputElement) {
						return (Object[]) inputElement;
					}
					public void dispose() {
					}
					public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
					}
				});
	}

	private TableViewer buildAndLayoutParamTable(final Table table) {
		TableViewer tableViewer = new TableViewer(table);
		TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnWeightData(50, 200, true));
		layout.addColumnData(new ColumnWeightData(50, 250, true));
		
		table.setLayout(layout);
		TableColumn accessColumn = new TableColumn(table, SWT.CENTER);
		accessColumn.setText(this.NAME_PROPERTY);
		TableColumn nameColumn = new TableColumn(table, SWT.CENTER);
		nameColumn.setText(this.TYPE_PROPERTY);
		
		table.setHeaderVisible(true);
		return tableViewer;
	}


	private void attachCellEditorsParam(final TableViewer viewer, Composite parent) {
		viewer.setCellModifier(
				new ICellModifier() {
					public boolean canModify(Object element, String property) {
						return true;
					}
					public Object getValue(Object element, String property) {
						if (NAME_PROPERTY.equals(property))
							return ((ParameterEditableTableItem)element).name;
						else if (TYPE_PROPERTY.equals(property))
							return ((ParameterEditableTableItem)element).stype;						
						return null;
						//				else
							//					return ((AttributeEditableTableItem)element).initValue;
					}
					public void modify(Object element, String property, Object value) {
						TableItem tableItem = (TableItem)element;
						ParameterEditableTableItem data = (ParameterEditableTableItem)tableItem.getData();

						//PKY 08082201 S 팀 프로젝트 읽기전용일때 수정불가능하도록 작업
						if(ProjectManager.getInstance()!=null && ProjectManager.getInstance().getSelectPropertyUMLElementModel()!=null)
							if(!ProjectManager.getInstance().isItemEnable(ProjectManager.getInstance().getSelectPropertyUMLElementModel()))
								return;
						if (NAME_PROPERTY.equals(property))
							data.name = value.toString();
						else if (TYPE_PROPERTY.equals(property)){					
							data.stype = value.toString();
						}		
						viewer.refresh(data);
					}
				});
		viewer.setCellEditors(
				new CellEditor[] {
						new TextCellEditor(parent), new TypeCellEditor(parent)
				});
		viewer.setColumnProperties(
				new String[] {
						NAME_PROPERTY, TYPE_PROPERTY
				});
	}
	
	public void setOperationEditableTableItem(OperationEditableTableItem item){
		operationEditableTableItem = item;
	}
	
	public OperationEditableTableItem getOperationEditableTableItem(){
		return operationEditableTableItem;
	}
}
