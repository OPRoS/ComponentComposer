package kr.co.n3soft.n3com.project.dialog;

import java.util.ArrayList;

import kr.co.n3soft.n3com.edit.UMLEditPart;
import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.activity.BehaviorActivityTableItem;
import kr.co.n3soft.n3com.model.activity.FinalActiionModel;
import kr.co.n3soft.n3com.model.activity.FinalActivityModel;
import kr.co.n3soft.n3com.model.activity.FinalObjectNodeModel;
import kr.co.n3soft.n3com.model.activity.FinalStrcuturedActivityModel;
import kr.co.n3soft.n3com.model.comm.ClassModel;
import kr.co.n3soft.n3com.model.comm.ClassifierModel;
import kr.co.n3soft.n3com.model.comm.ClassifierModelTextAttach;
import kr.co.n3soft.n3com.model.comm.StrcuturedPackageTreeModel;
import kr.co.n3soft.n3com.model.comm.TypeRefModel;
import kr.co.n3soft.n3com.model.comm.UMLElementModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.component.ArtfifactModel;
import kr.co.n3soft.n3com.model.component.ComponentModel;
import kr.co.n3soft.n3com.model.composite.PartModel;
import kr.co.n3soft.n3com.model.umlclass.AttributeEditableTableItem;
import kr.co.n3soft.n3com.model.umlclass.FinalClassModel;
import kr.co.n3soft.n3com.model.umlclass.InterfaceModel;
import kr.co.n3soft.n3com.model.usecase.CollaborationModel;
import kr.co.n3soft.n3com.model.usecase.FinalActorModel;
import kr.co.n3soft.n3com.model.usecase.FinalBoundryModel;
import kr.co.n3soft.n3com.model.usecase.UseCaseModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.DrillDownAdapter;

public class ModelRefDialog extends Dialog {
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
	UMLModel selectedModel = null;
	int attrCount = 0;
	BehaviorActivityTableItem selectedBehaviorActivityTableItem;
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

	private static final String NAME_PROPERTY = N3Messages.POPUP_NAME;//2008040302 PKY S 
	private static final String TYPE_PROPERTY = N3Messages.POPUP_TYPE;//2008040302 PKY S 
	private static final String SCOPE_PROPERTY = N3Messages.PALETTE_PACKAGE;//2008040301 PKY S 
	private static final String INITVALUE_PROPERTY = "부모노드";

	public ModelRefDialog(Shell parentShell) {
		super(parentShell);
		//		ProjectManager.getInstance();
		this.setShellStyle(SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE);
		this.attributes = ProjectManager.getInstance().getTypeModel();
		if (ProjectManager.getInstance().getSelectPropertyUMLElementModel() != null) {
			Object obj = ProjectManager.getInstance().getSelectPropertyUMLElementModel();
//			UMLEditPart classEditPart = (UMLEditPart)obj;
			selectedModel = (UMLModel)obj;

		}
	}

	public BehaviorActivityTableItem getBehaviorActivityTableItem() {
		return this.selectedBehaviorActivityTableItem;
	}

	public void initDesc() {
	}

	protected Control createDialogArea(Composite parent) {
		Composite comp = (Composite)super.createDialogArea(parent);
		ArrayList Models = new ArrayList();
		//2008040702PKY S

        comp.getShell().setText(N3Messages.POPUP_INSTANCE_CLASSIFIER);//20080717 KDI s			 
		comp.getShell().setImage(ProjectManager.getInstance().getImage(207));//20080724 KDI s
		
		if(this.attributes!=null)
			if(this.selectedModel instanceof FinalActorModel||this.selectedModel instanceof UseCaseModel
					||this.selectedModel instanceof FinalBoundryModel||this.selectedModel instanceof FinalActivityModel
					||this.selectedModel instanceof FinalStrcuturedActivityModel||this.selectedModel instanceof PartModel
					
					||this.selectedModel instanceof ArtfifactModel
										||this.selectedModel instanceof FinalObjectNodeModel){
				for(int i=0;i<attributes.size();i++){
					if(attributes.get(i) instanceof StrcuturedPackageTreeModel){
						StrcuturedPackageTreeModel Model=(StrcuturedPackageTreeModel)attributes.get(i);
						if(Model.getRefModel() instanceof FinalActorModel||Model.getRefModel() instanceof FinalClassModel
								||Model.getRefModel() instanceof InterfaceModel
								||Model.getRefModel() instanceof ComponentModel){
							Models.add(attributes.get(i));
						}

					}

				}
			}else if(this.selectedModel instanceof CollaborationModel){
				for(int i=0;i<attributes.size();i++){
					if(attributes.get(i) instanceof UMLTreeModel){
						UMLTreeModel uMLTreeModel=(UMLTreeModel)attributes.get(i);
						if(uMLTreeModel.getRefModel() instanceof UseCaseModel){
							Models.add(attributes.get(i));

						}
					}
				}
			}else if(this.selectedModel instanceof FinalActiionModel){
				for(int i=0;i<attributes.size();i++){
					if(attributes.get(i) instanceof UMLTreeModel){
						UMLTreeModel uMLTreeModel=(UMLTreeModel)attributes.get(i);
						if(uMLTreeModel.getRefModel() instanceof FinalActivityModel){
							Models.add(attributes.get(i));

						}
					}
				}
			}
		//2008040702PKY E

		try {
			SashForm sf = new SashForm(parent, SWT.HORIZONTAL);
			GridLayout layout = (GridLayout)comp.getLayout();
			FillLayout flowLayout = new FillLayout();
			sf.setLayout(flowLayout);
			GridData data5 = new GridData(GridData.FILL_BOTH);
			data5.heightHint = 300;
			data5.widthHint = 500;
			sf.setLayoutData(data5);
			layout.numColumns = 3;
			GridData data1 = new GridData(GridData.FILL_BOTH);
			data1.horizontalSpan = 2;
			data1.heightHint = 300;
			data1.widthHint = 500;
			type = new Group(sf, SWT.SHADOW_ETCHED_IN);
			type.setLayout(flowLayout);
			type.setText(N3Messages.POPUP_SETELEMENTCLASSIFIER);//2008040302 PKY S 
			type.setLayoutData(data1);
			final Table table = new Table(type, SWT.FULL_SELECTION);
			tableViewer = buildAndLayoutTable(table);
			attachContentProvider(tableViewer);
			attachLabelProvider(tableViewer);
			tableViewer.getTable().pack();
			tableViewer.setInput(Models.toArray());
			//PKY 08070701 S TypeRef모델이 있을경우 선택되어 있도록 수정
			java.util.List list = ProjectManager.getInstance().getSelectNodes();
			if (list != null && list.size() == 1) {
				Object obj = list.get(0);
				if (obj instanceof UMLEditPart) {
					UMLEditPart u = (UMLEditPart)obj;
					//PKY 08081101 S Type다이어그램 ClassCast 에러문제 수정
					ClassModel classModel = null;
					if(u.getModel()!=null && u.getModel() instanceof ClassifierModel){
						if(((ClassifierModel)u.getModel()).getClassModel()!=null)
						classModel = (ClassModel)((ClassifierModel)u.getModel()).getClassModel();
					}else if(u.getModel()!=null && u.getModel() instanceof ClassifierModelTextAttach){
						if(((ClassifierModelTextAttach)u.getModel()).getClassModel()!=null)
							classModel = (ClassModel)((ClassifierModelTextAttach)u.getModel()).getClassModel();
					}				
					

					if(classModel!=null){
						classModel.getUMLDataModel().getElementProperty("typeRefModel");
						if(classModel.getUMLDataModel().getElementProperty("typeRefModel")!=null){
							for( int i=0; i< Models.size(); i++){
								StrcuturedPackageTreeModel stPackage=(StrcuturedPackageTreeModel)Models.get(i);    						
								if(stPackage.getRefModel() == classModel.getUMLDataModel().getElementProperty("typeRefModel")){    	
									tableViewer.getTable().select(i);
									tableViewer.getTable().getItem(i).setBackground(new Color(null, 251,249,145));
								}
							}
						}
					}

				}
			}
			//PKY 08070701 E TypeRef모델이 있을경우 선택되어 있도록 수정
			
	          //PKY 08072201 S 모델 참조할때 에러발생하는 부분 수정
            if (list != null && list.size() == 1) {
            	Object obj = list.get(0);
            	if (obj instanceof UMLEditPart) {
            		UMLEditPart u = (UMLEditPart)obj;
            		ClassModel classModel=null;
            		if(u.getModel() instanceof ClassifierModel){
            		ClassifierModel um = (ClassifierModel)u.getModel();
            		if(um.getClassModel()!=null)
            		classModel=um.getClassModel();
            		}else if(u.getModel() instanceof ClassifierModelTextAttach){
            			ClassifierModelTextAttach um = (ClassifierModelTextAttach)u.getModel();
                		if(um.getClassModel()!=null)
                		classModel=um.getClassModel();
            		}
            		
            		if(classModel!=null){
            			classModel.getUMLDataModel().getElementProperty("typeRefModel");
    				if(classModel.getUMLDataModel().getElementProperty("typeRefModel")!=null){
    					for( int i=0; i< Models.size(); i++){
    						StrcuturedPackageTreeModel stPackage=(StrcuturedPackageTreeModel)Models.get(i);    						
    						if(stPackage.getRefModel() == classModel.getUMLDataModel().getElementProperty("typeRefModel")){    	
    							tableViewer.getTable().select(i);
    							tableViewer.getTable().getItem(i).setBackground(new Color(null, 251,249,145));
    						}
    					}
    				}
            		}
            	}
            }
          //PKY 08072201 E 모델 참조할때 에러발생하는 부분 수정
			//PKY 08081101 E Type다이어그램 ClassCast 에러문제 수정
			type.pack();
			//3월초~3월말 완료
			//PKY 08082201 S 팀 프로젝트 읽기전용일때 수정불가능하도록 작업
			if(ProjectManager.getInstance()!=null && ProjectManager.getInstance().getSelectPropertyUMLElementModel()!=null){
				UMLElementModel umlElementModel= ProjectManager.getInstance().getSelectPropertyUMLElementModel();
				if(umlElementModel!=null && umlElementModel instanceof UMLModel){
					if(!ProjectManager.getInstance().isItemEnable(ProjectManager.getInstance().getSelectPropertyUMLElementModel())){
						boolean isEnable = ProjectManager.getInstance().isItemEnable(ProjectManager.getInstance().getSelectPropertyUMLElementModel());
						tableViewer.getTable().setEnabled(isEnable);
					}
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
		if (buttonId == RESET_ID) {
			usernameField.setText("");
			passwordField.setText("");
		}
		else {
			if (buttonId == this.OK_ID) {
//				StructuredSelection iSelection = (StructuredSelection)tableViewer.getSelection();
//				Object obj = iSelection.getFirstElement();
//				if (obj != null) {
//				this.selectedBehaviorActivityTableItem = (BehaviorActivityTableItem)obj;
//				}
				this.setSelected();
			}
			super.buttonPressed(buttonId);
		}
	}

	public UMLModel getSelectedUMLModel() {
		if (selectedBehaviorActivityTableItem != null)
			return this.selectedBehaviorActivityTableItem.refModel;
		else
			return null;
	}

	public void setSelected(){

		IStructuredSelection sel = (IStructuredSelection)tableViewer.getSelection();
		Object obj = sel.getFirstElement();
		UMLModel um = (UMLModel)((UMLTreeModel)obj).getRefModel();

		if(selectedModel instanceof ClassifierModel){
			ClassifierModel umModel = (ClassifierModel)selectedModel;

			System.out.println("ddddd77771212");

			Object c = umModel.getClassModel();
			if (c instanceof TypeRefModel) {
				TypeRefModel typeRefModel = (TypeRefModel)c;
				typeRefModel.setTypeRef(um);
				Object obj1 = um;
				if (obj1 instanceof ClassifierModel) {
					try {
						ClassifierModel um1 = (ClassifierModel)obj1;
						um1.getClassModel().addUpdateListener(typeRefModel);
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
				else if(obj1 instanceof ClassifierModelTextAttach) {
					try {
						ClassifierModelTextAttach um1 = (ClassifierModelTextAttach)obj1;
						um1.getClassModel().addUpdateListener(typeRefModel);
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
				else if(obj1 instanceof FinalActorModel) {
					System.out.println("ddddd7777");
					try {
						FinalActorModel um1 = (FinalActorModel)obj1;
						um1.addUpdateListener(typeRefModel);
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
			}//-->
		}
		

	}

	private void attachLabelProvider(TableViewer viewer) {
		viewer.setLabelProvider(
				new ITableLabelProvider() {
					public Image getColumnImage(Object element, int columnIndex) {
						UMLModel um = (UMLModel)((UMLTreeModel)element).getRefModel();
						if(columnIndex==0)
							return ProjectManager.getInstance().getImage(ProjectManager.getInstance().getModelType(um, -1));
						else
							return null;
					}
					public String getColumnText(Object element, int columnIndex) {
						UMLModel um = null;
						try{
							um = (UMLModel)((UMLTreeModel)element).getRefModel();
						}
						catch(Exception e){
							e.printStackTrace();
						}
						switch (columnIndex) {
						case 0:
							String type = ProjectManager.getInstance().getModelTypeName(ProjectManager.getInstance().getModelType(um, -1));
							return type;

						case 1:
							String scope = um.getPackage();
							return scope;

						case 2:
							String name = um.getName();
							return name;

						case 3:
//							String parentElement = ((BehaviorActivityTableItem)element).parentElement;
return "";
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
		layout.addColumnData(new ColumnWeightData(100, 100, true));
		layout.addColumnData(new ColumnWeightData(300, 300, true));
		layout.addColumnData(new ColumnWeightData(100, 100, true));
		//		layout.addColumnData(new ColumnWeightData(50, 75, true));
		table.setLayout(layout);
		TableColumn typeColumn = new TableColumn(table, SWT.LEFT);
		typeColumn.setText(this.TYPE_PROPERTY);
		TableColumn accessColumn = new TableColumn(table, SWT.LEFT);
		accessColumn.setText(this.SCOPE_PROPERTY);
		TableColumn nameColumn = new TableColumn(table, SWT.LEFT);
		nameColumn.setText(this.NAME_PROPERTY);

		//		TableColumn initColumn = new TableColumn(table, SWT.CENTER);
		//		initColumn.setText(this.INITVALUE_PROPERTY);
		table.setHeaderVisible(true);
		return tableViewer;
	}


}
