package kr.co.n3soft.n3com.project.dialog;

import java.util.ArrayList;

import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.comm.ClassModel;
import kr.co.n3soft.n3com.model.comm.ClassifierModel;
import kr.co.n3soft.n3com.model.comm.ClassifierModelTextAttach;
import kr.co.n3soft.n3com.model.comm.UMLElementModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.comm.descriptor.TypeCellEditor;
import kr.co.n3soft.n3com.model.umlclass.AttributeEditableTableItem;
import kr.co.n3soft.n3com.model.usecase.ActorModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

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
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.DrillDownAdapter;

public class AttributeDialog extends Dialog {
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
	ClassModel classModel = null;
	ActorModel actorModel = null;//2008040201 PKY S 추가
	boolean isActor=false; //2008040201 PKY S 추가 
	
	Button buttonAdd = null;
	Button buttonRemove = null;

	//PKY 08072201 S 모든 오퍼레이션및 어트리뷰트등 정보 입력한 값에 대한 위치변경할 수있도록 수정
	Button buttonAttUp = null;
	Button buttonAttDown = null;
	//PKY 08072201 E 모든 오퍼레이션및 어트리뷰트등 정보 입력한 값에 대한 위치변경할 수있도록 수정
	
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

	public static final String[] SCOPE_SET = new String[] {
		"public", "protected", "private"
	};

	public static final String[] SCOPEA_SET = new String[] {
		"+", "#", "-"
	};

	public static final String[] TYPE_SET = new String[] {
		"int", "boolean", "char", "double", "float", "long", "short"
	};

	public static final String[] BOOLEAN_SET = new String[] {
		"False", "True"
	};

	private static final String NAME_PROPERTY = N3Messages.POPUP_NAME;//2008040302 PKY S 
	private static final String TYPE_PROPERTY = N3Messages.POPUP_TYPE;//2008040302 PKY S 
	private static final String SCOPE_PROPERTY = N3Messages.POPUP_SCOPE;//2008040302 PKY S 
	private static final String INITVALUE_PROPERTY = N3Messages.POPUP_DEFAULT_INITIAL_VALUE;//2008040302 PKY S 
	private static final String DERIVED_PROPERTY = N3Messages.DIALOG_DERIVED;//PKY 08060201 S
//	private static final String STATIC_PROPERTY = "Static";
	private static final String CONST_PROPERTY = N3Messages.DIALOG_CONST;//PKY 08060201 S

	public AttributeDialog(Shell parentShell) {
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
			UMLModel umModel = (UMLModel)obj;
			if (umModel instanceof ClassifierModel) {
				ClassifierModel classifierModel = (ClassifierModel)umModel;
				classModel = classifierModel.getClassModel();
				attributes = (java.util.ArrayList) classModel.getAttributes().clone();
				attrCount = classModel.getAttributeCount();
			}
			else if(umModel instanceof ClassifierModelTextAttach) {
				ClassifierModelTextAttach classifierModel = (ClassifierModelTextAttach)umModel;
				classModel = classifierModel.getClassModel();
				attributes = (java.util.ArrayList) classModel.getAttributes().clone();
				attrCount = classModel.getAttributeCount();
			}
			
			//			}
		}

	}

	public AttributeDialog(Shell parentShell, java.util.ArrayList p) {
		super(parentShell);
		this.setShellStyle(SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE);
		this.attributes = p;
	}

	public void initDesc() {
	}

	protected Control createDialogArea(Composite parent) {
		Composite comp = (Composite)super.createDialogArea(parent);

		try {
			comp.getShell().setText(N3Messages.POPUP_ATTRIBUTES);//20080717 KDI s

			comp.getShell().setImage(ProjectManager.getInstance().getImage(102));//20080724 KDI s

			SashForm sf = new SashForm(parent, SWT.VERTICAL);//20080717 KDI s SWT.HORIZONTAL);
			GridLayout layout = (GridLayout)comp.getLayout();
			FillLayout flowLayout = new FillLayout();
//			flowLayout.marginHeight = 10;
//			flowLayout.marginWidth = 10;
//			flowLayout.spacing = 5;
			sf.setLayout(flowLayout);
			GridData data5 = new GridData(GridData.FILL_BOTH);
//			data5.heightHint = 500;
			data5.widthHint = 600;
			sf.setLayoutData(data5);
			layout.numColumns = 2;
			GridData data1 = new GridData(GridData.FILL_BOTH);
			data1.horizontalSpan = 2;
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
			popupMenu.add(newRowAction);
			popupMenu.add(DeleteRowAction);
			//PKY 08060201 S 팝업으로 Type결정하도록 수정
			popupMenu.add(typeStringAction);
			popupMenu.add(typeByteAction);
			popupMenu.add(typeCharAction);
			popupMenu.add(typeDoubleAction);
			popupMenu.add(typeFloatAction);
			popupMenu.add(typeIntAction);
			popupMenu.add(typeLongAction);
			popupMenu.add(typeShortAction);	

			//PKY 08060201 E 팝업으로 Type결정하도록 수정
			Menu menu = popupMenu.createContextMenu(table);
//			table.setMenu(menu);//PKY 08082201 S 팀 프로젝트 읽기전용일때 수정불가능하도록 작업
			tableViewer.getTable().pack();
			tableViewer.setInput(attributes.toArray());
			buttonAdd = new Button(type,SWT.PUSH);
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
			buttonAdd.setImage(ProjectManager.getInstance().getImage(203));
			buttonAdd.addSelectionListener(new SelectionListener() {

				public void widgetSelected(SelectionEvent e)
				{
					ISelection iSelection = tableViewer.getSelection();
					AttributeEditableTableItem newItem;//2008040201 PKY S 수정 
					//AttributeEditableTableItem newItem=new AttributeEditableTableItem(new Integer(0), "attribute" +classModel.addAttributeCount() , "String", "");

					//2008040201 PKY S 추가
					if(isActor==false){
						//PKY 08060201 S 저장 불러오기 할경우 어트리뷰트,오퍼레이션 생성 이름 넘버러링이 초기화 되는문제
						if(classModel.getAttributeCount()==0&&classModel.getAttributes().size()>0){
							for(int i=0; i<classModel.getAttributes().size();i++){
								if(((AttributeEditableTableItem)classModel.getAttributes().get(i)).getName().lastIndexOf("attribute")>-1){
									AttributeEditableTableItem att=(AttributeEditableTableItem)classModel.getAttributes().get(i);
									String name=att.getName().substring(((AttributeEditableTableItem)classModel.getAttributes().get(i)).getName().lastIndexOf("e")+1,
											((AttributeEditableTableItem)classModel.getAttributes().get(i)).getName().length());
									char [] charString =name.toCharArray();
									boolean ischar=false;
									for(int j=0; j<charString.length;j++){                              
										if(!Character.isDigit(charString[j])) {
											ischar=true;
										}
									}
									if(ischar==false){
										int s=Integer.parseInt(att.getName().substring(((AttributeEditableTableItem)classModel.getAttributes().get(i)).getName().lastIndexOf("e")+1,
												((AttributeEditableTableItem)classModel.getAttributes().get(i)).getName().length()));

										if(classModel.getAttributeCount()<=s){
											classModel.setAttributeCount(s);
										}
									}

								}
							}
							newItem = new
							AttributeEditableTableItem(new Integer(0), "attribute"+classModel.addAttributeCount(), "String", "");
						}
						newItem=new AttributeEditableTableItem(new Integer(0), "attribute" +classModel.addAttributeCount() , "String", "");
						//PKY 08060201 E 저장 불러오기 할경우 어트리뷰트,오퍼레이션 생성 이름 넘버러링이 초기화 되는문제
					}else{
						newItem=new AttributeEditableTableItem(new Integer(0), "attribute" +actorModel.addAttributeCount() , "String", "");
					}
					//2008040201 PKY E 추가
					tableViewer.add(newItem);
//					attrCount++;

				}

				public void widgetDefaultSelected(SelectionEvent e)
				{
				}

			});

			buttonRemove = new Button(type,SWT.PUSH);
			buttonRemove.addSelectionListener(new SelectionListener() {

				public void widgetSelected(SelectionEvent e)
				{
					StructuredSelection iSelection = (StructuredSelection)tableViewer.getSelection();
					Object obj = iSelection.getFirstElement();
					int selectIndex=tableViewer.getTable().getSelectionIndex();//PKY 08070101 S 어트리뷰트,오퍼레이션,파라미터 삭제할때 삭제 한 후 선택이 계속 머물 수 있도록 수정
					if (obj != null) {
						tableViewer.remove(obj);
						classModel.downAttributeCount();

						//PKY 08070101 S 어트리뷰트,오퍼레이션,파라미터 삭제할때 삭제 한 후 선택이 계속 머물 수 있도록 수정
						if(tableViewer.getTable().getItemCount()>=selectIndex+1){
							tableViewer.getTable().select(selectIndex);
						}else{
							tableViewer.getTable().select(tableViewer.getTable().getItemCount()-1);
						}
						//PKY 08070101 E 어트리뷰트,오퍼레이션,파라미터 삭제할때 삭제 한 후 선택이 계속 머물 수 있도록 수정


					}

				}

				public void widgetDefaultSelected(SelectionEvent e)
				{
				}

			});
//			buttonAdd.setText("Add");
			buttonRemove.setImage(ProjectManager.getInstance().getImage(204));

			//PKY 08072201 S 모든 오퍼레이션및 어트리뷰트등 정보 입력한 값에 대한 위치변경할 수있도록 수정
			buttonAttUp = new Button(type,SWT.PUSH);
			buttonAttUp.setImage(ProjectManager.getInstance().getImage(330));
			buttonAttUp.addSelectionListener(new SelectionListener() {
				public void widgetSelected(SelectionEvent e)
				{
					ArrayList arryList= new ArrayList();
					StructuredSelection iSelection = (StructuredSelection)tableViewer.getSelection();
					if(iSelection!=null){
						Object obj = iSelection.getFirstElement();
						Table table=tableViewer.getTable();
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
						tableViewer.setInput(arryList.toArray());
					}
				}
				public void widgetDefaultSelected(SelectionEvent e)
				{
				}
			});
			buttonAttDown = new Button(type,SWT.PUSH);
			buttonAttDown.setImage(ProjectManager.getInstance().getImage(331));
			buttonAttDown.addSelectionListener(new SelectionListener() {
				public void widgetSelected(SelectionEvent e)
				{
					int chanage= 0;
					boolean ischanage=false;
					StructuredSelection iSelection = (StructuredSelection)tableViewer.getSelection();
					if(iSelection!=null){
						Object obj = iSelection.getFirstElement();
						Table table=tableViewer.getTable();
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
//						removeNullArray(arryList);
						tableViewer.setInput(arryList.toArray());
					}
				}
				public void widgetDefaultSelected(SelectionEvent e)
				{
				}
			});
			//PKY 08072201 E 모든 오퍼레이션및 어트리뷰트등 정보 입력한 값에 대한 위치변경할 수있도록 수정

			type.pack();
			GridData data2 = new GridData(GridData.FILL_BOTH);
			data2.heightHint = 200;
			data2.widthHint = 200;
			description = new Group(sf, SWT.SHADOW_ETCHED_IN);
			description.setText(N3Messages.POPUP_DESCRIPTION);//2008040302 PKY S 
			description.setLayoutData(data2);
			description.setLayout(flowLayout);
			styleField = new StyledText(description, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
			styleField.pack();
			description.pack();

			if(attributes.size()>0){

				this.oldSit = (AttributeEditableTableItem)attributes.get(0);
				if(this.oldSit.desc!=null){
					table.select(0);
					styleField.setText(this.oldSit.desc);
				}
			}
			//PKY 08082201 S 팀 프로젝트 읽기전용일때 수정불가능하도록 작업
			if(ProjectManager.getInstance()!=null && ProjectManager.getInstance().getSelectPropertyUMLElementModel()!=null){
					if(!ProjectManager.getInstance().isItemEnable(ProjectManager.getInstance().getSelectPropertyUMLElementModel())){
						boolean isEnable = ProjectManager.getInstance().isItemEnable(ProjectManager.getInstance().getSelectPropertyUMLElementModel());
						buttonAttDown.setEnabled(isEnable);
						buttonAttUp.setEnabled(isEnable);
						buttonRemove.setEnabled(isEnable);
						buttonAdd.setEnabled(isEnable);
					}
			}
			styleField.addKeyListener(new KeyListener(){
				public void keyPressed(KeyEvent e){
					if(ProjectManager.getInstance()!=null && ProjectManager.getInstance().getSelectPropertyUMLElementModel()!=null){
						UMLElementModel umlElementModel= ProjectManager.getInstance().getSelectPropertyUMLElementModel();
						if(umlElementModel.getAcceptParentModel()!=null && umlElementModel.getAcceptParentModel() instanceof UMLModel){
							if(!ProjectManager.getInstance().isItemEnable(ProjectManager.getInstance().getSelectPropertyUMLElementModel())){
								StructuredSelection iSelection = (StructuredSelection)tableViewer.getSelection();
								Object obj = iSelection.getFirstElement();
								if( obj instanceof AttributeEditableTableItem){
									System.out.println("11");
									AttributeEditableTableItem sit = (AttributeEditableTableItem)obj;

									styleField.setText(sit.desc);


								}
							}
						}
					}
				}
				public void keyReleased(KeyEvent e){

				}
			});
			//PKY 08082201 E 팀 프로젝트 읽기전용일때 수정불가능하도록 작업
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
				for (int i = 0; i < tableItem.length; i++) {
					this.attributes.add(tableItem[i].getData());
					//					classModel.setAttributes((AttributeEditableTableItem)tableItem[i].getData());
				}
				if(oldSit!=null)
					oldSit.desc = styleField.getText();
				//2008040201 PKY S 추가 
				if(isActor==false)
					classModel.setAttributes(this.attributes);
				if(isActor==false)
					//2008040201 PKY E 추가 
					ProjectManager.getInstance().autoSize(classModel);

				//				System.out.println("tableItem:"+tableItem[0].getData());
			}
			else{
//				attrCount
				//2008040201 PKY E 추가 
				if(isActor==false)
				classModel.setAttributeCount(attrCount);
				else
					actorModel.setAttributeCount(attrCount);
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
							Number index = ((AttributeEditableTableItem)element).scope;
							return SCOPE_SET[index.intValue()];
						case 1:
							String name = ((AttributeEditableTableItem)element).name;
							return name;
						case 2:
							String type = ((AttributeEditableTableItem)element).stype;
							return type;
						case 3:
							String initValue = ((AttributeEditableTableItem)element).initValue;
							return initValue;
						case 4:
							Number index1 = ((AttributeEditableTableItem)element).isDerived;
							return BOOLEAN_SET[index1.intValue()];
//							case 5:
//							index1 = ((AttributeEditableTableItem)element).isStatic;
//							return BOOLEAN_SET[index1.intValue()];
						case 5:
							index1 = ((AttributeEditableTableItem)element).isConst;
							return BOOLEAN_SET[index1.intValue()];
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
		layout.addColumnData(new ColumnWeightData(50, 175, true));
		layout.addColumnData(new ColumnWeightData(50, 75, true));
		layout.addColumnData(new ColumnWeightData(50, 75, true));
		layout.addColumnData(new ColumnWeightData(50, 75, true));
		layout.addColumnData(new ColumnWeightData(50, 75, true));
//		layout.addColumnData(new ColumnWeightData(50, 75, true));
		table.setLayout(layout);
		TableColumn accessColumn = new TableColumn(table, SWT.CENTER);
		accessColumn.setText(this.SCOPE_PROPERTY);
		TableColumn nameColumn = new TableColumn(table, SWT.CENTER);
		nameColumn.setText(this.NAME_PROPERTY);
		TableColumn typeColumn = new TableColumn(table, SWT.CENTER);
		typeColumn.setText(this.TYPE_PROPERTY);
		TableColumn initColumn = new TableColumn(table, SWT.CENTER);
		initColumn.setText(this.INITVALUE_PROPERTY);
		TableColumn derivedColumn = new TableColumn(table, SWT.CENTER);
		derivedColumn.setText(this.DERIVED_PROPERTY);
//		TableColumn staticColumn = new TableColumn(table, SWT.CENTER);
//		staticColumn.setText(this.STATIC_PROPERTY);
		TableColumn constColumn = new TableColumn(table, SWT.CENTER);
		constColumn.setText(this.CONST_PROPERTY);
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
						if (NAME_PROPERTY.equals(property))
							return ((AttributeEditableTableItem)element).name;
						else if (TYPE_PROPERTY.equals(property))
							return ((AttributeEditableTableItem)element).stype;
						else if (SCOPE_PROPERTY.equals(property))
							return ((AttributeEditableTableItem)element).scope;
						else if(INITVALUE_PROPERTY.equals(property))
							return ((AttributeEditableTableItem)element).initValue;
						else if(DERIVED_PROPERTY.equals(property))
							return ((AttributeEditableTableItem)element).isDerived;
//						else if(STATIC_PROPERTY.equals(property))
//						return ((AttributeEditableTableItem)element).isStatic;
						else 
							return ((AttributeEditableTableItem)element).isConst;
					}
					public void modify(Object element, String property, Object value) {
						TableItem tableItem = (TableItem)element;
						AttributeEditableTableItem data = (AttributeEditableTableItem)tableItem.getData();
						//PKY 08082201 S 팀 프로젝트 읽기전용일때 수정불가능하도록 작업
						if(ProjectManager.getInstance()!=null && ProjectManager.getInstance().getSelectPropertyUMLElementModel()!=null)
								if(!ProjectManager.getInstance().isItemEnable(ProjectManager.getInstance().getSelectPropertyUMLElementModel()))
									return;
						
						if (NAME_PROPERTY.equals(property))
							data.name = value.toString();
						else if (TYPE_PROPERTY.equals(property))
							data.stype = value.toString();
						else if (SCOPE_PROPERTY.equals(property))
							//PKY 08070901 S Combox에 text 입력시 에러발생문제 수정
							if((Integer)value!=-1)
							data.scope = (Integer)value;
							else{
								 MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_WARNING);
								 dialog.setText("Message");
								 dialog.setMessage(N3Messages.DIALOG_SCOPE_NULL_MESSAGE);
								 dialog.open();
								data.scope = 0;
							}
							//PKY 08070901 E Combox에 text 입력시 에러발생문제 수정
						else if (INITVALUE_PROPERTY.equals(property))
							data.initValue = value.toString();
						//PKY 08072201 S 어트리뷰트 Const변경안되는문제 수정

						else if (DERIVED_PROPERTY.equals(property)){
							if((Integer)value!=-1) //20080717 KDI s 텍스트 입력시 에러 발생							
								data.isDerived = (Integer)value;
						}
//						else if (STATIC_PROPERTY.equals(property))
//						data.isStatic = (Integer)value;
						else if(CONST_PROPERTY.equals(property))
							if((Integer)value!=-1) //20080717 KDI s 텍스트 입력시 에러 발생	
								data.isConst = (Integer)value;
						//PKY 08072201 E 어트리뷰트 Const변경안되는문제 수정
						
						viewer.refresh(data);
					}
				});
		viewer.setCellEditors(
				new CellEditor[] {
						new ComboBoxCellEditor(parent, SCOPE_SET), new TextCellEditor(parent), new TypeCellEditor(parent),
						new TextCellEditor(parent),new ComboBoxCellEditor(parent, BOOLEAN_SET),new ComboBoxCellEditor(parent, BOOLEAN_SET)//,new ComboBoxCellEditor(parent, BOOLEAN_SET)
				});
		viewer.setColumnProperties(
				new String[] {
						SCOPE_PROPERTY, NAME_PROPERTY, TYPE_PROPERTY, INITVALUE_PROPERTY,DERIVED_PROPERTY,CONST_PROPERTY
				});
	}
}
//class EditableTableItem
//{
//public String name;
//public Integer scope;
//public String initValue;
//public Integer type;
//public EditableTableItem(Integer s,String n, Integer v,String i)
//{
//scope = s;
//name = n;
//type = v;
//initValue = i;
//}
//public String toString(){
//String value= AttributeDialog.SCOPE_SET[scope]+" "+name+" : "+AttributeDialog.TYPE_SET[type];
//return value;
//}
//}
