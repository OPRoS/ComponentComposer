package kr.co.n3soft.n3com.project.dialog;

import java.util.ArrayList;
import java.util.HashMap;

import kr.co.n3soft.n3com.edit.UMLEditPart;
import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.activity.ControlFlowLineModel;
import kr.co.n3soft.n3com.model.comm.LineModel;
import kr.co.n3soft.n3com.model.comm.StrcuturedPackageTreeModel;
import kr.co.n3soft.n3com.model.comm.UMLElementModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.comm.command.ChangeDetailPropCommand;
import kr.co.n3soft.n3com.model.component.AtomicComponentModel;
import kr.co.n3soft.n3com.model.component.OPRoSMonitorVariablesElementModel;
import kr.co.n3soft.n3com.model.umlclass.ImportLineModel;
import kr.co.n3soft.n3com.model.umlclass.MergeLineModel;
import kr.co.n3soft.n3com.model.usecase.AssociateLineModel;
import kr.co.n3soft.n3com.model.usecase.UseCaseModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
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
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class DetailPropertyDiaolg extends Dialog {
	private static final int RESET_ID = IDialogConstants.NO_TO_ALL_ID + 1;
	private static final int OK_ID = 0;
	private static final int CANCEL_ID = 1;
	SashForm sf2 = null;
	HashMap detailLineProp = null;
	private TableViewer trigerViewer;
	private TableViewer sourceViewer;
	private TableViewer targetViewer;
	private TableViewer typeViewer;
	private TableViewer extensionPointViewer;
	private TableViewer tagViewer;
	private TableViewer mViewer;
	private StyledText scenarioStyledText;
	private StyledText descStyledText;
	Composite constraintsStateComposite = null;
	Group guardGroup;
	Group trigerGroup;
	Group trigerButtonGroup;
	Label effectLabel = null;
	Text effectText = null;
	Button addTrigerButton = null;
	Button removeTrigerButton = null;


	Composite sourceComposite = null;
	Composite scenarioComposite = null;	
	Composite extensionPointComposite = null;
	Composite tagComposite = null;
	Composite constraintsComposite = null;
	Label guardLabel = null;
	Text guardText = null;
	Label weightLabel = null;
	Text weightText = null;

	Group sourceGroup;
	Group constraintsGroup;
//	Group multiGroup;
	Label sourceActorRoleLabel = null;
	Text sourceActorRoleText = null;

	Label sourceRoleNoteLabel = null;
	Text sourceRoleNoteText = null;

	Label sourceRoleConstraintLabel = null;
	Text sourceRoleConstraintText = null;
	Label sourceQualifierLabel = null;
	Text sourceQualifierText = null;

	Label sourceDerivedLabel = null;
	Button sourceDerivedButton = null;

	Label sourceDerivedUnionLabel = null;
	Button sourceDerivedUnionButton = null;

	Label sourceOwnedUnionLabel = null;
	Button sourceOwnedButton = null;

	Label sourceOrderedLabel = null;
	Button sourceOrderedButton = null;

	Label sourceAllowDuplicatesLabel = null;
	Button sourceAllowDuplicatesButton = null;

	Label sourceMulLabel = null;
	Combo sourceMulCombo = null;


	Label sourceAggregationLabel = null;
	Combo sourceAggregationCombo = null;

	Label sourceNavigabilityLabel = null;
	Combo sourceNavigabilityCombo = null;

	Composite targetComposite = null;


	Label targetActorRoleLabel = null;
	Text targetActorRoleText = null;

	Label targetRoleNoteLabel = null;
	Text targetRoleNoteText = null;

	Label targetRoleConstraintLabel = null;
	Text targetRoleConstraintText = null;
	Label targetQualifierLabel = null;
	Text targetQualifierText = null;

	Label targetDerivedLabel = null;
	Button targetDerivedButton = null;

	Label targetDerivedUnionLabel = null;
	Button targetDerivedUnionButton = null;

	Label targetOwnedUnionLabel = null;
	Button targetOwnedButton = null;

	Label targetOrderedLabel = null;
	Button targetOrderedButton = null;

	Label targetAllowDuplicatesLabel = null;
	Button targetAllowDuplicatesButton = null;

	Label targetMulLabel = null;
	Combo targetMulCombo = null;


	Label targetAggregationLabel = null;
	Combo targetAggregationCombo = null;

	Label targetNavigabilityLabel = null;
	Combo targetNavigabilityCombo = null;

	Group targetGroup;
	Group treeGroup;
	Group tagGroup;
	Group tagButtonGroup;
	Group extensionPointGroup;
	Group extensionPointButtonGroup;
	Group DescGroup;
	Group groupScenarioDesc;
	Group groupScenarioType;
	Button buttonApply = null;
	Button buttonAdd = null;
	Button buttonRemove = null;
	Button buttonAddTag = null;
	Button buttonRemoveTag = null;
	//PKY 08072201 S 모든 오퍼레이션및 어트리뷰트등 정보 입력한 값에 대한 위치변경할 수있도록 수정
	Button buttonUp = null;
	Button buttonDown = null;
	//PKY 08072201 E 모든 오퍼레이션및 어트리뷰트등 정보 입력한 값에 대한 위치변경할 수있도록 수정
	
	DetailPropertyTableItem oldSit = null;
	public java.util.ArrayList scenario = new java.util.ArrayList();
	public java.util.ArrayList extendsPoints = new java.util.ArrayList();
	public java.util.ArrayList tags = new java.util.ArrayList();
	
	public java.util.ArrayList monitorVariables = new java.util.ArrayList();
	OPRoSMonitorVariablesElementModel mvs = null;
	
	public java.util.ArrayList trigers = new java.util.ArrayList();
	public String desc = "";
	private TreeViewer viewer;

	private static final String[] TRIGER_TYPE_SET = new String[] {
		"Call", "Change", "Signal","Time"
	};

	CTabFolder tabFolder = null;
	UMLModel um = null;

	LineModel lm = null;
//	Composite temp = null;
	private Text text;
	Composite comp = null;

	private static final String TYPE_PROPERTY = N3Messages.POPUP_TYPE;//2008040302 PKY S 
	private static final String EXTENSIONPOINT_PROPERTY = N3Messages.DIALOG_EXTENSIONPOINT;//PKY 08060201 S 

	private static final String TAG_PROPERTY = N3Messages.DIALOG_TAG;//PKY 08060201 S 
	private static final String TAGVALUE_PROPERTY = N3Messages.DIALOG_VALUE;//PKY 08060201 S 


	private static final String TRIGER_NAME =  N3Messages.POPUP_NAME;//PKY 08060201 S 
	private static final String TRIGER_TYPE = N3Messages.POPUP_TYPE;//PKY 08060201 S 
	private static final String TRIGER_SPECIFICATION = N3Messages.DIALOG_SPECIFICATION;//PKY 08060201 S




	public DetailPropertyDiaolg(Shell parentShell) {
		super(parentShell);
		this.setShellStyle(SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE);
		UMLTreeModel  uMLTreeModel= ProjectManager.getInstance().getModelBrowser().getUMLTreeModelSelected();
System.out.println("ProjectManager.getInstance().getSelectPropertyUMLElementModel()==========>"+ProjectManager.getInstance().getSelectPropertyUMLElementModel());
		if (ProjectManager.getInstance().getSelectPropertyUMLElementModel() != null) {
			Object obj = ProjectManager.getInstance().getSelectPropertyUMLElementModel();
			if(obj instanceof UMLModel){
				UMLModel umModel = (UMLModel)obj;
//				UMLModel umModel = (UMLModel)classEditPart.getModel();
				um = (UMLModel)umModel;
				
				scenario = (java.util.ArrayList)um.getDetailProperty().clone();
				this.extendsPoints = (java.util.ArrayList)um.getExtendsPoints().clone();
				tags= (java.util.ArrayList)um.getTags().clone();
				desc = um.getDesc();
				Object obj1 = um.getMonitorVariables();
				if(obj1 !=null){
					monitorVariables = (java.util.ArrayList)um.getMonitorVariables().clone();
				}
//				AtomicComponentModel am = null;
//				if(um instanceof AtomicComponentModel){
//					am = (AtomicComponentModel)um;
//					 mvs = (OPRoSMonitorVariablesElementModel)am.getClassModel().getUMLDataModel().getElementProperty("OPRoSMonitorVariablesElementModel");
//				}
				
			}
			else if(obj instanceof LineModel){
//				LineEditPart classEditPart = (LineEditPart)obj;
				lm = (LineModel)obj;
				this.detailLineProp = (HashMap)lm.getDetailProp();

			}
		}
		else{
			java.util.List list = ProjectManager.getInstance().getSelectNodes();
			if(list!=null && list.size()>0){
				if (list != null && list.size() == 1) {
		            Object obj = list.get(0);
		            if (obj instanceof UMLEditPart) {
		                UMLEditPart u = (UMLEditPart)obj;
		                um = (UMLModel)u.getModel();
		                scenario = (java.util.ArrayList)um.getDetailProperty().clone();
						this.extendsPoints = (java.util.ArrayList)um.getExtendsPoints().clone();
						tags= (java.util.ArrayList)um.getTags().clone();
						desc = um.getDesc();
						monitorVariables = (java.util.ArrayList)um.getMonitorVariables().clone();
		               
		            }
		        }
				
			}
		}

	}



	private void attachLabelProviderExtensionPoints(TableViewer viewer) {
		viewer.setLabelProvider(
				new ITableLabelProvider() {
					public Image getColumnImage(Object element, int columnIndex) {
						if(element instanceof DetailPropertyTableItem){
							DetailPropertyTableItem si = (DetailPropertyTableItem)element;

						}

						return null;

					}
					public String getColumnText(Object element, int columnIndex) {
						if(element instanceof DetailPropertyTableItem){
							DetailPropertyTableItem si = (DetailPropertyTableItem)element;
							return si.desc;
						}
						else 
							return element.toString();
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


	private void attachCellEditorsTriger(final TableViewer viewer, Composite parent) {
		viewer.setCellModifier(
				new ICellModifier() {
					public boolean canModify(Object element, String property) {
						return true;
					}
					public Object getValue(Object element, String property) {
						if (TRIGER_NAME.equals(property))
							return ((DetailPropertyTableItem)element).sName;
						else if (TRIGER_TYPE.equals(property))
							return ((DetailPropertyTableItem)element).sType;
						else if (TRIGER_SPECIFICATION.equals(property))
							return ((DetailPropertyTableItem)element).sSpecification;

						return null;
					}
					public void modify(Object element, String property, Object value) {
						TableItem tableItem = (TableItem)element;
						DetailPropertyTableItem data = (DetailPropertyTableItem)tableItem.getData();
						//PKY 08082201 S 팀 프로젝트 읽기전용일때 수정불가능하도록 작업
						if(ProjectManager.getInstance()!=null && ProjectManager.getInstance().getSelectPropertyUMLElementModel()!=null)
							if(!ProjectManager.getInstance().isItemEnable(ProjectManager.getInstance().getSelectPropertyUMLElementModel()))
								return;
						if (TRIGER_NAME.equals(property)){
							data.sName = value.toString();
						}
						else if (TRIGER_TYPE.equals(property)){
							if((Integer)value!=-1) //20080723 KDI s
								data.sType = (Integer)value;
						}
						else if (TRIGER_SPECIFICATION.equals(property)){
							data.sSpecification = value.toString();
						}
						viewer.refresh(data);
					}
				});
		viewer.setCellEditors(
				new CellEditor[] {
						new TextCellEditor(parent),new ComboBoxCellEditor(parent,TRIGER_TYPE_SET),new TextCellEditor(parent)
				});
		viewer.setColumnProperties(
				new String[] {
						TRIGER_NAME,TRIGER_TYPE,TRIGER_SPECIFICATION
				});
	}


	private void attachCellEditorsTag(final TableViewer viewer, Composite parent) {
		viewer.setCellModifier(
				new ICellModifier() {
					public boolean canModify(Object element, String property) {
						return true;
					}
					public Object getValue(Object element, String property) {
						if (TAG_PROPERTY.equals(property))
							return ((DetailPropertyTableItem)element).tapName;
						else if ("type".equals(property))
							return ((DetailPropertyTableItem)element).sTagType;
						else if (TAGVALUE_PROPERTY.equals(property))
							return ((DetailPropertyTableItem)element).desc;

						return null;
					}
					public void modify(Object element, String property, Object value) {
						TableItem tableItem = (TableItem)element;
						DetailPropertyTableItem data = (DetailPropertyTableItem)tableItem.getData();
						//PKY 08082201 S 팀 프로젝트 읽기전용일때 수정불가능하도록 작업
						if(ProjectManager.getInstance()!=null && ProjectManager.getInstance().getSelectPropertyUMLElementModel()!=null)
							if(!ProjectManager.getInstance().isItemEnable(ProjectManager.getInstance().getSelectPropertyUMLElementModel()))
								return;
						if (TAG_PROPERTY.equals(property)){
							data.tapName = value.toString();
						}
						else if ("type".equals(property)){
							data.sTagType = value.toString();
						}
						else if (TAGVALUE_PROPERTY.equals(property)){
							data.desc = value.toString();
						}
						viewer.refresh(data);
					}
				});
		viewer.setCellEditors(
				new CellEditor[] {
						new TextCellEditor(parent),new TextCellEditor(parent),new TextCellEditor(parent)
				});
		viewer.setColumnProperties(
				new String[] {
						TAG_PROPERTY,"type",TAGVALUE_PROPERTY
				});
	}



	private void attachCellEditorsExtensionPoints(final TableViewer viewer, Composite parent) {
		viewer.setCellModifier(
				new ICellModifier() {
					public boolean canModify(Object element, String property) {
						return true;
					}
					public Object getValue(Object element, String property) {
						if (EXTENSIONPOINT_PROPERTY.equals(property))
							return ((DetailPropertyTableItem)element).desc;

						return null;
					}
					public void modify(Object element, String property, Object value) {
						TableItem tableItem = (TableItem)element;
						DetailPropertyTableItem data = (DetailPropertyTableItem)tableItem.getData();
						//PKY 08082201 S 팀 프로젝트 읽기전용일때 수정불가능하도록 작업
						if(ProjectManager.getInstance()!=null && ProjectManager.getInstance().getSelectPropertyUMLElementModel()!=null)
							if(!ProjectManager.getInstance().isItemEnable(ProjectManager.getInstance().getSelectPropertyUMLElementModel()))
								return;
						if (EXTENSIONPOINT_PROPERTY.equals(property)){
							data.desc = value.toString();
						}
						viewer.refresh(data);
					}
				});
		viewer.setCellEditors(
				new CellEditor[] {
						new TextCellEditor(parent)
				});
		viewer.setColumnProperties(
				new String[] {
						EXTENSIONPOINT_PROPERTY
				});
	}

	public void createTreeCTabItem(CTabFolder p){
		CTabItem descItem = new CTabItem(tabFolder,SWT.NONE);
		descItem.setText(N3Messages.POPUP_DESCRIPTION);//2008040302 PKY S ;
		FillLayout flowLayout2 = new FillLayout();
		tabFolder.setLayout(flowLayout2);
		GridData data6 = new GridData(GridData.FILL_BOTH);
		data6.heightHint = 500;
		data6.widthHint = 500;
		tabFolder.setLayoutData(data6);
		treeGroup = new Group(tabFolder, SWT.SHADOW_ETCHED_IN);
		treeGroup.setLayoutData(data6);
		treeGroup.setLayout(flowLayout2);
		GridData data3 = new GridData(GridData.FILL_BOTH);
		data3.heightHint = 300;
		data3.widthHint = 300;

		descItem.setControl(DescGroup);
		this.viewer = ProjectManager.getInstance().getModelBrowser().getViewer();
		descItem.setControl(treeGroup);
	}
	
	public void createTagCMonitorVariableTabItem(CTabFolder p){
		FillLayout flowLayout = new FillLayout();
		CTabItem descItem = new CTabItem(tabFolder,SWT.NONE);
		descItem.setText("Monitoring Variable"); //20080723 KDI s
		GridLayout gridLayout = new GridLayout(2,false);
		GridLayout gridLayoutButton = new GridLayout(1,true);
		tagComposite = new Composite(tabFolder,comp.getStyle());
		tagComposite.setLayout(gridLayout);



		GridData tagGroupGroupData = new GridData(GridData.FILL_BOTH);
		tagGroupGroupData.heightHint = 500;
		tagGroupGroupData.widthHint = 400;
		GridData tagButtonGroupData = new GridData(GridData.FILL_BOTH);
		tagButtonGroupData.heightHint = 500;
		tagButtonGroupData.widthHint = 50;
		this.tagGroup = new Group(tagComposite, SWT.SHADOW_ETCHED_IN);
		this.tagGroup.setLayout(flowLayout);
		this.tagGroup.setLayoutData(tagGroupGroupData);
		this.tagButtonGroup = new Group(tagComposite, SWT.SHADOW_ETCHED_IN);
		this.tagButtonGroup.setLayout(gridLayoutButton);
		this.tagButtonGroup.setLayoutData(tagButtonGroupData);
		final Table table = new Table(tagGroup, SWT.FULL_SELECTION);

		mViewer = buildAndLayoutTagTable(table);
		this.attachLabelProviderTag(mViewer);
		this.attachCellEditorsTag(mViewer, table);

		if(monitorVariables.toArray().length>0){
			for(int i=0;i<monitorVariables.size();i++){
				DetailPropertyTableItem sti = (DetailPropertyTableItem)monitorVariables.get(i);
				mViewer.add(sti);
			}
		}

		buttonAddTag = new Button(tagButtonGroup,SWT.PUSH);
		buttonRemoveTag = new Button(tagButtonGroup,SWT.PUSH);
		//buttonAddTag.setText(N3Messages.POPUP_ADD);//2008040301 PKY S
		buttonAddTag.setImage(ProjectManager.getInstance().getImage(203));//2008040302 PKY S 
		buttonAddTag.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e)
			{

				DetailPropertyTableItem newItem = new DetailPropertyTableItem();
				newItem.tapName = "name";
				newItem.sTagType = "string";
				mViewer.add(newItem);
				monitorVariables.add(newItem);
			}

			public void widgetDefaultSelected(SelectionEvent e)
			{
			}

		});

		//buttonRemoveTag.setText(N3Messages.POPUP_REMOVE);//2008040301 PKY S 
		buttonRemoveTag.setImage(ProjectManager.getInstance().getImage(204));//2008040302 PKY S 
		buttonRemoveTag.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e)
			{
				int selectIndex=mViewer.getTable().getSelectionIndex();//PKY 08071601 S 다이얼로그 UI작업
				StructuredSelection iSelection = (StructuredSelection)mViewer.getSelection();
				Object obj = iSelection.getFirstElement();
				if (obj != null) {
					mViewer.remove(obj);
					monitorVariables.remove(obj);
					//PKY 08071601 S 다이얼로그 UI작업
					if(mViewer.getTable().getItemCount()>=selectIndex+1){
						mViewer.getTable().select(selectIndex);
					}else{
						mViewer.getTable().select(mViewer.getTable().getItemCount()-1);
					}
					//PKY 08071601 E 다이얼로그 UI작업
				}
			}

			public void widgetDefaultSelected(SelectionEvent e)
			{
			}

		});
		tagButtonGroup.pack();
		
		//PKY 08082201 S 팀 프로젝트 읽기전용일때 수정불가능하도록 작업
		if(ProjectManager.getInstance()!=null && ProjectManager.getInstance().getSelectPropertyUMLElementModel()!=null){
			if(!ProjectManager.getInstance().isItemEnable(ProjectManager.getInstance().getSelectPropertyUMLElementModel())){
				boolean isEnable = ProjectManager.getInstance().isItemEnable(ProjectManager.getInstance().getSelectPropertyUMLElementModel());

					buttonAddTag.setEnabled(false);
					buttonRemoveTag.setEnabled(false);
					buttonUp.setEnabled(false);
					buttonDown.setEnabled(false);

				}
			}
		

		descItem.setControl(tagComposite);
	}



	public void createTagCTabItem(CTabFolder p){
		FillLayout flowLayout = new FillLayout();
		CTabItem descItem = new CTabItem(tabFolder,SWT.NONE);
		descItem.setText("Properties"); //20080723 KDI s
		GridLayout gridLayout = new GridLayout(2,false);
		GridLayout gridLayoutButton = new GridLayout(1,true);
		tagComposite = new Composite(tabFolder,comp.getStyle());
		tagComposite.setLayout(gridLayout);



		GridData tagGroupGroupData = new GridData(GridData.FILL_BOTH);
		tagGroupGroupData.heightHint = 500;
		tagGroupGroupData.widthHint = 400;
		GridData tagButtonGroupData = new GridData(GridData.FILL_BOTH);
		tagButtonGroupData.heightHint = 500;
		tagButtonGroupData.widthHint = 50;
		this.tagGroup = new Group(tagComposite, SWT.SHADOW_ETCHED_IN);
		this.tagGroup.setLayout(flowLayout);
		this.tagGroup.setLayoutData(tagGroupGroupData);
		this.tagButtonGroup = new Group(tagComposite, SWT.SHADOW_ETCHED_IN);
		this.tagButtonGroup.setLayout(gridLayoutButton);
		this.tagButtonGroup.setLayoutData(tagButtonGroupData);
		final Table table = new Table(tagGroup, SWT.FULL_SELECTION);

		tagViewer = buildAndLayoutTagTable(table);
		this.attachLabelProviderTag(tagViewer);
		this.attachCellEditorsTag(tagViewer, table);

		if(tags.toArray().length>0){
			for(int i=0;i<tags.size();i++){
				DetailPropertyTableItem sti = (DetailPropertyTableItem)tags.get(i);
				tagViewer.add(sti);
			}
		}

		buttonAddTag = new Button(tagButtonGroup,SWT.PUSH);
		buttonRemoveTag = new Button(tagButtonGroup,SWT.PUSH);
		//buttonAddTag.setText(N3Messages.POPUP_ADD);//2008040301 PKY S
		buttonAddTag.setImage(ProjectManager.getInstance().getImage(203));//2008040302 PKY S 
		buttonAddTag.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e)
			{

				DetailPropertyTableItem newItem = new DetailPropertyTableItem();
				newItem.tapName = "name";
				newItem.sTagType = "string";
				tagViewer.add(newItem);
				tags.add(newItem);
			}

			public void widgetDefaultSelected(SelectionEvent e)
			{
			}

		});

		//buttonRemoveTag.setText(N3Messages.POPUP_REMOVE);//2008040301 PKY S 
		buttonRemoveTag.setImage(ProjectManager.getInstance().getImage(204));//2008040302 PKY S 
		buttonRemoveTag.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e)
			{
				int selectIndex=tagViewer.getTable().getSelectionIndex();//PKY 08071601 S 다이얼로그 UI작업
				StructuredSelection iSelection = (StructuredSelection)tagViewer.getSelection();
				Object obj = iSelection.getFirstElement();
				if (obj != null) {
					tagViewer.remove(obj);
					tags.remove(obj);
					//PKY 08071601 S 다이얼로그 UI작업
					if(tagViewer.getTable().getItemCount()>=selectIndex+1){
						tagViewer.getTable().select(selectIndex);
					}else{
						tagViewer.getTable().select(tagViewer.getTable().getItemCount()-1);
					}
					//PKY 08071601 E 다이얼로그 UI작업
				}
			}

			public void widgetDefaultSelected(SelectionEvent e)
			{
			}

		});
		
		//PKY 08072201 S 모든 오퍼레이션및 어트리뷰트등 정보 입력한 값에 대한 위치변경할 수있도록 수정
//		buttonUp = new Button(tagButtonGroup,SWT.PUSH);
//		buttonUp.setImage(ProjectManager.getInstance().getImage(330));
//		buttonUp.addSelectionListener(new SelectionListener() {
//			public void widgetSelected(SelectionEvent e)
//			{
//				int selset=0;
//				ArrayList arryList= new ArrayList();
//				StructuredSelection iSelection = (StructuredSelection)tagViewer.getSelection();
//				if(iSelection!=null){
//				Object obj = iSelection.getFirstElement();
//				Table table=tagViewer.getTable();
//				for(int i=0; i < table.getItems().length; i++){
//					if(table.getItem(i).getData()== obj){
//						if(i>0){
//						arryList.add(i-1,table.getItem(i).getData());
//						selset=i-1;
//						}else{
//							arryList.add(table.getItem(i).getData());
//						}
//					}else{
//						arryList.add(table.getItem(i).getData());
//					}
//				}
//					tagViewer.remove(arryList.toArray());	
//				for(int i = 0 ; i < arryList.size(); i++){
//					tagViewer.add(arryList.get(i));	
//				}
//				tagViewer.getTable().select(selset);
//				}
//			}
//			public void widgetDefaultSelected(SelectionEvent e)
//			{
//			}
//		});
//		buttonDown = new Button(tagButtonGroup,SWT.PUSH);
//		buttonDown.setImage(ProjectManager.getInstance().getImage(331));
//		buttonDown.addSelectionListener(new SelectionListener() {
//			public void widgetSelected(SelectionEvent e)
//			{
//				int chanage= 0;
//				boolean ischanage=false;
//				StructuredSelection iSelection = (StructuredSelection)tagViewer.getSelection();
//				if(iSelection!=null){
//				Object obj = iSelection.getFirstElement();
//				Table table=tagViewer.getTable();
//				java.util.Vector arryList= new java.util.Vector (table.getItems().length);
//				for(int i=0; i < table.getItems().length; i++){
//					if(table.getItem(i).getData()== obj){
//						chanage=i;
//						ischanage=true;
//					}else{
//						arryList.add(table.getItem(i).getData());
//					}
//					
//				}
//				if(ischanage==true){
//					arryList.add(chanage+1,obj);
//				}
////				removeNullArray(arryList);
//				tagViewer.remove(arryList.toArray());	
//				for(int i = 0 ; i < arryList.size(); i++){
//					tagViewer.add(arryList.get(i));	
//				}
//				tagViewer.getTable().select(chanage+1);
//				
//				}
//			}
//			public void widgetDefaultSelected(SelectionEvent e)
//			{
//			}
//		});
		//PKY 08072201 E 모든 오퍼레이션및 어트리뷰트등 정보 입력한 값에 대한 위치변경할 수있도록 수정
		
		tagButtonGroup.pack();
		
		//PKY 08082201 S 팀 프로젝트 읽기전용일때 수정불가능하도록 작업
		if(ProjectManager.getInstance()!=null && ProjectManager.getInstance().getSelectPropertyUMLElementModel()!=null){
			if(!ProjectManager.getInstance().isItemEnable(ProjectManager.getInstance().getSelectPropertyUMLElementModel())){
				boolean isEnable = ProjectManager.getInstance().isItemEnable(ProjectManager.getInstance().getSelectPropertyUMLElementModel());

					buttonAddTag.setEnabled(false);
					buttonRemoveTag.setEnabled(false);
					buttonUp.setEnabled(false);
					buttonDown.setEnabled(false);

				}
			}
		

		descItem.setControl(tagComposite);
	}



	public void createStateConstratintsCTabItem(CTabFolder p){
		CTabItem stateConstratintsItem = new CTabItem(tabFolder,SWT.NONE);
		
		comp.getShell().setText(N3Messages.PALETTE_TRANSITION); //20080724 KDI s
		comp.getShell().setImage(ProjectManager.getInstance().getImage(334)); //20080724 KDI s
		
		stateConstratintsItem.setText(N3Messages.DIALOG_TAB_CONSTRAINTS); //20080723 KDI s
		GridLayout gridLayout = new GridLayout(2,false);
		//20080723 KDI s
//		gridLayout.verticalSpacing = 15; 
//		gridLayout.marginBottom = 10;
		gridLayout.marginLeft = 5;
		gridLayout.marginRight = 15;
//		gridLayout.marginTop = 10;
		//20080723 KDI e
		constraintsStateComposite = new Composite(tabFolder,comp.getStyle());
		guardGroup =   new Group(constraintsStateComposite, SWT.SHADOW_ETCHED_IN);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.heightHint = 500;
		gridData.widthHint = 400;
		guardGroup.setLayout(gridLayout);
		guardGroup.setLayoutData(gridLayout);
		guardLabel =  new Label(guardGroup, SWT.RIGHT);
		guardLabel.setText(N3Messages.DIALOG_GUARD+": "); //20080723 KDI s
		guardText = new Text(guardGroup, SWT.SINGLE | SWT.BORDER);
		gridData = new GridData();
		gridData.widthHint = 400;
		guardText.setLayoutData(gridData);
		gridData = new GridData();
		gridData.widthHint = 382; ////20080723 KDI s
		gridData.heightHint = 50;

		effectLabel =  new Label(guardGroup, SWT.RIGHT);
		effectLabel.setText(N3Messages.DIALOG_EFFECT+": "); //20080723 KDI s
		effectText = new Text(guardGroup, SWT.MULTI|SWT.H_SCROLL|SWT.V_SCROLL | SWT.BORDER);
		effectText.setLayoutData(gridData);

		if(this.detailLineProp!=null){
			String value = (String)detailLineProp.get(LineModel.ID_GUARD);
			if(value!=null)
				guardText.setText(value);
			value = (String)detailLineProp.get(LineModel.ID_TRAN_EFFECTS);
			if(value!=null)
				effectText.setText(value);
		}


		trigerGroup = new Group(guardGroup, SWT.SHADOW_ETCHED_IN);
		gridData = new GridData(GridData.FILL_BOTH);
		gridData.widthHint = 400;
		gridData.heightHint = 300;
		gridData.horizontalSpan = 2;

		trigerGroup.setLayout(gridLayout);
		trigerGroup.setLayoutData(gridData);
		final Table table = new Table(trigerGroup, SWT.FULL_SELECTION);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.widthHint = 400;
		gridData.heightHint = 250; //20080723 KDI s
		gridData.horizontalSpan = 2;
		table.setLayoutData(gridData);
		this.trigerViewer = buildAndLayoutTrigerTable(table);
		this.attachLabelProviderTriger(trigerViewer);
		this.attachCellEditorsTriger(trigerViewer, table);
		if(this.detailLineProp.get("ID_TRAN_TRIGERS")!=null){
			this.trigers = (java.util.ArrayList)this.detailLineProp.get("ID_TRAN_TRIGERS");
		}
		if(trigers!=null && trigers.toArray().length>0){
			for(int i=0;i<trigers.size();i++){
				DetailPropertyTableItem sti = (DetailPropertyTableItem)trigers.get(i);
				trigerViewer.add(sti);
			}
		}


		this.addTrigerButton = new Button(trigerGroup,SWT.PUSH);
//		addTrigerButton.setText(N3Messages.POPUP_ADD);//2008040302 PKY S
		addTrigerButton.setToolTipText(N3Messages.POPUP_ADD);//PKY 08071601 S State 다이얼로그 아이콘 삽입
		addTrigerButton.setImage(ProjectManager.getInstance().getImage(203));//PKY 08071601 S State 다이얼로그 아이콘 삽입
		gridData = new GridData();
		gridData.widthHint = 50;
		addTrigerButton.setLayoutData(gridData);
		this.removeTrigerButton = new Button(trigerGroup,SWT.PUSH);
//		removeTrigerButton.setText(N3Messages.POPUP_REMOVE);//2008040302 PKY S 
		removeTrigerButton.setToolTipText(N3Messages.POPUP_REMOVE);//PKY 08071601 S State 다이얼로그 아이콘 삽입
		removeTrigerButton.setImage(ProjectManager.getInstance().getImage(204));//PKY 08071601 S State 다이얼로그 아이콘 삽입
//		removeTrigerButton.setText("remove");
		gridData = new GridData();
		gridData.widthHint = 50;
		removeTrigerButton.setLayoutData(gridData);
		addTrigerButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e)
			{
				DetailPropertyTableItem newItem = new DetailPropertyTableItem();
				newItem.sName = "triger";
				newItem.sType = 0;
				trigers.add(newItem);
				trigerViewer.add(newItem);
			}

			public void widgetDefaultSelected(SelectionEvent e)
			{
			}

		});

		removeTrigerButton.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e)
			{
				//20080723 KDI s
				int selectIndex=trigerViewer.getTable().getSelectionIndex();
				StructuredSelection iSelection = (StructuredSelection)trigerViewer.getSelection();
				Object obj = iSelection.getFirstElement();
				DetailPropertyTableItem newItem = (DetailPropertyTableItem)obj;
				if (obj != null) {
					try{
						trigerViewer.remove(obj);
						trigers.remove(obj);
					}
					catch(Exception e1){
						e1.printStackTrace();
					}

					obj = trigerViewer.getTable().getItem(trigerViewer.getTable().getItemCount()-1);
					if(obj instanceof TableItem ){													
						if(trigerViewer.getTable().getItemCount()>=selectIndex+1){
							trigerViewer.getTable().select(selectIndex);
						}else{
							trigerViewer.getTable().select(trigerViewer.getTable().getItemCount()-1);
						}							
					}

				}				
				//20080723 KDI s
			}

			public void widgetDefaultSelected(SelectionEvent e)
			{
			}

		});
		trigerGroup.pack();
		guardGroup.pack();
		stateConstratintsItem.setControl(constraintsStateComposite);

	}


	public void createConstraintsCTabItem(CTabFolder p){
		CTabItem constraintsItem = new CTabItem(tabFolder,SWT.NONE);

		comp.getShell().setText(N3Messages.PALETTE_CONTROLFLOW); //20080724 KDI s
		comp.getShell().setImage(ProjectManager.getInstance().getImage(333)); //20080724 KDI s
		
		constraintsItem.setText(N3Messages.DIALOG_TAB_CONSTRAINTS); //20080723 KDI s
		GridLayout gridLayout = new GridLayout(2,false);
		//20080723 KDI s
		gridLayout.verticalSpacing = 15; 
		gridLayout.marginBottom = 10;
		gridLayout.marginLeft = 5;
		gridLayout.marginRight = 15;
		gridLayout.marginTop = 10;
		//20080723 KDI e
		constraintsComposite = new Composite(tabFolder,comp.getStyle());
		constraintsGroup =   new Group(constraintsComposite, SWT.SHADOW_ETCHED_IN);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.heightHint = 500;
		gridData.widthHint = 400;
		constraintsGroup.setLayout(gridLayout);
		constraintsGroup.setLayoutData(gridLayout);
		guardLabel =  new Label(constraintsGroup, SWT.RIGHT);
		guardLabel.setText(N3Messages.DIALOG_GUARD+": "); //20080723 KDI s
		guardText = new Text(constraintsGroup, SWT.SINGLE | SWT.BORDER);
		gridData = new GridData();
		gridData.widthHint = 400; 


		guardText.setLayoutData(gridData);
		gridData = new GridData();
		gridData.widthHint = 382; //20080723 KDI s
		gridData.heightHint = 100; //20080723 KDI s		

		weightLabel =  new Label(constraintsGroup, SWT.RIGHT);
		weightLabel.setText(N3Messages.DIALOG_WEIGHT+": "); //20080723 KDI s
		weightText = new Text(constraintsGroup, SWT.MULTI|SWT.H_SCROLL|SWT.V_SCROLL | SWT.BORDER);
		weightText.setLayoutData(gridData);
		constraintsGroup.pack();
		if(this.detailLineProp!=null){
			String value = (String)detailLineProp.get(LineModel.ID_GUARD);
			if(value!=null)
				guardText.setText(value);
			value = (String)detailLineProp.get(LineModel.ID_WEIGHT);
			if(value!=null)
				weightText.setText(value);
		}


		constraintsItem.setControl(constraintsComposite);
	}


	public void createSourceCTabItem(CTabFolder p){
		CTabItem sourceItem = new CTabItem(tabFolder,SWT.NONE);

		comp.getShell().setText(N3Messages.PALETTE_ASSOCIATE); //20080724 KDI s
		comp.getShell().setImage(ProjectManager.getInstance().getImage(332)); //20080724 KDI s
		
		sourceItem.setText(N3Messages.DIALOG_SOURCEROLE); //20080718 KDI s		
		GridLayout gridLayout = new GridLayout(4,false);
		sourceComposite = new Composite(tabFolder,comp.getStyle());
		sourceGroup =   new Group(sourceComposite, SWT.SHADOW_ETCHED_IN);
		sourceGroup.setText(N3Messages.DIALOG_SOURCEROLE);//20080718 KDI s
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.heightHint = 500;//20080718 KDI s	
		gridData.widthHint = 400;
		sourceGroup.setLayout(gridLayout);
		sourceGroup.setLayoutData(gridData);//20080718 KDI s	
		sourceActorRoleLabel =  new Label(sourceGroup, SWT.RIGHT);
		sourceActorRoleLabel.setText(N3Messages.DIALOG_ROLE+": ");//20080718 KDI s
		sourceActorRoleText = new Text(sourceGroup, SWT.SINGLE | SWT.BORDER);
		gridData = new GridData();
		gridData.widthHint  = 370;//2008040302 PKY S
		gridData.horizontalSpan = 3; //20080718 KDI s
		sourceActorRoleText.setLayoutData(gridData);



		sourceRoleNoteLabel =  new Label(sourceGroup, SWT.RIGHT);
		sourceRoleNoteLabel.setText(N3Messages.DIALOG_ROLENOTE+": ");//20080718 KDI s
		gridData = new GridData();
		gridData.widthHint = 352;//2008040302 PKY S 
		gridData.heightHint = 100;
		gridData.horizontalSpan = 3; //20080718 KDI s
		sourceRoleNoteText = new Text(sourceGroup, SWT.MULTI|SWT.H_SCROLL|SWT.V_SCROLL | SWT.BORDER);
		sourceRoleNoteText.setLayoutData(gridData);

		sourceRoleConstraintLabel =  new Label(sourceGroup, SWT.RIGHT);
		sourceRoleConstraintLabel.setText(N3Messages.DIALOG_CONSTRAINTS+": ");//20080718 KDI s
		sourceRoleConstraintText = new Text(sourceGroup, SWT.SINGLE | SWT.BORDER);
		gridData = new GridData();
		gridData.widthHint  = 370;//2008040302 PKY S
		gridData.horizontalSpan = 3; //20080718 KDI s
		sourceRoleConstraintText.setLayoutData(gridData);

		sourceQualifierLabel =  new Label(sourceGroup, SWT.RIGHT);
		sourceQualifierLabel.setText(N3Messages.DIALOG_QUALIFIERS+": ");//20080718 KDI s
		sourceQualifierText = new Text(sourceGroup, SWT.SINGLE | SWT.BORDER);
		gridData = new GridData();
		gridData.widthHint  = 370;//2008040302 PKY S 
		gridData.horizontalSpan = 3; //20080718 KDI s
		sourceQualifierText.setLayoutData(gridData);

		sourceDerivedLabel =  new Label(sourceGroup, SWT.RIGHT);
		sourceDerivedLabel.setText(N3Messages.DIALOG_DERIVED+": ");//20080718 KDI s
		sourceDerivedButton = new Button(sourceGroup,SWT.CHECK);
		//20080718 KDI s
		gridData = new GridData();
		gridData.widthHint  = 160;
		sourceDerivedButton.setLayoutData(gridData);
		//20080718 KDI e

		sourceDerivedUnionLabel =  new Label(sourceGroup, SWT.RIGHT);
		sourceDerivedUnionLabel.setText(N3Messages.DIALOG_DERIVED_UNION+": ");//20080718 KDI s
		sourceDerivedUnionButton = new Button(sourceGroup,SWT.CHECK);



		sourceOwnedUnionLabel =  new Label(sourceGroup, SWT.RIGHT);
		sourceOwnedUnionLabel.setText(N3Messages.DIALOG_OWNED+": ");//20080718 KDI s
		sourceOwnedButton = new Button(sourceGroup,SWT.CHECK);


		sourceOrderedLabel =  new Label(sourceGroup, SWT.RIGHT);
		sourceOrderedLabel.setText(N3Messages.DIALOG_ORDERED+": ");//20080718 KDI s
		sourceOrderedButton = new Button(sourceGroup,SWT.CHECK);

		sourceAllowDuplicatesLabel =  new Label(sourceGroup, SWT.RIGHT);
		sourceAllowDuplicatesLabel.setText("Allow Duplicates: ");
		sourceAllowDuplicatesButton = new Button(sourceGroup,SWT.CHECK);


		sourceMulLabel =  new Label(sourceGroup, SWT.RIGHT);
		sourceMulLabel.setText(N3Messages.DIALOG_MULTIPLICITY+": ");	//20080718 KDI s
		sourceMulCombo = new Combo(sourceGroup,SWT.DROP_DOWN);
		sourceMulCombo.add("*");
		sourceMulCombo.add("0");
		sourceMulCombo.add("0..*");
		sourceMulCombo.add("0..1");
		sourceMulCombo.add("1");
		sourceMulCombo.add("1..");
		sourceMulCombo.add("1..*");
//		sourceMulCombo.select(0);


		sourceAggregationLabel =  new Label(sourceGroup, SWT.RIGHT);
		sourceAggregationLabel.setText(N3Messages.DIALOG_AGGREGATION+": ");	//20080718 KDI s
		sourceAggregationCombo = new Combo(sourceGroup,SWT.READ_ONLY);
		sourceAggregationCombo.add("none");
		sourceAggregationCombo.add("Aggregate");
		sourceAggregationCombo.add("Composite");
		sourceAggregationCombo.select(0);

		sourceNavigabilityLabel =  new Label(sourceGroup, SWT.RIGHT);
		sourceNavigabilityLabel.setText(N3Messages.DIALOG_NAVIGABILITY+": ");	//20080718 KDI s
		sourceNavigabilityCombo = new Combo(sourceGroup,SWT.READ_ONLY);
		sourceNavigabilityCombo.add("Unspecified");
		sourceNavigabilityCombo.add("Navigable");
		sourceNavigabilityCombo.add("Non-Navigable");
		sourceNavigabilityCombo.select(0);

		String value = (String)detailLineProp.get(LineModel.ID_SOURCE_ROLE);
		if(value!=null)
			sourceActorRoleText.setText(value);

		value = (String)detailLineProp.get(LineModel.ID_SOURCE_ROLE_NOTE);
		if(value!=null)
			sourceRoleNoteText.setText(value);

		value = (String)detailLineProp.get(LineModel.ID_SOURCE_CONSTRAINT);
		if(value!=null)
			sourceRoleConstraintText.setText(value);

		value = (String)detailLineProp.get(LineModel.ID_SOURCE_QUALIFIER);
		if(value!=null)
			sourceQualifierText.setText(value);
		boolean isChk = false;
		value = (String)detailLineProp.get(LineModel.ID_SOURCE_DERIVED);
		if(value!=null &&(value.trim().equals("true")))
			isChk = true;
		sourceDerivedButton.setSelection(isChk);
		isChk = false;
		value = (String)detailLineProp.get(LineModel.ID_SOURCE_DERIVEDUNION);
		if(value!=null &&(value.trim().equals("true")))
			isChk = true;
		sourceDerivedUnionButton.setSelection(isChk);
		isChk = false;
		value = (String)detailLineProp.get(LineModel.ID_SOURCE_OWNED);
		if(value!=null &&(value.trim().equals("true")))
			isChk = true;
		sourceOwnedButton.setSelection(isChk);
		isChk = false;
		value = (String)detailLineProp.get(LineModel.ID_SOURCE_ORDERED);
		if(value!=null &&(value.trim().equals("true")))
			isChk = true;
		this.sourceOrderedButton.setSelection(isChk);
		isChk = false;

		value = (String)detailLineProp.get(LineModel.ID_SOURCE_ALLOWDUPLICATES);
		if(value!=null &&(value.trim().equals("true")))
			isChk = true;
		sourceAllowDuplicatesButton.setSelection(isChk);
		isChk = false;
		value = (String)detailLineProp.get(LineModel.ID_SOURCE_MUL);
		if(value!=null){
			boolean isEq = false;
			for(int i=0;i<sourceMulCombo.getItemCount();i++){
				String item = sourceMulCombo.getItem(i);
				if(item.equals(value)){
					sourceMulCombo.select(i);
					isEq = true;
					break;
				}
			}
			sourceMulCombo.add(value);

		}

		value = (String)detailLineProp.get(LineModel.ID_SOURCE_AGGREGATION);
		if(value!=null){

			for(int i=0;i<sourceAggregationCombo.getItemCount();i++){
				String item = sourceAggregationCombo.getItem(i);
				if(item.equals(value)){
					sourceAggregationCombo.select(i);

					break;
				}
			}


		}

		value = (String)detailLineProp.get(LineModel.ID_SOURCE_NAVIGABILITY);
		if(value!=null){

			for(int i=0;i<sourceNavigabilityCombo.getItemCount();i++){
				String item = sourceNavigabilityCombo.getItem(i);
				if(item.equals(value)){
					sourceNavigabilityCombo.select(i);

					break;
				}
			}


		}

		sourceGroup.pack();
		sourceItem.setControl(sourceComposite);
		//PKY 08082201 S 팀 프로젝트 읽기전용일때 수정불가능하도록 작업
		if(ProjectManager.getInstance()!=null && ProjectManager.getInstance().getSelectPropertyUMLElementModel()!=null){
			if(!ProjectManager.getInstance().isItemEnable(ProjectManager.getInstance().getSelectPropertyUMLElementModel())){
				boolean isEnable = ProjectManager.getInstance().isItemEnable(ProjectManager.getInstance().getSelectPropertyUMLElementModel());
					sourceNavigabilityCombo.setEnabled(isEnable);
					sourceAggregationCombo.setEnabled(isEnable);
					sourceMulCombo.setEnabled(isEnable);
					sourceAllowDuplicatesButton.setEnabled(isEnable);
					sourceOrderedButton.setEnabled(isEnable);
					sourceOwnedButton.setEnabled(isEnable);
					sourceDerivedUnionButton.setEnabled(isEnable);
					sourceDerivedButton.setEnabled(isEnable);
				}
			}
		
		sourceActorRoleText.addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent e){
				if(ProjectManager.getInstance()!=null && ProjectManager.getInstance().getSelectPropertyUMLElementModel()!=null){
					UMLElementModel umlElementModel= ProjectManager.getInstance().getSelectPropertyUMLElementModel();
					if(umlElementModel!=null && umlElementModel instanceof LineModel){
						LineModel lineModel = (LineModel)umlElementModel;
						if(!ProjectManager.getInstance().isItemEnable(ProjectManager.getInstance().getSelectPropertyUMLElementModel())){
							sourceActorRoleText.setText("");
							String value = (String)detailLineProp.get(LineModel.ID_SOURCE_ROLE);
							if(value!=null)
								sourceActorRoleText.setText(value);
						}
					}
				}
			}
			public void keyReleased(KeyEvent e){

				if(ProjectManager.getInstance()!=null && ProjectManager.getInstance().getSelectPropertyUMLElementModel()!=null){
					UMLElementModel umlElementModel= ProjectManager.getInstance().getSelectPropertyUMLElementModel();
					if(umlElementModel!=null && umlElementModel instanceof LineModel){
						LineModel lineModel = (LineModel)umlElementModel;
						if(lineModel.getDiagram()!=null && lineModel.getDiagram().isReadOnlyModel()){
							sourceActorRoleText.setText("");
							String value = (String)detailLineProp.get(LineModel.ID_SOURCE_ROLE);
							if(value!=null)
								sourceActorRoleText.setText(value);
						}
					}
				}
			
			}
		});
		sourceRoleNoteText.addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent e){
				if(ProjectManager.getInstance()!=null && ProjectManager.getInstance().getSelectPropertyUMLElementModel()!=null){
					UMLElementModel umlElementModel= ProjectManager.getInstance().getSelectPropertyUMLElementModel();
					if(umlElementModel!=null && umlElementModel instanceof LineModel){
						LineModel lineModel = (LineModel)umlElementModel;
						if(!ProjectManager.getInstance().isItemEnable(ProjectManager.getInstance().getSelectPropertyUMLElementModel())){
							sourceRoleNoteText.setText("");
							String value = (String)detailLineProp.get(LineModel.ID_SOURCE_ROLE_NOTE);
							if(value!=null)
								sourceRoleNoteText.setText(value);
						}
					}
				}
			}
			public void keyReleased(KeyEvent e){

				if(ProjectManager.getInstance()!=null && ProjectManager.getInstance().getSelectPropertyUMLElementModel()!=null){
					UMLElementModel umlElementModel= ProjectManager.getInstance().getSelectPropertyUMLElementModel();
					if(umlElementModel!=null && umlElementModel instanceof LineModel){
						LineModel lineModel = (LineModel)umlElementModel;
						if(lineModel.getDiagram()!=null && lineModel.getDiagram().isReadOnlyModel()){
							sourceRoleNoteText.setText("");
							String value = (String)detailLineProp.get(LineModel.ID_SOURCE_ROLE_NOTE);
							if(value!=null)
								sourceRoleNoteText.setText(value);
						}
					}
				}
			

			}
		});
		sourceRoleConstraintText.addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent e){
				if(ProjectManager.getInstance()!=null && ProjectManager.getInstance().getSelectPropertyUMLElementModel()!=null){
					UMLElementModel umlElementModel= ProjectManager.getInstance().getSelectPropertyUMLElementModel();
					if(umlElementModel!=null && umlElementModel instanceof LineModel){
						LineModel lineModel = (LineModel)umlElementModel;
						if(!ProjectManager.getInstance().isItemEnable(ProjectManager.getInstance().getSelectPropertyUMLElementModel())){
							sourceRoleConstraintText.setText("");
							String value = (String)detailLineProp.get(LineModel.ID_SOURCE_CONSTRAINT);
							if(value!=null)
								sourceRoleConstraintText.setText(value);
						}
					}
				}
			}
			public void keyReleased(KeyEvent e){

				if(ProjectManager.getInstance()!=null && ProjectManager.getInstance().getSelectPropertyUMLElementModel()!=null){
					UMLElementModel umlElementModel= ProjectManager.getInstance().getSelectPropertyUMLElementModel();
					if(umlElementModel!=null && umlElementModel instanceof LineModel){
						LineModel lineModel = (LineModel)umlElementModel;
						if(lineModel.getDiagram()!=null && lineModel.getDiagram().isReadOnlyModel()){
							sourceRoleConstraintText.setText("");
							String value = (String)detailLineProp.get(LineModel.ID_SOURCE_CONSTRAINT);
							if(value!=null)
								sourceRoleConstraintText.setText(value);
						}
					}
				}
			

			}
		});
		sourceQualifierText.addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent e){
				if(ProjectManager.getInstance()!=null && ProjectManager.getInstance().getSelectPropertyUMLElementModel()!=null){
					UMLElementModel umlElementModel= ProjectManager.getInstance().getSelectPropertyUMLElementModel();
					if(umlElementModel!=null && umlElementModel instanceof LineModel){
						LineModel lineModel = (LineModel)umlElementModel;
						if(!ProjectManager.getInstance().isItemEnable(ProjectManager.getInstance().getSelectPropertyUMLElementModel())){
							sourceQualifierText.setText("");
							String value = (String)detailLineProp.get(LineModel.ID_SOURCE_QUALIFIER);
							if(value!=null)
								sourceQualifierText.setText(value);
						}
					}
				}
			}
			public void keyReleased(KeyEvent e){

				if(ProjectManager.getInstance()!=null && ProjectManager.getInstance().getSelectPropertyUMLElementModel()!=null){
					UMLElementModel umlElementModel= ProjectManager.getInstance().getSelectPropertyUMLElementModel();
					if(umlElementModel!=null && umlElementModel instanceof LineModel){
						LineModel lineModel = (LineModel)umlElementModel;
						if(lineModel.getDiagram()!=null && lineModel.getDiagram().isReadOnlyModel()){
							sourceQualifierText.setText("");
							String value = (String)detailLineProp.get(LineModel.ID_SOURCE_QUALIFIER);
							if(value!=null)
								sourceQualifierText.setText(value);
						}
					}
				}
			

			}
		});
		//PKY 08082201 E 팀 프로젝트 읽기전용일때 수정불가능하도록 작업
	}


	public void createTargetCTabItem(CTabFolder p){
		CTabItem targetItem = new CTabItem(tabFolder,SWT.NONE);

		targetItem.setText(N3Messages.DIALOG_TARGETROLE); //20080718 KDI s
		GridLayout gridLayout = new GridLayout(4,false); //20080718 KDI s
		targetComposite = new Composite(tabFolder,comp.getStyle());
		targetGroup =   new Group(targetComposite, SWT.SHADOW_ETCHED_IN);
		targetGroup.setText(N3Messages.DIALOG_TARGETROLE); //20080718 KDI s
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.heightHint = 500;//20080718 KDI s	
		gridData.widthHint = 400;
		targetGroup.setLayout(gridLayout);
		targetGroup.setLayoutData(gridData);//20080718 KDI s	
		targetActorRoleLabel =  new Label(targetGroup, SWT.RIGHT);
		targetActorRoleLabel.setText(N3Messages.DIALOG_ROLE+": "); //20080718 KDI s
		targetActorRoleText = new Text(targetGroup, SWT.SINGLE | SWT.BORDER);
		gridData = new GridData();
		gridData.widthHint = 370; //PKY 08070101 S 다이얼로그 모양 정리
		gridData.horizontalSpan = 3; //20080718 KDI s
		targetActorRoleText.setLayoutData(gridData);


		targetRoleNoteLabel =  new Label(targetGroup, SWT.RIGHT);
		targetRoleNoteLabel.setText(N3Messages.DIALOG_ROLENOTE+": "); //20080718 KDI s
		gridData = new GridData();
		gridData.widthHint = 352;//PKY 08070101 S 다이얼로그 모양 정리
		gridData.heightHint = 100;
		gridData.horizontalSpan = 3; //20080718 KDI s
		targetRoleNoteText = new Text(targetGroup, SWT.MULTI|SWT.H_SCROLL|SWT.V_SCROLL | SWT.BORDER);
		targetRoleNoteText.setLayoutData(gridData);

		targetRoleConstraintLabel =  new Label(targetGroup, SWT.RIGHT);
		targetRoleConstraintLabel.setText(N3Messages.DIALOG_CONSTRAINTS+": "); //20080718 KDI s
		targetRoleConstraintText = new Text(targetGroup, SWT.SINGLE | SWT.BORDER);
		gridData = new GridData();
		gridData.widthHint = 370;//PKY 08070101 S 다이얼로그 모양 정리
		gridData.horizontalSpan = 3; //20080718 KDI s
		targetRoleConstraintText.setLayoutData(gridData);

		targetQualifierLabel =  new Label(targetGroup, SWT.RIGHT);
		targetQualifierLabel.setText(N3Messages.DIALOG_QUALIFIERS+": "); //20080718 KDI s
		targetQualifierText = new Text(targetGroup, SWT.SINGLE | SWT.BORDER);
		gridData = new GridData();
		gridData.widthHint = 370;//PKY 08070101 S 다이얼로그 모양 정리
		gridData.horizontalSpan = 3; //20080718 KDI s
		targetQualifierText.setLayoutData(gridData);



		targetDerivedLabel =  new Label(targetGroup, SWT.RIGHT);
		targetDerivedLabel.setText(N3Messages.DIALOG_DERIVED+": "); //20080718 KDI s
		targetDerivedButton = new Button(targetGroup,SWT.CHECK);
		//20080718 KDI s
		gridData = new GridData();
		gridData.widthHint = 160;		
		targetDerivedButton.setLayoutData(gridData);
		//20080718 KDI e

		targetDerivedUnionLabel =  new Label(targetGroup, SWT.RIGHT);
		targetDerivedUnionLabel.setText(N3Messages.DIALOG_DERIVED_UNION+": ");//20080718 KDI s
		targetDerivedUnionButton = new Button(targetGroup,SWT.CHECK);

		targetOwnedUnionLabel =  new Label(targetGroup, SWT.RIGHT);
		targetOwnedUnionLabel.setText(N3Messages.DIALOG_OWNED+": ");//20080718 KDI s
		targetOwnedButton = new Button(targetGroup,SWT.CHECK);




		targetOrderedLabel =  new Label(targetGroup, SWT.RIGHT);
		targetOrderedLabel.setText(N3Messages.DIALOG_ORDERED+": ");//20080718 KDI s
		targetOrderedButton = new Button(targetGroup,SWT.CHECK);
		targetAllowDuplicatesLabel =  new Label(targetGroup, SWT.RIGHT);
		targetAllowDuplicatesLabel.setText(N3Messages.DIALOG_ALLOW_DUPLICATES+": ");//20080718 KDI s
		targetAllowDuplicatesButton = new Button(targetGroup,SWT.CHECK);

		targetMulLabel =  new Label(targetGroup, SWT.RIGHT);
		targetMulLabel.setText(N3Messages.DIALOG_MULTIPLICITY+": ");	//20080718 KDI s
		targetMulCombo = new Combo(targetGroup,SWT.DROP_DOWN);
		targetMulCombo.add("*");
		targetMulCombo.add("0");
		targetMulCombo.add("0..*");
		targetMulCombo.add("0..1");
		targetMulCombo.add("1");
		targetMulCombo.add("1..");
		targetMulCombo.add("1..*");
//		targetMulCombo.select(0);


		targetAggregationLabel =  new Label(targetGroup, SWT.RIGHT);
		targetAggregationLabel.setText(N3Messages.DIALOG_AGGREGATION+": ");	//20080718 KDI s
		targetAggregationCombo = new Combo(targetGroup,SWT.READ_ONLY);
		targetAggregationCombo.add("none");
		targetAggregationCombo.add("Aggregate");
		targetAggregationCombo.add("Composite");//PKY 08061101 S Associate, Tagetrole aggregation 설정안됬던 문제 수정
		targetAggregationCombo.select(0);

		targetNavigabilityLabel =  new Label(targetGroup, SWT.RIGHT);
		targetNavigabilityLabel.setText(N3Messages.DIALOG_NAVIGABILITY+": ");	//20080718 KDI s
		targetNavigabilityCombo = new Combo(targetGroup,SWT.READ_ONLY);
		targetNavigabilityCombo.add("Unspecified");
		targetNavigabilityCombo.add("Navigable");
		targetNavigabilityCombo.add("Non-Navigable");
		targetNavigabilityCombo.select(0);
		targetGroup.pack();
		String value = (String)detailLineProp.get(LineModel.ID_TARGET_ROLE);
		if(value!=null)
			targetActorRoleText.setText(value);

		value = (String)detailLineProp.get(LineModel.ID_TARGET_ROLE_NOTE);
		if(value!=null)
			targetRoleNoteText.setText(value);

		value = (String)detailLineProp.get(LineModel.ID_TARGET_CONSTRAINT);
		if(value!=null)
			targetRoleConstraintText.setText(value);

		value = (String)detailLineProp.get(LineModel.ID_TARGET_QUALIFIER);
		if(value!=null)
			targetQualifierText.setText(value);
		boolean isChk = false;
		value = (String)detailLineProp.get(LineModel.ID_TARGET_DERIVED);
		if(value!=null &&(value.trim().equals("true")))
			isChk = true;
		targetDerivedButton.setSelection(isChk);
		isChk = false;
		value = (String)detailLineProp.get(LineModel.ID_TARGET_DERIVEDUNION);
		if(value!=null &&(value.trim().equals("true")))
			isChk = true;
		targetDerivedUnionButton.setSelection(isChk);
		isChk = false;
		value = (String)detailLineProp.get(LineModel.ID_TARGET_OWNED);
		if(value!=null &&(value.trim().equals("true")))
			isChk = true;
		targetOwnedButton.setSelection(isChk);
		isChk = false;
		value = (String)detailLineProp.get(LineModel.ID_TARGET_ORDERED);
		if(value!=null &&(value.trim().equals("true")))
			isChk = true;
		this.targetOrderedButton.setSelection(isChk);
		isChk = false;

		value = (String)detailLineProp.get(LineModel.ID_TARGET_ALLOWDUPLICATES);
		if(value!=null &&(value.trim().equals("true")))
			isChk = true;
		targetAllowDuplicatesButton.setSelection(isChk);
		isChk = false;
		value = (String)detailLineProp.get(LineModel.ID_TARGET_MUL);
		if(value!=null){
			boolean isEq = false;
			for(int i=0;i<targetMulCombo.getItemCount();i++){
				String item = targetMulCombo.getItem(i);
				if(item.equals(value)){
					targetMulCombo.select(i);
					isEq = true;
					break;
				}
			}
			targetMulCombo.add(value);

		}

		value = (String)detailLineProp.get(LineModel.ID_TARGET_AGGREGATION);
		if(value!=null){

			for(int i=0;i<targetAggregationCombo.getItemCount();i++){
				String item = targetAggregationCombo.getItem(i);
				if(item.equals(value)){
					targetAggregationCombo.select(i);

					break;
				}
			}


		}

		value = (String)detailLineProp.get(LineModel.ID_TARGET_NAVIGABILITY);
		if(value!=null){

			for(int i=0;i<targetNavigabilityCombo.getItemCount();i++){
				String item = targetNavigabilityCombo.getItem(i);
				if(item.equals(value)){
					targetNavigabilityCombo.select(i);

					break;
				}
			}


		}


		targetItem.setControl(targetComposite);
		//PKY 08082201 S 팀 프로젝트 읽기전용일때 수정불가능하도록 작업
		if(ProjectManager.getInstance()!=null && ProjectManager.getInstance().getSelectPropertyUMLElementModel()!=null){
			if(!ProjectManager.getInstance().isItemEnable(ProjectManager.getInstance().getSelectPropertyUMLElementModel())){
				boolean isEnable = ProjectManager.getInstance().isItemEnable(ProjectManager.getInstance().getSelectPropertyUMLElementModel());
					targetNavigabilityCombo.setEnabled(isEnable);
					targetAggregationCombo.setEnabled(isEnable);
					targetMulCombo.setEnabled(isEnable);
					targetAllowDuplicatesButton.setEnabled(isEnable);
					targetOrderedButton.setEnabled(isEnable);
					targetOwnedButton.setEnabled(isEnable);
					targetDerivedUnionButton.setEnabled(isEnable);
					targetDerivedButton.setEnabled(isEnable);
				}
			}
		
		targetActorRoleText.addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent e){
				if(ProjectManager.getInstance()!=null && ProjectManager.getInstance().getSelectPropertyUMLElementModel()!=null){
					UMLElementModel umlElementModel= ProjectManager.getInstance().getSelectPropertyUMLElementModel();
					if(umlElementModel!=null && umlElementModel instanceof LineModel){
						LineModel lineModel = (LineModel)umlElementModel;
						if(!ProjectManager.getInstance().isItemEnable(ProjectManager.getInstance().getSelectPropertyUMLElementModel())){
							targetActorRoleText.setText("");
							String value = (String)detailLineProp.get(LineModel.ID_TARGET_ROLE);
							if(value!=null)
								targetActorRoleText.setText(value);
						}
					}
				}
			}
			public void keyReleased(KeyEvent e){

				if(ProjectManager.getInstance()!=null && ProjectManager.getInstance().getSelectPropertyUMLElementModel()!=null){
					UMLElementModel umlElementModel= ProjectManager.getInstance().getSelectPropertyUMLElementModel();
					if(umlElementModel!=null && umlElementModel instanceof LineModel){
						LineModel lineModel = (LineModel)umlElementModel;
						if(lineModel.getDiagram()!=null && lineModel.getDiagram().isReadOnlyModel()){
							targetActorRoleText.setText("");
							String value = (String)detailLineProp.get(LineModel.ID_TARGET_ROLE);
							if(value!=null)
								targetActorRoleText.setText(value);
						}
					}
				}
			
			}
		});
		targetRoleNoteText.addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent e){
				if(ProjectManager.getInstance()!=null && ProjectManager.getInstance().getSelectPropertyUMLElementModel()!=null){
					UMLElementModel umlElementModel= ProjectManager.getInstance().getSelectPropertyUMLElementModel();
					if(umlElementModel!=null && umlElementModel instanceof LineModel){
						LineModel lineModel = (LineModel)umlElementModel;
						if(!ProjectManager.getInstance().isItemEnable(ProjectManager.getInstance().getSelectPropertyUMLElementModel())){
							targetRoleNoteText.setText("");
							String value = (String)detailLineProp.get(LineModel.ID_TARGET_ROLE_NOTE);
							if(value!=null)
								targetRoleNoteText.setText(value);
						}
					}
				}
			}
			public void keyReleased(KeyEvent e){

				if(ProjectManager.getInstance()!=null && ProjectManager.getInstance().getSelectPropertyUMLElementModel()!=null){
					UMLElementModel umlElementModel= ProjectManager.getInstance().getSelectPropertyUMLElementModel();
					if(umlElementModel!=null && umlElementModel instanceof LineModel){
						LineModel lineModel = (LineModel)umlElementModel;
						if(lineModel.getDiagram()!=null && lineModel.getDiagram().isReadOnlyModel()){
							targetRoleNoteText.setText("");
							String value = (String)detailLineProp.get(LineModel.ID_TARGET_ROLE_NOTE);
							if(value!=null)
								targetRoleNoteText.setText(value);
						}
					}
				}
			

			}
		});
		targetRoleConstraintText.addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent e){
				if(ProjectManager.getInstance()!=null && ProjectManager.getInstance().getSelectPropertyUMLElementModel()!=null){
					UMLElementModel umlElementModel= ProjectManager.getInstance().getSelectPropertyUMLElementModel();
					if(umlElementModel!=null && umlElementModel instanceof LineModel){
						LineModel lineModel = (LineModel)umlElementModel;
						if(!ProjectManager.getInstance().isItemEnable(ProjectManager.getInstance().getSelectPropertyUMLElementModel())){
							targetRoleConstraintText.setText("");
							String value = (String)detailLineProp.get(LineModel.ID_TARGET_CONSTRAINT);
							if(value!=null)
								targetRoleConstraintText.setText(value);
						}
					}
				}
			}
			public void keyReleased(KeyEvent e){

				if(ProjectManager.getInstance()!=null && ProjectManager.getInstance().getSelectPropertyUMLElementModel()!=null){
					UMLElementModel umlElementModel= ProjectManager.getInstance().getSelectPropertyUMLElementModel();
					if(umlElementModel!=null && umlElementModel instanceof LineModel){
						LineModel lineModel = (LineModel)umlElementModel;
						if(lineModel.getDiagram()!=null && lineModel.getDiagram().isReadOnlyModel()){
							targetRoleConstraintText.setText("");
							String value = (String)detailLineProp.get(LineModel.ID_TARGET_CONSTRAINT);
							if(value!=null)
								targetRoleConstraintText.setText(value);
						}
					}
				}
			

			}
		});
		targetQualifierText.addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent e){
				if(ProjectManager.getInstance()!=null && ProjectManager.getInstance().getSelectPropertyUMLElementModel()!=null){
					UMLElementModel umlElementModel= ProjectManager.getInstance().getSelectPropertyUMLElementModel();
					if(umlElementModel!=null && umlElementModel instanceof LineModel){
						LineModel lineModel = (LineModel)umlElementModel;
						if(!ProjectManager.getInstance().isItemEnable(ProjectManager.getInstance().getSelectPropertyUMLElementModel())){
							targetQualifierText.setText("");
							String value = (String)detailLineProp.get(LineModel.ID_TARGET_QUALIFIER);
							if(value!=null)
								targetQualifierText.setText(value);
						}
					}
				}
			}
			public void keyReleased(KeyEvent e){

				if(ProjectManager.getInstance()!=null && ProjectManager.getInstance().getSelectPropertyUMLElementModel()!=null){
					UMLElementModel umlElementModel= ProjectManager.getInstance().getSelectPropertyUMLElementModel();
					if(umlElementModel!=null && umlElementModel instanceof LineModel){
						LineModel lineModel = (LineModel)umlElementModel;
						if(lineModel.getDiagram()!=null && lineModel.getDiagram().isReadOnlyModel()){
							targetQualifierText.setText("");
							String value = (String)detailLineProp.get(LineModel.ID_TARGET_QUALIFIER);
							if(value!=null)
								targetQualifierText.setText(value);
						}
					}
				}
			

			}
		});
		//PKY 08082201 E 팀 프로젝트 읽기전용일때 수정불가능하도록 작업

	}

	public void createExtensionPointsCTabItem(CTabFolder p){
		FillLayout flowLayout = new FillLayout();
		CTabItem descItem = new CTabItem(tabFolder,SWT.NONE);
		descItem.setText(N3Messages.DIALOG_EXTENSIONPOINT);//PKY 08060201 S 
		GridLayout gridLayout = new GridLayout(2,false);
		GridLayout gridLayoutButton = new GridLayout(1,true);
		extensionPointComposite = new Composite(tabFolder,comp.getStyle());
		extensionPointComposite.setLayout(gridLayout);
		GridData extensionPointGroupData = new GridData(GridData.FILL_BOTH);
		extensionPointGroupData.heightHint = 500;
		extensionPointGroupData.widthHint = 400;
		GridData extensionPointButtonGroupData = new GridData(GridData.FILL_BOTH);
		extensionPointButtonGroupData.heightHint = 500;
		extensionPointButtonGroupData.widthHint = 50;
		this.extensionPointGroup = new Group(extensionPointComposite, SWT.SHADOW_ETCHED_IN);
		this.extensionPointGroup.setLayout(flowLayout);
		this.extensionPointGroup.setLayoutData(extensionPointGroupData);
		this.extensionPointButtonGroup = new Group(extensionPointComposite, SWT.SHADOW_ETCHED_IN);
		this.extensionPointButtonGroup.setLayout(gridLayoutButton);
		this.extensionPointButtonGroup.setLayoutData(extensionPointButtonGroupData);
		final Table table = new Table(extensionPointGroup, SWT.FULL_SELECTION);
		extensionPointViewer = buildAndLayoutExtensionPointTable(table);
		this.attachLabelProviderExtensionPoints(extensionPointViewer);
		this.attachCellEditorsExtensionPoints(extensionPointViewer, table);
		if(extendsPoints.toArray().length>0){
			for(int i=0;i<extendsPoints.size();i++){
				DetailPropertyTableItem sti = (DetailPropertyTableItem)extendsPoints.get(i);
				extensionPointViewer.add(sti);
			}
		}
		buttonAdd = new Button(extensionPointButtonGroup,SWT.PUSH);
		buttonRemove = new Button(extensionPointButtonGroup,SWT.PUSH);
//		buttonAdd.setText(N3Messages.POPUP_ADD);//2008040302 PKY S ;
		buttonAdd.setImage(ProjectManager.getInstance().getImage(203));//2008040302 PKY S 
		buttonAdd.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e)
			{

				DetailPropertyTableItem newItem = new DetailPropertyTableItem();
				newItem.desc = "extensionPoint";
				extendsPoints.add(newItem);
				extensionPointViewer.add(newItem);
			}

			public void widgetDefaultSelected(SelectionEvent e)
			{
			}

		});

//		buttonRemove.setText(N3Messages.POPUP_REMOVE);//2008040302 PKY S ;
		buttonRemove.setImage(ProjectManager.getInstance().getImage(204));//2008040302 PKY S 
		buttonRemove.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e)
			{
				StructuredSelection iSelection = (StructuredSelection)extensionPointViewer.getSelection();
				Object obj = iSelection.getFirstElement();
				int selectIndex=extensionPointViewer.getTable().getSelectionIndex();//PKY 08071601 S 다이얼로그 UI작업
				if (obj != null) {
					extensionPointViewer.remove(obj);
					//PKY 08071601 S 다이얼로그 UI작업
					if(extensionPointViewer.getTable().getItemCount()>=selectIndex+1){
						extensionPointViewer.getTable().select(selectIndex);
					}else{
						extensionPointViewer.getTable().select(extensionPointViewer.getTable().getItemCount()-1);
					}
					//PKY 08071601 E 다이얼로그 UI작업

					extendsPoints.remove(obj);
				}
			}

			public void widgetDefaultSelected(SelectionEvent e)
			{
			}

		});
		extensionPointButtonGroup.pack();

		//PKY 08072201 S 모든 오퍼레이션및 어트리뷰트등 정보 입력한 값에 대한 위치변경할 수있도록 수정
		buttonUp = new Button(extensionPointButtonGroup,SWT.PUSH);
		buttonUp.setImage(ProjectManager.getInstance().getImage(330));
		buttonUp.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e)
			{
				int selset=0;
				ArrayList arryList= new ArrayList();
				StructuredSelection iSelection = (StructuredSelection)extensionPointViewer.getSelection();
				if(iSelection!=null){
				Object obj = iSelection.getFirstElement();
				Table table=extensionPointViewer.getTable();
				for(int i=0; i < table.getItems().length; i++){
					if(table.getItem(i).getData()== obj){
						if(i>0){
						arryList.add(i-1,table.getItem(i).getData());
						selset=i-1;
						}else{
							arryList.add(table.getItem(i).getData());
						}
					}else{
						arryList.add(table.getItem(i).getData());
					}
				}
				extensionPointViewer.remove(arryList.toArray());	
				for(int i = 0 ; i < arryList.size(); i++){
					extensionPointViewer.add(arryList.get(i));	
				}
				extensionPointViewer.getTable().select(selset);
				}
			}
			public void widgetDefaultSelected(SelectionEvent e)
			{
			}
		});
		buttonDown = new Button(extensionPointButtonGroup,SWT.PUSH);
		buttonDown.setImage(ProjectManager.getInstance().getImage(331));
		buttonDown.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e)
			{
				int chanage= 0;
				boolean ischanage=false;
				StructuredSelection iSelection = (StructuredSelection)extensionPointViewer.getSelection();
				if(iSelection!=null){
				Object obj = iSelection.getFirstElement();
				Table table=extensionPointViewer.getTable();
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
//				removeNullArray(arryList);
				extensionPointViewer.remove(arryList.toArray());	
				for(int i = 0 ; i < arryList.size(); i++){
					extensionPointViewer.add(arryList.get(i));	
				}
				extensionPointViewer.getTable().select(chanage+1);
				
				}
			}
			public void widgetDefaultSelected(SelectionEvent e)
			{
			}
		});
		//PKY 08072201 E 모든 오퍼레이션및 어트리뷰트등 정보 입력한 값에 대한 위치변경할 수있도록 수정
		
		//PKY 08082201 S 팀 프로젝트 읽기전용일때 수정불가능하도록 작업
		if(ProjectManager.getInstance()!=null && ProjectManager.getInstance().getSelectPropertyUMLElementModel()!=null){
			if(!ProjectManager.getInstance().isItemEnable(ProjectManager.getInstance().getSelectPropertyUMLElementModel())){
				boolean isEnable = ProjectManager.getInstance().isItemEnable(ProjectManager.getInstance().getSelectPropertyUMLElementModel());

					buttonAdd.setEnabled(isEnable);
					buttonRemove.setEnabled(isEnable);
					buttonUp.setEnabled(isEnable);
					buttonDown.setEnabled(isEnable);
				}
			}
		
		descItem.setControl(extensionPointComposite);
	}


	public void createDescCTabItem(CTabFolder p){
		CTabItem descItem = new CTabItem(tabFolder,SWT.NONE);
		descItem.setText(N3Messages.POPUP_DESCRIPTION);//2008040302 PKY S ;
		FillLayout flowLayout2 = new FillLayout();
		tabFolder.setLayout(flowLayout2);
		GridData data6 = new GridData(GridData.FILL_BOTH);
		data6.heightHint = 500;
		data6.widthHint = 500;
		tabFolder.setLayoutData(data6);
		DescGroup = new Group(tabFolder, SWT.SHADOW_ETCHED_IN);
		DescGroup.setLayoutData(data6);
		DescGroup.setLayout(flowLayout2);
		GridData data3 = new GridData(GridData.FILL_BOTH);
		data3.heightHint = 300;
		data3.widthHint = 300;
		descStyledText = new StyledText(DescGroup, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		descStyledText.setLayoutData(data3);
		//2008041605PKY S
		if(detailLineProp!=null)
			desc= (String)detailLineProp.get(LineModel.ID_DESCRIPTORS);

		//2008041605PKY E
		if(desc!=null){
			descStyledText.setText(desc);
		}
		descItem.setControl(DescGroup);
		//PKY 08082201 S 팀 프로젝트 읽기전용일때 수정불가능하도록 작업
		descStyledText.addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent e){
				if(ProjectManager.getInstance()!=null && ProjectManager.getInstance().getSelectPropertyUMLElementModel()!=null){
					UMLElementModel umlElementModel= ProjectManager.getInstance().getSelectPropertyUMLElementModel();
					if(umlElementModel!=null && umlElementModel instanceof UMLModel){
						if(!ProjectManager.getInstance().isItemEnable(ProjectManager.getInstance().getSelectPropertyUMLElementModel())){
							descStyledText.setText(((UMLModel)umlElementModel).getDesc());
						}
					}else if(umlElementModel!=null && umlElementModel instanceof LineModel){
						LineModel lineModel = (LineModel)umlElementModel;
						if(lineModel.getDiagram()!=null){
							if(!ProjectManager.getInstance().isItemEnable(ProjectManager.getInstance().getSelectPropertyUMLElementModel())){
								if(desc!=null)
									descStyledText.setText(desc);
								else
								descStyledText.setText("");
							}
						}
					}
				}
			}
			public void keyReleased(KeyEvent e){

				if(ProjectManager.getInstance()!=null && ProjectManager.getInstance().getSelectPropertyUMLElementModel()!=null){
					UMLElementModel umlElementModel= ProjectManager.getInstance().getSelectPropertyUMLElementModel();
					if(umlElementModel!=null && umlElementModel instanceof UMLModel){
						if(((UMLModel)umlElementModel).isReadOnlyModel()||!((UMLModel)umlElementModel).isExistModel()){
							descStyledText.setText(((UMLModel)umlElementModel).getDesc());
						}
					}else if(umlElementModel!=null && umlElementModel instanceof LineModel){
						LineModel lineModel = (LineModel)umlElementModel;
						if(lineModel.getDiagram()!=null){
							if(lineModel.getDiagram().isReadOnlyModel||!lineModel.getDiagram().isExistModel){
								if(desc!=null)
									descStyledText.setText(desc);
								else
								descStyledText.setText("");
								System.out.print("");
							}
						}
					}
				}
			

			}
		});
		//PKY 08082201 E 팀 프로젝트 읽기전용일때 수정불가능하도록 작업
	}

	//20080723 KDI s 수정
	public void createScenarioCTabItem(CTabFolder p){
		CTabItem scenarioItem = new CTabItem(tabFolder,SWT.NONE);
		
		comp.getShell().setText(N3Messages.PALETTE_USECASE); //20080724 KDI s
		comp.getShell().setImage(ProjectManager.getInstance().getImage(2)); //20080724 KDI s
		
		scenarioComposite = new Composite(tabFolder,comp.getStyle());
		scenarioItem.setText(N3Messages.DIALOG_TAB_SCENARIO); //20080723 KDI s
		GridLayout gridLayout = new GridLayout(1,false);

		GridData data6 = new GridData(GridData.FILL_BOTH);
		data6.heightHint = 500;
		data6.widthHint = 500;
		tabFolder.setLayoutData(data6);

		scenarioComposite.setLayout(gridLayout);
		GridData data1 = new GridData(GridData.FILL_BOTH);

		data1.heightHint = 60; //20080723 KDI s
//		data1.widthHint = 100;
		groupScenarioType = new Group(scenarioComposite, SWT.SHADOW_ETCHED_IN);
		groupScenarioType.setLayoutData(data1);
		groupScenarioType.setLayout(new GridLayout());
		final Table table = new Table(groupScenarioType, SWT.FULL_SELECTION);

		table.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e)
			{
				System.out.println("");

				if(e.item.getData() instanceof DetailPropertyTableItem){

					DetailPropertyTableItem sit = (DetailPropertyTableItem)e.item.getData();
					if(oldSit==null){
						oldSit = sit;
						scenarioStyledText.setText(sit.desc);

					}
					else{
						oldSit.desc = scenarioStyledText.getText();
						oldSit = sit;
						scenarioStyledText.setText(sit.desc);

					}


				}

			}

			public void widgetDefaultSelected(SelectionEvent e)
			{
				System.out.println("");
			}

		});
		typeViewer = buildAndLayoutTable(table);
		
		this.attachLabelProvider(typeViewer);

		GridData data2 = new GridData(GridData.FILL_BOTH);
		data2.heightHint = 350;  //20080723 KDI s
//		data2.widthHint = 400;

		groupScenarioDesc = new Group(scenarioComposite, SWT.SHADOW_ETCHED_IN);
		groupScenarioDesc.setText(N3Messages.POPUP_DESCRIPTION);//2008040302 PKY S ;
		groupScenarioDesc.setLayoutData(data2);
		groupScenarioDesc.setLayout(gridLayout);

		GridData data3 = new GridData(GridData.FILL_BOTH);
//		data3.heightHint = 550; //20080723 KDI s
//		data3.widthHint = 300;
		scenarioStyledText = new StyledText(groupScenarioDesc, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		scenarioStyledText.setLayoutData(data3);



		if(scenario.toArray().length>0){
			for(int i=0;i<scenario.size();i++){
				DetailPropertyTableItem sti = (DetailPropertyTableItem)scenario.get(i);
				typeViewer.add(sti);
				if(i==0){
					this.oldSit = sti;
					this.scenarioStyledText.setText(sti.desc);
				}
			}
		}
		else{
			DetailPropertyTableItem basic = new DetailPropertyTableItem();
			basic.index= 0;
			basic.key = "기본흐름";
			DetailPropertyTableItem alternative = new DetailPropertyTableItem();
			alternative.index= 1;
			alternative.key = "대안흐름";
			DetailPropertyTableItem exception = new DetailPropertyTableItem();
			exception.index= 2;
			exception.key = "예외흐름";
			typeViewer.add(basic);
			typeViewer.add(alternative);
			typeViewer.add(exception);
			this.oldSit = basic;
		}
		table.select(0);

		table.pack();
		groupScenarioType.pack();


		scenarioStyledText.pack();
		
		scenarioItem.setControl(scenarioComposite);
		//PKY 08082201 S 팀 프로젝트 읽기전용일때 수정불가능하도록 작업
		scenarioStyledText.addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent e){
				if(ProjectManager.getInstance()!=null && ProjectManager.getInstance().getSelectPropertyUMLElementModel()!=null){
					UMLElementModel umlElementModel= ProjectManager.getInstance().getSelectPropertyUMLElementModel();
					if(umlElementModel!=null && umlElementModel instanceof UMLModel){
						if(!ProjectManager.getInstance().isItemEnable(ProjectManager.getInstance().getSelectPropertyUMLElementModel())){
							 TableItem []  iSelection = ( TableItem [])table.getSelection();
							 if(iSelection.length==1){
								
							DetailPropertyTableItem sit = (DetailPropertyTableItem)iSelection[0].getData();
							scenarioStyledText.setText(sit.desc);
							 }
//							Object obj = iSelection.getFirstElement();

//							DetailPropertyTableItem sit = (DetailPropertyTableItem)obj;
//							scenarioStyledText.setText(sit.desc);

						}
					}
				}
			}
			public void keyReleased(KeyEvent e){

			}
		});
		//PKY 08082201 E 팀 프로젝트 읽기전용일때 수정불가능하도록 작업
		
	}

	protected Control createDialogArea(Composite parent) {
		comp = (Composite)super.createDialogArea(parent);

		try {
			tabFolder = new CTabFolder(parent,SWT.TOP|SWT.BORDER);
			tabFolder.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,false));
			tabFolder.setSimple(false);
			tabFolder.setUnselectedCloseVisible(false);
			tabFolder.setUnselectedImageVisible(false);
			if(um!=null){
				if(um instanceof UseCaseModel){
					this.createScenarioCTabItem(tabFolder);
					createExtensionPointsCTabItem(tabFolder);
				}
				//20080724 KDI s
				else{
					comp.getShell().setText(N3Messages.POPUP_PROPERTIES); 
					comp.getShell().setImage(ProjectManager.getInstance().getImage(325));
				}
				//20080724 KDI e
				
				createTagCTabItem(tabFolder);
				this.createTagCMonitorVariableTabItem(tabFolder);
				createDescCTabItem(tabFolder);	
			}
			else if(lm!=null){
				if(lm instanceof AssociateLineModel){

					createSourceCTabItem(tabFolder);
					createTargetCTabItem(tabFolder);
					createDescCTabItem(tabFolder);	
//					createStateConstratintsCTabItem(tabFolder);	
				}
				else{
					if(lm instanceof ControlFlowLineModel){
						createConstraintsCTabItem(tabFolder);	
					}
					
					//20080724 KDI s
					else{
						comp.getShell().setText(N3Messages.POPUP_PROPERTIES); 
						comp.getShell().setImage(ProjectManager.getInstance().getImage(325));
					}
					//20080724 KDI e
					createDescCTabItem(tabFolder);	

				}
			}
//			else if(lm instanceof ExtendLineModel || lm instanceof IncludeLineModel
//			|| lm instanceof RealizeLineModel ||lm instanceof  DependencyLineModel
//			|| lm instanceof  AssociateLineModel
//			|| lm instanceof  RoleBindingLineModel ||lm instanceof DelegateLineModel
//			|| lm instanceof  RepresentsLineModel){
//			createSourceCTabItem(tabFolder);
//			createTargetCTabItem(tabFolder);
//			createDescCTabItem(tabFolder);	
//			}

//			this.createScenarioCTabItem(tabFolder);
//			createExtensionPointsCTabItem(tabFolder);
//			createTagCTabItem(tabFolder);

//			createSourceCTabItem(tabFolder);
//			createTargetCTabItem(tabFolder);
//			createConstraintsCTabItem(tabFolder);

//			groupScenarioDesc.pack();




		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return comp;
	}

	private TableViewer buildAndLayoutTagTable(final Table table) {
		TableViewer tableViewer = new TableViewer(table);
		TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnWeightData(100, 75, true));
		layout.addColumnData(new ColumnWeightData(100, 70, true));
		layout.addColumnData(new ColumnWeightData(50, 50, true));
		table.setLayout(layout);
		TableColumn tagColumn = new TableColumn(table, SWT.CENTER);
		tagColumn.setText("name");
		TableColumn tagType = new TableColumn(table, SWT.CENTER);
		tagType.setText("type");
		TableColumn valueColumn = new TableColumn(table, SWT.CENTER);
		valueColumn.setText("value");

		table.setHeaderVisible(true);
		return tableViewer;
	}

	private TableViewer buildAndLayoutTrigerTable(final Table table) {
		TableViewer tableViewer = new TableViewer(table);
		TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnWeightData(100, 20, true));
		layout.addColumnData(new ColumnWeightData(100, 75, true));
		layout.addColumnData(new ColumnWeightData(100, 75, true));
		table.setLayout(layout);
		TableColumn nameColumn = new TableColumn(table, SWT.CENTER);
		nameColumn.setText(this.TRIGER_NAME);
		TableColumn typeColumn = new TableColumn(table, SWT.CENTER);
		typeColumn.setText(this.TRIGER_TYPE);
		TableColumn specificationColumn = new TableColumn(table, SWT.CENTER);
		specificationColumn.setText(this.TRIGER_SPECIFICATION);

		table.setHeaderVisible(true);
		return tableViewer;
	}

	private TableViewer buildAndLayoutExtensionPointTable(final Table table) {
		TableViewer tableViewer = new TableViewer(table);
		TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnWeightData(50, 75, true));
		table.setLayout(layout);
		TableColumn accessColumn = new TableColumn(table, SWT.CENTER);
		accessColumn.setText(this.EXTENSIONPOINT_PROPERTY);
		table.setHeaderVisible(true);
		return tableViewer;
	}


	private TableViewer buildAndLayoutTable(final Table table) {
		TableViewer tableViewer = new TableViewer(table);
		TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnWeightData(50, 460, true));
		table.setLayout(layout);
		TableColumn accessColumn = new TableColumn(table, SWT.CENTER);
		accessColumn.setText(this.TYPE_PROPERTY);
		table.setHeaderVisible(true);
		return tableViewer;
	}

	private void attachLabelProviderTriger(TableViewer viewer) {
		viewer.setLabelProvider(
				new ITableLabelProvider() {
					public Image getColumnImage(Object element, int columnIndex) {
						if(element instanceof DetailPropertyTableItem){
							DetailPropertyTableItem si = (DetailPropertyTableItem)element;

						}

						return null;

					}
					public String getColumnText(Object element, int columnIndex) {
						switch (columnIndex) {
						case 0:
							String value = ((DetailPropertyTableItem)element).sName;
							return value;
						case 1:
							Number index = ((DetailPropertyTableItem)element).sType;
							return  TRIGER_TYPE_SET[index.intValue()];


						case 2:
							value = ((DetailPropertyTableItem)element).sSpecification;
							return value;

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

	private void attachLabelProviderTag(TableViewer viewer) {
		viewer.setLabelProvider(
				new ITableLabelProvider() {
					public Image getColumnImage(Object element, int columnIndex) {
						if(element instanceof DetailPropertyTableItem){
							DetailPropertyTableItem si = (DetailPropertyTableItem)element;

						}

						return null;

					}
					public String getColumnText(Object element, int columnIndex) {
						switch (columnIndex) {
						case 0:
							String value = ((DetailPropertyTableItem)element).tapName;
							return value;
						case 1:
							value = ((DetailPropertyTableItem)element).sTagType;
							return value;
						case 2:
							value = ((DetailPropertyTableItem)element).desc;
							return value;

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

	private void attachLabelProvider(TableViewer viewer) {
		viewer.setLabelProvider(
				new ITableLabelProvider() {
					public Image getColumnImage(Object element, int columnIndex) {
						if(element instanceof DetailPropertyTableItem){
							DetailPropertyTableItem si = (DetailPropertyTableItem)element;

						}

						return null;

					}
					public String getColumnText(Object element, int columnIndex) {
						if(element instanceof DetailPropertyTableItem){
							DetailPropertyTableItem si = (DetailPropertyTableItem)element;
							return si.key;
						}
						else 
							return element.toString();
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

	protected void buttonPressed(int buttonId)
	{

		if(buttonId == RESET_ID)
		{
//			usernameField.setText("");
//			passwordField.setText("");
		}
		else
		{
			try{
				if (buttonId == OK_ID) {
//					if(um)


					if(oldSit!=null){
						oldSit.desc = scenarioStyledText.getText();
					}


					if(this.um!=null){
						if(typeViewer!=null){
							TableItem[] tableItem = typeViewer.getTable().getItems();
							this.scenario.clear();
							for (int i = 0; i < tableItem.length; i++) {
								this.scenario.add(tableItem[i].getData());
								//					classModel.setAttributes((AttributeEditableTableItem)tableItem[i].getData());
							}
						}
						um.setDetailProperty((java.util.ArrayList)this.scenario.clone());
						um.setExtendsPoints((java.util.ArrayList)this.extendsPoints.clone());
						um.setTags((java.util.ArrayList)this.tags.clone());
						um.setDesc(descStyledText.getText());
						um.setMonitorVariables((java.util.ArrayList)this.monitorVariables.clone());
					}
					if(this.lm!=null){
						//2008040103 PKY S  null체크 

						String value="";
						boolean isChk;
						System.out.print(descStyledText.getText());
						ChangeDetailPropCommand cpc = new ChangeDetailPropCommand();
						cpc.setOldProp((HashMap)detailLineProp.clone());
						//2008041605PKY S
						if(DescGroup!=null){
							value = (String)descStyledText.getText();
							detailLineProp.put(LineModel.ID_DESCRIPTORS, value);
						}
						//2008041605PKY E
						if(sourceActorRoleText!=null){
							value = (String)sourceActorRoleText.getText();
							detailLineProp.put(LineModel.ID_SOURCE_ROLE, value);
						}

						if(sourceRoleNoteText!=null){
							value = (String)sourceRoleNoteText.getText();
							detailLineProp.put(LineModel.ID_SOURCE_ROLE_NOTE, value);
						}

						if(sourceRoleConstraintText!=null){
							value = (String)sourceRoleConstraintText.getText();
							detailLineProp.put(LineModel.ID_SOURCE_CONSTRAINT, value);					
						}

						if(sourceQualifierText!=null){
							value = (String)sourceQualifierText.getText();
							detailLineProp.put(LineModel.ID_SOURCE_QUALIFIER, value);
						}

						if(sourceDerivedButton!=null){						
							isChk = sourceDerivedButton.getSelection();
							detailLineProp.put(LineModel.ID_SOURCE_DERIVED, String.valueOf(isChk));
						}

						if(sourceDerivedUnionButton!=null){
							isChk = sourceDerivedUnionButton.getSelection();
							detailLineProp.put(LineModel.ID_SOURCE_DERIVEDUNION, String.valueOf(isChk));
						}

						if(sourceOwnedButton!=null){
							isChk = sourceOwnedButton.getSelection();
							detailLineProp.put(LineModel.ID_SOURCE_OWNED, String.valueOf(isChk));
						}

						if(sourceOrderedButton!=null){
							isChk = sourceOrderedButton.getSelection();
							detailLineProp.put(LineModel.ID_SOURCE_ORDERED, String.valueOf(isChk));
						}

						if(sourceAllowDuplicatesButton!=null){
							isChk = sourceAllowDuplicatesButton.getSelection();
							detailLineProp.put(LineModel.ID_SOURCE_ALLOWDUPLICATES, String.valueOf(isChk));
						}

						if(sourceMulCombo!=null){
							value = (String)sourceMulCombo.getText();
							detailLineProp.put(LineModel.ID_SOURCE_MUL, value);
						}

						if(sourceAggregationCombo!=null){
							value = (String)sourceAggregationCombo.getText();
							detailLineProp.put(LineModel.ID_SOURCE_AGGREGATION, value);
						}					

						if(sourceNavigabilityCombo!=null){
							value = (String)sourceNavigabilityCombo.getText();
							detailLineProp.put(LineModel.ID_SOURCE_NAVIGABILITY, value);
						}			

						if(targetActorRoleText!=null){
							value = (String)targetActorRoleText.getText();
							detailLineProp.put(LineModel.ID_TARGET_ROLE, value);
						}

						if(targetRoleNoteText!=null){
							value = (String)targetRoleNoteText.getText();
							detailLineProp.put(LineModel.ID_TARGET_ROLE_NOTE, value);
						}

						if(targetRoleConstraintText!=null){
							value = (String)targetRoleConstraintText.getText();
							detailLineProp.put(LineModel.ID_TARGET_CONSTRAINT, value);
						}

						if(targetQualifierText!=null){
							value = (String)targetQualifierText.getText();
							detailLineProp.put(LineModel.ID_TARGET_QUALIFIER, value);
						}

						if(targetDerivedButton!=null){
							isChk = targetDerivedButton.getSelection();
							detailLineProp.put(LineModel.ID_TARGET_DERIVED, String.valueOf(isChk));
						}

						if(targetDerivedUnionButton!=null){
							isChk = targetDerivedUnionButton.getSelection();
							detailLineProp.put(LineModel.ID_TARGET_DERIVEDUNION, String.valueOf(isChk));
						}

						if(targetOwnedButton!=null){
							isChk = targetOwnedButton.getSelection();
							detailLineProp.put(LineModel.ID_TARGET_OWNED, String.valueOf(isChk));
						}

						if(targetOrderedButton!=null){
							isChk = targetOrderedButton.getSelection();
							detailLineProp.put(LineModel.ID_TARGET_ORDERED, String.valueOf(isChk));
						}

						if(targetAllowDuplicatesButton!=null){
							isChk = targetAllowDuplicatesButton.getSelection();
							detailLineProp.put(LineModel.ID_TARGET_ALLOWDUPLICATES, String.valueOf(isChk));
						}

						if(targetMulCombo!=null){
							value = (String)targetMulCombo.getText();
							detailLineProp.put(LineModel.ID_TARGET_MUL, value);
						}

						if(targetAggregationCombo!=null){
							value = (String)targetAggregationCombo.getText();
							detailLineProp.put(LineModel.ID_TARGET_AGGREGATION, value);
						}

						if(targetNavigabilityCombo!=null){
							value = (String)targetNavigabilityCombo.getText();
							detailLineProp.put(LineModel.ID_TARGET_NAVIGABILITY, value);
						}

						if(weightText!=null){
							value = (String)this.weightText.getText();
							detailLineProp.put(LineModel.ID_WEIGHT, value);
						}

						if(guardText!=null){
							value = (String)this.guardText.getText();
							detailLineProp.put(LineModel.ID_GUARD, value);
						}


						if(effectText!=null){
							value = (String)this.effectText.getText();
							detailLineProp.put(LineModel.ID_TRAN_EFFECTS, value);
						}
//						java.util.ArrayList  = (java.util.ArrayList)this.trigers;
						detailLineProp.put(LineModel.ID_TRAN_TRIGERS, this.trigers);
						//PKY 08051401 E 디스크립션 넣을경우 방향선이 사라지는문제 Name이 사라지는문제
						if(lm instanceof MergeLineModel||lm instanceof ImportLineModel){
							detailLineProp.put(LineModel.ID_TARGET_NAVIGABILITY, "Navigable");
						}

						//PKY 08051401 E 디스크립션 넣을경우 방향선이 사라지는문제 Name이 사라지는문제

						cpc.setNewProp((HashMap)detailLineProp.clone());
						cpc.setLm(lm);
						ProjectManager.getInstance().getCommandStack().execute(cpc);




						//2008040103 PKY E  null체크 
					}
					//2008041401PKY S
					if(this.um!=null){
						if(this.um.getExtendsPoints()!=null){
							ArrayList eArray=this.um.getExtendsPoints();
							if(eArray.size()>0){
								int eheight=44;
								for(int j=0;j<eArray.size();j++){
									eheight=eheight+13;
								}

								if(eheight>=this.um.getSize().height){
									Dimension Size =   um.getSize();
									if(this.um.getUMLTreeModel()!=null){
										if(this.um.getUMLTreeModel() instanceof StrcuturedPackageTreeModel){
											StrcuturedPackageTreeModel strcuturedPackageTreeModel = (StrcuturedPackageTreeModel)this.um.getUMLTreeModel();
											if(strcuturedPackageTreeModel.getStereo()!=null){
												eheight=eheight+20;
												this.um.setStero(true);

											}
										}
									}
									if(eArray.size()>20){
										int count=eArray.size()/4;
										for(int i=0;i<count;i++){
//											if(eArray.size()>10){
	eheight=eheight-1;
//	}
										}
									}

									this.um.setSize(new Dimension(Size.width,eheight));
								}

							}
						}
						if(this.um.getStereotype()!=null){
							this.um.setStero(true);
						}
						//2008041401PKY E
					}
				}
				super.buttonPressed(buttonId);
			}
			catch(Exception e){
				e.printStackTrace();

			}
		}
	}

}
