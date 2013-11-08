package kr.co.n3soft.n3com.project.dialog;

import java.util.ArrayList;

import kr.co.n3soft.n3com.edit.StateEditPart;
import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.activity.BehaviorActivityTableItem;
import kr.co.n3soft.n3com.model.comm.StrcuturedPackageTreeModel;
import kr.co.n3soft.n3com.model.comm.UMLElementModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.umlclass.AttributeEditableTableItem;
import kr.co.n3soft.n3com.model.umlclass.FinalClassModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
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
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.DrillDownAdapter;

public class TypeDialog extends Dialog {
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
    private Combo typeList;
    UMLModel selectedModel = null;
    int attrCount = 0;
    private String name="";
    BehaviorActivityTableItem selectedBehaviorActivityTableItem;
    private Combo nameMulCombo = null;
    private Combo langCombo = null; //20080724 KDI s
    private Button tableCheck=null;
    private Button nameCheck=null;
    private boolean checkBoolean=true;
    private boolean checkOkButton =false;
    
    public java.util.ArrayList attributes = new ArrayList();
    public java.util.ArrayList models = new ArrayList();

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
  //PKY 08071601 S State  Operation 표시 다른것 수정
    public static final String[] STATE_TYPE_SET = new String[] {
        "do", "entry", "exit"
    };
  //PKY 08071601 E State  Operation 표시 다른것 수정

    private static final String NAME_PROPERTY = N3Messages.POPUP_NAME;//2008040302 PKY S 
    private static final String TYPE_PROPERTY = N3Messages.POPUP_TYPE;//2008040302 PKY S 
    private static final String SCOPE_PROPERTY = N3Messages.PALETTE_PACKAGE;//2008040301 PKY S 
    private static final String INITVALUE_PROPERTY = "부모노드";
    
    public TypeDialog(Shell parentShell) {
        super(parentShell);
        //		ProjectManager.getInstance();
        this.setShellStyle(SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE);
        this.attributes = ProjectManager.getInstance().getTypeModel();
		for(int i = 0; i < this.attributes.size(); i++){
			if(this.attributes.get(i) instanceof  StrcuturedPackageTreeModel){
				if(((StrcuturedPackageTreeModel)this.attributes.get(i)).getRefModel()!=null)
				if(((StrcuturedPackageTreeModel)this.attributes.get(i)).getRefModel()instanceof FinalClassModel){
					models.add(this.attributes.get(i));
				}
			}
		}
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
        
        comp.getShell().setText(N3Messages.POPUP_TYPE);//20080717 KDI s			 
		comp.getShell().setImage(ProjectManager.getInstance().getImage(207));//20080724 KDI s
		

      //2008040702PKY E

        try {
            
            SashForm sf = new SashForm(parent, SWT.HORIZONTAL);
            GridLayout layout = (GridLayout)comp.getLayout();
            layout.numColumns = 2;
            FillLayout flowLayout = new FillLayout();
            sf.setLayout(flowLayout);
            GridData data5 = new GridData(GridData.FILL_BOTH);
            data5.heightHint = 400;
            data5.widthHint = 600;
            sf.setLayoutData(data5);
            
            GridData data1 = new GridData(GridData.FILL_BOTH);
//            data1.horizontalSpan = 2;
            data1.heightHint = 400;
            data1.widthHint = 600;
            
            
            
            GridLayout layout2 = new GridLayout(3, false);
            type = new Group(sf, SWT.SHADOW_ETCHED_IN);
            type.setLayout(layout2);
            type.setText(N3Messages.POPUP_SETELEMENTCLASSIFIER);//2008040302 PKY S 
            type.setLayoutData(data1);
            
            
            GridData data6 = new GridData(GridData.BEGINNING);
//          data1.horizontalSpan = 2;
//            data6.heightHint = 20;
            data6.widthHint = 100;
            nameCheck = new Button(type,SWT.RADIO);
            nameCheck.setText(this.NAME_PROPERTY);
            nameCheck.setLayoutData(data6);
            nameCheck.pack();
            nameCheck.addSelectionListener(new SelectionListener() {

                public void widgetSelected(SelectionEvent e) {
                	checkBoolean=true;
                	nameMulCombo.setEnabled(true);
                	langCombo.setEnabled(true);
                	tableViewer.getControl().setEnabled(false);
                }

                public void widgetDefaultSelected(SelectionEvent e) {
                }
              });

//            nameField.setSize(20, 50);

            
            GridData data8 = new GridData(GridData.BEGINNING);
//          data1.horizontalSpan = 2;
//            data6.heightHint = 10;
            data8.widthHint = 50;

            //20080724 KDI s
            langCombo = new Combo(type, SWT.DROP_DOWN | SWT.READ_ONLY);
            if(ProjectManager.getInstance().getSelectNodes().size()>0){
            	if(ProjectManager.getInstance().getSelectNodes().get(0) instanceof StateEditPart){            		
            		langCombo.add(" ");
            	}else{
            		langCombo.add("C++");
            		langCombo.add("Java");            		            		
            	}
            }
            langCombo.setLayoutData(data8);
            langCombo.pack();
            langCombo.select(1);
            langCombo.addSelectionListener(new SelectionListener() {
            	public void widgetSelected(SelectionEvent e){
            		System.out.print("");
            		if(langCombo.getText().equalsIgnoreCase("java")){
                        if(ProjectManager.getInstance().getSelectNodes().size()>0){
                        	nameMulCombo.removeAll();
                        	if(ProjectManager.getInstance().getSelectNodes().get(0) instanceof StateEditPart){
                        		nameMulCombo.add("do");
                        		nameMulCombo.add("entry");
                        		nameMulCombo.add("exit");
                        	}else{
                        		nameMulCombo.add("String");
                        		nameMulCombo.add("boolean");
                        		nameMulCombo.add("byte");
                        		nameMulCombo.add("char");
                        		nameMulCombo.add("double");
                        		nameMulCombo.add("float");
                        		nameMulCombo.add("int");
                        		nameMulCombo.add("long");
                        		nameMulCombo.add("short");
                        	}                        	
                        }
            		}
            		else{
                        if(ProjectManager.getInstance().getSelectNodes().size()>0){
                        	nameMulCombo.removeAll();
                        	if(ProjectManager.getInstance().getSelectNodes().get(0) instanceof StateEditPart){
                        		nameMulCombo.add("do");
                        		nameMulCombo.add("entry");
                        		nameMulCombo.add("exit");
                        	}else{
                        		nameMulCombo.add("char");
                        		nameMulCombo.add("short");
                        		nameMulCombo.add("int");
                        		nameMulCombo.add("long");
                        		nameMulCombo.add("float");
                        		nameMulCombo.add("double");
                        		nameMulCombo.add("bool");
                        		nameMulCombo.add("pointer");                        		
                        	}
                        }
            		}
                	if(nameMulCombo.getItemCount()>0)
                		nameMulCombo.select(0);
            	}

				public void widgetDefaultSelected(SelectionEvent e) {					
					System.out.print("");
				}
            });
            //20080724 KDI e
            
            nameMulCombo = new Combo(type,SWT.DROP_DOWN);
            //PKY 08071601 S State  Operation 표시 다른것 수정
            if(ProjectManager.getInstance()!=null && ProjectManager.getInstance().getSelectPropertyUMLElementModel()!=null){
            	
            		nameMulCombo.add("String");
            		nameMulCombo.add("boolean");
            		nameMulCombo.add("byte");
            		nameMulCombo.add("char");
            		nameMulCombo.add("double");
            		nameMulCombo.add("float");
            		nameMulCombo.add("int");
            		nameMulCombo.add("long");
            		nameMulCombo.add("short");
            	
            }
            //PKY 08071601 E State  Operation 표시 다른것 수정
            data8 = new GridData(GridData.END);
            data8.widthHint = 130;
            nameMulCombo.setLayoutData(data8);
            nameMulCombo.pack();
            nameMulCombo.select(0);
            nameMulCombo.setVisibleItemCount(10);
            nameMulCombo.addSelectionListener(new SelectionListener() {
            	public void widgetSelected(SelectionEvent e){
            		name=nameMulCombo.getText();
            	}

				public void widgetDefaultSelected(SelectionEvent e) {
					name=nameMulCombo.getText();
					
				}
            });
            nameMulCombo.addKeyListener( new KeyListener(){
            	public void keyPressed(KeyEvent e){
            		System.out.print("");
            	}

            	public void keyReleased(KeyEvent e){
            		name=nameMulCombo.getText();
            	}
            });
            tableCheck = new Button(type,SWT.RADIO);
            tableCheck.setText(N3Messages.POPUP_SELECT_TYPE);
            data8 = new GridData(GridData.FILL_BOTH);
            data8.horizontalSpan = 3;
            tableCheck.setLayoutData(data8);
            tableCheck.pack();
            
            tableCheck.addSelectionListener(new SelectionListener() {

                public void widgetSelected(SelectionEvent e) {
                	checkBoolean=false;
                	nameMulCombo.setEnabled(false);
                	langCombo.setEnabled(false);
                	tableViewer.getControl().setEnabled(true);                	
                }

                public void widgetDefaultSelected(SelectionEvent e) {
                }
              });
            
            GridData data2 = new GridData(GridData.FILL_BOTH);
            data2.horizontalSpan = 3;
            data2.heightHint = 300;
            data2.widthHint = 500;

          
            final Table table = new Table(type, SWT.FULL_SELECTION);


            tableViewer = buildAndLayoutTable(table);
            
            attachContentProvider(tableViewer);
            attachLabelProvider(tableViewer);
            tableViewer.getTable().pack();
            tableViewer.setInput(models.toArray());
            table.setLayoutData(data2);
            type.pack();
            //3월초~3월말 완료
            
            tableViewer.addSelectionChangedListener(
                    new ISelectionChangedListener() {
                        public void selectionChanged(SelectionChangedEvent event) {
                            IStructuredSelection sel = (IStructuredSelection)event.getSelection();
                            Object obj = sel.getFirstElement();
                            if(obj instanceof StrcuturedPackageTreeModel){
                            	StrcuturedPackageTreeModel strcuturedPackageTreeModel = (StrcuturedPackageTreeModel)obj;
//                            	className.setText(strcuturedPackageTreeModel.getName());
                            	selectedModel=(UMLModel)strcuturedPackageTreeModel.getRefModel();
                            	tableCheck.setEnabled(true);
                            }
                        }
                    });
                //			viewer.setInput(this.get);
            
			//PKY 08082201 S 팀 프로젝트 읽기전용일때 수정불가능하도록 작업
			
			if(ProjectManager.getInstance()!=null && ProjectManager.getInstance().getSelectPropertyUMLElementModel()!=null){
				UMLElementModel umlElementModel= ProjectManager.getInstance().getSelectPropertyUMLElementModel();
				if(umlElementModel!=null && umlElementModel instanceof UMLModel){
					if(!ProjectManager.getInstance().isItemEnable(ProjectManager.getInstance().getSelectPropertyUMLElementModel())){
						boolean isEnable = ProjectManager.getInstance().isItemEnable(ProjectManager.getInstance().getSelectPropertyUMLElementModel());

						tableViewer.getTable().setEnabled(isEnable);
						nameMulCombo.setEnabled(isEnable);
						tableCheck.setEnabled(isEnable);
	                	langCombo.setEnabled(isEnable);
	                	nameCheck.setEnabled(isEnable);

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
               	this.setCheckOkButton(true);
//                StructuredSelection iSelection = (StructuredSelection)tableViewer.getSelection();
//                Object obj = iSelection.getFirstElement();
//                if (obj != null) {
//                    this.selectedBehaviorActivityTableItem = (BehaviorActivityTableItem)obj;
//                }
            	IStructuredSelection sel = (IStructuredSelection)tableViewer.getSelection();
        		Object obj = sel.getFirstElement();
//        		if(selectedModel.getUMLDataModel().getId()!=null)
//        			this.name=this.selectedModel.getPackage()+"."+className.getText();
//        		else
//        			this.name=className.getText();
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
    
    public String  setSelected(){   	
    	
        return "이바보야";
        
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
                        	//20080805 KDI s
                        	String name = um.getName();
                            return name;
//                            String type = ProjectManager.getInstance().getModelTypeName(ProjectManager.getInstance().getModelType(um, -1));
//                            return type;
                            //20080805 KDI e
                           
                        case 1:
                        	  String scope = um.getPackage();
                              return scope;
                          
                        case 2:
                        	//20080805 KDI s
                        	String type = ProjectManager.getInstance().getModelTypeName(ProjectManager.getInstance().getModelType(um, -1));
                            return type;
//                        	  String name = um.getName();
//                              return name;
                              //20080805 KDI e
                          
                        case 3:
//                            String parentElement = ((BehaviorActivityTableItem)element).parentElement;
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
        typeColumn.setText(this.NAME_PROPERTY);//.TYPE_PROPERTY);//20080805 KDI s
        TableColumn accessColumn = new TableColumn(table, SWT.LEFT);
        accessColumn.setText(this.SCOPE_PROPERTY);
        TableColumn nameColumn = new TableColumn(table, SWT.LEFT);
        nameColumn.setText(this.TYPE_PROPERTY);//.NAME_PROPERTY);//20080805 KDI s
      
        //		TableColumn initColumn = new TableColumn(table, SWT.CENTER);
        //		initColumn.setText(this.INITVALUE_PROPERTY);
        table.setHeaderVisible(true);
        return tableViewer;
    }

	public TableViewer getTableViewer() {
		return tableViewer;
	}

	public void setTableViewer(TableViewer tableViewer) {
		this.tableViewer = tableViewer;
	}

	public UMLModel getSelectedModel() {
		return selectedModel;
	}

	public void setSelectedModel(UMLModel selectedModel) {
		this.selectedModel = selectedModel;
	}

	public Combo getNameMulCombo() {
		return nameMulCombo;
	}

	public void setNameMulCombo(Combo nameMulCombo) {
		this.nameMulCombo = nameMulCombo;
	}

	public Button getTableCheck() {
		return tableCheck;
	}

	public void setTableCheck(Button tableCheck) {
		this.tableCheck = tableCheck;
	}

	public Button getNameCheck() {
		return nameCheck;
	}

	public void setNameCheck(Button nameCheck) {
		this.nameCheck = nameCheck;
	}

	public boolean isCheckBoolean() {
		return checkBoolean;
	}

	public void setCheckBoolean(boolean checkBoolean) {
		this.checkBoolean = checkBoolean;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isCheckOkButton() {
		return checkOkButton;
	}

	public void setCheckOkButton(boolean checkOkButton) {
		this.checkOkButton = checkOkButton;
	}


}