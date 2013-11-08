package kr.co.n3soft.n3com.project.dialog;

import java.util.ArrayList;

import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.activity.BehaviorActivityTableItem;
import kr.co.n3soft.n3com.model.comm.StrcuturedPackageTreeModel;
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

public class PatternModelRefDialog extends Dialog {
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
    String name="";
    BehaviorActivityTableItem selectedBehaviorActivityTableItem;
    Text className=null;

    
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

    private static final String NAME_PROPERTY = N3Messages.POPUP_NAME;//2008040302 PKY S 
    private static final String TYPE_PROPERTY = N3Messages.POPUP_TYPE;//2008040302 PKY S 
    private static final String SCOPE_PROPERTY = N3Messages.PALETTE_PACKAGE;//2008040301 PKY S 
    private static final String INITVALUE_PROPERTY = "부모노드";
    
    public PatternModelRefDialog(Shell parentShell) {
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
        
        comp.getShell().setText(N3Messages.POPUP_INSTANCE_CLASSIFIER);//20080717 KDI s			 
		comp.getShell().setImage(ProjectManager.getInstance().getImage(207));//20080724 KDI s
		
        
        ArrayList Models = new ArrayList();
      //2008040702PKY S


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
            
            
            
            
            type = new Group(sf, SWT.SHADOW_ETCHED_IN);
            type.setLayout(layout);
            type.setText(N3Messages.POPUP_SETELEMENTCLASSIFIER);//2008040302 PKY S 
            type.setLayoutData(data1);
            
            
            GridData data6 = new GridData(GridData.FILL_BOTH);
//          data1.horizontalSpan = 2;
            data6.heightHint = 20;
            data6.widthHint = 20;
          
            Label nameField = new Label(type, SWT.LEFT);
            nameField.setText(this.NAME_PROPERTY);
//            nameField.setSize(20, 50);
            nameField.setLayoutData(data6);
            nameField.pack();
            
            GridData data8 = new GridData(GridData.FILL_BOTH);
//          data1.horizontalSpan = 2;
            data6.heightHint = 10;
            data8.widthHint = 20;
            
        	className = new Text(type, SWT.LEFT | SWT.BORDER);
        	className.setEditable(true);
        	className.setSize(200, 10);
//            GridData aaa = new GridData(GridData.FILL_BOTH);
////            aaa.heightHint = 300;
//            aaa.widthHint = 50;
        	className.setLayoutData(data8);
        	className.setText("");
        	className.pack();
            
            
            GridData data2 = new GridData(GridData.FILL_BOTH);
            data2.horizontalSpan = 2;
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
                            	className.setText(strcuturedPackageTreeModel.getName());
                            	selectedModel=(UMLModel)strcuturedPackageTreeModel.getRefModel();
                            }
                        }
                    });
                //			viewer.setInput(this.get);
            

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
//                StructuredSelection iSelection = (StructuredSelection)tableViewer.getSelection();
//                Object obj = iSelection.getFirstElement();
//                if (obj != null) {
//                    this.selectedBehaviorActivityTableItem = (BehaviorActivityTableItem)obj;
//                }
            	IStructuredSelection sel = (IStructuredSelection)tableViewer.getSelection();
        		Object obj = sel.getFirstElement();
        		if(selectedModel.getUMLDataModel().getId()!=null)
        			this.name=this.selectedModel.getPackage()+"."+className.getText();
        		else
        			this.name=className.getText();
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
                            String type = ProjectManager.getInstance().getModelTypeName(ProjectManager.getInstance().getModelType(um, -1));
                            return type;
                           
                        case 1:
                        	  String scope = um.getPackage();
                              return scope;
                          
                        case 2:
                        	  String name = um.getName();
                              return name;
                          
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


}
