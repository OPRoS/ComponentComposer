package kr.co.n3soft.n3com.project.dialog;

import java.util.ArrayList;

import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.comm.ClassModel;
import kr.co.n3soft.n3com.model.comm.ClassifierModel;
import kr.co.n3soft.n3com.model.comm.ClassifierModelTextAttach;
import kr.co.n3soft.n3com.model.comm.UMLElementModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.comm.descriptor.ParameterCellEditor;
import kr.co.n3soft.n3com.model.comm.descriptor.TypeCellEditor;
import kr.co.n3soft.n3com.model.umlclass.AttributeEditableTableItem;
import kr.co.n3soft.n3com.model.umlclass.OperationEditableTableItem;
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
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
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

public class OperationDialog extends Dialog {
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
	Group Param;
	Group description;
	StringBuffer descClass;
	Label descriptionField = null;
	//	private TreeViewer viewer;
	private TableViewer tableViewer;
	private TableViewer paramTableViewer;
	private StyledText styleField;
	ClassModel classModel = null;
	public boolean isState = false;
	public java.util.ArrayList attributes = new ArrayList();
	public int paramCount = 0;
	public int operCount = 0;

	Button buttonOpeationAdd = null;
	Button buttonOpeationRemove = null;

	Button buttonParamAdd = null;
	Button buttonParamRemove = null;
	
	//PKY 08072201 S 모든 오퍼레이션및 어트리뷰트등 정보 입력한 값에 대한 위치변경할 수있도록 수정
	Button buttonOperUp = null;
	Button buttonOperDown = null;
	//PKY 08072201 E 모든 오퍼레이션및 어트리뷰트등 정보 입력한 값에 대한 위치변경할 수있도록 수정

	OperationEditableTableItem oldSit = null;

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

	public String[] TYPE_SET = ProjectManager.TYPE_SET;
	private static final String NAME_PROPERTY = N3Messages.POPUP_NAME;//2008040302 PKY S 
	private String RETURN_TYPE_PROPERTY = N3Messages.POPUP_RETURNTYPE;//2008040302 PKY S 
	private static final String TYPE_PROPERTY = N3Messages.POPUP_TYPE;//2008040302 PKY S 
	private static final String SCOPE_PROPERTY = N3Messages.POPUP_SCOPE;//2008040302 PKY S 
	private static final String DEFAULT_PROPERTY = N3Messages.POPUP_DEFAULT_INITIAL_VALUE;//2008040302 PKY S 
	private static final String DESCRIPTION_PROPERTY = N3Messages.POPUP_DESCRIPTION;//2008040302 PKY S
	public static final String STEREOTYPE = N3Messages.POPUP_STEREOTYPE;//PKY 08071101 S 오퍼레이션 스트레오타입 입력가능하도록
	public static final String PARAMETER_PROPERTY = N3Messages.DIALOG_PARAMETERS;//20080721 KDI s


	public OperationDialog(Shell parentShell) {
		super(parentShell);
		this.setShellStyle(SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE);
		//		if(ProjectManager.getInstance())
		ProjectManager.getInstance().getSelectNodes();
		System.out.println("dddd==>" + ProjectManager.getInstance().getSelectNodes());
		if (ProjectManager.getInstance().getSelectPropertyUMLElementModel() != null) {
			Object obj = ProjectManager.getInstance().getSelectPropertyUMLElementModel();
//			Object obj = ProjectManager.getInstance().getSelectNodes().get(0);

			UMLModel umModel = (UMLModel)obj;
			if (umModel instanceof ClassifierModel) {
				ClassifierModel classifierModel = (ClassifierModel)umModel;
				
					this.checkClassType();
					isState = false;
				
				classModel = classifierModel.getClassModel();
				this.operCount = classModel.getOperationCount();
				attributes = (java.util.ArrayList) classModel.getOperations().clone();
			}
			else if(umModel instanceof ClassifierModelTextAttach){
				ClassifierModelTextAttach classifierModel = (ClassifierModelTextAttach)umModel;

				this.checkClassType();
				classModel = classifierModel.getClassModel();
				this.operCount = classModel.getOperationCount();
				attributes = (java.util.ArrayList) classModel.getOperations().clone();
			}
		}
	}


	public void initDesc() {
	}

	public void checkStateType() {
		RETURN_TYPE_PROPERTY = "Action";
		TYPE_SET = ProjectManager.STATE_TYPE_SET;
	}

	public void checkClassType() {
		RETURN_TYPE_PROPERTY = N3Messages.POPUP_RETURN;//2008040302 PKY S 
		TYPE_SET = ProjectManager.TYPE_SET;
	}

	protected Control createDialogArea(Composite parent) {
		Composite comp = (Composite)super.createDialogArea(parent);
		try {
			comp.getShell().setText(N3Messages.POPUP_OPERATION); //20080717 KDI s
			
			comp.getShell().setImage(ProjectManager.getInstance().getImage(103));//20080724 KDI s
			
			SashForm sf2 = new SashForm(parent, SWT.VERTICAL); 
			GridLayout layout2 = (GridLayout)comp.getLayout();
			layout2.numColumns = 2;
			FillLayout flowLayout2 = new FillLayout();
			sf2.setLayout(flowLayout2);
			GridData data6 = new GridData(GridData.FILL_BOTH);
			data6.heightHint = 600;
			data6.widthHint = 600;
			sf2.setLayoutData(data6);
			//20080717 KDI s 주석처리
//			SashForm sf = new SashForm(sf2, SWT.HORIZONTAL); 
			GridLayout layout = (GridLayout)comp.getLayout();

			FillLayout flowLayout = new FillLayout();
//			sf.setLayout(flowLayout);
//			GridData data5 = new GridData(GridData.FILL_BOTH);
//			data5.heightHint = 400;
//			data5.widthHint = 600;
//			sf.setLayoutData(data5);
			//20080717 KDI e 주석처리
			GridData data1 = new GridData(GridData.FILL_BOTH);
			data1.heightHint = 400;
			data1.widthHint = 400;


			type = new Group(sf2, SWT.SHADOW_ETCHED_IN); //20080717 KDI s 
			type.setLayout(layout);
			type.setText(N3Messages.POPUP_OPERATION);//2008040302 PKY S 
			type.setLayoutData(data1);

			final Table table = new Table(type, SWT.FULL_SELECTION);
			table.addSelectionListener(new SelectionListener() {

				public void widgetSelected(SelectionEvent e)
				{
					//20080721 KDI s
					System.out.println("");
//					if(parameterCellEditor != null){
//					parameterCellEditor.setOperationItem((ParameterCellEditor)e.item.getData());
//					}
					//20080721 KDI e
				}

				public void widgetDefaultSelected(SelectionEvent e)
				{
					System.out.println("");
				}

			});



			tableViewer = buildAndLayoutTable(table);
			data1 = new GridData(GridData.FILL_HORIZONTAL);
			data1.horizontalSpan = 2;
			data1.heightHint = 200;
			data1.widthHint = 400;
			table.setLayoutData(data1);
			buttonOpeationAdd = new Button(type,SWT.PUSH);
			buttonOpeationRemove = new Button(type,SWT.PUSH);
			buttonOpeationAdd.setImage(ProjectManager.getInstance().getImage(203));
			buttonOpeationRemove.setImage(ProjectManager.getInstance().getImage(204));

			//PKY 08072201 S 모든 오퍼레이션및 어트리뷰트등 정보 입력한 값에 대한 위치변경할 수있도록 수정
			buttonOperUp= new Button(type,SWT.PUSH);
			buttonOperDown= new Button(type,SWT.PUSH);
			buttonOperUp.setImage(ProjectManager.getInstance().getImage(330));
			buttonOperDown.setImage(ProjectManager.getInstance().getImage(331));
			
			
			buttonOperUp.addSelectionListener(new SelectionListener() {
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
			
			buttonOperDown.addSelectionListener(new SelectionListener() {
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
//					removeNullArray(arryList);
					tableViewer.setInput(arryList.toArray());
					}
				}
				public void widgetDefaultSelected(SelectionEvent e)
				{
				}
			});
			//PKY 08072201 E 모든 오퍼레이션및 어트리뷰트등 정보 입력한 값에 대한 위치변경할 수있도록 수정


			attachContentProvider(tableViewer);
			attachLabelProvider(tableViewer);
			attachCellEditors(tableViewer, table);
			MenuManager popupMenu = new MenuManager();
			IAction newRowAction = new NewRowAction();
			IAction DeleteRowAction = new DeleteRowAction();
			popupMenu.add(newRowAction);
			popupMenu.add(DeleteRowAction);
			Menu menu = popupMenu.createContextMenu(table);
//			table.setMenu(menu);//PKY 08082201 S 팀 프로젝트 읽기전용일때 수정불가능하도록 작업
			tableViewer.getTable().pack();
			tableViewer.setInput(attributes.toArray());
			type.pack();
			GridData data2 = new GridData(GridData.FILL_BOTH);
			data2.heightHint = 300;
			data2.widthHint = 200;
			description = new Group(sf2, SWT.SHADOW_ETCHED_IN); ////20080717 KDI s
			description.setText(N3Messages.POPUP_DESCRIPTION);//2008040302 PKY S ;
			description.setLayoutData(data2);
			description.setLayout(flowLayout); ////20080717 KDI s
			styleField = new StyledText(description, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
			styleField.pack();
			description.pack();

			GridData data3 = new GridData(GridData.FILL_BOTH);
			data3.horizontalSpan = 2;
			data3.heightHint = 300;
			data3.widthHint = 400;
//			//20080717 KDI s 주석
//			Param = new Group(sf2, SWT.SHADOW_ETCHED_IN);
//			Param.setLayout(layout2);
//			Param.setText(N3Messages.DIALOG_PARAMETERS);//2008040301 PKY S 
//			Param.setLayoutData(data3);
//			final Table tableParam = new Table(Param, SWT.FULL_SELECTION);
//			data1 = new GridData(GridData.FILL_HORIZONTAL);
//			data1.horizontalSpan = 2;
//			data1.heightHint = 200;
//			data1.widthHint = 400;
//			tableParam.setLayoutData(data1);

//			buttonParamAdd = new Button(Param,SWT.PUSH);
//			buttonParamRemove = new Button(Param,SWT.PUSH);
//			buttonParamAdd.setImage(ProjectManager.getInstance().getImage(203));
//			buttonParamRemove.setImage(ProjectManager.getInstance().getImage(204));
			//20080717 KDI e 주석

			buttonOpeationAdd.addSelectionListener(new SelectionListener() {

				public void widgetSelected(SelectionEvent e)
				{
					//PKY 08060201 S 저장 불러오기 할경우 어트리뷰트,오퍼레이션 생성 이름 넘버러링이 초기화 되는문제
					ISelection iSelection = tableViewer.getSelection();
					int countOper=0 ;
					if(classModel.getOperationCount()==0&&classModel.getOperations().size()>0){
						for(int i=0; i<classModel.getOperations().size();i++){
							if(((OperationEditableTableItem)classModel.getOperations().get(i)).getName().lastIndexOf("operation")>-1){
								OperationEditableTableItem att=(OperationEditableTableItem)classModel.getOperations().get(i);
								String name=att.getName().substring(((OperationEditableTableItem)classModel.getOperations().get(i)).getName().lastIndexOf("n")+1,
										((OperationEditableTableItem)classModel.getOperations().get(i)).getName().length());
								char [] charString =name.toCharArray();
								boolean ischar=false;
								for(int j=0; j<charString.length;j++){                              
									if(!Character.isDigit(charString[j])) {
										ischar=true;
									}
								}
								if(ischar==false){
									int s=Integer.parseInt(att.getName().substring(((OperationEditableTableItem)classModel.getOperations().get(i)).getName().lastIndexOf("n")+1,
											((OperationEditableTableItem)classModel.getOperations().get(i)).getName().length()));

									if(classModel.getOperationCount()<=s){
										classModel.setOperationCount(s+1);
									}
								}

							}
						}

						countOper=classModel.getOperationCount();
					}else{
						classModel.addOperationCount();
						countOper =classModel.getOperationCount();//2008033102 PKY S 추가 
					}



					OperationEditableTableItem newItem = new OperationEditableTableItem(new Integer(0), "operation" + countOper,"void", "");
					//OperationEditableTableItem newItem = new OperationEditableTableItem(new Integer(0), "operation" + addOperationCount(),"void", ""); 수정 
					if (isState) {
						//newItem.setIsState(true);//2008033102 PKY S 주석처리
						newItem= new OperationEditableTableItem(new Integer(0), "operation" + countOper,"do", "");//2008033102 PKY S 추가
					}
					else {
						newItem.setIsState(false);
					}
					
					//20080721 KDI s
//    				ParameterEditableTableItem p = new ParameterEditableTableItem("param" + paramCount, "String", "");
//    				newItem.getParams().add(new java.util.ArrayList());
    				newItem.setParams(new java.util.ArrayList());
//    				paramTableViewer.add(p);
//    				paramCount++;
					//20080721 KDI e
					
					tableViewer.add(newItem);
//					tableViewer.setSelection(selection)
//					table.select(tableViewer.getTable())
					tableViewer.getTable().select(tableViewer.getTable().getItemCount()-1);

					OperationEditableTableItem sit = (OperationEditableTableItem)newItem;
//					if(oldSit==null){
					oldSit = sit;
					styleField.setText(sit.desc);

//					}



					//PKY 08060201 E 저장 불러오기 할경우 어트리뷰트,오퍼레이션 생성 이름 넘버러링이 초기화 되는문제
				}

				public void widgetDefaultSelected(SelectionEvent e)
				{
				}

			});

			buttonOpeationRemove.addSelectionListener(new SelectionListener() {

				public void widgetSelected(SelectionEvent e)
				{
					int selectIndex=tableViewer.getTable().getSelectionIndex();//PKY 08070101 S 어트리뷰트,오퍼레이션,파라미터 삭제할때 삭제 한 후 선택이 계속 머물 수 있도록 수정
					StructuredSelection iSelection = (StructuredSelection)tableViewer.getSelection();
					Object obj = iSelection.getFirstElement();
					if (obj != null) {
						try{
							tableViewer.remove(obj);
							classModel.downOperationCount();
						}
						catch(Exception e1){
							e1.printStackTrace();
						}

						obj = tableViewer.getTable().getItem(tableViewer.getTable().getItemCount()-1);
						if(obj instanceof TableItem ){
							TableItem ti = (TableItem)obj;
							oldSit = (OperationEditableTableItem)ti.getData();
							if(oldSit.desc!=null)
								styleField.setText(oldSit.desc);
							else
								styleField.setText("");	
							//PKY 08070101 S 어트리뷰트,오퍼레이션,파라미터 삭제할때 삭제 한 후 선택이 계속 머물 수 있도록 수정
							if(tableViewer.getTable().getItemCount()>=selectIndex+1){
								tableViewer.getTable().select(selectIndex);
							}else{
								tableViewer.getTable().select(tableViewer.getTable().getItemCount()-1);
							}
							//PKY 08070101 E 어트리뷰트,오퍼레이션,파라미터 삭제할때 삭제 한 후 선택이 계속 머물 수 있도록 수정
						}

					}



				}

				public void widgetDefaultSelected(SelectionEvent e)
				{
				}

			});


			//20080717 KDI s 주석
//			buttonParamAdd.addSelectionListener(new SelectionListener() {

//			public void widgetSelected(SelectionEvent e)
//			{
//			IStructuredSelection sel = (IStructuredSelection)tableViewer.getSelection();
//			Object obj = sel.getFirstElement();
//			OperationEditableTableItem newItem = (OperationEditableTableItem)obj;
//			ParameterEditableTableItem p = new ParameterEditableTableItem("param" + paramCount, "String", "");
//			newItem.getParams().add(p);
//			paramTableViewer.add(p);
//			paramCount++;


//			}

//			public void widgetDefaultSelected(SelectionEvent e)
//			{
//			}

//			});

//			buttonParamRemove.addSelectionListener(new SelectionListener() {

//			public void widgetSelected(SelectionEvent e)
//			{
//			//2008042503PKY S
//			StructuredSelection iSelection = (StructuredSelection)paramTableViewer.getSelection();
//			Object obj = iSelection.getFirstElement();
//			ParameterEditableTableItem newItem = (ParameterEditableTableItem)obj;

//			if (obj != null) {
//			paramTableViewer.remove(newItem);
//			}
//			//2008042503PKY 

//			}

//			public void widgetDefaultSelected(SelectionEvent e)
//			{
//			}

//			});




//			MenuManager popupMenu1 = new MenuManager();
//			IAction newParamRowAction = new NewParamRowAction();
//			IAction deleteParamRowAction = new DeleteParamRowAction();
//			popupMenu1.add(newParamRowAction);
//			popupMenu1.add(deleteParamRowAction);
//			Menu menu1 = popupMenu1.createContextMenu(tableParam);
//			tableParam.setMenu(menu1);
//			paramTableViewer = buildAndLayoutParamTable(tableParam);
//			attachContentProviderParam(paramTableViewer);
//			attachLabelProviderParam(paramTableViewer);
//			attachCellEditorsParam(paramTableViewer, tableParam);
//			paramTableViewer.getTable().pack();
			//20080717 KDI e 주석

			tableViewer.addSelectionChangedListener(
					new ISelectionChangedListener() {
						public void selectionChanged(SelectionChangedEvent event) {
							IStructuredSelection sel = (IStructuredSelection)event.getSelection();
							Object obj = sel.getFirstElement();
							//20080717 KDI s 주석
//							if(obj==null){
//							paramTableViewer.getTable().removeAll();
//							return;
//							}
//							else
//							paramTableViewer.getTable().removeAll();
//							OperationEditableTableItem newItem = (OperationEditableTableItem)obj;
//							for (int i = 0; i < newItem.getParams().size(); i++) {
//							ParameterEditableTableItem p = (ParameterEditableTableItem)newItem.getParams().get(i);
//							paramTableViewer.add(p);
//							}
//							paramTableViewer.getTable().select(0);
							//20080717 KDI e 주석
							if(obj instanceof OperationEditableTableItem){
								OperationEditableTableItem sit = (OperationEditableTableItem)obj;
								if(oldSit==null){
									oldSit = sit;
									styleField.setText(sit.desc);

								}
								else{
									oldSit.desc = styleField.getText();
									oldSit = sit;
									styleField.setText(sit.desc);

								}


							}

						}
					});

			if(attributes.size()>0){

				this.oldSit = (OperationEditableTableItem)attributes.get(0);
				if(this.oldSit.desc!=null){
					table.select(0);
					styleField.setText(this.oldSit.desc);
					//20080717 KDI s 주석
//					OperationEditableTableItem newItem = (OperationEditableTableItem)oldSit;
//					for (int i = 0; i < newItem.getParams().size(); i++) {
//					ParameterEditableTableItem p = (ParameterEditableTableItem)newItem.getParams().get(i);
//					paramTableViewer.add(p);
//					}
//					paramTableViewer.getTable().select(0);
					//20080717 KDI e 주석
				}
			}
			//PKY 08082201 S 팀 프로젝트 읽기전용일때 수정불가능하도록 작업			
			if(ProjectManager.getInstance()!=null && ProjectManager.getInstance().getSelectPropertyUMLElementModel()!=null){
				UMLElementModel umlElementModel= ProjectManager.getInstance().getSelectPropertyUMLElementModel();
				if(umlElementModel!=null && umlElementModel instanceof UMLModel){
					if(!ProjectManager.getInstance().isItemEnable(ProjectManager.getInstance().getSelectPropertyUMLElementModel())){
						boolean isEnable = ProjectManager.getInstance().isItemEnable(ProjectManager.getInstance().getSelectPropertyUMLElementModel());

						buttonOpeationAdd.setEnabled(isEnable);
						buttonOpeationRemove.setEnabled(isEnable);
						buttonOperUp.setEnabled(isEnable);
						buttonOperDown.setEnabled(isEnable);

					}
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
								if( obj instanceof OperationEditableTableItem){
									System.out.println("11");
									OperationEditableTableItem sit = (OperationEditableTableItem)obj;

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
			super("오퍼레이션추가");
		}

		public void run() {
			ISelection iSelection = tableViewer.getSelection();
			OperationEditableTableItem newItem = new
			OperationEditableTableItem(new Integer(0), "operation" + operCount, "void", "");
//			newItem.setParams(new java.util.ArrayList());//20080721 KDI s
			if (isState) {
				newItem.setIsState(true);
			}
			else {
				newItem.setIsState(false);
			}
			//20080721 KDI s
//			ParameterEditableTableItem p = new ParameterEditableTableItem("param" + paramCount, "String", "");
//			newItem.getParams().add(new java.util.ArrayList());
			newItem.setParams(new java.util.ArrayList());
//			paramTableViewer.add(p);
//			paramCount++;
			//20080721 KDI e
			
			tableViewer.add(newItem);
			operCount++;
		}
	}


	private class DeleteRowAction extends Action {
		public DeleteRowAction() {
			super("오퍼레인션삭제");
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


	//20080717 KDI s 주석
//	private class NewParamRowAction extends Action {
//	public NewParamRowAction() {
//	super("파라미터추가");
//	}

//	public void run() {
//	//			ISelection iSelection = tableViewer.getSelection();
//	try {
//	IStructuredSelection sel = (IStructuredSelection)tableViewer.getSelection();
//	Object obj = sel.getFirstElement();
//	OperationEditableTableItem newItem = (OperationEditableTableItem)obj;
//	ParameterEditableTableItem p = new ParameterEditableTableItem("param" + paramCount, "String", "");
//	newItem.getParams().add(p);
//	paramTableViewer.add(p);
//	paramCount++;
//	}
//	catch (Exception e) {
//	e.printStackTrace();
//	}
//	}
//	}


//	private class DeleteParamRowAction extends Action {
//	public DeleteParamRowAction() {
//	super("파라미터삭제");
//	}

//	public void run() {
//	StructuredSelection iSelection = (StructuredSelection)paramTableViewer.getSelection();
//	Object obj = iSelection.getFirstElement();
//	if (obj != null) {
//	paramTableViewer.remove(obj);
//	}
//	}
//	}
	//20080717 KDI e


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
				int opCount = attributes.size();
				int attrCount = classModel.getAttributes().size();





				classModel.setOperations(this.attributes);

				if(oldSit!=null)
					oldSit.desc = styleField.getText();
			}
			else{
				classModel.setOperationCount(this.operCount);
			}
			ProjectManager.getInstance().autoSize(classModel);
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
							Number index = ((OperationEditableTableItem)element).scope;
							return SCOPE_SET[index.intValue()];
						case 1:
							String stero = ((OperationEditableTableItem)element).stereo;
							return stero;
						case 2:
							String name = ((OperationEditableTableItem)element).name;
							return name;
						case 3:
//							Number index1 = ((OperationEditableTableItem)element).type;
//							if(index1.intValue()!= -1){
//							return TYPE_SET[index1.intValue()];
//							}
//							else{
//							return  ((OperationEditableTableItem)element).stype;
//							}
							String type = ((OperationEditableTableItem)element).stype;
							return type;
						case 4:
							//20080722 KDI s
							String param = ((OperationEditableTableItem)element).getParams().toString();
							if(param.startsWith("["))
								param = param.substring(1);
							if(param.indexOf("]")>-1)
								param = param.substring(0,param.lastIndexOf("]"));
							return param;
							//20080722 KDI e
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

	//20080717 KDI s 주석
//	private void attachLabelProviderParam(TableViewer viewer) {
//	viewer.setLabelProvider(
//	new ITableLabelProvider() {
//	public Image getColumnImage(Object element, int columnIndex) {
//	return null;
//	}
//	public String getColumnText(Object element, int columnIndex) {
//	switch (columnIndex) {
//	case 0:
//	String name = ((ParameterEditableTableItem)element).name;
//	return name;
//	case 1:
//	String stype =  ((ParameterEditableTableItem)element).stype;
//	return stype;
//	case 2:
//	String initValue = ((ParameterEditableTableItem)element).defalut;
//	return initValue;
//	case 3:
//	initValue = ((ParameterEditableTableItem)element).desc;
//	return initValue;
//	default:
//	return "Invalid column: " + columnIndex;
//	}
//	}
//	public void addListener(ILabelProviderListener listener) {
//	}
//	public void dispose() {
//	}
//	public boolean isLabelProperty(Object element, String property) {
//	return false;
//	}
//	public void removeListener(ILabelProviderListener lpl) {
//	}
//	});
//	}
	//20080717 KDI e

	private void attachContentProvider(TableViewer viewer) {
		viewer.setContentProvider(
				new IStructuredContentProvider() {
					public Object[] getElements(Object inputElement) {
						return (Object[]) inputElement;
					}
					public void dispose() {
					}
					public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
						System.out.println("adsf");
					}
				});
	}

	//20080717 KDI s 주석
//	private void attachContentProviderParam(TableViewer viewer) {
//	viewer.setContentProvider(
//	new IStructuredContentProvider() {
//	public Object[] getElements(Object inputElement) {
//	return (Object[]) inputElement;
//	}
//	public void dispose() {
//	}
//	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
//	}
//	});
//	}

//	private TableViewer buildAndLayoutParamTable(final Table table) {
//	TableViewer tableViewer = new TableViewer(table);
//	TableLayout layout = new TableLayout();
//	layout.addColumnData(new ColumnWeightData(50, 75, true));
//	layout.addColumnData(new ColumnWeightData(50, 75, true));
//	layout.addColumnData(new ColumnWeightData(50, 75, true));
//	layout.addColumnData(new ColumnWeightData(150, 100, true));
//	//		layout.addColumnData(new ColumnWeightData(50, 75, true));
//	table.setLayout(layout);
//	TableColumn accessColumn = new TableColumn(table, SWT.CENTER);
//	accessColumn.setText(this.NAME_PROPERTY);
//	TableColumn nameColumn = new TableColumn(table, SWT.CENTER);
//	nameColumn.setText(this.TYPE_PROPERTY);
////	TableColumn typeColumn = new TableColumn(table, SWT.CENTER);
////	typeColumn.setText(this.DEFAULT_PROPERTY);
////	TableColumn descColumn = new TableColumn(table, SWT.CENTER);
////	descColumn.setText(this.DESCRIPTION_PROPERTY);
//	//		TableColumn initColumn = new TableColumn(table, SWT.CENTER);
//	//		initColumn.setText(this.INITVALUE_PROPERTY);
//	table.setHeaderVisible(true);
//	return tableViewer;
//	}
	//20080717 KDI e

	private TableViewer buildAndLayoutTable(final Table table) {
		TableViewer tableViewer = new TableViewer(table);
		TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnWeightData(50, 75, true));
		layout.addColumnData(new ColumnWeightData(50, 75, true));
		layout.addColumnData(new ColumnWeightData(50, 100, true));
		layout.addColumnData(new ColumnWeightData(50, 75, true));
		//20080721 KDI s
		layout.addColumnData(new ColumnWeightData(50, 200, true));
		//20080721 KDI e
		//		layout.addColumnData(new ColumnWeightData(50, 75, true));
		table.setLayout(layout);
		TableColumn accessColumn = new TableColumn(table, SWT.CENTER);
		accessColumn.setText(this.SCOPE_PROPERTY);
		TableColumn steroColumn = new TableColumn(table, SWT.CENTER);
		steroColumn.setText(this.STEREOTYPE);
		TableColumn nameColumn = new TableColumn(table, SWT.CENTER);
		nameColumn.setText(this.NAME_PROPERTY);
		TableColumn typeColumn = new TableColumn(table, SWT.CENTER);
		typeColumn.setText(this.RETURN_TYPE_PROPERTY);
		//20080721 KDI s
		TableColumn paraColumn = new TableColumn(table, SWT.CENTER);
		paraColumn.setText(this.PARAMETER_PROPERTY);
		//20080721 KDI e

		table.setHeaderVisible(true);
		return tableViewer;
	}

	//20080717 KDI s 주석
//	private void attachCellEditorsParam(final TableViewer viewer, Composite parent) {
//	viewer.setCellModifier(
//	new ICellModifier() {
//	public boolean canModify(Object element, String property) {
//	return true;
//	}
//	public Object getValue(Object element, String property) {
//	if (NAME_PROPERTY.equals(property))
//	return ((ParameterEditableTableItem)element).name;
//	else if (TYPE_PROPERTY.equals(property))
//	return ((ParameterEditableTableItem)element).stype;
//	else if (DEFAULT_PROPERTY.equals(property))
//	return ((ParameterEditableTableItem)element).defalut;
//	else if (DESCRIPTION_PROPERTY.equals(property))
//	return ((ParameterEditableTableItem)element).desc;
//	return null;
//	//				else
//	//					return ((AttributeEditableTableItem)element).initValue;
//	}
//	public void modify(Object element, String property, Object value) {
//	TableItem tableItem = (TableItem)element;
//	OperationEditableTableItem data = (OperationEditableTableItem)tableItem.getData();
//	if (NAME_PROPERTY.equals(property))
//	data.name = value.toString();
//	else if (RETURN_TYPE_PROPERTY.equals(property)){

//	data.stype = value.toString();
//	}
//	else if (SCOPE_PROPERTY.equals(property))
//	//PKY 08070901 S Combox에 text 입력시 에러발생문제 수정
//	if((Integer)value!=-1)
//	data.scope = (Integer)value;
//	else{
//	MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_WARNING);
//	dialog.setText("Message");
//	dialog.setMessage(N3Messages.DIALOG_SCOPE_NULL_MESSAGE);
//	dialog.open();
//	data.scope = 0;
//	}
//	//PKY 08070901 E Combox에 text 입력시 에러발생문제 수정
//	//				else
//	//					data.initValue = value.toString();
//	viewer.refresh(data);

//	}
//	});
//	viewer.setCellEditors(
//	new CellEditor[] {
//	new TextCellEditor(parent), new TypeCellEditor(parent), new TextCellEditor(parent), new TextCellEditor(parent), new TextCellEditor(parent)
//	});
//	viewer.setColumnProperties(
//	new String[] {
//	NAME_PROPERTY, TYPE_PROPERTY, DEFAULT_PROPERTY,DESCRIPTION_PROPERTY,DESCRIPTION_PROPERTY
//	});
//	}
	//20080717 KDI e

	private void attachCellEditors(final TableViewer viewer, Composite parent) {
		viewer.setCellModifier(
				new ICellModifier() {
					public boolean canModify(Object element, String property) {
						return true;
					}
					public Object getValue(Object element, String property) {
						if (NAME_PROPERTY.equals(property))
							return ((OperationEditableTableItem)element).name;
						else if (RETURN_TYPE_PROPERTY.equals(property))
							return ((OperationEditableTableItem)element).stype;
						else if (SCOPE_PROPERTY.equals(property))
							return ((OperationEditableTableItem)element).scope;
						else if(STEREOTYPE.equals(property)){
							return ((OperationEditableTableItem)element).stereo;
						}
						//20080721 KDI s
						else if(PARAMETER_PROPERTY.equals(property))
							return ((OperationEditableTableItem)element).getParams();
						//20080721 KDI e
						return null;
						//				else
						//					return ((AttributeEditableTableItem)element).initValue;
					}
					public void modify(Object element, String property, Object value) {
						TableItem tableItem = (TableItem)element;
						OperationEditableTableItem data = (OperationEditableTableItem)tableItem.getData();
						//PKY 08082201 S 팀 프로젝트 읽기전용일때 수정불가능하도록 작업
						if(ProjectManager.getInstance()!=null && ProjectManager.getInstance().getSelectPropertyUMLElementModel()!=null)
							if(!ProjectManager.getInstance().isItemEnable(ProjectManager.getInstance().getSelectPropertyUMLElementModel()))
								return;
						if (NAME_PROPERTY.equals(property))
							data.name = value.toString();
						else if (RETURN_TYPE_PROPERTY.equals(property)){

							data.stype = value.toString();
						}
						else if (SCOPE_PROPERTY.equals(property))
							//PKY 08071601 S Combox에 text 입력시 에러발생문제 수정
							if((Integer)value!=-1)
							data.scope = (Integer)value;
							else{
								 MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_WARNING);
								 dialog.setText("Message");
								 dialog.setMessage(N3Messages.DIALOG_SCOPE_NULL_MESSAGE);
								 dialog.open();
								data.scope = 0;
							}
							//PKY 08071601 E  Combox에 text 입력시 에러발생문제 수정

						else if(STEREOTYPE.equals(property))
							data.stereo = value.toString();
						//				else
						//					data.initValue = value.toString();
						//20080721 KDI s
						else if(PARAMETER_PROPERTY.equals(property))
							data.setParams((java.util.ArrayList)value);
						//20080721 KDI e
						viewer.refresh(data);

					}
				});

		viewer.setCellEditors(
				new CellEditor[] {
						new ComboBoxCellEditor(parent, SCOPE_SET), new TextCellEditor(parent), new TextCellEditor(parent), new TypeCellEditor(parent),  new ParameterCellEditor(parent)//20080721 KDI s
				});
		viewer.setColumnProperties(
				new String[] {
						SCOPE_PROPERTY, STEREOTYPE,NAME_PROPERTY, RETURN_TYPE_PROPERTY, PARAMETER_PROPERTY //20080721 KDI s
				});
	}
}
