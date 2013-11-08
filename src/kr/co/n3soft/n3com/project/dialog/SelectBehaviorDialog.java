package kr.co.n3soft.n3com.project.dialog;

import java.util.ArrayList;

import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.activity.BehaviorActivityTableItem;
import kr.co.n3soft.n3com.model.comm.ClassModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.umlclass.AttributeEditableTableItem;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ILabelProviderListener;
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
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.DrillDownAdapter;

public class SelectBehaviorDialog extends Dialog {
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
    
    public SelectBehaviorDialog(Shell parentShell) {
        super(parentShell);
        //		ProjectManager.getInstance();
        this.setShellStyle(SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE);
        this.attributes = ProjectManager.getInstance().getBehaviorActivityList();
    }

    public BehaviorActivityTableItem getBehaviorActivityTableItem() {
        return this.selectedBehaviorActivityTableItem;
    }

    public void initDesc() {
    }

    protected Control createDialogArea(Composite parent) {
        Composite comp = (Composite)super.createDialogArea(parent);
        try {
        	comp.getShell().setText(N3Messages.DIALOG_SELECT_BEHAVIOR); //20080725 KDI s
        	comp.getShell().setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT));//20080725 KDI s
        	
            SashForm sf = new SashForm(parent, SWT.HORIZONTAL);
            GridLayout layout = (GridLayout)comp.getLayout();
            FillLayout flowLayout = new FillLayout();
            sf.setLayout(flowLayout);
            GridData data5 = new GridData(GridData.FILL_BOTH);
            data5.heightHint = 300;
            data5.widthHint = 600;
            sf.setLayoutData(data5);
            layout.numColumns = 3;
            GridData data1 = new GridData(GridData.FILL_BOTH);
            data1.horizontalSpan = 2;
            data1.heightHint = 300;
            data1.widthHint = 400;
            type = new Group(sf, SWT.SHADOW_ETCHED_IN);
            type.setLayout(flowLayout);
            type.setText("");//PKY 08080501 S 저장 불러오기 할 경우 엑션 참조할 엑티비티가 나오지 않는문제
            type.setLayoutData(data1);
            final Table table = new Table(type, SWT.FULL_SELECTION);
            tableViewer = buildAndLayoutTable(table);
            attachContentProvider(tableViewer);
            attachLabelProvider(tableViewer);
            //			attachCellEditors(tableViewer, table);
            tableViewer.getTable().pack();
            tableViewer.setInput(attributes.toArray());
            type.pack();
            GridData data2 = new GridData(GridData.FILL_BOTH);
            data2.heightHint = 300;
            data2.widthHint = 200;
          //PKY 08080501 S 저장 불러오기 할 경우 엑션 참조할 엑티비티가 나오지 않는문제 
//            description = new Group(sf, SWT.SHADOW_ETCHED_IN);
//            description.setText(N3Messages.POPUP_DESCRIPTION);//2008040302 PKY S ;
//            description.setLayoutData(data2);
//            description.setLayout(flowLayout);
//            styleField = new StyledText(description, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
//            styleField.pack();
//            description.pack();
          //PKY 08080501 E 저장 불러오기 할 경우 엑션 참조할 엑티비티가 나오지 않는문제 
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
                StructuredSelection iSelection = (StructuredSelection)tableViewer.getSelection();
                Object obj = iSelection.getFirstElement();
                if (obj != null) {
                    this.selectedBehaviorActivityTableItem = (BehaviorActivityTableItem)obj;
                }
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

    private void attachLabelProvider(TableViewer viewer) {
        viewer.setLabelProvider(
            new ITableLabelProvider() {
                public Image getColumnImage(Object element, int columnIndex) {
                    return null;
                }
                public String getColumnText(Object element, int columnIndex) {
                	//20080725 KDI s
                	UMLModel um = null;
                	try{
                		if(((BehaviorActivityTableItem)element).getRef() instanceof UMLModel)
                			um = (UMLModel)((BehaviorActivityTableItem)element).getRef();
                	}
                    catch(Exception e){
                    	e.printStackTrace();
                    }
                  //20080725 KDI e
                    switch (columnIndex) {
                        case 0:
                        	String scope = um.getPackage(); //20080725 KDI s
                            return scope;
                        case 1:
                            String name = ((BehaviorActivityTableItem)element).name;
                            return name;
                        case 2:
                            String type = ((BehaviorActivityTableItem)element).type;
                            return type;
                        case 3:
                            String parentElement = ((BehaviorActivityTableItem)element).parentElement;
                            return parentElement;
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
        layout.addColumnData(new ColumnWeightData(50, 130, true)); //20080725 KDI s
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
        //		TableColumn initColumn = new TableColumn(table, SWT.CENTER);
        //		initColumn.setText(this.INITVALUE_PROPERTY);
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
                        return ((BehaviorActivityTableItem)element).name;
                    else if (TYPE_PROPERTY.equals(property))
                        return ((BehaviorActivityTableItem)element).type;
                    else if (SCOPE_PROPERTY.equals(property))
                        return ((BehaviorActivityTableItem)element).scope;
                    else
                        return ((BehaviorActivityTableItem)element).parentElement;
                }
                public void modify(Object element, String property, Object value) {
                    TableItem tableItem = (TableItem)element;
                    BehaviorActivityTableItem data = (BehaviorActivityTableItem)tableItem.getData();
                    if (NAME_PROPERTY.equals(property))
                        data.name = value.toString();
                    else if (TYPE_PROPERTY.equals(property))
                        data.type = value.toString();
                    else if (SCOPE_PROPERTY.equals(property))
                        data.scope = value.toString();
                    else
                        data.parentElement = value.toString();
                    viewer.refresh(data);
                }
            });
        viewer.setCellEditors(
            new CellEditor[] {
                new TextCellEditor(parent), new TextCellEditor(parent), new TextCellEditor(parent),
            });
        viewer.setColumnProperties(
            new String[] {
                SCOPE_PROPERTY, NAME_PROPERTY, TYPE_PROPERTY, INITVALUE_PROPERTY
            });
    }
}
