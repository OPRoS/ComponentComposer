package kr.co.n3soft.n3com.project.dialog;

import java.util.ArrayList;

import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.comm.LineModel;
import kr.co.n3soft.n3com.model.communication.MessageCommunicationModel;
import kr.co.n3soft.n3com.model.umlclass.AttributeEditableTableItem;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.DrillDownAdapter;

//PKY 08070301 S Communication Dialog 추가작업

public class CommunicationMessagePropertyDialog extends Dialog {
	private static final int RESET_ID = IDialogConstants.NO_TO_ALL_ID + 1;
	private static final int OK_ID = 0;
	private static final int CANCEL_ID = 1;
	private Text usernameField;
	private Text passwordField;
	//	private TextArea descriptionField;
	private DrillDownAdapter drillDownAdapter;
	private String diagramName = "";
	private int diagramType = -1;
	public boolean isOK = false;
	private Text diagramNameField;
	ListViewer selectFrom = null;
	ListViewer diagramTypes = null;
	Group type;
	Group description;
	StringBuffer descClass;
	Label descriptionField = null;
	//	private TreeViewer viewer;
	private TableViewer tableViewer;
	private StyledText styleField;
	boolean isActor=false; //2008040201 PKY S 추가 
	LineModel lineModel= null;
//	Button buttonAdd = null;
//	Button buttonRemove = null;

	int attrCount = 0;
	AttributeEditableTableItem oldSit = null;
	public java.util.ArrayList attributes = new ArrayList();

	private static final Object[] CONTENT = new Object[] {
		new AttributeEditableTableItem(new Integer(0), "ddd", new Integer(0), "ff"),
		new AttributeEditableTableItem(new Integer(1), "sss", new Integer(0), "dfd")
	};

	private static final String[] VALUE_SET = new String[] {
		"xxx", "yyy", "zzz"
	};

	public static final String[] SIGNATURE_SET = new String[] {
		"false","true" 
	};

	public static final String[] SYNCH_SET = new String[] {
		"Synchronous", "Asynchronous"
	};
	
	public static final String[] KIND_SET = new String[] {
		"Call", "Signal"
	};
	
	public static final String[] LIFECYCLE_SET = new String[] {
		"New", "Delete"
	};

	public static final String NAME_PROPERTY = N3Messages.POPUP_NAME;//2008040302 PKY S 
	public static final String SIGNATURE_PROPERTY = "Signature";//2008040302 PKY S 
	public static final String SYNCH_PROPERTY = "Synch";//2008040302 PKY S 
	public static final String KIND_PROPERTY ="Kind";//2008040302 PKY S 
	public static final String LIFECYCLE_PROPERTY = "LifeCycle";//PKY 08060201 S
//	private static final String STATIC_PROPERTY = "Static";
	public static final String CONST_PROPERTY = N3Messages.DIALOG_CONST;//PKY 08060201 S

	public CommunicationMessagePropertyDialog(Shell parentShell) {
		super(parentShell);
		ProjectManager.getInstance();
		this.setShellStyle(SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE);
		//		if(ProjectManager.getInstance())
		ProjectManager.getInstance().getSelectNodes();
		System.out.println("dddd==>" + ProjectManager.getInstance().getSelectNodes());
		if (ProjectManager.getInstance().getSelectPropertyUMLElementModel() != null) {
			Object obj = ProjectManager.getInstance().getSelectPropertyUMLElementModel();
//			Object obj = ProjectManager.getInstance().getSelectNodes().get(0);
			//			if(obj instanceof FinalClassEditPart){
//			UMLEditPart classEditPart = (UMLEditPart)obj;
			LineModel umModel = (LineModel)obj;
			lineModel=umModel;
			System.out.print("");
//			ArrayList aa = (ArrayList)umModel.getMiddleLneTextModel().getLineTextm().getLineTextModels();
			
			//PKY 08070701 S 커뮤니케이션 모양 변경
			for(int i = 0; i < umModel.getMiddleLineTextModel().getLineTextm().getLineTextModels().size(); i++){
				attributes.add(((MessageCommunicationModel)umModel.getMiddleLineTextModel().getLineTextm().getLineTextModels().get(i)).clone());
			}
			
			System.out.print("");

		}

	}

	public CommunicationMessagePropertyDialog(Shell parentShell, java.util.ArrayList p) {
		super(parentShell);
		this.setShellStyle(SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE);
		this.attributes = p;
	}

	public void initDesc() {
	}

	protected Control createDialogArea(Composite parent) {
		Composite comp = (Composite)super.createDialogArea(parent);
		try {
			comp.getShell().setText(N3Messages.POPUP_EDIT_MESSAGE);//PKY 08072201 S오퍼레이션 어트리뷰트 아이콘 삽입
			SashForm sf = new SashForm(parent, SWT.HORIZONTAL);
			GridLayout layout = (GridLayout)comp.getLayout();
			FillLayout flowLayout = new FillLayout();
			sf.setLayout(flowLayout);
			GridData data5 = new GridData(GridData.FILL_BOTH);
			data5.heightHint = 300;
			data5.widthHint = 600;
			sf.setLayoutData(data5);
			layout.numColumns = 2;
			GridData data1 = new GridData(GridData.FILL_BOTH);
//			data1.horizontalSpan = 2;
			data1.heightHint = 300;
			data1.widthHint = 500;

			type = new Group(sf, SWT.SHADOW_ETCHED_IN);
			type.setLayout(layout);
			type.setText(N3Messages.POPUP_ATTRIBUTES);//2008040302 PKY S 
			type.setLayoutData(data1);
			final Table table = new Table(type, SWT.FULL_SELECTION);
			tableViewer = buildAndLayoutTable(table);

			data1 = new GridData(GridData.FILL_HORIZONTAL);
			data1.horizontalSpan = 2;
			data1.heightHint = 200;
			data1.widthHint = 400;
			table.setLayoutData(data1);


			attachContentProvider(tableViewer);
			attachLabelProvider(tableViewer);
			attachCellEditors(tableViewer, table);
			MenuManager popupMenu = new MenuManager();
			IAction newRowAction = new NewRowAction();
			IAction DeleteRowAction = new DeleteRowAction();
			//PKY 08060201 S 팝업으로 Type결정하도록 수정
			IAction typeStringAction= new TypeStringAction();
			IAction typeByteAction= new TypeByteAction();
			IAction typeCharAction= new TypeCharAction();
			IAction typeDoubleAction= new TypeDoubleAction();
			IAction typeFloatAction= new TypeFloatAction();
			IAction typeIntAction= new TypeIntAction();
			IAction typeLongAction= new TypeLongAction();
			IAction typeShortAction= new TypeShortAction();
			//PKY 08060201 E 팝업으로 Type결정하도록 수정
//			popupMenu.add(newRowAction);
//			popupMenu.add(DeleteRowAction);
			//PKY 08060201 S 팝업으로 Type결정하도록 수정
//			popupMenu.add(typeStringAction);
//			popupMenu.add(typeByteAction);
//			popupMenu.add(typeCharAction);
//			popupMenu.add(typeDoubleAction);
//			popupMenu.add(typeFloatAction);
//			popupMenu.add(typeIntAction);
//			popupMenu.add(typeLongAction);
//			popupMenu.add(typeShortAction);	

			//PKY 08060201 E 팝업으로 Type결정하도록 수정
			Menu menu = popupMenu.createContextMenu(table);
			table.setMenu(menu);
			tableViewer.getTable().pack();
			tableViewer.setInput(attributes.toArray());
	

//			buttonAdd = new Button(type,SWT.PUSH);
			table.addSelectionListener(new SelectionListener() {

				public void widgetSelected(SelectionEvent e)
				{
					System.out.println("1");
					System.out.println("2");

					if(e.item.getData() instanceof AttributeEditableTableItem){
						System.out.println("11");
						AttributeEditableTableItem sit = (AttributeEditableTableItem)e.item.getData();
						if(oldSit==null){
							System.out.println("12");
							oldSit = sit;
							styleField.setText(sit.desc);
							System.out.println("13");

						}
						else{
							oldSit.desc = styleField.getText();
							oldSit = sit;
							styleField.setText(sit.desc);

						}


					}

				}

				public void widgetDefaultSelected(SelectionEvent e)
				{
					System.out.println("");
				}

			});
//			buttonAdd.setImage(ProjectManager.getInstance().getImage(203));
//			buttonAdd.addSelectionListener(new SelectionListener() {
//
//				public void widgetSelected(SelectionEvent e)
//				{
//					ISelection iSelection = tableViewer.getSelection();
//					AttributeEditableTableItem newItem;//2008040201 PKY S 수정 
//					//AttributeEditableTableItem newItem=new AttributeEditableTableItem(new Integer(0), "attribute" +classModel.addAttributeCount() , "String", "");
//					
//					//2008040201 PKY S 추가
//					if(isActor==false){
//					  //PKY 08060201 S 저장 불러오기 할경우 어트리뷰트,오퍼레이션 생성 이름 넘버러링이 초기화 되는문제
//
//						newItem=new AttributeEditableTableItem(new Integer(0), "attribute" +classModel.addAttributeCount() , "String", "");
//						//PKY 08060201 E 저장 불러오기 할경우 어트리뷰트,오퍼레이션 생성 이름 넘버러링이 초기화 되는문제
//					}else{
//						newItem=new AttributeEditableTableItem(new Integer(0), "attribute" +actorModel.addAttributeCount() , "String", "");
//					}
//					//2008040201 PKY E 추가
//					tableViewer.add(newItem);
////					attrCount++;
//
//				}
//
//				public void widgetDefaultSelected(SelectionEvent e)
//				{
//				}
//
//			});

//			buttonRemove = new Button(type,SWT.PUSH);
//			buttonRemove.addSelectionListener(new SelectionListener() {
//
//				public void widgetSelected(SelectionEvent e)
//				{
//					StructuredSelection iSelection = (StructuredSelection)tableViewer.getSelection();
//					Object obj = iSelection.getFirstElement();
//					int selectIndex=tableViewer.getTable().getSelectionIndex();//PKY 08070101 S 어트리뷰트,오퍼레이션,파라미터 삭제할때 삭제 한 후 선택이 계속 머물 수 있도록 수정
//					if (obj != null) {
//						tableViewer.remove(obj);
//						classModel.downAttributeCount();
//						
//	   					//PKY 08070101 S 어트리뷰트,오퍼레이션,파라미터 삭제할때 삭제 한 후 선택이 계속 머물 수 있도록 수정
//    					if(tableViewer.getTable().getItemCount()>=selectIndex+1){
//    						tableViewer.getTable().select(selectIndex);
//    					}else{
//    						tableViewer.getTable().select(tableViewer.getTable().getItemCount()-1);
//    					}
//    					//PKY 08070101 E 어트리뷰트,오퍼레이션,파라미터 삭제할때 삭제 한 후 선택이 계속 머물 수 있도록 수정
//						
//						
//					}
//
//				}
//
//				public void widgetDefaultSelected(SelectionEvent e)
//				{
//				}
//
//			});
//			buttonAdd.setText("Add");
//			buttonRemove.setImage(ProjectManager.getInstance().getImage(204));



			type.pack();
			GridData data2 = new GridData(GridData.FILL_BOTH);
			data2.heightHint = 300;
			data2.widthHint = 200;
//			description = new Group(sf, SWT.SHADOW_ETCHED_IN);
//			description.setText(N3Messages.POPUP_DESCRIPTION);//2008040302 PKY S 
//			description.setLayoutData(data2);
//			description.setLayout(flowLayout);
//			styleField = new StyledText(description, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
//			styleField.pack();
//			description.pack();

//			if(attributes.size()>0){
//
//				this.oldSit = (AttributeEditableTableItem)attributes.get(0);
//				if(this.oldSit.desc!=null){
//					table.select(0);
//					styleField.setText(this.oldSit.desc);
//				}
//			}
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

	private class NewRowAction extends Action {
		public NewRowAction() {
			super(N3Messages.POPUP_ATTRIBUTES+N3Messages.POPUP_ADD);//2008040302 PKY S 
		}

		public void run() {
			ISelection iSelection = tableViewer.getSelection();
			AttributeEditableTableItem newItem = new
			AttributeEditableTableItem(new Integer(0), "attribute" + attrCount, "String", "");
			tableViewer.add(newItem);
			attrCount++;
		}
	}


	private class DeleteRowAction extends Action {
		public DeleteRowAction() {
			super(N3Messages.POPUP_ATTRIBUTES+N3Messages.POPUP_REMOVE);//2008040302 PKY S 
		}

		public void run() {
			StructuredSelection iSelection = (StructuredSelection)tableViewer.getSelection();
			Object obj = iSelection.getFirstElement();
			if (obj != null) {
				tableViewer.remove(obj);
			}
			//			tableViewer.getSelection()
			//			tableViewer.remove(element)
		}
	}
	//PKY 08060201 S 팝업으로 Type결정하도록 수정
	private class TypeStringAction extends Action {
		public TypeStringAction() {
			super("Type:String");//2008040302 PKY S 
			}

		public void run() {
			StructuredSelection iSelection = (StructuredSelection)tableViewer.getSelection();
			Object obj = iSelection.getFirstElement();
			if (obj != null) {
				((AttributeEditableTableItem)obj).setStype("string");
				tableViewer.refresh(obj);
			}
			//			tableViewer.getSelection()
			//			tableViewer.remove(element)
		}
	}
	private class TypeBooleanAction extends Action {
		public TypeBooleanAction() {
			super("Type:Boolean");//2008040302 PKY S 
			}

		public void run() {
			StructuredSelection iSelection = (StructuredSelection)tableViewer.getSelection();
			Object obj = iSelection.getFirstElement();
			if (obj != null) {
				((AttributeEditableTableItem)obj).setStype("boolean");
				tableViewer.refresh(obj);
			}
		}
	}
	private class TypeByteAction extends Action {
		public TypeByteAction() {
			super("Type:Byte");//2008040302 PKY S 
			}

		public void run() {
			StructuredSelection iSelection = (StructuredSelection)tableViewer.getSelection();
			Object obj = iSelection.getFirstElement();
			if (obj != null) {
				((AttributeEditableTableItem)obj).setStype("byte");
				tableViewer.refresh(obj);
			}
		}
	}
	private class TypeCharAction extends Action {
		public TypeCharAction() {
			super("Type:Char");//2008040302 PKY S 
			}

		public void run() {
			StructuredSelection iSelection = (StructuredSelection)tableViewer.getSelection();
			Object obj = iSelection.getFirstElement();
			if (obj != null) {
				((AttributeEditableTableItem)obj).setStype("char");
				tableViewer.refresh(obj);
			}
		}
	}
	private class TypeDoubleAction extends Action {
		public TypeDoubleAction() {
			super("Type:Double");//2008040302 PKY S 
			}

		public void run() {
			StructuredSelection iSelection = (StructuredSelection)tableViewer.getSelection();
			Object obj = iSelection.getFirstElement();
			if (obj != null) {
				((AttributeEditableTableItem)obj).setStype("double");
				tableViewer.refresh(obj);
			}
		}
	}
	private class TypeFloatAction extends Action {
		public TypeFloatAction() {
			super("Type:Float");//2008040302 PKY S 
			}

		public void run() {
			StructuredSelection iSelection = (StructuredSelection)tableViewer.getSelection();
			Object obj = iSelection.getFirstElement();
			if (obj != null) {
				((AttributeEditableTableItem)obj).setStype("float");
				tableViewer.refresh(obj);
			}
		}
	}
	private class TypeIntAction extends Action {
		public TypeIntAction() {
			super("Type:Int");//2008040302 PKY S 
			}

		public void run() {
			StructuredSelection iSelection = (StructuredSelection)tableViewer.getSelection();
			Object obj = iSelection.getFirstElement();
			if (obj != null) {
				((AttributeEditableTableItem)obj).setStype("int");
				tableViewer.refresh(obj);
			}
		}
	}
	private class TypeLongAction extends Action {
		public TypeLongAction() {
			super("Type:Long");//2008040302 PKY S 
			}

		public void run() {
			StructuredSelection iSelection = (StructuredSelection)tableViewer.getSelection();
			Object obj = iSelection.getFirstElement();
			if (obj != null) {
				((AttributeEditableTableItem)obj).setStype("long");
				tableViewer.refresh(obj);
			}
		}
	}
	private class TypeShortAction extends Action {
		public TypeShortAction() {
			super("Type:Short");//2008040302 PKY S 
			}

		public void run() {
			StructuredSelection iSelection = (StructuredSelection)tableViewer.getSelection();
			Object obj = iSelection.getFirstElement();
			if (obj != null) {
				((AttributeEditableTableItem)obj).setStype("short");
				tableViewer.refresh(obj);
			}
		}
	}
	//PKY 08060201 E 팝업으로 Type결정하도록 수정
	protected void buttonPressed(int buttonId) {
		if (buttonId == RESET_ID) {
			usernameField.setText("");
			passwordField.setText("");
		}
		else {
			if (buttonId == this.OK_ID) {
				TableItem[] tableItem = tableViewer.getTable().getItems();
				this.attributes.clear();
				for(int i =0; i < lineModel.getMiddleLineTextModel().getMessageSize(); i++){
					MessageCommunicationModel msg=(MessageCommunicationModel)this.lineModel.getMiddleLineTextModel().getMessageCommunicationModel(i);
					MessageCommunicationModel msg1=(MessageCommunicationModel)tableItem[i].getData();
					String name=msg1.getName();
					if(name.indexOf(":")>-1){
					msg.setName(name.substring(name.indexOf(":")+1, name.length()));
					}
					else{
						msg.setName(msg1.getName());
					}
					msg.signature=msg1.signature;
					msg.synch=msg1.synch;
					msg.kind=msg1.kind;
					msg.lifecycle=msg1.lifecycle;					
				}
				lineModel.getMiddleLineTextModel().setLocation(new Point(lineModel.getMiddleLineTextModel().getLocation())); //PKY 08070701 S 리플래쉬 안되는문제 수정
//				for (int i = 0; i < tableItem.length; i++) {
//					this.attributes.add();
//					//					classModel.setAttributes((AttributeEditableTableItem)tableItem[i].getData());
//				}
//				lineModel.getMiddleLineTextModel().getLineTextm().setLineTextModels(this.attributes);
//				if(oldSit!=null)
//					oldSit.desc = styleField.getText();
//				//2008040201 PKY S 추가 
//				if(isActor==false)
//					classModel.setAttributes(this.attributes);
//				if(isActor==false)
//					//2008040201 PKY E 추가 
//					ProjectManager.getInstance().autoSize(classModel);

				//				System.out.println("tableItem:"+tableItem[0].getData());
			}
			else{
//				attrCount
				//2008040201 PKY E 추가 
//				if(isActor==false)
//				classModel.setAttributeCount(attrCount);
//				else
//					actorModel.setAttributeCount(attrCount);
			}
			isActor=false;//2008040201 PKY S 추가 
			super.buttonPressed(buttonId);
		}
	}

	private void attachLabelProvider(TableViewer viewer) {
		viewer.setLabelProvider(
				new ITableLabelProvider() {
					public Image getColumnImage(Object element, int columnIndex) {
						return null;
					}
					public String getColumnText(Object element, int columnIndex) {
						switch (columnIndex) {
						case 0:
//							Number index = ((MessageCommunicationModel)element).n
							
							String name = ((MessageCommunicationModel)element).getName();
							//PKY 08070701 S
							if(name.indexOf(":")>-1){
								name=name.substring(name.indexOf(":")+1, name.length());
								}
							//PKY 08070701 E

							return name;
						case 1:
								return SIGNATURE_SET[((MessageCommunicationModel)element).signature];
								
						case 2:
								return SYNCH_SET[((MessageCommunicationModel)element).synch];

						case 3:
//							String initValue = ((MessageCommunicationModel)element).initValue;							if(((MessageCommunicationModel)element).getUMLDataModel().getElementProperty(KIND_PROPERTY)!=null)
								return KIND_SET[((MessageCommunicationModel)element).kind];

						case 4:
								return LIFECYCLE_SET[((MessageCommunicationModel)element).lifecycle];


						default:
							return KIND_SET[0];
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

	private void attachContentProvider(TableViewer viewer) {
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

	private TableViewer buildAndLayoutTable(final Table table) {
		TableViewer tableViewer = new TableViewer(table);
		TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnWeightData(50, 75, true));
		layout.addColumnData(new ColumnWeightData(50, 75, true));
		layout.addColumnData(new ColumnWeightData(50, 75, true));
		layout.addColumnData(new ColumnWeightData(50, 75, true));
		layout.addColumnData(new ColumnWeightData(50, 75, true));
//		layout.addColumnData(new ColumnWeightData(50, 75, true));
//		layout.addColumnData(new ColumnWeightData(50, 75, true));
		table.setLayout(layout);
		TableColumn nameColumn = new TableColumn(table, SWT.CENTER);
		nameColumn.setText(this.NAME_PROPERTY);
		nameColumn.setWidth(500);
		TableColumn accessColumn = new TableColumn(table, SWT.CENTER);	
		accessColumn.setText(this.SIGNATURE_PROPERTY);
		TableColumn typeColumn = new TableColumn(table, SWT.CENTER);
		typeColumn.setText(this.SYNCH_PROPERTY);
		TableColumn initColumn = new TableColumn(table, SWT.CENTER);
		initColumn.setText(this.KIND_PROPERTY);
		TableColumn derivedColumn = new TableColumn(table, SWT.CENTER);
		derivedColumn.setText(this.LIFECYCLE_PROPERTY);
//		TableColumn staticColumn = new TableColumn(table, SWT.CENTER);
//		staticColumn.setText(this.STATIC_PROPERTY);
		table.setHeaderVisible(true);
		return tableViewer;
	}

	private void attachCellEditors(final TableViewer viewer, Composite parent) {
		viewer.setCellModifier(
				new ICellModifier() {
					public boolean canModify(Object element, String property) {
						return true;
					}
					public Object getValue(Object element, String property) {
						if (NAME_PROPERTY.equals(property)){
							if(((MessageCommunicationModel)element).getName()!=null){
								String name =((MessageCommunicationModel)element).getName();
								if(name.indexOf(":")>-1){
									name=name.substring(name.indexOf(":")+1, name.length());
									}
								//PKY 08070701 E
							return name;
							}
							return null;
						}else if (SIGNATURE_PROPERTY.equals(property)){
							return ((MessageCommunicationModel)element).signature;
							
						}else if (SYNCH_PROPERTY.equals(property)){
							return ((MessageCommunicationModel)element).synch;


						}else if(KIND_PROPERTY.equals(property)){
							return ((MessageCommunicationModel)element).kind;
						}else if(LIFECYCLE_PROPERTY.equals(property)){
							return ((MessageCommunicationModel)element).lifecycle;
		
						}
//						else if(STATIC_PROPERTY.equals(property))
//						return ((AttributeEditableTableItem)element).isStatic;
						return null;
					}
					public void modify(Object element, String property, Object value) {
						TableItem tableItem = (TableItem)element;
						MessageCommunicationModel data = (MessageCommunicationModel)tableItem.getData();
						if (NAME_PROPERTY.equals(property))
							data.setName(value.toString());
						else if (SIGNATURE_PROPERTY.equals(property))
						//PKY 08070901 S Combox에 text 입력시 에러발생문제 수정
						if((Integer)value!=-1)
							data.signature = (Integer)value;
						else{
							 MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_WARNING);
							 dialog.setText("Message");
							 dialog.setMessage(N3Messages.DIALOG_SCOPE_NULL_MESSAGE);
							 dialog.open();
							 data.signature = 0;
						}
						//PKY 08070901 E Combox에 text 입력시 에러발생문제 수정
						else if (SYNCH_PROPERTY.equals(property))
						//PKY 08070901 S Combox에 text 입력시 에러발생문제 수정
						if((Integer)value!=-1)
							data.synch = (Integer)value;
						else{
							 MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_WARNING);
							 dialog.setText("Message");
							 dialog.setMessage(N3Messages.DIALOG_SCOPE_NULL_MESSAGE);
							 dialog.open();
							 data.synch = 0;
						}
						//PKY 08070901 E Combox에 text 입력시 에러발생문제 수정
						else if (KIND_PROPERTY.equals(property))
							//PKY 08070901 S Combox에 text 입력시 에러발생문제 수정
							if((Integer)value!=-1)
								data.kind = (Integer)value;
							else{
								 MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_WARNING);
								 dialog.setText("Message");
								 dialog.setMessage(N3Messages.DIALOG_SCOPE_NULL_MESSAGE);
								 dialog.open();
								 data.kind = 0;
							}
							//PKY 08070901 E Combox에 text 입력시 에러발생문제 수정
						else if (LIFECYCLE_PROPERTY.equals(property))
							//PKY 08070901 S Combox에 text 입력시 에러발생문제 수정
							if((Integer)value!=-1)
								data.lifecycle = (Integer)value;
							else{
								 MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_WARNING);
								 dialog.setText("Message");
								 dialog.setMessage(N3Messages.DIALOG_SCOPE_NULL_MESSAGE);
								 dialog.open();
								 data.lifecycle = 0;
							}
							//PKY 08070901 E Combox에 text 입력시 에러발생문제 수정
//						else if (STATIC_PROPERTY.equals(property))
//						data.isStatic = (Integer)value;
						viewer.refresh(data);
					}
				});
		viewer.setCellEditors(
				new CellEditor[] {
						new TextCellEditor(parent), new ComboBoxCellEditor(parent, SIGNATURE_SET),
						new ComboBoxCellEditor(parent, SYNCH_SET),new ComboBoxCellEditor(parent, KIND_SET),
						new ComboBoxCellEditor(parent, LIFECYCLE_SET)
						
				});
		viewer.setColumnProperties(
				new String[] {
						NAME_PROPERTY ,SIGNATURE_PROPERTY, SYNCH_PROPERTY, KIND_PROPERTY, LIFECYCLE_PROPERTY
				});
	}

	

}
